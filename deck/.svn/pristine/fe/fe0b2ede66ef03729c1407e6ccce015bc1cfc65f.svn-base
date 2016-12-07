package com.broadeast.entity;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("t6_user_draw_log")
public class UserDrawLog {
	
	@Id
	private int id;
	
	@Column("portal_user_id")
	private int userId;//用户id
	
	@Column("drawContent")
	private int drawContent;//抽奖内容
	
	@Column("drawTime")
	private Date drawTime;//抽奖时间
	
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

	public int getDrawContent() {
		return drawContent;
	}

	public void setDrawContent(int drawContent) {
		this.drawContent = drawContent;
	}

	public Date getDrawTime() {
		return drawTime;
	}

	public void setDrawTime(Date drawTime) {
		this.drawTime = drawTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
