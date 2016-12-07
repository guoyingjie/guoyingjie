package com.broadeast.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

public class HttpPool {
	
	private static int maxTotal=10;								// 最大链接数
	private static int DefaultMaxPerRoute=10;						//默认每个路由最大线程量
//	private static int maxPerRoute=100;								//每个路由最大线程量		
	private static int socketTimeout=5000;							//链接超时时间		
	private static int connectTimeout=5000;							//链接超时时间				
	private static int connectionRequestTimeout=5000;				//请求超时时间			
	
	private static RequestConfig requestConfig;						//		
	private static CloseableHttpClient httpClient;					//			
	private static PoolingHttpClientConnectionManager pcm;			//				
	
	
	static{
		pcm = new PoolingHttpClientConnectionManager();
		// Increase max total connection to 200
		pcm.setMaxTotal(maxTotal);
		// Increase default max connection per route to 20
		pcm.setDefaultMaxPerRoute(DefaultMaxPerRoute);
		// Increase max connections for localhost:80 to 50
	}
	
	
	public HttpPool(String host, int port,int maxPerRoute){
	    super();
	    HttpHost localhost = new HttpHost(host, port);
		pcm.setMaxPerRoute(new HttpRoute(localhost), maxPerRoute);
		httpClient = HttpClients.custom().setConnectionManager(pcm).build();
		requestConfig = RequestConfig.copy(RequestConfig.DEFAULT)
				.setSocketTimeout(socketTimeout)
				.setConnectTimeout(connectTimeout)
				.setConnectionRequestTimeout(connectionRequestTimeout).build();
    }

	public  HttpPost getPost(String url,List <NameValuePair> nvps) throws UnsupportedEncodingException{
		
		HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        httpPost.setEntity(new UrlEncodedFormEntity(nvps,Consts.UTF_8));
		return httpPost;
		
	}
	
	public  CloseableHttpResponse doPost(String url,List <NameValuePair> nvps) throws ClientProtocolException, IOException{
		HttpPost post=getPost(url, nvps);
		return httpClient.execute(post);
	} 
	
	public  String getPostResult(String url,List <NameValuePair> nvps){
		try {
			CloseableHttpResponse response=doPost(url, nvps);
			int statue=response.getStatusLine().getStatusCode();
			//System.out.println(statue);
			if(statue==200){
				HttpEntity entity=response.getEntity();
				return EntityUtils.toString(entity);
				
			}else return "error";
		} catch (ClientProtocolException e) {
//			e.printStackTrace();
			return "error";
		} catch (IOException e) {
//			e.printStackTrace();
			return "error";
		}
		
	}
	
	
	

}
