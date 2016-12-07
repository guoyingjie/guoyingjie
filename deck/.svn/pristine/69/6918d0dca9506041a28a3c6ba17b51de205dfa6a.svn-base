$(function(){
	isHavePhone();
	$(".photo").click(function(){
		upLoadPic();
	});
	var username = $(".phone").text();
	var name = username.substring(0,3); 
	var end = username.substring(7,username.length);
	var allname = name+"****"+end;
	$('.phone').html(allname);
});
function changeName(){
	 window.location.href=ctx+"/ProtalUserManage/goNickNamePage";
}
//判断是否有图片
function isHavePhone(){
	var imgSrc = $('#phoneurl').val();
	if(imgSrc){
		$('.photo').css('background','#fff url(http://oss.kdfwifi.net/user_pic/'+imgSrc+') no-repeat center');
		$('.photo').css('background-size','cover');
	}
}
//获取用户消息
function message(){
	var userName=$("#username").val();
	window.location.href=ctx+"/ProtalUserManage/goToMessagePage?handleType=0"+"&userName="+userName;
}
//获取用户彩票
function lottery(){
	var userName=$("#username").val();
	window.location.href=ctx+"/ProtalUserManage/goToMessagePage?handleType=1"+"&userName="+userName;
}
//修改密码
function changePwd(){
	 window.location.href=ctx+"/ProtalUserManage/changePassword";
}
//切换账号
function changeNumber(){
	window.location.href=ctx+"/w/changeNumber";
}
//上传图片跳转
function upLoadPic(){
	window.location.href=ctx+"/ProtalUserManage/goToUpLoadPic"
}