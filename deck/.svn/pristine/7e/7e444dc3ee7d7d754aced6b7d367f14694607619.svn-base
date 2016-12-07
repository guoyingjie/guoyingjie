package com.broadeast.service.impl;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.List;

import javax.annotation.Resource;

import org.nutz.dao.Dao;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.broadeast.entity.PortalUserTrialRecord;
/**
 * 试用记录操作
 * @author xyzhang
 *
 */
@Service
public class TrialService {
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="templJdbcTemplate")
	private JdbcTemplate templJdbcTemplate;
	
	@Resource(name="nutDao")
	private Dao nutDao;
	/**
	 * 是否存在试用记录
	 * @return
	 */
	public int isExistTotrialRecord(int site_id,String mac){
		String  sql = "select count(id) from t_portal_user_trial_records where site_Id=? and mac=?";
		int i = jdbcTemplate.queryForObject(sql, new Object[]{site_id,mac},Integer.class);
		if(i == 0){//不存在记录，业务上就可访问试用页面
			return -1;
		}else{
			return 1;
		}
	}
	
	/**
	 * 获得该设备的is_logout集合
	 * @return
	 */
	public List<Integer> getIsLogoutList(String mac){
		String  sql = "select is_logout from t_portal_user_trial_records where  mac=?";
		List<Integer> paramList = jdbcTemplate.queryForList(sql, new Object[]{mac},Integer.class);
		return paramList;
	}
	
	/**
	 * 更新is_logout
	 * 
	 * @param macs
	 * @param siteId
	 */
	public void updateIsLogout(String mac,Integer isLogout) {
		String sql = "UPDATE t_portal_user_trial_records SET is_logout= ? WHERE mac = ? ";
		jdbcTemplate.update(sql, new Object[] { isLogout, mac });
	}
	
	/**
	 * 根据mac删除v1_router_device中信息
	 * 
	 * @param macs
	 * @param siteId
	 */
	public void delRouterDeviceByMac(String mac) {
		String sql = "delete from v1_router_device where device_mac = ? ";
		templJdbcTemplate.update(sql, new Object[] { mac });
	}
	
	/**
	 * 检测是否可以试用 1--可以试用 return true    0---不可以试用return false;
	 * @param siteId
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public boolean isTryOrNotTry(Integer siteId) {
		try {
			String sql = "SELECT is_probative FROM t_cloud_site WHERE id=?";
			int isTry = jdbcTemplate.queryForInt(sql, new Object[] { siteId });
			if (isTry == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 保存试用记录
	 * @return int (大于0是保存成功，否则是保存失败)
	 */
	public int saveTrialRecord(int siteId,String siteMac,String mac){
		
		PortalUserTrialRecord portalUserTrialRecord = new PortalUserTrialRecord();
		portalUserTrialRecord.setMac(mac);
		portalUserTrialRecord.setSite_id(siteId);
		portalUserTrialRecord.setSite_mac(siteMac);
		Calendar nowDate = Calendar.getInstance();
		portalUserTrialRecord.setCreate_time(new Timestamp(nowDate.getTimeInMillis()));
		 nutDao.insert(portalUserTrialRecord);
		return portalUserTrialRecord.getId();
	}
	/**
	 * 获取库中所有未执行踢人动作的试用记录
	 * @return 只返回设备Mac
	 */
	public List<Map<String, Object>> getNotlogoutrecord(Date date){
		String sql = "select site_mac,mac from t_portal_user_trial_records where is_logout=0 and create_time<?";
		return jdbcTemplate.queryForList(sql,date);
	}
	/**
	 * 批量更新是否已踢除状态
	 */
	public void batchUpdateIslogout(final List<Map<String, Object>> logouts){
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
		String sql = "update t_portal_user_trial_records set is_logout=1 where mac=? ";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				// TODO Auto-generated method stub
				ps.setString(1,(String) logouts.get(i).get("mac"));
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return logouts.size();
			}
		});
	}

	
}
