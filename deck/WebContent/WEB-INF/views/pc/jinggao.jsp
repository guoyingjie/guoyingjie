<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="pcPath" value="http://oss.kdfwifi.net/deck/pc" />
<!DOCTYPE html>
<html>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<head>
<meta charset="utf-8">
<title>账号被锁定</title>
<link rel="stylesheet" type="text/css" href="${pcPath}/css/newcss/common.css">
<style type="text/css">
.th{
	width: 85px;
	height: 85px;
	color: #fff;
	display: block;
	background: #f06e64;
	text-align: center;
	border-radius: 50%;
	font-size: 56px;
	margin-top: 64px;
}
h1{
	text-align: center;
	margin-top: 20px;
	font-weight: 400;
	color: #595959;
	font-size: 24px;
}
.text{
	text-align: center;
	color: #808080;
	font-size: 12px;
	margin-top: 35px;
}
.time{
	color: green;
	font-size: 14px;
}
.links{
	text-align: center;
	margin-top: 35px;
}
.links > span{
	color: #47a4dc;
	margin: 0 20px;
	display: inline-block;
	padding-left: 24px;
	cursor: pointer;
	
}
.links > span:nth-child(1){
	background: url(${basePath}/pc/img/newimg/qh.png) no-repeat left;
	/* background: url(/deck/pc/img/newimg/qh.png) no-repeat left; */
}
.links > span:nth-child(2){
	background: url(${basePath}/pc/img/newimg/wh.png) no-repeat left;
	/* background: url(/deck/pc/img/newimg/wh.png) no-repeat left; */
}
</style>
</head>
<body>
<header>欢迎使用<br>${site.site_name}Wi-Fi</header>
<span class="th">!</span>
<h1>您的账号被多台设备频繁登录</h1>
<p class="text">账号存在风险，已被锁定，<span class="time"><c:if test="${!empty times}">${times}</c:if>  <c:if test="${empty times}">24小时</c:if></span>后自动恢复正常</p>
<p class="links"><span id="changeusernum">切换账号</span><span id="changepassword">修改密码</span></p>
<footer><p class="copyRight">KDFWIFI.COM 2013-2016  联系电话 400-666-0050</p></footer>
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
var basePath="${basePath}";
var id="<%=request.getSession().getAttribute("siteMac")%>";
var mac="<%=request.getSession().getAttribute("clientMac")%>";
var ip="<%=request.getSession().getAttribute("clientIp")%>";
var url="<%=request.getSession().getAttribute("fromUrl")%>";

</script>
</body>
<script type="text/javascript">
$(function(){
	$("#changepassword").click(function(){
		window.location.href=basePath+'/ProtalUserManage/changePassword?jing=gao';
	});
	$("#changeusernum").click(function(){
		window.location.href=basePath+'/w/changeNumber';
	});
});
</script>
</html>