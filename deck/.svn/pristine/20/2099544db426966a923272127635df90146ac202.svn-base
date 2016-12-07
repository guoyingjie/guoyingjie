<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="ctx" value="http://oss.kdfwifi.net/deck" />
<c:set var="ctx1" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>缴费成功</title>
<meta name="viewport"
	content="width=device-width,height=device-height,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link href="${ctx}/img/img/favicon.ico" type="image/x-icon" rel="shortcut icon">
<link rel="stylesheet" type="text/css" href="${ctx}/mobile/css/zhifu.css">
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/mobile/js/jweixin-1.0.0.js"></script>
</head>
<script type="text/javascript">
	var ctx = "${ctx1}";
</script>
<body>
	<header class="header">宽东方Wi-Fi帐户中心</header>
	<div class="cont">
		<h1 style="color: #44cb32;">恭喜您，支付成功</h1>
		<span class="succe"></span>
		<p class="info"></p>
	</div>
	<div class="remind">
		<span>温馨提示：</span>
		<p>1、请勿将您的帐号借于他人使用，会有一定机率的帐户锁定风险；</p>
		<p>2、您在上网过程中遇到的任何问题，都可关注宽东方WiFi服务公众号“宽未来”获得帮助；</p>
		<p>3、由于ios系统原因，个别iphone用户连接WiFi后不会自动弹跳认证页面，请重启手机即可解决。</p>
	</div>
<input type="hidden" id="userId" value="${userId}">
<input type="hidden" id="siteId" value="${siteId}">
</body>
<script type="text/javascript">
		$(function(){
				window.location.href=ctx+"/weChatPublicNum/doSign?userId="+$("#userId").val()+"&siteId="+$("#siteId").val();
		});
</script>
</html>