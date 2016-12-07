package com.broadeast.util;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

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

/**
 * @author songyanbiao
 * @since 2016.06.16
 * @version 1.0.0_1
 * 
 */
public class RandomUtil {
	/**
	 * 生成4位随机数
	 * 
	 * @return
	 */
	public static String randCode() {
		String code = "";
		for (int i = 0; i < 6; i++) {
			Random rand = new Random();
			code += rand.nextInt(9);
		}
		return code;
	}
	
	 //随机生成包含验证码字符串
	 public static String random(int num){
		Random rand = new Random();

	  //初始化种子
	  String[] str={"0","1","2","3","4","5","6","7","8","9",
	       "a","b","c","d","e","f","g","h","i","j",
	       "k","l","m","n","p","q","r","s","t"};
	  int number=str.length;
	  //接收随机字符
	  String text = "";
	  //随机产生4个字符的字符串
	  for(int i=0;i<num;i++){
	   text+=str[rand.nextInt(number)];
	  }
	  return text;
	 }
	 
	 public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
         "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
         "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
         "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
         "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
         "W", "X", "Y", "Z" };


	public static String generateShortUuid() {
	 StringBuffer shortBuffer = new StringBuffer();
	 String uuid = UUID.randomUUID().toString().replace("-", "");
	 for (int i = 0; i < 8; i++) {
	     String str = uuid.substring(i * 4, i * 4 + 4);
	     int x = Integer.parseInt(str, 16);
	     shortBuffer.append(chars[x % 0x3E]);
	 }
	 
	 return shortBuffer.toString();

}
	public static void main(String[] args) {
		Map map= new HashMap<>();
		for (int i = 0; i < 1000000; i++) {
			map.put(generateShortUuid(), 1);
			if(i==999999){
				System.out.println("hah");
			}
		}
		System.out.println(map.size());
	} 
}  