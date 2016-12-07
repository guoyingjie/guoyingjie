<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath1" value="${basePath}/mobile" />
<c:set var="mobilePath" value="http://oss.kdfwifi.net/deck/mobile" />
<!DOCTYPE html>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<html>
<head>
<meta charset="utf-8">
<title>消息中心</title>
<meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/newcss/common.css?<%=getTimestamp %>">
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/newcss/msmq.css">
<script type="text/javascript">
		var ctx="${basePath}";
		var id="${id}";
		var gw_id="${gw_id}";
		var mac="${mac}";
		var ip="${ip}";
		var url="${url}";
		var fromFw="${fromFw}";
		var gw_address="${gw_address}";
		var gw_port="${gw_port}";
		var deckurl = "${deckurl}";
</script>
</head>
<body>
<header>
	<a class="goBack" href="javascript: history.back(-1);"></a>
	消息中心
</header>
<!--<div class="msmq">
 	<span class="logo"></span>
	<div class="msmqBox">
		<span class="jj"></span>
		<p class="title"><span class="xt">系统消息：</span>05-13 11:59</p>
		<p class="nr">工程师正在前往您的公司为您一键报修工程师正在前往您的公司为您一键报修工程师正在前往您的公司为您一键报修工程师正在前往您的公司为您<a href="###">一键报修</a>工程师正在前往您的公司为您一键报修工程师正在前往您的公司为您一键报修工程师正在前往您的公司为您一键报修</p>
	</div>
</div>
<div class="msmq">
	<span class="logo"></span>
	<div class="msmqBox">
		<span class="jj"></span>
		<p class="title"><span class="xt">系统消息：</span>05-13 11:59</p>
		<p class="nr">工程师正在前往您的公司为您一键报修工程师正在前往您的公司为您一键报修工程师正在前往您的公司为您一键报修工程师正在前往您的公司为您一键报修工程师正在前往您的公司为您一键报修工程师正在前往您的公司为您一键报修工程师正在前往您的公司为您一键报修</p>
	</div>
</div>
<div class="msmq">
	<span class="logo"></span>
	<div class="msmqBox">
		<span class="jj"></span>
		<p class="title"><span class="xt">系统消息：</span>05-13 11:59</p>
		<p class="nr">工程师正在前往您的公司为您一键报修工程师正在前往您的公司为您一键报修工程师正在前往您的公司为您一键报修工程师正在前往您的公司为您一键报修工程师正在前往您的公司为您一键报修工程师正在前往您的公司为您一键报修工程师正在前往您的公司为您一键报修</p>
	</div>
</div> -->
<div class="noMsg"></div>
<div class="altMask">
	<div>
		<span></span>
		<p class="msg"></p>
	</div>
</div>
<input type="hidden" value="${proUser.userName}" id="username">
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${mobilePath1}/js/newjs/msmq.js"></script>
</body>
</html>
 
