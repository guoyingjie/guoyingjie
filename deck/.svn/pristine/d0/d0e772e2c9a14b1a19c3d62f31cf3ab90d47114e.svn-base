
var tel = /^(((13[0-9]{1})|(14[4-7]{1})|(15[0-9]{1})|(170)|(17[1-8]{1})|(18[0-9]{1}))+\d{8})$/;// 手机号码

/*
 * 全站公共脚本,基于jquery-1.9.1脚本库
*/
$(function(){
	//跳转到服务条款的页面
	$(".terms").click(function() {
		window.location.href = basePath + "/ProtalUserManage/goToTerms";
	});
	
	var trynu = $('#trynum').val();
	if(trynu==0){
		$('.notice').css('display','none');
	}
	
	
	 hideElement()//显示主页的内容
	 ishaveUser()//格式化用户名为130****5463格式
	
	 clickLogin()//一键登录
	 regesterLogin()//已经注册了,输入用户名密码登陆
	 resetPassword();//重置密码
	 resterLogin()//注册完直接走登陆流程
	 findbackPassword()//找回密码
	 removeSession()//清除session
	 regserterSendCode();
	 getBannerUrl()//获得bannerurl轮播的图片
	 jqLB($('.top-nav'),5000,$('.r_box'));
	
	//切换账号	
	//$('.userMsg').css("display","none").eq(0).css("display","block");
	$('.change-to').click(function(){
		//$('.userMsg').css("display","none").eq(1).css("display","block")
		$('.userMsg').eq(1).animate({'opacity':'0'},function(){
			$('.userMsg').css("display","none").eq(1).css('display','block').animate({'opacity':'1'});
		});
		$('.login-pass').val();
	})
	 
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
							setTimeout(function(){
								$('.userMsg').animate({'opacity':'0'},function(){
									$('.userMsg').css("display","none").eq(2).css('display','block').animate({'opacity':'1'});
								});
								$('.welcome-word').html('输入手机号即可轻松上网');
								$('.welcome-word').css("fontWeight","normal");
							}, 500);
							$('.regist-user').val(tels);
						}else if(data=="son"){
							errorMsg('账号不存在');
							return;
						}else if(data=="nomoney"){
							errorMsg('账号暂无时间或者流量');
							return;
						}else{
							$('.userMsg').eq(5).animate({'opacity':'0'},function(){
								$('.userMsg').css("display","none").eq(5).css('display','block').animate({'opacity':'1'});
				    		});
							$('#whether-login').attr('disabled',false).css('background','#47a4dc');
							$('.noregist-user').val(tels);
						}
						
						
					}
				});
			}else{
				if(tels.length==11||tels.length==12){
					clearTimeout(clearTime);
					errorMsg('手机号格式不正确');
				}
			}
		},1500);
	});*/
	
	
	//判断是否注册
	var clearTime;
	$("#resterName").keydown(function(){
		clearTimeout(clearTime);
		$('#regseterLogin').attr('disabled',true);
		clearTime = setTimeout(function(){
			var tels = $("#resterName").val();
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
							$('#regseterLogin').attr('disabled',false);
						}else if(data=="son"){
							errorMsg('账号不存在');
							$('#regseterLogin').attr('disabled',false);
							return;
						}else if(data=="noactive"){
							errorMsg('账号暂未激活');
							return;
						}else if(data=="nomoney"){
							$('#regseterLogin').attr('disabled',false);
							errorMsg('账号暂无时间或者流量');
							return;
						}else{
							$('.userMsg').eq(5).animate({'opacity':'0'},function(){
								$('.userMsg').css("display","none").eq(5).css('display','block').animate({'opacity':'1'});
				    		});
							$('#whether-login').attr('disabled',false).css('background','#47a4dc');
							$('.noregist-user').val(tels);
						}
					}
				});
			}else{
				if(tels.length==11||tels.length==12){
					$('#regseterLogin').attr('disabled',false);
					clearTimeout(clearTime);
					errorMsg('手机号格式不正确');
				}
			}
		},1000);
	});
	
	
	  $('#whether-login').click(function(){
	        $(this).attr('disabled',true);
			setTimeout(function(){
				$('#whether-login').attr('disabled',false).css('background','#47a4dc');
			}, 3000);
		
		    var pattern = /^1[34578]\d{9}$/;
		    var phone= $("#changenums").val();
			var siteId = $('#siteId').val();
			
			if(phone==""){
				errorMsg('请输入手机号');
				return;
			} 
			
		    if(!(pattern.test(phone.replace(/[^0-9]/ig,"")))){
		      errorMsg('账号格式不正确');
		      return;
		    } 
		    $.ajax({
				type:"post",
				url:basePath+"/w/checkUserRegister",
				data:{
					userName:phone,
					siteId:siteId
				},
				success:function(data){
					if(data=="diff"){//直接显示登录,密码输入框
						setTimeout(function(){
							$('.userMsg').animate({'opacity':'0'},function(){
								$('.userMsg').css("display","none").eq(2).css('display','block').animate({'opacity':'1'});
							});
							$('.welcome-word').html('输入手机号即可轻松上网');
							$('.welcome-word').css("fontWeight","normal");
						}, 500);
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
						$('.userMsg').eq(5).animate({'opacity':'0'},function(){
							$('.userMsg').css("display","none").eq(5).css('display','block').animate({'opacity':'1'});
			    		});
						$('#whether-login').attr('disabled',false).css('background','#47a4dc');
						$('.noregist-user').val(phone);
					}
				}
			});
	}) 
	
	
	
	$('#findpaswod').click(function(){
		//$('.userMsg').css("display","none").eq(3).css("display","block")
		$('#findpaswod').attr('disabled',true);
		setTimeout(function(){
			$('#findpaswod').attr('disabled',false);
		}, 3000);
		var telnum = $("#resterName").val();
		var siteId = $('#siteId').val();
		if(telnum==""){
			errorMsg('请输入手机号');
			$('#findpaswod').attr('disabled',true);
			setTimeout(function(){
				$('#findpaswod').attr('disabled',false);
			}, 3000);
			return;
		}else{
			$('#findpaswod').attr('disabled',false);
		}
		
		if(!tel.test(telnum.replace(/[^0-9]/ig,""))){
			errorMsg('手机号格式不正确');
			$('#findpaswod').attr('disabled',true);
			setTimeout(function(){
				$('#findpaswod').attr('disabled',false);
			}, 3000);
			return;
		}else{
			$('#findpaswod').attr('disabled',false);
		}
		$.ajax({
			type:"post",
			url:basePath+"/w/checkUserRegister",
			data:{
				userName:telnum,
				siteId:siteId
			},
			success:function(data){
				if(data=="diff"){//直接显示登录,密码输入框
					$('.userMsg').eq(3).animate({'opacity':'0'},function(){
						$('.userMsg').css("display","none").eq(3).css('display','block').animate({'opacity':'1'});
					});
					/*sendMsgCode( $('#codeIn'),60);
					findPsToUser(telnum);*/
				/*}else if(data=="son"){
					errorMsg('账号不存在');
					return;
				}else if(data=="nomoney"){
					errorMsg('账号暂无时间或者流量');
					return;*/
				}else{
					errorMsg('手机号未注册,页面跳转中');
					$('.userMsg').eq(5).animate({'opacity':'0'},function(){
						$('.userMsg').css("display","none").eq(5).css('display','block').animate({'opacity':'1'});
					});
					$(".noregist-user").val(telnum);
					 return;
				}
			}
		});
		
	})
	
	
	$('.next_to').click(function(){
       findPsnext();
	})
	$('.return-to').click(function(){
		//$('.userMsg').css("display","none").eq(1).css("display","block");
		$('.userMsg').eq(1).animate({'opacity':'0'},function(){
			$('.userMsg').css("display","none").eq(1).css('display','block').animate({'opacity':'1'});
		});
		$("#changenums").val("");
		clearInterval(timer);
		$('.get-code').attr('disabled',false);
		$('.get-code').css('background','#47a4dc');
		$('.get-code').css('color','#fff');
		$('.get-code').css('border','1px solid #47a4dc');
		$('.get-code').text('重新获取');
	})
	//未注册
	$('.get-linepass').click(function(){
		   
		 $('.get-linepass').attr('disabled',true);
			setTimeout(function(){
				$('.get-linepass').attr('disabled',false).css('background','#47a4dc');
			}, 3000);	
		
		var tels = $('#nouser').val();
		var siteId = $('#siteId').val();
		if(tels==""){
			errorMsg('请输入手机号');
			$('.get-linepass').attr('disabled',true);
			setTimeout(function(){
				$('.get-linepass').attr('disabled',false).css('background','#47a4dc');
			}, 3000);
			return;
		}
		if(!tel.test(tels.replace(/[^0-9]/ig,""))){
			errorMsg('手机号不正确');
			$('.get-linepass').attr('disabled',true);
			setTimeout(function(){
				$('.get-linepass').attr('disabled',false).css('background','#47a4dc');
			}, 3000);
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
					errorMsg('手机号已注册');
					setTimeout(function(){
						$('.userMsg').animate({'opacity':'0'},function(){
							$('.userMsg').css("display","none").eq(2).css('display','block').animate({'opacity':'1'});
						});
					}, 2000);
					$("#resterName").val(tels)
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
					
					sendMsgCode( $('#codeIn1'),60);
					$('.get-linepass').attr('disabled',true).text('密 码 获 取 中···');
					$('.get-linepass').css('border','1px solid #ccc');
					setTimeout(function(){
						$('.userMsg').animate({'opacity':'0'},function(){
							$('.userMsg').css("display","none").eq(6).css('display','block').animate({'opacity':'1'});
						});
						$('.welcome-word').html('输入手机号即可轻松上网');
						$('.welcome-word').css("fontWeight","normal");
					},2000);
					sendCodeToUser(tels,siteId);
				}
			}
		});
	})
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
 
	
	 
	//错误操作提示
	function errorMsg(msg){
	  $('.msg').text(msg);
	  $('.errorP').fadeIn(1000);
	  $('.errorP').fadeOut(2000);
	}
	
	 
	/**
	 * 忘记密码下一步操作
	 */
	function findPsnext(){
		
		$('.next_to').attr('disabled',true);
		setTimeout(function(){
			$('.next_to').attr('disabled',false).css('background','#47a4dc');
		}, 3000);
		var code = $(".coded-in").val();
		var username =$.trim($("#resterName").val()); 
		if(code==""){
			errorMsg("请输入验证码");
			$('.next_to').attr('disabled',true);
			setTimeout(function(){
				$('.next_to').attr('disabled',false).css('background','#47a4dc');
			}, 3000);
			return;
		}else{
			$('.next_to').attr('disabled',false).css('background','#47a4dc');
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
		    		//$('.userMsg').css("display","none").eq(4).css("display","block");
		    		$('.userMsg').eq(4).animate({'opacity':'0'},function(){
						$('.userMsg').css("display","none").eq(4).css('display','block').animate({'opacity':'1'});
		    		});
		    		$(".coded-in").val("");
		    	}else{
		    	   errorMsg("验证码错误");
		    	   $('.next_to').attr('disabled',true);
					setTimeout(function(){
						$('.next_to').attr('disabled',false).css('background','#47a4dc');
					}, 3000);
		    	   return ;
		    	}
		    },
		    error:function(){
		    	$('.next_to').attr('disabled',false).css('background','#47a4dc');
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
			
			var code = $(".newPass").val();
			var code1 = $('.newPass1').val();
			var username =$.trim($("#resterName").val()); 
			if(code==""){
				errorMsg("请输入密码");
				 $('.complete').attr('disabled',true);
					setTimeout(function(){
						$('.complete').attr('disabled',false).css('background','#47a4dc');
					}, 3000);
				return;
			}
			
			if(code.length<6){
				errorMsg("密码长度少于6位");
				 $('.complete').attr('disabled',true);
					setTimeout(function(){
						$('.complete').attr('disabled',false).css('background','#47a4dc');
					}, 3000);
				return;
			}
			
			if(code1==""){
				errorMsg("请输入密码");
				 $('.complete').attr('disabled',true);
					setTimeout(function(){
						$('.complete').attr('disabled',false).css('background','#47a4dc');
					}, 3000);
				return;
			}
			if(code1.length<6){
				errorMsg("密码长度少于6位");
				 $('.complete').attr('disabled',true);
					setTimeout(function(){
						$('.complete').attr('disabled',false).css('background','#47a4dc');
					}, 3000);
				return;
			}
			if(code!=code1&&code.length!=code1.length){
				 $('.complete').attr('disabled',true);
					setTimeout(function(){
						$('.complete').attr('disabled',false).css('background','#47a4dc');
					}, 3000);
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
						errorMsg("密码修改成功");
						//$('.userMsg').css("display","none").eq(2).css("display","block");
						$('.userMsg').eq(2).animate({'opacity':'0'},function(){
							$('.userMsg').css("display","none").eq(2).css('display','block').animate({'opacity':'1'});
			    		});
						$(".newPass").val("");
						$("#changenums").val("");
						
					}else{
						errorMsg(data.msg);
						return;
					}
				}
				
			});
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
	 * 用户名没有注册,发送验证码的时候注册用户
	 */
	function resterLogin(){
		
	  $(".code-login").click(function(){
	  $('.code-login').text('登录中···');
	  $('.code-login').attr('disabled',true);
		setTimeout(function(){
			$('.code-login').attr('disabled',false).css('background','#47a4dc');
		}, 3000);
			
			
			var username = $.trim($("#getcodesUser").val());
			var password = $.trim($(".code-in").val());
			
			if(username==""){
				errorMsg("请输入手机号");
				 $('.code-login').attr('disabled',true);
					setTimeout(function(){
						$('.code-login').attr('disabled',false).css('background','#47a4dc');
					}, 3000);
				return;
			}
			
			if(!tel.test(username.replace(/[^0-9]/ig,""))){
				errorMsg("手机号输入错误");
				 $('.code-login').attr('disabled',true);
					setTimeout(function(){
						$('.code-login').attr('disabled',false).css('background','#47a4dc');
					}, 3000);
				return;
			}
			
			
			if(password==""){
				errorMsg("请输入收到的密码");
				 $('.code-login').attr('disabled',true);
					setTimeout(function(){
						$('.code-login').attr('disabled',false).css('background','#47a4dc');
					}, 3000);
				return;
			}
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
	
	/**
	 * 获得bannerurl,并渲染到轮播中
	 */
 	function getBannerUrl(){
 		
 		
 		var url = $('#banner').val();
 		if(url!=""){
 			var strs= new Array(); //定义一数组 
 				$(".bannerurl>li").remove();
 				strs=url.split(","); 
 				var html="";
 				
 				for (var i = 0; i < strs.length; i++) {
 					html+="<li><a><img src='http://oss.kdfwifi.net/"+strs[i]+"'><h3></h3></a></li>";
 				}
 				html +="<li><a><img src='http://oss.kdfwifi.net/"+strs[0]+"'><h3></h3></a></li>";
 				$(".bannerurl").html(html);
 				$('.top-nav ul').css('width',$('.top-nav li').width()*(strs.length+1)+'px');
 			}
 		/*}*/
 	}
 	
 	//判断是否登录过
 	function ishaveUser(){
 		var username = $("#username").val();
 		if(username==null||username==""){
 			$(".userMsg").eq(1).css("display","block");
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
	 	    	$(".userMsg").css("display","none").eq(0).css("display","block");
 	         }else{
 	        	$(".userMsg").css("display","none").eq(1).css("display","block");
 	         }
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
					//$('.userMsg').css("display","none").eq(2).css("display","block")
					$('.userMsg').eq(2).animate({'opacity':'0'},function(){
						$('.userMsg').css("display","none").eq(2).css('display','block').animate({'opacity':'1'});
		    		});
					
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
					$('.userMsg').eq(5).animate({'opacity':'0'},function(){
						$('.userMsg').css("display","none").eq(5).css('display','block').animate({'opacity':'1'});
		    		});
					//$('.userMsg').css("display","none").eq(5).css("display","block")
					$('.noregist-user').val(phone);
				}
			}
		});
	}
	
	
	/**
	 * 一键登录的时候触发的条件
	 * 
	 * 密码,账号已经存入到session中,输入框的密码传入空字符串就可以
	 */
 	function clickLogin(){
 		//既然可以点击这个按钮说明了就可以一键登录
 		$(".logon_to").click(function(){
 			$('.logon_to').text("登录中···");
 			 var userName = $.trim($("#username").val());
 			 var password = $.trim($("#tupian").val());
 			 var siteId = $('#siteId').val();
 			$('.logon_to').attr('disabled',true);
 		    setTimeout(function(){
 				$('.logon_to').attr('disabled',false).css('background','#47a4dc');
 			}, 3000); 
 		    $.ajax({
				type:"post",
				url:basePath+"/w/checkUserRegister",
				data:{
					userName:userName,
					siteId:siteId
				},
				success:function(data){
					if(data=="diff"){ 
						login(userName,"",password);
					}else if(data=="son"){
						errorMsg('账号不存在');
						$('.logon_to').text("登录");
						return;
					}else if(data=="noactive"){
						errorMsg('账号暂未激活');
						$('.logon_to').text("登录");
						return;
					}else if(data=="nomoney"){
						errorMsg('账号暂无时间或者流量');
						$('.logon_to').text("登录");
						return;
					} 
				}
			});
 			
 		});
 	}
	
	/**
	 * 已经注册了的用户需要输入用户名密码登陆,
	 * 此时密码标识传入pwd.
	 * 
	 * 此时是有输入框,密码框,给出参数pwd传入参数pwd就可以
	 */
 	function regesterLogin(){
 		$("#regseterLogin").click(function(){
 			var userName = $("#resterName").val();
 			var password = $(".login-pass").val();
 			var siteId = $('#siteId').val();
 			$('#regseterLogin').attr('disabled',true);
 			setTimeout(function(){
 				$('#regseterLogin').attr('disabled',false).css('background','#47a4dc');
 			}, 3000);
 			if(userName==""){
 	 			errorMsg("请输入手机号");
 	 			$('#regseterLogin').text("登录");
 				return;
 			} 
 			
 			if(!tel.test(userName.replace(/[^0-9]/ig,""))){
 	 			errorMsg("手机号格式不正确");
 	 			$('#regseterLogin').text("登录");
 				return;
 			} 
 			
 			$.ajax({
				type:"post",
				url:basePath+"/w/checkUserRegister",
				data:{
					userName:userName,
					siteId:siteId
				},
				success:function(data){
					if(data=="diff"){//直接显示登录,密码输入框
						$('#regseterLogin').text("登录中···");
						if(password==""){
			 	 			errorMsg("请输入密码");
			 	 			$('#regseterLogin').text("登录");
			 				return;
			 			} 
			 			login(userName,password,"pwd");
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
						$('.userMsg').eq(5).animate({'opacity':'0'},function(){
							$('.userMsg').css("display","none").eq(5).css('display','block').animate({'opacity':'1'});
			    		});
						$('#regseterLogin').attr('disabled',false).css('background','#47a4dc');
						$('.noregist-user').val(userName);
						 return ;
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
					errorMsg(data.msg);
					$('.log-mask').css('display', 'none');
					$('#regseterLogin').text("登录");
					$('.code-login').text('登录');
					$('.logon_to').text("登录");
				}else if(data.code==200){
					if(username.indexOf('a')>-1||username.indexOf('b')>-1){
						window.location.href=basePath+"/w/successCenter";
					}else{
						Jump(data,successUrl,payurl);
					}
				}else if(data.code == 202){
			        window.location.href=basePath +payurl;
				} else if (data.code == 203) {
					$('.log-mask').css('display', ' none');
					window.location.href = basePath + "/w/mobileJinggao";
				} else if (data.code == 300) {
				    $('#regseterLogin').text("登录");
				    $('.code-login').text('登录');
				    $('.logon_to').text("登录");
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
						if(username.indexOf('a')>-1||username.indexOf('b')>-1){
							window.location.href=basePath+"/w/successCenter";
						}else{
							Jump(data,successUrl,payurl);
						}
						$(".log-mask").css('display','none');
					});
					$("#cancel").click(function(){
						 $('#regseterLogin').text("登录");
						 $('.code-login').text('登录');
						 $('.logon_to').text("登录");
						 $(".log-mask").css('display','none');
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
					$('#regseterLogin').text("登录");
					$('.code-login').text('登录');
					$('.logon_to').text("登录");
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
							Jump(data,successUrl,payurl);
						}
						$(".log-mask").css('display','none');
					});	
					$("#cancel").click(function(){
						$(".log-mask").css('display','none');
					});	
				}
			},
			error : function(e) {
				$('.log-mask').css('display', 'none');
			}
		});
	}
	// 跳转页面
	function Jump(data, successUrl, payurl) {
		$('.log-mask').css('display','none');
		 if(data.code==200||data.code==300||data.code==302){
			  window.location.href=basePath+successUrl; 
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
	
	//返回登录将session清空
	function removeSession(){
		$(".change-to").click(function(){
			$('.userMsg').css("display","none").eq(1).css("display","block")
			$('#tupian').val('');
			$.ajax({
				type:'post',
				url :basePath+"/w/removeSession",
				success:function(data){},
				error:function(){}
			});
		});
	}
	 function jqLB(b_box,sceed,r_box){
			var zTimer;//@全局变量  总控制时间变量
			var _n = 0;//@全局变量  当前是第几张图片展示
			window.onload=function(){
				zTimer = setInterval(function(){
					_n++;
					moveTo(_n);
				},sceed);
		
				b_box.mousemove(function(){
					clearInterval(zTimer);
				});
				b_box.mouseout(function(){
					zTimer = setInterval(function(){
						_n++;
						moveTo(_n);
					},sceed);
		
				});
		
				r_box.children().click(function(){
					_n = $(this).index();
					// console.log(n);
					moveTo(_n);
				});
			}
			
			function moveTo(n){
				var _w = b_box.children().children().width();
				var _s = b_box.children().children().length-1;
				//console.log(_s);
				var _left = _w*n;
				b_box.children().eq(0).animate({'left':-_left+'px'},300,'swing',function(){
					r_box.children().removeClass('on').eq(_n).addClass('on');
					if(_left==_s*_w){
						b_box.children().eq(0).css('left','0px');
						_n=0;
						r_box.children().removeClass('on').eq(_n).addClass('on');
					}
				});
			}
		}
	 /**
	  * 发送验证码通用方法
	  * @param obj
	  * @param n
	  */
	 var timer;
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
	 
	 /**
	  * 找回密码触发事件
	  */
	 function findbackPassword(){
		 $('#codeIn').click(function(){
			 var telname = $('.regist-user').val();
			 sendMsgCode( $('#codeIn'),60);
			 findPsToUser(telname);
		 });
	 }
	 
	 /**
		 * 检测不到输入的用户时直接去注册
		 */
		function regserterSendCode(){
			
			$("#codeIn1").click(function(){
				var userTel = $("#getcodesUser").val();
				var siteId = $("#siteId").val();
				if(userTel ==""){
					errorMsg("请输入手机号");
					return;
				}
				if(!tel.test(userTel.replace(/[^0-9]/ig,""))){
					errorMsg("手机号输入错误");
					return;
				}
				$.ajax({
					type:"post",
					url:basePath+"/w/checkUserRegister",
					data:{
						userName:userTel,
						siteId:siteId
					},
					success:function(data){
						if(data=="diff"){//直接显示登录,密码输入框
							
							errorMsg("账号存在,跳转中");
							setTimeout(function(){
								$('.userMsg').eq(2).animate({'opacity':'0'},function(){
									$('.userMsg').css("display","none").eq(2).css('display','block').animate({'opacity':'1'});
								});
								$('.regist-user').val(userTel);
								$('.code-in').val("");
							}, 2000);
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
							 sendMsgCode($("#codeIn1"),60)
							 sendCodeToUser(userTel,siteId);
						}
					}
				});
				
			});
		}
	 
	 