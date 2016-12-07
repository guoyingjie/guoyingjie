window.onload=function(){
	$('#alert').click(function(){
		$('.mask').css('display','block');
		$.ajax({
			type : "post",
			url:ctx+"/isStateOne",
			success: function(data){
				eval("data="+data);
				if(data.code==200){
					$("#hrefBtn").unbind("click");
					$("#hrefBtn").click(function(){
						 return false;
					});
				}
			}
		});
		return false;
	});
	$('.mask').click(function(){
		$('.mask').css('display','none');
	});
	$("#hrefBtn").click(function(){
			window.location.href=ctx+"/w/nextTimeToAuthPage";
	});
	$(".pic").click(function(){
		history.go(-1);
	});
	
};