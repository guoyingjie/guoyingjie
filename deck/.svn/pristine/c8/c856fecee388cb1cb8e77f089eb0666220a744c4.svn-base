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
	session.removeAttribute("have");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>剩余时长</title>
<meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/newcss/common.css?<%=getTimestamp %>">
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/newcss/surplus.css?<%=getTimestamp %>">
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${mobilePath1}/js/newjs/surplus.js?<%=getTimestamp %>"></script>
<script type="text/javascript">
	var ctx = "${basePath}";
	var fromFw="${fromFw}";
</script>
</head>
<body>
<header>
	<!-- <a class="msmq on"  id="message"></a> -->
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
<div class="continer">
	<p class="title">剩余时长与流量</p>
	<div class="time">
		<div class="zong">
			<p class="plan"></p>
			<span class="flrigh"><c:if test="${!empty allTimeAndFlow.time}">${allTimeAndFlow.time}</c:if>
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
</div>
<div class='footer'>
	<ul>
		<li id="toPayrecord">缴费记录</li>
		<li id="goToPay">充值</li>
		<li id="goInternal">去上网</li>
	</ul>
</div>
<div class="altMask">
	<div>
		<span></span>
		<p class="msg"></p>
	</div>
</div>
<input type="hidden" value="${proUser.userName}" id="username">
<input type="hidden" value="${bili.ratioTime}" id="ratioTime">
<input type="hidden" value="${bili.ratioFlow}" id="ratioFlow">
<input type="hidden" value="${mess}" id="mess">
<input type="hidden" value="${proUser.imageUrl}" id="phoneurl">
<input type="hidden" value="${proUser.id}" id="userId">
<input type="hidden" value="${site.id}" id="siteId">
<c:if test="${!empty releaseUrl}">
		<script type="text/javascript" src="${releaseUrl}"></script>
		<% pageContext.getSession().setAttribute("releaseUrl", null);%>
</c:if> 
<script type="text/javascript">
$(function(){
//点击去上网
$("#goInternal").click(function(){
//	window.location.href="http://www.gonet.cc/m/";
	window.open("https://www.2345.com/?kdf00001");
});
$("#goToPay").click(function(){
	window.location.href=ctx+"/w/toPay";
});
$("#toPayrecord").click(function(){
	window.location.href=ctx+"/w/toPayRecord";
});

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