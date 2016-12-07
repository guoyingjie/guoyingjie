$(function(){
	$("#telephone").val("");
	$("#password").val("");
	init();
});
function init(){
	$("#tel_keyFlag").on("click",function(){ //清除按钮
		$("#telephone").val("");
	});
		
	
	$('#tel_submitBtn').on('click', function(){
		$("#telform").submit();
	});
	
	$(document).keydown(function(event){ 
		if(event.keyCode==13){
			$("#telform").submit();
		}
	});
	$("#Agree").on("click",function(){
		var agree=$(this).attr("Agree");
		if(agree=="no"){
			$(this).attr("Agree","yes");
			
			$(this).addClass("checked");
		}if(agree=="yes"){
			$(this).attr("Agree","no");
			$(this).removeClass("checked");
		}
	});
	
	var s=0;
	$('.list1').click(function(){
		s++;
		if(s==1){
			$(".p").remove();
			s=-1;
		}else{
			var pic='<img class="p" src="'+imgPath+'/dh.png" />';
			$('.list1').append(pic);
		}
	});
	
}

jQuery.validator.addMethod("phone", function(value, element) {         

	return this.optional(element) || /^(((13[0-9]{1})|(14[4-7]{1})|(15[0-9]{1})|(170)|(17[1-8]{1})|(18[0-9]{1}))+\d{8})$/.test(value);         
}, "请输入正确手机账号！"); 
$("#telform").validate({
	
	// 错误提示样式,在下方提示
	
	errorPlacement : function(error, element) {
		
		$("#mi").html("");
		var el = element.next();
		if (element.context.id == "password") {
			el = element.next().next();
		}
		error.appendTo(el.addClass("error"));
	},

	submitHandler : function(form) {
		logmask(true);
		var proLoginUrl;
		var successUrl;
		var payurl;
		if(fromFw!="null"&&fromFw!=""&&fromFw!=null&&fromFw!=undefined){
			proLoginUrl="/wifidog/ProuserLogin";
			successUrl="/wifidog/goToWherePage";
			payurl = '/wifidog/toPay';
		}else{
			proLoginUrl="/ProuserLogin";
			successUrl="/goToWherePage";
			payurl='/toPay';
		}
		$.ajax({
			type:"POST",
			url: basePath + proLoginUrl,
			data:{
				name :$("#telephone").val(),
				pwd : $("#password").val(),
				id 	: $("#id").val(),
				gw_id: gw_id,
				mac : $("#mac").val(),
				ip 	: $("#ip").val(),
				url : $("#url").val(),
				chargeType:0
			},
			success:function(data){
				var data=eval('(' + data + ')');
				if(data.code == 201){
					$("#mi").html("");
					$("#mi").html(data.msg);
					$('.log-mask').css('display','none');
					//------这里做频繁登陆的限制-------------------------------------------------------//
				}else if(data.code == 203){
					window.location.href= basePath+"/jinggao";
					//-----------------------------------------------------------------//
					//--------检测账号是否已有人登录---------------------------------------------------------//
				}else if(data.code == 300){
					var MAC=data.data[0].MAC.replace(/:/g,"");
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
					//alert("该账号已有设备登录,确定继续登录吗?");
				}else{
					$('.log-mask').css('display','block');
					Jump(data,successUrl,payurl);
				}
				
			},
			error:function(e){
				$("#sj").html("");
				$("#sj").html("服务不可用");
				$('.log-mask').css('display','none');
			}
		});
		
	},
	// 校验规则
	rules : {
		telephone : {
			required : true,
			rangelength:[11,11],
			phone:true,
			remote : {
				url : "/deck/ProtalUserManage/checkTel", //后台处理程序
				type : "post", //数据发送方式
				data : { //要传递的数据
					telephone : function() {
						return $.trim($("#telephone").val());
					}
				}
			}
		},
		password: {
			required : true,
			rangelength:[4,16]
		}
	},
	// 提示文本
	messages : {
		telephone : {
			required : "请输入手机号",
			rangelength:"请输入11位正确的手机号！",
			phone:      "请输入11位正确的手机号！",
			remote : "该手机号没有注册"
		},
		password : {
			required : "请输入密码",
			rangelength:"用户密码为4~16位长度"
		}
	}
});
function Jump(data,successUrl,payurl){
	if(data.code == 202){ //需要充值  doOrder.jsp

		   window.location.href=basePath +payurl; 

		$("#sj").html("");
		$("#sj").html("账户余额不足！");
	}else if(data.code==200||data.code==300){
		try{
			var oHead = document.getElementsByTagName('HEAD').item(0); 
			var oScript= document.createElement("script"); 
			oScript.type = "text/javascript"; 
			oScript.src=""+data.data+""; 
			oHead.appendChild( oScript); 
		}catch(e){
			window.document.write("<script type=\"text/javascript\" src=\""+data.data+"\"></script>");
		}
			window.location.href=basePath + successUrl;
	}else{
		window.location.href=basePath + "/loginFail";
	}
	
}
/**
 * 注册
 */
 function toJump() {
		 window.location.href=basePath +"/ProtalUserManage/toRegister?id="+id +
	  		"&mac="+mac+"&ip="+ip+"&url="+url; 
	} 
	var logmask=function(judeng){
		if(judeng){
			$('.log-mask').css('display','block');
			$('.warn').css('display','none');
			$('.loader').css('display','block');
		}
	};

	
	
	/* 记住密码 */
	$('.forget>i').click(function(){
		if($('.forget>i').attr('class')=='on'){
			$('.forget>i').removeClass('on');
		}else{
			$('.forget>i').addClass('on');
		}
	});
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