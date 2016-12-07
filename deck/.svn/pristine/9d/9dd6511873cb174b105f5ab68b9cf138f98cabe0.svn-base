<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="http://oss.kdfwifi.net/deck/mobile" />
 
<c:set var="mobilePath1" value="${basePath}/mobile" />
<!DOCTYPE html>
<html>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<head>
<meta charset="utf-8">
<title>登录</title>
<meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/newcss/common.css?<%=getTimestamp %>">
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/newcss/login.css?<%=getTimestamp %>">
<script type="text/javascript">
		var basePath="${basePath}";
		var id="${siteMac}";
		var gw_id="${gw_id}";
		var mac="${clientMac}";
		var ip="${clientIp}";
		var url="${fromUrl}";
		var fromFw="${fromFw}";
		var gw_address="${gw_address}";
		var gw_port="${gw_port}";
		 
</script>
</head>
<body>
<div class="banner">
     <%-- <c:if test="${!empty bannerUrl}"><img src="${bannerUrl}" width="100%"/></c:if>
     <c:if test="${empty bannerUrl}"><img src="${mobilePath}/img/newimg/banner.jpg" width="100%"></c:if>
      --%>
</div>
<form class="login">
	<input type="hidden" value="${bannerUrl}" id="banner"/>
	<input class="userName" type="tel" value="" placeholder="登录用户名" maxlength="11" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
	<input class="userPass" type="password" name="" value="" maxlength="16"  placeholder="登录密码">
	<button class="toLogin" type="button">登&nbsp;&nbsp;&nbsp;&nbsp;录</button>
	<a class="toForget" >忘记密码？</a><a class="toRegister">创建一个账号＞</a>
	    <input type="hidden" value="${siteMac}" id="id"/>
		<input type="hidden" value="${clientMac}" id="mac"/>
		<input type="hidden" value="${clientIp}" id="ip"/>
		<input type="hidden" value="${fromUrl}" id="url"/>
		<input type="hidden" value="${bannerUrl}" id="banner"/>
		<input type="hidden" value="" id="passwordMd5" />
</form>
<p class="terms">登录视为您同意<a id="terms">《服务条款》</a></p>
<footer>
	<p>本Wi-Fi由以下机构联合提供<br>【${site.site_name}/北京宽东方】</p>
	<p class="copyRight">KDFWIFI.COM 2013-2016</p>
</footer>
<div class="altMask">
	<div>
		<span></span>
		<p class="msg"></p>
	</div>
</div>
 <div class="log-mask">
		<!-- <p>登录中</p> -->
		<div class="loader">登录中...</div>
		<div class="warn">
			<p class="wanr-text">提示文字</p>
			<div class="buttons">
				<button id="sure">继续登录</button>
				<button id="cancel">取消登录</button>
			</div>
		</div>
	</div>
<div class="gif" style="display:none"></div>
<div class="logMask">
	<div class="content">
		<h4>该账号已登录！</h4>
		<p>请使用当前登录设备<span class="mac">7451BA3D8B1E</span>的浏览器访问！<span class="url">http://10.5.50.1:80/logout</span>退出登陆后，再在新设备上登录</p>
		<button type="button" class="iKnow">我知道了</button>
	</div>
</div>
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${mobilePath1}/js/newjs/login.js?<%=getTimestamp %>"></script>
<script type="text/javascript">
	var chillireturn = '<%=(String)request.getSession().getAttribute("chillilreturn")%>';
	if(chillireturn !='null' && chillireturn.length>1){

		var data=JSON.parse(chillireturn);
		//$(".gif").css('display', 'block');
		if(data.code == 201){
			msg(0, data.data);
			$('.log-mask').css('display', 'none');
		}else	if(data.code == 205){
			msg(0, data.data);
			$('.log-mask').css('display', 'none');
		}else	if(data.code == 209){
			msg(0, data.data);
			$('.log-mask').css('display', 'none');
		}else if(data.code == 202){
			  window.location.href=basePath +"/w/toPay";
		}else if(data.code == 300){
			var MAC=data.data[0].MAC.replace(/:/g,"");
			var model=data.data[0].Terminal_device;
			var obj= new repeatAlert();
			if(model!=0){
				obj.open("设备<span>"+model+" 设备地址"+MAC+"</span>正在使用该账号,是否继续登录!<br>(继续登录则设备<span>"+MAC+"</span>会强制下线)");
			}else{
				obj.open("设备<span>"+MAC+"</span>正在使用该账号,是否继续登录!<br>(继续登录则设备<span>"+MAC+"</span>会强制下线)");
			}
			$("#sure").click(function(){
				var successUrl="/w/goToWherePage";
				var	payurl = "/w/toPay";
				Jump(data,successUrl,payurl);
			});	
			$("#cancel").click(function(){
				$(".log-mask").hide();
			});	
		}else if(data.code==203){
			  window.location.href=basePath +"/w/jinggao";
		}else if(data.code==200){
			var successUrl="/w/goToWherePage";
			var	payurl = "/w/toPay";
			Jump(data,successUrl,payurl);
		}else if(data.code == 301){
			var obj= new repeatAlert();
			//var MAC=data.data[0].callingstationid.replace(/:/g,"");
			var model=data.data;
			//var nasipaddress = data.data[0].nasipaddress;
			var uamport = '3990';
			//var urllogoff = "http://"+nasipaddress+":"+uamport+"/logoff";
			var urllogoff = "http://logout";
			if(model!=0){
				$('.mac').text(model);
				$('.url').text(urllogoff);
				$('.logMask').css('display','block');
				/* obj.open("该账号已登录！请使用当前登录设备<span>"+model+"</span>的浏览器访问!<br><span>"+urllogoff+"</span>退出登录后，再在新设备上登录"); */
			}else{
				$('.mac').text(MAC);
				$('.url').text(urllogoff);
				$('.logMask').css('display','block');
				/* obj.open("该账号已登录！请使用当前登录设备<span>"+MAC+"</span>的浏览器访问!<br><span>"+urllogoff+"</span>退出登录后，再在新设备上登录"); */
			}
			$('.iKnow').click(function(){
				 window.location.href=urllogoff;
					$(".logMask").hide();
			});
			}else if(data.code == 302){
				var obj= new repeatAlert();
				//var MAC=data.callingmac.replace(/:/g,"");
				var model=data.data;
				//var nasipaddress = data.uamip;
				var urllogoff = "http://10.5.50.1/logout";
				if(model!=0){
					$('.mac').text(model);
					$('.url').text(urllogoff);
					$('.logMask').css('display','block');
					/* obj.open("该账号已登录！请使用当前登录设备<span>"+model+"</span>的浏览器访问!<br><span>"+urllogoff+"</span>退出登录后，再在新设备上登录"); */
				}else{
					$('.mac').text(MAC);
					$('.url').text(urllogoff);
					$('.logMask').css('display','block');
					/* obj.open("该账号已登录！请使用当前登录设备<span>"+MAC+"</span>的浏览器访问!<br><span>"+urllogoff+"</span>退出登录后，再在新设备上登录"); */
				}
				$('.iKnow').click(function(){
					 window.location.href=urllogoff;
						$(".logMask").hide();
				});
				}else if(data.code == 303){
					var obj= new repeatAlert();
					//var MAC=data.data[0].callingstationid.replace(/:/g,"");
					var model=data.data;
					//var nasipaddress = data.data[0].nasipaddress;
					var urllogoff = "http://1.0.0.0/logout";
					if(model!=0){
						$('.mac').text(model);
						$('.url').text(urllogoff);
						$('.logMask').css('display','block');
						/* obj.open("该账号已登录！请使用当前登录设备<span>"+model+"</span>的浏览器访问!<br><span>"+urllogoff+"</span>退出登录后，再在新设备上登录"); */
					}else{
						$('.mac').text(MAC);
						$('.url').text(urllogoff);
						$('.logMask').css('display','block');
						/* obj.open("该账号已登录！请使用当前登录设备<span>"+MAC+"</span>的浏览器访问!<br><span>"+urllogoff+"</span>退出登录后，再在新设备上登录"); */
					}
					$('.iKnow').click(function(){
						 window.location.href=urllogoff;
							$(".logMask").hide();
					});
						}
		
		}
</script>
</body>
</html>
 
