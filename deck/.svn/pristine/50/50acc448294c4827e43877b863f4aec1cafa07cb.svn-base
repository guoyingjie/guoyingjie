<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ page import="java.util.Date" %>
  <c:set var="basePath" value="${pageContext.request.contextPath}" />
  <c:set var="pcPath" value="http://oss.kdfwifi.net/deck/pc" />
<!DOCTYPE html>
<html>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit">
<title>缴费成功</title>
<link rel="stylesheet" href="${pcPath}/css/my.css?<%=getTimestamp %>" />
<link rel="stylesheet" href="${pcPath}/css/style.css?<%=getTimestamp %>" />
<script language="javascript" src="./pc/js/script.js"></script>
<script type="text/javascript">
	var basePath="${basePath}";
</script>
</head>

<body class="tybody">
<div class="ch"></div>
<span class="cg">缴费成功</span>
<p class="tz"><span id="sec">5</span>秒后自动跳转</p>
<a class="djtz" href='http://www.gonet.cc'>立即跳转</a>
<div class="tyjszc">技术支持|北京宽东方&nbsp;&nbsp;&nbsp;&nbsp;联系电话&nbsp;&nbsp;400-666-0050</div>
<script language="javascript">
	var ace=document.getElementById("sec");
	var sec=ace.innerHTML;
	var timer=setInterval(function(){
		sec--;
		if(sec<=0){
			clearInterval(timer);
			window.location.href="http://www.gonet.cc";
		}
		ace.innerHTML=sec;
	},1000);
</script>
</body>
</html>