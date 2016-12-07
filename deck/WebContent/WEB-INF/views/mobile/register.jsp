<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="http://oss.kdfwifi.net/deck/mobile" />
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>注册</title>
<meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/newcss/common.css?<%=getTimestamp %>">
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/newcss/register.css?<%=getTimestamp %>">
<script type="text/javascript">
var ctx="${basePath}";
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
     <input type="hidden" value="${bannerUrl}" id="banner"/>
</div>
<div class="plan">
	<p class="tiao"></p>
	<span class="round one"></span>
	<span class="round two"></span>
	<span class="round three"></span>
</div>
<form class="register">
	<div class="box">
		<div class="content">
			<p class="title">您的手机号码将作为您的上网账号</p>
			<input type="hidden" value="${bannerUrl}" id="banner"/>
			<input class="phone" type="tel" value="" name="" placeholder="请输入您的手机号" maxlength="11" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
			<p class="toLogin">已有账号？<a onclick="javascript :history.back(-1);">登录</a></p>
			<span class="next">下一步</span>
		</div>
		<div class="content">
			<p class="title">请设置您的密码</p>
			<input class="pass" type="password" value="" maxlength="16" name="" placeholder="请输入数字或字母" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
			<p class="toLogin">已有账号？<a onclick="javascript :history.back(-1);">登录</a></p>
			<input type="hidden" value="" id="pwd"/>
			<span class="next">下一步</span>
		</div>
		<div class="content" style="margin-right: 0">
			<p class="title">请输入您收到的短信验证码</p>
			<p class="verify">
				<input class="code" type="tel" value="" name="" maxlength="4" placeholder="短信验证码" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
				<button class="codeBtn" type="button">获取验证码</button>
			</p>
			<p class="toLogin">已有账号？<a  onclick="javascript :history.back(-1);">登录</a></p>
			<span class="next">完成注册</span>
		</div>
	</div>
</form>
<footer>
	<p>本Wi-Fi由以下机构联合提供<br>【${site.site_name}/北京宽东方】</p>
	<p class="copyRight">KDFWIFI.COM 2013-2016</p>
</footer>
<div class="altMask">
	<div>
		<span></span>
		<p class="msg"></p>
	</div>
</div>
<div class="gif" style="display:none"></div>
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${basePath}/mobile/js/newjs/register.js?<%=getTimestamp %>"></script>
<script type="text/javascript" src="${basePath }/mobile/js/MD5.js?<%=getTimestamp %>"></script>
</body>
</html>
