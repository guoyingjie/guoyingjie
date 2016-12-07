<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="http://oss.kdfwifi.net/deck/mobile" />
<c:set var="img" value="http://oss.kdfwifi.net/deck/mobile/img/newimg" />
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>走丢了</title>
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
<p class="links">亲,您打开的页面不存在<br>点此<a href="javascript:history.back(-1)">返回</a></p>
<a class="rload" href="javascript: location.replace(location.href);">重新加载</a>
</body>
</html>