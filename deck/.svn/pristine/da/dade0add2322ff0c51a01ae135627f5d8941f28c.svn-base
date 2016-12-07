window.onload=function(){
	
	//xiaoxi();
	/*if(true){
		$('.msmq').remove();
		$('.noMsg').css('display','block');
	}*/
	getMessage();
}
function getMessage(){
	$(".msmq").remove();
	var username = $("#username").val();
	$.ajax({
		type : 'post',
		url : ctx + '/ProtalUserManage/MessageUser',
		data : {
			userName : username,
			mealType : 0
		},
		success : function(data) {
			data = JSON.parse(data);
			var htmls = '';
			if (data.code == 200) {
				for (var i = 0; i < data.data.length; i++) {
					htmls += "<div class='msmq'>"
							+ "<span class='logo'></span>"
							+ "<div class='msmqBox'>"
							+ "<span class='jj'></span>"
							+ "<p class='title'><span class='xt'>系统消息：</span>"
							+ getDate(data.data[i].create_time) + "</p>"
							+ "<p class='nr' value=" + data.data[i].id + ">"
							+ data.data[i].content + "</p>" ;
							if(data.data[i].state==3){
								htmls+="<button class='receive-btn' sitemId="+data.data[i].sitemId+" usermId="+data.data[i].usermId+">点击领取</button>";
							}
							htmls+="</div>"
									+ "</div>";
						
				}
				$('.altMask').before(htmls);
				$(".receive-btn").unbind("click");
				$(".receive-btn").click(function(){
					var n=$(".receive-btn").index(this);
					console.log(n);
					getLottery(username,n);
				});
			} else if (data.code == 201) {
				$('.noMsg').css('display', 'block');
			} else {
				msg(0, "您访问的页面已过期");
				setTimeout("jump()", 2000);
			}
		}
	});
}

function jump(){
	window.location.href="http://www.gonet.cc";
}
//点击获取彩票
function getLottery(username,n){
	var sitemessage=$(".receive-btn").eq(n).attr("sitemId");
	var usermessage=$(".receive-btn").eq(n).attr("usermId");
	window.location.href=deckurl+"deck/w/lottery?username="+username+"&usermsg="+usermessage+","+sitemessage;
}
function tiao(n){
	if(n==1){
		$('.three').removeClass('on');
		$('.two').removeClass('on');
		$('.tiao').animate({width:'15%'},200,function(){
			$('.one').addClass('on');
		});
	}else if(n==2){
		$('.three').removeClass('on');
		$('.tiao').animate({width:'50%'},300,function(){
			$('.two').addClass('on');
		});
	}else if(n==3){
		$('.two').addClass('on');
		$('.tiao').animate({width:'100%'},300,function(){
			$('.three').addClass('on');
		});
	}
}
function boxMove(n){
	//tiao(n+2)
	/*$('.box').animate({left:(-277*(n+1))+'px'},400,function(){
		$('.content').addClass('big');
		setTimeout(function(){
			$('.content').removeClass('small');
			$('.content').removeClass('big');
		},400);
	});*/
	$('.content').css('display','none');
	$('.content').eq(n+1).css('display','block');
}
function coundDown(obj,n){
	obj.css('background','#ccc');
	obj.attr('disabled',true);
	obj.text(n+'秒后重新获取');
	var timer = setInterval(function(){
		n--;
		if(n==0){
			clearInterval(timer);
			obj.css('background','#47a4dc');
			obj.attr('disabled',false);
			obj.text('获取验证码');
		}else{
			obj.text(n+'秒后重新获取');
		}
	},1000);
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



/**
 * 处理时间方法
 */
function getDate(time){
	return time.substring(5,16);
}
