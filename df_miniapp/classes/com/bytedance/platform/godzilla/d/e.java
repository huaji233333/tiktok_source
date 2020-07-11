package com.bytedance.platform.godzilla.d;

import android.text.TextUtils;
import com.bytedance.platform.godzilla.d.a.a;
import com.bytedance.platform.godzilla.d.a.b;
import com.bytedance.platform.godzilla.d.a.c;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

final class e extends ThreadPoolExecutor implements c {
  private String a;
  
  private ThreadLocal<a> b = new ThreadLocal<a>();
  
  private Map<Runnable, c> c = new ConcurrentHashMap<Runnable, c>();
  
  e(int paramInt1, int paramInt2, long paramLong, TimeUnit paramTimeUnit, BlockingQueue<Runnable> paramBlockingQueue, ThreadFactory paramThreadFactory, String paramString) {
    super(paramInt1, paramInt2, paramLong, paramTimeUnit, paramBlockingQueue, paramThreadFactory);
    this.a = paramString;
  }
  
  e(int paramInt1, int paramInt2, long paramLong, TimeUnit paramTimeUnit, BlockingQueue<Runnable> paramBlockingQueue, ThreadFactory paramThreadFactory, RejectedExecutionHandler paramRejectedExecutionHandler, String paramString) {
    super(paramInt1, paramInt2, paramLong, paramTimeUnit, paramBlockingQueue, paramThreadFactory, paramRejectedExecutionHandler);
    this.a = paramString;
  }
  
  public final String a() {
    return !TextUtils.isEmpty(this.a) ? this.a : "PlatformScheduleExecutor";
  }
  
  protected final void afterExecute(Runnable paramRunnable, Throwable paramThrowable) {
    super.afterExecute(paramRunnable, paramThrowable);
    if (b.a())
      b.b(this.b.get()); 
  }
  
  protected final void beforeExecute(Thread paramThread, Runnable paramRunnable) {
    super.beforeExecute(paramThread, paramRunnable);
    if (b.a()) {
      b.b(this.c.remove(paramRunnable));
      a a = new a(this, paramThread, paramRunnable);
      this.b.set(a);
      b.a(a);
    } 
  }
  
  public final void execute(Runnable paramRunnable) {
    if (b.a()) {
      c c1 = new c(this, paramRunnable);
      this.c.put(paramRunnable, c1);
      b.a(c1);
    } 
    super.execute(paramRunnable);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\platform\godzilla\d\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */