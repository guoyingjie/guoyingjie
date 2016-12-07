package com.broadeast.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.lang.Lang;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;

import com.broadeast.entity.CloudSite;
import com.broadeast.entity.PortalUser;
import com.broadeast.entity.SiteUserRealNameAuth;

@Service
public class UserRealnameAuthImpls {

	private static Logger logger = Logger.getLogger(UserRealnameAuthImpls.class);
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	@Resource(name="nutDao")
	private Dao nutDao;
	
	/**
	 * @Description  添加用户认证信息
	 * @param siteId 场所id
	 * @param telephone 用户电话号码
	 * @param realName 用户真实姓名
	 * @param idCard 用户身份证号
	 * @param address  用户详细地址
	 * @param imgUrl   用户上传头像与省份证的url
	 * @return
	 */
	public int insertUserAuthInfo(int siteId,String telephone,String realName,String idCard,String address,String imgUrl){
		try {
			SiteUserRealNameAuth siteUserRealNameAuth = new SiteUserRealNameAuth();
			siteUserRealNameAuth.setSiteId(siteId);
			siteUserRealNameAuth.setUserName(realName);
			siteUserRealNameAuth.setTelephone(telephone);
			siteUserRealNameAuth.setIdCard(idCard);
			siteUserRealNameAuth.setImgUrl(imgUrl);
			siteUserRealNameAuth.setAddress(address);
			siteUserRealNameAuth.setState(1);
			nutDao.insert(siteUserRealNameAuth);
			return 1;
		} catch (Exception e) {
			logger.error(this.getClass().getCanonicalName()+"--method--insertUserAuthInfo--失败",e);
			return 0;
		}
	}
	/**
	 * @Description  添加用户认证信息并且更改用户的状态为2--不必等到用户审批的过程不能上网
	 * @param siteId 场所id
	 * @param telephone 用户电话号码
	 * @param realName 用户真实姓名
	 * @param idCard 用户身份证号
	 * @param address  用户详细地址
	 * @param imgUrl   用户上传头像与省份证的url
	 * @return
	 */
	public boolean ifDone(final int siteId,final String telephone,final String realName,final String idCard,final String address,final String imgUrl,final PortalUser portalUser){
		try {
	        Trans.exec(new Atom(){
	            public void run() {
	            	int num = insertUserAuthInfo(siteId, telephone, realName, idCard, address, imgUrl);
	        		if(num==0){
	        			throw Lang.makeThrow("添加用户认证信息失败--method--ifDone");
	        		}
	        		int ok = updateAuthState(portalUser,2);
	        		if(ok==0){
	        			throw Lang.makeThrow("添加用户认证信息并且更改用户的状态为2失败--method--ifDone");
	        		}
	            }
	        });
	        return true;
        }catch (Exception e) {
        	logger.error("deck--ifDone", e);
        	return false;
        }
	}
	
	/**
	 * @Description 获得用户的认证状态
	 * @param portalUser 用户表
	 * @return state
	 */
	public int getUserAuthState(PortalUser portalUser){
		int state = portalUser.getState();
		return state;
	}
	/**
	 * @Description  是否开启了认证
	 * @return 0--代表开启认证--true   1--代表关闭认证--false
	 */
	public boolean ifGoAuth(CloudSite site){
		boolean flag = true;
		int state = site.getState();
		if ("0".equals(String.valueOf(state))) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}
	/**
	 * @Description  0--新注册用户第一次可以通过,第二次需要认证,,1--直接去认证,,2--完成认证,,3--未通过去认证
	 * @param portalUser
	 */
	public int updateAuthState(PortalUser portalUser,int state){
		try {
			portalUser.setState(state);
			return nutDao.update(portalUser);
		} catch (Exception e) {
			logger.error("deck===updateAuthState", e);
			return 0;
		}
	}
	/**
	 * @Description  检查是否有重复的身份证号
	 * @param idCard
	 * @return  false--代表没有    true--代表有
	 */
	public boolean isHaveIdCard(String idCard){
		boolean flag = false;
		try {
			SiteUserRealNameAuth  siteIdCard = nutDao.fetch(SiteUserRealNameAuth.class,Cnd.where("id_card","=",idCard));
			if(siteIdCard!=null){
				flag = true;
			}else{
				flag = false;
			}
			
		} catch (Exception e) {
			 logger.error(this.getClass().getCanonicalName()+"检查重复身份证失败--method--isHaveIdCard",e);
		}
		return flag;
	}
	 
	/**
	 * @Description  检验用户是否已经认证
	 * @param po
	 * @return
	 */
	public boolean isAuth(PortalUser po){
		String telephone = po.getUserName();
		SiteUserRealNameAuth authname = nutDao.fetch(SiteUserRealNameAuth.class,Cnd.where("telephone","=",telephone).and("state","in","1,2"));
		if(authname!=null){
			return true;
		}else{
			return false;
		}
	}
	 
	
}