package com.tt.miniapp.manager;

import android.os.Looper;
import android.util.Log;
import com.tt.miniapp.AppConfig;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandServiceManager;
import com.tt.miniapp.adsite.AdSiteManager;
import com.tt.miniapp.streamloader.StreamLoader;
import com.tt.miniapp.util.TimeLogger;
import com.tt.miniapp.util.timeline.MpTimeLineReporter;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.util.DebugUtil;
import com.tt.miniapphost.util.ProcessUtil;
import org.json.JSONException;
import org.json.JSONObject;

public class AppConfigManager extends AppbrandServiceManager.ServiceBase {
  private volatile AppConfig mAppConfig;
  
  private boolean mLoaded = false;
  
  private final Object mLock = new Object();
  
  private int mRetryTime;
  
  protected AppConfigManager(AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramAppbrandApplicationImpl);
  }
  
  private void loadAppConfig() {
    try {
      MpTimeLineReporter mpTimeLineReporter = (MpTimeLineReporter)AppbrandApplicationImpl.getInst().getService(MpTimeLineReporter.class);
      JSONObject jSONObject = (new MpTimeLineReporter.ExtraBuilder()).kv("file_path", "app-config.json").build();
      mpTimeLineReporter.addPoint("get_file_content_from_ttpkg_begin", jSONObject);
      ((TimeLogger)this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "AppbrandApplicationImpl_startGetAppConfig" });
      String str = StreamLoader.loadStringFromStream("app-config.json");
      mpTimeLineReporter.addPoint("get_file_content_from_ttpkg_end", jSONObject);
      mpTimeLineReporter.addPoint("parse_json_begin", jSONObject);
      ((TimeLogger)this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "AppbrandApplicationImpl_startParseAppConfig" });
      this.mAppConfig = AppConfig.parseAppConfig(str);
      ((TimeLogger)this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "AppbrandApplicationImpl_stopParseAppConfig" });
      mpTimeLineReporter.addPoint("parse_json_end", jSONObject);
      if (this.mAppConfig != null)
        synchronized (this.mLock) {
          this.mLock.notifyAll();
          return;
        }  
    } catch (Exception exception) {
      AppBrandLogger.e("AppConfigManager", new Object[] { exception });
      if (this.mRetryTime <= 0) {
        this.mRetryTime++;
        loadAppConfig();
      } else {
        JSONObject jSONObject = new JSONObject();
        try {
          jSONObject.put("errMsg", Log.getStackTraceString(exception));
        } catch (JSONException jSONException) {
          AppBrandLogger.e("AppConfigManager", new Object[] { jSONException });
        } 
        ((TimeLogger)this.mApp.getService(TimeLogger.class)).logError(new String[] { "BaseActivityProxy_parseAppConfigFail" });
        AppBrandMonitor.statusRate("mp_parse_appconfig_error", 1006, jSONObject);
      } 
      if (this.mAppConfig != null)
        synchronized (this.mLock) {
          this.mLock.notifyAll();
          return;
        }  
    } finally {
      Exception exception;
    } 
  }
  
  public AppConfig getAppConfig() {
    if (!ProcessUtil.isMiniappProcess() || (this.mAppConfig == null && Looper.myLooper() == Looper.getMainLooper()))
      return this.mAppConfig; 
    if (this.mAppConfig != null)
      return this.mAppConfig; 
    if (AdSiteManager.getInstance().isAdSiteBrowser())
      return this.mAppConfig; 
    synchronized (this.mLock) {
      while (true) {
        AppConfig appConfig = this.mAppConfig;
        if (appConfig == null) {
          try {
            this.mLock.wait();
          } catch (InterruptedException interruptedException) {
            AppBrandLogger.e("AppConfigManager", new Object[] { interruptedException });
          } 
          continue;
        } 
        return this.mAppConfig;
      } 
    } 
  }
  
  public AppConfig initAppConfig() {
    if (Looper.myLooper() == Looper.getMainLooper())
      DebugUtil.logOrThrow("AppConfigManager", new Object[] { "不要在主线程里调用该方法：initAppConfig()" }); 
    synchronized (this.mLock) {
      if (this.mLoaded)
        return this.mAppConfig; 
      this.mLoaded = true;
      loadAppConfig();
      return this.mAppConfig;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\AppConfigManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */