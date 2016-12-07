package com.broadeast.util;

import com.alibaba.fastjson.JSON;

/**
 * 返值结果封装类
 * @ToDoWhat 
 * @author xmm
 */
public class ExecuteResult {
	private int code;
	private String msg;
	private Object data;

	public void setCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getData(){
		return data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String toJsonString(){
		return JSON.toJSONStringWithDateFormat(this, "yyyy-MM-dd HH:mm:ss");
	}
	
}
