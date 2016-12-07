<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="${basePath}/mobile" />
<!--<c:set var="imgPath" value="http://oss.solarsys.cn/0001/mobile_img" />-->
<c:set var="imgPath" value="${mobilePath}/img"/>
<c:set var="cssPath" value="${mobilePath}/css" />
<c:set var="jsPath" value="${mobilePath}/js" />
<!doctype html>
<html>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<head>
<meta charset="utf-8">
<meta name="format-detection" content="telephone=no">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>个人中心</title>
<script type="text/javascript">
 	var basePath="${basePath}";
	<%-- var id="<%=request.getSession().getAttribute("siteMac")%>";
	var mac="<%=request.getSession().getAttribute("clientMac")%>";
	var ip="<%=request.getSession().getAttribute("clientIp")%>";
	var url="<%=request.getSession().getAttribute("fromUrl")%>"; --%>
</script>
</head>
	<script type="text/javascript">
	var basePath="${basePath}";
	var imgPath="${imgPath}";
	var id="<%=request.getSession().getAttribute("siteMac")%>";
	var mac="<%=request.getSession().getAttribute("clientMac")%>";
	var ip="<%=request.getSession().getAttribute("clientIp")%>";
	var url="<%=request.getSession().getAttribute("fromUrl")%>"; 
	var fromFw="<%=request.getSession().getAttribute("fromFw")%>";
	var gw_address="<%=request.getSession().getAttribute("gw_address")%>";
	var gw_port="<%=request.getSession().getAttribute("gw_port")%>";
	var terminalDevice="<%=request.getSession().getAttribute("terminalDevice")%>";
				
</script>
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
	
	sb.append(ip).append("&id=").append(id).append("&mac=").append(mac).append("&url=").append(url).append("&terminalDevice=").append(terminalDevice);
	str = sb.toString();
%>
<body>
<header class="header">
    <h1 class="ui_tit">个人中心</h1>
    <a href="javascript: history.back(-1);"><i class="icon-angle-left"></i></a>
    
</header>
<div class="contener">
	剩余时间
	<p class="timeRema"><span>${day }</span>天<span>${hour }</span>小时<a href="${basePath}/toPay">立即续费</a></p>
	<p>账号<span class="userName">${pu.userName}</span></p>
	<p class="changeLink"><a href="${basePath}/mobileChangePwd">修改密码<i class="icon-angle-right"></i></a></p>
</div>
<div class="func">
	<a class="cut">切换账号</a>
</div>
<footer class="fixd">技术支持|北京宽东方&nbsp;&nbsp;联系电话&nbsp;400-666-0050</footer>
<script language="javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${jsPath}/mobliePersonal.js?<%=getTimestamp %>"></script>

</body>
<link href="${cssPath}/style1.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${cssPath}/font-awesome/css/font-awesome.min.css">
</html>