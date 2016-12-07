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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author songyanbiao
 * @since 2016.06.16
 * @version 1.0.0_1
 * 
 */
public class WanipUtil {

	/**
	 * 
	 *	@Description:
	 *  @author songyanbiao
	 *	@Date 2016年6月16日 
	 *	@param request
	 *	@param response
	 *	@return
	 */
	public static String getWanIp(HttpServletRequest request,HttpServletResponse response){
		   String wanip = request.getHeader("x-forwarded-for");  
	        if (wanip == null || wanip.length() == 0 || wanip.equalsIgnoreCase("unknown")) {  
	        	wanip = request.getHeader("Proxy-Client-IP");  
	        }  
	        if (wanip == null || wanip.length() == 0 || wanip.equalsIgnoreCase("unknown")) {  
	        	wanip = request.getHeader("WL-Proxy-Client-IP");  
	        }  
	        
	        if (wanip == null || wanip.length() == 0 || wanip.equalsIgnoreCase("unknown")) {  
	        	wanip = request.getHeader("X-Real-IP");  
	        }  
	        
	        if (wanip == null || wanip.length() == 0 || wanip.equalsIgnoreCase("unknown")) {  
	        	wanip = request.getRemoteAddr();  
	        }	
	        return wanip;
	}

}