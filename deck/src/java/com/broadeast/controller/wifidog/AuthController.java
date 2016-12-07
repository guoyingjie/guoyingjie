package com.broadeast.controller.wifidog;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.broadeast.bean.RouterAndDevice;
import com.broadeast.entity.SiteCustomerInfo;
import com.broadeast.service.impl.AuthService;
import com.broadeast.util.BASE64;
import com.broadeast.util.BigDecimalUtil;

@Controller
@RequestMapping("w")
public class AuthController {
	private static Logger log=Logger.getLogger(AuthController.class);
	@Autowired
	private AuthService authService;
	/**
	 * wifidog回调执行auth方法，校验token值是否一致，一致则放行，否则放行失败
	 * @param token 服务器生成的token值
	 * @param stage 请求类别，counters/login/logout，表示已认证的保活/新认证用户/超时删除用
	 * @param gw_id	路由器mac
	 * @param mac 用户设备mac
	 * @param request 
	 * @param response
	 * @param session
	 * @throws IOException
	 */
	@RequestMapping("auth")
	@ResponseBody
	public String auth(@RequestParam String token,@RequestParam String stage,@RequestParam String gw_id,@RequestParam String mac,@RequestParam(defaultValue="jk") String gw_mac, 
			@RequestParam String ip,@RequestParam String incoming,@RequestParam String outgoing,HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("进入到auth认证方法----------");
		System.out.println("回调值---token="+token+"--stage="+stage+"--gw_id="+gw_id+"--mac="+mac+"--incoming="+incoming+"--outgoing="+outgoing+"-----ip="+ip);
		//wifidog请求时的请求类别为login或者counters时 去查询t_site_customer_info表,
		//根据用户设备mac去查询如果查到再根据到期时间或者是流量以及免费体验时间,去决定是否放行
		//int usedFlow=Integer.parseInt("5170153828")+Integer.parseInt("5170153828");
		if(null==token||"".equals(token)){
			return "Auth: 0";
		}
		
		//防止破解
		if(token.indexOf("s")==-1){
			RouterAndDevice ro=authService.selRouterAndDevice(mac,token);
			if(ro!=null){
				if(!ro.getToken().equals(token)){
					return "Auth: 0";
				}
			}else{
				return "Auth: 0";
			}
			
		}
		boolean flag=authService.getSiteState(token);
		if(!flag){
			if(token.indexOf("s")!=-1){
				return "Auth: 0";
			}
			return "Auth: 1";
		}
		int i = 0;
		boolean b;
		String dfid="wifidog";
		//当gw_mac为默认值时代表用的是wifidog标准版,不为默认值时代表用的是集客系统
		String newRouter;
		if("jk".equals(gw_mac)){    
			newRouter=gw_id.replace(":", "");
		}else{
			newRouter=gw_mac.replace(":", "");
		}
		String wanIp=getWanIp(request, response);
		RouterAndDevice rad= null;
		SiteCustomerInfo sci = null;
		BigDecimal newUsedFlows =BigDecimalUtil.divide( BigDecimalUtil.add(new BigDecimal(incoming), new BigDecimal(outgoing)),new BigDecimal(1024));
	
		
		if(token.indexOf("s")!=-1){//用户切换账号时
			
			String newToken = token.split("s")[0];
			rad=authService.selRouterAndDevice(mac,newToken);
			sci = authService.selSiteCustomerInfo(mac, newToken,newRouter);
			if(sci!=null){
				String totalFlows = sci.getTotalFlow()==null?"0":sci.getTotalFlow();
				//判断用户走的是流量计费
				if (new Date().getTime() >= sci.getExpirationTime().getTime()) {
					if(!totalFlows.equals("0")&&totalFlows!=null){//判断用户是否还有流量
						authService.updateUserLoginTime(mac,rad,newRouter,incoming, outgoing);
					}
				}
			}
			return "Auth: 0";
		}
		rad=authService.selRouterAndDevice(mac,token);
		sci = authService.selSiteCustomerInfo(mac, token,newRouter);
		if (rad != null) {// 如果rad查不到的话则表示账户信息表里的没有此人,直接踢掉
			if (rad.getToken().equals(token)) {// token相同时
				switch (stage) {
				case "login":
					b = authService.checkResults(rad,sci, mac, newUsedFlows, token, wanIp, newRouter, incoming,outgoing);
					if (b) {// 如果校验通过则在登录日志表添加记录
						i = authService.insertUserLogin(rad, ip, newRouter, mac, wanIp, incoming, outgoing, dfid);
						
					}
					break;
				case "counters":
					b = authService.checkResults(rad,sci, mac, newUsedFlows, token,wanIp, gw_id, incoming,outgoing);
					if (b) {
						i=authService.upateUserTime(mac, rad,newRouter, incoming, outgoing);
					}
					break;
				default:
					authService.updateUserLoginTime(mac,rad,newRouter,incoming, outgoing);
		
					return "Auth: 0";
				}
			}
		} else {// 多台设备登录同一个账号踢人
			authService.updateUserLoginTime(mac,rad,newRouter,incoming, outgoing);
		}
		
		if(sci!=null){
			Date luckTime = sci.getLuckTime();
			if(null!=luckTime){
				long time = new Date().getTime()-luckTime.getTime();
				if(time<24*60*60*1000){
					authService.updateUserLoginTime(mac,rad,newRouter,incoming, outgoing);// 更新用户portal日志离线时间与流量
					return "Auth: 0";
				}
			}
		}
		log.info("放行时执行的最终结果---------0失败----1成功"+i);
		return i==1?"Auth: 1":"Auth: 0";
	}
	/**
	 * 获得路由wanip
	 * @param request
	 * @param response
	 * @return
	 */
	public String getWanIp(HttpServletRequest request,HttpServletResponse response){
//		   String wanip = request.getHeader("x-forwarded-for");  
//		   log.error("x-forwarded-for-----:"+wanip);
//	        if (wanip == null || wanip.length() == 0 || wanip.equalsIgnoreCase("unknown")) {  
//	        	wanip = request.getHeader("Proxy-Client-IP");  
//	        	  log.error("Proxy-Client-IP----:"+wanip);
//	        }  
//	        if (wanip == null || wanip.length() == 0 || wanip.equalsIgnoreCase("unknown")) {  
//	        	wanip = request.getHeader("WL-Proxy-Client-IP");  
//	        	  log.error("WL-Proxy-Client-IP-----:"+wanip);
//	        }  
//	        
//	        if (wanip == null || wanip.length() == 0 || wanip.equalsIgnoreCase("unknown")) {  
	        String	wanip = request.getHeader("X-Real-IP");  
	        	  log.error("X-Real-IP-----:"+wanip);
//	        }  
//	        
	        if (wanip == null || wanip.length() == 0 || wanip.equalsIgnoreCase("unknown")) {  
	        	wanip = request.getRemoteAddr();  
	        	  log.error("x-forwarded-for-----:"+wanip);
	        }	
	        return wanip;
	}
}
