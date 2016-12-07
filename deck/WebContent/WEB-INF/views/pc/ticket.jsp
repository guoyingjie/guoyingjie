<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="cssPath" value="http://oss.kdfwifi.net/deck/pc/css" />
<c:set var="jsPath" value="${basePath}/pc/js" />
<c:set var="imgPath" value="http://oss.kdfwifi.net/deck/pc/img" />
<!DOCTYPE html>
<html>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<script type="text/javascript">
var ctx = "${basePath}";
var imgPath="${imgPath}"
</script>
<head>
	<meta charset="utf-8" />
	<title>彩票</title>
	<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0" />
	<meta name="format-detection" content="telephone=no" />
	<meta name="MobileOptimized" content="320"/>
	<link rel="stylesheet" type="text/css" href="${cssPath}/newcss/lottery.css?<%=getTimestamp %>">
	<link rel="stylesheet" type="text/css" href="${cssPath}/newcss/common1.css?<%=getTimestamp %>">
	<link rel="stylesheet" type="text/css" href="${cssPath}/newcss/general.css?<%=getTimestamp %>">
</head>
<body>
		<div class="lottery">
			<img src="${imgPath}/newimg/cai3.png" class="lo-pic2"/>
			<p>双色球</p>
			<p class="time-item"></p>
			<p class="zs-time">赠送日期：<span></span></p>
			<div class="num-show">
				<p class="lo-num">
					<span class="num-title">我的号码</span>
				</p>
				<div class="my_num">
				</div>
			</div>
			<a href="#"><button class="last-btn">兑奖方法</button></a>
		</div>
		<div class="altMask">
			<div>
				<span></span>
				<p class="msg"></p>
			</div>
		</div>
		<div class="msk">
			<div>
				<p></p>
				<button class="iknow">我知道了</button>
			</div>
		</div>
	</body>
<input type="hidden" value="${proUser.userName}" id="username">
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${jsPath}/newjs/ticket.js?<%=getTimestamp %>"></script>

</html>