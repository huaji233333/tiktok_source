package com.tt.miniapphost.entity;

public class SDKBuildConfigEntity {
  private String doraVersion;
  
  private String miniAppSdkVersion;
  
  private int miniAppSdkVersionCode;
  
  public SDKBuildConfigEntity(int paramInt, String paramString1, String paramString2) {
    this.miniAppSdkVersionCode = paramInt;
    this.miniAppSdkVersion = paramString1;
    this.doraVersion = paramString2;
  }
  
  public String getDoraVersion() {
    return this.doraVersion;
  }
  
  public String getMiniAppSdkVersion() {
    return this.miniAppSdkVersion;
  }
  
  public int getMiniAppSdkVersionCode() {
    return this.miniAppSdkVersionCode;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\entity\SDKBuildConfigEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */