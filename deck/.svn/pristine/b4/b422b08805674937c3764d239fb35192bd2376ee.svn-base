var submitFlag=false;
$(function(){
	init();
	$(".zffs li").click(function(){
		var i=$(".zffs li").index(this);
		$(".zffs li").removeClass('on').eq(i).addClass('on');
		$(".zffs li span").removeClass('on').eq(i).addClass('on');
	});
	$(".lx").click(function(){
		maskon();
	});
	$(".pic").click(function(){
		history.go(-1);
		
	});
	$('.close').click(function(){
		maskoff();
	});
	$('.chargeList>li').click(function(){
		var n=$('.chargeList>li').index(this);
		$('.chargeList>li').removeClass('on').eq(n).addClass('on');
		var str=$('.chargeList>li').eq(n).text();
		maskoff();
		$('.nr').text(str);
		sumMoney();
		
	});
});

function init(){
	$(".chargeList>li").eq(0).addClass("on");
	$(".nr").html($(".chargeList>li").eq(0).html());
	sumMoney();
}
//购买数量键盘事件
/*$("#num").keyup(function() {
	sumMoney();
});*/
$('#num').bind('input propertychange',function(){
	sumMoney();
	
});
	

//计算价格
function sumMoney() {
	var price =$(".chargeList .on").attr("prices");
	var re = /\D/g;
	var nums = $("#num").val();
	var sumMoney="";
	if (nums == "" || nums == null || nums==0) {
		sumMoney=0;
		$(".nm").html(sumMoney.toFixed(2) + "元");
		submitFlag=false;
		$(".btn").css({"background-color": "red"});
		$(".btn").val("请输入整数");
		return;
	}
	if(re.test(nums)){
		submitFlag=false;
		return;
	}
	var sumMoney = price * nums;
	$(".nm").html(sumMoney.toFixed(2) + "元");
	submitFlag=true;
	$(".btn").removeAttr("style");
	$(".btn").val("立即支付");
}
//提交订单时校验
function checkSubmit(){
	var num=$("#num").val();
	if(num==0||num==""||num==null){
		submitFlag=false;
	}
	return submitFlag;
}
//点击支付按钮
$(".btn").click(function(){
	
	var weixins = $(".zffs>.on>#weixin").html();
	var zhifubao = $(".zffs>.on>#zhifubao").html();
		if(checkSubmit()){
			if(weixins=="微信支付"){
				weixin();
			}else{
				if(zhifubao=="支付宝支付"){
					alipay();
				}else{
					jdpay();
				}
			}
		};
});
	

//点击支付宝支付
function alipay(){
	var reg=/[\u4E00-\u9FA5]/g;
	var amount =$(".nm").text().replace(reg,'').trim();//总金额
	var priceConfigId=$(".chargeList .on").attr("value");//场所收费配置Id
	var num = $("#num").val();//购买数量
	var price_num=$(".chargeList .on").attr("price_num");//购买套餐类型
	var price_name=$(".nr").text().trim();//购买名称
		window.location.href= basePath+"/pay/aliPay?nums="+num+"&amount="+amount+"&priceConfig="+priceConfigId+"&price_num="+price_num+"&price_Name="+price_name;
}
//点击京东快捷支付
function jdpay(){
	var reg=/[\u4E00-\u9FA5]/g;
	var amount =$(".nm").text().replace(reg,'').trim();//总金额
	var priceConfigId=$(".chargeList .on").attr("value");//场所收费配置Id
	var num = $("#num").val();//购买数量
	var price_num=$(".chargeList .on").attr("price_num");//购买套餐类型
	var price_name=$(".nr").text().trim();//购买名称
		window.location.href= basePath+"/quickPayment/clickPayment?nums="+num+"&amount="+amount+"&priceConfig="+priceConfigId+"&price_num="+price_num+"&price_name="+price_name;
}
function maskon(){
	$('.mask').css('display','block');
	$('.popup').animate({bottom:'0px'},500);
}
function maskoff(){
	
	$('.popup').animate({bottom:'-50%'},300,function(){
		$('.mask').css('display','none');
	});
}


//跳转到微信支付页面
function weixin(){
	var reg=/[\u4E00-\u9FA5]/g;
	var amount =$(".nm").text().replace(reg,'').trim();//总金额
	var priceConfigId=$(".chargeList .on").attr("value");//场所收费配置Id
	var num = $("#num").val();//购买数量
	var price_num=$(".chargeList .on").attr("price_num");//购买套餐类型
	var price_name=$(".nr").text().trim();//购买名称
		//window.location.href= basePath+"/iappayMobile/weixin?nums="+num+"&amount="+amount+"&priceConfig="+priceConfigId+"&price_num="+price_num+"&subject="+price_name;
	$.ajax({
		type:"post",
		url:basePath+"/iappayMobile/weixin",
		async: false,
		data:{
			nums:num,
			amount:amount,
			priceConfig:priceConfigId,
			price_num:price_num,
			subject:price_name
		},
		success:function(data){
			eval("data="+data);
			if(data.code==200){
				window.location.href=data.msg;
			}else{
				if(data.code==201){
					window.location.href=data.msg;
				}
			}
		},
		error:function(){
			window.location.href=data.msg;
		}
	});
}
