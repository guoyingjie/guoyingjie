package com.broadeast.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import sun.misc.BASE64Decoder;

/**
 * 公共的字符串的处理方法
 * @author wanglei
 *
 */
public class StringUtil {

	/**
	 * 判断一个字符串是否为空，为空则返回true
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str){
		if(str==null){
			return true;
		}else if("".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 是否为大于0的数
	 * @param str
	 * @return
	 */
	public static boolean isLargerZero(String str){
		try{
			Double d = Double.valueOf(str);
			if(d>0){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			return false;
		}
	}
	
	/**格式化mac，去掉:和-，返回12位大写MAC*/
	public static String macFormat(String mac){
		mac = mac.replace("-", "");
		mac = mac.replace(":", "");
		mac = mac.toUpperCase();
		return mac;
	}
	
	/**将没有冒号的mac地址转化成有冒号的mac地址*/
	public static String macAddPoint(String mac){
		if(isNull(mac)){
			return null;
		}
		mac = mac.trim().toUpperCase();
		if(mac.length()!=12){
			return null;
		}
		byte[] gc = mac.getBytes();  
		int num = 0;
		for(int i=0;i<gc.length;i++){
	        num = (int) gc[i];  
			if(num>((int)'F')){
				return null;
			}
		}
		String newMac = "";
		for(int i=0;i<mac.length();i=i+2){
			newMac = newMac + mac.substring(i,i+2) + ":";
		}
		String temp = newMac.substring(newMac.length()-1);
		if(":".equals(temp)){
			newMac = newMac.substring(0,newMac.length()-1);
		}
		return newMac;
	}
	
	/**将当前日期转成字符串*/
	public static String dateFormatToString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");	//kk是24小时制
		String str = sdf.format(date);
		return str;
	}
	
	/**
	 * 生成十二位随机数
	 */
	public static String randCode() {
		String code = "";
		for (int i = 0; i < 3; i++) {
			Random rand = new Random();
			code += rand.nextInt(9);
		}
		return code.trim()+new Date().getTime();
	}
	/**
	 * @param base64String -- 前台经过base64编码的字符串
	 * @return  解析base64字符串后的图片流
	 */
	public static InputStream getInputStream(String base64String){
		InputStream in = null;
		if(base64String!=null&&!"".equals(base64String)){
			String formateStr = base64String.substring(base64String.indexOf("base64")+7);
			BASE64Decoder decoder = new BASE64Decoder();
	        try {
	            // Base64解码
	            byte[] bytes = decoder.decodeBuffer(formateStr);
	            for (int i = 0; i < bytes.length; ++i) {
	                if (bytes[i] < 0) {// 调整异常数据
	                    bytes[i] += 256;
	                }
	            }
	            in = new ByteArrayInputStream(bytes);
	        } catch (Exception e) {
	        	 
	        }
		} 
		return in;
	}
	
	
	
	
}






