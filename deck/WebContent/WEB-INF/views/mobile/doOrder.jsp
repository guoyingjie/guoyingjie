<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="http://oss.kdfwifi.net/deck/mobile" />
 
<c:set var="mobilePath1" value="${basePath}/mobile" />
<c:set var="imgPath" value="http://oss.solarsys.cn/0001/mobile_img" />
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta content="1" name="keywords" />
<meta content="1" name="description" />
<meta content="yes" name="apple-mobile-web-app-capable"/>
<meta content="yes" name="apple-touch-fullscreen"/>
<meta content="telephone=no" name="format-detection"/>
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
<title>续费</title>
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/my.css?<%=getTimestamp %>" />
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/jiaofei.css?<%=getTimestamp %>" />
<script type="text/javascript">
	var basePath="${basePath}";
	var imgPath="${imgPath}";
	var siteAll="${siteAll}";
	
function back(){
	window.history.back();
	
}
</script>
</head>

<body>
<div class="box">
	<!--页面背景-->
	<img id="bg" src="${imgPath}/bg.png" width="100%" />
    <!--页面背景-->
    <!--页面头部 start-->
	<form action="${basePath}/pay/aliPay" method="post" id="login" onsubmit="return submitCheck();">
    <div class="header"><p><span class="pic"  onclick="back();"><img src="${imgPath}/fh.png" width="50%" /></span><span class="dl">续&nbsp;&nbsp;&nbsp;&nbsp;费</span></p></div>
    <!--页面头部 end-->
    <!--购买main start-->
    <div class="xz">
    	<!--购买类型选择 start-->
    	<p class="lx">
	    	<span class="bt">购买类型:</span>

			<c:forEach var="p" items="${siteAll.list}">
				<span value="${p.id}" charge_type="${p.charge_type }" prices="${p.unit_price}" price_type="${p.price_type}" price_num="${p.price_num }" class="nr">
				${p.name}
				</span>
			</c:forEach>
			<input type="hidden" id="priceNum" name="price_num">
			<input type="hidden" id="priceConfig" name="priceConfig" value="1"/>
			<input type="hidden" id="priceName" name="price_Name">
    	</p>
        <!--购买类型选择 end-->
        
        <!--购买时长选择 start-->
        <p class="sc">
	        <span class="bt">购买时长:</span>
	        <span class="txt">
	        	<input type="text" id="nums" name="nums" value="1"/>
<!-- 	        	<input type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" value="1" /> -->
	        </span>
	        <span class="gb"  id="pconfig"></span>
        </p>
        <!--购买时长选择 end-->
        
        <!--总额：根据购买类型购买时长即时更改 start-->
        <p class="ze">总额：<span class="moneyInfo"></span></p>
        <!--总额：根据购买类型购买时长即时更改 end-->
        <p class="fs">支付方式：</p>
    </div>
   <input type="hidden" id="amount" name="amount" value="">
    
    <!--购买main end-->
    <!--支付类型选择 start-->
    <ul class="zffs">
    	<li class="on" id="alipayLi"><img class="i1" src="${imgPath}/zf.png" width="6%" /><i>支付宝支付</i><span class="on"></span><p class="intext">推荐有支付宝账号的用户使用</p></li>
        <li><img class="i3" src="${imgPath}/ym.png" width="6%" /><i>银行卡支付</i><span></span><p class="intext">无需开通网银，有卡就能支付</p></li> 
<%--         <li><img class="i2" src="${imgPath}/xyk.png" width="6%" /><i>信用卡支付</i><span></span></li> --%>
    </ul>
    <!--支付类型选择 end-->
    <!--支付类型跳转按钮 start-->
    <span class="jfdlk"><input class="btn" type="button" onclick="goPay()" value="立即支付" /></span>
    <!--支付类型跳转按钮 end-->
    </form>
    <!--页面底部-->
    <p id="cr">技术支持|北京宽东方  400-666-0050</p>
    <!--页面底部-->
</div>
</body>
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${mobilePath1 }/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${mobilePath1}/js/doOrder.js?<%=getTimestamp %>"></script>

</html>
