<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="basePath" value="http://oss.kdfwifi.net/deck" />
 
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
	<title></title>
	<link rel="stylesheet" type="text/css" href="${basePath}/mobile/css/newcss/public.css">
	<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0" />
	<meta name="format-detection" content="telephone=no" />
	<meta name="MobileOptimized" content="320"/>
	<style type="text/css">
		#down{
			width: 34px;
			height: 34px;
			position: fixed;
			top: 15px;
			right: 15px;
			background: rgba(255,255,255,.8);
			display: inline-block;
			color: #666;
			text-align: center;
			line-height: 34px;
			border-radius: 50%;
			z-index: 5;
			font-size: 1.2em;
		}
		#down:before{
			width: 45px;
			height: 45px;
			background: rgba(255,255,255,.5);
			position: absolute;
			z-index: 4;
			display: inline-block;
			content: '';
			border-radius: 50%;
			top: -5px;
			left: -5px;
		}
	</style>
</head>
<body>
<span id="down">3</span>
<img id="pic" src="http://oss.kdfwifi.net/school_pic/portal.jpg">
<script type="text/javascript">
	var imgArr = ['http://oss.kdfwifi.net/school_pic/portal.jpg','http://oss.kdfwifi.net/school_pic/portal2.jpg','http://oss.kdfwifi.net/school_pic/portal3.jpg'];
	document.getElementById('pic').src = imgArr[random(3)];
	var sObj = document.getElementById('down');
	var time = setInterval(function(){
		var n = sObj.innerHTML;
		sObj.innerHTML = --n;
		if(n<=0){
			clearInterval(time);
			toLogin();
		}
	},1000);
	function random(max){// 返回随机数 @max代表最大数
		while (true){
			var n = parseInt(Math.random()*10);
			if(n<max){
				console.log(n)
				return n;
			}
		}
	}
	function toLogin() {
		var b = window.location.href;
		var c = b.indexOf("homeurl");
		if (c > 0) {
			var a = b.substring(c).split("=");
			window.location.href = decodeURIComponent(a[1])
					//	+"?gw_address=192.168.1.1&gw_port=2060&gw_id=ACA2139FF0A0&ip=192.168.0.132&mac=08:57:00:0a:99:c8&url=234&homeurl=http://edu1.solarsys.cn/deck";
					+ "?id=ACA2139FF0A1&mac=08:57:00:0a:99:c8&ip=192.168.0.132&url=234&homeurl=http://charge.solarsys.cn/deck";
	
		} else {
			var obj=window.location; 
			var contextPath=obj.pathname.split("/")[1]; 
			var basePath=obj.protocol+"//"+obj.host+"/"+contextPath; 
			window.location.href = basePath+"/w/r/"+window.location.search;
		}
	}
</script>
</body>
</html>