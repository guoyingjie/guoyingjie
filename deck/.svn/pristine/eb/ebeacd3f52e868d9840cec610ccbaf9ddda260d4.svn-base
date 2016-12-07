<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="${basePath}/mobile" />
<c:set var="imgPath" value="http://oss.solarsys.cn/0001/mobile_img" />
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta content="1" name="keywords" />
<meta content="1" name="description" />
<meta content="yes" name="apple-mobile-web-app-capable"/>
<meta content="yes" name="apple-touch-fullscreen"/>
<meta content="telephone=no" name="format-detection"/>
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
<title>登录</title>
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/my.css?<%=getTimestamp %>" />
<link rel="stylesheet" type="text/css" href="${mobilePath}/css/style.css?<%=getTimestamp %>" />
<%-- <script language="javascript" src="${mobilePath}/js/js.js"></script> --%>

<script type="text/javascript">
	var basePath="${basePath}";
	var imgPath="${imgPath}";
	var id="${id}";
	var gw_id="${gw_id}";
	var mac="${mac}";
	var ip="${ip}";
	var url="${url}";
	var fromFw="${fromFw}";
	var gw_address="${gw_address}";
	var gw_port="${gw_port}";
</script>
</head>
<%  

    String name="";  
    String psw=""; 
   /*  String mac = ""; */
    Cookie[] cookies=request.getCookies();  
    if(cookies!=null&&cookies.length>0){   
        //遍历Cookie  
        for(int i=0;i<cookies.length;i++){  
            Cookie cookie=cookies[i];  
            //此处类似与Map有name和value两个字段,name相等才赋值,并处理编码问题   
            if("un".equals(cookie.getName())){  
                name=cookie.getValue();    
            }  
            if("pw".equals(cookie.getName())){  
                psw=cookie.getValue();  
            }  
           /*  if("mac".equals(cookie.getName())){
            	mac = cookie.getValue();
            } */
        }  
    }  
 %>
 
<body>
<div class="box">
	<img src="${imgPath}/bg.png" id="bg" width="100%" />
    <!--头部信息 返回 注册链接 start-->
    <div class="header"><p>
    <span class="pic"><img src="${imgPath}/zw.png" width="50%" /></span>
    <span class="dl">登&nbsp;&nbsp;录</span><span id="registerBtn" class="zc">注册</span></p></div>
    <!--头部信息 返回 注册链接 end-->
    <!--banner广告 start-->
    <div class="banner" id="banner">
    	<ul class="main">
        	<li><img src="${imgPath}/banner1.jpg" width="100%" /></li>
            <li><img src="${imgPath}/banner2.jpg" width="100%" /></li>
        </ul>
        <ul class="ctrl">
        	<li class="on"></li>
            <li class="cm"></li>
        </ul>
        <!--关闭广告按钮 start-->
        <span class="gb"></span>
        <!--关闭广告按钮 end-->
    </div>
    <!--banner广告 end-->
    <!--登陆表单 start-->
    <form  id="loginForm" class="dlform">
    	<!--手机号 start-->
    	<p><img class="tu1" src="${imgPath}/pone.png" width="5%" />
<!--     		<input id="num" type="text" value="请填写中国大陆手机号" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" /> -->
<!--     		<span id="sj" style="display:none;">请填写正确的手机号</span> -->
    		<input name="schoolNumber"  id="schoolNumber" type="tel" value="<%=name %>" placeholder="请输入手机号"  data-validate="telephone" />
    		<span id="errorMsg"></span>
    	</p>
        <!--手机号 end-->
        <!--密码 start-->
        <p class="x"><img class="tu2" src="${imgPath}/lock.png" width="5%" />
<%-- 	        <input id="pwd" type="text" value="6-14位，支持数字、密码、字符组合" /><img id="tu" class="tu3" src="${imgPath}/mm.png" width="6%" /> --%>
<!-- 	        <span id="mi" style="display:none;">密码输入有误</span> -->
	        <input type="password" value="<%=psw %>" placeholder="请输入密码" id="schoolRandCode" name="schoolRandCode"/> <span ></span>
	        <img id="tu" class="tu3" src="${imgPath}/mm.png" width="6%" />
	       
        </p>
        <!--密码 end-->
        <!--找回密码链接 start-->
        <a href="${basePath}/ProtalUserManage/toResetPassword">忘记密码?</a>
        <!--找回密码链接 end-->
        <!--提交表单 start-->
        <span class="dlk"><input id="dl" type="submit" value="登&nbsp;&nbsp;&nbsp;录" /></span>
        <!--提交表单 end-->
    	<input type="hidden" value="${id}" id="id"/>
		<input type="hidden" value="${mac}" id="mac"/>
		<input type="hidden" value="${ip}" id="ip"/>
		<input type="hidden" value="${url}" id="url"/>
		<input type="hidden" value="" id="passwordMd5" />
    </form>
    <!--登陆表单 end-->
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
    <!--页面底部-->
    <p id="cr">技术支持|北京宽东方  400-666-0050</p>
    <!--页面底部-->
</div>
<div class="mask">
	<div class="diao"><span class="clos"></span></div>
	<div class="diao1"></div>
	<div class="diao2"></div>
</div>
</body>
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${mobilePath }/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${mobilePath }/js/MD5.js"></script>
<script type="text/javascript" src="${mobilePath }/js/schoolLogin.js?<%=getTimestamp %>"></script>
<%-- <script type="text/javascript">
$().ready(function(){

	var submitName = $("#schoolNumber").val();
	var submitMac =  $("#mac").val();
	var nameCookie = '<%=name %>';
	var macCookie = '<%=mac %>';
	 
	if(nameCookie===submitName && macCookie===submitMac && submitName !=""){
		 
	}else{
	     var pHeight=$(window).height();
		$('.mask').height(pHeight);
		$(".mask").fadeIn(1000);
		window.setTimeout(function(){
			$('.diao').animate({bottom:'40%'},500,function(){
				$('.diao1').animate({bottom:'30%'},300,function(){
					$('.diao2').animate({bottom:'26%'},200);
				});
			});
		},1000);
		$('.clos').click(function(){
			$('.mask').css('display','none');
		});
	}
});
 
</script> --%>
</html>
