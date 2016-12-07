package com.broadeast.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.broadeast.bean.SitePriceConfigAll;
import com.broadeast.entity.CloudSite;
import com.broadeast.entity.CloudSiteRouters;
import com.broadeast.entity.PortalUser;
import com.broadeast.entity.SitePaymentRecord;
import com.broadeast.entity.SitePriceConfig;
import com.broadeast.entity.SiteCustomerInfo;
import com.broadeast.entity.SitePriceConfig;
import com.broadeast.util.BigDecimalUtil;
import com.broadeast.util.DateUtil;

@Service
public class SiteService {
	
	private static Logger logger = Logger.getLogger(SiteService.class);
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="nutDao")
	private Dao nutDao;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 查看MAC 没有返回-1 ，有值返回siteid
	 * @param id
	 * @return
	 */
	public int SiteRouters(String id){
		CloudSiteRouters  siteRouters=nutDao.fetch(CloudSiteRouters.class,Cnd.where("mac","=",id));
		if(siteRouters==null){ //没查到 mac对应的 场所ID  如果有则验证用户
			return -1;
		}
		
		return siteRouters.getSiteId();
	}
	
	/**
	 * 根据siteid 判断场所是否存在 
	 * @param siteid
	 * @return
	 */
	
	public CloudSite getSiteById(int siteId){
		CloudSite site= null;
		try {
			 site=nutDao.fetch(CloudSite.class,Cnd.where("id","=",siteId)); //获得当前场所
		} catch (Exception e) {
			logger.error(this.getClass().getCanonicalName());
		}
		return site;
	} 
	
	/**
	 * @Description  判断是否有试用时间,有则加上没有则不加
	 * @date 2016年5月24日下午4:20:06
	 * @author guoyingjie
	 * @param siteId
	 * @param userId
	 */
	public void addTryTimeToUser(int siteId,int userId){
		try {
			CloudSite site =  getSiteById(siteId);
			int num = site.getIs_probative();
//			if(num!=0){
				SiteCustomerInfo info = new SiteCustomerInfo();
				info.setIsTry(0);
				info.setSiteId(siteId);
				info.setPortalUserId(userId);
				if(num==30){
					info.setExpirationTime(new Date(new Date().getTime()+num*60*1000));
				}else{
					info.setExpirationTime(new Date(new Date().getTime()+num*60*60*1000));
				}
				nutDao.update(info);
//			}
		} catch (Exception e) {
			logger.error(this.getClass().getCanonicalName()+"--addTryTimeToUser");
		}
	}
	 
	/**
	 * 根据场所id和priceInfoId获取场所对应的价格配置信息,该配置必须is_stoped=0
	 * @param siteId
	 * @param priceInfoId
	 * @return
	 */
	public SitePriceConfig getSitePriceInfos(int siteId,int priceInfoId){
		SitePriceConfig priceInfo=nutDao.fetch(SitePriceConfig.class, Cnd.where("site_id","=",siteId)
				.and("id", "=", priceInfoId).and("is_stoped", "=", 0));
		return priceInfo;
	}
	
/**
 * 根据场所id,套餐名称priceNum和priceInfoId获取场所对应的价格配置信息,该配置必须is_stoped=0
 * @param siteId
 * @param priceInfoId
 * @param priceNum
 * @return
 */
	public SitePriceConfig getSitePriceInfo(int siteId,int priceInfoId,String priceNum){
		String sql="select * from t_site_price_config where site_id=? and id=? and is_stoped=? and price_num=?";
		List<Map<String, Object>> list= jdbcTemplate.queryForList(sql, new Object[]{siteId,priceInfoId,priceNum});
		return (SitePriceConfig) list.get(0);
	}
	
	/**保存用户支付的记录信息
	 * @param orderNum
	 * @param map
	 * @return 0失败。1成功
	 */
	public boolean savePaymentinfo(String orderNum,Map<String,String> map,int payType){
		try {
			
			String json=JSON.toJSONString(map);
			String payUser="";
			String tel="";
			SitePaymentRecord paymentRecord =new SitePaymentRecord(); 
			paymentRecord.setOrderNum(orderNum);
			paymentRecord.setUserId(Integer.parseInt(map.get("userId")));
			paymentRecord.setSiteId(Integer.parseInt(map.get("storeId")));
			paymentRecord.setParamJson(json);
			paymentRecord.setTradeNum("");
			paymentRecord.setFailReason("");
			paymentRecord.setPayType(payType);
			paymentRecord.setOutPayUser(tel=map.get("payUser")==null?null:map.get("payUser"));
			paymentRecord.setInputPayUser(payUser=map.get("tel")==null?null:map.get("tel"));
			nutDao.insert(paymentRecord);
			return paymentRecord.getId()>0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	
	/**
	 * 返回场所收费规则
	 * @param siteid场所id,chargeType 规则归属集团（0无归属，1归属电信）
	 * @return
	 */
	public SitePriceConfigAll getSitePriceConfigAll(int siteid,int chargeType){
		SitePriceConfigAll siteall=new SitePriceConfigAll();
		List<SitePriceConfig> list=nutDao.query(SitePriceConfig.class,Cnd.where("site_id","=",siteid).and("is_stoped","=","0").and("charge_type","=",chargeType).asc("price_type"));
		siteall.setList(list);
		return siteall;
	}
	//-----------------------------最新修改-----------------------------------------
	/**
	 * 验证场所是否存在
	 * @param id
	 * @return
	 */
	public CloudSite getSite(String id){
		CloudSiteRouters  siteRouters=nutDao.fetch(CloudSiteRouters.class,Cnd.where("dfid","=",id));
		if(siteRouters==null){ //没查到 mac对应的 场所ID  如果有则验证用户
			return null;
		}
		CloudSite site=nutDao.fetch(CloudSite.class,Cnd.where("id","=",siteRouters.getSiteId())); //获得当前场所
		if(site==null){ //没有查询到场所
			return null;
		}		
		return site;
	}
	/**
	 * @Description 获得场所的bannerUrl
	 * @param site  场所
	 * @return
	 */
	public String getBannerUrl(CloudSite site){
		if(site!=null){
			String urls = site.getBannerUrl();
			if(urls!=null&&!"".equals(urls)){
				String url[] = urls.split(",");
				if(url.length==1){
					return url[0];
				}else{
					return url[new Random().nextInt(url.length)];
				}
			}
		}
		return "school_pic/banner.jpg";
	}
	
	/**
	 * @Description  新版本的获取bannerurl,因为前段是轮班的形式,每个场所最多有三个bannerurl
	 * @date 2016年8月3日上午11:45:16
	 * @author guoyingjie
	 * @param site
	 * @return
	 */
	public String getBannerUrls(CloudSite site){
		String us = "";
		if(site!=null){
			String urls = site.getBannerUrl();
			if(urls!=null&&!"".equals(urls)){
				String url[] = urls.split(",");
				if(url.length==1){
					return url[0];
				}else{
					 for (int i = 0; i < url.length; i++) {
						if (i == url.length - 1) {
							us += url[i];
						} else {
							us += url[i] + ",";
						}
					}
					 return us;
				}
			}
		}
		return "school_pic/combanner1.jpg,school_pic/combanner2.jpg,school_pic/combanner3.jpg";
	}
	 
	/**
	 * 查询改场所下所有非停用的融合套餐规则
	 * @Description 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getChargeNumber(String id){
		String sql="SELECT s.id,s.comboNumber,s.name FROM t_site_price_config s INNER JOIN t_cloud_site_routers c ON s.site_id=c.site_id WHERE c.mac=? AND s.is_stoped=0 AND s.comboNumber IS NOT NULL";
		List<Map<String, Object>> list=jdbcTemplate.queryForList(sql, new Object[]{id});
		return list; 
		
	}
	/**
	 * 查询改场所下所有非停用的非融合套餐规则
	 * @Description 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getCommonNumber(String id){
		String sql="SELECT s.id,s.comboNumber,s.name FROM t_site_price_config s INNER JOIN t_cloud_site_routers c ON s.site_id=c.site_id WHERE c.mac=? AND s.is_stoped=0 AND s.comboNumber IS  NULL";
		List<Map<String, Object>> list=jdbcTemplate.queryForList(sql, new Object[]{id});
		return list;
	}
	

	//查询改场所下所有非停用的融合时间套餐规则
	public List<Map<String, Object>> getChargeNumber(int id){
		String sql="SELECT id,comboNumber,`name` FROM t_site_price_config WHERE site_id=? AND is_stoped=0 AND comboNumber IS NOT NULL";
		List<Map<String, Object>> list=jdbcTemplate.queryForList(sql, new Object[]{id});
		return list; 
		
	}

	//查询改场所下所有非停用的非融合时间套餐规则
	public List<Map<String, Object>> getCommonNumber(int id){
		String sql="SELECT id,comboNumber,`name` FROM t_site_price_config WHERE site_id=? AND is_stoped=0 AND comboNumber IS NULL";

		List<Map<String, Object>> list=jdbcTemplate.queryForList(sql, new Object[]{id});
		return list;
	}
	
	
	/**
	 * 用户场所配置新 (-1 没有此场所信息,0需要充值 ,1正常)
	 * @param siteid
	 * @param prouserid
	 * @return
	 */
	public int UserSiteConfig(int siteid,int prouserid){
		SiteCustomerInfo siteInfo = nutDao.fetch(SiteCustomerInfo.class,
				Cnd.where("portal_user_id", "=", prouserid).and("site_id", "=",siteid));
		if (siteInfo == null) { // 判断用户在此场所有没有消费过(空=没有消费过)判断是否过期
			return -1;
		}
		String flow = siteInfo.getTotalFlow();
		String usedFlow=siteInfo.getUsedFlow();
		if (flow == null || "0".equals(flow)) {
			flow = "0";
		} 
		if(usedFlow == null || "0".equals(usedFlow)){
			usedFlow ="0";
		}
		BigDecimal big= BigDecimalUtil.subtract(new BigDecimal(flow) , new BigDecimal(usedFlow));
		if((flow==null||"0".equals(flow))&&(new Date().compareTo(siteInfo.getExpirationTime()) == 1)){
			return -1;
		}
		if(((flow==null||"0".equals(flow))||big.compareTo(new BigDecimal("0.0000"))!=1)&&(new Date().compareTo(siteInfo.getExpirationTime()) == 1)){
			return -1;
		}
		if(((flow==null||"0".equals(flow))||big.compareTo(new BigDecimal("0.0000"))!=1)&&siteInfo.getExpirationTime()== null){
			return -1;
		}
		
		if( siteInfo.getExpirationTime() == null&&(flow!=null||!"0".equals(flow))){
		  return 1;	
		}
		
		if((flow!=null||!"0".equals(flow))&&(new Date().compareTo(siteInfo.getExpirationTime()) == 1)){
			return 1;
		}
		if((flow==null||"0".equals(flow))&&(new Date().getTime()<siteInfo.getExpirationTime().getTime())){
			return 1;
		}
		return 1;
	}
	
	/**
	 * @Description  判断用户是否有流量(-1 没有此场所信息,0需要充值 ,1正常)
	 * @date 2016年6月1日下午3:13:58
	 * @author guoyingjie
	 * @param siteid
	 * @param prouserid
	 */
	public int userHaveFlow(int siteid,int prouserid){
		SiteCustomerInfo siteInfo = nutDao.fetch(SiteCustomerInfo.class,
				Cnd.where("portal_user_id", "=", prouserid).and("site_id", "=",siteid));
		String flow = "0";
		if(siteInfo!=null){
			flow = siteInfo.getTotalFlow();
		}
		if ((siteInfo == null || siteInfo.getExpirationTime() == null)&&(flow!=null||!"0".equals(flow))) {  // 判断用户在此场所有没有消费过(空=没有消费过)判断是否过期
			return -1;
		}
		if(flow==null||"0".equals(flow)){
			return 0;//没有费用需要充值
		}else{
			return 1;
		}
	}
	
	
	/**
	 * 根据ProUserId查找场所客户账户
	 * @param prouserid
	 * @return
	 */
	public SiteCustomerInfo getExpirationTimeByProuserid(int prouserid,int siteId){
		SiteCustomerInfo siteInfo=null;
		try {
		    siteInfo=nutDao.fetch(SiteCustomerInfo.class,Cnd.where("portal_user_id","=",prouserid).and("site_id","=",siteId));
		} catch (Exception e) {
			logger.error("根据ProUserId查找场所客户账户失败");
		}
		return siteInfo;
	}
	/**
	 * @Description  判断支付宝是否交易成功
	 * @date 2016年5月31日下午3:44:37
	 * @author guoyingjie
	 * @param ordrNum
	 * @param tradeNum
	 * @param siteId
	 * @param UserId
	 * @return
	 */
	public boolean getSitePaymentRecordByTradeNum(String ordrNum,String tradeNum,int siteId,int UserId){
		SitePaymentRecord spr=nutDao.fetch(SitePaymentRecord.class, Cnd.where("tradeNum", "=", tradeNum)
				.and("orderNum", "=", ordrNum)
				.and("siteId", "=", siteId).and("userId", "=", UserId));
		if(spr!=null&&(spr.getIsFinish()==1||spr.getIsFinish()==-1)){
			 return true;
		}
		return false;
	}
	
	
	/**
	 * 返回场所有收费规则
	 * @param siteid,chargeType 规则归属集团（0无归属，1归属电信）
	 * @return
	 */
	public List<SitePriceConfig> SitePriceConfigAll(int siteid,int chargeType){
		return  nutDao.query(SitePriceConfig.class,Cnd.where("site_id","=",siteid).and("is_stoped","=","0").and("charge_type","=",chargeType).asc("price_type"));
	}
	public SitePriceConfig getSitePriceConfig(int siteid,String chargeType){
		
		return  nutDao.fetch(SitePriceConfig.class,Cnd.where("site_id","=",siteid).and("is_stoped","=","0").and("id","=",chargeType).asc("price_type"));
	}
	public SitePriceConfig getSitePriceConfigCharge(int siteid,String chargeType){
		SitePriceConfig sitePriceConfig1=nutDao.fetch(SitePriceConfig.class,Cnd.where("site_id","=",siteid).and("id","=",chargeType));
			
		return  nutDao.fetch(SitePriceConfig.class,Cnd.where("site_id","=",siteid).and("is_stoped","=","0").and("name","=",sitePriceConfig1.getName()).and("comboNumber","=",null).asc("price_type"));
	}
	//根据场所id获取场所名称
	public String getSiteName(int siteId){
		
		return nutDao.fetch(CloudSite.class,Cnd.where("id","=",siteId)).getSite_name();
	}
	
	/**
	 * 用户缴费时计算用户的到期套餐
	 * @param scii
	 * @param scf
	 * @param map
	 * @return
	 */
	public String getUserCustomer(SiteCustomerInfo scii,SitePriceConfig scf,Map<String ,String>map){
		String riqi="";
		int flow=0;
		if("1".equals(map.get("mealType"))){//用户购买的是时间套餐
			
			//根据和当前时间的比较计算到期时间
			//没缴过费的话
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			
			String str1 = sdf.format(now.getTime());//当前时间
			String str2 = "";
			if(scii==null){
				str2=sdf.format(now.getTime());
			}else{
				//如果用户有记录但是只有购买流量的记录,这时用户的过期时间为null
				if(scii.getExpirationTime()==null||scii.getExpirationTime().equals("null")||"".equals(scii.getExpirationTime())){
					str2=sdf.format(now.getTime());
				}else{
					str2 = sdf.format(scii.getExpirationTime().getTime());
				}
			}
			int cmp =DateUtil.compareDate(str1,str2);
			if(cmp==1){
				//到期时间小于等于当前时间时，在当前时间的基础上计算新的到期时间
				riqi = DateUtil.newDatePluss(scf.getPrice_type(), Integer.parseInt(map.get("buyNum")),str1,map.get("priceNum"));	
			}else{
				//到期时间大于当前时间时，在到期时间基础上计算新的到期时间
				riqi = DateUtil.newDatePluss(scf.getPrice_type(), Integer.parseInt(map.get("buyNum")),str2,map.get("priceNum"));	
			}
			if(map.get("addMealNum")!="0"&&map.get("addMealNum")!=null&&map.get("addMealUnit")!=null){
				riqi=DateUtil.newDatePluss(Integer.parseInt(map.get("addMealUnit")), Integer.parseInt(map.get("addMealNum")),riqi,"1");
				
			}
		}else{
			
			if(scii!=null){
				//如果用户有记录但是只有购买时间的记录,这时用户的流量为null
				if(scii.getTotalFlow()!=null&&!scii.getTotalFlow().equals("null")){
					flow=Integer.parseInt(scii.getTotalFlow());
				}
				if(scf.getPrice_type()==4){//购买的套餐是M
					flow=flow+Integer.parseInt(map.get("buyNum"))*Integer.parseInt(map.get("priceNum"))*1024;
				}else{//购买的套餐时G
					flow=flow+Integer.parseInt(map.get("buyNum"))*Integer.parseInt(map.get("priceNum"))*1024*1024;
				}
				//如果用户购买的套餐有赠送流量
				if(map.get("addMealNum")!=null&&map.get("addMealUnit")!=null){
					if("4".equals(map.get("addMealUnit"))){
						flow=flow+Integer.parseInt(map.get("addMealNum"))*1024;
					}else{
						flow=flow+Integer.parseInt(map.get("addMealNum"))*1024*1024;
					}
				}
				
			}else{
				if(scf.getPrice_type()==4){//购买的套餐是M
					flow=flow+Integer.parseInt(map.get("buyNum"))*Integer.parseInt(map.get("priceNum"))*1024;
				}else{//购买的套餐时G
					flow=flow+Integer.parseInt(map.get("buyNum"))*Integer.parseInt(map.get("priceNum"))*1024*1024;
				}
				//如果用户购买的套餐有赠送流量
				if(map.get("addMealNum")!=null&&map.get("addMealUnit")!=null){
					if("4".equals(map.get("addMealUnit"))){
						flow=flow+Integer.parseInt(map.get("addMealNum"))*1024;
					}else{
						flow=flow+Integer.parseInt(map.get("addMealNum"))*1024*1024;
					}
				}
			}
			riqi=String.valueOf(flow);
		}
		return riqi;
	}
	
	/**
	 * @Description  获取wifidog认证url
	 * @date 2016年6月1日下午12:08:47
	 * @author guoyingjie
	 * @param session
	 * @param request
	 * @param site
	 * @return
	 */
	public String getAuthUrl(HttpSession session,HttpServletRequest request,CloudSite site){
		String id=(String)session.getAttribute("siteMac");
		String mac=(String)session.getAttribute("clientMac");
		String ip=(String)session.getAttribute("clientIp");
		String url=(String)session.getAttribute("fromUrl");
		String gw_address=(String)session.getAttribute("gw_address");
		String terminalDevice = request.getHeader("User-Agent");
		String gw_port=(String)session.getAttribute("gw_port");
		PortalUser proUser=(PortalUser) session.getAttribute("proUser"); 
		String authUrl=null;
		String oldAuthUrl=null;
		String token=(String)session.getAttribute("token")+proUser.getId();
		if(id==null||mac==null||ip==null||url==null||terminalDevice==null){
		}else{
				authUrl="http://"+gw_address+":"+gw_port+"/wifidog/auth?token="+token+"&url="+"http://www.baidu.com"+"&timestamp="+DateUtil.getDateNoP();
		}
		return authUrl;
	}
	/**
	 * @Description  获取老版本的认证url;
	 * @date 2016年6月1日下午12:09:59
	 * @author guoyingjie
	 * @param session
	 * @param request
	 * @return
	 */
	public String getAuthUrlOld(HttpSession session,HttpServletRequest request){
		String id=(String)session.getAttribute("siteMac");
		String mac=(String)session.getAttribute("clientMac");
		String ip=(String)session.getAttribute("clientIp");
		String url=(String)session.getAttribute("fromUrl");
		String terminalDevice = request.getHeader("User-Agent");
		
		PortalUser proUser=(PortalUser) session.getAttribute("proUser");//添加用户到session
		CloudSite site=(CloudSite) session.getAttribute("site");//添加场所所有收费规则session
		String authUrl=null;
		if(id==null||mac==null||ip==null||url==null||terminalDevice==null){
		}else{
			authUrl="http://1.2.3.4/router/auth?requestTime="+DateUtil.getDateNoP()+"&mac="+mac
				+ "&requestId="+id+"&lanip="+ip
				+ "&authMethod=16&authId="+proUser.getUserName()+"&sex=0&faceUrl=&nickname=&fromurl="+url+"&storeId="+site.getId()+"&terminalDevice="+terminalDevice;
		}
		userService.keepSomeOnline(proUser.getUserName(),id,mac,site.getId());
		return authUrl;
	}
	
}
