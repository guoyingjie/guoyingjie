var v="4.1.4";
$(function(){

	$(".subBtn").click(function(){
		goNet();
	});
})
function goNet(){
	var reg=/^[1-9]\d*(\.\d{1,2})?$|^[0]\.\d{1,2}$/g;
	if(!reg.test($(".num").val())){
		msg(0,"请输入正确的金额");
		return ;
	}
	var username=$("#username").val();
	var url=basePath+"/weChatPayOther/wxPayOhter?amount="+$(".num").val().trim()+"&openid="+$("#openid").val()+"&userName="+username+"&v="+v;
	if($("#times").val()!=undefined){
		url=basePath+"/weChatPublicNum/payRetroactive?amount="+$(".num").val().trim()+"&openid="+$("#openid").val()+"&userName="+username+"&times="+$("#times").val()+"&siteId="+$("#siteId").val()+"&v="+v;
	}
	window.location.href=url;
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