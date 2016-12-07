<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="http://oss.kdfwifi.net/deck/wechat" />
 
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />

    <meta charset="UTF-8">
    <title>Title</title>
	<link rel="stylesheet" type="text/css" href="${mobilePath}/css/public.css">
</head>
<body>

<header class="header">
    <div class="back"><img src="${mobilePath}/img/back.png"/><span class="font-size-ss backText">返回</span></div>
    <span class="font-size-L">Wi-Fi下线</span>
</header>
<div class="box">
    <img class="cryImg" src="${mobilePath}/img/cry.png"/>
    <p class="mgn-t-50 font-size-L font-color">你当前并未连接Wi-Fi</p>
    <p class="mgn-t-20 font-size-L font-color">无法使用此功能！</p>
</div>
</body>
</html>