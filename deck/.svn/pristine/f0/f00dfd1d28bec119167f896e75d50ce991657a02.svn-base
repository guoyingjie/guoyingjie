package com.broadeast.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.log4j.Logger;



/**
 * 创瑞短信平台：发送短信
 * 
 * @author PengL
 *
 */
public class CRSendSms {
	
	private static Logger logger = Logger.getLogger(CRSendSms.class);
	/**
	 * 
	 * @param content
	 *            发送内容
	 * @param sign
	 *            签名
	 * @param mobile
	 *            手机号码
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static Integer sendSms(String content, String sign, String mobile)
			throws Exception {

		// 创建StringBuffer对象用来操作字符串
		StringBuffer sb = new StringBuffer("http://web.cr6868.com/asmx/smsservice.aspx?");

		// 向StringBuffer追加用户名
		sb.append("name=18610079224");

		// 向StringBuffer追加密码（登陆网页版，在管理中心--基本资料--接口密码，是28位的）
		sb.append("&pwd=38DEF8B0F0B6E3D6AF1CADF5BF78");

		// 向StringBuffer追加手机号码
		sb.append("&mobile=" + mobile);

		// 追加发送时间，可为空，为空为及时发送
		sb.append("&stime=");

		// type为固定值pt extno为扩展码，必须为数字 可为空
		sb.append("&type=pt&extno");
		
		// 向StringBuffer追加消息内容转URL标准码
		sb.append("&content=" + URLEncoder.encode(content));
		
		// 加签名
		sb.append("&sign=" + URLEncoder.encode(sign));
		
		// 创建url对象
		URL url = new URL(sb.toString());

		// 打开url连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// 设置url请求方式 ‘get’ 或者 ‘post’
		connection.setRequestMethod("POST");

		// 发送
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

		// 返回发送结果
		String inputline = in.readLine();
		
		// 返回结果为‘0，20140009090990,1，提交成功’ 发送成功 具体见说明文档
		inputline = inputline.split(",")[0];
		return Integer.parseInt(inputline);
	}

//	/**
//	 * 实现方式2
//	 * 
//	 * @param tel
//	 *            手机号
//	 * @param content
//	 *            内容
//	 * @throws Exception
//	 * @throws Exception
//	 *             void
//	 * @author PengL
//	 * @time 2015年1月30日下午4:40:27
//	 */
//	public final static void send(String tel, String content) throws Exception,
//			Exception {
//		HttpClient client = new HttpClient();
//
//		String url = "http://web.cr6868.com/asmx/smsservice.aspx";
//		PostMethod postMethod = new UTF8PostMethod(url);
//
//		// 增加变量
//		postMethod.addParameter("name", "18610079224"); // 用户名
//		postMethod.addParameter("pwd", "38DEF8B0F0B6E3D6AF1CADF5BF78"); // 密码
//		// postMethod.addParameter("destinationId","124196");
//		postMethod.addParameter("sign", "东方高盛"); // 可选参数。用户签名。
//		postMethod.addParameter("content", content); // 短信内容
//		// postMethod.addParameter("origin","Ningbo, Zhejiang, China (CNNGB)" );
//		// postMethod.addParameter("origin","Shanghai, Shanghai, China  (CNSHA)" );
//		postMethod.addParameter("mobile", tel); // 手机号，多组手机号用逗号隔开
//		// postMethod.addParameter("stime", "2012-08-01 8:20:23"); //发送时间，可不填
//		postMethod.addParameter("type", "pt"); // 固定值
//		postMethod.addParameter("extno", "122"); // 可选参数，扩展码，用户定义扩展码，只能为数字
//
//		client.executeMethod(postMethod);
//		InputStream stream = postMethod.getResponseBodyAsStream();
//
//		BufferedReader br = new BufferedReader(new InputStreamReader(stream,"UTF-8"));
//		StringBuffer buf = new StringBuffer();
//		String line;
//		while (null != (line = br.readLine())) {
//			buf.append(line).append("\n");
//		}
//		// System.out.println(buf.toString());
//		postMethod.releaseConnection();
//
//	}

	public static void main(String[] args) throws Exception {
		logger.info(CRSendSms.sendSms("(dddd：d多个。)", "宽东方", "17090419056"));
//		System.out
//				.println(CRSendSms.sendSms("(dddd：d多个。)", "宽东方", "17090419056"));;
	}
}
