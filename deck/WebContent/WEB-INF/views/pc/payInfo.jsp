<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="pcPath" value="http://oss.kdfwifi.net/deck/pc" />
<c:set var="pcPath1" value="${basePath}/pc" />
<c:set var="jsPath" value="${pcPath}/js" />
<%@ page import="java.util.Date" %>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<!DOCTYPE html>
<html>





<head>
<meta charset="utf-8">
	<title>缴费记录</title>
<link rel="stylesheet" type="text/css" href="${pcPath}/css/newcss/common.css">
<link rel="stylesheet" type="text/css" href="${pcPath}/css/newcss/payInfo.css">
<script type="text/javascript">
        var ctx = "${basePath}";
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
<body oncontextmenu=self.event.returnValue=false>
<header>欢迎使用<br>${site.site_name}Wi-Fi</header>
<div class="payInfo">
	<p class="title">缴费记录</p>
	<div class="content">
		<ul class="list">
			<li class="weiX">
				<p><span class="date">今天</span><span class="time">8：12</span></p>
				<p><span class="name">两天时长上网包</span><span class="price">30.00</span></p>
			</li>
			<li class="aliPay">
				<p><span class="date">今天</span><span class="time">8：12</span></p>
				<p><span class="name">两天时长上网包</span><span class="price">30.00</span></p>
			</li>
			<li class="card">
				<p><span class="date">今天</span><span class="time">8：12</span></p>
				<p><span class="name">两天时长上网包</span><span class="price">30.00</span></p>
			</li>
		</ul>
	</div>
</div>
<input type="hidden" value="${proUser.id}" id="userId">
<input type="hidden" value="${proUser.userName}" id="userName">
<input type="hidden" value="${site.id}" id="siteId">
<footer>登录视为您同意<a id="terms">《服务条款》</a><p class="copyRight">KDFWIFI.COM 2013-2016  联系电话 400-666-0050</p></footer>
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${pcPath1}/js/newjs/payInfo.js"></script>
<script type="text/javascript">
$(function(){
	$("#terms").click(function(){
	    window.location.href= ctx+"/ProtalUserManage/goToTerms";
	});
});
</script>
</body>
</html>
