package com.tt.miniapp.thread;

import com.bytedance.platform.godzilla.d.g;
import com.storage.async.Scheduler;
import com.storage.async.SchedulerCreator;
import java.util.concurrent.ExecutorService;

public class ThreadPools {
  private static final SchedulerCreator backgroundCreator;
  
  private static final SchedulerCreator defaultCreator;
  
  private static final SchedulerCreator justCreator = new JustSchedulerCreator();
  
  private static final SchedulerCreator longIOCreator;
  
  private static final SchedulerCreator singleCreator;
  
  private static final SchedulerCreator timerCreator;
  
  private static final SchedulerCreator uiCreator = new UISchedulerCreator();
  
  static {
    defaultCreator = new DefaultCreator();
    longIOCreator = new LongIOCreator();
    backgroundCreator = new BackGroundCreator();
    singleCreator = new SingleCreator();
    timerCreator = new TimerCreator();
  }
  
  public static Scheduler backGround() {
    return backgroundCreator.create();
  }
  
  public static Scheduler defaults() {
    return defaultCreator.create();
  }
  
  public static Scheduler just() {
    return justCreator.create();
  }
  
  public static Scheduler longIO() {
    return longIOCreator.create();
  }
  
  public static Scheduler single() {
    return singleCreator.create();
  }
  
  public static Scheduler timer() {
    return timerCreator.create();
  }
  
  public static Scheduler ui() {
    return uiCreator.create();
  }
  
  static class BackGroundCreator implements SchedulerCreator {
    public Scheduler create() {
      return ThreadPools.BackGroundHolder.DEFAULT;
    }
  }
  
  static final class BackGroundHolder {
    static final Scheduler DEFAULT = new ThreadPools.BackGroundScheduler();
  }
  
  static class BackGroundScheduler implements Scheduler {
    ExecutorService threadPoolExecutor = g.b();
    
    public void execute(Runnable param1Runnable) {
      this.threadPoolExecutor.execute(param1Runnable);
    }
  }
  
  static class DefaultCreator implements SchedulerCreator {
    public Scheduler create() {
      return ThreadPools.DefaultHolder.DEFAULT;
    }
  }
  
  static final class DefaultHolder {
    static final Scheduler DEFAULT = new ThreadPools.DefaultScheduler();
  }
  
  static class DefaultScheduler implements Scheduler {
    ExecutorService threadPoolExecutor = BDPThreadPool.getDefaultThreadPool();
    
    public void execute(Runnable param1Runnable) {
      this.threadPoolExecutor.execute(param1Runnable);
    }
  }
  
  static final class JustHolder {
    static final Scheduler DEFAULT = new ThreadPools.JustScheduler();
  }
  
  static class JustScheduler implements Scheduler {
    private JustScheduler() {}
    
    public void execute(Runnable param1Runnable) {
      param1Runnable.run();
    }
  }
  
  static class JustSchedulerCreator implements SchedulerCreator {
    public Scheduler create() {
      return ThreadPools.JustHolder.DEFAULT;
    }
  }
  
  static class LongIOCreator implements SchedulerCreator {
    public Scheduler create() {
      return ThreadPools.LongIOHolder.DEFAULT;
    }
  }
  
  static final class LongIOHolder {
    static final Scheduler DEFAULT = new ThreadPools.LongIOScheduler();
  }
  
  static class LongIOScheduler implements Scheduler {
    ExecutorService threadPoolExecutor = g.a();
    
    public void execute(Runnable param1Runnable) {
      this.threadPoolExecutor.execute(param1Runnable);
    }
  }
  
  static class SingleCreator implements SchedulerCreator {
    public Scheduler create() {
      return ThreadPools.SingleHolder.DEFAULT;
    }
  }
  
  static final class SingleHolder {
    static final Scheduler DEFAULT = new ThreadPools.SingleScheduler();
  }
  
  static class SingleScheduler implements Scheduler {
    ExecutorService threadPoolExecutor = g.d();
    
    public void execute(Runnable param1Runnable) {
      this.threadPoolExecutor.execute(param1Runnable);
    }
  }
  
  static class TimerCreator implements SchedulerCreator {
    public Scheduler create() {
      return ThreadPools.TimerHolder.DEFAULT;
    }
  }
  
  static final class TimerHolder {
    static final Scheduler DEFAULT = new ThreadPools.TimerScheduler();
  }
  
  static class TimerScheduler implements Scheduler {
    ExecutorService threadPoolExecutor = g.c();
    
    public void execute(Runnable param1Runnable) {
      this.threadPoolExecutor.execute(param1Runnable);
    }
  }
  
  static final class UIHolder {
    static final Scheduler DEFAULT = new ThreadPools.UIScheduler();
  }
  
  static class UIScheduler implements Scheduler {
    public void execute(Runnable param1Runnable) {
      ThreadUtil.runOnUIThread(param1Runnable);
    }
  }
  
  static class UISchedulerCreator implements SchedulerCreator {
    public Scheduler create() {
      return ThreadPools.UIHolder.DEFAULT;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\thread\ThreadPools.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */