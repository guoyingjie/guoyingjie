$(function(){
	getLottery();
	
	$(".last-btn").click(function(){
		
		window.location.href=ctx+"/w/jumpMethod";
	});
	$(".tm-help").click(function(){
		
		window.location.href=ctx+"/w/jumpHelpMethod";
	});
	$('.iknow').click(function(){//我知道了按钮事件
		$('.msk').css('display','none');

	});
});
function getLottery(){
	$(".lottery-list li").remove();
	var username = $("#username").val();
	$.ajax({
		type : 'post',
		url : ctx + '/ProtalUserManage/getLoydetail',
		data : {
			userName : username,
		},
		success : function(data) {
			data = JSON.parse(data);
			var htmls = '';
			if (data.code == 200) {
				htmls += "<span class='red loy_num on'>"+data.data[0].content.split(",")[0].substring(5, 7)+"</span>"+
						"<span class='red loy_num on'>"+data.data[0].content.split(",")[0].substring(7, 9)+"</span>"+
						"<span class='red loy_num on'>"+data.data[0].content.split(",")[0].substring(9, 11)+"</span>"+
						"<span class='red loy_num on'>"+data.data[0].content.split(",")[0].substring(11, 13)+"</span>"+
						"<span class='red loy_num on'>"+data.data[0].content.split(",")[0].substring(13, 15)+"</span>"+
						"<span class='red loy_num on'>"+data.data[0].content.split(",")[0].substring(15, 17)+"</span>"+
						"<span class='blue loy_num on'>"+data.data[0].content.split(",")[0].substring(19,21)+"</span>";

				$('.my_num').html(htmls);
				
				$(".time-item").html(data.data[0].content.split(",")[1]);
				$(".zs-time span").html("("+data.data[0].create_time+")");
			} else if (data.code == 201) {
				msg(0,"暂无数据");
			} 
		}
	});
}
function jump(){
	window.location.href="http://www.gonet.cc";
}
function msg(code,str){
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


function iknow(str){
	$('.msk p').text(str);
	$('.msk').css('display','block');
}