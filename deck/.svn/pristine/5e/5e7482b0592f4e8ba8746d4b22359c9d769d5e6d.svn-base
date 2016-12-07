<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="pcPath" value="${basePath}/pc" />
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
<title>找回密码</title>
	<link rel="stylesheet" type="text/css" href="${cssPath}/style.css?<%=getTimestamp %>">
	
	<script type="text/javascript">
 	var basePath="${basePath}";
	var id="<%=request.getSession().getAttribute("siteMac")%>";
	var mac="<%=request.getSession().getAttribute("clientMac")%>";
	var ip="<%=request.getSession().getAttribute("clientIp")%>";
	var url="<%=request.getSession().getAttribute("fromUrl")%>";
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


<script type="text/javascript">
var basePath = "${basePath}";
</script>
</head>
<body>
<div class="ui-reg-main">
	<form id="xgform">
		<p class="ui-user">
			手机号
			<input class="ui-tel" type="tel" value="" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="11" placeholder="请输入手机号"  id="num" name="num"><!-- 手机号输入框 -->
			<span class="tel-hint" id="sj"></span><!-- 错误提示 -->
		</p>
		<p class="ui-pwd">
			新密码
			<input class="ui-pwd" type="password" value="" id="pwd" name="pwd" placeholder="请输入密码"><!-- 密码输入框 -->
			<input class="ui-tex" type="text" value="" placeholder="请输入密码"><!-- 密码输入框 -->
			
			<i class="eye"></i>
			<span class="pwd-hint" id="mi"></span><!-- 错误提示 -->
			
		</p>
		<p class="ui-test">
			验证码
			<input class="ui-test" type="text" value=""  maxlength="4"  id="yz" name="yz" placeholder="请输入验证码"><!-- 验证码输入框 -->
			<button class="ui-btn" type="button" value="获取验证码" id="obtainCode">获取验证码</button><!-- 获取密码按钮 -->
			<span class="test-hint" id="errorMsg"></span><!-- 错误提示 -->
		</p>
		<button class="ui-sub" type="button"  id="xgtj">修改密码</button><!-- 提交表单 -->
	</form>
</div>
<footer>技术支持|北京宽东方&nbsp;&nbsp;&nbsp;&nbsp;联系电话&nbsp;&nbsp;400-666-0050</footer>
</body>
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${basePath}/pc/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${basePath}/pc/js/xgmm.js?<%=getTimestamp %>"></script>
<script type="text/javascript" src="${basePath}/pc/js/floatAlert.js?<%=getTimestamp %>"></script>
</html>



