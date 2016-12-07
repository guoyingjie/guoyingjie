package com.broadeast.entity;

import java.sql.Timestamp;
import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("t2_self_user_message")
public class UserMessage {
	@Id
	private int id;
	@Column("user_id")
	private int userId;
	@Column("message_id")
	private int messageId;
	@Column("state_read")
	private int stateRead;
	@Column("state_delete")
	private int stateDelete;
	@Column("create_time")
	private Timestamp createTime;

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

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public int getStateRead() {
		return stateRead;
	}

	public void setStateRead(int stateRead) {
		this.stateRead = stateRead;
	}

	public int getStateDelete() {
		return stateDelete;
	}

	public void setStateDelete(int stateDelete) {
		this.stateDelete = stateDelete;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "UserMessage [id=" + id + ", userId=" + userId + ", messageId="
				+ messageId + ", stateRead=" + stateRead + ", stateDelete="
				+ stateDelete + ", createTime=" + createTime + "]";
	}

	 
}
