package com.broadeast.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.broadeast.entity.PortalUser;


/**
 * @ToDoWhat 
 * @author xmm
 */
public class IsLoginInter implements HandlerInterceptor{
	

	@Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
		Exception e = (Exception)arg0.getAttribute("exception");  
		if(e!=null){
			Logger.getLogger(IsLoginInter.class).error("出错啦！", e);
		}
	}

	@Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
	}

	@Override
    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		
		PortalUser user=(PortalUser)arg0.getSession().getAttribute("proUser");
		String servletPath=arg0.getServletPath();
		String params=arg0.getQueryString();
		
		if(!("/toLogin".equals(servletPath)||
				"/w/login/".equals(servletPath)||
				"/w/lottery".equals(servletPath)||
				"/w/saveLottery".equals(servletPath)||
				"/w/kdflottery".equals(servletPath)||
				"/w/deckurl".equals(servletPath)||
				"/w/login".equals(servletPath)||
				"/w/ping/".equals(servletPath)||
				"/w/ping".equals(servletPath)||
				"/w/portal/".equals(servletPath)||
				"/w/portal".equals(servletPath)||
				"/w/changeNumber".equals(servletPath)||
				"/changeNumber".equals(servletPath)||
				"/w/".equals(servletPath)||
				"/w/resultPage".equals(servletPath)||
				"/w".equals(servletPath)||
				"/w/auth/".equals(servletPath)||
				"/w/auth".equals(servletPath)||
				"/w/r/".equals(servletPath)||
				"/toSwitchAccount".equals(servletPath)||
				"/ProuserLogin".equals(servletPath)||
				"/w/ProuserLogin".equals(servletPath)||
				"/ProtalUserManage/toRegister".equals(servletPath)||
				"/ProtalUserManage/check".equals(servletPath)||
				"/ProtalUserManage/checkTel".equals(servletPath)||
				"/ProtalUserManage/goToForgetPs".equals(servletPath)||
				"/ProtalUserManage/doRegister".equals(servletPath)||
				"/ProtalUserManage/toResetPassword".equals(servletPath)||
				"/ProtalUserManage/doResetPassword".equals(servletPath)||
				"/ProtalUserManage/pcToResetPassword".equals(servletPath)||
				"/TelCodeManage/sendTelCode".equals(servletPath)||
				"/TelCodeManage/findPasswordCode".equals(servletPath)||
				"/goToTrialSuccess".equals(servletPath) ||
				"/w/goToTrialSuccess".equals(servletPath) ||
				"/rechargeLog/notify".equals(servletPath) ||
				"/notify/aliPayNotifyCard".equals(servletPath) ||      
				"/quickPayment/checkPayResult".equals(servletPath) ||
				"/quickPayment/payNotify".equals(servletPath) ||
				"/pcQuickPayment/pcPayNotify".equals(servletPath) ||
				"/pcQuickPayment/checkPayResult".equals(servletPath) ||
				"/jump.html".equals(servletPath) ||
				"/ProtalUserManage/goToTerms".equals(servletPath)||
				"/iappay/toPaySuccess".equals(servletPath) ||
				"/iappay/toPayFail".equals(servletPath) ||
				"/iappay/doPostByServlet".equals(servletPath) ||
				"/iappayMobile/doPostByServlet".equals(servletPath) ||
				"/w/jinggao".equals(servletPath) ||
				"/w/mobileJinggao".equals(servletPath) ||
				"/ProtalUserManage/goToForgetPs".equals(servletPath) ||//by:cuimiao
				"/w/goToWherePage".equals(servletPath) ||
				"/w/goToPerson".equals(servletPath) ||
				"/pay/aliPayNotify".equals(servletPath))&&user==null){
			if(params==null||params.length()==0){
				 if (arg0.getHeader("x-requested-with") != null && arg0.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){ //如果是ajax请求响应头会有，x-requested-with  
				    	return true;
				    }else{
				    	
				    	arg1.sendRedirect(arg0.getContextPath()+"/comm/errors.jsp");
				    }
			
				return false;
			}
			arg0.getRequestDispatcher("/toLogin").forward(arg0, arg1);
			return false;
		}
		if("/".equals(servletPath)&&user!=null){
			if(params==null||params.length()==0){
				arg1.sendRedirect(arg0.getContextPath()+"/comm/errors.jsp");
				return false;
			}
			arg0.getRequestDispatcher("/toLogin").forward(arg0, arg1);
			return false;
		}
		
		return true;
    }
	
	

}
