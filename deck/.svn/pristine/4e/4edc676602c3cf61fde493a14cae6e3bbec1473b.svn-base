var tele = /^(((13[0-9]{1})|(14[4-7]{1})|(15[0-9]{1})|(170)|(173)|(17[1-8]{1})|(18[0-9]{1}))+\d{8})$/;// 手机号码
var v="4.1.4";
$(function(){

	$(".subBtn").click(function(){
		goNet();
	});
})
function goNet(){
	if(!tele.test($(".phone").val())){
		msg(0,"请输入正确的手机号");
		return ;
	}
	var openid = $("#openid").val();
	var username = $(".phone").val();
	window.location.href=basePath+"/weChatPayOther/checkNumBind?userName="+username+"&openid="+openid+"&v="+v;
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