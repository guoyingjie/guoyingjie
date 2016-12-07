package com.broadeast.controller.wifidog;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
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
import org.springframework.web.servlet.ModelAndView;

import com.broadeast.bean.SitePriceConfigAll;
import com.broadeast.entity.CloudSite;
import com.broadeast.entity.CloudSiteRouters;
import com.broadeast.entity.Coovachilli;
import com.broadeast.entity.PortalLog;
import com.broadeast.entity.PortalUser;
import com.broadeast.entity.SiteCustomerInfo;
import com.broadeast.service.impl.IkuaiAuthloginservice;
import com.broadeast.service.impl.PortalLogService;
import com.broadeast.service.impl.SitePaymentRecordsService;
import com.broadeast.service.impl.SiteService;
import com.broadeast.service.impl.TrialService;
import com.broadeast.service.impl.UserRealnameAuthImpls;
import com.broadeast.service.impl.UserService;
import com.broadeast.service.impl.WeChatService;
import com.broadeast.util.CalendarUtil;
import com.broadeast.util.DateUtil;
import com.broadeast.util.ExecuteResult;
import com.broadeast.util.MD5;
import com.broadeast.util.OssSchoolManage;
import com.broadeast.util.StringUtil;
import com.broadeast.util.toLoginUtil;
import com.broadeast.weixin.comment.RandomStringGenerator;
import com.jd.jr.pay.gate.signature.util.BASE64;
import com.jd.jr.pay.gate.signature.util.ThreeDesUtil;
import com.wap.demo.constant.MerchantConstant;

@Controller
@RequestMapping("w")
@SuppressWarnings("all")
public class LoginController {
	private static Logger log = Logger.getLogger(LoginController.class);
	@Autowired
	private UserService userService;

	@Autowired
	private SiteService siteservice;

	@Autowired
	private TrialService trialService;

	@Autowired
	private UserRealnameAuthImpls userRealnameAuthImpls;

	@Autowired
	private SitePaymentRecordsService sitePaymentRecordsService;
	
	@Autowired
	private PortalLogService portalservice;

	@Resource(name="weChatService")
	private WeChatService weChatService;
	
	@Resource
	private MerchantConstant merchantConstant;
	/**
	 * 跳转到登陆页面
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("login")
	public String Login(@RequestParam(defaultValue="")  String gw_id,
			@RequestParam(defaultValue = "192.168.10.6") String ip,
			@RequestParam(defaultValue="")  String mac, @RequestParam(defaultValue="")  String gw_address,
			@RequestParam(defaultValue="")  String gw_port,
			@RequestParam(defaultValue = "jk") String gw_mac,
			@RequestParam(defaultValue="")  String url,
			@RequestParam(defaultValue = "-1") int fromTrial, Model model,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws IOException {
		String terminalDevice = request.getHeader("User-Agent");
		String isPc = toLoginUtil.getPcOrMobile(terminalDevice);
		if("1".equals(isPc)){
			return "/pc/jump";
		}else{
			return "/mobile/jumps";
		}

	}

	// 防止app攻击的缓冲
	@RequestMapping("r")
	public ModelAndView redirectLogin(@RequestParam(defaultValue="") String gw_id,
			@RequestParam(defaultValue = "192.168.10.6") String ip,
			@RequestParam(defaultValue = "jk") String gw_mac,
			@RequestParam(defaultValue="") String mac, @RequestParam(defaultValue="") String gw_address,
			@RequestParam(defaultValue="") String gw_port, @RequestParam (defaultValue="")String url,
			@RequestParam(defaultValue = "-1") int fromTrial, Model model,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws IOException, ParseException {
		
		ModelAndView mav = new ModelAndView();
		
		String res=request.getParameter("res");
		String ikenc = request.getParameter("ikenc");
		String ros = request.getParameter("ros");
		request.getSession().setAttribute("chillilreturn",null);
		
		String id="";
		String terminalDevice = request.getHeader("User-Agent");
		request.getSession().setAttribute("terminalDevice", terminalDevice);
		String isPc = toLoginUtil.getPcOrMobile(terminalDevice);
		String portallog = "";
		String password = "";//默认的情况下密码是空字符串,当检测当前的mac有对应的数据的时候将对应的数据添赋值到这变量上边
		if (ikenc!=null&&ikenc.length()>1&&ikenc.split("-").length==1){//说明是小辣椒,获得mac是加密的东西,需要解析出来,
			try {
				String ikuaimac = RoutersPortal.returnMac(ikenc);
				PortalUser  user = userService.getUserByMac(ikuaimac);
				request.getSession().setAttribute("clientmac", ikuaimac);
				if(user!=null){
					session.setAttribute("proUser",user);
					session.setAttribute("have","have");
					password=user.getPassWord();
				}
			} catch (Exception e) {
				 log.error("获得ikuaimac失败!");
			}
		}else{
			if(ikenc!=null&&ikenc.length()>1){
				//待处理
			}else{
				PortalUser user = userService.getUserByMac(mac);
				request.getSession().setAttribute("clientmac", mac);
				if(user!=null){
					session.setAttribute("proUser",user);
					session.setAttribute("have","have");
				}
			}
			
		}
		
		
		if("1".equals(isPc)){// 跳转PC登录
			//portallog= "/pc/newlogin";
			portallog= "/pc/pclogin";
		}else{// 跳转手机登录
			portallog= "/mobile/newlogin";
			//portallog= "/mobile/login";
			
		}
		mav.setViewName(portallog);
		CloudSite site =null;
		
		
		if (!"jk".equals(gw_mac)) {// gw_mac不等于jk时代表是wifidog标准版，反之代表的事集客系统
			id = gw_mac.replace(":", "");
		}else if (res!=null&&res.length()>1){//登陆coovachilli
			RoutersPortal routersPortal = new RoutersPortal();
		
			//判断是否登录过
			String flag = (String)session.getAttribute("have");
			
			
			Coovachilli coova = routersPortal.chilli_portal(userService, request,flag);
			String portalvaule = coova.getPortalValue();
			if("notyet".equals(portalvaule)){//暂时处理这个状态
				site= portalservice.getsite((String)session.getAttribute("nasid"));
				String bannerUrl = siteservice.getBannerUrls(site);
				if(null!=bannerUrl){
					session.setAttribute("bannerUrl",bannerUrl);
				}
				session.setAttribute("site", site);
				PortalLog portal_log = new PortalLog();
				portal_log.setClientIp(request.getParameter("ip"));
				portal_log.setClientMac(request.getParameter("mac"));
				portal_log.setNasid(request.getParameter("nasid"));
				portal_log.setRequestTime(CalendarUtil.currentTime());
				portal_log.setRouterIp(request.getParameter("uamip"));
				portal_log.setRouterMac(request.getParameter("called"));
				portal_log.setRouterType("coovachilli");
				portal_log.setTerminalDevice(terminalDevice);
				portal_log.setUrl(request.getParameter("userurl"));
				portalservice.insertPortalog(portal_log);
			}else if("success".equals(portalvaule)){
				portalservice.updateTerminal(terminalDevice, request.getParameter("mac"), request.getParameter("ip"), (String)session.getAttribute("UserName"));
				anywayToSuccess(request, session,(CloudSite)session.getAttribute("site"), coova.getPortalUser());
				String username = (String)session.getAttribute("UserName");
				if(username.indexOf("a")>-1||username.indexOf("b")>-1){
					 return this.successCenter(session, request);
				}else{
					if ("1".equals(isPc)) {
						mav.setViewName("/pc/surplus");
						return mav;
					} else {
						mav.setViewName("/mobile/details");
						return mav;
					}
				}
			}else if("failed".equals(portalvaule)){
				site= portalservice.getsite((String)session.getAttribute("nasid"));
				String bannerUrl = siteservice.getBannerUrls(site);
				if(null!=bannerUrl)
					session.setAttribute("bannerUrl", bannerUrl);
					session.setAttribute("site", site);
					mav.setViewName(portallog);
				 
				request.getSession().setAttribute("chillilreturn",RoutersPortal.getCode(request.getParameter("reply"),request,userService,portalservice));
				return mav;
			}else if("logoff".equals(portalvaule)){
				site= portalservice.getsite((String)session.getAttribute("nasid"));
				String bannerUrl = siteservice.getBannerUrls(site);
				if(null!=bannerUrl)
				session.setAttribute("bannerUrl",bannerUrl);
				session.setAttribute("site", site);
			}
			return mav;
		}else if (ikenc!=null&&ikenc.length()>1){//登陆ikuai
			String[] status =ikenc.split("-");
			if(status.length==1){
				PortalLog portal_log = new PortalLog();
				RoutersPortal routersPortal = new RoutersPortal();
				portal_log=routersPortal.ikuai_login(request,response,portal_log);
				portal_log.setTerminalDevice(terminalDevice);
				portalservice.insertPortalog(portal_log);
				site = portalservice.getsite((String)session.getAttribute("nasid"));
				String bannerUrl = siteservice.getBannerUrls(site);
				if(null!=bannerUrl)
				session.setAttribute("bannerUrl", bannerUrl);
				session.setAttribute("site", site); 
				mav.setViewName(portallog);
			    return mav;
			}else if(status.length>1&&status[1].equals("true")){
				session.setAttribute("loginsuccess", true);
				portalservice.updateTerminal(terminalDevice,(String)session.getAttribute("clientMac"),(String)session.getAttribute("clientIp"), (String)session.getAttribute("UserName"));
				PortalUser proUser = null;
				//这个是ikuai成功后返回的数据.password需要没有传进来
				String flag = (String)session.getAttribute("have");
				if("have".equals(flag)){
					proUser = userService.getUserByEncryptPs((String)session.getAttribute("UserName"), (String)session.getAttribute("Password"));
				}else{
					proUser = userService.getUserPro((String)session.getAttribute("UserName"), MD5.encode((String)session.getAttribute("Password")) .toLowerCase());
				}
				
				session.setAttribute("proUser", proUser);
				Object ob = session.getAttribute("site");
				anywayToSuccess(request, session,(CloudSite)session.getAttribute("site"),proUser);
				if(proUser!=null){
					String username = proUser.getUserName();
					if(username.indexOf("a")>-1||username.indexOf("b")>-1){
						 return this.successCenter(session, request);
					}
				}
				if ("1".equals(isPc)) {
					mav.setViewName("/pc/surplus");
					return mav;
				} else {
					mav.setViewName("/mobile/details");
					return mav;
				}
			}else if(status.length>1&&status[1].equals("false")){
				session.setAttribute("loginsuccess", false);
				site=(CloudSite)session.getAttribute("site");
				String bannerUrl = siteservice.getBannerUrls(site);
				if (null == bannerUrl) {
					// 这里可以给他一个默认的url
				} else {
					session.setAttribute("bannerUrl",  bannerUrl);
				}
				mav.setViewName(portallog);
				String flag = (String)session.getAttribute("have");	
				 
				String jsonCode = checkUser((String)session.getAttribute("UserName"), (String)session.getAttribute("clientMac"),(String)session.getAttribute("Password"),flag,session,request,response);
				//mav.addObject("chillilreturn",jsonCode);
				if(jsonCode.contains("登录成功！")){
					List values = portalservice.getLoginUser((String)session.getAttribute("UserName"),((CloudSite)session.getAttribute("site")).getId(),(String)session.getAttribute("clientMac"));
					if (values != null && values.size()>0) {
						Map<String,String> uport = new HashMap<>();
						ExecuteResult rs = new ExecuteResult();
						rs.setCode(303);
						rs.setData(values);
						request.getSession().setAttribute("chillilreturn",rs.toJsonString() );
						return mav;
					} 
				}
                mav.setViewName(portallog);
                request.getSession().setAttribute("chillilreturn",RoutersPortal.getCode(request.getParameter("reply"),request,userService,portalservice) );
				return mav;
			}
			return mav;
		}else if(ros!=null&&ros.length()>2){
			RoutersPortal rp = new RoutersPortal();
			String rosstatus = rp.rosportal(userService,request);
			if("notyet".equals(rosstatus)){
				site = portalservice.getsite((String) session.getAttribute("nasid"));
				String bannerUrl = siteservice.getBannerUrls(site);
				if (null != bannerUrl)
					session.setAttribute("bannerUrl",  bannerUrl);
				session.setAttribute("site", site);
				PortalLog portal_log = new PortalLog();
				portal_log.setClientIp(request.getParameter("ip"));
				portal_log.setClientMac(request.getParameter("mac"));
				portal_log.setNasid(request.getParameter("nasid"));
				portal_log.setRequestTime(CalendarUtil.currentTime());
				portal_log.setRouterIp(request.getParameter("uamip"));
				portal_log.setRouterMac(request.getParameter("called"));
				portal_log.setRouterType("ros");
				portal_log.setTerminalDevice(terminalDevice);
				portal_log.setUrl(request.getParameter("userurl"));
				portalservice.insertPortalog(portal_log);
			}else if("success".equals(rosstatus)){
				portalservice.updateTerminal(terminalDevice,(String)session.getAttribute("clientMac"), request.getParameter("ip"), (String)session.getAttribute("UserName"));
				anywayToSuccess(request, session,(CloudSite)session.getAttribute("site"),(PortalUser)session.getAttribute("proUser"));
				PortalUser proUser = (PortalUser)session.getAttribute("proUser");
				if(proUser!=null){
					String username = proUser.getUserName();
					if(username.indexOf("a")>-1||username.indexOf("b")>-1){
						 return this.successCenter(session, request);
					}
				}
				if ("1".equals(isPc)) {
					mav.setViewName("/pc/surplus");
					return mav;
				} else {
					mav.setViewName("/mobile/details");
					return mav;
				}
			}else if("err".equals(rosstatus)){
				mav.setViewName(portallog);
				request.getSession().setAttribute("chillilreturn",RoutersPortal.getCode(request.getParameter("error"),request,userService,portalservice));
				return mav;
			}
			return mav;
		} else {
			id = gw_id.replace(":", "");
		}
		//获取sessionId
		String sessionId= RandomStringGenerator.getRandomStringByLength(32);
		// 用于区分wifidog和原系统访问路径
		request.getSession().setAttribute("fromFw", "wifidog");
		// 放入路由的转发参数,给放行时用
		request.getSession().setAttribute("siteMac", id);
		request.getSession().setAttribute("nasid", id);
		request.getSession().setAttribute("clientMac", mac);
		request.getSession().setAttribute("clientIp", ip);
		request.getSession().setAttribute("fromUrl", url);
		request.getSession().setAttribute("gw_address", gw_address);
		request.getSession().setAttribute("gw_port", gw_port);
		request.getSession().setAttribute("sessionId", sessionId);
		request.getSession().setAttribute("nasid", id);
		site = siteservice.getSite(id);
		if(site!=null){
			String token = String.valueOf(site.getStauts())+System.currentTimeMillis() + "";
			request.getSession().setAttribute("token", token);
		}
		String bannerUrl = siteservice.getBannerUrls(site);
		if (null == bannerUrl) {
			// 这里可以给他一个默认的url
		} else {
			session.setAttribute("bannerUrl", bannerUrl);
		}
		session.setAttribute("site", site);
		PortalLog portal_log = new PortalLog();
		portal_log.setClientIp(ip);
		portal_log.setClientMac(mac);
		portal_log.setNasid(id);
		portal_log.setRequestTime(CalendarUtil.currentTime());
		portal_log.setRouterIp(	gw_address);
		portal_log.setRouterMac(gw_id);
		portal_log.setRouterType("wifidog");
		portal_log.setTerminalDevice(terminalDevice);
		portal_log.setUrl(url);
		portalservice.insertPortalog(portal_log);
		
		return mav;
	}

	/**
	 * 用户登录
	 * 
	 * @param id
	 * @param mac
	 * @param ip
	 * @param url
	 * @param name
	 * @param pwd
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("ProuserLogin")
	@ResponseBody
	public String userLogin(@RequestParam(defaultValue = "-1") String id,
			@RequestParam(defaultValue="")  String mac,
			@RequestParam(defaultValue = "0") String gw_id,
			@RequestParam(defaultValue="")  String ip, @RequestParam (defaultValue="") String url,
			@RequestParam String name, 
			@RequestParam(defaultValue="") String pwd,
			@RequestParam(defaultValue = "0") int chargeType,
			@RequestParam(defaultValue = "pwd") String password,//需要在前端穿过一个标识,如果是输入账号密码不需要这个参数.
			@RequestParam(defaultValue="-2") int siteId,
			@RequestParam(defaultValue="") String type,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws ParseException, NoSuchAlgorithmException {
		ExecuteResult rs = new ExecuteResult();
		String routerType=(String)session.getAttribute("type");
		String terminalDevice=(String)session.getAttribute("terminalDevice"); 
		PortalUser proUser =  null;
		//此时是直接登录时传过来的密码
		if(!password.equals("pwd")){
			proUser = userService.getUserByEncryptPs(name, password);
		}else{
			proUser = userService.getUserPro(name, MD5.encode(pwd).toLowerCase());
		}
		 
		CloudSite site= siteservice.getSiteById(siteId);
		if(site==null){
			rs.setCode(201);
			rs.setMsg("找不到此场所！");
			return rs.toJsonString();
		}
		session.setAttribute("site",site);
		//验证用户账号是否被锁定
		String lock = portalservice.getlockTime(name, site.getId());
		if(lock!=null){
			rs.setCode(203);
			rs.setMsg(lock);
			session.setAttribute("times", lock);
			return rs.toJsonString();
		}
		if(proUser!=null){
			userService.isHavePortalUser(site.getId(), proUser.getId());
		}
		if("ikuai".equals(type)){
			RoutersPortal rp = new RoutersPortal();
			return rp.ikuailogin(request, rs,password);
		}else if("coovachilli".equals(type)){
			RoutersPortal rp = new RoutersPortal();
			String secret = portalservice.getSecret(session.getAttribute("nasid").toString());
			session.setAttribute("secret", secret);
			rs = rp.chilli_login(secret,rs,userService,request,password);
			return rs.toJsonString();
		}else if("ros".equals(type)){
			RoutersPortal rp = new RoutersPortal();
			return rp.login(request,rs,password);
		}else{
			return checkUser(name, mac, pwd,password,session,request,response);
		}

	}

	/**
	 * 跳转登陆失败页面
	 */
	@RequestMapping("loginFail")
	public String loginFail() {
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
		
		//获取用户的余额
//		UserBalance ub = weChatService.getUserBalanceByName(proUser.getUserName());
//		if(ub!=null){
//			session.setAttribute("balance", ub.getBalance());
//		}else{
//			session.setAttribute("balance", 0);
//		}
		
		CloudSite site = (CloudSite) session.getAttribute("site");
		if (null == proUser || null == site) {
			if ("1".equals(isPc)) {
				return "/pc/newPage";
			} else {
				return "/mobile/newPage";
			}
		}
		SiteCustomerInfo sites = userService.getSiteCustomerInfo(site.getId(),
				proUser.getId());
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
			if (date.getTime() > exDate.getTime()
					&& (total == null || "0".equals(total))) {
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
	 * @Description 跳转到缴费页面
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("toRealPay")
	public String toRealPay(HttpSession session, HttpServletRequest request) {
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
		SitePriceConfigAll siteAll = userService.getSitePriceConfigAll(
				site.getId(), proUser.getId());
		if (siteAll != null) {
			session.setAttribute("siteAll", siteAll);
		}
		if ("1".equals(isPc)) {
			return "/pc/pay";
		} else {
			return "/mobile/jiaofei";
		}
	}

	/**
	 * 用户上网时判断状态
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("userStatus")
	@ResponseBody
	public String userStatus(HttpServletRequest request, HttpSession session) {
		ExecuteResult rs = new ExecuteResult();
		PortalUser ptu = (PortalUser) request.getSession().getAttribute(
				"proUser");
		SitePriceConfigAll spca = (SitePriceConfigAll) request.getSession()
				.getAttribute("siteAll");
		int userConfig = siteservice.UserSiteConfig(spca.getSiteInof().getId(),
				ptu.getId());
		if (userConfig != 1) {
			rs.setCode(202);
		} else {
			rs.setCode(200);
		}
		return rs.toJsonString();
	}

	/**
	 * 跳转到警告页面
	 * 
	 * @return
	 */
	@RequestMapping("jinggao")
	public String jinggao(HttpSession session, Model model) {
		return "/pc/jinggao";// 跳转PC
	}

	/**
	 * 跳转到警告页面
	 * 
	 * @return
	 */
	@RequestMapping("mobileJinggao")
	public String mobileJinggao(HttpSession session, Model model) {
		return "/mobile/jinggao"; // 手机站
	}

	/**
	 * 添加token值放行时校验
	 * 
	 * @param devicemac
	 * @param token
	 * @param userName
	 */
	public void insertToken(String devicemac, String token, int userId,
			String oldAuthUrl,String ip,int siteId ) {
		userService.insertToken(devicemac, token, userId, oldAuthUrl,ip,siteId,"");
	}

	/**
	 * wifidog放行前插入数据库中
	 * 
	 * @param session
	 * @param model
	 */
	public void changeData(HttpSession session, HttpServletRequest request,CloudSite site) {
		PortalUser proUser = (PortalUser) session.getAttribute("proUser");// 添加用户到session
		String id = (String) session.getAttribute("siteMac");
		String mac = (String) session.getAttribute("clientMac");
		String ip = (String) session.getAttribute("clientIp");
		// String url=(String)session.getAttribute("fromUrl");
		// String gw_address=(String)session.getAttribute("gw_address");
		// String gw_port=(String)session.getAttribute("gw_port");
		String terminalDevice = request.getHeader("User-Agent");
		String token = (String) session.getAttribute("token") + proUser.getId();
		String sessionId=(String)session.getAttribute("sessionId");
		String oldAuthUrl = "requestTime=" + DateUtil.getStringDate() + "&mac="
				+ mac + "&requestId=" + id + "&lanip=" + ip
				+ "&authMethod=16&authId=" + proUser.getUserName()
				+ "&sex=0&storeId=" + site.getId() + "&terminalDevice="
				+ terminalDevice+"&sessionId="+sessionId;
		insertToken(mac, token, proUser.getId(), oldAuthUrl,ip,site.getId());
	}

	/**
	 * @Description 当点击去下次再说的时跳转的页面并更改用户状态为1
	 * @return
	 */
	@RequestMapping("nextTimeToAuthPage")
	public String goTwoAuthPage(HttpSession session, HttpServletRequest request) {
		String terminalDevice = request.getHeader("User-Agent");
		String isPc = toLoginUtil.getPcOrMobile(terminalDevice);
		PortalUser portalUser = (PortalUser) session.getAttribute("proUser");
		CloudSite site = (CloudSite) session.getAttribute("site");
		// 更改状态为1--下次直接去验证
		userRealnameAuthImpls.updateAuthState(portalUser, 1);

		if ("1".equals(isPc)) {
			// 需要提醒页面 请用手机登录完成您的认证
			return "/pc/remindPage";
		}
		return anywayToSuccess(request, session, site, portalUser);
	}

	/**
	 * @Description <<点击确定>>跳转到成功页面
	 * @return
	 */
	@RequestMapping("goToSuccessPage")
	public String goToSuccessPage(HttpSession session,
			HttpServletRequest request) {
		String terminalDevice = request.getHeader("User-Agent");
		String isPc = toLoginUtil.getPcOrMobile(terminalDevice);
		PortalUser portalUser = (PortalUser) session.getAttribute("proUser");
		CloudSite site = (CloudSite) session.getAttribute("site");
		return anywayToSuccess(request, session, site, portalUser);
	}

	/**
	 * @Description 当点击<去认证>或者<进行验证>时跳转的页面
	 * @return
	 */
	@RequestMapping("goToAuthPage")
	public String goToAuthPage(HttpSession session, HttpServletRequest request) {
		String terminalDevice = request.getHeader("User-Agent");
		String isPc = toLoginUtil.getPcOrMobile(terminalDevice);
		PortalUser portalUser = (PortalUser) session.getAttribute("proUser");
		CloudSite site = (CloudSite) session.getAttribute("site");
		boolean authuser = userRealnameAuthImpls.isAuth(portalUser);
		if (authuser) {
			return anywayToSuccess(request, session, site, portalUser);
		}
		if ("1".equals(isPc)) {
			// 需要提醒页面 请用手机登录完成您的认证
			return "/pc/remindPage";
		} else {
			return "/mobile/autonymForm";
		}
	}

	/**
	 * @Description 场所下是否开启认证,如果开启时0--新注册用户第一次可以通过,
	 *              第二次需要认证,,1--直接去认证,,2--完成认证,,3--未通过去认证
	 * 
	 * @param portalUser
	 * @param session
	 * @param isPc
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	@RequestMapping("goToWherePage")
	public String goToWherePage(HttpSession session, HttpServletRequest request) throws NoSuchAlgorithmException {
		PortalUser portalUser = (PortalUser) session.getAttribute("proUser");
		String terminalDevice = request.getHeader("User-Agent");
		String isPc = toLoginUtil.getPcOrMobile(terminalDevice);
		// 0--新注册用户第一次可以通过,第二次需要认证,,1--直接去认证,,2--完成认证,,3--未通过去认证
		String state = String.valueOf(userRealnameAuthImpls
				.getUserAuthState(portalUser));
		String mac = (String) session.getAttribute("siteMac");
		// 场所是否存在
		//CloudSite site = siteservice.getSite(mac);
		CloudSite site = (CloudSite)session.getAttribute("site");
		//session.setAttribute("site", site);
		if(session.getAttribute("loginsuccess")!=null){
			boolean loginsuccess = (boolean)session.getAttribute("loginsuccess");
			if(!loginsuccess){
				RoutersPortal routerportal = new RoutersPortal();
				String  returnlog= routerportal.getRedirectUrl(session,portalUser);
						if(returnlog!=null){
							return returnlog;
						}
			}
		//session.setAttribute("site", site);
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
				return anywayToSuccess(request, session, site, portalUser);
			} else if ("3".equals(state)) {
				// 未完成认证 直接跳转到重新认证
				if ("1".equals(isPc)) {
					return "/pc/remindPage";// 提示页面让用户去手机端进行认证完成后在在操作
				} else {
					return "/mobile/autonymc";
				}
			}
		}
		return anywayToSuccess(request, session, site, portalUser);
	}

	/**
	 * @Description 支付同步通知结果的页面
	 * @date 2016年5月31日下午3:11:31
	 * @author guoyingjie
	 * @param request
	 * @param session
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("resultPage")
	public String resultPage(HttpServletRequest request, HttpSession session)
			throws UnsupportedEncodingException {
		PortalUser portalUser = (PortalUser) session.getAttribute("proUser");
		CloudSite site = (CloudSite) session.getAttribute("site");
		String orderNum = null;
		String token = null;
		String zhiOrderNum = null;
		String tradeNum = null;
		try {
			String borderNum = new String(request.getParameter("tradeNum"));
			log.error("京东进行3DES加密之前订单号=="+borderNum);
			/*token = new String(request.getParameter("token"));*/
			String deskey = merchantConstant.getMerchantDESKey();
			byte[] keys = BASE64.decode(deskey);
			orderNum = ThreeDesUtil.decrypt4HexStr(keys, borderNum);
			log.error("京东进行3DES解密之后订单号=="+borderNum);
		} catch (Exception e) {
			log.info("银行卡同步通知");
		}
		try {
			zhiOrderNum = new String(request.getParameter("out_trade_no"));
			tradeNum = new String(request.getParameter("trade_no"));
		} catch (Exception e) {
			log.info("支付宝支付同步通知");
		}
		String terminalDevice = request.getHeader("User-Agent");
		String isPc = toLoginUtil.getPcOrMobile(terminalDevice);
		if (null == portalUser) {
			if ("1".equals(isPc)) {
				return "/pc/newPage";
			} else {
				return "/mobile/newPage";
			}
		}
		// 说明是支付包的支付
		if (zhiOrderNum != null && tradeNum != null) {
			boolean is = siteservice.getSitePaymentRecordByTradeNum(
					zhiOrderNum, tradeNum, site.getId(), portalUser.getId());
			if (is) {
				return this.anywayToSuccess(request, session, site, portalUser);
			} else {
				if ("1".equals(isPc)) {
					return "/pc/payFail";
				} else {
					return "/mobile/payFail";
				}
			}
		} else {
			// 说明是银行卡
			boolean isok = userService.checkPayResult(orderNum);
			if (isok) {
				//sitePaymentRecordsService.insertToken(token, portalUser.getId());
				return this.anywayToSuccess(request, session, site, portalUser);
			} else {
				if ("1".equals(isPc)) {
					return "/pc/payFail";
				} else {
					return "/mobile/payFail";
				}
			}
		}
	}

	/**
	 * @Description 在这个页面进行放行操作,并拿到用户的剩余时长与购买总量
	 * @param request
	 * @param session
	 * @return
	 */
	public String anywayToSuccess(HttpServletRequest request,
			HttpSession session, CloudSite site, PortalUser portalUser) {
		    String clientmac = (session.getAttribute("clientmac")+"").trim();
			String wifidog = (String) session.getAttribute("fromFw");
			String authUrl = "";
			if (wifidog != null && !"".equals(wifidog)) {
				authUrl = siteservice.getAuthUrl(session, request, site);
			}
			try {
				Map<String, Object> map = userService.getSiteIncomeRecord(
						site.getId(), portalUser.getId());
				Map<String, Object> map2 = userService.getSYtimeOrflow(
						site.getId(), portalUser.getId());
				if (map.containsKey("time") && map2.containsKey("time")) {
					session.setAttribute("allTimeAndFlow", map);
					session.setAttribute("SyTimeAndFlow", map2);
					session.setAttribute("bili", userService.getTyleBili(map, map2));
				}
			} catch (Exception e) {
				log.error("获得总时间与总流量失败");
			}
			int mess = userService.getMessageNumber(portalUser.getId());
			session.setAttribute("mess", mess);
	
		String terminalDevice = request.getHeader("User-Agent");
		String isPc = toLoginUtil.getPcOrMobile(terminalDevice);
		if(authUrl!=null&&authUrl.length()>1){
			session.setAttribute("releaseUrl", authUrl);
			changeData(session, request,site);
		}
		String routerType=(String)session.getAttribute("type");
		if(routerType!=null&&routerType.equals("ikuai")){
			List<Map<String ,String>> ls=(List)session.getAttribute("loginMac");
			 
			if(ls!=null){
				String ip= session.getAttribute("clientIp").toString();
				String basip = session.getAttribute("basip").toString();
				String mac=session.getAttribute("clientMac").toString();
				String loginAction="PORTAL_LOGIN";
				String outAction="PORTAL_LOGINOUT";
				boolean isOk=IkuaiAuthloginservice.Method(outAction, portalUser.getUserName(), portalUser.getPassWord().substring(0, 16), ls.get(0).get("framedipaddress"),  ls.get(0).get("nasipaddress"), ls.get(0).get("callingstationid"));
				if(isOk)
				isOk=IkuaiAuthloginservice.Method(loginAction, portalUser.getUserName(),portalUser.getPassWord().substring(0, 16), ip, basip, mac);
				if(isOk){
					portalservice.updateTerminal(terminalDevice, mac, ip, portalUser.getUserName());
					userService.insertToken(mac, "", portalUser.getId(), "", ip,site.getId(),basip);
					session.removeAttribute("loginMac");
				}
			}
		}
		try {
			//用户登陆记住最后一次的设备mac
			if(portalUser.getClientMac()!=null){
				if(!portalUser.getClientMac().equals(clientmac)){
					userService.updateUserLastMac(portalUser, clientmac);
				}
			}else{
				userService.updateUserLastMac(portalUser, clientmac);
			}
		} catch (Exception e) {
		   log.error("用户登陆记住最后一次的设备mac异常",e);
		}
		if ("1".equals(isPc)) {
			return "/pc/surplus";
		} else {
			return "/mobile/details";
		}
	}

	/**
	 * @Description 是否开启了认证
	 * @return 0--代表开启认证--true 1--代表关闭认证--false
	 */
	public boolean ifGoAuth(CloudSite site) {
		boolean is = userRealnameAuthImpls.ifGoAuth(site);
		if (is) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 生成三位位随机数
	 */
	public static String randCode() {
		String code = "";
		for (int i = 0; i < 3; i++) {
			Random rand = new Random();
			code += rand.nextInt(9);
		}
		return code.trim() + new Date().getTime();
	}

	/**
	 * @Description 检查是否有这张身份证号码
	 * @param idCard
	 * @return --"false"==代表有 "true"==代表没有
	 */
	@RequestMapping("isHaveIdCard")
	@ResponseBody
	public String isHaveIdCard(@RequestParam String idCard) {
		boolean isHave = userRealnameAuthImpls.isHaveIdCard(idCard);
		if (isHave) {
			return "false";
		} else {
			return "true";
		}
	}

	/**
	 * @Description 实名认证成功后直接到统一页面
	 * @return
	 */
	@RequestMapping("/goSuccessAuthPage")
	public String goSuccessAuthPage(HttpSession session,
			HttpServletRequest request) {
		PortalUser proUser = (PortalUser) session.getAttribute("proUser");
		CloudSite site = (CloudSite) session.getAttribute("site");
		String terminalDevice = request.getHeader("User-Agent");
		String isPc = toLoginUtil.getPcOrMobile(terminalDevice);
		if (null == proUser || null == site) {
			if ("1".equals(isPc)) {
				return "/pc/newPage";
			} else {
				return "/mobile/newPage";
			}
		}
		return anywayToSuccess(request, session, site, proUser);
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
	public String goToPerson(HttpServletRequest request,
			@RequestParam String userName, @RequestParam int siteId,
			HttpSession session) {
		String terminalDevice = request.getHeader("User-Agent");
		String isPc = toLoginUtil.getPcOrMobile(terminalDevice);
		// PortalUser proUser=(PortalUser) session.getAttribute("proUser");
		PortalUser proUser = userService.getPortalUserByTel(userName);
		session.removeAttribute("proUser");
		session.setAttribute("proUser", proUser);
		CloudSite site = (CloudSite) session.getAttribute("site");
		if (proUser == null) {
			if ("1".equals(isPc)) {
				return "/pc/newPage";
			} else {
				return "/mobile/newPage";
			}
		}
		try {
			userService.perfectInfo(userName, siteId);
			userService.getUserMessage(site.getId(), proUser.getId());
			userService.getUserGift(site.getId(), proUser.getId());
			userService.getUserLottery(site.getId(), proUser.getId());
			userService.getMessageLottery(site.getId(), proUser.getId());
		} catch (Exception e) {
			log.error(this.getClass().getCanonicalName() + "--goToPerson");
		}
		if ("1".equals(isPc)) {
			return "/pc/person";
		} else {
			return "/mobile/person";
		}
	}

	/**
	 * 缴费记录 update by:cuimiao
	 * 
	 * @Description
	 * @param telphone
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
		List<Map<String, Object>> recordList = userService.getPayRecord(userId,
				site_id, pageIndex, pageNum);
		List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
		// 处理list，将时间（create_time字段）根据当天日期处理成“今天 08:30 ，昨天 13:20， 2016-5-16
		// 18:20” 的形式
		// 处理param_json，解析amount消费额，通过payType关联t_site_price_config表id字段获取name值作为已购买套餐名
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
	 * 缴费记录 update by:cuimiao
	 * 
	 * @Description
	 * @param telphone
	 * @param site_id
	 * @param request
	 * @param pageIndex
	 * @param pageNum
	 * @return
	 */
	@RequestMapping("/payRecordForPc")
	@ResponseBody
	public String payRecordForPc(
			@RequestParam(defaultValue = "") String userId,
			@RequestParam(defaultValue = "") String site_id,
			HttpServletRequest request) {
		ExecuteResult er = new ExecuteResult();
		// 获取缴费记录list
		List<Map<String, Object>> recordList = userService.getPayRecord(userId,
				site_id);
		List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
		// 处理list，将时间（create_time字段）根据当天日期处理成“今天 08:30 ，昨天 13:20， 2016-5-16
		// 18:20” 的形式
		// 处理param_json，解析amount消费额，通过payType关联t_site_price_config表id字段获取name值作为已购买套餐名
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
	 * @Description 去缴费记录导航页
	 * @date 2016年5月25日下午7:32:09
	 * @author guoyingjie
	 * @param request
	 * @return
	 */
	@RequestMapping("/toPayRecord")
	public String toPayRecord(HttpServletRequest request) {
		String terminalDevice = request.getHeader("User-Agent");
		String isPc = toLoginUtil.getPcOrMobile(terminalDevice);
		if (isPc.equals("1")) {
			return "/pc/payInfo";
		} else {
			return "/mobile/payInfo";
		}
	}

	/**
	 * @Description 获得缴费记录的总记录数
	 * @date 2016年5月26日上午9:38:51
	 * @author guoyingjie
	 * @param userId
	 * @param site_id
	 * @return
	 */
	@RequestMapping("getRecordCount")
	@ResponseBody
	public String getRecordCount(@RequestParam(defaultValue = "") int userId,
			@RequestParam(defaultValue = "") int site_id) {
		ExecuteResult ex = new ExecuteResult();
		try {
			int count = userService.getRecordCount(site_id, userId);
			ex.setCode(200);
			ex.setData(count);
			return ex.toJsonString();
		} catch (Exception e) {
			ex.setCode(201);
			return ex.toJsonString();
		}
	}

	/**
	 * @Description　　切换账号
	 * @date 2016年6月1日上午10:30:10
	 * @author guoyingjie
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("changeNumber")
	public String changeNumber(HttpSession session, HttpServletRequest request) {
		this.removeSession(session);
		String routerType=(String)session.getAttribute("type");
		CloudSite site = (CloudSite) session.getAttribute("site");
		PortalUser proUser = (PortalUser) session.getAttribute("proUser");
		if(routerType!=null&&routerType.equals("ikuai")){
			return "redirect:http://1.0.0.0/logout";
		}else if(routerType!=null&&routerType.equals("coovachilli")){
			return "redirect:http://"+(String)session.getAttribute("uamip")+":3990/logoff";
		}else if(routerType!=null&&routerType.equals("ros")){
			return "redirect:http://"+(String)session.getAttribute("uamip")+"/logout";
		}else{
			
			String gw_address = (String) session.getAttribute("gw_address");
			String gw_port = (String) session.getAttribute("gw_port");
			String token =String.valueOf(site.getStauts())+ System.currentTimeMillis()
						+ String.valueOf(proUser.getId()) + "s0000";
			String	authUrl = "http://" + gw_address + ":" + gw_port
						+ "/wifidog/auth?token=" + token + "&url="
						+ "http://www.gonet.cc" + "&timestamp="
						+ DateUtil.getDateNoP();
			session.setAttribute("releaseUrl", authUrl);
		}
		return "/mobile/page";
	}

	/**
	 * @Description 上传用户头像
	 * @date 2016年5月25日下午4:21:04
	 * @author guoyingjie
	 * @param session
	 * @param userName
	 * @param pictureBase64
	 * @return
	 */
	@RequestMapping("uploadUserPicture")
	@ResponseBody
	public String uploadUserPicture(HttpSession session,
			@RequestParam String userName,
			@RequestParam(defaultValue = "") String pictureBase64) {
		ExecuteResult rs = new ExecuteResult();
		InputStream baseInputOne = StringUtil.getInputStream(pictureBase64);
		OssSchoolManage oss = new OssSchoolManage();
		String names = userName + ".jpg";
		try {
			String isOk = oss.uploadFile(baseInputOne, "user_pic/" + names,
					"image/jpeg");
			if (isOk != null) {
				userService.updateUserPicture(userName, names);
			}
			return "1";
		} catch (Exception e) {
			log.error("上传用户头像失败");
			return "0";
		}
	}

	/**
	 * @Description 跳转到微信支付页面
	 * @date 2016年6月3日上午11:06:40
	 * @author guoyingjie
	 * @return
	 */
	@RequestMapping("goToWeixinPay")
	public String goToWeixinPay() {
		return "/pc/weixPay";
	}
	/***
	 * 
	 */
	@RequestMapping("timeMessage")
	@ResponseBody
	public String getMessageList(ModelAndView mv,HttpServletRequest request){
		HttpSession session = request.getSession();
		PortalUser portalUser = (PortalUser) session.getAttribute("proUser");
		int mess = 0;
		if(portalUser!=null){
		mess = userService.getMessageNumber(portalUser.getId());
		}
		return "{'success':true,'mess':'"+mess+"'}";
	}
	
	/**
	 * 
	 *	@Description:校验用户登陆时的信息
	 *  @author songyanbiao
	 *	@Date 2016年6月6日 
	 *	@param id
	 *	@param name
	 *	@param mac
	 *	@param pwd
	 *	@param session
	 *	@return
	 *	@throws ParseException
	 */
	public String checkUser(String name,String mac,String pwd,String flag,HttpSession session,HttpServletRequest request, HttpServletResponse response) throws ParseException{
		ExecuteResult rs = new ExecuteResult();
		// 校验场所是否存在
		CloudSite site = (CloudSite) session.getAttribute("site");;
		if (site == null) {
			rs.setCode(201);
			rs.setMsg("找不到此场所！");
			return rs.toJsonString();
		}
		
		// 判断用户是否已存在
		PortalUser pt = userService.getPortalUserByTel(name);
		if (pt == null) {
			rs.setCode(201);
			rs.setMsg("该用户不存在，请注册");
			return rs.toJsonString();
		}
		PortalUser proUser = null;
		
		String wifidog = (String)session.getAttribute("fromFw");//wifidog
		if("wifidog".equals(wifidog)){
			if(!"".equals(flag)&&!"pwd".equals(flag)){
				proUser = userService.getUserByEncryptPs(name,flag);
			}else{
				proUser = userService.getUserPro(name, MD5.encode(pwd).toLowerCase());
			}
		}else{
			if("have".equals(flag)){
				proUser = userService.getUserByEncryptPs(name, (String)session.getAttribute("Password"));
			}else{
				proUser = userService.getUserPro(name, MD5.encode(pwd).toLowerCase());
			}
		}
		
		if (proUser == null) {
			rs.setCode(201);
			rs.setMsg("用户名或密码错误！");
			return rs.toJsonString();
		} else {
			if (proUser.getIsStoped() == 1) {
				rs.setCode(201);
				rs.setMsg("用户停用请联系管理员！");
				return rs.toJsonString();
			}
		}
		session.setAttribute("proUser", proUser);// 添加用户到session
		session.setAttribute("UserName", proUser.getUserName());// 添加用户到session
		SiteCustomerInfo scis = userService.isHaveSiteCustomerInfo(
				proUser.getId(), site.getId());
		if (scis != null) {
			// 判断是否有锁定信息
			String isok = userService.ifUserLuck(scis);
			if (!"null".equals(isok)) {
				rs.setCode(203);
				rs.setMsg(isok);
				session.setAttribute("times", isok);
				return rs.toJsonString();
			} else {
				// 判断是否超过3次登录
				boolean ok = userService.isSuperThree(proUser.getUserName(),
						site.getId(), mac, scis);
				if (ok) {
					rs.setCode(203);
					return rs.toJsonString();
				}
			}
			 
		}  
		// 检查用户帐户状态，3种
		int userConfig=1;
		if(site.getStauts()==900){
			userConfig= siteservice.UserSiteConfig(site.getId(),
					proUser.getId());
		}
		if (userConfig != 1) {
			rs.setCode(202);
			rs.setMsg("登录成功,需要充值!");
			return rs.toJsonString();
		} else {
			// 用户登陆时检验该账号是否已经处于登陆状态中
			List list = userService.hasLogin(name, site.getId(), mac);
			if (list != null && list.size() != 0) {
				rs.setCode(300);
				rs.setData(list);
				return rs.toJsonString();
			} else {
				String siteMac =(String) session.getAttribute("siteMac");
				CloudSiteRouters csr = null;
				if(siteMac!=null&&siteMac.length()>0){
					csr=userService.selRouter(siteMac.replace("-", ""));
				}
				if(csr==null){
					csr = portalservice.selRouter((String)session.getAttribute("nasid"));
				}
				if(csr==null){
					rs.setCode(201);
					rs.setMsg("场所设备不存在！");
					return rs.toJsonString();
				}
				//登陆时查询路由ip是否正确,不正确修改路由ip
				AuthController authController = new AuthController();
				String newip = authController.getWanIp(request, response);
				if(csr.getIp()==null||!csr.getIp().equals(newip)){
					boolean flags=userService.updateRouIp(csr,newip);
					if(!flags){
						rs.setCode(201);
						rs.setMsg("路由ip错误,请修改路由ip");
						return rs.toJsonString();
					}
				}
				rs.setCode(200);
				rs.setMsg("登录成功！");
				return rs.toJsonString();
			}
		}
	}
	/**
	 * @Description 检测当前登陆用户是否注册过,
	 * 如果没有注册,直接把当前的账号注册,并发短信提示给他,默认一个用户登录密码
	 * 
	 * @date 2016年7月25日下午1:05:29
	 * @author guoyingjie
	 * @param username
	 * @return same=说明最后一次登录的用户是同一个设备,可以直接去登录
	 *         diff=直接填写用户名跟密码才能登录(说明是两台设备上登录)
	 *         register==没有用户信息,直接去注册  
	 */
	@RequestMapping("checkUserRegister")
	@ResponseBody
	public String checkUserRegister(@RequestParam String userName,@RequestParam(defaultValue="") int siteId,HttpSession session){
		
		PortalUser user = userService.getPortalUserByTel(userName);
		if(userName.indexOf("a")>-1||userName.indexOf("b")>-1){
			if(user==null){
				return "son";
			}else{
				int state = user.getSonstate();//0可以上网,1不可以上网
				if(state==1){
					return "noactive";
				}else{
					if(!"".equals(siteId)){
						CloudSite site = siteservice.getSiteById(siteId);
						if(site!=null){
							if(site.getStauts()==900){//正常的计费
								SiteCustomerInfo info = userService.getSiteCustomerInfo(siteId,user.getId());
								if(info==null){
									return "nomoney";
								}else{
									Date date = info.getExpirationTime();
									String flow = info.getTotalFlow();
									Date nowDate = new Date();
									if(date==null){
										date = nowDate;
									}
									if(flow==null||"".equals(flow)){
										flow = "0";
									}
									if(date.getTime()<=nowDate.getTime()&&Long.parseLong(flow)<=0){
										return "nomoney";
									}
								}
							}
						}
					}
				}
			}
		}
		if(user!=null){
			//直接显示登录跟密码框登录系统
			return "diff";
		}else{
			//隐形的去注册当前用户
			return "register";
		}
	}
	
	/**
	 * @Description 检测发送的密码跟填入的密码是否一致
	 * @date 2016年7月25日下午4:42:24
	 * @author guoyingjie
	 * @param session
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("checkPassword")
	@ResponseBody
	public String checkPassword(HttpServletRequest request,@RequestParam String username,@RequestParam String password){
		ExecuteResult er = new ExecuteResult();
		//获取生成的验证码
		String PasswordCode=(String)request.getSession().getAttribute(username);
		//判断验证码是否正确
		if(!password.equals(PasswordCode)){
			er.setCode(201);
			er.setMsg("密码输入错误");
			return er.toJsonString();
		}
		//5分钟
		Long oldTime=(Long)request.getSession().getAttribute("randCodeTime");
		oldTime=oldTime==null?0:oldTime;
		long newTime=new Date().getTime();
		if((newTime-5*60*1000)<=oldTime){//通过
			request.getSession().removeAttribute(username);
			request.getSession().removeAttribute("randCodeTime");
			
			/*PortalUser user = userService.getPortalUserByTel(username);
			if(user!=null){
				er.setCode(203);
			}else{*/
				er.setCode(200);
			/*}*/
			return er.toJsonString();
		}else{
			er.setCode(202);
			er.setMsg("密码失效,请重新获取");
			return er.toJsonString();
		}		
	}
	
	/**
	 * @Description 清空session,并将最后一次登录的设备mac
	 * @date 2016年8月11日下午5:39:07
	 * @author guoyingjie
	 * @param session
	 * @return
	 */
	@RequestMapping("removeSession")
	@ResponseBody
	public String removeSession(HttpSession session){
		try {
			session.removeAttribute("have");
			PortalUser user = (PortalUser)session.getAttribute("proUser");
			if(user!=null){
				String mac = user.getClientMac();
				if(mac!=null&&!"".equals(mac)){
					user.setClientMac(null);
					user.setUserName(user.getUserName());
					user.setPassWord(user.getPassWord());
					userService.userUpdata(user);
					session.removeAttribute("proUser");
				}
			}
		} catch (Exception e) {
			log.error("清空session,并将最后一次登录的设备mac异常",e);
		}
		return "ok";
	}
	
	/**
	 * @Description  子账号需要对wifidog放行,找了一个中间页直接跳转到http://www.2345.com
	 * @date 2016年10月28日下午2:23:11
	 * @author guoyingjie
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("successCenter")
	public ModelAndView successCenter(HttpSession session,HttpServletRequest request){
		ModelAndView view = new ModelAndView();
		String wifidog = (String) session.getAttribute("fromFw");
		CloudSite site = (CloudSite)session.getAttribute("site");
		String authUrl = "";
		if (wifidog != null && !"".equals(wifidog)) {
			authUrl = siteservice.getAuthUrl(session, request, site);
		}
		if(authUrl!=null&&authUrl.length()>1){
			session.setAttribute("releaseUrl", authUrl);
			changeData(session, request,site);
		}
		view.setViewName("/mobile/center");
		return view;
	}
	
}
