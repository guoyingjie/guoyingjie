package com.broadeast.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
@Table("t_site_price_config")
public class SitePriceConfig {
	@Id
	private int id;
	@Column
	private int site_id;
	@Column
	private int price_type;//收费类型(时、天、月、包年、包2年)
	@Column
	private int charge_type;//收费类型所属集团(0无归属普通计费规则，1归属电信计费规则)
	@Column
	private BigDecimal unit_price;
	@Column
	private String name;
	@Column
	private int is_stoped;
	@Column
	private Timestamp create_time;
	@Column
	private int price_num;
	@Column
	private String comboNumber;
	@Column("v2_describe")
	private String describe;
	@Column("v2_recommend_state")
	private int recommendState;
	@Column("v2_give_meal")
	private int giveMeal;
	@Column("v2_givemeal_unit")
	private int giveMealUnit;
	
	public int getPrice_num() {
		return price_num;
	}
	public void setPrice_num(int price_num) {
		this.price_num = price_num;
	}
	public String getComboNumber() {
		return comboNumber;
	}
	public void setComboNumber(String comboNumber) {
		this.comboNumber = comboNumber;
	}
	public int getCharge_type() {
		return charge_type;
	}
	public void setCharge_type(int charge_type) {
		this.charge_type = charge_type;
	}
	private String site_name; //添加场所名称 不映射数据库 
	
	private String address;//场所地址
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSite_name() {
		return site_name;
	}
	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSite_id() {
		return site_id;
	}
	public void setSite_id(int site_id) {
		this.site_id = site_id;
	}
	public int getPrice_type() {
		return price_type;
	}
	public void setPrice_type(int price_type) {
		this.price_type = price_type;
	}
	
	public BigDecimal getUnit_price() {
		return unit_price;
	}
	public void setUnit_price(BigDecimal unit_price) {
		this.unit_price = unit_price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIs_stoped() {
		return is_stoped;
	}
	public void setIs_stoped(int is_stoped) {
		this.is_stoped = is_stoped;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	 
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	public int getRecommendState() {
		return recommendState;
	}
	public void setRecommendState(int recommendState) {
		this.recommendState = recommendState;
	}
	public int getGiveMeal() {
		return giveMeal;
	}
	public void setGiveMeal(int giveMeal) {
		this.giveMeal = giveMeal;
	}
	public int getGiveMealUnit() {
		return giveMealUnit;
	}
	public void setGiveMealUnit(int giveMealUnit) {
		this.giveMealUnit = giveMealUnit;
	}
	@Override
	public String toString() {
		return "SitePriceConfig [id=" + id + ", site_id=" + site_id
				+ ", price_type=" + price_type + ", charge_type=" + charge_type
				+ ", unit_price=" + unit_price + ", name=" + name
				+ ", is_stoped=" + is_stoped + ", create_time=" + create_time
				+ ", price_num=" + price_num + ", comboNumber=" + comboNumber
				+ ", describe=" + describe + ", recommendState="
				+ recommendState + ", giveMeal=" + giveMeal + ", giveMealUnit="
				+ giveMealUnit + ", site_name=" + site_name + ", address="
				+ address + "]";
	}
	
	
	
	

}
