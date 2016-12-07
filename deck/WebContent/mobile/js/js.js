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
		}
		sp.innerHTML=spt;
	},1000);
}
$(function(){
	var i=0;
	var w=$(".main li").width();//获取图片宽度
	//轮播图代码
	var timer=setInterval(function(){
		i++;
		if(i==1){
			$(".main").animate({left:-w*i+"px"},500);
			$('.ctrl li').removeClass("on").eq(1).addClass("on");
			i=-1;
		}else if(i==0){
			$(".main").animate({left:-w*i+"px"},500);
			$('.ctrl li').removeClass("on").eq(0).addClass("on");
		}
	},2500);
	//轮播图代码
	//关闭轮播图按钮代码
	$('.gb').click(function(){
		$('.banner').hide();
		clearInterval(timer);
		$('form').animate({top:"15%"});
	})
	//返回按钮代码
	$('.pic').click(function(){
		window.location.href="index.html";
	})
	//性别按钮效果代码
	$(".xb input").click(function(){
		var xp=$(".xb input").index(this);
		$(".xb input").removeClass("on").eq(xp).addClass("on");
	})
	
})
window.onload=function(){
	var txt=document.getElementById("num");//获取手机号input框的ID
	var mm=document.getElementById("pwd");//获取密码input框的ID
	var ym=document.getElementById("ma");//获取验证码input框的ID
	var sp=document.getElementById("xs");
	var sj=document.getElementById("sj");//获取手机号错误后要显示的提示框的ID
	var mi=document.getElementById("mi");//获取密码错误后要显示的提示框的ID
	var aa=document.getElementById("tu");//获取显示隐藏密码的图片ID
	var b=document.getElementById('bg');//获取背景图片的ID
	var zyz=document.getElementById('zyz');//获取验证码错误后要显示的提示框的ID
	//阻止浏览器默认动作
	b.onclick=function(){
		var  preventDefault  = function(e) {
			e = e || window.event;
			if(e.preventDefault) {
				e.preventDefault();
			}else{
				e.returnValue = false;
			}
		}
	}
	//单击显示/隐藏图片后的动作
	aa.onclick=function(){
		if(mm.type=="password"){
			mm.type="text";
			aa.src=imgPath+"/mm.png";
			var str=aa.src;
		}else if(mm.type=="text"){
			mm.type="password";
			aa.src=imgPath+"/am.png";
		}
	}
	//手机号码文本框获取光标后的动作
	txt.onfocus=function(){
		if(txt.value=="请填写中国大陆手机号"){
			sj.style.display="none";
			txt.type="number"
			txt.value="";
		}
	}
	//手机号码文本框失去光标后的动作
	txt.onblur=function(){
		if(txt.value==""){
			txt.type="text";
			txt.value="请填写中国大陆手机号";
		}
		if(checkMobile(txt.value)){
			sj.style.display="none";
		}else{
			sj.style.display="block";
		}
	}
	//密码框获取光标后的动作
	mm.onfocus=function(){
		if(mm.value=="6-14位，支持数字、密码、字符组合"){
			mi.style.display="none";
			aa.src=imgPath+"/am.png";
			mm.value="";
			mm.type="password";
		}
	}
	//密码框失去光标后的动作
	mm.onblur=function(){
		if(mm.value==""){
			aa.src=imgPath+"/mm.png";
			mm.type="text";
			mm.value="6-14位，支持数字、密码、字符组合";
		}else{
			mm.type="password";
			mm.value=pas.value;
		}
		if(checkUser(mm.value)){
			mi.style.display="none";
		}else{
			mi.style.display="block";
		}
	}
	//验证码获取光标后的动作
	ym.onfocus=function(){
		if(ym.value=="验证码"){
			zyz.style.display="none";
			ym.value="";
		}
	}
	//验证码失去光标后的动作
	ym.onblur=function(){
		if(ym.value==""){
			zyz.style.display="block"
			ym.value="验证码";
		}
		if(ym.value!=1234){
			sp.style.display='block';
		}else if(ym.value==1234){
			sp.style.display='none';
		}
	}
}

$(function(){
	//单击性别后的效果
	$('#nan').click(function(){
		$('#nan').addClass("on");
		$('#nv').removeClass("on");
	})
	$('#nv').click(function(){
		$('#nv').addClass("on");
		$('#nan').removeClass("on");
	})
	//单击性别后的效果
	$(".btn1").click(function(){
		window.onpen('zhuce.html');
	})
	$(".nr").click(function(){
		var s=$(".nr").index(this);
		$(".nr").removeClass("on").eq(s).addClass("on");
		var str=$(".nr").eq(s).text();
		$(".gb").text(str);
		var fh=jisuan(s);
		$(".nm").text(fh);
	})
	$(".sc input").change(function(){
		var gbstr=$(".gb").text();
		if(gbstr=="时"){
			x=0;
		}else if(gbstr=="日"){
			x=1;
		}else if(gbstr=="月"){
			x=2;
		}else if(gbstr=="年"){
			x=3;
		}
		var fh1=jisuan(x);
		$(".nm").text(fh1);
	})
	$(".zffs li").click(function(){
		var i=$(".zffs li").index(this);
		$(".zffs li").removeClass('on').eq(i).addClass('on');
		$(".zffs li span").removeClass('on').eq(i).addClass('on');
	})
//	$(".pic").click(function(){
//		window.location.href="zhuce.html";
//	})
})
function jisuan(n){
	var dw=[1,7,20,240];
	var sr=$(".sc input").val();
	var ji=dw[n]*sr;
	return ji;
}
//正则表达式
//判断手机号
function checkMobile(str) {
    var re = /^1\d{10}$/;
    if (re.test(str)) {
        return true;
    } else {
        return false;
    }
}
//判断密码字母、数字、下划线组成，字母开头，4-16位
function checkUser(str){ 
var re = /^[a-zA-z]\w{3,15}$/;
    if(re.test(str)){
        return true;
    }else{
        return false;
    }          
}