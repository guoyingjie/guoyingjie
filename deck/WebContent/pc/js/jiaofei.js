var submitFlag=false;
$(function() {
	
	$('.mask').css('height',$(window).height()+'px');
	num(false);//调用购买数量提示框显示/隐藏函数
	setPriceType();
	into();
	$('.date').click(function(){/* 显示/隐藏下来菜单 */
		if($('.ul-list').css('display')=="none"){
			$('.fn-pu').removeClass('icon-chevron-down').addClass("icon-che" +
					"vron-up");
			$('.ul-list').css('display','block');
		}else{
			$('.fn-pu').removeClass('icon-chevron-up').addClass("icon-chevron-down");
			$('.ul-list').css('display','none');
		}
	});
	$('.ul-list li').click(function(){
		var str=$('.ul-list li').eq($('.ul-list li').index(this)).html();
		
		$('.date').html(str+'<i class="fn-pu icon-chevron-down"></i>');
		
		$('.ul-list').css('display','none');
	});
	/* 选择支付方式----js动作 */
	$('.payment li').click(function(){
		var n=$('.payment li').index(this);
		$('.payment li').removeClass('on').eq(n).addClass('on');
	});
	/* 选择银行----js动作 */
	$('.pay-btn').click(function(){
	    var str = $('.payment li.on').html();
		var nstr = str.substr(str.length - 4);
		// console.log(nstr);
			if (!submitFlag && $("#num").val() != 0 && $("#num").val() != "") {
				if(nstr=="微信支付"){
					weixin();
				}else{
				if (!$("#zfli").attr("class")) {
					PayCard();
				} else {
					pay();
				}
			}	 
		}
	});
		$('.mask').click(function(){
			$('.mask').css('display','none');
			$('.tc').css('display','none');
		});
		$('.djgb').click(function(){
			$('.mask').css('display','none');
			$('.tc').css('display','none');
		});
		$('.bank .h-list h3').click(function(){
			var blh3=$('.bank .h-list h3').index(this);
			$('.bank .h-list h3 i').removeClass('on').eq(blh3).addClass('on');
		});
	

	$("#rq li").click(function(){
		s=$("#rq li").index(this);
		$('#rq li').removeClass('on').eq(s).addClass('on');
		var cc=$(".on").html();
		$("#priceConfig").val($(".on").attr("value"));
		$("#priceNum").val($(".on").attr("pricenum"));
		$(".date").html(cc+'<i class="fn-pu icon-chevron-down"></i>');
		
		
		sumMoney();
		
	});
});
	
	$("#num").keyup(function() {
		if($("#num").val() == 0 || $("#num").val() == ""){
			$('.buy-num').css('display','block');
		}else{
			
			$('.buy-num').css('display','none');
		}
		sumMoney();
	});
	//初始化计算总价
	initAmount();

function into(){
	var str=$('.ul-list li').eq(0).html();
	$(".date").html(str+'<i class="fn-pu icon-chevron-down"></i>');
};

// 计算价格
function sumMoney() {
	/*var price = $("#priceConfig").find("option:selected").attr("prices");*/
	var price =$(".on").attr("prices");
	//var re = /\D/g;
	var nums = $("#num").val();
	var sumMoney="";
	if (nums == "" || nums == null ) {
		sumMoney=0;
		$("#je").html(sumMoney.toFixed(2)+"元");
		$("#amount").val(sumMoney.toFixed(2));
		$(".money span").html(sumMoney.toFixed(2)+"元");
		submitFlag=false;
		return;
	}

	 sumMoney = price * nums;
	$("#je").html(sumMoney.toFixed(2)+"元");
	$("#amount").val(sumMoney.toFixed(2));
	$(".money span").html(sumMoney.toFixed(2)+"元");
};

function initAmount(){
	$("#rq li").eq(0).addClass("on");
	$("#priceConfig").val($(".on").attr("value"));
	//$(".date").html($("#rq li").eq(0).html());
	$("#priceNum").val($("#rq li").eq(0).attr("pricenum"));
	sumMoney();
	
}

function pay(){
		
		var amount=$("#amount").prop("value");//总金额
		var priceConfig=$("#priceConfig").val();//场所收费配置Id
		var num = $("#num").val();//购买数量
		$(".date i").remove();
		var subject =$(".date").html();//主题名称
		var price_num=$("#priceNum").val();
		
		//window.open(basePath+"/rechargeLog/gopay?nums="+num+"&amount="+amount+"&priceConfig="+priceConfig+"&subject="+subject);
		window.location.href= basePath+"/rechargeLog/gopay?nums="+num+"&amount="+amount+"&priceConfig="+priceConfig+"&subject="+subject+"&price_num="+price_num;
		//return false;
};	

function setPriceType() { // 设置续费类型
	/*var type = $("#yu").val();
	var value = "";
	if (type == 0) {
		value = "小时";
	}
	if (type == 1) {
		value = "天";
	}
	if (type == 2) {
		value = "月";
	}*/
	/*var a=document.getElementById('rq');*/
//	var yu =document.getElementById('yu');
//	yu = $("rq li").value;
/*	$("#pconfig").html(value);*/
	/*var price = $("#priceConfig").find("option:selected").attr("prices");
	$("#unitPrice").html((price*1).toFixed(2)+"&nbsp;元");//(price*1)是为了转换格式，懒得用方法转了
*/}

function submitCheck(){
	return submitFlag;
}


function PayCard(){
	var amount=$("#amount").prop("value");//总金额
	var priceConfig=$("#priceConfig").val();//场所收费配置Id
	var num = $("#num").val();//购买数量 
	var price_num=$("#priceNum").val();
	$(".date i").remove();
	var subject =$(".date").html();//主题名称
	if(!submitFlag && $("#num").val() != 0 && $("#num").val() != ""){
		window.location.href= basePath+"/pcQuickPayment/pcPayment?nums="+num+"&amount="+amount+"&priceConfig="+priceConfig+"&ipAddr="+ipAddr+"&price_num="+price_num+"&subject="+subject;
		
	}
};
/* 购买数量错误提示显示/隐藏 */
var num=function(judeng){
	if(judeng){
		$('.buy-num').css('display','none');
	}else{
		$('.buy-num').css('display','block');
	}
};


//跳转到微信支付页面
function weixin(){
	var amount=$("#amount").prop("value");//总金额
	var priceConfig=$("#priceConfig").val();//场所收费配置Id
	var num = $("#num").val();//购买数量 
	var price_num=$("#priceNum").val();
	$(".date i").remove();
	var subject =$(".date").html();//主题名称
		//window.location.href=basePath+"/iappay/weixin?nums="+num+"&amount="+amount+"&priceConfig="+priceConfig+"&ipAddr="+ipAddr+"&price_num="+price_num+"&subject="+subject;
		$.ajax({
			type:"post",
			url:basePath+"/iappay/weixin",
			async: false,
			data:{
				nums:num,
				amount:amount,
				priceConfig:priceConfig,
				price_num:price_num,
				subjects:subject
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
			}
		});
}























