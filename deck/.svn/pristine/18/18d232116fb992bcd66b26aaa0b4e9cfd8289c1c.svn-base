<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Date" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>去支付</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0, user-scalable=no"/>
</head>
<body>


<div class="nav-wrap"></div>

<div class="grid">

    <form method="post" action="${serverUrl}" id="payForm">
        <!--交易信息 start-->
       <input type="hidden" name="version" value="${payOrderInfo.version}"><br/>
		<input type="hidden" name="merchant" value="${payOrderInfo.merchant}"><br/>
		 <input type="hidden" name="orderType" value="${payOrderInfo.orderType}"><br/> 
		 <input type="hidden" name="userId" value="${payOrderInfo.userId}"><br/> 
		<input type="hidden" name="tradeNum" value="${payOrderInfo.tradeNum}"><br/>
		<input type="hidden" name="tradeName" value="${payOrderInfo.tradeName}"><br/>
		<input type="hidden" name="tradeTime" value="${payOrderInfo.tradeTime}"><br/>
		<input type="hidden" name="amount" value="${payOrderInfo.amount}"><br/>
		<input type="hidden" name="currency" value="${payOrderInfo.currency}"><br/>
		<input type="hidden" name="notifyUrl" value="${payOrderInfo.notifyUrl}"><br/>
		<input type="hidden" name="callbackUrl" value="${payOrderInfo.callbackUrl}"><br/>
		<input type="hidden" name="ip" value="${payOrderInfo.ip}"><br/>
		<input type="hidden" name="sign" value="${payOrderInfo.sign}"><br/>
        <!--交易信息 end-->
    </form>

</div>

<script>
 	document.getElementById("payForm").submit();
    
</script>
</body>
</html>
