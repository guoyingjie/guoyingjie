// JavaScript Document
window.onload=function(){
	var tys=document.getElementById('ty-djs');//获取倒计时的ID
	var sp=tys.getElementsByTagName('span')[0];//获取倒计时中的span
	var spt=sp.innerHTML;//获取倒计时中span内容
	//让span里的内容每秒减一
	var timer=setInterval(function(){
		spt--;
		if(spt<=0){
			clearInterval(timer);
				window.location.href=ctx+"/w/goToAuthPage";
		}
		sp.innerHTML=spt;
	},1000);
};

$(function(){
	
	//返回按钮代码
	$('.pic').click(function(){
		//window.location.href="index.html";
		history.go(-1);
	});
	//性别按钮效果代码
	$(".xb input").click(function(){
		var xp=$(".xb input").index(this);
		$(".xb input").removeClass("on").eq(xp).addClass("on");
	});
	$("#ty-lj").click(function(){
			window.location.href=ctx+"/w/goToAuthPage";
	});
});
