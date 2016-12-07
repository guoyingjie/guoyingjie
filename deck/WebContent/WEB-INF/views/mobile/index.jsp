<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ page import="java.util.Date" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="http://oss.kdfwifi.net/deck/mobile" />
 
<c:set var="mobilePath1" value="${basePath}/mobile" />
<c:set var="imgPath" value="http://oss.solarsys.cn/0001/mobile_img" />
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta content="1" name="keywords" />
<meta content="1" name="description" />
<meta content="yes" name="apple-mobile-web-app-capable"/>
<meta content="yes" name="apple-touch-fullscreen"/>
<meta content="telephone=no" name="format-detection"/>
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
<title>WELCOME</title>
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/my.css?<%=getTimestamp %>" />
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/style.css?<%=getTimestamp %>" />

<script type="text/javascript">
	var basePath="${basePath}";
	var imgPath="${imgPath}";
	var id="${siteMac}";
	var mac="${mac}";
	var ip="${ip}";
	var url="${url}";
	var fromFw="${fromFw}";
	var gw_address="${gw_address}";
	var gw_port="${gw_port}";
</script>
</head>
<body>
<div class="box" id="box">
	<img id="bg" src="${imgPath}/box_bj.jpg" width="100%" />
	<span id="we">WELCOME</span>
    <!--免费体验按钮-->
    <p id="mf"><span class="sybtn"><input id="ty"  type="button" value="免费体验" /></span></p>
    <span id="fx">放心Wi-Fi，舒适生活</span>
    <!--注册登录按钮-->
    <div id="btn"><input class="btn1" id="registerBtn" type="button"  value="注&nbsp;&nbsp;册" />
    
    <input class="btn2" id="toLogin"  type="button" value="登&nbsp;&nbsp;录" /></div>
</div>
</body>
<script language="javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${mobilePath1 }/js/index.js?<%=getTimestamp %>"></script>
</html>