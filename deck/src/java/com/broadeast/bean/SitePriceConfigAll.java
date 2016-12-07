package com.broadeast.bean;

import java.util.ArrayList;
import java.util.List;

import com.broadeast.entity.CloudSite;
import com.broadeast.entity.SitePriceConfig;

/**
 * 本场所下 所有收费信息
 * 
 * @author Administrator
 * 
 */
public class SitePriceConfigAll {
	private CloudSite siteInof;
	private List<SitePriceConfig> list;

	public CloudSite getSiteInof() {
		return siteInof;
	}

	public void setSiteInof(CloudSite siteInof) {
		this.siteInof = siteInof;
	}

	public List<SitePriceConfig> getList() {
		return list;
	}

	public void setList(List<SitePriceConfig> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "SitePriceConfigAll [siteInof=" + siteInof + ", list=" + list
				+ "]";
	}


}
