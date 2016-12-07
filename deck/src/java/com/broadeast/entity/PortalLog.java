package com.broadeast.entity;

import java.sql.Date;

public class PortalLog {

	public PortalLog() {
		// TODO Auto-generated constructor stub
	}
/*
 * TerminalDevice	requestTime	routerMac	clientMac	routerIp	clientIp	routerType	url	nasid
*设备信息	请求时间	网管mac	设备mac	网管ip	设备ip	网管类型	来路url	设备名字
 */
	
	
	private String TerminalDevice;
	private String requestTime;
	private String routerMac;
	private String clientMac;
	private String routerIp;
	private String clientIp;
	private String routerType;
	private String url;
	private String nasid;
	public String getTerminalDevice() {
		return TerminalDevice;
	}
	public void setTerminalDevice(String terminalDevice) {
		TerminalDevice = terminalDevice;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	public String getRouterMac() {
		return routerMac;
	}
	public void setRouterMac(String routerMac) {
		this.routerMac = routerMac;
	}
	public String getClientMac() {
		return clientMac;
	}
	public void setClientMac(String clientMac) {
		this.clientMac = clientMac;
	}
	public String getRouterIp() {
		return routerIp;
	}
	public void setRouterIp(String routerIp) {
		this.routerIp = routerIp;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public String getRouterType() {
		return routerType;
	}
	public void setRouterType(String routerType) {
		this.routerType = routerType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getNasid() {
		return nasid;
	}
	public void setNasid(String nasid) {
		this.nasid = nasid;
	}
}
