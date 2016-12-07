package com.broadeast.service.impl;

import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.broadeast.entity.CloudSite;
import com.broadeast.entity.CloudSiteRouters;
import com.broadeast.entity.PortalLog;
import com.broadeast.entity.SiteCustomerInfo;
import com.broadeast.util.CalendarUtil;
import com.broadeast.util.InitContext;
@Service
@SuppressWarnings("all")
public class PortalLogService {
	Logger log = Logger.getLogger(PortalLogService.class);
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="nutDao")
	private Dao nutDao;
	
	
	/**
	 * @Description 查询网管ip
	 */
	public String getNasip(String usernae,String routerMac){
		String sql = "select nasipaddress from radacct where username=? AND calledstationid=?  limit 1";
		List rs = jdbcTemplate.queryForList(sql, new Object[]{usernae,routerMac});
		String nasipaddress="";
		for(int i=0;i<rs.size();i++){                    
			Map userMap=(Map) rs.get(i);  
			//System.out.println(userMap.get("callingstationid"));    
			nasipaddress=userMap.get("nasipaddress").toString();
			}
		if(rs.size()<1){
			nasipaddress="192.168.182.1";
		}
		return nasipaddress;
	}
	/**
	 * 获取网关秘钥
	 */
	public String getSecret(String nasid){
		String sql = "select secret_key from t_cloud_site_routers where dfid='"+nasid+"'";
		List rs = jdbcTemplate.queryForList(sql);
		String secret_key="";
		for(int i=0;i<rs.size();i++){                    
			Map userMap=(Map) rs.get(i);  
			//System.out.println(userMap.get("callingstationid"));    
			secret_key=userMap.get("secret_key").toString();
			}
		if(rs.size()<1){
			secret_key="testing123";
		}
		return secret_key;
	}
	/**
	 * 通过nasid获取cloudsit
	 */
	public CloudSite getsite(String nasid){
		CloudSiteRouters  siteRouters=nutDao.fetch(CloudSiteRouters.class,Cnd.where("dfid","=",nasid));
		if(siteRouters==null){ //没查到 mac对应的 场所ID  如果有则验证用户
			return null;
		}
		CloudSite site=nutDao.fetch(CloudSite.class,Cnd.where("id","=",siteRouters.getSiteId())); //获得当前场所
		if(site==null){ //没有查询到场所
			return null;
		}		
		return site;
	}
	/**
	 * @Description:检测用户是否为多用户登陆
	 */
	public List getLoginUser(String usernae,int siteid,String clientMac){
		String sql = "select nasipaddress,callingstationid,acctupdatetime,Terminal_device from radacct where username='"+usernae+"' AND acctstoptime IS NULL";
		List<Map<String, Object>>  macs= jdbcTemplate.queryForList(sql);
		List ls = new ArrayList<>();
		for(int i=0;i<macs.size();i++){
			Pattern pattern = Pattern
					.compile(";\\s?(\\S*?\\s?\\S*?)\\s?(Build)?/");
			Matcher matcher = pattern.matcher(macs.get(i).get(
					"Terminal_device")
					+ "");
			String model = null;
			boolean boo = matcher.find();
			if (boo) {
				model = matcher.group(1).trim();
				macs.get(i).put("Terminal_device", model);
			} else if ( macs.get(i).get("Terminal_device")!=null&&((String) macs.get(i).get("Terminal_device"))
					.indexOf("iPhone") > -1) {
				macs.get(i).put("Terminal_device", "iPhone");
			} else {
				macs.get(i).put("Terminal_device", 0);

			}
			if(macs.get(i).get("callingstationid").equals(clientMac)
					||CalendarUtil.timeDifference(macs.get(i).get("acctupdatetime").toString(),CalendarUtil.currentTime())>20){
			try{
			sql = "update radacct set acctstoptime='"+CalendarUtil.currentTime()+"'  where username='"+usernae+"' AND dfid in (select dfid from t_cloud_site_routers where site_id = "+siteid+")  AND acctstoptime IS NULL";
			jdbcTemplate.update(sql);
			} catch (Exception e) {
				log.error("修改结束时间未成功",e);
			}
			}
			if(macs.get(i).get("Terminal_device")==null){
				macs.get(i).put("Terminal_device", 0);
			}
			ls.add(macs.get(i));
		}
		return ls;
	}
	/***
	 * @Description:添加portal日志
	 * @param portallog
	 */
	public void insertPortalog(PortalLog portallog ){
		String inertlog = "insert into  t2_portallog_"+CalendarUtil.currentDay()
				+"(terminaldevice,requesttime,routermac,clientmac,routerip,clientip,routertype,url,nasid) values"
				+"(?,?,?,?,?,?,?,?,?)";
		Object[] values = new Object[]{portallog.getTerminalDevice(),portallog.getRequestTime(),portallog.getRouterMac(),
		portallog.getClientMac(),portallog.getRouterIp(),portallog.getClientIp(),portallog.getRouterType(),portallog.getUrl(),portallog.getNasid()};
		int[] types = new int[] {Types.VARCHAR,Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};
		try{
		int row = jdbcTemplate.update(inertlog, values, types);
		}catch(Exception e){
			e.printStackTrace();
			log.error("t2_portallog_"+CalendarUtil.currentDay()+" porta日志表不存在！");
				createPortalog();
		}
	}
	
	/**
	 * 
	 *	@Description:查询场所路由
	 *  @author songyanbiao
	 *	@Date 2016年6月7日 
	 *	@param gwid
	 *	@return
	 */
	public CloudSiteRouters selRouter(String dfid){
		CloudSiteRouters csr=null;
		try {
			csr=nutDao.fetch(CloudSiteRouters.class, Cnd.where("dfid","=",dfid));
		} catch (Exception e) {
			log.error("查询场所路由出错",e);
		}
		return csr;
	}
	/***
	 * @Description:按照日期创建portal日志表
	 * @param portallog
	 */
	private void createPortalog(){
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE TABLE if not exists t2_portallog_"+CalendarUtil.currentDay());
		sb.append("(");
		sb.append("`id` int   auto_increment,");
		sb.append("`terminaldevice` varchar(1000),");
		sb.append("`requesttime` datetime,");
		sb.append("`routermac` varchar(32),");
		sb.append("`clientmac` varchar(32),");
		sb.append("`routerip` varchar(32),");
		sb.append("`clientip` varchar(32),");
		sb.append("`routertype` varchar(15),");
		sb.append(" `url` text,");
		sb.append("`nasid` varchar(32),");
		sb.append(" PRIMARY KEY  (`id`)");
		sb.append(")");
		jdbcTemplate.execute(sb.toString());
		log.info("创建portal日志表成功");
	}
	/**
	 * 
	 *	@Description:ikuai登录时提醒登陆
	 *  @author songyanbiao
	 *	@Date 2016年6月15日 
	 *	@param userName 用户名
	 *	@param mac 用户设备mac
	 *	@return
	 */
	public List hasUserLogin(String userName,String mac,int siteId){
		String sql="SELECT callingstationid,Terminal_device,acctupdatetime,framedipaddress,nasipaddress FROM radacct WHERE username=? AND acctstoptime IS NULL ORDER BY acctstarttime DESC";
		List<Map> ls = new ArrayList<Map>();
		try {
			
			List<Map<String, Object>> lsMac=jdbcTemplate.queryForList(sql,new Object[]{userName});
			if(lsMac.size()==0){
				return ls;
			}else{
				for (int i = 0; i < lsMac.size(); i++) {
					if(!lsMac.get(i).get("callingstationid").equals(mac)){
						Pattern pattern = Pattern
								.compile(";\\s?(\\S*?\\s?\\S*?)\\s?(Build)?/");
						Matcher matcher = pattern.matcher(lsMac.get(i).get(
								"Terminal_device")
								+ "");
						String model = null;
						boolean boo = matcher.find();
						if (boo) {
							model = matcher.group(1).trim();
							lsMac.get(i).put("Terminal_device", model);
						} else if (((String) lsMac.get(i).get("Terminal_device"))
								.indexOf("iPhone") > -1) {
							lsMac.get(i).put("Terminal_device", "iPhone");
						} else {
							lsMac.get(i).put("Terminal_device", 0);

						}
						ls.add(lsMac.get(i));
					}else if(lsMac.get(i).get("callingstationid").equals(mac)
							||CalendarUtil.timeDifference(lsMac.get(i).get("acctupdatetime").toString(),CalendarUtil.currentTime())>20){
						//sql = "update radacct set acctstoptime='"+CalendarUtil.currentTime()+"'  where username='"+userName+"' AND dfid in (select dfid from t_cloud_site_routers where site_id = "+siteId+") AND calledstationid='"+mac+"' AND acctstoptime IS NULL";
						sql="UPDATE  radacct SET acctstoptime=? WHERE username=? AND dfid in (select dfid from t_cloud_site_routers where site_id = ? ) AND acctstoptime IS NULL";
						
						jdbcTemplate.update(sql,new Object[]{new Date(),userName,siteId});
					}
				}
			}
			
			return ls;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询账号是否已经登陆出错");
			return ls;
		}
		
	}
	/**
	 * 
	 *	@Description:修改用户登录日志头部信息
	 *  @author songyanbiao
	 *	@Date 2016年6月15日 
	 *	@param Terminal_device 请求头部信息
	 *	@param clientMac 设备mac
	 *	@param clientIp 设备ip
	 *	@param userName 用户名
	 */
	public void updateTerminal(String Terminal_device ,String clientMac,String clientIp,String userName){
		try {
			
			String sql="UPDATE radacct SET Terminal_device=? WHERE username=? AND framedipaddress=? AND callingstationid=? AND terminal_device IS NULL";
			jdbcTemplate.update(sql,new Object[]{Terminal_device,userName,clientIp,clientMac});
		} catch (Exception e) {
			log.error("修改用户登陆日志头部信息出错",e);
		}
	}
	
	/**
	 * @Description:检测用户是否被锁定
	 */
	public String getlockTime(String username,int siteId){
		try {
			long oneday = 12*60*60*1000;
			String sql="SELECT lock_time FROM t_site_customer_info WHERE site_id="+siteId+" and portal_user_id=(SELECT id FROM t_portal_user where user_name='"+username+"')";
			List<Map<String, Object>> locktime = jdbcTemplate.queryForList(sql);
			if(locktime.get(0).get("lock_time")==null||"".equals(locktime.get(0).get("lock_time"))){
				 return null;
			}else{
				String locktimes = locktime.get(0).get("lock_time").toString();
				long lock = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(locktimes).getTime();
				long manytime = new Date().getTime()-lock;
				if(manytime>oneday){
					return null;
				}else{
					return dateDiff(oneday-manytime);
				}
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * @Description 计算两个时间相差几年几小时几分钟
	 * @param diff
	 *            获得两个时间的毫秒时间差
	 * @return
	 * @throws ParseException
	 */
	public static String dateDiff(long diff) throws ParseException {
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long day = diff / nd;// 计算差多少天
		long hour = diff % nd / nh;// 计算差多少小时
		long min = diff % nd % nh / nm;// 计算差多少分钟
		long sec = diff % nd % nh % nm / ns;// 计算差多少秒//输出结果
		if (day <= 0) {
			return hour + "小时" + min + "分钟";
		}
		if (hour <= 0 && day <= 0) {
			return "0小时" + min + "分钟";
		}
		return day + "天" + hour + "小时";
	}
}
