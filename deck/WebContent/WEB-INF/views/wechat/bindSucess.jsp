<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="${basePath}/mobile" />
<c:set var="wechatPath" value="http://oss.kdfwifi.net/deck/wechat" />
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>绑定成功</title>
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta name="viewport"
	content="width=device-width,height=device-height,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<script type="text/javascript"
	src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${wechatPath}/css/common.css?<%=getTimestamp%>">
<link rel="stylesheet" type="text/css"
	href="${wechatPath}/css/bindingSuccess.css?<%=getTimestamp%>">
<script type="text/javascript">
	var ctx = "${basePath}";
</script>
</head>
<body>
	<p class="success">
		您已绑定成功！<br>
		<button type="button" id="bindsuccess">确定</button>
		<!-- <br> <a href="#" class="payBalance">去充值</a> -->
	</p>
	<input type='hidden' value="${tel}" id="username"/>
		<input type="hidden" value="${openid}" id="openid">
	<input type="hidden" value="${siteId}" id="siteId">
</body>
<script type="text/javascript">
	$(function() {
		var v="4.1.4";
		$("#bindsuccess").click(
		function() {
			window.location.href = ctx + "/weChatPublicNum/toUserDetailsPage?username="+$("#username").val()+"&openId="+$("#openid").val()+"&v="+v;
		});
		var openid=$("#openid").val();
		var username=$("#username").val();
		$(".payBalance").click(function(){
			window.location.href = ctx + "/myselfPay/toPayBalance?openid="+openid+"&userName="+username+"&v="+v;
		});				
	});
</script>
</html>