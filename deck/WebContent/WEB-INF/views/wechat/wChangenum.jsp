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
<title>账号</title>
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta name="viewport"
	content="width=device-width,height=device-height,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link rel="stylesheet" type="text/css" href="${wechatPath}/css/common.css">
<link rel="stylesheet" type="text/css" href="${wechatPath}/css/accountList.css">
<script type="text/javascript"
	src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	var ctx = "${basePath}";
</script>
</head>

<body>
	<div class="links">
		<a id="exctBind">取消绑定账号</a> <a id="changeBind">更换绑定账号</a>
	</div>
	<input type="hidden" value="${userName}" id="username"/>
<input type="hidden" value="${siteId}" id="siteId">
<input type="hidden" value="${openid}" id="openid">
</body>
<script type="text/javascript">
	var num=Math.random();
	$(function() {
		var siteId=$("#siteId").val();
		var openid=$("#openid").val();
		var username=$("#username").val();
		$("#exctBind").click(
				function() {
					window.location.href = ctx + "/weChatPublicNum/cancelBind?siteId="+siteId+"&openId="+openid+"&userName="+username+"&num="+num;
				});

		$("#changeBind").click(
				function() {
					window.location.href = ctx + "/weChatPublicNum/changeBind?siteId="+siteId+"&openId="+openid+"&userName="+username+"&num="+num;
				});
	});
</script>
</html>