
$(function(){
	loadRecord();
});

/**
 * 加载缴费记录
 */
function loadRecord(){
	var userId = $("#userId").val();
	var siteId = $("#siteId").val();
	$.ajax({
		type: 'post',
		url:ctx+'/w/payRecordForPc',
		 data: {
			userId:userId,
			site_id:siteId
		}, 
		success:function(data){
			data=JSON.parse(data);
			if(data.code==200){
				$('.list').empty();
				var htmls = '';
				if(data.data.length==0){
					$('.list').html("暂无数据");
				}else{
				
				for(var i=0;i<data.data.length;i++){
					if(data.data[i].pay_type_name=='weiX'){
						htmls += '<li class="weiX">'+
									'<p><span class="date">'+data.data[i].dateStr+'</span><span class="time">'+data.data[i].timeStr+'</span></p>'+
									'<p style="text-align: center"><span class="name">'+data.data[i].payName+'</span><span class="price">'+data.data[i].amount+'</span></p>'+
								 '</li>';
					}else if(data.data[i].pay_type_name=='alipay'){
						htmls += '<li class="aliPay">'+
									'<p><span class="date">'+data.data[i].dateStr+'</span><span class="time">'+data.data[i].timeStr+'</span></p>'+
									'<p style="text-align: center"><span class="name">'+data.data[i].payName+'</span><span class="price">'+data.data[i].amount+'</span></p>'+
								 '</li>';
					}else if(data.data[i].pay_type_name=='card'){
						htmls += '<li class="card">'+
									'<p><span class="date">'+data.data[i].dateStr+'</span><span class="time">'+data.data[i].timeStr+'</span></p>'+
									'<p style="text-align: center"><span class="name">'+data.data[i].payName+'</span><span class="price">'+data.data[i].amount+'</span></p>'+
								 '</li>';
					}
				}
				   $('.list').html(htmls);
				}
			}
		}
	})
}



