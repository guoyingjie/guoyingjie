<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="http://oss.kdfwifi.net/deck/mobile" />
<c:set var="jsPaths" value="${basePath}/mobile" />
<!--<c:set var="imgPath" value="http://oss.solarsys.cn/0001/mobile_img" />-->
<c:set var="imgPath" value="http://oss.solarsys.cn/0001/mobile_img"/>
<c:set var="cssPath" value="${mobilePath}/css" />
<c:set var="jsPath" value="${jsPaths}/js" />
<!doctype html>
<html>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<head>
<meta charset="utf-8">
<meta name="format-detection" content="telephone=no">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>一键认证</title>
<link href="${cssPath}/style1.css" rel="stylesheet" type="text/css?<%=getTimestamp %>">
<link rel="stylesheet" href="${cssPath}/font-awesome/css/font-awesome.min.css?<%=getTimestamp %>">
<script type="text/javascript" src="${jsPath}/jquery.js"></script>
<% 
String id = (String)request.getSession().getAttribute("siteMac");
String ip = (String)request.getSession().getAttribute("clientIp");
String mac = (String)request.getSession().getAttribute("clientMac");
String  url = (String)request.getSession().getAttribute("fromUrl");
String terminalDevice = (String)request.getSession().getAttribute("terminalDevice");
String fromFw=(String)request.getSession().getAttribute("fromFw");

StringBuffer sb = new StringBuffer();
String str = "";
sb.append(id).append("&ip=").append(ip).append("&mac=").append(mac).append("&url=").append("baidu.com").append("&terminalDevice=").append(terminalDevice);
str = sb.toString();
%>
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
var auther="<%=request.getSession().getAttribute("auther")%>";

/* var id="${id}";
var mac="${mac}";
var ip="${ip}";
var url="${url}";
var fromFw="${fromFw}";
var gw_address="${gw_address}";
var gw_port="${gw_port}";
var terminalDevice="${terminalDevice}"; */

</script>
</head>
<%  
 
    String sign = "0";  
    Cookie[] cookies=request.getCookies();  
    if(cookies!=null&&cookies.length>0){   
        //遍历Cookie  
        for(int i=0;i<cookies.length;i++){  
            Cookie cookie=cookies[i]; 
            //判断是否有sign，从而判断遮罩是否添加
            if("sign".equals(cookie.getName())){  
            	sign = cookie.getValue();
            }else{
        		Cookie cookie2 = new Cookie("sign", "1");
    			cookie.setPath("/");
    			cookie.setMaxAge(3600 * 24 * 365);
    			response.addCookie(cookie2);
            }  
        }  
    }  
    
 %>
 
<body>

<header class="header">
    <h1 class="ui_tit">一键认证</h1>
   <!--  <a onclick="window.history.back();"><i class="icon-angle-left"></i></a> -->
    <a class="pers">${telph}</a>
</header>
<div class="logo"></div>
		<!-- 从Session里边取得值 -->
		<input type="hidden" value="${mac}" id="mac"/>
		<input style="display:none;" id="num" name="num"  value="${pu.userName }"/>
<p class="greet"></p>
<div class="line"></div>
<a  class="oauth" >立即上网</a>
<%-- <a  class="oauth" href="${basePath}/immediateToInternet?id=<%=str %>">立即上网</a> --%>
<footer class="fixd">技术支持|北京宽东方&nbsp;&nbsp;联系电话&nbsp;400-666-0050</footer>
<div class="mask">
	<div class="hint">
		<button class="clos"></button>
	</div>
	<div class="warn">
		<p class="wanr-text">提示文字</p>
		<div class="buttons">
			<button id="sure">继续登录</button>
			<button id="cancel">取消登录</button>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(function(){
		var sign = '<%=sign %>';
		if(0 == sign){
			var pHeight=$(window).height();
			$('.mask').height(pHeight);
			$(".mask").fadeIn(1000);
			setTimeout(function(){
				$('.hint').css('display','block');
				$('.warn').css('display','none');
			},1000);
			$('.clos').click(function(){
				$('.mask').css('display','none');
			});
		}
	});
	
	
</script>
</body>
<script type="text/javascript" src="${jsPath}/auther.js?<%=getTimestamp %>"></script>
</html>
