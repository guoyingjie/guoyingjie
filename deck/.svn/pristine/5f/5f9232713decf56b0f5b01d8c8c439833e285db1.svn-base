/*
 * 全站公共脚本,基于jquery-1.9.1脚本库
*/
$(function(){
	var d= new Date();
	var curMonthDays = new Date(d.getFullYear(), (d.getMonth()+1), 0).getDate();//本月天数
	checkTan();
	$(".sl-bqd>.ts>i").text(d.getDate()-$(".sl-lxqd>.ts>i").text());
	/*去抽奖*/
	$(".qd-btn").click(function(){
		if($("#lsstate").val()==1){
			$(".mask").show();
			$(".opacity").show();
			$(".tip-warn").show();
			setTimeout(function(){
				$(".tip-warn").hide();
		        $(".mask").hide();
		        $(".opacity").hide();
			},3000);
		}else{
			window.location.href=ctx+"/weChatPublicNum/goDraw?usId="+$("#lsid").val();
		}				
	});
	$(".wl-right").click(function(){
		window.location.href=ctx+"/weChatPublicNum/goDrawList?userName="+$("#user").val();
	});
	/*签到规则*/
	$(".qd-rule").click(function(){
		window.location.href=ctx+"/weChatPublicNum/goRuleList";
	});
	
	$(".tip-warn").click(function(){
		$(".tip-warn").hide();
		$(".mask").hide();
		$(".opacity").hide();
	})
		document.documentElement.style.fontSize = document.documentElement.clientWidth / 7.5 + 'px';
		if ( $(".top-nav").length ){
			$(".top-nav").addClass("swiper-container");
			$(".top-nav ul:first").addClass("swiper-wrapper").find("li").addClass("swiper-slide");
			var auto_width = $(".top-nav").width();
			var auto_height = 500;
			var auto_num = $(".top-nav ul:first img").length;
			$(".top-nav").append("<div class='num'></div>")
			function topNav()
			{
				//获取屏幕的高度
				if ( $(".top-nav").parent().width() <= 321 )
				{
					auto_width = $(".top-nav").parent().width();
					auto_height = 500 * auto_width / 750;
				}else{
					auto_width = $(".top-nav").parent().width();
					auto_height = 500 * auto_width / 750;
				}
				$(".top-nav").width(auto_width).height(auto_height);
				$(".top-nav ul:first li").width(auto_width);
				$(".top-nav ul:first").width(auto_width*auto_num).height(auto_height);
			}
			 topNav();
			$(window).resize(function(){ topNav()});
			$('.top-nav').swiper({
				pagination: '.num',
				mode:'horizontal',
				loop: true,
				autoplay:5000
			}) 
		}
		$(".check-calendar ul").hide();
		$(".check-calendar ul").eq(0).show();
		$(".shou-zhan").click(function(){
			var imgS = $(".shou-zhan").find('img').attr("src");
			var imgS1 = $(".shou-zhan").find('img').attr("src1");
			var imgS2 = $(".shou-zhan").find('img').attr("src2");
			if(imgS == imgS1){
				$(".text-show").text("收起");
				$(".shou-zhan").find('img').attr("src",imgS2);
				$(".check-calendar ul").fadeIn();
			}else{
				$(".text-show").text("展开");
				$(".shou-zhan").find('img').attr("src",imgS1);
				$(".check-calendar ul").hide();
				$(".check-calendar ul").eq(0).show();
			}
		})
		//签到显示背景图
//    
//		$('.check-calendar ul li p').addClass("cc-show");
//		$('.check-calendar ul li p').addClass("cc-show1");
		//签到获取当前月份天数
		var d = new Date();
		//d.getMonth()+1代表下个月，月份索引从0开始，即当前月为6月时，getMonth()返回值为5，创建日期时同理
		//此处构造的日期为下个月的第0天，天数索引从1开始，第0天即代表上个月的最后一天
		var str = "" + (d.getMonth()+1) + "-"+d.getDate();//当前日期
		var signNum=$("#ls").val();
		var sig=[];	
		for (var int = 0; int < signNum.split(",").length-1; int++) {
			sig[sig.length]=signNum.split(",")[int];
		}
		for(var n=0;n<=d.getDate()-1;n++){
			
			$('.check-calendar ul li p').eq(n).addClass("cc-show1");//补签
			
		}
		for (var int2 = 0; int2 < sig.length; int2++) {
			$('.check-calendar ul li p').eq(sig[int2]-1).removeClass("cc-show1").addClass("cc-show");//已签到
		}
		$(".cc-show1").unbind("click")
		$(".cc-show1").click(function(){
			window.location.href=ctx+"/weChatPublicNum/retroactive?userName="+$("#user").val()+"&times="+$(this).prev().text()+"&siteId="+$("#siteid").val(); 
		})
		var curMonth = d.getMonth()+1;//当前月份
		var curMonthDays = new Date(d.getFullYear(), (d.getMonth()+1), 0).getDate();//本月天数
		var cSpan = $(".check-calendar ul li span");//获取span个数，共31个
		var cLi = $(".check-calendar ul li");//获取li个数，共31个
		var hideDays = 31-curMonthDays;//判断与当前月天数相差几天
		if(hideDays > 0){
			for(var j=0;j<hideDays;j++){
				cLi.eq(30-j).hide();//隐藏超出本月多余的天数
			}
		}
		for (var i=0;i <= curMonthDays-1;i++) {
			if(i<9){
				cSpan.eq(i).text(curMonth+"-0"+(i+1)) ;
			}else{
				cSpan.eq(i).text(curMonth+"-"+(i+1)) ;
			}
		}
		
		//弹出层操作
		//默认笑脸恭喜获得抽奖资格
		$(".tb-btn").click(function(){
			$(".tip-box").hide();
			$(".mask").hide();
		})
			
	function checkTan(){
			$.ajax({
				type:"post",
				url:ctx+"/weChatPublicNum/checkDraw",
				data:{
					userName:$("#user").val()
				},
				success:function(data){
					eval("data="+data);
					if(data.code==203){
						$(".mask").show();
						$(".opacity").show();
						$(".tip-box").show();
						//未中奖弹框
						$(".tip-box img").attr("src",ctx+"/wechat/img/ku.png");
						$(".tipb-wpod").html("很遗憾，您本次未中奖<br/>下次继续努力！");
					}else if(data.code==204){
						$(".mask").show();
						$(".opacity").show();
						$(".tip-box").show();
					}
				}
				
			})
	}	
		
	})
  