<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="http://oss.kdfwifi.net/deck/mobile" />
 
<c:set var="mobilePath1" value="${basePath}/mobile" />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>KDF登录</title>
		<link rel="stylesheet" href="${mobilePath}/css/lottery/common.css"/>
		<link rel="stylesheet" type="text/css" href="${mobilePath}/css/lottery/general.css">
		<link rel="stylesheet" href="${mobilePath}/css/lottery/lottery.css"/>
		<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0" />
		<meta name="format-detection" content="telephone=no" />
		<meta name="MobileOptimized" content="320"/>
	<script type="text/javascript">
		var ctx = "${basePath}";
		var username = "${username}";
		var usermsg = "${usermsg}";
	</script>
	</head>
	<body>
		<div class="lottery">
			<img src="${mobilePath}/img/lottery/error.png" class="lo-pic1"/>
			<p class="chose-true">选票失败！</p>
			<div class="num-show">
				<p class="lo-num">
					<span class="num-title">我的号码</span>
				</p>
				<div class="my_num" data-zj="${select }">
					<span class="red loy_num on">${lotterySuccessRed_1}</span>
					<span class="red loy_num on">${lotterySuccessRed_2}</span>
					<span class="red loy_num on">${lotterySuccessRed_3}</span>
					<span class="red loy_num on">${lotterySuccessRed_4}</span>
					<span class="red loy_num on">${lotterySuccessRed_5}</span>
					<span class="red loy_num on">${lotterySuccessRed_6}</span>
					<span class="blue loy_num on">${lotterySuccessBlue_1}</span>
				</div>
			</div>
			<button class="last-btn">重新提交</button>
		</div>
	</body>
	<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${mobilePath1}/js/lottery/lottery.js"></script>
</html>