<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="http://oss.kdfwifi.net/deck/wechat" />
 <c:set var="mobile" value="${basePath}/mobile" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>Title</title>
	<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${mobilePath}/css/public.css">
<script type="text/javascript">
	var ctx = "${basePath}";
</script>
</head>
<body>
<header class="header">
    <div class="back"><img src="${mobilePath}/img/back.png"/><span class="font-size-ss backText">返回</span></div>
    <span class="font-size-L">Wi-Fi下线</span>
</header>
<div class="place font-size-s"><span>所在场所：</span><span class="font-color">${siteName}</span></div>

<div class="box">
    <img class="cryImg" src="${mobilePath}/img/cry.png"/>
    <p class="mgn-t-50 font-size-L ">是否确认下线？</p>
    <p class="mgn-t-20 font-size-s ">确认后您的设备将立即断网</p>
    <div class="text-align mgn-t-50"><button class="offBtn font-size-s">下线</button></div>
</div>
<input type="hidden" value="${url }" id="typeurl">

<script type="text/javascript">
	$(function(){
		$(".offBtn").click(function(){
			    var self = this;
	            self.setAttribute("disabled", true);
	            self.style.backgroundColor = '#888';
	            var wait = 5;
	            self.time = setInterval(function () {
	                if (wait === 0) {
	              window.location.href=ctx+"/weChatPublicNum/getSuccess";
	                  self.removeAttribute("disabled");
	                    self.style.backgroundColor = '#1a9bd2';
	                    self.innerText = '下线';
	                    clearInterval(self.time)
	                }else {
	                    wait--;
	                    self.innerText = '下线(' + wait + ')';
	                }
	            }, 1000);
	    })
	})
</script>

</body>

</html>