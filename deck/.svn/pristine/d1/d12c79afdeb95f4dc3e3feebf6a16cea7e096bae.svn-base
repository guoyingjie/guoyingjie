/**
 * Created by Administrator on 2016/10/20.
 */
(function () {
    $('#complete').click(function () {
        $('#complete').attr('disabled',true);
        var sonname = $.trim($('#sonname').val());
        var username = $.trim($('#username').val());
        var siteId = $.trim($('#siteId').val());
        var openid = $.trim($('#openid').val());
        // 获取密码
        var pwd = $('#pwd').val();
        if (pwd.length<6) {
            ModelShow('密码太短');
            $('#complete').attr('disabled',false);
            return
        }
        $.ajax({
    		type:'post',
    		url:ctx+'/weChatPublicNum/createSonAccount',
    		data:{
    			username:username,
    			siteId:siteId,
    			sonname:sonname,
    			password:pwd
    		},
    		success:function(data){
    			eval("data="+data);
    			if(data.code==200){
    				ModelShow("创建成功")
    				setTimeout(function(){
    					window.location.href=ctx+"/weChatPublicNum/goToChangeNum?userName="+username+"&openId="+openid+"&siteId="+siteId;
    				},1000);
    			}else{
    				ModelShow("创建失败");
    				 $('#complete').attr('disabled',false);
    				return;
    			}
    		},
    		error:function(){
    			ModelShow("网络服务忙")
    			 $('#complete').attr('disabled',false);
    			return;
    		}
    	})
    });


    // 清空按钮
    $('.input-row i').click(function () {
        var index = $('.input-row i').index(this);
        var input = $('.input-row input').eq(index);
        input.val('');
        input.focus();
    });
})();


// model
function ModelShow(msg) {
    $('#errMsg').text(msg);
    var err = $('#errModel');
    err.fadeIn(1500,function () {
        err.fadeOut(1500);
    });

}

// 防止重复点击
function noRepeatClick(self) {
    self.setAttribute("disabled", true); // 禁止点击
    self.timer = setTimeout(function () {
        self.removeAttribute("disabled");
        clearTimeout(self.timer);
    },1000)
}
 