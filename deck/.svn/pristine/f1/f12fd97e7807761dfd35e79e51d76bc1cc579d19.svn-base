package com.broadeast.service.impl;

import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.nutz.lang.Lang;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.broadeast.entity.SitePaymentRecord;
import com.wap.demo.constant.MerchantConstant;


@Service
public class JDNotifyService {

	private static Logger logger = Logger.getLogger(JDNotifyService.class);
	 
	@Autowired
	private SitePaymentRecordsService sitePaymentRecordsService;

	@Autowired
	private UserService userService;

	@Autowired
	private SchoolPaymentService schoolPaymentService;
 
	/**
	 * 京东支付异步通知处理方法
	 * 
	 * @param orderNum
	 *            订单号
	 * @return "success" 成功 ， "fail" 失败
	 */
	@SuppressWarnings("unchecked")
	public String jdPayNotify(final String orderNum) {
		// 获取校园卡支付记录
		
		SitePaymentRecord payRecord = sitePaymentRecordsService
				.getRecordByOrderNum(orderNum);
		if (payRecord == null) {// 无支付记录
			logger.error("校园卡支付记录获取失败--orderNum:" + orderNum);
			return "fail";
		}
		// 校园卡支付记录表状态校验
		if (payRecord.getIsFinish() == 1) {// 支付状态为支付成功
			return "ok";
		}
		// 拿到paramMap,校验与系统中的用户状态是不是不一样。
		final Map<String, String> map = JSON.parseObject(
				payRecord.getParamJson(), Map.class);
		String checkResult = schoolPaymentService.paramsCheck(map);
		if (!"ok".equals(checkResult)) {
			logger.error("支付订单保留参数与系统现有数据不一致--orderNum:" + orderNum);
			sitePaymentRecordsService.updateFailReason("支付订单保留参数与系统现有数据不一致",
					orderNum);
			return "fail";
		}

		try {
			// 事务
			Trans.exec(new Atom() {
				@Override
				public void run() {
					// 修改支付用户的到期 时间
					int i = sitePaymentRecordsService.changeUserExpireMeal(map);
					if (i != 1) {
						logger.error("修改支付用户的到期 时间失败--orderNum:" + orderNum);
						sitePaymentRecordsService.updateFailReason(
								"修改支付用户的到期 时间失败", orderNum);
						throw Lang.makeThrow("修改支付用户的到期 时间失败--orderNum:"
								+ orderNum);
					}

					// 校园卡账务信息表添加记录
					String userName = userService.getPortalUserById(
							Integer.parseInt(map.get("userId"))).getUserName();
	        		i=sitePaymentRecordsService.saveSchooleFinanceRecord(new BigDecimal(map.get("amount")),Integer.parseInt(map.get("storeId")),
    				Integer.parseInt(map.get("userId")),userName,Integer.parseInt(map.get("buyNum")),map.get("priceName"),2);
					if (i != 1) {
						logger.error("校园卡账务信息表添加记录失败--orderNum:" + orderNum);
						sitePaymentRecordsService.updateFailReason(
								"校园卡账务信息表添加记录失败", orderNum);
						throw Lang.makeThrow("校园卡账务信息表添加记录失败--orderNum:"
								+ orderNum);
					}

					// 校园卡支付记录表状态修改为支付成功
					i = sitePaymentRecordsService.updateToFinish(orderNum);
					if (i != 1) {// 执行不成功
						logger.error("校园卡支付记录表状态修改失败--orderNum:" + orderNum);
						sitePaymentRecordsService.updateFailReason(
								"校园卡支付记录表状态修改失败：", orderNum);
						throw Lang.makeThrow("校园卡支付记录表状态修改失败--orderNum:"
								+ orderNum);
					}
					boolean y=sitePaymentRecordsService.updateCollect(new BigDecimal(map.get("amount")), Integer.parseInt(map.get("storeId")), Integer.parseInt(map.get("tenantId")));
					if(!y)
						throw Lang.makeThrow("计费表用户统计或场所统计插入或更新未成功"+ orderNum);
				}
			});
		} catch (Exception e) {
			logger.error("支付过程事务故障", e);
			return "fail";
		}
		return "ok";
	}
	
}
