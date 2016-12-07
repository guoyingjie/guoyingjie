package com.broadeast.entity;

import java.sql.Timestamp;
import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("t_cloud_site")
public class CloudSite {
	@Id
	private int id;
	@Column
	private String site_name;
	@Column
	private String address;
	@Column
	private int user_id;
	@Column
	private int is_probative;
	@Column
	private int allow_client_num;
	@Column
	private Timestamp create_time;

	@Column
	private int state;

	@Column("banner_url")
	private String bannerUrl;

	@Column("systemtype")
	private int systemtype;
	
	@Column("status")
	private int stauts;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSite_name() {
		return site_name;
	}

	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getIs_probative() {
		return is_probative;
	}

	public void setIs_probative(int is_probative) {
		this.is_probative = is_probative;
	}

	public int getAllow_client_num() {
		return allow_client_num;
	}

	public void setAllow_client_num(int allow_client_num) {
		this.allow_client_num = allow_client_num;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getBannerUrl() {
		return bannerUrl;
	}

	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}

	public int getSystemtype() {
		return systemtype;
	}

	public void setSystemtype(int systemtype) {
		this.systemtype = systemtype;
	}

	public int getStauts() {
		return stauts;
	}

	public void setStauts(int stauts) {
		this.stauts = stauts;
	}

}
