var reg = /^(((13[0-9]{1})|(14[4-7]{1})|(15[0-9]{1})|(170)|(17[1-8]{1})|(18[0-9]{1}))+\d{8})$/;// 手机号码
window.onload=function(){
	$('.toLogin').click(function(){
		loginAjax();
	});
	$('.userName').keyup(function(){
		if($(this).val()==''){
			$(this).next().text('请输入手机号');
			return ;
		}else{
			$(this).next().text('');
		}
		var r = reg.test($(this).val());
		if(!r){
			$(this).next().text('请输入正确的手机号');
			return ;
		}else{
			$(this).next().text('');
		}
	});
	$('.userName').click(function(){
		$(this).next().text('');
	});
	$('.userPass').click(function(){
		$(this).next().text('');
	});
	
	
	$(document).keydown(function(event){
		if(event.keyCode==13){
			loginAjax();
		}
	});
	
	//服务条款
	$("#terms").click(function(){
		window.location.href=basePath+"/ProtalUserManage/goToTerms";
	});
}
function loginAjax(){
	$(".gif").css('display', 'block');
	var userName = $('.userName').val();
	var userPass = $('.userPass').val();
	$(".spaninfo").text('') 
	$(".userPass").next().text("") 
	var proLoginUrl="/w/ProuserLogin";
	var successUrl="/w/goToWherePage";
	var	payurl = "/w/toPay";
	if(userName==''||userName==null){
		$(".spaninfo").text('请输入手机号') 
		$(".gif").css('display', 'none');
		return ;
	}else if(!reg.test(userName)){
		$(".spaninfo").text('请输入正确的手机号');
		$(".gif").css('display', 'none');
		return ;
	}else if(userPass==''||userPass==null){
		$(".userPass").next().text("请输入密码") 
		$(".gif").css('display', 'none');
		return;
	}else{
		$.ajax({
			type: 'post',
			url: basePath+proLoginUrl,
			data: {
				name :userName,
				pwd : userPass,
				id 	:id,
				gw_id: gw_id,
				mac : mac,
				ip 	: ip,
				url :url,
				chargeType:0
			},
			success: function(data){
				data=JSON.parse(data);
				$(".gif").css('display', 'none');
				if(data.code == 201){
					$(".userPass").next().text(data.msg);
					return;
				}else if(data.code == 202){
					  window.location.href=basePath +payurl;
				}else if(data.code == 300){
					var MAC=data.data[0].MAC.replace(/:/g,"");
					var model=data.data[0].Terminal_device;
					var obj= new repeatAlert();
					if(model!=0){
						obj.open("设备<span>"+model+" 设备地址"+MAC+"</span>正在使用该账号,是否继续登录!<br>(继续登录则设备<span>"+MAC+"</span>会强制下线)");
					}else{
						obj.open("设备<span>"+MAC+"</span>正在使用该账号,是否继续登录!<br>(继续登录则设备<span>"+MAC+"</span>会强制下线)");
					}
					$("#sure").click(function(){
						Jump(data,successUrl,payurl);
					});	
					$("#cancel").click(function(){
						$(".log-mask").hide();
					});	
				}else if(data.code==203){
					  window.location.href=basePath +"/w/jinggao";
				}else if(data.code==200){
					Jump(data,successUrl,payurl);
				}else if(data.code==101){
					//alert(typeof (data.data));
					//data=JSON.parse(data.data);
					var obj = eval('(' + data.data + ')'); 
					//alert(obj.uamip);
					 window.location.href="http://"+obj.uamip+":"+obj.uamport+"/logon?username="+obj.username+"&response="+obj.pappassword+"&userurl=";
				}else if(data.code==102){
					var obj = eval('(' + data.data + ')'); 
					 window.location.href=obj.url;
				}else if(data.code==103){
					 window.location.href=data.data;
				}
				else if(data.code==302){
					var MAC=data.data[0].callingstationid.replace(/:/g,"");
					var model=data.data[0].Terminal_device;
					var obj= new repeatAlert();
					if(model!=0){
						obj.open("设备<span>"+model+" 设备地址"+MAC+"</span>正在使用该账号,是否继续登录!<br>(继续登录则设备<span>"+MAC+"</span>会强制下线)");
					}else{
						obj.open("设备<span>"+MAC+"</span>正在使用该账号,是否继续登录!<br>(继续登录则设备<span>"+MAC+"</span>会强制下线)");
					}
					$("#sure").click(function(){
						Jump(data,successUrl,payurl);
					});	
					$("#cancel").click(function(){
						$(".log-mask").hide();
					});	
				}
			}
		});
	}
}
//跳转页面
function Jump(data,successUrl,payurl){
	$('.log-mask').css('display','none');
	 if(data.code==200||data.code==300||data.code==302){
		  window.location.href=basePath+successUrl; 
	}
}
function repeatAlert(){
	//显示遮罩背景和弹出框
	this.open=function(str){//str用来接收提示文本
		$('.wanr-text').html(str);
		$('.log-mask').css('display','block');
		$('.warn').css('display','block');
		$('.loader').css('display','none');
		//callback();
	};
	
	//关闭弹出框
	this.close=function(){
		$('.log-mask').css('display','none');
		//callback();
	};
}
/**
 * 注册
 */
function toJump() {
	 window.location.href=basePath +"/ProtalUserManage/toRegister?id="+id +
 		"&mac="+mac+"&ip="+ip+"&url="+url; 
}

/**
 * 忘记密码
 */
function jumpToForget() {
	 window.location.href=basePath +"/ProtalUserManage/goToForgetPs";
}

var logmask=function(judeng){
	if(judeng){
		$('.log-mask').css('display','block');
		$('.warn').css('display','none');
		$('.loader').css('display','block');
	}
};