<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="http://oss.kdfwifi.net/deck/mobile" />
<c:set var="mobilePath1" value="${basePath}/mobile" />
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>个人中心</title>
<meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/newcss/common.css?<%=getTimestamp %>">
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/newcss/person.css?<%=getTimestamp %>">
<script type="text/javascript">
var ctx="${basePath}";
var id="${siteMac}";
var gw_id="${gw_id}";
var mac="${clientMac}";
var ip="${clientIp}";
var url="${fromUrl}";
var fromFw="${fromFw}";
var gw_address="${gw_address}";
var gw_port="${gw_port}";
</script>
</head>
<body>
<header>
	<a class="goBack" href="javascript: history.back(-1);"></a>
	<span class="photo"></span>
</header>
<div class="userInfo">
	<p class="userName">
	        <c:if test="${ !empty proUser.userNickname}">${proUser.userNickname}</c:if>
			<c:if test="${empty proUser.userNickname}">未命名用户</c:if>
			<a id="changeName"></a></p>
	<div class="live">
		<p class="lv">等级<br><span>LV${proUser.userGrade}</span></p>
		<p class="jf">积分<br><span>${proUser.userIntegral}</span></p>
	</div>
</div>
<div class="links">
	<a id="message"><span class="xx" ></span>消息</a>
	<a id="changePassword">
		<span class="xg"></span>账号管理
	</a>
	<a href="javaScript: changeNumber();"><span class="zh"></span>切换账号</a>
	<a href="javaScript: myGift();"><span class="lb"></span>我的礼包</a>
	<a href="javaScript: myLettory();"><span class="cp"></span>我的彩票</a>
	<a href="javaScript: gotogames();"><span class="game"></span>游戏中心</a>
</div>
<div class="altMask">
	<div>
		<span></span>
		<p class="msg"></p>
	</div>
</div>
<input type="hidden" value="" id="imageContent">
<input type="hidden" value="${proUser.userName}" id="username">
<input type="hidden" value="${proUser.imageUrl}" id="phoneurl">
<input type="hidden" value="${site.id}" id="siteId">
<input style="filter: alpha(opacity=0);opacity: 0;" class="js_upFile" type="file" name="cover">
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${mobilePath1}/js/newjs/upImg.js?<%=getTimestamp %>"></script>
<script type="text/javascript" src="${mobilePath1}/js/newjs/person.js?<%=getTimestamp %>"></script>
</body>
</html>