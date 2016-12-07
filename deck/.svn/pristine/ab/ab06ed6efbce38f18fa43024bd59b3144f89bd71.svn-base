var tel = /^(((13[0-9]{1})|(14[4-7]{1})|(15[0-9]{1})|(170)|(17[1-8]{1})|(18[0-9]{1}))+\d{8})$/;
window.onload = function() {
	if(true){
		$('.banner').css('background','url('+$('#banner').val()+') no-repeat center');
		$('.banner').css('background-size','cover');
	}
	
	$('.toLogin').click(function() {
		loginAjax();
	});

	$("#cancel").click(function() {
		var obj = new repeatAlert();
		obj.close();
	});
	toRegister();
	toForget();
	$("#terms").click(function() {
		window.location.href = basePath + "/ProtalUserManage/goToTerms";
	});
}

// 跳转到注册页面
function toRegister() {
	$(".toRegister").on(
			'click',
			function() {// 跳转注册页面
				window.location.href = basePath
						+ "/ProtalUserManage/toRegister?id=" + id + "&mac="
						+ mac + "&ip=" + ip + "&url=" + url;
			});
}
// 修改密码
function toForget() {
	$(".toForget").click(function() {
		window.location.href = basePath + "/ProtalUserManage/goToForgetPs";
	});
}

function loginAjax() {
	$(".gif").css('display', 'block');
	var userName = $('.userName').val();
	var userPass = $('.userPass').val();
	if (userName == '' || null == userName) {
		msg(0, '请输入用户名');
		$(".gif").css('display', 'none');
		return false;
	}
	if (!tel.test(userName)) {
		$(".gif").css('display', 'none');
		msg(0, '请输入正确手机号');
		return false;
	}
	if (userPass == '' || null == userPass) {
		$(".gif").css('display', 'none');
		msg(0, '请输入密码');
		return false;
	}
	var	proLoginUrl = "/w/ProuserLogin";
	var	successUrl = "/w/goToWherePage";
	var	payurl = "/w/toPay";
	$.ajax({
		type : "POST",
		url : basePath + proLoginUrl,
		data : {
			name : $('.userName').val(),
			pwd : $('.userPass').val(),
			id : $("#id").val(),
			gw_id : gw_id,
			mac : $("#mac").val(),
			ip : $("#ip").val(),
			url : $("#url").val(),
			chargeType : 0
		},
		success : function(data) {
			var data = eval('(' + data + ')');
			$(".gif").css('display', 'none');
			if (data.code == 201) {
				msg(0, data.msg);
				$('.log-mask').css('display', 'none');
				
			} else if (data.code == 203) {
				$('.log-mask').css('display', ' none');
				window.location.href = basePath + "/w/mobileJinggao";
			} else if (data.code == 300) {
				var MAC = data.data[0].MAC.replace(/:/g, "");
				var model = data.data[0].Terminal_device;
				var obj = new repeatAlert();
				if (model != 0) {
					obj.open("设备<span>" + model + " 设备地址" + MAC
							+ "</span>正在使用该账号,是否继续登录!<br>(继续登录则设备<span>" + MAC
							+ "</span>会强制下线)");
				} else {
					obj.open("设备<span>" + MAC
							+ "</span>正在使用该账号,是否继续登录!<br>(继续登录则设备<span>" + MAC
							+ "</span>会强制下线)");

				}
				$("#sure").click(function() {
					Jump(data, successUrl, payurl);
				});

				// ------登录时检测是否已经有人登陆-------------------------------------------------------//
			} else if(data.code==101){
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
			}else if(data.code==302){
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
			}else {
				Jump(data, successUrl, payurl);
			}
		},
		error : function(e) {
			$('.log-mask').css('display', 'none');
		}
	});
}

// 跳转页面
function Jump(data, successUrl, payurl) {
	$('.log-mask').css('display', 'none');
	if (data.code == 202) { // 需要充值 doOrder.jsp
		window.location.href = basePath + payurl;
	} else if (data.code == 200 || data.code == 300||data.code == 302) {
		window.location.href = basePath + successUrl;
	}

}
// 登录 弹出确认框
function repeatAlert() {
	// 显示遮罩背景和弹出框
	this.open = function(str) {// str用来接收提示文本
		$('.wanr-text').html(str);
		$('.log-mask').css('display', 'block');
		$('.warn').css('display', 'block');
		$('.loader').css('display', 'none');
		// callback();
	};
	// 关闭弹出框
	this.close = function() {
		$('.log-mask').css('display', 'none');
		// callback();
	};
}

function msg(code, str) {
	$('.altMask > div').removeClass('true');
	$('.altMask > div').removeClass('false');
	$('.altMask').css('display', 'block');
	$('.msg').text(str);
	if (code == 0) {
		$('.altMask > div').addClass('false');
		$('.altMask > div').animate({
			top : '25%'
		}, 400);
		setTimeout(function() {
			$('.altMask > div').animate({
				top : '-160px'
			}, 200, function() {
				$('.altMask').css('display', 'none');
			});
		}, 2900);
	} else {
		$('.altMask > div').addClass('true');
		$('.altMask > div').animate({
			top : '25%'
		}, 400, function() {
			setTimeout(function() {
				$('.altMask > div').animate({
					top : '-160px'
				}, 400, function() {
					$('.altMask').css('display', 'none');
				});
			}, 2900);
		});
	}
}
