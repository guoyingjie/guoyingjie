package com.broadeast.controller.wifidog;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.broadeast.service.impl.PingService;
@Controller
@RequestMapping("w")
public class PingController {
	private static Logger log=Logger.getLogger(PingController.class);
	@Autowired
	private PingService pingService;
	
	@RequestMapping("ping")
	@ResponseBody
	public String ping(@RequestParam(defaultValue="wifdog") String gw_mac,@RequestParam String sys_uptime,@RequestParam String sys_memfree,@RequestParam String gw_id,
			@RequestParam String sys_load,@RequestParam String wifidog_uptime,HttpServletRequest request,HttpServletResponse response){
		log.info("路由器心跳发送成功----");
		System.out.println("路由器心跳发送成功----");
		String mac;
		if("wifdog".equals(gw_mac)){
			mac=gw_id.replace(":", "");
		}else{
			mac=gw_mac.replace(":", "");
		}
		
		List<Map<String, Object>> list=pingService.selRouterByMac(mac);
		String wanip=getWanIp(request, response);
		int i=0;
		if(list!=null&&list.size()!=0){//添加路由器
			//更新路由器的状态
			i=pingService.updateRouter(mac,wanip);
		}
		return i==1?"Pong":"Ping";
	}
	/**
	 * 获得路由wanip
	 * @param request
	 * @param response
	 * @return
	 */
	public String getWanIp(HttpServletRequest request,HttpServletResponse response){
		   String wanip = request.getHeader("x-forwarded-for");  
	        if (wanip == null || wanip.length() == 0 || wanip.equalsIgnoreCase("unknown")) {  
	        	wanip = request.getHeader("Proxy-Client-IP");  
	        }  
	        if (wanip == null || wanip.length() == 0 || wanip.equalsIgnoreCase("unknown")) {  
	        	wanip = request.getHeader("WL-Proxy-Client-IP");  
	        }  
	        
	        if (wanip == null || wanip.length() == 0 || wanip.equalsIgnoreCase("unknown")) {  
	        	wanip = request.getHeader("X-Real-IP");  
	        }  
	        
	        if (wanip == null || wanip.length() == 0 || wanip.equalsIgnoreCase("unknown")) {  
	        	wanip = request.getRemoteAddr();  
	        }	
	        return wanip;
	}
	
}
