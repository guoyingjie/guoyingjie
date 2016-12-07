package com.broadeast.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.lang.Lang;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import sun.misc.BASE64Decoder;
import sun.util.logging.resources.logging;

import com.alibaba.fastjson.JSON;
import com.broadeast.bean.RouterAndDevice;
import com.broadeast.bean.SitePriceConfigAll;
import com.broadeast.dao.PortalLogDao;
import com.broadeast.entity.CloudSite;
import com.broadeast.entity.CloudSiteRouters;
import com.broadeast.entity.PortalUser;
import com.broadeast.entity.SiteCustomerInfo;
import com.broadeast.entity.SiteIntegralGrade;
import com.broadeast.entity.SitePaymentRecord;
import com.broadeast.entity.SitePriceConfig;
import com.broadeast.entity.UserBalance;
import com.broadeast.entity.UserMessage;
import com.broadeast.entity.UserRecommend;
import com.broadeast.util.BigDecimalUtil;
import com.broadeast.util.CalendarUtil;
import com.broadeast.util.DateUtil;
import com.broadeast.util.ExecuteResult;
import com.broadeast.util.InitContext;
import com.broadeast.util.MD5;
import com.broadeast.util.OssSchoolManage;
import com.broadeast.util.SHA256;
import com.broadeast.util.StringUtil;
import com.broadeast.util.toLoginUtil;
import com.broadeast.weixin.comment.RandomStringGenerator;

@Service
@SuppressWarnings("all")
public class UserService {
	Logger log = Logger.getLogger(UserService.class);
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Resource(name = "nutDao")
	private Dao nutDao;
	@Resource(name = "templJdbcTemplate")
	private JdbcTemplate templJdbcTemplate;

	@Resource(name = "templNutDao")
	private Dao templNutDao;
	@Autowired
	private PortalLogDao portalLogDao;

	@Autowired
	private SiteService siteservice;

	@Resource(name="weChatService")
	private WeChatService weChatService;
	
	
	/**
	 * 根据userId 获取PortalUser对象
	 * 
	 * @param userId
	 * @return
	 */
	public PortalUser getPortalUserById(int userId) {
		return nutDao.fetch(PortalUser.class, Cnd.where("id", "=", userId));
	}

	/**
	 * 用户注册
	 * 
	 * @param user
	 * @return
	 */
	public boolean userRegist(PortalUser user) {
		try {
			// 保存PortalUser表
			nutDao.insert(user);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean userRegists(int siteId,PortalUser user) {
		try {
			// 保存PortalUser表
			nutDao.insert(user);
			this.intoRegisterPortal(siteId, user.getId());
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * 用户修改密码
	 */
	public boolean userUpdata(PortalUser user) {
		try {
			nutDao.update(user);
		} catch (Exception e) {
			log.error("用户修改密码失败", e);
			return false;
		}
		return true;
	}

	/**
	 * @Description 用户登录成功后添加最后一次登录的mac地址
	 * @date 2016年7月25日下午3:18:59
	 * @author guoyingjie
	 * @param user
	 */
	public void updateUserLastMac(PortalUser user,String clientMac){
		user.setClientMac(clientMac);
		user.setId(user.getId());
		user.setUserName(user.getUserName());
		user.setPassWord(user.getPassWord());
		nutDao.update(user);
	}
	
	/**
	 * @Description  根据设备mac确定当前登陆的用户是否是上次登录的用户
	 * @date 2016年7月26日下午1:35:08
	 * @author guoyingjie
	 * @param clientmac
	 * 
	 * 如果是当前的用户那么在他登陆成功后就直接把当前登路的设备mac存入.
	 * 
	 * 下次当另一个设备登陆的时候检测到么有就直接重走流程
	 * @return
	 */
	public PortalUser getUserByMac(String clientmac){
		PortalUser user = null;
		try {
			user = nutDao.fetch(PortalUser.class,Cnd.where("client_mac","=",clientmac));
		} catch (Exception e) {
			log.error("根据设备mac确定当前登陆的用户是否是上次登录的用户出错",e);
		}
		return user;
	}
	
	/**
	 * 
	 * @Description 根据用户手机号获得当前登陆的用户
	 * @date 2016年8月2日上午10:25:31
	 * @author guoyingjie
	 * @param tel
	 * @return
	 */
	public PortalUser getPortalUserByTel(String tel) {
		/*Sql sql=Sqls.fetchString("call aaa("+tel+")");
		nutDao.execute(sql);*/
		PortalUser pt = null;
		try {
			pt = nutDao.fetch(PortalUser.class,
					Cnd.where("user_name", "=", tel));
		} catch (Exception e) {
			log.error("获得用户信息失败");
		}
		return pt;
	}
	
	/**
	 * 验证用户登录
	 * 
	 * @param name
	 * @param pwd
	 * @return
	 */
	public PortalUser getUserPro(String name, String pwd) {
		String newPw = SHA256.getUserPassword(name, pwd);
		PortalUser porUser = nutDao.fetch(PortalUser.class,
				Cnd.where("user_name", "=", name).and("pass_word", "=", newPw));
		return porUser;
	}
	
	/**
	 * @Description 通过加密的密码获得portalUser
	 * @date 2016年7月29日下午3:38:09
	 * @author guoyingjie
	 * @param name
	 * @param pwd
	 * @return
	 */
	public PortalUser getUserByEncryptPs(String name, String pwd){
		PortalUser porUser = nutDao.fetch(PortalUser.class,
				Cnd.where("user_name", "=", name).and("pass_word", "=", pwd));
		return porUser;
	}
	

	/**
	 * //注册成功后在中间表中插入数据,以便拿到注册的人数
	 * 
	 * @param siteId
	 * @param userId
	 */
	public void intoRegisterPortal(int siteId, int userId) {
		String sql = "INSERT INTO t_cloud_site_portal(site_id,portal_id) VALUES(?,?)";
		jdbcTemplate.update(sql, new Object[] { siteId, userId });
	}

	/**
	 * 按照场所配置的终端人数踢人
	 * 
	 * @param telePhone
	 * @param siteMac
	 * @param clientMac
	 * @param siteId
	 */
	public void keepSomeOnline(String telePhone, String siteMac,
			String clientMac, int siteId) {
		// 查询场所配置的最大终端人数n
		CloudSite site = nutDao.fetch(CloudSite.class,
				Cnd.where("id", "=", siteId));
		// 由于多台设备登录需要用到 t_cloud_site 的 allow_client_num字段,取消以前踢人的配置字段
		// int clientNum = site.getAllow_client_num();
		// 获取当前用户的非当前mac的记录结果集list，按照Login_time倒叙排列 , 将不是当前用户的mac 记录拿到
		List<String> list = portalLogDao.getUserOnlineMacs(siteId, telePhone,
				clientMac);

		// list.size()>=n,n-1条以后的记录全部必须踢掉
		if (list.size() >= 1) {// 需要踢 这里写死了.
			// int i = clientNum < 1 ? 0 : (clientNum - 1);// 要保留的条数
			// 将待踢mac直接拼接 ,
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < list.size(); i++) {
				sb.append(list.get(i));
			}
			log.info("kick out macs:" + sb.toString()
					+ this.getClass().getCanonicalName()
					+ "---UserService-174行");
			String oldKickMac = portalLogDao.getOldKickMacs(siteMac);
			if (oldKickMac != null) {// 将踢人表原有的当前登录mac移除，不踢掉
				oldKickMac.replace(clientMac, "");
			}
			log.info("kick oldKickMac:" + oldKickMac
					+ this.getClass().getCanonicalName()
					+ "---UserService-180行");
			// 追加踢之
			if (oldKickMac != null) {
				sb.append(oldKickMac);
				// update
				if (siteMac != null && !("".equals(siteMac))) {
					portalLogDao.updateKickMac(sb.toString(), siteMac);
				}
			} else {
				// insert
				if (siteMac != null && !("".equals(siteMac))) {
					portalLogDao.insertKickMac(sb.toString(), siteMac);
				}
			}

		}

	}

	/**
	 * 判断同一个账号是否在当天超过三次登录不同的mac地址
	 * 
	 * @param telephone
	 *            用户手机号
	 * @param siteId
	 *            场所id
	 * @return true{超过三次} ,false{没有超过三次}
	 */
	@SuppressWarnings("deprecation")
	public boolean isSuperThree(String telephone, int siteId, String mac,
			SiteCustomerInfo scis) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String tablename = "v1_portal_" + sdf.format(new java.util.Date());
		String table = "select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME='"
				+ tablename + "'";
		List<String> isTable = templJdbcTemplate.queryForList(table,
				String.class);
		if (isTable != null && isTable.size() != 0) {
			try {
				String sql = "SELECT count(DISTINCT MAC) FROM "
						+ tablename
						+ " where Auth_id=? AND STORE_ID = ? AND Dynamic_pwd=-1 ";
				int count = templJdbcTemplate.queryForInt(sql, new Object[] {
						telephone, siteId });
				String isHaveMac = "SELECT DISTINCT MAC FROM "
						+ tablename
						+ " where Auth_id=? AND STORE_ID = ? AND Dynamic_pwd=-1 ";
				List<String> macs = templJdbcTemplate.queryForList(isHaveMac,
						new Object[] { telephone, siteId }, String.class);
				boolean isHaveMacs = macs.contains(mac.trim());
				// 这个注释的功能是cloud项目中场所管理允许一个用户下的最多登录的终端数量
				String allowLoginMacNum = "SELECT allow_client_num  from t_cloud_site WHERE id=?";
				int allowMacNum = jdbcTemplate.queryForInt(allowLoginMacNum,
						new Object[] { siteId });
				if (count >= allowMacNum && isHaveMacs == false) {
					// 如果有锁定记录,将今天的portal日志表中的状态状改为0,方便在用户要求在解绑,直接改锁定时间就可以
					String update = "UPDATE " + tablename
							+ " SET Dynamic_pwd = 0 where Auth_id='"
							+ telephone + "'";
					templJdbcTemplate.update(update);
					updateSiteCustomerInfo(scis);
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * @Description 判断用户是否锁定{当前时间-锁定时间>24h,锁定失效,否则锁定}
	 * @param scis
	 *            场所到期表
	 * @return true{用户被锁定},false{用户没有被锁定}
	 * @throws ParseException
	 */
	public String ifUserLuck(SiteCustomerInfo scis) throws ParseException {
		Date lunkTime = scis.getLuckTime();
		if (lunkTime == null || "".equals(lunkTime)) {
			return "null";
		} else {
			long oneDay = 24 * 60 * 60 * 1000;
			long time = new Date().getTime() - lunkTime.getTime();
			if (time > oneDay) {
				return "null";
			} else {
				return dateDiff(oneDay - time);
			}
		}
	}

	/**
	 * @Description 获得到期时间表的数据
	 * @param portalId
	 *            用户id
	 * @param siteId
	 *            场所id
	 * @return
	 */
	public SiteCustomerInfo isHaveSiteCustomerInfo(int userId, int siteId) {
		SiteCustomerInfo scis = null;

		try {
			scis = nutDao.fetch(
					SiteCustomerInfo.class,
					Cnd.where("portal_user_id", "=", userId).and("site_id",
							"=", siteId));
		} catch (Exception e) {
			log.error(this.getClass().getCanonicalName(), e);
			return null;
		}
		return scis;
	}

	/**
	 * 判断是否在中间表里有当前登录的用户
	 * 
	 * @param siteId
	 * @param userId
	 * @return
	 */
	public void isHavePortalUser(Integer siteId, Integer userId) {
		String sql = "SELECT * FROM t_cloud_site_portal WHERE site_id =? AND portal_id = ? LIMIT 0,1";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,
				new Object[] { siteId, userId });
		if (list.size() != 0 && list != null) {
		} else {
			try {
				String sqls = "INSERT INTO t_cloud_site_portal(site_id,portal_id) VALUES (?,?)";
				jdbcTemplate.update(sqls, new Object[] { siteId, userId });
			} catch (Exception e) {
				log.error(this.getClass().getCanonicalName()
						+ "--用户信息id,场所信息id插入中间表失败", e);
			}
		}
	}

	/**
	 * 判断登录之前是否已经有人在登录
	 * 
	 * @param telephone
	 *            用户账号
	 * @param siteId
	 *            场所id
	 * @param mac
	 *            设备mac
	 * 
	 */
	public List hasLogin(String telephone, int siteId, String mac) {
		List ls = new ArrayList<>();
		String macSql = "SELECT DISTINCT callingstationid as MAC, terminal_device AS Terminal_device FROM radacct where username = ?"
				+ " AND acctstoptime is null  AND callingstationid != ? ORDER BY acctstarttime DESC LIMIT 0,1";
			try {
				List<Map<String, Object>> macs = jdbcTemplate .queryForList(macSql, new Object[] { telephone, mac });
				for (int i = 0; i < macs.size(); i++) {
					if (!macs.get(i).get("MAC").equals(mac)) {
						Pattern pattern = Pattern .compile(";\\s?(\\S*?\\s?\\S*?)\\s?(Build)?/");
						Matcher matcher = pattern.matcher(macs.get(i).get("Terminal_device")+"");
						String model = null;
						boolean boo = matcher.find();
						if (boo) {
							model = matcher.group(1).trim();
							macs.get(i).put("Terminal_device", model);
						} else if (((String) macs.get(i).get("Terminal_device")).indexOf("iPhone") > -1) {
							macs.get(i).put("Terminal_device", "iPhone");
						} else {
							macs.get(i).put("Terminal_device", 0);
						}
						ls.add(macs.get(i));
					}
				}
			} catch (Exception e) {
			}
			return ls;
	}

	/**
	 * 添加token值放行时校验
	 * 
	 * @param devicemac
	 *            用户设备mac
	 * @param token
	 * @param userId
	 *            用户id id为0时代表用户是体验用户
	 */
	public void insertToken(String devicemac, String token, int userId,
			String oldAuthUrl,String ip,int siteId,String basip) {
		RouterAndDevice rad = null;
		try {
			rad = templNutDao.fetch(RouterAndDevice.class,
					Cnd.where("portal_user_id", "=", userId));
			if (userId == 0 || rad == null) {// 如果为null则添加，不为null则修改token值
				rad = new RouterAndDevice();
				rad.setDevicemac(devicemac);
				rad.setToken(token);
				rad.setUserId(userId);
				rad.setOldAuthUrl(oldAuthUrl);
				rad.setUserIp(ip);
				rad.setSiteId(siteId);
				rad.setBasip(basip);
				templNutDao.insert(rad);
			} else {
				rad.setToken(token);
				rad.setOldAuthUrl(oldAuthUrl);
				rad.setDevicemac(devicemac);
				rad.setUserIp(ip);
				rad.setSiteId(siteId);
				rad.setBasip(basip);
				templNutDao.update(rad);
			}
		} catch (Exception e) {
			log.error("添加token值放行时校验错误", e);
		}

	}
	
	/**
	 * 根据路由器mac获得场所id
	 * 
	 * @param routerMac
	 * @return
	 */
	public int selSiteId(String routerMac) {
		CloudSiteRouters csr = nutDao.fetch(CloudSiteRouters.class,
				Cnd.where("mac", "=", routerMac.replace(":", "")));
		return csr.getSiteId();
	}

	/**
	 * 
	 * @Description //查询改场所下所有非停用的融合套餐规则
	 * @param siteId
	 * @return
	 */
	public List<Map<String, Object>> getIsActiveRule(int siteId) {
		String sql = "SELECT s.id,s.comboNumber,s.name FROM t_site_price_config s  WHERE s.site_id=? AND s.is_stoped=0 AND s.comboNumber IS NOT NULL";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,
				new Object[] { siteId });
		return list;

	}
	/**
	 * 
	 * @Description:获取场所套餐包	
	 * @author songyanbiao
	 * @date 2016年9月19日 下午2:03:06
	 * @param
	 * @return
	 */
	public List<Map<String, Object>> getMealList(int siteId){
		String sql=	"SELECT GROUP_CONCAT(id) ids,GROUP_CONCAT(unit_price ORDER BY id ASC) price,name,price_type,is_stoped,GROUP_CONCAT(charge_type ORDER BY charge_type ASC) charge_type,"+
				 	" price_num,GROUP_CONCAT(comboNumber) comboNumber,v2_recommend_state,v2_give_meal,v2_givemeal_unit,v2_describe"+
					" FROM t_site_price_config WHERE site_id=? AND is_stoped=0  GROUP BY name ORDER BY is_stoped ,price_type ASC";
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		try {
			list = jdbcTemplate.queryForList(sql,new Object[] { siteId });
		} catch (Exception e) {
			log.error("查询场所套餐包出错",e);
		}
		return list;
	}
	

	/**
	 * @Description 查询改场所下所有非停用的非融合套餐规则
	 * @param siteId
	 * @return
	 */
	public List<Map<String, Object>> getNotActiveRule(int siteId) {
		String sql = "SELECT s.id,s.comboNumber,s.name FROM t_site_price_config s WHERE s.site_id=? AND s.is_stoped=0 AND s.comboNumber IS  NULL";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,
				new Object[] { siteId });
		return list;
	}

	/**
	 * @Description 判断用户是否注册过
	 * @param username
	 *            用户的电话号码
	 */
	public PortalUser ishavePortalUser(String username) {
		try {
			PortalUser portal = nutDao.fetch(PortalUser.class,
					Cnd.where("user_name", "=", username));
			if (portal == null) {
				return null;
			} else {
				return portal;
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @Description 返回场所id 从中间表中查到场所的信息,这个必须是注册过的用户才能查到场所的信息
	 * @param portalId
	 *            用户唯一的id标识
	 * 
	 * @return
	 */
	public List<Integer> getSiteId(int portalId) {
		String sql = "SELECT site_id FROM t_cloud_site_portal WHERE portal_id = ?";
		List<Integer> list = jdbcTemplate.queryForList(sql, Integer.class,
				new Object[] { portalId });
		return list == null ? null : list;
	}

	/**
	 * @Description 获得场所信息
	 * @param siteId
	 * @return
	 */
	public CloudSite findCloudSite(int siteId) {
		try {
			CloudSite site = nutDao.fetch(CloudSite.class,
					Cnd.where("id", "=", siteId));
			return site;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @Description 获取当前用户还有多少小时
	 * @param time
	 *            结束时间
	 * @return
	 * @throws ParseException
	 */
	public String getHourInfo(long time) throws ParseException {
		long nowtime = new Date().getTime();
		// 当前时间大于到期时间,说明已经过期
		if (nowtime - time >= 0) {
			return "已到期";
		} else {
			long rTime = time - nowtime;
			return dateDiff(rTime);
		}
	}

	/**
	 * @Description 计算两个时间相差几年几小时几分钟
	 * @param diff
	 *            获得两个时间的毫秒时间差
	 * @return
	 * @throws ParseException
	 */
	public static String dateDiff(long diff) throws ParseException {
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long day = diff / nd;// 计算差多少天
		long hour = diff % nd / nh;// 计算差多少小时
		long min = diff % nd % nh / nm;// 计算差多少分钟
		long sec = diff % nd % nh % nm / ns;// 计算差多少秒//输出结果
		if (day <= 0) {
			return hour + "小时" + min + "分钟";
		}
		if (hour <= 0 && day <= 0) {
			return "0小时" + min + "分钟";
		}
		return day + "天" + hour + "小时";
	}

	/**
	 * @Description 查找用户的剩余时间,用户流量预留,如果<br/>
	 *              注册了却没有缴费,处理这种情况,让他的剩余时间为0天0小时
	 * @param username
	 */
	public Map<String, Object> findSiteCustomerInfo(int portalId, int siteId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CloudSite site = findCloudSite(siteId);
			SiteCustomerInfo scis = nutDao.fetch(
					SiteCustomerInfo.class,
					Cnd.where("portal_user_id", "=", portalId).and("site_id",
							"=", siteId));
			if (scis != null) {
				String totalFlow = scis.getTotalFlow();// 数据库默认的是null,需要判断null
				if (null == totalFlow || "".equals(totalFlow)
						|| "null".equals(totalFlow)) {
					totalFlow = "0.0000";
				}
				String userFlow = scis.getUsedFlow();
				if (userFlow == null || "".equals(userFlow)
						|| "null".equals(userFlow)) {
					userFlow = "0.0000";
				}
				BigDecimal totalFlows = new BigDecimal(totalFlow);
				BigDecimal userFlows = new BigDecimal(userFlow);
				BigDecimal surplusFlow = BigDecimalUtil.subtract(totalFlows,
						userFlows);
				String timeText = getHourInfo(scis.getExpirationTime()
						.getTime());
				map.put("portalId", portalId);
				map.put("siteId", siteId);
				map.put("time", timeText);
				map.put("surplusFlow", surplusFlow.toString().split("\\.")[0]);
				map.put("siteName", site.getSite_name());
			} else {
				// 说明用户注册了但是没有缴费
				map.put("portalId", portalId);
				map.put("siteId", siteId);
				map.put("time", "已到期");
				map.put("surplusFlow", "0");
				map.put("siteName", site.getSite_name());
			}
			return map;
		} catch (Exception e) {
			log.error(this.getClass().getCanonicalName()
					+ "==method=findSiteCustomerInfo", e);
			return null;
		}
	}

	/**
	 * @Description 根据用户名获得用户的剩余时间
	 * @param username
	 *            用户电话号码
	 */
	public List<Map<String, Object>> findUserBalance(String username) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		try {
			// 根据电话号码找到对应的用户
			PortalUser portal = nutDao.fetch(PortalUser.class,
					Cnd.where("user_name", "=", username));
			if (portal != null) {
				List<Integer> lists = getSiteId(portal.getId());
				if (lists.size() > 0 && lists != null) {
					for (int i = 0; i < lists.size(); i++) {
						map = findSiteCustomerInfo(portal.getId(), lists.get(i));
						list.add(map);
					}
				}
			}
			return list == null ? null : list;
		} catch (Exception e) {
			log.error(" 根据用户名获得用户的剩余时间异常", e);
			return null;
		}
	}

	/**
	 * @Description  查找用户余额
	 * @date 2016年12月5日下午4:12:07
	 * @author guoyingjie
	 * @param userName
	 * @return
	 */
	public UserBalance getUserBalanceByName(String userName){
		UserBalance userBalance = null;
		try {
			userBalance = nutDao.fetch(UserBalance.class,Cnd.where("user_name","=",userName));
		} catch (Exception e) {
			log.error("获得用余额失败");
		}
	     return userBalance;
	}
	
	/**
	 * @Description 根据username获得场所信息
	 * @param username
	 */
	public List<Map<String, Object>> getSiteListInfo(String username) {
		String sql = "SELECT id siteId, site_name siteName FROM t_cloud_site WHERE id in (SELECT site_id FROM t_cloud_site_portal WHERE portal_id=(SELECT id FROM t_portal_user WHERE user_name=?));";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,
				new Object[] { username });
		return list;
	}
	/**
	 * @Description 获得场所下的全部的消费配置
	 * @param siteId
	 * @param portalId
	 * @return
	 */
	public SitePriceConfigAll getSitePriceConfigAll(int siteId, int portalId) {
		SitePriceConfigAll siteAll = new SitePriceConfigAll();
		try {
			PortalUser portal = getPortalUserById(portalId);
			// 根据场所id获得场所信息
			CloudSite site = findCloudSite(siteId);
			List<SitePriceConfig> spcList = new ArrayList<SitePriceConfig>();
			List<Map<String, Object>> listMeal=getMealList(siteId);
			if( listMeal.size()!=0){
				for (int i = 0; i < listMeal.size(); i++) {
					boolean flag=false;
					if(listMeal.get(i).get("comboNumber")!=null&&!(listMeal.get(i).get("comboNumber")+"").equals("")){
						for (int j = 0; j < (listMeal.get(i).get("comboNumber")+"").split(";").length; j++) {
							if(portal.getUserName().indexOf((listMeal.get(i).get("comboNumber")+"").split(";")[j])==0){
								SitePriceConfig spc=new SitePriceConfig();
								spc.setCharge_type(Integer.valueOf((listMeal.get(i).get("charge_type")+"").split(",")[1]));
								spc.setDescribe(listMeal.get(i).get("v2_describe")+"");
								spc.setGiveMeal(Integer.valueOf(listMeal.get(i).get("v2_give_meal")+""));
								spc.setGiveMealUnit(Integer.valueOf(listMeal.get(i).get("v2_givemeal_unit")+""));
								spc.setId(Integer.valueOf((listMeal.get(i).get("ids")+"").split(",")[1]));
								spc.setName(listMeal.get(i).get("name")+"");
								spc.setPrice_num(Integer.valueOf(listMeal.get(i).get("price_num")+""));
								spc.setPrice_type(Integer.valueOf(listMeal.get(i).get("price_type")+""));
								spc.setRecommendState(Integer.valueOf(listMeal.get(i).get("v2_recommend_state")+""));
								spc.setUnit_price(new BigDecimal((listMeal.get(i).get("price")+"").split(",")[1]));
								spc.setSite_id(siteId);
								spcList.add(spc);
								flag=false;
								break;
							}else{
								flag=true;
							}
						}
						if(flag){
							SitePriceConfig spc=new SitePriceConfig();
							spc.setCharge_type(Integer.valueOf((listMeal.get(i).get("charge_type")+"").split(",")[0]));
							spc.setDescribe(listMeal.get(i).get("v2_describe")+"");
							spc.setGiveMeal(Integer.valueOf(listMeal.get(i).get("v2_give_meal")+""));
							spc.setGiveMealUnit(Integer.valueOf(listMeal.get(i).get("v2_givemeal_unit")+""));
							spc.setId(Integer.valueOf((listMeal.get(i).get("ids")+"").split(",")[0]));
							spc.setName(listMeal.get(i).get("name")+"");
							spc.setPrice_num(Integer.valueOf(listMeal.get(i).get("price_num")+""));
							spc.setPrice_type(Integer.valueOf(listMeal.get(i).get("price_type")+""));
							spc.setRecommendState(Integer.valueOf(listMeal.get(i).get("v2_recommend_state")+""));
							spc.setUnit_price(new BigDecimal((listMeal.get(i).get("price")+"").split(",")[0]));
							spc.setSite_id(siteId);
							spcList.add(spc);
					  }
					}else{
						SitePriceConfig spc=new SitePriceConfig();
						spc.setCharge_type(Integer.valueOf(listMeal.get(i).get("charge_type")+""));
						spc.setDescribe(listMeal.get(i).get("v2_describe")+"");
						spc.setGiveMeal(Integer.valueOf(listMeal.get(i).get("v2_give_meal")+""));
						spc.setGiveMealUnit(Integer.valueOf(listMeal.get(i).get("v2_givemeal_unit")+""));
						spc.setId(Integer.valueOf(listMeal.get(i).get("ids")+""));
						spc.setName(listMeal.get(i).get("name")+"");
						spc.setPrice_num(Integer.valueOf(listMeal.get(i).get("price_num")+""));
						spc.setPrice_type(Integer.valueOf(listMeal.get(i).get("price_type")+""));
						spc.setRecommendState(Integer.valueOf(listMeal.get(i).get("v2_recommend_state")+""));
						spc.setUnit_price(new BigDecimal(listMeal.get(i).get("price")+""));
						spc.setSite_id(siteId);
						spcList.add(spc);
					}
					
				}
			}
			siteAll.setSiteInof(site);
			siteAll.setList(spcList);
			// 根据路由器Mac设置该路由所隶属场所的价格信息到seesion中，用以将价格信息传递到缴费页面
			//siteAll = setPriceToSession(site, chargeType);
		} catch (Exception e) {
			log.error(this.getClass().getCanonicalName()
					+ "===getSitePriceConfigAll", e);
			return null;
		}
		return siteAll;

	}

	/**
	 * 根据tradeNum校验支付是否完成
	 * 
	 * @param out_trade_no
	 * @param session
	 * @return
	 */
	public boolean checkPayResult(@RequestParam String outTradeNo) {
		SitePaymentRecord spr = nutDao.fetch(SitePaymentRecord.class,
				Cnd.where("order_num", "=", outTradeNo));

		if (spr != null && (spr.getIsFinish() == 1 || spr.getIsFinish() == -1)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @Description 更新到期时间表,锁定时间
	 * @param siteinfo
	 */
	public void updateSiteCustomerInfo(SiteCustomerInfo siteinfo) {
		try {
			siteinfo.setExpirationTime(siteinfo.getExpirationTime());
			siteinfo.setLuckTime(new Date());
			siteinfo.setSiteId(siteinfo.getSiteId());
			siteinfo.setPortalUserId(siteinfo.getPortalUserId());
			nutDao.update(siteinfo);
		} catch (Exception e) {
			log.error(this.getClass().getCanonicalName() + "==updateSiteCustomerInfo");
		}
	}
 
	/**
	 * @Description 更新对象的方法
	 * @param obj
	 */
	public int updateObject(SiteCustomerInfo obj) {
		try {
			return nutDao.update(obj);
		} catch (Exception e) {
			log.error(this.getClass().getCanonicalName() + "==updateObject");
			return 0;
		}
	}
	
	
	/**
	 * @Description 获得用户最近一次的缴费记录
	 * @date 2016年5月26日下午12:55:09
	 * @author guoyingjie
	 * @param siteId
	 * @param userId
	 * @return
	 */
	public SitePaymentRecord getSitePaymentRecord(int siteId, int userId) {
		SitePaymentRecord record = null;
		try {
			String sql = "SELECT  * FROM t_sitepayment_records WHERE user_id =? AND site_id =? AND is_finish !=0 AND param_json like '%expireDate%'   ORDER BY create_time DESC LIMIT 0,1";
			List<SitePaymentRecord> records = jdbcTemplate.query(sql, new Object[] { userId,siteId }, new BeanPropertyRowMapper(SitePaymentRecord.class));
			if(records!=null&&records.size()>0){
				record = records.get(0);
			}
		} catch (Exception e) {
			log.error("获得最近一次的缴费记录失败", e);
		}
		return record;
	}
	 
	/**
	 * @Description 获得用户到期时间信息
	 * @date 2016年5月26日下午12:55:09
	 * @author guoyingjie
	 * @param siteId
	 * @param userId
	 * @return
	 */
	public SiteCustomerInfo getSiteCustomerInfo(int siteId, int userId) {
		SiteCustomerInfo info = null;
		try {
			info = nutDao.fetch(SiteCustomerInfo.class,Cnd.where("site_id", "=", siteId).
					and("portal_user_id","=", userId));
		} catch (Exception e) {
			log.error("获得用户到期时间信息失败", e);
		}
		return info;
	}

	/**
	 * @Description 获得用户的总的购买时长与购买流量
	 * @date 2016年5月26日下午1:16:02
	 * @author guoyingjie
	 * @param siteId
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getSiteIncomeRecord(int siteId, int userId) {
		SiteCustomerInfo info = getSiteCustomerInfo(siteId, userId);
		SitePaymentRecord record = getSitePaymentRecord(siteId, userId);
		Map<String, Object> map = null;
		try {
			map = new HashMap<String, Object>();
			if(info!=null){
				Date exDate = info.getExpirationTime();// 到期时间
				if(exDate==null){
					map.put("time", "0小时");
					map.put("alltime", "0");
				}else{
					if (exDate.getTime() - new Date().getTime() <= 0) {
						map.put("time", "0小时");
						map.put("alltime", "0");
					} else {
						// 当没有缴费记录的时候,默认他首次充费的时间
						Timestamp time = info.getCreateTime();
						if (record != null) {
							time = record.getCreateTime();// 最新创建时间
						}
						long betw = exDate.getTime() - time.getTime();
						map.put("time", timeBetween((betw), 2));
						map.put("alltime", betw + "");
					}
				}
				String tFlow = info.getLastFlow();
				if (null == tFlow||"0".equals(tFlow)) {
					map.put("flow", "0");
					map.put("flowstr", "0M");
				} else {
					map.put("flow", tFlow);
					map.put("flowstr", kbChangeGM(tFlow));
				}
			 }else{
				 map.put("flow", "0");
				 map.put("flowstr", "0M");
				 map.put("time", "0小时");
				 map.put("alltime", "0");
			  }
		} catch (Exception e) {
			log.error(this.getClass().getCanonicalName() + "暂无缴费记录,无法获得总时间与总流量");
		}
		return map;
	}

	/**
	 * @Description 计算两个时间相差几年几小时几分钟
	 * @param diff
	 *            获得两个时间的毫秒时间差
	 * @return
	 * @throws ParseException
	 */
	public static String timeBetween(long diff, int code) throws ParseException {
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long day = diff / nd;// 计算差多少天
		long hour = diff % nd / nh;// 计算差多少小时
		long min = diff % nd % nh / nm;// 计算差多少分钟
		long sec = diff % nd % nh % nm / ns;// 计算差多少秒//输出结果
		if (code == 1) {
			return day + "天" + hour + "小时" + min + "分钟";
		}
		if (code == 2) {

			if (diff <= nh && diff >= 57 * nm) {
				return "1小时";
			}
			if (day > 0) {
				return day + "天" + hour + "小时";
			}
			if (day <= 0 && hour > 0) {
				if(min==0){
					return hour + "小时" ;
				}else{
					return hour + "小时" + min + "分钟";
				}
			}
			if (hour <= 0 && day <= 0) {
				return min + "分钟";
			}
		}
		return null;
	}

	/**
	 * @Description 获得剩余时间或者流量
	 * @param siteId
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getSYtimeOrflow(int siteId, int userId) {
		Date date = new Date();// 系统当前的时间
		Map<String, Object> map = null;
			try {
				map = new HashMap<String, Object>();
				SiteCustomerInfo info = getSiteCustomerInfo(siteId, userId);
			    if(info!=null){
					Date exDate = info.getExpirationTime();// 用户到期时间
					if(exDate==null){
						map.put("time", "0小时");
						map.put("alltime", "0");
					}else{
						if (date.getTime() - exDate.getTime() >= 0) {
							map.put("time", "0小时");
							map.put("alltime", "0");
						} else {
							map.put("time",
									timeBetween(exDate.getTime() - date.getTime(), 2));
							map.put("alltime", (exDate.getTime() - date.getTime()) + "");
						}
					}
					if (info.getTotalFlow() != null &&!"0".equals(info.getTotalFlow())) {
						String uFlow = info.getUsedFlow();
						if (uFlow == null || "0".equals(uFlow)) {
							uFlow = "0";
							map.put("flowstr", "0M");
						}
						BigDecimal gFlow = BigDecimalUtil.subtract(
								new BigDecimal(info.getTotalFlow()), new BigDecimal(
										uFlow));
						if (gFlow.compareTo(new BigDecimal(0.0000)) == 1) {
							map.put("flow", gFlow.toString());
							map.put("flowstr", kbChangeGM(gFlow.toString()));
						} else {
							map.put("flow", "0");
							map.put("flowstr", "0M");
						}
					} else {
						map.put("flow", "0");
						map.put("flowstr", "0M");
					}
			    }else{
					map.put("time", "0小时");
					map.put("alltime", "0");
					map.put("flow", "0");
					map.put("flowstr", "0M");
			    }
		} catch (Exception e) {
			log.error(this.getClass().getCanonicalName() + "==getSYtimeOrflow",e);
		}
		return map;
	}

	/**
	 * @Description 获得时间或者流量占用总流量的百分比
	 * @param total
	 *            总时间或者总流量的集合
	 * @param single
	 *            剩余时间或者流量的集合
	 * @return
	 */
	public static Map<String, Object> getTyleBili(Map<String, Object> total,
			Map<String, Object> single) {
		double all = Double.parseDouble(total.get("alltime") + "");
		double self = Double.parseDouble(single.get("alltime") + "");
		double aFlow = Double.parseDouble(total.get("flow") + "");
		double sFlow = Double.parseDouble(single.get("flow") + "");
		DecimalFormat df = new DecimalFormat("######0.0000");
		Map<String, Object> map = new HashMap<String, Object>();
		if (all <= 0 || self <= 0) {
			map.put("ratioTime", 0);
		} else {
			map.put("ratioTime", df.format(self / all));
		}
		if (aFlow == 0 || sFlow == 0) {
			map.put("ratioFlow", 0);
		} else {
			map.put("ratioFlow", df.format(sFlow / aFlow));
		}
		return map;
	}

	 /**
	 * @Description 将字符串转化为?G?M的格式
	 * @date 2016年5月25日上午10:32:34
	 * @author guoyingjie
	 * @param kbstr
	 * @return
	 */
	public static String kbChangeGM(String kbstr) {
		DecimalFormat df = new DecimalFormat("######0.00");
		DecimalFormat dfs = new DecimalFormat("######0");
		if (kbstr != null && !"".equals(kbstr)) {
			double kb = Double.valueOf(kbstr);
			if (kb == 1024) {
				return "1M";
			}
			if (kb < 1024) { 
				return df.format(kb / 1024) + "M";
			}
			if (kb >= 1024 && kb < 1024 * 1024) {
				return df.format(kb / 1024) + "M";
			}
			if(kb== 1024*1024){
				return "1G";
			}
			if (kb > 1024 * 1024) {
				String gb = Double.toString(kb / (1024 * 1024));
				String d[] = gb.split("\\.");
				String M = dfs.format(Double.valueOf(("0." + d[1])) * 1024);
				if("0".equals(M)){
					return d[0] + "G";
				}
				return d[0] + "G" + M + "M";
			}
		}
		return "0M";
	}

	
	
	/**
	 * 试用用户在注册时没有给试用时间,但是后来开启了试用.
	 * 
	 * @param expireDate
	 *            到期时间
	 * @param siteId
	 *            场所id
	 * @param userId
	 *            用户id
	 * @return 1成功
	 */
	public void insertExpireDate(String expireDate, int siteId, int userId,SiteCustomerInfo scis) {
		try {
			if (scis == null) {
				String sql = "INSERT INTO t_site_customer_info("
						+ "expiration_time,site_id,portal_user_id,update_time,create_time,total_flow,used_flow,pay_way,lock_time,is_try,last_flow) "
						+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
				jdbcTemplate.update(sql,new Object[]{
						expireDate,siteId,userId,new Date(),new Date(),"0","0",0,null,0,"0"
				});
			} else {
				scis.setExpirationTime(expireDate);
				scis.setTotalFlow(scis.getTotalFlow());
				scis.setUsedFlow(scis.getUsedFlow());
				scis.setSiteId(siteId);
				scis.setPortalUserId(userId);
				scis.setIsTry(0);
				scis.setUpdateTime(new Timestamp(new Date().getTime()));
				nutDao.update(scis);
			}
		} catch (Exception e) {
			log.error("试用用户在注册时没有给试用时间,但是后来开启了试用失败");
		}
		
	}

	/**
	 * 保存用户支付的记录信息
	 * 
	 * @param orderNum
	 * @param map
	 * @return 0失败。1成功
	 */
	public void savePaymentinfo(int siteId, int userId) {
		try {
			SitePaymentRecord paymentRecord = new SitePaymentRecord();
			paymentRecord.setOrderNum(RandomStringGenerator.getRandomStringByLength(32));
			paymentRecord.setUserId(userId);
			paymentRecord.setSiteId(siteId);
			paymentRecord.setParamJson("2");
			paymentRecord.setTradeNum("");
			paymentRecord.setFailReason("");
			paymentRecord.setIsFinish(1);
			paymentRecord.setPayType(0);
			paymentRecord.setFinishTime(new Date());
			paymentRecord.setCreateTime(new Timestamp(new Date().getTime()));
			nutDao.insert(paymentRecord);
		} catch (Exception e) {
			log.error("保存支付记录失败",e);
		}
	}

	/**
	 * @Description 获得当前用户的信息的个数
	 * @date 2016年5月24日下午2:41:58
	 * @author guoyingjie
	 * @param siteId
	 * @param userId
	 */
	public int getMessageCount(int userId) {
		String sql = "SELECT COUNT(id) FROM t2_self_user_message WHERE state_read = 2 or state_delete = 2 and user_id=?";
		try {
			int cont = jdbcTemplate.queryForInt(sql, new Object[] { userId });
			return cont;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * @Description 获得用户总的缴费金额,以便给用户添加积分
	 * @date 2016年5月25日下午5:27:39
	 * @author guoyingjie
	 * @param userName
	 * @param siteId
	 */
	public void perfectInfo(String userName, int siteId) {
		try {
			SiteIntegralGrade sil = getSiteIntegralGrade(siteId);
			if (userName != null && !"".equals(userName)) {
				PortalUser user = getPortalUserByTel(userName);
				Timestamp time = user.getEndTime();
				if (time == null || "".equals(time)) {
					time = new Timestamp(new Date().getTime() - 24 * 60 * 60
							* 1000);
				}
				String sql = "SELECT SUM(transaction_amount) FROM t_site_income WHERE portal_user_id = ? AND site_id = ? AND create_time > ?";
				int count = jdbcTemplate.queryForInt(sql,
						new Object[] { user.getId(), siteId, time });
				if (count == 0) {
					return;
				}
				int experience = user.getUserExperience();//经验
				int integral = user.getUserIntegral();//积分
				int pexperience = 100;//经验值达到100后升级1等级
				if (sil != null) {
					pexperience = sil.getSite_experience();
				}
				if (experience - pexperience > 0) {
					user.setUserGrade(user.getUserGrade()//等级
							+ (experience / pexperience));
					user.setUserExperience(experience % pexperience + count);
					user.setEndTime(new Timestamp(new Date().getTime()));
				} else {
					user.setUserExperience(experience + count);
					user.setEndTime(time);
				}
				user.setUserIntegral(integral + count*10);
				user.setUserName(userName);
				user.setPassWord(user.getPassWord());
				userUpdata(user);
			}
		} catch (Exception e) {
			log.error(this.getClass().getCanonicalName()
					+ "--获得用户总的缴费金额,以便给用户添加积分失败");
		}
	}

	/**
	 * @Description 获得场所配置
	 * @date 2016年5月25日下午6:56:13
	 * @author guoyingjie
	 * @param siteId
	 */
	public SiteIntegralGrade getSiteIntegralGrade(int siteId) {
		SiteIntegralGrade grade = null;
		try {
			nutDao.fetch(SiteIntegralGrade.class,
					Cnd.where("site_id", "=", siteId));
		} catch (Exception e) {
			log.error("获得场所配置失败");
		}
		return grade;
	}

	/**
	 * 查询缴费记录 by:cuimiao
	 * 
	 * @Description
	 * @param telphone
	 *            用户电话号，即用户id
	 * @param site_id
	 *            场所id
	 * @param pageIndex
	 *            页码，起始页为0页
	 * @param pageNum
	 *            每页显示数据条数
	 * @return
	 */
	public List<Map<String, Object>> getPayRecord(String userId, String siteId,
			String pageIndex, String pageNum) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  pay_name,create_time,transaction_amount, ");
		sql.append(" (case pay_type ");
		sql.append(" when 0 then '未知' ");
		sql.append(" when 1 then 'alipay' ");
		sql.append(" when 2 then 'card' ");
		sql.append(" when 3 then 'weiX' ");
		sql.append(" when 4 then 'man' ");
		sql.append(" end) as pay_type_name");
		sql.append(" from t_site_income");
		sql.append(" where  portal_user_id = ?");
		sql.append(" and site_id = ?");
		sql.append(" order by create_time desc");
		sql.append(" limit ?,?");
		// 当前应该从第pageStartNum条数据开始查询
		String pageStartNum = (Integer.parseInt(pageIndex) * Integer
				.parseInt(pageNum)) + "";
		// 应该查询至第pageEndNum
		String pageEndNum = (Integer.parseInt(pageStartNum) + Integer
				.parseInt(pageNum)) + "";
		List<Map<String, Object>> list = null;
		try {
			list = jdbcTemplate.queryForList(
					sql.toString(),
					new Object[] { userId, siteId,
							Integer.parseInt(pageStartNum),
							Integer.parseInt(pageEndNum) });
		} catch (Exception e) {
			log.error(this.getClass().getCanonicalName() + "--获得缴费记录失败", e);
		}
		return list;
	}

	/**
	 * 查询缴费记录pc端 by:cuimiao
	 * 
	 * @Description
	 * @param telphone
	 *            用户电话号，即用户id
	 * @param site_id
	 *            场所id
	 * @param pageIndex
	 *            页码，起始页为0页
	 * @param pageNum
	 *            每页显示数据条数
	 * @return
	 */
	public List<Map<String, Object>> getPayRecord(String userId, String siteId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select create_time,transaction_amount,pay_name, ");
		sql.append(" (case pay_type ");
		sql.append(" when 0 then '未知' ");
		sql.append(" when 1 then 'alipay' ");
		sql.append(" when 2 then 'card' ");
		sql.append(" when 3 then 'weiX' ");
		sql.append(" when 4 then 'man' ");
		sql.append(" end) as pay_type_name");
		sql.append(" from t_site_income");
		sql.append(" where  portal_user_id = ?");
		sql.append(" and site_id = ?");
		sql.append(" order by create_time desc");
		List<Map<String, Object>> list = null;
		try {
			list = jdbcTemplate.queryForList(sql.toString(), new Object[] {
					userId, siteId });
		} catch (Exception e) {
			log.error(this.getClass().getCanonicalName() + "--获得缴费记录失败", e);
		}
		return list;
	}

	/**
	 * 通过payType获取payName by:cuimiao
	 * 
	 * @Description
	 * @param payType
	 * @return
	 */
	public String getPayName(String payType) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select name from t_site_price_config where id = ?");
		String payName = "";
		try {
			payName = jdbcTemplate.queryForObject(sql.toString(), String.class,
					new Object[] { payType });
		} catch (Exception e) {
			payName = "未知类型";
		}

		return payName;
	}

	/**
	 * @Description 获得缴费记录条数
	 * @date 2016年5月26日上午9:30:59
	 * @author guoyingjie
	 * @param siteId
	 * @param userId
	 * @return
	 */
	public int getRecordCount(int siteId, int userId) {
		int count = 0;
		try {
			String sql = "select count(*) from t_site_income where portal_user_id = ? and site_id = ?";
			count = jdbcTemplate.queryForInt(sql,
					new Object[] { userId, siteId });
		} catch (Exception e) {
			log.error(this.getClass().getCanonicalName() + "获得缴费条数", e);
		}
		return count;
	}

	/**
	 * @Description 校验用户密码跟原始密码是否一致
	 * @date 2016年5月26日下午3:45:36
	 * @author guoyingjie
	 * @param username
	 * @param password
	 */
	public boolean checkUserPsd(PortalUser user, String password) {
		String userps = SHA256.getUserPassword(user.getUserName(),
				MD5.encode(password.trim()).toLowerCase());
		if (userps.equals(user.getPassWord())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @Description 修改密码与修改昵称
	 * @date 2016年5月26日下午4:13:24
	 * @author guoyingjie
	 * @param user
	 * @param password
	 * @return
	 */
	public boolean changePsForUser(PortalUser user, String password,
			String nikename) {
		if (nikename == null || "".equals(nikename)) {
			String userps = SHA256.getUserPassword(user.getUserName(), MD5
					.encode(password.trim()).toLowerCase());
			user.setPassWord(userps);
			user.setUserName(user.getUserName());
		} else {
			user.setUserName(user.getUserName());
			user.setPassWord(user.getPassWord());
			user.setUserNickname(nikename);
			user.setImageUrl(user.getImageUrl());
		}
		try {
			nutDao.update(user);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @Description 忘记密码情况下的修改密码(PC)
	 * @date 2016年5月26日下午4:13:24
	 * @author cuimiao
	 * @param user
	 * @param password
	 * @return
	 */
	public boolean changePsForUser(String username, String password) {
		String userps = SHA256.getUserPassword(username,
				MD5.encode(password.trim()).toLowerCase());
		String sql = "update t_portal_user set pass_word = ? where user_name = ?";
		int i = jdbcTemplate.update(sql, new Object[] { userps, username });
		if (i > 0) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * @Description 检查用户昵称是否有重复
	 * @date 2016年5月27日上午11:29:09
	 * @author guoyingjie
	 * @param userName
	 */
	public boolean checkUserName(String userName) {
		boolean flag = false;
		String sql = "select user_nickname from t_portal_user";
		List<String> usernames = jdbcTemplate.queryForList(sql, String.class);
		if (usernames.size() > 0 && usernames != null) {
			if (usernames.contains(userName.trim())) {
				flag = true;
			} else {
				flag = false;
			}
		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * 用户在个人中心时,点击切换账号时,先修改v1_router_device表中用户登录的信息，让wifidog把该用户踢掉
	 * 
	 * @param userMac
	 * @param userId
	 * @return
	 */
	public boolean updateUserLoginState(String userMac, int userId) {
		try {
			String errorToken = System.currentTimeMillis() + "0000";
			String sql = "UPDATE v1_router_device SET token=? WHERE portal_user_id=? AND device_mac=?";
			templJdbcTemplate.update(sql, new Object[] { errorToken, userId,
					userMac });
			return true;
		} catch (Exception e) {
			log.error("修改token值永用来切换账号------", e);
			return false;
		}
	}

	/**
	 * 用户点击个人中心时执行的方法,查询用户最新信息
	 * 
	 * @param siteId
	 *            场所id
	 * @param userId
	 *            用户id
	 */
	public void getUserMessage(int siteId, int userId) {
		try {

			// 查询总信息表里的有效信息
			String sql = "SELECT * FROM t2_site_user_message WHERE site_id=? AND state=0 AND DATE_SUB(CURDATE(),INTERVAL 5 DAY)<=DATE(create_time) OR user_id=? AND state=0 AND DATE_SUB(CURDATE(),INTERVAL 5 DAY)<=DATE(create_time)";
			List<Map<String, Object>> allMessageList = jdbcTemplate
					.queryForList(sql, new Object[] { siteId, userId });
			// 查询个人信息表中未读和未删除的信心记录
			String sql1 = "SELECT * FROM t2_self_user_message WHERE user_id =?";
			List<Map<String, Object>> userMessageList = jdbcTemplate
					.queryForList(sql1, new Object[] { userId });
			int messageId = 0;
			String sql2 = "INSERT INTO t2_self_user_message (user_id,message_id,create_time)VALUES(?,?,?)";
			// 如果用户个人信息表里没有数据，并且总库里也有信息，把总库信息关联到个人信息表里
			if (userMessageList.size() == 0 && allMessageList.size() > 0) {
				for (int i = 0; i < allMessageList.size(); i++) {
					messageId = (int) allMessageList.get(i).get("id");
					jdbcTemplate.update(sql2, new Object[] { userId, messageId,
							new Date() });
				}
				// 如果总库有信息，个人信息表里也有信息，那么对比重复的信息，把在总库中新的消息关联到个人信息表里
			} else if (userMessageList.size() != 0 && allMessageList.size() > 0) {
				for (int j = 0; j < userMessageList.size(); j++) {
					for (int i = 0; i < allMessageList.size(); i++) {
						if ((int) userMessageList.get(j).get("message_id") == (int) allMessageList
								.get(i).get("id")) {
							allMessageList.remove(i);
							break;

						}
					}
				}
				if (allMessageList.size() != 0) {

					for (int i = 0; i < allMessageList.size(); i++) {
						messageId = (int) allMessageList.get(i).get("id");
						jdbcTemplate.update(sql2, new Object[] { userId,
								messageId, new Date() });
					}
				}
			}
		} catch (Exception e) {
			log.error("查询用户信息时出错", e);
		}

	}

	/**
	 * 用户点击个人中心时执行的方法,获取用户最新礼包
	 * 
	 * @param siteId
	 *            场所id
	 * @param userId
	 *            用户id
	 * @return
	 */
	public void getUserGift(int siteId, int userId) {
		try {

			// 获得总信息库里信息
			String sql = "SELECT * FROM t2_site_user_message WHERE site_id=? AND state=2 AND DATE_SUB(CURDATE(),INTERVAL 5 DAY)<=DATE(create_time) OR user_id=? AND state=2 AND DATE_SUB(CURDATE(),INTERVAL 5 DAY)<=DATE(create_time)";
			List<Map<String, Object>> allGiftList = jdbcTemplate.queryForList(
					sql, new Object[] { siteId, userId });
			// 获取用户个人信息
			String sql1 = "SELECT * FROM t2_self_user_message WHERE user_id =?";
			List<Map<String, Object>> userGiftList = jdbcTemplate.queryForList(
					sql1, new Object[] { userId });
			String sql2 = "INSERT INTO t2_self_user_message (user_id,message_id,create_time)VALUES(?,?,?)";
			int messageId = 0;
			// 如果用户个人信息表里没有数据，并且总库里也有信息，把总库信息关联到个人信息表里
			if (userGiftList.size() == 0 && allGiftList.size() > 0) {
				for (int i = 0; i < allGiftList.size(); i++) {
					messageId = (int) allGiftList.get(i).get("id");
					jdbcTemplate.update(sql2, new Object[] { userId, messageId,
							new Date() });
				}
				// 如果总库有信息，个人信息表里也有信息，那么对比重复的信息，把在总库中新的消息关联到个人信息表里
			} else if (userGiftList.size() != 0 && allGiftList.size() > 0) {
				for (int j = 0; j < userGiftList.size(); j++) {
					for (int i = 0; i < allGiftList.size(); i++) {
						if ((int) userGiftList.get(j).get("message_id") == (int) allGiftList
								.get(i).get("id")) {
							allGiftList.remove(i);
							break;

						}
					}
				}
				if (allGiftList.size() != 0) {

					for (int i = 0; i < allGiftList.size(); i++) {
						messageId = (int) allGiftList.get(i).get("id");
						jdbcTemplate.update(sql2, new Object[] { userId,
								messageId, new Date() });
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询用户礼包时出错", e);
		}

	}

	/**
	 * 用户点击个人中心时执行的方法,获取用户的彩票
	 * 
	 * @param siteId
	 *            场所id
	 * @param userId
	 *            用户id
	 * @return
	 */
	public void getUserLottery(int siteId, int userId) {

		try {

			// 获得总信息库里信息
			String sql = "SELECT * FROM t2_site_user_message WHERE site_id=? AND state=1  AND DATE_SUB(CURDATE(),INTERVAL 5 DAY)<=DATE(create_time) OR user_id=? AND state=1 AND DATE_SUB(CURDATE(),INTERVAL 5 DAY)<=DATE(create_time)";
			List<Map<String, Object>> allTicketList = jdbcTemplate
					.queryForList(sql, new Object[] { siteId, userId });
			// 获取用户个人信息
			String sql1 = "SELECT * FROM t2_self_user_message WHERE user_id =?";
			List<Map<String, Object>> userTicketList = jdbcTemplate
					.queryForList(sql1, new Object[] { userId });
			String sql2 = "INSERT INTO t2_self_user_message (user_id,message_id,create_time)VALUES(?,?,?)";
			int messageId = 0;
			// 如果用户个人信息表里没有数据，并且总库里也有信息，把总库信息关联到个人信息表里
			if (userTicketList.size() == 0 && allTicketList.size() > 0) {
				for (int i = 0; i < allTicketList.size(); i++) {
					messageId = (int) allTicketList.get(i).get("id");
					jdbcTemplate.update(sql2, new Object[] { userId, messageId,
							new Date() });
				}
				// 如果总库有信息，个人信息表里也有信息，那么对比重复的信息，把在总库中新的消息关联到个人信息表里
			} else if (userTicketList.size() != 0 && allTicketList.size() > 0) {
				for (int j = 0; j < userTicketList.size(); j++) {
					for (int i = 0; i < allTicketList.size(); i++) {
						if ((int) userTicketList.get(j).get("message_id") == (int) allTicketList
								.get(i).get("id")) {
							allTicketList.remove(i);
							break;

						}
					}
				}
				if (allTicketList.size() != 0) {

					for (int i = 0; i < allTicketList.size(); i++) {
						messageId = (int) allTicketList.get(i).get("id");
						jdbcTemplate.update(sql2, new Object[] { userId,
								messageId, new Date() });

					}
				}
			}

		} catch (Exception e) {
			log.error("查询用户彩票时出错", e);
		}

	}

	/**
	 * 用户点击个人中心时执行的方法,获取用户的彩票信息
	 * 
	 * @param siteId
	 *            场所id
	 * @param userId
	 *            用户id
	 * @return
	 */
	public void getMessageLottery(int siteId, int userId) {

		try {

			// 获得总信息库里信息
			String sql = "SELECT * FROM t2_site_user_message WHERE  user_id=? AND state=3 ";
			List<Map<String, Object>> allTicketList = jdbcTemplate
					.queryForList(sql, new Object[] { userId });
			// 获取用户个人信息
			String sql1 = "SELECT * FROM t2_self_user_message WHERE user_id =?";
			List<Map<String, Object>> userTicketList = jdbcTemplate
					.queryForList(sql1, new Object[] { userId });
			String sql2 = "INSERT INTO t2_self_user_message (user_id,message_id,create_time)VALUES(?,?,?)";
			int messageId = 0;
			// 如果用户个人信息表里没有数据，并且总库里也有信息，把总库信息关联到个人信息表里
			if (userTicketList.size() == 0 && allTicketList.size() > 0) {
				for (int i = 0; i < allTicketList.size(); i++) {
					messageId = (int) allTicketList.get(i).get("id");
					jdbcTemplate.update(sql2, new Object[] { userId, messageId,
							new Date() });
				}
				// 如果总库有信息，个人信息表里也有信息，那么对比重复的信息，把在总库中新的消息关联到个人信息表里
			} else if (userTicketList.size() != 0 && allTicketList.size() > 0) {
				for (int j = 0; j < userTicketList.size(); j++) {
					for (int i = 0; i < allTicketList.size(); i++) {
						if ((int) userTicketList.get(j).get("message_id") == (int) allTicketList
								.get(i).get("id")) {
							allTicketList.remove(i);
							break;

						}
					}
				}
				if (allTicketList.size() != 0) {

					for (int i = 0; i < allTicketList.size(); i++) {
						messageId = (int) allTicketList.get(i).get("id");
						jdbcTemplate.update(sql2, new Object[] { userId,
								messageId, new Date() });

					}
				}
			}

		} catch (Exception e) {
			log.error("查询用户彩票时出错", e);
		}

	}

	
	/**
	 * 用户在个人中心点击信息时，查询用户接受信息
	 * 
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getmessage(int userId, int mealType) {
		String sql = "";
		List<Map<String, Object>> mesList = null;
		try {
			if (mealType == 0) {
//				sql = "SELECT id,content,create_time,state FROM t2_site_user_message WHERE state IN (0,3) "
//						//+ mealType
//						+ " AND id IN(SELECT message_id FROM t2_self_user_message WHERE user_id=? AND state_read=2 AND state_delete=2)";
				sql="SELECT sitem.id sitemId,sitem.content,sitem.create_time,sitem.state,userm.id usermId FROM t2_site_user_message sitem "+
									" LEFT JOIN t2_self_user_message userm ON sitem.state IN(0,3) AND sitem.id =userm.message_id WHERE  userm.user_id=? AND  userm.state_read=2 AND  userm.state_delete=2";
			
			} else if (mealType == 1) {
				sql = "SELECT id,content,create_time FROM t2_site_user_message WHERE state="
						+ mealType
						+ " AND id IN(SELECT message_id FROM t2_self_user_message WHERE user_id=? AND state_read=2 AND state_delete=2)";
			} else {
				sql = "SELECT id,content,create_time FROM t2_site_user_message WHERE state="
						+ mealType
						+ " AND id IN(SELECT message_id FROM t2_self_user_message WHERE user_id=? AND state_read=2 AND state_delete=2)";
			}
			mesList = jdbcTemplate.queryForList(sql, new Object[] { userId });
		} catch (Exception e) {
			log.error("用户在个人中心点击信息时，查询用户接受信息错误");
		}
		return mesList;
	}

	/**
	 * 用户点击进入个人中心后,显示消息数量
	 * 
	 * @param userId
	 * @return
	 */
	public int getMessageNumber(int userId) {
		int number = 0;
		try {

			String sql = "SELECT COUNT(u.id) FROM t2_site_user_message s INNER JOIN t2_self_user_message u WHERE s.id=u.message_id AND s.state=0 AND DATE_SUB(CURDATE(),INTERVAL 5 DAY)<=DATE(s.create_time) AND u.state_read=2 AND u.state_delete=2 AND u.user_id=?";
			String sql1="SELECT count(*) FROM t2_self_user_message WHERE state_read=2 AND state_delete = 2 AND DATE_SUB(CURDATE(),INTERVAL 5 DAY)<=DATE(create_time) AND user_id=?";
			String sql2="SELECT COUNT(*) FROM  t2_site_user_message WHERE state IN (0,3) AND id NOT IN(SELECT message_id FROM t2_self_user_message) AND user_id=?";
			number = jdbcTemplate.queryForInt(sql1, new Object[] { userId });
			if(number==0){
				number= jdbcTemplate.queryForInt(sql2, new Object[] { userId });
			}

		} catch (Exception e) {
			log.error("查询用户信息总数时出错", e);
		}
		return number;
	}

	/**
	 * 用户点击进入个人中心时,显示的彩票信息数量
	 * 
	 * @param userId
	 * @return
	 */
	public int getLotteryNumber(int userId) {
		int number = 0;
		try {
			String sql = "SELECT COUNT(u.id) FROM t2_site_user_message s INNER JOIN t2_self_user_message u WHERE s.id=u.message_id AND s.state=1 AND DATE_SUB(CURDATE(),INTERVAL 5 DAY)<=DATE(s.create_time) AND u.state_read=2 AND u.state_delete=2 AND u.user_id=?";
			number = jdbcTemplate.queryForInt(sql, new Object[] { userId });
		} catch (Exception e) {
			log.error("查询用户彩票总数时出错", e);

		}
		return number;
	}

	/**
	 * 用户点击进入个人中心时显示的礼包数量
	 * 
	 * @param userId
	 * @return
	 */
	public int getGiftNumber(int userId) {
		int number = 0;
		try {
			String sql = "SELECT COUNT(u.id) FROM t2_site_user_message s INNER JOIN t2_self_user_message u WHERE s.id=u.message_id AND s.state=2 AND DATE_SUB(CURDATE(),INTERVAL 5 DAY)<=DATE(s.create_time) AND u.state_read=2 AND u.state_delete=2 AND u.user_id=?";
			number = jdbcTemplate.queryForInt(sql, new Object[] { userId });
		} catch (Exception e) {
			log.error("查询用户礼包总数时出错", e);
		}
		return number;
	}


	/**
	 * 用户操作信息时 ，删除和已读 handleType为1代表用户删除该信息， 为2时代表用户已读该信息
	 * 
	 * @param userId
	 * @param messageId
	 * @param handleType
	 * @return
	 */
	public int handleUserMessage(int userId, int handleType) {
		String sql = "";
		if (handleType == 1) {
			sql = "UPDATE t2_self_user_message SET state_delete=1 WHERE user_id=? "
					+ " AND message_id IN (SELECT id FROM t2_site_user_message WHERE user_id="+userId+" AND state=0) AND  state_delete=2 AND state_read=2";
		} else {
			sql = "UPDATE t2_self_user_message SET state_read=1 WHERE user_id=? AND state=0";
		}
		try {
			jdbcTemplate.update(sql, new Object[] { userId });
			return 1;
		} catch (Exception e) {
			log.error("删除或已读该信息时出错", e);
			return 0;
		}
	}
	/**
	 * 
	 * @Description:查询用户彩票	
	 * @author songyanbiao
	 * @date 2016年9月2日 上午11:18:04
	 * @param
	 * @return
	 */
	public List<Map<String, Object>> getLotteryNums(int userId){
		String sql="SELECT *  FROM t2_site_user_message WHERE state=1 AND id IN(SELECT message_id FROM t2_self_user_message WHERE user_id=? AND state_read=2 AND state_delete=2)";
		List<Map<String, Object>> ls= new ArrayList<Map<String, Object>>();
		try {
			ls=jdbcTemplate.queryForList(sql,new Object[]{userId});
		} catch (Exception e) {
			log.error("查询用户彩票出错",e);
		}
		return ls;
	}
	/**
	 * 
	 * @Description:查看彩票详情	
	 * @author songyanbiao
	 * @date 2016年9月2日 下午12:56:19
	 * @param
	 * @return
	 */
	public List<Map<String,Object>> getLotteryDetail(int messageId){
		String sql="SELECT * FROM  t2_site_user_message WHERE id=? AND state=1";
		List<Map<String,Object>> ls= new ArrayList<Map<String,Object>>();
		try {
			
			ls=jdbcTemplate.queryForList(sql,new Object[]{messageId});
		} catch (Exception e) {
			log.error("查看彩票详情出错",e);
		}
		return ls;
	}
	
	/**
	 * 修改用户的昵称
	 * 
	 * @param userName
	 * @param nickName
	 * @return
	 */
	public int updateUserNick(String userName, String nickName) {
		try {
			String sql = "UPDATE t_portal_user SET user_nickname=? WHERE user_name=?";
			jdbcTemplate.update(sql, new Object[] { nickName, userName });
			return 1;
		} catch (Exception e) {
			log.error("修改用户昵称时出错", e);
			return 0;
		}
	}

	/**
	 * 用户点击上传图像保存用户上传图像的url
	 * 
	 * @param userName
	 * @param pictureUrl
	 * @return
	 */
	public int updateUserPicture(String userName, String pictureUrl) {
		try {
			String sql = "UPDATE t_portal_user SET image_url=? WHERE user_name=?";
			jdbcTemplate.update(sql, new Object[] { pictureUrl, userName });
			return 1;
		} catch (Exception e) {
			log.error("保存用户头像失败", e);
			return 0;
		}

	}

	/**
	 * by:cuimiao 判断该账号是否已经注册
	 * 
	 * @Description
	 * @param phoneNum
	 * @return
	 */
	public boolean isRegister(String phoneNum) {
		String sql = "select count(id) from t_portal_user where user_name = ?";
		int count = jdbcTemplate.queryForInt(sql, new Object[] { phoneNum });
		if (count == 0) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 
	 *	@Description:ikuai系统用户登陆时先检验该账号是否已经登陆
	 *  @author songyanbiao
	 *	@Date 2016年6月6日 
	 *	@param user
	 *	@param ip
	 *	@return
	 */
	public RouterAndDevice checkUserLogin(PortalUser user,String ip){
		RouterAndDevice rad=null;
		try {
			
			rad=templNutDao.fetch(RouterAndDevice.class,Cnd.where("portal_user_id","=",user.getId()));
		} catch (Exception e) {
			log.error("");
		}
			
		return rad;
	} 
	/**
	 * 
	 *	@Description:查询场所路由
	 *  @author songyanbiao
	 *	@Date 2016年6月7日 
	 *	@param gwid
	 *	@return
	 */
	public CloudSiteRouters selRouter(String gwid){
		CloudSiteRouters csr=null;
		try {
			csr=nutDao.fetch(CloudSiteRouters.class, Cnd.where("dfid","=",gwid));
		} catch (Exception e) {
			log.error("查询场所路由出错",e);
		}
		return csr;
	}
	/**
	 * 
	 *	@Description:检测商户路由ip不正确时，修改路由ip
	 *  @author songyanbiao
	 *	@Date 2016年6月7日 
	 *	@param csr
	 *	@return
	 */
	public boolean updateRouIp(CloudSiteRouters csr,String basip){
		try {
			csr.setIp(basip);
			nutDao.update(csr);
			return true;
		} catch (Exception e) {
			log.error("检测商户路由ip不正确时，修改路由ip出错",e);
			return false;
		}
	}
	
	/**
	 * @Description 将用户到期时间存入到sesison中
	 * @date 2016年6月14日下午6:46:46
	 * @author guoyingjie
	 * @param siteId
	 * @param userId
	 * @param session
	 */
	public void setInfoToSession(int siteId,int userId,HttpSession session){
		try {
			Map<String, Object> map = this.getSiteIncomeRecord(siteId,userId);
			Map<String, Object> map2 = this.getSYtimeOrflow(siteId, userId);
			if (map.containsKey("time") && map2.containsKey("time")) {
				session.setAttribute("allTimeAndFlow", map);
				session.setAttribute("SyTimeAndFlow", map2);
				session.setAttribute("bili", this.getTyleBili(map, map2));
			}else{
				//weChatService.setSession(session);
			}
		} catch (Exception e) {
			log.error("获得总时间与总流量失败");
		}
	}
	/**
	 * @Description:返回时回去用户的额信息	
	 * @author songyanbiao
	 * @date 2016年7月11日 下午2:17:17
	 * @param
	 * @return
	 */
	public Map<String,Object> getMap(Map<String,Object> map,int siteId,int userId){
		try {
			Map<String, Object> map1= this.getSiteIncomeRecord(siteId,userId);
			Map<String, Object> map2 = this.getSYtimeOrflow(siteId, userId);
			if (map1.containsKey("time") && map2.containsKey("time")) {
				map.put("allTimeAndFlow", map2.get("time"));
				map.put("SyTimeAndFlow", map2.get("flowstr"));
				map.put("bili",  this.getTyleBili(map1, map2));
			}
		} catch (Exception e) {
			log.error("获得总时间与总流量失败");
		}
		return map;
	}
	/**
	 * @Description 注册成功后添加试用时间
	 * @date 
	 * @author guoyingjie
	 * @param site
	 * @param proUser
	 * @return
	 */
	public void rSaveTryTime(CloudSite site,PortalUser proUser){
		     int tryState = site.getIs_probative();
			 try {
				String exTime = "";
				if (tryState != 0) {
					if(tryState==30){
						exTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime()+30*60*1000);
					}else{
					    exTime = DateUtil.datePlus(0, tryState);
					}
				}else{
					exTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime());
				}
				this.savePaymentinfo(site.getId(), proUser.getId());
				this.insertExpireDate(exTime, site.getId(),proUser.getId(),null);
			} catch (Exception e) {
				log.error("注册成功后添加试用时间异常");
			}  
	}
	/**
	 * 
	 * @Description:微信推荐码注册	
	 * @author songyanbiao
	 * @date 2016年10月15日 下午1:34:18
	 * @param
	 * @return
	 */
	public boolean doWecharRegister(final String code,final String tel,final String recommend,final UserRecommend urd){
		try {
			Trans.exec(new Atom() {
				@Override
				public void run() {
					PortalUser u=new PortalUser();
					u.setUserName(tel);
					u.setPassWord(SHA256.getUserPassword(tel, MD5.encode(code).toLowerCase()));
					u.setUserIntegral(5);
					u.setRecomdState(1);
					int i=nutDao.insert(u).getId();
					if(i<1){
						throw Lang.makeThrow("微信推荐码注册用户失败======doWecharRegister");
					}
					PortalUser user=nutDao.fetch(PortalUser.class, Cnd.where("id","=",urd.getUserId()))	;
					user.setUserIntegral(user.getUserIntegral()+5);
					i=nutDao.update(user);
					if(i<1){
						throw Lang.makeThrow("微信推荐用户增加积分失败======doWecharRegister");
					}
				}
			});
		} catch (Exception e) {
			log.error("微信推荐码注册失败",e);
			return false;
		}
		return true;
	}
	/**
	 * 
	 * @Description:查询推荐码是否正确	
	 * @author songyanbiao
	 * @date 2016年10月15日 下午2:15:17
	 * @param
	 * @return
	 */
	public UserRecommend getRecommend(String recommend){
		return nutDao.fetch(UserRecommend.class, Cnd.where("recommend","=",recommend));
	} 
	/**
	 * 
	 * @Description:微信充值送积分	
	 * @author songyanbiao
	 * @date 2016年11月4日 下午5:53:19
	 * @param
	 * @return
	 */
	public int updateUserInfo(String userName,String amount){
		int counts=(int) Double.parseDouble(amount);
		int perience=counts*100;
		if(counts<10){
			counts=counts*10;
		}else if(counts>=10&&counts<50){
			counts=counts*10+5;
		}else if(counts>=50&&counts<300){
			counts=counts*10+30;
		}else if(counts>=300){
			counts=counts*10+200;
		}
		PortalUser user=nutDao.fetch(PortalUser.class, Cnd.where("user_name","=",userName));
		if(user.getUserGrade()<7){
			if((user.getUserExperience()+perience)/500>3&&(user.getUserExperience()+perience)/500<=9){
				user.setUserGrade(3);
			}else if((user.getUserExperience()+perience)/500>9&&(user.getUserExperience()+perience)/500<=27){
				user.setUserGrade(4);
			}else if((user.getUserExperience()+perience)/500>27&&(user.getUserExperience()+perience)/500<=81){
				user.setUserGrade(5);
			}else if((user.getUserExperience()+perience)/500>81&&(user.getUserExperience()+perience)/500<=162){
				user.setUserGrade(6);
			}else if((user.getUserExperience()+perience)/500>162){
				user.setUserGrade(7);
			}
		}
		user.setUserIntegral(user.getUserIntegral()+counts);
		user.setUserExperience(user.getUserExperience()+perience);
		return nutDao.update(user);
	}
	
	
}
