<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="http://oss.kdfwifi.net/deck/wechat" />
 
<!DOCTYPE html>
<html>
<head>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />

    <title>下线</title>
	<link rel="stylesheet" type="text/css" href="${mobilePath}/css/public.css?<%=getTimestamp %>">
	<script type="text/javascript">
  	  var basePath= "${basePath}";
	</script>
</head>
<body>
<header class="header">
    <div class="back"><img src="${mobilePath}/img/back.png"/><span class="font-size-ss backText">返回</span></div>
    <span class="font-size-L">Wi-Fi下线</span>
</header>
    <div class="place font-size-s"><span>所在场所：</span><span class="font-color">${siteName}</span></div>
    <div class="box">
        <img class="cryImg" src="${mobilePath}/img/cry.png"/>
        <p class="mgn-t-50 font-size-L font-color">您的设备暂不支持此功能!</p>
    </div>
</body>
</html>