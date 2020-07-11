package com.tt.miniapp.customer.service;

public class CustomerServiceParam {
  private String aId;
  
  private String appId;
  
  private String platform;
  
  private String userId;
  
  public CustomerServiceParam(String paramString1, String paramString2, String paramString3, String paramString4) {
    this.appId = paramString1;
    this.aId = paramString2;
    this.userId = paramString3;
    this.platform = paramString4;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("CustomerServiceParam{appId='");
    stringBuilder.append(this.appId);
    stringBuilder.append('\'');
    stringBuilder.append(", aId='");
    stringBuilder.append(this.aId);
    stringBuilder.append('\'');
    stringBuilder.append(", userId='");
    stringBuilder.append(this.userId);
    stringBuilder.append('\'');
    stringBuilder.append(", platform='");
    stringBuilder.append(this.platform);
    stringBuilder.append('\'');
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  public String toURL(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append("?appid=");
    stringBuilder.append(this.appId);
    stringBuilder.append("&aid=");
    stringBuilder.append(this.aId);
    stringBuilder.append("&uid=");
    stringBuilder.append(this.userId);
    stringBuilder.append("&os=");
    stringBuilder.append(this.platform);
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\customer\service\CustomerServiceParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */