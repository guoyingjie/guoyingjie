<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="pcPath" value="http://oss.kdfwifi.net/deck/pc" />
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
	<title>服务条款</title>
<link rel="stylesheet" type="text/css" href="${pcPath }/css/newcss/common.css">
<link rel="stylesheet" type="text/css" href="${pcPath }/css/newcss/terms.css">
</head>
<body oncontextmenu=self.event.returnValue=false>
<header>欢迎使用<br>${site.site_name}Wi-Fi</header>
<div class="terms">
	<p class="title">服务条款</p>
	<p class="xtitle">宽东方Wi-Fi服务条款</p>
	<p class="xz">校园Wi-Fi上网须知</p>
	<p class="text">1、宽东方校园WIFI为您提供快速、安全的WIFI上网服务，请您放心进行上网支付，我们将对您的个人信息严格保密。</p>
	<p class="text">2、在任何情况下，宽东方对接入高校用户因使用校园WIFI而导致的直接或间接的损失不承担责任，损失包括但不限于在使用校园WIFI的过程中产生的财物损失、声誉损害、人身伤害或对第三方造成的不利影响。宽东方相信，最主要的上网损失可能会来自于互联网，而那已经超出我们的控制范围。</p>
	<p class="text">3、校园WIFI用户应自行保证自己的上网设备具备网络连接能力，宽东方对用户自己的设备能否使用其网络服务不承担责任并不作出任何担保，对由于提供了上网建议后的服务失败不承担责任，对由于提供的上网服务有所变动而使得用户的设备不匹配而不能向用户提供上网服务不承担责任。</p>
	<p class="text">4、宽东方校园WIFI仅限校园上网用户使用， 非校园上网用户使用无线网络，需进行注册， 注册完成后，您会被自动视为校园上网用户，宽东方对您在使用校园WIFI上网时产生的直接或间接的损失不承担责任。</p>
	<p class="text">5、校园WIFI用户需保持文明、绿色的上网行为，严禁浏览不良网页和非法网站，杜绝发表反政府、反社会、反人类的言论。若因此造成财产损失或违法犯罪行为，一切后果均由用户自行承担，宽东方会积极配合有关部门的调查取证工作，为有关部门提供相关的用户信息。</p>
</div>
</body>
</html>