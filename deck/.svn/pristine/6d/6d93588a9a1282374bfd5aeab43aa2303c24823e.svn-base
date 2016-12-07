<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="mobilePath" value="${basePath}/mobile" />
<c:set var="wechat" value="http://oss.kdfwifi.net/deck/wechat" />
<c:set var="wechat1" value="${basePath}/wechat" />
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
<!DOCTYPE html>
<html>
<head>
 <meta charset="UTF-8">
 <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
  <title>注册</title>
<script type="text/javascript" src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="${wechat}/css/register.css?<%=getTimestamp %>">
<script type="text/javascript">
	var ctx = "${basePath}";
</script>
</head>
<body>
	
<div class="wrap">
    <!--头部轮播图-->
    <div class="top-nav">
        <ul>
            <li><a><img src="http://oss.kdfwifi.net/school_pic/combanner2.jpg"><h3></h3></a></li>
            <li><a><img src="http://oss.kdfwifi.net/school_pic/combanner1.jpg"><h3></h3></a></li>
            <li><a><img src="http://oss.kdfwifi.net/school_pic/combanner2.jpg"><h3></h3></a></li>
            <li><a><img src="http://oss.kdfwifi.net/school_pic/combanner3.jpg"><h3></h3></a></li>
        </ul>
    </div>
    <!--主体登陆内容-->
    <div class="content text-center mt-20">
        <h5 class="blue">输入手机号即可轻松上网</h5>
        <div style="padding: 0 20px;margin-top: 20px;">
            <div class="inputBox">
                <div class="user_info tel-input" style=" background-image: none;">
                    <input id="phoneNumber" onkeyup="this.value=this.value.replace(/\D/g,'')" type="tel" maxlength="11" class="uPhone" placeholder="请输入手机号">
                </div>
                <div style="width: 100%;height: 1px;background-color: #C9C9C9"></div>
                <div class="user_info tel-input" style=" background-image: none;">
                    <input id="pwdText" type="password" class="uPhone" placeholder="请输入登录密码">
                    <button id="pwdBtn" class="passwordBtn">获取密码</button>
                </div>
            </div>
            <p class="text-TS">请填写短信收到的登录密码，您可到个人中心内进行修改</p>

            <div class="place-check" style="position: relative">
                <div style="float: left;margin-top: 2px">
                    <input id="checkbox" checked class="place-checkbox radio" name="type" type="checkbox"/>
                   <!--  <div class="radio"></div> -->
                    <p style="float: left; font-size: 14px;margin-left: 5px">推荐码</p>
                </div>

                <p id="code" class="code">${recommend}</p>
            </div>

            <div>
                <button id="loginBtn" class="loginBtn">完成注册</button>
            </div>

            <div class="TS">
                <img src="${wechat}/img/shit.png"/>
                <span >登录视为您已同意<a href="#">《服务条款》</a></span>
            </div>
        </div>
    </div>

    <div id="errModel" style="display: none"><p id="errMsg">手机号错误</p></div>
    <!--公告及尾部版权声明-->
    <!--<div class="footer">-->
        <p class="copyright">Copyright &copy;2016 KDF inc. All Rights Reserved</p>
    <!--</div>-->
</div>
</body>
<script type="text/javascript" src="${wechat1}/js/idangerous.swiper-2.0.min.js?<%=getTimestamp %>"></script>
<script type="text/javascript" src="${wechat1}/js/register.js?<%=getTimestamp %>"></script>
<script>
    (function () {
        if ($(".top-nav").length ) {
            $(".top-nav").addClass("swiper-container");
            $(".top-nav ul:first").addClass("swiper-wrapper").find("li").addClass("swiper-slide");
            var auto_width = $(".top-nav").width();
            var auto_height = 360;
            var auto_num = $(".top-nav ul:first img").length;
            $(".top-nav").append("<div class='num'></div>")
            function topNav()
            {
                //获取屏幕的高度
                if ( $(".top-nav").parent().width() >=640 )
                {
                    auto_width = 640;
                    auto_height = 360;
                }
                else
                {
                    auto_width = $(".top-nav").parent().width();
                    auto_height = 360 * auto_width / 640;
                }
                $(".top-nav").width(auto_width).height(auto_height);
                $(".top-nav ul:first li").width(auto_width);
                $(".top-nav ul:first").width(auto_width*auto_num).height(auto_height);
            }
            topNav();
            $(window).resize(function(){ topNav()});
            $('.top-nav').swiper({
                pagination: '.num',
                mode:'horizontal',
                loop: true,
                autoplay:5000
            })
        }
    })()
</script>
</html>