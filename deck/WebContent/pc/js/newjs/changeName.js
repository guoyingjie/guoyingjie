window.onload=function(){
	$('.cha').click(function(){
		$('#name').val('');
	});
	$(".next").click(function(){
		updateNickName();
	});
	$("#name").focus(function(){
		$(".msg").text("");
	});
	//初始化昵称
	initializeNickName();
}

/**
 * 初始化昵称
 */
function initializeNickName(){
	$('#name').val($('#userNickname').val());
}

function updateNickName(){
	
	var nickName=$('#name').val();
	if(nickName==""){
		$(".msg").text("请输入昵称");
		return;
	}
	$.ajax({
		type:"post",
		url:ctx+"/ProtalUserManage/checkUserName",
		data:{
			username:nickName,
			tel:$("#userName").val()
		},
		success:function(data){
			eval("data="+data);
			if(data.code==200){
				 window.location.href=ctx+"/w/goToPerson?userName="+data.data.userName+"&siteId="+data.data.siteId;
			}else{
				$(".msg").text("昵称已存在,请更换");
			}
		}
		
	})
	
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
			},2900);
		});
	}
}