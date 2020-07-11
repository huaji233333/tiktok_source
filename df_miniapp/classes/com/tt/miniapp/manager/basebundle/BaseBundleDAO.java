package com.tt.miniapp.manager.basebundle;

import android.content.Context;
import android.content.SharedPreferences;
import com.tt.miniapp.mmkv.KVUtil;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;

public class BaseBundleDAO {
  private static SharedPreferences getAppbrandSP(Context paramContext) {
    return KVUtil.getSharedPreferences(paramContext, "appbrand_file");
  }
  
  static long getBuildInJsSdkVersion(Context paramContext) {
    return getAppbrandSP(paramContext).getLong("build_in_bundle_version", -1L);
  }
  
  public static SharedPreferences getJsSdkSP(Context paramContext) {
    return KVUtil.getSharedPreferences(paramContext, "tma_jssdk_info");
  }
  
  public static long getLastBundleVersion(Context paramContext) {
    return getAppbrandSP(paramContext).getLong("latest_bundle_version", 0L);
  }
  
  public static String getLastVersionName(Context paramContext) {
    return getAppbrandSP(paramContext).getString("latest_version_name", "");
  }
  
  static boolean isJsSdkShouldDownload(Context paramContext) {
    return getJsSdkSP(paramContext).getBoolean("should_download", false);
  }
  
  public static boolean isLocalTestBundleSwitch(Context paramContext) {
    return getAppbrandSP(paramContext).getBoolean("base_local_test_bundle_update_switch", false);
  }
  
  public static boolean isVdomNotCompareVersionCode(Context paramContext) {
    return getAppbrandSP(paramContext).getBoolean("vdom_version_code", false);
  }
  
  static void setBuildInJsSdkVersion(Context paramContext, long paramLong) {
    getAppbrandSP(paramContext).edit().putLong("build_in_bundle_version", paramLong).apply();
  }
  
  static void setJsSdkShouldDownload(Context paramContext, boolean paramBoolean) {
    getJsSdkSP(paramContext).edit().putBoolean("should_download", paramBoolean).apply();
  }
  
  public static void setLastBundleVersion(Context paramContext, long paramLong) {
    getAppbrandSP(paramContext).edit().putLong("latest_bundle_version", paramLong).apply();
  }
  
  public static void setLastVersionName(Context paramContext, String paramString) {
    getAppbrandSP(paramContext).edit().putString("latest_version_name", paramString).apply();
  }
  
  public static void setLocalTestBundleSwitch(Context paramContext, boolean paramBoolean) {
    getAppbrandSP(paramContext).edit().putBoolean("base_local_test_bundle_update_switch", paramBoolean).apply();
  }
  
  public static void setVdomNotCompareVersionSwitch(Context paramContext, boolean paramBoolean) {
    getAppbrandSP(paramContext).edit().putBoolean("vdom_version_code", paramBoolean).apply();
  }
  
  @Deprecated
  public static void updateMMKVSwitch(Context paramContext) {
    SettingsDAO.getInt(paramContext, 0, new Enum[] { (Enum)Settings.TT_TMA_SWITCH, (Enum)Settings.TmaSwitch.MMKV_SWITCH });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\basebundle\BaseBundleDAO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */