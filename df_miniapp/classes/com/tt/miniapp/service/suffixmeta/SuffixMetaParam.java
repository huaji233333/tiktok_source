package com.tt.miniapp.service.suffixmeta;

public class SuffixMetaParam {
  private String appId;
  
  private String deviceId;
  
  private String hostAppId;
  
  public SuffixMetaParam(String paramString1, String paramString2, String paramString3) {
    this.appId = paramString1;
    this.hostAppId = paramString2;
    this.deviceId = paramString3;
  }
  
  public String getAppId() {
    return this.appId;
  }
  
  public String getDeviceId() {
    return this.deviceId;
  }
  
  public String getHostAppId() {
    return this.hostAppId;
  }
  
  public void setAppId(String paramString) {
    this.appId = paramString;
  }
  
  public void setDeviceId(String paramString) {
    this.deviceId = paramString;
  }
  
  public void setHostAppId(String paramString) {
    this.hostAppId = paramString;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\service\suffixmeta\SuffixMetaParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */