<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Date"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="cssPath" value="http://oss.kdfwifi.net/deck/mobile/css" />
<c:set var="imgPath" value="http://oss.kdfwifi.net/deck/mobile/img" />
 
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>KDF-WiFi</title>
<meta http-equiv=content-type content="text/html; charset=utf-8">
<link media=all href="${cssPath}/404.css" type=text/css rel=stylesheet>
<meta content="mshtml 6.00.2900.3354" name=generator>
</head>
<body style="table-layout: fixed; word-break: break-all">
	<div id='message'>
		<p>
			<a href="http://www.gonet.cc/">
			<img alt="KDF-WiFi"src="${imgPath}/LOGO.gif" border=0></a>
		</p>
		<p></p>
		<h1>您访问的页面可能已经删除、更名或暂时不可用。您可以尝试搜索一下(错误代码：500)</h1>
		<p>
			<a onclick="window.history.back();">请点击这里返回试试</a>
		</p>
	</div>
	<script type="text/javascript">
		var cnzz_protocol = (("https:" == document.location.protocol) ? " https://"
				: " http://");
		document
				.write(unescape("%3Cspan id='cnzz_stat_icon_1256802078'%3E%3C/span%3E%3Cscript src='"
						+ cnzz_protocol
						+ "s4.cnzz.com/stat.php%3Fid%3D1256802078' type='text/javascript'%3E%3C/script%3E"));
	</script>
</body>
</html>
