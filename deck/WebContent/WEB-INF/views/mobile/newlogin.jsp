<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
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
<meta charset="utf-8" />
<title>KDF登录</title>
<link rel="stylesheet"
	href="${mobilePath}/css/newcss/public.css?<%=getTimestamp %>" />
<link rel="stylesheet"
	href="${mobilePath}/css/newcss/newlogin.css?<%=getTimestamp %>" />
<meta name="viewport"
	content="width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0" />
<meta name="format-detection" content="telephone=no" />
<meta name="MobileOptimized" content="320" />
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
		var sitename = "${site.site_name}";
	</script>
</head>

<body>
	<div class="wrap">
		<!--头部轮播图-->
		<div class="top-nav">
			<ul class="bannerurl">
				<!-- 这里存放banner的地方 -->
				<%-- <li><a><img src="${basePath}/mobile/img/newimg/banner01.jpg"><h3></h3></a></li>
				<li><a><img src="${basePath}/mobile/img/newimg/banner02.jpg"><h3></h3></a></li>
				<li><a><img src="${basePath}/mobile/img/newimg/banner03.jpg"><h3></h3></a></li>
				<li><a><img src="${basePath}/mobile/img/newimg/banner01.jpg"><h3></h3></a></li> --%>
			</ul>
			<input type="hidden" value="${bannerUrl}" id="banner" />
		</div>
		<!--主体登陆内容-->
		<section class="mian-cont" data-this="0">
			<h1 class="welcome-word" style="font-weight: bold;">欢迎使用${site.site_name}Wi-Fi</h1>
			<!--登录首页0-->
			<div class="userMsg" style="display: none;">
				<input type="hidden" value="${proUser.userName}" id="username" />
				<p class="get-method">使用手机号</p>
				<p class="tel-info" id="newphone"></p>
				<p class="user_info login-btn">
					<span class="change-to left_btn" id="changenum">切 换 账 号</span>
					<button class="logon_to right_btn">登 录</button>
				</p>
				<p class="advice-word">建议使用自己的手机号登录，您将获得更多的权益和服务</p>
			</div>
			<!--修改账号1-->
			<div class="userMsg" style="display: none;">
				<p class="user_info tel-input" style="margin-top: 15px;">
					<input  onkeyup="this.value=this.value.replace(/[^0-9A-Ba-b]/g,'')" type="text"
						maxlength="12" class="uPhone" placeholder="输入手机号即可轻松上网"
						id="changenums" />
				</p>
				<p class="advice-word">建议使用自己的手机号登录，您将获得更多的权益和服务</p>
				<button id="whether-login" class="no-login">登 录</button>
				<p class="serve-term">
					<img src="${mobilePath}/img/newimg/tip.png" />登录视为您已同意 <i
						class="terms"><<服务条款>> </i>
				</p>
				<p class="notice">
					<img src="${mobilePath}/img/newimg/horn.png" />&nbsp;<i>新用户可免费试用${site.is_probative}小时</i>
				</p>
				<p class="dividing-line"></p>
			</div>
			<!--已注册2-->
			<div class="userMsg" style="display: none;">
				<p class="user_info tel-input"
					style="border-bottom-left-radius: 0px; border-bottom-right-radius: 0px;">
					<input type="text" maxlength="12" class="regist-user"
						id="regesterOkUsername" placeholder="输入手机号即可轻松上网"
						onkeyup="this.value=this.value.replace(/[^0-9A-Ba-b]/g,'')" /><img
						class="has-regist" src="${mobilePath}/img/newimg/telture.png">
				</p>
				<p class="user_info pass-input">
					<input type="password" minlength="6" placeholder="请输入登录密码" onkeyup="this.value=this.value.replace( /\s+/g,'')"
						id="regsterPs" /><span class="pass-view"><img
						src="${mobilePath}/img/newimg/view.png" alt="切换" /></span>
				</p>
				<p class="find_pass">
					<button id="findpassword">找回密码</button>
				</p>
				<button class="has-logins" >登 录</button>
			</div>
			<!--找回密码3-->
			<div class="userMsg" style="display: none;">
				<p class="user_info get-Verification">
					<input onkeyup="this.value=this.value.replace(/\D/g,'')" type="tel"  placeholder="请输入手机验证码" maxlength="20" id="findpscode" />
					<button class="get-code" id="findpasswordcode">获取验证码</button>
				</p>
				<p class="user_info login-btn">
					<span class="return-to left_btn">返 回 登 录</span>
					<button class="next_to right_btn">下 一 步</button>
				</p>
			</div>
			<!--设置新密码4-->
			<div class="userMsg" style="display: none;">
				<p class="user_info new-pass">
					<input type="password" maxlength="20"  placeholder="6位以上数字或字母" id="resetPs" onkeyup="this.value=this.value.replace( /\s+/g,'')"/>
				</p>
				<p class="user_info new-pass">
					<input  type="password" maxlength="20" placeholder="请确认您的密码" id="sendpass" onkeyup="this.value=this.value.replace( /\s+/g,'')"/>
				</p>
				<p class="user_info login-btn">
					<span class="return-to left_btn">返回登录</span>
					<button class="complete right_btn">完成</button>
				</p>
			</div>
			<!--未注册新用户 index为5-->
			<div class="userMsg" style="display: none;">
				<p class="user_info newtel-input">
					<input onkeyup="this.value=this.value.replace(/[^0-9A-Ba-b]/g,'')" type="tel" class="noregist-user" id="nouserto" placeholder="请输入手机号" maxlength="12" />
					<img class="no-regist" src="${mobilePath}/img/newimg/telerror.png">
				</p>
				<button class="get-linepass">获取上网密码</button>
			</div>
			<!--注册用户获取密码6-->
			<div class="userMsg" style="display: none;">
				<p class="user_info newtel-input"
					style="border-bottom-left-radius: 0px; border-bottom-right-radius: 0px;">
					<input type="tel" class="noregist-user" placeholder="请输入手机号" id="resterUser" maxlength="12" /> <img class="no-regist"
						src="${mobilePath}/img/newimg/telerror.png">
				</p>
				<p class="user_info newget-Verification">
					<input onkeyup="this.value=this.value.replace( /\s+/g,'')" type="tel" placeholder="输入手机接收的登录密码" maxlength="20" id="regesterCode" />
					<button class="get-code" id="reggetcode" disabled="disabled">获取登录密码</button>
				</p>
				<button class="has-login" id="regseterLogin" >登 录</button>
			</div>
		</section>
		<!--错误提示框-->
		<p class="errorP">
			<span class="msg"></span>
		</p>
		<!--公告及尾部版权声明-->
		<div class="footer">
			<p class="copyright">&copy;2016 KDF inc.</p>
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
		<!-- 多台设备登录的提示框 -->
		<div class="logMask">
			<div class="content">
				<h4>该账号已登录！</h4>
				<p style="font-size: 14px;"> 请使用当前在线设备输入
					<span class="url">http://10.5.50.1:80/logout</span>网址退出登陆,也可进入微信公众号更多服务中进行Wi-Fi下线操作！
				</p>
				<button type="button" class="iKnow">我知道了</button>
			</div>
		</div>
	    <!-- 多台设备登录提示框结束 -->
	</div>
	<input type="hidden" value="${proUser.passWord}" id="tupian">
	<input type="hidden" value="${UserName}" id="sonname">
	<input type="hidden" value="${siteMac}" id="id" />
	<input type="hidden" value="${clientMac}" id="mac" />
	<input type="hidden" value="${clientIp}" id="ip" />
	<input type="hidden" value="${fromUrl}" id="url" />
	<input type="hidden" value="${have}" id="have" />
	<input type="hidden" value="${site.is_probative}" id="trynum" />
	<input type="hidden" value="${site.id}" id="siteId" />
	<input type="hidden" value="${type}" id="routerType">
	<input type="hidden" value="" id="passwordMd5" />
	<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="http://oss.kdfwifi.net/js/idangerous.swiper-2.0.min.js"></script>
	<script type="text/javascript" src="${mobilePath1}/js/newjs/newlogin.js?<%=getTimestamp %>"></script>
	<script type="text/javascript">
	var username = '<%= (String)session.getAttribute("UserName")%>';
	var chillireturn = '<%=(String)request.getSession().getAttribute("chillilreturn")%>';
	if(chillireturn !='null' && chillireturn.length>1){
 		var data=JSON.parse(chillireturn);
		if(data.code == 201){
			errorMsg(data.msg);
			$('.log-mask').css('display', 'none');
		}else	if(data.code == 205){
			errorMsg(data.msg);
			$('.log-mask').css('display', 'none');
		}else	if(data.code == 209){
			errorMsg(data.msg);
			$('.log-mask').css('display', 'none');
		}else if(data.code == 202){
			  window.location.href=basePath +"/w/toPay";
	    }else if(data.code == 300){//wifidog多台登录的提示信息
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
				if(username.indexOf('a')>-1||username.indexOf('b')>-1){
					window.location.href=basePath+"/w/successCenter";			
				}else{
					Jump(data,successUrl,payurl);
				}
			});	
			$("#cancel").click(function(){
				$('.log-mask').css('display', 'none');
			}); 
		}else if(data.code==203){
			  window.location.href=basePath +"/w/jinggao";
		}else if(data.code==200){
			if(username.indexOf('a')>-1||username.indexOf('b')>-1){
				window.location.href=basePath+"/w/successCenter";		
			}else{
				var successUrl="/w/goToWherePage";
				var	payurl = "/w/toPay";
				Jump(data,successUrl,payurl);
			}
		}else if(data.code == 301){//小辣椒多台登录提示
			var obj= new repeatAlert();
			var model=data.data;
			var uamport = '3990';
			var urllogoff = "http://logout";
			if(model!=0){
				$('.mac').text(model);
				$('.url').text(urllogoff);
				$('.logMask').css('display','block');
			}else{
				$('.mac').text(MAC);
				$('.url').text(urllogoff);
				$('.logMask').css('display','block');
			}
			$('.iKnow').click(function(){
		          $(".logMask").hide();
			});
		}else if(data.code == 302){//ros多台登录提醒
			var obj= new repeatAlert();
			var model=data.data;
			var urllogoff = "http://10.5.50.1/logout";
			if(model!=0){
				$('.mac').text(model);
				$('.url').text(urllogoff);
				$('.logMask').css('display','block');
			}else{
				$('.mac').text(MAC);
				$('.url').text(urllogoff);
				$('.logMask').css('display','block');
			}
			$('.iKnow').click(function(){
				$(".logMask").hide();
			});
		 }else if(data.code == 303){//ikuai多台登录提示
			var obj= new repeatAlert();
			var model=data.data;
			var urllogoff = "http://1.0.0.0/logout";
			if(model!=0){
				$('.mac').text(model);
				$('.url').text(urllogoff);
				$('.logMask').css('display','block');
			}else{
				$('.mac').text(MAC);
				$('.url').text(urllogoff);
				$('.logMask').css('display','block');
			}
			$('.iKnow').click(function(){
		        $(".logMask").hide();
			});
	    }
}
</script>
</body>
</html>

