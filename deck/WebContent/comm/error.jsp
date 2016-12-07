<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="${basePath}/mobile" />
<c:set var="img" value="${basePath}/mobile/img/newimg/" />
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>出错了</title>
<meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/newcss/common.css">
<script type="text/javascript">
var ctx = "${basePath}";
var img = "${img}";
</script>
<style type="text/css">
.cw{
	width: 100%;
	height: 258px;
	background: url(${img}/404.png) no-repeat center;
	margin-top: 30px;
}
.links{
	text-align: center;
	margin-top: 30px;
	color: #999;
}
.links > a{
	color: #47a4dc;
	text-decoration: underline;
	margin: 0 15px;
}
.rload{
	margin-top: 80px;
	display: block;
	text-align: center;
	color: #47a4dc;
	width: 120px;
	height: 30px;
	padding-top: 50px;
	background: url(${img}/load.png) no-repeat top;
	background-size: 35px;
}
</style>
</head>
<body oncontextmenu=self.event.returnValue=false>
<div class="cw"></div>
<p class="links">您打开的页面不存在正在跳转中···<a></a></p>
</body>
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
$(function(){
	setTimeout(function(){
		window.location.href="http://www.gonet.cc";
	},3000);
});

</script>
</html>