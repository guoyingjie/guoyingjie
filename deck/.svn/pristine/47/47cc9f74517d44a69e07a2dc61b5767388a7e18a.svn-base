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
	<title>上传头像</title>
	<link rel="stylesheet" type="text/css" href="${cssPath}/newcss/common.css?<%=getTimestamp %>">
	<link rel="stylesheet" type="text/css" href="${cssPath}/newcss/upPhoto.css?<%=getTimestamp %>">
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
	<div class="content">
		<p class="title">上传头像</p>
		<span class="photo"></span>
		<p class="upImg"><span>＋</span> 选择图片</p>
		<p class="zhichi">只支持JPG，PNG格式</p>
	</div>
	<input style="filter: alpha(opacity=0);opacity: 0;" class="js_upFile" type="file" name="cover">
	<p class="btns"><span class="cancel">取消</span><span class="confirm">确定</span></p>
</div>
<input type="hidden" value="${proUser.userName}" id="userName">
<input type="hidden" value="${site.id}" id="siteId">
<div class="altMask">
	<div>
		<span></span>
		<p class="msg"></p>
	</div>
</div>
<footer><p class="copyRight">KDFWIFI.COM 2013-2016  联系电话 400-666-0050</p></footer>
</body>
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${pcPath}/js/newjs/upPhoto.js?<%=getTimestamp %>"></script>
<script type="text/javascript" src="${pcPath}/js/newjs/upImg.js?<%=getTimestamp %>"></script>

</html>