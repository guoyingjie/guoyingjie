package com.broadeast.entity;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("t6_user_drawn")
public class UserDrawn {
	
	@Id
	private int id;
	
	@Column("user_name")
	private String userName;//用户名
	
	@Column("site_name")
	private String siteName;//场所名
	
	@Column("draw_level")
	private int level;//中奖等级  1一等奖，2二等奖，3三等奖
	
	@Column("state")
	private int state;//用户中奖状态 0审核，1中奖，2未中奖 ,3已领奖

	@Column("warn_verify_state")
	private int verifyState;//参加抽奖状态提醒  0未提示 1已提示
	
	@Column("warn_draw_state")
	private int drawState;//中奖提示 0未提示 1已提示
	
	@Column("warn_nodraw_state")
	private int nodrawState;//未中奖提示 0未提示 1已提示
	
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

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getDrawState() {
		return drawState;
	}

	public void setDrawState(int drawState) {
		this.drawState = drawState;
	}

	public int getNodrawState() {
		return nodrawState;
	}

	public void setNodrawState(int nodrawState) {
		this.nodrawState = nodrawState;
	}

	public int getVerifyState() {
		return verifyState;
	}

	public void setVerifyState(int verifyState) {
		this.verifyState = verifyState;
	}
	
	
	
}
