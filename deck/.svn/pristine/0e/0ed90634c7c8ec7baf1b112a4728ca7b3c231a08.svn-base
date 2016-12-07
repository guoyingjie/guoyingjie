
var payType=0;
var mType=1;
var submitFlag=false;
$(function(){
	 
	$('.title p').click(function(){
		var n = $('.title p').index(this);
		on($('.title p'),n);
	});
	$('.payList li').click(function(){
		var n = $('.payList li').index(this);
		payType=n;
		on($('.payList li'),n);
		
	});
	$('.payType span').click(function(){
		if($('.typeList').css('display')=='block'){
			$('.typeList').css('display','none');
		}else{
			$('.typeList').css('display','block');
		}
		return false;
	});
	
	$('.typeList > li').click(function(){
		var n = $('.typeList > li').index(this);
		var str = $(this).text();
		var con=$(this).attr("data-text");
		$(".payNum input").val("1");
		$(".typeList li").removeClass('on').eq(n).addClass('on');
			$('.payType').css('display','block');
			$(".favorable").show();
			$(".nr").text("");
			$(".nr").text(con);
			if($(".nr").text()==""){
				$(".favorable").hide();
			}
			$('.payType span').text(str);
			$(".payMony span").text(parseFloat($(".typeList .on").attr("prices")).toFixed(2)+"元");
			sumMoney();
			choiceMeal(n);
		 
	});
	$('.payNum input').bind('input propertychange',function(){
		sumMoney();
	});
	 
	$('.cut').click(function(){
		var n = $('#number').val();
		if(n<=1){
			n = n;
		}else{
			n--;
		}
		$('#number').val(n)
		sumMoney();
	});
	$('.add').click(function(){
		var n = $('#number').val();
		n++;
		$('#number').val(n)
		sumMoney();
	});
	$('.goPay > span').click(function(){
           if(checkSubmit()){
			payWay(payType);
		}
	});
	$('body').click(function(){
		$('.typeList').css('display','none');
	});
	if($(".tLi").length==0){
		$(".title .gprs").css("display","block");
		$(".title .time").css("display","none");
		on($('.title p'),1);
		showFlowTable();
		mType=2;
	}else if($(".fLi").length==0){
		$(".title .time").css("display","block");
		$(".title .gprs").css("display","none");
		on($('.title p'),0);
		showTimeTable();//进去页面默认为选中时间
	}else{
		showTimeTable();//进去页面默认为选中时间
	}
	sumMoney();
	$('.title p').click(function(){
		mType=1;
		$('.payNum input').val("1");
		var n = $('.title p').index(this);
		if(n==0){
			if($(".tLi").length==0){
				msg(0,"该场所未设置时长套餐");
				return;
			}
			$(".tLi").css("display","block");
			$(".fLi").css("display","none");
			$(".payType>span").text($(".tli").eq(0).text());
			showTimeTable();
			sumMoney();
			mType=n+1;
		}
		if(n==1){
			if($(".fLi").length==0){
				msg(0,"该场所未设置流量套餐");
				return;
			}
			$(".fLi").css("display","block")
			$(".tLi").css("display","none");
			$(".payType>span").text($(".fli").eq(0).text());
			showFlowTable();
			sumMoney();
			mType=n+1;
		}
		on($('.title p'),n);
	});
});
//默认时间套餐列表展示
function showTimeTable(){
	$(".typeList li").removeClass('on');
	$(".tLi").css("display","block");
	$(".fLi").css("display","none");
	$(".nr").text("");
	$(".payType>span").text($(".tLi").eq(0).text());
	for(var i=0;i<$(".tLi").length;i++){
		if($(".tLi").eq(i).attr("reommend")==1){
			$(".payType>span").text($(".tLi").eq(i).text());
			$(".favorable").show();
			$(".nr").text($(".tLi").eq(i).attr("data-text"));
			if($(".nr").text()==""){
				$(".favorable").hide();
			}
			mealTble(i,0);
			if(i!=0){
				$(".tLi").eq(0).before($(".tLi").eq(i));
			}
			$(".tLi").eq(0).addClass("on");
			break;
		}
	} 
	//如果所有套餐没有推荐,就选第一条
	if($(".nr").text()==""){
		$(".favorable").show();
		$(".nr").text($(".typeList li").eq(0).attr("data-text"));
		if($(".nr").text()==""){
			$(".favorable").hide();
		}
		$(".typeList li").eq(0).addClass("on");
		mealTble(0,0);
	}
	$(".payMony span").text(parseFloat($(".typeList .on").attr("prices")).toFixed(2)+"元");
}
//默认流量套餐列表
function showFlowTable(){
	$(".typeList li").removeClass('on');
	$(".payType>span").text($(".fLi").eq(0).text());
	$(".nr").text("");
	for(var i=0;i<$(".fLi").length;i++){
		if($(".fLi").eq(i).attr("reommend")==1){
			$(".payType>span").text($(".fLi").eq(i).text());
			$(".favorable").show();
			$(".nr").text($(".fLi").eq(i).attr("data-text"));
			if($(".nr").text()==""){
				$(".favorable").hide();
			}
			mealTble(i,1);
			if(i!=0){
				$(".fLi").eq(0).before($(".fLi").eq(i));
			}
			$(".fLi").eq(0).addClass("on");
			break;
		}
	} 
	//如果所有套餐没有推荐,就选第一条
	if($(".nr").text()==""){
		$(".favorable").show();
		$(".nr").text($(".fLi").eq(0).attr("data-text"));
		if($(".nr").text()==""){
			$(".favorable").hide();
		}
		$(".fLi").eq(0).addClass("on");
		mealTble(0,1);
	}
	$(".payMony span").text(parseFloat($(".payType .on").attr("prices")).toFixed(2)+"元");
}

//默认赠送展示
function mealTble(num,type){
	$(".give").remove();
	if(type==0){
		if($(".tLi").eq(num).attr("mealNum")!=0){
			if($(".tLi").eq(num).attr("mealType")==0){
				$(".payNum").after("<div class='give'>赠送：<span>"+$(".tLi").eq(num).attr("mealNum")+"小时附加包</span></div>")
			}else if($(".tLi").eq(num).attr("mealType")==1){
				$(".payNum").after("<div class='give'>赠送：<span>"+$(".tLi").eq(num).attr("mealNum")+"天附加包</span></div>")
			}else{
				$(".payNum").after("<div class='give'>赠送：<span>"+$(".tLi").eq(num).attr("mealNum")+"月附加包</span></div>")
			}
		}
	}else{
		if($(".fLi").eq(num).attr("mealNum")!=0){
			if($(".fLi").eq(num).attr("mealType")==4){
				$(".payNum").after("<div class='give'>赠送：<span>"+$(".fLi").eq(num).attr("mealNum")+"M流量附加包</span></div>")
			}
			if($(".fLi").eq(num).attr("mealType")==5){
				$(".payNum").after("<div class='give'>赠送：<span>"+$(".fLi").eq(num).attr("mealNum")+"G流量附加包</span></div>")
			}
		}
	}
}
//用户选择套餐时，展示优惠内容
function choiceMeal(x){
	$(".give").remove();
	if($(".payType li").eq(x).attr("mealNum")!=0){
		if($(".payType li").eq(x).attr("mealType")<=3){
			if($(".payType li").eq(x).attr("mealType")==0){
				$(".payNum").after("<div class='give'>赠送：<span>"+$(".payType li").eq(x).attr("mealNum")+"小时附加包</span></div>")
			}
			if($(".payType li").eq(x).attr("mealType")==1){
				$(".payNum").after("<div class='give'>赠送：<span>"+$(".payType li").eq(x).attr("mealNum")+"天附加包</span></div>")
			}
			if($(".payType li").eq(x).attr("mealType")==2){
				$(".payNum").after("<div class='give'>赠送：<span>"+$(".payType li").eq(x).attr("mealNum")+"月附加包</span></div>")
			}
		}else{
			if($(".payType li").eq(x).attr("mealType")==4){
				$(".payNum").after("<div class='give'>赠送：<span>"+$(".payType li").eq(x).attr("mealNum")+"M流量附加包</span></div>")
			}
			if($(".payType li").eq(x).attr("mealType")==5){
				$(".payNum").after("<div class='give'>赠送：<span>"+$(".payType li").eq(x).attr("mealNum")+"G流量附加包</span></div>")
			}
		}
	}
}
function on(obj,n){
	obj.removeClass('on').eq(n).addClass('on');
}


//计算总额
function sumMoney() {
	var price =$(".typeList .on").attr("prices");
	var re = /\D/g;
	var nums = $(".payNum input").val();
	var sumMoney="";
	if (nums == "" || nums == null || nums==0) {
		sumMoney=0;
		$(".paySum span").text(sumMoney.toFixed(2));
		msg(0,"请输入整数");
		submitFlag=false;
		return;
	}
	if(re.test(nums)){
		submitFlag=false;
		return;
	}
	submitFlag=true;
	var sumMoney = price * nums;
	$(".paySum span").html(sumMoney.toFixed(2));
}
function checkSubmit(){
	var num=$(".payNum input").val();
	if(num==0||num==""||num==null){
		submitFlag=false;
		msg(0,"请输入整数");
	}
	return submitFlag;
}



//支付方式
//payWayType区分是支付宝还是微信银联 0--银联  1---支付宝  2---微信  。payMealType区分购买的套餐是流量还是时长  0--时长  1---流量
function payWay(payWayType){
	var amount =$(".paySum span").text();//总金额
	var priceConfigId=$(".typeList .on").attr("value");//场所收费配置Id
	var num = $(".payNum input").val();//购买数量
	var price_num=$(".typeList .on").attr("price_num");//购买套餐配置数量
	var price_name=$(".payType span").text().trim();//购买名称
	var addMealNum=$(".typeList .on").attr("mealNum");//优惠赠送的数量
	var addMealUnit=$(".typeList .on").attr("mealType");//优惠赠送的单位
	var mealType=mType;//购买套餐的类型 1 -- 时长套餐  , 2---流量套餐
	if(payWayType==0){
		window.location.href= basePath+"/pcQuickPayment/pcPayment?nums="+num+"&amount="+amount+"&priceConfig="+priceConfigId+
							"&price_num="+price_num+"&subject="+price_name+"&addMealNum="+addMealNum+"&addMealUnit="+addMealUnit+"&mealType="+mealType;
	}
	if(payWayType==1){
		window.location.href= basePath+"/rechargeLog/gopay?nums="+num+"&amount="+amount+"&priceConfig="+priceConfigId+
		"&price_num="+price_num+"&subject="+price_name+"&addMealNum="+addMealNum+"&addMealUnit="+addMealUnit+"&mealType="+mealType;

	}
	if(payWayType==2){
		 window.location.href=basePath+'/w/goToWeixinPay';
		 
	}
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
