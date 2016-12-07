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
	<title>立即上网</title>
	<link rel="stylesheet" type="text/css" href="${cssPath}/style.css?<%=getTimestamp %>">
<%-- 	<script type="text/javascript" src="${cssPath}/jquery.js"></script> --%>
</head>
<% 
String id = (String)request.getSession().getAttribute("siteMac");
String ip = (String)request.getSession().getAttribute("clientIp");
String mac = (String)request.getSession().getAttribute("clientMac");
String  url = (String)request.getSession().getAttribute("fromUrl");
String fromFw=(String)request.getSession().getAttribute("fromFw");
String gw_address=(String)request.getSession().getAttribute("gw_address");
String gw_port=(String)request.getSession().getAttribute("gw_port");
String terminalDevice = (String)request.getSession().getAttribute("terminalDevice");
StringBuffer sb = new StringBuffer();
String str = "";
sb.append(id).append("&ip=").append(ip).append("&mac=").append(mac).append("&url=").append("baidu.com").append("&terminalDevice=").append(terminalDevice);
str = sb.toString();
%>
<script type="text/javascript">
var basePath = "${basePath}";
var id="<%=request.getSession().getAttribute("siteMac")%>";
var mac="<%=request.getSession().getAttribute("clientMac")%>";
var ip="<%=request.getSession().getAttribute("clientIp")%>";
var fromUrl="<%=request.getSession().getAttribute("fromUrl")%>";
var fromFw="<%=request.getSession().getAttribute("fromFw")%>";
var gw_address="<%=request.getSession().getAttribute("gw_address")%>";
var gw_port="<%=request.getSession().getAttribute("gw_port")%>";
var terminalDevice="<%=request.getSession().getAttribute("terminalDevice")%>";
</script>
<body>
<div class="ui-box">
	<div class="ui-nei">
		<p class="ui-p">放心Wi-Fi&nbsp;&nbsp;&nbsp;舒适生活</p>
	</div>
	<div class="ui-links">
	<a class="ui-expe">立即上网</a> 
		<%-- <a class="ui-expe" href="${basePath}/immediateToInternet?id=<%=str %>">立即上网</a>  --%>
		<a class="ui-per">个人中心</a>
	</div>
</div>
 <div class="log-mask">
		<!-- <p>登录中</p> -->
		<div class="loader">登录中...</div>
		<div class="warn">
			<p class="wanr-text">提示文字</p>
			<div class="buttons">
				<button id="sure">继续登录</button>
				<button id="cancel">取消登录</button>
			</div>
		</div>
	</div>
<footer>技术支持|北京宽东方&nbsp;&nbsp;&nbsp;&nbsp;联系电话&nbsp;&nbsp;400-666-0050</footer>
</body>
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${pcPath}/js/pcAuther.js?<%=getTimestamp %>"></script>

</html>