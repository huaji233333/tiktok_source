package com.tt.miniapp.settings.data;

import android.content.Context;
import android.content.SharedPreferences;
import com.storage.async.Action;
import com.storage.async.Schedulers;
import com.tt.miniapp.manager.basebundle.BaseBundleManager;
import com.tt.miniapp.mmkv.KVUtil;
import com.tt.miniapp.offlinezip.OfflineZipManager;
import com.tt.miniapp.settings.configs.SettingsConfigImp;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppbrandConstants;
import com.tt.miniapphost.AppbrandContext;

public class SettingsManager {
  private SettingsManager() {
    SettingsHandler.init((new SettingsConfigImp()).create());
  }
  
  public static SettingsManager getInstance() {
    return Holder.INSTANCE;
  }
  
  public void forceUpdateSettingsAndBasebundle(final BaseBundleManager.BaseBundleUpdateListener listener) {
    getInstance().updateSettings(new SettingsUpdateListener() {
          public void onUpdateComplete() {
            BaseBundleManager.getInst().checkUpdateBaseBundle((Context)AppbrandContext.getInst().getApplicationContext(), false, new BaseBundleManager.BaseBundleUpdateListener() {
                  public void onBaseBundleUpdate() {
                    listener.onBaseBundleUpdate();
                  }
                });
          }
        });
  }
  
  public void updateNeedUpdateSettings(final int needUpdateSettings) {
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            SharedPreferences sharedPreferences = KVUtil.getSharedPreferences((Context)AppbrandContext.getInst().getApplicationContext(), "settings_config");
            if (sharedPreferences.getInt("is_need_update_settings", 0) != needUpdateSettings) {
              sharedPreferences.edit().putInt("is_need_update_settings", needUpdateSettings).apply();
              SettingsManager.getInstance().updateSettings(new SettingsUpdateListener() {
                    public void onUpdateComplete() {
                      AppbrandConstants.getBundleManager().checkUpdateBaseBundle((Context)AppbrandContext.getInst().getApplicationContext());
                    }
                  },  true);
            } 
          }
        }Schedulers.longIO());
  }
  
  public void updateSettings() {
    getInstance().updateSettings(new SettingsUpdateListener() {
          public void onUpdateComplete() {}
        });
  }
  
  public void updateSettings(SettingsUpdateListener paramSettingsUpdateListener) {
    updateSettings(paramSettingsUpdateListener, false);
  }
  
  public void updateSettings(final SettingsUpdateListener listener, final boolean isForce) {
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            SharedPreferences sharedPreferences = KVUtil.getSharedPreferences((Context)AppbrandContext.getInst().getApplicationContext(), "settings_config");
            if (sharedPreferences != null && (isForce || System.currentTimeMillis() - sharedPreferences.getLong("LAST_REQUEST_TIME_KEY", 0L) > 3600000L)) {
              sharedPreferences.edit().putLong("LAST_REQUEST_TIME_KEY", System.currentTimeMillis()).apply();
              SettingsHandler.updateSettings();
            } 
            SettingsHandler.registerOrNotifyListener(new SettingsUpdateListener() {
                  public void onUpdateComplete() {
                    if (listener != null)
                      listener.onUpdateComplete(); 
                    SettingsHandler.unRegisterListener(this);
                  }
                });
          }
        }ThreadPools.longIO());
  }
  
  public void updateSettingsWhenHostRestart() {
    getInstance().updateSettings(new SettingsUpdateListener() {
          public void onUpdateComplete() {
            OfflineZipManager.INSTANCE.checkUpdateOfflineZip((Context)AppbrandContext.getInst().getApplicationContext(), null, new String[0]);
            AppbrandConstants.getBundleManager().checkUpdateBaseBundle((Context)AppbrandContext.getInst().getApplicationContext());
          }
        }true);
  }
  
  static class Holder {
    public static SettingsManager INSTANCE = new SettingsManager();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\settings\data\SettingsManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */