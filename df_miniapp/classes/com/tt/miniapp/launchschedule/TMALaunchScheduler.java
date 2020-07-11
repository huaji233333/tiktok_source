package com.tt.miniapp.launchschedule;

import android.content.Context;
import android.content.ContextWrapper;
import android.text.TextUtils;
import android.util.Log;
import com.tt.miniapp.AppConfig;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.JsRuntime;
import com.tt.miniapp.debug.DebugManager;
import com.tt.miniapp.dialog.LoadHelper;
import com.tt.miniapp.errorcode.ErrorCode;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapp.event.LoadStateManager;
import com.tt.miniapp.guide.ReenterGuideHelper;
import com.tt.miniapp.jsbridge.JsRuntimeManager;
import com.tt.miniapp.route.PageRouter;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.util.TimeLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.util.TimeMeter;

public class TMALaunchScheduler extends AbsSubLaunchScheduler {
  TMALaunchScheduler(LaunchScheduler paramLaunchScheduler, AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramLaunchScheduler, paramAppbrandApplicationImpl);
  }
  
  public void onMiniAppInstallSuccess() {
    AppConfig appConfig = this.mApp.getAppConfig();
    if (appConfig == null) {
      AppBrandMonitor.reportPreloadCase("initView_appConfig_null", 6009);
      LoadHelper.handleMiniProcessFail(ErrorCode.MAIN.PARSE_APPCONFIG_ERROR.getCode());
      return;
    } 
    String str = appConfig.mEntryPath;
    AppInfoEntity appInfoEntity = this.mApp.getAppInfo();
    if (!TextUtils.isEmpty(appInfoEntity.startPage))
      str = appInfoEntity.startPage; 
    ((PageRouter)this.mApp.getService(PageRouter.class)).setup(appConfig, str);
    ((JsRuntimeManager)this.mApp.getService(JsRuntimeManager.class)).getCurrentRuntime().loadMainJs(new JsRuntime.MainJsLoadCallback() {
          TimeMeter mCpJsEvalTime;
          
          public void afterEval() {
            ((TimeLogger)TMALaunchScheduler.this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "JsTMARuntime_loadAppServiceSuccess" });
            InnerEventHelper.mpCpJsLoadResult("success", TimeMeter.stop(this.mCpJsEvalTime), "");
            LoadStateManager.getIns().startRenderTime();
            LoadStateManager.getIns().setLoadState("rendering");
            TMALaunchScheduler.this.mParentScheduler.onJsCoreReady();
            ThreadUtil.runOnUIThread(new Runnable() {
                  public void run() {
                    ReenterGuideHelper.preload((Context)AppbrandContext.getInst().getApplicationContext());
                  }
                });
          }
          
          public void beforeEval() {
            LoadStateManager.getIns().setLoadState("cp_js_loading");
            ((TimeLogger)TMALaunchScheduler.this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "JsTMARuntime_startLoadAppServiceJs" });
            this.mCpJsEvalTime = TimeMeter.newAndStart();
          }
          
          public void evalException(Exception param1Exception) {
            InnerEventHelper.mpCpJsLoadResult("fail", TimeMeter.stop(this.mCpJsEvalTime), Log.getStackTraceString(param1Exception));
            ((TimeLogger)TMALaunchScheduler.this.mApp.getService(TimeLogger.class)).logError(new String[] { "JsTMARuntime_loadAppServiceError", Log.getStackTraceString(param1Exception) });
          }
        });
  }
  
  public void onStartLaunch() {
    if (!(DebugManager.getInst()).mIsRemoteDebug)
      ((JsRuntimeManager)this.mApp.getService(JsRuntimeManager.class)).initTMARuntime((ContextWrapper)AppbrandContext.getInst().getApplicationContext()); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchschedule\TMALaunchScheduler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */