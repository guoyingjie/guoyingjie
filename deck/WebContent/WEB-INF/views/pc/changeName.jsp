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
</script>
<head>
	<meta charset="utf-8">
	<title>修改昵称</title>
	<link rel="stylesheet" type="text/css" href="${cssPath}/newcss/changeName.css?<%=getTimestamp %>">
	<link rel="stylesheet" type="text/css" href="${cssPath}/newcss/common.css?<%=getTimestamp %>">
</head>
<body oncontextmenu=self.event.returnValue=false>
<header>欢迎使用<br>${site.site_name}Wi-Fi</header>
<div class="changeName">
	<p class="title">修改昵称</p>
	<p class="change">
		<label for="name">用户名：</label>
		<input id="name" maxlength="10" type="text" placeholder="请输入新的用户名">
		<span class="msg"></span>
		<span class="cha"></span>
	</p>
	<span class="next">保存</span>
</div>
<input type="hidden" value="${proUser.userName}" id="userName">
<input type="hidden" value="${proUser.userNickname}" id="userNickname">
<footer><p class="copyRight">KDFWIFI.COM 2013-2016  联系电话 400-666-0050</p></footer>
</body>
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${pcPath}/js/newjs/changeName.js?<%=getTimestamp %>"></script>

</html>