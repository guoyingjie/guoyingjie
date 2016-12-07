package com.broadeast.service.impl;

import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.broadeast.entity.PortalUser;
import com.broadeast.entity.SiteCustomerInfo;
import com.broadeast.util.BigDecimalUtil;
import com.broadeast.util.PAP_Quit_V1;
import com.broadeast.util.PortalUtil;
@Service
public class IkuaiAuthloginservice {
	
	
	 public static Boolean Method(String Action, String userName, String passWord, String ip, String bip, String mac)
	  {

		    short SerialNo_Short = (short)(int)(1.0D + Math.random() * 32767.0D);

		    byte[] SerialNo = PortalUtil.SerialNo(SerialNo_Short);
		    byte[] UserIP = new byte[4];
		    String[] ips = ip.split("[.]");

		    for (int i = 0; i < 4; i++) {
		      int m = NumberUtils.toInt(ips[i]);
		      byte b = (byte)m;
		      UserIP[i] = b;
		    }
		 //登陆   
	      if (Action.equals("PORTAL_LOGIN")) {
		        return Boolean.valueOf(iKuaiAuth.auth(bip, 2000, 3, userName, passWord, SerialNo, UserIP, mac));
		   }
	      //退出
	      	return Boolean.valueOf(PAP_Quit_V1.quit(0, bip, 2000,  3, SerialNo, UserIP));
		 
	  }

	  
}
