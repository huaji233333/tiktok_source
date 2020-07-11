package com.tt.miniapphost;

import android.content.Context;
import android.text.TextUtils;
import com.tt.miniapp.manager.basebundle.BaseBundleManager;
import com.tt.option.q.d;
import java.util.HashMap;
import java.util.Map;

public class ParamManager {
  static Map<String, String> baseParam;
  
  static String doraVersion;
  
  static String fullAppSdkVersion;
  
  static String jsEngineVersion;
  
  static String jsSdkVersion;
  
  static String mCurrentSessionId;
  
  static String miniAppSdkVersion;
  
  static int miniAppSdkVersionCode;
  
  private static String createSessionId() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(d.a());
    stringBuilder.append(System.currentTimeMillis());
    return stringBuilder.toString();
  }
  
  public static Map<String, String> getBaseEventParam(Context paramContext) {
    Map<String, String> map = baseParam;
    Map map1 = null;
    String str = "";
    if (map == null && paramContext != null) {
      baseParam = new HashMap<String, String>();
      if (BaseBundleManager.getInst() != null) {
        str1 = BaseBundleManager.getInst().getSdkCurrentVersionStr(paramContext);
      } else {
        map = null;
      } 
      if (!TextUtils.isEmpty((CharSequence)map))
        baseParam.put("tma_jssdk_version", map); 
      if (AppbrandApplication.getInst() != null) {
        if (AppbrandApplication.getInst().getAppInfo() != null) {
          str1 = (AppbrandApplication.getInst().getAppInfo()).appId;
        } else {
          str1 = "";
        } 
        if (!TextUtils.isEmpty(str1))
          baseParam.put("tma_app_id", str1); 
      } 
      if (AppbrandContext.getInst().getInitParams() != null) {
        str1 = AppbrandContext.getInst().getInitParams().getAppId();
      } else {
        str1 = "";
      } 
      if (!TextUtils.isEmpty(str1))
        baseParam.put("host_app_id", str1); 
      if (AppbrandContext.getInst().getInitParams() != null) {
        str1 = AppbrandContext.getInst().getInitParams().getPluginVersion();
      } else {
        str1 = "";
      } 
      if (!TextUtils.isEmpty(str1))
        baseParam.put("tma_plugin_version", str1); 
      String str1 = getMiniAppSdkVersion();
      if (!TextUtils.isEmpty(str1))
        baseParam.put("tma_sdk_version", str1); 
      str1 = getDoraVersion();
      if (!TextUtils.isEmpty(str1))
        baseParam.put("tma_dora_version", str1); 
    } 
    if (TextUtils.isEmpty(baseParam.get("tma_dora_version"))) {
      String str1 = getDoraVersion();
      baseParam.put("tma_dora_version", str1);
    } 
    if (TextUtils.isEmpty(baseParam.get("tma_sdk_version"))) {
      String str1 = getMiniAppSdkVersion();
      baseParam.put("tma_sdk_version", str1);
    } 
    if (TextUtils.isEmpty(baseParam.get("tma_plugin_version"))) {
      String str1;
      if (AppbrandContext.getInst().getInitParams() != null) {
        str1 = AppbrandContext.getInst().getInitParams().getPluginVersion();
      } else {
        str1 = "";
      } 
      baseParam.put("tma_plugin_version", str1);
    } 
    if (TextUtils.isEmpty(baseParam.get("host_app_id"))) {
      String str1;
      if (AppbrandContext.getInst().getInitParams() != null) {
        str1 = AppbrandContext.getInst().getInitParams().getAppId();
      } else {
        str1 = "";
      } 
      baseParam.put("host_app_id", str1);
    } 
    if (TextUtils.isEmpty(baseParam.get("tma_app_id")) && AppbrandApplication.getInst() != null) {
      String str1 = str;
      if (AppbrandApplication.getInst().getAppInfo() != null)
        str1 = (AppbrandApplication.getInst().getAppInfo()).appId; 
      baseParam.put("tma_app_id", str1);
    } 
    if (TextUtils.isEmpty(baseParam.get("tma_jssdk_version"))) {
      String str1;
      map = map1;
      if (BaseBundleManager.getInst() != null)
        str1 = BaseBundleManager.getInst().getSdkCurrentVersionStr(paramContext); 
      baseParam.put("tma_jssdk_version", str1);
    } 
    return baseParam;
  }
  
  public static String getDoraVersion() {
    if (TextUtils.isEmpty(doraVersion) && AppbrandContext.getInst().getBuildConfig() != null)
      doraVersion = AppbrandContext.getInst().getBuildConfig().getDoraVersion(); 
    return doraVersion;
  }
  
  public static String getFullAppSdkVersion() {
    if (TextUtils.isEmpty(fullAppSdkVersion) && AppbrandContext.getInst().getBuildConfig() != null)
      fullAppSdkVersion = AppbrandContext.getInst().getBuildConfig().getMiniAppSdkVersion(); 
    return fullAppSdkVersion;
  }
  
  public static String getJsSdkVersion(Context paramContext) {
    if (paramContext == null)
      return jsSdkVersion; 
    if (!TextUtils.isEmpty(jsSdkVersion))
      return jsSdkVersion; 
    BaseBundleManager baseBundleManager = BaseBundleManager.getInst();
    if (baseBundleManager != null)
      jsSdkVersion = baseBundleManager.getSdkCurrentVersionStr(paramContext); 
    return jsSdkVersion;
  }
  
  public static String getMiniAppSdkVersion() {
    String str = getFullAppSdkVersion();
    if (TextUtils.isEmpty(miniAppSdkVersion) && !TextUtils.isEmpty(str))
      miniAppSdkVersion = str.replaceAll("(\\d+)\\.(\\d+)\\.(\\d+).*", "$1.$2.$3"); 
    return miniAppSdkVersion;
  }
  
  public static int getMiniAppSdkVersionCode() {
    if (miniAppSdkVersionCode == 0 && AppbrandContext.getInst().getBuildConfig() != null)
      miniAppSdkVersionCode = AppbrandContext.getInst().getBuildConfig().getMiniAppSdkVersionCode(); 
    return miniAppSdkVersionCode;
  }
  
  public static String getSessionId() {
    return mCurrentSessionId;
  }
  
  public static void newSession() {
    mCurrentSessionId = createSessionId();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\ParamManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */