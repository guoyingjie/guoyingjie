package com.broadeast.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import sun.java2d.pipe.SpanShapeRenderer.Simple;

import com.broadeast.dao.PortalLogDao;
import com.broadeast.service.impl.TrialService;

/**
 * 执行提出到达试用截止时间的上网用户
 * @author Administrator
 *
 */
public class TrialUserLogoutTask {


	public void clearTrialUsers() {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");
		//查询到所有未踢除的用户设备Mac
		TrialService ts = SpringContextHolder.getBean("trialService");
		Calendar nowDate = Calendar.getInstance();
		nowDate.add(Calendar.HOUR, -1);
		List<Map<String, Object>> list = ts.getNotlogoutrecord(nowDate.getTime());
		//踢人，并更新踢人状态
		PortalLogDao pld = SpringContextHolder.getBean("portalLogDao");
		pld.batchUpdateKickMac(list);
		//更新踢人状态
		ts.batchUpdateIslogout(list);
	}
}
