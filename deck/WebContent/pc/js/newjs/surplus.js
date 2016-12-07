window.onload=function(){
	
	var ratioFlow = $("#ratioFlow").val(); 
	var ratioTime = $("#ratioTime").val();
	if(ratioTime==''){
		ratioTime =0
		//ratioTime= 1;
	}
	if(ratioFlow==""){
		ratioFlow = 0;
	}
	message();
	if(ratioFlow>1){
		ratioFlow =1.00;
	}
    if(ratioTime>1){
    	ratioTime=1.00;
	}
    if(ratioFlow<0){
		ratioFlow =0;
	}
    if(ratioTime<0){
    	ratioTime=0;
	}
    
	changeColor(ratioTime,ratioFlow);//第一个代表时间，第二个代表流量
	//点击去上网
	$("#goInternal").click(function(){
//		window.location.href="http://www.gonet.cc/m/";
		window.location.href="https://www.2345.com/?kdf00001";
	});
	$("#goToPay").click(function(){
		//TODO 充值页面
		window.location.href=basePath+"/w/toPay";
	});
	$("#toPayrecord").click(function(){
		//TODO 缴费记录页面
		window.location.href=basePath+"/w/toPayRecord";
	});
	
	$(".title").click(function(){
		//TODO 个人中心页面
		window.location.href=basePath+"/w/goToPerson?userName="+$('#username').val()+"&siteId="+$('#siteId').val();
	});
	
	
	var imgSrc = $('#phoneurl').val();
	if(imgSrc){
		$('.photo').css('background','#fff url(http://oss.kdfwifi.net/user_pic/'+imgSrc+') no-repeat center');
		$('.photo').css('background-size','cover');
	}
	
	//未发现这个方法有卵用
//	$("#personGo").click(function(){
//		var userName= $("#username").val();
//		var siteId = $("#siteId").val();
//	    window.location.href= basePath+"/wifidog/goToPerson?userName="+userName+"&siteId="+siteId;
//	});
	
	$("#message").click(function(){
		window.location.href=basePath+"/ProtalUserManage/goToMessagePage?handleType=1";
	});
	
//	changeColor(.24,.12);//第一个代表时间，第二个代表流量
	
	 setInterval("msgload()",1000*60*10);
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

//判断是否有信息
function message(){
	var mess = $("#mess").val();
	if(mess==0){
		$("#message").removeClass("on");
	}else{
		$("#message").addClass("on");
	} 
	var username = $("#username").val();
	var name = username.substring(0,3); 
	var end = username.substring(7,username.length);
	var allname = name+"****"+end;
	$('.phone').html(allname);
}

function boxMove(n){
	tiao(n+2)
	$('.box').animate({left:(-277*(n+1))+'px'},400,function(){
		$('.content').addClass('big');
		setTimeout(function(){
			$('.content').removeClass('small');
			$('.content').removeClass('big');
		},400);
	});
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
function colors(n){
	var red = '#ff464f';
	var yello = '#ffba4d';
	var green = '#30d9b3';
	var blue = '#4dbdef';
	if(n<=0.2){
		return red;
	}else if(n>0.2&&n<=0.5){
		return yello;
	}else if(n>0.5&&n<=0.8){
		return green;
	}else{
		return blue;
	}
}
function changeColor(n,m){
	$('.time .zong').css('border-color',colors(n));
	$('.time .plan').css('background',colors(n));
	$('.time .plan').css('width',n*100+'%');
	$('.gprs .zong').css('border-color',colors(m));
	$('.gprs .plan').css('background',colors(m));
	$('.gprs .plan').css('width',m*100+'%');
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
function msgload(){
	$.post(basePath+"/w/timeMessage",
	          function(data){
		if(data.success){
	         var mess = data.mess;
	      	if(mess==0){
	      		$("#message").removeClass("on");
	      	}else{
	      		$("#message").addClass("on");
	      	} }
	          }, "json");
}