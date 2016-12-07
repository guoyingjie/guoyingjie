<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="http://oss.kdfwifi.net/deck/mobile" />
<c:set var="imgPath" value="http://oss.solarsys.cn/0001/mobile_img" />
<!DOCTYPE html>
<html>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<head>
<meta charset="utf-8">
<meta content="1" name="keywords" />
<meta content="1" name="description" />
<meta content="yes" name="apple-mobile-web-app-capable"/>
<meta content="yes" name="apple-touch-fullscreen"/>
<meta content="telephone=no" name="format-detection"/>
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
<title>试用成功</title>
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/my.css?<%=getTimestamp %>" />
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/style.css?<%=getTimestamp %>" />
<%-- <script language="javascript" src="${mobilePath}/js/js.js"></script> --%>
<script type="text/javascript">
var basePath="${basePath}";
var imgPath="${imgPath}";

function goJump(){
	window.location.href="http://www.gonet.cc/m/";
}

window.onload=function(){
	var tys=document.getElementById('ty-djs');//获取倒计时的ID
	var sp=tys.getElementsByTagName('span')[0];//获取倒计时中的span
	var spt=sp.innerHTML;//获取倒计时中span内容
	//让span里的内容每秒减一
	var timer=setInterval(function(){
		spt--;
		if(spt<=0){
			clearInterval(timer);
			window.location.href="http://www.gonet.cc/m/";
		}
		sp.innerHTML=spt;
	},1000);
}
</script>
</head>

<body>
<div class="box">
	<img id="bg" src="${imgPath}/bg.png" width="100%" />
	<p id="ty-to"><span>宽东方极速 Wi-Fi</span></p>
    <p id="ty-dh"></p>
    <p id="ty-mf">免费体验一小时</p>
    <p id="ty-cg">成功开通!</p>
    <!--倒计时5面自动跳转-->
    <p id="ty-djs"><span>5</span>秒钟后自动跳转</p>
    <!--倒计时5面自动跳转-->
    <!--单击立即跳转-->
    <p id="ty-lj" onclick="goJump();">立即跳转</p>
    <!--单击立即跳转-->
    <!--页面底部-->
    <p id="cr">技术支持|北京宽东方  400-666-0050</p>
    <!--页面底部-->
</div>
<c:if test="${!empty releaseTrialUrl}">
		<script type="text/javascript" src="${releaseTrialUrl}"></script>
</c:if>
</body>
</html>
