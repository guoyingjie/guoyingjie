<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="pcPath" value="${basePath}/pc" />
<c:set var="imgPath" value="http://oss.solarsys.cn/0001/pc_img" />
<c:set var="cssPath" value="http://oss.kdfwifi.net/deck/pc/css" />
<c:set var="jsPath" value="${pcPath}/js" />
<!DOCTYPE html>
<html>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta content="1" name="keywords" />
<meta content="1" name="description" />
<meta content="yes" name="apple-mobile-web-app-capable"/>
<meta content="yes" name="apple-touch-fullscreen"/>
<meta content="telephone=no" name="format-detection"/>
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
<title>修改失败</title>
<link rel="stylesheet" type="text/css" href="${cssPath}/my.css?<%=getTimestamp %>" />
<link rel="stylesheet" type="text/css" href="${cssPath}/style.css?<%=getTimestamp %>" />
<!-- <script language="javascript" src="js/js.js"></script> -->
</head>

<body class="sbbody">
<div class="sbch"></div>
<span class="cg">密码修改失败</span>
<p class="tz"><span id="sec">5</span>秒后自动跳转</p>
<!-- <a class="djtz" onclick="history.go(-1)">立即跳转</a> -->
<a class="djtz" href="javascript:history.go(-1)">立即跳转</a>
<div class="tyjszc">技术支持|北京宽东方&nbsp;&nbsp;&nbsp;&nbsp;联系电话&nbsp;&nbsp;400-666-0050</div>
<script type="text/javascript">
var ace=document.getElementById("sec");
var sec=ace.innerHTML;
var timer=setInterval(function(){
	sec--;
	if(sec<=0){
		clearInterval(timer);
		history.back();
	}
	ace.innerHTML=sec;
},1000); 
</script>
</body>
</html>
