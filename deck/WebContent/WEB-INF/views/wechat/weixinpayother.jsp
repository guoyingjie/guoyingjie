<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="ctx" value="http://oss.kdfwifi.net/deck" />
<c:set var="ctx1" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link href="${ctx}/img/img/favicon.ico" type="image/x-icon" rel="shortcut icon">
<link rel="stylesheet" type="text/css" href="${ctx}/mobile/css/zhifu.css">
<title>缴费处理</title>
<script src="${ctx1}/mobile/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
var basePath="${ctx1}";
</script>
</head>
<body>
	<header class="header">宽东方Wi-Fi帐户中心</header>
	<div class="cont">
		<h1 style="color: #44cb32;">缴费处理中···</h1>
		<span class="succ">···</span>
		<p class="info"></p>
	</div>
	<div class="remind">
		<span>温馨提示：</span>
		<p>1、请勿将您的帐号借于他人使用，会有一定机率的帐户锁定风险；</p>
		<p>2、您在上网过程中遇到的任何问题，都可关注宽东方WiFi服务公众号“宽未来”获得帮助；</p>
		<p>3、由于ios系统原因，个别iphone用户连接WiFi后不会自动弹跳认证页面，请重启手机即可解决。</p>
	</div>
	 
	<script type="text/javascript">
  	function onBridgeReady(){
  	   var order = "<%=pageContext.getSession().getAttribute("outTradeNo")%>";
  	   WeixinJSBridge.invoke('getBrandWCPayRequest', 
  	         {
	  	    	 "appId" : "<%=pageContext.getSession().getAttribute("appId")%>",
	  	  		 "timeStamp" :"<%=pageContext.getSession().getAttribute("timeStamp")%>",
	  	  		 "nonceStr" : "<%=pageContext.getSession().getAttribute("nonceStr")%>",
	  	  		 "package" : "<%=pageContext.getSession().getAttribute("package")%>",
	  	  		 "signType" : "MD5",
	  	  		 "paySign" : "<%=pageContext.getSession().getAttribute("paySign")%>"
  	  		 },
  	  		  function(res) {
					 if (res.err_msg == "get_brand_wcpay_request:ok") {
						window.location.href=basePath+"/weChatPayOther/getUserStatus?outTradeNo="+order; 
					} else if (res.err_msg == "get_brand_wcpay_request:cancel") {
						window.location.href=basePath+"/weChatPayOther/getUserStatus?outTradeNo="+order;
					} else {
						window.location.href=basePath+"/weChatPayOther/getUserStatus?outTradeNo="+order;
					} ;
					
		 	  });				
		};

		if (typeof WeixinJSBridge == "undefined") {
			if (document.addEventListener) {
				document.addEventListener('WeixinJSBridgeReady', onBridgeReady,false);
			} else if (document.attachEvent) {
				document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
				document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
			}
		} else {
			onBridgeReady();
		};
		
	</script>
	
</body>
</html>