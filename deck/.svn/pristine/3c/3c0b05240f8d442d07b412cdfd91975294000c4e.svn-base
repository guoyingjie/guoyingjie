package com.wap.alipay.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.stereotype.Repository;
import com.broadeast.service.IPayCenterService;
import com.wap.alipay.config.AlipayConfig;


/**网页版及时到账支付服务
 * @ToDoWhat 
 * @author xmm
 */
@Repository
public class AliWapDirectPayServiceImpl implements IPayCenterService {
	
//	private static Logger logger = Logger.getLogger(AliWapDirectPayServiceImpl.class);


	@Override
	public String getPayHTML(String out_trade_no, String subject, String total_fee){
		
		////////////////////////////////////调用授权接口alipay.wap.trade.create.direct获取授权码token//////////////////////////////////////
		
		//请求号
		String req_id = UtilDate.getReqId();
		//必填，须保证每次请求都是唯一
		
		//请求业务参数详细
//		String req_dataToken = "<direct_trade_create_req><notify_url>" + notify_url + "</notify_url><call_back_url>" + call_back_url + "</call_back_url><seller_account_name>" + AlipayConfig.seller_email + "</seller_account_name><out_trade_no>" + out_trade_no + "</out_trade_no><subject>" + subject + "</subject><total_fee>" + total_fee + "</total_fee><merchant_url>" + merchant_url + "</merchant_url></direct_trade_create_req>";
		String req_dataToken = "<direct_trade_create_req><notify_url>" + AlipayConfig.notify_url + "</notify_url><call_back_url>" + AlipayConfig.call_back_url + "</call_back_url><seller_account_name>" + AlipayConfig.seller_email + "</seller_account_name><out_trade_no>" + out_trade_no + "</out_trade_no><subject>" + subject + "</subject><total_fee>" + total_fee + "</total_fee></direct_trade_create_req>";
		//必填
		
		//////////////////////////////////////////////////////////////////////////////////
		
		//把请求参数打包成数组
		Map<String, String> sParaTempToken = new HashMap<String, String>();
		sParaTempToken.put("service", "alipay.wap.trade.create.direct");
		sParaTempToken.put("partner", AlipayConfig.partner);
		sParaTempToken.put("_input_charset", AlipayConfig.input_charset);
		sParaTempToken.put("sec_id", AlipayConfig.sign_type);
		sParaTempToken.put("format", AlipayConfig.format);
		sParaTempToken.put("v", AlipayConfig.v);
		sParaTempToken.put("req_id", req_id);
		sParaTempToken.put("req_data", req_dataToken);
		//获取token
		String request_token;
        try {
	        //建立请求
	        String sHtmlTextToken = AlipaySubmit.buildRequest(AlipayConfig.ALIPAY_GATEWAY_NEW,"", "",sParaTempToken);
	        //URLDECODE返回的信息
	        sHtmlTextToken = URLDecoder.decode(sHtmlTextToken,AlipayConfig.input_charset);
	        request_token = AlipaySubmit.getRequestToken(sHtmlTextToken);
        }catch (UnsupportedEncodingException e) {
//	        logger.error("获取request_token出错",e);
	        return "<script>alert('支付遇到问题，请联系服务商解决。');</script>";
        }catch (Exception e) {
//	        logger.error("获取request_token出错",e);
	        return "<script>alert('支付遇到问题，请联系服务商解决。');</script>";
        }
		//out.println(request_token);
		
		////////////////////////////////////根据授权码token调用交易接口alipay.wap.auth.authAndExecute//////////////////////////////////////
		
		//业务详细
		String req_data = "<auth_and_execute_req><request_token>" + request_token + "</request_token></auth_and_execute_req>";
		//必填
		
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "alipay.wap.auth.authAndExecute");
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("sec_id", AlipayConfig.sign_type);
		sParaTemp.put("format", AlipayConfig.format);
		sParaTemp.put("v", AlipayConfig.v);
		sParaTemp.put("req_data", req_data);
		sParaTemp.put("app_pay","Y");
		
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(AlipayConfig.ALIPAY_GATEWAY_NEW, sParaTemp, "get", "确认");
		return sHtmlText;
	}

	
	@Override
	public Map<String, String> getNotifyParams(Map<String, String> params) throws Exception {
		//RSA签名解密
	   	if(AlipayConfig.sign_type.equals("0001")) {
	   		params = AlipayNotify.decrypt(params);
	   	}
		//XML解析notify_data数据
		Document doc_notify_data = DocumentHelper.parseText(params.get("notify_data"));
		
		Map<String,String> map=new HashMap<String,String>(3);
		//商户订单号
		map.put("out_trade_no",doc_notify_data.selectSingleNode( "//notify/out_trade_no" ).getText());
		//支付宝交易号
		map.put("trade_no",doc_notify_data.selectSingleNode( "//notify/trade_no" ).getText());
		//交易状态
		map.put("trade_status",doc_notify_data.selectSingleNode( "//notify/trade_status" ).getText());
		return map;
	}


	@Override
    public boolean verify(Map<String, String> params) throws Exception {
		 return AlipayNotify.verifyNotify(params);
    }

}
