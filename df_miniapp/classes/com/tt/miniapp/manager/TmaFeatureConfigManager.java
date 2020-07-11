package com.tt.miniapp.manager;

import android.content.Context;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import org.json.JSONObject;

public class TmaFeatureConfigManager {
  private boolean isTmgShowLoadingBgEnable;
  
  private long showLoadingBgDelayTime;
  
  private TmaFeatureConfigManager() {
    initTmaFeatureConfigs();
  }
  
  public static TmaFeatureConfigManager getInstance() {
    return Holder.mOurInstance;
  }
  
  private void initTmaFeatureConfigs() {
    boolean bool;
    JSONObject jSONObject = SettingsDAO.getJSONObject((Context)AppbrandContext.getInst().getApplicationContext(), new Enum[] { (Enum)Settings.BDP_SHOW_LOADING_BG });
    if (jSONObject == null) {
      AppBrandLogger.e("TmaFeatureConfigManager", new Object[] { "fetch no settings config" });
      return;
    } 
    if (jSONObject.optInt("tmg_show_loading_bg_enable", 0) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    this.isTmgShowLoadingBgEnable = bool;
    this.showLoadingBgDelayTime = jSONObject.optLong("delay_time", 3000L);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(Settings.BDP_SHOW_LOADING_BG.toString());
    stringBuilder.append(jSONObject);
    AppBrandLogger.d("TmaFeatureConfigManager", new Object[] { stringBuilder.toString() });
  }
  
  public long getShowLoadingBgDelayTime() {
    return this.showLoadingBgDelayTime;
  }
  
  public boolean isTmgShowLoadingBgEnable() {
    return this.isTmgShowLoadingBgEnable;
  }
  
  static class Holder {
    static final TmaFeatureConfigManager mOurInstance = new TmaFeatureConfigManager();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\TmaFeatureConfigManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */