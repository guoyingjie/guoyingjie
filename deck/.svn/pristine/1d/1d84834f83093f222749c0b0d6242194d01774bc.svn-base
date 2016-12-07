/**
 * Created by Administrator on 2016/10/20.
 */
(function () {
	
	getCustomInfo();
 
    // 跳转划拨资费页面
    $('#hbzf').click(function () {
    	 var siteId = $.trim($('#siteId').val());
         var username = $.trim($('#username').val());
         var sonname = $.trim($('#sonname').val());
         var openid = $.trim($('#openId').val());
         window.location.href=ctx+"/weChatPublicNum/goTansferSonPage?username="+username+"&siteId="+siteId+"&sonname="+sonname+"&openId="+openid;
         
    });

    // 删除子账号
    $('#deleteShow').click(function () {
        $('#mask').show();
    });

    // 确认删除
    $('#delete').click(function () {
        $('#mask').hide();
        
        	 var siteId = $.trim($('#siteId').val());
             var username = $.trim($('#username').val());
             var sonname = $.trim($('#sonname').val());
             var openid = $.trim($('#openId').val());
             $.ajax({
         		type:'post',
         		url:ctx+'/weChatPublicNum/deleteSonAccount',
         		data:{
         			username:username,
         			siteId:siteId,
         			sonname:sonname,
         		},
         		success:function(data){
         			eval("data="+data);
         			if(data.code==200){
         				ModelShow("删除成功")
         				setTimeout(function(){
         					window.location.href=ctx+"/weChatPublicNum/goToChangeNum?userName="+username+"&openId="+openid+"&siteId="+siteId;
         				},1000);
         			}else{
         				ModelShow("删除失败")
         				return;
         			}
         		},
         		error:function(){
         			ModelShow("网络服务忙")
         			return;
         		}
         	})
    });

    // 取消按钮
    $('#cancel').click(function () {
        $('#mask').hide();
    })


})();

// model
function ModelShow(msg) {
    $('#errMsg').text(msg);
    var err = $('#errModel');
    err.fadeIn(1500,function () {
        err.fadeOut(1500);
    });

}
 
function sucModelShow(msg,fn) {
    $('#sucMsg').text(msg);
    var err = $('#sucModel');
    err.fadeIn(1500,function () {
        err.fadeOut(1500,function () {
            if (fn) {
                fn()
            }
        });
    });
}
// 获取用户的时间占比
function getCustomInfo(){
	 var siteId = $.trim($('#siteId').val());
     var sonname = $.trim($('#sonname').val());
     $.ajax({
  		type:'post',
  		url:ctx+'/weChatPublicNum/getSiteCustomTime',
  		data:{
  			siteId:siteId,
  			sonname:sonname,
  		},
  		success:function(data){
  			eval("data="+data);
  			if(data.code==200){
  				if(data.data.sTime=="0"&&data.data.sFlow=="0"){
  					$('#noHave').show();
  					$('#have').hide();
  				}else{
  					$('#have').show();
  					$('#noHave').hide();
  				// 剩余时间
  	  			    var surplus = $('.surplus');
  	  			    var surplusText = $('.surplusText');
  	  			    var countTime = data.data.alltime; // 总时间
  	  			    var residueTime = data.data.sTime;  // 剩余时间
  	  			    if(residueTime=="0"){
  	  			    	surplus.eq(0).css('width',0+'%');
  	  			    }else{
  	  			    	var TW = residueTime/countTime * 100; // div宽度百分比
  	  			    	surplus.eq(0).css('width',TW+'%');
  	  			    }
  	  			    surplusText.eq(0).text(data.data.time);
  	  			    // 剩余流量
  	  			    var flowCount = data.data.flow; // 总流量
  	  			    var flowResidue = data.data.sFlow; // 剩余流量
  	  			    if(flowResidue=="0"){
  	  			    	surplus.eq(1).css('width',0+'%');
  	  			    }else{
  	  			    	var FW = flowResidue / flowCount *100;
  	  			    	surplus.eq(1).css('width',FW+'%');
  	  			    }
  	  			    surplusText.eq(1).text(data.data.flowstr);
  				}
  			} 
  		},
  		error:function(){
  			ModelShow("网络服务忙")
  			return;
  		}
  	})
}

