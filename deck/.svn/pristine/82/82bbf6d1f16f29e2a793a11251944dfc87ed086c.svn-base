package com.broadeast.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.broadeast.bean.RouterAndDevice;
import com.broadeast.dao.PortalLogDao;
import com.broadeast.entity.CloudSiteRouters;
import com.broadeast.entity.PortalUser;
import com.broadeast.entity.PortalUserTrialRecord;
import com.broadeast.entity.SiteCustomerInfo;
import com.broadeast.util.BigDecimalUtil;

@Service
public class AuthService {
	private static Logger log = Logger.getLogger(AuthService.class);
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Resource(name = "nutDao")
	private Dao nutDao;
	@Resource(name = "templJdbcTemplate")
	private JdbcTemplate templJdbcTemplate;

	@Resource(name = "templNutDao")
	private Dao templNutDao;
	@Autowired
	private PortalLogDao portalLogDao;

	/**
	 * 查询token值
	 * 
	 * @param routermac
	 *            路由器mac
	 * @param devicemac
	 *            用户设备mac
	 * @param token
	 * @return
	 */
	public SiteCustomerInfo selSiteCustomerInfo(String devicemac, String token, String gw_id) {
		SiteCustomerInfo sci = null;
		try {
			CloudSiteRouters csr = nutDao.fetch(CloudSiteRouters.class, Cnd.where("mac", "=", gw_id));
			sci = nutDao.fetch( SiteCustomerInfo.class, Cnd.where("portal_user_id", "=", getUserId(token)).and(
							"site_id", "=", csr.getSiteId()));
		} catch (Exception e) {
			log.error("查询token值异常",e);
		}
		return sci;
	}

	/**
	 * 获得token
	 * 
	 * @param devicemac
	 *            用户设备mac
	 * @return
	 */
	public RouterAndDevice selRouterAndDevice(String devicemac, String token) {
		RouterAndDevice rad = null;
		try {
			rad = templNutDao.fetch( RouterAndDevice.class, Cnd.where("device_mac", "=", devicemac).and(
							"portal_user_id", "=", getUserId(token)));
		} catch (Exception e) {
			log.error("查询token值异常",e);
		}
		return rad;
	}
	/**
	 * @Description  校验用户是否放行成功
	 * @date 2016年6月1日下午6:09:14
	 * @author guoyingjie
	 * @param sci
	 * @param mac
	 * @param usingFlow  用户使用的流量
	 * @param token
	 * @param wanIp
	 * @param gw_id 
	 * true 代表放行,false代表放行失败
	 */
	public boolean checkResults(RouterAndDevice rad,SiteCustomerInfo sci, String mac,BigDecimal usingFlow, String token, 
			String wanIp, String newRouter,String incoming,String outgoing) {
		boolean flag = true;
		String sql = "DELETE FROM v1_router_device WHERE portal_user_id=?";
		Date nowDate = new Date();// 获得当前时间
		Date exTime = sci.getExpirationTime();
		// 用户总流量
		String totalFlow = sci.getTotalFlow();
		BigDecimal totalFlows;
		if (totalFlow == null ||"0".equals(totalFlow)) {
			totalFlows = new BigDecimal(0.0000);
		} else {
			totalFlows = new BigDecimal(totalFlow);
		}
		// 总流量-使用的流量
		if(sci.getExpirationTime()==null){
			if(totalFlows.compareTo(new BigDecimal(0.0000))==1){
				if(totalFlows.compareTo(usingFlow)!=1){
					updateUserLoginTime(mac, rad, newRouter, incoming, outgoing);
					flag= false;
				}
			}else{
				flag= false;
				updateUserLoginTime(mac, rad, newRouter, incoming, outgoing);
			}
		}
		// 系统时间大于到期时间,用户已到期,检查流量是否消耗完?
		if (nowDate.getTime() >= exTime.getTime()) {
			if(totalFlows.compareTo(new BigDecimal(0.0000))==1){
				// 时间到期检查是否有流量
				if(totalFlows.compareTo(usingFlow)!=1){// 购买的总流量小于于已使用的流量则踢掉
					updateUserLoginTime(mac, rad, newRouter, incoming, outgoing);
					flag= false;
				}	
			}else{
				updateUserLoginTime(mac, rad, newRouter, incoming, outgoing);
				flag = false;
			}
		}
		if (!flag) {
			templJdbcTemplate.update(sql, new Object[] { getUserId(token) });
			flag = false;
		}
		return flag;
	}
	 
	/**
	 * 当离线时,计算用户的剩余流量
	 * 
	 * @param token
	 * @param usedFlow
	 *            用户已使用的流量
	 * @param gw_id
	 *            路由器mac
	 * @return
	 */
	public int countFlow(String token, BigDecimal usedFlow, String gw_id,SiteCustomerInfo sci) {
		try {
			//String selSql = "SELECT used_flow ,last_flow FROM t_site_customer_info WHERE portal_user_id=? AND site_id IN(SELECT site_id FROM t_cloud_site_routers WHERE mac=?)";
			String sql = "UPDATE t_site_customer_info SET total_flow=? WHERE portal_user_id=? AND site_id in(SELECT site_id FROM t_cloud_site_routers WHERE mac=?)";
			if (usedFlow.equals(new BigDecimal(-1))) {// 当usedFlow为-1时代表用户使用流量已经超过购买流量

				sql = "UPDATE t_site_customer_info SET used_flow=?,total_flow=? ,last_flow=? WHERE portal_user_id=? AND site_id in(SELECT site_id FROM t_cloud_site_routers WHERE mac=?)";
				jdbcTemplate.update(sql, new Object[] { 0, 0, 0,getUserId(token),gw_id });
			} else {
				// 获得用户已用的流量
				//如果检测用户传来的流量没有数据库保存的是最后一次传来的流量大,则认为该用户重新登录
					jdbcTemplate.update( sql, new Object[] { (usedFlow),
							getUserId(token), gw_id });
					
			}
			return 2;
		} catch (Exception e) {
			log.error("用户离线时计算用户已用流量失败-------------method---countFlow--145行");
			return 0;
		}
	}

	/**
	 * 用户离线时更新用户离线时的状态
	 * 
	 * @param mac
	 *            用户设备mac
	 * @param token
	 * @param wanIp
	 *            路由器网关ip
	 * @return
	 */
	public void updatePortalLogin(String mac, String token) {
		SimpleDateFormat sdf= new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		String userSql = "SELECT user_name FROM t_portal_user WHERE id=?";
		String sql="UPDATE radacct SET acctstoptime=? WHERE username=? AND callingstationid=? AND acctstoptime IS NULL";
		try {
			List<String> ls = jdbcTemplate.queryForList(userSql,new Object[] { getUserId(token) }, String.class);
			jdbcTemplate.update(sql,new Object[]{sdf.format(new Date()),ls.get(0),mac});
		} catch (Exception e) {
			log.error("更改portal日志表用户离线时间失败-----method----updatePortalLogin---197行");
		}

	}

	/**
	 * 插入用户认证登录日志
	 * 
	 * @param rad
	 * @param wanIp
	 * @param state
	 *            状态为1时代表用户离线 添加用户离线时间,0代表用户登录 添加用户portal日志
	 * @return
	 */
	public int insertPortalRecord(RouterAndDevice rad, String wanIp, int state,String incoming,String outgoing) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			String sql = "insert into radacct (Terminal_device,acctsessionid,username,nasipaddress,acctstarttime,acctupdatetime,acctinputoctets,acctoutputoctets,calledstationid,callingstationid,framedipaddress) values(?,?,?,?,?,?,?,?,?,?,?)";
			String selSql = "SELECT * FROM radacct where callingstationid=? AND username=? AND acctstoptime IS NULL";
			String updateSql = "UPDATE radacct SET terminal_device=?,acctsessionid=?,nasipaddress=?,acctstarttime=?,calledstationid=?,framedipaddress=? WHERE username=? AND callingstationid=? AND acctstoptime IS NULL";
			String sql1="UPDATE radacct SET acctstoptime=? WHERE username=? AND callingstationid=? AND acctstoptime IS NULL";
			Map<String, String> parameter = getAuthUrl(rad.getOldAuthUrl());
			String mac = parameter.get("mac");
			String requestId = parameter.get("requestId");
			String ip = parameter.get("ip");
			String authId = parameter.get("authId");
			String terminalDevice = parameter.get("terminalDevice");
			String sessionId=parameter.get("sessionId");
			if (state == 1) {
				jdbcTemplate.update(sql1,
						new Object[] { sdf.format(new Date()), authId,mac});

			} else {
				// 防止用户退回去重新登录以及用户离开wifi覆盖的范围时，这样就能避免不断的向portal日志表里插入登录日志记录
				// 当查询到li不为空时则代表用户用同一个账号在同一台设备上反复登录，只需要更新portal日志就行无需插入
				// 当查询不到时则直接插入登录记录
				List<Map<String, Object>> li = templJdbcTemplate.queryForList(
						selSql, new Object[] { mac, authId });
				if (li.size() != 0 && li != null) {

				jdbcTemplate.update(
							updateSql,
							new Object[] { terminalDevice,sessionId,wanIp,sdf.format(new Date()),  
									requestId, ip,authId,mac});
				} else {
					jdbcTemplate.update(sql, new Object[] {terminalDevice,sessionId,authId,wanIp,sdf.format(new Date()),sdf.format(new Date()),
							new BigDecimal(incoming),new BigDecimal(outgoing),requestId,mac,ip} );
				}
			}

			return 1;
		} catch (Exception e) {
			log.error("添加用户登录portal日志错误---method-----insertPortalRecord---248行");
			return 0;
		}

	}

	/**
	 * 获得放行路径的参数
	 * 
	 * @param token
	 * @return
	 */
	public static Map<String, String> getAuthUrl(String oldAuthUrl) {
		Map<String, String> map = new HashMap<>();
		String[] authurl = oldAuthUrl.split("&");
		for (int i = 0; i < authurl.length; i++) {
			map.put(authurl[i].split("=")[0], authurl[i].split("=")[1]);
		}
		return map;
	}

	/**
	 * 获得用户id
	 * 
	 * @param token
	 * @return
	 */
	public String getUserId(String token) {
		token=token.substring(3, token.length());
		String len = System.currentTimeMillis() + "";
		String userId = token.substring(len.length());
		return userId;
	}
	/**
	 * 
	 * @Description:查看场所是否开启计费	
	 * @author songyanbiao
	 * @date 2016年8月12日 下午2:35:57
	 * @param
	 * @return
	 */
	public boolean getSiteState(String token){
		return token.indexOf("900")==0?true:false;
	}
	
	/**
	 * 当用户有其他设备登录时,踢人修改用户的计费流量,以及修改portal日志中的离线时间
	 * 
	 * @param sci
	 * @param token
	 * @param usedFlow   使用的流量
	 * @param newRouter  路由器mac
	 * @param mac  用户设备mac
	 * @param wanIp
	 */
	public void updateUserTimeOrFlow(SiteCustomerInfo sci, String token,BigDecimal usedFlow, String newRouter, String mac) {
//		Date nowDate = new Date();// 获得当前时间
//		Date exTime = sci.getExpirationTime();
//		// 用户总流量
//		String totalFlow = sci.getTotalFlow();
//		BigDecimal totalFlows;
//		if (totalFlow == null ||"0".equals(totalFlow)) {
//			totalFlows = new BigDecimal(0.0000);
//		} else {
//			totalFlows = new BigDecimal(totalFlow);
//		}
//		// 总流量-使用的流量
//		BigDecimal big = BigDecimalUtil.subtract(totalFlows,usedFlow);
//		
//		if(sci.getExpirationTime()==null){
//			if (big.compareTo(BigDecimal.ZERO) == -1|| big.compareTo(BigDecimal.ZERO) == 0) {// 购买的总流量小于于已使用的流量则踢掉
//				countFlow(token, new BigDecimal(-1), newRouter,sci);// 用户离线计算流量
//			}else{
//				countFlow(token,big, newRouter,sci);// 用户离线计算流量
//			}
//		}
//		// 系统时间大于到期时间,用户已到期,检查流量是否消耗完?
//		if (nowDate.getTime() >= exTime.getTime()) {
//			// 时间到期检查是否有流量
//			if (big.compareTo(BigDecimal.ZERO) == -1|| big.compareTo(BigDecimal.ZERO) == 0) {// 购买的总流量小于于已使用的流量则踢掉
//				countFlow(token, new BigDecimal(-1), newRouter,sci);// 用户离线计算流量
//			}else{
//				countFlow(token,  big, newRouter,sci);// 更改用户的试用流量
//			}
//		}
		updatePortalLogin(mac, token);//更改用户离线时间
	}

	/**
	 * 
	 *	@Description:用户 登陆时插入用户登陆日志表
	 *  @author songyanbiao
	 *	@Date 2016年6月29日 
	 *	@param rad
	 *	@param ip
	 *	@param newRouter
	 *	@param mac
	 *	@param wanIp
	 *	@param incoming
	 *	@param outgoing
	 *	@param dfid
	 *	@return
	 */
	public int insertUserLogin(RouterAndDevice rad, String ip,String newRouter,String mac,String wanIp,String incoming,String outgoing,String dfid){
		int i=0;
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			String sql = "insert into radacct (Terminal_device,acctsessionid,acctuniqueid,username,nasipaddress,acctstarttime,acctupdatetime,acctinputoctets,acctoutputoctets,calledstationid,callingstationid,framedipaddress,dfid) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			String selSql = "SELECT * FROM radacct where callingstationid=? AND username=? AND acctstoptime IS NULL";
			//String updateSql = "UPDATE radacct SET terminal_device=?,acctsessionid=?,nasipaddress=?,acctstarttime=?,calledstationid=?,framedipaddress=? WHERE username=? AND callingstationid=? AND acctstoptime IS NULL";
			String updateSql="UPDATE radacct SET acctstoptime=? WHERE username=? AND callingstationid=? AND acctstoptime IS NULL";
			Map<String, String> parameter = getAuthUrl(rad.getOldAuthUrl());
			String authId = parameter.get("authId");
			String terminalDevice = parameter.get("terminalDevice");
			String sessionId=parameter.get("sessionId");
			
			// 防止用户退回去重新登录以及用户离开wifi覆盖的范围时，这样就能避免不断的向portal日志表里插入登录日志记录
			// 当查询到li不为空时则代表用户用同一个账号在同一台设备上反复登录，只需要更新portal日志就行无需插入
			// 当查询不到时则直接插入登录记录
			List<Map<String, Object>> li = jdbcTemplate.queryForList(
					selSql, new Object[] { mac, authId });
			if (li.size() != 0 && li != null) {
				jdbcTemplate.update(updateSql, new Object[]{sdf.format(new Date()),authId,mac});
			}
			jdbcTemplate.update(sql, new Object[] {terminalDevice,sessionId.substring(0, 16),sessionId,authId,wanIp,sdf.format(new Date()),sdf.format(new Date()),
						new BigDecimal(incoming),new BigDecimal(outgoing),newRouter,mac,ip,newRouter});
			
			i=1;
		} catch (Exception e) {
			
			e.printStackTrace();
			log.error("插入用户登录日志表出错",e);
		}
		return i;
		
	}
	/**
	 * 
	 *	@Description:更改用户的离线时间
	 *  @author songyanbiao
	 *	@Date 2016年6月29日 
	 *	@param token
	 *	@param mac
	 */
	public void updateUserLoginTime(String mac,RouterAndDevice rad,String routerMac,String incoming,String outgoing ){
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		StringBuffer sbf= new StringBuffer();
		try {
			Map<String, String> parameter = getAuthUrl(rad.getOldAuthUrl());
			String authId = parameter.get("authId");
			sbf.append("UPDATE radacct SET");
			sbf.append(" acctstoptime=").append("'"+sdf.format(new Date())+"'");
			if(!"0".equals(incoming)){
				sbf.append(" ,acctinputoctets=").append(new BigDecimal(incoming));
			}
			if(!"0".equals(outgoing)){
				sbf.append(" ,acctoutputoctets=").append(new BigDecimal(outgoing));
			}
			sbf.append(" WHERE username=").append(authId);
			sbf.append(" AND callingstationid=").append("'"+mac+"'");
			sbf.append(" AND calledstationid=").append("'"+routerMac+"'");
			sbf.append(" AND acctstoptime IS NULL");

			//String sql="UPDATE radacct SET acctstoptime=? WHERE username=? AND callingstationid=? AND acctinputoctets=? AND acctoutputoctets=? AND calledstationid=? AND acctstoptime IS NULL";
			jdbcTemplate.update(sbf.toString());
		} catch (Exception e) {
			log.error("用户切换账号时更新用户的登陆日志离线时间",e);
		}
		
	}
	/**
	 * 
	 *	@Description:根据心跳机制更新用户的acctupdatetime字段一分钟更新一次
	 *  @author songyanbiao
	 *	@Date 2016年6月29日 
	 *	@param mac
	 *	@param rad
	 *	@return
	 */
	public int upateUserTime(String mac,RouterAndDevice rad,String routerMac,String incoming,String outgoing){
		int i=0;
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			Map<String, String> parameter = getAuthUrl(rad.getOldAuthUrl());
			String authId = parameter.get("authId");
			String sql="UPDATE radacct SET acctupdatetime=?,acctinputoctets=?,acctoutputoctets=? WHERE username=? AND callingstationid=?  AND calledstationid=? AND acctstoptime IS NULL";
			jdbcTemplate.update(sql, new Object[]{sdf.format(new Date()),
					BigDecimalUtil.divide(new BigDecimal(incoming), new BigDecimal("1024")),
					BigDecimalUtil.divide(new BigDecimal(outgoing), new BigDecimal("1024")),
					authId,mac,routerMac});
			i=1;
		} catch (Exception e) {
			log.error("更改用户登陆后acctupdatetime字段",e);
		}
		return i;
		
	}
	
	 
}