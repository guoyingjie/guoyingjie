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
<link rel="stylesheet" type="text/css" href="${cssPath}/autonymForm.css?<%=getTimestamp %>" />
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
var ctx = "${basePath}";
var id="<%=request.getSession().getAttribute("siteMac")%>";
var mac="<%=request.getSession().getAttribute("clientMac")%>";
var ip="<%=request.getSession().getAttribute("clientIp")%>";
var url="<%=request.getSession().getAttribute("fromUrl")%>";
var fromFw="${fromFw}";
var terminalDevice="<%=request.getSession().getAttribute("terminalDevice")%>";
</script>
</head>
<body>
<div class="box">
	<img src="${imgPath}/bg.png" id="bg" width="100%" />
    <!--头部信息 返回 注册链接 start-->
    <div class="header"><p><span class="pic"><img src="${imgPath}/fh.png" width="50%" /></span><span class="dl">实名认证</span></p></div>
    <!--头部信息 返回 注册链接 end-->
    <!--banner广告 start-->
    <div class="banner" id="banner">
    	<p class="schoolName">请将身份证置于光源充足的环境拍摄，保证能看清楚照片及相关信息</p>
    </div>
    <div class="banner" id="banner">
        <p class="schoolName">请在光源充足的环境拍摄，保证能看清楚照片</p>
    </div>
    <!--banner广告 end-->
    <form id="autonym">
        <div class="uForm">
            <input name="userName" id="userName" type="text" value="" placeholder="请填写您的真实姓名">
            <span class="nameerro"></span>
            <input name="userCard" id="userCard" type="tel" value="" placeholder="请填写您的身份证号码" maxlength="18">
            <span class="carderro"></span>
            <input name="userPosi" id="userPosi" type="text" value="" placeholder="请填写所在宿舍楼号和房号">
            <span class="posierro"></span>
            <button class="next" type="button">下一步</button>
            <em>实名认证信息由国家相关部门收集，平台不会保留<br>任何信息，请您放心填写。</em>
        </div>
        <div class="idCardPic">
            <a class="cardPic" onclick="cardPic.click()"></a>
            <ul class="img-list"></ul>
            <input id="cardPic" accept="image/*" type="file">
            <input class="imgBase" type="hidden" value="">
            <button class="next1" type="button">下一步</button>
        </div>
        <div class="userPicImg">
            <a class="user-Pic" onclick="userPic.click()"></a>
            <ul class="img-list"></ul>
            <input id="userPic" accept="image/*" type="file">
            <input class="imgBase" type="hidden" value="">
            <button class="over" type="button">完成</button>
        </div>
    </form>
    <!--页面底部-->
    <p id="cr">技术支持|北京宽东方  400-666-0050</p>
    <!--页面底部-->
</div>
<div class="mask">
	<img alt="" src="${imgPath}/img.gif">
</div>
<div class="log-mask">
	<div class="warn">
		<p class="wanr-text">您的账号已经提交认证,请确定登录上网</p>
		<span id="authId">确定</span>
	</div>
</div>
<script type="text/javascript" src="${jsPath}/jquery.validate.min.js"></script>
<script type="text/javascript" src="${jsPath}/jquery.validate.messages_cn.js"></script>
<script type="text/javascript" src="${jsPath}/autonymForm.js"></script>
</body>
</html>