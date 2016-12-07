package com.broadeast.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.nutz.lang.Lang;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xml.sax.SAXException;

import com.broadeast.bean.SitePriceConfigAll;
import com.broadeast.bean.WinxinSingleton;
import com.broadeast.entity.CloudSite;
import com.broadeast.entity.CloudSitePortalEntity;
import com.broadeast.entity.CloudSiteRouters;
import com.broadeast.entity.PortalUser;
import com.broadeast.entity.SiteCustomerInfo;
import com.broadeast.entity.SitePriceConfig;
import com.broadeast.entity.UserBalance;
import com.broadeast.entity.UserDrawLog;
import com.broadeast.entity.UserDrawn;
import com.broadeast.entity.UserRecommend;
import com.broadeast.entity.UserSign;
import com.broadeast.entity.WechatUserInfo;
import com.broadeast.util.BASE64;
import com.broadeast.util.BigDecimalUtil;
import com.broadeast.util.ExecuteResult;
import com.broadeast.util.InitContext;
import com.broadeast.util.MD5;
import com.broadeast.util.PropertiesParam;
import com.broadeast.util.SHA256;
import com.broadeast.util.WanipUtil;
import com.broadeast.weixin.comment.Configure;
import com.broadeast.weixin.comment.HttpService;
import com.broadeast.weixin.comment.JsonUtil;
import com.broadeast.weixin.comment.RandomStringGenerator;
import com.broadeast.weixin.comment.UnifiedOrderReqData;
import com.broadeast.weixin.comment.WxPayApi;
import com.util.thirdpay.Pay;

@Service
@SuppressWarnings("all")
public class WeChatService {

	Logger logger = Logger.getLogger(WeChatService.class);
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Resource(name = "nutDao")
	private Dao nutDao;
	
	@Resource(name = "templJdbcTemplate")
	private JdbcTemplate templJdbcTemplate;

	
	@Resource(name="userService")
	private UserService userService;
	
	@Autowired
	private SchoolPaymentService schoolPaymentService;
	
	@Resource(name="myselfPayService")
	private MyselfPayService myselfPayService;
	
	@Resource(name="weChatOtherService")
	private WeChatOtherService weChatOtherService;
	
	@Autowired
	private SiteService siteService;
	/**
	 * @Description  检测关注公众号用户是否跟wifi用户绑定
	 * @date 2016年6月14日下午4:06:02
	 * @author guoyingjie
	 * @param openid
	 */
	public WechatUserInfo whetherBindUser(String openid){
		WechatUserInfo userinfo = null; 
		try {
			userinfo = nutDao.fetch(WechatUserInfo.class,Cnd.where("openid","=",openid));
		} catch (Exception e) {
			logger.error(this.getClass().getCanonicalName()+"==whetherBindUser",e);
		}
		return userinfo;
	}
	/**
	 * 
	 * @Description:检测微信和手机号绑定	
	 * @author songyanbiao
	 * @date 2016年10月14日 上午11:49:09
	 * @param
	 * @return
	 */
	public WechatUserInfo hasBindUser(String openid,int portalId){
		WechatUserInfo userinfo = null; 
		try {
			userinfo = nutDao.fetch(WechatUserInfo.class,Cnd.where("openid","=",openid).and("portal_user_id","=",portalId));
		} catch (Exception e) {
			logger.error(this.getClass().getCanonicalName()+"==whetherBindUser",e);
		}
		return userinfo;
	}
	/**
	 * @Description 获得用户在几个场所
	 * @date 2016年6月14日下午4:59:38
	 * @author guoyingjie
	 * @param portalId
	 */
	public SiteCustomerInfo getSiteCoustomInfo(int portalId,int siteId){
		SiteCustomerInfo infos = null;
		try {
			infos = nutDao.fetch(SiteCustomerInfo.class,Cnd.where("portal_user_id","=",portalId).and("site_id","=",siteId));
		} catch (Exception e) {
			logger.error(this.getClass().getCanonicalName()+"==获得用户场所失败",e);
		}
		return infos;
	}
	/**
	 * @Description  当用户注册了但是没有缴费的时候给默认值填充页面
	 * @date 2016年6月14日下午6:13:13
	 * @author guoyingjie
	 */
	public void setSession(HttpSession session){
		Map<String,Object> map1 = new HashMap<String,Object>();
		Map<String,Object> map2 = new HashMap<String,Object>();
		map1.put("time", "0小时");
		map1.put("alltime", "0");
		map1.put("flow", "0");
		map1.put("flowstr", "0M");
		map2.put("time", "0小时");
		map2.put("alltime", "0");
		map2.put("flow", "0");
		map2.put("flowstr", "0M");
		session.setAttribute("allTimeAndFlow", map1);
		session.setAttribute("SyTimeAndFlow", map2);
		session.setAttribute("bili", userService.getTyleBili(map1, map2));
	}
	/**
	 * @Description  当用户注册了但是没有缴费的时候给默认值填充页面
	 * @date 2016年6月14日下午6:13:13
	 * @author guoyingjie
	 */
	public Map<String,Object> setMap(Map<String,Object> map){
		Map<String,Object> map1 = new HashMap<String,Object>();
		Map<String,Object> map2 = new HashMap<String,Object>();
		map1.put("time", "0小时");
		map1.put("alltime", "0");
		map1.put("flow", "0");
		map1.put("flowstr", "0M");
		map2.put("time", "0小时");
		map2.put("alltime", "0");
		map2.put("flow", "0");
		map2.put("flowstr", "0M");
		map.put("allTimeAndFlow", map2.get("time"));
		map.put("SyTimeAndFlow", map2.get("flowstr"));
		map.put("bili", userService.getTyleBili(map1, map2));
		return map;
	}
	/**
	 * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
	 * 
	 * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
	 * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
	 * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,192.168.1.100 
	 * 用户真实IP为： 192.168.1.110 
	 * @param request
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String wanip = request.getHeader("x-forwarded-for");  
        if (wanip == null || wanip.length() == 0 || wanip.equalsIgnoreCase("unknown")) {  
        	wanip = request.getHeader("Proxy-Client-IP");  
        }  
        if (wanip == null || wanip.length() == 0 || wanip.equalsIgnoreCase("unknown")) {  
        	wanip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        
        if (wanip == null || wanip.length() == 0 || wanip.equalsIgnoreCase("unknown")) {  
        	wanip = request.getHeader("X-Real-IP");  
        }  
        
        if (wanip == null || wanip.length() == 0 || wanip.equalsIgnoreCase("unknown")) {  
        	wanip = request.getRemoteAddr();  
        }	
        return wanip;
	}
	/**
	 * @Description 通过外网ip获得场所信息
	 * @date 2016年6月15日下午1:12:32
	 * @author guoyingjie
	 * @param ip
	 */
	public CloudSite getSiteIdByIp(HttpServletRequest request){
		String ip = this.getIpAddress(request);
		//根据ip获得路由的设备,此时有wifidog与小辣椒与ikuai,第一步去判断在路由新消息表里查找路由的信息,如果有说明是小辣椒或者是ikuai.如果查不到则是wifidog
		CloudSiteRouters router = nutDao.fetch(CloudSiteRouters.class,Cnd.where("ip","=",ip).limit(0,1));
		if(router==null){
			//此时路由设备不是小辣椒与ikuai,在路由注册表中根据wanip查找设备mac
			String sql = "SELECT mac FROM v1_store_router where wanip = ? GROUP BY wanip";
			List<Map<String,Object>> mac = templJdbcTemplate.queryForList(sql,new Object[]{ip});
			if(mac!=null&&mac.size()>0&&mac.get(0).get("mac")!=null&&!"".equals(mac.get(0).get("mac"))){
				try {
					//如果当前的路由设备存在.通过mac找到用户所在的归属场所.
					CloudSiteRouters rout = nutDao.fetch(CloudSiteRouters.class,Cnd.where("mac","=",mac.get(0)).limit(0,1));
					CloudSite site = nutDao.fetch(CloudSite.class,Cnd.where("id","=",rout.getSiteId()));
					return site;
				} catch (Exception e) {
					return null;
				}
			}else{
				return null;
			}
		}else{
			//此时的情况为小辣椒与ikuai
		    CloudSite site = nutDao.fetch(CloudSite.class,Cnd.where("id","=",router.getSiteId()));
		    return site;
		}
	}
	
	/**
	 * @Description  在公众号中检测到用户没有注册,直接注册用户
	 * 
	 * 这个需要讨论.如果检测到当前的用户不存在话是提示去wifi计费系统还是直接注册一个用户
	 * 
	 * 目前是直接把这个账号注册了一个
	 * 
	 * @date 2016年6月15日上午9:43:53
	 * @author guoyingjie
	 * @param userName
	 * @return
	 */
	public int registerPortalUser(String userName,String password){
		try {
			PortalUser u = new PortalUser();
			u.setUserName(userName);
			u.setPassWord(SHA256.getUserPassword(userName, MD5.encode(password).toLowerCase()));
			u.setSex(1);
			nutDao.insert(u);
		} catch (Exception e) {
			logger.error(this.getClass().getCanonicalName()+"==注册portal用户失败",e);
			return 0;
		}
		return 1;
	}
	/**
	 * @Description  保存绑定用户信息失败
	 * @date 2016年6月15日上午10:08:37
	 * @author guoyingjie
	 * @param userId
	 * @param map
	 */
	public int insertWechatUserInfo(int userId,String openid,Map<String, Object> map,HttpSession session){
		try {
			WechatUserInfo info = new WechatUserInfo();
			info.setPortal_user_id(userId);
			info.setOpenid(openid);
			info.setNickname(map.get("nickname")==null?"":BASE64.encryptBASE64(map.get("nickname").toString()));
			info.setSex(map.get("sex")==null?0:(int)map.get("sex"));
			info.setProvince(map.get("province")==null?"":map.get("province").toString());
			info.setCity(map.get("city")==null?"":map.get("city").toString());
			info.setCountry(map.get("country")==null?"":map.get("country").toString());
			info.setHeadimgurl(map.get("headimgurl")==null?"":map.get("headimgurl").toString());
			info.setPrivilege(map.get("privilege")==null?"":map.get("privilege").toString());
			info.setUnionid(map.get("unionid")==null?"":map.get("unionid").toString());
			nutDao.insert(info);
			session.setAttribute("userinfo", info);
			return 1;
		} catch (Exception e) {
			logger.error("保存绑定用户信息失败",e);
			return 0;
		}
	}
	/**
	 * @Description 更换绑定微信账号
	 * @date 2016年6月16日上午11:30:46
	 * @author guoyingjie
	 * @param info
	 * @param userId
	 * @return
	 */
	public int updateWechatUserInfo(String sesisonName,int userId){
		try {
			PortalUser sessionUser = userService.getPortalUserByTel(sesisonName);
			WechatUserInfo info = this.checkOpenid(sessionUser.getId());
			info.setPortal_user_id(userId);
			nutDao.update(info);
			return 1;
		} catch (Exception e) {
			logger.error(this.getClass().getCanonicalName(),e);
			return 0;
		}
	}
	
	/**
	 * @Description  检查openid是否被多个用户绑定 
	 * @date 2016年6月15日下午3:17:33
	 * @author guoyingjie
	 * @param username
	 * @param openid
	 */
	public WechatUserInfo checkOpenid(int userId){
		WechatUserInfo info = null;
		try {
			info = nutDao.fetch(WechatUserInfo.class,Cnd.where("portal_user_id","=",userId));
		} catch (Exception e) {
			logger.error(this.getClass().getCanonicalName()+"==检查openid是否被多个用户绑定失败",e);
		}
		return info;
	}
	/**
	 * @Description 取消绑定微信号  
	 * @date 2016年6月15日下午8:02:32
	 * @author guoyingjie
	 * @param info
	 */
	public int deleteWechatUser(WechatUserInfo info){
		try {
			nutDao.delete(info);
			return 1;
		} catch (Exception e) {
			logger.error(this.getClass().getCanonicalName(),e);
			return 0;
		}
		
	}
	
	/**
	 * @Description  根据用户名查询用户的余额
	 * @date 2016年6月15日下午5:47:23
	 * @author guoyingjie
	 * @param userName
	 */
	public UserBalance getUserBalanceByName(String userName){
		UserBalance ub = null;
		try {
			ub = nutDao.fetch(UserBalance.class,Cnd.where("user_name","=",userName));
		} catch (Exception e) {
		   logger.error(this.getClass().getCanonicalName(),e);
		}
		return ub;
	}
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
		List<Map<String, Object>> ls=null;
		CloudSite site=null;
		try {
//			String sql="SELECT * FROM t_cloud_site WHERE id=?";
//			 ls=jdbcTemplate.queryForList(sql,new Object[]{siteId});
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
	public UserBalance getUserMoney(String userName){
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
	 *	@Description:提取用户绑定openid以及自动注册
	 *  @author songyanbiao
	 *	@Date 2016年6月25日 
	 *	@param openid
	 *	@param accessToken
	 *	@param password
	 *	@param userName
	 *	@param code
	 *	@param session
	 *	@return
	 *	@throws Exception
	 */
	public String bindUserOpenid(String jumpUrl, String openid, String accessToken, String password, String userName,String code,HttpSession session) throws Exception{
		ExecuteResult rs = new ExecuteResult(); 
		 if(openid==null||"".equals(openid)||accessToken==null||"".equals(accessToken)){
			rs.setCode(201);
			rs.setMsg("授权凭证 已过期,请返回重试");
			return rs.toJsonString();
		}
		
		String sessionCode = (String)session.getAttribute(userName);
		if(!code.equals(sessionCode)){
			rs.setCode(201);
			rs.setMsg("验证码错误");
			return rs.toJsonString();
		}
		//5分钟
		Long oldTime=(Long)session.getAttribute("randCodeTime");
		oldTime=oldTime==null?0:oldTime;
		long newTime=new Date().getTime();
		if((newTime-5*60*1000)<=oldTime){//通过
			session.removeAttribute(userName);
			session.removeAttribute("randCodeTime");
		}else{
			rs.setCode(201);
			rs.setMsg("验证码失效，请重新获取");
			return rs.toJsonString();
		} 	
		
//		String checkurl = "https://api.weixin.qq.com/sns/auth?access_token="+accessToken+"&openid="+openid;
//		String result = HttpService.doGet(checkurl);
//		Map<String, Object> checkmap = JsonUtil.fromJson(result, HashMap.class);
//		if(!checkmap.get("errmsg").toString().equals("ok")&&!checkmap.isEmpty()){
//			rs.setCode(201);
//			rs.setMsg("授权凭证 已过期,请返回重试");
//			return rs.toJsonString();
//		}
		
		PortalUser user = userService.getPortalUserByTel(userName);
		logger.error("user===="+user+"======user");

		if(user==null){
			int state = registerPortalUser(userName,password);
			if(state!=1){
				rs.setCode(201);
				rs.setMsg("绑定用户失败,请重新绑定");
				return rs.toJsonString();
			}
			user = userService.getPortalUserByTel(userName);
		} 
//		PortalUser proUser = userService.getUserPro(userName, MD5.encode(password).toLowerCase());
//		if(proUser==null){
//			rs.setCode(201);
//			rs.setMsg("用户名密码错误");
//			return rs.toJsonString();
//		}
//		String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid="+openid+"&lang=zh_CN";
//		String res = HttpService.doGet(url);
//		Map<String, Object> resultMap = JsonUtil.fromJson(res, HashMap.class);
		Map<String, Object> resultMap=new HashMap<>();
		WechatUserInfo winfo = checkOpenid(user.getId());
		if(winfo!=null){
			rs.setCode(201);
			rs.setMsg("您的微信已经绑定到其它用户,请更换账户绑定");
			return rs.toJsonString();
		}
		int isok = insertWechatUserInfo(user.getId(), openid,resultMap,session);
		logger.error("isok===="+isok+"======isok");
		if(1!=isok){
			rs.setCode(201);
			rs.setMsg("保存绑定用户失败");
			return rs.toJsonString();
		}
		session.setAttribute("proUser", user);
		session.setAttribute("tel", user.getUserName());
		rs.setCode(200);
		rs.setMsg(jumpUrl);
		return rs.toJsonString();
	}
	/**
	 * 
	 *	@Description:获取用户的归属场所
	 *  @author songyanbiao
	 *	@Date 2016年6月28日 
	 *	@param ls
	 *	@return
	 */
	public List<CloudSite> getSite(List<Map<String, Object>> ls){
		List<CloudSite> siteLs=new ArrayList<CloudSite>();
		CloudSite site=null;
		for (int i = 0; i < ls.size(); i++) {
			site=getSiteName((int)ls.get(i).get("site_id"));
			if(site!=null){
				siteLs.add(site);
			}
		}
		return siteLs;
	}
	/**
	 * 
	 *	@Description:微信用户信息发生改变时，修改用户的微信信息
	 *  @author songyanbiao
	 *	@Date 2016年7月6日 
	 *	@param openid
	 *	@param accessToken
	 *	@param userName
	 *	@param code
	 *	@throws Exception
	 */
	public void updateUserWxMessage(WechatUserInfo info,String accessToken) throws Exception{
		String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid="+info.getOpenid()+"&lang=zh_CN";
		String res = HttpService.doGet(url);
		Map<String, Object> map = JsonUtil.fromJson(res, HashMap.class);
		try {
			if(!(map.get("nickname")==null||map.get("headimgurl")==null||BASE64.encryptBASE64(map.get("nickname").toString()).equals(info.getNickname())
				||map.get("headimgurl").toString().equals(info.getHeadimgurl()))){
				info.setNickname(map.get("nickname")==null?"":BASE64.encryptBASE64(map.get("nickname").toString()));
				info.setSex(map.get("sex")==null?0:(int)map.get("sex"));
				info.setProvince(map.get("province")==null?"":map.get("province").toString());
				info.setCity(map.get("city")==null?"":map.get("city").toString());
				info.setCountry(map.get("country")==null?"":map.get("country").toString());
				info.setHeadimgurl(map.get("headimgurl")==null?"":map.get("headimgurl").toString());
				info.setPrivilege(map.get("privilege")==null?"":map.get("privilege").toString());
				info.setUnionid(map.get("unionid")==null?"":map.get("unionid").toString());
				nutDao.update(info);
			}
		} catch (Exception e) {
			logger.error("修改微信用户信息时出错======updateUserWxMessage",e);
		}
	}
	/**
	 * 
	 * @Description:获取外网ip	
	 * @author songyanbiao
	 * @date 2016年9月20日 下午3:51:13
	 * @param
	 * @return
	 */
	public List<Map<String, Object>> getRouterType(String wanIp){
		String sql="SELECT routers.router_type,site.site_name FROM t_cloud_site_routers routers LEFT JOIN t_cloud_site site ON routers.site_id=site.id WHERE routers.ip=? LIMIT 0,1";
		List<Map<String, Object>> types=new ArrayList<Map<String, Object>>();
		try {
			types=jdbcTemplate.queryForList(sql, new Object[]{wanIp});
		} catch (Exception e) {
			logger.error("根据外网ip获取场所设备类型出错",e);
		}
		return types;
	}
	/**
	 * 
	 * @Description:获取设备类型
	 * @author songyanbiao
	 * @date 2016年9月21日 下午2:04:42
	 * @param
	 * @return
	 */
	public String getRouterTypeById(int siteId){
		String sql="SELECT router_type FROM t_cloud_site_routers WHERE site_id=? LIMIT 0,1";
		List<Map<String, Object>> types=new ArrayList<Map<String, Object>>();
		try {
			types=jdbcTemplate.queryForList(sql, new Object[]{siteId});
			if(types.size()!=0){
				return types.get(0).get("router_type")+"";
			}
		} catch (Exception e) {
			logger.error("根据场所id获取场所设备类型出错",e);
		}
		return "";
	}
	 
	/**
	 * @Description 获得子账号列表
	 * @date 2016年10月21日下午3:22:40
	 * @author guoyingjie
	 * @param username
	 */
	public String getUserByUsername(String username){
		PortalUser user = nutDao.fetch(PortalUser.class,Cnd.where("user_name", "=", username));
		String sonname = user.getSonName();
		if(sonname!=null&&!"".equals(sonname)){
			return sonname;
		}
		return null;
	}
	/**
	 * 
	 * @Description:查看用户推荐码	
	 * @author songyanbiao
	 * @date 2016年10月14日 下午2:30:17
	 * @param
	 * @return
	 */
	public String checkRecommend(int portal_user_id){
		List<Map<String, Object>> result=new ArrayList<>();
		try {
			String sql="SELECT recommend FROM t6_user_recommend WHERE portal_user_id=?";
			result=jdbcTemplate.queryForList(sql, new Object[]{portal_user_id});
			
		} catch (Exception e) {
			logger.error("查询用户是否已经生成推荐码出错",e);
		} 
		return result.size()==0?null:result.get(0).get("recommend")+"";
	}
	/**
	 * 
	 * @Description:校推荐码唯一性	
	 * @author songyanbiao
	 * @date 2016年10月14日 下午3:00:28
	 * @param
	 * @return
	 */
	public String checkRecommend(String code){
		List<Map<String, Object>> result=new ArrayList<>();
		try {
			String sql="SELECT recommend FROM t6_user_recommend WHERE recommend=?";
			result=jdbcTemplate.queryForList(sql, new Object[]{code});
		} catch (Exception e) {
			logger.error("查询用户是否已经生成推荐码出错",e);
		}
		return result.size()==0?null:result.get(0).get("recommend")+"";
	}
	/**
	 * 
	 * @Description:插入推荐码	
	 * @author songyanbiao
	 * @date 2016年10月14日 下午3:00:42
	 * @param
	 * @return
	 */
	public boolean insertRecommend(String code,int userId){
		UserRecommend ur= new UserRecommend();
		ur.setRecommend(code);
		ur.setUserId(userId);
		return nutDao.insert(ur).getId()>0?true:false;
	}
	/**
	 * 
	 * @Description:获取ticket值,有token值时直接获取	
	 * @author songyanbiao
	 * @date 2016年10月20日 下午4:13:16
	 * @param
	 * @return
	 */
	public String getsignByToken(String APPID,String APPSECRET) throws Exception{
		String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+APPSECRET;
		String res = HttpService.doGet(url);
		Map<String, Object> resultMap = JsonUtil.fromJson(res, HashMap.class);
		String token=resultMap.get("access_token")+"";
		String urls="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+token+"&type=jsapi";
		return resultMap.get("ticket")+"";
	}
	/**
	 * 
	 * @Description:获取ticket值,无token值时先获取token值然后获取	ticket
	 * @author songyanbiao
	 * @date 2016年10月20日 下午4:13:57
	 * @param
	 * @return
	 */
	public String getsign(String token,String APPID,String APPSECRET) throws Exception{
		String url="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+token+"&type=jsapi";
		String res = HttpService.doGet(url);
		Map<String, Object> resultMap = JsonUtil.fromJson(res, HashMap.class);
		if(!(resultMap.get("errmsg")+"").equals("ok")){
			String urls="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+APPSECRET;
			String result = HttpService.doGet(urls);
			Map<String, Object> map = JsonUtil.fromJson(result, HashMap.class);
			token=map.get("access_token")+"";
			res=HttpService.doGet(url);
			resultMap = JsonUtil.fromJson(res, HashMap.class);
			return resultMap.get("ticket")+"";
		}else{
			return resultMap.get("ticket")+"";
		}
	}
	/**
	 * 
	 * @Description:获取ticket值,无token值时先获取token值然后获取	ticket
	 * @author songyanbiao
	 * @date 2016年10月24日 上午10:16:20
	 * @param
	 * @return
	 */
	public String getTicket(String APPID,String APPSECRET) throws Exception{
		String tik="";
		WinxinSingleton ws= WinxinSingleton.getInstance();
		Map<String, String> map = ws.getMap();
		String tokenTime = map.get("tokenTime");
		String ticketTime=map.get("ticketTime");
		String accessToken = map.get("access_token");
		String ticket=map.get("ticket");
		Long nowDate = new Date().getTime();
		//这里设置过期时间 7200*1000就好了
		if (accessToken != null && ticketTime != null &&ticket!=null&& nowDate - Long.parseLong(ticketTime) < 7200 * 1000) {
			tik=ticket;
		} else if(accessToken != null&&tokenTime!=null&&nowDate - Long.parseLong(tokenTime) < 7200 * 1000 ){
			String urls="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi";
			String res = HttpService.doGet(urls);
			Map<String, Object> map1 = JsonUtil.fromJson(res, HashMap.class);
			if(!(map1.get("errmsg")+"").equals("ok")){
				map.put("ticket", map1.get("ticket")+"");
				map.put("ticketTime", new Date().getTime()+"");
				tik=map1.get("ticket")+"";
			}else{
				urls="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+APPSECRET;
				res = HttpService.doGet(urls);
				Map<String, Object> resultMap = JsonUtil.fromJson(res, HashMap.class);
				map.put("access_token", resultMap.get("access_token")+"");
				map.put("tokenTime", new Date().getTime()+"");
				String token=resultMap.get("access_token")+"";
				urls="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+token+"&type=jsapi";
				res = HttpService.doGet(urls);
				resultMap = JsonUtil.fromJson(res, HashMap.class);
				map.put("ticket", resultMap.get("ticket")+"");
				map.put("ticketTime", new Date().getTime()+"");
				tik= resultMap.get("ticket")+"";
			}	
		}else {
			String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+APPSECRET;
			String res = HttpService.doGet(url);
			Map<String, Object> resultMap = JsonUtil.fromJson(res, HashMap.class);
			map.put("access_token", resultMap.get("access_token")+"");
			map.put("tokenTime", new Date().getTime()+"");
			String token=resultMap.get("access_token")+"";
			String urls="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+token+"&type=jsapi";
			res = HttpService.doGet(urls);
			resultMap = JsonUtil.fromJson(res, HashMap.class);
			map.put("ticket", resultMap.get("ticket")+"");
			map.put("ticketTime", new Date().getTime()+"");
			tik= resultMap.get("ticket")+"";
		}
		return tik;
	}
	/**
	 * 
	 * @Description:用户签到	
	 * @author songyanbiao
	 * @date 2016年10月26日 下午2:53:57
	 * @param
	 * @return
	 */
	public Map doSign(PortalUser proUser,CloudSite site){
		SimpleDateFormat sdf= new SimpleDateFormat("MM");
		SimpleDateFormat sdf1= new SimpleDateFormat("dd");
		Map map = new HashMap<>();
		UserSign us=null;
		try {
			Date d= new Date();
			us=nutDao.fetch(UserSign.class, Cnd.where("portal_user_id","=",proUser.getId()));
			if(us==null){
				us= new UserSign();
				us.setSignNum(1);
				us.setUserId(proUser.getId());
				us.setCreateTime(d);
				us.setSignTime(sdf1.format(d)+",");
				us.setSignTimeState(d);
				if(nutDao.insert(us).getId()>0){
					map.put("us", us);
				}
			}else{
				if(sdf.format(d).equals(sdf.format(us.getSignTimeState()))){//当月签到
					if(!sdf1.format(d).equals(sdf1.format(us.getSignTimeState()))){
						us.setSignNum(us.getSignNum()+1);
						us.setSignTimeState(d);
						us.setSignTime(us.getSignTime()+sdf1.format(d)+",");
						us.setState(0);
						if(nutDao.update(us)>0){
							map.put("us", us);
						}
						Calendar a = Calendar.getInstance();  
				        a.set(Calendar.DATE, 1);  
				        a.roll(Calendar.DATE, -1);  
				        int maxDate = a.get(Calendar.DATE);  
						if(us.getSignNum()==maxDate){
				        	UserDrawn ud=new UserDrawn();
				        	ud.setCreateTime(d);
				        	ud.setSiteName(site.getSite_name());
				        	ud.setUserName(proUser.getUserName());
				        	
				        	nutDao.insert(ud);
						}
						
						
					}else{
						map.put("us", us);
					}
				}else{//次月签到
					us.setSignNum(1);
					us.setSignTimeState(d);
					us.setSignTime(sdf1.format(d)+",");
					us.setState(0);
					if(nutDao.update(us)>0){
						map.put("us", us);
					}
					
				}
				
			}
		} catch (Exception e) {
			logger.error("签到错误",e);
		}
		return map;
	}
	/**
	 * 
	 * @Description:获取是否中奖	
	 * @author songyanbiao
	 * @date 2016年10月27日 下午2:22:13
	 * @param
	 * @return
	 */
	public int getDrawList(String userName){
		int i=0;
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		try {
			UserDrawn ud=nutDao.fetch(UserDrawn.class, Cnd.where("user_name","=",userName).and("create_time",">",(sdf.format(calendar.getTime())+"-01 00:00:00")));
			if(ud==null){
			}else if(ud.getState()==2&&ud.getNodrawState()==0){
				ud.setNodrawState(1);
				if(nutDao.update(ud)>0){
					i= 1;
				};
			}else if(ud.getState()==0&&ud.getVerifyState()==0){
				ud.setVerifyState(1);
				if(nutDao.update(ud)>0){
					i= 2;
				}
			}else if(ud.getState()==1&&ud.getDrawState()==0){
				ud.setDrawState(1);
				if(nutDao.update(ud)>0){
					i= 3;
				}
			}else{
				i= 4;
			}
			
		} catch (Exception e) {
			logger.error("获取上期中奖名单失败",e);
			i= -1;
		}
		return i;
	}
	/**
	 * 
	 * @Description:校验是否到达开奖时间	
	 * @author songyanbiao
	 * @date 2016年10月27日 下午2:10:40
	 * @param
	 * @return
	 */
	public int checkOpen() throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String selSql="SELECT open_time FROM t6_draw_config";
		String times=jdbcTemplate.queryForObject(selSql, String.class);
		if(sdf.parse(times).getTime()<new Date().getTime()){
			return 1;
		}else{
			return 0;
		}
	}
	/**
	 * @Description  创建子账号并在主账号中把子账号信息更新进去,添加场所用户中间表收集场所信息
	 * @date 2016年10月21日下午4:59:50
	 * @author guoyingjie
	 * @param username ===主账号手机号
	 * @param siteId
	 */
	public void  createSonAccount(final String username,final int siteId,final String sonname,final String password) throws Exception{
		Trans.exec(new Atom(){
			@Override
			public void run() {
				PortalUser p = userService.getPortalUserByTel(sonname);
				if(p!=null){
					 throw Lang.makeThrow("子账号已存在");
				}else{
					 PortalUser u = new PortalUser();
					 u.setUserName(sonname);
					 u.setPassWord(SHA256.getUserPassword(sonname, MD5.encode(password).toLowerCase()));
					 u.setSex(1);
					 u.setSonstate(1);
					 boolean is = userService.userRegist(u);
					 if(!is){
						 throw Lang.makeThrow("注册子账号失败");
					 }
					 String sql = "INSERT INTO t_cloud_site_portal(site_id,portal_id) VALUES(?,?)";
					 int i = jdbcTemplate.update(sql, new Object[] { siteId, u.getId() });
					 if(i!=1){
						 throw Lang.makeThrow("注册子账号收集场所信息失败");
					 }
					  
					 boolean isok = updateSonnameToDad(sonname,username);
					 if(!isok){
						 throw Lang.makeThrow("更新主账号失败");
					 }
				}
			}
		});	
	}
	
	/**
	 * @Description 将子账号更新到主账号中
	 * @date 2016年10月24日上午9:14:19
	 * @author guoyingjie
	 * @param sonName--子账号
	 * @param dadName--主账号
	 */
	public boolean updateSonnameToDad(String sonName,String dadName){
		PortalUser user = nutDao.fetch(PortalUser.class, Cnd.where("user_name","=",dadName));
		String isHaveSon = user.getSonName();
		if(isHaveSon!=null&&!"".equals(isHaveSon)){
			int is = isHaveSon.lastIndexOf("a");
			if(is>-1){//说明是有a子账号了了
				user.setSonName(isHaveSon+","+sonName);
			}else{
				user.setSonName(sonName+","+isHaveSon);
			}
		}else{
			user.setSonName(sonName);
		}
		user.setUserName(dadName);
		user.setPassWord(user.getPassWord());
		return userService.userUpdata(user);
	}
	
	/**
	 * @Description 自动生成子账号,判断如果存在子账号a,再次创建自动生成子账号b.
	 * @date 2016年10月24日上午10:29:41
	 * @author guoyingjie
	 * @param dadName
	 * @return
	 */
	public String getSonname(String dadName){
		PortalUser user = nutDao.fetch(PortalUser.class, Cnd.where("user_name","=",dadName));
		String isHaveSon = user.getSonName();
		if(isHaveSon!=null&&!"".equals(isHaveSon)){
			int is = isHaveSon.lastIndexOf("a");
			if(is>-1){//说明是有a子账号了了
				 return dadName+"b";
			}else{
				 return dadName+"a";
			}
		}else{
			 return dadName+"a";
		}
	}
	/**
	 * @Description 删除子账号
	 * @date 2016年10月24日下午12:45:34
	 * @author guoyingjie
	 * @param sonname
	 * @param dadname
	 * @param siteId
	 */
	public void deleteSonAccount(final String sonname,final String dadname,final int siteId) throws Exception{
		Trans.exec(new Atom(){
			@Override
			public void run() {
			    int i = deleteDadOrSon(dadname,sonname,1);
			    if(i!=1){
			    	 throw Lang.makeThrow("更新主账号失败");
			    }
			   
			    int izs = getPortalCloud(sonname,siteId);
			    if(izs!=1&&izs!=2){
			    	throw Lang.makeThrow("删除场所信息收集表失败");
			    }
			    
			    int d = deleteSiteCustom(sonname,siteId);
			    if(d!=1&&d!=2){
			    	throw Lang.makeThrow("删除场所用户到期时间表失败");
			    }
			    
			    int iz = deleteDadOrSon(dadname,sonname,2);
			    if(iz!=1){
			    	 throw Lang.makeThrow("删除子账号失败");
			    }
			}
		});
	}
	
	/**
	 * @Description  删除子账号,更新主账号
	 * @date 2016年10月27日上午10:17:14
	 * @author guoyingjie
	 * @param dadname
	 * @param sonname
	 * @param state:1--主账号;2--子账号
	 */
	public int deleteDadOrSon(String dadname,String sonname,int state){
		PortalUser user = null;
		if(state==1){
			user = nutDao.fetch(PortalUser.class, Cnd.where("user_name","=", dadname));
			user.setUserName(dadname);
			user.setPassWord(user.getPassWord());
			user.setSonName(user.getSonName().replace(sonname,"").replace(",",""));;
		    return nutDao.update(user);
		}else{
			user = nutDao.fetch(PortalUser.class, Cnd.where("user_name","=", sonname));
		    return nutDao.delete(user);
		}
	}
	
	/**
	 * @Description  确定是否有场所信息收集记录,有则删除
	 * @date 2016年10月24日下午2:00:23
	 * @author guoyingjie
	 * @param name
	 * @param siteId
	 * @return
	 */
	public int getPortalCloud(String name,int siteId){
		try {
			CloudSitePortalEntity portal = nutDao.fetch(CloudSitePortalEntity.class,Cnd.wrap(" portal_id = (SELECT id from t_portal_user where user_name = '"+name+"') and site_Id="+siteId+""));
			if(portal==null){
				return 2;
			}else{
				return nutDao.delete(portal);
			}
		} catch (Exception e) {
			return 0;
		}
	}
	/**
	 * @Description 删除用户到期时间信息
	 * @date 2016年10月24日下午2:22:30
	 * @author guoyingjie
	 * @param sonname
	 * @param siteId
	 * @return
	 */
	public int deleteSiteCustom(String sonname,int siteId){
		try {
			SiteCustomerInfo info = nutDao.fetch(SiteCustomerInfo.class, Cnd.wrap(" portal_user_id = (SELECT id from t_portal_user where user_name = '"+sonname+"') and site_Id="+siteId+""));
			if(info!=null){
				return nutDao.delete(info);
			}else{
				return 2;
			}
		} catch (Exception e) {
			return 0;
		}
	}
	/**
	 * @Description  获得用户的流量与时间的占比
	 * @date 2016年10月24日下午4:26:03
	 * @author guoyingjie
	 * @param sonname
	 * @param siteId
	 */
	public Map getSiteCustomTime(String sonname,int siteId){
		Map map = new HashMap<>();
		try {
			PortalUser user = userService.getPortalUserByTel(sonname);
			SiteCustomerInfo info = userService.getSiteCustomerInfo(siteId,user.getId());
			if(info==null){//当用子账号是没有划分过任何的信息
				map.put("time", "0小时");
				map.put("alltime", "0");
				map.put("sTime", 0);
				map.put("flow", "0");
				map.put("sFlow",0);
				map.put("flowstr", "0M");
			}else{
				Date date = new Date();
				String totalTime = "";//划分的总时间毫秒级的记录
				if(info.getTotalTime()!=null&&!"".equals(info.getTotalTime())){
					 totalTime = info.getTotalTime();//划分的总时间毫秒级的记录
				}else{
					 totalTime = "0";//划分的总时间毫秒级的记录
				}
				Date exDate = info.getExpirationTime();//子账号的到期时间
				if(exDate==null){
					map.put("time", "0小时");
					map.put("alltime", "0");
					map.put("sTime", 0);
				}else{
					if (date.getTime() - exDate.getTime() >= 0) {
						map.put("time", "0小时");
						map.put("alltime", "0");
						map.put("sTime", 0);
					} else {
						map.put("time",userService.timeBetween(exDate.getTime() - date.getTime(), 2));
						map.put("alltime",totalTime);
						map.put("sTime", exDate.getTime()-date.getTime());
					}
				}
				if (info.getTotalFlow() != null &&!"0".equals(info.getTotalFlow())) {
					String uFlow = info.getUsedFlow();
					if (uFlow == null || "0".equals(uFlow)) {
						uFlow = "0";
					}
					BigDecimal gFlow = BigDecimalUtil.subtract(
							new BigDecimal(info.getTotalFlow()), new BigDecimal(uFlow));
					if (gFlow.compareTo(new BigDecimal(0.0000)) == 1) {
						map.put("flow", info.getTotalFlow());
						map.put("flowstr", userService.kbChangeGM(gFlow.toString()));
						map.put("sFlow",gFlow.toString());
					} else {
						map.put("flow", "0");
						map.put("flowstr", "0M");
						map.put("sFlow",0);
					}
				} else {
					map.put("flow", "0");
					map.put("flowstr", "0M");
					map.put("sFlow",0);
				}
			}
		} catch (Exception e) {
			map.put("time", "0小时");
			map.put("alltime", "0");
			map.put("sTime", 0);
			map.put("flow", "0");
			map.put("sFlow",0);
			map.put("flowstr", "0M");
		}
		return map;
	}
	
	/**
	 * @Description  判断主账号的时间与流量是否在规定的范围内,
	 * 默认时间是必须在三天以上,流量最少200兆.
	 * 后期再做改造具体的要求听领导的,呵呵呵,sb!
	 * @date 2016年10月24日下午5:52:22
	 * @author guoyingjie
	 * @param dadname
	 * @param state--状态(1--划分时间,0--划分流量)
	 */
	public boolean checkDadAccount(String dadname,int siteId,int state,String timeOrFlow){
		PortalUser user = userService.getPortalUserByTel(dadname);
		SiteCustomerInfo info = userService.getSiteCustomerInfo(siteId,user.getId());
		if(info!=null){
			if(state==1){
				Date date = info.getExpirationTime();//主账号的到期时间
				
				Date nowDate = new Date();//系统时间
				if(date==null){
					date = nowDate;
				}
				if((date.getTime()-nowDate.getTime())>=3*24*60*60*1000){//主账号的剩余时长必须大于三天才能划拨
					long time = Long.parseLong(timeOrFlow);
					if(time>(date.getTime()-nowDate.getTime())){//划拨时间>剩余时间
						return false;
					}else{
						return true;
					}
				}else{
					return false;
				}
			}else{
				String totalFlow = info.getTotalFlow()==null?"0":info.getTotalFlow();//用户总流量
				String userFlow = info.getUsedFlow()==null?"0":info.getUsedFlow();//用户使用流量
				BigDecimal gFlow = BigDecimalUtil.subtract(new BigDecimal(totalFlow), new BigDecimal(userFlow));//主账号的剩余流量
				BigDecimal flow = new BigDecimal(timeOrFlow);//划拨的流量
				if(gFlow.compareTo(new BigDecimal(204800)) == 1){//主账号的流量必须大于200兆才能划拨
					if(flow.compareTo(gFlow) == 1){//划拨流量>剩余流量
						return false;
					}else{
						return true;
					}
				}else{
					return false;
				}
			}
		}else{
			return false;
		}
	}
	
	/**
	 * @Description  主账号划拨流量与时间到子账号
	 * @date 2016年10月25日上午10:23:55
	 * @author guoyingjie
	 * @param dadname--主账号
	 * @param sonname--子账号
	 * @param siteId--场所id
	 * @param flowsOrTime--时间或者流量(如果是流量直接传过来的是kb为单位)
	 * @param state--状态(1--划分时间,0--划分流量)
	 */
	public void dadDevideToSonTimeAndFlow(final String dadname,final String sonname,final int siteId,final String flowsOrTime,final int state) throws Exception{
		Trans.exec(new Atom(){
			@Override
			public void run() {
			PortalUser user = userService.getPortalUserByTel(dadname);
			PortalUser sonuser = userService.getPortalUserByTel(sonname);
			SiteCustomerInfo info = userService.getSiteCustomerInfo(siteId,user.getId());
			SiteCustomerInfo soninfo = userService.getSiteCustomerInfo(siteId,sonuser.getId());
			if(info!=null){
				if(state==1){
					int i = updateSiteConstomInfo(flowsOrTime, "", info, 1, siteId, user.getId(), 1);
					if(i!=1){
						 throw Lang.makeThrow("更改主账号的剩余时间异常");
					}
					int z =updateSiteConstomInfo(flowsOrTime, "", soninfo, 2, siteId, sonuser.getId(), 1);
					if(z!=1){
						 throw Lang.makeThrow("更改子账号的剩余时间异常");
					}
					int s = updateSonState(sonuser);
					if(s==0){
						 throw Lang.makeThrow("更改子账号激活状态异常");
					}
				}else{
					int i = updateSiteConstomInfo( "",flowsOrTime, info, 1, siteId, user.getId(), 2);
					if(i!=1){
						 throw Lang.makeThrow("更改主账号的剩余流量异常");
					}
					int z = updateSiteConstomInfo( "",flowsOrTime, soninfo, 2, siteId, sonuser.getId(), 2);
					if(z!=1){
						 throw Lang.makeThrow("更改子账号的流量异常");
					}
					int s = updateSonState(sonuser);
					if(s==0){
						 throw Lang.makeThrow("更改子账号激活状态异常");
					}
				}
			}
		}  
	});	
}
	/**
	 * 
	 * @Description:通过id获取用户签到信息	
	 * @author songyanbiao
	 * @date 2016年10月26日 下午5:53:12
	 * @param
	 * @return
	 */
	public UserSign getUsById(int uid){
		return nutDao.fetch(UserSign.class, Cnd.where("id","=",uid));
	}
	/**
	 * 
	 * @Description:抽奖	
	 * @author songyanbiao
	 * @date 2016年10月27日 上午10:01:48
	 * @param
	 * @return
	 */
	public int checkDraw(int signid,int grade,HttpSession session){
		UserSign us=getUsById(signid);
		if(us.getState()!=0){
			return 0;
		}else{
			return doDraw(signid, grade);
		}
	}
	
	/**
	 * 
	 * @Description:用户抽奖	
	 * @author songyanbiao
	 * @date 2016年10月26日 下午6:31:43
	 * @param
	 * @return
	 */
	public int doDraw(final int signid,final int grade){
			try {
				Trans.exec(new Atom() {
					@Override
					public void run() {
						UserSign us=getUsById(signid);
						PortalUser user=userService.getPortalUserById(us.getUserId());
						user.setUserIntegral(user.getUserIntegral()+grade);
						int i= nutDao.update(user);
						if(i<1){
							throw Lang.makeThrow("抽奖更新用户积分出错");
						}
						
						i=getDarwByUserid(us.getUserId(), grade);
						if(i<1){
							throw Lang.makeThrow("插入用户抽奖记录表失败");
						}
						us.setState(1);
						i=nutDao.update(us);
						if(i<1){
							throw Lang.makeThrow("更新用户抽奖状态失败");
						}
					}
				});
				
			} catch (Exception e) {
				logger.error("抽奖失败");
				return -1;
			}
		return 1;
	}
	/**
	 * 
	 * @Description:插入用户抽奖记录	
	 * @author songyanbiao
	 * @date 2016年10月26日 下午6:20:06
	 * @param
	 * @return
	 */
	public int getDarwByUserid(int userId,int grade){
		try {
			
			UserDrawLog ud= new UserDrawLog();
			ud.setCreateTime(new Date());
			ud.setDrawContent(grade);
			ud.setDrawTime(new Date());
			ud.setUserId(userId);
			return nutDao.insert(ud).getId();
		} catch (Exception e) {
			logger.error("插入抽奖记录出错",e);
			return 0;
		}
	}
	/**
	 * 
	 * @Description:查询上期中奖名单	
	 * @author songyanbiao
	 * @date 2016年10月31日 下午5:08:05
	 * @param
	 * @return
	 */
	public List<Map<String, Object>> getList(String userName){
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		String date1=sdf.format(calendar.getTime())+"-01 00:00:00";
		String date2=sdf.format(new Date())+"-01 00:00:00";
		String sql="SELECT * FROM t6_user_drawn WHERE create_time> ? AND create_time<?";
		List<Map<String, Object>> ls =new ArrayList<>();
		try {
			 ls=jdbcTemplate.queryForList(sql,new Object[]{date1,date2});
		} catch (Exception e) {
			logger.error("查询获奖名单出错",e);
			return null;
		}
		return ls;
	}
	/**
	 * 
	 * @Description:获取用户是否中奖	
	 * @author songyanbiao
	 * @date 2016年10月31日 下午5:07:50
	 * @param
	 * @return
	 */
	public int getDrawMyself(String userName){
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		String date1=sdf.format(calendar.getTime())+"-01 00:00:00";
		String date2=sdf.format(new Date())+"-01 00:00:00";
		String sql="SELECT state FROM t6_user_drawn WHERE  user_name=? AND create_time> ? AND create_time<?";
		int i=0;
		try {
			i=jdbcTemplate.queryForObject(sql, new Object[]{userName,date1,date2},Integer.class);
			
		} catch (Exception e) {
			logger.error("查询自己是否中奖失败",e);
			return -1;
		}
		return i;
	}
	/**
	 * 
	 * @Description:补签充值余额	
	 * @author songyanbiao
	 * @date 2016年11月3日 上午11:24:38
	 * @param
	 * @return
	 */
	public String PayBalanceForRepla(String amount,String userName,String openId,String APPID ,String MCHID,
			String times,int siteId,HttpServletRequest request){
		try {
			if (null == openId || "".equals(openId)) {
				logger.error("此时openid为空····");
				return "/wechat/weixinerror";
			}
			Map<String, String> map = new HashMap<String, String>();
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
			map.put("times", times);
			map.put("storeId", siteId+"");// 场所Id
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
			request.getSession().setAttribute("siteId", siteId); //场所id
			request.getSession().setAttribute("userId", user.getId()); //用户id
		} catch (Exception e) {
			logger.error("JSAPI统一下单失败", e);
			return "/wechat/weixinerror";
		} 
		return "/wechat/wxpayorepala";
	}
	
	/**
	 * 
	 * @Description:检测是否缴费成功	
	 * @author songyanbiao
	 * @date 2016年11月3日 下午1:38:29
	 * @param
	 * @return
	 */
	public String getPayOtherStatus(HttpSession session, String outTradeNo,int siteId,int userId){
		 boolean isok = userService.checkPayResult(outTradeNo);
		   if(isok){
			   session.setAttribute("siteId", siteId);
			   session.setAttribute("userId", userId);
			   return "/wechat/wxsuccess";
		   }else{
			   return "/wechat/weixinerror";
		   }
	}
	/**
	 * 
	 * @Description:获取场所套餐	
	 * @author songyanbiao
	 * @date 2016年11月3日 下午4:05:48
	 * @param
	 * @return
	 */
	public String getMeal(String userName,int siteId,HttpSession session){
		ExecuteResult ex= new ExecuteResult();
		Map map= new HashMap<>();
		PortalUser user=userService.getPortalUserByTel(userName);
		if(user==null){
			return "/wechat/wxerror";
		}
		UserBalance ub=weChatOtherService.getUserBanlance(userName);
		if(ub!=null){
			//session.setAttribute("sum", ub.getBalance());
			ex.setMsg( ub.getBalance()+"");
		}else{
			//session.setAttribute("sum", new BigDecimal("0.00"));
			ex.setMsg("0.00");
		}
		session.setAttribute("tel", userName);
		SitePriceConfigAll siteAll=myselfPayService.getSiteRule(siteId, user.getId());
		if(siteAll!=null){
			//session.setAttribute("siteAll", map); 
			ex.setCode(200);
			ex.setData(siteAll);
		}else{
			ex.setCode(201);
		}
		
		return ex.toJsonString();
	}
	/**
	 * 
	 * @Description签到去充值获取场所套餐	
	 * @author songyanbiao
	 * @date 2016年11月3日 下午4:54:07
	 * @param
	 * @return
	 */
	public String toPayPage(String userName,int siteId,String times,HttpSession session){
		PortalUser user=userService.getPortalUserByTel(userName);
		if(user==null){
			return "/wechat/wxerror";
		}
		CloudSite site=(CloudSite)session.getAttribute("site");
		if(site==null){
			site=myselfPayService.getSite(siteId+"");
			if(site!=null){
				session.setAttribute("site", site);
			}
		}
		WechatUserInfo info=(WechatUserInfo)session.getAttribute("userinfo");
		if(info==null){
			info=checkOpenid(user.getId());
			session.setAttribute("openid", info.getOpenid());
		}
		session.setAttribute("tel", userName);
		return "/wechat/payMeal";
	}
	public String weiXinPayOtherAccount(String APPID, String MCHID,HttpServletRequest request,HttpServletResponse response,HttpSession session){
		
		return "";
	}
/**
 * @Description  更改到期时间或者剩余流量
 * @date 2016年10月25日上午11:25:53
 * @author guoyingjie
 * @param endTime
 * @param endFlow
 * @param info
 * @param state-1主账号,2子账号
 * @param timeorflow--1更改时间,2更改流量
 */
public int updateSiteConstomInfo(String endTime,String endFlow,SiteCustomerInfo info,int state,int siteId,int portalId,int timeorflow){
	long nowtime = new Date().getTime();//系统的时间
	
	if(state==1){//主账号
		if(timeorflow==1){//更改时间
			long stime = Long.parseLong(endTime);//划分的时间
			long eTime = info.getExpirationTime().getTime();//账号剩余的时间
			if(info.getExpirationTime()==null){
				info.setExpirationTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(nowtime+stime)));
			}else{
				if(nowtime>=eTime){//说明已经过期
					info.setExpirationTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(nowtime+stime)));
				}else{
					info.setExpirationTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(eTime-stime)));
				}
			}
			info.setTotalFlow(info.getTotalFlow());
		}else{
			info.setExpirationTime(info.getExpirationTime());
			long flow = Long.parseLong(endFlow);//划分的流量
			String totalFlow = info.getTotalFlow()==null?"0":info.getTotalFlow();//用户总流量
			info.setTotalFlow((Long.parseLong(totalFlow)-flow)+"");
		}
		info.setPayWay(info.getPayWay());
		info.setPortalUserId(portalId);
		info.setSiteId(siteId);
		info.setCreateTime(info.getCreateTime());
		info.setUpdateTime(new Timestamp(new Date().getTime()));
		return userService.updateObject(info);
	}else{
		if(info==null){
			info = new SiteCustomerInfo();
			if(timeorflow==1){//按时间划分
				long stime = Long.parseLong(endTime);//划分的时间
				info.setExpirationTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(nowtime+stime)));
				info.setPayWay(0);
				info.setTotalTime(endTime);
				info.setTotalFlow("0");
			}else{
				info.setExpirationTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				info.setPayWay(2);
				info.setTotalFlow(endFlow);
			}
			info.setPortalUserId(portalId);
			info.setSiteId(siteId);
			info.setCreateTime(new Timestamp(new Date().getTime()));
			info.setUpdateTime(new Timestamp(new Date().getTime()));
			return nutDao.insert(info).getId()>0?1:0;
		}else{
			if(timeorflow==1){//时间
				long stime = Long.parseLong(endTime);//划分的时间
				if(info.getExpirationTime()==null){
					info.setExpirationTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(nowtime+stime)));
					String totalTime = stime+"";//划分的总时间毫秒级的记录
					info.setTotalTime(totalTime);
				}else{
					long eTime = info.getExpirationTime().getTime();//账号剩余的时间
					if(nowtime>=eTime){//说明已经过期
						info.setExpirationTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(nowtime+stime)));
					}else{
						info.setExpirationTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(eTime+stime)));
					}
					String totalTime = eTime+stime-nowtime+"";//划分的总时间毫秒级的记录
					info.setTotalTime(totalTime);
				}
				info.setTotalFlow(info.getTotalFlow());
			}else{
				info.setExpirationTime(info.getExpirationTime());
				long flow = Long.parseLong(endFlow);//划分的流量
				String totalFlow = info.getTotalFlow()==null?"0":info.getTotalFlow();//用户总流量
				info.setTotalFlow((Long.parseLong(totalFlow)+flow)+"");
			}
			info.setPayWay(info.getPayWay());
			info.setPortalUserId(portalId);
			info.setSiteId(siteId);
			info.setCreateTime(info.getCreateTime());
			info.setUpdateTime(new Timestamp(new Date().getTime()));
			return userService.updateObject(info);
		}
	}
  }
	/**
	 * @Description  激活子账号
	 * @date 2016年11月10日上午10:37:21
	 * @author guoyingjie
	 * @param userName
	 */
	public void activeSonState(String userName){
		String[] name = userName.split(",");
		String sonname = "";
		if(name.length==1){
			sonname = "('"+name[0]+"')";
		}else{
			sonname = "('"+name[0]+"','"+name[1]+"')";
		}
		String updatesql = "UPDATE t_portal_user SET t5_son_state = 0 WHERE user_name IN "+sonname;
		jdbcTemplate.update(updatesql);
	}
		 
	/**
	 * @Description  更改子账号激活状态
	 * @date 2016年11月10日上午11:15:47
	 * @author guoyingjie
	 * @param user
	 * @return
	 */
	public int updateSonState(PortalUser user){
		try {
			user.setUserName(user.getUserName());
			user.setPassWord(user.getPassWord());
			user.setSonstate(1);
			user.setState(user.getState());
			user.setSex(user.getSex());
			user.setCreateTime(user.getCreateTime());
			user.setIsStoped(user.getIsStoped());
			return nutDao.update(user);
		} catch (Exception e) {
			return 0;
		}
	}
	
}
