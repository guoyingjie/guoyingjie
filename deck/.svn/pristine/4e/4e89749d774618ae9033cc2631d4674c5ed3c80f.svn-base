package com.broadeast.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.InitBinder;

import com.alibaba.fastjson.JSON;
import com.broadeast.entity.CloudSite;
import com.broadeast.entity.CommonConfig;
import com.broadeast.entity.IncomeCollect;
import com.broadeast.entity.PortalUser;
import com.broadeast.entity.SiteCustomerInfo;
import com.broadeast.entity.SiteIncome;
import com.broadeast.entity.SiteIncomeCollect;
import com.broadeast.entity.SitePaymentRecord;
import com.broadeast.entity.SitePriceConfig;
import com.broadeast.entity.UserBalance;
import com.broadeast.entity.WechatUserInfo;
import com.broadeast.util.BigDecimalUtil;
import com.broadeast.util.DateUtil;
import com.broadeast.util.InitContext;
import com.broadeast.weixin.comment.JsonUtil;


/**场所消费支付记录 service
 * @ToDoWhat 
 * @author xmm
 */
@Service
public class SitePaymentRecordsService {
	private static Logger log = Logger.getLogger(SitePaymentRecordsService.class);
	private static String lock="1";
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="nutDao")
	private Dao nutDao;
	
	/**
	 * 根据orderNum获取相关记录
	 * @param orderNum
	 * @return
	 */
	public SitePaymentRecord getRecordByOrderNum(String orderNum){
		try {
			
			return nutDao.fetch(SitePaymentRecord.class, Cnd.where("orderNum", "=", orderNum));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 更新失败原因
	 * @param failReason
	 * @param orderNum
	 * @return
	 */
	public int updateFailReason(String failReason,String orderNum){
		SitePaymentRecord spr=getRecordByOrderNum(orderNum);
		spr.setFailReason(failReason);
		return nutDao.update(spr);
	}
	
	/**
	 * 更新用户到期时间
	 * @param expireDate
	 * @param siteId
	 * @param userId
	 * @return 1成功
	 */
	public int changeUserExpireDate(String expireDate,int siteId,int userId){
		SiteCustomerInfo sci=nutDao.fetch(SiteCustomerInfo.class, Cnd.where("siteId", "=", siteId).and("portal_user_id", "=", userId));
		if(sci==null){
			sci=new SiteCustomerInfo();
			sci.setExpirationTime(expireDate);
			sci.setSiteId(siteId);
			sci.setPortalUserId(userId);
			nutDao.insert(sci);
		}else{
			sci.setExpirationTime(expireDate);
			sci.setSiteId(siteId);
			sci.setPortalUserId(userId);
			sci.setUpdateTime(new Timestamp(new Date().getTime()));
			nutDao.update(sci);
		}
		return sci.getId()>0?1:0;
	}
	
	/**
	 * 更新用户到期套餐 时间或者流量
	 * @param expireFlow
	 * @param siteId
	 * @param userId
	 * @return
	 */
	public int changeUserExpireMeal(Map<String,String> map){
		SiteCustomerInfo sci=nutDao.fetch(SiteCustomerInfo.class, Cnd.where("siteId","=",Integer.parseInt(map.get("storeId"))).and("portal_user_id","=",Integer.parseInt(map.get("userId"))));
		try {
			if(sci==null){
				sci=new SiteCustomerInfo();
				if("1".equals(map.get("mealType"))){
					sci.setExpirationTime(map.get("expireDate"));
					sci.setTotalFlow("0");
					sci.setLastFlow("0");
				}else{
					sci.setExpirationTime(new Date());
					sci.setTotalFlow(map.get("expireFlow"));
					sci.setLastFlow(map.get("expireFlow"));
				}
				sci.setSiteId(Integer.parseInt(map.get("storeId")));
				sci.setPortalUserId(Integer.parseInt(map.get("userId")));
				sci.setCreateTime(new Timestamp(new Date().getTime()));
				sci.setIsTry(1);
				sci.setPayWay(0);
				sci.setUpdateTime(new Timestamp(new Date().getTime()));
				sci.setLottertTime("1970-01-01 00:00:00");
				nutDao.insert(sci);
			}else{
				if("1".equals(map.get("mealType"))){
					sci.setExpirationTime(map.get("expireDate"));
					if(sci.getTotalFlow()==null){
						sci.setTotalFlow("0");
					}
				}else{
					sci.setTotalFlow(map.get("expireFlow"));
					sci.setLastFlow(map.get("expireFlow"));
					if(sci.getExpirationTime()==null){
						sci.setExpirationTime(new Date());
					}
				}
				sci.setSiteId(Integer.parseInt(map.get("storeId")));
				sci.setPortalUserId(Integer.parseInt(map.get("userId")));
				sci.setIsTry(0);
				Date date = sci.getLottertTime();
				if(date==null){
					sci.setLottertTime("1970-01-01 00:00:00"); 
				}else{
					sci.setLottertTime(date); 
				}
				nutDao.update(sci);
			}
			return sci.getId()>0?1:0;
		} catch (Exception e) {
			log.error("更新用户到期套餐 时间或者流量失败");
			return 0;
		}
	}
 
	/**
	 * 保存场所收入记录
	 * @param amount
	 * @param siteId
	 * @param userId
	 * @param userName
	 * @return  1成功
	 */
	public int saveSchooleFinanceRecord(BigDecimal amount,int siteId,int userId,String userName){
		SiteIncome si=new SiteIncome();
		si.setSiteId(siteId);
		si.setPortalUserId(userId);
		si.setPortalUserName(userName);
		si.setTransactionAmount(amount);
	
		nutDao.insert(si);
		return si.getId()>0?1:0;
	}
	
	/**
	 * 保存场所收入记录
	 * @param amount
	 * @param siteId
	 * @param userId
	 * @param userName
	 * @return  1成功
	 * 交易类型1--支付宝 2--银行卡 3--微信 4--人工
	 */
	public int saveSchooleFinanceRecord(BigDecimal amount,int siteId,int userId,String userName,int buy_num,String payName,int payType){
		SiteIncome si=new SiteIncome();
		si.setSiteId(siteId);
		si.setPortalUserId(userId);
		si.setPortalUserName(userName);
		si.setTransactionAmount(amount);
		si.setPayName(payName);
		si.setBuyNum(buy_num);
		si.setPayType(payType);
		nutDao.insert(si);
		return si.getId()>0?1:0;
	}
	/**
	 * 
	 * @Description:更新商户汇总表和场所收入表	
	 * @author songyanbiao
	 * @date 2016年7月15日 下午3:32:06
	 * @param
	 * @return
	 */
	public boolean updateCollect(BigDecimal amount, int siteId, int userId) {
		int upback = 0;
		int upback1=0;
		try {
			String sql="UPDATE t4_income_collect SET platform_income=platform_income+?,account_income=account_income+? WHERE user_id=?";
			String sql1="UPDATE t4_site_income_collect SET platform_income=platform_income+?,account_income=account_income+? WHERE user_id=? AND site_id=?";
			upback=jdbcTemplate.update(sql,new Object[]{amount,amount,userId});
			upback1=jdbcTemplate.update(sql1,new Object[]{amount,amount,userId,siteId});
			System.out.println(1);
			if(upback==0&&upback1==0){
				System.out.println(2);
				synchronized (lock) {
					System.out.println(3);
					IncomeCollect incomeCollect = nutDao.fetch(IncomeCollect.class,Cnd.where("user_id", "=", userId));
					if(incomeCollect==null){
						incomeCollect=new IncomeCollect();
						CommonConfig comconfig = nutDao.fetch(CommonConfig.class,Cnd.where("ident","=","881"));
						incomeCollect.setUserId(userId+"");
						incomeCollect.setPlatformIncome(amount);
						incomeCollect.setAccountRefund(new BigDecimal("0"));
						incomeCollect.setOfflineIncome(new BigDecimal("0"));
						incomeCollect.setWithdrawTime(new Date().getTime());
						incomeCollect.setLowestMoney(comconfig.getCommonMinMoney());
						incomeCollect.setShortestCycle(comconfig.getCommonBalanceday());
						incomeCollect.setChargeRate(comconfig.getCommonChagrge());
						incomeCollect.setAccountIncome(amount);
						upback = nutDao.insert(incomeCollect).getId();

					}else{
						upback=jdbcTemplate.update(sql,new Object[]{amount,amount,userId});

					}
					SiteIncomeCollect sitincomeCollect = nutDao.fetch(SiteIncomeCollect.class,Cnd.where("site_id", "=", siteId).and("user_id","=",userId));
					if(sitincomeCollect==null){
						sitincomeCollect=new SiteIncomeCollect();
						sitincomeCollect.setSiteId(siteId+"");
						sitincomeCollect.setUserId(userId+"");
						sitincomeCollect.setPlatformIncome(amount);
						sitincomeCollect.setAccountIncome(amount);
						sitincomeCollect.setOfflineIncome(new BigDecimal("0"));
						sitincomeCollect.setAccounRrefund(new BigDecimal("0"));
						upback1 = nutDao.insert(sitincomeCollect).getId();

					}else{
						upback1=jdbcTemplate.update(sql1,new Object[]{amount,amount,userId,siteId});

					}
					System.out.println(7);
				}
				System.out.println(8);
			}
			if(upback==0&&upback1!=0){
				IncomeCollect incomeCollect = nutDao.fetch(IncomeCollect.class,Cnd.where("user_id", "=", userId));
				if(incomeCollect==null){
					incomeCollect=new IncomeCollect();
					CommonConfig comconfig = nutDao.fetch(CommonConfig.class,Cnd.where("ident","=","881"));
					incomeCollect.setUserId(userId+"");
					incomeCollect.setPlatformIncome(amount);
					incomeCollect.setAccountRefund(new BigDecimal("0"));
					incomeCollect.setOfflineIncome(new BigDecimal("0"));
					incomeCollect.setWithdrawTime(new Date().getTime());
					incomeCollect.setLowestMoney(comconfig.getCommonMinMoney());
					incomeCollect.setShortestCycle(comconfig.getCommonBalanceday());
					incomeCollect.setChargeRate(comconfig.getCommonChagrge());
					incomeCollect.setAccountIncome(amount);
					upback = nutDao.insert(incomeCollect).getId();
				}else{
					upback=jdbcTemplate.update(sql,new Object[]{amount,amount,userId});

				}
			}
			
			if(upback!=0&&upback1==0){
				SiteIncomeCollect sitincomeCollect = nutDao.fetch(SiteIncomeCollect.class,Cnd.where("site_id", "=", siteId).and("user_id","=",userId));
				if(sitincomeCollect==null){
					sitincomeCollect=new SiteIncomeCollect();
					sitincomeCollect.setSiteId(siteId+"");
					sitincomeCollect.setUserId(userId+"");
					sitincomeCollect.setPlatformIncome(amount);
					sitincomeCollect.setAccountIncome(amount);
					sitincomeCollect.setOfflineIncome(new BigDecimal("0"));
					sitincomeCollect.setAccounRrefund(new BigDecimal("0"));
					upback1 = nutDao.insert(sitincomeCollect).getId();
				}else{
					upback1=jdbcTemplate.update(sql1,new Object[]{amount,amount,userId,siteId});

				}
			}
		} catch (Exception e) {
			log.error("用户缴费插入汇总表报错,缴费金额==="+amount+"商户id==="+userId+"场所id==="+siteId);
		}
		if(upback>0&&upback1>0){
			return true;
		}else{
			return false;

		}
	}
	/**
	 * 更新支付记录为成功
	 * @param tradeNum
	 * @param orderNum
	 * @return 1成功
	 */
	public int updateToFinish(String tradeNum,String orderNum){
		SitePaymentRecord spr=nutDao.fetch(SitePaymentRecord.class, Cnd.where("orderNum", "=", orderNum));
		spr.setIsFinish(1);
		spr.setTradeNum(tradeNum);
		spr.setFinishTime(new Date());
		spr.setFailReason("");
		return nutDao.update(spr);
	}
	
	/**
	 * 京东支付时更新支付记录为成功
	 * @param tradeNum
	 * @param orderNum
	 * @return 1成功
	 */
	public int updateToFinish(String orderNum){
		SitePaymentRecord spr=nutDao.fetch(SitePaymentRecord.class, Cnd.where("orderNum", "=", orderNum));
		spr.setIsFinish(1);
		spr.setFinishTime(new Date());
		spr.setFailReason("");
		return nutDao.update(spr);
	}
	/**
	 * 根据tradeNum查询用户交易记录
	 * @param TradeNum  第三方平台的交易账号
	 * @param siteId
	 * @param UserId
	 * @return
	 */
	public SitePaymentRecord getSitePaymentRecordByTradeNum(String ordrNum,String tradeNum,int siteId,int UserId){
		SitePaymentRecord spr=nutDao.fetch(SitePaymentRecord.class, Cnd.where("tradeNum", "=", tradeNum)
				.and("orderNum", "=", ordrNum)
				.and("siteId", "=", siteId).and("userId", "=", UserId));
		return spr;
	}
	/**
	 * 根据ordrNum查询用户交易记录
	 * @param siteId
	 * @param UserId
	 * @return
	 */
	public SitePaymentRecord getSitePaymentRecordByTradeNum(String ordrNum,int siteId,int UserId){
		SitePaymentRecord spr=nutDao.fetch(SitePaymentRecord.class, Cnd.where("orderNum", "=", ordrNum)
				.and("siteId", "=", siteId).and("userId", "=", UserId));
		return spr;
	}
	/**
	 * 
	 * @param 添加京东的交易授权令牌token
	 */
	public void insertToken(String token,int userId){
		PortalUser portalUser=nutDao.fetch(PortalUser.class,Cnd.where("token", "=", token)
				.and("id", "=", userId));
		if(portalUser==null){
			String sql = "update t_portal_user set token =? where id=?";
			jdbcTemplate.update(sql,new Object[]{token,userId});
		}
	}
	/**
	 * 查询用户是否有token
	 */
	public String selToken(int userid){
		String sql="select token from t_portal_user where id=? ";
		List<String> token=jdbcTemplate.queryForList(sql,new Object[]{userid},String.class);
		if(token.size()>0 && token!=null){
			return token.get(0);
		}
		return null;
	}
	/**
	 * 支付成功后更改用户支付状态
	 * @param orderNum
	 */
	public void updateIsFinish(String orderNum){
		String update ="UPDATE t_sitepayment_records SET is_finish = -1 where order_num =?";
		jdbcTemplate.update(update,new Object[]{orderNum});
	}
	/**
	 * 
	 *	@Description:用户使用余额支付时更改用户账上余额
	 *  @author songyanbiao
	 *	@Date 2016年6月21日 
	 *	@param userId
	 *	@param userAccount
	 *	@param usedAccount
	 *	@return
	 */
	public int updateUserBalance(String openid,String balance){
		WechatUserInfo wu=null;
		PortalUser user=null;	
		try {
			wu=nutDao.fetch(WechatUserInfo.class, Cnd.where("openid","=",openid));
			user=nutDao.fetch(PortalUser.class, Cnd.where("id","=",wu.getPortal_user_id()));
			UserBalance ub=nutDao.fetch(UserBalance.class, Cnd.where("user_name","=",user.getUserName()));
			ub.setBalance(new BigDecimal(balance));
			 nutDao.update(ub);
			 return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	/**
	 * 
	 *	@Description:给被人充值余额
	 *  @author songyanbiao
	 *	@Date 2016年6月21日 
	 *	@param tel
	 *	@param banlance
	 *	@return
	 */
	public int updatePayBalance(String tel,String banlance){
			UserBalance ub=nutDao.fetch(UserBalance.class, Cnd.where("user_name","=",tel));
			if(ub==null){
				ub=new UserBalance();
				ub.setBalance(new BigDecimal(banlance));
				ub.setUserName(tel);
				nutDao.insert(ub);
				return ub.getId()>0?1:0;
			}else{
				ub.setBalance(BigDecimalUtil.add(new BigDecimal(banlance) ,ub.getBalance()));
				return nutDao.update(ub);
			}
	}
//	@SuppressWarnings("unchecked")
//	public void giveLottery(int userId,int siteId,CloudSite site,SitePaymentRecord spr,SitePriceConfig scf) throws ParseException{
//		
//		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM");
//		SiteCustomerInfo scu=nutDao.fetch(SiteCustomerInfo.class,Cnd.where("site_id","=",siteId).and("portal_user_id","=",userId));
//		Date giveTime=scu.getLottertTime();
//		String giveLotteryTime=sdf.format(giveTime);
//		String nowTime=sdf.format (new Date());
//		long siteTime=site.getIs_probative()*60*1000L;
//		
//		long createTime=scu.getCreateTime().getTime();
//		long updateTime=scu.getUpdateTime().getTime();
//		if(createTime==updateTime){
//			result=this.addUserMessage(userId);
//			if(result==1){
//				scu.setLottertTime(new Date());
//				nutDao.update(scu);
//			}
//		}
//		
//		int result=0;
//		if(site.getIs_probative()!=30){
//			siteTime=siteTime*60;
//		}
//		Map<String, String> map = JSON.parseObject(spr.getParamJson(), Map.class);		
//		//如果赠送彩票时间不是当月时间则查询用户当月消费记录是否已达到要求,符合要求 送给用户彩票,并把时间改为当月时间.反之则不作任何处理
//		if(!giveLotteryTime.equals(nowTime)){
//			/*String value=this.getUserCustomer(scu, scf, map);
//			if(map.get("mealType").equals("1")){//时间比较
//				if(sdf.parse(value).getTime()==0||sdf.parse(value).getTime()-scu.getCreateTime().getTime()<=siteTime){
//					result=this.addUserMessage(userId);
//					if(result==1){
//						scu.setLottertTime(new Date());
//						nutDao.update(scu);
//					}
//				}
//			}else{
//				if(Integer.valueOf(value)==0){
//					result=this.addUserMessage(userId);
//					if(result==1){
//						scu.setLottertTime(new Date());
//						nutDao.update(scu);
//					}
//				}
//			}*/
//			
//			
//			String sql="SELECT SUM(transaction_amount) allMoney FROM t_site_income WHERE portal_user_id=? and site_id=? AND create_time>?";
//			 List<BigDecimal> money=jdbcTemplate.queryForList(sql,new Object[]{userId,siteId,giveLotteryTime+"00:00:00"},BigDecimal.class);
//			if(money.get(0).compareTo(new BigDecimal("30"))>=0){
//				
//			}
//		}
//	}
	/**
	 * 
	 * @Description:发送彩票,校验用户是否符合标准	
	 * @author songyanbiao
	 * @date 2016年9月1日 下午4:24:21
	 * @param
	 * @return
	 */
	public int giveLottery(Map<String, String> map ){
		SimpleDateFormat sdf1= new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SiteCustomerInfo sci=nutDao.fetch(SiteCustomerInfo.class, Cnd.where("siteId","=",Integer.parseInt(map.get("storeId"))).and("portal_user_id","=",Integer.parseInt(map.get("userId"))));
		try {
			long sTmie = sdf.parse("1970-01-01 00:00:00").getTime();
			if(sci==null){
				sci=new SiteCustomerInfo();
				int result=this.addUserMessage(Integer.parseInt(map.get("userId")+""),0);
				if(result>0){
					sci.setCreateTime(new Timestamp(new Date().getTime()));
					sci.setLottertTime(sdf.format(new Date()));
					sci.setExpirationTime(sdf.format(new Date()));
					sci.setPortalUserId(Integer.parseInt(map.get("userId")+""));
					sci.setSiteId(Integer.parseInt(map.get("storeId")+""));
					sci.setTotalFlow("0");
					return nutDao.insert(sci).getId();
				}
			}else{
				Date d=new Date();
				String giveLotteryTime="";
				if(sci.getLottertTime()!=null){
					giveLotteryTime=sdf1.format(sci.getLottertTime());
				}
				String nowTime=sdf1.format (d);
				if(giveLotteryTime.equals("")||!giveLotteryTime.equals(nowTime)){
					int result=this.addUserMessage(Integer.parseInt(map.get("userId")+""),1);
					if(result==1){
						sci.setLottertTime(sdf.format(d));
						return nutDao.update(sci);
					}
					
				}
			}
		} catch (ParseException e) {
			log.error("添加信息出错",e);
			return 0;
		}
		return 2;
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
	public int addUserMessage(int userId,int state){
		String sql=" INSERT INTO t2_site_user_message (user_id,content,state,create_time) VALUES(?,?,?,?)";
		String content="尊敬的用户您好,您累计充值金额已达到30元.现赠送您一张中国福利彩票,请在当月内尽快点击领取!";
		state=0;
		if(state==0){
			content="尊敬的用户您好,由于您是当天首次充值,现赠送您一张中国福利彩票,请在当月内尽快点击领取!";
		}
		try {
			return jdbcTemplate.update(sql, new Object[]{userId,content,3,new Timestamp(new Date().getTime())});
		} catch (Exception e) {
			return 0;
		}
	}
//	/*查询用户是否为第一次缴费*/
//	public String getUserCustomer(SiteCustomerInfo scii,SitePriceConfig scf,Map<String,String> map){
//		String riqi="";
//		int flow=0;
//		if("1".equals(map.get("mealType"))){//用户购买的是时间套餐
//			
//			//根据和当前时间的比较计算到期时间
//			//没缴过费的话
//			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			Date now = new Date();
//			
//			String str2 = sdf.format(scii.getExpirationTime().getTime());
//			riqi = DateUtil.newDateFrist(scf.getPrice_type(), Integer.parseInt(map.get("buyNum")),str2,map.get("priceNum"));	
//			if(map.get("addMealNum")!="0"&&map.get("addMealNum")!=null&&map.get("addMealUnit")!=null){
//				riqi=DateUtil.newDateFrist(Integer.parseInt(map.get("addMealUnit")), Integer.parseInt(map.get("addMealNum")),riqi,"1");
//			}
//		}else{
//			//如果用户有记录但是只有购买时间的记录,这时用户的流量为null
//			if(scii.getTotalFlow()!=null&&!scii.getTotalFlow().equals("null")){
//				flow=Integer.parseInt(scii.getTotalFlow());
//			}
//			if(scf.getPrice_type()==4){//购买的套餐是M
//				flow=flow-Integer.parseInt(map.get("buyNum"))*Integer.parseInt(map.get("priceNum"))*1024;
//			}else{//购买的套餐时G
//				flow=flow-Integer.parseInt(map.get("buyNum"))*Integer.parseInt(map.get("priceNum"))*1024*1024;
//			}
//			//如果用户购买的套餐有赠送流量
//			if(map.get("addMealNum")!=null&&map.get("addMealUnit")!=null){
//				if("4".equals(map.get("addMealUnit"))){
//					flow=flow-Integer.parseInt(map.get("addMealNum"))*1024;
//				}else{
//					flow=flow-Integer.parseInt(map.get("addMealNum"))*1024*1024;
//				}
//			}
//			riqi=String.valueOf(flow);
//		}
//		return riqi;
//	}
}
