var order=0;
var mType=1;
var submitFlag=false;
var v="4.1.4";
$(function(){
	//进取页面判断默认加载套餐类型

	hide(order);
	$('.tab p').click(function(){
		mType=1;
		$('.payNum input').val("1");
		var n = $('.tab p').index(this);
		if(n==0){
			if($(".tLi").length==0){
				msg(0,"该场所未设置时长套餐");
				return;
			}
			$(".fLi").removeClass("on");
			$(".payTypeList>ul:eq("+order+") .tLi").css("display","block");
			$(".payTypeList>ul:eq("+order+") .fLi").css("display","none");
			//$(".payType>span").text($(".payTypeList>ul:eq("+order+") .tLi").eq(0).text());
			showTimeTable(order);
			sumMoney();
			mType=n+1;
		}
		if(n==1){
			if($(".fLi").length==0){
				msg(0,"该场所未设置流量套餐");
				return;
			}
			$(".tLi").removeClass("on");
			$(".payTypeList>ul:eq("+order+") .fLi").css("display","block")
			$(".payTypeList>ul:eq("+order+") .tLi").css("display","none");
			//$(".payType>span").text($(".payTypeList>ul:eq("+order+") .fLi").eq(0).text());
			showFlowTable(order);
			sumMoney();
			mType=n+1;
		}
		on($('.tab p'),n);
	});
	$('.payList li').click(function(){
		var n = $('.payList li').index(this);
		on($('.payList li'),n);
	});
	$('.siteName').click(function(){
		$('.maskSite').css('display','block');
		$('.maskSite .content').animate({bottom:'0'},400)
	});
	$('.payType span').click(function(){
		$('.maxkXl').css('display','block');
		$('.maxkXl .content').animate({bottom:'0'},400)
	});
	$('.maxkXl').click(function(){
		$('.maxkXl .content').animate({bottom:'-46%'},400,function(){
			$('.maxkXl').css('display','none');
		});
	});
	$('.payNum input').bind('input propertychange',function(){
		if($(".payNum input").val()=="0"||$(".payNum input").val()==""){
			$(".payNum input").val("1");
		}
		sumMoney();
		
	});
	$('.maskSite').click(function(){
		$('.maskSite .content').animate({bottom:'-46%'},400,function(){
			$('.maskSite').css('display','none');
		});
	});
	$('.maxkXl .tc span').click(function(){
		$('.maxkXl .content').animate({bottom:'-46%'},400,function(){
			$('.maxkXl').css('display','none');
		});
	});
	$('.maskSite .tc span').click(function(){
		$('.maskSite .content').animate({bottom:'-46%'},400,function(){
			$('.maskSite').css('display','none');
		});
	});
	
	$(".payTypeList>ul:eq("+order+") Li").click(function(){
		var n = $(".payTypeList>ul:eq("+order+") Li").index(this);
		var str = $(this).text();
		var con=$(this).attr("data-text");
		$(".payTypeList>ul:eq("+order+") Li").removeClass('on').eq(n).addClass('on');
		$('.content').animate({bottom:'-46%'},400,function(){
			$('.maxkXl').css('display','none');
			$(".nr").text("");
			$(".nr").text(con);
			$(".favorable").css("display","block");
			if($(".nr").text()==""){
				$(".favorable").css("display","none");
			}
			$('.payType span').text(str);
			$(".payMony span").text(parseFloat($(".payTypeList .on").attr("prices")).toFixed(2)+"元");
			sumMoney();
			choiceMeal(order,n);
		});
	});
	$('.siteList > li').click(function(){
		$(".payTypeList ul li").removeClass("on");
		var n = $('.siteList > li').index(this);
		order=n;
		var str = $(this).text();
		$('.maskSite .content').animate({bottom:'-46%'},400,function(){
			$('.maskSite').css('display','none');
			$('.siteName').text(str);
			hide(order);
		});
		$(".payTypeList>ul Li").unbind('click');
		$(".payTypeList>ul:eq("+order+") Li").click(function(){
			var n = $(".payTypeList>ul:eq("+order+") Li").index(this);
			var str = $(this).text();
			var con=$(this).attr("data-text");
			$(".payTypeList>ul:eq("+order+") Li").removeClass('on').eq(n).addClass('on');
			$('.content').animate({bottom:'-46%'},400,function(){
				$('.maxkXl').css('display','none');
				$(".nr").text("");
				$(".nr").text(con);
				$(".favorable").css("display","block");
				if($(".nr").text()==""){
					$(".favorable").css("display","none");
				}
				$('.payType span').text(str);
				$(".payMony span").text(parseFloat($(".payTypeList .on").attr("prices")).toFixed(2)+"元");
				sumMoney();
				choiceMeal(order,n);
			});
		});
	});
	$('.content').click(function(){
		return false;
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
	$('.mask').click(function(){
		$(this).css('display','none');
	});
	$('.goPay ').click(function(){
		
		var classSum=$(".balance").attr("class");
		if(classSum=="balance on"){
			if(submitFlag){
				goPay();
			}
		}else{
			if(submitFlag){
				goBalancePay();
			}
		}
	});
	
	$('.balance').click(function(){
		var sum=$(".balance span").text();
		if(sum!="0"){
			var str = $('.balance').attr('class');
			if(str=='balance on'){
				$('.balance').removeClass('on');
			}else{
				$('.balance').addClass('on');
			}
		}
	});
	copyValue();
})
function on(obj,n){
	obj.removeClass('on').eq(n).addClass('on');
}
//默认选中第一个场所值
function copyValue(){
	$(".siteName").text($('.siteList > li').eq(0).text());
	if(sum!=0&&sum!=null){
		$(".balance span").text(sum);
	}else{
		$(".balance span").text(0);
		$('.balance').addClass('on');
	}
	var len=$('.siteList > li').length;
	if(len==1){
		$('.siteName').unbind('click');
		$(".site").text("当前场所");
	}
	
}
//判断该场所是否开启流量或者是时间
function hide(n){
	$(".payTypeList>ul").css("display","none");
	$(".payTypeList>ul").eq(n).css("display","block");
	if($(".payTypeList>ul:eq("+n+") .tLi").length==0){
		$('.tab p').eq(1).css("display","block");
		$('.tab p').eq(0).css("display","none");
		showFlowTable(n);
	}else if($(".payTypeList>ul:eq("+n+") .fLi").length==0){
		$('.tab p').eq(0).css("display","block");
		$('.tab p').eq(1).css("display","none");
		showTimeTable(n);
	}else{
		$('.tab p').css("display","block");
		showTimeTable(n);
		
	}
	sumMoney();
}
//显示套餐
function hideMeal(n){
	$(".payTypeList>ul").eq(n).css("display","block");
}
//默认赠送展示
function mealTble(n,num,type){
	$(".give").remove();
	if(type==0){
		if($(".payTypeList>ul:eq("+n+") .tLi").eq(num).attr("mealNum")!=0){
			if($(".payTypeList>ul:eq("+n+") .tLi").eq(num).attr("mealType")==0){
				$(".payNum").after("<div class='give'>赠送：<span>"+$(".payTypeList>ul:eq("+n+") .tLi").eq(num).attr("mealNum")+"小时附加包</span></div>")
			}else if($(".payTypeList>ul:eq("+n+") .tLi").eq(num).attr("mealType")==1){
				$(".payNum").after("<div class='give'>赠送：<span>"+$(".payTypeList>ul:eq("+n+") .tLi").eq(num).attr("mealNum")+"天附加包</span></div>")
			}else{
				$(".payNum").after("<div class='give'>赠送：<span>"+$(".payTypeList>ul:eq("+n+") .tLi").eq(num).attr("mealNum")+"月附加包</span></div>")
			}
		}
	}else{
		if($(".payTypeList>ul:eq("+n+") .fLi").eq(num).attr("mealNum")!=0){
			if($(".payTypeList>ul:eq("+n+") .fLi").eq(num).attr("mealType")==4){
				$(".payNum").after("<div class='give'>赠送：<span>"+$(".payTypeList>ul:eq("+n+") .fLi").eq(num).attr("mealNum")+"M流量附加包</span></div>")
			}
			if($(".payTypeList>ul:eq("+n+") .fLi").eq(num).attr("mealType")==5){
				$(".payNum").after("<div class='give'>赠送：<span>"+$(".payTypeList>ul:eq("+n+") .fLi").eq(num).attr("mealNum")+"G流量附加包</span></div>")
			}
		} 
	}
}
//默认时间套餐列表展示
function showTimeTable(n){
	$(".payTypeList>ul:eq("+n+") .fLi").css("display","none");
	$(".payTypeList>ul:eq("+n+") .tLi").removeClass('on');
	$(".favorable").css("display","block");
	$(".nr").text("");
	$(".payType>span").text($(".payTypeList>ul:eq("+n+") .tLi").eq(0).text());
	for(var i=0;i<$(".payTypeList>ul:eq("+n+") .tLi").length;i++){
		if($(".payTypeList>ul:eq("+n+") .tLi").eq(i).attr("reommend")==1){
			$(".payType>span").text($(".payTypeList>ul:eq("+n+") .tLi").eq(i).text());
			$(".nr").text($(".payTypeList>ul:eq("+n+") .tLi").eq(i).attr("data-text"));
			mealTble(n,i,0);
			if(i!=0){
				$(".payTypeList>ul:eq("+n+") .tLi").eq(0).before($(".payTypeList>ul:eq("+n+") .tLi").eq(i));
			}
			$(".payTypeList>ul:eq("+n+") .tLi").eq(0).addClass("on");
			break;
		}
	} 
	//如果所有套餐没有推荐,就选第一条
	if($(".nr").text()==""){
		$(".nr").text($(".payTypeList>ul:eq("+n+") .tLi").eq(0).attr("data-text"));
		$(".payTypeList>ul:eq("+n+") .tLi").eq(0).addClass("on");
		mealTble(n,0,0);
	}
	if($(".nr").text()==""){
		$(".favorable").css("display","none");
	}
	$(".payMony span").text(parseFloat($(".payTypeList .on").attr("prices")).toFixed(2)+"元");
}
//默认流量套餐列表
function showFlowTable(n){
	$(".payTypeList>ul:eq("+n+") .fLi").removeClass('on');
	$(".payType>span").text($(".payTypeList>ul:eq("+n+") .fLi").eq(0).text());
	$(".favorable").css("display","block");
	$(".nr").text("");
	for(var i=0;i<$(".payTypeList>ul:eq("+n+") .fLi").length;i++){
		if($(".payTypeList>ul:eq("+n+") .fLi").eq(i).attr("reommend")==1){
			$(".payType>span").text($(".payTypeList>ul:eq("+n+") .fLi").eq(i).text());
			$(".nr").text($(".payTypeList>ul:eq("+n+") .fLi").eq(i).attr("data-text"));
			mealTble(n,i,1);
			if(i!=0){
				$(".payTypeList>ul:eq("+n+") .fLi").eq(0).before($(".payTypeList>ul:eq("+n+") .fLi").eq(i));
			}
			$(".payTypeList>ul:eq("+n+") .fLi").eq(0).addClass("on");
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
		$(".favorable").css("display","none");
	}
	$(".payMony span").text(parseFloat($(".payTypeList .on").attr("prices")).toFixed(2)+"元");
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
//用户选择套餐时，展示优惠内容
function choiceMeal(n,x){
	$(".give").remove();
	if($(".payTypeList>ul:eq("+order+") Li").eq(x).attr("mealNum")!=0){
		if($(".payTypeList>ul:eq("+order+") Li").eq(x).attr("mealType")<=3){
			if($(".payTypeList>ul:eq("+order+") Li").eq(x).attr("mealType")==0){
				$(".payNum").after("<div class='give'>赠送：<span>"+$(".payTypeList>ul:eq("+order+") Li").eq(x).attr("mealNum")+"小时附加包</span></div>")
			}
			if($(".payTypeList>ul:eq("+order+") Li").eq(x).attr("mealType")==1){
				$(".payNum").after("<div class='give'>赠送：<span>"+$(".payTypeList>ul:eq("+order+") Li").eq(x).attr("mealNum")+"天附加包</span></div>")
			}
			if($(".payTypeList>ul:eq("+order+") Li").eq(x).attr("mealType")==2){
				$(".payNum").after("<div class='give'>赠送：<span>"+$(".payTypeList>ul:eq("+order+") Li").eq(x).attr("mealNum")+"月附加包</span></div>")
			}
		}else{
			if($(".payTypeList>ul:eq("+order+") Li").eq(x).attr("mealType")==4){
				$(".payNum").after("<div class='give'>赠送：<span>"+$(".payTypeList>ul:eq("+order+") Li").eq(x).attr("mealNum")+"M流量附加包</span></div>")
			}
			if($(".payTypeList>ul:eq("+order+") Li").eq(x).attr("mealType")==5){
				$(".payNum").after("<div class='give'>赠送：<span>"+$(".payTypeList>ul:eq("+order+") Li").eq(x).attr("mealNum")+"G流量附加包</span></div>")
			}
		}
	}
}
//微信支付
function goPay(){
		var username=$("#username").val();
		var openid = $("#openid").val();
		var num=$(".payNum input").val();
		var amount=$(".paySum span").text().trim();//用户实际支付的金额
		var siteConfigId=$(".payTypeList .on").attr("value");
		var priceNum=$(".payTypeList .on").attr("price_num");
		var addMealNum=$(".payTypeList .on").attr("mealNum");//优惠赠送的数量
		var addMealUnit=$(".payTypeList .on").attr("mealType");//优惠赠送的单位
		var mealType=mType;//购买套餐的类型 1 -- 时长套餐  , 2---流量套餐
		var siteId=$(".payTypeList .on").attr("siteId");
		var userAccount=$(".paySum span").text();//用户未使用余额抵消之前应付的金额
		var payWay="1";//1代表用户使用微信支付，2代表用户使用余额
		window.location.href= basePath+"/weChatPayOther/weixinPayOtherSite?nums="+num+"&amount="+amount+"&siteConfigId="+siteConfigId+"&userAccount="+userAccount+"&priceNum="+
		priceNum+"&siteId="+siteId+"&addMealNum="+addMealNum+"&addMealUnit="+addMealUnit+"&mealType="+mealType+"&openid="+openid+"&userName="+username+"&payWay="+payWay+"&v="+v;
		
}
//余额支付
function goBalancePay(){
	var username=$("#username").val();
	var openid = $("#openid").val();
	var num=$(".payNum input").val();
	var siteConfigId=$(".payTypeList .on").attr("value");
	var priceNum=$(".payTypeList .on").attr("price_num");
	var addMealNum=$(".payTypeList .on").attr("mealNum");//优惠赠送的数量
	var addMealUnit=$(".payTypeList .on").attr("mealType");//优惠赠送的单位
	var mealType=mType;//购买套餐的类型 1 -- 时长套餐  , 2---流量套餐
	var balance=$(".balance span").text().trim();//用户的余额
	var siteId=$(".payTypeList .on").attr("siteId");
	var userAccount=$(".paySum span").text().trim();//用户未使用余额抵消之前应付的金额	
	var amount=eval(balance -userAccount).toFixed(4);//用户实际支付的金额
	var payWay=2;//1代表用户使用微信支付，2代表用户使用余额
	if(amount<0){
		amount=eval( userAccount-balance).toFixed(4);
		$(".yeMask").css("display","block");
		$(".yes").click(function(){
			window.location.href= basePath+"/weChatPayOther/weixinPayOtherSite?nums="+num+"&amount="+amount+"&siteConfigId="+siteConfigId+
			"&priceNum="+priceNum+"&siteId="+siteId+"&addMealNum="+addMealNum+"&addMealUnit="+addMealUnit+"&mealType="+mealType+"&userAccount="+userAccount+
			"&usedAccount="+balance+"&openid="+openid+"&userName="+username+"&payWay="+payWay+"&v="+v;
			$(".yeMask").css("display","none");
		});
		$(".no").click(function(){
			$(".yeMask").css("display","none");
		})
	}else{
		window.location.href= basePath+"/weChatPayOther/weixinPayOtherSite?nums="+num+"&amount="+amount+"&siteConfigId="+siteConfigId+
		"&priceNum="+priceNum+"&siteId="+siteId+"&addMealNum="+addMealNum+"&addMealUnit="+addMealUnit+"&mealType="+mealType+"&userAccount="+userAccount+
		"&usedAccount="+balance+"&openid="+openid+"&userName="+username+"&payWay="+payWay+"&v="+v;
	}
}
//错误提示
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