package com.ss.android.ugc.aweme.miniapp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import com.ss.android.ugc.aweme.miniapp.j.a;
import com.ss.android.ugc.aweme.miniapp.j.b;
import com.ss.android.ugc.aweme.miniapp.j.c;
import com.ss.android.ugc.aweme.miniapp_api.a.l;
import com.ss.android.ugc.aweme.miniapp_api.b.g;
import com.storage.async.AsyncSchedulerInit;
import com.storage.async.Scheduler;
import com.storage.async.SchedulerCreator;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.hostmethod.HostMethodManager;
import com.tt.miniapphost.hostmethod.IHostMethod;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.util.ProcessUtil;

public class i {
  public static final String a = i.class.getSimpleName();
  
  private static void a(Application paramApplication) {
    if (ProcessUtil.getCurProcessName((Context)paramApplication).contains("miniapp")) {
      c(paramApplication);
      MiniAppService.inst().getBaseLibDepend().d();
      MiniAppService.inst().getBaseLibDepend().b((Context)paramApplication, "");
      b(paramApplication);
      HostMethodManager.getInstance().registerHostMethod("aweme_addFriend", (IHostMethod)new a());
      HostMethodManager.getInstance().registerHostMethod("aweme_showVideo", (IHostMethod)new c());
      HostMethodManager.getInstance().registerHostMethod("aweme_showVideoDetailList", (IHostMethod)new b());
    } 
  }
  
  public static void a(Application paramApplication, g paramg, boolean paramBoolean) {
    if (Build.VERSION.SDK_INT < 21)
      return; 
    AsyncSchedulerInit.setDBSchedulerHandler(new SchedulerCreator() {
          public final Scheduler create() {
            return new Scheduler(this) {
                public final void execute(Runnable param2Runnable) {
                  MiniAppService.inst().getBaseLibDepend().a(param2Runnable);
                }
              };
          }
        });
    AsyncSchedulerInit.setNetSchedulerHandler(new SchedulerCreator() {
          public final Scheduler create() {
            return new Scheduler(this) {
                public final void execute(Runnable param2Runnable) {
                  MiniAppService.inst().getBaseLibDepend().a(param2Runnable);
                }
              };
          }
        });
    boolean bool = ProcessUtil.isMainProcess((Context)paramApplication);
    StringBuilder stringBuilder = new StringBuilder("miniapp init reason");
    stringBuilder.append(Log.getStackTraceString(new Exception()));
    String str = stringBuilder.toString();
    if (bool) {
      if (paramBoolean) {
        MiniAppService.inst().getBaseLibDepend().b(new Runnable(paramApplication, str, paramg) {
              public final void run() {
                AppbrandContext.init(this.a, b.a());
                i.a(this.b);
                g g1 = this.c;
                if (g1 != null)
                  g1.a(); 
              }
            });
      } else {
        AppbrandContext.init(paramApplication, b.a());
        a(str);
      } 
    } else {
      a((Context)paramApplication);
      AppbrandContext.init(paramApplication, b.a());
      a(paramApplication);
      if (paramg != null)
        paramg.a(); 
    } 
    d(paramApplication);
  }
  
  private static void a(Context paramContext) {
    if (!ProcessUtil.isMainProcess(paramContext))
      try {
        b("c++_shared");
        b("v8_libbase.cr");
        b("v8_libplatform.cr");
        b("v8.cr");
        b("freetypelite");
        b("skialite");
        b("jsbinding");
        b("jsc");
        b("jsbinding-jsc");
        b("aurum");
        b("helium");
        b("effect");
        b("helium-game");
        return;
      } finally {
        paramContext = null;
      }  
  }
  
  public static void a(String paramString) {
    String str = a;
    StringBuilder stringBuilder = new StringBuilder("uploadInitReason");
    stringBuilder.append(paramString);
    AppBrandLogger.d(str, new Object[] { stringBuilder.toString() });
    InnerEventHelper.mpTechnologyMsg(paramString);
  }
  
  private static void b(Application paramApplication) {
    try {
      return;
    } finally {
      paramApplication = null;
    } 
  }
  
  private static void b(String paramString) {
    try {
      return;
    } finally {
      paramString = null;
      AppBrandLogger.eWithThrowable(a, "loading error", (Throwable)paramString);
    } 
  }
  
  private static void c(Application paramApplication) {
    l l = MiniAppService.inst().getRouterDepend();
    if (l != null)
      l.b(); 
  }
  
  private static void d(Application paramApplication) {
    if (!ProcessUtil.isMainProcess((Context)paramApplication) && paramApplication != null)
      paramApplication.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            public final void onActivityCreated(Activity param1Activity, Bundle param1Bundle) {}
            
            public final void onActivityDestroyed(Activity param1Activity) {}
            
            public final void onActivityPaused(Activity param1Activity) {
              HostProcessBridge.hostActionSync("micro_app_lifecycle", CrossProcessDataEntity.Builder.create().build());
            }
            
            public final void onActivityResumed(Activity param1Activity) {}
            
            public final void onActivitySaveInstanceState(Activity param1Activity, Bundle param1Bundle) {}
            
            public final void onActivityStarted(Activity param1Activity) {}
            
            public final void onActivityStopped(Activity param1Activity) {}
          }); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */