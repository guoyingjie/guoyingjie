var v="4.1.4";
$(function(){

	
	$(".sure").click(function(){
		var openid=$("#openid").val();
		var siteId=$('#site').attr("siteId");
		window.location.href=basePath+"/myselfPay/toPay?openid="+openid+"&siteId="+siteId+"&v="+v;
	});
	
	$(".goPay").click(function(){
		var openid=$("#openid").val();
		var username=$("#username").val();
		window.location.href=basePath+"/myselfPay/toPayBalance?openid="+openid+"&userName="+username+"&v="+v;
	});
	$(".text>span").text($("#site").val());
})
function GetRandomNum(Min,Max)
	{   
	var Range = Max - Min;   
	var Rand = Math.random();   
	return(Min + Math.round(Rand * Range));   
}   