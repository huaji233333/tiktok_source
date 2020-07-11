package com.tt.miniapphost.entity;

public class BaseBundleEntity {
  private long buildInBundleVersion = -1L;
  
  private String jssdkVer;
  
  private long jssdkVerCode;
  
  private volatile String jssdkVerUpdate;
  
  private String latestSdkUrl;
  
  public long getBuildInBundleVersion() {
    return this.buildInBundleVersion;
  }
  
  public String getJssdkVer() {
    return this.jssdkVer;
  }
  
  public long getJssdkVerCode() {
    return this.jssdkVerCode;
  }
  
  public String getJssdkVerUpdate() {
    return this.jssdkVerUpdate;
  }
  
  public String getLatestSdkUrl() {
    return this.latestSdkUrl;
  }
  
  public void setBuildInBundleVersion(long paramLong) {
    this.buildInBundleVersion = paramLong;
  }
  
  public void setJssdkVer(String paramString) {
    this.jssdkVer = paramString;
  }
  
  public void setJssdkVerCode(long paramLong) {
    this.jssdkVerCode = paramLong;
  }
  
  public void setJssdkVerUpdate(String paramString) {
    this.jssdkVerUpdate = paramString;
  }
  
  public void setLatestSdkUrl(String paramString) {
    this.latestSdkUrl = paramString;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("BaseBundleEntity{buildin version=");
    stringBuilder.append(this.buildInBundleVersion);
    stringBuilder.append("jssdkVer='");
    stringBuilder.append(this.jssdkVer);
    stringBuilder.append('\'');
    stringBuilder.append(", jssdkVerCode=");
    stringBuilder.append(this.jssdkVerCode);
    stringBuilder.append(", jssdkVerUpdate='");
    stringBuilder.append(this.jssdkVerUpdate);
    stringBuilder.append('\'');
    stringBuilder.append(", latestSdkUrl='");
    stringBuilder.append(this.latestSdkUrl);
    stringBuilder.append('\'');
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\entity\BaseBundleEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */