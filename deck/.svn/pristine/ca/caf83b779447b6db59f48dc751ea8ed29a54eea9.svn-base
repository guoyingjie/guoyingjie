package com.broadeast.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {
	
	/**
	 * 线程共享一个simpledateformat对象,多线程下处理对象创建问题
	 */
	private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    /**
     * @Description 字符串转化为时间
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Date parse(String dateStr) throws ParseException {
        return threadLocal.get().parse(dateStr);
    }

    /**
     * @Description  时间转化为字符串
     * @param date
     * @return
     */
    public static String format(Date date) {
        return threadLocal.get().format(date);
    }
	
	/**
	 * 获取小时数和分钟数拼接后转换为int 列子：2014-09-23 08:32:54-->832
	 * @return
	 */
	public static int getHHMMInt(){
		SimpleDateFormat sdf=new SimpleDateFormat("HHmm");
		String str=sdf.format(new Date());
		return Integer.parseInt(str);
	}
	
	/**
	 * 当前时间转化为yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getStringDate(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	
	/**
	 * 给定date时间转化为yyyy-MM-dd HH:mm:ss,可能为null
	 * @return 
	 */
	public static String getStringDate(Date date){
		if(date==null){return null;}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	/**
	 * 字符串时间转化为Date
	 * @param str
	 * @return
	 */
	public static Date getDateFromString(String str){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 日期加减
	 * 0按小时收费；1按天;2按月
	 */
	public static String datePlus(int type,int num){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar now=Calendar.getInstance();
		if(type==0){//0按小时
			now.add(Calendar.HOUR, num);
			
		}else if(type==1){//1按天
			now.add(Calendar.DAY_OF_YEAR, num);
		}else if(type==2){//2按月
			now.add(Calendar.MONTH, num);
		}else if(type==3){//按一年
			now.add(Calendar.YEAR, num);
			
		}else if(type==4){//按两年
			now.add(Calendar.YEAR, num*2);
		}
		return DateUtil.format(now.getTime());
	}
	/**
	 * 日期加减
	 * 0按小时收费；1按天;2按月
	 */
	public static String datePlus(int type,int num,String priceNum){
		
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar now=Calendar.getInstance();
		//System.out.println(sdf.format(now.getTime()));
			if(type==0){//0按小时
				
				now.add(Calendar.HOUR, num*Integer.parseInt(priceNum));
			}else if(type==1){//1按天
				
				now.add(Calendar.DAY_OF_YEAR, num*Integer.parseInt(priceNum));
			}else if(type==2){
				
				now.add(Calendar.MONTH, num*Integer.parseInt(priceNum));
			}
		return DateUtil.format(now.getTime());
	}
	/** 
	 * 
	 * 根据到期时间和当前时间比较判断日期加减
	 * 0按小时收费；1按天;2按月
	 */
	public static String datePluss(int type,int num,String date){
//		System.out.println(type);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar now=Calendar.getInstance();
		try {
			Date test = DateUtil.parse(date);
			now.setTime(test);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(type==0){//0按小时
			now.add(Calendar.HOUR, num);
			
		}else if(type==1){//1按天
			now.add(Calendar.DAY_OF_YEAR, num);
		}else if(type==2){//2按月
			now.add(Calendar.MONTH, num);
		}else if(type==3){//按一年
			now.add(Calendar.YEAR, num);
			
		}else if(type==4){//按两年
			now.add(Calendar.YEAR, num*2);
		}
		return DateUtil.format(now.getTime());
	}
	
	/**
	 * 根据到期时间和当前时间比较判断日期加减
	 * 0按小时收费；1按天;2按月
	 */
	public static String newDatePluss(int type,int num,String data,String priceNum){
		int nums=Integer.parseInt(priceNum);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar now=Calendar.getInstance();
		//System.out.println(sdf.format(now.getTime()));
		try {
			Date test = DateUtil.parse(data);
			now.setTime(test);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(type==0){//0按小时
			now.add(Calendar.HOUR, num*nums);
				
		}else if(type==1){//1按天
			now.add(Calendar.DAY_OF_YEAR, num*nums);
		}else if(type==2){//2按月
			now.add(Calendar.MONTH, num*nums);
		}
		
		return DateUtil.format(now.getTime());
	}
	 
	public static String getDateNoP(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}
	/**
	 * 此方法是对当前时间和过期时间的比较
	 * 
	 * @param DATE1 当前时间
	 * @param DATE2  用户的缴费记录时间
	 * @return 1--当前时间>用户的缴费记录的时间(过期)
	 *        -1/0 --当前时间<用户缴费记录的时间(没有过期)
	 */
	public static int compareDate(String DATE1, String DATE2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date dt1 = DateUtil.parse(DATE1);
			Date dt2 = DateUtil.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() <= dt2.getTime()) {
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	public static void main(String[] args) {
		System.out.println(datePlus(0,2));
	}

}
