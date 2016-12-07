<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="http://oss.kdfwifi.net/deck/mobile" />
 
<c:set var="mobilePath1" value="${basePath}/mobile" />
<c:set var="cssPath" value="${mobilePath}/css" />
<c:set var="jsPath" value="${mobilePath1}/js" />
<c:set var="imgPath" value="${mobilePath}/img" />
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>实名认证</title>
<link rel="stylesheet" type="text/css" href="${cssPath}/my.css?<%=getTimestamp %>" />
<link rel="stylesheet" type="text/css" href="${cssPath}/style.css?<%=getTimestamp %>" />
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
    var ctx = "${basePath}";
    var id="${siteMac}";
    var gw_id="${gw_id}";
    var mac="${clientMac}";
    var ip="${clientIp}";
    var url="${fromUrl}";
	var terminalDevice="<%=request.getSession().getAttribute("terminalDevice")%>";
	var fromFw = (String)request.getSession().getAttribute("fromFw");
</script>
</head>

<body>
<div class="box">
	<img id="bg" src="${imgPath}/bg.png?<%=getTimestamp %>" width="100%" />
	<p id="ty-to"><span>宽东方极速 Wi-Fi</span></p>
    <p id="ty-dh"></p>
    <p id="ty-cg">认证信息成功提交!</p>
    <!--倒计时5秒自动跳转-->
    <p id="ty-djs"><span>5</span>秒钟后自动跳转</p>
    <!--倒计时5秒自动跳转-->
    <!--单击立即跳转-->
    <p id="ty-lj">立即跳转</p>
    <!--单击立即跳转-->
    <p id="cr">技术支持|北京宽东方  400-666-0050</p>
</div>
<script type="text/javascript" src="${jsPath}/comment.js?<%=getTimestamp %>"></script>
</body>
<c:if test="${!empty releaseUrl}">
		<script type="text/javascript" src="${releaseUrl}"></script>
</c:if>
</html>
