package com.bytedance.platform.godzilla.d;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class g {
  public static b a;
  
  public static h b;
  
  private static final int c;
  
  private static final int d;
  
  private static b e;
  
  private static h f = new h() {
      public final void a(Throwable param1Throwable) {
        if (g.b != null)
          g.b.a(param1Throwable); 
      }
    };
  
  private static volatile ThreadPoolExecutor g;
  
  private static volatile ThreadPoolExecutor h;
  
  private static volatile ScheduledThreadPoolExecutor i;
  
  private static volatile ThreadPoolExecutor j;
  
  public static ThreadPoolExecutor a() {
    // Byte code:
    //   0: getstatic com/bytedance/platform/godzilla/d/g.g : Ljava/util/concurrent/ThreadPoolExecutor;
    //   3: ifnonnull -> 168
    //   6: ldc com/bytedance/platform/godzilla/d/g
    //   8: monitorenter
    //   9: getstatic com/bytedance/platform/godzilla/d/g.g : Ljava/util/concurrent/ThreadPoolExecutor;
    //   12: ifnonnull -> 156
    //   15: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   18: ifnull -> 108
    //   21: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   24: getfield a : Lcom/bytedance/platform/godzilla/d/g$a;
    //   27: ifnull -> 108
    //   30: new com/bytedance/platform/godzilla/d/e
    //   33: dup
    //   34: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   37: getfield a : Lcom/bytedance/platform/godzilla/d/g$a;
    //   40: getfield a : I
    //   43: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   46: getfield a : Lcom/bytedance/platform/godzilla/d/g$a;
    //   49: getfield b : I
    //   52: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   55: getfield a : Lcom/bytedance/platform/godzilla/d/g$a;
    //   58: getfield e : J
    //   61: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   64: getfield a : Lcom/bytedance/platform/godzilla/d/g$a;
    //   67: getfield f : Ljava/util/concurrent/TimeUnit;
    //   70: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   73: getfield a : Lcom/bytedance/platform/godzilla/d/g$a;
    //   76: getfield c : Ljava/util/concurrent/BlockingQueue;
    //   79: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   82: getfield a : Lcom/bytedance/platform/godzilla/d/g$a;
    //   85: getfield g : Ljava/util/concurrent/ThreadFactory;
    //   88: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   91: getfield a : Lcom/bytedance/platform/godzilla/d/g$a;
    //   94: getfield d : Ljava/util/concurrent/RejectedExecutionHandler;
    //   97: ldc 'platform-io'
    //   99: invokespecial <init> : (IIJLjava/util/concurrent/TimeUnit;Ljava/util/concurrent/BlockingQueue;Ljava/util/concurrent/ThreadFactory;Ljava/util/concurrent/RejectedExecutionHandler;Ljava/lang/String;)V
    //   102: putstatic com/bytedance/platform/godzilla/d/g.g : Ljava/util/concurrent/ThreadPoolExecutor;
    //   105: goto -> 156
    //   108: new com/bytedance/platform/godzilla/d/e
    //   111: dup
    //   112: iconst_0
    //   113: sipush #128
    //   116: ldc2_w 60
    //   119: getstatic java/util/concurrent/TimeUnit.SECONDS : Ljava/util/concurrent/TimeUnit;
    //   122: new java/util/concurrent/SynchronousQueue
    //   125: dup
    //   126: invokespecial <init> : ()V
    //   129: new com/bytedance/platform/godzilla/d/a
    //   132: dup
    //   133: ldc 'platform-io'
    //   135: getstatic com/bytedance/platform/godzilla/d/g.f : Lcom/bytedance/platform/godzilla/d/h;
    //   138: invokespecial <init> : (Ljava/lang/String;Lcom/bytedance/platform/godzilla/d/h;)V
    //   141: new com/bytedance/platform/godzilla/d/g$2
    //   144: dup
    //   145: invokespecial <init> : ()V
    //   148: ldc 'platform-io'
    //   150: invokespecial <init> : (IIJLjava/util/concurrent/TimeUnit;Ljava/util/concurrent/BlockingQueue;Ljava/util/concurrent/ThreadFactory;Ljava/util/concurrent/RejectedExecutionHandler;Ljava/lang/String;)V
    //   153: putstatic com/bytedance/platform/godzilla/d/g.g : Ljava/util/concurrent/ThreadPoolExecutor;
    //   156: ldc com/bytedance/platform/godzilla/d/g
    //   158: monitorexit
    //   159: goto -> 168
    //   162: astore_0
    //   163: ldc com/bytedance/platform/godzilla/d/g
    //   165: monitorexit
    //   166: aload_0
    //   167: athrow
    //   168: getstatic com/bytedance/platform/godzilla/d/g.g : Ljava/util/concurrent/ThreadPoolExecutor;
    //   171: areturn
    // Exception table:
    //   from	to	target	type
    //   9	105	162	finally
    //   108	156	162	finally
    //   156	159	162	finally
    //   163	166	162	finally
  }
  
  public static ThreadPoolExecutor b() {
    // Byte code:
    //   0: getstatic com/bytedance/platform/godzilla/d/g.h : Ljava/util/concurrent/ThreadPoolExecutor;
    //   3: ifnonnull -> 193
    //   6: ldc com/bytedance/platform/godzilla/d/g
    //   8: monitorenter
    //   9: getstatic com/bytedance/platform/godzilla/d/g.h : Ljava/util/concurrent/ThreadPoolExecutor;
    //   12: ifnonnull -> 181
    //   15: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   18: ifnull -> 123
    //   21: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   24: getfield b : Lcom/bytedance/platform/godzilla/d/g$a;
    //   27: ifnull -> 123
    //   30: new com/bytedance/platform/godzilla/d/e
    //   33: dup
    //   34: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   37: getfield b : Lcom/bytedance/platform/godzilla/d/g$a;
    //   40: getfield a : I
    //   43: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   46: getfield b : Lcom/bytedance/platform/godzilla/d/g$a;
    //   49: getfield b : I
    //   52: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   55: getfield b : Lcom/bytedance/platform/godzilla/d/g$a;
    //   58: getfield e : J
    //   61: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   64: getfield b : Lcom/bytedance/platform/godzilla/d/g$a;
    //   67: getfield f : Ljava/util/concurrent/TimeUnit;
    //   70: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   73: getfield b : Lcom/bytedance/platform/godzilla/d/g$a;
    //   76: getfield c : Ljava/util/concurrent/BlockingQueue;
    //   79: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   82: getfield b : Lcom/bytedance/platform/godzilla/d/g$a;
    //   85: getfield g : Ljava/util/concurrent/ThreadFactory;
    //   88: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   91: getfield b : Lcom/bytedance/platform/godzilla/d/g$a;
    //   94: getfield d : Ljava/util/concurrent/RejectedExecutionHandler;
    //   97: ldc 'platform-default'
    //   99: invokespecial <init> : (IIJLjava/util/concurrent/TimeUnit;Ljava/util/concurrent/BlockingQueue;Ljava/util/concurrent/ThreadFactory;Ljava/util/concurrent/RejectedExecutionHandler;Ljava/lang/String;)V
    //   102: astore_0
    //   103: aload_0
    //   104: putstatic com/bytedance/platform/godzilla/d/g.h : Ljava/util/concurrent/ThreadPoolExecutor;
    //   107: aload_0
    //   108: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   111: getfield b : Lcom/bytedance/platform/godzilla/d/g$a;
    //   114: getfield h : Z
    //   117: invokevirtual allowCoreThreadTimeOut : (Z)V
    //   120: goto -> 181
    //   123: new com/bytedance/platform/godzilla/d/e
    //   126: dup
    //   127: getstatic com/bytedance/platform/godzilla/d/g.d : I
    //   130: iconst_4
    //   131: invokestatic min : (II)I
    //   134: getstatic com/bytedance/platform/godzilla/d/g.d : I
    //   137: iconst_4
    //   138: invokestatic min : (II)I
    //   141: ldc2_w 60
    //   144: getstatic java/util/concurrent/TimeUnit.SECONDS : Ljava/util/concurrent/TimeUnit;
    //   147: new java/util/concurrent/LinkedBlockingQueue
    //   150: dup
    //   151: invokespecial <init> : ()V
    //   154: new com/bytedance/platform/godzilla/d/a
    //   157: dup
    //   158: ldc 'platform-default'
    //   160: getstatic com/bytedance/platform/godzilla/d/g.f : Lcom/bytedance/platform/godzilla/d/h;
    //   163: invokespecial <init> : (Ljava/lang/String;Lcom/bytedance/platform/godzilla/d/h;)V
    //   166: ldc 'platform-default'
    //   168: invokespecial <init> : (IIJLjava/util/concurrent/TimeUnit;Ljava/util/concurrent/BlockingQueue;Ljava/util/concurrent/ThreadFactory;Ljava/lang/String;)V
    //   171: astore_0
    //   172: aload_0
    //   173: putstatic com/bytedance/platform/godzilla/d/g.h : Ljava/util/concurrent/ThreadPoolExecutor;
    //   176: aload_0
    //   177: iconst_1
    //   178: invokevirtual allowCoreThreadTimeOut : (Z)V
    //   181: ldc com/bytedance/platform/godzilla/d/g
    //   183: monitorexit
    //   184: goto -> 193
    //   187: astore_0
    //   188: ldc com/bytedance/platform/godzilla/d/g
    //   190: monitorexit
    //   191: aload_0
    //   192: athrow
    //   193: getstatic com/bytedance/platform/godzilla/d/g.h : Ljava/util/concurrent/ThreadPoolExecutor;
    //   196: areturn
    // Exception table:
    //   from	to	target	type
    //   9	120	187	finally
    //   123	181	187	finally
    //   181	184	187	finally
    //   188	191	187	finally
  }
  
  public static ScheduledExecutorService c() {
    // Byte code:
    //   0: getstatic com/bytedance/platform/godzilla/d/g.i : Ljava/util/concurrent/ScheduledThreadPoolExecutor;
    //   3: ifnonnull -> 122
    //   6: ldc com/bytedance/platform/godzilla/d/g
    //   8: monitorenter
    //   9: getstatic com/bytedance/platform/godzilla/d/g.i : Ljava/util/concurrent/ScheduledThreadPoolExecutor;
    //   12: ifnonnull -> 110
    //   15: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   18: ifnull -> 78
    //   21: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   24: getfield c : Lcom/bytedance/platform/godzilla/d/g$a;
    //   27: ifnull -> 78
    //   30: new com/bytedance/platform/godzilla/d/f
    //   33: dup
    //   34: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   37: getfield c : Lcom/bytedance/platform/godzilla/d/g$a;
    //   40: getfield a : I
    //   43: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   46: getfield c : Lcom/bytedance/platform/godzilla/d/g$a;
    //   49: getfield g : Ljava/util/concurrent/ThreadFactory;
    //   52: ldc 'platform-schedule'
    //   54: invokespecial <init> : (ILjava/util/concurrent/ThreadFactory;Ljava/lang/String;)V
    //   57: putstatic com/bytedance/platform/godzilla/d/g.i : Ljava/util/concurrent/ScheduledThreadPoolExecutor;
    //   60: getstatic com/bytedance/platform/godzilla/d/g.i : Ljava/util/concurrent/ScheduledThreadPoolExecutor;
    //   63: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   66: getfield c : Lcom/bytedance/platform/godzilla/d/g$a;
    //   69: getfield h : Z
    //   72: invokevirtual allowCoreThreadTimeOut : (Z)V
    //   75: goto -> 110
    //   78: new com/bytedance/platform/godzilla/d/f
    //   81: dup
    //   82: iconst_1
    //   83: new com/bytedance/platform/godzilla/d/a
    //   86: dup
    //   87: ldc 'platform-schedule'
    //   89: getstatic com/bytedance/platform/godzilla/d/g.f : Lcom/bytedance/platform/godzilla/d/h;
    //   92: invokespecial <init> : (Ljava/lang/String;Lcom/bytedance/platform/godzilla/d/h;)V
    //   95: ldc 'platform-schedule'
    //   97: invokespecial <init> : (ILjava/util/concurrent/ThreadFactory;Ljava/lang/String;)V
    //   100: putstatic com/bytedance/platform/godzilla/d/g.i : Ljava/util/concurrent/ScheduledThreadPoolExecutor;
    //   103: getstatic com/bytedance/platform/godzilla/d/g.i : Ljava/util/concurrent/ScheduledThreadPoolExecutor;
    //   106: iconst_1
    //   107: invokevirtual allowCoreThreadTimeOut : (Z)V
    //   110: ldc com/bytedance/platform/godzilla/d/g
    //   112: monitorexit
    //   113: goto -> 122
    //   116: astore_0
    //   117: ldc com/bytedance/platform/godzilla/d/g
    //   119: monitorexit
    //   120: aload_0
    //   121: athrow
    //   122: getstatic com/bytedance/platform/godzilla/d/g.i : Ljava/util/concurrent/ScheduledThreadPoolExecutor;
    //   125: areturn
    //   126: astore_0
    //   127: goto -> 110
    // Exception table:
    //   from	to	target	type
    //   9	60	116	finally
    //   60	75	126	java/lang/Exception
    //   60	75	116	finally
    //   78	103	116	finally
    //   103	110	126	java/lang/Exception
    //   103	110	116	finally
    //   110	113	116	finally
    //   117	120	116	finally
  }
  
  public static ThreadPoolExecutor d() {
    // Byte code:
    //   0: getstatic com/bytedance/platform/godzilla/d/g.j : Ljava/util/concurrent/ThreadPoolExecutor;
    //   3: ifnonnull -> 156
    //   6: ldc com/bytedance/platform/godzilla/d/g
    //   8: monitorenter
    //   9: getstatic com/bytedance/platform/godzilla/d/g.j : Ljava/util/concurrent/ThreadPoolExecutor;
    //   12: ifnonnull -> 144
    //   15: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   18: ifnull -> 98
    //   21: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   24: getfield d : Lcom/bytedance/platform/godzilla/d/g$a;
    //   27: ifnull -> 98
    //   30: new com/bytedance/platform/godzilla/d/e
    //   33: dup
    //   34: iconst_1
    //   35: iconst_1
    //   36: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   39: getfield d : Lcom/bytedance/platform/godzilla/d/g$a;
    //   42: getfield e : J
    //   45: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   48: getfield d : Lcom/bytedance/platform/godzilla/d/g$a;
    //   51: getfield f : Ljava/util/concurrent/TimeUnit;
    //   54: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   57: getfield d : Lcom/bytedance/platform/godzilla/d/g$a;
    //   60: getfield c : Ljava/util/concurrent/BlockingQueue;
    //   63: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   66: getfield d : Lcom/bytedance/platform/godzilla/d/g$a;
    //   69: getfield g : Ljava/util/concurrent/ThreadFactory;
    //   72: ldc 'platform-single'
    //   74: invokespecial <init> : (IIJLjava/util/concurrent/TimeUnit;Ljava/util/concurrent/BlockingQueue;Ljava/util/concurrent/ThreadFactory;Ljava/lang/String;)V
    //   77: astore_0
    //   78: aload_0
    //   79: putstatic com/bytedance/platform/godzilla/d/g.j : Ljava/util/concurrent/ThreadPoolExecutor;
    //   82: aload_0
    //   83: getstatic com/bytedance/platform/godzilla/d/g.e : Lcom/bytedance/platform/godzilla/d/b;
    //   86: getfield d : Lcom/bytedance/platform/godzilla/d/g$a;
    //   89: getfield h : Z
    //   92: invokevirtual allowCoreThreadTimeOut : (Z)V
    //   95: goto -> 144
    //   98: new com/bytedance/platform/godzilla/d/e
    //   101: dup
    //   102: iconst_1
    //   103: iconst_1
    //   104: ldc2_w 60
    //   107: getstatic java/util/concurrent/TimeUnit.SECONDS : Ljava/util/concurrent/TimeUnit;
    //   110: new java/util/concurrent/LinkedBlockingQueue
    //   113: dup
    //   114: invokespecial <init> : ()V
    //   117: new com/bytedance/platform/godzilla/d/a
    //   120: dup
    //   121: ldc 'platform-single'
    //   123: getstatic com/bytedance/platform/godzilla/d/g.f : Lcom/bytedance/platform/godzilla/d/h;
    //   126: invokespecial <init> : (Ljava/lang/String;Lcom/bytedance/platform/godzilla/d/h;)V
    //   129: ldc 'platform-single'
    //   131: invokespecial <init> : (IIJLjava/util/concurrent/TimeUnit;Ljava/util/concurrent/BlockingQueue;Ljava/util/concurrent/ThreadFactory;Ljava/lang/String;)V
    //   134: astore_0
    //   135: aload_0
    //   136: putstatic com/bytedance/platform/godzilla/d/g.j : Ljava/util/concurrent/ThreadPoolExecutor;
    //   139: aload_0
    //   140: iconst_1
    //   141: invokevirtual allowCoreThreadTimeOut : (Z)V
    //   144: ldc com/bytedance/platform/godzilla/d/g
    //   146: monitorexit
    //   147: goto -> 156
    //   150: astore_0
    //   151: ldc com/bytedance/platform/godzilla/d/g
    //   153: monitorexit
    //   154: aload_0
    //   155: athrow
    //   156: getstatic com/bytedance/platform/godzilla/d/g.j : Ljava/util/concurrent/ThreadPoolExecutor;
    //   159: areturn
    // Exception table:
    //   from	to	target	type
    //   9	95	150	finally
    //   98	144	150	finally
    //   144	147	150	finally
    //   151	154	150	finally
  }
  
  static {
    int i = Runtime.getRuntime().availableProcessors();
    c = i;
    if (i > 0) {
      i = c;
    } else {
      i = 1;
    } 
    d = i;
  }
  
  public static final class a {
    public int a;
    
    public int b;
    
    public BlockingQueue<Runnable> c;
    
    public RejectedExecutionHandler d;
    
    public long e;
    
    public TimeUnit f;
    
    public ThreadFactory g;
    
    public boolean h;
  }
  
  public static interface b {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\platform\godzilla\d\g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */