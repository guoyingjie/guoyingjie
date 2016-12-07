var tele = /^(((13[0-9]{1})|(14[4-7]{1})|(15[0-9]{1})|(170)|(173)|(17[1-8]{1})|(18[0-9]{1}))+\d{8})$/;// 手机号码


window.onload=function() {
	//imgAjax();
	//start
	$('.primary').click(function(){
		$('.weui_dialog_alert').css('display','none');
	});
	
	$('.close').click(function(){
		$('.popup').animate({bottom:'-50%'},400,function(){
			$('.mask').css('display','none');
		});
	});
	$('.mask').click(function(){
		$('.popup').animate({bottom:'-50%'},400,function(){
			$('.mask').css('display','none');
		});
	});
	$('.popup').click(function(){
		return false;
	});
	//end
	if(window.location.search!='?state=0'){
		$('.title').text('充值上网时长');
		$('#phone').attr('placeholder','请输入需充值的手机号');
		$('.btn').text('开始充值');
		$('.btn').click(function(){
			if($("#phone").val()==""){
				$('.weui_dialog_title').text('错误');
				$('.weui_dialog_bd').text('请输入手机号');
				$('.weui_dialog_alert').css('display','block');
				return false;
			}
			if(!tele.test($("#phone").val())){
				$('.weui_dialog_title').text('错误');
				$('.weui_dialog_bd').text('请输入正确的手机号');
				$('.weui_dialog_alert').css('display','block');
				$('input').val('');
				return false;
			}
			var phone = $('#phone').val();
			payAjax(phone);
		});
	}else{
		$('.btn').click(function(){
			if($("#phone").val()==""){
				$('.weui_dialog_title').text('错误');
				$('.weui_dialog_bd').text('请输入手机号');
				$('.weui_dialog_alert').css('display','block');
				return false;
			}
			if(!tele.test($("#phone").val())){
				$('.weui_dialog_title').text('错误');
				$('.weui_dialog_bd').text('请输入正确的手机号');
				$('.weui_dialog_alert').css('display','block');
				$('input').val('');
				return false;
			}
			
			//渲染数据
			subAjax();
			
		});
		$('.btns button').eq(0).click(function(){
			$('.content').css('display','block');
			$('.quireResult').css('display','none');
		});
		$('.btns button').eq(1).click(function(){
			var phone = $('#phone').val();
			payAjax(phone);
		});
	}
	
};
 
//缴费
function payAjax(phone){
	$.ajax({
		type: 'post',
		url: basePath+'/weixinNumber/getSiteList',
		data: {
			username:phone
		},
		success: function(data){
			data=JSON.parse(data);
			if(data.code==200){
				window.location.href=basePath+'/weixinNumber/gotopay?username='+phone;
			}else{
				$('.weui_dialog_title').text('错误');
				$('.weui_dialog_bd').text(data.msg);
				$('.weui_dialog_alert').css('display','block');
				$('input').val('');
			}
		}
	});
}

function subAjax(){
	$('#loadingToast').css('display','block');
	var username = $("#phone").val();
	$(".chargeList").empty();
	$.ajax({
		type: 'post',
		url:  basePath+'/weixinNumber/findExpireTime',
		data: {
			username: username
		},
		success: function(data){
			data=JSON.parse(data);
			if(data.code==200){
				$(".chargeList").empty();
				var htmls = '';
	
				if(data.data[0].time=="已到期"||data.data[0].time.indexOf("天")==-1){
					$(".time").css('color','red');
				}else{
					$(".time").css('color','#44cb32');
				}
				$(".time").html(data.data[0].time);
				
				if(data.data.length<2){
					$('#loadingToast').css('display','none');
					$('.siteName').html('<i></i>'+data.data[0].siteName);
					$('.siteName').attr("siteId",data.data[0].siteId);
					$('.siteName').attr("portalId",data.data[0].portalId);
					$('.mask').css("display","none");
					$('.siteName').unbind("click");
				}else{
					$('#loadingToast').css('display','none');
					$('.siteName').html('<i></i>'+data.data[0].siteName+'<i class="xl"></i>');
					$('.siteName').unbind("click");
					$('.siteName').click(function(){
						if(data.data.length<2){
							$('.mask').css("display","none");
						}else{
							$('.mask').css('display','block');
							$('.popup').animate({bottom:0},400);
						}
					});
					for(var i=0;i<data.data.length;i++){
						if(i == 0){
							htmls += '<li portalId="'+data.data[i].portalId+'" siteId="'+data.data[i].siteId+'" data-time="'+data.data[i].time+'" class="on">'+data.data[i].siteName+'<span></span></li>';
						}else{
							htmls += '<li portalId="'+data.data[i].portalId+'" siteId="'+data.data[i].siteId+'" data-time="'+data.data[i].time+'">'+data.data[i].siteName+'<span></span></li>';
						}
					}
					$('.chargeList').html(htmls);
					
						$('.chargeList>li').unbind("click");
						$('.chargeList>li').click(function(){
							var n=$('.chargeList>li').index(this);
							$('.chargeList>li').removeClass('on').eq(n).addClass('on');
							var str=$('.chargeList>li').eq(n).text();
							var m = $('.chargeList>li').eq(n).attr('data-time');
							$('.siteName').attr('portalId',$('.chargeList>li').eq(n).attr('portalId'));
							$('.siteName').attr('siteId',$('.chargeList>li').eq(n).attr('siteId'));
							//$('.siteName').attr('surplusFlow',$('.chargeList>li').eq(n).attr('surplusFlow'));
							 
								$('.popup').animate({bottom:'-50%'},400,function(){
									$('.mask').css('display','none');
									$('.siteName').html('<i></i>'+str+'<i class="xl"></i>');
									if(m=="已到期"||m.indexOf("天")==-1){
										$(".time").css('color','red');
									}else{
										$(".time").css('color','#44cb32');
									}
									$(".time").html(m);
								});
							 
						});
				}
				$('.content').css('display','none');
				$('.quireResult').css('display','block');
			}else if(data.code==202){
				$('#loadingToast').css('display','none');
				$('.weui_dialog_title').text('错误');
				$('.weui_dialog_bd').text(data.msg);
				$('.weui_dialog_alert').css('display','block');
			}else{
				$('#loadingToast').css('display','none');
				$('.weui_dialog_title').text('错误');
				$('.weui_dialog_bd').text(data.msg);
				$('.weui_dialog_alert').css('display','block');
				 
			}
		}
	});
}
