<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="pcPath" value="http://oss.kdfwifi.net/deck/pc" />
<c:set var="pcPath1" value="${basePath}/pc" />
<c:set var="jsPath" value="${pcPath}/js" />
<%@ page import="java.util.Date"%>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>请充值</title>
<meta name="viewport"
	content="width=device-width,height=device-height,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link rel="stylesheet" type="text/css" href="${pcPath}/css/newcss/common.css?<%=getTimestamp %>">
<link rel="stylesheet" type="text/css" href="${pcPath}/css/newcss/expire.css?<%=getTimestamp %>">
<script type="text/javascript">

var ctx = "${basePath}";
</script>
</head>
<body oncontextmenu=self.event.returnValue=false>
	<header>
		欢迎使用<br>${site.site_name}Wi-Fi
	</header>
	<p class="text">
		您当前账号余额为0，<br>请尽快充值体验${site.site_name}Wi-Fi吧！
	</p>
	<a class="goPay" id="gopay"></a>
	<footer><p class="copyRight">KDFWIFI.COM 2013-2016 联系电话 400-666-0050</p>
	</footer>
	<input type="hidden" value="${proUser.userName}" id="username">
	<input type="hidden" value="${site.id}" id="siteId">
	<input type="hidden" value="${proUser.imageUrl}" id="phoneurl">
	<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pcPath1}/js/newjs/expire.js"></script>
</body>
</html>