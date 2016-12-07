package com.broadeast.entity;

import java.sql.Timestamp;
import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("t6_user_recommend")
public class UserRecommend {
	@Id
	private int id;
	@Column("portal_user_id")
	private int userId;
	@Column("recommend")
	private String recommend;
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
	public String getRecommend() {
		return recommend;
	}
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	@Override
	public String toString() {
		return "UserRecommend [id=" + id + ", userId=" + userId
				+ ", recommend=" + recommend + "]";
	}

	
}
