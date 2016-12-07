<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<c:set var="css" value="http://oss.kdfwifi.net/deck/wechat/css"/>
<c:set var="img" value="http://oss.kdfwifi.net/deck/wechat/img/ziimg"/>
<c:set var="js" value="${basePath}/wechat/js" />
<!DOCTYPE html>
<html>
<head>
<%@ page import="com.broadeast.util.PropertiesParam"%>
<%
	String getTimestamp = PropertiesParam.VERSIONS;
%>
 <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>子账号管理</title>
    <link rel="stylesheet" type="text/css" href="${css}/newpublic.css?<%=getTimestamp %>"/>
    <link rel="stylesheet" type="text/css" href="${css}/manage.css?<%=getTimestamp %>">
    <script type="text/javascript">
    var ctx = "${basePath}";
    </script>
</head>
<body>

<div class="content" style="position: relative">
    <p class="user">子账号:   <span id="userName" class="blue">${sonname}</span></p>

    <div id="have" style="display:none">
        <div class="icon-title">
            <img src="${img }/surplus.png"/>
            <span>套餐余量</span>
        </div>
        <div class="surplusBox">
            <div class="count">
                <div class="surplus"></div>
                <span class="surplusText"></span>
            </div>
        </div>

        <div class="surplusBox icon-bg">
            <div class="count">
                <div class="surplus"></div>
                <span class="surplusText"></span>
            </div>
        </div>
    </div>

    <div id="noHave" class="text-center" style="padding: 0 15px;display: none">
        <img style="width: 67px;display: inline;margin-bottom: 15px" src="${img }/sor.png"/>
        <p style="font-size: 14px">您当前子账号余量为0，可点击下方“划拨资费”从 主账号进行资费划拨！</p>
    </div>

</div>

<div id="hbzf" class="list">
    <p>划拨资费</p>
    <img class="right-icon" src="${img}/enter.png">
</div>
<div class="text-center" style="margin-top: 10px">
   <!--  <span id="deleteShow" style="color: #30a2d3">删除子账号</span> -->
</div>

<div id="sucModel" style="display: none">
    <div class="sucBox">
        <p id="sucMsg">删除成功</p>
    </div>
</div>

<div id="mask" style="display: none">
    <div class="msgBox">
        <p>子账号将被删除此操作不可撤销</p>
        <div class="btns">
            <button id="delete" class="btn-left">删除</button>
            <button id="cancel" class="btn-right">取消</button>
        </div>
    </div>
</div>

<div id="errModel" style="display: none"><p id="errMsg"></p></div>

<div class="footer">KDFWIFI.COM 2013-2016</div>
<input type="hidden" value="${sonname}" id="sonname" />
<input type="hidden" value="${siteId}" id="siteId" />
<input type="hidden" value="${username}" id="username" />
<input type="hidden" value="${openid}" id="openId" />
</body>
<script src="http://oss.kdfwifi.net/js/jquery-1.9.1.min.js"></script>
<script src="${js}/manage.js?<%=getTimestamp %>"></script>
</html>