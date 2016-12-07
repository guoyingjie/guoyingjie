package com.broadeast.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 判断日期是否为今天
 * 
 * @author guoyingjie
 * 
 */
public class CalendarUtil {
	/**
	 * 是否是今天
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isToday(String date) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date datetime = null;
		try {
			datetime = sd.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return isTheDay(datetime, new Date());
	}

	/**
	 * 是否是相对于今天的指定日期 by:cuimiao
	 * 
	 * @param date
	 *            指定日期
	 * @param num
	 *            日期平移量，正数为日期前移，负数为后移 例：date 为2016-05-17 num为-1 今天为2016-05-18
	 *            返回true
	 * @return
	 */
	public static boolean isTheDay(String date, int num) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date datetime = null;
		try {
			datetime = sd.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return isTheDay(datetime, getTheDay(num));
	}

	/**
	 * 根据当天，获取到第num天的日期 例：今天为 2016-05-18 10:20:12 num为-1 则返回2016-05-17 10:20:12
	 * by:cuimiao
	 * 
	 * @Description
	 * @param num
	 * @return
	 */
	public static Date getTheDay(int num) {
		Date date = new Date();// 取时间
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendar.DATE, num);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		return date;
	}

	/** ------------------------新增加的时间工具类------------------------------ */
	/**
	 * 返回timeAfter与timeBefore相差的毫秒数
	 * 
	 * @Description
	 * @param timeBefore
	 * @param timeAfter
	 * @return
	 */
	public static int timeDiffer(Date timeBeforeDate, Date timeAfterDate) {
		long timeBefore = timeBeforeDate.getTime();
		long timeAfter = timeAfterDate.getTime();
		int differValue = (int) (timeAfter - timeBefore);
		return differValue;
	}

	/**
	 * 以字符串形式返回当前毫秒数
	 * 
	 * @return
	 */
	public static String getCurTime() {
		return System.currentTimeMillis() + "";
	}

	/**
	 * 
	 * @Description 得到当日日期 yyyy-MM-dd格式字符串
	 * @return
	 */
	public static String getTodayDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}

	/**
	 * 
	 * @Description 得到此刻时间 yyyy-MM-dd hh:mm:ss格式字符串
	 * @return
	 */
	public static String getTodayTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	/**
	 * 将String转化为Date(自动识别String格式，支持yyyy-MM-dd和yyyy-MM-dd HH:mm:ss两种格式)
	 * 
	 * @Description
	 * @param dateStr
	 * @return
	 */
	public static Date stringToDate(String dateStr) {
		// String dateString = "2012-12-06 13:01:11";
		Date date = null;
		try {
			SimpleDateFormat sdf = null;
			if (dateStr.indexOf(":") != -1) {
				// 精确到秒
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			} else {
				// 精确到日
				sdf = new SimpleDateFormat("yyyy-MM-dd");
			}
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 
	 * @Description 将日期转化为 yyyy-MM-dd HH:mm:ss格式字符串(24时)
	 * @return
	 */
	public static String dateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (date != null) {
			return sdf.format(date);
		} else {
			return null;
		}
	}

	/** ---------------------------------------------------------- */

	/**
	 * 是否是指定日期
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static boolean isTheDay(final Date date, final Date day) {
		return date.getTime() >= CalendarUtil.dayBegin(day).getTime()
				&& date.getTime() <= CalendarUtil.dayEnd(day).getTime();
	}

	/**
	 * 获取指定时间的那天 00:00:00.000 的时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date dayBegin(final Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 获取指定时间的那天 23:59:59.999 的时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date dayEnd(final Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

	/**
	 * 获得当天的凌晨开始时间
	 * 
	 * @return
	 */
	public static String getBegin() {
		Date date = new Date();
		SimpleDateFormat sds = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String begin = sds.format(date);
		return begin;
	}

	/**
	 * 获得当天的结束时间
	 * 
	 * @return
	 */
	public static String getEnd() {
		Date date = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		String end = sd.format(date);
		return end;
	}

	/**
	 * 获得当前时间
	 * 
	 * @return
	 */
	public static String currentTime() {
		Date date = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sd.format(date);
		return currentTime;
	}

	/**
	 * 
	 * @Description:获得当前时间YYYYMMdd
	 * @author songyanbiao
	 * @Date 2016年6月14日
	 * @return
	 */
	public static String currentDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
		Calendar cal = Calendar.getInstance();
		return sdf.format(cal.getTime());
	}

	/**
	 * 
	 * @param args
	 */
	public static int timeDifference(String time1, String time2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int timevalues = 0;
		try {
			Date d1 = df.parse(time1);
			Date d2 = df.parse(time2);
			long diff = d2.getTime() - d1.getTime();// 这样得到的差值是微秒级别
			// long days = diff / (1000 * 60 * 60 * 24);
			// long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
			// long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 *
			// 60))/(1000* 60);
			// System.out.println(""+days+"天"+hours+"小时"+minutes+"分");
			timevalues = (int) (diff / 60 / 1000);
		} catch (Exception e) {
		}
		return timevalues;
	}
	/**
	   * @Description  获得当月的天数的集合
	   * @date 2016年10月10日下午3:53:21
	   * @author guoyingjie
	   * @return
	   */
    public static String getDateText(){
  	  StringBuilder sb = new StringBuilder();
  	  Calendar a = Calendar.getInstance();  
		  a.set(Calendar.DATE, 1);//把日期设置为当月第一天  
		  a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天  
		  int maxDate = a.get(Calendar.DATE);  
		  String max = new SimpleDateFormat("MM").format(new Date());
		  for (int i = 1; i <= maxDate; i++) {
			  if(i<=9){
				  sb.append(max).append("-0"+i).append(",");
			  }else{
				  if(i==maxDate){
					  sb.append(max).append("-"+i);
				  }else{
					  sb.append(max).append("-"+i).append(",");
				  }
			  }
		  }
		  return sb.toString();  
    }
}
