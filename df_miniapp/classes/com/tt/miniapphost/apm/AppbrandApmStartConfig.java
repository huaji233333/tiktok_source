package com.tt.miniapphost.apm;

import org.json.JSONObject;

public class AppbrandApmStartConfig {
  private int mAid;
  
  private String mAppVersion;
  
  private String mChannel;
  
  private String mDeviceId;
  
  private JSONObject mExtra;
  
  private ApmStartListener mStartListener;
  
  private String mUpdateVersionCode;
  
  public AppbrandApmStartConfig(Builder paramBuilder) {
    this.mAid = paramBuilder.aid;
    this.mDeviceId = paramBuilder.deviceId;
    this.mChannel = paramBuilder.channel;
    this.mAppVersion = paramBuilder.appVersion;
    this.mUpdateVersionCode = paramBuilder.updateVersionCode;
    this.mExtra = paramBuilder.extra;
    this.mStartListener = paramBuilder.listener;
  }
  
  public int getAid() {
    return this.mAid;
  }
  
  public String getAppVersion() {
    return this.mAppVersion;
  }
  
  public String getChannel() {
    return this.mChannel;
  }
  
  public String getDeviceId() {
    return this.mDeviceId;
  }
  
  public JSONObject getExtra() {
    return this.mExtra;
  }
  
  public ApmStartListener getStartListener() {
    return this.mStartListener;
  }
  
  public String getUpdateVersionCode() {
    return this.mUpdateVersionCode;
  }
  
  public static class Builder {
    public int aid;
    
    public String appVersion;
    
    public String channel;
    
    public String deviceId;
    
    public JSONObject extra;
    
    public ApmStartListener listener;
    
    public String updateVersionCode;
    
    public Builder aid(int param1Int) {
      this.aid = param1Int;
      return this;
    }
    
    public Builder appVersion(String param1String) {
      this.appVersion = param1String;
      return this;
    }
    
    public AppbrandApmStartConfig build() {
      return new AppbrandApmStartConfig(this);
    }
    
    public Builder channel(String param1String) {
      this.channel = param1String;
      return this;
    }
    
    public Builder deviceId(String param1String) {
      this.deviceId = param1String;
      return this;
    }
    
    public Builder extra(JSONObject param1JSONObject) {
      this.extra = param1JSONObject;
      return this;
    }
    
    public Builder startListener(ApmStartListener param1ApmStartListener) {
      this.listener = param1ApmStartListener;
      return this;
    }
    
    public Builder updateVersionCode(String param1String) {
      this.updateVersionCode = param1String;
      return this;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\apm\AppbrandApmStartConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */