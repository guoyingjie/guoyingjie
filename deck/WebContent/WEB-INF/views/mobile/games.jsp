<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
	<title></title>
	<link rel="stylesheet" type="text/css" href="${basePath}/mobile/css/newcss/public.css?<%=getTimestamp %>">
	<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0" />
	<meta name="format-detection" content="telephone=no" />
	<meta name="MobileOptimized" content="320"/>
</head>
<body>
<img id="pic" src="${basePath}/mobile/img/newimg/xjol.jpg">
<script type="text/javascript">
	document.getElementById('pic').onclick = function(){
		window.location.href = 'http://ad.gaeamobile.net/?r=api&auth=ujE5Wz7V';
	}
</script>
</body>
</html>