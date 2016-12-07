package com.broadeast.entity;

import javax.annotation.Generated;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("t_user_realname_auth")
public class SiteUserRealNameAuth {

	@Id
	private int id ;//主键id
	@Column("site_id")
	private int siteId;//场所id
	@Column("user_name")
	private String userName; //用户名
	@Column("telephone")
	private String telephone;//用户电话号码
	@Column("id_card")
	private String idCard;//身份证号
	@Column("address")
	private String address;//用户地址
	@Column("img_url")
	private String imgUrl; //图片url
	@Column("state")
	private int state;//审核状态  1未审核，2代表通过
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSiteId() {
		return siteId;
	}
	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	@Override
	public String toString() {
		return "SiteUserRealNameAuth [id=" + id + ", siteId=" + siteId
				+ ", userName=" + userName + ", telephone=" + telephone
				+ ", idCard=" + idCard + ", address=" + address + ", imgUrl="
				+ imgUrl + ", state=" + state + "]";
	}
	
	
	
}
