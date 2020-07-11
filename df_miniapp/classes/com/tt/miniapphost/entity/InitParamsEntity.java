package com.tt.miniapphost.entity;

import android.os.Build;
import android.text.TextUtils;
import android.util.SparseArray;
import com.tt.miniapphost.util.DebugUtil;
import java.util.Locale;

public class InitParamsEntity {
  private String appId;
  
  private String appName;
  
  private String channel;
  
  private String deviceId;
  
  private String devicePlatform = "Android";
  
  private String feedbackAppKey;
  
  private String installId;
  
  private SparseArray<String> mHostConfigMap;
  
  private String osVersion = Build.VERSION.RELEASE;
  
  private String pluginVersion;
  
  private String shortcutClassName;
  
  private String tmaJssdkVersion;
  
  private String uaName;
  
  private String updateVersionCode;
  
  private String versionCode;
  
  private InitParamsEntity(Builder paramBuilder) {
    this.uaName = paramBuilder.uaName;
    this.appId = paramBuilder.appId;
    this.pluginVersion = paramBuilder.pluginVersion;
    this.versionCode = paramBuilder.versionCode;
    this.updateVersionCode = paramBuilder.updateVersionCode;
    this.channel = paramBuilder.channel;
    this.appName = paramBuilder.appName;
    this.mHostConfigMap = paramBuilder.strMap;
    this.deviceId = paramBuilder.deviceId;
    this.installId = paramBuilder.installId;
    this.feedbackAppKey = paramBuilder.feedbackAppKey;
  }
  
  public InitParamsEntity(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, SparseArray<String> paramSparseArray, String paramString11) {
    this.uaName = paramString1;
    this.appId = paramString2;
    this.pluginVersion = paramString3;
    this.versionCode = paramString4;
    this.updateVersionCode = paramString5;
    this.channel = paramString6;
    this.deviceId = paramString7;
    this.appName = paramString8;
    this.installId = paramString9;
    this.feedbackAppKey = paramString10;
    this.mHostConfigMap = paramSparseArray;
    this.shortcutClassName = paramString11;
  }
  
  public String getAppId() {
    return this.appId;
  }
  
  public String getAppName() {
    return this.appName;
  }
  
  public String getChannel() {
    return this.channel;
  }
  
  public String getDeviceId() {
    return this.deviceId;
  }
  
  public String getDevicePlatform() {
    return this.devicePlatform;
  }
  
  public String getFeedbackAppKey() {
    return this.feedbackAppKey;
  }
  
  public Boolean getHostBoolean(int paramInt, boolean paramBoolean) {
    String str = getHostStr(paramInt, null);
    if (TextUtils.isEmpty(str))
      return Boolean.valueOf(paramBoolean); 
    paramInt = -1;
    int i = str.hashCode();
    if (i != 3569038) {
      if (i == 97196323 && str.equals("false"))
        paramInt = 1; 
    } else if (str.equals("true")) {
      paramInt = 0;
    } 
    return (paramInt != 0) ? ((paramInt != 1) ? Boolean.valueOf(paramBoolean) : Boolean.valueOf(false)) : Boolean.valueOf(true);
  }
  
  public String getHostStr(int paramInt, String paramString) {
    SparseArray<String> sparseArray = this.mHostConfigMap;
    return (sparseArray != null) ? ((sparseArray.get(paramInt) == null) ? paramString : (String)this.mHostConfigMap.get(paramInt)) : paramString;
  }
  
  public String getInstallId() {
    return this.installId;
  }
  
  public String getOsVersion() {
    return this.osVersion;
  }
  
  public String getPluginVersion() {
    return this.pluginVersion;
  }
  
  public String getShortcutClassName() {
    return this.shortcutClassName;
  }
  
  public String getUaName() {
    return this.uaName;
  }
  
  public String getUpdateVersionCode() {
    return this.updateVersionCode;
  }
  
  public String getVersionCode() {
    return this.versionCode;
  }
  
  public boolean useWebLivePlayerWheRenderInBrowser(boolean paramBoolean) {
    return getHostBoolean(3001, paramBoolean).booleanValue();
  }
  
  public boolean useWebVideoWhenNotRenderInBrowser(boolean paramBoolean) {
    return getHostBoolean(3000, paramBoolean).booleanValue();
  }
  
  public static class Builder {
    public String appId = "";
    
    public String appName = "";
    
    public String channel = "";
    
    public String deviceId = "";
    
    public String feedbackAppKey = "";
    
    private Locale initLocale;
    
    public String installId = "";
    
    private boolean isEnableAppbundle;
    
    public String pluginVersion = "";
    
    private String shortcutClassName = "";
    
    public SparseArray<String> strMap;
    
    public String uaName = "";
    
    public String updateVersionCode = "";
    
    public String versionCode = "";
    
    public InitParamsEntity build() {
      return new InitParamsEntity(this.uaName, this.appId, this.pluginVersion, this.versionCode, this.updateVersionCode, this.channel, this.deviceId, this.appName, this.installId, this.feedbackAppKey, this.strMap, this.shortcutClassName);
    }
    
    public Builder setAppId(String param1String) {
      if (!DebugUtil.debug() || !TextUtils.isEmpty(param1String)) {
        this.appId = param1String;
        return this;
      } 
      throw new IllegalStateException("appId can not empty");
    }
    
    public Builder setAppName(String param1String) {
      if (!DebugUtil.debug() || !TextUtils.isEmpty(param1String)) {
        this.appName = param1String;
        return this;
      } 
      throw new IllegalStateException("appName can not empty");
    }
    
    public Builder setChannel(String param1String) {
      if (!DebugUtil.debug() || !TextUtils.isEmpty(param1String)) {
        this.channel = param1String;
        return this;
      } 
      throw new IllegalStateException("channel can not empty");
    }
    
    @Deprecated
    public Builder setDeviceId(String param1String) {
      this.deviceId = param1String;
      return this;
    }
    
    public Builder setEnableAppbundle(boolean param1Boolean) {
      this.isEnableAppbundle = param1Boolean;
      return this;
    }
    
    public Builder setFeedbackAppKey(String param1String) {
      this.feedbackAppKey = param1String;
      return this;
    }
    
    public Builder setInitLocale(Locale param1Locale) {
      this.initLocale = param1Locale;
      return this;
    }
    
    public Builder setInstallId(String param1String) {
      this.installId = param1String;
      return this;
    }
    
    public Builder setPluginVersion(String param1String) {
      this.pluginVersion = param1String;
      return this;
    }
    
    public Builder setShortcutClassName(String param1String) {
      this.shortcutClassName = param1String;
      return this;
    }
    
    public Builder setStrMap(SparseArray<String> param1SparseArray) {
      this.strMap = param1SparseArray;
      return this;
    }
    
    public Builder setUaName(String param1String) {
      this.uaName = param1String;
      return this;
    }
    
    public Builder setUpdateVersionCode(String param1String) {
      this.updateVersionCode = param1String;
      return this;
    }
    
    public Builder setVersionCode(String param1String) {
      if (!DebugUtil.debug() || !TextUtils.isEmpty(param1String)) {
        this.versionCode = param1String;
        return this;
      } 
      throw new IllegalStateException("versionCode can not empty");
    }
  }
  
  public static class HostConfigValue {}
  
  public static class HostKey {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\entity\InitParamsEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */