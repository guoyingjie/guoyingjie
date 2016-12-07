<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>修改用户名</title>
<meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/newcss/common.css?<%=getTimestamp %>">
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/newcss/changeName.css?<%=getTimestamp %>">
<script type="text/javascript">
var ctx="${basePath}";
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
	<a class="goBack" href="javascript: history.back(-1);"></a>
	修改用户名
	<span class="qd">确定</span>
</header>
<p class="change">
	<label for="name">用户名：</label>
	<input id="name" type="text" placeholder="请输入新的用户名" maxlength="10">
	<span class="cha"></span>
</p>
<div class="altMask">
	<div>
		<span></span>
		<p class="msg"></p>
	</div>
</div>
<input type="hidden" value="${proUser.userName}" id="username">
<input type="hidden" value="${site.id}" id="siteId">
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${mobilePath1}/js/newjs/changeName.js?<%=getTimestamp %>"></script>
</body>
</html>
 