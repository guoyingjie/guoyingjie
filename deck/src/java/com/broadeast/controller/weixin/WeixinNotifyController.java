package com.broadeast.controller.weixin;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.nutz.lang.Lang;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.SAXException;

import com.alibaba.fastjson.JSON;
import com.broadeast.bean.SitePriceConfigAll;
import com.broadeast.entity.PortalUser;
import com.broadeast.entity.SiteCustomerInfo;
import com.broadeast.entity.SitePaymentRecord;
import com.broadeast.entity.SitePriceConfig;
import com.broadeast.handler.JsonView;
import com.broadeast.service.impl.AlipayService;
import com.broadeast.service.impl.SchoolPaymentService;
import com.broadeast.service.impl.SitePaymentRecordsService;
import com.broadeast.service.impl.SiteService;
import com.broadeast.service.impl.UserService;
import com.broadeast.util.DateUtil;
import com.broadeast.weixin.comment.Configure;
import com.broadeast.weixin.comment.MD5;
import com.broadeast.weixin.comment.RandomStringGenerator;
import com.broadeast.weixin.comment.Signature;
import com.broadeast.weixin.comment.UnifiedOrderReqData;
import com.broadeast.weixin.comment.WxPayApi;
import com.broadeast.weixin.comment.XMLParser;
import com.util.thirdpay.Pay;

@Controller
@RequestMapping(value = "/weixinNotify")
@SuppressWarnings("all")
public class WeixinNotifyController {
	private static Logger logger = Logger.getLogger(WeixinNotifyController.class);
	private static final String APPID = "wxc5fb6a6dabc34dfb";
	private static final String APPSECRET = "ceafa600a2d9b2a98d36885081d16058";
	private static final String MCHID = "1332831801";

	@Autowired
	private SchoolPaymentService schoolPaymentService;
	@Autowired
	private SitePaymentRecordsService sitePaymentRecordsService;
	@Autowired
	private SiteService siteService;
	@Autowired
	private UserService userService;
	@Autowired
	private AlipayService alipayService;

	/**
	 * 微信支付
	 * 
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/weixinPay")
	public String weixinPay(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try {
			String nums = request.getParameter("nums") + "";
			String amount = request.getParameter("amount") + "";
			String siteConfigId = request.getParameter("siteConfigId") + "";
			String code = request.getParameter("code") + "";
			String siteId = request.getParameter("siteId") + "";
			String portalId = request.getParameter("portalId") + "";
			String priceNum = request.getParameter("priceNum") + "";
			Map<String, String> map = new HashMap<String, String>();
			if ("".equals(code.trim())) {
				return "/mobile/weixinerror";
			}
			float sessionmoney = Float.parseFloat(amount);
			String finalmoney = String.format("%.2f", sessionmoney);
			finalmoney = finalmoney.replace(".", "");

			// 抽取必填参数
			map.put("userId", portalId.trim());// 用户Id
			map.put("storeId", siteId.trim());// 场所Id
			map.put("payType", siteConfigId.trim());// 场所收费配置Id
			map.put("buyNum", nums.trim());// 购买数量
			map.put("amount", amount.trim());// 总金额
			map.put("priceNum", priceNum.trim());
			map.put("addMealNum","0");
			map.put("addMealUnit","0");
			map.put("mealType","1");//选择的套餐类型 1----时间，2-----流量
			
			// 校验
			String checkResult = schoolPaymentService.paramsCheck(map);
			if ("ok".equals(checkResult)) {// 校验通过
				SitePriceConfig scf = siteService.getSitePriceInfos(Integer.parseInt(siteId),Integer.parseInt(siteConfigId));
				SiteCustomerInfo scii = siteService.getExpirationTimeByProuserid(Integer.parseInt(portalId),Integer.parseInt(siteId));
				if(scf==null){
					map.put("payName","会员价");
				}else{
					map.put("payName", scf.getName());
				}
				// 没缴过费的话
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date now = new Date();
				String str1 = sdf.format(now.getTime());//当前时间
				String str2 = "";
				if (scii == null) {
					str2 = sdf.format(now.getTime());//如果当前用户没有缴费记录的话到期时间设置到当前时间
				} else {
					str2 = sdf.format(scii.getExpirationTime().getTime());//有缴费记录获得缴费记录信息
				}
				int cmp = DateUtil.compareDate(str1, str2);
				String riqi = "";
				if (cmp == 1) {
					// 到期时间小于等于当前时间时，在当前时间的基础上计算新的到期时间
					riqi = DateUtil.newDatePluss(scf.getPrice_type(),Integer.parseInt(map.get("buyNum")), str1,map.get("priceNum"));
				} else {
					// 到期时间大于当前时间时，在到期时间基础上计算新的到期时间
					riqi = DateUtil.newDatePluss(scf.getPrice_type(),Integer.parseInt(map.get("buyNum")), str2,map.get("priceNum"));
				}
				// 日期加减 0按小时收费；1按天;2按月
				map.put("expireDate", riqi);
				
				String out_trade_no = Pay.getUuidOrderNumFromUserId(map.get("userId"));
				// 保存支付信息
				schoolPaymentService.savePaymentinfo(out_trade_no, map,3);
				if ("".equals(code)) {
					logger.error("微信支付从微信服务器返回的code为空····");
					return "/mobile/weixinerror";
				}
				    String  openid = WxPayApi.getOpenid(APPID, APPSECRET, code);
					if (null == openid || "".equals(openid)) {
						logger.error("此时openid为空····");
						return "/mobile/weixinerror";
					}
					UnifiedOrderReqData reqData = new UnifiedOrderReqData.UnifiedOrderReqDataBuilder(APPID, MCHID, 
							new String(("宽东方会员按(" + scf.getName()+")充值").getBytes(),"utf-8"),
							out_trade_no,Integer.parseInt(finalmoney),request.getRemoteAddr(), 
							Configure.NOTIFY_URL,"JSAPI", "WEB").setOpenid(openid).build();
					Map<String, Object> reMap = WxPayApi.UnifiedOrder(reqData);
					String prepay_id = "";
					if ((reMap.get("result_code")+"").trim().equals("SUCCESS")) {
						prepay_id = reMap.get("prepay_id")+"";
					} else {
						logger.error("微信支付从微信服务器返货的prepay_id为空····");
						return "/mobile/weixinerror";
					}
					String nonceStr = RandomStringGenerator.getRandomStringByLength(16).trim();
					String timeStamp = RandomStringGenerator.getTimeStamp().trim();
					String string = "appId=" + APPID + "&nonceStr=" + nonceStr + "&package=prepay_id=" + prepay_id.trim() + "&signType=MD5&timeStamp=" + timeStamp + "&key=ceafa600a2d9b2a98d36885081d16058";
					// 生成支付签名,这个签名 给 微信支付的调用使用
					String paySign = MD5.MD5Encode(string).toUpperCase();
					request.getSession().setAttribute("outTradeNo", out_trade_no);
					request.getSession().setAttribute("paySign", paySign.trim());
					request.getSession().setAttribute("appId", APPID.trim());
					request.getSession().setAttribute("timeStamp", timeStamp); // 时间戳
					request.getSession().setAttribute("nonceStr", nonceStr); // 随机字符串
					request.getSession().setAttribute("signType", "MD5".trim()); // 加密格式
					request.getSession().setAttribute("package",("prepay_id=" + prepay_id).trim());// 预支付id
			} else {// 校验不通过
				return "/mobile/weixinerror";
			}
		} catch (Exception e) {
			logger.error("JSAPI统一下单失败", e);
			return "/mobile/weixinerror";
		}
		return "/mobile/weixinpay";
	}

	

	/**
	 * 微信通知
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/weixinNotifyNotice")
	public void weixinNotifyNotice(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			if (excute(request, response)) {
				response.getWriter().write(XMLParser.setXML("SUCCESS", "OK"));
				logger.info("已经进入微信支付异步通知····");
			} else {
				response.getWriter().write(XMLParser.setXML("FAIL", "FAIL"));
			}
			return;
		} catch (Exception e) {
			logger.error("支付结果通知接口异常.", e);
			response.getWriter().write(XMLParser.setXML("FAIL", "FAIL"));
		}
	}

	/**
	 * 微信异步通知
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	protected boolean excute(HttpServletRequest request,HttpServletResponse response) throws ServletException, 
	    IOException,ParserConfigurationException, SAXException {
		InputStream inStream = request.getInputStream();
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		String result = new String(outSteam.toByteArray(), "utf-8");// 获取微信调用我们notify_url的返回信息
		Map<String, Object> map = XMLParser.getMapFromXML(result);
		String sign = Signature.getSignFromResponseString(result);
		// 微信返回的订单号
		final String out_trade_no = map.get("out_trade_no") + "";
		// 微信交易号
		final String trade_no = map.get("transaction_id") + "";
		if ((map.get("sign") + "").trim().equals(sign.trim())&& (map.get("result_code") + "").trim().equals("SUCCESS")) {
			return wechatPayNotify(out_trade_no, trade_no);
		} else {
			return false;
		}
	}
 
	
	/**
	 * 微信支付异步通知处理方法
	 * 
	 * @param orderNum  订单号
	 * @param tradeNum  支付宝交易号
	 * @return true 成功 , false 失败
	 */
	public boolean wechatPayNotify(final String orderNum, final String tradeNum) {
		// 获取校园卡支付记录
		SitePaymentRecord payRecord = sitePaymentRecordsService.getRecordByOrderNum(orderNum);
		if (payRecord == null) {// 无支付记录
			logger.error("校园卡支付记录获取失败--orderNum:" + orderNum + ";支付宝交易号："+ tradeNum);
			return false;
		}
		// 校园卡支付记录表状态校验
		if (payRecord.getIsFinish() == 1) {// 支付状态为支付成功
			return true;
		}
		// 拿到paramMap,校验与系统中的用户状态是不是不一样。
		final Map<String, String> map = JSON.parseObject(payRecord.getParamJson(), Map.class);
		String checkResult = schoolPaymentService.paramsCheck(map);
		if (!"ok".equals(checkResult)) {
			logger.error("支付订单保留参数与系统现有数据不一致--orderNum:" + orderNum + ";支付宝交易号：" + tradeNum);
			sitePaymentRecordsService.updateFailReason("支付订单保留参数与系统现有数据不一致",orderNum);
			return false;
		}
		try {
			// 事务
			Trans.exec(new Atom() {
				@Override
				public void run() {
					// 修改支付用户的到期 时间
					int i = sitePaymentRecordsService.changeUserExpireDate(map.get("expireDate"),Integer.parseInt(map.get("storeId")),
							Integer.parseInt(map.get("userId")));
					if (i != 1) {
						logger.error("修改支付用户的到期 时间失败--orderNum:" + orderNum+ ";支付宝交易号：" + tradeNum);
						sitePaymentRecordsService.updateFailReason("修改支付用户的到期 时间失败", orderNum);
						throw Lang.makeThrow("修改支付用户的到期 时间失败--orderNum:"+ orderNum + ";支付宝交易号：" + tradeNum);
					}
					// 校园卡账务信息表添加记录
					String userName = userService.getPortalUserById(Integer.parseInt(map.get("userId"))).getUserName();
					i = sitePaymentRecordsService.saveSchooleFinanceRecord(
							new BigDecimal(map.get("amount")),
							Integer.parseInt(map.get("storeId")),
							Integer.parseInt(map.get("userId")),userName, Integer.parseInt(map.get("buyNum")),map.get("payName"),3);
					if (i != 1) {
						logger.error("校园卡账务信息表添加记录失败--orderNum:" + orderNum + ";支付宝交易号：" + tradeNum);
						sitePaymentRecordsService.updateFailReason("校园卡账务信息表添加记录失败", orderNum);
						throw Lang.makeThrow("校园卡账务信息表添加记录失败--orderNum:"+ orderNum + ";支付宝交易号：" + tradeNum);
					}

					// 校园卡支付记录表状态修改为支付成功
					i = sitePaymentRecordsService.updateToFinish(tradeNum,orderNum);
					if (i != 1) {// 执行不成功
						logger.error("校园卡支付记录表状态修改失败--orderNum:" + orderNum + ";支付宝交易号：" + tradeNum);
						sitePaymentRecordsService.updateFailReason("校园卡支付记录表状态修改失败：", orderNum);
						throw Lang.makeThrow("校园卡支付记录表状态修改失败--orderNum:" + orderNum + ";支付宝交易号：" + tradeNum);
					}
				}
			});
		} catch (Exception e) {
			logger.error("支付过程事务故障", e);
			return false;
		}
		return true;
	}

}
