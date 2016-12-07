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
<script type="text/javascript">
	var ctx = "${basePath}";
</script>
<head>
		<meta charset="utf-8" />
		<title>签到</title>
		<link rel="stylesheet" type="text/css" href="${wechatPath}/css/signpublic.css"/>
		<link rel="stylesheet" type="text/css" href="${wechatPath}/css/signIn.css">
		<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0" />
		<meta name="format-detection" content="telephone=no" />
		<meta name="MobileOptimized" content="320"/>
	</head>
	<body>
	<div class="mask opacity"></div>
		<div class="signIn">
			<div class="si-head">
				<div class="si-left">
					<p class="sl-lxqd"><span class="ts"><i class="zt-blue">${userSign.signNum}</i>&nbsp;天</span><span>累计签到</span></p>
					<p class="sl-bqd"><span class="ts"><i>1</i>&nbsp;天</span><span>可补签</span></p>
					<div class="qd-btn" onclick="javascript:;"><img src="${wechatPath}/img/qcbtn.png"/></div>
				</div>
				<div class="si-right">
					<div class="top-nav">
						<ul>
							<li><a href=""><img src="http://oss.kdfwifi.net/school_pic/combanner1.jpg"><h3></h3></a></li>
							<li><a href=""><img src="http://oss.kdfwifi.net/school_pic/combanner2.jpg"><h3></h3></a></li>
							<li><a href=""><img src="http://oss.kdfwifi.net/school_pic/combanner3.jpg"><h3></h3></a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="winning-list">
				<p class="wl-left"><span class="wl-yue">每</span>月<span class="wl-ri">1</span>号揭秘上期中奖名单</p>
				<p class="wl-right" onclick="javascript:;">上期中奖名单&nbsp;>></p>
			</div>
			<div class="check-calendar">
				 <ul>
					<li><span></span><p class=""></p></li>
					<li><span></span><p class=""></p></li>
					<li><span></span><p class=""></p></li>
					<li><span></span><p class=""></p></li>
					<li><span></span><p class=""></p></li>
					<li><span></span><p class=""></p></li>
					<li><span></span><p class=""></p></li>
					<li><span></span><p class=""></p></li>
					<li><span></span><p class=""></p></li>
					<li><span></span><p class=""></p></li>
				</ul>
				<ul>
					<li><span></span><p class=""></p></li>
					<li><span></span><p class=""></p></li>
					<li><span></span><p class=""></p></li>
					<li><span></span><p class=""></p></li>
					<li><span></span><p class=""></p></li>
					<li><span></span><p class=""></p></li>
					<li><span></span><p class=""></p></li>
					<li><span></span><p class=""></p></li>
					<li><span></span><p class=""></p></li>
					<li><span></span><p class=""></p></li>
					<li><span></span><p class=""></p></li>
					<li><span></span><p class=""></p></li>
					<li><span></span><p class=""></p></li>
					<li><span></span><p class=""></p></li>
					<li><span></span><p class=""></p></li>
					<li><span></span><p class=""></p></li>
					<li><span></span><p class=""></p></li>
					<li><span></span><p class=""></p></li>
					<li><span></span><p class=""></p></li>
					<li><span></span><p class=""></p></li>
					<li><span></span><p class=""></p></li>
				</ul> 
			</div>
			<div class="shou-zhan"><span class="text-show">展开</span>&nbsp;<img src="${wechatPath}/img/xia.png" src1="${wechatPath}/img/xia.png" src2="${wechatPath}/img/shang.png"/></div>
			<div class="win-cont"> 
				<p class="wc-title">本期奖品</p>
				<p class="wc-intro"><img src="${wechatPath}/img/winstyle.png"/></p>
				<button class="qd-rule">签 到 规 则</button>
			</div>
		</div>
		<div class="tip-box">
			<img src="${wechatPath}/img/xiao.png"/>
			<p class="tipb-wpod">恭喜您获得抽取月度大奖的资格<br/>敬请期待开奖结果！</p>
			<button class="tb-btn">关 闭</button>
		</div>
		<div class="tip-warn">
			您今天已经抽过奖喽!
		</div>
		<input id="ls" type="hidden" value="${userSign.signTime }">
		<input id="lsid" type="hidden" value="${userSign.id }">
		<input id="lsstate" type="hidden" value="${userSign.state }">
		<input id="lscount" type="hidden" value="${userSign.signNum }">
		<input id="user" type="hidden" value="${proUser.userName}">
		<input id="siteid" type="hidden" value="${site.id}">
	</body>
		<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${wechatPath1}/js/idangerous.swiper-2.0.min.js"></script>
	<script type="text/javascript" src="${wechatPath1}/js/signIn.js"></script>
</html>