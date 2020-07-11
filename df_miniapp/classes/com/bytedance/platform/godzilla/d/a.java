package com.bytedance.platform.godzilla.d;

import android.os.Process;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public final class a implements ThreadFactory {
  public final h a;
  
  private final String b;
  
  private final ThreadGroup c;
  
  private final AtomicInteger d;
  
  public a(String paramString, h paramh) {
    ThreadGroup threadGroup;
    this.d = new AtomicInteger(1);
    this.a = paramh;
    SecurityManager securityManager = System.getSecurityManager();
    if (securityManager != null) {
      threadGroup = securityManager.getThreadGroup();
    } else {
      threadGroup = Thread.currentThread().getThreadGroup();
    } 
    this.c = threadGroup;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append("-thread-");
    this.b = stringBuilder.toString();
  }
  
  public final Thread newThread(Runnable paramRunnable) {
    ThreadGroup threadGroup = this.c;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.b);
    stringBuilder.append(this.d.getAndIncrement());
    paramRunnable = new Thread(this, threadGroup, paramRunnable, stringBuilder.toString(), 0L) {
        public final void run() {
          Process.setThreadPriority(10);
          if (this.a.a != null)
            try {
              return;
            } finally {
              Exception exception = null;
              this.a.a.a(exception);
            }  
          super.run();
        }
      };
    if (paramRunnable.isDaemon())
      paramRunnable.setDaemon(false); 
    return (Thread)paramRunnable;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\platform\godzilla\d\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */