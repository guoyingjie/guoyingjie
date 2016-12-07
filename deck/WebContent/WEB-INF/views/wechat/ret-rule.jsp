<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="${basePath}/mobile" />
<c:set var="wechatPath" value="http://oss.kdfwifi.net/deck/wechat" />
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
		<link rel="stylesheet" type="text/css" href="${wechatPath}/css/ret-rule.css">
		<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0" />
		<meta name="format-detection" content="telephone=no" />
		<meta name="MobileOptimized" content="320"/>
	</head>
		<body>
		<div class="rr-intro">
			<div class="ri-detial">
				<p>【抽奖规则】</p>
				抽奖分为两种，一种是每日抽奖，一种是月度抽奖。每一个账号每日签到后即可获得每日抽奖的机会一次；每一个账号每月连续签满一个月，即可在每月最后一天进行月度抽大奖一次。
			</div>
			<div class="ri-detial">
				<p>【补签规则】</p>
				每次充值后可获得一次补签机会，可对当月没有签到的天数进行补签，补签没有每日抽奖的机会，只作为月度抽奖的机会依据。
			</div>
			<div class="ri-detial">
				<p>【奖项设置】</p>
				每日签到：四等奖赠5积分、三等奖赠10积分、二等奖赠15积分、一等奖赠20积分。<br/>月度抽奖：三等奖3名、二等奖2名、一等奖1名。具体奖品以宽东方当月公示抽奖说明为准。
			</div>
			<div class="ri-detial">
				<p>【中奖提醒】</p>
				每日抽奖如果中奖获得积分后，将会自动收到反馈。<br/>月度抽奖如果中奖后将由抽奖活动的运营人员进行电话、短信等方式的联系。
			</div>
			<div class="ri-detial">
				<p>【奖品发送】</p>
				积分将自动充入获奖账号。<br/>月度抽奖的奖品的收件地址将由运营人员进行联系后确定，请保证联系方式的畅通和正确。<br>若出现客户未签收、由他人代收或未开箱验货导致的一切损失，均由客户本人自行承担。<br/>所有奖品是全新未使用的，但奖品在运输中可能会有轻微刮痕或些许瑕疵，宽东方所抽奖的奖品不提供维修或更换服务。<br/>如遇交通管制、大雨雪、洪涝、冰灾、地震、停电等不可抗力因素造成的奖品遗失、受损、收取时间的延误，抽奖活动方不承担相应的责任。
			</div>
			<div class="ri-end">本次活动最终解释权归宽东方所有</div>
		</div>
	</body>
		<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
</html>