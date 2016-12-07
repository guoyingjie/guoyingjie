package com.broadeast.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 配置文件操作类<br>
 * 用法：添加一个静态变量，<br>
 * init方法里添加赋值的语句即可。
 * 
 * @ToDoWhat 
 * @author xmm
 */
public class ConfigurationManager {
	static Logger log=Logger.getLogger(ConfigurationManager.class);
	
	public static Properties COMM;
	
	static{
		init();
	}
	
	private ConfigurationManager() {
	}
	public static void init(){
		try {
			ConfigurationManager.COMM=getPropertiesByName("commen.properties");
			
		} catch (IOException e) {
			e.printStackTrace();
			log.error("propeties file init error----", e);
		}
		
	}
	
	public static Properties getPropertiesByName(String propname) throws IOException {
		Properties properties = new Properties();
		InputStream inputStream = ConfigurationManager.class.getClassLoader().getResourceAsStream(propname);
		properties.load(inputStream);
		return properties;
	}

	
	public static void main(String[] args) throws Exception {
		
		//System.out.println(ConfigurationManager.COMM.getProperty("www"));
	}







}
