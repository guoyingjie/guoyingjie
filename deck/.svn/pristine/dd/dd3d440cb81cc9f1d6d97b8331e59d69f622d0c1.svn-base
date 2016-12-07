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
public class IkuaiAuthTiggerservice {
	
	private static Logger log = Logger.getLogger(IkuaiAuthTiggerservice.class);
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Resource(name = "nutDao")
	private Dao nutDao;
	@Resource(name = "templJdbcTemplate")
	private JdbcTemplate templJdbcTemplate;

	@Resource(name = "templNutDao")
	private Dao templNutDao;
	
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
		/**
		 * 
		 *	@Description:ikuai校验用户是否到期
		 *  @author songyanbiao
		 *	@Date 2016年6月7日 
		 *	@param request
		 *	@param response
		 */
	  public void ikuaiCharging(){
			 List<Map<String, Object>> ls=null;
			 String sql="SELECT * FROM v1_router_device WHERE user_ip IS NOT NULL";
			 String sqlUpdate="UPDATE t_site_customer_info SET used_flow=?,total_flow=? WHERE portal_user_id=? AND site_id=?";
			 String totalFlow="";
			 BigDecimal totalFlows;
			 PortalUser proUser=null;
			 SiteCustomerInfo sci=null;
			 String Action="PORTAL_LOGINOUT";
			 try {
			  ls=templJdbcTemplate.queryForList(sql);
			  if(ls!=null){
				  for (int i = 0; i < ls.size(); i++) {
					  sci=nutDao.fetch(SiteCustomerInfo.class, Cnd.where("site_id","=",ls.get(i).get("siteId")).and("portal_user_id","=",ls.get(i).get("portal_user_id")));
					  proUser=nutDao.fetch(PortalUser.class, Cnd.where("id","=",ls.get(i).get("portal_user_id")));
					  totalFlow = sci.getTotalFlow();
						if (totalFlow == null || totalFlow.equals("null")|| totalFlow == "null") {
							totalFlows = new BigDecimal(0.0000);
						} else {
							totalFlows = new BigDecimal(totalFlow);
						}
					  if(sci.getExpirationTime()==null){//无时间有流量
						  if(totalFlows.compareTo(new BigDecimal(0.0000))!=1){
							  Method(Action,proUser.getUserName(), proUser.getPassWord(),(String)ls.get(i).get("user_ip"), (String)ls.get(i).get("basip"),(String)ls.get(i).get("device_mac"));
							  jdbcTemplate.update(sqlUpdate, new Object[]{0,0,proUser.getId(),ls.get(i).get("siteId")});
							  continue;
						  }
					  }
					  if(new Date().getTime()>sci.getExpirationTime().getTime()){
						  if(totalFlows.compareTo(new BigDecimal(0.0000))==1){
								BigDecimal big = BigDecimalUtil.subtract(totalFlows, new BigDecimal(sci.getUsedFlow()==null?"0":sci.getUsedFlow()));
								if (big.compareTo(BigDecimal.ZERO) == -1|| big.compareTo(BigDecimal.ZERO) == 0) {// 购买的总流量小于于已使用的流量则踢掉
									  Method(Action,proUser.getUserName(), proUser.getPassWord(),(String)ls.get(i).get("user_ip"), (String)ls.get(i).get("basip"),(String)ls.get(i).get("device_mac"));
									  jdbcTemplate.update(sqlUpdate, new Object[]{0,0,proUser.getId(),ls.get(i).get("siteId")});
									  continue;
								}	
						  }else{
							  Method(Action,proUser.getUserName(), proUser.getPassWord(),(String)ls.get(i).get("user_ip"), (String)ls.get(i).get("basip"),(String)ls.get(i).get("device_mac"));
							  jdbcTemplate.update(sqlUpdate, new Object[]{0,0,proUser.getId(),ls.get(i).get("siteId")});
							  continue;
						  }
					  }
				  }
			  }
			  
			  
		} catch (Exception e) {
			System.out.println(2);
			log.error("ikuai校验用户是否到期出错",e);
		}
	  }
	  
	  
}
