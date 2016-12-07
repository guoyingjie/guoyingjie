<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="pcPath" value="${basePath}/pc" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<title>启动认证中</title>
	<link rel="stylesheet" type="text/css" href="http://oss.solarsys.cn/0001/index_page/css/incss.css">
	<script type="text/javascript" src="http://oss.solarsys.cn/0001/index_page/js/jquery.js"></script>
	<script type="text/javascript" src="${basePath}/pc/js/init.js"></script>
</head>
<body>
<div class="ui-div one"></div>
<div class="ui-div two"></div>
<div class="ui-div three"></div>
<div id="count">3</div>
</body>
</html>