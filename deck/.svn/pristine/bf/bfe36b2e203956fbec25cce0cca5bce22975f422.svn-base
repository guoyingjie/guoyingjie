package com.wap.alipay.config;

import com.broadeast.util.PropertiesParam;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088111293209220";
	
	// 交易安全检验码，由数字和字母组成的32位字符串
	// 如果签名方式设置为“MD5”时，请设置该参数
	public static String key = "q3cs2qv96n4gwac9i2s0vyydl3hdiea1";
	
    // 商户的私钥
    // 如果签名方式设置为“0001”时，请设置该参数
//	public static String private_key = "";
	public static String private_key = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANp3+s0fn5y/xAeYPPlH0g7saBfV2PfEyKrKaxaUZY59FYvi4ZMxBBJ+gTFFWntfAuTVm/2eyjl4cuacKNTCvWbSs7FkoMcot6W/OgNjvwrB30NV39jXv/9YfyQmhbOPRq0Xm8uSvbqOnO0Gjn+0Df3Od5vKoOA0dFHtXdSO0dBhAgMBAAECgYEAsNs7/15AWiarzPIjOjI4DLi4s8L8d+eoAwsGkirj4+vCy5GJxxyBDW+3qmlYjFMoBpQeSbyIPOoTKjj0nPhhViKBaI3T6b5irnGRPwP3lyBZfCEx5fB60oueuex9eXrG/z1DyZCDZBinsT+JSxKmVZDu+BhuFtezqfb80uyokAECQQD5U/QKOk01Br/VTGC7+79xXawZwCt48rYXWAQvJBmWuPyHKaP9p7zpH0FhSLVkDQw4FqJ4T2nfhtpELghsdhuhAkEA4FCfLfeJKOR0+15IitZWzv4yP48npBHglSKoq4pgCZgS2BikTfdnwQPo+Ah3wJvuLy7VmR+VAwfYDpm44kV8wQJAcTIWZ9ZUWzC3Q2WrvWzsW9HFYuOGWSEIXszNCYIARBe0PsE+/Nh8wfI4wqw5/jSWSZitJqYXUS5uReoIVQXCQQJBAJv9lOS7eme0j+FqjJn4OkASJ9FpdRC02e/8PT2lqemiFqCuajxJ654fYPhzswt/pGCteh7VBqQn5Ukp/5di6UECQQCI6sOj2M0/ISVHOBBiDk4+wjSANj3vYnMyEHkwYtdZ/JrFJ/Veeqy9jnEO2895u9EpMmFy9jQAz+YtMeaAhHIL";


    // 支付宝的公钥
    // 如果签名方式设置为“0001”时，请设置该参数
//	public static String ali_public_key = "";
	public static String ali_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCCys/GsmcKxyQWHZY9i/gK0Stnn2/3PMuo+IJc 0pMcgKJPZgOFbR3kkORt16aSDUHScbYtVHDpYTD2wcMvwe8GlT/bo2e5G8+dA6o7305ajMK1502o ZWo05PcwbG35eNPvz8fIC2CFt3m0rBmY5m+28Pz0OBadwxFz0MmStgy2NwIDAQAB";

	
	//返回格式
	public static String format = "xml";
	//必填，不需要修改
	//返回格式
	public static String v = "2.0";
	//必填，不需要修改seller_account_name 

	//交易自动关闭时间，单位为分钟。
	public static String pay_expire="30";
	
	//支付宝网关地址
	public static String ALIPAY_GATEWAY_NEW = "http://wappaygw.alipay.com/service/rest.htm?";

	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	/**东方高盛的支付宝账号，写死的*/
	public static String seller_email = "support@solarsys.cn";
	
	//服务器异步通知页面路径
//	public static String notify_url = "http://xiao1ming2.xicp.net:33257/deck/pay/aliPayNotify";
//	public static String notify_url = "http://portal.solarsys.cn/deck/pay/aliPayNotify";
	public static String notify_url = PropertiesParam.DeckUrl+"deck/pay/aliPayNotify";

	//需http://格式的完整路径，不能加?id=123这类自定义参数

	//默认页面跳转同步通知页面路径
//	public static String call_back_url = "http://xiao1ming2.xicp.net:33257/deck/mobile/result.jsp";
//	public static String call_back_url = "http://portal.solarsys.cn/deck/mobile/result.jsp";
	public static String call_back_url = PropertiesParam.DeckUrl+"deck/w/resultPage";

	//需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/
	
	
//	//服务器异步通知页面路径
//	String notify_url = "http://商户网关地址/WS_WAP_PAYWAP-JAVA-UTF-8/notify_url.jsp";
//	//需http://格式的完整路径，不能加?id=123这类自定义参数

//	//页面跳转同步通知页面路径
//	String call_back_url = "http://127.0.0.1:8080/WS_WAP_PAYWAP-JAVA-UTF-8/call_back_url.jsp";
//	//需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/

	/**
	 * 校园卡运营商的商家后台转账接口，用于用户给校园卡充值时钱要加到运营商的商家账号上
	 */
	//public static String storeUserAmountAddmoney_url="http://v3.solarsys.cn/cloud/allLog/AddMoney";
//	public static String storeUserAmountAddmoney_url = "http://localhost/sun/allLog/AddMoney";
//	public static String storeUserAmountAddmoney_url = "http://portalc1.zhy43807.zhihui.chinaccnet.cn/sun/allLog/AddMoney";

	
	
	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";

	// 字符编码格式 目前支持  utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式，选择项：0001(RSA)、MD5
	public static String sign_type = "0001";
	// 无线的产品中，签名方式为rsa时，sign_type需赋值为0001而不是RSA

}
