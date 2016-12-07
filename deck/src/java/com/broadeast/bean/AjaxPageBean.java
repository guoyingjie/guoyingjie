package com.broadeast.bean;

import java.util.List;

/**
 * 用于往页面写分页json
 * @author xmm 
 *
 */
public class AjaxPageBean<T> { 
	private int code; 
	private int recordStart;
	private int recordEnd;
	private int curPage;//当前页
	private int pageSize;//页大小
	private int actualPageSize;//实际页大小
	private int totalPages;//总页数
	private List<T> data;//分页数据
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getRecordStart() {
		return recordStart;
	}
	public void setRecordStart(int recordStart) {
		this.recordStart = recordStart;
	}
	public int getRecordEnd() {
		return recordEnd;
	}
	public void setRecordEnd(int recordEnd) {
		this.recordEnd = recordEnd;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getActualPageSize() {
		return actualPageSize;
	}
	public void setActualPageSize(int actualPageSize) {
		this.actualPageSize = actualPageSize;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	
	public AjaxPageBean(int code) {
		this.code = code;
	}
	
	/**
	 * 添加自己编辑的List
	 * @param code
	 * @param curPage
	 * @param pageSize
	 * @param meibanPage
	 * @param totalPages
	 * @param data
	 */
	public AjaxPageBean(int code, int curPage,int pageSize, int meibanPage, int totalPages, List<T> data) {
		super();
		this.code = code;
		this.curPage = curPage;
		this.pageSize = pageSize;
		this.totalPages = totalPages;
		
		this.recordStart = (curPage-1)/meibanPage*meibanPage+1;	//设置分页	起始页
		//计算最后一页的公式，如果最大页max>(recordStart+pageSize-1)，则不用考虑最后一页
		//否则，则结束号设置为总页数的号码
		if(this.totalPages >(this.recordStart+meibanPage-1)){
			this.recordEnd = this.recordStart + meibanPage - 1;
		}else{
			this.recordEnd = this.totalPages ;
		}		
		
		this.data = data;
	}
	@Override
    public String toString() {
	    return "AjaxPageBean [code=" + code + ", recordStart=" + recordStart + ", recordEnd=" + recordEnd + ", curPage=" + curPage + ", pageSize=" + pageSize + ", actualPageSize=" + actualPageSize + ", totalPages=" + totalPages + ", data=" + data + "]";
    }
	
//	/**
//	 * 将Page直接装进去
//	 * @param code
//	 * @param curPage
//	 * @param pageSize
//	 * @param meibanPage
//	 * @param page
//	 */
//	public AjaxPageBean(int code,int curPage,int pageSize,int meibanPage,Page<T> page){
//		this.code = code;
//		this.curPage = curPage;
//		this.pageSize = pageSize;
//		this.totalPages = page.getTotalPages();
//		this.recordStart = (curPage-1)/meibanPage*meibanPage+1;	//设置分页	起始页
//		//计算最后一页的公式，如果最大页max>(recordStart+pageSize-1)，则不用考虑最后一页
//		//否则，则结束号设置为总页数的号码
//		if(this.totalPages >(this.recordStart+meibanPage-1)){
//			this.recordEnd = this.recordStart + meibanPage - 1;
//		}else{
//			this.recordEnd = this.totalPages ;
//		}
//		this.data = page.getContent();
//		
//	}
	

}
