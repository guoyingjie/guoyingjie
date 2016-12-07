<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="pcPath" value="${basePath}/pc" />
<c:set var="imgPath"
	value="http://oss.solarsys.cn/0001/pc_img" />
<c:set var="cssPath" value="http://oss.kdfwifi.net/deck/pc/css" />
<c:set var="jsPath" value="${pcPath}/js" />
<!DOCTYPE html>
<html>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit">
<title>注册</title>
<link rel="stylesheet" type="text/css"
	href="${cssPath}/newcss/common.css?<%=getTimestamp %>">
<link rel="stylesheet" type="text/css"
	href="${cssPath}/newcss/register.css?<%=getTimestamp %>">
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>

<script type="text/javascript">
	var basePath = "${basePath}";

	var id = "${siteMac}";
	var gw_id = "${gw_id}";
	var mac = "${clientMac}";
	var ip = "${clientIp}";
	var url = "${fromUrl}";
	var fromFw = "${fromFw}";
	var gw_address = "${gw_address}";
	var gw_port = "${gw_port}";
</script>
</head>
<body oncontextmenu=self.event.returnValue=false>
	<header> 欢迎使用<br>${site.site_name }Wi-Fi</header>
	<form class="register">
		<p class="title">您的手机号码将作为上网账号</p>
		<p>
			<input class="phone" type="tel" name="" placeholder="请输入您的手机号码" 
				maxlength="11" onkeyup="this.value=this.value.replace(/\D/g,'')"><span
				class="spaninfo"></span>
		</p>
		<p>
			<input class="pass" type="password" name="" placeholder="请输入您的密码"
				maxlength="16"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')"><span id='passtext'></span>
		</p>
		<p class="verify">
			<input class="code" type="tel" value="" name="" placeholder="请输入验证码"
				onkeyup="this.value=this.value.replace(/[^\d]/g,'')" maxlength="4"><span id="randText"></span>
			<button class="codeBtn" type="button">获取验证码</button>
			<span></span>
		</p>
		<button class="over" type="button">完成注册</button>
		<p class="toLogin">
			已有账号？<a href="javascript:history.go(-1)">登录&gt;</a>
		</p>
	</form>
	<div class="gif" style="display:none"></div>
	<footer> <p class="copyRight">KDFWIFI.COM 2013-2016 联系电话 400-666-0050</p> </footer>
	<script type="text/javascript" src="${jsPath}/MD5.js"></script>
	<script type="text/javascript" src="${jsPath}/newjs/register.js?<%=getTimestamp %>"></script>
</body>
</html>





