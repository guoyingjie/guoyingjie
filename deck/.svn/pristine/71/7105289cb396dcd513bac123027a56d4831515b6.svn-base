/**
 * Created by Administrator on 2016/10/20.
 */
(function () {
    var number = $('#number');
    var seleTxt = $('.seleTxt');
    var payType = 0;
    // 立即划拨按钮
    $('#rechargeBtn').click(function () {
    	$('#rechargeBtn').attr('disabled',true);
    	var payNum = parseInt(number.val());//购买数量
    	var payText = seleTxt.text();//购买的单位名称
    	console.log(payType,payNum,payText);
        var siteId = $.trim($('#siteId').val());
        var username = $.trim($('#username').val());
        var sonname = $.trim($('#sonname').val());
        
        if(payType==0){
        	if(routerType=='ikuai'){
        		ModelShow("暂未开通流量计费,请选择充值时长");
        		$('#rechargeBtn').attr('disabled',false);
        		return ;
        	}
        }
        if(payNum==''){
        	ModelShow("请输入购买数量");
        	$('#rechargeBtn').attr('disabled',false);
        }else{
        	checkDadAccount(username,siteId,payType,sonname,payText,payNum);
        }
    	 
  });

    // 单选框事件
    $('.payType').change(function () {

        var costTypeUl = $('.costType > ul');
        var html1 = '<li>G</li><li>M</li>';
        var html2 = '<li>时</li><li>天</li><li>月</li>';
        var index = $('.payType').index(this);
        payType = index;
        if (index) {
            // 选择第二个 时长购买
            seleTxt.text('时');
            costTypeUl.html(html2)
            number.val(1);

        } else {
            // 选择第一个 流量购买
            seleTxt.text('G');
            costTypeUl.html(html1)
            number.val(1);
        }
        downBox()
    });

    // 购买数量改变
    number.val(1);
    $('#tel-row').on('click','a',function () {
        var index = $('#tel-row').find('a').index(this);
        if (index) {
            number.val(+number.val() + 1)
        } else {
            if (!(number.val() === '1')&&!(number.val()==='')) {
                number.val(+number.val() - 1)
            }
        }
    });

    downBox()
})();


//检验主账号是否有时间或者流量可以划拨
function checkDadAccount(username,siteId,payType,sonname,payText,payNum){
	var flowsOrTime = '';
	if(payType==0){//流量
    	if(payText=="G"){
    		flowsOrTime=payNum*1024*1024;
    	}else{
    		flowsOrTime= payNum*1024;
    	}
    }else{
		if (payText == "时") {
			flowsOrTime = payNum*60*60*1000;
		} else if(payText=='天'){
			flowsOrTime = payNum*24*60*60*1000;
		}else{
			flowsOrTime = payNum*31*24*60*60*1000;
		}
	}
	$.ajax({
		type:'post',
		url:ctx+"/weChatPublicNum/checkDadAccount",
		data:{
			dadname:username,
			siteId:siteId,
			state:payType,
			timeOrFlow:flowsOrTime
		},
		success:function(data){
			eval("data="+data);
			if(data.code==200){
				if(payType==0){//流量
	            	dadDevideToSonTimeAndFlow(username,siteId,payType,sonname,flowsOrTime);
	            }else{
					dadDevideToSonTimeAndFlow(username,siteId,payType,sonname,flowsOrTime);
				}
			}else{
				if(payType==0){
					ModelShow("剩余流量小于200兆或者划拨流量大于剩余流量");
				}else{
					ModelShow("剩余时间小于三天或者划拨时间大于剩余时间");
				}
				$('#rechargeBtn').attr('disabled',false);
				return;
			}
		},
		error:function(){
			$('#rechargeBtn').attr('disabled',false);
			ModelShow("网络服务忙");
		}
	});
}


/**
 * @Description  主账号划拨流量与时间到子账号
 * @date 2016年10月25日上午10:23:55
 * @author guoyingjie
 * @param dadname--主账号
 * @param sonname--子账号
 * @param siteId--场所id
 * @param flowsOrTime--时间或者流量(如果是流量直接传过来的是kb为单位)
 * @param state--状态(1--划分时间,0--划分流量)
 */
function dadDevideToSonTimeAndFlow(username,siteId,payType,sonname,flowsOrTime){
	$.ajax({
		type:'post',
		url:ctx+"/weChatPublicNum/dadDevideToSonTimeAndFlow",
		data:{
			dadname:username,
			siteId:siteId,
			state:payType,
			sonname:sonname,
			flowsOrTime:flowsOrTime
			
		},
		success:function(data){
			eval("data="+data);
			if(data.code==200){
				ModelShow("划拨成功,请激活子账号");
				setTimeout(function(){
					window.location.href=ctx+'/weChatPublicNum/goToChangeNum?userName='+username+'&siteId='+siteId;
				},2000);
			}else{
			    ModelShow("划拨失败,请刷新再试!");
				return false;
			}
		},
		error:function(){
			ModelShow("网络服务忙");
		}
	});
	
}

// 防止重复点击
function noRepeatClick(self) {
    self.setAttribute("disabled", true); // 禁止点击
    self.timer = setTimeout(function () {
        self.removeAttribute("disabled");
        clearTimeout(self.timer);
    },3000)
}

// 下拉框事件
function downBox() {
    /* 选择费用类型 */
    var seleType = $('.seleType');
    var seleTxt = $('.seleTxt');
    var costTypeUl = $('.costType > ul');
    var costTypeLi = $('.costType > ul > li');
    var isNone = false;

    seleType.unbind('click');
    seleType.click(function () {
        var nn = seleType.index(this);
        isNone = !isNone;
        if (isNone) {
            costTypeUl.eq(nn).css('display', 'block');
            return false;
        } else {
            costTypeUl.eq(nn).css('display', 'none');
            return false;
        }

    });
    costTypeLi.unbind('click');
    costTypeLi.click(function () {
        var nnd = costTypeLi.index(this);
        var str = costTypeLi.eq(nnd).text();
        seleTxt.text(str);
        costTypeUl.css('display', 'none');

        console.log(seleTxt.text())
    });
    costTypeUl.unbind('click');
    costTypeUl.click(function () {
        return false;
    });
    /* 选择费用类型 */

    $('body').click(function () {
        costTypeUl.css('display', 'none');
    });
}

function ModelShow(msg) {
    $('#errMsg').text(msg);
    var err = $('#errModel');
    err.fadeIn(2000,function () {
        err.fadeOut(2000);
    });

}