var v="4.1.4";
var Rand = Math.random(); 
$(function(){
	getNew();
	$(".sure").click(function(){
		var siteId=$('.siteList > .on').attr("value");
		window.location.href=basePath+"/weChatPublicNum/UserOfSiteDetails?siteId="+siteId+"&openId="+$("#openid").val()+"&v="+Rand;
		
	});
	$('.siteName').click(function(){
		$('.maskSite').css('display','block');
		$('.maskSite .content').animate({bottom:'0'},400)
	});
	$('.maskSite').click(function(){
		$('.maskSite .content').animate({bottom:'-46%'},400,function(){
			$('.maskSite').css('display','none');
		});
	});
	$('.maskSite .tc span').click(function(){
		$('.maskSite .content').animate({bottom:'-46%'},400,function(){
			$('.maskSite').css('display','none');
		});
	});
	$('.siteList > li').click(function(){
		var n = $('.siteList > li').index(this);
		var str = $(this).text();
		$(".siteList > li").removeClass('on').eq(n).addClass('on');
		$('.maskSite .content').animate({bottom:'-46%'},400,function(){
			$('.maskSite').css('display','none');
			$('.siteName').text(str);
			$('.text > span').text(str);
		});
	});
	$('.content').click(function(){
		return false;
	});
})
function copy(){
	$('.siteList > li').eq(0).addClass("on");
	var str=$('.siteList > li').eq(0).text();
	$('.text > span').text(str);
	$(".siteName").text(str);
	$(".text>span").text($("#site").val());
	if($('.siteList > li').length==1){
		$('.siteName').unbind("click");
		$(".siteName").css("background","none");
	}
	$('.siteList > li').click(function(){
		var n = $('.siteList > li').index(this);
		var str = $(this).text();
		$(".siteList > li").removeClass('on').eq(n).addClass('on');
		$('.maskSite .content').animate({bottom:'-46%'},400,function(){
			$('.maskSite').css('display','none');
			$('.siteName').text(str);
			$('.text > span').text(str);
		});
	});
}
function GetRandomNum(Min,Max)
{   
var Range = Max - Min;   
var Rand = Math.random();   
return(Min + Math.round(Rand * Range));   
}  
function getNew(){
	var openid=$("#openid").val();
	var accessToken=$("#accessToken").val();
	$(".siteList li").remove();
	$.ajax({
		type:"post",
		url:basePath+"/weChatPublicNum/getSiteMessage",
		data:{
			openId:openid,
			accessToken:accessToken,
			Rand:Rand
		},
		success: function(data){
			eval("data="+data);
			if(data.code==200){
				var htmls="";
				for (var i = 0; i < data.data.length; i++) {
					htmls+="<li value="+data.data[i].id+">"+data.data[i].site_name+"</li>"	
				}
				$(".siteList").html(htmls);
				copy();
			}
		}
	})
}