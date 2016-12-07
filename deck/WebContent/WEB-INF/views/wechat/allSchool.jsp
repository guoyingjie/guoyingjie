<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="http://oss.kdfwifi.net/deck/wechat" />
<c:set var="mobilePath1" value="${basePath}/wechat" />
 
<!DOCTYPE html>
<html>
<head>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<meta charset="utf-8">
	<title>场所信息</title>
	<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<link rel="stylesheet" type="text/css" href="${mobilePath}/css/common.css?<%=getTimestamp %>">
	<link rel="stylesheet" type="text/css" href="${mobilePath}/css/school.css?<%=getTimestamp %>">
<script type="text/javascript">
    var basePath= "${basePath}";
    var site="${siteLs}";
</script>


  </head>

<body>
<p class="school"></p>
<p class="siteName"></p>
<p class="text">您正选择了<span></span>的Wi-Fi覆盖区域内，是否直接购买<span>s</span>的资费套餐？</p>
<p class="btns"><button class="sure" type="button">确定</button><br><!-- <a href="#" class="goPay">去充值</a> --></p>
<div class="maskSite">
	<div class="content">
		<p class="tc"><span>关闭</span></p>
		<ul class="siteList">
			<c:forEach var="s" items="${siteLs}">
				<li value="${s.id }" imgUrl="${s.bannerUrl}">${s.site_name }</li>
			</c:forEach>
		</ul>
	</div>
</div>
<input type="hidden" value="${openid}" id="openid">
<input type="hidden" value="${tel}" id="username">
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${mobilePath1}/js/selectS.js?<%=getTimestamp %>"></script>
</body>
</html>