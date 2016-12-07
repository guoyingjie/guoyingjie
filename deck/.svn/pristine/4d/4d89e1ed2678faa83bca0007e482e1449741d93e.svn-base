package com.broadeast.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.broadeast.bean.SitePriceConfigAll;
import com.broadeast.entity.CloudSite;
import com.broadeast.entity.PortalUser;
import com.broadeast.entity.SiteCustomerInfo;
import com.broadeast.entity.SitePriceConfig;
import com.broadeast.service.impl.SiteService;
import com.broadeast.service.impl.TrialService;
import com.broadeast.service.impl.UserRealnameAuthImpls;
import com.broadeast.service.impl.UserService;
import com.broadeast.util.CookieUtil;
import com.broadeast.util.DateUtil;
import com.broadeast.util.ExecuteResult;
import com.broadeast.util.MD5;
import com.broadeast.util.OssManage;
import com.broadeast.util.SHA256;
import com.broadeast.util.StringUtil;
import com.broadeast.util.toLoginUtil;

/**
 * 该controller已废弃
 * Copyright (c) All Rights Reserved, 2016.
 * 版权所有                  kdf Information Technology Co .,Ltd
 * @Project		deck
 * @File		ProLoginCntroller.java
 * @Date		2016年7月8日 上午11:43:27
 * @Author		cuimiao
 */
@Controller
public class ProLoginCntroller {
	private static Logger log=Logger.getLogger(ProLoginCntroller.class);
	@Autowired  
	private UserService userService;
	
	@Autowired  
	private SiteService siteservice;
	
	@Autowired
	private TrialService trialService;
	
	@Autowired  
	private UserRealnameAuthImpls userRealnameAuthImpls;
	
	/**
	 * 跳转到登陆页面
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("toLogin")
	public String Login(@RequestParam String id,@RequestParam String mac ,
			@RequestParam String ip, @RequestParam String url,
			@RequestParam(defaultValue = "-1") int fromTrial, Model model,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws IOException {

		model.addAttribute("id", URLDecoder.decode(id, "utf-8"));
		model.addAttribute("mac", URLDecoder.decode(mac, "utf-8"));
		model.addAttribute("ip", ip);
		model.addAttribute("url", url);
		String terminalDevice = request.getHeader("User-Agent");
		// 放入路由的转发参数,给放行时用
		request.getSession().setAttribute("siteMac", id);
		request.getSession().setAttribute("clientMac", mac);
		request.getSession().setAttribute("clientIp", ip);
		request.getSession().setAttribute("fromUrl", url);
		request.getSession().setAttribute("terminalDevice", terminalDevice);

		String isPc = toLoginUtil.getPcOrMobile(terminalDevice);
		CloudSite site = siteservice.getSite(id);
		session.setAttribute("site",site);
	    String bannerUrl = siteservice.getBannerUrl(site);
	    if(null==bannerUrl){
	    	//这里可以给他一个默认的url
	    }else{
	    	session.setAttribute("bannerUrl", bannerUrl);
	    }
		if ("1".equals(isPc)) {
			return "/pc/login"; // 跳转PC登录
		} else {
			return "/mobile/login";// 跳转手机
		}
	}
	/**
	 * 用户登录
	 * @param id
	 * @param mac
	 * @param ip
	 * @param url
	 * @param name
	 * @param pwd
	 * @return
	 * @throws ParseException 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("ProuserLogin")
	@ResponseBody
	public String userLogin(@RequestParam String id,@RequestParam String mac ,
		@RequestParam  String ip,@RequestParam  String url,@RequestParam String name,@RequestParam String pwd,@RequestParam(defaultValue="0") int chargeType,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws ParseException{
		ExecuteResult rs=new ExecuteResult();
		//校验场所是否存在
		CloudSite site=siteservice.getSite(id);
		
		if(site==null){ 
			rs.setCode(201);
			rs.setMsg("找不到此场所！");
			return rs.toJsonString();
		}
		session.setAttribute("site",site);
		//判断用户是否已存在
		PortalUser pt=userService.getPortalUserByTel(name);
		if(pt==null){
			rs.setCode(201);
			rs.setMsg("该用户不存在，请注册");
			return rs.toJsonString();
		}
		//查询用户是否存在
		PortalUser proUser=userService.getUserPro(name, MD5.encode(pwd).toLowerCase());
		if(proUser==null){
			rs.setCode(201);
			rs.setMsg("用户名或密码错误！");
			return rs.toJsonString();
		}else{
			if(proUser.getIsStoped()==1){
				rs.setCode(201);
				rs.setMsg("用户停用请联系管理员！");
				return rs.toJsonString();
			}
		}
		session.setAttribute("proUser",proUser);//添加用户到session
		SiteCustomerInfo scis = userService.isHaveSiteCustomerInfo(proUser.getId(),site.getId());
		//到期时间如果不为空,认证当前的用户是体验过或者交过费的,不在处理试用
		if(scis!=null){
			//判断是否有锁定信息
			String isok = userService.ifUserLuck(scis);
			if(!"null".equals(isok)){
				rs.setCode(203);
				rs.setMsg(isok);
				return rs.toJsonString();
			}else{
				//判断是否超过3次登录
				boolean ok = userService.isSuperThree(proUser.getUserName(), site.getId(), mac,scis);
				if(ok){
					rs.setCode(203);
					return rs.toJsonString();
				}
			}
			int isTry = scis.getIsTry();
			if(isTry==1){
				int tryState = site.getIs_probative();
				if (tryState != 0) {
					Date date = scis.getExpirationTime();
					if(date!=null){
						if(date.getTime()<new Date().getTime()){
							String exTime = DateUtil.datePlus(0, tryState);
							userService.insertExpireDate(exTime, site.getId(), proUser.getId(),scis);
						}else{
							String exDate = DateUtil.datePluss(0, tryState, DateUtil.format(date));
							userService.insertExpireDate(exDate, site.getId(), proUser.getId(),scis);
						}
					}else{
						String exTime = DateUtil.datePlus(0, tryState);
						userService.insertExpireDate(exTime, site.getId(), proUser.getId(),scis);
					}
				}
			}
			
		} else {// 有当前的用户但是还没有缴费,是注册但是没有缴费的用户,在这里处理一下是否给他试用时间
			int tryState = site.getIs_probative();
			if (tryState != 0) {
				try {
					String exTime = DateUtil.datePlus(0, tryState);
					userService.savePaymentinfo(site.getId(), proUser.getId());
					userService.insertExpireDate(exTime, site.getId(), proUser.getId(),scis);
					rs.setCode(200);
					rs.setMsg("登录成功！");
					return rs.toJsonString();
				} catch (Exception e) {
					rs.setCode(202);
					rs.setMsg("登录成功,需要充值!");
					return rs.toJsonString();
				}
			}
		}
		//检查用户帐户状态，3种
		int userConfig=siteservice.UserSiteConfig(site.getId(),proUser.getId());
		if(userConfig!=1){
			rs.setCode(202);
			rs.setMsg("登录成功,需要充值!");
			//为了做数据统计向中间表中插入当前的登录用户的归属场所
			userService.isHavePortalUser(site.getId(),proUser.getId());
			return rs.toJsonString();
		}else{
			//为了做数据统计向中间表中插入当前的登录用户的归属场所
			userService.isHavePortalUser(site.getId(),proUser.getId());
			//用户登陆时检验该账号是否已经处于登陆状态中
			List list=userService.hasLogin(name,site.getId(),mac);
			if(list!=null&&list.size()!=0){
				rs.setCode(300);
				rs.setData(list);
				return rs.toJsonString();
			}else{
				rs.setCode(200);
				rs.setMsg("登录成功！");
				return rs.toJsonString();
			}
		}
	}
	 
	/**
	 * 跳转登陆失败页面
	 */
	@RequestMapping("loginFail")
	public String loginFail(){
		return "pc/loginFail";
	}
	/**
	 * @Description 跳转到支付导航页面
	 * @param session
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("toPay")
	public String toPay(HttpSession session, Model model,
			HttpServletRequest request) {
		String terminalDevice = request.getHeader("User-Agent");
		String isPc = toLoginUtil.getPcOrMobile(terminalDevice);
		PortalUser proUser = (PortalUser) session.getAttribute("proUser");
		CloudSite site = (CloudSite) session.getAttribute("site");
		if (null == proUser || null == site) {
			if ("1".equals(isPc)) {
				return "/pc/newPage";
			} else {
				return "/mobile/newPage";
			}
		}
		SiteCustomerInfo sites = userService.getSiteCustomerInfo( site.getId(),proUser.getId());
		if (sites == null) {
			if ("1".equals(isPc)) {
				return "/pc/expire";
			} else {
				return "/mobile/newjiaofei";
			}
		} else {
			Date date = new Date();
			Date exDate = sites.getExpirationTime();
			String total = sites.getTotalFlow();
			// 当前时间大于到期时间
			if (date.getTime() > exDate.getTime() && (total == null || "0".equals(total))) {
				if ("1".equals(isPc)) {
					return "/pc/expire";
				} else {
					return "/mobile/newjiaofei";
				}
			} else {
				return this.toRealPay(session, request);
			}
		}
	}
	/**
	 * @Description  跳转到缴费页面
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("toRealPay")
	public String toRealPay(HttpSession session ,HttpServletRequest request){
		String terminalDevice = request.getHeader("User-Agent");
		String isPc=toLoginUtil.getPcOrMobile(terminalDevice);
		PortalUser proUser=(PortalUser) session.getAttribute("proUser");
		CloudSite site = (CloudSite)session.getAttribute("site");
		if(null==proUser||null==site){
			if("1".equals(isPc)){
				return  "/pc/newPage";
			}else{
				return  "/mobile/newPage";
			}
		}
		SitePriceConfigAll siteAll = userService.getSitePriceConfigAll(site.getId(), proUser.getId());
		if(siteAll!=null){
			session.setAttribute("siteAll",siteAll);
		}
		if("1".equals(isPc)){
			return "/pc/jiaofei";
		}else{
			return "/mobile/jiaofei";
		}
	}
	 
	
	
	/**
	 * 用户上网时判断状态
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("userStatus")
	@ResponseBody
	public String userStatus(HttpServletRequest request,HttpSession session){
		ExecuteResult rs=new ExecuteResult();
		PortalUser ptu=(PortalUser)request.getSession().getAttribute("proUser");
		SitePriceConfigAll spca=(SitePriceConfigAll)request.getSession().getAttribute("siteAll");
		int userConfig=siteservice.UserSiteConfig(spca.getSiteInof().getId(),ptu.getId());
		if(userConfig!=1){
			rs.setCode(202);
		}else{
			rs.setCode(200);
		}
		return rs.toJsonString();
	}
	/**
	 * 跳转到警告页面
	 * @return
	 */
	@RequestMapping("jinggao")
	public String jinggao(HttpSession session,Model model){
			return "/pc/jinggao";//跳转PC
   }
	/**
	 * 跳转到警告页面
	 * @return
	 */
	@RequestMapping("mobileJinggao")
	public String mobileJinggao(HttpSession session,Model model){
		return "/mobile/jinggao";   // 手机站 
   }
	/**
	 * 跳转到个人页面的修改密码
	 * @return
	 */
	@RequestMapping("mobileChangePwd")
	public String mobileChangePwd(HttpSession session,Model model){
		return "/mobile/changePwd";   
   }
 
	/**
	 * @Description 当点击去下次再说的时跳转的页面并更改用户状态为1
	 * @return
	 */
	@RequestMapping("nextTimeToAuthPage")
	public String goTwoAuthPage(HttpSession session,HttpServletRequest request){
		String terminalDevice = request.getHeader("User-Agent");
		String isPc=toLoginUtil.getPcOrMobile(terminalDevice);
		PortalUser portalUser=(PortalUser) session.getAttribute("proUser");
		CloudSite site = (CloudSite)session.getAttribute("site");
		//更改状态为1--下次直接去验证
		userRealnameAuthImpls.updateAuthState(portalUser,1);
		
		if("1".equals(isPc)){
			//需要提醒页面  请用手机登录完成您的认证
			return "/pc/remindPage";
		} 
		return anywayToSuccess(request,session,site,portalUser);
	}
	/**
	 * @Description 
	 * @return
	 */
	@RequestMapping("isStateOne")
	@ResponseBody
	public String isStateOne(HttpSession session){
		ExecuteResult res = new ExecuteResult();
		PortalUser portalUser=(PortalUser) session.getAttribute("proUser");
	    int state = portalUser.getState();
	    if(state==1){
	    	res.setCode(200);
	    	return res.toJsonString();
	    }else{
	    	res.setCode(201);
	    	return res.toJsonString();
	    }
	}
	/**
	 * @Description <<点击确定>>跳转到成功页面
	 * @return
	 */
	@RequestMapping("goToSuccessPage")
	public String goToSuccessPage(HttpSession session,HttpServletRequest request){
		String terminalDevice=request.getHeader("User-Agent");
		String isPc=toLoginUtil.getPcOrMobile(terminalDevice);
		PortalUser portalUser=(PortalUser) session.getAttribute("proUser");
		CloudSite site = (CloudSite)session.getAttribute("site");
		return anywayToSuccess(request,session,site,portalUser);
	}
	/**
	 * @Description 当点击<去认证>或者<进行验证>时跳转的页面
	 * @return
	 */
	@RequestMapping("goToAuthPage")
	public String goToAuthPage(HttpSession session,HttpServletRequest request){
		String terminalDevice = request.getHeader("User-Agent");
		String isPc=toLoginUtil.getPcOrMobile(terminalDevice);
		PortalUser portalUser=(PortalUser) session.getAttribute("proUser");
		boolean authuser = userRealnameAuthImpls.isAuth(portalUser);
		CloudSite site = (CloudSite)session.getAttribute("site");
		if(authuser){
			return anywayToSuccess(request,session,site,portalUser);
		}
		if("1".equals(isPc)){
			//需要提醒页面  请用手机登录完成您的认证
			return "/pc/remindPage";
		}else{
			return "/mobile/autonymForm";
		}
	}
	 
	/**
	 * @Description  是否开启了认证
	 * @return 0--代表开启认证--true   1--代表关闭认证--false
	 */
	public  boolean ifGoAuth(CloudSite site){
		boolean is = userRealnameAuthImpls.ifGoAuth(site);
		if(is){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * @Description 检查是否有这张身份证号码  
	 * @param idCard
	 * @return --"false"==代表有    "true"==代表没有
	 */
	@RequestMapping("isHaveIdCard")        
	@ResponseBody
    public String isHaveIdCard(@RequestParam String idCard){
	   boolean isHave = userRealnameAuthImpls.isHaveIdCard(idCard);
	   if(isHave){
		   return "false";
	   }else{
		   return "true";
	   }
    }
	/**
	 * @Description  添加用户认证信息
	 * @param siteId 场所id
	 * @param telephone 用户电话号码
	 * @param realName 用户真实姓名
	 * @param idCard 用户身份证号
	 * @param address  用户详细地址
	 * @param base64one --文件流
	 * @param base64Two --文件流
	 * @return
	 */
	@RequestMapping("setUserAuthInfo")
	@ResponseBody
	public String setUserAuthInfo(
			@RequestParam(defaultValue="") String realName,
			@RequestParam(defaultValue="") String idCard,
			@RequestParam(defaultValue="") String address,
			@RequestParam(defaultValue="") String base64one,//身份证
			@RequestParam(defaultValue="") String base64Two,//图片
			HttpServletRequest request,HttpServletResponse response,
			HttpSession session){
		String terminalDevice = request.getHeader("User-Agent");
		ExecuteResult er = new ExecuteResult();
		List<InputStream> list=new ArrayList<InputStream>();
		InputStream baseInputOne = StringUtil.getInputStream(base64one);
		InputStream baseInputTwo = StringUtil.getInputStream(base64Two);
		list.add(baseInputOne);
		list.add(baseInputTwo);
		request.getSession().setAttribute("id", session.getAttribute("siteMac"));
		request.getSession().setAttribute("mac", session.getAttribute("clientMac"));
		request.getSession().setAttribute("ip", session.getAttribute("clientIp"));
		request.getSession().setAttribute("url", session.getAttribute("fromUrl"));
		request.getSession().setAttribute("terminalDevice",terminalDevice);
		boolean flag = true;
		PortalUser portalUser = (PortalUser) session.getAttribute("proUser");
		if (portalUser == null) {
			er.setCode(202);
			er.setMsg("用户不存在");
			return er.toJsonString();
		}
		String mac = (String) session.getAttribute("siteMac");
		// 场所是否存在
		CloudSite site = siteservice.getSite(mac);
		if (site == null) {
			er.setCode(203);
			er.setMsg("此用户没有归属场所");
			return er.toJsonString();
		}
		boolean ishave = userRealnameAuthImpls.isAuth(portalUser);
		if(ishave){
			er.setCode(204);
			er.setMsg("此用户已经处于验证状态或者已经完成验证");
			return er.toJsonString();
		}
		StringBuilder fileUrl = new StringBuilder();
		OssManage oss = new OssManage();
		try {
			if (list.size() > 0 && list != null&&list.get(0)!=null&&list.get(1)!=null) {
				for (int i = 0; i < list.size(); i++) {
					String names = StringUtil.randCode() + ".jpg";
					// 保存数据库你的路径,只保存文件名,在jsp中将文件的路径设置到c标签里
					String path = names;
					fileUrl.append(path).append(",");
					String ifOk = oss.uploadFile(list.get(i), "user_picture/"+ names, "image/jpeg");
					if (ifOk != null) {
						flag = true;
					} else {
						flag = false;
						break;
					}
				}
			} else {
				flag = false;
			}
			if(flag){
				boolean isOk = userRealnameAuthImpls.ifDone(
						site.getId(), portalUser.getUserName(), realName, idCard,
						address, fileUrl.toString(),portalUser);
				if (isOk) {
					er.setCode(200);
					return er.toJsonString();
				} else {
					er.setCode(201);
					er.setMsg("认证信息保存失败");
					return er.toJsonString();
				}
			}else{
				er.setCode(201);
				er.setMsg("认证信息保存失败,请重新认证");
				return er.toJsonString();
			}
		} catch (Exception e) {
			er.setCode(201);
			er.setMsg("认证信息保存失败");
			return er.toJsonString();
		}
	}
	/**
	 * @Description  跳转到验证成功的页面
	 * @return
	 */
	@RequestMapping("/goSuccessAuthPage")
	public String goSuccessAuthPage(HttpSession session,HttpServletRequest request){
		PortalUser proUser=(PortalUser) session.getAttribute("proUser");
		CloudSite site = (CloudSite)session.getAttribute("site");
		return anywayToSuccess(request,session,site,proUser);
	}
	/**
	 * @Description  跳转到验证失败的页面
	 * @return
	 */
	@RequestMapping("/goFailAuthPage")
	public String goFailAuthPage(){
		return "/mobile/autonymFail";
	}
	
	/**
	 * @Description  场所下是否开启认证,如果开启时0--新注册用户第一次可以通过,
	 * 第二次需要认证,,1--直接去认证,,2--完成认证,,3--未通过去认证
	 * 
	 * @param portalUser
	 * @param session
	 * @param isPc
	 * @return
	 */
	@RequestMapping("goToWherePage")
	public String goAnywayPage(HttpSession session,HttpServletRequest request){
		PortalUser portalUser=(PortalUser) session.getAttribute("proUser");
		String terminalDevice=(String)session.getAttribute("terminalDevice");
		String isPc=toLoginUtil.getPcOrMobile(terminalDevice);
		// 0--新注册用户第一次可以通过,第二次需要认证,,1--直接去认证,,2--完成认证,,3--未通过去认证
		String state = String.valueOf(userRealnameAuthImpls.getUserAuthState(portalUser));
		String mac = (String) session.getAttribute("siteMac");
		//场所是否存在
		CloudSite site = siteservice.getSite(mac);
		if(site!=null){
			boolean authuser = userRealnameAuthImpls.isAuth(portalUser);
			if(authuser){
				return anywayToSuccess(request,session,site,portalUser);
			}
		// 判断该场所下是否需要开启认证 true--认证, false--不认证
		boolean ifAuth = ifGoAuth(site);
		if (ifAuth) {
			if ("0".equals(state)) {
				// 需认证但是有两次机会
				if ("1".equals(isPc)) {
					// 需要提醒页面 请用手机登录完成您的认证
					return "/pc/remindPage";
				} else {
					return "/mobile/autonym";
				}
			} else if ("1".equals(state)) {
				// 直接去认证
				if ("1".equals(isPc)) {
					// 需要提醒页面 请用手机登录完成您的认证
					return "/pc/remindPage";
				} else {
					return "/mobile/autonymForm";
				}
			} else if ("2".equals(state)) {
				// 已经完成了验证直接去上网 此状态也是审核中的状态,如果通过了验证此状态不发生改变,如果没有通过更改此状态为3
				return anywayToSuccess(request,session,site,portalUser);
			} else if ("3".equals(state)) {
				// 未完成认证 直接跳转到重新认证
				if ("1".equals(isPc)) {
					return "/pc/remindPage";// 提示页面让用户去手机端进行认证完成后在在操作
				} else {
					return "/mobile/autonymc";
				}
		      }
		   }
		}
		return anywayToSuccess(request,session,site,portalUser);
	}
	
	/**
	 * @Description  在这个页面进行放行操作,并拿到用户的剩余时长与购买总量
	 * @param request
	 * @param session
	 * @return
	 */
	public String anywayToSuccess(HttpServletRequest request,HttpSession session,CloudSite site,PortalUser portalUser){
		String authUrl=siteservice.getAuthUrlOld(session,request);
		session.setAttribute("releaseUrl", authUrl);
		try {
			Map<String, Object> map = userService.getSiteIncomeRecord(site.getId(),portalUser.getId());
			Map<String, Object> map2 = userService.getSYtimeOrflow(site.getId(),portalUser.getId());
			if(map.containsKey("time")&&map2.containsKey("time")){
				session.setAttribute("allTimeAndFlow",map);
				session.setAttribute("SyTimeAndFlow",map2);
				session.setAttribute("bili", userService.getTyleBili(map,map2));
			}
			int mess = userService.getMessageCount(portalUser.getId());
			session.setAttribute("mess", mess);
		} catch (Exception e) {
			 log.error(this.getClass().getCanonicalName()+"==anywayToSuccess");
		}
		
		String terminalDevice = request.getHeader("User-Agent");
		String isPc=toLoginUtil.getPcOrMobile(terminalDevice);
		if("1".equals(isPc)){
			return "/pc/surplus";
		}else{
			return "/mobile/details";
		}
	}
	 
	 /**
	 	 * 跳转到切换账户
	 	 * @param id
	 	 * @param mac
	 	 * @param ip
	 	 * @param url
	 	 * @param fromTrial
	 	 * @param model
	 	 * @param request
	 	 * @param response
	 	 * @param session
	 	 * @return
	 	 * @throws IOException
	 	 */
	 	@RequestMapping("toSwitchAccount")
	 	public String toSwitchAccount(@RequestParam String id,@RequestParam String mac ,@RequestParam  String ip,@RequestParam  String url,
	 			@RequestParam(defaultValue="-1") int fromTrial,Model model,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException{
	 		model.addAttribute("id",id);
	 		model.addAttribute("mac",mac);
	 		model.addAttribute("ip",ip);
	 		model.addAttribute("url",url);
	 		String terminalDevice = request.getHeader("User-Agent");
	 		//放入路由的转发参数,给放行时用
	 		request.getSession().setAttribute("siteMac", id);
	 		request.getSession().setAttribute("clientMac", mac);
	  		request.getSession().setAttribute("clientIp", ip);
	 		request.getSession().setAttribute("fromUrl", url);
	 		request.getSession().setAttribute("terminalDevice", terminalDevice);
	 		String isPc=toLoginUtil.getPcOrMobile(terminalDevice);
 			if("1".equals(isPc)){ //测试手机站 
 				return "/pc/login";  //跳转PC 
 			}else{
 				return "redirect:/toLogin";//跳转手机 
 			}
	 	}
	
}