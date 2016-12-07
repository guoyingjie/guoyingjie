package com.broadeast.controller.wifidog;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.broadeast.util.BASE64;
import com.broadeast.util.StringUtils;
import com.broadeast.entity.CloudSiteRouters;
import com.broadeast.entity.Coovachilli;
import com.broadeast.entity.PortalLog;
import com.broadeast.entity.PortalUser;
import com.broadeast.service.impl.PortalLogService;
import com.broadeast.service.impl.UserService;
import com.broadeast.util.CalendarUtil;
import com.broadeast.util.ExecuteResult;
import com.broadeast.util.MD5;
import com.broadeast.util.RadMd5;
import com.broadeast.util.SHA256;
import com.broadeast.util.WanipUtil;

public class RoutersPortal {
	private static Logger log=Logger.getLogger(AuthController.class);
	
	public ModelAndView ikuai_portal(ModelAndView mav,HttpServletRequest request){
		
		return null;
	}
	private static String httpurl=null;
	public PortalLog ikuai_login(HttpServletRequest request,HttpServletResponse response,PortalLog portal_log) throws UnsupportedEncodingException{
		String ikenc=request.getParameter("ikenc");
		String refer=request.getParameter("refer");
		String ikcs = "";
		String ikweb = "";
		 if ((StringUtils.isNotBlank(ikenc)))
		      try {
		        ikcs = BASE64.decryptBASE64(ikenc);
		       if( (StringUtils.isNotBlank(refer))){
		    	   ikweb = BASE64.decryptBASE64(refer);
		       }
		      }
		      catch (Exception localException1) {
		      }
		 String gwid="";
		 String router_ver="";
		 String mac="";
		 String user_ip="";
		 String timestamp="";
		 String bssid="";
		 String apmac="";
		 String ssid="";
		 String firmware="";
		 String nasname="";
		 String basip="";
		    if (StringUtils.isNotBlank(ikcs)) {
		      String[] cs = ikcs.split("&");
		      for (String c : cs) {
		    	if(c.startsWith("gwid=")){
		    		gwid=c.replace("gwid=", "");
		    	}
		        if (c.startsWith("nasname=")) {
		          nasname = c.replace("nasname=", "");
		        }
		        if (c.startsWith("gwid=")) {
			          nasname = c.replace("gwid=", "");
			        }
		        if (c.startsWith("basip=")) {
		        	basip = c.replace("basip=", "").trim();
		        }
		        if (c.startsWith("user_ip=")) {
		        	user_ip = c.replace("user_ip=", "");
		        }
		        if (c.startsWith("mac=")) {
		        	mac = c.replace("mac=", "");
		        }
		        if (c.startsWith("ssid=")) {
		          ssid = c.replace("ssid=", "");
		          ssid = URLDecoder.decode(ssid, "utf-8");
		        }

		      }
		    }
			String basIp=WanipUtil.getWanIp(request, response);
		    HttpSession session = request.getSession();
		    session.setAttribute("nasname", nasname);
		    session.setAttribute("basip", basIp);
		    session.setAttribute("clientIp", user_ip);
		    session.setAttribute("clientMac", mac);
		    session.setAttribute("nasid", nasname);
		    session.setAttribute("siteMac", gwid);
		    session.setAttribute("type","ikuai");
			
			portal_log.setClientIp(user_ip);
			portal_log.setClientMac(mac);
			portal_log.setNasid(nasname);
			portal_log.setRequestTime(CalendarUtil.currentTime());
			portal_log.setRouterIp(basIp);
			portal_log.setRouterMac(gwid);
			portal_log.setRouterType("ikuai");
			portal_log.setUrl(ikweb);
		
		return portal_log;
	}
	
/**
 * @Description 小辣椒portal处理
 * @date 2016年8月12日下午1:59:52
 * @author guoyingjie
 * @param userService
 * @param request
 * @param password
 * @return
 * @throws ParseException
 */
public Coovachilli chilli_portal(UserService userService,HttpServletRequest request,String password) throws ParseException{
	String res=request.getParameter("res");
	String reason=request.getParameter("reason");//radius返回值reason:timeout
	String uamip=request.getParameter("uamip");
	String uamport=request.getParameter("uamport");
	String challenge=request.getParameter("challenge");
	String called=request.getParameter("called");
	String mac=request.getParameter("mac");
	String ip=request.getParameter("ip");
	String nasid=request.getParameter("nasid");
	String sessionid=request.getParameter("sessionid");
	String userurl=request.getParameter("userurl");
	String md=request.getParameter("md");
	String timeleft=request.getParameter("timeleft");
	Coovachilli coova = new Coovachilli();
	coova.setCalled(called);
	coova.setChallenge(challenge);
	coova.setIp(ip);
	coova.setMac(mac);
	coova.setMd(md);
	coova.setNasid(nasid);
	coova.setRes(res);
	HttpSession session = request.getSession();
	String type =  (String)session.getAttribute("type");
	if(res==null){
		coova.setPortalValue("no_login");
		return coova;
	}
	session.setAttribute("challenge", challenge);
	if(res.equals("notyet")||res.equals("failed")||res.equals("logoff")){
	session.setAttribute("reason", reason);
	session.setAttribute("uamip", uamip);
	session.setAttribute("uamport", uamport);
	session.setAttribute("siteMac", nasid);
	session.setAttribute("called", called);
	session.setAttribute("clientMac", mac.replace("-", ""));
	session.setAttribute("Mac", mac);
	session.setAttribute("clientIp", ip);
	session.setAttribute("nasid", nasid);
	session.setAttribute("md", md);
	session.setAttribute("type", "coovachilli");
	}
	String page="";
	switch (res) {			
	case "success":   
		session.setAttribute("loginsuccess", true);
		String UserName=(String)session.getAttribute("UserName");
		String Password = (String)session.getAttribute("Password");
		PortalUser proUser = null;
		if("have".equals(password)){
			proUser = userService.getUserByEncryptPs(UserName, Password);
		}else{
			proUser = userService.getUserPro(UserName, MD5.encode(Password).toLowerCase());
		}
		
		coova.setPortalUser(proUser);
		coova.setCalled((String)session.getAttribute("siteMac"));
		  session.setAttribute("proUser", proUser);
		  coova.setPortalValue("success");
		 return coova;
	  case "failed":  
		  session.setAttribute("loginsuccess", false);
		  coova.setCalled((String)session.getAttribute("siteMac"));
		  coova.setMac((String)session.getAttribute("clientMac"));
		  coova.setPortalValue("failed");
		  return coova;
	  case "logoff":    
		  coova.setPortalValue("logoff");
		  return coova;
	  case "already":  
		  coova.setPortalValue("already");
		  return coova;
	  case "notyet":    
		  coova.setPortalValue("notyet");
		  return coova;
	  case "smartclient": 
		  coova.setPortalValue("smartclient");
		  return coova;
	  case "popup1":  
		  coova.setPortalValue("popup1");
		  return coova;
	  case "popup2":  
		  coova.setPortalValue("popup2");
		  return coova;
	  case "popup3":  
		  coova.setPortalValue("popup3");
		  return coova;
	  default: 
		  ; // Default: It was not a form request}
	}
	return coova;
	}
	
	/**
	 * 
	 * @Description  小辣椒登录触发
	 * @date 2016年8月12日下午1:58:06
	 * @author guoyingjie
	 * @param secret
	 * @param rs
	 * @param userService
	 * @param request
	 * @param password
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public ExecuteResult chilli_login(String secret,ExecuteResult rs,UserService userService,HttpServletRequest request,String password) throws NoSuchAlgorithmException{
		String UserName=request.getParameter("name");
		 
		HttpSession session = request.getSession();
		String uamip=(String)session.getAttribute("uamip");
		String uamport=(String)session.getAttribute("uamport");
		String challenge=(String)session.getAttribute("challenge");
		String userurl=(String)session.getAttribute("userurl");
		String newPw = "";
		if(!password.trim().equals("pwd")){
			newPw = password;
			session.setAttribute("UserName", UserName);
			session.setAttribute("Password",newPw);
		}else{
			String Password=request.getParameter("pwd");
			session.setAttribute("UserName", UserName);
			session.setAttribute("Password",Password);
			newPw = SHA256.getUserPassword(UserName, MD5.encode(Password).toLowerCase());
		}	
		
		byte[] bytes = RadMd5.pack(challenge);
		String newchall = RadMd5.md52(secret,bytes);
		byte[] newchallen =RadMd5.pack(newchall);
		String md5pass = RadMd5.md5("\0"+newPw.substring(0, 16),newchallen);//pc.encodePacket(Password, "testing123");
		rs.setData("{'uamip':'"+uamip+"','uamport':'"+uamport+"','username':'"+UserName+"','pappassword':'"+md5pass+"','userurl':'"+userurl+"'}");
		rs.setCode(101);
		return rs;
	}
	 
	/**
	 * @Description  对接ros路由系统登录触发
	 * @date 2016年7月29日下午2:26:08
	 * @author guoyingjie
	 * @param request
	 * @param rs
	 * @param flag
	 * @param password
	 * @return
	 */
	public String login(HttpServletRequest request,ExecuteResult rs,String password){
		HttpSession session = request.getSession();
		String uamip = (String)session.getAttribute("uamip");
		String UserName=request.getParameter("name");
		//String Password=request.getParameter("pwd");
		String newPw = "";
		//说明是已经登录的用户,密码存入的是加密后的密码
		if(!password.trim().equals("pwd")){
			newPw = password;
			session.setAttribute("UserName", UserName);
			session.setAttribute("Password",newPw);
		}else{
			String Password=request.getParameter("pwd");
			session.setAttribute("UserName", UserName);
			session.setAttribute("Password",Password);
			newPw = SHA256.getUserPassword(UserName, MD5.encode(Password).toLowerCase());
		}		
		String url="http://"+uamip+"/login?dst=www.gonet.cc&popup=true&username="+UserName+"&password="+newPw.substring(0, 16);
		rs.setData("{'url':'"+url+"'}");
		rs.setCode(102);
		return rs.toJsonString();
	}
	
	/**
	 * @Description  ros系统对接,portal处理
	 * @date 2016年8月12日下午1:54:18
	 * @author guoyingjie
	 * @param userService
	 * @param request
	 * @param password
	 * @return
	 */
	public String rosportal(UserService userService,HttpServletRequest request){
		HttpSession session = request.getSession();
		
		
		String ros = request.getParameter("ros");
		String error = request.getParameter("error");
		
		if("notyet".equals(ros)){
			if(error!=null&&error.length()>3){
				session.setAttribute("loginsuccess", false);
				return "err";
			}
			String uamip = request.getParameter("uamip");
			String mac = request.getParameter("mac");
			String user = request.getParameter("user");
			String userurl = request.getParameter("userurl");
			String nasid = request.getParameter("nasid");
			session.setAttribute("type","ros");
			session.setAttribute("nasid",nasid);
			session.setAttribute("clientMac", mac.replace("-", ""));
			session.setAttribute("uamip",uamip);
			return "notyet";
		}else if("success".equals(ros)){
			session.setAttribute("loginsuccess", true);
			String UserName=(String)session.getAttribute("UserName");
			String Password = (String)session.getAttribute("Password");
			PortalUser proUser = null;
			String flag = (String)session.getAttribute("have");
			
			if("have".equals(flag)){
				proUser = userService.getUserByEncryptPs(UserName,Password);
			}else{
				proUser = userService.getUserPro(UserName, MD5.encode(Password).toLowerCase());
				
			}
			session.setAttribute("proUser", proUser);
			return "success";
		}
	return null;
	}
	/**
	 * @Description  对接ikuai路由系统
	 * @date 2016年7月29日下午2:09:11
	 * @author guoyingjie
	 * @param request
	 * @param rs
	 * @param flag--标识代表是
	 * @param password
	 * @return
	 */
	public String ikuailogin(HttpServletRequest request,ExecuteResult rs,String password){
		HttpSession session = request.getSession();
		String uamip = (String)session.getAttribute("uamip");
		String UserName=request.getParameter("name"); 
		String newPw = "";
		//说明是已经登录的用户,密码存入的是加密后的密码
		if(!password.trim().equals("pwd")){
			newPw = password;
			session.setAttribute("UserName", UserName);
			session.setAttribute("Password",newPw);
		}else{
			String Password=request.getParameter("pwd");
			session.setAttribute("UserName", UserName);
			session.setAttribute("Password",Password);
			newPw = SHA256.getUserPassword(UserName, MD5.encode(Password).toLowerCase());
		}
		
		String usrmac= (String)session.getAttribute("clientMac");
		String usrip =  (String)session.getAttribute("clientIp");
		
		StringBuffer url = request.getRequestURL();  
		httpurl = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString(); 
		String urls="http://portal.ikuai8.com/webradius?usrname="+UserName+"&passwd="+newPw.substring(0, 16)+"&usrmac="+usrmac+"&usrip="+usrip
				+"&success="+httpurl+"/deck/w/r/?ikenc=ikuai8-true&fail="+httpurl+"/deck/w/r/?ikenc=ikuai8-false";
		rs.setData(urls);
		rs.setCode(103);
		return rs.toJsonString();
	}
	
	/**
	 * 重新登录
	 * @throws NoSuchAlgorithmException 
	 */
	
	public String getRedirectUrl(HttpSession session,PortalUser user) throws NoSuchAlgorithmException{
		String uamip=(String)session.getAttribute("uamip");
		String uamport=(String)session.getAttribute("uamport");
		String UserName = user.getUserName();
		String Password = user.getPassWord();
//		String newPw = /*SHA256.getUserPassword(UserName, MD5.encode(Password).toLowerCase())*/Password.substring(0, 16);
		String newPw = Password.substring(0, 16);
		String type = (String)session.getAttribute("type");
		String challenge = (String)session.getAttribute("challenge");
		if(type!=null&&"coovachilli".equals(type)){
			byte[] bytes = RadMd5.pack(challenge);
			String newchall = RadMd5.md52((String)session.getAttribute("secret"),bytes);
			byte[] newchallen =RadMd5.pack(newchall);
			String md5pass = RadMd5.md5("\0"+newPw,newchallen);//pc.encodePacket(Password, "testing123");
			return "redirect:"+"http://"+uamip+":"+uamport+"/logon?username="+UserName+"&response="+md5pass+"&userurl=";
		}else if(type!=null&&"ros".equals(type)){
			return  "redirect:"+"http://"+uamip+"/login?dst=www.gonet.cc&popup=true&username="+UserName+"&password="+newPw;
		}else if(type!=null&&"ikuai".equals(type)){
			String usrmac= (String)session.getAttribute("clientMac");
			String usrip =  (String)session.getAttribute("clientIp");
			String urls="http://portal.ikuai8.com/webradius?usrname="+UserName+"&passwd="+newPw+"&usrmac="+usrmac+"&usrip="+usrip
					+"&success="+httpurl+"/deck/w/r/?ikenc=ikuai8-true&fail="+httpurl+"/deck/w/r/?ikenc=ikuai8-false";
			return "redirect:"+urls;
		}
		return null;
	}
	
	/**
	 * @Description  返回ikuai的设备mac
	 * @date 2016年8月9日下午3:55:59
	 * @author guoyingjie
	 * @param ikenc
	 * @return
	 * @throws Exception
	 */
	public static String returnMac(String ikenc) throws Exception{
		String mac = null;
		if ((StringUtils.isNotBlank(ikenc))){
		   String ikcs  = BASE64.decryptBASE64(ikenc);
		   if (StringUtils.isNotBlank(ikcs)) {
			     String[] cs = ikcs.split("&");
			     for (String c : cs) {
			    	 if (c.startsWith("mac=")) {
				        	mac = c.replace("mac=", "");
				        	break;
				        }
				}
		   }
		}
		return mac.trim();
	}
 
	
	//通过radius返回的CODE值来输出错误提示
	public static String getCode(String err,HttpServletRequest request,UserService userService,PortalLogService portalservice){
		ExecuteResult rs = new ExecuteResult();
		if(err==null||err==""||err.length()<1){
			if("coovachilli".equals((String)request.getSession().getAttribute("type"))){
			CloudSiteRouters csr = portalservice.selRouter((String)request.getSession().getAttribute("nasid"));
			if(csr!=null){
				String newip = getWanIp(request);
				if(csr.getIp()==null||!csr.getIp().equals(newip)){
				boolean flag=userService.updateRouIp(csr,newip);
				if(!flag){
					rs.setCode(201);
					rs.setMsg("网关ip错误,请联系运维人员修改网关ip");
					return rs.toJsonString();
				}
			}
			}}
			rs.setCode(209);
			rs.setMsg("认证服务器未响应！");
			return rs.toJsonString();
		}
		String[] codes = err.split("-");
		switch (codes[0]) {
		case "200":
			rs.setCode(205);
			rs.setMsg("用户名或密码错误！");
			break;
		case "201":
			rs.setCode(201);
			rs.setMsg("找不到此场所！");
			break;
		case "202":
			rs.setCode(202);
			rs.setMsg("登录成功,需要充值！");
			break;
		case "203":
			rs.setCode(203);
			rs.setMsg(codes[1]);
			break;
		case "205":
			rs.setCode(205);
			rs.setMsg("用户名或密码错误！");
			break;
		case "206":
			rs.setCode(205);
			rs.setMsg("登陆太频繁！");
			break;
		case "208":
			rs.setCode(205);
			rs.setMsg("账号不存在！");
			break;
		case "304":
			Pattern pattern = Pattern
			.compile(";\\s?(\\S*?\\s?\\S*?)\\s?(Build)?/");
			Matcher matcher = pattern.matcher(codes[1]);
			String model = null;
			boolean boo = matcher.find();
			if (boo) {
				model = matcher.group(1).trim();
				rs.setData(model);
			} else if (codes[1]!=null&&((String) codes[1]).indexOf("iPhone") > -1) {
				rs.setData("iPhone");
			} else {
				rs.setData(codes[1]);
			}
			if("coovachilli".equals((String)request.getSession().getAttribute("type"))){
				rs.setCode(301);
			}else if("ros".equals((String)request.getSession().getAttribute("type"))){
				rs.setCode(302);
			}else if("ikuai".equals((String)request.getSession().getAttribute("type"))){
				rs.setCode(303);
			}
			break;
		default:
			break;
		}
		if(rs.getCode()==202){
			PortalUser proUser = null;
			String have = (String)request.getSession().getAttribute("have");
			if("have".equals(have)){
				 proUser = userService.getUserByEncryptPs((String)request.getSession().getAttribute("UserName"), (String)request.getSession().getAttribute("Password"));
			}else{
				 proUser = userService.getUserPro((String)request.getSession().getAttribute("UserName"),
							MD5.encode((String)request.getSession().getAttribute("Password")).toLowerCase());
			}
			if (proUser == null) {
				rs.setCode(205);
				rs.setMsg("用户名或密码错误！");
				return rs.toJsonString();
			} else {
				if (proUser.getIsStoped() == 1) {
					rs.setCode(201);
					rs.setMsg("用户停用请联系管理员！");
					return rs.toJsonString();
				}
			}
			request.getSession().setAttribute("proUser", proUser);// 添加用户到session
		}else if(rs.getCode()==0){

			CloudSiteRouters csr = portalservice.selRouter((String)request.getSession().getAttribute("nasid"));
			if(csr!=null){
				String newip = getWanIp(request);
				if(csr.getIp()==null||!csr.getIp().equals(newip)){
				boolean flag=userService.updateRouIp(csr,newip);
				if(!flag){
					rs.setCode(201);
					rs.setMsg("网关ip错误,请联系运维人员修改网关ip");
					return rs.toJsonString();
				}
			}
			}
			rs.setCode(209);
			rs.setMsg("认证服务器未响应！");
		}
		return rs.toJsonString();
	}
	
	
	/**
	 *获取请求设备的IP地址
	 * @param request
	 * @return
	 * deck
	 */
	public static String getWanIp(HttpServletRequest request){
		
	        String	wanip = request.getHeader("X-Real-IP");  
	        	  log.error("X-Real-IP-----:"+wanip);
	        if (wanip == null || wanip.length() == 0 || wanip.equalsIgnoreCase("unknown")) {  
	        	wanip = request.getRemoteAddr();  
	        	  log.error("x-forwarded-for-----:"+wanip);
	        }	
	        return wanip;
	}

}
