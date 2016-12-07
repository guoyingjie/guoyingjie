var errSubBtnColor = "red";
var mytimer;
$(function() {
	 pwd(false);//调用密码提示框显示/隐藏函数
	 user(false);//调用手机号提示框显示/隐藏函数
	 test(false);//调用验证码提示框显示/隐藏函数
	
	
	
	//选择性别
	
	$('.fn-sex li').click(function(){
		var n=$('.fn-sex li').index(this);
		$('.fn-sex li').removeClass('on').eq(n).addClass('on');
	});
	
//	$('#nan').click(function(){
//		$('#nan').addClass("on");
//		$('#nv').removeClass("on");
//	});
//	$('#nv').click(function(){
//		$('#nv').addClass("on");
//		$('#nan').removeClass("on");
//	});
	
	$(document).keydown(function(event){ 
		if(event.keyCode==13){
			$("#zcform").submit();
		}
		});
	$(".ui-btn").on(
			"click",
			function() {
				// 倒计时
				// 倒计时
				$(".ui-btn").attr("disabled", "disabled");
				mytimer = setInterval("timer()", 1000);
				if(zcForm.element($("#num"))){
						if(zcForm.element($("#pwd"))){
							
							
								$.ajax({
									type : "POST",
									url : basePath + "/TelCodeManage/sendTelCode",
									data : {
										tel : $("#num").val(),
										templateCode:"SMS_12916561"
									},
									success : function(msg) {
										if (msg == -1) {
											$("#dl").css("background", errSubBtnColor).val(
													"验证码发送失败");
											return false;
										}
										if(msg==-2){
											clearTimeout(mytimer);
											$(".ui-btn").html("获取手机验证码");
											$(".ui-btn").removeAttr("disabled");
											floatAlert(280,55,'请不要频繁发送验证码,谢谢您的配合与支持!',2300);
											return false;
										}
										
									}
								});
						}
				}

			});
	
//注册提交
	$("#zctj").click(function() {
		$("#zcform").submit();
	});
});

jQuery.validator
		.addMethod(
				"phone",
				function(value, element) {
					return this.optional(element)
							|| /^(((13[0-9]{1})|(14[4-7]{1})|(15[0-9]{1})|(170)|(17[3-8]{1})|(18[0-9]{1}))+\d{8})$/
									.test(value);
				}, "请输入正确手机账号！");

var zcForm = $("#zcform").validate({

	// 错误提示样式,在下方提示
	errorPlacement : function(error, element) {
		//$("#xs").html("");
		$("#sj").html("");
		//$("#mi").html("");
		// $("#errorMsg").css( "border","1px #e0e0e0 solid;");
		var el = element.next();
		
		if (element.context.id == "yz") {
			el = element.next().next();
		}
		if (element.context.id == "pwd") {
			el = element.next().next().next();
		}
		error.css({
		}).appendTo(el.addClass("error"));
	},
	// success: function(label) {
	// },
	submitHandler : function(form) {
		var phonecode = $.trim($("#yz").val());
		var password = $.trim($("#pwd").val());
		var telephone = $.trim($("#num").val());
		var md5Pwd=hex_md5(password);
		var sex=$(".on").attr("value");//1代表男,0代表女
		//检测注册手机号是否为电信手机号
		var chargeType = 0;//默认计费类型为普通计费模式
		if(checkPhoneType(telephone)){//如果是电信手机号则传递chargeType到后台标明为计费规则归属为电信既：chargeType = 1
			chargeType = 1;
		}	
		//$("#zctj").click(function(){
			$.ajax({
				type : "POST",
				url : basePath +"/ProtalUserManage/doRegister",
				data : {
					telephone : telephone,
					md5Pwd : md5Pwd,
					phonecode : phonecode,
					chargeType : chargeType,// 计费规则类型(0普通，1电信)
					id :  id, 
					mac : mac,
					ip : ip, 
					url :url,
					sex :sex
					
				},
				success : function(data) {
					eval("data = " + data);

					if (data.code == "201") {
						//$("#xs").html("");
						$("#sj").html(data.msg);
					}
					if (data.code == "202") {
						$("#pwd").val("");
						$("#yz").val("");
						$("#num").val("");
						// 跳转注册成功页面	
						window.location.href = basePath +"/ProtalUserManage/registerSuccess";
					}
					if (data.code == "200") {
						$("#password").val("");
						$("#phonecode").val("");
						$("#telephone").val("");
						// 注册成功
						 window.location.href=basePath+"/goToSuccess";
					}
				},
				error : function(data) {// 注册失败
					window.location.href = basePath + "/ProtalUserManage/registerFail";
				}
			});
			setTimeout(function(){
				document.getElementById("zctj").disabled = false;
			},3000);
		//})
		
	},
	// 校验规则
	rules : {
		num : {
			required : true,
			rangelength : [ 11, 11 ],
			phone : true,
//			remote:{
//				type : "POST",
//				url : basePath +"/ProtalUserManage/check",
//				data :{
//					telephone : function(){return $("#num").val();}
//				}
//				
//			}
				
		},
		pwd : {
			required : true,
			rangelength : [ 4, 16 ]
		},
		yz : {
			required : true,
			digits : true,
			rangelength : [ 4, 4 ],
		}
	},
	// 提示文本
	messages : {
		num : {
			required : "请输入手机号",
			rangelength : "请输入正确的手机号！",
			phone : "请输入正确的手机号！",
//			remote : "该号码已注册,请直接登录"
		},
		pwd : {
			required : "请输入密码",
			rangelength : "用户密码为4~16位长度"
		},
		yz : {
			required : "请输入验证码",
			digits : "验证码是数字",
			rangelength : "验证码长度为4~4位",
		}
	}
});

$(".ui-tel").bind('input propertychange',function(){
	var tel= $.trim($(".ui-tel").val());
	if(tel.length>10){
		$.ajax({
			type:"POST",
			url:basePath+"/ProtalUserManage/check",
			data:{
				telephone : tel
			},
			success:function(data){
				eval("data="+data);
				if(data){
					$(".ui-btn").removeAttr("disabled");
					$("#sj").html("");
				}else{
					$(".ui-btn").attr("disabled","disabled");
					$("#sj").html("该号码已注册,请直接登录");
					$(".ui-btn").html("获取验证码");
					clearTimeout(mytimer);
				}
			}
		}); 
	}
});

var x = 90;
// 倒计时
function timer() {
	x = x - 1;
	$(".ui-btn").html(x + "秒后重新发送");
	if (x < 1) {
		x = 50;
		$(".ui-btn").html("获取手机验证码");
		$(".ui-btn").removeAttr("disabled");
		clearTimeout(mytimer);
	}
}
/**
 * 验证手机号是否为电信制定手机号段
 */
function checkPhoneType(phoneNum){
	var flag = false;
	var subPhone = phoneNum.substr(0,7);
	switch(subPhone) {
		case "1775580" :
			flag = true;
		break;
		case "1775589" :
			flag = true;
		break;
		case "1895674" :
			flag = true;
		break;
		case "1895673" :
			flag = true;
		break;
		case "1894908" :
			flag = true;
		break;
		case "1890968" :
			flag = true;
		break;
		case "1813310" :
			flag = true;
		break;
		case "1815627" :
			flag = true;
		break;
		case "1809677" :
			flag = true;
		break;
		case "1811051" :
			flag = true;
		break;
		case "1805697" :
			flag = true;
		break;
		case "1805588" :
			flag = true;
		break;
	}
	return flag;
	
}
/* 手机号错误提示显示/隐藏 */
var user=function(judeng){
	if(judeng){
		$('.tel-hint').css('display','none');
	}else{
		$('.tel-hint').css('display','block');
	}
};
/* 密码错误提示显示/隐藏 */
var pwd=function(judeng){
	if(judeng){
		$('.pwd-hint').css('display','none');
	}else{
		$('.pwd-hint').css('display','block');
	}
};
/* 验证码错误提示显示/隐藏 */
var test=function(judeng){
	if(judeng){
		$('.test-hint').css('display','none');
	}else{
		$('.test-hint').css('display','block');
	}
};
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
	

	
	
