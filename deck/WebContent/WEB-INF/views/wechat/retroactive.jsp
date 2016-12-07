<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="${basePath}/mobile" />
<c:set var="wechatPath" value="http://oss.kdfwifi.net/deck/wechat" />
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<!DOCTYPE html>
<html>
<script type="text/javascript">
	var ctx = "${basePath}";
</script>
<head>
		<meta charset="utf-8" />
		<title>签到</title>
		<link rel="stylesheet" type="text/css" href="${wechatPath}/css/signpublic.css"/>
		<link rel="stylesheet" type="text/css" href="${wechatPath}/css/retroactive.css">
		<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0" />
		<meta name="format-detection" content="telephone=no" />
		<meta name="MobileOptimized" content="320"/>
	</head>
	<body>
		<div class="retro"> 
			<img src="${wechatPath}/img/czPic.png"/>
			<p class="retro-word">充值任意金额到您的账户即可补充签到</p>
			<button class="prepaid">立 即 充 值</button>
			<button class="buy-package">购 买 套 餐</button>
		</div>
		<input id="username" type="hidden" value="${tel}">
		<input id="times" type="hidden" value="${times}">
		<input id="siteId" type="hidden" value="${siteId}">
	</body>
	<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="http://oss.kdfwifi.net/js/idangerous.swiper-2.0.min.js"></script>
<script>
		$(function(){
			$(".prepaid").click(function(){
				window.location.href=ctx+"/weChatPublicNum/replePayBalance?userName="+$("#username").val()+"&times="+$("#times").val()+"&siteId="+$("#siteId").val();
			});
			$(".buy-package").click(function(){
				window.location.href=ctx+"/weChatPublicNum/toPayPage?userName="+$("#username").val()+"&times="+$("#times").val()+"&siteId="+$("#siteId").val();

			});
		})
			
			
			
	
</script>
</html>