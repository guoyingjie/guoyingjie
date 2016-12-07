<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="http://oss.kdfwifi.net/deck/mobile" />
<c:set var="wechatPath" value="${basePath}/wechat" />
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>修改密码</title>
<meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/newcss/common.css?<%=getTimestamp %>">
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/newcss/change.css?<%=getTimestamp %>">
<script type="text/javascript">
	var ctx = "${basePath}";
</script>
</head>
<body>
<header>
	<a class="goBack" href="javascript: history.back(-1);"></a>
	修改密码
</header>
<form class="forget">
	<div class="content" style="display: block">
		<p class="title">原始登录密码</p>
		<input class="jpass" type="password" value="" name="" placeholder="输入原始登录密码完成身份验证" maxlength="16" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
		<span class="next">下一步</span>
	</div>
	<div class="content">
		<p class="title">请您设置一个新密码用来登录</p>
		<input class="pass" type="password" value="" name="" placeholder="请输入新密码" maxlength="16" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
		<input class="inpass" type="password" value="" name="" placeholder="再次确认您的密码" maxlength="16" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')">
		<span class="next">完成</span>
	</div>
</form>
<footer>
</footer>
<div class="altMask">
	<div>
		<span></span>
		<p class="msg"></p>
	</div>
</div>
<input type="hidden" value="${userName}" id="username">
<input type="hidden" value="${siteId}" id="siteId">
<input type="hidden" value="${openid}" id="openid">
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${wechatPath}/js/weixinchange.js?<%=getTimestamp %>"></script>
</body>
</html>