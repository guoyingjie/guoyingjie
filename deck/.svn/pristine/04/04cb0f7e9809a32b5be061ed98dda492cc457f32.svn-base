window.onload=function(){
	$('.next').click(function(){
		$("#autonym").submit();
	});
	cardPicCanvasCaompress();
	userPicCanvasCaompress();
	$('.next1').click(function(){
		next1();
	});
	$(".pic").click(function(){
		window.history.go(-1);
	});
	$('.over').click(function(){
		$('.mask').css('display','block');
		formUpdate();
		return false;
	});
	//认证成功后点击立即上网
	$('#authId').click(function(){
			window.location.href=ctx+"/w/goSuccessAuthPage";
	});
};
 
/**
* 上传图片用canvas显示压缩图片。
*/
function cardPicCanvasCaompress(){
	var filechooser=document.getElementById('cardPic');
	
	
    filechooser.onchange = function () {
    	if (!this.files.length) return;
    	$('.progress span').animate({"width":"100%"},400);
        $(".img-list li").remove();
        $(".img-list").eq(0).css('z-index','9');
        var li = document.createElement("li");
        li.innerHTML = '<div class="progress"><span></span><p class="upz">上传中</p></div>';
        $(".img-list").eq(0).append($(li));
        ImageFileResize(this.files[0], 800, 800, function (dataUrl) {
        	$('.progress span').animate({"width":"98%"},500);
        	$(li).css('background-image','url('+dataUrl+')');
            $(filechooser).next().val(dataUrl);
            $('.progress span').animate({"width":"100%"},100,function(){
            	$(filechooser).next().next().css('display','block');
            	$('.upz').css('display','none');
            });
            $(".img-list > li").unbind('click');
            $(".img-list:eq(0) > li").click(function(){
	        	filechooser.click();
	        });
        });
    };
    function ImageFileResize(file, maxWidth, maxHeight, callback) {
         var Img = new Image;
         var canvas = document.createElement('canvas');
         var ctx = canvas.getContext('2d');
         var count=1;

         Img.onload = function() {
             if (Img.width>maxWidth || Img.height>maxHeight) {
                 var bili = Math.max(Img.width/maxWidth, Img.height/maxHeight);
                 canvas.width = Img.width/bili/count;
                 canvas.height = Img.height/bili/count;
             }else{
                 canvas.width = Img.width/count;
                 canvas.height = Img.height/count;
             }
             ctx.drawImage(Img, 0, 0, Img.width, Img.height, 0, 0, canvas.width, canvas.height);
             var imgDataUrl = canvas.toDataURL('image/jpeg',0.5);
             if((imgDataUrl.length/1024)>400){
            	 	
                     count=count+1;
                     try{
                         Img.src = window.URL.createObjectURL(file);
                     }catch(err){
                         try{
                             Img.src = window.webkitURL.createObjectURL(file);
                         }catch(err){
                             alert(err.message);
                         }
                     }
             }else{
                 callback(imgDataUrl);
             }
         };

         try{
             Img.src = window.URL.createObjectURL(file);
         }catch(err){
             try{
                 Img.src = window.webkitURL.createObjectURL(file);
             }catch(err){
                 alert(err.message);
             }
         }
     }
}

function userPicCanvasCaompress(){
	var filechooser=document.getElementById('userPic');
	
    filechooser.onchange = function () {
    	if (!this.files.length) return;
    	$('.progress span').animate({"width":"100%"},400);
        $(".img-list li").remove();
        $(".img-list").eq(1).css('z-index','9');
        var li = document.createElement("li");
        li.innerHTML = '<div class="progress"><span></span><p class="upz">上传中</p></div>';
        $(".img-list").eq(1).append($(li));
        ImageFileResize(this.files[0], 1000, 1000, function (dataUrl) {
        	$('.progress span').animate({"width":"98%"},500);
        	$(li).css('background-image','url('+dataUrl+')');
            $(filechooser).next().val(dataUrl);
            
            $('.progress span').animate({"width":"100%"},100,function(){
            	$(filechooser).next().next().css('display','block');
            	$('.upz').css('display','none');
            });
            $(".img-list > li").unbind('click');
            $(".img-list:eq(1) > li").click(function(){
	        	filechooser.click();
	        });
        });
    };
    function ImageFileResize(file, maxWidth, maxHeight, callback) {
         var Img = new Image;
         var canvas = document.createElement('canvas');
         var ctx = canvas.getContext('2d');
         var count=1;

         Img.onload = function() {
             if (Img.width>maxWidth || Img.height>maxHeight) {
                 var bili = Math.max(Img.width/maxWidth, Img.height/maxHeight);
                 canvas.width = Img.width/bili/count;
                 canvas.height = Img.height/bili/count;
             }else{
                 canvas.width = Img.width/count;
                 canvas.height = Img.height/count;
             }

             ctx.drawImage(Img, 0, 0, Img.width, Img.height, 0, 0, canvas.width, canvas.height);
             var imgDataUrl = canvas.toDataURL('image/jpeg',0.5);
             if((imgDataUrl.length/1024)>400){
                     count=count+1;
                     try{
                         Img.src = window.URL.createObjectURL(file);
                     }catch(err){
                         try{
                             Img.src = window.webkitURL.createObjectURL(file);
                         }catch(err){
                             alert(err.message);
                         }
                     }

             }else{
                 callback(imgDataUrl);
             }
         };
         try{
             Img.src = window.URL.createObjectURL(file);
         }catch(err){
             try{
                 Img.src = window.webkitURL.createObjectURL(file);
             }catch(err){
                 alert(err.message);
             }
         }
     }
}
/**
*显示第二模块
*/
function next(){
	$('.uForm').css('display','none');
	$('.idCardPic').css('display','block');
	$('.banner').css('display','none').eq(0).css('display','block');
}
/**
*显示第三模块
*/
function next1(){
	$('#autonym > div').css('display','none');
	$('.banner').css('display','none').eq(1).css('display','block');
	$('.userPicImg').css('display','block');
}
/**
* 提交表单 ajax
*/
function formUpdate(){
	var realName = $('#userName').val(),
	     idCard = $('#userCard').val(),
	     address = $('#userPosi').val(),
	     base64one = $('.imgBase').eq(0).val(),
	     base64Two = $('.imgBase').eq(1).val();
	$.ajax({
		type : "post",
		url: ctx+"/setUserAuthInfo",
		data: {
			realName: realName,
			idCard: idCard,
			address: address,
			base64one: base64one,
			base64Two: base64Two
		},
		success: function(data){
			eval("data="+data);
			if(data.code==200){
				//成功跳转页面
					window.location.href=ctx+"/w/goSuccessAuthPage";
			}else if(data.code==204){
				$(".mask").css("display","none");
				$(".log-mask").css("display","block");
				//window.location.href=ctx+"/goSuccessAuthPage";
			}else{
				window.location.href=ctx+"/goFailAuthPage";
			}
		},
		error:function(){
			window.location.href=ctx+"/goFailAuthPage";
		}
	});
}
/**
*手机号  正则--fan
*/
var addressRule = /^(((13[0-9]{1})|(14[4-7]{1})|(15[0-9]{1})|(170)|(17[6-8]{1})|(18[0-9]{1}))+\d{8})$/;
/**
* 身份证   正则--fan
*/
var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;

// 自定义手机号validate--fan
jQuery.validator.addMethod("phoneNum", function(value, element) { 
    return this.optional(element) || addressRule.test(value);
}, "请输入正确的手机号");
//自定义身份证 validate--fan
jQuery.validator.addMethod("userIdNum", function(value, element) { 
    return this.optional(element) || reg.test(value);
}, "请输入正确身份证号");
// validate 手动认证校验--fan
var autonym =$("#autonym").validate({
	success: function(element){
		element.css('display','none');
	},

	errorPlacement : function(error, element) {
		
		//$(".error").html("");
			error.css({

			}).appendTo(element.next().addClass("error").css('display','block'));
			
		},
	submitHandler : function(form) {
		next();
	},
	rules : {
		userName: {
			required:true
		},
		userCard: {
			required:true,
			maxlength:18,
			userIdNum:true,
			 remote:{
				url: ctx+"/isHaveIdCard",     //后台处理程序
			    type: "post",               //数据发送方式
			    data: {                     //要传递的数据
			    	idCard: function() {
			            return $("#userCard").val();
			        }
			    }
			} 
		},
		userPosi: {
			required:true
		}
	},
	messages : {
		userName: {
			required:'请输入真实姓名'
		},
		userCard: {
			required:'请输入身份证号',
			minlength:'身份证号不能大于18位',
			userIdNum: '请输入正确身份证号',
			remote:'身份证号已绑定'
		},
		userPosi: {
			required:'请输入宿舍位置'
		}
	}
});