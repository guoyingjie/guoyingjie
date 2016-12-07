package com.kdf.portal.model;

import com.kdf.portal.model.Portalbas;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Config
  implements Serializable
{
  private static final long serialVersionUID = -7834052616912881869L;
  private Map<String, Portalbas> configMap = new ConcurrentHashMap();

  private static Config instance = new Config();

  public static Config getInstance()
  {
    return instance;
  }

  public Map<String, Portalbas> getConfigMap() {
    return this.configMap;
  }

  public void setConfigMap(Map<String, Portalbas> configMap) {
    this.configMap = configMap;
  }
}