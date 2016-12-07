
var tel = /^(((13[0-9]{1})|(14[4-7]{1})|(15[0-9]{1})|(170)|(17[1-8]{1})|(18[0-9]{1}))+\d{8})$/;
var v="4.1.4";
$(function(){

	success();
	sendMsg();
});

/**
 * 绑定按钮确定的时候触发的事件
 */
function success(){
	$(".subBtn").click(function(){
		 goToSuccess();
	});
}
/**
 * 发送验证码
 */
function sendMsg(){
	$('.codeBtn').click(function(){
		var userName = $.trim($(".phone").val());
		var password = $.trim($(".pass").val());
		if(userName==""||null==userName){
			msg(0,"请输入手机号");
			return ;
		}
		if(password==""||null==password){
			msg(0,"请输入密码");
			return;
		}
		
		coundDown($('.codeBtn'),60);
		sendMegToUser(userName);
	});
}
/**
 * 绑定用户成功
 */
function goToSuccess(){
		var usernames = $("#username").val();
		var username = $(".phone").val();
		var code = $(".codeNum").val();
		var password = $(".pass").val();
		var openid=$("#openid").val();
		var siteId=$("#siteId").val();
		if (username == "" || null == username) {
			msg(0,"请输入手机号");
			return;
		}
		if(!tel.test(username)){
			msg(0,"请输入11位手机号");
			return;
		}
		
		if ($.trim(username) == $.trim(usernames)) {
			msg(0,"新的账号与当前账户相同");
			return;
		}
		if (password == "" || password == null) {
			msg(0,"请输入密码");
			return;
		}
		if (code == "" || null == code) {
			msg(0,"请输入验证码");
			return;
		}
		$.ajax({
			type : "post",
			url : ctx + "/weChatPublicNum/doChangeBind",
			data : {
				userName : username,
				sessionName : usernames,
				code : code,
				password : password,
				v:v
			},
			success : function(data) {
				data = JSON.parse(data);
				if (data.code == 200) {
					msg(1,"绑定成功");
					setTimeout(function(){
						window.location.href=ctx+"/weChatPublicNum/gotoBindSuccess?openId="+openid+"&siteId="+siteId+"&userName="+username+"&v="+v;
					}, 1000);
				} else {
					msg(0,data.msg);
					return;
				}
			},
			error : function() {
				msg(0,"服务忙,请稍后");
			}
		}); 
}
/**
 * 倒计时功能插件
 * @param obj
 * @param n
 */
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
//发送验证码
function sendMegToUser(phone){
	$.ajax({
		type : "POST",
		url : ctx+"/TelCodeManage/sendTelCode",
		data : {
			tel : phone,
			templateCode:"SMS_12881576",
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



/** 
 * 消息提示框 1--正确 0--错误
 * @param code
 * @param str
 */
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
			},1000);
		});
	}
}