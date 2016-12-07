$(function(){
	
	$(".ui-expe").click(function(){
		goInternet();
	});
	$(".ui-per").click(function(){
		goPersonalUrl();
		
	});
	
});
function goPersonalUrl(){
	var toPcPersonUrl;
	if(fromFw!="null"&&fromFw!=""&&fromFw!=null&&fromFw!=undefined){
		toPcPersonUrl="/wifidog/toPcPerson?id="+id;
	}else{
		toPcPersonUrl="/toPcPerson?id="+id;
	}
	window.location.href=basePath+toPcPersonUrl;
}
function goInternet(){
	var failUrl;//登录失败重新登录login路径,fromFw为null为原系统路径,不为null是wifidog的login路径
	var toInterUrl;
	var topcUrl;
	var fromUrl="";
	if(fromFw!="null"&&fromFw!=""&&fromFw!=null&&fromFw!=undefined){
		failUrl="/wifidog/redirectLogin/?gw_id="+id+"&ip="+ip+"&mac="+mac+"&url="+fromUrl+"&terminalDevice="+terminalDevice+"&gw_address="+gw_address+"&gw_port="+gw_port;
		toInterUrl="/wifidog/toInternet";
		topcUrl="/wifidog/toPcLogin";
	}else{
		toInterUrl="/toInternet";
		failUrl="/toLogin?id="+id+"&ip="+ip+"&mac="+mac+"&url="+fromUrl+"&terminalDevice="+terminalDevice;
		topcUrl="/toPcLogin";
	}
	$.ajax({
		type:"POST",
		url:basePath+toInterUrl,
		data:{
			id:id,
			ip:ip,
			mac:mac,
			url:fromUrl,
			terminalDevice:terminalDevice
		},
		success:function(data){
			eval("data="+data);
			if(data.code==1){
				window.location.href=basePath+"/jinggao";
			}else if(data.code==2){
				window.location.href=basePath+"/mobileJinggao";
			}else if(data.code==200){//pc版账号已被登陆
				var MAC=data.data[0].MAC.replace(/:/g,"");
				var obj= new repeatAlert();
				obj.open("设备<span>"+MAC+"</span>正在使用该账号,是否继续登录!<br>(继续登录则设备<span>"+MAC+"</span>会强制下线)");
				$("#sure").click(function(){
					window.location.href=basePath+topcUrl;
				});	
			}else if(data.code==202){
				window.location.href=basePath+topcUrl;
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
			$('.wanr-text').html(str);
			$('.log-mask').css('display','block');
			$('.warn').css('display','block');
			$('.loader').css('display','none');
		};
		
		//关闭弹出框
		this.close=function(){
			$('.log-mask').css('display','none');
		};
	}
}
