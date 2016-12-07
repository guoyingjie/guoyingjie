package com.broadeast.util;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.broadeast.service.impl.MyselfPayService;

/**
 * @author pengxw E-mail:pengxianwei@kdfwifi.com
 * @version 创建时间：2016年9月2日 下午3:39:49
 * @describe
 */
public class PropertiesParam {
	private static Logger log =  Logger.getLogger(PropertiesParam.class);
	/**
	 * 项目使用的域名
	 */
	public static String DeckUrl;
	
	
	/**
	 * 彩票属性配置
	 */
	public static String partnerId;
	public static String partnerCallbackURL;
	public static boolean partnerDebug;
	public static String VERSIONS;
	
	static{
		try {
			ResourceBundle rb = ResourceBundle.getBundle("bankcardconf", Locale.getDefault()); 
			DeckUrl= rb.getString("web.deck");
			System.out.println(DeckUrl);
			/**
			 * 彩票PartnerID,回调URL配置
			 */
			partnerId = rb.getString("lottery.partnerId");
			partnerCallbackURL = DeckUrl+rb.getString("lottery.partnerCallbackURL");
			partnerDebug = false;
			VERSIONS = rb.getString("web.versions");
			System.out.println(VERSIONS);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getLocalizedMessage());
		}
		
	}
	
}
