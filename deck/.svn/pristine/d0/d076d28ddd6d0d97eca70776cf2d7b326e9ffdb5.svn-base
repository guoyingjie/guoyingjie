(function (){
	$('.last-btn').click(function(){
		var numStr = '';
		$('.my_num span').each(function(){
			var n = $(this).index();
			if(n==$('.my_num span').length){
				numStr+=$(this).text().substring(0,2);
			}else{
				numStr+=$(this).text().substring(0,2)+',';
			}
		});
		var type = $('.my_num').attr('data-zj');
		window.location.href =ctx+ "/w/saveLottery?betDetail="+numStr+"&numberSelectType="+type+"&username="+username+"&usermsg="+usermsg;
	});
})();
