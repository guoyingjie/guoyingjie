package com.broadeast.entity;

import java.math.BigDecimal;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * 场所总收入表
 * Copyright (c) All Rights Reserved, 2016.
 * 版权所有                  kdf Information Technology Co .,Ltd
 * @Project		newCloud
 * @File		SiteIncomeCollect.java
 * @Date		2016年7月13日 下午4:08:18
 * @Author		cuimiao
 */
@Table("t4_site_income_collect")
public class SiteIncomeCollect {
	/**主键*/
	@Id
	private int id ;
	/**用户id*/
	@Column("user_id")
	private String userId ;
	/**场所id*/
	@Column("site_id")
	private String siteId ;
	/**平台收入（线上收入）*/
	@Column("platform_income")
	private BigDecimal platformIncome ;
	/**线下收入*/
	@Column("offline_income")
	private BigDecimal offlineIncome ;
	/**退款*/
	@Column("account_refund")
	private BigDecimal accounRrefund ;
	/**总收入*/
	@Column("account_income")
	private BigDecimal accountIncome ;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public BigDecimal getPlatformIncome() {
		return platformIncome;
	}
	public void setPlatformIncome(BigDecimal platformIncome) {
		this.platformIncome = platformIncome;
	}
	public BigDecimal getOfflineIncome() {
		return offlineIncome;
	}
	public void setOfflineIncome(BigDecimal offlineIncome) {
		this.offlineIncome = offlineIncome;
	}
	public BigDecimal getAccounRrefund() {
		return accounRrefund;
	}
	public void setAccounRrefund(BigDecimal accounRrefund) {
		this.accounRrefund = accounRrefund;
	}
	public BigDecimal getAccountIncome() {
		return accountIncome;
	}
	public void setAccountIncome(BigDecimal accountIncome) {
		this.accountIncome = accountIncome;
	}
	
	
	
}
