package com.broadeast.controller.weixin;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

import com.broadeast.bean.SitePriceConfigAll;
import com.broadeast.controller.PortalUserController;
import com.broadeast.entity.CloudSite;
import com.broadeast.entity.PortalUser;
import com.broadeast.entity.UserBalance;
import com.broadeast.entity.WechatUserInfo;
import com.broadeast.service.impl.MyselfPayService;
import com.broadeast.service.impl.SitePaymentRecordsService;
import com.broadeast.service.impl.SiteService;
import com.broadeast.service.impl.UserService;
import com.broadeast.service.impl.WeChatOtherService;
import com.broadeast.service.impl.WeChatService;
import com.broadeast.util.ExecuteResult;
import com.broadeast.util.MD5;
import com.broadeast.util.PropertiesParam;
import com.broadeast.util.SHA256;
import com.broadeast.weixin.comment.HttpService;
import com.broadeast.weixin.comment.JsonUtil;
import com.broadeast.weixin.comment.WxPayApi;
import com.sun.xml.internal.fastinfoset.sax.Properties;

/**
 * 给自己充值缴费相关的controller
 * Copyright (c) All Rights Reserved, 2016.
 * 版权所有                  kdf Information Technology Co .,Ltd
 * @Project		deck
 * @File		MyselfPayController.java
 * @Date		2016年6月18日 上午11:40:58
 * @Author		cuimiao
 */
@Controller
@RequestMapping("myselfPay")
@SuppressWarnings("all")
public class MyselfPayController {
	
	private static final String APPID = "wxc5fb6a6dabc34dfb";
	private static final String APPSECRET = "ceafa600a2d9b2a98d36885081d16058";
	private static Logger logger = Logger.getLogger(MyselfPayController.class);
	
	@Resource(name="myselfPayService")
	private MyselfPayService myselfPayService;
	
	@Resource(name="weChatOtherService")
	private WeChatOtherService weChatOtherService;
	
	@Resource(name="weChatService")
	private WeChatService weChatService;
	
	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name="sitePaymentRecordsService")
	private SitePaymentRecordsService sitePaymentRecordsService;
	
	/**
	 * 
	 * @Description: 授权微信公众号拿到用户的微信信息
	 * @return
	 * @Date		2016年6月18日 上午11:43:23
	 * @Author		cuimiao
	 */
	@RequestMapping("authorizeToUser")
	public String authorizeToUser(){
		String redirectUrl =PropertiesParam.DeckUrl+"deck/myselfPay/getWeChatUserInfo";//回调url
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
	public String getWeChatUserInfo(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		//获取code，用来获取openid
		String code = (request.getParameter("code")+"").trim();
		try {
			//获取openid和access_token
     		Map<String, Object> map = WxPayApi.getOpenidAndAccessToken(APPID, APPSECRET, code);
     		if(map!=null){
     			String openid = map.get("openid")==null?null:map.get("openid").toString();
     			String accessToken = map.get("access_token")==null?null:map.get("access_token").toString();
     			session.setAttribute("openid",openid);
     			session.setAttribute("accessToken", accessToken);
     			//检测关注公众号用户是否跟wifi用户绑定，若绑定返回绑定信息实体对象
     			WechatUserInfo userinfo = weChatService.whetherBindUser(openid);
     			if(userinfo==null){//用户没有绑定,
     				//跳转至绑定的页面
     				session.setAttribute("url", "/myselfPay/bindUser");
     				return "/wechat/binduser";
     			}else{
     				session.setAttribute("userinfo", userinfo);
     				PortalUser user=userService.getPortalUserById(userinfo.getPortal_user_id());
     				session.setAttribute("proUser", user);
     				session.setAttribute("tel", user.getUserName());
     				return this.payPage(user,openid ,request, session,response);
     				
    			}
     		}
		} catch (Exception e) {
			 logger.error("获得openid失败",e);
		}
		return  "/wechat/prompt";
	}
	/**
	 * 
	 *	@Description:如果微信openid和该用户账号已经绑定
	 *  @author songyanbiao
	 *	@Date 2016年6月25日 
	 *	@param request
	 *	@param session
	 *	@return
	 */
	public String payPage(PortalUser user,String openId,HttpServletRequest request,HttpSession session,HttpServletResponse response){
		return myselfPayService.jumpPage(user,openId, request, session, response);
	}
	/**
	 * 绑定页面  触发该方法
	 * @Description: 用户注册或者绑定openid跳转页面
	 * @param userName
	 * @param accessToken
	 * @param openid
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 * @Date		2016年6月21日 下午2:31:14
	 * @Author		cuimiao
	 */
	@RequestMapping("bindUser")
	@ResponseBody
	public String bindUser(@RequestParam String userName ,@RequestParam String password,@RequestParam(defaultValue="") String accessToken ,@RequestParam(defaultValue="") String openid,
			@RequestParam(defaultValue="") String code,HttpServletRequest request,HttpSession session) throws Exception{
		String url="/myselfPay/userOfSite";
		return weChatService.bindUserOpenid(url,openid, accessToken, password, userName, code, session);
	}
	/**
	 * 
	 *	@Description:用户绑定或注册成功跳转页面
	 *  @author songyanbiao
	 *	@Date 2016年6月25日 
	 *	@param openid
	 *	@param request
	 *	@param session
	 *	@return
	 */
	@RequestMapping("userOfSite")
	public String userOfSite(@RequestParam String openId,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		WechatUserInfo userinfo = weChatService.whetherBindUser(openId);
		PortalUser	user=userService.getPortalUserById(userinfo.getPortal_user_id());
		return payPage(user, openId,request, session, response);
	}
	/**
	 * 
	 *	@Description:去支付
	 *  @author songyanbiao
	 *	@Date 2016年6月25日 
	 *	@param openid
	 *	@param request
	 *	@param session
	 *	@return
	 * @throws ParserConfigurationException 
	 * @throws SAXException 
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	@RequestMapping("toPay")
	public String toPay(@RequestParam String openid,HttpServletRequest request,HttpSession session,HttpServletResponse response) throws NumberFormatException, IOException, SAXException, ParserConfigurationException{
		String sucUrl="/wechat/payMyself";
		session.setAttribute("openid",openid);
		String siteId=request.getParameter("siteId");
		session.setAttribute("siteId",siteId);
		return sucUrl;
		
	}
	/**
	 * 
	 *	@Description:个自己充值余额跳转
	 *  @author songyanbiao
	 *	@Date 2016年6月27日
	 */
	@RequestMapping("toPayBalance")
	public String toPayBalance(@RequestParam String openid,@RequestParam String userName,HttpServletRequest request,HttpSession session){
		if(openid==null||openid==""){
			logger.error("openid丢失");
			return "/wechat/weixinerror";
		}
		session.setAttribute("openid",openid);
		session.setAttribute("tel", userName);
		return "/wechat/payForMyself";
	}
	
	/**
	 * 
	 *	@Description:给自己充值余额
	 *  @author songyanbiao
	 *	@Date 2016年6月25日 
	 *	@param request
	 *	@param response
	 *	@param session
	 *	@return
	 */
	@RequestMapping("payMyself")
	@ResponseBody
	public String payMyself(@RequestParam String openid,@RequestParam String siteId ,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		ExecuteResult ex= new ExecuteResult();
		Map map= new HashMap<>();
		CloudSite site=myselfPayService.getSite(siteId);
		WechatUserInfo userinfo = weChatService.whetherBindUser(openid);
		PortalUser	portal=userService.getPortalUserById(userinfo.getPortal_user_id());
		
		//检测用户账户下是否有余额
		//UserBalance ub=weChatOtherService.getUserMoney(openid);
		UserBalance ub=weChatOtherService.getUserBanlance(portal.getUserName());
		if(ub!=null){
			//session.setAttribute("sum", ub.getBalance());
			//map.put("sum", ub.getBalance());
			ex.setMsg( ub.getBalance()+"");
		}else{
			//session.setAttribute("sum", new BigDecimal("0.00"));
			//map.put("sum","0.00");
			ex.setMsg("0.00");
		}
		session.setAttribute("tel", portal.getUserName());
		SitePriceConfigAll siteAll=myselfPayService.getSiteRule(site.getId(), portal.getId());
		if(siteAll!=null){
			//map.put(site, siteAll);
			//session.setAttribute("siteAll", map); 
			ex.setData(siteAll);
			ex.setCode(200);
		}else{
			ex.setCode(201);
		}
		return ex.toJsonString();
	}
//	/**
//	 * 
//	 *	@Description:给自己充值
//	 *  @author songyanbiao
//	 *	@Date 2016年6月25日 
//	 *	@param request
//	 *	@param response
//	 *	@param session
//	 *	@return
//	 */
//	@RequestMapping("payMyselfSite")
//	public String payMyselfSite(@RequestParam String opeind,HttpServletRequest request,HttpServletResponse response,HttpSession session){
//		return weChatOtherService.weiXinPayOtherAccount(opeind,request, response, session);
//	}
}
