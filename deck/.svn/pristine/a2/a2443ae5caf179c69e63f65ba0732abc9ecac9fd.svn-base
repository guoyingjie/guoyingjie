<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath1" value="${basePath}/mobile" />
<c:set var="mobilePath" value="http://oss.kdfwifi.net/deck/mobile" />
 
<c:set var="imgPath" value="http://oss.solarsys.cn/0001/mobile_img" />
<c:set var="imgsPath" value="http://oss.solarsys.cn/0001/mobile_img/mm.png" />
<c:set var="cssPath" value="${mobilePath}/css" />
<c:set var="jsPath" value="${mobilePath1}/js" />
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta content="1" name="keywords" />
<meta content="1" name="description" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="yes" name="apple-touch-fullscreen" />
<meta content="telephone=no" name="format-detection" />
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
<title>修改密码</title>
<link rel="stylesheet" type="text/css" href="${cssPath}/my.css" />
<link rel="stylesheet" type="text/css" href="${cssPath}/style.css" />
<script type="text/javascript">
var basePath="${basePath}";
var imgPath="${imgPath}";
var id="<%=request.getSession().getAttribute("siteMac")%>";
var mac="<%=request.getSession().getAttribute("clientMac")%>";
var ip="<%=request.getSession().getAttribute("clientIp")%>";
var url="<%=request.getSession().getAttribute("fromUrl")%>"; 
var fromFw="<%=request.getSession().getAttribute("fromFw")%>";
var gw_address="<%=request.getSession().getAttribute("gw_address")%>";
var gw_port="<%=request.getSession().getAttribute("gw_port")%>";
var terminalDevice="<%=request.getSession().getAttribute("terminalDevice")%>";

</script>
</head>
<body>
 
	<div class="box">
		<!--页面背景-->
		<img src="${imgPath}/bg.png" id="bg" width="100%"/> 
		<!--页面背景-->
		<!--页面头部 start-->
		<div class="header">
			<p>
				<span class="pic"><img src="${imgPath}/fh.png" width="50%" /></span>
				<span class="dl">修改密码</span>
			</p>
		</div>
		<!--页面头部 end-->
		<!--找回密码表单 start-->
		<form class="zhform" id="zhforms">
			<!--手机号 start-->
			<p>
				<img class="tu1" src="${imgPath}/pone.png" width="5%" />
				<input disabled="true" value="${telph}" placeholder="" type="text" data-validate="telephone" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
					<input style="display:none;" id="num" name="num"  value="${pu.userName }"/>
					<span></span>
				<span id="sj" style="display: none;">请填写正确的手机号</span>
				<span id="no" style="display: none;">手机号不存在，请重新输入</span>
				<span id="pIsNull" style="display: none;">请输入手机号</span>
			</p>
			<!--手机号 end-->
			<!--密码 start-->
			<p class="x">
				<img class="tu2" src="${imgPath}/lock.png" width="5%" />
				<input id="pwd" type="password"  name="pwd"
				  placeholder="4-16位，支持数字、密码、字符组合"  maxlength="16" onkeyup="this.value=this.value.replace(/[^\w]/g,'');"/><span></span>
					<img id="tu" class="tu3" src="${imgsPath}" width="6%" />
					<span id="mi" style="display: none;">请输入正确密码格式</span>
			</p>
			<!--密码 end-->
			<!--获取验证码、并输入 start-->
			<p class="yz">
				<img  class="tu4" src="${imgPath}/yz.png" width="6%" />
				<input id="ma" class="ma" name="ma" type="tel"  placeholder="验证码" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
				<button class="yzm" type="button" id="sendCode">获取手机验证码</button>
				<span id="errorMsg"></span>
			</p>
			<!--获取验证码、并输入 end-->
			<!--提交找回密码表单 start-->
			<span class="dlk">
			<input id="dl" type="button" value="确&nbsp;认&nbsp;更&nbsp;改" />
			</span>
			<!--提交找回密码表单 end-->
		</form>
		<!--找回密码表单 end-->
		<!--页面底部-->
		<p id="cr">技术支持|北京宽东方 400-666-0050</p>
		<!--页面底部-->
	</div>
</body>
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${jsPath }/jquery.validate.min.js"></script>
<script type="text/javascript" src="${jsPath}/changePwd.js?<%=getTimestamp %>"></script>
<script type="text/javascript" src="${jsPath}/floatAlert.js?<%=getTimestamp %>"></script>
</html>
