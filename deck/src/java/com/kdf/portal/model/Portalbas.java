package com.kdf.portal.model;

import java.io.Serializable;

public class Portalbas
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Long id;
  private String basname;
  private String basIp;
  private String basPort;
  private String portalVer;
  private String authType;
  private String timeoutSec;
  private String sharedSecret;
  private String portalPort;
  private String bas;
  private String isdebug;
  private String verifyCode;
  private String userHeart;
  private String userHeartCount;
  private String userHeartTime;
  private String authInterface;
  private String basUser;
  private String basPwd;
  private String accountAdd;
  private String isKick;
  private Long web;

  public Long getId()
  {
    return this.id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getBasname() {
    return this.basname;
  }
  public void setBasname(String basname) {
    this.basname = basname;
  }
  public String getBasIp() {
    return this.basIp;
  }
  public void setBasIp(String basIp) {
    this.basIp = basIp;
  }
  public String getBasPort() {
    return this.basPort;
  }
  public void setBasPort(String basPort) {
    this.basPort = basPort;
  }
  public String getPortalVer() {
    return this.portalVer;
  }
  public void setPortalVer(String portalVer) {
    this.portalVer = portalVer;
  }
  public String getAuthType() {
    return this.authType;
  }
  public void setAuthType(String authType) {
    this.authType = authType;
  }
  public String getTimeoutSec() {
    return this.timeoutSec;
  }
  public void setTimeoutSec(String timeoutSec) {
    this.timeoutSec = timeoutSec;
  }
  public String getSharedSecret() {
    return this.sharedSecret;
  }
  public void setSharedSecret(String sharedSecret) {
    this.sharedSecret = sharedSecret;
  }
  public String getPortalPort() {
    return this.portalPort;
  }
  public void setPortalPort(String portalPort) {
    this.portalPort = portalPort;
  }
  public String getBas() {
    return this.bas;
  }
  public void setBas(String bas) {
    this.bas = bas;
  }
  public String getIsdebug() {
    return this.isdebug;
  }
  public void setIsdebug(String isdebug) {
    this.isdebug = isdebug;
  }
  public String getVerifyCode() {
    return this.verifyCode;
  }
  public void setVerifyCode(String verifyCode) {
    this.verifyCode = verifyCode;
  }
  public String getUserHeart() {
    return this.userHeart;
  }
  public void setUserHeart(String userHeart) {
    this.userHeart = userHeart;
  }
  public String getUserHeartCount() {
    return this.userHeartCount;
  }
  public void setUserHeartCount(String userHeartCount) {
    this.userHeartCount = userHeartCount;
  }
  public String getUserHeartTime() {
    return this.userHeartTime;
  }
  public void setUserHeartTime(String userHeartTime) {
    this.userHeartTime = userHeartTime;
  }
  public String getAuthInterface() {
    return this.authInterface;
  }
  public void setAuthInterface(String authInterface) {
    this.authInterface = authInterface;
  }
  public String getBasUser() {
    return this.basUser;
  }
  public void setBasUser(String basUser) {
    this.basUser = basUser;
  }
  public String getBasPwd() {
    return this.basPwd;
  }
  public void setBasPwd(String basPwd) {
    this.basPwd = basPwd;
  }
  public String getAccountAdd() {
    return this.accountAdd;
  }
  public void setAccountAdd(String accountAdd) {
    this.accountAdd = accountAdd;
  }
  public String getIsKick() {
    return this.isKick;
  }
  public void setIsKick(String isKick) {
    this.isKick = isKick;
  }
  public Long getWeb() {
    return this.web;
  }
  public void setWeb(Long web) {
    this.web = web;
  }
  public String toString() {
    return "Portalbas [id=" + this.id + ",basname=" + this.basname + ",basIp=" + this.basIp + ",basPort=" + this.basPort + ",portalVer=" + this.portalVer + ",authType=" + this.authType + ",timeoutSec=" + this.timeoutSec + ",sharedSecret=" + this.sharedSecret + ",portalPort=" + this.portalPort + ",bas=" + this.bas + ",isdebug=" + this.isdebug + ",verifyCode=" + this.verifyCode + ",userHeart=" + this.userHeart + ",userHeartCount=" + this.userHeartCount + ",userHeartTime=" + this.userHeartTime + ",authInterface=" + this.authInterface + ",basUser=" + this.basUser + ",basPwd=" + this.basPwd + ",accountAdd=" + this.accountAdd + ",isKick=" + this.isKick + ",web=" + this.web + "]";
  }
}