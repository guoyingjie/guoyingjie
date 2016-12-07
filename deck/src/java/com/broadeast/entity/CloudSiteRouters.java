package com.broadeast.entity;

import java.sql.Timestamp;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;


/**
 * @ToDoWhat 
 * @author xmm
 */
@Table("t_cloud_site_routers")
public class CloudSiteRouters {
	@Id
	private int id;// '主键',
	
	@Column("site_id")
	private int siteId;//'场所id',
	
	@Column("mac")
	private String mac;//'路由器mac',
	
	@Column("create_time")
	private Timestamp createTime;// '创建时间',
	@Column("ip")
	private String ip;
	@Column("router_type")
	private String routerType;
	@Column("secret_key")
	private String secretKey;
	@Column("install_position")
	private String installPosition;
	@Column("dfid")
	private String dfid;
	@Column("startup_time")
	private Timestamp startupTime;
	@Column("last_time")
	private Timestamp lastTime;
	
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

	
    public String getMac() {
    	return mac;
    }

	
    public void setMac(String mac) {
    	this.mac = mac;
    }

    public Timestamp getCreateTime() {
    	return createTime;
    }

	
    public void setCreateTime(Timestamp createTime) {
    	this.createTime = createTime;
    }


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public String getRouterType() {
		return routerType;
	}


	public void setRouterType(String routerType) {
		this.routerType = routerType;
	}


	public String getSecretKey() {
		return secretKey;
	}


	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	
	public String getInstallPosition() {
		return installPosition;
	}


	public void setInstallPosition(String installPosition) {
		this.installPosition = installPosition;
	}


	public String getDfid() {
		return dfid;
	}


	public void setDfid(String dfid) {
		this.dfid = dfid;
	}


	public Timestamp getStartupTime() {
		return startupTime;
	}


	public void setStartupTime(Timestamp startupTime) {
		this.startupTime = startupTime;
	}


	public Timestamp getLastTime() {
		return lastTime;
	}


	public void setLastTime(Timestamp lastTime) {
		this.lastTime = lastTime;
	}


	@Override
	public String toString() {
		return "CloudSiteRouters [id=" + id + ", siteId=" + siteId + ", mac="
				+ mac + ", createTime=" + createTime + ", ip=" + ip
				+ ", routerType=" + routerType + ", secretKey=" + secretKey
				+ ", installPosition=" + installPosition + ", dfid=" + dfid
				+ ", startupTime=" + startupTime + ", lastTime=" + lastTime
				+ "]";
	}


	

}
