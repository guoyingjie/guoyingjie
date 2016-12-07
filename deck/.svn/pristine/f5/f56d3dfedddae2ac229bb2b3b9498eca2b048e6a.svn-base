<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="${basePath}/mobile" />
<c:set var="wechatPath" value="http://oss.kdfwifi.net/deck/wechat" />
<c:set var="wechatPath1" value="${basePath}/wechat" />
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>取消绑定</title>
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta name="viewport"
	content="width=device-width,height=device-height,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link rel="stylesheet" type="text/css" href="${wechatPath}/css/common.css?<%=getTimestamp %>">
<link rel="stylesheet" type="text/css" href="${wechatPath}/css/changePhone.css?<%=getTimestamp %>">
<script type="text/javascript"
	src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	var ctx = "${basePath}";
</script>
</head>
<body>
	<form>
		<p class="hint">您将解除宽东方账号${proUser.userName}与该微信号的绑定</p>
		<p class="code">
			<input class="codeNum" type="tel" name="" placeholder="请输入验证码"
				maxlength="4" onkeyup="this.value=this.value.replace(/\D/g,'')"
				onafterpaste="this.value=this.value.replace(/\D/g,'')">
			<button class="codeBtn" type="button">获取验证码</button>
		</p>
		<button class="subBtn" type="button">取消绑定</button>
	</form>
	<div class="altMask">
	<div>
		<span></span>
		<p class="msg"></p>
	</div>
</div>
	<input type="hidden" value="${userName}" id="username"/>
	<input type="hidden" value="${siteId}" id="siteId">
	<input type="hidden" value="${openid}" id="openid">
	<script type="text/javascript"
		src="${wechatPath1}/js/cancelBind.js?<%=getTimestamp %>"></script>
</body>
</html>