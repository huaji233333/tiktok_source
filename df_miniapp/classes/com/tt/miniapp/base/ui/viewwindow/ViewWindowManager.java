package com.tt.miniapp.base.ui.viewwindow;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.WeakHashMap;

public class ViewWindowManager {
  public static WeakHashMap<Activity, Integer> activityLifecycleStateMap;
  
  private static volatile boolean isInited;
  
  private static HashSet<WeakReference<ViewWindowRoot>> viewWindowContainerMap = new HashSet<WeakReference<ViewWindowRoot>>();
  
  static {
    activityLifecycleStateMap = new WeakHashMap<Activity, Integer>();
  }
  
  public static void dispatchActivityDestroy(Activity paramActivity) {
    for (WeakReference<ViewWindowRoot> weakReference : viewWindowContainerMap) {
      if (weakReference != null) {
        ViewWindowRoot viewWindowRoot = weakReference.get();
        if (viewWindowRoot != null && viewWindowRoot.getActivity() == paramActivity)
          viewWindowRoot.dispatchOnActivityDestroy(); 
      } 
    } 
  }
  
  public static void dispatchActivityPause(Activity paramActivity) {
    for (WeakReference<ViewWindowRoot> weakReference : viewWindowContainerMap) {
      if (weakReference != null) {
        ViewWindowRoot viewWindowRoot = weakReference.get();
        if (viewWindowRoot != null && viewWindowRoot.getActivity() == paramActivity)
          viewWindowRoot.dispatchOnActivityPause(); 
      } 
    } 
  }
  
  public static void dispatchActivityResult(Activity paramActivity, int paramInt1, int paramInt2, Intent paramIntent) {
    for (WeakReference<ViewWindowRoot> weakReference : viewWindowContainerMap) {
      if (weakReference != null) {
        ViewWindowRoot viewWindowRoot = weakReference.get();
        if (viewWindowRoot != null && viewWindowRoot.getActivity() == paramActivity)
          viewWindowRoot.dispatchActivityResult(paramInt1, paramInt2, paramIntent); 
      } 
    } 
  }
  
  public static void dispatchActivityResume(Activity paramActivity) {
    for (WeakReference<ViewWindowRoot> weakReference : viewWindowContainerMap) {
      if (weakReference != null) {
        ViewWindowRoot viewWindowRoot = weakReference.get();
        if (viewWindowRoot != null && viewWindowRoot.getActivity() == paramActivity)
          viewWindowRoot.dispatchOnActivityResume(); 
      } 
    } 
  }
  
  public static int getActivityLifecycleState(Activity paramActivity) {
    Integer integer = activityLifecycleStateMap.get(paramActivity);
    return (integer == null) ? 0 : integer.intValue();
  }
  
  public static void init(Application paramApplication) {
    if (isInited)
      return; 
    isInited = true;
    paramApplication.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
          public final void onActivityCreated(Activity param1Activity, Bundle param1Bundle) {
            ViewWindowManager.activityLifecycleStateMap.put(param1Activity, Integer.valueOf(1));
          }
          
          public final void onActivityDestroyed(Activity param1Activity) {
            ViewWindowManager.activityLifecycleStateMap.put(param1Activity, Integer.valueOf(6));
            ViewWindowManager.dispatchActivityDestroy(param1Activity);
          }
          
          public final void onActivityPaused(Activity param1Activity) {
            ViewWindowManager.activityLifecycleStateMap.put(param1Activity, Integer.valueOf(4));
            ViewWindowManager.dispatchActivityPause(param1Activity);
          }
          
          public final void onActivityResumed(Activity param1Activity) {
            ViewWindowManager.activityLifecycleStateMap.put(param1Activity, Integer.valueOf(3));
            ViewWindowManager.dispatchActivityResume(param1Activity);
          }
          
          public final void onActivitySaveInstanceState(Activity param1Activity, Bundle param1Bundle) {}
          
          public final void onActivityStarted(Activity param1Activity) {
            ViewWindowManager.activityLifecycleStateMap.put(param1Activity, Integer.valueOf(2));
          }
          
          public final void onActivityStopped(Activity param1Activity) {
            ViewWindowManager.activityLifecycleStateMap.put(param1Activity, Integer.valueOf(5));
          }
        });
  }
  
  protected static void regAsViewWindowContainer(ViewWindowRoot paramViewWindowRoot) {
    if (isInited) {
      if (paramViewWindowRoot != null) {
        viewWindowContainerMap.add(new WeakReference<ViewWindowRoot>(paramViewWindowRoot));
        return;
      } 
      throw new Error("container and activity must be not null");
    } 
    throw new Error("ViewWindowManager must be inited,please use ViewWindowManager.init first");
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\bas\\ui\viewwindow\ViewWindowManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */