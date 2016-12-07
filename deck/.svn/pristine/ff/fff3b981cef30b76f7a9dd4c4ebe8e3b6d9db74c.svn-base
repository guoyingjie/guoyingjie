package com.broadeast.bean;

import java.util.List;

/**
 * @author pengxw E-mail:pengxianwei@kdfwifi.com
 * @version 创建时间：2016年9月1日 上午10:55:50
 * @describe
 */
public class LotteryTransData {
//	private String partnerId;  //合作方代码--必填
//	private boolean partnerDebug; // 是否打开调试模式--必填
//	private String transData;//交易数据。json字符串，在传输过程中可进行加密
	private String partnerChannelId;//合作方渠道ID，长度不超过64
	private String partnerReserved;//合作方保留域。长度不超过100。
	private String appId;//申报过的appId，当appId存在并且有效时，该appId可以有独立的配置生效。
	private String partnerOrderNumber; //投注订单号（投注交易数据的唯一标识）。长度不超过32。合作方自行确保唯一。必填
	private String userPhoneNumber;  //用户手机号。---必填
	private String userName; //用户姓名。
	private String lotteryId; //玩法代码。10001-双色球；10003-七乐彩 --必填
	private String numberSelectType;//1机选2单式自选 --必填
	private String betTotalAmount;//投注总注数。--必填
	private String partnerCallbackURL;//回调地址 --必填
	private List<BetInfo> betInfoList;



	public String getPartnerChannelId() {
		return partnerChannelId;
	}


	public void setPartnerChannelId(String partnerChannelId) {
		this.partnerChannelId = partnerChannelId;
	}


	public String getPartnerReserved() {
		return partnerReserved;
	}


	public void setPartnerReserved(String partnerReserved) {
		this.partnerReserved = partnerReserved;
	}


	public String getAppId() {
		return appId;
	}


	public void setAppId(String appId) {
		this.appId = appId;
	}


	public String getPartnerOrderNumber() {
		return partnerOrderNumber;
	}


	public void setPartnerOrderNumber(String partnerOrderNumber) {
		this.partnerOrderNumber = partnerOrderNumber;
	}


	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}


	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getLotteryId() {
		return lotteryId;
	}


	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}


	public String getNumberSelectType() {
		return numberSelectType;
	}


	public void setNumberSelectType(String numberSelectType) {
		this.numberSelectType = numberSelectType;
	}


	public String getBetTotalAmount() {
		return betTotalAmount;
	}


	public void setBetTotalAmount(String betTotalAmount) {
		this.betTotalAmount = betTotalAmount;
	}


	public String getPartnerCallbackURL() {
		return partnerCallbackURL;
	}


	public void setPartnerCallbackURL(String partnerCallbackURL) {
		this.partnerCallbackURL = partnerCallbackURL;
	}


	public List<BetInfo> getBetInfoList() {
		return betInfoList;
	}


	public void setBetInfoList(List<BetInfo> betInfoList) {
		this.betInfoList = betInfoList;
	}

	public class BetInfo{
		private String betMode; //投注方式。101：单式；102：复式。(暂时只支持单式) --必填
		private String betDetail;//注码细节描述 --必填
		public String getBetMode() {
			return betMode;
		}
		public void setBetMode(String betMode) {
			this.betMode = betMode;
		}
		public String getBetDetail() {
			return betDetail;
		}
		public void setBetDetail(String betDetail) {
			this.betDetail = betDetail;
		}
	}
	/***
	 * Web前端协议：
接收地址：http://api.youkala.com/api.jsp
接口说明

字段名	类型	必填	描述
partnerId	String	●	合作方代码。
partnerDebug	String	●	是否打开调试模式
transData	String	●	交易数据。json字符串，在传输过程中可进行加密。
transData参数如下
字段名	类型	必填	描述
partnerChannelId	String  合作方渠道ID，长度不超过64
partnerReserved	String	合作方保留域。长度不超过100。
appId	String 申报过的appId，当appId存在并且有效时，该appId可以有独立的配置生效。
partnerOrderNumber	String	●	投注订单号（投注交易数据的唯一标识）。长度不超过32。合作方自行确保唯一。
userPhoneNumber	String	●	用户手机号。
userName	String	 	用户姓名。
lotteryId	String	●	玩法代码。10001-双色球；10003-七乐彩。
numberSelectType	int	●	1机选2单式自选
betTotalAmount	int	●	投注总注数。
partnerCallbackURL	String	●	回调地址。
betInfoList	List	 自选彩票投注信息列表
BetInfo参数如下，每个投注信息只含有一条注码信息
字段名	类型	必填	描述
betMode	String	●	投注方式。101：单式；102：复式。(暂时只支持单式)
betDetail	String	●	注码细节描述。

兑换成功之后回调：

字段名	类型	必填	描述
partnerId	String	●	合作方代码。
transData	String	●	交易数据。json字符串，在传输过程中可进行加密。
       transData参数如下
字段名	类型	必填	描述
transDateTime	Date	●	交易时间
partnerChannelId	String	
	渠道Id
partnerReserved	String	
	合作方保留域
appId	String	
	
partnerOrderNumber	String	●	合作方订单号（同请求中的投注订单号）。
orderStatus	String	●	test or formal
orderNumber	String	●	投注订单号（有卡）
result	int	●	投注结果
0：成功
2000：部分成功
2001：订单已受理
2002：用户取消
其它：失败
具体原因在结果描述中
resultDesc	String	●	投注结果描述
issueNumber	String	
	期号。
betSuccAmount	int	
	投注成功注数。
orderAcceptTime	Date	
	订单受理时间。
ticketInfoList	List
	
	彩票信息列表。
       ticketInfo参数如下
字段名	类型	必填	描述
ticketId	String	●	特征码。此票的唯一标识，且包含票面信息。
betDateTime	Date	●	出票时间。
betDetail	String	●	注码细节描述。
** */
}
