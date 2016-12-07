package com.broadeast.entity;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;


/**
 * 场所客户账户信息
 * @author xyzhang
 */
@Table("t_site_customer_info")
public class SiteCustomerInfo {
	
	@Id
	private int id;//'主键',
	
	@Column("expiration_time")
	private Date expirationTime;// 过期时间
	
	@Column("site_id")
	private int siteId;// 场所id
	
	@Column("portal_user_id")
	private int portalUserId;// portal用户id
	
	@Column("update_time")
	private Timestamp updateTime;// 更新时间
	
	@Column("create_time")
	private Timestamp createTime;// 创建时间
	
	@Column("total_flow")
	private String totalFlow;//总流量
	
	@Column("used_flow")
	private String usedFlow;//已用流量
	
	@Column("pay_way")
	private int payWay;  //用户购买的方式 0只购买时间,1既购买时间又购买流量,2只购买流量

	@Column("lock_time")
	private Date luckTime;
	
	@Column("is_try")
	private int isTry;
	@Column("last_flow")
	private String lastFlow;
	@Column("t5_lottery_time")
	private Date lottertTime;
	
	@Column("t5_total_time")
	private String totalTime;
	 
	
    public String getLastFlow() {
		return lastFlow;
	}
	public void setLastFlow(String lastFlow) {
		this.lastFlow = lastFlow;
	}


	public int getId() {
    	return id;
    }

	
    public void setId(int id) {
    	this.id = id;
    }

	
    public Date getExpirationTime() {
    	return expirationTime;
    }

	
    public void setExpirationTime(Date expirationTime) {
    	this.expirationTime = expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
    	try {
	        this.expirationTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(expirationTime);
        }catch (ParseException e) {
	        e.printStackTrace();
        }
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

	
    public Timestamp getUpdateTime() {
    	return updateTime;
    }

	
    public void setUpdateTime(Timestamp updateTime) {
    	this.updateTime = updateTime;
    }

	
    public Timestamp getCreateTime() {
    	return createTime;
    }

	
    public void setCreateTime(Timestamp createTime) {
    	this.createTime = createTime;
    }

	public String getTotalFlow() {
		return totalFlow;
	}


	public void setTotalFlow(String totalFlow) {
		this.totalFlow = totalFlow;
	}


	public String getUsedFlow() {
		return usedFlow;
	}


	public void setUsedFlow(String usedFlow) {
		this.usedFlow = usedFlow;
	}



	
	public int getPayWay() {
		return payWay;
	}


	public void setPayWay(int payWay) {
		this.payWay = payWay;
	}


	public Date getLuckTime() {
		return luckTime;
	}


	public void setLuckTime(Date luckTime) {
		this.luckTime = luckTime;
	}


	public int getIsTry() {
		return isTry;
	}


	public void setIsTry(int isTry) {
		this.isTry = isTry;
	}
	

	public Date getLottertTime() {
		return lottertTime;
	}
	public void setLottertTime(String lottertTime) {
		try {
			this.lottertTime =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(lottertTime);
		} catch (ParseException e) {
		}
	}
	public String getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}
	 
	public void setLottertTime(Date lottertTime) {
		this.lottertTime = lottertTime;
	}
	@Override
	public String toString() {
		return "SiteCustomerInfo [id=" + id + ", expirationTime="
				+ expirationTime + ", siteId=" + siteId + ", portalUserId="
				+ portalUserId + ", updateTime=" + updateTime + ", createTime="
				+ createTime + ", totalFlow=" + totalFlow + ", usedFlow="
				+ usedFlow + ", payWay=" + payWay + ", luckTime=" + luckTime
				+ ", isTry=" + isTry + ", lastFlow=" + lastFlow
				+ ", lottertTime=" + lottertTime + ", totalTime=" + totalTime
				+ "]";
	}
	 
}
