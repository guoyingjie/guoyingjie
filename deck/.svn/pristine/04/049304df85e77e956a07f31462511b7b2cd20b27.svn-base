package com.broadeast.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.nutz.lang.Lang;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;

import com.alibaba.fastjson.JSON;
import com.broadeast.entity.SitePaymentRecord;
import com.wap.demo.domain.request.AsynNotificationReqDto;
import com.wap.demo.utils.BASE64;
import com.wap.demo.utils.DESUtil;
import com.wap.wepaypc.model.BankCardJdConf;
import com.wap.demo.utils.JsonUtil;
@Service
public class JdPayBankService {
	private static Logger logger = Logger.getLogger(JdPayBankService.class);
	@Autowired
	private SiteService siteService;

	@Autowired
	private SitePaymentRecordsService sitePaymentRecordsService;

	@Autowired
	private UserService userService;

	@Autowired
	private SchoolPaymentService schoolPaymentService;


	@Resource
	private BankCardJdConf bankCardJdConf;
	/**
	 * 京东pc支付异步通知
	 */
	public void jdNotify(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		String result = "fail";
	        //获取通知原始信息
	        String resp = request.getParameter("resp");

	        logger.info("异步通知原始数据:"+resp);
	        if(null == resp){
	        	out.write(result);
	        	return;
	        }
	        //获取配置密钥
			String desKey = bankCardJdConf.getMerchantDESKey();
			String md5Key = bankCardJdConf.getMerchantMD5Key();
	        logger.info("desKey:"+ desKey);
	        logger.info("md5Key:"+ md5Key);
	        try {
	            //首先对Base64编码的数据进行解密
	            byte[] decryptBASE64Arr =  BASE64.decode(resp);
	            //解析XML
	            AsynNotificationReqDto dto = parseXML(decryptBASE64Arr);
	            logger.info("解析XML得到对象:"+ JsonUtil.write2JsonStr(dto));
	            //验证签名
	            String ownSign = generateSign(dto.getVersion(), dto.getMerchant(), dto.getTerminal(), dto.getData(), md5Key);
	            logger.info("根据传输数据生成的签名:"+ownSign);
	            if (!dto.getSign().equals(ownSign)) {
	                //验签不对
	            	out.write(result);
	            	logger.error("签名验证错误!");
	                throw new RuntimeException();
	            }else{
	                logger.info("签名验证正确!");
	            }
	            //验签成功，业务处理
	            //对Data数据进行解密
	            byte[] rsaKey = decryptBASE64(desKey);
	            String decryptArr = DESUtil.decrypt(dto.getData(), rsaKey, "utf-8");
	            Document doc = DocumentHelper.parseText(decryptArr);
				Element root = doc.getRootElement();
				Element rootone = root.element("TRADE");
				List<Element> elements = rootone.elements();
				String orderNum = "";// 订单号
				String status = "";// 交易返回状态
				for (Element e : elements) {
					if ("ID".equals(e.getName())) {
						orderNum = e.getTextTrim();
					} else if ("STATUS".equals(e.getName())) {
						status = e.getTextTrim();
					}
				}
				if ("0".equals(status)) {
					result = jdPayNotify(orderNum);
					System.out.println("result====" + result);
					out.write(result);
				} else {
					out.write(result);
				}
	            logger.info("**********接收异步通知结束。**********");
	        } catch (Exception e) {
	            e.printStackTrace();
	            logger.error(e);
	        }
		}

	    //XML解析为Java对象
	    private static AsynNotificationReqDto parseXML(byte[] xmlString) {
	        Document document = null;
	        try {
	            InputStream is = new ByteArrayInputStream(xmlString);
	            SAXReader sax = new SAXReader(false);
	            document = sax.read(is);
	        } catch (DocumentException e) {
	            e.printStackTrace();
	        }
	        AsynNotificationReqDto dto = new AsynNotificationReqDto();
	        Element rootElement = document.getRootElement();
	        if (null == rootElement) {
	            return dto;
	        }
	        Element versionEliment = rootElement.element("VERSION");
	        if (null != versionEliment) {
	            dto.setVersion(versionEliment.getText());
	        }
	        Element merchantEliment = rootElement.element("MERCHANT");
	        if (null != merchantEliment) {
	            dto.setMerchant(merchantEliment.getText());
	        }
	        Element terminalEliment = rootElement.element("TERMINAL");
	        if (null != terminalEliment) {
	            dto.setTerminal(terminalEliment.getText());
	        }
	        Element datalEliment = rootElement.element("DATA");
	        if (null != datalEliment) {
	            dto.setData(datalEliment.getText());
	        }
	        Element signEliment = rootElement.element("SIGN");
	        if (null != signEliment) {
	            dto.setSign(signEliment.getText());
	        }
	        return dto;
	    }


	    //对Base64进行解密
	    public static byte[] decryptBASE64(String key) throws Exception {
	        return (new BASE64Decoder()).decodeBuffer(key);
	    }

	    /**
	     * 签名
	     */
	    public static String generateSign(String version, String merchant, String terminal, String data, String md5Key) throws Exception {
	        StringBuilder sb = new StringBuilder();
	        sb.append(version);
	        sb.append(merchant);
	        sb.append(terminal);
	        sb.append(data);
	        String sign = "";
	        sign = md5(sb.toString(), md5Key);
	        return sign;
	    }

	    public static String md5(String text, String salt) throws Exception {
	        byte[] bytes = (text + salt).getBytes();

	        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
	        messageDigest.update(bytes);
	        bytes = messageDigest.digest();

	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < bytes.length; i++) {
	            if ((bytes[i] & 0xff) < 0x10) {
	                sb.append("0");
	            }
	            sb.append(Long.toString(bytes[i] & 0xff, 16));
	        }
	        return sb.toString().toLowerCase();
	    }
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
			return "success";
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
					/*int  i=sitePaymentRecordsService.giveLottery(map);
					if(i<1){
						logger.error("赠送用户彩票出错--orderNum:" + orderNum);
						throw Lang.makeThrow("赠送用户彩票出错--orderNum:"+ orderNum);
					}*/
					// 修改支付用户的到期 时间
					int i= sitePaymentRecordsService.changeUserExpireMeal(map);
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
//					i = sitePaymentRecordsService.saveSchooleFinanceRecord(
//							new BigDecimal(map.get("amount")),
//							Integer.parseInt(map.get("storeId")),
//							Integer.parseInt(map.get("userId")), userName);
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
		return "success";
	}
}
