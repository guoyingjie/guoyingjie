package com.broadeast.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.nutz.dao.Dao;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.broadeast.util.InitContext;

/**
 * 操作基础平台数据库的Dao
 * 
 * @ToDoWhat
 * @author xmm
 */
@Service
public class PortalLogDao {

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Resource(name = "nutDao")
	private Dao nutDao;

	@Resource(name = "templJdbcTemplate")
	private JdbcTemplate templJdbcTemplate;

	@Resource(name = "templNutDao")
	private Dao templNutDao;

	private static Logger log = Logger.getLogger(PortalLogDao.class);

	/**
	 * 获取待踢mac名单
	 * 
	 * @param siteId
	 * @param telephone
	 * @param clientMac
	 * @return
	 */
	public List<String> getUserOnlineMacs(int siteId,String telephone,String clientMac){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//本日登录日志表
		String tablename = "V1_PORTAL_"+sdf.format(new java.util.Date());
		List<String> result=new ArrayList<>();
		String sql ="SELECT DISTINCT MAC FROM  "+tablename+
		" WHERE AUTH_ID= ?  AND STORE_ID = ? AND mac != ? and Logout_time is null ORDER BY Login_time desc";
		String table = "select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME='"+tablename+"'";
		List<String> isTable = templJdbcTemplate.queryForList(table,String.class);
		List<Map<String, Object>> list=null;
		//判断是否有登录日志表
		if(isTable!=null&&isTable.size()!=0){
			try {
				list = templJdbcTemplate.queryForList(sql, new Object[]{telephone,siteId,clientMac});
			} catch (Exception e) {
				 
			}
		} 
        if(list!=null){
			for(int i=0;i<list.size();i++){
				result.add((String)(list.get(i).get("MAC"))); 
			}
        }
		return result;
	}

	/**
	 * 获取已经记录到命令表的踢人命令
	 * 
	 * @param siteMac
	 * @return
	 */
	public String getOldKickMacs(String siteMac) {
		String sql = "SELECT VALUE FROM V1_ROUTER_COMMAND WHERE ID= ? AND NAME='logout' ";
		List<Map<String, Object>> list = templJdbcTemplate.queryForList(sql,
				new Object[] { siteMac });
		return list.size() == 0 ? null : (String) list.get(0).get("VALUE");

	}

	/**
	 * 更新踢人命令
	 * 
	 * @param macs
	 * @param siteId
	 */
	public void updateKickMac(String macs, String siteMac) {
		String sql = "UPDATE V1_ROUTER_COMMAND SET VALUE= ? WHERE ID = ?  AND NAME='logout' ";
		templJdbcTemplate.update(sql, new Object[] { macs, siteMac });
	}

	/**
	 * 新增踢人命令
	 * 
	 * @param macs
	 * @param siteId
	 */
	public void insertKickMac(String macs, String siteMac) {
		String sql = "INSERT INTO V1_ROUTER_COMMAND VALUES ( ? , 'logout' , ? ) ";
		templJdbcTemplate.update(sql, new Object[] { siteMac, macs });

	}

	/**
	 * 批量更新踢人命令
	 * 
	 * @param logouts
	 */
	public void batchUpdateKickMac(final List<Map<String, Object>> logouts){
		String sql = " INSERT INTO v1_router_command (id,name,value) VALUES(?,'logout',?) ON DUPLICATE KEY UPDATE  value=concat(value,?);";
		templJdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				// TODO Auto-generated method stub
				ps.setString(1,(String)logouts.get(i).get("site_mac"));
				ps.setString(2,(String) logouts.get(i).get("mac"));
				ps.setString(3, (String)logouts.get(i).get("mac"));
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return logouts.size();
			}
		});
		
	}
	/**
	 * 获得 五分钟之前  与 当前时间的到期时间的用户;
	 * 
	 * @return
	 */
	/*public List<Map<String, Object>> getPortalUserName() {
		List<Map<String, Object>> list = null;
		String sql = "SELECT t.user_name,s.site_id FROM  t_portal_user t LEFT JOIN  t_site_customer_info s on  t.id=s.portal_user_id "
				+ " where s.expiration_time BETWEEN ' "
				+ CalendarUtil.fiveTimeBefore()
				+ " ' AND ' "
				+ CalendarUtil.currentTime() + " '";
		list = jdbcTemplate.queryForList(sql);
		return list == null ? null : list;
	}*/

	/**
	 * 获得portal日志表过期时间的用户的设备mac,场所的mac信息
	 * 
	 * @param list
	 * @return
	 */
	/*@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<String, Object>> getPortalUserMeg(
			List<Map<String, Object>> list) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String tablename = "v1_portal_" + sdf.format(new java.util.Date());
		List<Map<String, Object>> rUser = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			List<Map<String, Object>> user = new ArrayList();
			String sql = "SELECT Request_id,mac from " + tablename
					+ " where Auth_id = '"
					+ ((String) list.get(i).get("user_name")).trim()
					+ "' and Store_id= "
					+ ((Integer) list.get(i).get("site_id"))
					+ " and Logout_time is null  GROUP BY mac , Request_id ";
			user = templJdbcTemplate.queryForList(sql);
			rUser.addAll(user);
		}
		 
		return rUser == null ? null : rUser;
	}*/

	/**
	 * 批量更新时间到期踢人命令表
	 * 
	 * @param logouts
	 */
	/*public void batchUpdateExpireMac(final List<Map<String, Object>> list) {
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				String macs = getOldKickMacs((String) list.get(i).get("Request_id"));
				String clientMac = (String) list.get(i).get("mac");
				if (macs != null) {
					if (macs.trim().indexOf(clientMac.trim()) < 0) {
						String sql = " INSERT INTO v1_router_command (id,name,value) VALUES(?,'logout',?) ON DUPLICATE KEY UPDATE  value=concat(value,?)";
						templJdbcTemplate.update(sql, new Object[] {(String) list.get(i).get("Request_id"),clientMac, clientMac });
					}
				} else {
					String sql = " INSERT INTO v1_router_command (id,name,value) VALUES(?,'logout',?) ON DUPLICATE KEY UPDATE  value=concat(value,?);";
					templJdbcTemplate.update(sql, new Object[] {(String) list.get(i).get("Request_id"),clientMac, clientMac });
				        }
			      }
		     }
	   }*/
	 
}
