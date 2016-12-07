package com.broadeast.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("t6_user_Recative_log")
public class UserRecativeLog {
	
	@Id
	private int id;
	
	@Column("user_name")
	private String userName;//用户名
	
	@Column("racative_money")
	private BigDecimal racativeMoney;//补签金额
	
	@Column("racativeDay")
	private String racativeDay;//场所名
	
	@Column("create_time")
	private Date createTime;//创建时间

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRacativeDay() {
		return racativeDay;
	}

	public void setRacativeDay(String racativeDay) {
		this.racativeDay = racativeDay;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getRacativeMoney() {
		return racativeMoney;
	}

	public void setRacativeMoney(BigDecimal racativeMoney) {
		this.racativeMoney = racativeMoney;
	}
	
	
}
