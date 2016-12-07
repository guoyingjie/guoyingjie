<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="http://oss.kdfwifi.net/deck/mobile" />
 
<c:set var="mobilePath1" value="${basePath}/mobile" />
<c:set var="imgPath"
	value="http://oss.solarsys.cn/0001/mobile_img" />
<!DOCTYPE html>
<html>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<head>
<meta charset="utf-8">
<meta content="1" name="keywords" />
<meta content="1" name="description" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="yes" name="apple-touch-fullscreen" />
<meta content="telephone=no" name="format-detection" />
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
<title>缴费</title>
<link rel="stylesheet" type="text/css"
	href="${mobilePath}/css/newcss/common.css?<%=getTimestamp %>" />
<link rel="stylesheet" type="text/css"
	href="${mobilePath}/css/newcss/pay.css?<%=getTimestamp %>" />
<script type="text/javascript">
	var basePath = "${basePath}";
	var imgPath = "${imgPath}";
	var siteAll = "${siteAll}";
	var user = "${proUser}";
	var site = "${site}";
	function back() {
		window.history.back();

	}
</script>
</head>

<body>
	<header>
		<a class="goBack" href="javascript: history.back(-1);"></a> 充值缴费 <a
			class="goPerson"
			href="${basePath}/w/goToPerson?userName=${proUser.userName}&siteId=${site.id}"></a>
	</header>
	<div class="tab">
		<p class="time on">
			按时长购买<span><i></i></span>
		</p>
		<p class="gprs">
			按流量购买<span><i></i></span>
		</p>
	</div>
	<div class="favorable">
		<p class="title">套餐描述</p>
		<p class="nr"></p>
	</div>
	<div class="payType">
		购买单位：<span></span>
	</div>
	<div class="payMony">
		购买单价：<span></span>
	</div>
	<div class="payNum">
		购买数量：
		<p>
			<span class="cut">-</span><input id="number" type="text" name=""
				onkeyup="this.value=this.value.replace(/\D/g,'')"
				onafterpaste="this.value=this.value.replace(/\D/g,'')" value="1"><span
				class="add">+</span>
		</p>
	</div>
	<p class="paySum">
		应付总额：<span></span>元
	</p>
	<p class="payTitle">支付方式</p>
	<ul class="payList">
		<li class="card on"><span class="logo"></span>
			<p>
				银行卡支付<br> <span>银行卡及信用卡均可使用</span>
			</p></li>
		<li class="alipay"><span class="logo"></span>
			<p>
				支付宝支付<br> <span>推荐支付宝用户使用</span>
			</p></li>
		<li class="weiX"><span class="logo"></span>
			<p>
				微信支付<br> <span>需关注微信公众号使用</span>
			</p></li>
	</ul>
	<p class="goPay">
		<span>付款</span>
	</p>
	<div class="altMask">
		<div>
			<span></span>
			<p class="msg"></p>
		</div>
	</div>
	<div class="maxkXl">
		<div class="content">
			<p class="tc">
				<span>关闭</span>
			</p>
			<ul class="payTypeList">
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
	</div>
	<input type="hidden" value="${proUser.imageUrl}" id="phoneurl">
	<div class="mask"></div>
</body>
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${mobilePath1 }/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${mobilePath1}/js/newjs/pay.js?<%=getTimestamp%>"></script>

</html>
