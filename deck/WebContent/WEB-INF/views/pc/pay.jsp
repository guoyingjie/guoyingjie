<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="pcPath" value="http://oss.kdfwifi.net/deck/pc" />
<c:set var="pcPath1" value="${basePath}/pc" />
<!DOCTYPE html>
<html>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<head>
<meta charset="utf-8">
	<title>充值缴费</title>
<link rel="stylesheet" type="text/css" href="${pcPath}/css/newcss/common.css?<%=getTimestamp %>">
<link rel="stylesheet" type="text/css" href="${pcPath}/css/newcss/pay.css?<%=getTimestamp %>">
<script type="text/javascript">

var basePath = "${basePath}";
</script>
</head>
<body oncontextmenu=self.event.returnValue=false>
<header>欢迎使用<br>${site.site_name }Wi-Fi</header>
<div class="pay">
	<div class="title">
		<p class="time on">按时长购买<span><i></i></span></p>
		<p class="gprs">按流量购买<span><i></i></span></p>
	</div>
	<div class="favorable">
		<p class="xtitle">套餐描述</p>
		<p class="nr">优惠信息 购买50小时网时，可赠送2小时免费上网</p>
	</div>
	<div class="payType">购买单位：<span id='type'>时</span>
		<ul class="typeList">
			<c:forEach var="p" items="${siteAll.list}">
					<c:if test="${p.price_type<=3 }">
						<li class="tLi" data-text="${p.describe}" mealNum="${p.giveMeal}"
							mealType="${p.giveMealUnit }" value="${p.id}"
							reommend="${p.recommendState}" charge_type="${p.charge_type }"
							prices="${p.unit_price}" price_type="${p.price_type}"
							price_num="${p.price_num }">${p.name}</li>
					</c:if>
					<c:if test="${p.price_type>3 }">
						<li class="fLi" data-text="${p.describe}" mealNum="${p.giveMeal}"
							mealType="${p.giveMealUnit }" value="${p.id}"
							reommend="${p.recommendState}" charge_type="${p.charge_type }"
							prices="${p.unit_price}" price_type="${p.price_type}"
							price_num="${p.price_num }">${p.name}</li>
					</c:if>
				</c:forEach>
		</ul>
	</div>
	<div class="payMony">购买单价：<span>1.00元</span></div>
	<div class="payNum">购买数量：<p><span class="cut">-</span><input id="number" type="tel" name="" onkeyup="this.value=this.value.replace(/\D/g,'')"
				onafterpaste="this.value=this.value.replace(/\D/g,'')" value="1"><span class="add">+</span></p></div>
	<div class="give">赠送：<span>2小时流量附加包</span></div>
	<p class="paySum">应付总额：<span>20.00</span>元</p>
	<p class="payTitle">支付方式</p>
	<ul class="payList">
		<li class="card on"><span class="logo"></span><p>银行卡支付</p></li>
		<li class="alipay"><span class="logo"></span><p>支付宝支付</p></li>
		<li class="weiX"><span class="logo"></span><p>微信支付</p></li>
	</ul>
	<p class="goPay"><span>付款</span></p>
</div>
<div class="mask"></div>
<footer><p class="copyRight">KDFWIFI.COM 2013-2016  联系电话 400-666-0050</p></footer>
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${pcPath1}/js/newjs/pay.js?<%=getTimestamp %>"></script>
</body>
</html>