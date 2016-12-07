<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="http://oss.kdfwifi.net/deck/wechat" />
<c:set var="mobilePath1" value="${basePath}/wechat" />
<!DOCTYPE html>
<html>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<head>
<meta charset="utf-8">
	<title>充值</title>
	<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
	<meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<link rel="stylesheet" type="text/css"href="${mobilePath}/css/common.css?<%=getTimestamp %>" />
	<link rel="stylesheet" type="text/css"href="${mobilePath}/css/pay.css?<%=getTimestamp %>" />
	<script type="text/javascript">
		var basePath = "${basePath}";
		var imgPath = "${imgPath}";
		var sum="${sum}";
	</script>
</head>
<body>
<div class="siteBox">
	<p class="site">选择场所</p>
	<p class="siteName1"></p>
</div>
<p class="goumai">购买方式</p>
<div class="tab">
	<p class="time on">按时长购买<span><i></i></span></p>
	<p class="gprs">按流量购买<span><i></i></span></p>
</div>
<div class="favorable">
	<p class="title">优惠信息</p>
	<p class="nr"></p>
</div>
<div class="payType">购买单位：<span></span></div>
<div class="payMony">购买单价：<span></span></div>
<div class="payNum">购买数量：<p><span class="cut">-</span><input id="number" type="tel" name="" onkeyup="this.value=this.value.replace(/\D/g,'')"
				onafterpaste="this.value=this.value.replace(/\D/g,'')" value="1"><span class="add">+</span></p></div>
<div class="give">赠送：<span></span></div>
<p class="paySum">应付总额：<span></span>元</p>
<p class="balance">可用余额：<span></span>元</p>
<p class="goPay"><span>付款</span></p>
<p style="height: 60px;"></p>	
<div class="altMask">
	<div>
		<span></span>
		<p class="msg"></p>
	</div>
</div>
<div class="maxkXl">
	<div class="content">
		<p class="tc"><span>关闭</span></p>
		<ul class="payTypeList">
			<%-- <c:forEach var="p1" items="${siteAll}">
				<ul>
					<c:forEach var="p" items="${p1.value.list}">
					<c:if test="${p.price_type<=3 }">
						<li class="tLi" data-text="${p.describe}" siteId="${p.site_id }" mealNum="${p.giveMeal}"
							mealType="${p.giveMealUnit }" value="${p.id}"
							reommend="${p.recommendState}" charge_type="${p.charge_type }"
							prices="${p.unit_price}" price_type="${p.price_type}"
							price_num="${p.price_num }">${p.name}</li>
					</c:if>
					<c:if test="${p.price_type>3 }">
						<li class="fLi" data-text="${p.describe}" siteId="${p.site_id }" mealNum="${p.giveMeal}"
							mealType="${p.giveMealUnit }" value="${p.id}"
							reommend="${p.recommendState}" charge_type="${p.charge_type }"
							prices="${p.unit_price}" price_type="${p.price_type}"
							price_num="${p.price_num }">${p.name}</li>
					</c:if> 
					</c:forEach>
				</ul>
				</c:forEach> --%>
		</ul>
	</div>
</div>

<div class="yeMask">
	<div class="yeBox">
		<p>您的余额不足，将使用微信支付补足</p>
		<button class="yes">确定</button><button class="no">取消</button>
	</div>
</div>
<input type="hidden" value="${openid}" id="openid">
<input type="hidden" value="${tel}" id="username">
<input type="hidden" value="${site.id}" id="siteId">
<input type="hidden" value="${times}" id="times">
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${mobilePath1}/js/payReactive.js?<%=getTimestamp%>"></script>
</body>
</html>
