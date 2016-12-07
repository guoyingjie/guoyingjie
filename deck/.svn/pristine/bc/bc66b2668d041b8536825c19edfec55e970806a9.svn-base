
var reg1 = /^[a-zA-Z0-9\u4e00-\u9fa5-_]{2,10}$/;// 2-10位中英文数字
var telstandard = /^(((13[0-9]{1})|(14[4-7]{1})|(15[0-9]{1})|(170)|(173)|(17[1-8]{1})|(18[0-9]{1}))+\d{8})$/;// 手机号码
var passstandard = /^[A-Za-z0-9_-]{4,16}$/;// 密码
var telcodestandard = /^[0-9]{4}$/;
var x_toggle=1;
var x=90;
var mytimer;
var errSubBtnColor = "red";// 提交按钮，错误颜色
var theme = "white";// 主题颜色
var checkFag=false;

$(function(){
	var i=0;
	var w=$(".main li").width();//获取图片宽度
	//轮播图代码
	var timer=setInterval(function(){
		i++;
		if(i==1){
			$(".main").animate({left:-w*i+"px"},500);
			$('.ctrl li').removeClass("on").eq(1).addClass("on");
			i=-1;
		}else if(i==0){
			$(".main").animate({left:-w*i+"px"},500);
			$('.ctrl li').removeClass("on").eq(0).addClass("on");
		}
	},2500);
	//轮播图代码
	//关闭轮播图按钮代码
	$('.gb').click(function(){
		$('.banner').hide();
		clearInterval(timer);
		$('form').animate({top:"15%"});
	});
	
	$('.nan').click(function(){
		$('.nan').addClass("on");
		$('.nv').removeClass("on");
		$("#gender").val(1);
	});
	$('.nv').click(function(){
		$('.nv').addClass("on");
		$('.nan').removeClass("on");
		$("#gender").val(2);
	});

	$("#goBack").click(function(){
		window.history.back();
	});
	
	$("#tu").click(function(){
		var mm=document.getElementById("password");//获取密码input框的ID
		var aa=document.getElementById("tu");//获取显示隐藏密码的图片ID
		if(mm.type=="password"){
			mm.type="text";
			aa.src=imgPath+"/mm.png";
		}else if(mm.type=="text"){
			mm.type="password";
			aa.src=imgPath+"/am.png";
		}
	});
	

	//设置密码的可见性
	passVisibility();

	
	$("#doRegisterBtn").click(function() {
			doRegister();
		
	});
	
	$("#registeChk").on("click",function(){
		var chk=document.getElementById("registeChk");
		if(chk.checked==true){
			$("#doRegisterBtn").click(function() {
				doRegister();
			});
		}else{
			$("#doRegisterBtn").unbind("click");
			$("#doRegisterBtn").css("color", theme).val("注册");
		}
	});
	
	$("#telephone").blur(function() {	
		telCheck();
	});
		
	
	$("#password").blur(function() {
		passCheck();
	});
	$("#phonecode").blur(function() {
		telcodeCheck();
	});
	$("#telephone").bind('input propertychange',function(){
		$(".set_icon3").attr("disabled","disabled");
		var tel= $.trim($("#telephone").val());
			if(tel.length>10){
				tellCheck();
			}
	});
	$(".set_icon3").on("click",function (){
		var flag=getIsRight();
		if(flag){
			$.ajax({
				type : "POST",
				url : basePath+"/TelCodeManage/sendTelCode",
				data : {
					tel : $("#telephone").val(),
					templateCode:"SMS_12916561"
				},
				success : function(msg) {
					if(msg==-1){
						$("#doRegisterBtn").css("color", errSubBtnColor).val("验证码发送失败");
						return false;
					}
					if(msg==-2){
						clearTimeout(mytimer);
						$(".set_icon3").text("获取手机验证码");
						$(".set_icon3").removeAttr("disabled");
						floatAlert(280,55,'请不要频繁发送验证码,谢谢您的配合与支持!',2300);
						return false;
					}
					//倒计时
					$(".set_icon3").attr("disabled","disabled");
					mytimer=setInterval("timer()",1000); 
				}
			});
		}
	});
});

/**
 * 点击获取手机验证码时验证用户名可密码是否可用,并获取验证码
 */
function getIsRight(){
	var telisable=telCheck();
	if(telisable==false){
		return false;
	}
	
	if(!rel){
		return false;
	}
	var passisable=passCheck(); 
	if(passisable==false){
		return false;
	}
	return true;
}


//倒计时
function timer(){
	x=x-1;
	$(".set_icon3").text(x+"秒后重新发送");
	if(x<1){
		x=90;
		$(".set_icon3").text("获取手机验证码");
		$(".set_icon3").removeAttr("disabled");
		clearTimeout(mytimer);
	}
}


/**
 * 注册提交
 */
function doRegister() {
	if (telCheck()&&passCheck()&&telcodeCheck()&&rel) {
		$("#doRegisterBtn").css("color", theme).val("注册中...");
//		$("#doRegisterBtn").attr("disabled");///防止多次点击后重复提交
		document.getElementById("doRegisterBtn").disabled = true;
		var phonecode=$("#phonecode").val();
		var password=$("#password").val();
		var telephone=$("#telephone").val();
		var md5Pwd=hex_md5(password);
		var sex = $(".xb .on").attr("sexs");//1代表男,0代表女
		$("#md5Pwd").val(md5Pwd);
		//检测注册手机号是否为电信手机号
		var chargeType = 0;//默认计费类型为普通计费模式
		if(checkPhoneType(telephone)){//如果是电信手机号则传递chargeType到后台标明为计费规则归属为电信既：chargeType = 1
			chargeType = 1;
			
		}
		$.ajax({
			type : "POST",
			url : basePath+"/ProtalUserManage/doRegister",
			data : {
				telephone : telephone,
				md5Pwd : md5Pwd,
				phonecode: phonecode,
				chargeType: chargeType,//计费规则类型(0普通，1电信)
				id:id,
				mac:mac,
				ip:ip,
				url:url,
				sex :sex
			},
			success : function(data) {
				eval("data = " + data);
			
				if(data.code=="201"){
					$("#doRegisterBtn").css("color", errSubBtnColor).val(data.msg);
				}
				if(data.code=="202"){
					$("#password").val("");
					$("#phonecode").val("");
					$("#telephone").val("");
					//跳转支付页面
					 window.location.href=basePath +"/toPay"; 
				}
				if(data.code=="200"){
					$("#password").val("");
					$("#phonecode").val("");
					$("#telephone").val("");
					//注册成功
					 window.location.href=basePath+"/goToSuccess"; 
				}
			},
			error:function(data){
				$("#doRegisterBtn").css("color", errSubBtnColor).val("服务不可用");
			}
		});
		setTimeout(function(){
			document.getElementById("doRegisterBtn").disabled = false;
		},3000);
	} 
}




/**
 * 设置密码的可见性
 */
function passVisibility(){
	//密码可见性
	$(".set_icon2").on("click",function(){
		if(x_toggle==1){
			$("#password").attr("type","text");
			x_toggle=2;
		}else{
			$("#password").attr("type","password");
			x_toggle=1;
		}
		});
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

function telCheck(){
	telIsNotNull();
	telIsLegal();
	return telNotNull&&telLegal;
}


var rel=false;
function tellCheck(){
	var tel= $.trim($("#telephone").val());
	if (telstandard.test(tel)) {
		
		$.ajax({
			type:"POST",
			url:basePath+"/ProtalUserManage/check",
			data:{
				telephone : tel
			},
			success:function(data){
				eval("data="+data);
				
				if(data){
					$(".set_icon3").removeAttr("disabled");
					$("#doRegisterBtn").css("color", theme).val("注册");
					rel=true;
				}else{
					$(".set_icon3").attr("disabled","disabled");
					$("#doRegisterBtn").css("color", errSubBtnColor).val("该手机号已被注册,请直接登录");
					$(".set_icon3").text("获取手机验证码");
					clearTimeout(mytimer);
					rel=false;
				}
			}
		}); 
	}
	return rel;
}

var telNotNull=false;
function telIsNotNull(){
	var telval = $.trim($("#telephone").val());
	if (telval != "") {
		telNotNull=true;
	}else{
		$("#doRegisterBtn").css("color", errSubBtnColor).val("请输入您的手机号");
		telNotNull= false;
	}
	
}

var telLegal=false;
function telIsLegal(){
	var telval = $.trim($("#telephone").val());
	if (telstandard.test(telval)) {
		telLegal= true;
	}else{
		$("#doRegisterBtn").css("color", errSubBtnColor).val("手机号格式不对");
		telLegal= false;
	}
}


/**
 * 验证密码格式是否正确
 * @returns {Boolean}
 */
function passCheck(){
	var passval=$("#password").val();
	if(passval==""){
		if(rel){
			$("#doRegisterBtn").css("color", errSubBtnColor).val("请输入密码");
		}
		return false;
	}else{
		if(passstandard.test(passval)){
			if(rel){
				$("#doRegisterBtn").css("color", theme).val("注册");
			}
			return true;
		}else{
			if(rel){
				
				$("#doRegisterBtn").css("color", errSubBtnColor).val("密码格式错误");
			}
			return false;
		}
	}
}

/**
 * 验证手机验证码和格式是否正确
 * @returns {Boolean}
 */
function telcodeCheck(){
	var phonecode=$("#phonecode").val();
	if(phonecode==""){
		$("#doRegisterBtn").css("color", errSubBtnColor).val("请输入验证码");
		return false;
	}
	if(!telcodestandard.test(phonecode)){
		$("#doRegisterBtn").css("color", errSubBtnColor).val("验证码格式错误");
		return false;
	}
	if(rel){
		$("#doRegisterBtn").css("color", theme).val("注册");
	}
	return true;
}

function checkIsResgister(){
	var tel=$("#phonecode").val();
	$({
		type:"POST",
		url:basePath+"/ProtalUserManage/doRegister",
		data:{
			telephone:tel
		},
		success:function(data){
			eval("data="+data);
			
		}
		
	});
	
	
}

