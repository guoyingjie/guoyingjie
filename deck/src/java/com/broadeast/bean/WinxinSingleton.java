package com.broadeast.bean;
import java.util.HashMap;
import java.util.Map;

public class WinxinSingleton { 
	//缓存accessToken 的Map  ,map中包含 一个accessToken 和 缓存的时间戳
	private Map<String, String> map = new HashMap<>();

	private WinxinSingleton() {
	}

	private static WinxinSingleton single = null;

	// 静态工厂方法
	public static WinxinSingleton getInstance() {
		if (single == null) {
			single = new WinxinSingleton();
		}
		return single;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public static WinxinSingleton getSingle() {
		return single;
	}

	public static void setSingle(WinxinSingleton single) {
		WinxinSingleton.single = single;
	}
	

}
