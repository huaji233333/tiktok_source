package com.facebook.react;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import com.facebook.i.a.a;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;
import com.facebook.react.jstasks.HeadlessJsTaskContext;
import com.facebook.react.jstasks.HeadlessJsTaskEventListener;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public abstract class HeadlessJsTaskService extends Service implements HeadlessJsTaskEventListener {
  private static PowerManager.WakeLock sWakeLock;
  
  public final Set<Integer> mActiveTasks = new CopyOnWriteArraySet<Integer>();
  
  public static void acquireWakeLockNow(Context paramContext) {
    PowerManager.WakeLock wakeLock = sWakeLock;
    if (wakeLock == null || !wakeLock.isHeld()) {
      PowerManager.WakeLock wakeLock1 = ((PowerManager)a.b(paramContext.getSystemService("power"))).newWakeLock(1, HeadlessJsTaskService.class.getSimpleName());
      sWakeLock = wakeLock1;
      wakeLock1.setReferenceCounted(false);
      sWakeLock.acquire();
    } 
  }
  
  protected ReactNativeHost getReactNativeHost() {
    return ((ReactApplication)getApplication()).getReactNativeHost();
  }
  
  protected HeadlessJsTaskConfig getTaskConfig(Intent paramIntent) {
    return null;
  }
  
  public void invokeStartTask(ReactContext paramReactContext, final HeadlessJsTaskConfig taskConfig) {
    final HeadlessJsTaskContext headlessJsTaskContext = HeadlessJsTaskContext.getInstance(paramReactContext);
    headlessJsTaskContext.addTaskEventListener(this);
    UiThreadUtil.runOnUiThread(new Runnable() {
          public void run() {
            int i = headlessJsTaskContext.startTask(taskConfig);
            HeadlessJsTaskService.this.mActiveTasks.add(Integer.valueOf(i));
          }
        });
  }
  
  public IBinder onBind(Intent paramIntent) {
    return null;
  }
  
  public void onDestroy() {
    super.onDestroy();
    if (getReactNativeHost().hasInstance()) {
      ReactContext reactContext = getReactNativeHost().getReactInstanceManager().getCurrentReactContext();
      if (reactContext != null)
        HeadlessJsTaskContext.getInstance(reactContext).removeTaskEventListener(this); 
    } 
    PowerManager.WakeLock wakeLock = sWakeLock;
    if (wakeLock != null)
      wakeLock.release(); 
  }
  
  public void onHeadlessJsTaskFinish(int paramInt) {
    this.mActiveTasks.remove(Integer.valueOf(paramInt));
    if (this.mActiveTasks.size() == 0)
      stopSelf(); 
  }
  
  public void onHeadlessJsTaskStart(int paramInt) {}
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2) {
    HeadlessJsTaskConfig headlessJsTaskConfig = getTaskConfig(paramIntent);
    if (headlessJsTaskConfig != null) {
      startTask(headlessJsTaskConfig);
      return 3;
    } 
    return 2;
  }
  
  protected void startTask(final HeadlessJsTaskConfig taskConfig) {
    UiThreadUtil.assertOnUiThread();
    acquireWakeLockNow((Context)this);
    final ReactInstanceManager reactInstanceManager = getReactNativeHost().getReactInstanceManager();
    ReactContext reactContext = reactInstanceManager.getCurrentReactContext();
    if (reactContext == null) {
      reactInstanceManager.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
            public void onReactContextInitialized(ReactContext param1ReactContext) {
              HeadlessJsTaskService.this.invokeStartTask(param1ReactContext, taskConfig);
              reactInstanceManager.removeReactInstanceEventListener(this);
            }
          });
      if (!reactInstanceManager.hasStartedCreatingInitialContext()) {
        reactInstanceManager.createReactContextInBackground();
        return;
      } 
    } else {
      invokeStartTask(reactContext, taskConfig);
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\HeadlessJsTaskService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */