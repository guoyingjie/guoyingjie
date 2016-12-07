
var tel = /^(((13[0-9]{1})|(14[4-7]{1})|(15[0-9]{1})|(170)|(17[1-8]{1})|(18[0-9]{1}))+\d{8})$/;
/*
 * 全站公共脚本,基于jquery-1.9.1脚本库
*/
$(function(){
	 
	    ishaveUser();//格式化用户名
	    hideElement();//隐藏其它的元素
	    clickLogin();//一键登录触发的条件
	    resterLogin();//注册后进行登录操作
	    regesterOkLogin();//已经注册了直接去登录
	    removeSession()//清除session
	    resetPassword();
        forgetPassword() //忘记密码触发事件
	    sendpasswordcode();
        getBannerUrl();//初始化获得bannerurl;
//        changeText();
	    var num = $("#trynum").val();
	    if(num<=0){
	    	$('.notice').css('display','none');
	    	$('.dividing-line').css('display','none');
	    	
	    }else{
	    	if(num==30){
	    		$('.notice>i').text('新用户可免费试用30分钟');
	    	}else{
	    		$('.notice>i').text('新用户可免费试用'+num+'小时');
	    	}
	    	$('.notice').css('display','block');
	    	$('.dividing-line').css('display','block');
	    }
	    
	    $(".terms").click(function() {
			window.location.href = basePath + "/ProtalUserManage/goToTerms";
		});
	    
		if ( $(".top-nav").length )
		{
			$(".top-nav").addClass("swiper-container");
			$(".top-nav ul:first").addClass("swiper-wrapper").find("li").addClass("swiper-slide");
			var auto_width = $(".top-nav").width();
			var auto_height = 360;
			var auto_num = $(".top-nav ul:first img").length;
			$(".top-nav").append("<div class='num'></div>")
			function topNav()
			{
				//获取屏幕的高度
				if ( $(".top-nav").parent().width() >=640 )
				{
					auto_width = 640;
					auto_height = 360;
				}
				else
				{
					auto_width = $(".top-nav").parent().width();
					auto_height = 360 * auto_width / 640;
				}
				$(".top-nav").width(auto_width).height(auto_height);
				$(".top-nav ul:first li").width(auto_width);
				$(".top-nav ul:first").width(auto_width*auto_num).height(auto_height);
			}
			 topNav();
			$(window).resize(function(){ topNav()});
			$('.top-nav').swiper({
				pagination: '.num',
				mode:'horizontal',
				loop: true,
				autoplay:5000
			})
		}
	
	//切换账号	
	
	$('.tel-input').css("background-image","none");
	$('.newtel-input').css("background-image","none");

	//判断是否注册
	/*var clearTime;
	$("#changenums").keydown(function(){
		clearTimeout(clearTime);
		$('#whether-login').attr('disabled',true);
		clearTime = setTimeout(function(){
			var tels = $("#changenums").val();
			var siteId = $('#siteId').val();
			var pattern = /^1[34578]\d{9}$/;
			if(pattern.test(tels.replace(/[^0-9]/ig,""))){ 
				$.ajax({
					type:"post",
					url:basePath+"/w/checkUserRegister",
					data:{
						userName:tels,
						siteId:siteId
					},
					success:function(data){
						if(data=="diff"){//直接显示登录,密码输入框
							$('.userMsg').animate({'opacity':'0'},function(){
								$('.userMsg').css("display","none").eq(2).css('display','block').animate({'opacity':'1'});
							});
							$('.welcome-word').html('输入手机号即可轻松上网');
							$('.welcome-word').css("fontWeight","normal");
							$('.regist-user').val(tels);
							$('#whether-login').attr('disabled',false);
						}else if(data=="son"){
							errorMsg('账号不存在');
							$('#whether-login').attr('disabled',false);
							return;
						}else if(data=="nomoney"){
							errorMsg('账号暂无时间或者流量');
							$('#whether-login').attr('disabled',false);
							return;
						}else{
							$('.userMsg').animate({'opacity':'0'},function(){
								$('.userMsg').css("display","none").eq(5).css('display','block').animate({'opacity':'1'});
							});
							$('.welcome-word').html('输入手机号即可轻松上网');
							$('.welcome-word').css("fontWeight","normal");
							$('.noregist-user').val(tels);
							$('#whether-login').attr('disabled',false);
						}
					}
				});
			}else{
				if(tels.length==11||tels.length==12){
					$('#whether-login').attr('disabled',false);
					clearTimeout(clearTime);
					errorMsg('格式不正确');
				}
			}
		},1500);
	});*/
	
	//判断是否注册
	var clearTime;
	$("#regesterOkUsername").keydown(function(){
		clearTimeout(clearTime);
		//$('.has-logins').attr('disabled',true);
		
		clearTime = setTimeout(function(){
			var tels = $("#regesterOkUsername").val();
			var siteId = $('#siteId').val();
			var pattern = /^1[34578]\d{9}$/;
			if(pattern.test(tels.replace(/[^0-9]/ig,""))){ 
				$.ajax({
					type:"post",
					url:basePath+"/w/checkUserRegister",
					data:{
						userName:tels,
						siteId:siteId
					},
					success:function(data){
						if(data=="diff"){//直接显示登录,密码输入框
						}else if(data=="son"){
							errorMsg('账号不存在');
							return;
						}else if(data=="noactive"){
							errorMsg('账号暂未激活');
							return;
						}else if(data=="nomoney"){
							errorMsg('账号暂无时间或者流量');
							return;
						}else{
							$('.userMsg').animate({'opacity':'0'},function(){
								$('.userMsg').css("display","none").eq(5).css('display','block').animate({'opacity':'1'});
							});
							$('.welcome-word').html('输入手机号即可轻松上网');
							$('.welcome-word').css("fontWeight","normal");
							$('.noregist-user').val(tels);
						}
					}
				});
			}else{
				if(tels.length==11||tels.length==12){
					clearTimeout(clearTime);
					errorMsg('格式不正确');
				}
			}
		},1000);
	});
	
	$('#whether-login').unbind('click');
	$('#whether-login').click(function(){
		$('#whether-login').attr('disabled',true);
		
		setTimeout(function(){
			$('#whether-login').attr('disabled',false).css('background','#47a4dc');
		}, 3000);
		var pattern = /^1[34578]\d{9}$/;
		var tels = $("#changenums").val();
		var siteId = $('#siteId').val();
		if(tels==""){
			errorMsg('请输入手机号');
			return;
		} 
		if(!(pattern.test(tels.replace(/[^0-9]/ig,"")))){
			errorMsg('手机号格式不正确');
			return;
		} 
		$.ajax({
			type:"post",
			url:basePath+"/w/checkUserRegister",
			data:{
				userName:tels,
				siteId:siteId
			},
			success:function(data){
				if(data=="diff"){//直接显示登录,密码输入框
					$('.userMsg').animate({'opacity':'0'},function(){
						$('.userMsg').css("display","none").eq(2).css('display','block').animate({'opacity':'1'});
					});
					$('.welcome-word').html('输入手机号即可轻松上网');
					$('.welcome-word').css("fontWeight","normal");
					$('.regist-user').val(tels);
					 $('#whether-login').attr('disabled',false);
				}else if(data=="son"){
					errorMsg('账号不存在');
					//$('#whether-login').attr('disabled',false);
					return;
				}else if(data=="noactive"){
					errorMsg('账号暂未激活');
					return;
				}else if(data=="nomoney"){
					errorMsg('账号暂无时间或者流量');
					//$('#whether-login').attr('disabled',false);
					return;
				}else{
					$('.userMsg').animate({'opacity':'0'},function(){
						$('.userMsg').css("display","none").eq(5).css('display','block').animate({'opacity':'1'});
					});
					$('.welcome-word').html('输入手机号即可轻松上网');
					$('.welcome-word').css("fontWeight","normal");
					$('.noregist-user').val(tels);
					 $('#whether-login').attr('disabled',false);
				}
			}
		});
	})
	 
	
	
	
	//忘记密码下一步的操作
	$('.next_to').click(function(){
		findPsnext();
	})
	
	
	$('.return-to').click(function(){
		$('.userMsg').animate({'opacity':'0'},function(){
			$('.userMsg').css("display","none").eq(1).css('display','block').animate({'opacity':'1'});
		});
		$.ajax({
			type:'post',
			url :basePath+"/w/removeSession",
			success:function(data){},
			error:function(){}
		});
		$("#changenums").val("");
		clearTimeout(timer);
		$(".get-code").removeAttr("disabled");    
        $('.get-code').text("获取验证码");
        $('.get-code').css('background','#47A4DC');
        $('.get-code').css('color','#fff');
        $('.get-code').css('border','1px solid #47A4DC');
		
		$('.welcome-word').html('输入手机号即可轻松上网');
		$('.welcome-word').css('font-weight','bold');
		$('.mian-cont').attr('data-this',1);
			changeText();
			$("input[type='tel']").val("");
			$("input[type='password']").val("");
			
	})
	//未注册
	$('.get-linepass').unbind('click');
	$('.get-linepass').click(function(){
		
        $('.get-linepass').attr('disabled',true);
		setTimeout(function(){
			$('.get-linepass').attr('disabled',false).css('background','#47a4dc');
		}, 3000);
		var username = $("#nouserto").val();
		var siteId = $("#siteId").val();
		if(username==""){
			errorMsg('请输入手机号');
			return;
		} 
		if(!tel.test(username.replace(/[^0-9]/ig,""))){
			errorMsg('手机号不正确');
			return;
		} 
		$.ajax({
			type:"post",
			url:basePath+"/w/checkUserRegister",
			data:{
				userName:username,
				siteId:siteId
			},
			success:function(data){
				if(data=="diff"){//直接显示登录,密码输入框
					errorMsg('账号已注册,跳转中')
					setTimeout(function(){
						$('.userMsg').animate({'opacity':'0'},function(){
							$('.userMsg').css("display","none").eq(2).css('display','block').animate({'opacity':'1'});
						});
						$('.welcome-word').html('输入手机号即可轻松上网');
						$('.welcome-word').css("fontWeight","normal");
						$('.regist-user').val(username);
					}, 2000);
					return;
				}else if(data=="son"){
					errorMsg('账号不存在');
					return;
				}else if(data=="noactive"){
					errorMsg('账号暂未激活');
					return;
				}else if(data=="nomoney"){
					errorMsg('账号暂无时间或者流量');
					return;
				}else{
					sendMsgCode($('#reggetcode'),60);
					$('.get-linepass').attr('disabled',true).text('密码发送中···');
					setTimeout(function(){
						$('.userMsg').animate({'opacity':'0'},function(){
							$('.userMsg').css("display","none").eq(6).css('display','block').animate({'opacity':'1'});
						});
						$('.get-linepass').attr('disabled',false).css('background','#47a4dc').text('获取上网密码');
					},2000);
//					$('.userMsg').css("display","none").eq(6).css("display","block")
					$('.welcome-word').html('输入手机号即可轻松上网');
					$('.welcome-word').css("fontWeight","normal");
					$('.mian-cont').attr('data-this',0);
						changeText();
						 sendCodeToUser(username,siteId);
				}
			}
		});
	})
	
	
		//注册页面的时候重新获取验证码
	
		$('#reggetcode').unbind('click')
		$('#reggetcode').click(function(){
			 
			var username = $('#resterUser').val();
			var siteId = $('#siteId').val();
			$('#reggetcode').attr('disabled',true);
			setTimeout(function(){
				$('#reggetcode').attr('disabled',false).css('background','#47a4dc');
			}, 3000);
			if(username==""){
				errorMsg('请输入手机号');
				return;
			} 
			if(!tel.test(username.replace(/[^0-9]/ig,""))){
				errorMsg('手机号格式不正确');
				return;
			} 
			$.ajax({
				type:"post",
				url:basePath+"/w/checkUserRegister",
				data:{
					userName:username,
					siteId:siteId
				},
				success:function(data){
					if(data=="diff"){//直接显示登录,密码输入框
						
						errorMsg('账号已注册,跳转中')
						setTimeout(function(){
							$('.userMsg').animate({'opacity':'0'},function(){
								$('.userMsg').css("display","none").eq(2).css('display','block').animate({'opacity':'1'});
							});
							$('.welcome-word').html('输入手机号即可轻松上网');
							$('.welcome-word').css("fontWeight","normal");
							$('.regist-user').val(username);
						}, 2000);
						return;
					}else if(data=="son"){
						errorMsg('账号不存在');
						return;
					}else if(data=="noactive"){
						errorMsg('账号暂未激活');
						return;
					}else if(data=="nomoney"){
						errorMsg('账号暂无时间或者流量');
						return;
					}else{
						 sendCodeToUser(username,siteId);
						 sendMsgCode($('#reggetcode'),60);
						 return;
					}
				}
			});
		});
	
	
	
	//切换密码可见
	$('.pass-view').click(function(){
		var ptext = $('.pass-input > input').attr('type');
		if(ptext == 'password'){
			$('.pass-input > input').attr('type','text');
		}else{
			$('.pass-input > input').attr('type','password');
		}
	})
	});


	//忘记密码触发事件
	function forgetPassword(){
		
		//找回密码
		
		$('#findpassword').click(function(){
				var username = $("#regesterOkUsername").val();
				var siteId = $('#siteId').val();
				$('#findpassword').attr('disabled',true);
				setTimeout(function(){
					$('#findpassword').attr('disabled',false);
				}, 3000);
				
	 		    if(username==""){
	 		    	errorMsg('请输入手机号');
	 		    	return;
	 		    }
	 		    if(!tel.test(username.replace(/[^0-9]/ig,""))){
	 		    	errorMsg('手机号格式不正确');
	 		    	return;
	 		    }
	 		   $.ajax({
					type:"post",
					url:basePath+"/w/checkUserRegister",
					data:{
						userName:username,
						siteId:siteId
					},
					success:function(data){
						if(data=="diff"){//已经注册
							//sendMsgCode($('#findpasswordcode'),60);
							//findPsToUser(username);//发送验证码
							$('.userMsg').animate({'opacity':'0'},function(){
								$('.userMsg').css("display","none").eq(3).css('display','block').animate({'opacity':'1'});
							});
							 
//							$('.userMsg').css("display","none").eq(3).css("display","block")
							$('.welcome-word').html('请输入您手机收到的验证码');
							$('.welcome-word').css("fontWeight","normal");
							$('.mian-cont').attr('data-this',0);
								changeText();
					/*	}else if(data=="son"){
							errorMsg('账号不存在');
							return;		
						}else if(data=="nomoney"){
							errorMsg('账号暂无时间或者流量');
							return;	*/
						}else{
							errorMsg('账号未注册,跳转中');
							$('#nouserto').val(username);
							setTimeout(function(){
								$('.userMsg').animate({'opacity':'0'},function(){
									$('.userMsg').css("display","none").eq(5).css('display','block').animate({'opacity':'1'});
								});
								$('.get-linepass').attr('disabled',false).text('获取上网密码');
							},2000);
							return;
						}
					}
				});	
		})
	}


	/**
	 * 忘记密码发送验证码 
	 */
    function sendpasswordcode(){
    	$("#findpasswordcode").click(function(){
    		var username = $("#regesterOkUsername").val();
    		var siteId = $('#siteId').val();
    		
    		//$('#findpasswordcode').attr('disabled',true);
			/*setTimeout(function(){
				$('#findpasswordcode').attr('disabled',false);
			}, 3000);*/
    		
 		    if(username==""){
 		    	errorMsg('请输入手机号');
 		    	return;
 		    }
 		    if(!tel.test(username.replace(/[^0-9]/ig,""))){
 		    	errorMsg('手机号格式不正确');
 		    	return;
 		    }
 		   $.ajax({
				type:"post",
				url:basePath+"/w/checkUserRegister",
				data:{
					userName:username,
					siteId:siteId
				},
				success:function(data){
					if(data=="diff"){//已经注册
						sendMsgCode($("#findpasswordcode"),60);
						findPsToUser(username);//发送验证码
						return;
					/*}else if(data=="son"){
						errorMsg('账号不存在');
						return;
					}else if(data=="nomoney"){
						errorMsg('账号暂无时间或者流量');
						return;*/
					}else{
						errorMsg('账号未注册,跳转到注册页面')
						setTimeout(function(){
							$('.userMsg').animate({'opacity':'0'},function(){
								$('.userMsg').css("display","none").eq(2).css('display','block').animate({'opacity':'1'});
							});
							$('.welcome-word').html('输入手机号即可轻松上网');
							$('.welcome-word').css("fontWeight","normal");
							$('.regist-user').val(username);
							$('#resterUser').val(username)
						}, 2000);
						return;
					}
				}
			});
    	});
    }
		


 
	
	
	
	
	//标题变换
	var textArr = ['欢迎使用'+sitename+'Wi-Fi','输入手机号即可轻松上网'];
 	var ti = 1;
 	var timers;
	function changeText(){
		if($('.mian-cont').attr('data-this') == 1){
			timers = setInterval(function(){
				if(ti>1){
					ti=0;
				}
				$('.mian-cont h1').animate({opacity:"0",filter:"alpha(opacity=0)"},200,function(){
					$('.mian-cont h1').text(textArr[ti]);
					ti++;
					$('.mian-cont h1').animate({opacity:"1",filter:"alpha(opacity=100)"},200);
				});
		 	},2000);
		}else{
			 clearInterval(timers);
		}
	}
	
	/**
	 * 获得bannerurl,并渲染到轮播中
	 */
 	function getBannerUrl(){
 		
 		var url = $('#banner').val();
 		var strs= new Array(); //定义一数组 
 		/*if(url=="school_pic/banner01.jpg,school_pic/banner02.jpg"){*/
 			/*$(".bannerurl>li").remove();
 			var html="";
 			html+="<li><a ><img src='http://oss.kdfwifi.net/school_pic/zhongqiu.jpg'><h3></h3></a></li>";
 			html+="<li><a><img src='http://oss.kdfwifi.net/school_pic/zhongqiu.jpg'><h3></h3></a></li>";
 			$(".bannerurl").html(html);*/
 		/*}else{*/
 			$(".bannerurl>li").remove();
 			strs=url.split(","); 
 			var html="";
 			for (var i = 0; i < strs.length; i++) {
 				html+="<li><a><img src='http://oss.kdfwifi.net/"+strs[i]+"'><h3></h3></a></li>";
 			}
 			$(".bannerurl").html(html);
 		/*}*/
 	}
 	
 	
 	//判断是否登录过
 	function ishaveUser(){
 		var username = $("#username").val();
 		if(username==null||username==""){
 			$('.userMsg').css("display","none").eq(1).css('display','block')
 			/*$('.userMsg').animate({'opacity':'0'},function(){
 				$('.userMsg').css("display","none").eq(1).css('display','block').animate({'opacity':'1'});
 			});*/
 			$('.mian-cont').attr('data-this',1);
 			changeText();
 		}else{
 			doFomat(username);
 		}
 	}
 	
 	
 	/**
 	 * 格式化用户名为1212****2323格式
 	 */
 	
 	function doFomat(username){
 		var name = username.substring(0,3); 
 		var end = username.substring(7,username.length);
 		var allname = name+"****"+end;
 		$("#newphone").text(allname);
 	}

 	/**
 	 * 当用户登录过的时候,到达页面把登录显示,其余隐藏
 	 */
 	function hideElement(){
 			var have = $("#have").val();
			if(have=="have"){
				$('.userMsg').css("display","none").eq(0).css('display','block');
				/*$('.userMsg').animate({'opacity':'0'},function(){
					$('.userMsg').css("display","none").eq(0).css('display','block').animate({'opacity':'1'});
				});*/
//				$(".userMsg").eq(0).css("display","block");
			}else{
				$('.userMsg').css("display","none").eq(1).css('display','block');
				/*$('.userMsg').animate({'opacity':'0'},function(){
					$('.userMsg').css("display","none").eq(1).css('display','block').animate({'opacity':'1'});
				});*/
//				$(".userMsg").eq(1).css("display","block");
			}
 	}
	/**
	 * 一键登录的时候触发的条件
	 */
 	function clickLogin(){
 		//既然可以点击这个按钮说明了就可以一键登录
 		$(".logon_to").click(function(){
 			$(".logon_to").text("登录中···");
 			 var userName = $.trim($("#username").val());
 			 var password = $.trim($("#tupian").val());
 			 var siteId = $('#siteId').val();
 			 $(".logon_to").attr('disabled',true);
 			 setTimeout(function(){
 				$(".logon_to").attr('disabled',false).css('background','#47a4dc');
 			 }, 3000);
 			$.ajax({
				type:"post",
				url:basePath+"/w/checkUserRegister",
				data:{
					userName:userName,
					siteId:siteId
				},
				success:function(data){
					if(data=="diff"){//直接显示登录,密码输入框
						login(userName,"",password);
					}else if(data=="son"){
						errorMsg('账号不存在');
						$(".logon_to").text("登录");
						return;
					}else if(data=="noactive"){
						errorMsg('账号暂未激活');
						$(".logon_to").text("登录");
						return;
					}else if(data=="nomoney"){
						errorMsg('账号暂无时间或者流量');
						$(".logon_to").text("登录");
						return;
					} 
				}
			});
 		});
 	}
 	
 	
 	/**
 	 * 登录操作,所得情况都到这里来处理
 	 * @param username --用户名,
 	 * @param password--密码--如果当前的session不存在have,传入的是前台输入的密码
 	 * @param newpwd--如果当前的session中have存在则传入获得后密码
 	 */
	function login(username,password,newpwd){
		var	proLoginUrl = "/w/ProuserLogin";
		var	successUrl = "/w/goToWherePage";
		var	payurl = "/w/toPay";
		$.ajax({
			type : "POST",
			url : basePath + proLoginUrl,
			data : {
				name :username,
				pwd : password,
				id : $("#id").val(),
				gw_id : gw_id,
				mac : $("#mac").val(),
				ip : $("#ip").val(),
				url : $("#url").val(),
				chargeType : 0,
				password:newpwd,
				siteId:$('#siteId').val(),
				type:$('#routerType').val()
			},
			success : function(data) {
				var data = eval('(' + data + ')');
				if (data.code == 201) {
					$(".has-logins").text('登录');
					$('#regseterLogin').text("登录");
					$(".logon_to").text("登录");
					errorMsg(data.msg);
					$('.log-mask').css('display', 'none');
					return;
				} else if (data.code == 203) {
					$(".has-logins").text('登录');
					$('#regseterLogin').text("登录");
					$(".logon_to").text("登录");
					$('.log-mask').css('display', ' none');
					window.location.href = basePath + "/w/mobileJinggao";
					return;
				} else if (data.code == 300) {
					$(".has-logins").text('登录');
					$('#regseterLogin').text("登录");
					$(".logon_to").text("登录");
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
						$(".has-logins").text('登录');
						$('#regseterLogin').text("登录");
						$(".logon_to").text("登录");
						if(username.indexOf('a')>-1||username.indexOf('b')>-1){
							window.location.href=basePath+"/w/successCenter";
						}else{
							Jump(data, successUrl, payurl);
						}
					});
					$("#cancel").click(function(){
						$(".log-mask").css('display', 'none');
						$(".log-mask").hide();
						$(".has-logins").text('登录');
						$('#regseterLogin').text("登录");
						$(".logon_to").text("登录");
						return;
					});	
			    } else if(data.code==101){
					var obj = eval('(' + data.data + ')'); 
					 window.location.href="http://"+obj.uamip+":"+obj.uamport+"/logon?username="+obj.username+"&response="+obj.pappassword+"&userurl=";
				}else if(data.code==102){
					var obj = eval('(' + data.data + ')'); 
					 window.location.href=obj.url;
				}else if(data.code==103){
					 window.location.href=data.data;
				}else if(data.code==302){
					$(".has-logins").text('登录');
					$('#regseterLogin').text("登录");
					$(".logon_to").text("登录");
					var MAC=data.data[0].callingstationid.replace(/:/g,"");
					var model=data.data[0].Terminal_device;
					var obj= new repeatAlert();
					if(model!=0){
						obj.open("设备<span>"+model+" 设备地址"+MAC+"</span>正在使用该账号,是否继续登录!<br>(继续登录则设备<span>"+MAC+"</span>会强制下线)");
					}else{
						obj.open("设备<span>"+MAC+"</span>正在使用该账号,是否继续登录!<br>(继续登录则设备<span>"+MAC+"</span>会强制下线)");
					}
					$("#sure").click(function(){
						if(username.indexOf('a')>-1||username.indexOf('b')>-1){
							window.location.href=basePath+"/w/successCenter";
						}else{
							Jump(data, successUrl, payurl);
						}
					});	
					$("#cancel").click(function(){
						$(".log-mask").css('display', 'none');
						$(".log-mask").hide();
					});
				}else {
					if(username.indexOf('a')>-1||username.indexOf('b')>-1){
						window.location.href=basePath+"/w/successCenter";
					}else{
						Jump(data, successUrl, payurl);
					}
					$(".has-logins").text('登录');
					$('#regseterLogin').text("登录");
					$(".logon_to").text("登录");
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
		if (data.code == 202) {  
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
		};
	}
	
	/**
	 * 检测当前的用户是否注册了过
	 */
	function isRegster(){
		var phone = $.trim($(".uPhone").val());
		var siteId = $('#siteId').val();
		$.ajax({
			type:"post",
			url:basePath+"/w/checkUserRegister",
			data:{
				userName:phone,
				siteId:siteId
			},
			success:function(data){
				if(data=="diff"){//直接显示登录,密码输入框
					$('.userMsg').animate({'opacity':'0'},function(){
						$('.userMsg').css("display","none").eq(2).css('display','block').animate({'opacity':'1'});
					});
//					$('.userMsg').css("display","none").eq(2).css("display","block")
					$('.welcome-word').html('输入手机号即可轻松上网');
					$('.welcome-word').css("fontWeight","normal");
					$('.regist-user').val(phone);
				}else if(data=="son"){
					errorMsg('账号不存在');
					return;
				}else if(data=="noactive"){
					errorMsg('账号暂未激活');
					return;
				}else if(data=="nomoney"){
					errorMsg('账号暂无时间或者流量');
					return;
				}else{
					$('.userMsg').animate({'opacity':'0'},function(){
						$('.userMsg').css("display","none").eq(5).css('display','block').animate({'opacity':'1'});
					});
//					$('.userMsg').css("display","none").eq(5).css("display","block")
					$('.welcome-word').html('输入手机号即可轻松上网');
					$('.welcome-word').css("fontWeight","normal");
					$('.noregist-user').val(phone);
				}
			}
		});
	}
	
	/**
	 * 发送验证码,用户没有注册用户是发送的是密码
	 */
	function sendCodeToUser(username,siteId){
		$.ajax({
			type:"post",
			url:basePath+"/TelCodeManage/pwdOrRegester",
			data:{
				tel:username,
				templateCode:"SMS_26495034",
				siteId:(siteId==''?'-2':siteId)
			},
			success:function(data){
				 if(data==0){
				 }else if(data==-1){
					 errorMsg("验证码发送失败");
				 }else{
					 errorMsg("请不要频繁发送验证码");
				 }
			}
		});
	}
	
	
	/**
	 * 忘记密码发送验证码
	 */
	function findPsToUser(username){
		$.ajax({
			type:"post",
			url:basePath+"/TelCodeManage/sendTelCode",
			data:{
				tel:username,
				templateCode:"SMS_12891467",
			},
			success:function(data){
				 if(data==0){
				 }else if(data==-1){
					 errorMsg("验证码发送失败");
				 }else{
					 errorMsg("请不要频繁发送验证码");
				 }
			}
		});
	}
	
	
	
	function errorMsg(msg){//错误信息提示框
		$('.msg').text(msg);
		$('.errorP').fadeIn(1000);
		$('.errorP').fadeOut(2000);
	}
	
	/**
	 * 用户名没有注册,发送验证码的时候注册用户
	 */
	function resterLogin(){
		
		$("#regseterLogin").click(function(){
			var username = $.trim($("#resterUser").val());
			var password = $.trim($("#regesterCode").val());
			$("#regseterLogin").attr('disabled',true);
			setTimeout(function(){
				$('#regseterLogin').attr('disabled',false).css('background','#47a4dc');
			}, 3000);
			if(username==""){
				errorMsg("请输入手机号");
				return;
			}
			if(!tel.test(username.replace(/[^0-9]/ig,""))){
				errorMsg("手机号格式不正确");
				return;
			}
			
			if(password==""){
				errorMsg("请输入收到的密码");
				return;
			}
			 
			$('#regseterLogin').text("登录中···")
			$.ajax({
				type:"post",
				url:basePath+"/w/checkPassword",
			    data:{
			    	username:username,
			    	password:password
			    },
			    success:function(data){
			    	eval("data="+data);
			    	if(data.code==200){
			    	
			    		login(username,password,"pwd");
			    	}else{
			    	   errorMsg(data.msg)
			    	   return ;
			    	}
			    },
			    error:function(){
			    	errorMsg("网路异常,请稍后");
			    }
			});
		});
		
	}
	
	
	
	
	
	/**
	 * 账号已经注册直接去登录
	 */
	function regesterOkLogin(){
		$(".has-logins").click(function(){
			var username =$.trim($("#regesterOkUsername").val()); 
			var password=$.trim($("#regsterPs").val());
			var siteId = $('#siteId').val();
			$(".has-logins").attr('disabled',true);
			setTimeout(function(){
				$(".has-logins").attr('disabled',false).css('background','#47a4dc');
			}, 3000);
			if(username==""){
				errorMsg("请输入手机号");
				return;
			} 
			if(!tel.test(username.replace(/[^0-9]/ig,""))){
				errorMsg("手机号格式不正确");
				return;
			} 
			$.ajax({
				type:"post",
				url:basePath+"/w/checkUserRegister",
				data:{
					userName:username,
					siteId:siteId
				},
				success:function(data){
					if(data=="diff"){//直接显示登录,密码输入框
						if(password==""){
							errorMsg("请输入密码");
							return ;
						} 
						$(".has-logins").text('登录中···');
						login(username,password,"pwd");
					}else if(data=="son"){
						errorMsg('账号不存在');
						return;
					}else if(data=="noactive"){
						errorMsg('账号暂未激活');
						return;
					}else if(data=="nomoney"){
						errorMsg('账号暂无时间或者流量');
						return;
					}else{
						$('.userMsg').animate({'opacity':'0'},function(){
							$('.userMsg').css("display","none").eq(5).css('display','block').animate({'opacity':'1'});
						});
						$('.welcome-word').html('输入手机号即可轻松上网');
						$('.welcome-word').css("fontWeight","normal");
						$('.noregist-user').val(username);
						return;
					}
				}
			});
		});
	}
	
	/**
	 * 忘记密码下一步操作
	 */
	function findPsnext(){
		
		$('.next_to').attr('disabled',true);
		
		setTimeout(function(){
			$('.next_to').attr('disabled',false).css('background','#47a4dc');
		}, 3000);
		
		var code = $("#findpscode").val();
		var username =$.trim($("#regesterOkUsername").val()); 
		if(code==""){
			errorMsg("请输入验证码");
			return;
		}
		
		$.ajax({
			type:"post",
			url:basePath+"/w/checkPassword",
		    data:{
		    	username:username,
		    	password:code
		    },
		    success:function(data){
		    	eval("data="+data);
		    	if(data.code==200){
		    		$('.userMsg').animate({'opacity':'0'},function(){
		    			$('.userMsg').css("display","none").eq(4).css('display','block').animate({'opacity':'1'});
		    		});
//		    		$('.userMsg').css("display","none").eq(4).css("display","block")
		    		$('.welcome-word').html('请设置您的新密码');
		    		$('.welcome-word').css("fontWeight","normal");
		    		$('.mian-cont').attr('data-this',0);
		    			changeText();
		    	}else{
		    	   errorMsg("验证码错误");
		    	   return ;
		    	}
		    },
		    error:function(){
		    	errorMsg("网路异常,请稍后");
		    }
		});
	}
	/**
	 * x修改密码
	 */
	function resetPassword(){
		$(".complete").click(function(){
			
			$('.complete').attr('disabled',true);
			setTimeout(function(){
				$('.complete').attr('disabled',false).css('background','#47a4dc');
			}, 3000);
			
			var code = $.trim($("#resetPs").val());
			var sendcode = $.trim($("#sendpass").val());
			var username =$.trim($("#regesterOkUsername").val()); 
			if(code==""){
				errorMsg("请输入密码");
				return;
			} 
			
			if(code.length<6){
				errorMsg("密码长度少于6位");
				return;
			} 
			
			
			if(sendcode==""){
				errorMsg("请输入密码");
				return;
			} 
			if(sendcode.length<6){
				errorMsg("密码长度少于6位");
				return;
			} 
			if(code!=sendcode){
				errorMsg("两次密码不一致");
				return;
			} 
			$.ajax({
				type:"post",
				url:basePath+"/ProtalUserManage/resetPasswordForUser",
				data:{
					telephone:username,
					password:code
				},
				success:function(data){
					eval("data="+data);
					if(data.code==200){
					$('.regist-user').val(username)
					
					$('.userMsg').animate({'opacity':'0'},function(){
						$('.userMsg').css("display","none").eq(2).css('display','block').animate({'opacity':'1'});
					});
					$('.welcome-word').html('输入手机号即可轻松上网');
					$('.welcome-word').css('font-weight','bold');
					$('.mian-cont').attr('data-this',1);
						changeText();
						$("#resetPs").val("");
						$("#findpscode").val("");
						$("#changenums").val("");
						$("#sendpass").val("");
						$('.complete').attr('disabled',true);
						setTimeout(function(){
							$('.complete').attr('disabled',false).css('background','#47a4dc');
						}, 3000);
					}else{
						errorMsg(data.msg);
						return;
					}
				}
				
			});
		});
	}
	//清空session
	function removeSession(){
		$("#changenum").click(function(){
			$('.userMsg').animate({'opacity':'0'},function(){
				$('.userMsg').css("display","none").eq(1).css('display','block').animate({'opacity':'1'});
			});
			$('.mian-cont').attr('data-this',1);
			changeText();
			$('#tupian').val('');
			$.ajax({
				type:'post',
				url :basePath+"/w/removeSession",
				success:function(data){},
				error:function(){}
			});
		});
	}
	
	var timer//时间控制器
	function sendMsgCode(obj,n){//验证码倒计时
		clearInterval(timer);
		obj.css('background','#fff');
		obj.css('color','#6E6E6E');
		obj.css('border','1px solid #6e6e6e');
		obj.attr('disabled',true);
		obj.text(n+'秒后重新获取');
		timer = setInterval(function(){
			n--;
			if(n==0){
				clearInterval(timer);
				obj.attr('disabled',false);
				obj.css('background','#47a4dc');
				obj.css('color','#fff');
				obj.css('border','1px solid #47a4dc');
				obj.text('重新获取');
			}else{
				obj.text(n+'秒后重新获取');
			}
		},1000);
	}
