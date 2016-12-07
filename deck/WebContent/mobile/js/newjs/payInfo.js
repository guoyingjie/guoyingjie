
var myScroll,
pullDownEl, pullDownOffset,
pullUpEl, pullUpOffset,
generatedCount = 0;
$(function(){
	var count = $("#recordCount").val();
	if(0==count||"0"==count){
		getRecordCount();
	}
	pullUpAction();
	var imgSrc = $('#phoneurl').val();
	if(imgSrc){
		$('.goPerson').css('background','#fff url(http://oss.kdfwifi.net/user_pic/'+imgSrc+') no-repeat center');
		$('.goPerson').css('background-size','cover');
	}
})
//获得缴费记录
function getRecordCount(){
	var userId = $("#userId").val();
	var siteId = $("#siteId").val();
	$.ajax({
		type:"post",
		url:ctx+"/w/getRecordCount",
		data:{
			userId:userId,
			site_id:siteId
		},
		success:function(data){
			data=JSON.parse(data);
			if(data.code==200){
				$("#recordCount").val(data.data);
			}
		}
	});
}

/**
 * 下拉刷新 （自定义实现此方法）
 * myScroll.refresh();		// 数据加载完成后，调用界面更新方法
 */
function pullDownAction () {
	location.reload()
}

/**
 * 滚动翻页 （自定义实现此方法）
 * myScroll.refresh();		
 * // 数据加载完成后，调用界面更新方法
 */
function pullUpAction () {
	var userId = $("#userId").val();
	var siteId = $("#siteId").val();
	
	var licount = $('#thelist >li').length;
	var count = $("#recordCount").val();
	if(licount==count&&licount!=0){
	}else{
	   $.ajax({
				type: 'post',
				url: ctx+'/w/payRecord',
				data: {
					userId:userId,
					site_id:siteId,
					pageIndex: generatedCount
				},
				success:function(data){
					data=JSON.parse(data);
					if(data.code==200){
						var htmls = '';
						if(data.data.length==0){
							
						}else{
							for(var i=0;i<data.data.length;i++){
								if(data.data[i].pay_type_name=='weiX'){
									htmls += '<li class="weiX">'+
												'<p class="dateTime"><span class="date">'+data.data[i].dateStr+'</span><br><span class="time">'+data.data[i].timeStr+'</span></p>'+
												'<span class="logo"></span>'+
												'<p class="detail"><span class="money">'+data.data[i].amount+'</span><br><span class="title">'+data.data[i].payName+'</span></p>'+
											 '</li>';
								}else if(data.data[i].pay_type_name=='alipay'){
									htmls += '<li class="alipay">'+
												'<p class="dateTime"><span class="date">'+data.data[i].dateStr+'</span><br><span class="time">'+data.data[i].timeStr+'</span></p>'+
												'<span class="logo"></span>'+
												'<p class="detail"><span class="money">'+data.data[i].amount+'</span><br><span class="title">'+data.data[i].payName+'</span></p>'+
											 '</li>';
								}else if(data.data[i].pay_type_name=='card'){
									htmls += '<li class="card">'+
												'<p class="dateTime"><span class="date">'+data.data[i].dateStr+'</span><br><span class="time">'+data.data[i].timeStr+'</span></p>'+
												'<span class="logo"></span>'+
												'<p class="detail"><span class="money">'+data.data[i].amount+'</span><br><span class="title">'+data.data[i].payName+'</span></p>'+
											 '</li>';
								}
							}
							if($('#thelist >li').length>0){
								$('#thelist > li').eq($('#thelist > li').length-1).after(htmls);
							}else{
								$('#thelist').html(htmls);
							}
						}
						generatedCount++;
						myScroll.refresh();
					}
				}
			})
	}
}

/**
 * 初始化iScroll控件
 */
function loaded() {
	pullDownEl = document.getElementById('pullDown');
	pullDownOffset = pullDownEl.offsetHeight;
	pullUpEl = document.getElementById('pullUp');	
	pullUpOffset = pullUpEl.offsetHeight;
	
	myScroll = new iScroll('wrapper', {
		scrollbarClass: 'myScrollbar', /* 重要样式 */
		useTransition: false, /* 此属性不知用意，本人从true改为false */
		topOffset: pullDownOffset,
		onRefresh: function () {
			if (pullDownEl.className.match('loading')) {
				pullDownEl.className = '';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新...';
			} else if (pullUpEl.className.match('loading')) {
				pullUpEl.className = '';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多...';
			}
		},
		onScrollMove: function () {
			if (this.y > 5 && !pullDownEl.className.match('flip')) {
				pullDownEl.className = 'flip';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '松手开始更新...';
				this.minScrollY = 0;
			} else if (this.y < 5 && pullDownEl.className.match('flip')) {
				pullDownEl.className = '';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新...';
				this.minScrollY = -pullDownOffset;
			} else if (this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
				pullUpEl.className = 'flip';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '松手开始更新...';
				this.maxScrollY = this.maxScrollY;
			} else if (this.y > (this.maxScrollY + 5) && pullUpEl.className.match('flip')) {
				pullUpEl.className = '';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多...';
				this.maxScrollY = pullUpOffset;
			}
		},
		onScrollEnd: function () {
			if (pullDownEl.className.match('flip')) {
				pullDownEl.className = 'loading';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '加载中...';				
				pullDownAction();	// Execute custom function (ajax call?)
			} else if (pullUpEl.className.match('flip')) {
				var licount = $('#thelist >li').length;
				var count = $("#recordCount").val();
				if(licount==count&&licount!=0){
					 $("#pullUp").css("display","none");
				}else{
					pullUpEl.className = 'loading';
					pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';				
					pullUpAction();	// Execute custom function (ajax call?)
				}
			}
		}
	});
	
	setTimeout(function () { document.getElementById('wrapper').style.left = '0'; }, 800);
}

//初始化绑定iScroll控件 
document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
document.addEventListener('DOMContentLoaded', loaded, false); 
