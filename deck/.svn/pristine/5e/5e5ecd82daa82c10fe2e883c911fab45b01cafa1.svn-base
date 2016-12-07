/**
 * 登录
 */
$(function (){
	
	initClick();
	
});
function initClick(){
	var loginUrl;
	var trialUrl;
	if(fromFw!="null"&&fromFw!=""&&fromFw!=null&&fromFw!=undefined){
		loginUrl="/wifidog/redirectLogin/?gw_id="+id+"&ip="+ip+"&mac="+mac+"&url="+url+"&gw_address="+gw_address+"&gw_port="+gw_port+"&fromTrial=" + 1;
		trialUrl="/wifidog/goToTrialSuccess";
	}else{
		loginUrl="/toLogin?id="+id +"&mac="+mac+"&ip="+ip+"&url="+url + "&fromTrial=" + 1; 
		trialUrl="/goToTrialSuccess";
	}
//	alert(trialUrl);
	$('#dl').on('click', function(){
		$("#loginForm").submit();
	});
	
	$("#registerBtn").on('click',function(){//跳转注册页面
		  window.location.href=basePath +"/ProtalUserManage/toRegister?id="+id +
		  		"&mac="+mac+"&ip="+ip+"&url="+url; 
	});
	
	$("#toLogin").on('click',function(){//跳转登陆页面
		  window.location.href=basePath +loginUrl;
//		  "/toLogin?id="+id +
//		  		"&mac="+mac+"&ip="+ip+"&url="+url + "&fromTrial=" + 1; 
	});
	$("#ty").on('click',function(){//跳转试用成功页面
		Jump(trialUrl);
	});
}


//跳转页面
function Jump(trialUrl){

		  window.location.href=basePath+trialUrl; 
	
}