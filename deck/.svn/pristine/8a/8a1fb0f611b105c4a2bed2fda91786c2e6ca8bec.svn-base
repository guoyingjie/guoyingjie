package com.broadeast.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @ToDoWhat 
 * @author xmm
 */
public class InitContext {
	
	private static ApplicationContext context;
	
	public static void init(){
		context=new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	static {
		init();
	}

	public static <T> T getBean(String beanName,Class<T> clazz){
		
		return context.getBean(beanName, clazz);
	}
	
	
	
}
