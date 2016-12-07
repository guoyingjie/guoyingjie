package com.broadeast.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.broadeast.bean.SitePriceConfigAll;
import com.broadeast.entity.PortalUser;
import com.broadeast.entity.SiteCustomerInfo;
import com.broadeast.entity.SitePaymentRecord;
import com.broadeast.entity.SitePriceConfig;
import com.broadeast.handler.JsonView;
import com.broadeast.service.impl.SchoolPaymentService;
import com.broadeast.service.impl.SitePaymentRecordsService;
import com.broadeast.service.impl.SiteService;
import com.broadeast.util.DateUtil;
import com.broadeast.util.ExecuteResult;
import com.util.thirdpay.Pay;


/**
 * @ToDoWhat 
 * @author xmm
 * 
 * 此类主要处理手机端的支付宝支付 
 */
@Controller
@RequestMapping("/pay")
public class AliPayForMobileController {
	private static Logger logger = Logger.getLogger(AliPayForMobileController.class);

	
	@Autowired
	private SchoolPaymentService schoolPaymentService;
	
	@Autowired
	private SitePaymentRecordsService sitePaymentRecordsService;
	
	@Autowired
	private SiteService siteService;
	
	/**
	 * 支付宝支付
	 * @param id
	 * @param response
	 * @return
	 * 
	 */
	@RequestMapping("/aliPay")
	public ModelAndView aliPay(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		Map<String ,String>map=new HashMap<String,String>(7);
		PortalUser user=(PortalUser) request.getSession().getAttribute("proUser");//添加用户到session
		SitePriceConfigAll siteConfigAll=(SitePriceConfigAll) request.getSession().getAttribute("siteAll");//添加场所所有收费规则session
		if(user==null||siteConfigAll==null){
			ModelAndView mav = new ModelAndView("/mobile/doOrder");  
			mav.addObject("priceType", request.getParameter("payType"));
			mav.addObject("month", request.getParameter("buyNum"));
			mav.addObject("type", request.getParameter("type"));
			mav.addObject("msg", "用户信息缺失,请重新d登录");
	        return mav;
		}
		
		map.put("userId",user.getId()+"");//用户Id
		map.put("storeId",siteConfigAll.getSiteInof().getId()+"");//场所Id
		map.put("tenantId", siteConfigAll.getSiteInof().getUser_id()+"");//商户id
		map.put("payType",request.getParameter("priceConfig"));//场所收费配置Id
		map.put("buyNum",request.getParameter("nums"));//购买数量
		map.put("amount",request.getParameter("amount"));//总金额
		map.put("priceNum", request.getParameter("price_num"));//套餐类型
		map.put("priceName",request.getParameter("price_Name"));//套餐名称
		
		String giveNum=request.getParameter("addMealNum");//赠送的套餐数量
		String giveUnit=request.getParameter("addMealUnit");//赠送的套餐单位
		map.put("addMealNum",giveNum);
		map.put("addMealUnit",giveUnit);
		map.put("mealType",request.getParameter("mealType"));//选择的套餐类型 1----时间，2-----流量
		//校验
//		schoolPaymentService.setPayCenterService(new AliWapDirectPayServiceImpl());
		String checkResult=schoolPaymentService.paramsCheck(map);
		if("ok".equals(checkResult)){//校验通过
			
			SitePriceConfig scf=siteService.getSitePriceInfos(siteConfigAll.getSiteInof().getId(),Integer.parseInt(map.get("payType")));
			String siteName=siteService.getSiteName(siteConfigAll.getSiteInof().getId());
			
			//根据和当前时间的比较计算到期时间
			PortalUser pu =(PortalUser) request.getSession().getAttribute("proUser");
			SiteCustomerInfo scii =siteService.getExpirationTimeByProuserid(pu.getId(),siteConfigAll.getSiteInof().getId());
			//计算用户到期时间或者流量
			String riqi=siteService.getUserCustomer(scii, scf, map);
			if("1".equals(map.get("mealType"))){//用户购买的是时间套餐
				map.put("expireDate",riqi);
			}else{
				map.put("expireFlow",riqi);
			}
			
			String orderNum=Pay.getUuidOrderNumFromUserId(map.get("userId"));
			String subject="校园卡会员充值,按"+map.get("priceName")+"充值("+siteName+")";
			
			//保存支付信息
			schoolPaymentService.savePaymentinfo(orderNum,map,1);
			//跳转到支付宝
			String html=schoolPaymentService.getPayHTML(orderNum,subject, request.getParameter("amount"));
	        return JsonView.Render(html, response);
			
		}else{//校验不通过
			ModelAndView mav = new ModelAndView("/mobile/doOrder");  
			mav.addObject("priceType", request.getParameter("payType"));
			mav.addObject("month", request.getParameter("buyNum"));
			mav.addObject("type", request.getParameter("type"));
			mav.addObject("msg", checkResult);
	        return mav;
		}
	}
	 
	/**
	 * 支付宝通知
	 * @param request
	 * @param response
	 */
	@RequestMapping("/aliPayNotify")
	public void aliPayNotify(HttpServletRequest request, HttpServletResponse response){
		try {
			logger.info("支付宝异步通知调用");
			schoolPaymentService.Notify(request, response);
			return;
        }catch (Exception e) {
        	logger.error("支付结果通知接口异常.", e);
            try {
            	PrintWriter out = response.getWriter();
	            out.write("fail");
            }catch (IOException e1) {
	            e1.printStackTrace();
            }
        }
	}
	
	/**
	 * 根据tradeNum校验支付是否完成
	 * @param out_trade_no
	 * @param session
	 * @return
	 */
	@RequestMapping("/checkPayResult")
	@ResponseBody
	public String checkPayResult(@RequestParam String out_trade_no,@RequestParam String trade_no,HttpSession session){
		ExecuteResult rs=new ExecuteResult();
		PortalUser user=(PortalUser) session.getAttribute("proUser");//添加用户到session
		SitePriceConfigAll siteConfigAll=(SitePriceConfigAll) session.getAttribute("siteAll");//添加场所所有收费规则session
		
		SitePaymentRecord spr=sitePaymentRecordsService.getSitePaymentRecordByTradeNum(out_trade_no,trade_no, siteConfigAll.getSiteInof().getId(), user.getId());
		
		if(spr!=null&&spr.getIsFinish()==1){
			
			rs.setCode(200);
			sitePaymentRecordsService.updateIsFinish(out_trade_no);
		}else{
			rs.setCode(201);
			rs.setMsg("支付未完成或状态未同步.");
		}
		
		return rs.toJsonString();
	}
	
}
