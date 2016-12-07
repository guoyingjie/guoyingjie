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
<script type="text/javascript">
	var ctx = "${basePath}";
</script>
<head>
		<meta charset="utf-8" />
		<title>签到</title>
		<link rel="stylesheet" type="text/css" href="${wechatPath}/css/signpublic.css"/>
		<link rel="stylesheet" type="text/css" href="${wechatPath}/css/lottery.css">
		<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0" />
		<meta name="format-detection" content="telephone=no" />
		<meta name="MobileOptimized" content="320"/>
	</head>
	<body>
		<div class="lp-show"><img src="http://oss.kdfwifi.net/school_pic/combanner2.jpg"/></div>
		<div class="jl-word">
			<p class="lp-word">恭喜您获得一次抽奖机会</p>
			<p class="lp-word1"></p>
		</div>
		<div class="lp-card">
			<div class="lp-cardShow"><img src="${wechatPath}/img/lpPic.png"/></div>
			<div class="lp-cardShow1"><img src="${wechatPath}/img/w-lott.png"/><span></span></div>
		</div>
		<div class="lp-card">
			<div class="lp-cardShow"><img src="${wechatPath}/img/lpPic.png"/></div>
			<div class="lp-cardShow1"><img src="${wechatPath}/img/w-lott.png"/><span></span></div>
		</div>
		<div class="lp-card">
			<div class="lp-cardShow"><img src="${wechatPath}/img/lpPic.png"/></div>
			<div class="lp-cardShow1"><img src="${wechatPath}/img/w-lott.png"/><span></span></div>
		</div>
		<div class="lp-card">
			<div class="lp-cardShow"><img src="${wechatPath}/img/lpPic.png"/></div>
			<div class="lp-cardShow1"><img src="${wechatPath}/img/w-lott.png"/><span></span></div>
		</div>
		<input id="lsid" type="hidden" value="${usid }">
	</body>
		<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${wechatPath1}/js/lottery.js"></script>
</html>