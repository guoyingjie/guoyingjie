<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="http://oss.kdfwifi.net/deck/mobile" />
 
<c:set var="mobilePath1" value="${basePath}/mobile" />
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>忘记密码</title>
<meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/newcss/common.css?<%=getTimestamp %>">
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/newcss/forget.css?<%=getTimestamp %>">
<script type="text/javascript">
	var ctx = "${basePath}";
	var id="${siteMac}";
	var gw_id="${gw_id}";
	var mac="${clientMac}";
	var ip="${clientIp}";
	var url="${fromUrl}";
	var fromFw="${fromFw}";
	var gw_address="${gw_address}";
	var gw_port="${gw_port}";
</script>
</head>
<body>
<div class="banner">
      <%-- <c:if test="${!empty bannerUrl}"><img src="${bannerUrl}" width="100%"/></c:if>
     <c:if test="${empty bannerUrl}"><img src="${mobilePath}/img/newimg/banner.jpg" width="100%"></c:if> --%>
</div>
<div class="plan">
	<p class="tiao"></p>
	<span class="round one"></span>
	<span class="round two"></span>
	<span class="round three"></span>
</div>
<form class="forget">
	<input type="hidden" value="${bannerUrl}" id="banner"/>
	<div class="content" style="display: block">
		<p class="title">请输入您的上网账号（手机号）</p>
		<input class="phone" type="tel" value="" name="" placeholder="请输入您的手机号" maxlength="11" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
		<span class="next">下一步</span>
	</div>
	<div class="content">
		<p class="title">请输入您收到的短信验证码</p>
		<p class="verify">
			<input class="code" type="tel" value="" name="" maxlength="4" placeholder="短信验证码" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" >
			<button class="codeBtn" type="button">获取验证码</button>
		</p>
		<span class="next">下一步</span>
	</div>
	<div class="content">
		<p class="title">请您设置一个新密码用来登录</p>
		<input class="pass" type="password" maxlength="16" value="" name="" placeholder="请输入新密码" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
		<input class="inpass" type="password" maxlength="16" value="" name="" placeholder="再次确认您的密码" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
		<span class="next">完成</span>
	</div>
	<p class="toLogin">登录视为您同意<a id="forgetItems">《服务条款》</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript: history.back(-1);">返回登录</a></p>
</form>
<div class="altMask">
	<div>
		<span></span>
		<p class="msg"></p>
	</div>
</div>
<div class="gif" style="display:none"></div>
<footer>
	<p>本Wi-Fi由以下机构联合提供<br>【${site.site_name}/北京宽东方】</p>
	<p class="copyRight">KDFWIFI.COM 2013-2016</p>
</footer>
<input type="hidden" value="${proUser.userName}" id="username">
<input type="hidden" value="${site.id}" id="siteId">
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${mobilePath1}/js/newjs/forget.js?<%=getTimestamp %>"></script>
</body>
</html>