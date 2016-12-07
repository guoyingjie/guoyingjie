(function(){

	// numlayout();

	//alert(1)
	/* ----------------- 事件 ----------------- */

	$('.red.loy_num').click(function(){ // 红色区域
		var cls = $(this).attr('class').indexOf('on');
		if(cls>0){
			$(this).removeClass('on');
			add_Loy('red',$(this).text(),false);
		}else{
			if(red_num('.red_round')>=6) {
				return false;
			}else{
				$(this).addClass('on');
				add_Loy('red',$(this).text(),true);
			}
		}
	});

	$('.blue.loy_num').click(function(){ // 蓝色区域
		var cls = $(this).attr('class').indexOf('on');
		if(cls>0){
			$(this).removeClass('on');
			add_Loy('blue',$(this).text(),false);
		}else{
			if(red_num('.blue_round')>=1) {
				return false;
			}else{
				$(this).addClass('on');
				add_Loy('blue',$(this).text(),true);
			}
		}
	});

	$('.sel_tabel span').click(function(){ // 手动选号和随机选号切换
		var n = $('.sel_tabel span').index(this);
		$('.sel_tabel span').removeClass('on').eq(n).addClass('on');
		if(n==1){
			$('.my_num').attr('data-zj',1);
			$('.my_num').addClass('on');
			$('.sd').css('display','none');
			$('.change').css('display','block');
			add_round();
		}else{
			$('.my_num').attr('data-zj',2);
			$('.my_num').removeClass('on');
			$('.sd').css('display','block');
			$('.change').css('display','none');
			$('.red_round span').removeClass('on');
			$('.blue_round span').removeClass('on');
			$('.my_num > span').remove();
		}
	});

	$('.change').click(function(){
		add_round();
	});
	
	$('.suer').click(function(){
		if($('.my_num span').length!=7){
			alert('号码不够！');
			return false;
		}
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

	/*$('.my_num i').click(function(){
		alert('dlt')
		var n = parseInt($(this).parent().text());
		var str = $(this).parent().attr('class');
		if(str.indexOf('red')>-1){
			$('.red_round .loy_num').each(function(){
				if($(this).text()==n){
					$(this).removeClass('on');
				}
			});
		}else{
			$('.blue_round .loy_num').each(function(){
				if($(this).text()==n){
					$(this).removeClass('on');
				}
			});
		}
		$(this).parent().remove();
	});*/

	/* ----------------- 事件 ----------------- */

	/* ----------------- 方法 ----------------- */

	function red_num(obj){// 返回现在一个选了几个红球
		var a_num = 0;
		$(obj+' .loy_num').each(function(){
			if($(this).attr('class').indexOf('on')>1){
				a_num++;
			}
		});
		return a_num;
	}

	/*function numlayout(){
		var w = $('.red_round').width();
		for(var i=0;i<$('.red_round .loy_num').length;i++){

		}

		console.log(parseInt(w/8));
	}*/

	function add_Loy(color,n,tof){//添加 红球
		if(color=='red'){
			if(tof){
				var htmls = '<span class="red loy_num on">'+n+'<i>&times;</i></span>';
				if($('.my_num .blue.loy_num').length>0){
					$('.my_num .blue.loy_num').before(htmls);
				}else{
					$('.my_num').append(htmls);
				}
			}else{
				$('.my_num .red.loy_num').each(function(){
					if(parseInt($(this).text())==n){
						$(this).remove();
					}
				});
			}
		}else{
			if(tof){
				var htmls = '<span class="blue loy_num on">'+n+'<i>&times;</i></span>';
				$('.my_num').append(htmls);
			}else{
				$('.my_num .blue').remove();
			}
		}

		$('.my_num i').unbind('click');
		$('.my_num i').click(function(){//  删除已选中的号码
			var n = parseInt($(this).parent().text());
			var str = $(this).parent().attr('class');
			if(str.indexOf('red')>-1){
				$('.red_round .loy_num').each(function(){
					if($(this).text()==n){
						$(this).removeClass('on');
					}
				});
			}else{
				$('.blue_round .loy_num').each(function(){
					if($(this).text()==n){
						$(this).removeClass('on');
					}
				});
			}
			$(this).parent().remove();
		});
	}

	function add_round(){ // 把随机出来的数渲染到页面上
		$('.my_num > span').remove();
		var red = random(6,34);
		var blue = random(1,17);
		var htmls = '';
		for(var i in red){
			/*if(red[i]<10){
				red[i] = '0'+red[i];
			}*/
			htmls+='<span class="red loy_num on">'+(red[i]>=10?red[i]:'0'+red[i])+'</span>';
		}
		htmls+='<span class="blue loy_num on">'+(blue[0]>=10?blue[0]:'0'+blue[0])+'</span>';
		$('.my_num p').after(htmls);
	}

	function random(z,max){// 返回随机数 @z代表几个 @max代表最大数
		var numArr = [];
		while (true){
			var n = parseInt(Math.random()*100);
			if(n>0&&n<max){
				if(contains(numArr,n)){
					numArr.push(n);
					if(numArr.length==z){
						break;
					}
				}
			}
		}
		console.log(numArr);
		return numArr;
	}

	function contains(arr,n){// 判断n 有没有存在于arr中
		for(var i in arr){
			if(arr[i]==n){
				return false;
			}
		}
		return true;
	}

/*	function add_b_Loy(n){// 添加蓝球
		console.log(n);
		var htmls = '<span class="red loy_num">'+n+'<i>&times;</i></span>';
		if($('.my_num .blue.loy_num').length>0){
			$('.my_num .blue.loy_num').before(htmls);
		}else{
			$('.my_num ').append(htmls);
		}
	}*/

	/* ----------------- 方法 ----------------- */
})();

