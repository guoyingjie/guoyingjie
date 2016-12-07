$(function(){
	
	$(".oauth").click(function(){
		goInternet();
	});
	$(".pers").click(function(){
		
		goPersonal();
	});
	
});
function goPersonal(){
	var goPersonalUrl;
	if(fromFw!="null"&&fromFw!=""&&fromFw!=null&&fromFw!=undefined){
		goPersonalUrl="/w/mobilePersonal?id="+id;
	}else{
		goPersonalUrl="/mobilePersonal?id="+id;
	}
	window.location.href=basePath+goPersonalUrl;
}
function goInternet(){
	var	failUrl="/w/redirectLogin/?gw_id="+id+"&ip="+ip+"&mac="+mac+"&url="+url+"&terminalDevice="+terminalDevice+"&gw_address="+gw_address+"&gw_port="+gw_port;
	var	trialUrl="/w/toMobleLogin";
	var	toInterUrl="/w/toInternet";
	$.ajax({
		type:"POST",
		url:basePath+toInterUrl,
		data:{
			id:id,
			ip:ip,
			mac:mac,
			url:url,
			terminalDevice:terminalDevice
		},
		success:function(data){
			eval("data="+data);
			if(data.code==1){
				window.location.href=basePath+"jinggao";
			}else if(data.code==2){
				window.location.href=basePath+"/mobileJinggao";
			}else if(data.code==201){//手机版账号已被登陆
				var MAC=data.data[0].MAC.replace(/:/g,"");
				var obj= new repeatAlert();
				obj.open("设备<span>"+MAC+"</span>正在使用该账号,是否继续登录!<br>(继续登录则设备<span>"+MAC+"</span>会强制下线)");
				$("#sure").click(function(){
					window.location.href=basePath+trialUrl;
				});	
			}else if(data.code==204){
				window.location.href=basePath+trialUrl;
			}else{
				window.location.href=basePath+failUrl;
				//"/toLogin?id="+id+"&ip="+ip+"&mac="+mac+"&url="+url+"&terminalDevice="+terminalDevice;
			}
		}
		
	});
	$("#cancel").click(function(){
		var obj=new repeatAlert();
		obj.close();
		
	});
	//登录 弹出确认框
	function repeatAlert(){
		//显示遮罩背景和弹出框
		this.open=function(str){//str用来接收提示文本
			$('.mask').css('display','block');
			$('.wanr-text').html(str);
			$('.warn').css('display','block');
			$('.hint').css('display','none');
		};
		
		//关闭弹出框
		this.close=function(){
			$('.mask').css('display','none');
		};
	}
}
