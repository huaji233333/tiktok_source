package com.tt.miniapphost;

import com.storage.async.Scheduler;
import java.util.concurrent.ThreadPoolExecutor;

public class LaunchThreadPool implements Scheduler {
  private static volatile boolean sIsLowPriorityLaunch;
  
  private ThreadPoolExecutor mHighPriorityExecutor = getHighPriorityThreadPool();
  
  private ThreadPoolExecutor mLowPriorityExecutor = getLowPriorityThreadPool();
  
  private volatile ThreadPoolExecutor sHighPriorityThreadPool;
  
  private volatile ThreadPoolExecutor sLowPriorityThreadPool;
  
  private LaunchThreadPool() {}
  
  private ThreadPoolExecutor getHighPriorityThreadPool() {
    // Byte code:
    //   0: aload_0
    //   1: getfield sHighPriorityThreadPool : Ljava/util/concurrent/ThreadPoolExecutor;
    //   4: ifnonnull -> 74
    //   7: ldc com/tt/miniapphost/LaunchThreadPool
    //   9: monitorenter
    //   10: aload_0
    //   11: getfield sHighPriorityThreadPool : Ljava/util/concurrent/ThreadPoolExecutor;
    //   14: ifnonnull -> 62
    //   17: aload_0
    //   18: new java/util/concurrent/ThreadPoolExecutor
    //   21: dup
    //   22: bipush #8
    //   24: bipush #8
    //   26: ldc2_w 5
    //   29: getstatic java/util/concurrent/TimeUnit.SECONDS : Ljava/util/concurrent/TimeUnit;
    //   32: new java/util/concurrent/LinkedBlockingQueue
    //   35: dup
    //   36: invokespecial <init> : ()V
    //   39: new com/tt/miniapp/thread/HighPriorityThreadFactory
    //   42: dup
    //   43: ldc 'high-priority'
    //   45: invokespecial <init> : (Ljava/lang/String;)V
    //   48: invokespecial <init> : (IIJLjava/util/concurrent/TimeUnit;Ljava/util/concurrent/BlockingQueue;Ljava/util/concurrent/ThreadFactory;)V
    //   51: putfield sHighPriorityThreadPool : Ljava/util/concurrent/ThreadPoolExecutor;
    //   54: aload_0
    //   55: getfield sHighPriorityThreadPool : Ljava/util/concurrent/ThreadPoolExecutor;
    //   58: iconst_1
    //   59: invokevirtual allowCoreThreadTimeOut : (Z)V
    //   62: ldc com/tt/miniapphost/LaunchThreadPool
    //   64: monitorexit
    //   65: goto -> 74
    //   68: astore_1
    //   69: ldc com/tt/miniapphost/LaunchThreadPool
    //   71: monitorexit
    //   72: aload_1
    //   73: athrow
    //   74: aload_0
    //   75: getfield sHighPriorityThreadPool : Ljava/util/concurrent/ThreadPoolExecutor;
    //   78: areturn
    // Exception table:
    //   from	to	target	type
    //   10	62	68	finally
    //   62	65	68	finally
    //   69	72	68	finally
  }
  
  public static LaunchThreadPool getInst() {
    return Holder.sInstance;
  }
  
  private ThreadPoolExecutor getLowPriorityThreadPool() {
    // Byte code:
    //   0: aload_0
    //   1: getfield sLowPriorityThreadPool : Ljava/util/concurrent/ThreadPoolExecutor;
    //   4: ifnonnull -> 72
    //   7: ldc com/tt/miniapphost/LaunchThreadPool
    //   9: monitorenter
    //   10: aload_0
    //   11: getfield sLowPriorityThreadPool : Ljava/util/concurrent/ThreadPoolExecutor;
    //   14: ifnonnull -> 60
    //   17: aload_0
    //   18: new java/util/concurrent/ThreadPoolExecutor
    //   21: dup
    //   22: iconst_4
    //   23: iconst_4
    //   24: ldc2_w 5
    //   27: getstatic java/util/concurrent/TimeUnit.SECONDS : Ljava/util/concurrent/TimeUnit;
    //   30: new java/util/concurrent/LinkedBlockingQueue
    //   33: dup
    //   34: invokespecial <init> : ()V
    //   37: new com/tt/miniapp/thread/LowPriorityThreadFactory
    //   40: dup
    //   41: ldc 'low-priority'
    //   43: invokespecial <init> : (Ljava/lang/String;)V
    //   46: invokespecial <init> : (IIJLjava/util/concurrent/TimeUnit;Ljava/util/concurrent/BlockingQueue;Ljava/util/concurrent/ThreadFactory;)V
    //   49: putfield sLowPriorityThreadPool : Ljava/util/concurrent/ThreadPoolExecutor;
    //   52: aload_0
    //   53: getfield sLowPriorityThreadPool : Ljava/util/concurrent/ThreadPoolExecutor;
    //   56: iconst_1
    //   57: invokevirtual allowCoreThreadTimeOut : (Z)V
    //   60: ldc com/tt/miniapphost/LaunchThreadPool
    //   62: monitorexit
    //   63: goto -> 72
    //   66: astore_1
    //   67: ldc com/tt/miniapphost/LaunchThreadPool
    //   69: monitorexit
    //   70: aload_1
    //   71: athrow
    //   72: aload_0
    //   73: getfield sLowPriorityThreadPool : Ljava/util/concurrent/ThreadPoolExecutor;
    //   76: areturn
    // Exception table:
    //   from	to	target	type
    //   10	60	66	finally
    //   60	63	66	finally
    //   67	70	66	finally
  }
  
  public void execute(Runnable paramRunnable) {
    if (sIsLowPriorityLaunch) {
      this.mLowPriorityExecutor.execute(paramRunnable);
      return;
    } 
    this.mHighPriorityExecutor.execute(paramRunnable);
  }
  
  public void setLowPriorityLaunch(boolean paramBoolean) {
    // Byte code:
    //   0: getstatic com/tt/miniapphost/LaunchThreadPool.sIsLowPriorityLaunch : Z
    //   3: iload_1
    //   4: if_icmpeq -> 31
    //   7: ldc com/tt/miniapphost/LaunchThreadPool
    //   9: monitorenter
    //   10: getstatic com/tt/miniapphost/LaunchThreadPool.sIsLowPriorityLaunch : Z
    //   13: iload_1
    //   14: if_icmpeq -> 21
    //   17: iload_1
    //   18: putstatic com/tt/miniapphost/LaunchThreadPool.sIsLowPriorityLaunch : Z
    //   21: ldc com/tt/miniapphost/LaunchThreadPool
    //   23: monitorexit
    //   24: return
    //   25: astore_2
    //   26: ldc com/tt/miniapphost/LaunchThreadPool
    //   28: monitorexit
    //   29: aload_2
    //   30: athrow
    //   31: return
    // Exception table:
    //   from	to	target	type
    //   10	21	25	finally
    //   21	24	25	finally
    //   26	29	25	finally
  }
  
  static class Holder {
    public static LaunchThreadPool sInstance = new LaunchThreadPool();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\LaunchThreadPool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */