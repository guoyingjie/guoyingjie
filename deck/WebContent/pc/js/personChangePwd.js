
var mytimer;
$(function() {
	
	// 修改密码提交
	$("#xgtj").click(function() {
		$("#xgform").submit();
	});
	
	$(document).keydown(function(event){ 
		if(event.keyCode==13){
			$("#xgform").submit();
		}
		});
	
	$("#obtainCode").on("click",function() {
		// 倒计时
		$("#obtainCode").attr("disabled", "disabled");
		mytimer = setInterval("timer()", 1000);
				if (xgForm.element($("#num2"))) {
					$.ajax({
						type : "POST",
						url : "/deck/TelCodeManage/sendTelCode",
						data : {
							tel : $.trim($("#num2").val()),
							templateCode:"SMS_12926266"
						},
						success : function(msg) {
							if (msg == -1) {
								return false;
							}
							if(msg==-2){
								clearTimeout(mytimer);
								$("#obtainCode").html("获取手机验证码");
								$("#obtainCode").removeAttr("disabled");
								floatAlert(280,55,'请不要频繁发送验证码,谢谢您的配合与支持!',2300);
								return false;
							}

						}
					});
				}

			});
});

jQuery.validator.addMethod("phone",function(value, element) {
					return this.optional(element)|| /^(((13[0-9]{1})|(14[4-7]{1})|(15[0-9]{1})|(170)|(17[6-8]{1})|(18[0-9]{1}))+\d{8})$/.test(value);
				}, "请输入正确手机账号！");
var xgForm = $("#xgform").validate({
	
	
	// 错误提示样式,在下方提示
	errorPlacement : function(error, element) {
		$("#errorMsg").html("");
		 $("#sj").html("");
		var el = element.next();
		if(element.context.id=="pwd"){
			el = element.next().next().next();
		}
		if (element.context.id == "yz") {
			el = element.next().next() ;
		}
		error.css({
		}).appendTo(el.addClass("error"));
	},
	submitHandler : function(form) {
		var phonecode = $.trim($("#yz").val());
		var password = $.trim($("#pwd").val());
		var telephone = $.trim($("#num2").val());
		
		$.ajax({
			type : "POST",
			url : "/deck/ProtalUserManage/doResetPassword",
			data : {
				telephone : telephone,
				phonecode : phonecode,
				password : password,
			},
			success : function(data) {
				eval("data = " + data);
				if (data.code == "303") {//修改密码成功
					window.location.href = basePath + "/ProtalUserManage/pcToResetPwdSuccess";
					
				} else if (data.code == "301" || data.code == "302") {
					$("#errorMsg").html(data.msg);
					
				} 

			
			},
			error : function(data) {
				window.location.href = basePath + "/ProtalUserManage/pcToResetPwdFailure";
			}
		});
	},
	// 校验规则
	rules : {
		num2 : {
			required : true,
			rangelength : [ 11, 11 ],
			phone : true,
			remote : {
				url : "/deck/ProtalUserManage/checkTel", //后台处理程序
				type : "post", //数据发送方式
				data : { //要传递的数据
					telephone : function() {
						return $.trim($("#num2").val());
					}
				}
			}
		},
		pwd : {
			required : true,
			rangelength : [4,16],
		},
		yz : {
			required : true,
			digits : true,
			rangelength : [4,4],
			
		}
	},
	// 提示文本
	messages : {
		num2 : {
			required : "请输入手机号",
			rangelength : "请输入正确的手机号！",
			phone : "请输入正确的手机号！",
			remote : "该手机号没有注册"
		},
		pwd : {
			required : "请输入密码",
			rangelength : "用户密码为4~16位长度"
		},
		yz : {
			required : "请输入验证码",
			digits : "验证码是数字",
			rangelength : "验证码长度为4位"
		}
	}
});
var x = 90;
//倒计时
function timer() {
	x = x - 1;
	$("#obtainCode").html(x + "秒后重新发送");
	if (x < 1) {
		x = 50;
		$("#obtainCode").html("获取手机验证码");
		$("#obtainCode").removeAttr("disabled");
		clearTimeout(mytimer);
	}
}
/* 显示隐藏密码 */
$('.eye').mousedown(function(){
	var str=$('.eye').prev().prev().val();
	$('.eye').prev().prev().css('display','none');
	$('.eye').prev().val(str);
	$('.eye').prev().css('display','block');
	});
	$('.eye').mouseup(function(){
	$('.eye').prev().prev().css('display','block');
	$('.eye').prev().css('display','none');
	});