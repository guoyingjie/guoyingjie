package com.broadeast.bean;

import java.util.List;

/**
 * @author pengxw E-mail:pengxianwei@kdfwifi.com
 * @version 创建时间：2016年9月1日 下午2:14:37
 * @describe
 */
public class LotteryResponse {

	private String transDateTime; 		//交易时间
	private String appId;					//
	private String partnerChannelId;	//渠道Id
	private String partnerReserved;	//合作方保留域
	private String partnerOrderNumber; //合作方订单号（同请求中的投注订单号）
	private String orderStatus;	//test or formal
	private String orderNumber;	//投注订单号（有卡）
	private int result;	//投注结果0：成功	2000：部分成功	2001：订单已受理	2002：用户取消	其它：失败	具体原因在结果描述中
	private String resultDesc;	//投注结果描述
	private String issueNumber;//期号。
	private int betSuccAmount;//投注成功注数。
	private Long orderAcceptTime;//订单受理时间。
	private List<TicketInfo> ticketInfoList;//彩票信息列表。
	
	public String getTransDateTime() {
		return transDateTime;
	}

	public void setTransDateTime(String transDateTime) {
		this.transDateTime = transDateTime;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

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

	public String getPartnerOrderNumber() {
		return partnerOrderNumber;
	}

	public void setPartnerOrderNumber(String partnerOrderNumber) {
		this.partnerOrderNumber = partnerOrderNumber;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	public int getBetSuccAmount() {
		return betSuccAmount;
	}

	public void setBetSuccAmount(int betSuccAmount) {
		this.betSuccAmount = betSuccAmount;
	}

	public Long getOrderAcceptTime() {
		return orderAcceptTime;
	}

	public void setOrderAcceptTime(Long orderAcceptTime) {
		this.orderAcceptTime = orderAcceptTime;
	}

	public List<TicketInfo> getTicketInfoList() {
		return ticketInfoList;
	}

	public void setTicketInfoList(List<TicketInfo> ticketInfoList) {
		this.ticketInfoList = ticketInfoList;
	}

	public class TicketInfo{
		private String betDateTime;
		private String betDetail;
		private String ticketId;		
		/**
		 *出票时间
		 */
		public String getBetDateTime() {
			return betDateTime;
		}
		/**
		 *出票时间
		 */
		public void setBetDateTime(String betDateTime) {
			this.betDateTime = betDateTime;
		}
		/**
		 *注码细节描述
		 */
		public String getBetDetail() {
			return betDetail;
		}
		/**
		 *注码细节描述
		 */
		public void setBetDetail(String betDetail) {
			this.betDetail = betDetail;
		}
		/**
		 *特征码。此票的唯一标识，且包含票面信息
		 */
		public String getTicketId() {
			return ticketId;
		}
		/**
		 *特征码。此票的唯一标识，且包含票面信息
		 */
		public void setTicketId(String ticketId) {
			this.ticketId = ticketId;
		}
	}
}
