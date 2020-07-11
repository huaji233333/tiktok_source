package com.tt.miniapp.base.thread;

import android.os.Handler;
import android.os.Looper;
import d.f;
import d.f.a.a;
import d.f.b.l;
import d.f.b.m;
import d.f.b.u;
import d.f.b.v;
import d.f.b.x;
import d.g;
import d.k.d;
import d.k.h;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class ThreadManager {
  public static final ThreadManager INSTANCE = new ThreadManager();
  
  private static final f mExecutor$delegate;
  
  private static final f mUihandler$delegate = g.a(ThreadManager$mUihandler$2.INSTANCE);
  
  static {
    mExecutor$delegate = g.a(ThreadManager$mExecutor$2.INSTANCE);
  }
  
  private final ExecutorService getMExecutor() {
    return (ExecutorService)mExecutor$delegate.getValue();
  }
  
  private final Handler getMUihandler() {
    return (Handler)mUihandler$delegate.getValue();
  }
  
  public final void execute(Runnable paramRunnable) {
    l.b(paramRunnable, "runnable");
    getMExecutor().execute(paramRunnable);
  }
  
  public final void postInUI(Runnable paramRunnable) {
    l.b(paramRunnable, "runnable");
    getMUihandler().post(paramRunnable);
  }
  
  public final void postInUIDelayed(Runnable paramRunnable, long paramLong) {
    l.b(paramRunnable, "runnable");
    getMUihandler().postDelayed(paramRunnable, paramLong);
  }
  
  static final class ThreadManager$mExecutor$2 extends m implements a<ThreadPoolExecutor> {
    public static final ThreadManager$mExecutor$2 INSTANCE = new ThreadManager$mExecutor$2();
    
    ThreadManager$mExecutor$2() {
      super(0);
    }
    
    public final ThreadPoolExecutor invoke() {
      return new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 2147483647, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
    }
  }
  
  static final class ThreadManager$mUihandler$2 extends m implements a<Handler> {
    public static final ThreadManager$mUihandler$2 INSTANCE = new ThreadManager$mUihandler$2();
    
    ThreadManager$mUihandler$2() {
      super(0);
    }
    
    public final Handler invoke() {
      return new Handler(Looper.getMainLooper());
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\base\thread\ThreadManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */