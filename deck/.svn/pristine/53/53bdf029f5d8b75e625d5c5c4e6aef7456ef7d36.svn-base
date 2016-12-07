package com.broadeast.entity;

import java.sql.Timestamp;
import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * portal用户设备试用记录
 * @author xyzhang
 *
 */
@Table("t_portal_user_trial_records")
public class PortalUserTrialRecord {
	@Id
	private int id;
	@Column
	private int site_id;
	@Column
	private String mac;
	@Column
	private String site_mac;
	
	@Column
	private Timestamp create_time;//创建时间


	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public int getId() {
		return id;
	}
	public int getSite_id() {
		return site_id;
	}
	public String getSite_mac() {
		return site_mac;
	}
	public void setSite_mac(String site_mac) {
		this.site_mac = site_mac;
	}
	public void setSite_id(int site_id) {
		this.site_id = site_id;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public void setId(int id) {
		this.id = id;
	}

	
}
