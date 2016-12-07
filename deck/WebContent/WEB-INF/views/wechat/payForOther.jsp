<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="http://oss.kdfwifi.net/deck/wechat" />
<c:set var="mobilePath1" value="${basePath}/wechat" />
 
<!DOCTYPE html>
<html>
<head>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<meta charset="utf-8">
	<title>帮别人充</title>
	<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
	<meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/common.css?<%=getTimestamp %>">
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/payForOther.css?<%=getTimestamp %>">
<script type="text/javascript">
    var basePath= "${basePath}";
</script>
</head>

<body>
<form>
	<p class="hint">为别人账户充值</p>
	<input class="phone" type="tel" name="" placeholder="请输入需要充值的手机号">
	<button class="subBtn" type="button">下一步</button>
</form>
<div class="altMask">
	<div>
		<span></span>
		<p class="msg"></p>
	</div>
</div>
<input type="hidden" value="${openid}" id="openid">
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${mobilePath1}/js/payForOther.js?<%=getTimestamp %>"></script>
</body>
</html>