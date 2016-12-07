<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="http://oss.kdfwifi.net/deck" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<script src="${pageContext.request.contextPath}/mobile/js/jweixin-1.0.0.js"></script>
<title>警告</title>
<style>
.bottom {
	position: absolute;
	color: #666666;
	bottom: 0;
	font-size: 10px;
	width: 100%;
	text-align: center;
}
</style>
</head>
<body>

	<div style="text-align: center; margin-top: 150px">
		<img style="width: 150px;" src="${ctx}/wechat/img/err.png">

		<p style="color: #e86c5f; font-size: 20px; margin-top: 30px">请务盗链网页!</p>

	</div>

	<p class="bottom">Copyright ©2016 KDF inc. All Rights Reserved.</p>

</body>
<script type="text/javascript">
	pushHistory();
	window.addEventListener("popstate", function(e) {
		if (window.confirm("确定离开此页面?")) {
			wx.closeWindow();
		} else {
			pushHistory();
		}
	}, false);

	function pushHistory() {
		var state = {
			title : "title",
			url : "#"
		};
		window.history.pushState(state, "title", "#");
	}
</script>
</html>