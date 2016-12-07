$(function(){
	getLottery();
});
function getLottery(){
	$(".lottery-list li").remove();
	var username = $("#username").val();
	$.ajax({
		type : 'post',
		url : ctx + '/ProtalUserManage/getLotterPage',
		data : {
			userName : username,
		},
		success : function(data) {
			data = JSON.parse(data);
			var htmls = '';
			if (data.code == 200) {
				for (var i = 0; i < data.data.length; i++) {
					htmls += "<li class='ll-intro'>"+
							 "<p class='left-pic'>" +
									"<img src='"+imgPath+"/newimg/cai3.png'></p>"+
							 "<div class='right-intro'>"+
								 "<p>赠送时间：<span>"+data.data[i].create_time+"</span></p>"+
								 "<p>开奖时间：<span class='ti-time'>依福彩中心开奖日期为准</span></p>"+
							"</div>"	+
							 "<p class='ll-mark'><img src='"+imgPath+"/newimg/cai4.png'></p>"+
							 "<p class='click-to' value="+data.data[i].id+"></p> </li>";
				}
				$('.lottery-list').html(htmls);
				$(".click-to").unbind("click");
				$(".click-to").click(function(){
					var n=$(".click-to").index(this);
					getJumpLottery(n);
				});
			} else if (data.code == 201) {
				msg(0,"暂无数据");
			} 
		}
	});
}
function jump(){
	window.location.href="http://www.gonet.cc";
}
//跳转到彩票页面
function getJumpLottery(n){
	var username = $("#username").val();
	var id=$(".click-to").eq(n).attr("value");
	window.location.href=ctx+"/ProtalUserManage/getLottery?userName="+username+"&message="+id;
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
			},1000);
		});
	}
}
