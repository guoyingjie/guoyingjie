package com.broadeast.entity;

import java.sql.Timestamp;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("t_portal_user")
public class PortalUser {

	@Id
	private int id;// 主键
	@Column("user_name")
	private String userName;// 用户名
	@Column("pass_word")
	private String passWord;// 用户密码
	@Column("sex")
	private int sex;// 用户性别 1代表男,0代表女
	@Column("is_stoped")
	private int isStoped;// 是否停用，0未停用，1停用
	@Column("create_time")
	private Timestamp createTime;// 创建时间
	@Column("token")
	private String token;// 用户获取京东交易支付的交易令牌
	@Column("state")
	private int state; // 认证的状态

	@Column("image_url")
	private String imageUrl;//banner url
	@Column("user_grade")
	private int userGrade;   //等级
	@Column("user_nickname")
	private String userNickname;  //昵称
	@Column("user_integral")
	private int userIntegral;    //积分
	@Column("user_experience")
	private int userExperience;//经验

	@Column("end_time")
	private Timestamp endTime;//等级计算的结算时间
	
	@Column("client_mac")
	private String clientMac;
	@Column("t6_recomend_state")
	private int recomdState;//用户推荐码注册用户 0 普通注册用户,1推荐码注册用户
	
	@Column("t5_son_name")
	private String sonName;
	
	@Column("t5_son_state")
	private int sonstate;//划分时间或者流量需要手动下线,0--可以上网,1--不可以上网
	
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

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getIsStoped() {
		return isStoped;
	}

	public void setIsStoped(int isStoped) {
		this.isStoped = isStoped;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getUserGrade() {
		return userGrade;
	}

	public void setUserGrade(int userGrade) {
		this.userGrade = userGrade;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public int getUserIntegral() {
		return userIntegral;
	}

	public void setUserIntegral(int userIntegral) {
		this.userIntegral = userIntegral;
	}

	public int getUserExperience() {
		return userExperience;
	}

	public void setUserExperience(int userExperience) {
		this.userExperience = userExperience;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getClientMac() {
		return clientMac;
	}

	public void setClientMac(String clientMac) {
		this.clientMac = clientMac;
	}

	public String getSonName() {
		return sonName;
	}

	public void setSonName(String sonName) {
		this.sonName = sonName;
	}
 
	public int getRecomdState() {
		return recomdState;
	}

	public void setRecomdState(int recomdState) {
		this.recomdState = recomdState;
	}

	public int getSonstate() {
		return sonstate;
	}

	public void setSonstate(int sonstate) {
		this.sonstate = sonstate;
	}

	@Override
	public String toString() {
		return "PortalUser [id=" + id + ", userName=" + userName
				+ ", passWord=" + passWord + ", sex=" + sex + ", isStoped="
				+ isStoped + ", createTime=" + createTime + ", token=" + token
				+ ", state=" + state + ", imageUrl=" + imageUrl
				+ ", userGrade=" + userGrade + ", userNickname=" + userNickname
				+ ", userIntegral=" + userIntegral + ", userExperience="
				+ userExperience + ", endTime=" + endTime + ", clientMac="
				+ clientMac + ", recomdState=" + recomdState + ", sonName="
				+ sonName + ", sonstate=" + sonstate + "]";
	}
	
}
