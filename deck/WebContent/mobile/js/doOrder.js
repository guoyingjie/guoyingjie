var submitFlag=false;
$(function() {
	into();
	//默认价格
	defaultMoney();
	$(".zffs li").click(function(){

		var i=$(".zffs li").index(this);
		$(".zffs li").removeClass('on').eq(i).addClass('on');
		$(".zffs li span").removeClass('on').eq(i).addClass('on');
	});
	
	
	//setPriceType();
});
function into() {// 初始化绑定事件
//	$("#priceConfig").change(function() { // 场所价格配置
//		setPriceType();
//		sumMoney();
//	});
	$(".nr").click(function(){
		var s=$(".nr").index(this);
		$(".nr").removeClass("on").eq(s).addClass("on");
		var str=$(".nr").eq(s).text();
		//$(".gb").text(str);
		$("#priceConfig").val($(".nr").eq(s).attr("value"));
		$("#priceNum").val($(".nr").eq(s).attr("price_num"));
		$("#priceName").val($(".nr").eq(s).html().trim());
//		var fh=jisuan(s);
//		$(".nm").text(fh);
		setPriceType();
		sumMoney();
	});
	
	$("#nums").keyup(function() {
		sumMoney();
	});
	
}
function defaultMoney(){

	$(".nr").removeClass("on").eq(0).addClass("on");
	var str=$(".nr").eq(0).text();
	//$(".gb").text(str);
	$("#priceConfig").val($(".nr").eq(0).attr("value"));
	$("#priceNum").val($(".nr").eq(0).attr("price_num"));
	$("#priceName").val($(".nr").eq(0).text().trim());
//	var fh=jisuan(s);
//	$(".nm").text(fh);
	setPriceType();
	sumMoney();
	
}

// 计算价格
function sumMoney() {
//	var price = $("#priceConfig").find("option:selected").attr("prices");
	var price =$(".on").attr("prices");
	var re = /\D/g;
	var nums = $("#nums").val();
	var sumMoney="";
	if (nums == "" || nums == null) {
		sumMoney=0;
		$(".moneyInfo").html(sumMoney.toFixed(2) + "元");
		$("#amount").val(sumMoney.toFixed(2));
		submitFlag=false;
		return;
	}
	if (re.test(nums)) {
//		$(".btn").attr("submit","button");
		$(".btn").css({"background-color": "red"});
		$(".btn").val("请输入整数");
		
		submitFlag=false;
		return;
	}
	var sumMoney = price * nums;
	$(".moneyInfo").html(sumMoney.toFixed(2) + "元");
	$("#amount").val(sumMoney.toFixed(2));
	submitFlag=true;
//	$(".btn").attr("button","submit");
	$(".btn").removeAttr("style");
	$(".btn").val("立即支付");
}
function setPriceType() { // 设置续费类型
//	var type = $("#priceConfig").val();
//	var value = "";
//	if (type == 0) {
//		value = "小时";
//	}
//	if (type == 1) {
//		value = "天";
//	}
//	if (type == 2) {
//		value = "月";
//	}
//	$("#pconfig").html(value);
//	var price = $("#priceConfig").find("option:selected").attr("prices");
	var price =$(".on").attr("prices");
	$("#unitPrice").html((price*1).toFixed(2)+"&nbsp;元");//(price*1)是为了转换格式，懒得用方法转了
}

function submitCheck(){
	var nums = $("#nums").val();
	if(nums==0){
		submitFlag=false;
	}
	return submitFlag;
}
function quickpay(){
	var amount =$("#amount").prop("value");//总金额
	var priceConfig=$("#priceConfig").val();//场所收费配置Id
	var num = $("#nums").val();//购买数量
	var price_num=$("#priceNum").val();//购买套餐类型
	var price_name=$("#priceName").val();//购买名称
		window.location.href= basePath+"/quickPayment/clickPayment?nums="+num+"&amount="+amount+"&priceConfig="+priceConfig+"&price_num="+price_num+"&price_name="+price_name;
};

function goPay(){
	if(submitCheck()){
		if($("#alipayLi").attr("class")){
			$("#login").submit();
		}else{
			quickpay();
		}
	}
}