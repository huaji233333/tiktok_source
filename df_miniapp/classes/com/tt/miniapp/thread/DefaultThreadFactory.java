package com.tt.miniapp.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultThreadFactory implements ThreadFactory {
  private final ThreadGroup group;
  
  private final String namePrefix;
  
  private final AtomicInteger threadNumber;
  
  public DefaultThreadFactory(String paramString) {
    ThreadGroup threadGroup;
    this.threadNumber = new AtomicInteger(1);
    SecurityManager securityManager = System.getSecurityManager();
    if (securityManager != null) {
      threadGroup = securityManager.getThreadGroup();
    } else {
      threadGroup = Thread.currentThread().getThreadGroup();
    } 
    this.group = threadGroup;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append("-thread-");
    this.namePrefix = stringBuilder.toString();
  }
  
  public Thread newThread(Runnable paramRunnable) {
    ThreadGroup threadGroup = this.group;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.namePrefix);
    stringBuilder.append(this.threadNumber.getAndIncrement());
    paramRunnable = new Thread(threadGroup, paramRunnable, stringBuilder.toString(), 0L) {
        public void run() {
          super.run();
        }
      };
    if (paramRunnable.getPriority() != 5)
      paramRunnable.setPriority(5); 
    if (paramRunnable.isDaemon())
      paramRunnable.setDaemon(false); 
    return (Thread)paramRunnable;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\thread\DefaultThreadFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */