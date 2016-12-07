window.onload=function(){
	$('.upImg').click(function(){
		$(".js_upFile").click();
		//$('.photo').html('<img src="" width="80px" alt=" ">')
	});
	
	$(".js_upFile").uploadView({
		uploadBox: '.box',//设置上传框容器
		showBox : '.photo',//设置显示预览图片的容器
		width : 80, //预览图片的宽度，单位px
		height : 80, //预览图片的高度，单位px
		allowType: ["gif", "jpeg", "jpg", "bmp", "png"], //允许上传图片的类型
		maxSize :4, //允许上传图片的最大尺寸，单位M
		success:function(e){
			$('.upImg').css('display','none');
			$('.zhichi').css('display','none');
			$('.photo').css('display','block');
		}
	});
	$('.cancel').click(function(){
		$('.zhichi').css('display','block');
		$('.upImg').css('display','inline-block');
		$('.photo').css('display','none');
		$('.photo').attr('data-base',"");
		location.reload();
	});
	$('.confirm').click(function(){
		var userName=$("#userName").val();
		var pictureBase64=$('.photo').attr('data-base');
		if(pictureBase64==undefined||pictureBase64==""||pictureBase64==null){
			msg(0,"请选择图片");
			return;
		}
		$.ajax({
			type: 'post',
			url: ctx+"/w/uploadUserPicture",
			data: {
				userName:userName,
				pictureBase64: pictureBase64
			},
			success: function(data){
				if(data=="1"){
					msg(1,"上传图片成功");
					setTimeout(function(){
						window.location.href=ctx+"/w/goToPerson?userName="+$("#userName").val()+"&siteId="+$("#siteId").val();
					},500);
				}else{
					msg(0,"上传图片失败");
				}
			}
		})
	});
	
	
}
function msg(code,str){
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
			},1500);
		});
	}
}