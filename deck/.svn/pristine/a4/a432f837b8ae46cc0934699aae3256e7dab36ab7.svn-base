/**
 * Created by Administrator on 2016/10/14.
 */
(function () {
	$('#phoneNumber').val(''); // 手机号
	$("#pwdText").val('');//初始密码;
	$("#pwdBtn").attr('disabled',true);
	$("#loginBtn").attr("disabled",true);
	$('#phoneNumber').bind('input propertychange',function(){
		if($('#phoneNumber').val().length==11){
			doRegisterCheckUser($('#phoneNumber').val());
		}
	});
	
    // 获取密码按钮
    $('#pwdBtn').click(function () {
        var phone = $('#phoneNumber').val(); // 手机号
        // 验证手机
        var pattern = /^1[34578]\d{9}$/;
        if(!(pattern.test(phone))){
            ModelShow('手机号错误');
            return;
        }
        // 按钮倒计时
        BtnWaitTime(this,30,function () {
            // 倒计时结束回调
        })
        sendMegToUser(phone);
    });
    /*完成注册*/
    $("#loginBtn").click(function(){
    	var phone=$('#phoneNumber').val(); // 手机号
    	var code=$("#pwdText").val();//初始密码;
    	var recommend=$("#code").text();//推荐码
    	 var pattern = /^1[34578]\d{9}$/;
         if(!(pattern.test(phone))){
             ModelShow('手机号错误');
             return;
         }
         $.ajax({
        	type:"post",
        	url:ctx+"/weChatPublicNum/wechatRegister",
        	data:{
        		telephone:phone,
        		phonecode:code,
        		recommend:recommend
        	},
        	success: function(data){
        		eval("data="+data);
        		if(data.code==200){
        			window.location.href=ctx+"/weChatPublicNum/jumpPage?state=1"
        		}else if(data.code==201){
        			 ModelShow(data.msg);
        		}else if(data.code==202){
        			window.location.href=ctx+"/weChatPublicNum/jumpPage?state=0"
        		}
        	}
         })
    	
    });
    // model
    function ModelShow(msg) {
        $('#errMsg').text(msg)
        $('#errModel').fadeIn(1000);
        $('#errModel').fadeOut(2000);
    }
  //发送验证码
    function toSendMeg(){
    	$('.codeBtn').click(function(){
    		   coundDown($(this),60);
    		   var phone = $('#phoneNumber').val();
    		   sendMegToUser(phone);
    	});
    }
  //发送验证码
    function sendMegToUser(phone){
    	$.ajax({
    		type : "POST",
    		url : ctx+"/TelCodeManage/registerMsgRandCode",
    		data : {
    			tel : phone,
    			templateCode:"SMS_26495034",
    		},
    		success : function(data) {
    			if(data==-1){
    				 ModelShow('验证码发送失败')
    				return false;
    			}
    			if(data==-2){
    				ModelShow("请不要频繁发送验证码");
    				return false;
    			}
    		}
    	});
    }
    
  //第一步用户名的校验
function doRegisterCheckUser(phone){
		$.ajax({
			type : "POST",
			url : ctx+"/ProtalUserManage/checkTel",
			data : {
				telephone : phone,
			},
			success : function(data) {
				eval("data = " + data);
				if(data==true){
					ModelShow('该手机号已注册');
					$("#pwdBtn").attr("disabled",true);
					$("#pwdBtn").css("background","#ccc");
					$("#loginBtn").attr("disabled",true);
					$("#loginBtn").css("background","#ccc");
				}else{
					$("#pwdBtn").attr('disabled',false);
					$("#pwdBtn").css("background","#fff");
					$("#loginBtn").attr("disabled",false);
					$("#loginBtn").css("background","#47a4dc");
				}
			}
	});
}
    

/* 按钮倒计时
 * self 按钮对象
 * wait 时间
 * backFn 回调函数
 */
function BtnWaitTime(self,wait,backFn) {
    self.innerText = '重新获取'
    self.setAttribute("disabled", true);
    self.style.backgroundColor = '#fff';
    var text = self.innerText;
    self.time = setInterval(function () {
        if (wait === 0) {
            if (backFn){
                backFn();
            }
            self.removeAttribute("disabled");
            self.style.backgroundColor = '#fff';
            self.innerText = text;
            clearInterval(self.time)
        }
        else {
            wait--;
            self.innerText = text+'(' + wait + ')';
        }

    }, 1000);
}

})();


