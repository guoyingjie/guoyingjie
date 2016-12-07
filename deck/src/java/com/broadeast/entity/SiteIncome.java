package com.broadeast.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;


/**
 * @ToDoWhat 
 * @author xmm
 */
@Table("t_site_income")
public class SiteIncome {
	
	@Id
	private int id;//'主键',
	
	@Column("transaction_amount")
	private BigDecimal transactionAmount;//'交易金额，元',
	
	@Column("site_id")
	private int siteId;//'场所id',
	
	@Column("portal_user_id")
	private int portalUserId;//'充值用户id，用户是portal的用户',
	
	@Column("portal_user_name")
	private String portalUserName;//'portal用户名',
	
	@Column("buy_num")
	private int buyNum;//购买的数量
	
	@Column("pay_name")
	private String payName;//购买的类型
	
	@Column("create_time")
	private Timestamp createTime;//'创建时间',

	@Column("pay_type")
	private int payType;
	
    public int getId() {
    	return id;
    }

    public void setId(int id) {
    	this.id = id;
    }

    public BigDecimal getTransactionAmount() {
    	return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
    	this.transactionAmount = transactionAmount;
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

    public String getPortalUserName() {
    	return portalUserName;
    }

    public void setPortalUserName(String portalUserName) {
    	this.portalUserName = portalUserName;
    }
	
    
    public int getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}
	

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public Timestamp getCreateTime() {
    	return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
    	this.createTime = createTime;
    }

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	@Override
	public String toString() {
		return "SiteIncome [id=" + id + ", transactionAmount="
				+ transactionAmount + ", siteId=" + siteId + ", portalUserId="
				+ portalUserId + ", portalUserName=" + portalUserName
				+ ", buyNum=" + buyNum + ", payName=" + payName
				+ ", createTime=" + createTime + ", payType=" + payType + "]";
	}
	
}
