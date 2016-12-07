<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="pcPath" value="http://oss.kdfwifi.net/deck/pc" />
<c:set var="pcPath1" value="${basePath}/pc" />
<!DOCTYPE html>
<html>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit">
<title>登录</title>

<link rel="stylesheet" href="${pcPath}/css/newcss/common.css?<%=getTimestamp %>" />
<link rel="stylesheet" href="${pcPath}/css/newcss/login.css?<%=getTimestamp %>" />
</head>
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
<body oncontextmenu=self.event.returnValue=false>
<header>欢迎使用<br>${site.site_name }Wi-Fi</header>
<form class="login">
	<p><input class="userName" type="tel" name="" placeholder="登录用户名" maxlength="11" onkeyup="this.value=this.value.replace(/\D/g,'')"><span class="spaninfo"></span></p>
	<p><input class="userPass" type="password" name="" placeholder="登录密码" maxlength="16" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')"><span></span></p>
	<button class="toLogin" type="button">登录</button>
	<a class="toForget" onClick="jumpToForget()" href="#">忘记密码？</a>
	<a class="toRegister" onClick="toJump()" href="#">创建一个账号＞</a>
</form>
<div class="log-mask">
		<div class="loader">登录中...</div>
		<div class="warn">
			<p class="wanr-text">提示文字</p>
			<div class="buttons">
				<button id="sure">继续登录</button>
				<button id="cancel">取消登录</button>
			</div>
			<div class="buttons1" style="display: none; margin:0 auto;margin-top: 10px;">
				<button id="ikown" style="display: block; margin: 0 auto; float: none; ">我知道了</button>
			</div>
		</div>
	</div>
<footer>登录视为您同意<span id="terms">《服务条款》</span><p class="copyRight">KDFWIFI.COM 2013-2016  联系电话 400-666-0050</p></footer>
<div class="altMask">
	<div>
		<span></span>
		<p class="msg"></p>
	</div>
</div>
<div class="gif" style="display:none"></div>
</body>
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${pcPath1}/js/newjs/login.js?<%=getTimestamp %>"></script>
<script type="text/javascript" src="${pcPath1}/js/MD5.js"></script>
<script type="text/javascript">
	var chillireturn = '<%=(String)request.getSession().getAttribute("chillilreturn")%>';
	//alert(chillireturn+":chillireturn");
	if(chillireturn !='null' && chillireturn.length>1){
		//alert(chillireturn);
		var data=JSON.parse(chillireturn);
		//$(".gif").css('display', 'block');
		if(data.code == 201){
			$(".userPass").next().text(data.msg);
		}else if(data.code == 205){
			$(".userPass").next().text(data.data);
		}else if(data.code == 209){
			$(".userPass").next().text(data.data);
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
				Jump(data,successUrl,payurl);
			});	
			$("#cancel").click(function(){
				$(".log-mask").hide();
			});	
		}else if(data.code==203){
			  window.location.href=basePath +"/w/jinggao";
		}else if(data.code==200){
			
			Jump(data,successUrl,payurl);
		}else if(data.code == 301){
			var obj= new repeatAlert();
			//var MAC=data.data.callingstationid.replace(/:/g,"");
			var model=data.data;
			//var nasipaddress = data.data[0].nasipaddress;
			var uamport = '3990';
			//var urllogoff = "http://"+nasipaddress+":"+uamport+"/logoff";
			var urllogoff = "http://logout";
			if(model!=0){
				obj.open("该账号已登录！请使用当前登录设备<span>"+model+"</span>的浏览器访问!<br><span>http://logout/</span>退出登录后，再在新设备上登录");
			}else{
				obj.open("该账号已登录！请使用当前登录设备<span>"+model+"</span>的浏览器访问!<br><span>http://logout/</span>退出登录后，再在新设备上登录");
			}
			$(".buttons1").show();
			$(".buttons").hide();
			$("#ikown").click(function(){
				$(".log-mask").hide();
			});	
			}else if(data.code == 302){
			var obj= new repeatAlert();
			//var MAC=data.callingmac.replace(/:/g,"");
			var model=data.data;
			//var nasipaddress = data.uamip;
			var urllogoff = "http://10.5.50.1/logout";
			if(model!=0){
				obj.open("该账号已登录！请使用当前登录设备<span>"+model+"</span>的浏览器访问!<br><span>"+urllogoff+"</span>退出登录后，再在新设备上登录");
			}else{
				obj.open("该账号已登录！请使用当前登录设备<span>"+model+"</span>的浏览器访问!<br><span>"+urllogoff+"</span>退出登录后，再在新设备上登录");
			}
			$(".buttons1").show();
			$(".buttons").hide();
			$("#ikown").click(function(){
				$(".log-mask").hide();
			});	
				}else if(data.code == 303){
					var obj= new repeatAlert();
					//var MAC=data.data[0].callingstationid.replace(/:/g,"");
					var model=data.data;
					//var nasipaddress = data.data[0].nasipaddress;
					var urllogoff = "http://1.0.0.0/logout";
					if(model!=0){
						obj.open("该账号已登录！请使用当前登录设备<span>"+model+"</span>的浏览器访问!<br><span>http://1.0.0.0/logout</span>退出登录后，再在新设备上登录");
					}else{
						obj.open("该账号已登录！请使用当前登录设备<span>"+model+"</span>的浏览器访问!<br><span>http://1.0.0.0/logout</span>退出登录后，再在新设备上登录");
					}
					$(".buttons1").show();
					$(".buttons").hide();
					$("#ikown").click(function(){
						$(".log-mask").hide();
					});	
					}
		}
</script>
</html>