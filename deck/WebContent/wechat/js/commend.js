/**
 * Created by Administrator on 2016/10/13.
 */
(function () {
	getValues();
	$('#imgSrc').qrcode({width: 140,height: 140,text: content});
	var ss=content;
	var imgUrl=headUrl+'/deck/wechat/img/log.png';
    wx.config({
        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: $("#appId").val(), // 必填，公众号的唯一标识
        timestamp:$("#timestamp").val() , // 必填，生成签名的时间戳
        nonceStr: $("#nonce_str").val(), // 必填，生成签名的随机串
        signature: $("#signature").val(),// 必填，签名，见附录1
        jsApiList: ['onMenuShareAppMessage','onMenuShareQZone','onMenuShareTimeline','onMenuShareQQ','onMenuShareWeibo'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    });
    wx.ready(function () {
    		wx.onMenuShareAppMessage({
        		title: '推荐码',
        		desc: '分享推荐码赚取更多积分',
        		link: ss,
        		imgUrl: imgUrl,
        	});
    	
    	wx.onMenuShareTimeline({
    		title: '推荐码', // 分享标题
    		link: ss, // 分享链接
    		imgUrl: imgUrl, // 分享图标
    	});
    	wx.onMenuShareQQ({
    		title: '推荐码', // 分享标题
    		desc: '分享推荐码赚取更多积分', // 分享描述
    		link: ss, // 分享链接
    		imgUrl: imgUrl, // 分享图标
    	});
    	wx.onMenuShareWeibo({
    		title: '推荐码', // 分享标题
    		desc: '分享推荐码赚取更多积分', // 分享描述
    		link: ss, // 分享链接
    		imgUrl: imgUrl, // 分享图标
    	});
    	wx.onMenuShareQZone({
    		title: '推荐码', // 分享标题
    		desc: '分享推荐码赚取更多积分', // 分享描述
    		link: ss, // 分享链接
    		imgUrl: imgUrl, // 分享图标
    	});
    	
    	$("#shit").show();
    })     
    
    
  
    function getValues(){
    	$.ajax({
    		type:"POST",
    		url:ctx+"/weChatPublicNum/getSigsure",
    		async:false,
    		data:{
    			strBackUrl:location.href.split('#')[0]
    		},
    		success:function(data){
    			eval("data="+data);
    			$("#timestamp").val(data.data.timestamp);
    			$("#signature").val(data.data.signature);
    			$("#nonce_str").val(data.data.nonceStr);
    			$("#appId").val(data.data.appId);
    			
    		}
    		
    	})
    	
    }
    
})();