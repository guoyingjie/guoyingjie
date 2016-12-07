package com.broadeast.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.nutz.dao.Dao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.broadeast.bean.LotteryResponse;

/**
 * @author pengxw E-mail:pengxianwei@kdfwifi.com
 * @version 创建时间：2016年9月1日 下午4:19:24
 * @describe
 */
@Service
@SuppressWarnings("all")
public class LotteryService {
	Logger logger = Logger.getLogger(MyselfPayService.class);
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Resource(name = "nutDao")
	private Dao nutDao;
	//添加用户和彩票编号
	public int saveLotteryTicketInfo(String partnerOrderNumber,String userTel){
		String sql = "INSERT INTO t5_lottery_trans_data(partner_order_number,user_tel) VALUES (?,?)";
		try{
		int back = jdbcTemplate.update(sql,new Object[]{partnerOrderNumber,userTel});
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	//将出彩反馈的信息更新到数据库中
	public int updateTickeInfo(LotteryResponse lrp){
		String sql = "UPDATE t5_lottery_trans_data SET trans_date_time=?,order_status=?,order_number=?,result=?,result_desc=?,issue_number=?,bet_succ_amount=?,order_accept_time=? WHERE partner_order_number=?";
		int back = jdbcTemplate.update(sql,new Object[]{lrp.getTransDateTime(),lrp.getOrderStatus(),lrp.getOrderNumber(),lrp.getResult(),lrp.getResultDesc(),lrp.getIssueNumber(),lrp.getBetSuccAmount(),lrp.getOrderAcceptTime(),lrp.getPartnerOrderNumber()});
		if(back>0&&lrp.getResult()==0){
		sql = "INSERT INTO t5_lottery_ticket_info(ticket_id,bet_date_time,bet_detail) VALUES(?,?,?)";
		back = jdbcTemplate.update(sql,new Object[]{lrp.getTicketInfoList().get(0).getTicketId(),lrp.getTicketInfoList().get(0).getBetDateTime(),lrp.getTicketInfoList().get(0).getBetDetail()});	
		}
		return back;
	}
	//判断用户是否属于盗链领取彩票
	public List<Integer> getMessageListID(int userId,int selfID,int siteID){
		String sql = "SELECT t2self.id FROM t2_self_user_message t2self,t2_site_user_message t2site WHERE t2self.message_id=t2site.id AND t2self.user_id=t2site.user_id AND t2site.state =3 AND t2self.user_id=? AND t2self.id=? AND t2site.id=?";
		List list = jdbcTemplate.queryForList(sql, new Object[]{userId,selfID,siteID});
		List<Integer> listInt = new ArrayList<>();
		for(int i=0;i<list.size();i++){                    
			Map userMap=(Map) list.get(i);  
			listInt.add((Integer)(userMap.get("id")));
			}
		
		return listInt;
	}
	//参数为有卡用户保留域   0：username 1:用户消息id 2：场所消息id 3:机选/手选 4:用户列表ID
	public int successUpdate(String param,String issueNumber,String lotteryNum){
		String strs[] = param.split(",");
		String sql = "DELETE  FROM t2_self_user_message  WHERE user_id=? AND id=? ";
		try{
		int back =jdbcTemplate.update(sql, new Object[]{strs[4],strs[1]});
		if(back<1){
			logger.error("彩票领取成功后删除消息时未发现要删除的记录，是否被盗链了呢！");
		}
		}catch(Exception e){
			logger.error(e.getLocalizedMessage());
		}finally{
			sql = "UPDATE t2_site_user_message SET state=404 WHERE user_id=? AND id= ? AND state=3";
			int back =jdbcTemplate.update(sql, new Object[]{strs[4],strs[2]});
			if(back<1){
				logger.error("彩票领取成功后更新消息状态为404时未找到此ID下的消息！");
			}
			
			sql = "INSERT INTO t2_site_user_message(user_id,content,state,create_time) VALUES(?,?,1,NOW())";
			
			try{
			back =jdbcTemplate.update(sql, new Object[]{strs[4],lotteryNum+","+issueNumber});
			}catch(Exception e){
				e.printStackTrace();
			}
			
			if(back<1){
				logger.error("彩票领取成功后添加彩票记录时失败！");
			}
		}
		
		return 0;
	}
}
