<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="http://oss.kdfwifi.net/deck/mobile" />
 
<c:set var="mobilePath1" value="${basePath}/mobile" />
<!DOCTYPE html>
<html>
<head>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<meta charset="utf-8">
<title>帐户查询及缴费服务</title>
<meta name="viewport"
	content="width=device-width,height=device-height,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link href="${mobilePath}/img/img/favicon.ico" type="image/x-icon"
	rel="shortcut icon">
<link rel="stylesheet" type="text/css"
	href="${mobilePath}/css/zhifu.css?<%=getTimestamp %>">
<script type="text/javascript">
	var basePath = "${basePath}";
</script>
</head>
<body>
	<header class="header">宽东方Wi-Fi帐户中心</header>
	<div class="mod">
		<h1>帐户充值</h1>
		<div class="siteName">
			<i></i><i class="xl"></i>
		</div>

		<div class="select">
			<ul>
				<li>账户<span class="phone"></span></li>
				<li class="typeSelect" data-money=''>类型<i></i> <span
					class="type"> </span></li>
				<li style="border-bottom: none;">数量<input type="tel" value="1"
					id="num"
					onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
					onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"></li>
			</ul>
			<p class="sum">
				合计<span>元</span>
			</p>
		</div>
		<button id="goPay" class="btn" type="button">
			确定支付<span></span>元
		</button>
	</div>
	<footer>KDFWIFI.COM 2013-2016</footer>
	<div class="mask">
		<div class="popup1">
			请选择场所 <span class="close"></span>
			<ul class="chargeList1">

			</ul>
		</div>
		<div class="popup">
			选择上网类型 <span class="close"></span>
			<ul class="chargeList">

			</ul>
		</div>
	</div>
	
	<script type="text/javascript"
		src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript"
		src="${mobilePath1}/js/pay.js?<%=getTimestamp %>"></script>

</body>
</html>