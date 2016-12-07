// JavaScript Document
window.onload=function(){
	var txt=document.getElementById("num");
	var mm=document.getElementById("pwd");
	var ym=document.getElementById("yz");
	var sp=document.getElementById("xs");
	var sj=document.getElementById("sj");
	var mi=document.getElementById("mi");
	txt.onfocus=function(){
		if(txt.value=="请输入大陆手机号"){
			txt.value="";
		}
	}
	txt.onblur=function(){
		if(txt.value==""){
			txt.value="请输入大陆手机号";
		}
		if(checkMobile(txt.value)){
			sj.style.display="none";
		}else{
			sj.style.display="block";
		}
	}
	mm.onfocus=function(){
		if(mm.value=="6-14位，支持数字、字母、符号组合"){
			mm.type="password";
			mm.value="";
		}
	}
	mm.onblur=function(){
		if(mm.value==""){
			mm.type="text";
			mm.value="6-14位，支持数字、字母、符号组合";
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
	ym.onfocus=function(){
		if(ym.value=="请输入验证码"){
			ym.value="";
		}
	}
	ym.onblur=function(){
		if(ym.value==""){
			ym.value="请输入验证码";
		}
		if(ym.value!=1234){
			sp.style.display='block';
		}else if(ym.value==1234){
			sp.style.display='none';
		}
	}
}
$(function(){
	var s=0;
	$('#nan').click(function(){
		$('#nan').addClass("on");
		$('#nv').removeClass("on");
	})
	$('#nv').click(function(){
		$('#nv').addClass("on");
		$('#nan').removeClass("on");
	})
	$('.list1').click(function(){
		s++;
		if(s==1){
		var pic='<img class="p" src="img/dh.png" />';
		$('.list1').append(pic);
		s=-1;
		}else{
			$(".p").remove();
		}
	})
	$(".btn1").click(function(){
		window.onpen('zhuce.html');
	})
})
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