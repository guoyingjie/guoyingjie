package com.broadeast.service.impl;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.xml.sax.SAXException;

import com.broadeast.bean.SitePriceConfigAll;
import com.broadeast.entity.CloudSite;
import com.broadeast.entity.CloudSiteRouters;
import com.broadeast.entity.PortalUser;
import com.broadeast.entity.SiteCustomerInfo;
import com.broadeast.entity.UserBalance;
import com.broadeast.entity.WechatUserInfo;
import com.broadeast.util.ExecuteResult;
import com.broadeast.util.MD5;
import com.broadeast.util.SHA256;
import com.broadeast.util.WanipUtil;
import com.broadeast.weixin.comment.HttpService;
import com.broadeast.weixin.comment.JsonUtil;

@Service
@SuppressWarnings("all")
public class MyselfPayService {

	Logger logger = Logger.getLogger(MyselfPayService.class);
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Resource(name = "nutDao")
	private Dao nutDao;
	
	@Resource(name = "templJdbcTemplate")
	private JdbcTemplate templJdbcTemplate;

	
	@Resource(name="userService")
	private UserService userService;
	



	/**
	 * @Description 通过外网ip获得场所信息
	 * @date 2016年6月15日下午1:12:32
	 * @author guoyingjie
	 * @param ip
	 */
	public CloudSite getSiteIdByIp(HttpServletRequest request,HttpServletResponse response){
		String ip = WanipUtil.getWanIp(request,response);
		//根据ip获得路由的设备,此时有wifidog与小辣椒与ikuai,第一步去判断在路由新消息表里查找路由的信息,如果有说明是小辣椒或者是ikuai.如果查不到则是wifidog
		CloudSiteRouters router = nutDao.fetch(CloudSiteRouters.class,Cnd.where("ip","=",ip).limit(0,1));
		if(router==null){
			//此时路由设备不是小辣椒与ikuai,在路由注册表中根据wanip查找设备mac
//			String sql = "SELECT mac FROM v1_store_router where wanip = ? GROUP BY wanip";
//			List<Map<String,Object>> mac = templJdbcTemplate.queryForList(sql,new Object[]{ip});
//			if(mac!=null&&mac.size()>0&&!mac.isEmpty()){
//				//如果当前的路由设备存在.通过mac找到用户所在的归属场所.
//				CloudSiteRouters rout = nutDao.fetch(CloudSiteRouters.class,Cnd.where("mac","=",mac.get(0)).limit(0,1));
//				CloudSite site = nutDao.fetch(CloudSite.class,Cnd.where("id","=",rout.getSiteId()));
//				return site;
//			}else{
				return null;
//			}
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
	public int insertWechatUserInfo(int userId,String openid,Map<String, Object> map){
		try {
			WechatUserInfo info = new WechatUserInfo();
			info.setPortal_user_id(userId);
			info.setOpenid(openid);
			info.setNickname(map.get("nickname")==null?"":map.get("nickname").toString());
			info.setSex((int)map.get("sex"));
			info.setProvince(map.get("province")==null?"":map.get("province").toString());
			info.setCity(map.get("city")==null?"":map.get("city").toString());
			info.setCountry(map.get("country")==null?"":map.get("country").toString());
			info.setHeadimgurl(map.get("headimgurl")==null?"":map.get("headimgurl").toString());
			info.setPrivilege(map.get("privilege")==null?"":map.get("privilege").toString());
			info.setUnionid(map.get("unionid")==null?"":map.get("unionid").toString());
			nutDao.insert(info);
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
		String checkurl = "https://api.weixin.qq.com/sns/auth?access_token="+accessToken+"&openid="+openid;
		String result = HttpService.doGet(checkurl);
		Map<String, Object> checkmap = JsonUtil.fromJson(result, HashMap.class);
		if(!checkmap.get("errmsg").toString().equals("ok")&&!checkmap.isEmpty()){
			rs.setCode(201);
			rs.setMsg("授权凭证 已过期,请返回重试");
			return rs.toJsonString();
		}
		
		PortalUser user = userService.getPortalUserByTel(userName);
		if(user==null){
			int state = registerPortalUser(userName,password);
			if(state!=1){
				rs.setCode(201);
				rs.setMsg("绑定用户失败,请重新绑定");
				return rs.toJsonString();
			}
		} 
		PortalUser proUser = userService.getUserPro(userName, MD5.encode(password).toLowerCase());
		if(proUser==null){
			rs.setCode(201);
			rs.setMsg("用户名密码错误");
			return rs.toJsonString();
		}
		String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid="+openid+"&lang=zh_CN";
		String res = HttpService.doGet(url);
		Map<String, Object> resultMap = JsonUtil.fromJson(res, HashMap.class);
		WechatUserInfo winfo = checkOpenid(proUser.getId());
		if(winfo!=null){
			rs.setCode(201);
			rs.setMsg("您的微信已经绑定到其它用户,请更换账户绑定");
			return rs.toJsonString();
		}
		int isok = insertWechatUserInfo(user.getId(), openid,resultMap);
		if(1!=isok){
			rs.setCode(201);
			rs.setMsg("保存绑定用户失败");
			return rs.toJsonString();
		}
		session.setAttribute("proUser", proUser);
		rs.setCode(200);
		rs.setMsg(jumpUrl);
		return rs.toJsonString();
	}
	/**
	 * 
	 *	@Description:得场所下的全部的消费配置
	 *  @author songyanbiao
	 *	@Date 2016年6月25日 
	 *	@param site
	 *	@param portal
	 *	@return
	 */
	public SitePriceConfigAll getSiteRule(int siteId,int userId){
		SitePriceConfigAll siteAll = null;
		try {
			siteAll=userService.getSitePriceConfigAll(siteId, userId);
			return siteAll;
		} catch (Exception e) {
			logger.error(this.getClass().getCanonicalName()
					+ "===getSitePriceConfigAll", e);
		}
		return siteAll;
	}
	/**
	 * 
	 *	@Description:用户绑定之后去的页面
	 *  @author songyanbiao
	 *	@Date 2016年6月25日 
	 *	@param userId
	 *	@param request
	 *	@param session
	 *	@param response
	 *	@return
	 */
	public String jumpPage(PortalUser user,String openId,HttpServletRequest request,HttpSession session,HttpServletResponse response){
		//如果用户手机号绑定了openid.但是该用户没有注册
		List<CloudSite> siteLs=new ArrayList<CloudSite>();
		session.setAttribute("openid",openId);
		session.setAttribute("tel", user.getUserName());
		//根据外网ip查询用户场所,如果通过该外网ip查询不到该用户的归属场所,则根据用户名查询该用户所属的所有场所
		CloudSite site=getSiteIdByIp(request, response);
		if(site!=null){
			session.setAttribute("site", site);
		 	return "/wechat/school";
		}
		List<Map<String, Object>> ls=checkUserSite(user.getId());
		if(ls!=null){
			for (int i = 0; i < ls.size(); i++) {
				site=getSiteName((int)ls.get(i).get("site_id"));
				if(site!=null){
					siteLs.add(site);
				}
			}
		}
		//如果siteLs为空则认为该用户无归属场所，直接去充值余额
		if(siteLs!=null){
			if(siteLs.size()>1){
				session.setAttribute("siteLs", siteLs);
				return "/wechat/allSchool";
			}
			session.setAttribute("site", site);
			return "/wechat/school";
		}
		return "/wechat/payForMyself";
	}
	/**
	 * 
	 *	@Description:查询场所
	 *  @author songyanbiao
	 *	@Date 2016年7月4日 
	 *	@param siteId
	 *	@return
	 */
	public CloudSite getSite(String siteId){
		CloudSite site=null;
		try {
			site=nutDao.fetch(CloudSite.class,Cnd.where("id","=",siteId));
		} catch (Exception e) {
			logger.error("查询场所出错",e);
		}
		return site;
	}
}
