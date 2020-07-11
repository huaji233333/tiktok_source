package com.tt.miniapp.manager;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.storage.async.Action;
import com.storage.async.Scheduler;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.LaunchThreadPool;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ForeBackgroundManager {
  private long mBeginBackgroundTime = -1L;
  
  private CloseSystemDialogReceiver mCloseSystemReceiver;
  
  public List<ForeBackgroundListener> mForeBackgroundListener = new ArrayList<ForeBackgroundListener>();
  
  private boolean mGoingBackground;
  
  private boolean mIsForeground;
  
  private Runnable mNotifyBackgroundOverLimitTimeRunnable = new Runnable() {
      public void run() {
        ForeBackgroundManager.this.onBackgroundOverLimitTime();
      }
    };
  
  public volatile boolean mPauseBackgroundOverLimitTimeStrategy;
  
  private long getStayBackgroundTime() {
    return (this.mBeginBackgroundTime == -1L) ? -1L : (System.currentTimeMillis() - this.mBeginBackgroundTime);
  }
  
  private void setIsForeground(boolean paramBoolean) {
    this.mIsForeground = paramBoolean;
    this.mGoingBackground = false;
  }
  
  public void clear() {
    this.mForeBackgroundListener.clear();
    Application application = AppbrandContext.getInst().getApplicationContext();
    if (application != null) {
      CloseSystemDialogReceiver closeSystemDialogReceiver = this.mCloseSystemReceiver;
      if (closeSystemDialogReceiver == null)
        return; 
      application.unregisterReceiver(closeSystemDialogReceiver);
    } 
  }
  
  public boolean isBackground() {
    return !this.mIsForeground;
  }
  
  public boolean isBackgroundOrGoingBackground() {
    return (this.mGoingBackground || !this.mIsForeground);
  }
  
  public boolean isStayBackgroundOverLimitTime() {
    boolean bool;
    if (this.mPauseBackgroundOverLimitTimeStrategy) {
      AppBrandLogger.i("ForeBackgroundManager", new Object[] { "isStayBackgroundOverLimitTime mPauseBackgroundOverLimitTimeStrategy" });
      return false;
    } 
    if (getStayBackgroundTime() > 5000L) {
      bool = true;
    } else {
      bool = false;
    } 
    AppBrandLogger.i("ForeBackgroundManager", new Object[] { "isStayBackgroundOverLimitTime:", Boolean.valueOf(bool) });
    return bool;
  }
  
  public void onBackground() {
    AppBrandLogger.i("ForeBackgroundManager", new Object[] { "onBackground" });
    registerCloseSystemDialogReceiver();
    setIsForeground(false);
    this.mBeginBackgroundTime = System.currentTimeMillis();
    if (!this.mPauseBackgroundOverLimitTimeStrategy)
      AppbrandContext.mainHandler.postDelayed(this.mNotifyBackgroundOverLimitTimeRunnable, 5000L); 
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            synchronized (ForeBackgroundManager.this) {
              AppBrandLogger.i("ForeBackgroundManager", new Object[] { "notifyOnBackground" });
              Iterator<ForeBackgroundManager.ForeBackgroundListener> iterator = ForeBackgroundManager.this.mForeBackgroundListener.iterator();
              while (iterator.hasNext())
                ((ForeBackgroundManager.ForeBackgroundListener)iterator.next()).onBackground(); 
              return;
            } 
          }
        }(Scheduler)LaunchThreadPool.getInst());
  }
  
  public void onBackgroundOverLimitTime() {
    AppBrandLogger.i("ForeBackgroundManager", new Object[] { "onBackgroundOverLimitTime" });
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            synchronized (ForeBackgroundManager.this) {
              if (!ForeBackgroundManager.this.isBackground() || ForeBackgroundManager.this.mPauseBackgroundOverLimitTimeStrategy)
                return; 
              AppBrandLogger.i("ForeBackgroundManager", new Object[] { "notifyOnBackgroundOverLimitTime" });
              Iterator<ForeBackgroundManager.ForeBackgroundListener> iterator = ForeBackgroundManager.this.mForeBackgroundListener.iterator();
              while (iterator.hasNext())
                ((ForeBackgroundManager.ForeBackgroundListener)iterator.next()).onBackgroundOverLimitTime(); 
              return;
            } 
          }
        }(Scheduler)LaunchThreadPool.getInst());
  }
  
  public void onForeground() {
    AppBrandLogger.i("ForeBackgroundManager", new Object[] { "onForeground" });
    registerCloseSystemDialogReceiver();
    setIsForeground(true);
    this.mBeginBackgroundTime = -1L;
    this.mPauseBackgroundOverLimitTimeStrategy = false;
    AppbrandContext.mainHandler.removeCallbacks(this.mNotifyBackgroundOverLimitTimeRunnable);
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            synchronized (ForeBackgroundManager.this) {
              AppBrandLogger.i("ForeBackgroundManager", new Object[] { "notifyOnForeground" });
              Iterator<ForeBackgroundManager.ForeBackgroundListener> iterator = ForeBackgroundManager.this.mForeBackgroundListener.iterator();
              while (iterator.hasNext())
                ((ForeBackgroundManager.ForeBackgroundListener)iterator.next()).onForeground(); 
              return;
            } 
          }
        }(Scheduler)LaunchThreadPool.getInst());
  }
  
  public void onTriggerHomeOrRecentApp() {
    AppBrandLogger.i("ForeBackgroundManager", new Object[] { "onTriggerHomeOrRecentApp" });
    Iterator<?> iterator = (new ArrayList(this.mForeBackgroundListener)).iterator();
    while (iterator.hasNext())
      ((ForeBackgroundListener)iterator.next()).onTriggerHomeOrRecentApp(); 
  }
  
  public void pauseBackgroundOverLimitTimeStrategy() {
    AppBrandLogger.i("ForeBackgroundManager", new Object[] { "pauseBackgroundOverLimitTimeStrategy mPauseBackgroundOverLimitTimeStrategy", Boolean.valueOf(this.mPauseBackgroundOverLimitTimeStrategy) });
    this.mPauseBackgroundOverLimitTimeStrategy = true;
    AppbrandContext.mainHandler.removeCallbacks(this.mNotifyBackgroundOverLimitTimeRunnable);
  }
  
  public void registerCloseSystemDialogReceiver() {
    if (this.mCloseSystemReceiver != null)
      return; 
    Application application = AppbrandContext.getInst().getApplicationContext();
    if (application == null)
      return; 
    this.mCloseSystemReceiver = new CloseSystemDialogReceiver();
    try {
      application.registerReceiver(this.mCloseSystemReceiver, new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("ForeBackgroundManager", new Object[] { "registerCloseSystemDialogReceiver", exception });
      this.mCloseSystemReceiver = null;
      return;
    } 
  }
  
  public void registerForeBackgroundListener(ForeBackgroundListener paramForeBackgroundListener) {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull -> 5
    //   4: return
    //   5: aload_0
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield mForeBackgroundListener : Ljava/util/List;
    //   11: aload_1
    //   12: invokeinterface contains : (Ljava/lang/Object;)Z
    //   17: ifne -> 31
    //   20: aload_0
    //   21: getfield mForeBackgroundListener : Ljava/util/List;
    //   24: aload_1
    //   25: invokeinterface add : (Ljava/lang/Object;)Z
    //   30: pop
    //   31: aload_0
    //   32: monitorexit
    //   33: return
    //   34: astore_1
    //   35: aload_0
    //   36: monitorexit
    //   37: aload_1
    //   38: athrow
    // Exception table:
    //   from	to	target	type
    //   7	31	34	finally
    //   31	33	34	finally
    //   35	37	34	finally
  }
  
  public void resumeBackgroundOverLimitTimeStrategy() {
    AppBrandLogger.i("ForeBackgroundManager", new Object[] { "resumeBackgroundOverLimitTimeStrategy mPauseBackgroundOverLimitTimeStrategy", Boolean.valueOf(this.mPauseBackgroundOverLimitTimeStrategy) });
    this.mPauseBackgroundOverLimitTimeStrategy = false;
  }
  
  public void setGoingBackground(boolean paramBoolean) {
    AppBrandLogger.d("ForeBackgroundManager", new Object[] { "setGoingBackground", Boolean.valueOf(paramBoolean) });
    this.mGoingBackground = paramBoolean;
  }
  
  class CloseSystemDialogReceiver extends BroadcastReceiver {
    private CloseSystemDialogReceiver() {}
    
    public void onReceive(Context param1Context, Intent param1Intent) {
      if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(param1Intent.getAction())) {
        String str = param1Intent.getStringExtra("reason");
        if (str != null) {
          str = str.toLowerCase();
          byte b = -1;
          switch (str.hashCode()) {
            case 2014770135:
              if (str.equals("fs_gesture"))
                b = 3; 
              break;
            case 1092716832:
              if (str.equals("homekey"))
                b = 0; 
              break;
            case 350448461:
              if (str.equals("recentapps"))
                b = 1; 
              break;
            case -1408204183:
              if (str.equals("assist"))
                b = 2; 
              break;
          } 
          if (b != 0 && b != 1 && b != 2 && b != 3)
            return; 
          ForeBackgroundManager.this.onTriggerHomeOrRecentApp();
        } 
      } 
    }
  }
  
  public static class DefaultForeBackgroundListener implements ForeBackgroundListener {
    public void onBackground() {}
    
    public void onBackgroundOverLimitTime() {}
    
    public void onForeground() {}
    
    public void onTriggerHomeOrRecentApp() {}
  }
  
  public static interface ForeBackgroundListener {
    void onBackground();
    
    void onBackgroundOverLimitTime();
    
    void onForeground();
    
    void onTriggerHomeOrRecentApp();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\ForeBackgroundManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */