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
	var s="${ls}";
</script>
<head>
		<meta charset="utf-8" />
		<title>签到</title>
		<link rel="stylesheet" type="text/css" href="${wechatPath}/css/signpublic.css"/>
		<link rel="stylesheet" type="text/css" href="${wechatPath}/css/lottery-list.css">
		<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0" />
		<meta name="format-detection" content="telephone=no" />
		<meta name="MobileOptimized" content="320"/>
	<body>
		<div><img src="${wechatPath}/img/listBag.png" alt="" /></div>
		<div class="text-center ll-style">
			<p class="tip-word">很抱歉，您本期未中奖<br>下次继续努力哦！</p>
		</div>
		<div class="gift-pic">
			<img src="${wechatPath}/img/gift.png" alt="" />
		</div>
		<div class="ll-list">
			<div class="list-show">
						<p class="ls-show ls-show1">一等奖1名</p>
						<ul>
						<c:forEach var="p" items="${ls}">
							<c:if test="${p.draw_level==1 }">
							<li>
								<p class="ll-tel bac1">${p.user_name}</p>
								<p class="xu-name">${p.site_name}</p>
							</li>
							</c:if>
						</c:forEach>	
						</ul>
						<p class="ls-show ls-show2">一等奖2名</p>
						<ul>
						<c:forEach var="p" items="${ls}">
							<c:if test="${p.draw_level==2 }">
							<li>
								<p class="ll-tel">${p.user_name}</p>
								<p class="xu-name">${p.site_name}</p>
							</li>
							</c:if>
						</c:forEach>
						</ul>
						<p class="ls-show ls-show3">一等奖3名</p>
						<ul>
						<c:forEach var="p" items="${ls}">
							<c:if test="${p.draw_level==3 }">
							<li>
								<p class="ll-tel">${p.user_name}</p>
								<p class="xu-name">${p.site_name}</p>
							</li>
							</c:if>
						</c:forEach>
						</ul>
				
			</div>
			
		</div>
		<input type="hidden" id="sa" value="${state }">
		<input type="hidden" id="open" value="${open }">
	</body>
		<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${wechatPath1}/js/lottery.js"></script>
</html>