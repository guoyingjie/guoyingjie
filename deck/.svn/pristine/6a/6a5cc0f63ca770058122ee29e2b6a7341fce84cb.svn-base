<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="${basePath}/mobile" />
<c:set var="wechatPath" value="http://oss.kdfwifi.net/deck/wechat" />
<c:set var="wechatPath1" value="${basePath}/wechat" />
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>取消绑定</title>
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta name="viewport"
	content="width=device-width,height=device-height,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<script type="text/javascript"
	src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${wechatPath1}/js/jweixin-1.0.0.js"></script>
<link rel="stylesheet" type="text/css"
	href="${wechatPath}/css/common.css?<%=getTimestamp%>">
<link rel="stylesheet" type="text/css"
	href="${wechatPath}/css/bindingSuccess.css?<%=getTimestamp%>">
<script type="text/javascript">
	var ctx = "${basePath}";
</script>
</head>
<body>
	<p class="success">
		成功取消绑定！<br>
		<button type="button" onclick="closeWin()">关闭窗口</button>
	</p>
	<input type='hidden' value="${proUser.userName}" id="username"/>
</body>
<script type="text/javascript">
	 function closeWin(){
		 WeixinJSBridge.call("closeWindow");
	 }
</script>
</html>