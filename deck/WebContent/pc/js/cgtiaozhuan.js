// JavaScript Document
window.onload=function(){
	var a=document.getElementById("sec");
	s=a.innerHTML;
	var timer=setInterval(function(){
		s--;
		if(s<=0){
			clearInterval(timer);
			window.location.href="http://www.gonet.cc";
		}
		a.innerHTML=s;
	},1000);
}
