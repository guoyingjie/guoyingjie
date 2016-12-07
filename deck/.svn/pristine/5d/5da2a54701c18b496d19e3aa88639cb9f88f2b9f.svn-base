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
<title>请充值</title>
<meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/newcss/common.css?<%=getTimestamp %>">
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/newcss/expire.css?<%=getTimestamp %>">
<script type="text/javascript">
var ctx="${basePath}";
var imgPath="${imgPath}";
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
<header>
	<!-- <a class="msmq on" id="message"></a> -->
	<div class="content">
		<span class="photo"></span>
		<p>
			<span class="name"><c:if test="${ !empty proUser.userNickname}">${proUser.userNickname}</c:if>
			<c:if test="${empty proUser.userNickname}">未命名用户</c:if></span><br>
			<span class="phone"></span>
		</p>
		<a class="person"  id="personGo"></a>
	</div>
</header>
<p class="text">您当前账号余额为0，<br>请尽快充值体验${site.site_name}Wi-Fi吧！</p>
<a class="goPay" ></a>
<div class="altMask">
	<div>
		<span></span>
		<p class="msg"></p>
	</div>
</div>
<input type="hidden" value="${proUser.userName}" id="username">
<input type="hidden" value="${mess}" id="mess">
<input type="hidden" value="${site.id}" id="siteId">
<input type="hidden" value="${proUser.imageUrl}" id="phoneurl">
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${mobilePath1}/js/newjs/expire.js?<%=getTimestamp %>"></script>
</body>
</html>