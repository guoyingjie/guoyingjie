window.onload=function(){
	 
	checkName();
	$('.cha').click(function(){
		$('#name').val('');
	});
}
 
function checkName(){
	$('.qd').click(function(){
		var name = $("#name").val();
		var telp = $("#username").val();
		var siteId = $("#siteId").val();
		name   =   name.replace(/\s+/g,"");   
		if(name==null||""==name){
			msg(0,"请输入昵称")
			return false;
		}
		$.ajax({
			type:'post',
			url :ctx+"/ProtalUserManage/checkUserName",
			data:{
				username:name,
				tel:telp
			},
			success:function(data){
				eval("data="+data);
				if(data.code==201){
					msg(0,data.msg)
					return false;
				}else{
					 window.location.href=ctx+"/w/goToPerson?userName="+telp+"&siteId="+siteId;
				}
			}
		});
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
			},2900);
		});
	}
}