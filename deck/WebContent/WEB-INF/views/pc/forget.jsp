<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="pcPath" value="http://oss.kdfwifi.net/deck/pc" />
<c:set var="pcPath1" value="${basePath}/pc" />
<!DOCTYPE html>
<html>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<head>
<script type="text/javascript">
	var basePath = "${basePath}";
	var id="${siteMac}";
	var gw_id="${gw_id}";
	var mac="${clientMac}";
	var ip="${clientIp}";
	var url="${fromUrl}";
	var fromFw="${fromFw}";
	var gw_address="${gw_address}";
	var gw_port="${gw_port}";
</script>
<meta charset="utf-8">
	<title>忘记密码</title>
<link rel="stylesheet" type="text/css" href="${pcPath}/css/newcss/common.css?<%=getTimestamp %>">
<link rel="stylesheet" type="text/css" href="${pcPath}/css/newcss/forget.css?<%=getTimestamp %>">
</head>
<body oncontextmenu=self.event.returnValue=false>
<header>欢迎使用<br>${site.site_name }Wi-Fi</header>
<form class="forget">
	<div class="content">
		<p class="title">忘记密码</p>
		<p class="phone"><label for="phone">登录账号：</label><input type="tel" id="phone" placeholder="请输入账号（手机号）完成身份验证" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="11"><span class="msg" id="msgTel"></span></p>
		<p class="code"><label for="code">验证码：</label><button type="button" class="codeBtn">获取验证码</button><input type="text" id="code" placeholder="请输入验证码" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" maxlength="4"><span class="msg" id="rand"></span></p>
		<span class="next">下一步</span>
	</div>
	<div class="content" style="display: none">
		<p class="title">忘记密码</p>
		<p class="pass"><label for="pass">新密码：</label><input type="password" id="pass" placeholder="请输入一个新密码（数字或字母）" maxlength="16"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')"><span class="msg" id="one"></span></p>
		<p class="pass"><label for="pass">确认密码：</label><input type="password" id="inpass" placeholder="请再次输入新密码确认" maxlength="16"  onkeyup="this.value=this.value.replace(/^ +| +$/g,'')"><span class="msg" id="two"></span></p>
		<span class="next">完成</span>
	</div>
</form>
<div class="gif" style="display:none"></div>
<footer><p class="copyRight">KDFWIFI.COM 2013-2016  联系电话 400-666-0050</p></footer>
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${pcPath1}/js/newjs/forget.js?<%=getTimestamp %>"></script>
</body>
</html>