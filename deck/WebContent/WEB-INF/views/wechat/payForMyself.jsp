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
	<title>自己充值</title>
	<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
	<meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<link rel="stylesheet" type="text/css" href="${mobilePath}/css/common.css?<%=getTimestamp %>">
	<link rel="stylesheet" type="text/css" href="${mobilePath}/css/payForOtherNum.css?<%=getTimestamp %>">
	<script type="text/javascript">
	    var basePath= "${basePath}";
	</script>
</head>
<body>
<form>
	<p class="hint">个人账户充值</p>
	<input class="num" type="tel" name="" placeholder="请输入充值金额">
	<button class="subBtn" type="button">开始充值</button>
	<p class="text">充值后的金额可用于购买不同场所下的上网套餐</p>
</form>
<div class="altMask">
	<div>
		<span></span>
		<p class="msg"></p>
	</div>
</div>
<input type="hidden" value="${tel}" id="username">
<input type="hidden" value="${openid}" id="openid">
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${mobilePath1}/js/payForMyself.js?<%=getTimestamp %>"></script>
</body>
</html>