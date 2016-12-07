$(function() {
	var c = document.getElementById("count");
	var a = c.innerHTML;
	var b = setInterval(function() {
		a--;
		c.innerHTML = a;
		if (a == 0) {
			c.innerHTML = a;
			clearInterval(b);
			toLogin();
		}
	}, 1000);
	if (IsPC()) {
		$(".ui-div").css("display", "block");
		anim();
	} else {
		$("body")
				.css("background",
						"url(http://oss.solarsys.cn/0001/index_page/img/inbg.jpg) no-repeat");
		$("body").css("background-size", "100%");
		$("ui-div").css("display", "none");
	}
});
function IsPC() {
	var a = navigator.userAgent;
	var d = [ "Android", "iPhone", "SymbianOS", "Windows Phone", "iPod" ];
	var b = true;
	for (var c = 0; c < d.length; c++) {
		if (a.indexOf(d[c]) > 0) {
			b = false;
			break;
		}
	}
	return b;
}
function anim() {
	$(".one").animate({
		left : "51%"
	}, 500, "swing", function() {
		$(".one").animate({
			left : "50%"
		}, 200, "swing", function() {
			$(".two").animate({
				left : "49%"
			}, 500, "swing", function() {
				$(".two").animate({
					left : "50%"
				}, 200, "swing", function() {
					$(".three").animate({
						left : "50%"
					}, 600, "swing");
				});
			});
		});
	});
}
/*function toLogin() {
	var b = window.location.href;
	var c = b.indexOf("homeurl");
	if (c > 0) {
		var a = b.substring(c).split("=");
		window.location.href = decodeURIComponent(a[1])
				//	+"?gw_address=192.168.1.1&gw_port=2060&gw_id=ACA2139FF0A0&ip=192.168.0.132&mac=08:57:00:0a:99:c8&url=234&homeurl=http://api.gonet.cc/deck";
				+ "?id=ACA2139FF0A1&mac=08:57:00:0a:99:c8&ip=192.168.0.132&url=234&homeurl=http://api.gonet.cc/deck";

	} else {
		window.location.href = "http://api.gonet.cc/deck/wifidog/redirectLogin/"//"http://api.gonet.cc/deck"
				+ window.location.search;
	}
};*/

function toLogin() {
	var b = window.location.href;
	var c = b.indexOf("homeurl");
	if (c > 0) {
		var a = b.substring(c).split("=");
		window.location.href = decodeURIComponent(a[1])
				//	+"?gw_address=192.168.1.1&gw_port=2060&gw_id=ACA2139FF0A0&ip=192.168.0.132&mac=08:57:00:0a:99:c8&url=234&homeurl=http://api.gonet.cc/deck";
				+ "?id=ACA2139FF0A1&mac=08:57:00:0a:99:c8&ip=192.168.0.132&url=234&homeurl=http://charge.solarsys.cn/deck";

	} else {
		var obj=window.location; 
		var contextPath=obj.pathname.split("/")[1]; 
		var basePath=obj.protocol+"//"+obj.host+"/"+contextPath; 
		window.location.href = basePath+"/w/r/"+window.location.search;
	}
};


