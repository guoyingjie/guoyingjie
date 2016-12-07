package com.broadeast.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.lang.Lang;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.broadeast.entity.SitePaymentRecord;
import com.broadeast.entity.SitePriceConfig;
import com.broadeast.service.IPayCenterService;
import com.broadeast.util.BigDecimalUtil;
 /* 建立请求
 * @author gyj
 *
 */
import com.wap.alipay.pcConfig.AlipayConfig;
import com.wap.alipay.pcUtil.AlipaySubmit;

@Service
public class AlipayService {
	/**
	 * 建立请求
	 * @param out_trade_no
	 * @param subject
	 * @param total_fee
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	
	public String getParameters(String out_trade_no,String subject,String total_fee,String defaultbank) throws UnsupportedEncodingException{
				String payment_type = "1";
				Map<String, String> sParaTemp = new HashMap<String, String>();
				sParaTemp.put("service", "create_direct_pay_by_user");
		        sParaTemp.put("partner", AlipayConfig.partner);
		        sParaTemp.put("seller_email", AlipayConfig.seller_email);
		        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
				sParaTemp.put("payment_type", payment_type);
				sParaTemp.put("notify_url", AlipayConfig.notify_url);
				sParaTemp.put("return_url", AlipayConfig.return_url);
				sParaTemp.put("out_trade_no", out_trade_no);
				sParaTemp.put("subject", subject);
				sParaTemp.put("total_fee", total_fee);
				sParaTemp.put("defaultbank", defaultbank);
				String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
			    return sHtmlText;
	}
 
}
