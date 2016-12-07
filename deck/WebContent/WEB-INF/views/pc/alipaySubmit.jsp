<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="renderer" content="webkit">
<meta http-equiv="expires" content="0"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<title>去支付</title>
</head>
<body onload="autosubmit()">
	<form id="alipaysubmit" name="alipaysubmit" action="https://mapi.alipay.com/gateway.do?" method="get">
	<input type="hidden" name="sign" value="${sPara.sign}"/>
	<input type="hidden" name="_input_charset" value="${sPara._input_charset}"/>
	<input type="hidden" name="subject" value="${sPara.subject}"/>
	<input type="hidden" name="total_fee" value="${sPara.total_fee}"/>
	<input type="hidden" name="sign_type" value="${sPara.sign_type}"/>
	<input type="hidden" name="service" value="${sPara.service}"/>
	<input type="hidden" name="notify_url" value="${sPara.notify_url}"/>
	<input type="hidden" name="partner" value="${sPara.partner}"/>
	<input type="hidden" name="seller_email" value="${sPara.seller_email}"/>
	<input type="hidden" name="out_trade_no" value="${sPara.out_trade_no}"/>
	<input type="hidden" name="payment_type" value="${sPara.payment_type}"/>
	<input type="hidden" name="return_url" value="${sPara.return_url}"/>
	<input type="submit" value="确认" style="display:none;">
</form>

</body>
	<script>
	function autosubmit(){
		 document.getElementById("alipaysubmit").submit();
	}	
	</script>
</html>