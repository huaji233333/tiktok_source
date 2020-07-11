package com.bytedance.platform.godzilla.d;

import android.text.TextUtils;
import com.bytedance.platform.godzilla.d.a.a;
import com.bytedance.platform.godzilla.d.a.b;
import com.bytedance.platform.godzilla.d.a.c;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

final class f extends ScheduledThreadPoolExecutor implements c {
  private String a;
  
  private ThreadLocal<a> b = new ThreadLocal<a>();
  
  private Map<Runnable, c> c = new ConcurrentHashMap<Runnable, c>();
  
  f(int paramInt, ThreadFactory paramThreadFactory, String paramString) {
    super(paramInt, paramThreadFactory);
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


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\platform\godzilla\d\f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */