<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="${basePath}/mobile" />
<c:set var="wechatPath" value="${basePath}/wechat" />
<!DOCTYPE html>
<html>
<head>
<title>缴费记录</title>
<meta charset="utf-8">
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/newcss/common.css">
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/newcss/payInfo.css">
<script type="text/javascript">
        var ctx = "${basePath}";
</script>
</head>
<body>
<header>
	<a class="goBack" href="javascript: history.back(-1);"></a>
	缴费记录
	<!-- <a class="goPerson" id="personGo"></a> -->
</header>
<div id="wrapper">
	<div id="scroller">
		<div id="pullDown">
			<span class="pullDownIcon"></span><span class="pullDownLabel">下拉刷新...</span>
		</div>
	 
		<ul class="box" id="thelist">
			 
		</ul>
		<div id="pullUp">
			<span class="pullUpIcon"></span><span class="pullUpLabel">上拉加载更多...</span>
		</div>
	</div>
</div>
<div class="altMask">
	<div>
		<span></span>
		<p class="msg"></p>
	</div>
</div>
<input type="hidden" value="0" id="recordCount">
<input type="hidden" value="${proUser.imageUrl}" id="phoneurl">
<input type="hidden" value="${proUser.id}" id="userId">
<input type="hidden" value="${proUser.userName}" id="userName">
<input type="hidden" value="${site.id}" id="siteId">
<input type="hidden" value="${weixinurl}" id="weixinurl">
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${wechatPath}/js/payInfo.js"></script>
<script type="application/javascript" src="${wechatPath}/js/iscroll.js"></script>
<script type="text/javascript">
$(function(){
	$("#personGo").click(function(){
		var userName= $("#userName").val();
		var siteId = $("#siteId").val();
	    window.location.href= ctx+"/weChatPublicNum/goToPerson?userName="+userName+"&siteId="+siteId;
	});
});
</script>
</body>
</html>