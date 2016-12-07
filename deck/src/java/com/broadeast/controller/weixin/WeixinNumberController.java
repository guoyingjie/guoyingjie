package com.broadeast.controller.weixin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.broadeast.bean.SitePriceConfigAll;
import com.broadeast.entity.CloudSite;
import com.broadeast.entity.PortalUser;
import com.broadeast.entity.SitePriceConfig;
import com.broadeast.service.impl.SiteService;
import com.broadeast.service.impl.UserService;
import com.broadeast.util.ExecuteResult;
import com.broadeast.util.toLoginUtil;
import com.broadeast.weixin.comment.Configure;
import com.broadeast.weixin.comment.Sha1Util;

/**
 * Copyright (c) All Rights Reserved, 2016.
 * 版权所有                 KDF Information Technology Co .,Ltd
 * @Project		deck
 * @File		WeixinNumberController.java
 * @Date		2016年4月14日 上午11:01:44
 * @Author		gyj
 */
@Controller
@RequestMapping("weixinNumber")
@SuppressWarnings({ "all" })
public class WeixinNumberController {

	private static Logger logger=Logger.getLogger(WeixinNumberController.class);
	
	private static final String APPID = "wxc5fb6a6dabc34dfb";
	
	@Autowired  
	private UserService userService;
	@Autowired  
	private SiteService siteservice;
	
	
	
	/**
	 * @Description  根据用户电话号码获得用户的剩余时间
	 * @param username  用户电话号码
	 * @param request
	 * @param response
	 */
	@RequestMapping("findExpireTime")
	@ResponseBody
	public String findExpireTime(@RequestParam String username,HttpServletRequest request,HttpServletResponse response){
		ExecuteResult er = new ExecuteResult();
		PortalUser portal = userService.ishavePortalUser(username);
		if(portal==null){
			er.setCode(202);
			er.setMsg("用户还未注册,请去计费系统注册");
			return er.toJsonString();
		}
		List<Map<String, Object>>  list = userService.findUserBalance(username);
		 
		if(list.size()>0&&list!=null){
			er.setData(list);
			er.setCode(200);
			return er.toJsonString();
		}else{
			er.setCode(203);
			er.setMsg("您暂无剩余时间信息");
			return er.toJsonString();
		}
	}
	/**
	 * @Description  查询用户下所在几个场所
	 * @param username
	 * @return
	 */
	@RequestMapping("getSiteList")
	@ResponseBody
	public String getSiteList(@RequestParam String username){
		ExecuteResult er = new ExecuteResult();
		List<Map<String,Object>> siteinfos = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = null;
		PortalUser portal = userService.ishavePortalUser(username);
		if(portal==null){
			er.setCode(202);
			er.setMsg("用户还未注册,请去计费系统注册");
			return er.toJsonString();
		}else{
			int portalId = portal.getId();
			List<Map<String, Object>> list = userService.getSiteListInfo(username);
			for (int i = 0; i <list.size(); i++) {
				map = new HashMap<String,Object>();
				map.put("portalId", portalId);
				map.put("siteId",list.get(i).get("siteId"));
				map.put("siteName",list.get(i).get("siteName"));
				siteinfos.add(map);
			}
			er.setCode(200);
			er.setData(siteinfos);
			return er.toJsonString();
		}
	}
	
	/**
	 * @Description  跳转支付页面
	 * @param siteId
	 * @param portalId
	 * @return
	 */
	@RequestMapping("goWeixinPayPage")
	@ResponseBody
	public String goPayPage(@RequestParam int siteId,@RequestParam int portalId ,HttpServletRequest request){
		ExecuteResult er = new ExecuteResult();
		try {
			SitePriceConfigAll siteAll = userService.getSitePriceConfigAll(siteId, portalId);
			er.setCode(200);
			er.setData(siteAll);
			return er.toJsonString();
		} catch (Exception e) {
			er.setCode(201);
			return er.toJsonString();
		}
	}
	/**
	 * @Description 跳转到导航页
	 * @return
	 */
	@RequestMapping("gotoInquiry")
	public String gotoInquiry(){
		return "/mobile/nav";
	}
	 
	/**
	 * @Description 跳转支付页面
	 * @return
	 */
	@RequestMapping("gotopay")
	public String gotopay(){
		return "/mobile/pay";
	}
	
	/**
	 * @Description 跳转查询页面或者是缴费页面
	 * @return
	 */
	@RequestMapping("gotoManySitePay")
	public String gotoManySitePay(){
	     return "/mobile/inquiry";
	}
	
	/**
	 * 用户上网时判断状态
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("getUserStatus")
	public String userStatus(@RequestParam String outTradeNo){
	   boolean isok = userService.checkPayResult(outTradeNo);
	   if(isok){
		   return "/mobile/weixinsuccess";
	   }else{
		   return "/mobile/weixinerror";
	   }
	}
	
	
	/**
	 * @Description 获得微信返回的code
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("getWeixinCode")
	public String getWeixinCode(HttpServletRequest request) throws UnsupportedEncodingException{
		String backUri = Configure.REDIRECT_URL;
		String nums = request.getParameter("nums");
		String amount = request.getParameter("amount");
		String siteId = request.getParameter("siteId");
		String portalId = request.getParameter("portalId");
		String siteConfigId = request.getParameter("siteConfigId");
		String priceNum = request.getParameter("priceNum");
		StringBuilder sb = new StringBuilder();
		sb.append("?nums=").append(nums).append("&amount=").append(amount).append("&siteId=").append(siteId).append("&portalId=").
		append(portalId).append("&siteConfigId=").append(siteConfigId).append("&priceNum=").append(priceNum);
		String params = sb.toString();
		backUri = backUri+params;
		backUri = URLEncoder.encode(backUri,"UTF-8");
		//scope 参数视各自需求而定，这里用scope=snsapi_base 不弹出授权页面直接授权目的只获取统一支付接口的openid
		String url = Configure.GETCODE_URL+"appid="+APPID+"&redirect_uri="+backUri+"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
		//直接跳到授权页面,用户无感知
		 
		return "redirect:"+url;
	}
	
	/**
	 * @Description 检验服务器是否绑定成功
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("checkServerToken")
	public void checkServerToken (HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setDateHeader("expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("pragma", "no-cache");
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		boolean isok = Sha1Util.checkSignature(signature,timestamp,nonce);
        if(isok){
        	response.getWriter().print(echostr);
        }else{
        	response.getWriter().print(false);
        }
	}
}
