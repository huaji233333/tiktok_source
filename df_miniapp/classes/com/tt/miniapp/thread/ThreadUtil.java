package com.tt.miniapp.thread;

import android.os.Looper;
import com.storage.async.Action;
import com.storage.async.Observable;
import com.storage.async.Scheduler;
import com.storage.async.Subscriber;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import java.util.concurrent.ThreadFactory;

public class ThreadUtil {
  public static void cancelUIRunnable(Runnable paramRunnable) {
    if (paramRunnable == null)
      return; 
    AppbrandContext.mainHandler.removeCallbacks(paramRunnable);
  }
  
  public static Thread getThread(Runnable paramRunnable, String paramString) {
    return new Thread(paramRunnable, paramString);
  }
  
  public static boolean isUIThread() {
    return (Looper.getMainLooper() == Looper.myLooper());
  }
  
  public static void runNotOnUIThread(Runnable paramRunnable) {
    if (isUIThread()) {
      ThreadPools.defaults().execute(paramRunnable);
      return;
    } 
    paramRunnable.run();
  }
  
  public static void runOnUIThread(Runnable paramRunnable) {
    runOnUIThread(paramRunnable, true);
  }
  
  public static void runOnUIThread(Runnable paramRunnable, long paramLong) {
    if (paramLong <= 0L) {
      runOnUIThread(paramRunnable);
      return;
    } 
    AppbrandContext.mainHandler.postDelayed(paramRunnable, paramLong);
  }
  
  public static void runOnUIThread(Runnable paramRunnable, boolean paramBoolean) {
    if (isUIThread() && paramBoolean) {
      paramRunnable.run();
      return;
    } 
    AppbrandContext.mainHandler.post(paramRunnable);
  }
  
  public static void runOnWorkThread(Action paramAction, Scheduler paramScheduler) {
    runOnWorkThread(paramAction, paramScheduler, true);
  }
  
  public static void runOnWorkThread(Action paramAction, Scheduler paramScheduler, boolean paramBoolean) {
    if (paramBoolean || isUIThread()) {
      Observable.create(paramAction).schudleOn(paramScheduler).subscribe(new Subscriber() {
            public final void onError(Throwable param1Throwable) {
              AppBrandLogger.eWithThrowable("ThreadUtil", "runOnWorkThread", param1Throwable);
            }
            
            public final void onSuccess() {}
            
            public final void onSuccess(Object param1Object) {}
          });
      return;
    } 
    paramAction.act();
  }
  
  public static ThreadFactory threadFactory(final String name, final boolean daemon) {
    return new ThreadFactory() {
        public final Thread newThread(Runnable param1Runnable) {
          param1Runnable = new Thread(param1Runnable, name);
          param1Runnable.setDaemon(daemon);
          return (Thread)param1Runnable;
        }
      };
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\thread\ThreadUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */