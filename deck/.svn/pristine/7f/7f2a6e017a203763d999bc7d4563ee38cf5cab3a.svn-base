package com.broadeast.entity;

import java.sql.Timestamp;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("t_cloud_site_portal")
public class CloudSitePortalEntity {
	
	@Id
	private int id;
	
	@Column("site_id")
	private int siteId;
	
	@Column("portal_id")
	private int portalUserId;
	
	@Column("create_time")
	private Timestamp createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public int getPortalUserId() {
		return portalUserId;
	}

	public void setPortalUserId(int portalUserId) {
		this.portalUserId = portalUserId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "CloudSitePortalEntity [id=" + id + ", siteId=" + siteId
				+ ", portalUserId=" + portalUserId + ", createTime="
				+ createTime + "]";
	}
}
