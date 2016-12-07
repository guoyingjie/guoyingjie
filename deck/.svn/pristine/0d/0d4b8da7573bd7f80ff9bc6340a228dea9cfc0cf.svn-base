package com.broadeast.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * 商户汇总表
 * Copyright (c) All Rights Reserved, 2016.
 * 版权所有                  kdf Information Technology Co .,Ltd
 * @Project		newCloud
 * @File		IncomeCollect.java
 * @Date		2016年7月13日 下午4:03:13
 * @Author		cuimiao
 */
@Table("t4_income_collect")
public class IncomeCollect {
	/**主键*/
	@Id
	private int id;
	/**用户id*/
	@Column("user_id")
	private String userId;
	/**平台收入（线上收入）*/
	@Column("platform_income")
	private BigDecimal platformIncome;
	/**线下收入*/
	@Column("offline_income")
	private BigDecimal offlineIncome;
	/**退款*/
	@Column("account_refund")
	private BigDecimal accountRefund;
	/**历史总收入*/
	@Column("account_income")
	private BigDecimal accountIncome;
	/**提现时间*/
	@Column("withdraw_time")
	private long withdrawTime;
	/**手续费比率*/
	@Column("charge_rate")
	private BigDecimal chargeRate;
	/**提现最低金额*/
	@Column("lowest_money")
	private BigDecimal lowestMoney;
	/**最短提现周期（天）*/
	@Column("shortest_cycle")
	private int shortestCycle;
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
	public BigDecimal getAccountRefund() {
		return accountRefund;
	}
	public void setAccountRefund(BigDecimal accountRefund) {
		this.accountRefund = accountRefund;
	}
	public BigDecimal getAccountIncome() {
		return accountIncome;
	}
	public void setAccountIncome(BigDecimal accountIncome) {
		this.accountIncome = accountIncome;
	}
	public long getWithdrawTime() {
		return withdrawTime;
	}
	public void setWithdrawTime(long withdrawTime) {
		this.withdrawTime = withdrawTime;
	}
	public BigDecimal getChargeRate() {
		return chargeRate;
	}
	public void setChargeRate(BigDecimal chargeRate) {
		this.chargeRate = chargeRate;
	}
	public BigDecimal getLowestMoney() {
		return lowestMoney;
	}
	public void setLowestMoney(BigDecimal lowestMoney) {
		this.lowestMoney = lowestMoney;
	}
	public int getShortestCycle() {
		return shortestCycle;
	}
	public void setShortestCycle(int shortestCycle) {
		this.shortestCycle = shortestCycle;
	}
	
	
	
}
