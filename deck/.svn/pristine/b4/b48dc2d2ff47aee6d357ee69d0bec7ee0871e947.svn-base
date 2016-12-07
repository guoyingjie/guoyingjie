package com.broadeast.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 字符串常用处理工具类
 * 
 * @author tian
 */
public class StringUtils {
	/**
	 * 
	 * 检查字符串是否为null,“”,空格,和“null” 4中情况都包含
	 * 
	 * @author tian 为空时返回ture
	 */
	public static boolean isFullNull(String str) {
		if (str == null || "".equals(str.trim()) || "null".equalsIgnoreCase(str)||str.trim().length()==0)
			return true;
		return false;
	}

	/**
	 * 去掉字符串中的html格式
	 * 
	 * @author tian 2013-5-15
	 * @param input
	 * @param length
	 * @return
	 */
	public static String splitAndFilterString(String input, int length) {
		if (input == null || input.trim().equals("")) {
			return "";
		}
		// 去掉所有html元素,
		String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
		str = str.replaceAll("[(/>)<]", "");
		int len = str.length();
		if (len <= length) {
			return str;
		} else {
			str = str.substring(0, length);
			str += "......";
		}
		return str;
	}

	/**
	 * 截取指定名称password： 形如gonghao=1231&password=123123&type=daozhen的等号后面值
	 * 
	 * @author tian 2013-5-29 例如 一对，gonghao=1231 则返回1231
	 *         多对，gonghao=1231&password=123123 也返回1231
	 */
	public static String getContent(String str, String name) {
		if (isFullNull(str) || isFullNull(name) || !str.contains("="))
			return "";
		if (str.contains("&")) {
			String strarr[] = str.split("&");
			for (int i = 0; i < strarr.length; i++) {
				if (strarr[i].contains(name)) {
					return strarr[i].split("=")[1];
				}

			}
		} else if (str.contains(name)) {
			return str.split("=")[1];
		}

		return "";
	}




	// 字符串，转换数组
	public static String[] string2Array(String s, char delim, boolean trim) {
		if (s.length() == 0)
			return new String[] {};
		List<String> a = new ArrayList<String>();
		char c;
		int start = 0, end = 0, len = s.length();
		for (; end < len; ++end) {
			c = s.charAt(end);
			if (c == delim) {
				String p = s.substring(start, end);
				a.add(trim ? p.trim() : p);
				start = end + 1;
			}
		}
		// grab the last element
		String p = s.substring(start, end);
		a.add(trim ? p.trim() : p);
		return (String[]) a.toArray(new String[a.size()]);
	}

	/**
	 * Checks if a String is empty ("") or null.
	 * 
	 * <pre>
	 * StringUtils.isEmpty(null)      = true
	 * StringUtils.isEmpty("")        = true
	 * StringUtils.isEmpty(" ")       = false
	 * StringUtils.isEmpty("bob")     = false
	 * StringUtils.isEmpty("  bob  ") = false
	 * </pre>
	 * 
	 * @param str
	 *            the string to check,may be null
	 * @return <code>true</code> if the String is empty or null
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * Checks if a String is not empty ("") or null.
	 * 
	 * <pre>
	 * StringUtils.isNotEmpty(null)      = false
	 * StringUtils.isNotEmpty("")        = false
	 * StringUtils.isNotEmpty(" ")       = true
	 * StringUtils.isNotEmpty("bob")     = true
	 * StringUtils.isNotEmpty("  bob  ") = true
	 * </pre>
	 * 
	 * @param str
	 *            the String to check,may be null
	 * @return <code>true</code> if the String is not empty or null.
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * <p>
	 * Compares two Strings, returning <code>true</code> if they are equal.
	 * </p>
	 * 
	 * <p>
	 * <code>null</code>s are handled without exceptions. Two <code>null</code>
	 * references are considered to be equal. The comparison is case sensitive.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.equals(null, null)   = true
	 * StringUtils.equals(null, "abc")  = false
	 * StringUtils.equals("abc", null)  = false
	 * StringUtils.equals("abc", "abc") = true
	 * StringUtils.equals("abc", "ABC") = false
	 * </pre>
	 * 
	 * @see java.lang.String#equals(Object)
	 * @param str1
	 *            the first String, may be null
	 * @param str2
	 *            the second String, may be null
	 * @return <code>true</code> if the Strings are equal, case sensitive, or
	 *         both <code>null</code>
	 */
	public static boolean equals(String str1, String str2) {
		return str1 == null ? str2 == null : str1.equals(str2);
	}

	/**
	 * <p>
	 * Compares two Strings, returning <code>true</code> if they are equal
	 * ignoring the case.
	 * </p>
	 * 
	 * <p>
	 * <code>null</code>s are handled without exceptions. Two <code>null</code>
	 * references are considered equal. Comparison is case insensitive.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.equalsIgnoreCase(null, null)   = true
	 * StringUtils.equalsIgnoreCase(null, "abc")  = false
	 * StringUtils.equalsIgnoreCase("abc", null)  = false
	 * StringUtils.equalsIgnoreCase("abc", "abc") = true
	 * StringUtils.equalsIgnoreCase("abc", "ABC") = true
	 * </pre>
	 * 
	 * @see java.lang.String#equalsIgnoreCase(String)
	 * @param str1
	 *            the first String, may be null
	 * @param str2
	 *            the second String, may be null
	 * @return <code>true</code> if the Strings are equal, case insensitive, or
	 *         both <code>null</code>
	 */
	public static boolean equalsIgnoreCase(String str1, String str2) {
		return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
	}

	/**
	 * <p>
	 * Counts the length of String.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.count(null)			= 0
	 * StringUtils.count("")			= 0
	 * StringUtils.count("a*")			= 2
	 * StringUtils.count("大小small")	= 9
	 * </pre>
	 * 
	 * @param str
	 *            the String to check,may be null
	 * @return the number of String.
	 */
	public static int count(String str) {
		int length = 0;
		str = str.trim();
		if (isEmpty(str)) {
			return length;
		}
		char[] temp = str.toCharArray();
		for (char t : temp) {
			if (String.valueOf(t).matches("^[\\u4e00-\\u9fa5]*$")) {
				length += 2;
			} else if (String.valueOf(t).matches("^\\S*$")) {
				length += 1;
			}
		}
		return length;
	}

	/**
	 * 将一个网页编码转化为unicode符号，比如"&#8212;转为—
	 * 
	 * @param dataStr
	 * @return
	 */
	public static String decodeUnicode(final String dataStr) {
		int start = 0;
		int end = 0;
		final StringBuffer buffer = new StringBuffer();
		while (start > -1) {
			int system = 10;// 进制
			if (start == 0) {
				int t = dataStr.indexOf("&#");
				if (t < 0) {
					return dataStr;
				}
				buffer.append(dataStr.subSequence(0, t));
				if (start != t)
					start = t;
			}
			end = dataStr.indexOf(";", start + 2);
			String charStr = "";
			if (end != -1) {
				charStr = dataStr.substring(start + 2, end);

				// 判断进制
				char s = charStr.charAt(0);
				if (s == 'x' || s == 'X') {
					system = 16;
					charStr = charStr.substring(1);
				}
			}
			// 转换
			try {
				char letter = (char) Integer.parseInt(charStr, system);
				buffer.append(new Character(letter).toString());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}

			// 处理当前unicode字符到下一个unicode字符之间的非unicode字符
			start = dataStr.indexOf("&#", end);
			if (start - end > 1) {
				buffer.append(dataStr.substring(end + 1, start));
			}

			// 处理最后面的非unicode字符
			if (start == -1) {
				int length = dataStr.length();
				if (end + 1 != length) {
					buffer.append(dataStr.substring(end + 1, length));
				}
			}
		}
		return buffer.toString();
	}
	/**
	 * @author tian        2013-5-12 
	 * 从短xml字符串中截取指定标签最后一次出现的值
	 * <value>123</value>  => 123
	 * */
	public static String nabValue(String fields, String name) throws Exception {
		String value = "";
		int a = fields.lastIndexOf("<" + name + ">");
		int b = fields.lastIndexOf("</" + name + ">");
		if (a != -1 && b != -1) {
			value = fields.substring(a + name.length() + 2, b);
		}
		return value;
	}

	public static boolean isNotBlank(String str) {
	    if ((str == null) || (str.length() == 0) || ("".equals(str.trim())) || ("null".equals(str)) || ("null".equals(str.trim()))) {
	      return false;
	    }
	    return true;
	}
	public static void main(String[] args) {
		//System.out.println(isFullNull("    s"));
	}
}
