package com.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.broadeast.controller.NotifyContraller;


/**
 * @ToDoWhat 
 * @author xmm
 */
public class TestTask {
	private static Logger logger = Logger.getLogger(TestTask.class);
	public void testTimerTask(){
		logger.info("测试定时任务");
		//System.out.println("测试定时任务");
	}

	
	public static void main(String[] args) {
		
		
//		List<String> lst=new ArrayList<String>();
//		lst.add("111");
//		lst.add("222");
//		
//		Map<Object,Object> map=new HashMap<Object,Object>();
//		map.put("1","111");
//		map.put("2","222");
//		map.put("3","333");
//		
//		String listJson=JSON.toJSONString(lst);
//		String mapJson=JSON.toJSONString(map);
//		System.out.println("listJson--"+listJson);
//		System.out.println("mapJson--"+mapJson);
//	
//		Map newMap=JSON.parseObject(mapJson, Map.class);
//		System.out.println(newMap);
//		List newList=JSON.parseObject(listJson, List.class);
//		System.out.println(newList);
		
		
		Date d1=null;
		Date d2=new Date();
		logger.info(d1);
		//System.out.println(d1);
		
		
    }
}
