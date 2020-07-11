package com.tt.miniapp.debug;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandServiceManager;
import com.tt.miniapp.mmkv.KVUtil;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;

public class SwitchManager extends AppbrandServiceManager.ServiceBase {
  private SwitchManager(AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramAppbrandApplicationImpl);
  }
  
  private SharedPreferences getPerformanceConfig(Context paramContext) {
    return KVUtil.getSharedPreferences(paramContext, "performance_config");
  }
  
  private SharedPreferences getVConsoleConfig(Context paramContext) {
    return KVUtil.getSharedPreferences(paramContext, "vconsole_config");
  }
  
  public boolean isPerformanceSwitchOn() {
    AppInfoEntity appInfoEntity = this.mApp.getAppInfo();
    return (appInfoEntity == null) ? false : (!appInfoEntity.isLocalTest() ? false : getPerformanceConfig((Context)AppbrandContext.getInst().getApplicationContext()).getBoolean(appInfoEntity.appId, false));
  }
  
  public boolean isVConsoleSwitchOn() {
    AppInfoEntity appInfoEntity = this.mApp.getAppInfo();
    return (appInfoEntity == null) ? false : (!appInfoEntity.isLocalTest() ? false : getVConsoleConfig((Context)AppbrandContext.getInst().getApplicationContext()).getBoolean(appInfoEntity.appId, false));
  }
  
  public void setPerformanceSwithOn(boolean paramBoolean) {
    AppInfoEntity appInfoEntity = this.mApp.getAppInfo();
    if (appInfoEntity != null) {
      String str = appInfoEntity.appId;
      if (!TextUtils.isEmpty(str))
        getPerformanceConfig((Context)AppbrandContext.getInst().getApplicationContext()).edit().putBoolean(str, paramBoolean).commit(); 
    } 
  }
  
  public void setVConsoleSwitchOn(Context paramContext, boolean paramBoolean) {
    AppInfoEntity appInfoEntity = this.mApp.getAppInfo();
    if (appInfoEntity != null) {
      String str = appInfoEntity.appId;
      if (!TextUtils.isEmpty(str))
        getVConsoleConfig(paramContext).edit().putBoolean(str, paramBoolean).commit(); 
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\debug\SwitchManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */