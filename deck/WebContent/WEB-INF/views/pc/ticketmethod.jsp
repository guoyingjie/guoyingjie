<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="cssPath" value="http://oss.kdfwifi.net/deck/pc/css" />
<c:set var="jsPath" value="${basePath}/pc/js" />
<c:set var="imgPath" value="http://oss.kdfwifi.net/deck/pc/img" />
<!DOCTYPE html>
<html>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<script type="text/javascript">
var ctx = "${basePath}";
var imgPath="${imgPath}"
</script>
<head>
	<meta charset="utf-8" />
	<title>彩票</title>
	<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0" />
	<meta name="format-detection" content="telephone=no" />
	<meta name="MobileOptimized" content="320"/>
	<link rel="stylesheet" type="text/css" href="${cssPath}/newcss/public.css?<%=getTimestamp %>">
</head>
		<style type="text/css">
		.tm-cont{
			margin: 20px;
		}
		.tm-cont h3{
			margin:10px 0;
			font-size: 18px;
		}
		.tm-cont p{
			margin-left: 5px;
			font-size: 14px;
			color: #666;
		}
		.tm-cont p a{
			color: #008CEE;
		}
		.tm-help{
			float: right;
			margin-right: 10px;
			margin-top: 30px;
			font-size: 12px;
			color: #008CEE;
		}
	</style>
	<body>
		<div class="tm-cont">
			<h3>兑奖方法</h3>
			<p>双色球由中福彩中心每周二、周四、周日开奖统一开奖。</p>
			<p>查看是否中奖，请前往<a href="http://www.cwl.gov.cn">中国福利彩票</a>官方网站－开奖公告处查询。</p>
			<h3 style="margin-top: 30px;">兑奖流程</h3>
			<p>如果您所选择的双色球号码与当批开奖号码一致，说明您已中奖。请您自开奖之日起60个自然日内与北京宽东方信息技术有限公司取得联系，进行兑奖。</p>
			<p>如果60个自然日内未与我们取得联系，视为您弃奖，弃奖奖金纳入彩票公益金。
兑奖机构可以查验您的身份证等有效信息，对奖时您应予配合</p>
			<span class="tm-help">官方兑奖流程及帮助</a>
		</div>
<input type="hidden" value="${proUser.userName}" id="username">
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${jsPath}/newjs/ticket.js?<%=getTimestamp %>"></script>

</html>