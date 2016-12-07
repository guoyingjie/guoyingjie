<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="http://api.youkala.com:80/">

<title>My JSP 'index.jsp' starting page</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
</head>

<body>
<script type="text/javascript">
		var timestamp = new Date().getTime();
	</script>
	<br>
	<form id="formid"
		action="http://api.youkala.com/api.jsp"
		method="post">
		
			<input name="partnerId" type="hidden"
			value='${partnerId}'>
			
			<input name="transData" type="hidden" value='${transData}'>
			
			<input name="partnerDebug" type="hidden"
			value="true">
			
		<button type="submit" id="submitlottery">submit</button>
	</form>
</body>
<script type="text/javascript">

//IE
/* if(document.all) {
document.getElementById("submitlottery").click();
}
// 其它浏览器
else {
var e = document.createEvent("MouseEvents");
e.initEvent("click", true, true);
document.getElementById("submitlottery").dispatchEvent(e);
} */
</script>
