package com.broadeast.JD;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.jd.jr.pay.gate.signature.util.ThreeDesUtil;

/**
 * Copyright (c) All Rights Reserved, 2016.
 * 版权所有                   dfgs Information Technology Co .,Ltd
 * @Project		kdf-pay 京东支付公共类
 * @File		JDBeanComment.java
 * @Date		2016年12月2日 下午3:40:57
 * @Author		gyj
 */
public class JDBeanComment {
 
	private static Logger logger = Logger.getLogger(JDBeanComment.class);
	/**
	 * @Description   为保证信息安全，表单中的各个字段除了merchant（商户号）、
	 * 版本号（verion）以外，其余字段全部采用3DES进行加密
	 * @date 2016年12月1日下午1:14:36
	 * @author guoyingjie
	 * @param basePayOrderInfo
	 * @param key
	 */
	public static void setValueToBasePayOrderInfo(BasePayOrderInfo basePayOrderInfo,byte[] key){
		if (StringUtils.isNotBlank(basePayOrderInfo.getDevice())) {
			basePayOrderInfo.setDevice(ThreeDesUtil.encrypt2HexStr(key, basePayOrderInfo.getDevice()));
		}
		basePayOrderInfo.setTradeNum(ThreeDesUtil.encrypt2HexStr(key, basePayOrderInfo.getTradeNum()));
		if (StringUtils.isNotBlank(basePayOrderInfo.getTradeName())) {
			basePayOrderInfo.setTradeName(ThreeDesUtil.encrypt2HexStr(key, basePayOrderInfo.getTradeName()));
		}
		if (StringUtils.isNotBlank(basePayOrderInfo.getTradeDesc())) {
			basePayOrderInfo.setTradeDesc(ThreeDesUtil.encrypt2HexStr(key, basePayOrderInfo.getTradeDesc()));
		}
		basePayOrderInfo.setTradeTime(ThreeDesUtil.encrypt2HexStr(key, basePayOrderInfo.getTradeTime()));
		basePayOrderInfo.setAmount(ThreeDesUtil.encrypt2HexStr(key, basePayOrderInfo.getAmount()));
		basePayOrderInfo.setCurrency(ThreeDesUtil.encrypt2HexStr(key, basePayOrderInfo.getCurrency()));
		if (StringUtils.isNotBlank(basePayOrderInfo.getNote())) {
			basePayOrderInfo.setNote(ThreeDesUtil.encrypt2HexStr(key, basePayOrderInfo.getNote()));
		}
		basePayOrderInfo.setCallbackUrl(ThreeDesUtil.encrypt2HexStr(key, basePayOrderInfo.getCallbackUrl()));
		basePayOrderInfo.setNotifyUrl(ThreeDesUtil.encrypt2HexStr(key, basePayOrderInfo.getNotifyUrl()));
		basePayOrderInfo.setIp(ThreeDesUtil.encrypt2HexStr(key, basePayOrderInfo.getIp()));
		if (StringUtils.isNotBlank(basePayOrderInfo.getUserType())) {
			basePayOrderInfo.setUserType(ThreeDesUtil.encrypt2HexStr(key, basePayOrderInfo.getUserType()));
		}
		if (StringUtils.isNotBlank(basePayOrderInfo.getUserId())) {
			basePayOrderInfo.setUserId(ThreeDesUtil.encrypt2HexStr(key, basePayOrderInfo.getUserId()));
		}
		if (StringUtils.isNotBlank(basePayOrderInfo.getExpireTime())) {
			basePayOrderInfo.setExpireTime(ThreeDesUtil.encrypt2HexStr(key, basePayOrderInfo.getExpireTime()));
		}
		if (StringUtils.isNotBlank(basePayOrderInfo.getOrderType())) {
			basePayOrderInfo.setOrderType(ThreeDesUtil.encrypt2HexStr(key, basePayOrderInfo.getOrderType()));
		}
		    
		if (StringUtils.isNotBlank(basePayOrderInfo.getIndustryCategoryCode())) {
			basePayOrderInfo
					.setIndustryCategoryCode(ThreeDesUtil.encrypt2HexStr(key, basePayOrderInfo.getIndustryCategoryCode()));
		}

		if (StringUtils.isNotBlank(basePayOrderInfo.getSpecCardNo())) {
			basePayOrderInfo.setSpecCardNo(ThreeDesUtil.encrypt2HexStr(key, basePayOrderInfo.getSpecCardNo()));
		}
		if (StringUtils.isNotBlank(basePayOrderInfo.getSpecId())) {
			basePayOrderInfo.setSpecId(ThreeDesUtil.encrypt2HexStr(key, basePayOrderInfo.getSpecId()));
		}
		if (StringUtils.isNotBlank(basePayOrderInfo.getSpecName())) {
			basePayOrderInfo.setSpecName(ThreeDesUtil.encrypt2HexStr(key, basePayOrderInfo.getSpecName()));
		}
		if (StringUtils.isNotBlank(basePayOrderInfo.getVendorId())) {
			basePayOrderInfo.setVendorId(ThreeDesUtil.encrypt2HexStr(key, basePayOrderInfo.getVendorId()));
		}
		if (StringUtils.isNotBlank(basePayOrderInfo.getGoodsInfo())) {
			basePayOrderInfo.setGoodsInfo(ThreeDesUtil.encrypt2HexStr(key, basePayOrderInfo.getGoodsInfo()));
		}
		if (StringUtils.isNotBlank(basePayOrderInfo.getOrderGoodsNum())) {
			basePayOrderInfo.setOrderGoodsNum(ThreeDesUtil.encrypt2HexStr(key, basePayOrderInfo.getOrderGoodsNum()));
		}
		if (StringUtils.isNotBlank(basePayOrderInfo.getTermInfo())) {
			basePayOrderInfo.setTermInfo(ThreeDesUtil.encrypt2HexStr(key, basePayOrderInfo.getTermInfo()));
		}
		if (StringUtils.isNotBlank(basePayOrderInfo.getReceiverInfo())) {
			basePayOrderInfo.setReceiverInfo(ThreeDesUtil.encrypt2HexStr(key, basePayOrderInfo.getReceiverInfo()));
		}

		if (StringUtils.isNotBlank(basePayOrderInfo.getCert())) {
			basePayOrderInfo.setCert(ThreeDesUtil.encrypt2HexStr(key, basePayOrderInfo.getCert()));
		}
	}
	
	/**
	 * 
	 * @Title: doFilterCharProcess
	 * @Description: 执行特殊字符处理
	 * @param: @param param
	 * @param: @return
	 * @return: String
	 * @throws
	 * @author guoyingjie
	 * @Date 2016年8月6日 下午3:54:58
	 */
	public static String doFilterCharProcess(String param) {
		if (param == null || param.equals("")) {
			return param;
		} else {
			return StringEscape.htmlSecurityEscape(param);
		}

	}
	
	
	/**
	 * 
	 * @Title:        filterCharProcess 
	 * @Description:  特殊字符处理 
	 * @param:        @param basePayOrderInfo    
	 * @return:       void    
	 * @author       guoyingjie
	 * @Date         2016年8月6日 下午3:55:17
	 */
	public static  void filterCharProcess(BasePayOrderInfo basePayOrderInfo) {
		basePayOrderInfo.setVersion(doFilterCharProcess(basePayOrderInfo.getVersion()));
		basePayOrderInfo.setMerchant(doFilterCharProcess(basePayOrderInfo.getMerchant()));
		basePayOrderInfo.setDevice(doFilterCharProcess(basePayOrderInfo.getDevice()));
		basePayOrderInfo.setTradeNum(doFilterCharProcess(basePayOrderInfo.getTradeNum()));
		basePayOrderInfo.setTradeName(doFilterCharProcess(basePayOrderInfo.getTradeName()));
		basePayOrderInfo.setTradeDesc(doFilterCharProcess(basePayOrderInfo.getTradeDesc()));
		basePayOrderInfo.setTradeTime(doFilterCharProcess(basePayOrderInfo.getTradeTime()));
		basePayOrderInfo.setAmount(doFilterCharProcess(basePayOrderInfo.getAmount()));
		basePayOrderInfo.setCurrency(doFilterCharProcess(basePayOrderInfo.getCurrency()));
		basePayOrderInfo.setNote(doFilterCharProcess(basePayOrderInfo.getNote()));
		basePayOrderInfo.setCallbackUrl(doFilterCharProcess(basePayOrderInfo.getCallbackUrl()));
		basePayOrderInfo.setNotifyUrl(doFilterCharProcess(basePayOrderInfo.getNotifyUrl()));
		basePayOrderInfo.setIp(doFilterCharProcess(basePayOrderInfo.getIp()));
		basePayOrderInfo.setUserType(doFilterCharProcess(basePayOrderInfo.getUserType()));
		basePayOrderInfo.setUserId(doFilterCharProcess(basePayOrderInfo.getUserId()));
		basePayOrderInfo.setExpireTime(doFilterCharProcess(basePayOrderInfo.getExpireTime()));
		basePayOrderInfo.setOrderType(doFilterCharProcess(basePayOrderInfo.getOrderType()));
		basePayOrderInfo.setIndustryCategoryCode(doFilterCharProcess(basePayOrderInfo.getIndustryCategoryCode()));
		basePayOrderInfo.setSpecCardNo(doFilterCharProcess(basePayOrderInfo.getSpecCardNo()));
		basePayOrderInfo.setSpecId(doFilterCharProcess(basePayOrderInfo.getSpecId()));
		basePayOrderInfo.setSpecName(doFilterCharProcess(basePayOrderInfo.getSpecName()));
		basePayOrderInfo.setVendorId(doFilterCharProcess(basePayOrderInfo.getVendorId()));
		basePayOrderInfo.setGoodsInfo(doFilterCharProcess(basePayOrderInfo.getGoodsInfo()));
		basePayOrderInfo.setOrderGoodsNum(doFilterCharProcess(basePayOrderInfo.getOrderGoodsNum()));
		basePayOrderInfo.setTermInfo(doFilterCharProcess(basePayOrderInfo.getTermInfo()));
		basePayOrderInfo.setReceiverInfo(doFilterCharProcess(basePayOrderInfo.getReceiverInfo()));

	}
	/**
	 * @Description 获得商户加密用的基本类
	 * @date 2016年12月1日上午11:05:08
	 * @author guoyingjie
	 * @param request
	 * @return
	 */
	public static BasePayOrderInfo findBasePayOrderInfo(String version,String token,String tradeNum,String tradeName,String tradeTime,
			   String tradeAmount,String currency,String successCallbackUrl,String notifyUrl,String merchantNum,String ip){
		BasePayOrderInfo basePayOrderInfo = new BasePayOrderInfo();
		basePayOrderInfo.setVersion(version);
		basePayOrderInfo.setUserId(token);
		basePayOrderInfo.setMerchant(merchantNum);
		basePayOrderInfo.setTradeNum(tradeNum);
		basePayOrderInfo.setTradeName(tradeName);
		basePayOrderInfo.setOrderType("1");
		basePayOrderInfo.setTradeTime(tradeTime);
		basePayOrderInfo.setAmount(tradeAmount);
		basePayOrderInfo.setCurrency(currency);
		basePayOrderInfo.setCallbackUrl(successCallbackUrl);
		basePayOrderInfo.setNotifyUrl(notifyUrl);
		basePayOrderInfo.setIp(ip);
		return basePayOrderInfo;
	}
	 
}
