package com.tt.miniapp;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.storage.async.Action;
import com.storage.async.Scheduler;
import com.tt.miniapp.manager.basebundle.BaseBundleManager;
import com.tt.miniapp.notification.MiniAppNotificationManager;
import com.tt.miniapp.process.AppProcessManager;
import com.tt.miniapp.process.CacheCrossProcessDataInterceptor;
import com.tt.miniapp.process.ServiceBindManager;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapp.process.interceptor.ISyncCallInterceptor;
import com.tt.miniapp.rtc.RtcHelper;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.util.ChannelUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.LaunchThreadPool;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.process.bridge.ProcessCallControlBridge;
import com.tt.miniapphost.util.ProcessUtil;

public class MiniAppInitializer {
  public static void initInMiniAppProcess() {
    AppBrandLogger.i("MiniAppInitializer", new Object[] { "initInMiniAppProcess" });
    ThreadUtil.runOnWorkThread(new Action() {
          public final void act() {
            Application application = AppbrandContext.getInst().getApplicationContext();
            ServiceBindManager.getInstance().bindHostService();
            MiniAppNotificationManager.init((Context)AppbrandContext.getInst().getApplicationContext());
            InnerHostProcessBridge.notifyMiniAppProcessUsed(ProcessUtil.getCurProcessName((Context)application));
          }
        },  (Scheduler)LaunchThreadPool.getInst());
    if (ChannelUtil.isLocalTest())
      Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(Thread.getDefaultUncaughtExceptionHandler()) {
            public final void uncaughtException(Thread param1Thread, Throwable param1Throwable) {
              InnerHostProcessBridge.notifyMiniAppProcessCrash(ProcessUtil.getCurProcessName((Context)AppbrandContext.getInst().getApplicationContext()), Log.getStackTraceString(param1Throwable));
              Thread.UncaughtExceptionHandler uncaughtExceptionHandler = defaultUncaughtExceptionHandler;
              if (uncaughtExceptionHandler != null)
                uncaughtExceptionHandler.uncaughtException(param1Thread, param1Throwable); 
            }
          }); 
    BaseBundleManager.getInst().preload((Context)AppbrandContext.getInst().getApplicationContext());
    ProcessCallControlBridge.setSyncInterceptor((ISyncCallInterceptor)new CacheCrossProcessDataInterceptor());
    AppProcessManager.registerHostProcessLifeListener(new ServiceBindManager.HostProcessLifeListener() {
          public final void onAlive(boolean param1Boolean) {
            if (param1Boolean)
              InnerHostProcessBridge.setTmaLaunchFlag(); 
          }
          
          public final void onDied() {
            if (AppbrandContext.getInst().getCurrentActivity() == null) {
              AppBrandLogger.i("MiniAppInitializer", new Object[] { "killCurrentPreloadProcessWhenHostProcessDied" });
              ProcessUtil.killCurrentMiniAppProcess((Context)AppbrandContext.getInst().getApplicationContext());
              return;
            } 
            AppBrandMonitor.reportError("mp_special_error", "host process died", null);
          }
        });
    RtcHelper.tryPreloadRtcSo();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\MiniAppInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */