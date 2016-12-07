package com.broadeast.service;

import java.io.UnsupportedEncodingException;
import java.util.Map;


/**支付中心接口，定义两个方法
 * @ToDoWhat 
 * @author xmm
 */
public interface IPayCenterService {
	
	/**
	 * 生成支付跳转的html
	 * @param out_trade_no  订单号，系统唯一
	 * @param subject  订单名称
	 * @param total_fee  付款金额
	 * @return
	 */
	public String getPayHTML(String out_trade_no,String subject,String total_fee);
	
	/**
	 * 获取回调通知的参数
	 * @param params 根据request获取的参数列表
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public Map<String,String> getNotifyParams(Map<String,String> params) throws Exception;
	
	
	/**
	 * 验证消息是否是支付宝发出的合法消息
	 * @param params  通知返回来的参数数组
	 * @return 验证结果
	 */
	public boolean verify(Map<String, String> params)throws Exception;
	
	

}
