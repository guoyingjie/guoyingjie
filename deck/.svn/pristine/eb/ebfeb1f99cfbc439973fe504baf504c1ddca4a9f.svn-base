package com.broadeast.weixin.comment;

import com.broadeast.util.PropertiesParam;

/**
 * User: rizenguo
 * Date: 2014/10/29
 * Time: 14:40
 * 这里放置各种配置数据
 */
public class Configure {


//这个就是自己要保管好的私有Key了（切记只能放在自己的后台代码里，不能放在任何可能被看到源代码的客户端程序中）
	// 每次自己Post数据给API的时候都要用这个key来对所有字段进行签名，生成的签名会放在Sign这个字段，API收到Post数据的时候也会用同样的签名算法对Post过来的数据进行签名和验证
	// 收到API的返回的时候也要用这个key来对返回的数据算下签名，跟API的Sign数据进行比较，如果值不一致，有可能数据被第三方给篡改

	private static String key = "ceafa600a2d9b2a98d36885081d16058";

	//微信分配的公众号ID（开通公众号之后可以获取到）
	private static String appID = "wxc5fb6a6dabc34dfb";

	private static String appSecret = "ceafa600a2d9b2a98d36885081d16058";

	//微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
	private static String mchID = "1332831801";

	//受理模式下给子商户分配的子商户号
	private static String subMchID = "";

	//HTTPS证书的本地路径
	//private static String certLocalPath = "D://ssh/apiclient_cert.p12";
	private static String certLocalPath = "";

	//HTTPS证书密码，默认密码等于商户号MCHID
	private static String certPassword = "1332831801";

	//是否使用异步线程的方式来上报API测速，默认为异步模式
	private static boolean useThreadToDoReport = true;

	//机器IP
	private static String ip = "";

	//以下是几个API的路径：
    //0) 统一下单
	public static  final String UNIFIED_ORDER_API = "https://api.mch.weixin.qq.com/pay/unifiedorder";
 
	//获得 code
	public static final String GETCODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?";
	
	//重定向url
	public static final String REDIRECT_URL = PropertiesParam.DeckUrl+"deck/weixinNotify/weixinPay";
	//异步通知url'
	public static final String NOTIFY_URL = PropertiesParam.DeckUrl+"deck/weixinNotify/weixinNotifyNotice";
	
	//给别人冲值余额异步通知url'
	public static final String NOTIFYBALANCE_URL = PropertiesParam.DeckUrl+"deck/weChatPayOther/wxBanlanceNotify";
	
	//给别人充值异步通知url
	public static final String NOTIFYOTHER_URL = PropertiesParam.DeckUrl+"deck/weChatPayOther/weixinNotifyNotice";
	
	public static boolean isUseThreadToDoReport() {
		return useThreadToDoReport;
	}

	public static void setUseThreadToDoReport(boolean useThreadToDoReport) {
		Configure.useThreadToDoReport = useThreadToDoReport;
	}

	public static String HttpsRequestClassName = "com.broadeast.weixin.comment.HttpService";

	public static void setKey(String key) {
		Configure.key = key;
	}

	public static void setAppID(String appID) {
		Configure.appID = appID;
	}

	public  static void setAppSecret(String appSecret) {
		Configure.appSecret = appSecret;
	}

	public static void setMchID(String mchID) {
		Configure.mchID = mchID;
	}

	public static void setSubMchID(String subMchID) {
		Configure.subMchID = subMchID;
	}

	public static void setCertLocalPath(String certLocalPath) {
		Configure.certLocalPath = certLocalPath;
	}

	public static void setCertPassword(String certPassword) {
		Configure.certPassword = certPassword;
	}

	public static void setIp(String ip) {
		Configure.ip = ip;
	}

	public static String getKey(){
		return key;
	}
	
	public static String getAppid(){
		return appID;
	}

	public static String getAppSecret() {
		return appSecret;
	}
	
	public static String getMchid(){
		return mchID;
	}

	public static String getSubMchid(){
		return subMchID;
	}
	
	public static String getCertLocalPath(){
		return certLocalPath;
	}
	
	public static String getCertPassword(){
		return certPassword;
	}

	public static String getIP(){
		return ip;
	}

	public static void setHttpsRequestClassName(String name){
		HttpsRequestClassName = name;
	}



}
