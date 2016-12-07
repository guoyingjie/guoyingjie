/**
 * 登录
 */
$(function (){
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
	
	$("#tu").click(function(){
		var mm=document.getElementById("schoolRandCode");//获取密码input框的ID
		var aa=document.getElementById("tu");//获取显示隐藏密码的图片ID
		if(mm.type=="password"){
			mm.type="text";
			aa.src=imgPath+"/mm.png";
		}else if(mm.type=="text"){
			mm.type="password";
			aa.src=imgPath+"/am.png";
		}
	});
	
	
	initClick();
	
});
function initClick(){
	$('#dl').on('click', function(){
		$("#loginForm").submit();
	});
	
	$("#registerBtn").on('click',function(){//跳转注册页面
		  window.location.href=basePath +"/ProtalUserManage/toRegister?id="+id +
		  		"&mac="+mac+"&ip="+ip+"&url="+url; 
		
	/*	$.ajax({
			type:"POST",
			url: basePath + "/ProtalUserManage/toRegister",
			data:{
				id 	: id ,
				mac : mac,
				ip 	: ip ,
				url : url 
			},
			success:function(data){
				
			},
			error:function(e){
				alert("error");
			}
		});*/
		
	});
}
jQuery.validator.addMethod("phone", function(value, element) { 

    return this.optional(element) || /^(((13[0-9]{1})|(14[4-7]{1})|(15[0-9]{1})|(170)|(17[1-8]{1})|(18[0-9]{1}))+\d{8})$/.test(value);         

}, "请输入正确手机账号！"); 
$("#loginForm").validate({
	// 错误提示样式,在下方提示
	errorPlacement : function(error, element) {
		$("#errorMsg").html("");
//		$("#errorMsg").css( "border","1px #e0e0e0 solid;");
		error.css({
			display : "inline",
			color : "#ee7676",
			position : "relative"
		}).appendTo(element.next().addClass("error"));
	},
//	success: function(label) { 
//		alert(1);
//	},
	submitHandler : function(form) {
		$('.log-mask').css('display','block');
		document.getElementById("dl").disabled = true;
		$("#errorMsg").html("");
		var proLoginUrl;
		var successUrl;
		if(fromFw!="null"&&fromFw!=""&&fromFw!=null&&fromFw!=undefined){
			proLoginUrl="/wifidog/ProuserLogin";
			successUrl="/wifidog/goToSuccess";
		}else{
			proLoginUrl="/ProuserLogin";
			successUrl="/goToSuccess";
		}
		$.ajax({
			type:"POST",
			url: basePath + proLoginUrl,
			data:{
				name :$("#schoolNumber").val(),
				pwd : $("#schoolRandCode").val(),
				id 	: $("#id").val(),
				gw_id:gw_id,
				mac : $("#mac").val(),
				ip 	: $("#ip").val(),
				url : $("#url").val(),
				chargeType:0
			},
			success:function(data){
			var data=eval('(' + data + ')');
			if(data.code == 201){
				//alert(data.msg);
				$("#errorMsg").html(data.msg);
				$('.log-mask').css('display','none');
				//------这里做频繁登陆的限制-------------------------------------------------------//
			}else if(data.code == 203){
				$('.log-mask').css('display','none');
				window.location.href= basePath+"/mobileJinggao";
				//-----------------------------------------------------------------//
				//------登录时检测是否已经有人登陆-------------------------------------------------------//
			}else if(data.code ==300){
				var MAC=data.data[0].MAC.replace(/:/g,"");
				var model=data.data[0].Terminal_device;
				var obj = new repeatAlert();
				if(model!=0){
					obj.open("设备<span>"+model+" 设备地址"+MAC+"</span>正在使用该账号,是否继续登录!<br>(继续登录则设备<span>"+MAC+"</span>会强制下线)");
				}else{
					obj.open("设备<span>"+MAC+"</span>正在使用该账号,是否继续登录!<br>(继续登录则设备<span>"+MAC+"</span>会强制下线)");

				}
				$("#sure").click(function(){
					Jump(data,successUrl);
				});
				
				//------登录时检测是否已经有人登陆-------------------------------------------------------//
			}else{
				Jump(data,successUrl);
			}
				
			},
			error:function(e){
				$("#errorMsg").html("服务不可用");
				$('.log-mask').css('display','none');
			}
		});
		setTimeout(function(){
			document.getElementById("dl").disabled = false;
		},3000);
	},
	// 校验规则
	rules : {
		schoolNumber : {
			required : true,
			rangelength:[11,11],
			phone:true
		},
		schoolRandCode : {
			required : true,
			rangelength:[4,16],
		}
	},
	// 提示文本
	messages : {
		schoolNumber : {
			required : "请输入手机号",
			rangelength:"请输入正确的手机号！",
			phone:"请输入正确的手机号！"
		},
		schoolRandCode : {
			required : "请输入密码",
			rangelength:"用户密码为4~16位长度"
		}
	}
});

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

//跳转页面
function Jump(data,successUrl){
	$('.log-mask').css('display','none');
	if(data.code == 202){ //需要充值  doOrder.jsp
		   window.location.href=basePath +"/toPay"; 
	}else if(data.code==200||data.code==300){
		  window.location.href=basePath+successUrl; 
	}
	
}

$("#cancel").click(function(){
	var obj=new repeatAlert();
	obj.close();
	
});
//登录 弹出确认框
	function repeatAlert(){
		//显示遮罩背景和弹出框
		this.open=function(str){//str用来接收提示文本
			$('.wanr-text').html(str);
			$('.log-mask').css('display','block');
			$('.warn').css('display','block');
			$('.loader').css('display','none');
			//callback();
		};
		
		//关闭弹出框
		this.close=function(){
			$('.log-mask').css('display','none');
			//callback();
		};
	}