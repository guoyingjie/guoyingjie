
var tel = /^(((13[0-9]{1})|(14[4-7]{1})|(15[0-9]{1})|(170)|(17[1-8]{1})|(18[0-9]{1}))+\d{8})$/;
window.onload=function(){
	if(true){
		$('.banner').css('background','url('+$('#banner').val()+') no-repeat center');
		$('.banner').css('background-size','cover');
	}
	tiao(1);
	//$('.next').unbind("click");
	$('.next').click(function(){
		var n = $('.next').index(this);
		if (n == 0) {
			return checkUserIsHave(n);
		} else if (n == 1) {
			return checkCode(n);
		} else {
			resetPassword();
		}
	});
	 
	toSendMeg();
	$("#forgetItems").click(function(){
		window.location.href=ctx+"/ProtalUserManage/goToTerms";
	});
}
//发送验证码
function toSendMeg(){
	$('.codeBtn').click(function(){
		   coundDown($(this),60);
		   var phone = $('.phone').val();
		   sendMegToUser(phone);
	});
}
//发送验证码
function sendMegToUser(phone,password){
	$.ajax({
		type : "POST",
		url : ctx+"/TelCodeManage/sendTelCode",
		data : {
			tel : phone,
			templateCode:"SMS_12891467",
		},
		success : function(data) {
			if(data==-1){
				msg(0,"验证码发送失败");
				return false;
			}
			if(data==-2){
				msg(0,"请不要频繁发送验证码");
				return false;
			}
		}
	});
}

//检验用户是否存在
function checkUserIsHave(n){
	var phone = $(".phone").val();
	if(phone==null||""==phone){
		msg(0,"请输入账号");
		return false;
	}
	if(!tel.test(phone)){
		msg(0,"请输入11位手机号");
		return false;
	}
	$.ajax({
		type:'post',
		url : ctx+"/ProtalUserManage/check",
		data:{
			telephone:phone
		},
		success:function(data){
			if(data=="false"){
				if(n<2){
					$('.content').addClass('small');
					setTimeout(function(){
						boxMove(n);
					},400);
				} 
			}else{
				msg(0,"账号不存在,请注册");
				return false;
			}
		}
	});
	  coundDown($('.codeBtn'),60)
	  sendMegToUser(phone);
}
//验证验证码
function checkCode(n){
	 var code = $('.code').val();
	 var telephone = $(".phone").val();
	 if(telephone==''||telephone==null){
		 msg(0,"请输入手机号");
		return false;
	 }
	 if(code==null||""==code){
		 msg(0,"请输入验证码");
			return false;
	 }
	 $.ajax({
			type:'post',
			url : ctx+"/ProtalUserManage/checkRandCode",
			data:{
				phonecode:code,
				telephone:telephone
			},
			success:function(data){
				eval("data = " + data);
				if(data.code==200){
					if(n<2){
						$('.content').addClass('small');
						setTimeout(function(){
							boxMove(n);
						},400);
					} 
				}else{
					msg(0,data.msg);
					return false;
				}
			}
		});
}
//修改密码
function resetPassword(){
	var one = $.trim($(".pass").val());
	var two = $.trim($(".inpass").val());
	var username = $.trim($(".phone").val());
	$(".gif").css('display', 'block');
	if(one==null||""==one){
		msg(0,"请输入密码");
		$(".gif").css('display', 'none');
		return false;
	}
   /* if(one.length<6){
    	msg(0,"请设置六位以上密码");
		return false;
	}*/
	if(two==null||""==two){
		msg(0,"请输入密码");
		$(".gif").css('display', 'none');
		return false;
	}
	/*if(two.length<6){
		msg(0,"请设置六位以上密码");
		return false;
	}*/
	if(one!=two){
		msg(0,"两次密码不一致")
		$(".gif").css('display', 'none');
		return false;
	}
	$.ajax({
		type:'post',
		url : ctx+"/ProtalUserManage/resetPasswordForUser",
		data:{
			telephone:username,
			password:one
		},
		success:function(data){
			eval("data = " + data);
			$(".gif").css('display', 'none');
			if(data.code==200){
				msg(1,data.msg);
				setTimeout(function(){
					window.location.href="http://www.gonet.cc";
				}, 2900);
			}else{
				msg(0,data.msg);
				return false;
			}
		}
	});
}
function tiao(n){
	if(n==1){
		$('.three').removeClass('on');
		$('.two').removeClass('on');
		$('.tiao').animate({width:'15%'},200,function(){
			$('.one').addClass('on');
		});
	}else if(n==2){
		$('.three').removeClass('on');
		$('.tiao').animate({width:'50%'},300,function(){
			$('.two').addClass('on');
		});
	}else if(n==3){
		$('.two').addClass('on');
		$('.tiao').animate({width:'100%'},300,function(){
			$('.three').addClass('on');
		});
	}
}
function boxMove(n){
	$('.content').css('display','none');
	$('.content').eq(n+1).css('display','block');
}
function coundDown(obj,n){
	obj.css('background','#ccc');
	obj.attr('disabled',true);
	obj.text(n+'秒后重新获取');
	var timer = setInterval(function(){
		n--;
		if(n==0){
			clearInterval(timer);
			obj.css('background','#47a4dc');
			obj.attr('disabled',false);
			obj.text('获取验证码');
		}else{
			obj.text(n+'秒后重新获取');
		}
	},1000);
}
function msg(code,str){
	$('.altMask > div').removeClass('true');
	$('.altMask > div').removeClass('false');
	$('.altMask').css('display','block');
	$('.msg').text(str);
	if(code==0){
		$('.altMask > div').addClass('false');
		$('.altMask > div').animate({top:'25%'},400);
		setTimeout(function(){
			$('.altMask > div').animate({top:'-160px'},200,function(){
				$('.altMask').css('display','none');
			});
		},2900);
	}else{
		$('.altMask > div').addClass('true');
		$('.altMask > div').animate({top:'25%'},400,function(){
			setTimeout(function(){
				$('.altMask > div').animate({top:'-160px'},400,function(){
					$('.altMask').css('display','none');
				});
			},2900);
		});
	}
}