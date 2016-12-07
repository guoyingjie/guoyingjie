package com.broadeast.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.broadeast.bean.SitePriceConfigAll;
import com.broadeast.entity.CloudSite;
import com.broadeast.entity.PortalUser;
import com.broadeast.service.impl.SiteService;
import com.broadeast.service.impl.UserService;
import com.broadeast.util.CookieUtil;
import com.broadeast.util.DateUtil;
import com.broadeast.util.ExecuteResult;
import com.broadeast.util.MD5;
import com.broadeast.util.PropertiesParam;
import com.broadeast.util.SHA256;
import com.broadeast.util.toLoginUtil;
import com.wap.alipay.util.httpClient.HttpRequest;

/**
 * 
 * Copyright (c) All Rights Reserved, 2016.
 * 版权所有                  kdf Information Technology Co .,Ltd
 * @Project		deck
 * @File		PortalUserController.java
 * @Date		2016年7月8日 上午11:38:17
 * @Author		cuimiao
 */
@Controller
@RequestMapping("/ProtalUserManage")
public class PortalUserController {
	 
	@Autowired  
	private SiteService siteservice;
	@Autowired  
	private UserService userService;
	/**
	 * @Description  忘记密码
	 * @date 2016年6月6日下午4:25:12
	 * @author guoyingjie
	 * @param telephone
	 * @param phonecode
	 * @param password
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping("doResetPassword")
	@ResponseBody
	public  String doResetPassword(@RequestParam String telephone,@RequestParam(defaultValue="") String phonecode,@RequestParam String password,HttpServletRequest request,HttpSession session,HttpServletResponse response){
		ExecuteResult er = new ExecuteResult();
		//判断用户是否已存在
		PortalUser pt=userService.getPortalUserByTel(telephone);
		if(pt==null){
			er.setCode(300);
			er.setMsg("该号码不存在");
			return er.toJsonString();
		}
		//获取生成的验证码
		String PasswordCode=(String)request.getSession().getAttribute(telephone);
		//判断验证码是否正确
		if(!phonecode.equals(PasswordCode)){
			er.setCode(301);
			er.setMsg("验证码输入错误");
			return er.toJsonString();
		}
		//5分钟
		Long oldTime=(Long)request.getSession().getAttribute("randCodeTime");
		oldTime=oldTime==null?0:oldTime;
		long newTime=new Date().getTime();
		if((newTime-5*60*1000)<=oldTime){//通过
			request.getSession().removeAttribute(telephone);
			request.getSession().removeAttribute("randCodeTime");
		}else{
			er.setCode(302);
			er.setMsg("验证码失效，请重新获取");
			return er.toJsonString();
		}
		
		pt.setPassWord(SHA256.getUserPassword(telephone, MD5.encode(password).toLowerCase()));
		session.setAttribute("proUser",pt);
		
		boolean updataIsSucess=userService.userUpdata(pt);
		if(!updataIsSucess){
			er.setCode(304);
			er.setMsg("服务器拒绝访问，请稍后重试");
			return er.toJsonString();
		}else{
			session.setAttribute("proUser",pt);
			er.setCode(303);
			er.setMsg("修改密码成功");
			return er.toJsonString();
			}
	}
	 
	/** 
	 * 跳转到找回密码页面
	 */
	@RequestMapping("toResetPassword")
	public String toResetPassword(Model m,HttpSession session){
		 
		return "/mobile/zhmm";
	}
	
	/** 
	 * 跳转到pc修改密码页面
	 */
	@RequestMapping("pcToResetPassword")
	public String pcToResetPassword(Model m){
		return "/pc/xgmm";
	}
	/** 
	 * 跳转到个人中心pc修改密码页面
	 */
	@RequestMapping("pcToPersonResetPassword")
	public String pcToPersonResetPassword(Model m){
		return "/pc/personChangePwd";
	}
	/** 
	 * 跳转到pc修改密码页面成功
	 */
	@RequestMapping("pcToResetPwdSuccess")
	public String pcToResetPwdSuccess(Model m){
		return "/pc/pcxgcg";
	}
	/** 
	 * 跳转到pc修改密码页面失败
	 */
	@RequestMapping("pcToResetPwdFailure")
	public String pcToResetPwdFailure(Model m){
		return "/pc/pcxgsb";
	}
	/**
	 * 跳转到注册页面
	 * @param id 
	 * @param mac
	 * @param ip
	 * @param url
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("toRegister")
	public String toRegister(@RequestParam String id,
							@RequestParam String mac,
							@RequestParam String ip,
							@RequestParam String url,
							HttpServletRequest request,
							HttpSession session,
							Model model){
		model.addAttribute("id", id);
		model.addAttribute("mac", mac);
		model.addAttribute("ip", ip);
		model.addAttribute("url", url);
		//String terminalDevice=(String)session.getAttribute("terminalDevice");
		String terminalDevice=request.getHeader("User-Agent");
		String isPc=toLoginUtil.getPcOrMobile(terminalDevice);
		if("1".equals(isPc)){
			return "/pc/register";
		}else{
			return "/mobile/register";
		}
	}
	/**
	 * 注册成功跳转
	 */
	@RequestMapping("/registerSuccess")
	public String registerSuccess(){
		return "pc/registersuccess";
	}   
	 
	 
	
	 /**
		 *注册失败跳转
		 */
		@RequestMapping("/registerFail")
		public String registerFail(){
			return "pc/registerfail";
		}
	/**
	 * 找回密码根据手机号查询用户是否已存在
	 * @param telephone
	 * @return
	 */
	@RequestMapping("checkTel")
	@ResponseBody
	public String checkTel(@RequestParam String telephone){
		String flag="false";
		if(!("".equals(telephone))&&telephone!=null){
			PortalUser pt=userService.getPortalUserByTel(telephone);
			if(pt!=null){
				flag="true";
			} 
		}
		return flag;
	}
	/**
	 *注册根据手机号查询用户是否已存在
	 * @param telephone
	 * @return
	 */
	@RequestMapping(value="check")
	@ResponseBody
	public String check(@RequestParam String telephone){
		String flag="true";
		if(!("".equals(telephone))&&telephone!=null){
			PortalUser pt=userService.getPortalUserByTel(telephone);
			if(pt!=null){
				flag="false";
			} 
		}
		return flag;
	}
	/**
	 * @Description  用户注册时校验归属场所与用户是否存在,
	 * @date 2016年5月24日下午3:42:01
	 * @author guoyingjie
	 * @param telephone--用户名
	 * @param id   某个场所的某个路由器mac
	 * @param sex
	 * @param mac   自己的连接设备mac	
	 * @param ip
	 * @param url
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("doRegisterCheckUser")
	@ResponseBody
	public String doRegisterCheckUser(@RequestParam String telephone,HttpSession session,
			@RequestParam String id,@RequestParam String mac,@RequestParam String ip,@RequestParam String url){
		ExecuteResult er = new ExecuteResult();
		CloudSite site = (CloudSite)session.getAttribute("site");
//		int siteId=siteservice.SiteRouters(id);//获得场所id
//		if(siteId==-1){
//			er.setCode(201);
//			er.setMsg("归属场所不存在");
//			return er.toJsonString();
//		}
//		CloudSite site =siteservice.getMacSite(siteId+"");
		if(site==null){
			er.setCode(201);
			er.setMsg("归属场所不存在");
			return er.toJsonString();
		}
		//判断注册用户是否已存在
		PortalUser pt=userService.getPortalUserByTel(telephone);
		if(pt!=null){
			er.setCode(201);
			er.setMsg("该账号已注册,请直接登录");
			return er.toJsonString();
		}
		er.setCode(200);
		return er.toJsonString();
	}
	/**
	 * 用户注册(PC端和移动端的注册都走这个方法)
	 * @param telephone
	 * @param md5Pwd
	 * @param phonecode
	 * @param siteid
	 * @param id   某个场所的某个路由器mac (废弃参数)
	 * @param mac  自己的连接设备mac	即 终端mac (这个版本并未使用该参数)
	 * @param ip   网关ip
	 * @param url  来路url(废弃参数)
	 * @param request
	 * @return
	 */
	@RequestMapping("doRegister")
	@ResponseBody
	public String doRegister(@RequestParam String telephone,
							@RequestParam String md5Pwd,
//							@RequestParam String siteid,
							@RequestParam String phonecode,
							@RequestParam String id,
							@RequestParam int sex,
							@RequestParam String mac,
							@RequestParam String ip,
//							@RequestParam String url,
							HttpServletRequest request,
							HttpSession session){
		ExecuteResult er = new ExecuteResult();
		//TODO
		String ok = this.check(telephone);
		if("false".equals(ok)){
			er.setCode(201);
			er.setMsg("用户已经注册,请直接登录");
			return er.toJsonString();
		}
		// 5分钟
		Long oldTime = (Long) request.getSession().getAttribute("randCodeTime");
		oldTime = oldTime == null ? 0 : oldTime;
		long newTime = new Date().getTime();
		if ((newTime - 5 * 60 * 1000) <= oldTime) {
			// 通过,不做操作
		} else {
			er.setCode(201);
			er.setMsg("验证码失效，请重新获取");
			return er.toJsonString();
		}
		 // 获取生成的验证码
		String randCode = (String) request.getSession().getAttribute(telephone);
		// 判断验证码是否正确
		if (!phonecode.equals(randCode)) {
			er.setCode(201);
			er.setMsg("验证码输入错误");
			return er.toJsonString();
		}else{
			//验证码验证通过，在session中移除验证码
			request.getSession().removeAttribute(telephone);
			request.getSession().removeAttribute("randCodeTime");
		}
		CloudSite site = new CloudSite();
		if(session != null && session.getAttribute("site") != null){
			site = (CloudSite)session.getAttribute("site");
		}else{
			//若session丢失，则通过公网ip取到site信息继续操作
			
		}
		 
		int siteId=site.getId();//获得场所id
		PortalUser u=new PortalUser();
		u.setUserName(telephone);
		u.setPassWord(SHA256.getUserPassword(telephone, md5Pwd));
		u.setSex(sex);
		boolean registerIsSucess=userService.userRegist(u);
		if(!registerIsSucess){
			er.setCode(201);
			er.setMsg("服务器拒绝访问，请稍后重试");
			return er.toJsonString();
		}else{
			session.setAttribute("proUser",u);
			er.setCode(200);
			er.setMsg("注册成功");
			userService.intoRegisterPortal(siteId, u.getId());
			//siteservice.addTryTimeToUser(siteId,u.getId());
			//添加试用时间
			userService.rSaveTryTime(siteservice.getSiteById(siteId), u);
		}
		return er.toJsonString();
	}
	/**
	 * 用户注册(此时用于pc端的注册)
	 * @param telephone
	 * @param md5Pwd
	 * @param phonecode
	 * @param chargeType 计费规则所属集团(0无归属普通计费规则，1归属电信集团)
	 * @param id   某个场所的某个路由器mac
	 * @param mac  自己的连接设备mac	
	 * @param ip   
	 * @param url
	 * @param request
	 * @return
	 */
	//合并PC端与移动端注册，并取消了查询计费规则
	/*@RequestMapping("doRegister")
	@ResponseBody
	public String doRegister(@RequestParam String telephone,
							@RequestParam String md5Pwd,
							@RequestParam String phonecode,
							@RequestParam(defaultValue="0") int chargeType,
							@RequestParam String id,
							@RequestParam int sex,
							@RequestParam String mac,
							@RequestParam String ip,
							@RequestParam String url,
							HttpServletRequest request,
							HttpSession session){
		ExecuteResult er = new ExecuteResult();
		//根据mac地址获取siteID
		CloudSite site = (CloudSite)session.getAttribute("site");
		int siteid=site.getId();//获得场所id
//		int siteid=siteservice.SiteRouters(id); //mac存在不， 存在返回stiedi
//		if(siteid==-1){
//			er.setCode(201);
//			er.setMsg("设备归属场所不存在");
//			return er.toJsonString();
//		}
//		CloudSite siteMap=siteservice.getMacSite(siteid+"");
		if(site==null){
			er.setCode(201);
			er.setMsg("设备归属场所不存在");
			return er.toJsonString();
		}
		
		//判断注册用户是否已存在
		PortalUser pt=userService.getPortalUserByTel(telephone);
		if(pt!=null){
			er.setCode(201);
			er.setMsg("该号码已被注册,请直接登录");
			return er.toJsonString();
		}
		//获取生成的验证码
		String randCode=(String)request.getSession().getAttribute(telephone);
		//判断验证码是否正确
		if(!phonecode.equals(randCode)){
			er.setCode(201);
			er.setMsg("验证码输入错误");
			return er.toJsonString();
		}
		//5分钟
		Long oldTime=(Long)request.getSession().getAttribute("randCodeTime");
		oldTime=oldTime==null?0:oldTime;
		long newTime=new Date().getTime();
		if((newTime-5*60*1000)<=oldTime){//通过
			request.getSession().removeAttribute(telephone);
			request.getSession().removeAttribute("randCodeTime");
		}else{
			er.setCode(201);
			er.setMsg("验证码失效，请重新获取");
			return er.toJsonString();
		}
		PortalUser u=new PortalUser();
		u.setUserName(telephone);
		u.setPassWord(SHA256.getUserPassword(telephone, md5Pwd));
		u.setSex(sex);
		boolean registerIsSucess=userService.userRegist(u);
		if(!registerIsSucess){
			er.setCode(201);
			er.setMsg("服务器拒绝访问，请稍后重试");
			return er.toJsonString();
		}else{
			session.setAttribute("proUser",u);
			//根据siteId 和userId获取用户的账户信息（三个结果）
			//如果是异常、到期，获取场所的价格配置，返回失败信息
			int siteconfig=siteservice.UserSiteConfig(siteid,u.getId());
			er.setCode(200);
			er.setMsg("注册成功");
			*//****************注册成功后在中间表中插入数据,以便拿到注册的人数***********************************//*
			
			userService.intoRegisterPortal(siteid, u.getId());
			siteservice.addTryTimeToUser(siteid,u.getId());
			userService.rSaveTryTime(site, pt);
			
			*//***************************************************//*
			if(siteconfig==-1 || siteconfig==0){// 需续费
				SitePriceConfigAll siteAll=siteservice.getSitePriceConfigAll(siteid, chargeType);
				if(siteAll.getList().size() == 0){//判断是否存在融合套餐，如果不存在融合套餐则显示普通计费规则
					siteAll=siteservice.getSitePriceConfigAll(siteid, 0);
				}
				siteAll.setSiteInof(site);
				session.setAttribute("siteAll",siteAll);//添加场所所有收费规则session
				er.setData(siteAll);
				er.setCode(200);
				er.setMsg("注册成功,需要充值");
				return er.toJsonString();
			}
			//如果是正常。保存session，返回成功信息
		}
		return er.toJsonString();
	}*/
	
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
	public String changePassword(HttpServletRequest requeset,HttpSession session){
		String terminalDevice = requeset.getHeader("User-Agent");
		String isPc = toLoginUtil.getPcOrMobile(terminalDevice);
		PortalUser proUser=(PortalUser) session.getAttribute("proUser");
		if(null==proUser){
			if("1".equals(isPc)){
				return  "/pc/newPage";
			}else{
				return  "/mobile/newPage";
			}
		}
		if("1".equals(isPc)){
			return "/pc/change";
		}else{
			return "/mobile/change";
		}
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
		String terminalDevice = request.getHeader("User-Agent");
		String isPc=toLoginUtil.getPcOrMobile(terminalDevice);
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
	public String changePsforUser(@RequestParam String password,HttpServletRequest request,HttpSession session){
		String terminalDevice=(String)session.getAttribute("terminalDevice");
		String isPc=toLoginUtil.getPcOrMobile(terminalDevice);
		PortalUser proUser=(PortalUser) session.getAttribute("proUser");
		boolean is = userService.changePsForUser(proUser,password,null);
		if(is){
			return "true";
		}else{
			return "false";
		}
	}
	/**
	 * @Description  忘记密码
	 * @date 2016年5月26日下午4:56:05
	 * @author guoyingjie
	 * @return
	 */
	@RequestMapping("goToForgetPs")
	public String goToForgetPs(HttpServletRequest request,HttpSession session){
		String terminalDevice = request.getHeader("User-Agent");
		String isPc = toLoginUtil.getPcOrMobile(terminalDevice);
		if("1".equals(isPc)){
			return "/pc/forget";
		}else{
			return "/mobile/forget";
		}
	}
	/**
	 * @Description  新版的修改密码
	 * @date 2016年5月26日下午5:51:55
	 * @author guoyingjie
	 * @param telephone
	 * @param phonecode
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("checkRandCode")
	@ResponseBody
	public String checkRandCode(@RequestParam String telephone,@RequestParam(defaultValue="") String phonecode,HttpServletRequest request,HttpSession session){
		ExecuteResult er = new ExecuteResult();
		//获取生成的验证码
		String PasswordCode=(String)request.getSession().getAttribute(telephone);
		//判断验证码是否正确
		if(!phonecode.equals(PasswordCode)){
			er.setCode(201);
			er.setMsg("验证码输入错误");
			return er.toJsonString();
		}
		//5分钟
		Long oldTime=(Long)request.getSession().getAttribute("randCodeTime");
		oldTime=oldTime==null?0:oldTime;
		long newTime=new Date().getTime();
		if((newTime-5*60*1000)<=oldTime){//通过
			request.getSession().removeAttribute(telephone);
			request.getSession().removeAttribute("randCodeTime");
			er.setCode(200);
			return er.toJsonString();
		}else{
			er.setCode(201);
			er.setMsg("验证码失效，请重新获取");
			return er.toJsonString();
		}
	}
	/**
	 * @Description  修改密码
	 * @date 2016年5月26日下午6:23:50
	 * @author guoyingjie
	 * @param telephone
	 * @param password
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("resetPasswordForUser")
	@ResponseBody
	public String resetPasswordForUser(@RequestParam String telephone,@RequestParam String password,HttpServletRequest request,HttpSession session){
		ExecuteResult er = new ExecuteResult();
		//判断用户是否已存在
		PortalUser pt=userService.getPortalUserByTel(telephone);
		pt.setPassWord(SHA256.getUserPassword(telephone, MD5.encode(password).toLowerCase()));
		pt.setUserName(telephone);
		boolean updataIsSucess=userService.userUpdata(pt);
		if(!updataIsSucess){
			er.setCode(201);
			er.setMsg("密码修改失败");
			return er.toJsonString();
		}else{
			er.setCode(200);
			er.setMsg("密码修改成功");
			return er.toJsonString();
		}
	}
	/**
	 * @Description 导航到修改昵称页面
	 * @date 2016年5月27日上午11:05:21
	 * @author guoyingjie
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("goNickNamePage")
	public String goNickNamePage(HttpServletRequest request,HttpSession session){
		String terminalDevice = request.getHeader("User-Agent");
		String isPc = toLoginUtil.getPcOrMobile(terminalDevice);
		PortalUser proUser=(PortalUser) session.getAttribute("proUser");
		if(null==proUser){
			return "redirect:http://www.baidu.com";
		}
		if("1".equals(isPc)){
			return "/pc/changeName";
		}else{
			return "/mobile/changeName";
		}
	}
	/**
	 * @Description  检查昵称是否存在
	 * @date 2016年5月27日下午1:07:23
	 * @author guoyingjie
	 * @param username
	 * @return
	 */
	@RequestMapping("checkUserName")
	@ResponseBody
	public String checkUserName(@RequestParam String username,HttpSession session,@RequestParam String tel){
		ExecuteResult er = new ExecuteResult();
		PortalUser proUser=(PortalUser) session.getAttribute("proUser");
		CloudSite site = (CloudSite)session.getAttribute("site");
		Map<String,Object> map = new HashMap<String,Object>(2);
		if(null==proUser){
			return "redirect:http://www.baidu.com";
		}
		/*boolean is = userService.checkUserName(username);
		if(is){
			er.setCode(201);
			er.setMsg("昵称已存在,请更换");
			return er.toJsonString();
		}else{*/
			boolean ok = userService.changePsForUser(proUser,null,username);
			if(ok){
				er.setCode(200);
				map.put("userName", proUser.getUserName());
				map.put("siteId",site.getId());
				er.setData(map);
				return er.toJsonString();
			}else{
				er.setCode(201);
				er.setMsg("修改失败");
				return er.toJsonString();
			}
		/*}*/
	}
	/**
	 * 用户在个人中心点击用户信息时获取用户接收信息
	 * @param session
	 * @return
	 */
	@RequestMapping("MessageUser")
	@ResponseBody
	public String MessageUser(HttpSession session,@RequestParam String userName,@RequestParam int mealType){
	ExecuteResult ex = new ExecuteResult();
	PortalUser user = (PortalUser) session.getAttribute("proUser");
	if(null==user){
		user=userService.getPortalUserByTel(userName);
		if(user==null){
			
			return "redirect:http://www.baidu.com";
		}
		session.setAttribute("proUser", user);
	}
	List<Map<String, Object>> ls = userService.getmessage(user.getId(),mealType);
		if (ls.size() != 0 && ls != null) {
			ex.setData(ls);
			ex.setCode(200);
			try {
				userService.handleUserMessage(user.getId(), 1);
			} catch (Exception e) {
				
			}
		} else {
			ex.setCode(201);
			ex.setData(DateUtil.format(new Date()));
		}
		return ex.toJsonString();
	}
	/**
	 * 
	 * @Description:查看用户彩票	
	 * @author songyanbiao
	 * @date 2016年9月2日 上午11:20:14
	 * @param
	 * @return
	 */
	@RequestMapping("getLotterPage")
	@ResponseBody
	public String getLotterPage(HttpSession session,@RequestParam String userName){
		ExecuteResult ex = new ExecuteResult();
		PortalUser user = (PortalUser) session.getAttribute("proUser");
		if(null==user){
			user=userService.getPortalUserByTel(userName);
			if(user==null){
				
				return "redirect:http://www.baidu.com";
			}
			session.setAttribute("proUser", user);
		}
		List<Map<String, Object>> ls=userService.getLotteryNums(user.getId());
		if(ls.size()==0){
			ex.setCode(201);
		}else{
			ex.setCode(200);
		}
		ex.setData(ls);
		return ex.toJsonString();
	}	
	/**
	 * 
	 * @Description:跳转彩票列表	
	 * @author songyanbiao
	 * @date 2016年9月2日 上午11:58:23
	 * @param
	 * @return
	 */
	@RequestMapping("getLottery")
	public String getLottery(@RequestParam String userName,@RequestParam int message,HttpSession session){
		ExecuteResult ex = new ExecuteResult();
		session.setAttribute("msgId", message);
		PortalUser user = (PortalUser) session.getAttribute("proUser");
		if(null==user){
			user=userService.getPortalUserByTel(userName);
			if(user==null){
				
				return "redirect:http://www.baidu.com";
			}
			session.setAttribute("proUser", user);
		}
		return "/pc/ticket";
	}
	/**
	 * 
	 * @Description:查看彩票详情	
	 * @author songyanbiao
	 * @date 2016年9月2日 下午12:52:19
	 * @param
	 * @return
	 */
	@RequestMapping("getLoydetail")
	@ResponseBody
	public String getLoydetail(@RequestParam String userName,HttpSession session){
		ExecuteResult ex = new ExecuteResult();
		PortalUser user = (PortalUser) session.getAttribute("proUser");
		if(null==user){
			return "redirect:http://www.baidu.com";
		}
		int messageId=Integer.parseInt(session.getAttribute("msgId")+"");
		List<Map<String,Object>> ls=userService.getLotteryDetail(messageId);
		if(ls.size()==0){
			ex.setCode(201);
		}else{
			ex.setCode(200);
		}
		ex.setData(ls);
		return ex.toJsonString();
	}
	/**
	 * @Description  跳转到信息页面
	 * @date 2016年5月27日下午5:04:36
	 * @author guoyingjie
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("goToMessagePage")
	public String goToMessagePage(HttpServletRequest request,HttpSession session,@RequestParam(defaultValue="0") int handleType){
		String terminalDevice = request.getHeader("User-Agent");
		String isPc=toLoginUtil.getPcOrMobile(terminalDevice);
		PortalUser proUser=(PortalUser) session.getAttribute("proUser");
		String userName=request.getParameter("userName");
		if(null==proUser){
			proUser=userService.getPortalUserByTel(userName);
			
			if(null==proUser){
				return "redirect:http://www.baidu.com";
			}
		}
		request.setAttribute("deckurl", PropertiesParam.DeckUrl);
		String juumpPage="";
		if(handleType==0){
			if(isPc.equals("1")){
				juumpPage= "/pc/msmq";
			}else{
				juumpPage= "/mobile/msmq";
			}
		}else if(handleType==1){
			juumpPage= "/pc/lottery";
		}
		return juumpPage;
	}
	/**
	 * @Description 校验验证码(PC端)
	 * @date 2016年5月26日下午5:51:55
	 * @author cuimiao
	 * @param telephone
	 * @param phonecode
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("checkRandCodeForPC")
	@ResponseBody
	public String checkRandCodeForPC(@RequestParam(defaultValue="") String telephone,@RequestParam(defaultValue="") String phonecode,HttpServletRequest request,HttpSession session){
		ExecuteResult er = new ExecuteResult();
		//获取生成的验证码
		String PasswordCode=(String)request.getSession().getAttribute(telephone);
		//判断验证码是否正确
		if(!phonecode.equals(PasswordCode)){
			er.setCode(201);
			er.setMsg("验证码输入错误");
			return er.toJsonString();
		}
		//5分钟
		Long oldTime=(Long)request.getSession().getAttribute("randCodeTime");
		oldTime=oldTime==null?0:oldTime;
		long newTime=new Date().getTime();
		if((newTime-5*60*1000)<=oldTime){//通过
			request.getSession().removeAttribute(telephone);
			request.getSession().removeAttribute("randCodeTime");
			er.setCode(200);
			return er.toJsonString();
		}else{
			er.setCode(201);
			er.setMsg("验证码失效，请重新获取");
			return er.toJsonString();
		}
	}
	
	/**
	 * @Description  忘记密码时的修改密码(PC)
	 * @date 2016年5月26日下午4:21:36
	 * @author cuimiao
	 * @param password
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("changePsForget")
	@ResponseBody
	public String changePsForget(@RequestParam String password,@RequestParam String telphone ,HttpServletRequest request,HttpSession session){
		boolean is = userService.changePsForUser(telphone,password);
		if(is){
			return "true";
		}else{
			return "false";
		}
	}
	/**
	 * @Description  去用户协议的页面
	 * @date 2016年5月31日下午2:17:55
	 * @author guoyingjie
	 * @param request
	 * @return
	 */
	@RequestMapping("goToTerms")
	public String goToTerms(HttpServletRequest request){
		String terminalDevice = request.getHeader("User-Agent");
		String isPc=toLoginUtil.getPcOrMobile(terminalDevice);
		if(isPc.equals("1")){
			return "/pc/terms";
		}else{
			return "/mobile/terms";
		}
	}
	/**
	 * 
	 *	@Description:pc版上传图片跳转路径
	 *  @author songyanbiao
	 *	@Date 2016年6月3日 
	 *	@param request
	 *	@return
	 */
	@RequestMapping("goToUpLoadPic")
	public String goToUpLoadPic(HttpServletRequest request){
		return "/pc/upPhoto";
	}
	
	/**
	 * 跳转到游戏页面
	 * @Description  一句话描述此方法的功能
	 * @date 2016年9月9日下午2:37:00
	 * @author guoyingjie
	 * @return
	 */
	@RequestMapping("gotogames")
	public String gotogames(){
		return "/mobile/games";
	}
	
}
