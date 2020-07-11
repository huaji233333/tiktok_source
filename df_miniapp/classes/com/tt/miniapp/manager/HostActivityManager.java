package com.tt.miniapp.manager;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;
import com.tt.miniapp.entity.AppJumpListManager;
import com.tt.miniapp.util.ActivityUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.DebugUtil;
import java.lang.ref.WeakReference;
import java.util.LinkedList;

public class HostActivityManager {
  public static boolean mTriggeredHomeOrRecentApp;
  
  public static LinkedList<WeakReference<Activity>> sHostProcessActivityWrList = new LinkedList<WeakReference<Activity>>();
  
  public static Activity getHostTopActivity() {
    // Byte code:
    //   0: ldc com/tt/miniapp/manager/HostActivityManager
    //   2: monitorenter
    //   3: getstatic com/tt/miniapp/manager/HostActivityManager.sHostProcessActivityWrList : Ljava/util/LinkedList;
    //   6: invokevirtual size : ()I
    //   9: iconst_1
    //   10: isub
    //   11: istore_0
    //   12: iload_0
    //   13: iflt -> 38
    //   16: iload_0
    //   17: iconst_0
    //   18: invokestatic getTargetIndexActivity : (IZ)Landroid/app/Activity;
    //   21: astore_1
    //   22: aload_1
    //   23: ifnull -> 31
    //   26: ldc com/tt/miniapp/manager/HostActivityManager
    //   28: monitorexit
    //   29: aload_1
    //   30: areturn
    //   31: iload_0
    //   32: iconst_1
    //   33: isub
    //   34: istore_0
    //   35: goto -> 12
    //   38: ldc 'HostActivityManager'
    //   40: iconst_1
    //   41: anewarray java/lang/Object
    //   44: dup
    //   45: iconst_0
    //   46: ldc 'getNullHostTopActivity'
    //   48: aastore
    //   49: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   52: ldc com/tt/miniapp/manager/HostActivityManager
    //   54: monitorexit
    //   55: aconst_null
    //   56: areturn
    //   57: astore_1
    //   58: ldc com/tt/miniapp/manager/HostActivityManager
    //   60: monitorexit
    //   61: goto -> 66
    //   64: aload_1
    //   65: athrow
    //   66: goto -> 64
    // Exception table:
    //   from	to	target	type
    //   3	12	57	finally
    //   16	22	57	finally
    //   38	52	57	finally
  }
  
  private static Activity getTargetIndexActivity(int paramInt, boolean paramBoolean) {
    WeakReference<Activity> weakReference = sHostProcessActivityWrList.get(paramInt);
    if (weakReference != null && weakReference.get() != null)
      return weakReference.get(); 
    if (paramBoolean)
      sHostProcessActivityWrList.remove(paramInt); 
    return null;
  }
  
  public static boolean isIgnoreActivity(Activity paramActivity) {
    if (paramActivity == null)
      return true; 
    String str = paramActivity.getClass().getName();
    return TextUtils.isEmpty(str) ? true : str.endsWith("MoveHostFrontActivity");
  }
  
  public static void registerHostLifecycleListener(Application paramApplication) {
    paramApplication.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
          public final void onActivityCreated(Activity param1Activity, Bundle param1Bundle) {}
          
          public final void onActivityDestroyed(Activity param1Activity) {
            if (HostActivityManager.isIgnoreActivity(param1Activity))
              return; 
            try {
              HostActivityManager.removeActivityInWrList(param1Activity);
              return;
            } catch (Exception exception) {
              DebugUtil.outputError("HostActivityManager", new Object[] { "onActivityDestroyed", exception });
              return;
            } 
          }
          
          public final void onActivityPaused(Activity param1Activity) {
            if (HostActivityManager.isIgnoreActivity(param1Activity))
              return; 
            if (HostActivityManager.sHostProcessActivityWrList.size() == 0)
              HostActivityManager.sHostProcessActivityWrList.add(new WeakReference<Activity>(param1Activity)); 
          }
          
          public final void onActivityResumed(Activity param1Activity) {
            HostActivityManager.mTriggeredHomeOrRecentApp = false;
          }
          
          public final void onActivitySaveInstanceState(Activity param1Activity, Bundle param1Bundle) {}
          
          public final void onActivityStarted(Activity param1Activity) {
            if (HostActivityManager.isIgnoreActivity(param1Activity))
              return; 
            try {
              HostActivityManager.removeActivityInWrList(param1Activity);
              HostActivityManager.sHostProcessActivityWrList.add(new WeakReference<Activity>(param1Activity));
              return;
            } catch (Exception exception) {
              DebugUtil.outputError("HostActivityManager", new Object[] { "onActivityStarted", exception });
              return;
            } 
          }
          
          public final void onActivityStopped(Activity param1Activity) {}
        });
    ForeBackgroundManager foreBackgroundManager = new ForeBackgroundManager();
    foreBackgroundManager.registerCloseSystemDialogReceiver();
    foreBackgroundManager.registerForeBackgroundListener(new ForeBackgroundManager.DefaultForeBackgroundListener() {
          public final void onTriggerHomeOrRecentApp() {
            HostActivityManager.mTriggeredHomeOrRecentApp = true;
            AppJumpListManager.clearAppList();
          }
        });
  }
  
  public static void removeActivityInWrList(Activity paramActivity) {
    for (int i = sHostProcessActivityWrList.size() - 1; i >= 0; i--) {
      if (getTargetIndexActivity(i, true) == paramActivity) {
        sHostProcessActivityWrList.remove(i);
        return;
      } 
    } 
  }
  
  public static void tryMoveMiniAppActivityTaskToFront(String paramString) {
    AppBrandLogger.i("HostActivityManager", new Object[] { "tryMoveMiniAppActivityTaskToFront mTriggeredHomeOrRecentApp:", Boolean.valueOf(mTriggeredHomeOrRecentApp), "targetAppId:", paramString });
    if (!TextUtils.isEmpty(paramString))
      ActivityUtil.moveMiniAppActivityToFront(getHostTopActivity(), paramString); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\HostActivityManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */