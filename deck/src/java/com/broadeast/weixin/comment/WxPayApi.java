package com.broadeast.weixin.comment;
 
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hupeng on 2015/7/28.
 */
public class WxPayApi {

	private static Log logger = LogFactory.getLog(WxPayApi.class);

	public static Map<String, Object> UnifiedOrder(UnifiedOrderReqData reqData)throws IOException, SAXException, ParserConfigurationException {
		String res = HttpService.doPost(Configure.UNIFIED_ORDER_API, reqData);
		return XMLParser.getMapFromXML(res);
	}

	/**
	 * @Description  获得用户的唯一标示openid
	 * @date 2016年6月14日下午3:04:13
	 * @author guoyingjie
	 * @param appid
	 * @param appSecret
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static String getOpenid(String appid, String appSecret, String code)
			throws Exception {
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ appid
				+ "&secret="
				+ appSecret
				+ "&code="
				+ code
				+ "&grant_type=authorization_code";
		String res = HttpService.doGet(requestUrl);
		Map<String, Object> resultMap = JsonUtil.fromJson(res, HashMap.class);
		if (resultMap.get("openid") == null) {
			return null;
		}

		return resultMap.get("openid").toString();
	}
	/**
	 * @Description  同过code获得openid,网页授权接口调用凭证access_token
	 * @date 2016年6月14日下午3:04:44
	 * @author guoyingjie
	 * @param appid
	 * @param appSecret
	 * @param code
	 * @throws Exception
	 */
	public static Map<String, Object> getOpenidAndAccessToken(String appid, String appSecret, String code) throws Exception{
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ appid
				+ "&secret="
				+ appSecret
				+ "&code="
				+ code
				+ "&grant_type=authorization_code";
		String res = HttpService.doGet(requestUrl);
		Map<String, Object> resultMap = JsonUtil.fromJson(res, HashMap.class);
		if (resultMap.get("openid") == null) {
			return null;
		}
		if(!resultMap.isEmpty()&&resultMap.size()>0){
			return resultMap;
		}else{
			return null;
		}
	}
}
