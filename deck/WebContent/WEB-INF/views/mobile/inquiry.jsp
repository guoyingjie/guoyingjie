<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="http://oss.kdfwifi.net/deck/mobile" />
 
<c:set var="mobilePath1" value="${basePath}/mobile" />
 
<!DOCTYPE html>
<html>
<head>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<meta charset="utf-8">
<title>余额查询</title>
<meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link href="${mobilePath}/img/img/favicon.ico" type="image/x-icon" rel="shortcut icon">
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/zhifu.css?<%=getTimestamp %>">
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/weui.min.css?<%=getTimestamp %>">
<script type="text/javascript">
    var basePath= "${basePath}";
</script>
</head>

<body>
<header class="header">宽东方Wi-Fi帐户中心</header>
<div class="content">
	<p class="title">查询剩余上网时长及流量</p>
	<div class="phoneText"><label>上网帐户</label><input id="phone" type="tel" value="" placeholder="请输入需查询的手机号"></div>
	<button class="btn" type="button">开始查询</button>
</div>
<div class="quireResult">
	<div class="siteName"></div>
	<div class="round">
		<div class="animate"></div>
		<span>剩余时长</span>
		<p class="time"></p>
		上网流量不限
	</div>
	<p class="btns"><button>继续查询</button>|<button>续费充值</button></p>
</div>
<footer>KDFWIFI.COM  2013-2016</footer>
<div class="mask">
	<div class="popup">
        请选择场所
        <span class="close"></span>
        <ul class="chargeList">
            
        </ul>
    </div>
</div>

<!-- 微信样式 -->
<div id="loadingToast" class="weui_loading_toast" style="display:none;">
   <div class="weui_mask_transparent"></div>
   <div class="weui_toast">
       <div class="weui_loading">
           <!-- :) -->
           <div class="weui_loading_leaf weui_loading_leaf_0"></div>
           <div class="weui_loading_leaf weui_loading_leaf_1"></div>
           <div class="weui_loading_leaf weui_loading_leaf_2"></div>
           <div class="weui_loading_leaf weui_loading_leaf_3"></div>
           <div class="weui_loading_leaf weui_loading_leaf_4"></div>
           <div class="weui_loading_leaf weui_loading_leaf_5"></div>
           <div class="weui_loading_leaf weui_loading_leaf_6"></div>
           <div class="weui_loading_leaf weui_loading_leaf_7"></div>
           <div class="weui_loading_leaf weui_loading_leaf_8"></div>
           <div class="weui_loading_leaf weui_loading_leaf_9"></div>
           <div class="weui_loading_leaf weui_loading_leaf_10"></div>
           <div class="weui_loading_leaf weui_loading_leaf_11"></div>
       </div>
       <p class="weui_toast_content">查询中</p>
   </div>
</div>
<div class="weui_dialog_alert" style="display: none;">
   <div class="weui_mask"></div>
   <div class="weui_dialog">
       <div class="weui_dialog_hd"><strong class="weui_dialog_title">弹窗标题</strong></div>
       <div class="weui_dialog_bd">弹窗内容，告知当前页面信息等</div>
       <div class="weui_dialog_ft">
           <a class="weui_btn_dialog primary">确定</a>
       </div>
   </div>
</div>
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${mobilePath1}/js/inquire.js?<%=getTimestamp %>"></script>
 
</body>
</html>