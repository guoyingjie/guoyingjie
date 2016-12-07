var v="4.1.4";
window.onload=function(){

	copy();
	$(".sure").click(function(){
		var openid=$("#openid").val();
		var siteId=$('.siteList > .on').attr("value");
		window.location.href=basePath+"/myselfPay/toPay?openid="+openid+"&siteId="+siteId+"&v="+v;
		
	});
	$(".goPay").click(function(){
		var openid=$("#openid").val();
		var userName=$("#username").val();
		window.location.href=basePath+"/myselfPay/toPayBalance?openid="+openid+"&userName="+userName+"&v="+v;
	});
	$('.siteName').click(function(){
		$('.maskSite').css('display','block');
		$('.maskSite .content').animate({bottom:'0'},400)
	});
	$('.maskSite').click(function(){
		$('.maskSite .content').animate({bottom:'-46%'},400,function(){
			$('.maskSite').css('display','none');
		});
	});
	$('.maskSite .tc span').click(function(){
		$('.maskSite .content').animate({bottom:'-46%'},400,function(){
			$('.maskSite').css('display','none');
		});
	});
	$('.siteList > li').click(function(){
		var n = $('.siteList > li').index(this);
		var str = $(this).text();
		$(".siteList > li").removeClass('on').eq(n).addClass('on');
		$('.maskSite .content').animate({bottom:'-46%'},400,function(){
			$('.maskSite').css('display','none');
			$('.siteName').text(str);
			$('.text > span').text(str);
		});
	});
	$('.content').click(function(){
		return false;
	});
}
function copy(){
	$('.siteList > li').eq(0).addClass("on");
	var str=$('.siteList > li').eq(0).text();
	$('.text > span').text(str);
	$(".siteName").text(str);
	$(".text>span").text($("#site").val());
}
function GetRandomNum(Min,Max)
{   
var Range = Max - Min;   
var Rand = Math.random();   
return(Min + Math.round(Rand * Range));   
}   