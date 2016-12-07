$(function(){
	$(".msg").css("display","none");
	$('.next').click(function(){
		var n = $('.next').index(this);
		if(n==0){
			checkPw(n);
		}
		if(n==1){
			updatePw();
		}
	});
	
	$('#ypass').click(function(){
		$(".msg").text("");
	});
	$('#ypass').keyup(function(){
		$(".msg").text("");
	});
	$('#pass').click(function(){
		$(".msg").text("");
	});
	$('#inpass').click(function(){
		$(".msg").text("");
	});
	
})
//校验原始密码是否正确
function checkPw(n){
	$(".msg").text("");
	$(".msg").css("display","none");
	var pw=$("#ypass").val();
	if(pw==""){
		$(".msg").text("请输入原始密码");
		$(".msg").css("display","block");
		return;
	}
	$.ajax({
		type:"post",
		url:ctx+"/ProtalUserManage/checkUserPs",
		data:{
			password:pw
		},
		success:function(data){
			if(data=="true"){
				setTimeout(function(){
					boxMove(n);
				},400);
				$(".msg").text("");
				$(".msg").css("display","none");
			}else{
				$(".msg").text(" 登录密码不一致");
				$(".msg").css("display","block");
			}
		}
		
	})
}
function updatePw(){
	$(".msg").css("display","none");
	$(".msg").text("");
	var newPw=$("#pass").val();
	var repNewPw=$("#inpass").val();
	var  tel = $("#userName").val()
	if(newPw==""){
		$("#newPw").text(" 请输入新密码");
		$("#newPw").css("display","block");
		return;
	}
	if(repNewPw==""){
		$("#reNewPw").text(" 请再次输入新密码");
		$("#reNewPw").css("display","block");
		return;
	}
	if(newPw!=repNewPw){
		$("#reNewPw").text(" 两次输入的密码不一致请重新输入");
		$("#reNewPw").css("display","block");
		return;
	}
	$.ajax({
		type: "post",
		url: ctx+"/ProtalUserManage/resetPasswordForUser",
		data:{
			telephone:tel,
			password:repNewPw
		},
		success:function(data){
			eval("data="+data);
			if(data.code==200){
			      window.location.href=ctx+"/w/goToPerson?userName="+$("#userName").val()+"&siteId="+$("#siteId").val();
			}else{
				$("#newPw").text(" "+data.msg+"");
				$("#reNewPw").css("display","block");
			}
		}
		
	})
	
}

function boxMove(n){
	$('.content').css('display','none');
	$('.content').eq(n+1).css('display','block');
}

