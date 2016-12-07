var tel = /^(((13[0-9]{1})|(14[4-7]{1})|(15[0-9]{1})|(170)|(17[1-8]{1})|(18[0-9]{1}))+\d{8})$/;
window.onload=function(){
	if(true){
		$('.banner').css('background','url('+$('#banner').val()+') no-repeat center');
		$('.banner').css('background-size','cover');
	}
	//alert(1)
	/*$('.toLogin').click(function(){
		msg(1,'密码错误');
	});*/
	tiao(1);
	$('.next').click(function(){
		var n = $('.next').index(this);
		if(n==0){
			return doRegisterCheckUser(0);
		}else if(n==1){
			return checkPassword(1);
		}else{
			doRegesterUser();
		}
		toSendMeg();
	});
}
//发送验证码
function toSendMeg(){
	$('.codeBtn').click(function(){
		   coundDown($(this),60);
		   var password = $(".pass").val();
		   var phone = $('.phone').val();
		   sendMegToUser(phone,password);
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
//第一步用户名的校验
function doRegisterCheckUser(n){
	if(n==0){
		var phone = $('.phone').val();
		if(phone==''||phone==null){
			 msg(0,"请输入用户名");
              return false;
		}
		if(!tel.test(phone)){
			 msg(0,"请输入11位手机号");
             return false;
		}
		$.ajax({
			type : "POST",
			url : ctx+"/ProtalUserManage/doRegisterCheckUser",
			data : {
				telephone : phone,
				id:id,
				mac:mac,
				ip:ip,
				url:url,
			},
			success : function(data) {
				eval("data = " + data);
				if(data.code=="201"){
					 msg(0,data.msg);
					 return false;
				}else{
					if(n<2){
						$('.content').addClass('small');
						setTimeout(function(){
							boxMove(n);
						},400);
					} 
				}
			},
			error:function(data){
				msg(0,"网络忙,请稍后");
				return false;
			}
		});
	}
}
//校验密码
function checkPassword(n){
	if(n==1){
	   var password = $(".pass").val();
	   if(password==''||null==password){
			msg(0,"请输入密码");
		   return false;
	   }
	   /*if(password.length<6){
		   msg(0,"请设置六位以上密码");
		   return false;
	   }*/
	   var md5Pwd=hex_md5(password);
	   $("#pwd").val(md5Pwd);
	   if(n<2){
			$('.content').addClass('small');
			setTimeout(function(){
				boxMove(n);
			},400);
		}
	   var password = $(".pass").val();
	   var phone = $('.phone').val();
	   coundDown($('.codeBtn'),60)
	   sendMegToUser(phone,password);
	}
}
//发送验证码
function sendMegToUser(phone,password){
	$.ajax({
		type : "POST",
		url : ctx+"/TelCodeManage/sendTelCode",
		data : {
			tel : phone,
			templateCode:"SMS_12946746",
			meg:password
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
//注册
function doRegesterUser(){
	 var md5pwd =  $("#pwd").val();
//	 var siteid =  $("#siteid").val();
	 var phone = $('.phone').val();
	 var phonecode =  $(".code").val();
	 $(".gif").css('display', 'block');
	 if(phonecode==null||phonecode==""){
		 msg(0,"请输入验证码");
		 $(".gif").css('display', 'none');
		 return false;
	 }
	 $.ajax({
			type : "POST",
			url : ctx+"/ProtalUserManage/doRegister",//TODO 移动端注册
			data : {
				telephone : phone,
				md5Pwd : md5pwd,
				phonecode: phonecode,
				id:id,
				mac:mac,
				ip:ip,
//				url:url,
//				siteid:siteid,
				sex :1
			},
			success : function(data) {
				eval("data = " + data);
				$(".gif").css('display', 'none');
				if(data.code==201){
					msg(0,data.msg);
					return;
				}else{
					window.location.href="http://www.gonet.cc";
				}
			},
			error:function(){
				msg(0,"网络繁忙,请稍后");
				return false;
			}
		});
	 
	 
}


function boxMove(n){
	tiao(n+2)
	$('.box').animate({left:(-277*(n+1))+'px'},400,function(){
		$('.content').addClass('big');
		setTimeout(function(){
			$('.content').removeClass('small');
			$('.content').removeClass('big');
		},400);
	});
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
