<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="pcPath" value="${basePath}/pc" />
<c:set var="cssPath" value="${pcPath}/css" />
<c:set var="jsPath" value="${pcPath}/js" />
<!DOCTYPE html>
<html>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit">
<title>缴费</title>
<link rel="stylesheet" type="text/css" href="${cssPath}/style.css?<%=getTimestamp %>">
<link rel="stylesheet" type="text/css" href="${cssPath}/font-awesome/css/font-awesome.min.css?<%=getTimestamp %>">
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>

<script type="text/javascript">
    var basePath= "${basePath}";
    var p="${siteAll.list}";
    var ipAddr="<%=request.getRemoteAddr()%>";
	function back(){
		window.history.back();
	}
	
</script>
<script> 
	   (function() {
			 if (! 
			 /*@cc_on!@*/
			 0) return;
			 var e = "abbr, article, aside, audio, canvas, datalist, details, dialog, eventsource, figure, footer, header, hgroup, mark, menu, meter, nav, output, progress, section, time, video".split(', ');
			 var i= e.length;
			 while (i--){
				 document.createElement(e[i]);
			 } 
		})() 
	</script>


</head>

<body>
<div class="ui-pay-main">
	<form>
		<div class="time">
			购买时长
			<input class="duration" id="num" type="tel" value="1" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"><!-- 购买数量 -->
			<span class="date"><i class="fn-pu icon-chevron-down"></i></span><!-- 购买类型 根据可选类型随时更改类型 -->
			<input type="hidden" id="priceNum">
			<ul class="ul-list" id="rq"><!-- 可选类型 -->
			<c:forEach var="p" items="${siteAll.list}">
			<!-- 非融合套餐 -->
				<%-- <li value="${p.id}" prices="${p.unit_price}">${p.price_num}</li> --%>
			<%-- <c:if test="${p.price_type== 0}"><li value="${p.id}" prices="${p.unit_price}">小时</li></c:if>
			<c:if test="${p.price_type== 1}"><li value="${p.id}" prices="${p.unit_price}">包12天</li></c:if>
			<c:if test="${p.price_type== 2}"><li value="${p.id}" prices="${p.unit_price}">月</li></c:if>
			<c:if test="${p.price_type== 3}"><li value="${p.id}" prices="${p.unit_price}">包年</li></c:if>
			<c:if test="${p.price_type== 4}"><li value="${p.id}" prices="${p.unit_price}">包2年</li></c:if>
			<!-- 非融合套餐 -- end -->
			
			<!-- 融合套餐 -->
			<c:if test="${p.price_type== 5}"><li value="${p.id}" prices="${p.unit_price}">小时</li></c:if>
			<c:if test="${p.price_type== 6}"><li value="${p.id}" prices="${p.unit_price}">天</li></c:if>
			<c:if test="${p.price_type== 7}"><li value="${p.id}" prices="${p.unit_price}">月</li></c:if>
			<c:if test="${p.price_type== 8}"><li value="${p.id}" prices="${p.unit_price}">包年</li></c:if>
			<c:if test="${p.price_type== 9}"><li value="${p.id}" prices="${p.unit_price}">包2年</li></c:if>
			<!-- 融合套餐 -- end--> --%>
			<li value="${p.id}" types="${p.charge_type }" prices="${p.unit_price}" pricenum="${p.price_num}">${p.name}</li>
			</c:forEach>
			</ul>
			<span class="buy-num">请输入购买数量</span>
			 <!--选择购买单位 end-->
   			<input type="hidden" id="priceConfig" name="priceConfig" value="1"/>
		</div>
		<div class="ui-money">总额<span id="je">20.00元</span></div><!-- 根据购买数量/类型随时更改总额 -->
		 <input type="hidden" id="amount" name="amount" value="">
		<nav class="nav"><!-- 选择支付方式 start -->
			<ul class="payment">
				<li class="on" id="zfli"><span class="ui-y"><i></i></span><span class="alipay"></span>支付宝支付</li>
				<li class="c"><span class="ui-y"><i></i></span><span class="banc"></span>银行卡支付</li>
				<!-- <li class="cxin"><span class="ui-y"><i></i></span><span class="weixin"></span>微信支付</li> -->
			</ul>
		</nav><!-- 选择支付方式 end -->
		<button type="button" class="pay-btn">立即支付</button><!-- 提交表单按钮 -->
	</form>
</div>
 
<footer>技术支持|北京宽东方&nbsp;&nbsp;&nbsp;&nbsp;联系电话&nbsp;&nbsp;400-666-0050</footer>
</body>
<script type="text/javascript" src="${jsPath}/jiaofei.js?<%=getTimestamp %>"></script>
</html>




