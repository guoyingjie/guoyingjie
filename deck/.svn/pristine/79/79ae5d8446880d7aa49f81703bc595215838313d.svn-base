window.onload=function(){
	
	xiaoxi();
	
}
function xiaoxi(){
	$('.new').remove();
	var username = $("#username").val();
	$.ajax({
		type: 'post',
		url: ctx + '/ProtalUserManage/MessageUser',
		data : {
			userName : username,
			mealType : 0
		},
		success: function(data){
			data=JSON.parse(data);
			if(data.code==200){
				var htmls = '';
				for (var i = 0; i < data.data.length; i++) {
					htmls += "<div class='new'>"
							+ "<span class='logo'></span>"
							+ "<div class='msmqBox'>"
							+ "<span class='jj'></span>"
							+ "<p class='title'><span class='xt'>系统消息：</span>"
							+ data.data[i].create_time + "</p>"
							+ "<p class='nr'>"
							+ data.data[i].content + "</p>" ;
							if(data.data[i].state==3){
								htmls+="<button class='receive-btn' sitemId="+data.data[i].sitemId+" usermId="+data.data[i].usermId+">点击领取</button>";
							}
							htmls+="</div>"
									+ "</div>";
				}
				$('.noMsg').before(htmls);
				$(".receive-btn").unbind("click");
				$(".receive-btn").click(function(){
					var n=$(".receive-btn").index(this);
					console.log(n);
					getLottery(username,n);
				});
				
			}else if(data.code==201){
				$('.noMsg').css('display','block');
			}else{
				msg(0, "您访问的页面已过期");
				setTimeout("jump()", 2000);
			}
		}
	});
}
//点击获取彩票
function getLottery(username,n){
	var sitemessage=$(".receive-btn").eq(n).attr("sitemId");
	var usermessage=$(".receive-btn").eq(n).attr("usermId");
	window.location.href=deckurl+"deck/w/lottery?username="+username+"&usermsg="+usermessage+","+sitemessage;
}
function jump(){
	window.loaction.href="www.gonet.cc";
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
			},2900);
		});
	}
}