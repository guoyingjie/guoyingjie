package com.broadeast.entity;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("t6_user_sign")
public class UserSign {
	
	@Id
	private int id;
	
	@Column("portal_user_id")
	private int userId;//用户id
	
	@Column("sign_num")
	private int signNum;//签到次数
	
	@Column("sign_time")
	private String signTime;//签到时间
	
	@Column("sign_time_state")
	private Date signTimeState;//当天是否签到
	
	@Column("state")
	private int state;//是否已抽奖0未抽奖 1已抽奖,
	
	@Column("create_time")
	private Date createTime;//创建时间
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getSignNum() {
		return signNum;
	}

	public void setSignNum(int signNum) {
		this.signNum = signNum;
	}

	public String getSignTime() {
		return signTime;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}

	
	public Date getSignTimeState() {
		return signTimeState;
	}

	public void setSignTimeState(Date signTimeState) {
		this.signTimeState = signTimeState;
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
	
	
}
