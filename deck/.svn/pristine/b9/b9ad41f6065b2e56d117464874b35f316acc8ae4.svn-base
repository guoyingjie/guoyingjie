package com.broadeast.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.nutz.dao.Dao;
import org.nutz.lang.Lang;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;






import com.alibaba.fastjson.JSON;
import com.broadeast.entity.SitePaymentRecord;
import com.broadeast.entity.SitePriceConfig;
import com.broadeast.service.IPayCenterService;
import com.broadeast.util.BigDecimalUtil;
//import cn.solarsys.dao.PaymentRecordsDao;
//import cn.solarsys.entity.PaymentRecords;
//import cn.solarsys.entity.SchoolMember;
//import cn.solarsys.entity.SchoolPriceInfo;
import com.wap.alipay.config.AlipayConfig;


/**
 * @ToDoWhat 
 * @author xmm
 */
@Service
public class SchoolPaymentService {
	private static Logger logger = Logger.getLogger(SchoolPaymentService.class);
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private SitePaymentRecordsService sitePaymentRecordsService;
	
	@Autowired
	private UserService userService;
	
//	@Resource(name="aliWapDirectPayServiceImpl")
	@Autowired
	private IPayCenterService payCenterService;
	
//	public SchoolPaymentService(){
//    }
//	
//    public IPayCenterService getPayCenterService() {
//    	return payCenterService;
//    }
//	
//    public void setPayCenterService(IPayCenterService payCenterService) {
//    	this.payCenterService = payCenterService;
//    }

	/**
	 * 校验校园卡充值支付时的参数是否正确
	 * @param map
	 * @return 校验通过返回 "ok";不通过返回错误原因字符串。
	 */
	public String paramsCheck(Map<String,String> map){
		//map非空校验
		if(map.containsValue(null)||map.containsValue("")||map.containsValue("null"))return "参数不能含有空值";
		
//		//根据userId,siteId,查询出用户，非空。手机号匹配
//		SchoolMember sm=paymentRecordsDao.checkUser(Integer.parseInt(map.get("userId")), Integer.parseInt(map.get("storeId")));
//		if(sm==null)return "用户不存在或不是校园卡用户";
//		if(!map.get("userTel").equals(sm.getUserInfo().getUser().getTelephone())) return "用户手机号不匹配";
		
		//根据 siteId,sitePriceInfoId查询店铺配置的计费信息，非空
		SitePriceConfig spcf=siteService.getSitePriceInfos(Integer.parseInt(map.get("storeId")),Integer.parseInt(map.get("payType")));
		//SitePriceConfig spcf=siteService.getSitePriceInfo(Integer.parseInt(map.get("storeId")),Integer.parseInt(map.get("payType")),map.get("priceNum"));
		if(spcf==null)return "付费类型不存在";
		
		//校验金额  BigDecimal
		BigDecimal pageAmount=new BigDecimal(map.get("amount"));
		BigDecimal calculateResult=BigDecimalUtil.multiply(spcf.getUnit_price(), Integer.parseInt(map.get("buyNum")));
		if(!BigDecimalUtil.doubleValueEquals(pageAmount, calculateResult)){
			return "购买产品单价与配置信息不符";
		}
		//校验赠送的套餐
		if(!map.get("addMealNum").equals("0")){
			
			if(spcf.getGiveMeal()!=Integer.parseInt(map.get("addMealNum"))||spcf.getGiveMealUnit()!=Integer.parseInt(map.get("addMealUnit"))){
				return "购买赠送单价或数量与配置信息不符";
			}
		}
		return "ok";
	}
	
//	/**
//	 * 校验校园卡充值支付时的参数是否正确
//	 * @param map
//	 * @return 校验通过返回 "ok";不通过返回错误原因字符串。
//	 */
//	public String paramsCheck(Map<String,String> map){
//		//map非空校验
//		if(map.containsValue(null)||map.containsValue(""))return "参数不能含有空值";
//		
//		//根据userId,storeId,userTel查询出用户，非空。手机号匹配
//		SchoolMember sm=paymentRecordsDao.checkUser(Integer.parseInt(map.get("userId")), Integer.parseInt(map.get("storeId")));
//		if(sm==null)return "用户不存在或不是校园卡用户";
//		if(!map.get("userTel").equals(sm.getUserInfo().getUser().getTelephone())) return "用户手机号不匹配";
//		
//		//根据 storeId,payType查询店铺配置的计费信息，非空
//		SchoolPriceInfo spf=paymentRecordsDao.getPayTypeInfo(Integer.parseInt(map.get("storeId")),Integer.parseInt(map.get("payType")));
//		if(spf==null)return "付费类型不存在";
//		
//		//校验金额
//		if(Integer.parseInt(map.get("amount"))!=spf.getPriceValue()*Integer.parseInt(map.get("buyNum")))return "购买产品单价与配置信息不符";
//		
//		//校验到期时间，必须匹配
//		Calendar calendarNow=Calendar.getInstance();
//		boolean isExpired=true;
//		//获取到期时间，判断没有过期的话calendarNow应该等于到期时间
//		if(sm.getExpirationTime().getTime()>calendarNow.getTimeInMillis()){
//			calendarNow.setTime(sm.getExpirationTime());
//			isExpired=false;
//		}
//		
//		switch(spf.getPriceType()){//按照购买的信息，计算出到期时间
//			case 0 ://按月购买
//				calendarNow.add(Calendar.MONTH, Integer.parseInt(map.get("buyNum")));
//			 break;
//			case 1 ://按学期购买
//				Calendar nextTermCalendar=Calendar.getInstance();
//				int nowMonth=calendarNow.get(Calendar.MONTH);//0~11
//				if (1 < nowMonth && nowMonth < 7) {// 判断当前日期是否是在下班半学期(js,Date对象中月份是从0开始的)
//					nextTermCalendar.setTime(spf.getNextTermEndTime());
//					calendarNow.set(Calendar.MONTH, nextTermCalendar.get(Calendar.MONTH));
//					calendarNow.set(Calendar.DAY_OF_MONTH, nextTermCalendar.get(Calendar.DAY_OF_MONTH));
//				}else{// 当前时间为上班半学期
//					nextTermCalendar.setTime(spf.getLastTermEndTime());
//					calendarNow.add(Calendar.YEAR, 1);//年加一，上学期会跨年
//					calendarNow.set(Calendar.MONTH, nextTermCalendar.get(Calendar.MONTH));
//					calendarNow.set(Calendar.DAY_OF_MONTH, nextTermCalendar.get(Calendar.DAY_OF_MONTH));
//				}
//			 break;
//			case 2 ://按年购买
//				calendarNow.add(Calendar.YEAR, Integer.parseInt(map.get("buyNum")));
//			 break;
//		}
//		Date expireDate=calendarNow.getTime();
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		if(!isExpired&&!sdf.format(expireDate).equals(map.get("expireDate")))return "到期时间计算异常";
//		return "ok";
//	}
	
	/**保存用户支付的记录信息
	 * @param orderNum
	 * @param map
	 * @return 0失败。1成功
	 */
	public boolean savePaymentinfo(String orderNum,Map<String,String> map,int payType){
		boolean b=siteService.savePaymentinfo(orderNum, map,payType);
		return b;
	}
	
	
	/**
	 * 生成支付跳转的html
	 * @param out_trade_no  订单号，系统唯一
	 * @param subject  订单名称
	 * @param total_fee  付款金额
	 * @return
	 * @throws Exception 
	 */
	public String getPayHTML(String out_trade_no,String subject,String total_fee){
		
		//修改版
		return payCenterService.getPayHTML(out_trade_no, subject, total_fee);
	}
	
	/**
	 * 支付结果通知接口
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void Notify(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//获取支付宝POST过来反馈信息
		PrintWriter out=response.getWriter();
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		if(requestParams==null || requestParams.size()==0){
			logger.error("requestParams==null-----SchoolPaymentService-203行");
			out.write("fail");
			return;
		}
		for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
//			System.out.println(name+"--"+valueStr);
			params.put(name, valueStr);
		}
		
		/*//商户订单号
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//支付宝交易号
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		//交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");*/
		//修改版
		Map<String,String> map=payCenterService.getNotifyParams(params);
		String result="fail";
		if(payCenterService.verify(params)){//验证成功
			if (map.get("trade_status").equals("TRADE_SUCCESS")){
				result=AliPayNotify(map.get("out_trade_no"), map.get("trade_no"));
			}
			out.write(result);	//请不要修改或删除
		}else{//验证失败
				logger.error("验证失败---fail");
			out.write("fail");
		}
		
	}
	
	/**
	 * 阿里异步通知处理方法
	 * @param orderNum  订单号
	 * @param tradeNum 支付宝交易号
	 * @return  "success" 成功 ， "fail" 失败
	 */
	public String AliPayNotify(final String orderNum,final String tradeNum){
		//获取校园卡支付记录
		SitePaymentRecord payRecord=sitePaymentRecordsService.getRecordByOrderNum(orderNum);
		
		if(payRecord==null){//无支付记录
			logger.error("校园卡支付记录获取失败--orderNum:"+orderNum+";支付宝交易号："+tradeNum);
			return "fail";
		}
		//校园卡支付记录表状态校验
		if(payRecord.getIsFinish()==1){//支付状态为支付成功
			return "success";
		}
		//拿到paramMap,校验与系统中的用户状态是不是不一样。
		final Map<String,String> map=JSON.parseObject(payRecord.getParamJson(), Map.class);
		String checkResult= paramsCheck(map);
		if(!"ok".equals(checkResult)){
				logger.error("支付订单保留参数与系统现有数据不一致--orderNum:"+orderNum+";支付宝交易号："+tradeNum);
				sitePaymentRecordsService.updateFailReason("支付订单保留参数与系统现有数据不一致",orderNum);
				return "fail";
		}
		try {
	        //事务
	        Trans.exec(new Atom(){
	        	@Override
	        	public void run() {
	        		/*int  i=sitePaymentRecordsService.giveLottery(map);
					if(i<1){
						logger.error("赠送用户彩票出错--orderNum:" + orderNum);
						throw Lang.makeThrow("赠送用户彩票出错--orderNum:"+ orderNum);
					}*/
	               //修改支付用户的到期 时间
	        		int i=sitePaymentRecordsService.changeUserExpireMeal(map);
	        		if(i!=1){
	        			sitePaymentRecordsService.updateFailReason("修改支付用户的到期 时间失败",orderNum);
	        			throw Lang.makeThrow("修改支付用户的到期 时间失败--orderNum:"+orderNum+";支付宝交易号："+tradeNum);
	        			//return "fail";
	        		}
	        		
	        		//校园卡账务信息表添加记录
	        		String userName=userService.getPortalUserById(Integer.parseInt(map.get("userId"))).getUserName();
	        		i=sitePaymentRecordsService.saveSchooleFinanceRecord(new BigDecimal(map.get("amount")),Integer.parseInt(map.get("storeId")),
	        				Integer.parseInt(map.get("userId")),userName,Integer.parseInt(map.get("buyNum")),map.get("priceName"),1);
	        		if(i!=1){
	        			sitePaymentRecordsService.updateFailReason("校园卡账务信息表添加记录失败",orderNum);
	        			throw Lang.makeThrow("校园卡账务信息表添加记录失败--orderNum:"+orderNum+";支付宝交易号："+tradeNum);
	        		}
	        		//校园卡支付记录表状态修改为支付成功
	        		i=sitePaymentRecordsService.updateToFinish(tradeNum,orderNum);
	        		if(i!=1){//执行不成功
	        			sitePaymentRecordsService.updateFailReason("校园卡支付记录表状态修改失败：",orderNum);
	        			throw Lang.makeThrow("校园卡支付记录表状态修改失败--orderNum:"+orderNum+";支付宝交易号："+tradeNum);
	        		}
	        		boolean y=sitePaymentRecordsService.updateCollect(new BigDecimal(map.get("amount")), Integer.parseInt(map.get("storeId")), Integer.parseInt(map.get("tenantId")));
					if(!y)
						throw Lang.makeThrow("计费表用户统计或场所统计插入或更新未成功"+ orderNum);
	        	}
	        });
        }catch (Exception e) {
        	logger.error("支付过程事务故障",e);
        	e.printStackTrace();
        	return "fail";
        }
		return "success"; 
	}
}
