<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="wechat" value="http://oss.kdfwifi.net/deck/wechat" />
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />

    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="${ wechat}/css/sucAnderr.css">

</head>
<body>

<div class="text-center mgn-top">
    <img class="img-W-100" src="${ wechat}/img/err.png">

    <p class="red mt-20" style="font-size: 20px">注册失败!</p>
    <p class="f-size-12 stable mt-20">网络不给力</p>
</div>

<p class="bottom">Copyright ©2016 KDF inc. All Rights Reserved.</p>

</body>
</html>