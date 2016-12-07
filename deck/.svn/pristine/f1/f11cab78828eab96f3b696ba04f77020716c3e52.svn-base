window.onload = function() {
	var urlPhone = window.location.search;
	var username = urlPhone.split("=")[1];
	initAjax(username);
	loadPayType();
	calculate();
	goWeixinPay();
	$('.typeSelect').click(function() {
		$('.mask').css('display', 'block');
		$('.popup').animate({
			bottom : 0
		}, 400);
	});

	$('.close').click(function() {
		$('.popup').animate({
			bottom : '-50%'
		}, 400, function() {
			$('.mask').css('display', 'none');
		});
		$('.popup1').animate({
			bottom : '-50%'
		}, 400, function() {
			$('.mask').css('display', 'none');
		});
	});
	$('.mask').click(function() {
		$('.popup').animate({
			bottom : '-50%'
		}, 400, function() {
			$('.mask').css('display', 'none');
		});
		$('.popup1').animate({
			bottom : '-50%'
		}, 400, function() {
			$('.mask').css('display', 'none');
		});
	});
	$('.popup').click(function() {
		return false;
	});
	$('.popup1').click(function() {
		return false;
	});
};
// 加载缴费类型
function loadPayType() {
	calculate();
	$('.chargeList>li').click(function() {
		var n = $('.chargeList>li').index(this);
		$('.chargeList>li').removeClass('on').eq(n).addClass('on');
		var str = $('.chargeList>li').eq(n).text();
		var m = $('.chargeList>li').eq(n).attr('data-money');
		$('.typeSelect').attr('data-money', m);
		
		$(".typeSelect").attr("value",$('.chargeList>li').eq(n).attr('value'));
		$(".typeSelect").attr("charge_type",$('.chargeList>li').eq(n).attr('charge_type'));
		$(".typeSelect").attr("price_type",$('.chargeList>li').eq(n).attr('price_type'));
		$(".typeSelect").attr("price_num",$('.chargeList>li').eq(n).attr('price_num'));
		
		$('.popup').animate({
			bottom : '-50%'
		}, 400, function() {
			$('.mask').css('display', 'none');
			$('.type').html(str);
			calculate();
		});
	});

};
function disxiala(obj) {
	obj.toggle();
}

function xiala(n, obj, inobj, disobj) {
	var str = obj.eq(n).text();
	inobj.text(str);
	disobj.css('display', 'none');
}

function initAjax(username) {
	// body...
	$.ajax({
		type : 'post',
		url : basePath + '/weixinNumber/getSiteList',
		data : {
			username : username
		},
		success : function(data) {
			data = JSON.parse(data);
			if (data.code == 200) {
				$(".siteName").empty();
				var htmls = '';
				$('.phone').text(substrDemo(username, 3, 4));
				if (data.data.length == 1) {
					$(".siteName").html('<i></i>' + data.data[0].siteName);
					$(".siteName").attr("siteId", data.data[0].siteId);
					$(".siteName").attr("portalId", data.data[0].portalId);
					 
				} else {
					$(".siteName").attr("siteId", data.data[0].siteId);
					$(".siteName").attr("portalId", data.data[0].portalId);
					$(".siteName").html(
							'<i></i>' + data.data[0].siteName
									+ '<i class="xl"></i>');
					for (var i = 0; i < data.data.length; i++) {
						if (i == 0) {
							htmls += '<li portalId =' + data.data[0].portalId
									+ ' siteId=' + data.data[0].siteId
									+ ' class="on">' + data.data[0].siteName
									+ '<span></span></li>';
						} else {
							htmls += '<li portalId =' + data.data[i].portalId
									+ ' siteId=' + data.data[i].siteId + ' >'
									+ data.data[i].siteName
									+ '<span></span></li>';
						}
					}
					$('.chargeList1').html(htmls);
					$('.siteName').click(function() {
						$('.mask').css('display', 'block');
						$('.popup1').animate({
							bottom : 0
						}, 400);
					});
				}
				loadEvent();
				getPayType();
				calculate();
			}
		}
	});
}

// 加载场所操作事件
function loadEvent() {
	
	$('.chargeList1>li').click(function() {
		var n = $('.chargeList1>li').index(this);
		$('.chargeList1>li').removeClass('on').eq(n).addClass('on');
		$(".siteName").removeAttr("siteId");
		$(".siteName").removeAttr("portalId");
		var siteId = $('.chargeList1>li').eq(n).attr("siteId");
		var portalId = $('.chargeList1>li').eq(n).attr("portalId");
		$(".siteName").attr("siteId", siteId);
		$(".siteName").attr("portalId", portalId);
		var str = $('.chargeList1>li').eq(n).text();
		getPayType();
		$('.popup1').animate({
			bottom : '-50%'
		}, 400, function() {
			$('.mask').css('display', 'none');
			$('.siteName').html('<i></i>' + str + '<i class="xl"></i>');
		});
		calculate();
	});
}

// 获得缴费类型
function getPayType() {
	var siteId = $(".siteName").attr("siteId");
	var portalId = $(".siteName").attr("portalId");
	$.ajax({
		type : 'post',
		url : basePath + "/weixinNumber/goWeixinPayPage",
		data : {
			siteId : siteId,
			portalId : portalId
		},
		success : function(data) {
			data = JSON.parse(data);
			var htmls = '';
			if (data.code == 200) {
				$(".chargeList").empty();
				var infos = data.data.list;
				for (var i = 0; i < infos.length; i++) {
					var count = infos[i].price_type
					  if(count<=3){
						if (i == 0) {
							htmls += "<li class='on' price_num='"+infos[0].price_num+"' value='"+infos[0].id+"' charge_type='"+infos[0].charge_type+"' price_type='"+infos[0].price_type+"' data-money='"
							+ infos[0].unit_price + "'>" + infos[0].name
							+ "<span></span></li>";
						} else {
							htmls += "<li price_num='"+infos[i].price_num+"' value='"+infos[i].id+"' charge_type='"+infos[i].charge_type+"' price_type='"+infos[i].price_type+"'  data-money='" + infos[i].unit_price
							+ "'>" + infos[i].name + "<span></span></li>";
							
						}
					}
				}
				$(".chargeList").html(htmls);
				$(".typeSelect").attr("data-money", infos[0].unit_price);
				$(".typeSelect").attr("value",infos[0].id);
				$(".typeSelect").attr("charge_type",infos[0].charge_type);
				$(".typeSelect").attr("price_type",infos[0].price_type);
				$(".typeSelect").attr("price_num",infos[0].price_num);
				$('.type').html(infos[0].name);
				loadPayType();
			}
		}
	});

}

/*$('#num').keyup(function() {
	setTimeout(function(){
		calculate();
	},10)
	
});*/
$('#num').bind('input propertychange',function(){
	
	if(isNaN($('#num').val())){
		$('.sum span').text('0.00元');
		$('#goPay span').text("0.00");
	}else{
		calculate();
	} 
});



function calculate() {
	var a = $('.typeSelect').attr('data-money');
	var b = $('#num').val();
	if(b==""){
		$('.sum span').text('0.00元');
		$('#goPay span').text("0.00");
		
	}else{
		 
		var c = a * b;
		$('.sum span').text(c + '元');
		$('#goPay span').text(c);	
	}
	
}

//点击支付
function goWeixinPay(){
	
	$('#goPay').click(function(){
		var nums = $("#num").val();
		if(parseInt(nums)<=0){
			return false;
		}
		if(nums==""||nums==null){
			return false;
		}
		var amount = $("#goPay >span").text();
		var siteId = $(".siteName").attr("siteId");
		var portalId = $(".siteName").attr("portalId");
		var siteConfigId = $(".typeSelect").attr("value");
		var priceNum = $(".typeSelect").attr("price_num");
		if(siteConfigId==undefined){
			return false;
		}
		window.location.href=basePath+"/weixinNumber/getWeixinCode?nums="+nums+"&amount="+amount+"&siteId="+siteId+"&portalId="+portalId+"&siteConfigId="+siteConfigId+"&priceNum="+priceNum;
	});
}




/**
 * 处理字符串显示几位中间以‘*’号显示 只能用作回显银行卡号
 */
function substrDemo(str, n, l) {// 接受需要处理的字符串
	return (str.substr(0, n) + '****' + str.substr(str.length - l, l));// 直接返回处理好的字符串
}