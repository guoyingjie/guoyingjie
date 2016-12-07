window.onload=function () {
	
	isHavePhone();
	isUserName();
 $("#changePassword").click(function(){
	 var siteId=$("#siteId").val();
		window.location.href=ctx+"/weChatPublicNum/goToChangeNum?userName="+$("#username").val()+"&openId=noopenid&siteId="+siteId;
 });
 
 $("#changeName").click(function(){
	 window.location.href=ctx+"/ProtalUserManage/goNickNamePage";
 });
 
 $("#message").click(function(){
	 var userName= $("#username").val();
	 window.location.href=ctx+"/ProtalUserManage/goToMessagePage?handleType=0"+"&userName="+userName;;
 });
 	$('.photo').click(function(){
		$(".js_upFile").click();
		//$('.photo').html('<img src="" width="80px" alt=" ">')
		 
	});
 	$(".js_upFile").uploadView({
		uploadBox: 'body',//设置上传框容器
		showBox : '.photo',//设置显示预览图片的容器
		width : 80, //预览图片的宽度，单位px
		height : 80, //预览图片的高度，单位px
		allowType: ["gif", "jpeg", "jpg", "bmp", "png"], //允许上传图片的类型
		maxSize :4, //允许上传图片的最大尺寸，单位M
		success:function(e){
			$.ajax({
				type: 'post',
				url: ctx+'/w/uploadUserPicture',
				data: {
					userName : $("#username").val(),
					pictureBase64: $("#imageContent").val(),
				},
				success: function(data){
					if(data==1){
						window.location.href=ctx+'/w/goToPerson?userName='+$('#username').val()+'&siteId='+$("#siteId").val();
						msg(1,'头像上传成功');
						$("#imageContent").val("")
					}else{
						msg(0,'头像上传失败');
					}
				}
			});
		}
	});
}
//切换账号
function changeNumber(){
	window.location.href=ctx+"/w/changeNumber";
}
//我的礼包
function myGift(){
	msg(0,"暂时开发中,敬请期待");
}
function myLettory(){
	var userName= $("#username").val();
	msg(0,"暂无数据");
	//window.location.href=ctx+"/ProtalUserManage/goToMessagePage?handleType=1"+"&userName="+userName;
 }

function gotogames(){
	window.location.href=ctx+"/ProtalUserManage/gotogames";
 }

//判断是否有图片
function isHavePhone(){
	var imgSrc = $('#phoneurl').val();
	if(imgSrc){
		$('.photo').css('background','#fff url(http://oss.kdfwifi.net/user_pic/'+imgSrc+') no-repeat center');
		$('.photo').css('background-size','cover');
	}
}
//用户名切成232***223的格式
function isUserName(){
	var username = $("#username").val();
	var name = username.substring(0,3); 
	var end = username.substring(7,username.length);
	var allname = name+"****"+end;
	$('.phone').html(allname);
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
			},1000);
		});
	}
}