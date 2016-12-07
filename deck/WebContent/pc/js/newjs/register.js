var reg = /^(((13[0-9]{1})|(14[4-7]{1})|(15[0-9]{1})|(170)|(17[1-8]{1})|(18[0-9]{1}))+\d{8})$/;// 手机号码
window.onload=function(){
 
	$('.codeBtn').click(function(){
		if($('.phone').val() && $('.phone').val() != ""&&reg.test($('.phone').val())){
			if( $('.pass').val() != ""){
			   coundDown($(this),60,$('.phone').val());
			}else{
				$('.pass').next().text('请输入密码')
				return;
			}
			$('.phone').next().text('');
		}else{
			$('.phone').next().text('请输入手机号');
			return;
		}
		
	});
	$('.over').click(function(){
		register();
	});
	$('.phone').keyup(function(){
		var r = reg.test($(this).val());
		if(!r){
			$('.spaninfo').text('请输入正确的手机号');
			return;
		}else{
			$('.spaninfo').text('');
		}
	});
	 
	/**
	 * 回车事件
	 */
	$(document).keydown(function(event){
		if(event.keyCode==13){
			register();
		}
	});
	check();
}
function check(){
	$('.phone').click(function(){
		$('.spaninfo').text('');
	});
	$('.pass').click(function(){
		$('#passtext').text('');
	});
	$('.code').click(function(){
		$('#randText').text('');
	});
}




function register(){
	var phone = $('.phone').val();
	var pass =$('.pass').val() /*hex_md5($('.pass').val());*/
	var code = $('.code').val();
	$(".gif").css('display', 'block');
	if(phone==null||phone==''){
		$('.spaninfo').text('请输入手机号');
		$(".gif").css('display', 'none');
		return ;
	}else{
		$('.spaninfo').text('');
	}
	if(!reg.test(phone)){
		$('.spaninfo').text('请输入正确的手机号');
		$(".gif").css('display', 'none');
		return ;
	}else{
		$('.spaninfo').text('');
	}
	if(pass==null||pass==''){
		$(".gif").css('display', 'none');
		$('#passtext').text('请输入密码');
		return ;
	}else{
		$('#passtext').text('');
	}
	if(code==null||code==''){
		$(".gif").css('display', 'none');
		$('#randText').text('请输入验证码');
		return ;
	}else{
		$('#randText').text('');
	}
	$.ajax({
		type: 'post',
		url: basePath+"/ProtalUserManage/doRegister",//TODO PC端注册
		data: {
			telephone :phone,
			md5Pwd:hex_md5(pass),
			phonecode:code,
//			chargeType:0,//暂不判断电信用户
			id: id,
			sex:0,//暂时前端无性别选择
			mac:mac,
			ip:ip
//			url:url
		},
		success: function(data){
			data=JSON.parse(data);
				$(".gif").css('display', 'none');
				if(data.code==200){
					window.location.href='http://www.gonet.cc'
				}else{
					$('#randText').text(data.msg);
				}
		}
	});
}

function coundDown(obj,n,telephone){
	
	$.ajax({
		type: 'post',
		url: basePath+"/ProtalUserManage/check",
		data: {
			telephone :telephone,
		},
		success: function(data){
			if(data=="false"){
				$('.phone').next().text('账号已存在,请直接去登录');
				return false;
			}else{
				sendTelCode(telephone);
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
				return true;
			}
		}
	});
}

function sendTelCode(tel){
	$.ajax({
		type: 'post',
		url: basePath+"/TelCodeManage/sendTelCode",
		data: {
			tel : tel,
			templateCode: 'SMS_12946746',
			meg:$('.pass').val()
		},
		success: function(data){
			if(data==-1){
				$('#randText').text("验证码发送失败");
				return false;
			}
			if(data==-2){
				$('#randText').text("请不要频繁发送验证码");
				return false;
			}
		}
	});
}

/**
 * by:cuimiao
 * 检测用户是否存在
 * @param code
 * @param str
 */
function isRegister(phoneNum){
	$.ajax({
		type: 'post',
		url: basePath+"/ProtalUserManage/check",
		data: {
			telephone :phoneNum,
		},
		success: function(data){
			if(data=="false"){
				$('.phone').next().text('账号已存在,请直接去登录');
				return false;
			}else{
				return true;
			}
		}
	});
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
