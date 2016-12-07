$(function(){
	$('.mask').css('height',$(window).height()+'px');//初始化缴费页面遮罩层大小
	$('.log-mask').css('height',$(window).height()+'px');//初始化缴费页面遮罩层大小
	 pwd(true);//调用密码提示框显示/隐藏函数
	 user(false);//调用手机号提示框显示/隐藏函数
	 test(false);//调用验证码提示框显示/隐藏函数
	 num(false);//调用购买数量提示框显示/隐藏函数
	//登录页面单击登陆后显示 start
	$('.fn-log-btn').click(function(){
		logmask(true);
	});
	//登录页面单击登陆后显示 end
	// 按回车键触发事件 start
	$(document).keydown(function(event){ 
		if(event.keyCode==13){
			$('.log-mask').css('display','block');
		}
	});
	// 按回车键触发事件 end
	//处理中动画 start
	var sec=5;
	var mastr="处理中···";
	var numb=0;
	var time=setInterval(function(){
		if($('.mana').html()=="处理中···"){
			$('.mana').html("");
			numb=0;
		}
		$('.mana').html($('.mana').html()+mastr[numb])
		numb++;
	},500);
	//处理中动画 end
	//登陆/注册/修改密码页面5秒倒计时跳转 start
	var dtime=setInterval(function(){
		sec--;
		if(sec==0){
			clearInterval(dtime);
			window.location.href='';
		}
		$('.sec').html(sec);
	},1000)
})
//登陆/注册/修改密码页面5秒倒计时跳转 start
/* 购买类型下拉菜单---js动作组 */
$('.date').click(function(){/* 显示/隐藏下来菜单 */
	if($('.ul-list').css('display')=="none"){
		$('.fn-pu').removeClass('icon-chevron-down').addClass("icon-chevron-up");
		$('.ul-list').css('display','block');
	}else{
		$('.fn-pu').removeClass('icon-chevron-up').addClass("icon-chevron-down");
		$('.ul-list').css('display','none');
	}
})
$('.ul-list li').click(function(){
	var str=$('.ul-list li').eq($('.ul-list li').index(this)).html();
	//console.log(str)
	$('.date').html(str+'<i class="fn-pu icon-chevron-down"></i>')
	$('.ul-list').css('display','none');
})
/* 选择支付方式----js动作 */
$('.payment li').click(function(){
	var n=$('.payment li').index(this);
	$('.payment li').removeClass('on').eq(n).addClass('on');
})
/* 选择银行----js动作 */
$('.pay-btn').click(function(){
		var str=$('.payment li.on').html();
		var nstr=str.substr(str.length-3)
		//console.log(nstr);
		if(nstr!='支付宝'){
			$('.mask').css('display','block');
			$('.tc').css('display','block');
		}
	})
	$('.mask').click(function(){
		$('.mask').css('display','none');
		$('.tc').css('display','none');
	})
	$('.djgb').click(function(){
		$('.mask').css('display','none');
		$('.tc').css('display','none');
	})
	$('.bank .h-list h3').click(function(){
		var blh3=$('.bank .h-list h3').index(this);
		$('.bank .h-list h3 i').removeClass('on').eq(blh3).addClass('on');
	})
/* 错误提示信息隐藏/显示 */

/* 手机号错误提示显示/隐藏 */
var user=function(judeng){
	if(judeng){
		$('.tel-hint').css('display','none');
	}else{
		$('.tel-hint').css('display','block');
	}
}
/* 密码错误提示显示/隐藏 */
var pwd=function(judeng){
	if(judeng){
		$('.pwd-hint').css('display','none');
	}else{
		$('.pwd-hint').css('display','block');
	}
}
/* 验证码错误提示显示/隐藏 */
var test=function(judeng){
	if(judeng){
		$('.test-hint').css('display','none');
	}else{
		$('.test-hint').css('display','block');
	}
}
/* 购买数量错误提示显示/隐藏 */
var num=function(judeng){
	if(judeng){
		$('.buy-num').css('display','none');
	}else{
		$('.buy-num').css('display','block');
	}
}
/* 点击登录页显示遮罩 动画 */
var logmask=function(judeng){
	if(judeng){
		$('.log-mask').css('display','block');
	}
}