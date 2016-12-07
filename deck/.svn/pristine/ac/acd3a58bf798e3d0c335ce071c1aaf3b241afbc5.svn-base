package com.broadeast.controller.wifidog;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.broadeast.bean.LotteryResponse;
import com.broadeast.bean.LotteryTransData;
import com.broadeast.bean.LotteryTransData.BetInfo;
import com.broadeast.entity.PortalUser;
import com.broadeast.service.impl.LotteryService;
import com.broadeast.service.impl.UserService;
import com.broadeast.util.HttpGetPost;
import com.broadeast.util.PropertiesParam;

/**用来赠送彩票流程
 * @author pengxw E-mail:pengxianwei@kdfwifi.com
 * @version 创建时间：2016年9月1日 上午9:53:44
 * @describe
 */
@Controller
@RequestMapping("w")
@SuppressWarnings("all")
public class LotteryController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private LotteryService lotteryService;
	/**
	 * 初始化获取系统当前域名
	 */
	@RequestMapping("deckurl")
	@ResponseBody
	public String getDeckUrl(HttpServletRequest request){
		StringBuffer url = request.getRequestURL();  
		String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString(); 
		PropertiesParam.DeckUrl = tempContextUrl;
		return tempContextUrl;
	}
	@RequestMapping("jumpMethod")
	public String jumpMethod(){
		return "/pc/ticketmethod";
	}
	@RequestMapping("jumpHelpMethod")
	public String jumpHelpMethod(){
		return "/pc/tmheip";
	}
	
	/**
	 * 打开选择彩票的页面
	 * @param request
	 * @return
	 * deck
	 */
	@RequestMapping("lottery")
	public String getSelect(HttpServletRequest request){
		PortalUser user = (PortalUser)request.getSession().getAttribute("proUser");
		if(user==null){
			String userName = request.getParameter("username");
			if(userName==null) return "";
			user = userService.getPortalUserByTel(userName);
		}
		request.setAttribute("username", user.getUserName());
		request.setAttribute("usermsg", request.getParameter("usermsg"));
		return "/mobile/lottery";
	}
	/**
	 * 提交选择好的彩票（机选或手选）
	 * @return
	 * deck
	 */
	@RequestMapping("saveLottery")
	@ResponseBody
	public String saveLottery(String betDetail,String numberSelectType,String usermsg,HttpServletRequest request){
		PortalUser user = (PortalUser)request.getSession().getAttribute("proUser");
		if(user==null){
			String userName = request.getParameter("username");
			if(userName==null) return "";
			user = userService.getPortalUserByTel(userName);
		}
		String ids[] = usermsg.split(",");
		if(lotteryService.getMessageListID(user.getId(),Integer.parseInt(ids[0]),Integer.parseInt(ids[1])).size()<1){
			return "不合法的请求！";
		}
		String userPhoneNumber = user.getUserName();
		String partnerId = PropertiesParam.partnerId;  //合作方代码--必填
		boolean partnerDebug = PropertiesParam.partnerDebug; // 是否打开调试模式--必填
		String transData = "";//交易数据。json字符串，在传输过程中可进行加密
		String partnerChannelId="";//合作方渠道ID，长度不超过64
		String partnerReserved=userPhoneNumber+","+usermsg+","+numberSelectType+","+user.getId();//合作方保留域。长度不超过100。
		String appId = "";//申报过的appId，当appId存在并且有效时，该appId可以有独立的配置生效。
		 UUID uuid = UUID.randomUUID();
		String partnerOrderNumber = uuid.toString(); //投注订单号（投注交易数据的唯一标识）。长度不超过32。合作方自行确保唯一。必填
		user.getUserName();  //用户手机号。---必填
		String userName = ""; //用户姓名。
		String lotteryId = "10001"; //玩法代码。10001-双色球；10003-七乐彩 --必填
//		String numberSelectType = "1";//1机选2单式自选 --必填
		String betTotalAmount ="1";//投注总注数。--必填
		String partnerCallbackURL = PropertiesParam.partnerCallbackURL;//回调地址 --必填
		String betMode = "101"; //投注方式。101：单式；102：复式。(暂时只支持单式) --必填
		String betDetails[] = betDetail.split(",");
		for (String string : betDetails) {
			if(string.length()!=2||Integer.parseInt(string)>33){
				return "不合法的请求";
			}
		}
		betDetail = "00106"+betDetails[0]+betDetails[1]+betDetails[2]+betDetails[3]
				+betDetails[4]+betDetails[5]+"01"+betDetails[6];
//		String betDetail = "宽东方充值送彩票了！" ;//注码细节描述 --必填
		/**
		 *        双色球和七乐彩机选注码表示方式为：前3位表示倍数，接下2个字节表示注码个数，后面跟随具体号码。对于一张票多注情况，每注注码依次连接，并且每注注码的具体号码按大小顺序记录。
				多条单式票：前三位表示倍数。
				    第一注注码实际为红球：010203040506，蓝球：07，倍数1
				    第二注注码实际为红球：010203040507，蓝球：08，倍数2
				    注码显示为001060102030405060107002060102030405070108
		 */
		LotteryTransData ltd = new LotteryTransData();
		LotteryTransData.BetInfo betInfo =ltd.new BetInfo();
		betInfo.setBetDetail(betDetail);
		betInfo.setBetMode(betMode);
		List<LotteryTransData.BetInfo> betInfoList = new ArrayList<>();
		betInfoList.add(betInfo);
		ltd.setAppId(appId);
		ltd.setBetInfoList(betInfoList);
		ltd.setLotteryId(lotteryId);
		ltd.setNumberSelectType(numberSelectType);
		ltd.setPartnerCallbackURL(partnerCallbackURL);
		ltd.setPartnerChannelId(partnerChannelId);
		ltd.setPartnerOrderNumber(partnerOrderNumber);
		ltd.setPartnerReserved(partnerReserved+","+betDetail);
		ltd.setUserName(userName);
		ltd.setUserPhoneNumber(userPhoneNumber);
		String param = "partnerId="+partnerId+"&partnerDebug="+partnerDebug+"&transData="+JSON.toJSONString(ltd);
//		System.out.println(JSON.toJSONString(ltd));
		lotteryService.saveLotteryTicketInfo(partnerOrderNumber, userPhoneNumber);
//		request.setAttribute("partnerId", partnerId);
//		request.setAttribute("transData", JSON.toJSONString(ltd));
		return HttpGetPost.sendPost("http://api.youkala.com/api.jsp",param);
	}
	/**
	 * 回调函数，返回彩票出单是否成功
	 * @param partnerId
	 * @param transData
	 * @return
	 * deck
	 */ 
	@RequestMapping("kdflottery")
	public String kdflottery(String partnerId,String transData,HttpServletRequest request){
//		System.out.println(transData);
		LotteryResponse lrp = JSON.parseObject(transData, LotteryResponse.class);
		
		lotteryService.updateTickeInfo(lrp);
		if(lrp.getResult()==0){
			
			request.setAttribute("lotterySuccessRed_1", lrp.getTicketInfoList().get(0).getBetDetail().substring(5,7));
			request.setAttribute("lotterySuccessRed_2", lrp.getTicketInfoList().get(0).getBetDetail().substring(7, 9));
			request.setAttribute("lotterySuccessRed_3", lrp.getTicketInfoList().get(0).getBetDetail().substring(9, 11));
			request.setAttribute("lotterySuccessRed_4", lrp.getTicketInfoList().get(0).getBetDetail().substring(11, 13));
			request.setAttribute("lotterySuccessRed_5", lrp.getTicketInfoList().get(0).getBetDetail().substring(13, 15));
			request.setAttribute("lotterySuccessRed_6", lrp.getTicketInfoList().get(0).getBetDetail().substring(15, 17));
			request.setAttribute("lotterySuccessBlue_1", lrp.getTicketInfoList().get(0).getBetDetail().substring(19,21));
			lotteryService.successUpdate( lrp.getPartnerReserved(),lrp.getIssueNumber(),lrp.getTicketInfoList().get(0).getBetDetail());
			return "/mobile/lotterysuccess";
		}else if(lrp.getResult()==2000||lrp.getResult()==2001){
			
		}else if(lrp.getResult()==2002){
			String strs[] =  lrp.getPartnerReserved().split(",");
			request.setAttribute("lotterySuccessRed_1",strs[5].substring(5, 7));
			request.setAttribute("lotterySuccessRed_2", strs[5].substring(7, 9));
			request.setAttribute("lotterySuccessRed_3", strs[5].substring(9, 11));
			request.setAttribute("lotterySuccessRed_4",strs[5].substring(11, 13));
			request.setAttribute("lotterySuccessRed_5",strs[5].substring(13, 15));
			request.setAttribute("lotterySuccessRed_6",strs[5].substring(15, 17));
			request.setAttribute("lotterySuccessBlue_1", strs[5].substring(19,21));
			request.setAttribute("username", strs[0]);
			request.setAttribute("usermsg", strs[1]+","+strs[2]);
			request.setAttribute("select", strs[3]);
			return "/mobile/lotteryerr";
		}else{
			String strs[] =  lrp.getPartnerReserved().split(",");
			request.setAttribute("lotterySuccessRed_1",strs[5].substring(5, 7));
			request.setAttribute("lotterySuccessRed_2", strs[5].substring(7, 9));
			request.setAttribute("lotterySuccessRed_3", strs[5].substring(9, 11));
			request.setAttribute("lotterySuccessRed_4",strs[5].substring(11, 13));
			request.setAttribute("lotterySuccessRed_5",strs[5].substring(13, 15));
			request.setAttribute("lotterySuccessRed_6",strs[5].substring(15, 17));
			request.setAttribute("lotterySuccessBlue_1", strs[5].substring(19,21));
			request.setAttribute("username", strs[0]);
			request.setAttribute("usermsg", strs[1]+","+strs[2]);
			request.setAttribute("select", strs[3]);
			return "/mobile/lotteryerr";
		}
		return "/mobile/lotterysuccess";
	}
	public static void main(String[] args) {}
}
