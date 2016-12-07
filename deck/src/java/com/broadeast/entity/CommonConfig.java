package com.broadeast.entity;

import java.math.BigDecimal;
import java.util.List;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import com.broadeast.entity.CloudSite;
@Table("t4_common_config")
public class CommonConfig {
	@Id
	private int id;
	@Column("commonchagrge")
	private BigDecimal commonChagrge;
	@Column("commonminmoney")
	private BigDecimal commonMinMoney;
	@Column("commonbalanceday")
	private int commonBalanceday;
	@Column("ident")
	private int ident;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BigDecimal getCommonChagrge() {
		return commonChagrge;
	}
	public void setCommonChagrge(BigDecimal commonChagrge) {
		this.commonChagrge = commonChagrge;
	}
	public BigDecimal getCommonMinMoney() {
		return commonMinMoney;
	}
	public void setCommonMinMoney(BigDecimal commonMinMoney) {
		this.commonMinMoney = commonMinMoney;
	}
	public int getCommonBalanceday() {
		return commonBalanceday;
	}
	public void setCommonBalanceday(int commonBalanceday) {
		this.commonBalanceday = commonBalanceday;
	}
	public int getIdent() {
		return ident;
	}
	public void setIdent(int ident) {
		this.ident = ident;
	}
	
}
