package com.tt.miniapp.thread;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PlatformHandlerThread {
  private static volatile Handler backgroundHandler;
  
  private static volatile HandlerThread backgroundHandlerThread;
  
  private static volatile Handler defaultHandler;
  
  private static volatile HandlerThread defaultHandlerThread;
  
  private static volatile Handler defaultMainHandler = new Handler(Looper.getMainLooper());
  
  private static HashMap<String, HandlerThread> handlerThreads = new HashMap<String, HandlerThread>();
  
  public static Handler getBackgroundHandler() {
    // Byte code:
    //   0: ldc com/tt/miniapp/thread/PlatformHandlerThread
    //   2: monitorenter
    //   3: getstatic com/tt/miniapp/thread/PlatformHandlerThread.backgroundHandler : Landroid/os/Handler;
    //   6: ifnonnull -> 13
    //   9: invokestatic getBackgroundHandlerThread : ()Landroid/os/HandlerThread;
    //   12: pop
    //   13: getstatic com/tt/miniapp/thread/PlatformHandlerThread.backgroundHandler : Landroid/os/Handler;
    //   16: astore_0
    //   17: ldc com/tt/miniapp/thread/PlatformHandlerThread
    //   19: monitorexit
    //   20: aload_0
    //   21: areturn
    //   22: astore_0
    //   23: ldc com/tt/miniapp/thread/PlatformHandlerThread
    //   25: monitorexit
    //   26: aload_0
    //   27: athrow
    // Exception table:
    //   from	to	target	type
    //   3	13	22	finally
    //   13	20	22	finally
    //   23	26	22	finally
  }
  
  public static HandlerThread getBackgroundHandlerThread() {
    // Byte code:
    //   0: ldc com/tt/miniapp/thread/PlatformHandlerThread
    //   2: monitorenter
    //   3: getstatic com/tt/miniapp/thread/PlatformHandlerThread.backgroundHandlerThread : Landroid/os/HandlerThread;
    //   6: ifnonnull -> 45
    //   9: new com/tt/miniapp/thread/PlatformHandlerThread$NoQuitHandlerThread
    //   12: dup
    //   13: ldc 'platform-back-handler'
    //   15: bipush #10
    //   17: invokespecial <init> : (Ljava/lang/String;I)V
    //   20: astore_0
    //   21: aload_0
    //   22: putstatic com/tt/miniapp/thread/PlatformHandlerThread.backgroundHandlerThread : Landroid/os/HandlerThread;
    //   25: aload_0
    //   26: invokevirtual start : ()V
    //   29: new android/os/Handler
    //   32: dup
    //   33: getstatic com/tt/miniapp/thread/PlatformHandlerThread.backgroundHandlerThread : Landroid/os/HandlerThread;
    //   36: invokevirtual getLooper : ()Landroid/os/Looper;
    //   39: invokespecial <init> : (Landroid/os/Looper;)V
    //   42: putstatic com/tt/miniapp/thread/PlatformHandlerThread.backgroundHandler : Landroid/os/Handler;
    //   45: getstatic com/tt/miniapp/thread/PlatformHandlerThread.backgroundHandlerThread : Landroid/os/HandlerThread;
    //   48: astore_0
    //   49: ldc com/tt/miniapp/thread/PlatformHandlerThread
    //   51: monitorexit
    //   52: aload_0
    //   53: areturn
    //   54: astore_0
    //   55: ldc com/tt/miniapp/thread/PlatformHandlerThread
    //   57: monitorexit
    //   58: aload_0
    //   59: athrow
    // Exception table:
    //   from	to	target	type
    //   3	45	54	finally
    //   45	52	54	finally
    //   55	58	54	finally
  }
  
  public static Handler getDefaultHandler() {
    // Byte code:
    //   0: ldc com/tt/miniapp/thread/PlatformHandlerThread
    //   2: monitorenter
    //   3: getstatic com/tt/miniapp/thread/PlatformHandlerThread.defaultHandler : Landroid/os/Handler;
    //   6: ifnonnull -> 13
    //   9: invokestatic getDefaultHandlerThread : ()Landroid/os/HandlerThread;
    //   12: pop
    //   13: getstatic com/tt/miniapp/thread/PlatformHandlerThread.defaultHandler : Landroid/os/Handler;
    //   16: astore_0
    //   17: ldc com/tt/miniapp/thread/PlatformHandlerThread
    //   19: monitorexit
    //   20: aload_0
    //   21: areturn
    //   22: astore_0
    //   23: ldc com/tt/miniapp/thread/PlatformHandlerThread
    //   25: monitorexit
    //   26: aload_0
    //   27: athrow
    // Exception table:
    //   from	to	target	type
    //   3	13	22	finally
    //   13	20	22	finally
    //   23	26	22	finally
  }
  
  public static HandlerThread getDefaultHandlerThread() {
    // Byte code:
    //   0: ldc com/tt/miniapp/thread/PlatformHandlerThread
    //   2: monitorenter
    //   3: getstatic com/tt/miniapp/thread/PlatformHandlerThread.defaultHandlerThread : Landroid/os/HandlerThread;
    //   6: ifnonnull -> 43
    //   9: new com/tt/miniapp/thread/PlatformHandlerThread$NoQuitHandlerThread
    //   12: dup
    //   13: ldc 'platform-handler'
    //   15: invokespecial <init> : (Ljava/lang/String;)V
    //   18: astore_0
    //   19: aload_0
    //   20: putstatic com/tt/miniapp/thread/PlatformHandlerThread.defaultHandlerThread : Landroid/os/HandlerThread;
    //   23: aload_0
    //   24: invokevirtual start : ()V
    //   27: new android/os/Handler
    //   30: dup
    //   31: getstatic com/tt/miniapp/thread/PlatformHandlerThread.defaultHandlerThread : Landroid/os/HandlerThread;
    //   34: invokevirtual getLooper : ()Landroid/os/Looper;
    //   37: invokespecial <init> : (Landroid/os/Looper;)V
    //   40: putstatic com/tt/miniapp/thread/PlatformHandlerThread.defaultHandler : Landroid/os/Handler;
    //   43: getstatic com/tt/miniapp/thread/PlatformHandlerThread.defaultHandlerThread : Landroid/os/HandlerThread;
    //   46: astore_0
    //   47: ldc com/tt/miniapp/thread/PlatformHandlerThread
    //   49: monitorexit
    //   50: aload_0
    //   51: areturn
    //   52: astore_0
    //   53: ldc com/tt/miniapp/thread/PlatformHandlerThread
    //   55: monitorexit
    //   56: aload_0
    //   57: athrow
    // Exception table:
    //   from	to	target	type
    //   3	43	52	finally
    //   43	50	52	finally
    //   53	56	52	finally
  }
  
  public static Handler getDefaultMainHandler() {
    return defaultMainHandler;
  }
  
  public static HandlerThread getNewHandlerThread(String paramString) {
    return getNewHandlerThread(paramString, 0, "");
  }
  
  public static HandlerThread getNewHandlerThread(String paramString1, int paramInt, String paramString2) {
    Iterator<Map.Entry> iterator = handlerThreads.entrySet().iterator();
    while (iterator.hasNext()) {
      if (!((HandlerThread)((Map.Entry)iterator.next()).getValue()).isAlive())
        iterator.remove(); 
    } 
    HandlerThread handlerThread2 = handlerThreads.get(paramString1);
    HandlerThread handlerThread1 = handlerThread2;
    if (handlerThread2 == null) {
      handlerThread1 = new InnerHandlerThread(paramString1, paramInt);
      handlerThread1.start();
      handlerThreads.put(paramString1, handlerThread1);
    } 
    return handlerThread1;
  }
  
  public static HandlerThread getNewHandlerThread(String paramString1, String paramString2) {
    return getNewHandlerThread(paramString1, 0, paramString2);
  }
  
  public static class InnerHandlerThread extends HandlerThread {
    private volatile boolean isStart;
    
    public InnerHandlerThread(String param1String) {
      super(param1String);
    }
    
    public InnerHandlerThread(String param1String, int param1Int) {
      super(param1String, param1Int);
    }
    
    public void start() {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield isStart : Z
      //   6: istore_1
      //   7: iload_1
      //   8: ifeq -> 14
      //   11: aload_0
      //   12: monitorexit
      //   13: return
      //   14: aload_0
      //   15: iconst_1
      //   16: putfield isStart : Z
      //   19: aload_0
      //   20: invokespecial start : ()V
      //   23: aload_0
      //   24: monitorexit
      //   25: return
      //   26: astore_2
      //   27: aload_0
      //   28: monitorexit
      //   29: aload_2
      //   30: athrow
      // Exception table:
      //   from	to	target	type
      //   2	7	26	finally
      //   14	23	26	finally
    }
  }
  
  public static class NoQuitHandlerThread extends InnerHandlerThread {
    public NoQuitHandlerThread(String param1String) {
      super(param1String);
    }
    
    public NoQuitHandlerThread(String param1String, int param1Int) {
      super(param1String, param1Int);
    }
    
    public boolean quit() {
      return true;
    }
    
    public boolean quitSafely() {
      return true;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\thread\PlatformHandlerThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */