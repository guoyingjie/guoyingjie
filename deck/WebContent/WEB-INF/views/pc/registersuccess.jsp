<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date" %>
  <c:set var="basePath" value="${pageContext.request.contextPath}" />
  <c:set var="pcPath" value="${basePath }/pc" />
  <!DOCTYPE html>
<html>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit">
<title>注册成功</title>
<link rel="stylesheet" href="${pcPath}/css/my.css?<%=getTimestamp %>" />
<link rel="stylesheet" href="${pcPath}/css/style.css?<%=getTimestamp %>" />
<script type="text/javascript">
	var basePath="${basePath}";
</script>

<script> 
	   (function() {
			 if (! 
			 /*@cc_on!@*/
			 0) return;
			 var e = "abbr, article, aside, audio, canvas, datalist, details, dialog, eventsource, figure, footer, header, hgroup, mark, menu, meter, nav, output, progress, section, time, video".split(', ');
			 var i= e.length;
			 while (i--){
				 document.createElement(e[i])
			 } 
		})() 
	</script>

</head>

<body class="tybody">
<div class="reg-w win">
	<div class="tru"></div>
	<span>注册成功</span>
	<p><span class="sec" id="sec">5</span>秒后自动跳转</p>
	<a href="javascript:;" onclick="tojump()">立即跳转</a>
</div>
<footer>技术支持|北京宽东方&nbsp;&nbsp;&nbsp;&nbsp;联系电话&nbsp;&nbsp;400-666-0050</footer>
<script language="javascript">
	var ace=document.getElementById("sec");
	var sec=ace.innerHTML;
	var timer=setInterval(function(){
		sec--;
		if(sec<=0){
			clearInterval(timer);
			window.location.href=basePath+"/toPay";
		}
		ace.innerHTML=sec;
	},1000);
	
	function tojump(){
		window.location.href=basePath+"/toPay";
	}
	
	
</script>
</body>
</html>