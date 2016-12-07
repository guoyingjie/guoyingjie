$(function(){
	
	$(".cut").click(function(){
		goInternet();
	});
	
});
function goInternet(){
	var failUrl;//登录失败重新登录login路径,fromFw为null为原系统路径,不为null是wifidog的login路径
	if(fromFw!="null"&&fromFw!=""&&fromFw!=null&&fromFw!=undefined){
		//failUrl="/wifidog/toSwitchAccount?id="+id+"&ip="+ip+"&mac="+mac+"&url="+url+"&terminalDevice="+terminalDevice+"&wifidog="+fromFw;

		failUrl="/wifidog/toSwitchAccount?gw_address="+gw_address+"&gw_id="+id+"&gw_port="+gw_port+"&ip="+ip+"&mac="+mac+"&url="+url;
	}else{
		failUrl="/toSwitchAccount?id="+id+"&ip="+ip+"&mac="+mac+"&url="+url+"&terminalDevice="+terminalDevice;
	}
	window.location.href=basePath+failUrl;
}
