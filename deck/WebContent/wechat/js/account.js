/**
 * Created by Administrator on 2016/10/20.
 */

(function () {
	dianEvent();
	getUserByson()
	var openid = $('#openid').val();
	if(openid=='noopenid'){
		$('#exctBind').remove();
		$('#changeBind').remove();
		$('.border-div').eq(0).remove();
		$('.border-div').eq(1).remove();
	}
    $('#signOut').click(function () {
    	$("#signOut").attr('disabled',true);
    	var openid = $('#openid').val();
        var dadname = $('.userName').eq(0).text()+','+$('.userName').eq(1).text();
        $.ajax({
        	type:'post',
        	url:ctx+'/weChatPublicNum/activeSonState',
        	data:{
        		dadname:dadname
        	},
        	success:function(data){
        		eval('data='+data);
        		if(data.code==200){
        			 window.location.href=ctx+'/weChatPublicNum/getSuccess?state=1';
        		}else{
        			ModelShow("激活失败!");
        			$("#signOut").attr('disabled',false);
        			return;
        		}
        	},error:function(){ 
        		ModelShow("网络繁忙请稍后");
        		$("#signOut").attr('disabled',false);
        		return;
        	}
        });
    })
})();

// 渲染列表
function listHtml(number,username,zhuusername) {
    var html;
    // 0个子账号时
    if (number == 0) {
        html ='<div class="list text-center blue" pan="add" zhu='+zhuusername+'>'+
                '<img class="addList" src="'+img+'/add.png">创建子账号'+
                '</div>'
    } else if (number == 1) { // 一个子账号时
        html ='<div class="list" pan="manager" zhu='+zhuusername+'>'+
            '<p class="listTitle">子账号管理</p>'+
            '<img class="right-icon" src="'+img+'/enter.png">'+
            '<span class="userName">'+username+'</span>'+
            '</div>'+
            '<div class="border-div"></div>' +
            '<div class="list text-center blue" pan="add" zhu='+zhuusername+'>'+
            '<img class="addList" src="'+img+'/add.png">创建子账号'+
            '</div>';
    } else { // 两个子账号时
        html ='<div class="list" pan="manager" zhu='+zhuusername+'>'+
            '<p class="listTitle">子账号1管理</p>'+
            '<img class="right-icon" src="'+img+'/enter.png">'+
            '<span class="userName">'+username.split(',')[0]+'</span>'+
            '</div>'+
            '<div class="border-div"></div>'+
            '<div class="list" pan="manager" zhu='+zhuusername+'>'+
            '<p class="ListTitle">子账号2管理</p>'+
            '<img class="right-icon" src="'+img+'/enter.png">'+
            '<span class="userName">'+username.split(',')[1]+'</span>'+
            '</div>';
    }

    $('#ajaxList').html(html); // 渲染列表

    $('.list').click(function(){
      var n = $('.list').index(this);
      var pan = $('.list').eq(n).attr('pan');
      if(pan=='add'){
    	  var username = $('.list').eq(n).attr('zhu');
    	  var siteId = $('#siteId').val();
    	  var openid = $('#openid').val();
    	  window.location.href=ctx+"/weChatPublicNum/goCreateSonPage?username="+username+"&siteId="+siteId+"&openId="+openid;
      }else if (pan=='manager'){
    	  var sonname = $('.list').eq(n).find('span').text();
    	  var siteId = $('#siteId').val();
    	  var openid = $('#openid').val();
    	  var username = $('#username').val();
    	  var siteId = $('#siteId').val();
    	  window.location.href=ctx+"/weChatPublicNum/goManageSonPage?username="+username+"&siteId="+siteId+"&openId="+openid+"&sonname="+sonname;
      }
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

//菜单跳转点击事件
function dianEvent(){
	$("#exctBind").click(function() {
		var num=Math.random();
		var siteId=$("#siteId").val();
		var openid=$("#openid").val();
		var username=$("#username").val();
		window.location.href = ctx + "/weChatPublicNum/cancelBind?siteId="+siteId+"&openId="+openid+"&userName="+username+"&num="+num;
	});

	$("#changeBind").click(function() {
		var num=Math.random();
		var siteId=$("#siteId").val();
		var openid=$("#openid").val();
		var username=$("#username").val();
		window.location.href = ctx + "/weChatPublicNum/changeBind?siteId="+siteId+"&openId="+openid+"&userName="+username+"&num="+num;
	});
		
	 $("#changePassword").click(function(){
		var siteId=$("#siteId").val();
		var openid=$("#openid").val();
		var username=$("#username").val();
		window.location.href=ctx+"/weChatPublicNum/changePassword?openId="+openid+"&userName="+username+"&siteId="+siteId;
	 });
}

//获得用户下的子账号
function getUserByson(){
	var username=$("#username").val();
	$.ajax({
      type:"post",
      url:ctx+"/weChatPublicNum/getUserByUsername",
      data:{
    	  username:username
      },
      success:function(data){
    	  eval("data="+data)
          if(data.code==200){
        	  var i = data.data.length;
        	  if(i==1){
        		  listHtml(1,data.data.sonname.split(',')[0],username);
        	  }else{
        		  listHtml(2,data.data.sonname,username);
        	  }
          }else{
        	  listHtml(0,"",username);
          }
      }
  })
}
function ModelShow(msg) {
    $('#errMsg').text(msg);
    var err = $('#errModel');
    err.fadeIn(1500,function () {
        err.fadeOut(1500);
    });
}


