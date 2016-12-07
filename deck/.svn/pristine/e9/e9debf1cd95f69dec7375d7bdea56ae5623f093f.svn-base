/*var reg1 = /^[a-zA-Z0-9\u4e00-\u9fa5-_]{2,10}$/;// 2-10位中英文数字
var telstandard = /^(((13[0-9]{1})|(14[4-7]{1})|(15[0-9]{1})|(170)|(17[6-8]{1})|(18[0-9]{1}))+\d{8})$/;// 手机号码
var passstandard = /^[A-Za-z0-9_-]{4,16}$/;// 密码
var telcodestandard = /^[0-9]{4}$/;
var x_toggle = 1;
var x = 50;

var errSubBtnColor = "red";
var checkFag = false;
var changepwd = false;
var flag = false;*/
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
				$(".yzm").attr("disabled", "disabled");
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
											$("#dl").css("background", "red").val("验证码发送失败");
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
jQuery.validator.addMethod("phone", function(value, element) { 
    return this.optional(element) || /^(((13[0-9]{1})|(14[4-7]{1})|(15[0-9]{1})|(170)|(17[1-8]{1})|(18[0-9]{1}))+\d{8})$/.test(value);         
}, "请输入正确手机账号！"); 
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
//	success: function(label) { 
//		alert(1);
//	},
	submitHandler : function(form) {
	
		var phonecode = $.trim($("#ma").val());
		var password = $.trim($("#pwd").val());
		var telephone = $.trim($("#num").val());
		var successUrl;
		if(fromFw!="null"&&fromFw!=""&&fromFw!=null&&fromFw!=undefined){
			successUrl="/registerSuccess.html";
		}else{
			successUrl="/xgcg.html";
			
		}
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
					window.location.href =basePath+successUrl;
				}else if(data.code == "301" || data.code == "302"){
					$("#errorMsg").html(data.msg);
				}else {
					window.location.href = basePath+"/xgsb.html";
				}
				
				
/*				else if(data.code == "300"){//手机号不存在
					document.getElementById("no").style.display = "block";
				}else if (data.code == "301") {//验证码错误

						document.getElementById("yan").style.display = "block";
					} else if(data.code == "302") {//验证码失效
						
						document.getElementById("failCode").style.display = "block";
					}else{
						window.location.href = basePath+"/xgsb.html";
						
					}*/
				
			},
			error : function(data) {
				changepwd = false;
				window.location.href = basePath+"/xgsb.html";
			}
		});

		
		
/*		document.getElementById("dl").disabled = true;
		$("#errorMsg").html("");
		$("#passwordMd5").val(hex_md5($("#schoolRandCode").val()));
		$.ajax({
			type:"POST",
			url: basePath + "/ProuserLogin",
			data:{
				name :$("#schoolNumber").val(),
				pwd : $("#passwordMd5").val(),
				id 	: $("#id").val(),
				mac : $("#mac").val(),
				ip 	: $("#ip").val(),
				url : $("#url").val()
			},
			success:function(data){
			var data=eval('(' + data + ')');
			if(data.code == 201){
				//alert(data.msg);
				$("#errorMsg").html(data.msg);
			}else{
				Jump(data);
			}
				
			},
			error:function(e){
				$("#errorMsg").html("服务不可用");
			}
		});
		setTimeout(function(){
			document.getElementById("dl").disabled = false;
		},3000);*/
	},
	// 校验规则
	rules : {
		num : {
			required : true,
			rangelength:[11,11],
			phone:true,
			remote:{
			    url: "/deck/ProtalUserManage/checkTel",     //后台处理程序
			    type: "post",               //数据发送方式
			//    dataType: "json",           //接受数据格式   
			    data: {                     //要传递的数据
			    	telephone: function() {
			            return $.trim($("#num").val());
			        }
			    }
			}
		},
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
		num : {
			required : "请输入手机号",
			rangelength:"请输入正确的手机号！",
			phone:"请输入正确的手机号！",
			remote:"该手机号没有注册"
		},
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
			$("#tu").attr("src", "../mobile/img/am.png");
			x_toggle = 2;
		} else {
			$("#pwd").attr("type", "password");
			$("#tu").attr("src", "../mobile/img/mm.png");
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

