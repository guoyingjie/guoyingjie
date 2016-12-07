package com.broadeast.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.lang.Lang;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.xml.sax.SAXException;

import com.alibaba.fastjson.JSON;
import com.broadeast.bean.SitePriceConfigAll;
import com.broadeast.entity.AllMessage;
import com.broadeast.entity.CloudSite;
import com.broadeast.entity.CloudSiteRouters;
import com.broadeast.entity.PortalUser;
import com.broadeast.entity.SiteCustomerInfo;
import com.broadeast.entity.SitePaymentRecord;
import com.broadeast.entity.SitePriceConfig;
import com.broadeast.entity.UserBalance;
import com.broadeast.entity.UserRecativeLog;
import com.broadeast.entity.UserSign;
import com.broadeast.entity.WechatUserInfo;
import com.broadeast.util.BigDecimalUtil;
import com.broadeast.util.CalendarUtil;
import com.broadeast.util.SHA256;
import com.broadeast.weixin.comment.Configure;
import com.broadeast.weixin.comment.MD5;
import com.broadeast.weixin.comment.RandomStringGenerator;
import com.broadeast.weixin.comment.Signature;
import com.broadeast.weixin.comment.UnifiedOrderReqData;
import com.broadeast.weixin.comment.WxPayApi;
import com.broadeast.weixin.comment.XMLParser;
import com.util.thirdpay.Pay;

@Service
@SuppressWarnings("all")
public class WeChatOtherService {
	private static final String APPID = "wxc5fb6a6dabc34dfb";
	private static final String APPSECRET = "ceafa600a2d9b2a98d36885081d16058";
	private static final String MCHID = "1332831801";
	Logger logger = Logger.getLogger(WeChatOtherService.class);
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Resource(name = "nutDao")
	private Dao nutDao;
	
	@Resource(name = "templJdbcTemplate")
	private JdbcTemplate templJdbcTemplate;

	@Autowired
	private SiteService siteService;
	
	@Resource(name="userService")
	private UserService userService;
	
	@Autowired
	private SitePaymentRecordsService sitePaymentRecordsService;
	
	@Autowired
	private SchoolPaymentService schoolPaymentService;
	/**
	 * 
	 *	@Description:检验用户是否有归属场所
	 *  @author songyanbiao
	 *	@Date 2016年6月16日 
	 *	@param userId
	 *	@return
	 */
	public List<Map<String, Object>> checkUserSite(int userId){
		List<Map<String, Object>> ls=new ArrayList<Map<String, Object>>();
		try {
			String sql="SELECT * FROM t_cloud_site_portal WHERE portal_id=?";
			ls=jdbcTemplate.queryForList(sql,new Object[]{userId});  	
		} catch (Exception e) {
			logger.error("检验用户是否有归属场所出错",e);
		}
		return ls;
	}
	/**
	 * 
	 *	@Description:获取用户归属场所名称
	 *  @author songyanbiao
	 *	@Date 2016年6月17日 
	 *	@param siteId
	 *	@return
	 */
	public CloudSite getSiteName(int siteId){
		CloudSite site=null;
		try {
			site=nutDao.fetch(CloudSite.class, Cnd.where("id","=",siteId));
		} catch (Exception e) {
			logger.error("获取用户归属场所名称出错",e);
		}
		return site;
	}
	/**
	 * 
	 *	@Description:查询该手机号是否有余额
	 *  @author songyanbiao
	 *	@Date 2016年6月20日 
	 *	@param userName
	 *	@return
	 */
	public UserBalance getUserMoney(String openid){
		
		UserBalance ub=null;
		WechatUserInfo wu=null;
		try {
			wu=nutDao.fetch(WechatUserInfo.class, Cnd.where("openid","=",openid));
			if(wu!=null){
				PortalUser user=nutDao.fetch(PortalUser.class, Cnd.where("id","=",wu.getPortal_user_id()));
				ub=nutDao.fetch(UserBalance.class, Cnd.where("user_name","=",user.getUserName()));
			}
		} catch (Exception e) {
			logger.error("查询该手机号是否有余额出错",e);
		}
		
		return ub;
	}
	public UserBalance getUserBanlance(String userName){
		
		UserBalance ub=null;
		try {
			ub=nutDao.fetch(UserBalance.class, Cnd.where("user_name","=",userName));
		} catch (Exception e) {
			logger.error("查询该手机号是否有余额出错",e);
		}
		
		return ub;
	}
	
	/**
	 * 
	 *	@Description:检查是否已经绑定wifi账号
	 *  @author songyanbiao
	 *	@Date 2016年6月20日 
	 *	@param userName
	 *	@param sucUrl
	 *	@param failUrl
	 *	@param session
	 *	@return
	 *	@throws NumberFormatException
	 *	@throws IOException
	 *	@throws SAXException
	 *	@throws ParserConfigurationException
	 */
	public String checkNumBind( String userName, String sucUrl, String failUrl,HttpSession session,String openid) throws NumberFormatException, IOException, SAXException, ParserConfigurationException{
		// 判断用户是否已存在
		PortalUser pt = userService.getPortalUserByTel(userName);
		List<SitePriceConfigAll> siteLs=new ArrayList<SitePriceConfigAll>();
		List<List<Map<String, Object>>> siteNameLs=new ArrayList<List<Map<String, Object>>>();
		Map map= new HashMap<>();
		session.setAttribute("tel", userName);
		//如果该手机号未绑定wifi账号则直接充值余额
		if (pt == null) {
			return failUrl;
		}else{
			List<Map<String, Object>> ls=checkUserSite(pt.getId());
			if(ls.size()!=0){//如果ls不为空代表该手机用户有归属场所，则按照场所配置去缴费,ls为空则去充值余额
				session.setAttribute("proUser", pt);
				SitePriceConfigAll siteAll=null;
				for (int i = 0; i < ls.size(); i++) {
					siteAll = userService.getSitePriceConfigAll((int)ls.get(i).get("site_id"), pt.getId());
					if (siteAll != null) {
						CloudSite site=getSiteName((int)ls.get(i).get("site_id"));
						map.put(site, siteAll);
					}
				}
				if(siteLs!=null){
					session.setAttribute("siteAll", map); 
				}
				//检测用户账户下是否有余额
				//UserBalance ub=getUserMoney(openid);
				UserBalance ub=getUserBanlance(userName);
				if(ub!=null){
					session.setAttribute("sum", ub.getBalance());
				}else{
					session.setAttribute("sum", new BigDecimal("0.00"));
				}
				return sucUrl;
			}else{
				logger.error("没哟查到场所");
				return failUrl;
			}
		}
	}
	

	/**
	 * 微信支付异步通知
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
			return makeUserBalance(out_trade_no, trade_no);
		} else {
			return false;
		}
	}
	
/**
 * 
 *	@Description:我IE新支付给别人充值缴费异步通知
 *  @author songyanbiao
 *	@Date 2016年6月21日 
 *	@param orderNum 订单号
 *	@param tradeNum 微信支付交易号
 *	@return
 */
	public boolean makeUserBalance(final String orderNum,final String tradeNum){
		boolean flag = false;
		SitePaymentRecord payRecord = sitePaymentRecordsService.getRecordByOrderNum(orderNum);
		if (payRecord == null) {// 无支付记录
			logger.error("校园卡支付记录获取失败--orderNum:" + orderNum);
			return false;
		}
		if(payRecord.getIsFinish()==1){
			return true;
		}
		final Map<String, String> map = JSON.parseObject(
				payRecord.getParamJson(), Map.class);
		String checkResult= schoolPaymentService.paramsCheck(map);
		if(!"ok".equals(checkResult)){
				logger.error("支付订单保留参数与系统现有数据不一致--orderNum:"+orderNum+";支付宝交易号："+tradeNum);
				sitePaymentRecordsService.updateFailReason("支付订单保留参数与系统现有数据不一致",orderNum);
				return false;
		}
		final String userName = userService.getPortalUserById(Integer.parseInt(map.get("userId"))).getUserName();
		try {
			Trans.exec(new Atom() {
				@Override
				public void run() {
				/*	int  i=sitePaymentRecordsService.giveLottery(map);
					if(i<1){
						logger.error("赠送用户彩票出错--orderNum:" + orderNum);
						throw Lang.makeThrow("赠送用户彩票出错--orderNum:"+ orderNum);
					}*/
					int i = sitePaymentRecordsService.changeUserExpireMeal(map);
					if(i!=1){
						logger.error("修改支付用户的到期 时间失败");
						throw Lang.makeThrow("修改支付用户的到期 时间失败--");
					}
					i=sitePaymentRecordsService.saveSchooleFinanceRecord(new BigDecimal(map.get("userAccount")),Integer.parseInt(map.get("storeId")),
		    				Integer.parseInt(map.get("userId")),userName,Integer.parseInt(map.get("buyNum")),map.get("payName"),3);
							if (i != 1) {
								logger.error("校园卡账务信息表添加记录失败--");
								throw Lang.makeThrow("校园卡账务信息表添加记录失败--");
							}
							
					boolean y=sitePaymentRecordsService.updateCollect(new BigDecimal(map.get("amount")), Integer.parseInt(map.get("storeId")), Integer.parseInt(map.get("tenantId")));
						if(!y){
							throw Lang.makeThrow("计费表用户统计或场所统计插入或更新未成功"+ orderNum);
						}
					// 校园卡支付记录表状态修改为支付成功
					i = sitePaymentRecordsService.updateToFinish(tradeNum,orderNum);
					if (i != 1) {// 执行不成功
						logger.error("校园卡支付记录表状态修改失败--orderNum:" + orderNum );
						sitePaymentRecordsService.updateFailReason("校园卡支付记录表状态修改失败：", orderNum);
						throw Lang.makeThrow("校园卡支付记录表状态修改失败--orderNum:" + orderNum );
					}
				}
			});
			flag = true;
		} catch (Exception e) {
			logger.error("支付过程事务故障", e);
			return false;
		}
		if(flag){
			try {
			     addUserMessage(Integer.parseInt(map.get("userId")),map.get("userAccount"));
			     userService.updateUserInfo(userName, map.get("userAccount"));
			     if(map.get("times")!=null){
				     addReaplactiveTime(map.get("times"), Integer.valueOf(map.get("userId")));
				     addRepcativeLog(map.get("times"), map.get("tel"),new BigDecimal(map.get("userAccount")));
			      }
			} catch (Exception e) {logger.error("签到失败");}
		}
		return true;
	};

	/**
	 * 微信支付异步通知
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	protected boolean wxBanlanceNotify(HttpServletRequest request,HttpServletResponse response) throws ServletException, 
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
			return banlanceNotify(out_trade_no, trade_no);
		} else {
			return false;
		}
	}
	/**
	 * 
	 *	@Description:微信支付充值余额异步通知处理
	 *  @author songyanbiao
	 *	@Date 2016年6月21日 
	 *	@param orderNum
	 *	@param tradeNum
	 *	@return
	 */
	public boolean banlanceNotify(final String orderNum,final String tradeNum){
		SitePaymentRecord payRecord = sitePaymentRecordsService.getRecordByOrderNum(orderNum);
		if (payRecord == null) {// 无支付记录
			logger.error("校园卡支付记录获取失败--orderNum:" + orderNum);
			return false;
		}
		if(payRecord.getIsFinish()==1){
			return true;
		}
		final Map<String, String> map = JSON.parseObject(payRecord.getParamJson(), Map.class);
		boolean flag = false;
		try {
			Trans.exec(new Atom() {
				@Override
				public void run() {
					//更改用户账上余额
					int i=sitePaymentRecordsService.updatePayBalance(map.get("tel"), map.get("amount"));
					if(i!=1){
						logger.error("更改用户账上余额失败--orderNum:" + orderNum);
						sitePaymentRecordsService.updateFailReason(
								"更改用户账上余额失败：", orderNum);
						throw Lang.makeThrow("校园卡支付记录表状态修改失败--orderNum:"
								+ orderNum);
					}
					// 校园卡支付记录表状态修改为支付成功
					i = sitePaymentRecordsService.updateToFinish(tradeNum,orderNum);
						if (i != 1) {// 执行不成功
							logger.error("校园卡支付记录表状态修改失败--orderNum:" + orderNum );
							sitePaymentRecordsService.updateFailReason("校园卡支付记录表状态修改失败：", orderNum);
							throw Lang.makeThrow("校园卡支付记录表状态修改失败--orderNum:" + orderNum );
					    }
				    }
			    });
			    flag = true;
		} catch (Exception e) {
			logger.error("支付过程事务故障", e);
			return false;
		}
		if(flag){
			if(map.get("times")!=null){
				addReaplactiveTime(map.get("times"), Integer.valueOf(map.get("userId")));
				addRepcativeLog(map.get("times"), map.get("tel"),new BigDecimal(map.get("amount")));
			}
		}
		return true;
	}
	
	/**
	 * 
	 *	@Description:微信给他人充值余额
	 *  @author songyanbiao
	 *	@Date 2016年6月21日 
	 *	@param request
	 *	@param response
	 *	@param session
	 *	@return
	 */
	public String winxinPayOhterBalance(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			String openId=(String)session.getAttribute("openid");
			if (null == openId || "".equals(openId)) {
				logger.error("此时openid为空····");
				return "/wechat/weixinerror";
			}
			Map<String, String> map = new HashMap<String, String>();
			String amount = request.getParameter("amount") + "";//金额
//			String userName= (String)session.getAttribute("tel");//用户手机号
			String userName=request.getParameter("userName");
			String out_trade_no= RandomStringGenerator.getRandomStringByLength(32);//订单号
			float sessionmoney = Float.parseFloat(amount);
			String finalmoney = String.format("%.2f", sessionmoney);
			finalmoney = finalmoney.replace(".", "");
			logger.error("amount="+amount+"---userName="+userName+"----out_trade_no"+out_trade_no+"---");
			logger.error("sessionmoney="+sessionmoney+"---finalmoney="+finalmoney+"-------");
			PortalUser user=userService.getPortalUserByTel(userName);;//拿到当前的用户信息
			if(user==null){
				map.put("userId", "-1");// 用户Id	
			}else{
				map.put("userId", user.getId()+"");// 用户Id	
			}
			// 抽取必填参数
			map.put("amount", amount.trim());// 总金额
			map.put("payUser", openId);//充值方的微信openid;
			map.put("tel", userName);//被充值的手机号
			
			map.put("storeId", "-1");// 场所Id
			// 保存支付信息
			schoolPaymentService.savePaymentinfo(out_trade_no, map,3);		
			
			UnifiedOrderReqData reqData = new UnifiedOrderReqData.UnifiedOrderReqDataBuilder(APPID, MCHID, 
					new String(("宽东方余额充值").getBytes(),"utf-8"),
					out_trade_no,Integer.parseInt(finalmoney),request.getRemoteAddr(), 
					Configure.NOTIFYBALANCE_URL,"JSAPI", "WEB").setOpenid(openId).build();
			Map<String, Object> reMap = WxPayApi.UnifiedOrder(reqData);
			logger.error("reMap==="+reMap);
			String prepay_id = "";
			if ((reMap.get("result_code")+"").trim().equals("SUCCESS")) { 
				prepay_id = reMap.get("prepay_id")+"";
			} else {
				logger.error("微信支付从微信服务器返货的prepay_id为空····");
				return "/wechat/weixinerror";
			}
			logger.error("prepay_id==="+prepay_id);
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
			request.getSession().setAttribute("amount", amount); //支付金额
		} catch (Exception e) {
			logger.error("JSAPI统一下单失败", e);
			return "/wechat/weixinerror";
		}
		return "/wechat/weixinpayother";
	}
	/**
	 * 
	 *	@Description:微信支付给他人充值
	 *  @author songyanbiao
	 *	@Date 2016年6月21日 
	 *	@param request
	 *	@param response
	 *	@param session
	 *	@return
	 */
	public String weiXinPayOtherAccount(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		Map<String, String> map = new HashMap<String, String>();
		String nums = request.getParameter("nums") + "";
		String amount = request.getParameter("amount") + "";//实际应付的金额
		String siteConfigId = request.getParameter("siteConfigId") + "";
		String siteId = request.getParameter("siteId") + "";
		String priceNum = request.getParameter("priceNum") + "";
		String userName=request.getParameter("userName");
		String mealType=request.getParameter("mealType") + "";
		String giveNum=request.getParameter("addMealNum");//赠送的套餐数量
		String giveUnit=request.getParameter("addMealUnit");//赠送的套餐单位
		String wayPay=request.getParameter("payWay"); //1代表用户使用微信支付,2代表用户使用余额支付
		String userAccount=request.getParameter("userAccount") + "";//用户没有使用余额抵消前的金额
		String openId=request.getParameter("openid")+"";
		String times=request.getParameter("times")+"";
		if (null == openId || "".equals(openId)) {
			logger.error("此时openid为空····"+openId);
			return "/wechat/weixinerror";
		}
		
		PortalUser user=userService.getPortalUserByTel(userName);;//拿到当前的用户信息
		if(user==null){//防止session丢失
			logger.error("session丢失");
			return "/wechat/weixinerror";
		}
		CloudSite site=getSiteName(Integer.parseInt(siteId));
		// 抽取必填参数
		map.put("userId", (user.getId()+"").trim());// 用户Id
		map.put("storeId", siteId.trim());// 场所Id
		map.put("tenantId", site.getUser_id()+"");// 商户Id
		map.put("payType", siteConfigId.trim());// 场所收费配置Id
		map.put("buyNum", nums.trim());// 购买数量
		map.put("amount", userAccount);// 总金额
		map.put("priceNum", priceNum.trim());
		map.put("addMealNum",giveNum);
		map.put("addMealUnit",giveUnit);
		map.put("mealType",mealType);//选择的套餐类型 1----时间，2-----流量
		map.put("tel", user.getUserName());//被充值的手机号
		map.put("payUser", openId);//充值方的微信openid;
		map.put("payWay", wayPay);//充值方的微信openid;
		map.put("userAccount", userAccount);//用户未使用余额抵消钱实际应付的金额
		if(!times.equals("null")){
			map.put("times", times);
		}
		SitePriceConfig scf = siteService.getSitePriceInfos(Integer.parseInt(siteId),Integer.parseInt(siteConfigId));
		SiteCustomerInfo scii = siteService.getExpirationTimeByProuserid(user.getId(),Integer.parseInt(siteId));
		String out_trade_no = Pay.getUuidOrderNumFromUserId(map.get("userId"));
	
		//wayPay为1用户使用微信支付
		if(wayPay.equals("1")){
			try {
				float sessionmoney = Float.parseFloat(amount);
				String finalmoney = String.format("%.2f", sessionmoney);
				finalmoney = finalmoney.replace(".", "");
				// 校验
				String checkResult = schoolPaymentService.paramsCheck(map);
				if ("ok".equals(checkResult)) {// 校验通过
					if(scf==null){
						map.put("payName","会员价");
					}else{
						map.put("payName", scf.getName());
					}
				
					//计算用户到期时间或者流量
					String riqi=siteService.getUserCustomer(scii, scf, map);
					if("1".equals(map.get("mealType"))){//用户购买的是时间套餐
						map.put("expireDate",riqi);
					}else{
						map.put("expireFlow",riqi);
					}
					// 保存支付信息
					schoolPaymentService.savePaymentinfo(out_trade_no, map,3);
					 
						UnifiedOrderReqData reqData = new UnifiedOrderReqData.UnifiedOrderReqDataBuilder(APPID, MCHID, 
								new String(("宽东方会员按(" + scf.getName()+")充值").getBytes(),"utf-8"),
								out_trade_no,Integer.parseInt(finalmoney),request.getRemoteAddr(), 
								Configure.NOTIFYOTHER_URL,"JSAPI", "WEB").setOpenid(openId).build();
						Map<String, Object> reMap = WxPayApi.UnifiedOrder(reqData);
						String prepay_id = "";
						if ((reMap.get("result_code")+"").trim().equals("SUCCESS")) {
							prepay_id = reMap.get("prepay_id")+"";
						} else {
							logger.error("微信支付从微信服务器返货的prepay_id为空····");
							return "/wechat/weixinerror";
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
						request.getSession().setAttribute("siteId", siteId); //场所id
						request.getSession().setAttribute("userId", user.getId()); //用户id
				} else {// 校验不通过
					logger.error("校验不通过");
					return "/wechat/weixinerror";
				}
			} catch (Exception e) {
				logger.error("JSAPI统一下单失败", e);
				return "/wechat/weixinerror";
			}
			if(!times.equals("null")){
				return "/wechat/wxpayorepala";
			}else{
				
				return "/wechat/weixinpayother";
			}
			
		}else{
			//获取用户的额余额
			BigDecimal allMoney=null;
			//UserBalance ub=getUserMoney(openId);
			UserBalance ub=getUserBanlance(userName);
			if(ub==null){
				allMoney=new BigDecimal("0");
			}else{
				allMoney=ub.getBalance();
			}
			if(allMoney==null){//防止session丢失
				return "/wechat/weixinerror";
			}
			//判断用户应支付的金额与用户的余额比较
			String usedAccount=request.getParameter("usedAccount") + "";//用户的余额
			map.put("usedAccount", usedAccount);
			if(allMoney.compareTo(new BigDecimal(usedAccount))!=0){
				logger.error("前台传的用户余额与后台校验不相等");
				return "/mobile/weixinerror";
			}
			BigDecimal balance=BigDecimalUtil.subtract(new BigDecimal(userAccount), new BigDecimal(usedAccount));
		
			if(balance.compareTo(new BigDecimal("0.0000"))!=1){//如果用户的余额足够支付此次购买套餐的总额,则直接扣余额.反之去微信支付缴费
				
				balance=BigDecimalUtil.subtract(new BigDecimal(usedAccount), new BigDecimal(userAccount));
				if(balance.compareTo(new BigDecimal(amount))!=0){
					logger.error("前台传的应支付金额与后台校验不相等");
					return "/mobile/weixinerror";
				}
				map.put("balance", balance.toString());
				if(scf==null){
					map.put("payName","会员价");
				}else{
					map.put("payName", scf.getName());
				}
				//计算用户到期时间或者流量
				String riqi=siteService.getUserCustomer(scii, scf, map);
				if("1".equals(map.get("mealType"))){//用户购买的是时间套餐
					map.put("expireDate",riqi);
				}else{
					map.put("expireFlow",riqi);
				}
				schoolPaymentService.savePaymentinfo(out_trade_no, map,3);
				boolean result=makeBalancePay(map, out_trade_no);
				if(result){  
					request.getSession().setAttribute("siteId", siteId); //场所id
					request.getSession().setAttribute("userId", user.getId()); //用户id
					if(!times.equals("null")){
						return "/wechat/wxsuccess";
					}else{
						
						return "/wechat/weixinsuccess";
					}
				}
				return "/wechat/weixinerror";
				
			}else{
				
				try {
				 
					if(balance.compareTo(new BigDecimal(amount))!=0){
						logger.error("前台传的应支付金额与后台校验不相等");
						return "/wechat/weixinerror";
					}
					map.put("balance","0");
					float sessionmoney = Float.parseFloat(amount);
					String finalmoney = String.format("%.2f", sessionmoney);
					finalmoney = finalmoney.replace(".", "");
					// 校验
					String checkResult = schoolPaymentService.paramsCheck(map);
					if ("ok".equals(checkResult)) {// 校验通过
						if(scf==null){
							map.put("payName","会员价");
						}else{
							map.put("payName", scf.getName());
						}
					
						//计算用户到期时间或者流量
						String riqi=siteService.getUserCustomer(scii, scf, map);
						if("1".equals(map.get("mealType"))){//用户购买的是时间套餐
							map.put("expireDate",riqi);
						}else{
							map.put("expireFlow",riqi);
						}
						// 保存支付信息
						schoolPaymentService.savePaymentinfo(out_trade_no, map,3);
						 
							UnifiedOrderReqData reqData = new UnifiedOrderReqData.UnifiedOrderReqDataBuilder(APPID, MCHID, 
									new String(("宽东方会员按(" + scf.getName()+")充值").getBytes(),"utf-8"),
									out_trade_no,Integer.parseInt(finalmoney),request.getRemoteAddr(), 
									Configure.NOTIFYOTHER_URL,"JSAPI", "WEB").setOpenid(openId).build();
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
							request.getSession().setAttribute("siteId", siteId); //场所id
							request.getSession().setAttribute("userId", user.getId()); //用户id
					} else {// 校验不通过
						return "/wechat/weixinerror";
					}
				} catch (Exception e) {
					logger.error("JSAPI统一下单失败", e);
					return "/wechat/weixinerror";
				}
				if(!times.equals("null")){
					return "/wechat/wxpayorepala";
				}else{
					
					return "/wechat/weixinpayother";
				}
			}
		}
	}
	
	
	/**
	 * 
	 *	@Description:微信支付是否支付成功
	 *  @author songyanbiao
	 *	@Date 2016年6月22日 
	 *	@param session
	 *	@param outTradeNo
	 *	@param amount
	 *	@return
	 */
	public String getPayOtherStatus(HttpSession session, String outTradeNo){
		 boolean isok = userService.checkPayResult(outTradeNo);
		   if(isok){
			   return "/wechat/weixinsuccess";
		   }else{
			   return "/wechat/weixinerror";
		   }
	}
	/**
	 * 
	 *	@Description:微信支付异步通知
	 *  @author songyanbiao
	 *	@Date 2016年6月22日 
	 *	@param request
	 *	@param response
	 *	@throws IOException
	 */
	public void wxNotifyNotice(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			if (excute(request, response)) {
				response.getWriter().write(XMLParser.setXML("SUCCESS", "weixin pay SUCCESS"));
				logger.info("已经进入微信支付异步通知····");
			} else {
				response.getWriter().write(XMLParser.setXML("FAIL", "weixin pay fail"));
			}
			return;
		} catch (Exception e) {
			logger.error("支付结果通知接口异常.", e);
			response.getWriter().write(XMLParser.setXML("FAIL", "weixin pay fail"));
		}
	} 
	/**
	 * 
	 *	@Description:微信支付帮别人充值余额异步通知
	 *  @author songyanbiao
	 *	@Date 2016年6月22日 
	 *	@param request
	 *	@param response
	 * @throws IOException 
	 */
	public void balanceNotifyNotice(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			if (wxBanlanceNotify(request, response)) {
				response.getWriter().write(XMLParser.setXML("SUCCESS", "weixin pay SUCCESS"));
				logger.info("已经进入微信支付异步通知····");  
			} else {
				response.getWriter().write(XMLParser.setXML("FAIL", "weixin pay fail"));
			}
			return;
		} catch (Exception e) {
			logger.error("支付结果通知接口异常.", e);
			response.getWriter().write(XMLParser.setXML("FAIL", "weixin pay fail"));
		}
	}
	/**
	 * 
	 *	@Description:余额支付
	 *  @author songyanbiao
	 *	@Date 2016年6月24日 
	 *	@param map
	 *	@param orderNum
	 *	@return
	 */
	public boolean makeBalancePay(final Map<String ,String> map,final String orderNum){
		SitePaymentRecord payRecord = sitePaymentRecordsService
				.getRecordByOrderNum(orderNum);
		if (payRecord == null) {// 无支付记录
			logger.error("校园卡支付记录获取失败--orderNum:" + orderNum);
			return false;
		}
		if(payRecord.getIsFinish()==1){
			return true;
		}

		try {
			Trans.exec(new Atom() {
				@Override
				public void run() {
					int  i = sitePaymentRecordsService.changeUserExpireMeal(map);
					if(i!=1){
						logger.error("修改支付用户的到期 时间失败");
						throw Lang.makeThrow("修改支付用户的到期 时间失败--");
					}
					
					String userName = userService.getPortalUserById(
							Integer.parseInt(map.get("userId"))).getUserName();
					i=sitePaymentRecordsService.saveSchooleFinanceRecord(new BigDecimal(map.get("userAccount")),Integer.parseInt(map.get("storeId")),
		    				Integer.parseInt(map.get("userId")),userName,Integer.parseInt(map.get("buyNum")),map.get("payName"),3);
							if (i != 1) {
								logger.error("校园卡账务信息表添加记录失败--");
								throw Lang.makeThrow("校园卡账务信息表添加记录失败--");
							}
					boolean y=sitePaymentRecordsService.updateCollect(new BigDecimal(map.get("userAccount")), Integer.parseInt(map.get("storeId")), Integer.parseInt(map.get("tenantId")));
					if(!y){
						throw Lang.makeThrow("计费表用户统计或场所统计插入或更新未成功"+ orderNum);
					}		
							
					// 校园卡支付记录表状态修改为支付成功
					i = sitePaymentRecordsService.updateToFinish(orderNum);
					if (i != 1) {// 执行不成功
						logger.error("校园卡支付记录表状态修改失败--:" );
						throw Lang.makeThrow("校园卡支付记录表状态修改失败--:" );
					}
						//更改用户账上余额
					i=sitePaymentRecordsService.updateUserBalance((map.get("payUser")), map.get("balance"));
					if(i!=1){
						logger.error("更改用户账上余额失败--orderNum:");
						throw Lang.makeThrow("校园卡支付记录表状态修改失败--:");
					}
					i=addUserMessage(Integer.parseInt(map.get("userId")),map.get("userAccount"));
					if(i!=1){
						logger.error("消息推送添加失败--:");
						throw Lang.makeThrow("消息推送添加失败--:");
					}
					if(map.get("times")!=null){
						i=addReaplactiveTime(map.get("times"), Integer.valueOf(map.get("userId")));
						if (i < 1){
							logger.error("添加补签日期失败--orderNum:" + orderNum );
							throw Lang.makeThrow("添加补签日期失败--orderNum:" + orderNum );
						}
						
						i=addRepcativeLog(map.get("times"), map.get("tel"),new BigDecimal(map.get("userAccount")));
						if(i<1){
							logger.error("添加补签记录失败--orderNum:" + orderNum );
							throw Lang.makeThrow("添加补签记录失败--orderNum:" + orderNum );
						}
					}
					
				}
			});
			
		} catch (Exception e) {
			logger.error("支付过程事务故障", e);
			return false;
		}
		return true;
	}
	/**
	 * 
	 *	@Description:给别人充值推送消息
	 *  @author songyanbiao
	 *	@Date 2016年6月27日 
	 *	@param userId
	 *	@param content
	 *	@return
	 */
	public int addUserMessage(int userId,String account){
		String sql=" INSERT INTO t2_site_user_message (user_id,content,state,create_time) VALUES(?,?,?,?)";
		String content="您的账户已充值"+account+"元成功";
		try {
			jdbcTemplate.update(sql, new Object[]{userId,content,0,new Timestamp(new Date().getTime())});
			return 1;
		} catch (Exception e) {
			logger.error("推送消息出错");
			return 0;
		}
	}
	
 
	/**
	 * @Description  消息推送
	 * @date 2016年11月22日下午1:05:33
	 * @author guoyingjie
	 * @param userId
	 * @param content
	 */
	public void toActiveUserMessage(int userId,String content){
		String sql=" INSERT INTO t2_site_user_message (user_id,content,state,create_time) VALUES(?,?,?,?)";
		try {
			jdbcTemplate.update(sql, new Object[]{userId,content,0,new Timestamp(new Date().getTime())});
		} catch (Exception e) {
			logger.error("推送消息出错");
		}
	}
 
	
	/**
	 * 
	 * @Description:补签充值余额插入补签的日期	
	 * @author songyanbiao
	 * @date 2016年11月3日 下午1:24:44
	 * @param
	 * @return
	 */
	public int addReaplactiveTime(String times,int userId){
		try {
			UserSign us=nutDao.fetch(UserSign.class, Cnd.where("portal_user_id","=",userId));
			if(us==null){
				us= new UserSign();
				us.setCreateTime(new Date());
				us.setSignNum(1);
				us.setUserId(userId);
				us.setSignTimeState(new Date());
				us.setSignTime(times.split("-")[1]+",");
				return nutDao.insert(us).getId();
			}else{
				us.setSignNum(us.getSignNum()+1);
				us.setSignTime(us.getSignTime()+times.split("-")[1]+",");
				return nutDao.update(us);
			}
		} catch (Exception e) {
			logger.error("充值余额补签出错",e);
			return -1;
		}
		
	}
	/**
	 * 
	 * @Description:插入补签记录	
	 * @author songyanbiao
	 * @date 2016年11月3日 下午1:32:15
	 * @param
	 * @return
	 */
	public int addRepcativeLog(String times,String userName,BigDecimal money){
		try {
			UserRecativeLog url= new UserRecativeLog();
			url.setCreateTime(new Date());
			url.setRacativeDay(times);
			url.setUserName(userName);
			url.setRacativeMoney(money);
			return nutDao.insert(url).getId();
			
		} catch (Exception e) {
			logger.error("插入补签记录失败");
			return -1;
		}
	}
	
}
