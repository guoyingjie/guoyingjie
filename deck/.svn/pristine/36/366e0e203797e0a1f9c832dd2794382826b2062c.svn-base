package com.broadeast.util;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.broadeast.bean.AjaxPageBean;


/**
 * 用于数据分页展示。
 * 依赖的PagingORMService这个是数据持久化的接口
 * @author 肖明明
 *
 */

public class PagingFactory {
	
	
	

	
	/**获取总记录数
	 * @param sql
	 * @return
	 */
	private static String getTotalRecordSQL(String sql){
		return "select sum(1) totalNum from("+sql+") Tname";
	}
	
	/**
	 * 获取分页sql语句
	 * @param sql
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	private static String getPagingSQL(String sql,int pageNum,int pageSize){
		pageNum=(pageNum-1)<0?0:(pageNum-1);
		return "select Tname.* from ("+sql+") Tname limit "+pageNum*pageSize+","+pageSize;
	}
	
	/**
	 * 获取分页封装Bean
	 * @param us 数据库持久化实现类
	 * @param sqlStr sql查询语句，条件用？替代
	 * @param pageNum 页面提交的页码数
	 * @param pageSize 页面提交的每页记录数
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static  AjaxPageBean getPageNationResultList(JdbcTemplate jdbcTemplate,RowMapper rm,String sqlStr,int pageNum,int pageSize) throws Exception{
//		int totalNum=jdbcTemplate.queryForInt(getTotalRecordSQL(sqlStr));//get totalNum
		int totalNum=0;
		List<Map<String, Object>> lm=jdbcTemplate.queryForList(getTotalRecordSQL(sqlStr));//get totalNum
		if(lm.size()!=0&&lm.get(0).get("totalNum")!=null){totalNum=Integer.parseInt(lm.get(0).get("totalNum")+"");}
		
		pageNum=pageNum<1?1:pageNum;
		int totalPageNum=(totalNum%pageSize)>0?(totalNum/pageSize+1):totalNum/pageSize;
		pageNum=(pageNum>totalPageNum)?totalPageNum:pageNum;
		List ls=jdbcTemplate.query(getPagingSQL(sqlStr,pageNum,pageSize),rm);
		AjaxPageBean apb=new AjaxPageBean(200,pageNum,pageSize,5,totalPageNum,ls);
		return apb;
	}
	
	/**
	 * 带参数查询
	 * @param jdbcTemplate
	 * @param params
	 * @param rm
	 * @param sqlStr
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static  AjaxPageBean getPageNationResultList(JdbcTemplate jdbcTemplate,Object[] params,RowMapper rm,String sqlStr,int pageNum,int pageSize) throws Exception{
//		int totalNum=jdbcTemplate.queryForInt(getTotalRecordSQL(sqlStr),params);//get totalNum
		int totalNum=0;
		List<Map<String, Object>> lm=jdbcTemplate.queryForList(getTotalRecordSQL(sqlStr),params);//get totalNum
		if(lm.size()!=0&&lm.get(0).get("totalNum")!=null){totalNum=Integer.parseInt(lm.get(0).get("totalNum")+"");}
		
		pageNum=pageNum<1?1:pageNum;
		int totalPageNum=(totalNum%pageSize)>0?(totalNum/pageSize+1):totalNum/pageSize;
		pageNum=(pageNum>totalPageNum)?totalPageNum:pageNum;
		List ls=jdbcTemplate.query(getPagingSQL(sqlStr,pageNum,pageSize),params,rm);
		AjaxPageBean apb=new AjaxPageBean(200,pageNum,pageSize,5,totalPageNum,ls);
		return apb;
	}
	
	public static  AjaxPageBean getPageNationResultListMap(JdbcTemplate jdbcTemplate,Object[] params,String sqlStr,int pageNum,int pageSize) throws Exception{
//		int totalNum=jdbcTemplate.queryForInt(getTotalRecordSQL(sqlStr),params);//get totalNum
		int totalNum=0;
		List<Map<String, Object>> lm=jdbcTemplate.queryForList(getTotalRecordSQL(sqlStr),params);//get totalNum
		if(lm.size()!=0&&lm.get(0).get("totalNum")!=null){totalNum=Integer.parseInt(lm.get(0).get("totalNum")+"");}
		
		pageNum=pageNum<1?1:pageNum;
		int totalPageNum=(totalNum%pageSize)>0?(totalNum/pageSize+1):totalNum/pageSize;
		pageNum=(pageNum>totalPageNum)?totalPageNum:pageNum;
		List ls=jdbcTemplate.queryForList(getPagingSQL(sqlStr,pageNum,pageSize),params);
		AjaxPageBean apb=new AjaxPageBean(200,pageNum,pageSize,5,totalPageNum,ls);
		return apb;
	}
	
	
}
