package com.broadeast.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.broadeast.dao.PortalLogDao;
import com.broadeast.entity.CloudSite;
import com.broadeast.entity.PortalUser;
import com.broadeast.util.CalendarUtil;
import com.broadeast.util.InitContext;
import com.broadeast.util.SHA256;

@Service
public class PingService {
	Logger log = Logger.getLogger(PingService.class);
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
	 * 
	 * @param routerMac路由器mac
	 * @return
	 */
	public List<Map<String,Object>> selRouterByMac(String routerMac){
		String sql="SELECT * FROM t_cloud_site_routers WHERE dfid=?";
		List<Map<String, Object>> list=null;
		try {
			list=jdbcTemplate.queryForList(sql, new Object[]{routerMac});
			
		} catch (Exception e) {
			log.error("selRouterByMac------出错",e);
		}
		 return list;
	}
	/**
	 * 修改路由器状态记录
	 * @param routerMac 路由器mac
	 * @param sys_uptime 系统启动时间
	 * @param sys_memfree 系统空间
	 * @param sys_load 系统cpu
	 * @param wifidog_uptime wifiog启动时间
	 * @param lasttime 最后一次心跳
	 * @return
	 */
	public int updateRouter(String routerMac,String wanip){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql="UPDATE t_cloud_site_routers SET last_time=? ,mac=?,ip=? WHERE dfid=?";
		String sql1="SELECT last_time FROM t_cloud_site_routers WHERE dfid=?";//获取路由器已经启动最后一次心跳的时间
		String updateSql="UPDATE t_cloud_site_routers SET mac=?, last_time=?, startup_time=?, ip=? WHERE dfid=?";	
		try {
			//在原来路由器启动时间上加上路由启动时间，如果路由心跳最后一次请求时间加4分钟大于当前时间则认为设备路由已经离线，这时路由发来心跳视为重新启动
			//这时修改路由器的启动时间
			List<String> listTime=jdbcTemplate.queryForList(sql1, new Object[]{routerMac},String.class);
			Date d=sdf.parse(listTime.get(0));
			long nowTime=1*60*1000*4L;
			long last=nowTime+d.getTime();
			long s=new Date().getTime()-new Date(last).getTime();//路由设备当前时间减去最后一次请求时间加上4分钟
			if(s<0){//如果小于零 设备在线
				
				jdbcTemplate.update(sql, new Object[]{sdf.format(new Date()),routerMac,wanip,routerMac});
			}else{
				//大于零 设备离线
				jdbcTemplate.update(updateSql, new Object[]{routerMac,sdf.format(new Date()),sdf.format(new Date()),wanip,routerMac});

			}
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 路由器登录日志，当路由器第一次登录或者是离线后再次登录时处理过程
	 * @param mac 路由器mac
	 * @return
	 */
	public int doRouterLogin(String mac){
		SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		SimpleDateFormat sdf1=new SimpleDateFormat("YYYYMMdd");
		String tableName="v1_router_login_"+sdf1.format(new java.util.Date());
		String tableSql="SELECT TABLE_NAME FROM information_schema.TABLES WHERE table_name=?";
		String routerSql="SELECT * FROM "+tableName+" WHERE id=?";
		int i=0;
		List<Map<String, Object>> routerLsit=null;
		try {
			List<String> ls=templJdbcTemplate.queryForList(tableSql, new Object[]{tableName},String.class);
			if(ls.size()!=0&&ls!=null){//如果表存在直接插入或修改
				routerLsit=templJdbcTemplate.queryForList(routerSql, new Object[]{mac});
				if(routerLsit!=null&&routerLsit.size()!=0){
					
					i=updateRouterLogin(mac, sdf, tableName);
				}else{
					i=insertRouterLogin(mac, sdf, tableName);
					
				}
			}else{//如果表不存在
				
				i=createRouterTabel(sdf1, tableName);//创建表 之后插入或修改
				
				if(i==1){
					i=insertRouterLogin(mac, sdf, tableName);
				}
			}
			return i;
			
		} catch (Exception e) {
			log.error("路由器添加或修改router_login表失败--------method-----doRouterLogin");
			return i;
		}
	}
	
	
	
	/**
	 * 当路由器离线后,再次登录时修改路由器的请求时间
	 * @param routerMac 路由器mac
	 * @param sdf
	 * @return
	 */
	public int updateRouterLogin(String routerMac,SimpleDateFormat sdf,String tableName){
		String sql="UPDATE "+tableName+" SET server_time=? WHERE id=?";
		int i=0;
		try {
			i=templJdbcTemplate.update(sql, new Object[]{sdf.format(new Date()),routerMac});
			return i;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("修改路由器请求时间失败-----method----updateRouterLogin");
			return i;
		}
		
	}
	
	
	
	/**
	 * 路由器第一次登录时添加路由器登陆日志
	 * @param routerMac
	 * @param sdf
	 * @param tableName
	 * @return
	 */
	public int insertRouterLogin(String routerMac,SimpleDateFormat sdf,String tableName){
		String sql="INSERT INTO "+tableName+" (id,server_time) VALUES(?,?)";
		int i=0;
		try {
			i=templJdbcTemplate.update(sql, new Object[]{routerMac,sdf.format(new Date())});
			return i;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("添加路由器登录日志失败-----method----insertRouterLogin");
			return i;
		}
	}
	
	/**
	 * 创建router_login表
	 * @return
	 */
	public int createRouterTabel(SimpleDateFormat sdf,String tableName){
		try {
			String sql="CREATE TABLE "+tableName+" ("+
				  "`id` varchar(12) DEFAULT NULL,"+
				  "`server_time` datetime DEFAULT NULL,"+
				  "`request` text,"+
				  "`response`text"+
				")ENGINE=InnoDB DEFAULT CHARSET=utf8;";
			
				templJdbcTemplate.update(sql);
			return 1;	
		} catch (Exception e) {
			e.printStackTrace();
			log.error("创建portal日志失败----method----createPortalTabel---202行");
			return 0;	
		}
		
	}
	
	
}
