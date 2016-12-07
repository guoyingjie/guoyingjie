package com.broadeast.controller.weixin;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.broadeast.bean.WinxinSingleton;
import com.broadeast.entity.CloudSite;
import com.broadeast.entity.PortalUser;
import com.broadeast.entity.SiteCustomerInfo;
import com.broadeast.entity.UserBalance;
import com.broadeast.entity.UserRecommend;
import com.broadeast.entity.UserSign;
import com.broadeast.entity.WechatUserInfo;
import com.broadeast.service.impl.MyselfPayService;
import com.broadeast.service.impl.UserService;
import com.broadeast.service.impl.WeChatOtherService;
import com.broadeast.service.impl.WeChatService;
import com.broadeast.util.BASE64;
import com.broadeast.util.CalendarUtil;
import com.broadeast.util.EncoderHandlerUtil;
import com.broadeast.util.ExecuteResult;
import com.broadeast.util.MD5;
import com.broadeast.util.PropertiesParam;
import com.broadeast.util.RandomUtil;
import com.broadeast.util.Sign;
import com.broadeast.util.WanipUtil;
import com.broadeast.util.toLoginUtil;
import com.broadeast.weixin.comment.HttpService;
import com.broadeast.weixin.comment.JsonUtil;
import com.broadeast.weixin.comment.WxPayApi;

@Controller
@RequestMapping("weChatPublicNum")
@SuppressWarnings("all")
public class WeChatPublicNumController {

	private static final String APPID = "wxc5fb6a6dabc34dfb";
	private static final String APPSECRET = "ceafa600a2d9b2a98d36885081d16058";
	private static final String MCHID = "1332831801";
	private static Logger logger = Logger.getLogger(WeChatPublicNumController.class);
	private String token;
	@Resource(name="weChatService")
	private WeChatService weChatService;
	
	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name="weChatOtherService")
	private WeChatOtherService weChatOtherService;
	
	@Resource(name="myselfPayService")
	private MyselfPayService myselfPayService;

	
    /**
	 * @Description 授权微信公众号拿到用户的微信信息code
	 * @date 2016年6月14日下午1:13:17
	 * @author guoyingjie
	 * @return
	 */
	@RequestMapping("authorizeToUser")
	public String authorizeToUser(){
        String redirectUrl = PropertiesParam.DeckUrl+"deck/weChatPublicNum/getWeChatUserInfo?rand="+Math.random();
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+APPID+"&redirect_uri="
		+redirectUrl+"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
		return "redirect:"+url;
	}
	
	/**
	 * @Description 微信回调路径得到openid,判断用户是否绑定
	 * @date 2016年6月14日下午2:13:52
	 * @author guoyingjie
	 * @throws  
	 */
	@RequestMapping("getWeChatUserInfo")
	public String getWeChatUserInfo(HttpServletRequest request,HttpSession session){
		String code = (request.getParameter("code")+"").trim();
		try {
     		Map<String, Object> map = WxPayApi.getOpenidAndAccessToken(APPID, APPSECRET, code);
     		if(map!=null){
     			String openid = map.get("openid")==null?null:map.get("openid").toString();
     			String accessToken = map.get("access_token")==null?null:map.get("access_token").toString();
//				String openid="oXfdYv_ZtIT0J9cDyL160Nm2JMXU";
//				String accessToken="11";
				session.setAttribute("openid",openid);
     			session.setAttribute("accessToken", accessToken);
     			WinxinSingleton ws= WinxinSingleton.getInstance();
     			Map<String, String> wsMap = ws.getMap();
     			wsMap.put("access_token", accessToken);
     			wsMap.put("tokenTime", new Date().getTime()+"");
     			//根据用户的openid判断此openid是否绑定过用户
     			WechatUserInfo userinfo = weChatService.whetherBindUser(openid);
     			if(userinfo==null){//用户没有绑定,直接去绑定的页面
     				session.setAttribute("url", "/weChatPublicNum/bindUser");
     				return "/wechat/binduser";
     			}else{
     				PortalUser user = userService.getPortalUserById(userinfo.getPortal_user_id());
     				if(user==null){
     					return "/wechat/payForMyself";
     				}
     				//检测用户微信信息是否已经改变，改变则更新新的用户信息
     				weChatService.updateUserWxMessage(userinfo,accessToken);
     				session.setAttribute("userinfo", userinfo);
     				//检测到已经判定了用户直接到用户详情
     				return this.getUserDetails(user,openid,session,request,userinfo);
     			}
    		}
		} catch (Exception e) {
			 logger.error("获得openid失败",e);
		}
		//根据ip没有获得场所信息,暂时处理到这个页面,后期的讨论在处理这种情况
		return  "/wechat/nosite";
	}
	
	/**
	 * @Description  检测到输入的账户不存在.去注册页面,注册并且绑定成功了就来到这个页面,导航到详情页
	 * @date 2016年6月22日下午2:13:39
	 * @author guoyingjie
	 * @param username
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("toUserDetailsPage")
	public String toUserDetailsPage(@RequestParam String username,@RequestParam String openId,HttpSession session,HttpServletRequest request) throws Exception{
		WechatUserInfo winfo =  weChatService.whetherBindUser(openId);
		PortalUser user = userService.getPortalUserById(winfo.getPortal_user_id());
		return this.getUserDetails(user, openId,session, request, winfo);
	}
	/**
	 * @Description:时刻获取用户的信息	
	 * @author songyanbiao
	 * @date 2016年7月11日 下午1:54:52
	 * @param
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("getUserMessage")
	@ResponseBody
	public String getUserMessage(@RequestParam String openId,@RequestParam int siteId,HttpSession session) throws Exception{
		ExecuteResult ex= new ExecuteResult();
		Map<String,Object> map= new HashMap<>();
		WechatUserInfo info =  weChatService.whetherBindUser(openId);
		PortalUser user = userService.getPortalUserById(info.getPortal_user_id());
		CloudSite site=myselfPayService.getSite(siteId+"");
		String nickName = user.getUserNickname();
		if(nickName!=null&&!"".equals(nickName)){
			map.put("nickname", nickName);
		}else{
			nickName=BASE64.decryptBASE64(info.getNickname());
			map.put("nickname", nickName);
		}
		if(user.getImageUrl()!=null&&!"".equals(user.getImageUrl())){
			map.put("imageUrl", user.getImageUrl());
		}else{
			map.put("weixinurl", info.getHeadimgurl());
		}
		//获取用户的余额
		UserBalance ub = weChatService.getUserBalanceByName(user.getUserName());
		if(ub!=null){
			map.put("balance",  ub.getBalance());
		}else{
			map.put("balance", "0.00");
		}
		SiteCustomerInfo infos = weChatService.getSiteCoustomInfo(user.getId(),site.getId());
		if(infos!=null){
			map=userService.getMap(map, siteId, user.getId());
		}else{
			map=weChatService.setMap(map);
		}
		int mess = userService.getMessageNumber(user.getId());
		ex.setCode(mess);
		ex.setData(map);
		return ex.toJsonString();
	}
	
	/**
	 * 
	 * @Description 这个是测试用的,测试完成后可以删除啊
	 * @date 2016年11月22日下午4:52:26
	 * @author guoyingjie
	 * @param openid
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getDate")
	public String getDate(@RequestParam String openid,HttpSession session,HttpServletRequest request) throws Exception{
		WechatUserInfo userinfo = weChatService.whetherBindUser(openid);
		PortalUser user = userService.getPortalUserById(userinfo.getPortal_user_id());
		return this.getUserDetails(user, openid, session, request, userinfo);
	}
	
	
	/**
	 * @Description 用户绑定微信公众号,跳转到用户详情页
	 * 
	 * 当检测没有时间或者流量的时候直接去给自己充的
	 * 
	 * @date 2016年6月14日下午4:37:14
	 * @author guoyingjie
	 * @param portalUserId
	 * @throws Exception 
	 */
	//@RequestMapping("getUserDetails")
	public String getUserDetails(PortalUser user,String openId,HttpSession session,HttpServletRequest request,WechatUserInfo info) throws Exception{
		//PortalUser user = userService.getPortalUserById(portalUserId);
		if(user!=null){
			session.setAttribute("openid", openId);
			session.setAttribute("proUser", user);
			session.setAttribute("tel", user.getUserName());
			session.setAttribute("userId",user.getId());
			//根据用户请求的外网ip获得用户所在的场所的信息
			CloudSite site = weChatService.getSiteIdByIp(request);
			if(site!=null){
				session.setAttribute("site", site);
				//getUser(user, info, site, session);
			    return "/wechat/userdetails";
			}else{//如果无法通过外网ip获取用户的归属场所，则根据用户名获取归属场所
				List<Map<String, Object>> ls=weChatOtherService.checkUserSite(user.getId());
				if(ls.size()!=0){//如果根据用户名也获取不到用户的归属场所则直接让用户去充值余额
					List<CloudSite>  siteList=weChatService.getSite(ls);
					if(siteList.size()>1){
						session.setAttribute("siteList", siteList);
						return "/wechat/allSite";
					}else {
						session.setAttribute("site", siteList.get(0));
						//getUser(user, info, siteList.get(0), session);
						return "/wechat/userdetails";
					}
				}
				return "/wechat/payForMyself";
			}
		}
		return "/wechat/payForMyself";
	}
	/**
	 * 
	 *	@Description:用户进入到归属场所的额个人中心
	 *  @author songyanbiao
	 *	@Date 2016年6月28日 
	 *	@param siteId
	 *	@return
	 * @throws Exception 
	 */
	@RequestMapping("UserOfSiteDetails")
	public String UserOfSiteDetails(@RequestParam String siteId,@RequestParam String openId,HttpSession session) throws Exception{
		WechatUserInfo	userinfo=weChatService.whetherBindUser(openId);
		PortalUser user=userService.getPortalUserById(userinfo.getPortal_user_id());
		CloudSite site=myselfPayService.getSite(siteId);
		session.setAttribute("site", site);
		if(user!=null){
			session.setAttribute("openid", openId);
			session.setAttribute("proUser", user);
			session.setAttribute("tel", user.getUserName());
			session.setAttribute("userId",user.getId());
			//getUser(user, userinfo, site, session);
			return "/wechat/userdetails";
		}
		return "/wechat/nosite";
	}
	/**
	 * 
	 *	@Description:进去个人中心获取用户的信息提取方法
	 *  @author songyanbiao
	 *	@Date 2016年6月28日 
	 *	@param user
	 *	@param info
	 *	@param site
	 *	@param session
	 *	@throws Exception
	 */
	public void getUser(PortalUser user,WechatUserInfo info,CloudSite site,HttpSession session) throws Exception{
			String nickName = user.getUserNickname();
			if(nickName!=null&&!"".equals(nickName)){
				session.setAttribute("nickname",nickName);
			}else{
				nickName=BASE64.decryptBASE64(info.getNickname());
				session.setAttribute("nickname",nickName);
				
			}
			if(user.getImageUrl()!=null&&!"".equals(user.getImageUrl())){
				session.setAttribute("imageUrl",user.getImageUrl());
			}else{
				session.setAttribute("weixinurl",info.getHeadimgurl());
			}
			//获取用户的余额
			UserBalance ub = weChatService.getUserBalanceByName(user.getUserName());
			if(ub!=null){
				session.setAttribute("balance", ub.getBalance());
			}else{
				session.setAttribute("balance", "0.00");
			}
			int mess = userService.getMessageNumber(user.getId());
			session.setAttribute("mess", mess);
			session.setAttribute("site",site);
			SiteCustomerInfo infos = weChatService.getSiteCoustomInfo(user.getId(),site.getId());
			if(infos!=null){
				userService.setInfoToSession(site.getId(),user.getId(), session);
			}else{
				weChatService.setSession(session);
			}
	}
	/**
	 * @Description  绑定微信用户
	 * @date 2016年6月14日下午7:41:25
	 * @author guoyingjie
	 * @param openid
	 * @param accessToken
	 * @param userName
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("bindUser")
	@ResponseBody
	public String bindUser(@RequestParam String openid,@RequestParam String accessToken,@RequestParam String password,
			@RequestParam String userName,@RequestParam(defaultValue="") String code,HttpSession session) throws Exception{
		String url="/weChatPublicNum/gotoBindSuccess";
		session.setAttribute("openid", openid);
		return weChatService.bindUserOpenid(url,openid, accessToken, password, userName, code, session);
	}
	 
	/**
	 * @Description 绑定成功后跳转到绑定成功页面
	 * @date 2016年6月14日下午6:55:27
	 * @author guoyingjie
	 * @param siteId
	 * @param userId
	 * @return
	 */
	@RequestMapping("gotoBindSuccess")
	public String gotoBindSuccess(@RequestParam String openId,@RequestParam String userName,HttpSession session){
		session.setAttribute("openid", openId);
		session.setAttribute("tel", userName);
		return "/wechat/bindSucess";
	}
	/**
	 * @Description 取消绑定成功  
	 * @date 2016年6月23日下午7:46:01
	 * @author guoyingjie
	 * @return
	 */
	@RequestMapping("canBindSuccess")
	public String canBindSuccess(){
		return "/wechat/canSucess";
	}
	
	/**
	 * @Description 绑定与取消绑定跳转的页面  
	 * @date 2016年6月15日下午7:24:59
	 * @author guoyingjie
	 * @param userName
	 * @return
	 */
	@RequestMapping("goToChangeNum")
	public String goToChangeNum(@RequestParam String userName,@RequestParam(defaultValue="") String openId,@RequestParam String siteId,HttpSession session){
		session.setAttribute("userName", userName);
		session.setAttribute("openid", openId);
		session.setAttribute("siteId", siteId);
		return "/wechat/account";
	}
	/**
	 * @Description 更换绑定
	 * @date 2016年6月15日下午7:38:27
	 * @author guoyingjie
	 * @param userName
	 * @param session
	 * @return
	 */
	@RequestMapping("changeBind")
	public String changeBind(@RequestParam String openId,@RequestParam String userName,@RequestParam String siteId,HttpSession session){
		session.setAttribute("userName", userName);
		session.setAttribute("openid", openId);
		session.setAttribute("siteId", siteId);
		return "/wechat/changebind";
	}
	
	/**
	 * @Description 取消绑定
	 * @date 2016年6月15日下午7:38:27
	 * @author guoyingjie
	 * @param userName
	 * @param session
	 * @return
	 */
	@RequestMapping("cancelBind")
	public String cancelBind(@RequestParam String openId,@RequestParam String siteId,@RequestParam String userName,HttpSession session){
		session.setAttribute("userName", userName);
		session.setAttribute("openid", openId);
		session.setAttribute("siteId", siteId);
		return "/wechat/cancebind";
	}
	/**
	 * @Description  执行取消绑定命令,取消微信号的绑定
	 * @date 2016年6月15日下午7:53:27
	 * @author guoyingjie
	 * @param userName
	 * @param code
	 * @return
	 */
	@RequestMapping("doCanceBind")
	@ResponseBody
	public String doCanceBind(@RequestParam String userName,@RequestParam(defaultValue="") String code,HttpSession session){
		ExecuteResult rs = new ExecuteResult();
		String sessionCode = (String)session.getAttribute(userName);
		if(!code.equals(sessionCode)){
			rs.setCode(201);
			rs.setMsg("验证码错误");
			return rs.toJsonString();
		}
		//5分钟
		Long oldTime=(Long)session.getAttribute("randCodeTime");
		oldTime=oldTime==null?0:oldTime;
		long newTime=new Date().getTime();
		if((newTime-5*60*1000)<=oldTime){//通过
			session.removeAttribute(userName);
			session.removeAttribute("randCodeTime");
		}else{
			rs.setCode(201);
			rs.setMsg("验证码失效，请重新获取");
			return rs.toJsonString();
		} 
		PortalUser user = userService.getPortalUserByTel(userName);
		WechatUserInfo wi = weChatService.checkOpenid(user.getId());
		if (wi != null) {
			int state = weChatService.deleteWechatUser(wi);
			if (state != 1) {
				rs.setCode(201);
				rs.setMsg("取消绑定失败");
				return rs.toJsonString();
			} else {
				session.setAttribute("proUser",user);
				rs.setCode(200);
				return rs.toJsonString();
			}
		} else {
			rs.setCode(201);
			rs.setMsg("此账号暂未绑定该微信号");
			return rs.toJsonString();
		}
		 
	}
	
	/**
	 * @Description 跳转到个人中心
	 * @date 2016年5月27日下午4:19:44
	 * @author guoyingjie
	 * @param request
	 * @param userName
	 *            手机号
	 * @param siteId
	 * @param session
	 * @return
	 */
	@RequestMapping("goToPerson")
	public String goToPerson(HttpServletRequest request,@RequestParam(defaultValue="") String openId/*,@RequestParam String balance*/,
			@RequestParam String userName, @RequestParam int siteId,HttpSession session) {
		String terminalDevice = request.getHeader("User-Agent");
		String isPc = toLoginUtil.getPcOrMobile(terminalDevice);
		PortalUser proUser = userService.getPortalUserByTel(userName);
		UserBalance userBalance = userService.getUserBalanceByName(userName);
		String banace = "0";
		if(userBalance!=null){
			BigDecimal ban =  userBalance.getBalance();
			banace = ban+"";
		} 
		session.setAttribute("proUser", proUser);
		CloudSite site=myselfPayService.getSite(siteId+"");
		session.setAttribute("site", site);
		session.setAttribute("oepnid", openId);
		session.setAttribute("balance", banace);
		WechatUserInfo info =  weChatService.whetherBindUser(openId);
		if(proUser.getImageUrl()!=null&&!"".equals(proUser.getImageUrl())){
			session.setAttribute("imageUrl", proUser.getImageUrl());
		}else{
			session.setAttribute("weixinurl", info.getHeadimgurl());
		}
		try {
			userService.perfectInfo(userName, siteId);
		}catch (Exception e){
			logger.error(this.getClass().getCanonicalName() + "--goToPerson1",e);
		}
		try {
			userService.getUserMessage(site.getId(), proUser.getId());
		} catch (Exception e) {
			logger.error(this.getClass().getCanonicalName() + "--goToPerson2",e);
		}
		try {
			
			userService.getUserGift(site.getId(), proUser.getId());
		} catch (Exception e) {
			logger.error(this.getClass().getCanonicalName() + "--goToPerson3",e);
		}
		try {
			
			userService.getUserLottery(site.getId(), proUser.getId());
		} catch (Exception e) {
			logger.error(this.getClass().getCanonicalName() + "--goToPerson4",e);
		}
		try {
			userService.getMessageLottery(site.getId(), proUser.getId());
		} catch (Exception e) {
			logger.error(this.getClass().getCanonicalName() + "--goToPerson5",e);
		}
	
		return "/wechat/personal";
	}
	 
	/**
	 * @Description 更换微信绑定到另一个账户
	 * @date 2016年6月16日上午11:06:05
	 * @author guoyingjie
	 * @param code
	 * @param userName
	 * @param session
	 * @return
	 */
	@RequestMapping("doChangeBind")
	@ResponseBody
	public String doChangeBind(@RequestParam String code,@RequestParam String userName,@RequestParam String password,
			@RequestParam String sessionName,HttpSession session){
		 ExecuteResult rs = new ExecuteResult();
		 String sessionCode = (String)session.getAttribute(userName);
    	 if(!code.equals(sessionCode)){
			rs.setCode(201);
			rs.setMsg("验证码错误");
			return rs.toJsonString();
		}
		//5分钟
		Long oldTime=(Long)session.getAttribute("randCodeTime");
		oldTime=oldTime==null?0:oldTime;
		long newTime=new Date().getTime();
		if((newTime-5*60*1000)<=oldTime){//通过
			session.removeAttribute(userName);
			session.removeAttribute("randCodeTime");
		}else{
			rs.setCode(201);
			rs.setMsg("验证码失效，请重新获取");
			return rs.toJsonString();
		} 
		if(sessionName==null||"".equals(sessionName)){
			rs.setCode(201);
			rs.setMsg("您的操作已经过期");
			return rs.toJsonString();
		}
		PortalUser user = userService.getPortalUserByTel(userName);
		if(user==null){
			//直接去注册当前的用户
			int state = weChatService.registerPortalUser(userName,password);
			if(state!=1){
				rs.setCode(201);
				rs.setMsg("更换绑定用户失败");
				return rs.toJsonString();
			}
		}
		//用户不为空检查用户是否正确
		PortalUser proUser=userService.getUserPro(userName, MD5.encode(password.trim()).toLowerCase()); 
		if(proUser==null){
			rs.setCode(201);
			rs.setMsg("用户名或密码错误");
			return rs.toJsonString();
		}			
		session.setAttribute("proUser", proUser);
		
		WechatUserInfo info =  weChatService.checkOpenid(proUser.getId());
		if(info!=null){
			rs.setCode(201);
			rs.setMsg("此账号已经绑定微信号");
			return rs.toJsonString();
		}
		
		int state = weChatService.updateWechatUserInfo(sessionName,proUser.getId());
		if(state!=1){
			rs.setCode(201);
			rs.setMsg("绑定新的微信账户失败");
			return rs.toJsonString();
		}
		
		rs.setCode(200);
		return rs.toJsonString();
	}
	/**
	 * @Description  缴费记录页面
	 * @date 2016年6月23日下午4:26:08
	 * @author guoyingjie
	 * @return
	 */
	@RequestMapping("payRecordForWeixin")
	public String payRecordForWeixin(@RequestParam String openid,@RequestParam String siteId){
		return "/wechat/payInfo";
	}
	
	/**
	 * @Description  获得用户的缴费记录
	 * @date 2016年6月23日下午3:18:17
	 * @author guoyingjie
	 * @param userId
	 * @param site_id
	 * @param request
	 * @param pageIndex
	 * @param pageNum
	 * @return
	 */
	@RequestMapping("/payRecord")
	@ResponseBody
	public String payRecord(@RequestParam(defaultValue = "") String userId,
			@RequestParam(defaultValue = "") String site_id,
			HttpServletRequest request,
			@RequestParam(defaultValue = "0") String pageIndex,
			@RequestParam(defaultValue = "5") String pageNum) {
		ExecuteResult er = new ExecuteResult();
		// 获取缴费记录list
		List<Map<String, Object>> recordList = userService.getPayRecord(userId,site_id, pageIndex, pageNum);
		List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
		// 处理list，将时间（create_time字段）根据当天日期处理成“今天 08:30 ，昨天 13:20， 2016-5-16 18:20” 的形式
		for (Map<String, Object> map : recordList) {
			Date createTime = (Date) map.get("create_time");
			// 创建时间的字符串表示
			String createTimeStr = CalendarUtil.dateToString(createTime);
			// 存储日期
			String dateStr = "";
			// 存储时间
			String timeStr = "";
			// 判断日期
			if (CalendarUtil.isToday(createTimeStr)) {
				// 是今天
				timeStr = createTimeStr.substring("yyyy-MM-dd".length());
				dateStr = "今天";
			} else if (CalendarUtil.isTheDay(createTimeStr, -1)) {
				// 是昨天
				timeStr = createTimeStr.substring("yyyy-MM-dd".length());
				dateStr = "昨天";
			} else {
				timeStr = createTimeStr.substring("yyyy-MM-dd".length());
				dateStr = createTimeStr.substring(0, "yyyy-MM-dd".length());
			}
			Map jsonMap = new HashMap();
			jsonMap.put("timeStr", timeStr);
			jsonMap.put("dateStr", dateStr);
			jsonMap.put("amount", map.get("transaction_amount"));
			jsonMap.put("payName", map.get("pay_name"));
			jsonMap.put("pay_type_name", map.get("pay_type_name"));
			jsonList.add(jsonMap);
		}
		er.setData(jsonList);
		er.setCode(200);
		return er.toJsonString();
	}
	/**
	 * @Description 修改密码
	 * @date 2016年5月26日下午3:13:10
	 * @author guoyingjie
	 * @param userName
	 * @param requeset
	 * @param session
	 * @return
	 */
	@RequestMapping("changePassword")
	public String changePassword(@RequestParam String openId,@RequestParam String userName,@RequestParam int siteId,HttpSession session){
		session.setAttribute("siteId", siteId);
		session.setAttribute("userName", userName);
		session.setAttribute("openid", openId);

		return "/wechat/change";
	}
	/**
	 * @Description  校验原始密码与输入的密码是否一致
	 * @date 2016年5月26日下午3:50:30
	 * @author guoyingjie
	 * @param password
	 * @param username
	 * @param request
	 * @return
	 */
	@RequestMapping("checkUserPs")
	@ResponseBody
	public String checkUserPs(@RequestParam String password,HttpServletRequest request,HttpSession session){
		PortalUser proUser=(PortalUser) session.getAttribute("proUser");
		boolean is = userService.checkUserPsd(proUser, password);
		if(is){
			return "true";
		}else{
			return "false";
		}
	}
	/**
	 * @Description  修改密码
	 * @date 2016年5月26日下午4:21:36
	 * @author guoyingjie
	 * @param password
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("changePsforUser")
	@ResponseBody
	public String changePsforUser(@RequestParam String password,@RequestParam String username,HttpServletRequest request,HttpSession session){
		PortalUser proUser=(PortalUser) session.getAttribute("proUser");
		if(proUser==null){
			proUser=userService.getPortalUserByTel(username);
		}
		boolean is = userService.changePsForUser(proUser,password,null);
		if(is){
			return "true";
		}else{
			return "false";
		}
	}
	/**
	 * 
	 * @Description:获取用户场所信息	
	 * @author songyanbiao
	 * @date 2016年7月14日 下午4:44:00
	 * @param
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("getSiteMessage")
	@ResponseBody
	public String getSiteMessage(@RequestParam String openId,@RequestParam String accessToken,HttpSession session,HttpServletRequest request) throws Exception{
		ExecuteResult er = new ExecuteResult();
		WechatUserInfo userinfo = weChatService.whetherBindUser(openId);
//		weChatService.updateUserWxMessage(userinfo,accessToken);
		session.setAttribute("userinfo", userinfo);
		PortalUser user = userService.getPortalUserById(userinfo.getPortal_user_id());
//			//检测到已经判定了用户直接到用户详情
		session.setAttribute("openid", openId);
		session.setAttribute("proUser", user);
		session.setAttribute("tel", user.getUserName());
		session.setAttribute("userId",user.getId());
		//根据用户请求的外网ip获得用户所在的场所的信息
		List<Map<String, Object>> ls=weChatOtherService.checkUserSite(user.getId());
		if(ls.size()!=0){//如果根据用户名也获取不到用户的归属场所则直接让用户去充值余额
			List<CloudSite>  siteList=weChatService.getSite(ls);
			er.setCode(200);
			er.setData(siteList);
			return er.toJsonString();
		}
		return er.toJsonString();
	}
	/**
	 * 
	 * @Description:下线	
	 * @author songyanbiao
	 * @date 2016年9月21日 下午1:59:09
	 * @param
	 * @return
	 */
	@RequestMapping("getJumpPage")
	public String getJumpPage(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException{
		ExecuteResult er = new ExecuteResult();
		//String terminalDevice = request.getHeader("User-Agent").toLowerCase();
		String wanIp=WanipUtil.getWanIp(request, response);
		List<Map<String, Object>> ls=weChatService.getRouterType(wanIp);
		if(ls.size()==0||(ls.get(0).get("router_type")+"").equals("")||ls.get(0).get("router_type")==null){
			return "/wechat/offLink";
		}else{
			session.setAttribute("siteName",ls.get(0).get("site_name"));
			return "/wechat/offLineSuccess";
		}
	}
	
	/**
	 * 
	 * @Description:个人中心下线	
	 * @author songyanbiao
	 * @date 2016年9月21日 下午1:27:13
	 * @param
	 * @return
	 */
	@RequestMapping("getOff")
	public String getPersonMessage(HttpServletRequest request,HttpSession session,@RequestParam int siteId,HttpServletResponse response){
		ExecuteResult er = new ExecuteResult();
		//String terminalDevice = request.getHeader("User-Agent").toLowerCase();
		CloudSite site=myselfPayService.getSite(siteId+"");
		session.setAttribute("siteName",site.getSite_name());
		return "/wechat/offLineSuccess";
	}
	
	/**
	 * @Description  下线成功跳转的页面
	 * @date 2016年10月28日上午11:32:38
	 * @author guoyingjie
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("getSuccess")
	public String getSuccess(@RequestParam(defaultValue="0") int state ,HttpServletRequest request,HttpSession session){
		//String terminalDevice = request.getHeader("User-Agent").toLowerCase();
		//if(terminalDevice.indexOf("micromessenger")>0){
		if(state==1){
			return "/wechat/activeSuccess";
		}else{
			return "/wechat/offSuccess";
		}
		//} 
	}
	
	/**
	 * 创建完成主动去下线主账号
	 * @Description  一句话描述此方法的功能
	 * @date 2016年10月25日下午1:02:49
	 * @author guoyingjie
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("getOfflineForSon")
	public String getOfflineForSon(HttpServletRequest request,HttpSession session){
		return "/wechat/ziOffLineSuccess";
	}
	
	/**
	 * @Description  获得子账号列表
	 * @date 2016年10月21日下午4:04:55
	 * @author guoyingjie
	 * @param username
	 */
	@RequestMapping("getUserByUsername")
	@ResponseBody
	public String getUserByUsername(@RequestParam String username){
		ExecuteResult er = new ExecuteResult();
		String sonname = weChatService.getUserByUsername(username);
		Map map = new HashMap(2);
		if(sonname!=null){
			int length = sonname.split(",").length;
			map.put("length", length);
			map.put("sonname", sonname);
			er.setCode(200);
			er.setData(map);
		}else{
			er.setCode(201);
		}
		return er.toJsonString();
	}
	/**
	 * 
	 * @Description:跳转推荐码页面	
	 * @author songyanbiao
	 * @date 2016年10月14日 上午11:13:50
	 * @param
	 * @return
	 */
	@RequestMapping("getComdPage")
	public String getComdPage(HttpServletRequest request,HttpServletResponse response,
			String opneId,int portalId,int siteId,HttpSession session){
		
		boolean result=false;
		String terminalDevice = request.getHeader("User-Agent").toLowerCase();
		if(terminalDevice.indexOf("micromessenger")>0){
			String recommend="";
			String res=weChatService.checkRecommend(portalId);
			if(res==null){
				recommend=RandomUtil.generateShortUuid();
				res=weChatService.checkRecommend(recommend);
				if(res!=null){
					while (true) {
						recommend=RandomUtil.generateShortUuid();
						res=weChatService.checkRecommend(recommend);
						if(res==null){
							result=weChatService.insertRecommend(recommend,portalId);
							break;
						}
					}
				}else{
					result=weChatService.insertRecommend(recommend,portalId);
				}
			}else{
				result=true;
				recommend=res;
			}
			if(result){
				String content=PropertiesParam.DeckUrl+"deck/weChatPublicNum/jumpRestiger?opneId="+opneId+"&portId="+portalId+"&recom="+recommend;
				session.setAttribute("recommend", recommend);
				session.setAttribute("opneId", opneId);
				session.setAttribute("userId", portalId);
				session.setAttribute("siteId", siteId);
				session.setAttribute("content", content);
				session.setAttribute("ur", PropertiesParam.DeckUrl);
				return "/wechat/commend";
			}else{
				return "/wechat/recomerror";
			}
		}
		return "/wechat/wxerror";
	}
	/**
	 * 
	 * @Description:生成二维码
	 * @author songyanbiao
	 * @date 2016年10月14日 上午11:01:51
	 * @param
	 * @return
	 */
//	@RequestMapping("getCommend")
//	public void getCommend(String opneId,int portalId,String recom,HttpServletRequest request,HttpServletResponse response,HttpSession session){
//		String terminalDevice = request.getHeader("User-Agent").toLowerCase();
////		if(terminalDevice.indexOf("micromessenger")>0){
////			String content=PropertiesParam.DeckUrl+"deck/weChatPublicNum/jumpRestiger?opneId="+opneId;
//			String content="http://192.168.10.200/deck/weChatPublicNum/jumpRestiger?opneId="+opneId+"&portId="+portalId+"&recom="+recom;
//			EncoderHandlerUtil encoderHandler= new EncoderHandlerUtil();
//			encoderHandler.encoderQRCoder(content, response);
//			session.setAttribute("shareUrl", content);
//			session.setAttribute("ur", "http://192.168.10.200/");
////		}
//	}
	/**
	 * 
	 * @Description:跳转到注册页面	
	 * @author songyanbiao
	 * @date 2016年10月14日 上午11:39:44
	 * @param
	 * @return
	 */
	@RequestMapping("jumpRestiger")
	public String jumpRestiger(HttpServletRequest request,HttpServletResponse response,
			String opneId,int portId,String recom,HttpSession session){
		String terminalDevice = request.getHeader("User-Agent").toLowerCase();
		if(terminalDevice.indexOf("micromessenger")>0){
			WechatUserInfo userinfo = weChatService.hasBindUser(opneId,portId);
			if(userinfo!=null){
				session.setAttribute("recommend", recom);
				session.setAttribute("opneId", opneId);
				session.setAttribute("userId", portId);
			}
			return "/wechat/weChatRegister";
		}
		return "/wechat/wxerror";
	}
	
	/**
	 * @Description 跳转到添加子账号的页面
	 * @date 2016年10月24日上午10:20:57
	 * @author guoyingjie
	 * @param username--主账号
	 * @param siteId
	 * @param session
	 * @return
	 */
	@RequestMapping("goCreateSonPage")
	public String goCreateSonPage(@RequestParam String username,@RequestParam int siteId,@RequestParam(defaultValue="") String openId,HttpSession session){
		String sonname = weChatService.getSonname(username);
		session.setAttribute("sonname",sonname);
		session.setAttribute("siteId", siteId);
		session.setAttribute("username",username);
		session.setAttribute("openId",openId);
		return "/wechat/fount";
	}
	
	/**
	 * @Description 跳转到添加子账号管理的页面
	 * @date 2016年10月24日上午10:20:57
	 * @author guoyingjie
	 * @param username--主账号
	 * @param siteId
	 * @param session
	 * @return
	 */
	@RequestMapping("goManageSonPage")
	public String goManageSonPage(@RequestParam String username,@RequestParam String sonname,@RequestParam int siteId,@RequestParam(defaultValue="") String openId,HttpSession session){
		session.setAttribute("sonname",sonname);
		session.setAttribute("siteId", siteId);
		session.setAttribute("username",username);
		session.setAttribute("openId",openId);
		return "/wechat/manage";
	}
	
	/**
	 * @Description 跳转到划分费用的页面
	 * @date 2016年10月24日上午10:20:57
	 * @author guoyingjie
	 * @param username--主账号
	 * @param siteId
	 * @param session
	 * @return
	 */
	@RequestMapping("goTansferSonPage")
	public String goTansferSonPage(@RequestParam String username,@RequestParam String sonname,@RequestParam int siteId,@RequestParam(defaultValue="") String openId,HttpSession session){
		session.setAttribute("sonname",sonname);
		session.setAttribute("siteId", siteId);
		session.setAttribute("username",username);
		session.setAttribute("openId",openId);
		return "/wechat/transfer";
	}
	
	/**
	 * @Description  创建子账号
	 * @date 2016年10月21日下午4:56:37
	 * @author guoyingjie
	 * @param username
	 */
	@RequestMapping("createSonAccount")
	@ResponseBody
	public String createSonAccount(@RequestParam String username,@RequestParam int siteId,@RequestParam String sonname,@RequestParam String password){
		ExecuteResult re = new ExecuteResult();
		try {
			weChatService.createSonAccount(username, siteId, sonname, password);
			re.setCode(200);
		} catch (Exception e) {
			re.setCode(201);
		}
		return re.toJsonString();
	}
	/**
	 * @Description 删除子账号
	 * @date 2016年10月24日下午1:16:11
	 * @author guoyingjie
	 * @param username
	 * @param siteId
	 * @param sonname
	 * @return
	 */
	@RequestMapping("deleteSonAccount")
	@ResponseBody
	public String deleteSonAccount(@RequestParam String username,@RequestParam int siteId,@RequestParam String sonname){
		ExecuteResult re = new ExecuteResult();
		try {
			weChatService.deleteSonAccount(sonname,username, siteId);
			re.setCode(200);
		} catch (Exception e) {
			re.setCode(201);
		}
		return re.toJsonString();
	}
	
	/**
	 * @Description  获得用户的流量与时间的占比
	 * @date 2016年10月24日下午4:31:38
	 * @author guoyingjie
	 * @param siteId
	 * @param sonname
	 * @return
	 */
	@RequestMapping("getSiteCustomTime")
	@ResponseBody
	public String getSiteCustomTime(@RequestParam int siteId,@RequestParam String sonname){
		ExecuteResult re = new ExecuteResult();
		try {
			Map map = weChatService.getSiteCustomTime(sonname,siteId);
			re.setCode(200);
			re.setData(map);
		} catch (Exception e) {
			re.setCode(201);
		}
		return re.toJsonString();
	}
	
	/**
	 * @Description  检查主账号是否有时间划分
	 * @date 2016年10月24日下午6:19:00
	 * @author guoyingjie
	 * @param siteId
	 * @param dadname
	 * @return
	 */
	@RequestMapping("checkDadAccount")
	@ResponseBody
	public String checkDadAccount(@RequestParam int siteId,@RequestParam String dadname,@RequestParam int state,@RequestParam String timeOrFlow){
		ExecuteResult re = new ExecuteResult();
		try {
			boolean is = weChatService.checkDadAccount(dadname,siteId,state,timeOrFlow);
			if(is){
				re.setCode(200);
			}else{
				re.setCode(201);
			}
		} catch (Exception e) {
			re.setCode(201);
		}
		return re.toJsonString();
	}
	/**
	 * @Description  主账号划拨流量与时间到子账号
	 * @date 2016年10月25日上午10:23:55
	 * @author guoyingjie
	 * @param dadname--主账号
	 * @param sonname--子账号
	 * @param siteId--场所id
	 * @param flowsOrTime--时间或者流量(如果是流量直接传过来的是kb为单位)
	 * @param state--状态(1--划分时间,0--划分流量)
	 */
	@RequestMapping("dadDevideToSonTimeAndFlow")
	@ResponseBody
	public String dadDevideToSonTimeAndFlow(@RequestParam String dadname,@RequestParam String sonname,@RequestParam int siteId,@RequestParam String flowsOrTime,@RequestParam int state){
		ExecuteResult re = new ExecuteResult();
		try {
			 weChatService.dadDevideToSonTimeAndFlow(dadname,sonname,siteId,flowsOrTime,state);
			 re.setCode(200);
		} catch (Exception e) {
			re.setCode(201);
		}
		return re.toJsonString();
	}
	
	/**
	 * @Description  激活子账号
	 * @date 2016年11月10日上午10:39:04
	 * @author guoyingjie
	 * @param dadname
	 * @return
	 */
	@RequestMapping("activeSonState")
	@ResponseBody
	public String activeSonState(@RequestParam String dadname){
		ExecuteResult re = new ExecuteResult();
		try {
			 weChatService.activeSonState(dadname);
			 re.setCode(200);
		} catch (Exception e) {
			re.setCode(201);
		}
		return re.toJsonString();
	}
	
	
	/**
	 * 
	 * @Description:微信推荐码注册	
	 * @author songyanbiao
	 * @date 2016年10月15日 下午1:27:26
	 * @param
	 * @return
	 */
	@RequestMapping("wechatRegister")
	@ResponseBody
	public String wechatRegister(String telephone,String phonecode,String recommend,HttpServletRequest request){
		ExecuteResult ecx = new ExecuteResult();
		String terminalDevice = request.getHeader("User-Agent").toLowerCase();
			if(!("".equals(telephone))&&telephone!=null){
				PortalUser pt=userService.getPortalUserByTel(telephone);
				if(pt!=null){
					ecx.setCode(201);
					ecx.setMsg("用户已经注册,请直接登录");
					return ecx.toJsonString();
				} 
			}
			// 5分钟
			Long oldTime = (Long) request.getSession().getAttribute("randCodeTime");
			oldTime = oldTime == null ? 0 : oldTime;
			long newTime = new Date().getTime();
			if ((newTime - 5 * 60 * 1000) <= oldTime) {
				// 通过,不做操作
			} else {
				ecx.setCode(201);
				ecx.setMsg("验证码失效，请重新获取");
				return ecx.toJsonString();
			}
			 // 获取生成的验证码
			String randCode = (String) request.getSession().getAttribute(telephone);
			// 判断验证码是否正确
			if (!phonecode.equals(randCode)) {
				ecx.setCode(201);
				ecx.setMsg("验证码输入错误");
				return ecx.toJsonString();
			}else{
				//验证码验证通过，在session中移除验证码
				request.getSession().removeAttribute(telephone);
				request.getSession().removeAttribute("randCodeTime");
			}
			UserRecommend urd=userService.getRecommend(recommend);
			if(urd==null){
				ecx.setCode(201);
				ecx.setMsg("推荐码不存在,请输入正确推荐码");
				return ecx.toJsonString();
			}
			boolean result=userService.doWecharRegister(phonecode, telephone, recommend, urd);
			if(result){
				ecx.setCode(200);
				return ecx.toJsonString();
			}else{
				ecx.setCode(202);
				ecx.setMsg("注册失败,请稍后重试");
				return ecx.toJsonString();
			}
	}
	/**
	 * 
	 * @Description:注册结果跳转页面	
	 * @author songyanbiao
	 * @date 2016年10月15日 下午2:54:56
	 * @param
	 * @return
	 */
	@RequestMapping("jumpPage")
	public String jumpPage(int state,HttpServletRequest request){
		String url="";
		String terminalDevice = request.getHeader("User-Agent").toLowerCase();
		if(terminalDevice.indexOf("micromessenger")>0){
			if(state==0){
				url= "/wechat/registererror";
			}else if(state==1){
				url= "/wechat/registersuccess";
			}
		}else{
			url="/wechat/wxerror";
		}
		return url;
	}
	/**
	 * 
	 * @Description:获取分享签名	
	 * @author songyanbiao
	 * @date 2016年10月26日 下午1:33:16
	 * @param
	 * @return
	 */
	@RequestMapping("getSigsure")
	@ResponseBody
	public String getSigsure(HttpSession session,HttpServletRequest request,String strBackUrl) throws Exception{
		ExecuteResult ecx = new ExecuteResult();
		HttpServletRequest httpRequest=(HttpServletRequest)request;
		String ticket=weChatService.getTicket(APPID, APPSECRET);
		Map<String, String> map=Sign.getSign(ticket,strBackUrl,APPID);
		ecx.setData(map);
		return ecx.toJsonString();
	}
	/**
	 * 
	 * @Description:用户签到	
	 * @author songyanbiao
	 * @date 2016年10月26日 下午2:09:46
	 * @param
	 * @return
	 */
	@RequestMapping("doSign")
	public String doSign(@RequestParam int userId,@RequestParam int siteId,HttpSession session,HttpServletRequest request){
		String terminalDevice = request.getHeader("User-Agent").toLowerCase();
		if(terminalDevice.indexOf("micromessenger")>0){
			PortalUser proUser=(PortalUser)session.getAttribute("proUser");
			if(proUser==null){
				proUser=userService.getPortalUserById(userId);
				if(proUser==null){
					return "/wechat/wxerror1";
				}else{
					session.setAttribute("proUser", proUser);
				}
			}
			CloudSite site=(CloudSite)session.getAttribute("site");
			if(site==null){
				site=myselfPayService.getSite(siteId+"");
				if(site==null){
					return "/wechat/wxerror1";
				}else{
					session.setAttribute("site", site);
				}
			}
			Map map=weChatService.doSign(proUser,site);
			if(map.size()==0){
				return "";
			}else{
				session.setAttribute("userSign", (UserSign)map.get("us"));
				return "/wechat/signIndex";
			}
			
		}else{
			return "/wechat/wxerror";
		}
	}
	/**
	 * 
	 * @Description:提示用户是否中奖	
	 * @author songyanbiao
	 * @date 2016年10月27日 下午2:50:20
	 * @param
	 * @return
	 */
	@RequestMapping("checkDraw")
	@ResponseBody
	public String checkDraw(@RequestParam String userName) throws ParseException{
		ExecuteResult exc = new ExecuteResult();
		int i=weChatService.checkOpen();
		if(i==0){
			exc.setCode(201);
		}else{
			i=weChatService.getDrawList(userName);
			switch (i) {
				case 0:exc.setCode(202);break;//未报名抽奖
				case 1:exc.setCode(203);break;//未中奖未提示
				case 2:exc.setCode(204);break;//待审核中奖未提示
				case 3:exc.setCode(205);break;//已中奖未提示 
				case 4:exc.setCode(206);break;//已中奖或未中奖已提示
				case -1:exc.setCode(201);break;//网络故障
			}
		}
		return exc.toJsonString();
	}
	
	/**
	 * 
	 * @Description:去抽奖	
	 * @author songyanbiao
	 * @date 2016年10月26日 下午4:35:21
	 * @param
	 * @return
	 */
	@RequestMapping("goDraw")
	public String goDraw(@RequestParam int usId,HttpSession session,HttpServletRequest request){
		String terminalDevice = request.getHeader("User-Agent").toLowerCase();
		if(terminalDevice.indexOf("micromessenger")>0){
			session.setAttribute("usid", usId);
			return "/wechat/signLottery";
		}else{
			return "/wechat/payMeal";
		}			
	}
	/**
	 * 
	 * @Description:用户抽奖	
	 * @author songyanbiao
	 * @date 2016年10月26日 下午6:31:30
	 * @param
	 * @return
	 */
	@RequestMapping("doDraw")
	@ResponseBody
	public String doDraw(@RequestParam int signid,@RequestParam int grade,HttpSession session,HttpServletRequest request){
		ExecuteResult ecx = new ExecuteResult();
		String terminalDevice = request.getHeader("User-Agent").toLowerCase();
		PortalUser proUser=(PortalUser)session.getAttribute("proUser");
		if(terminalDevice.indexOf("micromessenger")>0){
			int flag=weChatService.checkDraw(signid,grade,session);
			if(flag==1){
				weChatOtherService.toActiveUserMessage(proUser.getId(),"恭喜您,抽奖获得"+grade+"积分,已累计到您的账户积分");
			} 
		    ecx.setCode(flag==1?200:flag==0?201:flag==-2?202:203);
		}else{
			ecx.setCode(204);
		}
		return ecx.toJsonString();
	}
	/**
	 * 
	 * @Description:跳转到中奖名单页面
	 * @author songyanbiao
	 * @date 2016年10月31日 下午4:42:18
	 * @param
	 * @return
	 */
	@RequestMapping("goDrawList")
	public String goDrawList(@RequestParam String userName,HttpSession session){
		session.setAttribute("userName", userName);
		List<Map<String, Object>> ls=weChatService.getList(userName);
		if(ls==null){
			return "";
		}else if(ls.size()==0){
			session.setAttribute("open", 1);
		}else{
			int result=weChatService.getDrawMyself(userName);
			session.setAttribute("state", result);
			session.setAttribute("ls", ls);
			
		}
		return "/wechat/signLotteryList";
	}
	/*签到规则*/
	@RequestMapping("goRuleList")
	public String goRuleList(){
		return "/wechat/ret-rule";
	}
	/**
	 * 
	 * @Description:去补签	
	 * @author songyanbiao
	 * @date 2016年11月1日 上午9:48:12
	 * @param
	 * @return
	 */
	@RequestMapping("retroactive")
	public String retroactive(@RequestParam String userName,@RequestParam String times,@RequestParam int siteId,HttpSession session){
		session.setAttribute("tel", userName);
		session.setAttribute("times", times);
		session.setAttribute("siteId", siteId);
		return "/wechat/retroactive";
	}
	/**
	 * 
	 * @Description:补签冲余额	
	 * @author songyanbiao
	 * @date 2016年11月3日 上午10:59:13
	 * @param
	 * @return
	 */
	@RequestMapping("replePayBalance")
	public String replePayBalance(@RequestParam String userName,@RequestParam String times,@RequestParam int siteId,HttpSession session){
		String openid=(String)session.getAttribute("openid");
		if(openid==null){
			WechatUserInfo info=(WechatUserInfo)session.getAttribute("userinfo");
			if(info==null){
				PortalUser user=userService.getPortalUserByTel(userName);
				info=weChatService.checkOpenid(user.getId());
				session.setAttribute("openid", info.getOpenid());
			}else{
				session.setAttribute("openid", info.getOpenid());
			}
			    
		}
		String tel=(String)session.getAttribute("tel");
		if(tel==null){
			
			session.setAttribute("tel",userName);
		}
		session.setAttribute("siteId", siteId);
		String tim=(String)session.getAttribute("times");
		if(tim==null){
			session.setAttribute("times", times);
		}
		return "/wechat/payreplaBanlace";
	}
	/**
	 * 
	 * @Description:补签充值余额	
	 * @author songyanbiao
	 * @date 2016年11月3日 上午11:54:55
	 * @param
	 * @return
	 */
	@RequestMapping("payRetroactive")
	public String payRetroactive(@RequestParam String amount, @RequestParam String userName,@RequestParam String openid,@RequestParam String times,@RequestParam int siteId,HttpServletRequest request){
		return weChatService.PayBalanceForRepla(amount, userName, openid, APPID, MCHID, times,siteId,request);
	} 
	
	@RequestMapping("payRetroactiveMeal")
	@ResponseBody
	public String payRetroactiveMeal(@RequestParam String userName,@RequestParam int siteId,HttpSession session ){
		return weChatService.getMeal(userName, siteId, session);
		
	}
	/**
	 * 
	 * @Description:补签充值套餐	
	 * @author songyanbiao
	 * @date 2016年11月3日 下午4:54:54
	 * @param
	 * @return
	 */
	@RequestMapping("toPayPage")
	public String toPayPage(@RequestParam String userName,@RequestParam String times,@RequestParam int siteId,HttpSession session){
		session.setAttribute("times",times);
		return weChatService.toPayPage(userName, siteId, times, session);
	}
	

	
	
	/**
	 * 
	 * @Description:检验是否充值成功	
	 * @author songyanbiao
	 * @date 2016年11月3日 上午11:55:22
	 * @param
	 * @return
	 */
	@RequestMapping("getUserStatus")
	public String getUserStatus(@RequestParam String outTradeNo,@RequestParam int siteId,@RequestParam int userId,HttpSession session){
		return weChatService.getPayOtherStatus(session, outTradeNo,siteId,userId);
	}
}
