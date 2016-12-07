package com.broadeast.entity;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("t6_draw_config")
public class UserDrawnConfig {
	
	@Id
	private int id;
	
	@Column("level_one")
	private String levelOne;//用户名
	
	@Column("level_two")
	private String levelTwo;//场所名
	
	@Column("level_three")
	private int levelThree;//中奖等级  1一等奖，2二等奖，3三等奖
	
	@Column("open_time")
	private Date openTime;//创建时间
	
	@Column("create_time")
	private Date createTime;//创建时间

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLevelOne() {
		return levelOne;
	}

	public void setLevelOne(String levelOne) {
		this.levelOne = levelOne;
	}

	public String getLevelTwo() {
		return levelTwo;
	}

	public void setLevelTwo(String levelTwo) {
		this.levelTwo = levelTwo;
	}

	public int getLevelThree() {
		return levelThree;
	}

	public void setLevelThree(int levelThree) {
		this.levelThree = levelThree;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
