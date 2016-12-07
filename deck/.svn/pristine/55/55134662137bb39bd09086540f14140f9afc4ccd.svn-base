//全局变量

//标识verifyCode方法返回值
var verifyCodeFlag = false;
var reg = /^(((13[0-9]{1})|(14[4-7]{1})|(15[0-9]{1})|(170)|(17[1-8]{1})|(18[0-9]{1}))+\d{8})$/;// 手机号码判断正则

window.onload = function() {

	$('.next').click(function() {

		var n = $('.next').index(this);
		if (n == 0) {
			return changePs(n);

		} else if (n == 1) {
			return changePass(n);
		}
	});

	$(document).keydown(function(event) {
		if (event.keyCode == 13) {
			verifyCode();
		}
	});
	sendTelrand();
	check();
}// window.onload end
function check() {
	/**
	 * 输入手机号 焦点失去事件
	 */
	$('#phone').keyup(function() {
		var r = reg.test($(this).val());
		if (!r) {
			$(this).next().text('请输入正确的手机号');
		} else if ($(this).val() == '') {
			$(this).next().text('请输入手机号');
		} else {
			$(this).next().text('');
		}
	});
	$('#phone').click(function() {
		$(this).next().text('');
	});
	
	$('#code').click(function() {
		$(this).next().text('');
	});
	$('#code').keyup(function() {
		$(this).next().text('');
	});

	$('#pass').click(function() {
		$('#one').text('');
	});
	$('#inpass').click(function() {
		$('#two').text('');
	});
	$('#pass').keyup(function() {
		$('#one').text('');
	});
	$('#inpass').keyup(function() {
		$('#two').text('');
	});

}
/**
 * 发送验证码
 */
function sendTelrand() {
	$('.codeBtn').click(function() {
		var telephone = $('#phone').val();
		if (telephone == "") {

			$('#msgTel').text("请输入账号");
			return false;
		}
		if (!reg.test(telephone)) {

			$('#msgTel').text("请输入正确的手机号");
			return false;
		}
		
		if (reg.test(telephone) && telephone != "") {
		 
			$.ajax({
				type:'post',
				url : basePath+"/ProtalUserManage/check",
				data:{
					telephone:telephone
				},
				success:function(data){
					if(data=="false"){
						$('#msgTel').text("");
						sendTelCode();
						coundDown($('.codeBtn'), 60)
					}else{
						$('#msgTel').text("账户未注册,请先去注册您的wifi账户");
						return false;
					}
				}
			});
			
			
			
		}
	});

}
//检验用户是否存在
function checkUserIsHave(telephone){
	$.ajax({
		type:'post',
		url : basePath+"/ProtalUserManage/check",
		data:{
			telephone:telephone
		},
		success:function(data){
			if(data=="false"){
				$('#msgTel').text("");
			}else{
				$('#msgTel').text("账户未注册,请注册wifi账户");
				return false;
			}
		}
	});
}
// 修改密码
function changePs(n) {
	
	var telephone = $('#phone').val();
	var phonecode = $('#code').val();
	if (telephone == "") {
		$('#msgTel').text("请输入账号");
		return false;
	} else {
		$('#msgTel').text("");
	}
	if (!reg.test(telephone)) {
		$('#msgTel').text("请输入正确的手机号");
		return false;
	} else {
		$('#msgTel').text("");
	}
	if (phonecode == "") {
		$(".gif").css('display', 'none');
		$('#rand').text("请输入验证码");
		return false;
	} else {
		$('#rand').text("");
	}
	$.ajax({
		type : 'post',
		url : basePath + "/ProtalUserManage/checkRandCodeForPC",
		async : false,
		data : {
			telephone : telephone,
			phonecode : phonecode,
		},
		success : function(data) {
			data = JSON.parse(data);
			if (data.code == 200) {
				if (n < 1) {
					setTimeout(function() {
						boxMove(n);
					}, 400);
				}
			} else {
				$('#rand').text(data.msg);
				return false;
			}
		}
	});
}
/**
 * 发送验证码
 */
function sendTelCode() {
	$.ajax({
		type : 'post',
		url : basePath + "/TelCodeManage/sendTelCode",
		data : {
			tel : $('#phone').val(),
			templateCode:"SMS_12891467"
		},
		success : function(data) {
			if (data == -1) {
				$('#rand').text("验证码发送失败");
				return false;
			}
			if (data == -2) {
				$('#rand').text("请不要频繁发送验证码");
				return false;
			}
		}
	});
}
/**
 * 修改密码
 */
function changePass(n) {
	var password1 = $('#pass').val();
	var password = $('#inpass').val();
	var tele = $("#phone").val();
	$(".gif").css('display', 'block');
	if (password1 == '' || password1 == null) {
		$('#one').text('请输入密码');
		$(".gif").css('display', 'none');
		return;
	} else {
		$('#one').text('');
	}
	if (password == '' || password == null) {
		$('#two').text('请输入密码');
		$(".gif").css('display', 'none');
		return;
	} else {
		$('#two').text('');
	}
	if (password1 != password) {
		$('#two').text('两次密码不一致');
		$(".gif").css('display', 'none');
		return;
	} else {
		$('#two').text('');
	}
	$.ajax({
		type : 'post',
		url : basePath + "/ProtalUserManage/changePsForget",
		async : false,
		data : {
			telphone : tele,
			password : password
		},
		success : function(data) {
			$(".gif").css('display', 'none');
			if (data == 'true') {
				window.location.href = 'http://www.gonet.cc';
			} else {
				$('#two').next().text('修改失败');
				return;
			}
		}
	});
}

function boxMove(n) {
	$('.content').css('display', 'none');
	$('.content').eq(n + 1).css('display', 'block');
}
function coundDown(obj, n) {
	obj.css('background', '#ccc');
	obj.attr('disabled', true);
	obj.text(n + '秒后重新获取');
	var timer = setInterval(function() {
		n--;
		if (n == 0) {
			clearInterval(timer);
			obj.css('background', '#47a4dc');
			obj.attr('disabled', false);
			obj.text('获取验证码');
		} else {
			obj.text(n + '秒后重新获取');
		}
	}, 1000);
}
