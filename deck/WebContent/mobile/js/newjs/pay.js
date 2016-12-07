var payType=0;
var mType=1;
var submitFlag=false;
window.onload=function(){
	
	var imgSrc = $('#phoneurl').val();
	if(imgSrc){
		$('.goPerson').css('background','#fff url(http://oss.kdfwifi.net/user_pic/'+imgSrc+') no-repeat center');
		$('.goPerson').css('background-size','cover');
	}
	
	if($(".tLi").length==0){
		$(".tab .gprs").css("display","block");
		$(".tab .time").css("display","none");
		on($('.tab p'),1);
		showFlowTable();
		mType=2;
	}else if($(".fLi").length==0){
		$(".tab .time").css("display","block");
		$(".tab .gprs").css("display","none");
		on($('.tab p'),0);
		showTimeTable();//进去页面默认为选中时间
	}else{
		showTimeTable();//进去页面默认为选中时间
	}
	sumMoney();
	$('.tab p').click(function(){
		mType=1;
		$('.payNum input').val("1");
		var n = $('.tab p').index(this);
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
		on($('.tab p'),n);
	});
	$('.payList li').click(function(){
		var n = $('.payList li').index(this);
		payType=n;
		on($('.payList li'),n);
		
	});
	$('.payType span').click(function(){
		$('.maxkXl').css('display','block');
		$('.content').animate({bottom:'0'},400)
	});
	$('.maxkXl').click(function(){
		$('.content').animate({bottom:'-46%'},400,function(){
			$('.maxkXl').css('display','none');
		});
	});
	$('.tc span').click(function(){
		$('.content').animate({bottom:'-46%'},400,function(){
			$('.maxkXl').css('display','none');
		});
	});
	$('.payTypeList > li').click(function(){
		var n = $('.payTypeList > li').index(this);
		var str = $(this).text();
		var con=$(this).attr("data-text");
		$(".payNum input").val("1");
		$(".payTypeList li").removeClass('on').eq(n).addClass('on');
		$('.content').animate({bottom:'-46%'},400,function(){
			$('.maxkXl').css('display','none');
			$(".favorable").show();
			$(".nr").text("");
			$(".nr").text(con);
			if($(".nr").text()==""){
				$(".favorable").hide();
			}
			$('.payType span').text(str);
			$(".payMony span").text(parseFloat($(".payTypeList .on").attr("prices")).toFixed(2)+"元");
			sumMoney();
			choiceMeal(n);
		});
	});
	$('.content').click(function(){
		return false;
	});
	$('.payNum input').bind('input propertychange',function(){
		sumMoney();
		
	});
	$(".goPay span").click(function(){
	
		if(checkSubmit()){
			
			payWay(payType);
		}
	});
	$('.cut').click(function(){
		var n = $('#number').val();
		if(n<=1){
			n = n;
		}else{
			n--;
		}
		$('#number').val(n);
		sumMoney();
	});
	$('.add').click(function(){
		var n = $('#number').val();
		n++;
		$('#number').val(n);
		sumMoney();
	});
	$('.mask').click(function(){
		$(this).css('display','none');
	});
	$('.goPay > span').click(function(){
		var str = $('.payList .on').attr('class');
		if(str=='weiX on'){
			$('.mask').css('display','block');
		}
	});
	
}
function on(obj,n){
	obj.removeClass('on').eq(n).addClass('on');
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
//默认时间套餐列表展示
function showTimeTable(){
	$(".payTypeList li").removeClass('on');
	$(".tLi").css("display","block");
	$(".fLi").css("display","none");
	$(".nr").text("");
	$(".favorable").show();
	$(".payType>span").text($(".tLi").eq(0).text());
	for(var i=0;i<$(".tLi").length;i++){
		if($(".tLi").eq(i).attr("reommend")==1){
			$(".payType>span").text($(".tLi").eq(i).text());
			$(".nr").text($(".tLi").eq(i).attr("data-text"));
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
		$(".nr").text($(".payTypeList li").eq(0).attr("data-text"));
		$(".payTypeList li").eq(0).addClass("on");
		mealTble(0,0);
	}
	if($(".nr").text()==""){
		$(".favorable").hide();
	}
	$(".payMony span").text(parseFloat($(".payTypeList .on").attr("prices")).toFixed(2)+"元");
}
//默认流量套餐列表
function showFlowTable(){
	$(".payTypeList li").removeClass('on');
	$(".payType>span").text($(".fLi").eq(0).text());
	$(".nr").text("");
	$(".favorable").show();
	for(var i=0;i<$(".fLi").length;i++){
		if($(".fLi").eq(i).attr("reommend")==1){
			$(".payType>span").text($(".fLi").eq(i).text());
			$(".nr").text($(".fLi").eq(i).attr("data-text"));
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
		$(".nr").text($(".fLi").eq(0).attr("data-text"));
		$(".fLi").eq(0).addClass("on");
		mealTble(0,1);
	}
	if($(".nr").text()==""){
		$(".favorable").hide();
	}
	$(".payMony span").text(parseFloat($(".payTypeList .on").attr("prices")).toFixed(2)+"元");
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
	if($(".payTypeList li").eq(x).attr("mealNum")!=0){
		if($(".payTypeList li").eq(x).attr("mealType")<=3){
			if($(".payTypeList li").eq(x).attr("mealType")==0){
				$(".payNum").after("<div class='give'>赠送：<span>"+$(".payTypeList li").eq(x).attr("mealNum")+"小时附加包</span></div>")
			}
			if($(".payTypeList li").eq(x).attr("mealType")==1){
				$(".payNum").after("<div class='give'>赠送：<span>"+$(".payTypeList li").eq(x).attr("mealNum")+"天附加包</span></div>")
			}
			if($(".payTypeList li").eq(x).attr("mealType")==2){
				$(".payNum").after("<div class='give'>赠送：<span>"+$(".payTypeList li").eq(x).attr("mealNum")+"月附加包</span></div>")
			}
		}else{
			if($(".payTypeList li").eq(x).attr("mealType")==4){
				$(".payNum").after("<div class='give'>赠送：<span>"+$(".payTypeList li").eq(x).attr("mealNum")+"M流量附加包</span></div>")
			}
			if($(".payTypeList li").eq(x).attr("mealType")==5){
				$(".payNum").after("<div class='give'>赠送：<span>"+$(".payTypeList li").eq(x).attr("mealNum")+"G流量附加包</span></div>")
			}
		}
	}
}

//计算总额
function sumMoney() {
	var price =$(".payTypeList .on").attr("prices");
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
	var priceConfigId=$(".payTypeList .on").attr("value");//场所收费配置Id
	var num = $(".payNum input").val();//购买数量
	var price_num=$(".payTypeList .on").attr("price_num");//购买套餐配置数量
	var price_name=$(".payType span").text().trim();//购买名称
	var addMealNum=$(".payTypeList .on").attr("mealNum");//优惠赠送的数量
	var addMealUnit=$(".payTypeList .on").attr("mealType");//优惠赠送的单位
	var mealType=mType;//购买套餐的类型 1 -- 时长套餐  , 2---流量套餐
	if(payWayType==0){
		window.location.href= basePath+"/quickPayment/clickPayment?nums="+num+"&amount="+amount+"&priceConfig="+priceConfigId+
							"&price_num="+price_num+"&price_name="+price_name+"&addMealNum="+addMealNum+"&addMealUnit="+addMealUnit+"&mealType="+mealType;
	}
	if(payWayType==1){
		window.location.href= basePath+"/pay/aliPay?nums="+num+"&amount="+amount+"&priceConfig="+priceConfigId+
		"&price_num="+price_num+"&price_Name="+price_name+"&addMealNum="+addMealNum+"&addMealUnit="+addMealUnit+"&mealType="+mealType;

	}
	if(payWayType==2){
		//msg(0,"关注公众号:宽未来")
		 
	}
}

