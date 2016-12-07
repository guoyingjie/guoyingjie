package com.broadeast.util;

import java.math.BigDecimal;

import org.apache.log4j.Logger;


/**
 * @ClassName: BigDecimalUtil
 * @Description: BigDecimal计算工具类
 * @author xmm
 * @date 2014年12月5日 下午4:17:57
 */

public class BigDecimalUtil {

	private BigDecimalUtil(){}

	private static final int scale = 4;
	private static Logger logger = Logger.getLogger(BigDecimalUtil.class);

	/**
	 * @Title: add 
	 * @Description: 将两个 BigDecimal相加
	 * @param @param a
	 * @param @param b
	 * @return BigDecimal    返回类型 
	 * @author xmm
	 * @throws
	 */
	public static BigDecimal add(BigDecimal a,BigDecimal b) {
		a=a.add(b);
		return getScale(a);
	}

	/**
	 * @Title: subtract 
	 * @Description: 两个BigDecimal相减 
	 * @param @param a 被减数
	 * @param @param b 减数
	 * @param @return    设定文件 
	 * @return BigDecimal    返回类型 
	 * @author xmm
	 * @throws
	 */
	public static BigDecimal subtract(BigDecimal a,BigDecimal b) {
		a=a.subtract(b);
		return getScale(a);
	}

	/**
	 * @Title: multiply 
	 * @Description: 两个BigDecimal相乘 
	 * @param @param a 
	 * @param @param b
	 * @param @return    设定文件 
	 * @return BigDecimal    返回类型 
	 * @author xmm
	 * @throws
	 */
	public static BigDecimal multiply(BigDecimal a,BigDecimal b) {
		a=a.multiply(b);
		return getScale(a);
	}

	/**
	 * @Title: divide 
	 * @Description: 两个BigDecimal相除，a/b
	 * @param @param a
	 * @param @param b
	 * @param @return    设定文件 
	 * @return BigDecimal    返回类型 
	 * @author xmm
	 * @throws
	 */
	public static BigDecimal divide(BigDecimal a,BigDecimal b) {
		a=a.divide(b);
		return getScale(a);
	}

	/**
	 * 返回a与b的乘积。看清楚！返值是个什么！
	 * @param a
	 * @param b
	 * @return 正常时返回 a*BigDecimal(b)；当b不是数字时，返回0;
	 */
	public static BigDecimal multiply(BigDecimal a,String b) {
		if(NumberUtils.isNotNumber(b)) b="0";
		return multiply(a,new BigDecimal(b));
	}
	
	/**
	 * 返回a与b的乘积。看清楚！返值是个什么！
	 * @param a
	 * @param b
	 * @return 正常时返回 a*BigDecimal(b)；当b是null时，返回0;
	 */
	public static BigDecimal multiply(BigDecimal a,Integer b) {
			return multiply(a,new BigDecimal((b==null?0:b)));
	}
	
	/**
	 * 返回a与b的乘积。看清楚！返值是个什么！
	 * @param a
	 * @param b
	 * @return 正常时返回 a*BigDecimal(b)；当b是null时，返回0;
	 */
	public static BigDecimal multiply(BigDecimal a,Double b) {
			return multiply(a,new BigDecimal((b==null?0:b)));
	}
	
	/**
	 * 将BigDecimal 转化为精度为4的格式，超过部分直接舍去
	 * @param b
	 * @return
	 */
	public static BigDecimal getScale(BigDecimal b){
//		System.out.println("-----"+b);
		return b.setScale(scale, BigDecimal.ROUND_FLOOR );
	}
	
	/**
	 * 判断BigDecimal a BigDecimal b的double值是否相等<br>
	 * 因为BigDecimal自身的equals 方法是值与精度都相等才返回true
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean doubleValueEquals(BigDecimal a,BigDecimal b){
		return a.doubleValue()==b.doubleValue();
	}
	
}
