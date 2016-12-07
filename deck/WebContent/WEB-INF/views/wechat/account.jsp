<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="css" value="http://oss.kdfwifi.net/deck/wechat/css"/>
<c:set var="img" value="http://oss.kdfwifi.net/deck/wechat/img/ziimg"/>
<c:set var="js" value="${basePath}/wechat/js" />
<!DOCTYPE html>
<html>
<head>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>子账号管理</title>
<link rel="stylesheet" type="text/css" href="${css}/newpublic.css?<%=getTimestamp %>" />
<link rel="stylesheet" type="text/css" href="${css}/account.css?<%=getTimestamp %>">
<script type="text/javascript">
		var ctx = "${basePath}";
		var img = "${img}";
</script>
</head>
<body>

    <p class="list-title blue">主账号</p>
	<div class="list-row">
		
		<div class="list" id="exctBind">
			<p>取消绑定主账号</p>
			<img class="right-icon" src="${img}/enter.png">
		</div>
		<div class="border-div"></div>
		<div class="list" id="changeBind">
			<p>更换绑定主账号</p>
			<img class="right-icon" src="${img}/enter.png">
		</div>
		<div class="border-div"></div>
		<div class="list" id="changePassword">
			<p>修改主账号密码</p>
			<img class="right-icon" src="${img}/enter.png">
		</div>
	</div>
	<p class="list-title blue">子账号</p>
	<div id="ajaxList" class="list-row">
		<%-- <div class="list">
			<p>子账号管理</p>
			<img class="right-icon" src="${img}/enter.png">
			 <span id="userName">18701570688a</span>
		</div>
		 <div class="border-div"></div>
		 <div class="list text-center blue">
			<img class="addList" src="${img}/add.png">创建子账号
		</div>  --%>
	</div>

	<div class="footer-btn">
		<button id="signOut" class="btn-block blue-bg">激活子账号</button>
	</div>

	<div class="footer">KDFWIFI.COM 2013-2016</div>
	<div id="errModel" style="display: none"><p id="errMsg"></p></div>
	<input type="hidden" value="${userName}" id="username"/>
	<input type="hidden" value="${siteId}" id="siteId">
	<input type="hidden" value="${openid}" id="openid">
</body>
<script src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script src="${js}/account.js?<%=getTimestamp %>"></script>
</html>