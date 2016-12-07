package com.broadeast.util;

import org.nutz.dao.impl.NutDao;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * spring的容器工具类<br>
 * 用于在web容器中获取容器对象，并从中获取bean实例
 * 
 * @ToDoWhat 
 * @author xmm
 */
public class SpringContextUtils {
	
	public  static  JdbcTemplate JdbcTemplate;
	
	public static NutDao NutDao;
	
	public static Cache Cache;
	
	static{
		SpringContextUtils.JdbcTemplate=SpringContextHolder.getBean("jdbcTemplate");
		SpringContextUtils.NutDao=SpringContextHolder.getBean("nutDao");
		SpringContextUtils.Cache=((EhCacheCacheManager)SpringContextHolder.getBean("cacheManager")).getCache("HExpertCache");
	}
	
	


}
