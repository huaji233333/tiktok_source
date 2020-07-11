package com.tt.miniapp.thread;

import java.util.concurrent.ThreadPoolExecutor;

class BDPThreadPool {
  private static final int AVAILABLE_PROCESSORS;
  
  private static final int CPU_COUNT;
  
  private static volatile ThreadPoolExecutor sDefaultThreadPool;
  
  static {
    int i = Runtime.getRuntime().availableProcessors();
    AVAILABLE_PROCESSORS = i;
    if (i > 0) {
      i = AVAILABLE_PROCESSORS;
    } else {
      i = 1;
    } 
    CPU_COUNT = i;
  }
  
  static ThreadPoolExecutor getDefaultThreadPool() {
    // Byte code:
    //   0: getstatic com/tt/miniapp/thread/BDPThreadPool.sDefaultThreadPool : Ljava/util/concurrent/ThreadPoolExecutor;
    //   3: ifnonnull -> 80
    //   6: ldc com/tt/miniapp/thread/BDPThreadPool
    //   8: monitorenter
    //   9: getstatic com/tt/miniapp/thread/BDPThreadPool.sDefaultThreadPool : Ljava/util/concurrent/ThreadPoolExecutor;
    //   12: ifnonnull -> 68
    //   15: new java/util/concurrent/ThreadPoolExecutor
    //   18: dup
    //   19: getstatic com/tt/miniapp/thread/BDPThreadPool.CPU_COUNT : I
    //   22: iconst_4
    //   23: invokestatic min : (II)I
    //   26: getstatic com/tt/miniapp/thread/BDPThreadPool.CPU_COUNT : I
    //   29: iconst_2
    //   30: imul
    //   31: iconst_1
    //   32: iadd
    //   33: ldc2_w 30
    //   36: getstatic java/util/concurrent/TimeUnit.SECONDS : Ljava/util/concurrent/TimeUnit;
    //   39: new java/util/concurrent/LinkedBlockingQueue
    //   42: dup
    //   43: invokespecial <init> : ()V
    //   46: new com/tt/miniapp/thread/DefaultThreadFactory
    //   49: dup
    //   50: ldc 'bdp-defaults'
    //   52: invokespecial <init> : (Ljava/lang/String;)V
    //   55: invokespecial <init> : (IIJLjava/util/concurrent/TimeUnit;Ljava/util/concurrent/BlockingQueue;Ljava/util/concurrent/ThreadFactory;)V
    //   58: astore_0
    //   59: aload_0
    //   60: putstatic com/tt/miniapp/thread/BDPThreadPool.sDefaultThreadPool : Ljava/util/concurrent/ThreadPoolExecutor;
    //   63: aload_0
    //   64: iconst_1
    //   65: invokevirtual allowCoreThreadTimeOut : (Z)V
    //   68: ldc com/tt/miniapp/thread/BDPThreadPool
    //   70: monitorexit
    //   71: goto -> 80
    //   74: astore_0
    //   75: ldc com/tt/miniapp/thread/BDPThreadPool
    //   77: monitorexit
    //   78: aload_0
    //   79: athrow
    //   80: getstatic com/tt/miniapp/thread/BDPThreadPool.sDefaultThreadPool : Ljava/util/concurrent/ThreadPoolExecutor;
    //   83: areturn
    // Exception table:
    //   from	to	target	type
    //   9	68	74	finally
    //   68	71	74	finally
    //   75	78	74	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\thread\BDPThreadPool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */