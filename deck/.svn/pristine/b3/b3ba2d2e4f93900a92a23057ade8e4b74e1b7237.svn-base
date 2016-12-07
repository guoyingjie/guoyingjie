<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath1" value="${basePath}/mobile" />
<c:set var="mobilePath" value="http://oss.kdfwifi.net/deck/mobile" />
<!DOCTYPE html>
<html>
<head>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<meta charset="utf-8">
	<title>帐户查询及缴费服务</title>
<meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link href="${mobilePath}/img/img/favicon.ico" type="image/x-icon" rel="shortcut icon">
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/zhifu.css?<%=getTimestamp %>">
<script type="text/javascript">
    var basePath= "${basePath}";
    function returns (){
    	alert("充值功能即将上线,敬请期待");
    }
</script>
</head>
<body>
<header class="header">${site.site_name}Wi-Fi帐户中心</header>
<nav class="nav">
	<ul>
		<li class="cha" onclick="window.location.href = '${basePath}/weixinNumber/gotoManySitePay?state=0'"><span>查</span><p>查询帐户剩余时长</p></li>
		<li class="chong" onclick="window.location.href = '${basePath}/weixinNumber/gotoManySitePay?state=1'"><span>充</span><p>帐户充值缴费</p></li> 
		<!-- <li class="chong" onclick="returns ()"><span>充</span><p>帐户充值缴费</p></li> -->
	</ul>
</nav>
<footer>KDFWIFI.COM  2013-2016</footer>

</body>

</html>