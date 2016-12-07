package com.broadeast.controller;

import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.broadeast.entity.PortalUser;
import com.broadeast.service.impl.SiteService;
import com.broadeast.service.impl.UserService;
import com.broadeast.util.InitContext;
import com.broadeast.util.MD5;
import com.broadeast.util.MsgUtil;
import com.broadeast.util.SHA256;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.internal.parser.json.SimplifyJsonConverter;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * Copyright (c) All Rights Reserved, 2016. 版权所有 kdf Information Technology
 * Co.,Ltd
 * 
 * @Project newCloud
 * @File SendMsgController.java
 * @Date 2016年3月29日 下午3:01:50
 * @Author gyj
 */
@Controller
@RequestMapping("/TelCodeManage")
public class SendMsgController {
	private static Logger logger = Logger.getLogger(SendMsgController.class);
	@Resource
	private UserService userService;
	@Autowired
	private SiteService siteservice;
	
	/**
	 * @Description 发送验证码调用接口
	 * @param telephone --- 被发送人的手机号
	 * @param templateCode ---模板短信id,这个是自定义的短信模板
	 * @param session
	 * @return -1是发送失败,0是发送成功,-2是一分钟内同一个手机号多次发送
	 */
	@RequestMapping("sendTelCode")
	@ResponseBody
	public String getSendMsgRandCode(@RequestParam String tel,@RequestParam String templateCode,HttpSession session) {
		try {
			String code = MsgUtil.randCode();
			String str = MsgUtil.paramer1SendCode(tel.replace("a","").replace("b",""), templateCode, code);
			if (!"error".equals(str)) {
				boolean flag = MsgUtil.sendMsgIsSuccessful(str);
				if (flag) {
					session.setAttribute(tel, code);
					session.setAttribute("randCodeTime",new Date().getTime());
					return "0";
				} else {
					String errStr = MsgUtil.backErrorMsg(str);
					if ("触发业务流控".equals(errStr)) {
						return "-2";
					} else {
						return "-1";
					}
				}
			} else {
				return "-1";
			} 
		} catch (Exception e) {
			logger.error("发送验证码失败", e);
			return "-1";
		}
	}
 
	/**
	 * @Description 发送验证码调用接口
	 * @param telephone --- 被发送人的手机号
	 * @param templateCode ---模板短信id,这个是自定义的短信模板
	 * @param session
	 * @return -1是发送失败,0是发送成功,-2是一分钟内同一个手机号多次发送
	 */
	@RequestMapping("registerMsgRandCode")
	@ResponseBody
	public String registerMsgRandCode(@RequestParam String tel,@RequestParam String templateCode,HttpSession session) {
		try {
			String code = MsgUtil.rand3Code();
			String str = MsgUtil.registerSendCode(tel, templateCode, code);
			if (!"error".equals(str)) {
				boolean flag = MsgUtil.sendMsgIsSuccessful(str);
				if (flag) {
					session.setAttribute(tel, code);
					session.setAttribute("randCodeTime",new Date().getTime());
					return "0";
				} else {
					String errStr = MsgUtil.backErrorMsg(str);
					if ("触发业务流控".equals(errStr)) {
						return "-2";
					} else {
						return "-1";
					}
				}
			} else {
				return "-1";
			} 
		} catch (Exception e) {
			logger.error("发送验证码失败", e);
			return "-1";
		}
	}
	
	/**
	 * @Description 发送验证码调用接口
	 * @param telephone --- 被发送人的手机号
	 * @param templateCode ---模板短信id,这个是自定义的短信模板
	 * @param session
	 * @return -1是发送失败,0是发送成功,-2是一分钟内同一个手机号多次发送
	 */
    @RequestMapping("pwdOrRegester")
	@ResponseBody
	public String pwdOrRegester(@RequestParam String tel,@RequestParam String templateCode,@RequestParam  int siteId,HttpSession session) {
		String code =  MsgUtil.rand3Code();
		String newPw = SHA256.getUserPassword(tel, MD5.encode(code).toLowerCase());
		PortalUser user = userService.getPortalUserByTel(tel);
		boolean isok = false;
		if(user==null){
			PortalUser users = new PortalUser();
			users.setUserName(tel);
			users.setPassWord(newPw);
			isok = userService.userRegists(siteId,users);
			if(siteId!=-2){
				userService.rSaveTryTime(siteservice.getSiteById(siteId),users);
			}
		}
		if(!isok){
			return "-1";
		}
		try {
			String str = MsgUtil.paramer2SendCode(tel, templateCode, code);
			if (!"error".equals(str)) {
				boolean flag = MsgUtil.sendMsgIsSuccessful(str);
				if (flag) {
					session.setAttribute(tel, code);
					session.setAttribute("randCodeTime",new Date().getTime());
					return "0";
				} else {
					String errStr = MsgUtil.backErrorMsg(str);
					if ("触发业务流控".equals(errStr)) {
						return "-2";
					} else {
						return "-1";
					}
				}
			} else {
				return "-1";
			} 
		} catch (Exception e) {
			logger.error("发送验证码失败", e);
			return "-1";
		}
	}
}
