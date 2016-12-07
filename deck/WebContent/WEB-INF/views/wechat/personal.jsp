<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="wechatPath" value="http://oss.kdfwifi.net/deck/wechat" />
<c:set var="wechatPath1" value="${basePath}/wechat" />
<c:set var="mobilePath" value="${basePath}/mobile" />
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>个人中心</title>
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link rel="stylesheet" type="text/css" href="${wechatPath}/css/common.css">
<link rel="stylesheet" type="text/css" href="${wechatPath}/css/person_1.css">
<script type="text/javascript">
        var ctx="${basePath}";
</script>
</head>
<body>
<div class="mask opacity"></div>
<header>
	<span class="photo"></span>
	<span id="sign" style="position:absolute;right:15px;top:15px;width:70px;box-shadow:0 0 5px #fff;height:24px;line-height:24px;text-align:center;border:1px solid #fff;border-radius:10px;font-size:14px;color:#fff;">点击签到</span>
</header>
<div class="integral">
	<p class="money">余额：<span>
	    <c:if test="${ !empty balance}">${balance}</c:if>
		<c:if test="${empty balance}">0.00</c:if>
	</span></p>
	<ul class="lvjf">
		<li class="lv"><span>等级</span><span>LV${proUser.userGrade}</span></li>
		<li class="jf"><span>积分</span><span>${proUser.userIntegral}</span></li>
	</ul>
</div>
<div class="links">
	<a href="javaScript: changeNumber();"><span class="zh"></span>账号管理<span class="phone">${proUser.userName}</span></a>
	<a id="message"><span class="xx"></span>消息</a>
	<!-- <a style="margin-bottom: 10px; border-bottom: 1px solid #d6d6d6" id="changePassword"><span class="xg"></span>修改密码</a> -->
	<a href="javaScript: myGift();"><span class="lb"></span>我的礼包</a>
	<a href="javaScript: myLettory();"><span class="cp"></span>我的彩票</a>
	<a href="javaScript: gotogames();"><span class="game"></span>游戏中心</a>
	<a href="javaScript: recommend();"><span class="recomCode"></span>我的推荐码</a>
</div>
<footer>KDFWIFI.COM 2013-2016</footer>
<div class="altMask">
	<div>
		<span></span>
		<p class="msg"></p>
	</div>
</div>
<div class="congrats">
	<p class="conimg1"><img src="${wechatPath}/img/chu.png"></p>
	<p class="conimg2"><img src="${wechatPath}/img/zhojai.png"></p>
</div>
<input type="hidden" value="" id="imageContent">
<input type="hidden" value="${proUser.userName}" id="username">
<input type="hidden" value="${proUser.id}" id="portalId">
<input type="hidden" value="${proUser.imageUrl}" id="phoneurl">
<input type="hidden" value="${site.id}" id="siteId">
<input type="hidden" value="${oepnid}" id="openid">
<input type="hidden" value="${weixinurl}" id="weixinurl">
<input style="filter: alpha(opacity=0);opacity: 0;" class="js_upFile" type="file" name="cover">
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${wechatPath1}/js/wupImg.js?<%=getTimestamp %>"></script>
<script type="text/javascript" src="${wechatPath1}/js/wperson.js?<%=getTimestamp %>"></script>
</body>
</html>