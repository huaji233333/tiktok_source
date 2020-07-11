package com.tt.miniapp.rtc;

import android.content.Context;
import android.content.SharedPreferences;
import com.storage.async.Action;
import com.tt.miniapp.mmkv.KVUtil;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.option.d;

public class RtcHelper {
  private static SharedPreferences getSharedPreference() {
    return KVUtil.getSharedPreferences((Context)AppbrandContext.getInst().getApplicationContext(), "rtc_config");
  }
  
  public static boolean hasPreload() {
    return getSharedPreference().getBoolean("so_preload", false);
  }
  
  public static void setPreload() {
    getSharedPreference().edit().putBoolean("so_preload", true).apply();
  }
  
  public static void tryPreloadRtcSo() {
    if (!hasPreload())
      ThreadUtil.runOnWorkThread(new Action() {
            public final void act() {
              try {
                return;
              } finally {
                Exception exception = null;
                AppBrandLogger.eWithThrowable("RtcHelper", "", exception);
              } 
            }
          }ThreadPools.longIO()); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\rtc\RtcHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */