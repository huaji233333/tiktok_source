package com.tt.miniapp.exit;

import android.os.Handler;
import android.os.Looper;
import com.tt.miniapp.util.JsCoreUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.util.TimeMeter;
import org.json.JSONException;
import org.json.JSONObject;

public class AppBrandExitManager {
  public Runnable mExitRunnable;
  
  private Handler mHandler = new Handler(Looper.getMainLooper());
  
  private TimeMeter mTimeMeter;
  
  private Runnable mTimeOutRunnable = new Runnable() {
      public void run() {
        AppBrandLogger.d("AppBrandExitManager", new Object[] { "exit callback timeout" });
        AppBrandMonitor.statusRate("mp_close_callback_timeout", 1, new JSONObject());
        if (AppBrandExitManager.this.mExitRunnable != null) {
          AppBrandExitManager.this.mExitRunnable.run();
          AppBrandExitManager.this.mExitRunnable = null;
        } 
      }
    };
  
  private AppBrandExitManager() {}
  
  public static AppBrandExitManager getInst() {
    return Holder.INSTANCE;
  }
  
  public void onBeforeExit(boolean paramBoolean, Runnable paramRunnable) {
    if (this.mExitRunnable != null) {
      AppBrandLogger.d("AppBrandExitManager", new Object[] { "currently deal with exit" });
      return;
    } 
    AppBrandLogger.d("AppBrandExitManager", new Object[] { "onBeforeExit" });
    this.mExitRunnable = paramRunnable;
    JsCoreUtils.sendOnBeforeExit(paramBoolean);
    this.mTimeMeter = TimeMeter.newAndStart();
    this.mHandler.postDelayed(this.mTimeOutRunnable, 500L);
  }
  
  public void onBeforeExitReturn(boolean paramBoolean) {
    StringBuilder stringBuilder = new StringBuilder("onBeforeExitReturn: ");
    stringBuilder.append(paramBoolean);
    AppBrandLogger.d("AppBrandExitManager", new Object[] { stringBuilder.toString() });
    this.mHandler.removeCallbacks(this.mTimeOutRunnable);
    if (!paramBoolean) {
      Runnable runnable = this.mExitRunnable;
      if (runnable != null)
        runnable.run(); 
    } 
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("duration", TimeMeter.stop(this.mTimeMeter));
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable("AppBrandExitManager", "", (Throwable)jSONException);
    } 
    AppBrandMonitor.statusRate("mp_close_callback_timeout", 0, jSONObject);
    this.mExitRunnable = null;
  }
  
  static class Holder {
    public static AppBrandExitManager INSTANCE = new AppBrandExitManager();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\exit\AppBrandExitManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */