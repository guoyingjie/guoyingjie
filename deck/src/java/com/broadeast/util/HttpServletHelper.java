package com.broadeast.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;


public class HttpServletHelper {
	private static Map<String, String> contentMap = new HashMap<String, String>();

	static {
		contentMap.put("xml", "text/xml;charset=");
		contentMap.put("json", "text/json;charset=");
		contentMap.put("xml_utf8", "text/xml;charset=UTF-8");
		contentMap.put("json_utf8", "text/json;charset=UTF-8");
		contentMap.put("html_utf8", "text/html;charset=UTF-8");
	}

	public static void WriteXml(HttpServletResponse response, String writeText) throws Exception {
		write(response, writeText, "xml_utf8");
	}

	public static void WriteHtml(HttpServletResponse response, String writeText) throws Exception {
		write(response, writeText, "html_utf8");
	}

	public static void WriteXml(HttpServletResponse response, String writeText, String charset) throws Exception {
		write(response, writeText, "xml", charset);
	}

	public static void WriteJSON(HttpServletResponse response, String writeText) throws Exception {
		write(response, writeText, "json_utf8");
	}

	public static void WriteJSON(HttpServletResponse response, String writeText, String charset) throws Exception {
		write(response, writeText, "json", charset);
	}

	private static void write(HttpServletResponse response, String writeText, String contentType) throws Exception {
		write(response, writeText, contentType, "");
	}

	private static void write(HttpServletResponse response, String writeText, String contentType, String charset) throws Exception {
		response.setContentType(contentMap.get(contentType) + charset);
		PrintWriter out = response.getWriter();
		out.println(writeText);//
		out.flush();
		out.close();
	}

	/**
	 * @author tian
	 * @date 2013-6-3
	 * @return 访问servlet，并获取返回值
	 */

	public String getRequestServlet(String strURL) throws Exception {
		String strCharset = "UTF-8";
		if ((strURL == null) || (strURL.length() == 0))
			return "";

		String[] arrContent = null;
		if (strURL.indexOf("?") > -1) {
			arrContent = StringUtils.string2Array(strURL.substring(strURL.indexOf("?") + 1), '&', false);
			strURL = strURL.substring(0, strURL.indexOf("?"));
		}
		StringBuilder sb = new StringBuilder();
		HttpURLConnection con = null;
		URL url = new URL(strURL);
		con = (HttpURLConnection) url.openConnection();
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setInstanceFollowRedirects(true);
		con.setRequestMethod("GET");
		con.addRequestProperty("Content-type", "application/x-www-form-urlencoded");
		con.setUseCaches(false);
		con.connect();

		if ((arrContent != null) && (arrContent.length > 0)) {
			StringBuilder sbContent = new StringBuilder();
			for (int i = 0; i < arrContent.length; i++) {
				if ((arrContent[i] == null) || (arrContent[i].indexOf("=") == -1))
					continue;
				sbContent.append(arrContent[i].substring(0, arrContent[i].indexOf("="))).append("=");
				sbContent.append(URLEncoder.encode(arrContent[i].substring(arrContent[i].indexOf("=") + 1), strCharset)).append("&");
			}
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes(sbContent.toString());
			out.flush();
			out.close();
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), strCharset));
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		con.disconnect();
		return sb.toString();
	}
}
