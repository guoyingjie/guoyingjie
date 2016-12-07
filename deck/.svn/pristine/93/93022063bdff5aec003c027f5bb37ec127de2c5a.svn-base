package com.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.broadeast.util.HttpPool;


/**
 * @ToDoWhat 
 * @author xmm
 */
public class HTTPTest {
	private static Logger logger = Logger.getLogger(HTTPTest.class);
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
//		final HttpPool httpPool=new HttpPool("192.168.10.167",80,11);
//		
//		for(int i=0;i<100;i++){
//			final int n=i;
//			Thread th=new Thread(""+i){@Override
//				public void run() {
//					List <NameValuePair> nvps = new ArrayList <NameValuePair>();
//					nvps.add(new BasicNameValuePair("userId", ""+n));
//					String s=httpPool.getPostResult("http://192.168.10.167./7pspspsp/ss", nvps);
//					//System.out.println("result--"+s);
//					
//				}};
//			th.start();
//			
//		}
		
		HTTPTest.postForm();
		
		
    }
	
	 /** 
     * post方式提交表单
     */  
    public static void postForm() {  
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        // 创建httppost    
        HttpPost httppost = new HttpPost("http://192.168.10.167./7pspspsp/ss");  
        // 创建参数队列    
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
        formparams.add(new BasicNameValuePair("userId", "admin"));  
        try {  
            httppost.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8")); 
            logger.info("executing request " + httppost.getURI());
           // System.out.println("executing request " + httppost.getURI());  
            CloseableHttpResponse response = httpclient.execute(httppost);  
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                   // System.out.println("--------------------------------------");  
                	 logger.info("Response content: " + EntityUtils.toString(entity, "UTF-8"));
                	//System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));  
                   // System.out.println("--------------------------------------");  
                }  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  

	 /** 
    * post方式提交表单
    */  
   public static String post(String url,List<NameValuePair> formparams) {  
       // 创建默认的httpClient实例.    
       CloseableHttpClient httpclient = HttpClients.createDefault();  
       // 创建httppost    
       HttpPost httppost = new HttpPost(url);  
       CloseableHttpResponse response = null;
       try {  
           httppost.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));  
           response = httpclient.execute(httppost);  
               HttpEntity entity = response.getEntity();  
               if (entity != null) {  
                   return EntityUtils.toString(entity, "UTF-8");
               }
       } catch (ClientProtocolException e) {  
           e.printStackTrace();  
       } catch (UnsupportedEncodingException e1) {  
           e1.printStackTrace();  
       } catch (IOException e) {  
           e.printStackTrace();  
       } finally {  
    	   if(response!=null){
    		   try {
	            response.close();
            }catch (IOException e) {
	            e.printStackTrace();
            } 
    	   }
           // 关闭连接,释放资源    
           try {  
               httpclient.close();  
           } catch (IOException e) {  
               e.printStackTrace();  
           }  
       }  
       return null;
   }  

}
