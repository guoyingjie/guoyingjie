
var mytimer;
$(function() {
	  $(".pic").click(function(){ 
		  window.history.back(); });
		// 设置密码的可见性
		passVisibility();
	// 更改密码提交
	$("#dl").click(function() {
		$("#zhforms").submit();
//		//doResetPassword();
	});
	$("#sendCode").on(
			"click",
			function() {
				// 倒计时
				$(".yzm").attr("disabled", "true");
				mytimer = setInterval("timer()", 1000);
				if(zhFormVd.element($("#num"))){
					$.ajax({
						type : "POST",
						url : "/deck/TelCodeManage/sendTelCode",
						data : {
							tel : $.trim($("#num").val()),
							templateCode:"SMS_12926266"
						},
						success : function(msg) {
							// alert(msg);
							if (msg == -1) {
								$("#dl").css("background","red").val(
										"验证码发送失败");
								return false;
							}
							if(msg==-2){
								clearTimeout(mytimer);
								$(".yzm").text("获取手机验证码");
								$(".yzm").removeAttr("disabled");
								floatAlert(280,55,'请不要频繁发送验证码,谢谢您的配合与支持!',2300);
								return false;
							}
							
						}
					});
				}
			});
});
var zhFormVd = $("#zhforms").validate({
	
	// 错误提示样式,在下方提示
	errorPlacement : function(error, element) {
		$("#errorMsg").html("");
//		$("#errorMsg").css( "border","1px #e0e0e0 solid;");
		var el = element.next();
        if(element.context.id == "ma"){
        	el = element.next().next();
        }
		error.css({
			display : "inline",
			color : "#ee7676",
			position : "relative"
		}).appendTo(el.addClass("error"));
	},
	submitHandler : function(form) {
		var phonecode = $.trim($("#ma").val());
		var password = $.trim($("#pwd").val());
		var telephone = $.trim($("#num").val());
		$.ajax({
			type : "POST",
			url : "/deck/ProtalUserManage/doResetPassword",
			//async : false,
			data : {
				telephone : telephone,
				phonecode : phonecode,
				password : password,
			},
			success : function(data) {
				eval("data = " + data);

				if (data.code == "303") {//修改密码成功
					changepwd = true;
					window.location.href =basePath+"/mobileAuther";
				}else if(data.code == "301" || data.code == "302"){
					$("#errorMsg").html(data.msg);
				}else {
					window.location.href = basePath+"/xgsb.html";
				}
			},
			error : function(data) {
				changepwd = false;
				window.location.href = basePath+"/xgsb.html";
			}
		});
	},
	// 校验规则
	rules : {
		pwd : {
			required : true,
			rangelength:[4,16],
		},
		ma : {
			required : true,
			digits : true,
			rangelength:[4,4]
		}
	},
	// 提示文本
	messages : {
		pwd : {
			required : "请输入密码",
			rangelength:"用户密码为4~16位长度"
		},
		ma : {
			required : "请输入验证码",
			digits :"验证码是数字",
			rangelength:"验证码长度为4~4位"
		}
	}
});

var x_toggle = 1;//可见状态
/**
 * 设置密码的可见性
 */
function passVisibility() {
	// 密码可见性
	$("#tu").on("click", function() {
		if (x_toggle == 1) {
			$("#pwd").attr("type", "text");
			$("#tu").attr("src", "mobile/img/am.png");
			x_toggle = 2;
		} else {
			$("#pwd").attr("type", "password");
			$("#tu").attr("src", "mobile/img/mm.png");
			x_toggle = 1;
		}
	});
}
var x = 90;
//倒计时
function timer() {
	x = x - 1;
	$(".yzm").text(x + "秒后重新发送");
	if (x < 1) {
		x = 50;
		$(".yzm").text("获取手机验证码");
		$(".yzm").removeAttr("disabled");
		clearTimeout(mytimer);
	}
}

