window.onload=function(){
	isHavePhone();
	isUserName();
	$("#personGo").click(function(){
		var userName= $("#username").val();
		var siteId = $("#siteId").val();
	    window.location.href= ctx+"/w/goToPerson?userName="+userName+"&siteId="+siteId;
	});
	 $("#message").click(function(){
		 window.location.href=ctx+"/ProtalUserManage/goToMessagePage";
	 });
	    var mess = $("#mess").val();
		if(mess==0){
			$("#message").removeClass("on");
		}else{
			$("#message").addClass("on");
		}
		$(".goPay").click(function(){
			 window.location.href=ctx+"/w/toRealPay";
		});
};
//判断是否有图片
function isHavePhone(){
	var imgSrc = $('#phoneurl').val();
	if(imgSrc){
		$('.photo').css('background','#fff url(http://oss.kdfwifi.net/user_pic/'+imgSrc+') no-repeat center');
		$('.photo').css('background-size','cover');
	}
}
//用户名切成232***223的格式
function isUserName(){
	var username = $("#username").val();
	var name = username.substring(0,3); 
	var end = username.substring(7,username.length);
	var allname = name+"****"+end;
	$('.phone').html(allname);
}

function msg(code,str){
	console.log(1)
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
			},2900);
		});
	}
}