$(function(){
	if($("#sa").val()==1){
		$(".tip-word").text("恭喜您中奖了");
	}else if($("#open").val()==1){
		$(".tip-word").text("很抱歉,上期未开奖");
	}
	
	for (var i = 0; i <$(".list-show ul").eq(1).find("li").length; i++) {
		$(".list-show ul").eq(1).find("li").eq(i).find("p").eq(0).addClass("bac"+(parseInt(i)+parseInt(1)));
	}
	for (var i = 0; i < $(".list-show ul").eq(2).find("li").length; i++) {
		$(".list-show ul").eq(2).find("li").eq(i).find("p").eq(0).addClass("bac"+(parseInt(i)+parseInt(1)));
	}
	for (var y = 0; y <$(".list-show li").length ; y++) {
		$(".list-show li").eq(y).find("p").eq(0).text(substrDemo($(".list-show li").eq(y).find("p").eq(0).text()))
	}
	
	$(".lp-cardShow").click(function(){
		$(".lp-cardShow").unbind("click");
		var n = $(".lp-cardShow").index(this);
		var n1 = Math.round(Math.random()*100);
		var arg=[5,10,15,20];
		if(n1>95){
			 $(".lp-cardShow1 span").eq(n).text("20积分");
			 arg.splice(3,1);
			 arg.splice(n, 0, 20);
			 $(".lp-cardShow1").eq(n).addClass("bond");
		}else if(n1<=95&&n1>85){
			 $(".lp-cardShow1 span").eq(n).text("15积分");
			 arg.splice(2,1);
			 arg.splice(n, 0, 15);
			 $(".lp-cardShow1").eq(n).addClass("bond");

		}else if(n1<=85&&n1>70){
			 $(".lp-cardShow1 span").eq(n).text("10积分");
			 arg.splice(1,1);
			 arg.splice(n, 0, 10);
			 $(".lp-cardShow1").eq(n).addClass("bond");

		}else if(n1<=70){
			 $(".lp-cardShow1 span").eq(n).text("5积分");
			 arg.splice(0,1);
			 arg.splice(n, 0, 5);
			 $(".lp-cardShow1").eq(n).addClass("bond");
		}
		for (var i = 0; i < arg.length; i++) {
			$(".lp-cardShow1 span").eq(i).text(arg[i]+"积分");
		}
		$.ajax({
			type:"post",
			url:ctx+"/weChatPublicNum/doDraw",
			data:{
				signid:$("#lsid").val(),
				grade:$(".bond >span").text().replace(/[^0-9]+/g, '').trim()
			},
			success:function(data){
				eval("data="+data);
				if(data.code==200){
					$(".lp-cardShow").addClass("jrStyle");
					$(".lp-cardShow1 img").eq(n).attr("src",ctx+"/wechat/img/has-lott.png");
					$(".lp-cardShow1").eq(n).css("color","#FE914A");
					$(".lp-cardShow1").addClass("jrStyle1");
					var jfNum = $(".lp-cardShow1 span").eq(n).text();
					$(".lp-word").css("display","none");		
					$(".lp-word1").html("恭喜您获得"+jfNum+",<br/>积分已累计到您的个人账户中！");
				}else if(data.code==201){
					$(".lp-word").text("您今天已经抽过奖喽!");
				}else if(data.code==202){
					$(".lp-word").text("您今天已经抽过奖喽!");
				}else if(data.code==203){
					$(".lp-word").text("网络出错,请稍后重试");
				}
			}
			
			
		})
	
	})
	
	function substrDemo(str){//接受需要处理的字符串
		return (str.substr(0,3)+'****'+str.substr(str.length-4,4));//直接返回处理好的字符串
	}
})
