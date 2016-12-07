var v="4.1.4";
window.onload=function(){

	tiao(1);
	$('.next').click(function(){
		var n = $('.next').index(this);
		 
		var n = $('.next').index(this);
		if(n==0){
			return checkUserPs(0);
		}else if(n==1){
			return perfectPs(1);
		}
		
		
		if(n<1){
			$('.content').addClass('small');
			setTimeout(function(){
				boxMove(n);
			},400);
		}
	});
	$('.codeBtn').click(function(){
		coundDown($(this),60);
	});
}
//校验原始密码
function checkUserPs(n){
	var password = $(".jpass").val();
	if(null==password||""==password){
		msg(0,"请输入密码")
		return false;
	}
	$.ajax({
		type:'post',
		url :ctx+"/weChatPublicNum/checkUserPs",
		data:{
			password:password,
			v:v
		},
		success:function(data){
			if(data=="true"){
				if(n<1){
					$('.content').addClass('small');
					setTimeout(function(){
						boxMove(n);
					},400);
				}
			}else{
				msg(0,"登录密码不一致");
				return false;
			}
		},
		error:function(){
			msg(0,"系统繁忙,请稍后")
			return false;
		}
	});
}
//修改密码
function perfectPs(n){
	var one = $.trim($(".pass").val());
	var two = $.trim($(".inpass").val());
	var username = $.trim($("#username").val());
	var siteId = $.trim($("#siteId").val());
	var openid=$("#openid").val();
	if(one==null||""==one){
		msg(0,"请输入密码");
		return false;
	}
	if(two==null||""==two){
		msg(0,"请输入密码");
		return false;
	}
	if(one!=two){
		msg(0,"两次密码不一致")
		return false;
	}
	$.ajax({
		type:'post',
		url :ctx+"/weChatPublicNum/changePsforUser",
		data:{
			password:two,
			username:username,
			v:v
		},
		success:function(data){
			if(data=="true"){
				if(n<1){
					$('.content').addClass('small');
					setTimeout(function(){
						boxMove(n);
					},400);
				}
				msg(1,"密码修改成功")
				setTimeout(function(){
					if(openid=='noopenid'){
						window.location.href=ctx+"/w/goToPerson?userName="+username+"&siteId="+siteId;
					}else{
						window.location.href=ctx+"/weChatPublicNum/goToPerson?userName="+username+"&siteId="+siteId+"&openId="+openid+"&v="+v;
					}
				}, 1000);
			}else{
				msg(0,"修改失败");
				return false;
			}
		},
		error:function(){
			msg(0,"系统繁忙,请稍后")
			return false;
		}
	});
	
	
}

function tiao(n){
	if(n==1){
		$('.three').removeClass('on');
		$('.two').removeClass('on');
		$('.tiao').animate({width:'15%'},200,function(){
			$('.one').addClass('on');
		});
	}else if(n==2){
		$('.three').removeClass('on');
		$('.tiao').animate({width:'50%'},300,function(){
			$('.two').addClass('on');
		});
	}else if(n==3){
		$('.two').addClass('on');
		$('.tiao').animate({width:'100%'},300,function(){
			$('.three').addClass('on');
		});
	}
}
function boxMove(n){
	$('.content').css('display','none');
	$('.content').eq(n+1).css('display','block');
}
 
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