package com.broadeast.controller.weixin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

import com.broadeast.bean.SitePriceConfigAll;
import com.broadeast.entity.CloudSite;
import com.broadeast.entity.PortalUser;
import com.broadeast.entity.SiteCustomerInfo;
import com.broadeast.entity.SitePriceConfig;
import com.broadeast.entity.UserBalance;
import com.broadeast.entity.WechatUserInfo;
import com.broadeast.service.impl.SchoolPaymentService;
import com.broadeast.service.impl.SiteService;
import com.broadeast.service.impl.UserService;
import com.broadeast.service.impl.WeChatOtherService;
import com.broadeast.service.impl.WeChatService;
import com.broadeast.util.BigDecimalUtil;
import com.broadeast.util.DateUtil;
import com.broadeast.util.ExecuteResult;
import com.broadeast.util.PropertiesParam;
import com.broadeast.util.toLoginUtil;
import com.broadeast.weixin.comment.Configure;
import com.broadeast.weixin.comment.HttpService;
import com.broadeast.weixin.comment.JsonUtil;
import com.broadeast.weixin.comment.MD5;
import com.broadeast.weixin.comment.RandomStringGenerator;
import com.broadeast.weixin.comment.UnifiedOrderReqData;
import com.broadeast.weixin.comment.WxPayApi;
import com.broadeast.weixin.comment.XMLParser;
import com.util.thirdpay.Pay;

@Controller
@RequestMapping("weChatPayOther")
@SuppressWarnings("all")
public class WeChatPublicPayOtherController {

	private static final String APPID = "wxc5fb6a6dabc34dfb";
	private static final String APPSECRET = "ceafa600a2d9b2a98d36885081d16058";
	private static final String MCHID = "1332831801";
	private static Logger logger = Logger.getLogger(WeChatPublicPayOtherController.class);
	
	@Resource(name="weChatOtherService")
	private WeChatOtherService weChatOtherService;
	
	@Resource(name="userService")
	private UserService userService;
	
	@Autowired
	private SchoolPaymentService schoolPaymentService;
	
	@Autowired
	private SiteService siteService;
   /**
    *	@Description:授权微信公众号拿到用户的微信信息
    *  @author songyanbiao
    *	@Date 2016年6月16日 
    *	@return
    */
	@RequestMapping("authorizeToUser")
	public String authorizeToUser(){
		String redirectUrl = PropertiesParam.DeckUrl+"deck/weChatPayOther/getWeChatUserInfo";
		//String redirectUrl = "http://192.168.10.200/deck/weC hatPayOther/getWeChatUserInfo";
		String url =Configure.GETCODE_URL+"appid="+APPID+"&redirect_uri="
		+redirectUrl+"&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
		return "redirect:"+url;
	}
	
	/**
	 * 
	 *	@Description:微信回调路径得到openid.
	 *  @author songyanbiao
	 *	@Date 2016年6月16日 
	 *	@param request
	 *	@param session
	 *	@return
	 */
	@RequestMapping("getWeChatUserInfo")
	public String getWeChatUserInfo(HttpServletRequest request,HttpSession session){
		String code = (request.getParameter("code")+"").trim();
		try {
     		Map<String, Object> map = WxPayApi.getOpenidAndAccessToken(APPID, APPSECRET, code);
     		if(map!=null){
     			String openid = map.get("openid")==null?null:map.get("openid").toString();
     			String accessToken = map.get("access_token")==null?null:map.get("access_token").toString();
     			session.setAttribute("openid",openid);
     			session.setAttribute("accessToken",accessToken );
     		}
		} catch (Exception e) {
			 logger.error("获得openid失败",e);
			 return  "/wechat/nosite";
		}
		return  "/wechat/payForOther";
	}
	
	/**
	 * 
	 *	@Description:检查是否已经绑定wifi账号
	 *  @author songyanbiao
	 *	@Date 2016年6月16日 
	 *	@param userName
	 *	@return
	 * @throws ParserConfigurationException 
	 * @throws SAXException 
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	@RequestMapping("checkNumBind")
	public String checkNumBind(@RequestParam String userName,@RequestParam String openid,
			HttpSession session) throws NumberFormatException, IOException, SAXException, ParserConfigurationException{
		String sucUrl="/wechat/pay";
		String failUrl="/wechat/payForOtherNum";
		session.setAttribute("openid",openid);
		return weChatOtherService.checkNumBind(userName, sucUrl, failUrl, session,openid);

	}
	/**
	 * 
	 *	@Description:微信支付给他人充值余额
	 *  @author songyanbiao
	 *	@Date 2016年6月16日 
	 *	@param request
	 *	@param response
	 *	@return
	 * @throws NumberFormatException 
	 * @throws ParserConfigurationException 
	 * @throws SAXException 
	 * @throws IOException 
	 */
	@RequestMapping("wxPayOhter")
	public String winxinPayOhter(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws NumberFormatException, IOException, SAXException, ParserConfigurationException{
		return weChatOtherService.winxinPayOhterBalance(request, response, session);
	}
	/**
	 * 
	 *	@Description:微信支付充值
	 *  @author songyanbiao
	 *	@Date 2016年6月21日 
	 *	@param request
	 *	@param response
	 *	@param session
	 *	@return
	 */
	@RequestMapping("weixinPayOtherSite")
	public String weixinPayOtherSite(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		return weChatOtherService.weiXinPayOtherAccount(request, response, session);
			
	}
	
	/**
	 * 
	 *	@Description:微信支付异步通知
	 *  @author songyanbiao
	 *	@Date 2016年6月21日 
	 *	@param request
	 *	@param response
	 *	@throws IOException
	 */
	@RequestMapping("weixinNotifyNotice")
	public void weixinNotifyNotice(HttpServletRequest request,HttpServletResponse response) throws IOException {
		weChatOtherService.wxNotifyNotice(request, response);
			
	}
	/**
	 * 
	 *	@Description:微信支付帮别人充值余额异步通知
	 *  @author songyanbiao
	 *	@Date 2016年6月21日 
	 *	@param request
	 *	@param response
	 *	@throws IOException
	 */
	@RequestMapping("wxBanlanceNotify")
	public void winxinBanlanceNotify(HttpServletRequest request,HttpServletResponse response) throws IOException {
		weChatOtherService.balanceNotifyNotice(request, response);
	}
	
	/**
	 * 
	 *	@Description:微信支付是否支付成功
	 *  @author songyanbiao
	 *	@Date 2016年6月16日 
	 *	@param outTradeNo
	 *	@return
	 */
	@RequestMapping("getUserStatus")
	public String getUserStatus(HttpSession session,@RequestParam String outTradeNo){
		return weChatOtherService.getPayOtherStatus(session, outTradeNo);
	}
	
	@RequestMapping("url")
	public String goURL(String url){
		return url.replace(".jsp","");
	}
	
}
