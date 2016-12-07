package com.broadeast.weixin.comment;




import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;

import javax.servlet.http.HttpServletRequest;

/*
'============================================================================
'api说明：
'createSHA1Sign创建签名SHA1
'getSha1()Sha1签名
'============================================================================
'*/
public class Sha1Util {

	private static final String KEY = "ceafa600a2d9b2a98d36885081d16058";
	public static String getNonceStr() {
		Random random = new Random();
		return MD5.MD5Encode(String.valueOf(random.nextInt(10000)));
	}
	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}
	
   //创建签名SHA1
	public static String createSHA1Sign(SortedMap<String, String> signParams) throws Exception {
		StringBuffer sb = new StringBuffer();
		Set es = signParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			sb.append(k + "=" + v + "&");
			//要采用URLENCODER的原始值！
		}
		String params = sb.substring(0, sb.lastIndexOf("&"));
//		System.out.println("sha1之前:" + params);
//		System.out.println("SHA1签名为："+getSha1(params));
		return getSha1(params);
	}
	//Sha1签名
	public static String getSha1(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };

		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("GBK"));

			byte[] md = mdTemp.digest();
			int j = md.length;
			char buf[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 验证消息真实性
	 * @param  signature
	 * @param  timestamp
	 * @param  nonce
	 *            - 微信服务器发送的GET请求，包含signature、timestamp、nonce、echostr4个参数
	 * @return true-消息请求来自微信服务器，原样返回echostr参数<br>
	 *         false-消息验证失败
	 */
	public static boolean checkSignature(String signature,String timestamp,String nonce) {
		if (signature != null) {
			//ConfigUtil.TOKEN指服务器配置中用于生成签名的Token
			String[] tmpArr = {KEY, timestamp, nonce };
			Arrays.sort(tmpArr);
			StringBuilder buf = new StringBuilder();
			for (int i = 0; i < tmpArr.length; i++) {
				buf.append(tmpArr[i]);
			}
			if (signature.equals(encrypt(buf.toString()))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 对字符串进行sha1加密
	 * @param strSrc - 要加密的字符串
	 * @return 加密后的字符串
	 */
	public static String encrypt(String strSrc) {
		MessageDigest md = null;
		String strDes = null;

		byte[] bt = strSrc.getBytes();
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(bt);
			strDes = bytes2Hex(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Invalid algorithm.");
			return null;
		}
		return strDes;
	}

	private static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}
	
	
	
	
}
