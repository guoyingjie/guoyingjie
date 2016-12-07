<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="http://oss.kdfwifi.net/deck/mobile" />
<c:set var="mobilePath1" value="${basePath}/mobile" />
<c:set var="cssPath" value="${mobilePath}/css" />
<c:set var="jsPath" value="${mobilePath1}/js" />
<c:set var="imgPath" value="${mobilePath}/img" />
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>实名认证</title>
<link rel="stylesheet" type="text/css" href="${cssPath}/my.css?<%=getTimestamp %>" />
<link rel="stylesheet" type="text/css" href="${cssPath}/autonym.css?<%=getTimestamp %>" />
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	var ctx="${basePath}";
	var id="${siteMac}";
	var gw_id="${gw_id}";
	var mac="${clientMac}";
	var ip="${clientIp}";
	var url="${fromUrl}";
	var terminalDevice="<%=request.getSession().getAttribute("terminalDevice")%>";
	var fromFw="${fromFw}";
</script>
</head>

<body>
<div class="box">
	<img src="${mobilePath}/img/bg.png?<%=getTimestamp %>" id="bg" width="100%" />
    <!--头部信息 返回 注册链接 start-->
    <div class="header"><p><span class="pic"><img src="${imgPath}/fh.png?<%=getTimestamp %>" width="50%"/></span><span class="dl">实名认证</span></p></div>
     
    <div class="content">
        <p>由于政策需求<br>首次使用需要进行实名认证</p>
    </div>
    <div class="btns">
    	<a href="${basePath}/w/goToAuthPage" class="ui-btn">进行验证</a>
    <button class="ui-btn" id="alert">下次再说</button></div>
    <!--页面底部-->
    <p id="cr">技术支持|北京宽东方  400-666-0050</p>
    <!--页面底部-->
</div>
<div class="mask">
    <div class="prompt">
        <hr>
        <p class="text">“<span>下次登录</span>”只能使用一次，再次登录时必须进行实名认证，请您提前准备好身份证进行认证。</p>
        <hr class="two">
        <span class="ts">*您也可以到当地营业厅进行认证。</span>
        <div class="alertBtns">
        <a class="ui-altBtn"  href="${basePath}/w/goToAuthPage">去认证</a>
        
        <a id="hrefBtn" class="ui-altBtn">确定</a></div>
    </div>
</div>
<script type="text/javascript" src="${jsPath}/autonym.js?<%=getTimestamp %>"></script>
</body>
</html>
