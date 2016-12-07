package com.broadeast.entity;

import java.sql.Timestamp;
import java.util.Date;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;


/**场所消费支付记录表
 * @ToDoWhat 
 * @author xmm
 */
@Table("t_sitepayment_records")
public class SitePaymentRecord {
	
	@Id
	private int id;							//'主键',
	
	@Column("order_num")
	private String orderNum;				//'支付订单号，全局唯一',
	
	@Column("trade_num")
	private String tradeNum;				//'支付宝交易号',
	
	@Column("param_json")
	private String paramJson;				//'参数集合，map转换为json',
	
	@Column("site_id")
	private int siteId;					//'店铺id',
	
	@Column("user_id")
	private int userId;					//'付款用户Id,portal用户id',
	
	@Column("is_finish")
	private int isFinish;					//'是否支付完成，0未完成，1完成',
	
	@Column("create_time")
	private Timestamp createTime;			//'创建订单时间',
	
	@Column("finish_time")
	private Date  finishTime;					//'支付完成时间',
	
	@Column("fail_reason")
	private String failReason;				//'失败原因',

	@Column("pay_type")                    //支付方式
	private int payType;
	
	@Column("input_pay_user")
	private String inputPayUser;				//'失败原因',
	
	@Column("out_pay_user")
	private String outPayUser;				//'失败原因',
	
    public int getId() {
    	return id;
    }
	
    public void setId(int id) {
    	this.id = id;
    }

    public String getOrderNum() {
    	return orderNum;
    }
	
    public void setOrderNum(String orderNum) {
    	this.orderNum = orderNum;
    }
	
    public String getTradeNum() {
    	return tradeNum;
    }
	
    public void setTradeNum(String tradeNum) {
    	this.tradeNum = tradeNum;
    }
	
    public String getParamJson() {
    	return paramJson;
    }
	
    public void setParamJson(String paramJson) {
    	this.paramJson = paramJson;
    }
	
    public int getSiteId() {
    	return siteId;
    }

    public void setSiteId(int siteId) {
    	this.siteId = siteId;
    }

    public int getUserId() {
    	return userId;
    }
	
    public void setUserId(int userId) {
    	this.userId = userId;
    }
	
    public int getIsFinish() {
    	return isFinish;
    }
	
    public void setIsFinish(int isFinish) {
    	this.isFinish = isFinish;
    }

    public Timestamp getCreateTime() {
    	return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
    	this.createTime = createTime;
    }

    public Date getFinishTime() {
    	return finishTime;
    }

    public void setFinishTime(Date finishTime) {
    	this.finishTime = finishTime;
    }

    public String getFailReason() {
    	return failReason;
    }
	
    public void setFailReason(String failReason) {
    	this.failReason = failReason;
    }

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public String getInputPayUser() {
		return inputPayUser;
	}

	public void setInputPayUser(String inputPayUser) {
		this.inputPayUser = inputPayUser;
	}

	public String getOutPayUser() {
		return outPayUser;
	}

	public void setOutPayUser(String outPayUser) {
		this.outPayUser = outPayUser;
	}

	@Override
	public String toString() {
		return "SitePaymentRecord [id=" + id + ", orderNum=" + orderNum
				+ ", tradeNum=" + tradeNum + ", paramJson=" + paramJson
				+ ", siteId=" + siteId + ", userId=" + userId + ", isFinish="
				+ isFinish + ", createTime=" + createTime + ", finishTime="
				+ finishTime + ", failReason=" + failReason + ", payType="
				+ payType + ", inputPayUser=" + inputPayUser + ", outPayUser="
				+ outPayUser + "]";
	}

	

	 

}
