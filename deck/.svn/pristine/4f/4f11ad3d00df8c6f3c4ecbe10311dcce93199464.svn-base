package com.broadeast.util;

/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.HashMap;
import java.util.Map;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Utilities for encoding and decoding the Base64 representation of binary data.
 * See RFCs <a href="http://www.ietf.org/rfc/rfc2045.txt">2045</a> and <a
 * href="http://www.ietf.org/rfc/rfc3548.txt">3548</a>.
 * 
 * BASE64 基于Android Open Source Project修改
 * 
 * @author gaozhenhai
 * @since 2013.01.15
 * @version 1.0.0_1
 * 
 */
public class BASE64 {

	private static final Map<Integer, Character> base64CharMap = new HashMap<>();
	private static final String base64CharString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	static {
		// initialize base64 map
		for (int i = 0; i < base64CharString.length(); i++) {
			char c = base64CharString.charAt(i);
			base64CharMap.put(new Integer(i), new Character(c));
		}
	}

	public static String encode(String origin) {
		if (origin == null) {
			return null;
		}
		if (origin.length() == 0) {
			return "";
		}
		int length = origin.length();

		String binaryString = "";
		// to binary String
		for (int i = 0; i < length; i++) {
			int ascii = origin.charAt(i);
			String binaryCharString = Integer.toBinaryString(ascii);
			while (binaryCharString.length() < 8) {
				binaryCharString = "0" + binaryCharString;
			}
			binaryString += binaryCharString;
		}

		// to base64 index
		int beginIndex = 0;
		int endIndex = beginIndex + 6;
		String base64BinaryString = "";
		String charString = "";
		while ((base64BinaryString = binaryString.substring(beginIndex,
				endIndex)).length() > 0) {
			// if length is less than 6, add "0".
			while (base64BinaryString.length() < 6) {
				base64BinaryString += "0";
			}
			int index = Integer.parseInt(base64BinaryString, 2);
			char base64Char = base64CharMap.get(index);
			charString = charString + base64Char;
			beginIndex += 6;
			endIndex += 6;
			if (endIndex >= binaryString.length()) {
				endIndex = binaryString.length();
			}
			if (endIndex < beginIndex) {
				break;
			}
		}
		/*if (length % 3 == 2) {
			charString += "=";
		}
		if (length % 3 == 1) {
			charString += "==";
		}*/
		charString += "=";
		System.out.println(length);
		return charString;
	}

	/**
	 * This method is used to decode from base64 string to a normal string.
	 * 
	 * @param encodedString
	 *            The string to be decoded.
	 * @return The string after decoded.
	 */
	public static String decode(String encodedString) {
		if (encodedString == null) {
			return null;
		}
		if (encodedString.length() == 0) {
			return "";
		}
		// get origin base64 String
		String origin = encodedString.substring(0, encodedString.indexOf("="));
		/*String equals = encodedString.substring(encodedString.indexOf("="));*/
		String equals = encodedString.substring(0, encodedString.indexOf("="));
		String binaryString = "";
		// convert base64 string to binary string
		for (int i = 0; i < origin.length(); i++) {
			char c = origin.charAt(i);
			int ascii = base64CharString.indexOf(c);
			String binaryCharString = Integer.toBinaryString(ascii);
			while (binaryCharString.length() < 6) {
				binaryCharString = "0" + binaryCharString;
			}
			binaryString += binaryCharString;
		}
		// the encoded string has 1 "=", means that the binary string has append
		// 2 "0"
		if (equals.length() == 1) {
			binaryString = binaryString.substring(0, binaryString.length() - 2);
		}
		// the encoded string has 2 "=", means that the binary string has append
		// 4 "0"
		if (equals.length() == 2) {
			binaryString = binaryString.substring(0, binaryString.length() - 4);
		}

		// convert to String
		String charString = "";
		String resultString = "";
		int beginIndex = 0;
		int endIndex = beginIndex + 8;
		while ((charString = binaryString.substring(beginIndex, endIndex))
				.length() == 8) {
			int ascii = Integer.parseInt(charString, 2);
			resultString += (char) ascii;
			beginIndex += 8;
			endIndex += 8;
			if (endIndex > binaryString.length()) {
				break;
			}
		}
		return resultString;
	}
	 public static String decryptBASE64(String key)throws Exception
	  {
	    return new String(new BASE64Decoder().decodeBuffer(key));
	  }
	
	  public static String encryptBASE64(String key)
	    throws Exception
	  {
	    return new BASE64Encoder().encodeBuffer(key.getBytes());
	  }

	// Test methods here.
	public static void main(String[] args) throws Exception {
		
//		   String data = encryptBASE64("nasname=iKuai&gwid=d17c3a0c684b421966e0ac4ba01b4015&user_ip=192.168.1.1&mac=00:11:22:33:44:55&route_ver=2.5.0&bssid=00:02:06:0a:0d:f2&ssid=iKuai8&apmac=00:02:06:0a:0d:f1&timestamp=1437467086");
//		    System.out.println("加密后：" + data);
//
//		    System.out.println("解密后：" + decryptBASE64(data));

		    System.out.println("解密后：" + decryptBASE64("Z3dpZD0wN2NkMGY0NDI4ZjliM2ZhZWFmYjUwZDE5Njk1YWUyNCZyb3V0ZXJfdmVyPTIuNS4xMCZtYWM9ZjA6ZGU6ZjE6ZTg6Y2M6MzUmdXNlcl9pcD0xOTIuMTY4LjEuMTAmdGltZXN0YW1wPTE0NjQ3NTIzMDEmYnNzaWQ9JmFwbWFjPSZzc2lkPSZmaXJtd2FyZT1JSy1Sb3V0ZXJPUyZuYXNuYW1lPWlLdWFpJmJhc2lwPTE5Mi4xNjguMTAuMTE4&refer=aHR0cDovL3d3dy5xcS5jb20v"));
		   System.out.println("解密后：" + decryptBASE64("07cd0f4428f9b3faeafb50d19695ae24"));
	}
}