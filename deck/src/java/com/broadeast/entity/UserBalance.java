package com.broadeast.entity;

import java.math.BigDecimal;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("t_user_balance")
public class UserBalance {

	@Id
	private int id;
	@Column("user_name")
	private String userName;
	@Column("balance")
	private BigDecimal balance;
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
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "UserBalance [id=" + id + ", userName=" + userName
				+ ", balance=" + balance + "]";
	}
	
	
}
