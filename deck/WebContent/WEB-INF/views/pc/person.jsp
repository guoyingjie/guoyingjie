<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="pcPath" value="${basePath}/pc" />
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
	<title>个人中心</title>
	<link rel="stylesheet" type="text/css" href="${cssPath}/newcss/person.css?<%=getTimestamp %>">
	<link rel="stylesheet" type="text/css" href="${cssPath}/newcss/common.css?<%=getTimestamp %>">
</head>
<script type="text/javascript">
var ctx = "${basePath}";
var id="${siteMac}";
var gw_id="${gw_id}";
var mac="${clientMac}";
var ip="${clientIp}";
var url="${fromUrl}";
var fromFw="${fromFw}";
var gw_address="${gw_address}";
var gw_port="${gw_port}";
</script>
<body oncontextmenu=self.event.returnValue=false>
<header>欢迎使用<br>${site.site_name }Wi-Fi</header>
<div class="box">
	<div class="left">
		<p class="title" onclick="changeName()">
		<c:if test="${!empty proUser.userNickname}">${proUser.userNickname}</c:if>
		<c:if test="${empty proUser.userNickname}">未命名用户</c:if>
		</p>
		<span class="photo"></span>
		<p class="phone">${proUser.userName }</p>
		<div class="lvjs">
			<p>等级<br>LV<span>${proUser.userGrade }</span></p>
			<p>积分<br><span>${proUser.userIntegral }</span></p>
		</div>
	</div>
	<div class="right">
		<p class="title">个性功能</p>
		<ul>
			<li><span class="xx"></span>消息<a href="javaScript: message();">查看</a></li>
			<li><span class="xg"></span>修改密码<a href="javaScript: changePwd();">点击修改</a></li>
			<li><span class="qh"></span>切换账号<a href="javascript: changeNumber();">切换</a></li>
			<li><span class="lb"></span>我的礼包<a href="##">查看</a></li>
			<li><span class="cp"></span>我的彩票<a href="javascript: lottery();">查看</a></li>
		</ul>
	</div>
</div>
<input type="hidden" value="${proUser.userName}" id="username">
<input type="hidden" value="${proUser.imageUrl}" id="phoneurl">
<footer><p class="copyRight">KDFWIFI.COM 2013-2016  联系电话 400-666-0050</p></footer></body>
<script type="text/javascript" src=" http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${pcPath}/js/newjs/person.js?<%=getTimestamp %>"></script>

</html>