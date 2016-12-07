<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="pcPath" value="http://oss.kdfwifi.net/deck/pc" />
<c:set var="pcPath1" value="${basePath}/pc" />
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
	session.removeAttribute("have");
%>

<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
	var basePath = "${basePath}";
	var id="${siteMac}";
	var gw_id="${gw_id}";
	var mac="${clientMac}";
	var ip="${clientIp}";
	var url="${fromUrl}";
	var fromFw="${fromFw}";
	var gw_address="${gw_address}";
	var gw_port="${gw_port}";
</script>
<meta charset="utf-8">
	<title>剩余时长</title>
<link rel="stylesheet" type="text/css" href="${pcPath }/css/newcss/common.css?<%=getTimestamp %>">
<link rel="stylesheet" type="text/css" href="${pcPath }/css/newcss/surplus.css?<%=getTimestamp %>">
</head>
<body oncontextmenu=self.event.returnValue=false>
<header>欢迎使用<br>${site.site_name }Wi-Fi</header>
<div class="box">
	<div class="left">
		<p class="title"  id="goToPerseon">基本信息</p>
		<span class="photo"></span>
		<p class="name"><c:if test="${ !empty proUser.userNickname}">${proUser.userNickname}</c:if>
		<c:if test="${empty proUser.userNickname}">未命名用户</c:if></p>
		<p class="phone">${proUser.userName }</p>
	</div>
	<div class="right">
		<p class="title">剩余时长与流量</p>
		<div class="time">
			<div class="zong">
				<p class="plan"></p>
				<span class="flrigh">
				<c:if test="${!empty allTimeAndFlow.time}">${allTimeAndFlow.time}</c:if>
								<c:if test="${empty allTimeAndFlow.time}">正在加载数据···</c:if>
				</span>
				<span class="cente">
				<c:if test="${empty SyTimeAndFlow.time}">正在加载数据···</c:if>
				<c:if test="${!empty SyTimeAndFlow.time}">${SyTimeAndFlow.time}</c:if>
				</span>
			</div>
		</div>
		<div class="gprs">
			<div class="zong">
				<p class="plan"></p>
				<span class="flrigh">
				<c:if test="${empty allTimeAndFlow.flowstr}">正在加载数据···</c:if>
				<c:if test="${!empty allTimeAndFlow.flowstr}">${allTimeAndFlow.flowstr}</c:if>
				</span>
				<span class="cente">
				<c:if test="${empty SyTimeAndFlow.flowstr}">正在加载数据···</c:if>
				<c:if test="${!empty SyTimeAndFlow.flowstr}">${SyTimeAndFlow.flowstr}</c:if>
				</span>
			</div>	
		</div>
		<ul class="links">
			<li id="toPayrecord">缴费记录</li>
			<li id="goToPay">充值</li>
			<li id="goInternal">去上网</li>
		</ul>
	</div>
</div>
<input type="hidden" value="${proUser.userName}" id="username">
<input type="hidden" value="${bili.ratioTime}" id="ratioTime">
<input type="hidden" value="${bili.ratioFlow}" id="ratioFlow">
<input type="hidden" value="${mess}" id="mess">
<input type="hidden" value="${proUser.imageUrl}" id="phoneurl">
<input type="hidden" value="${proUser.id}" id="userId">
<input type="hidden" value="${site.id}" id="siteId">
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${pcPath1 }/js/newjs/surplus.js?<%=getTimestamp %>"></script>
<c:if test="${!empty releaseUrl}">
		<script type="text/javascript" src="${releaseUrl}"></script>
		<% session.removeAttribute("releaseUrl");%>
</c:if>
<script type="text/javascript">
$(function(){

	 var time = $(".time").html();
	 var flow = $('.gprs').html();
	 var timer = $("#ratioTime").val();
	 var flowr = $("#ratioFlow").val();
	 if(timer=='0'&&flow!='0'){
		 $(".time").replaceWith($('.gprs'));
		 $('.gprs').after("<div class='time'>"+time+"</div>");
	 }
});

</script>
</body>
</html>