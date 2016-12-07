<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="pcPath" value="${basePath}/pc" />
<c:set var="imgPath" value="http://oss.solarsys.cn/0001/pc_img" />
<c:set var="cssPath" value="http://oss.kdfwifi.net/deck/pc/css" />
<c:set var="jsPath" value="${pcPath}/js" />
<!DOCTYPE html>
<html>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<meta content="1" name="keywords" />
<meta content="1" name="description" />
<meta content="yes" name="apple-mobile-web-app-capable"/>
<meta content="yes" name="apple-touch-fullscreen"/>
<meta content="telephone=no" name="format-detection"/>
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
<title>修改成功</title>
<link rel="stylesheet" type="text/css" href="${cssPath}/my.css?<%=getTimestamp %>" />
<link rel="stylesheet" type="text/css" href="${cssPath}/style.css?<%=getTimestamp %>" />
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
<% 
String id = (String)request.getSession().getAttribute("siteMac");
String ip = (String)request.getSession().getAttribute("clientIp");
String mac = (String)request.getSession().getAttribute("clientMac");
String  url = (String)request.getSession().getAttribute("fromUrl");
String fromFw=(String)request.getSession().getAttribute("fromFw");
String gw_address=(String)request.getSession().getAttribute("gw_address");
String gw_port=(String)request.getSession().getAttribute("gw_port");
String terminalDevice = (String)request.getSession().getAttribute("terminalDevice");
StringBuffer sb = new StringBuffer();
String str = "";

%>
 
 
<body class="tybody">
<div class="fog-w win">
	<div class="tru"></div>
	<span>修改成功</span>
	<p><span class='sec' id="sec">5</span>秒后自动跳转</p>
	<a href="javascript:;" onclick="goJump();">立即跳转</a>
</div>
<footer>技术支持|北京宽东方&nbsp;&nbsp;&nbsp;&nbsp;联系电话&nbsp;&nbsp;400-666-0050</footer>
 
</body>

<script type="text/javascript">
 
	
	var basePath="${basePath}";
	var id="<%=request.getSession().getAttribute("siteMac")%>";
	var mac="<%=request.getSession().getAttribute("clientMac")%>";
	var ip="<%=request.getSession().getAttribute("clientIp")%>";
	var url="<%=request.getSession().getAttribute("fromUrl")%>";
	var terminalDevice ="<%=request.getSession().getAttribute("terminalDevice")%>";
	var fromFw="${fromFw}";
	var gw_address="${gw_address}";
	var gw_port="${gw_port}";
	var loginUrl;
	if(fromFw!="null"&&fromFw!=""&&fromFw!=null&&fromFw!=undefined){
		loginUrl="/w/r/?gw_id="+id+"&ip="+ip+"&mac="+mac+"&url="+url+"&terminalDevice="+terminalDevice+"&gw_address="+gw_address+"&gw_port="+gw_port;
	}else{
		loginUrl="/toLogin?id="+id+"&&ip="+ip+"&mac="+mac+"&url="+url+"&terminalDevice="+terminalDevice;
		
	}
	var ace=document.getElementById("sec");
	var sec=ace.innerHTML;
	var timer=setInterval(function(){
		sec--;
		if(sec<=0){
			clearInterval(timer);
			window.location.href=basePath+loginUrl;
		}
		ace.innerHTML=sec;
	},1000);
	function goJump(){
		window.location.href=basePath+loginUrl;
	}
</script>
</html>
