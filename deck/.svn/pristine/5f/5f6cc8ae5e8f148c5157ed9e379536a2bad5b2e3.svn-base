/**
 * 注册
 */

function trial(){
	var trialUrl;
	if(fromFw!="null"&&fromFw!=""&&fromFw!=null&&fromFw!=undefined){
		trialUrl=basePath+"/wifidog/goToTrialSuccess";
	}else{
		trialUrl=basePath+"/goToTrialSuccess";
	}
	 window.location.href=basePath=trialUrl;
}
 function toJump() {
		 window.location.href=basePath +"/ProtalUserManage/toRegister?id="+id +
	  		"&mac="+mac+"&ip="+ip+"&url="+url; 
		//document.location.href = "${basePath}/toRegister";
	} 
function login(){
		var loginUrl;
		if(fromFw!="null"&&fromFw!=""&&fromFw!=null&&fromFw!=undefined){
			loginUrl="/wifidog/redirectLogin/?gw_id="+id+"&ip="+ip+"&mac="+mac+"&url="+url+"&gw_address="+gw_address+"&gw_port="+gw_port+"&fromTrial=" + 1;

		}else{
			loginUrl="/toLogin?id="+id +"&mac="+mac+"&ip="+ip+"&url="+url + "&fromTrial=" + 1; 
		}
//		if(fromFw){
//			loginUrl="/toLogin?id="+id +"&mac="+mac+"&ip="+ip+"&url="+url + "&fromTrial=" + 1; 
//			//trialUrl="/goToTrialSuccess";
//		}else{
//			loginUrl="/wifidog/redirectLogin/?gw_id="+id+"&ip="+ip+"&mac="+mac+"&url="+url+"&gw_address="+gw_address+"&gw_port="+gw_port+"&fromTrial=" + 1;
//			//trialUrl="/wifidog/goToTrialSuccess";
//		}
		window.location.href=basePath +loginUrl;
//		"/login?gw_id="+id +"&gw_address="+s+"&gw_port="+2060+
//		"&mac="+mac+"&ip="+ip+"&url="+url + "&fromTrial=" + 1; 
//		  window.location.href=basePath +"/toLogin?id="+id +
//	  		"&mac="+mac+"&ip="+ip+"&url="+url + "&fromTrial=" + 1; 
		
//		$.ajax({
//			type : "POST",
//			url : basePath +"/toLogin",
//			data : {
//				id : id,
//				mac : mac,
//				ip : ip,
//				url : url
//			},
//			success : function(data){
//				
//			}
//			
//		})
//	
	
}