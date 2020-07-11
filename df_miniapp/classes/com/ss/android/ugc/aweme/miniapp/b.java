package com.ss.android.ugc.aweme.miniapp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import com.ss.android.ugc.aweme.AccountService;
import com.ss.android.ugc.aweme.IAccountService;
import com.ss.android.ugc.aweme.IAccountUserService;
import com.ss.android.ugc.aweme.miniapp.utils.f;
import com.ss.android.ugc.aweme.profile.model.User;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandSupport;
import com.tt.miniapphost.IAppbrandInitializer;
import com.tt.miniapphost.ParamManager;
import com.tt.miniapphost.host.HostDependManager;
import java.util.Locale;

public class b implements IAppbrandInitializer {
  public static final String a = b.class.getSimpleName();
  
  public static a b = new a();
  
  public static volatile boolean c = false;
  
  private static volatile b d;
  
  public static b a() {
    // Byte code:
    //   0: getstatic com/ss/android/ugc/aweme/miniapp/b.d : Lcom/ss/android/ugc/aweme/miniapp/b;
    //   3: ifnonnull -> 37
    //   6: ldc com/ss/android/ugc/aweme/miniapp/b
    //   8: monitorenter
    //   9: getstatic com/ss/android/ugc/aweme/miniapp/b.d : Lcom/ss/android/ugc/aweme/miniapp/b;
    //   12: ifnonnull -> 25
    //   15: new com/ss/android/ugc/aweme/miniapp/b
    //   18: dup
    //   19: invokespecial <init> : ()V
    //   22: putstatic com/ss/android/ugc/aweme/miniapp/b.d : Lcom/ss/android/ugc/aweme/miniapp/b;
    //   25: ldc com/ss/android/ugc/aweme/miniapp/b
    //   27: monitorexit
    //   28: goto -> 37
    //   31: astore_0
    //   32: ldc com/ss/android/ugc/aweme/miniapp/b
    //   34: monitorexit
    //   35: aload_0
    //   36: athrow
    //   37: getstatic com/ss/android/ugc/aweme/miniapp/b.d : Lcom/ss/android/ugc/aweme/miniapp/b;
    //   40: areturn
    // Exception table:
    //   from	to	target	type
    //   9	25	31	finally
    //   25	28	31	finally
    //   32	35	31	finally
  }
  
  public void init(Application paramApplication, String paramString, boolean paramBoolean) {
    Locale locale;
    if (paramBoolean) {
      AccountService.createIAccountServicebyMonsterPlugin().addLoginOrLogoutListener(b);
      f.a();
      locale = MiniAppService.inst().getBaseLibDepend().a((Context)paramApplication);
      if (locale != null)
        AppbrandSupport.inst().switchLang(locale); 
    } else if (paramString.contains(":miniapp")) {
      MiniAppService.inst().getBaseLibDepend().a((Application)locale);
      MiniAppService.inst().getBaseLibDepend().a(false);
      MiniAppService.inst().getBaseLibDepend().a((Application)locale, 0, ParamManager.getMiniAppSdkVersionCode(), ParamManager.getBaseEventParam((Context)locale));
      locale.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks(this, (Application)locale) {
            public final void onActivityCreated(Activity param1Activity, Bundle param1Bundle) {
              if (param1Activity instanceof com.tt.miniapphost.MiniappHostBase)
                try {
                  AppbrandApplicationImpl.getInst().getMainHandler().post(new Runnable(this) {
                        public final void run() {
                          MiniAppService.inst().getBaseLibDepend().a(ParamManager.getBaseEventParam((Context)this.a.a));
                        }
                      });
                  return;
                } catch (Exception exception) {
                  AppBrandLogger.eWithThrowable(b.a, "onActivityCreated", exception);
                }  
            }
            
            public final void onActivityDestroyed(Activity param1Activity) {}
            
            public final void onActivityPaused(Activity param1Activity) {}
            
            public final void onActivityResumed(Activity param1Activity) {}
            
            public final void onActivitySaveInstanceState(Activity param1Activity, Bundle param1Bundle) {}
            
            public final void onActivityStarted(Activity param1Activity) {}
            
            public final void onActivityStopped(Activity param1Activity) {}
          });
    } 
    HostDependManager.getInst().initAdDepend();
    c = true;
  }
  
  public boolean isDebug() {
    MiniAppService.inst().getBaseLibDepend();
    return false;
  }
  
  public static final class a implements IAccountService.a {
    public boolean a;
    
    public final void a(int param1Int1, boolean param1Boolean, int param1Int2, User param1User) {
      IAccountUserService iAccountUserService = AccountService.createIAccountServicebyMonsterPlugin().userService();
      if (iAccountUserService != null)
        iAccountUserService.isLogin(); 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */