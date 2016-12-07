<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="pcPath" value="${basePath}/pc" />
<c:set var="cssPath" value="http://oss.kdfwifi.net/deck/pc/css" />
<c:set var="jsPath" value="${pcPath}/js" />
<!DOCTYPE html>
<html>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<script type="text/javascript">
var ctx = "${basePath}";
var deckurl = "${deckurl}";
</script>
<head>
	<meta charset="utf-8">
	<title>消息</title>
	<link rel="stylesheet" type="text/css" href="${cssPath}/newcss/msmq.css?<%=getTimestamp %>">
	<link rel="stylesheet" type="text/css" href="${cssPath}/newcss/common.css?<%=getTimestamp %>">
</head>
<body oncontextmenu=self.event.returnValue=false>
<header>欢迎使用<br>${site.site_name}Wi-Fi</header>
<div class="msmq">
	<p class="title">消息</p>
	<div class="content">
		<div class="noMsg"></div>
	</div>
</div>
<input type="hidden" value="${proUser.userName}" id="username">
<footer><p class="copyRight">KDFWIFI.COM 2013-2016  联系电话 400-666-0050</p></footer>
</body>
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${pcPath}/js/newjs/msmq.js?<%=getTimestamp %>"></script>

</html>