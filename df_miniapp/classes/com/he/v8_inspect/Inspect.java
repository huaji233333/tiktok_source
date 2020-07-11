package com.he.v8_inspect;

public class Inspect {
  private static String debugURL;
  
  private static Runnable devToolConnectCallback;
  
  private static boolean started;
  
  private static void callDevToolConnectCallback() {
    Runnable runnable = devToolConnectCallback;
    if (runnable != null) {
      runnable.run();
      devToolConnectCallback = null;
    } 
  }
  
  private static native void nativeConnect(String paramString);
  
  private static native void nativeListen(int paramInt);
  
  public static native void onDispose(String paramString);
  
  public static native void onNewIsolate(String paramString1, String paramString2);
  
  public static void setDevToolConnectCallback(Runnable paramRunnable) {
    devToolConnectCallback = paramRunnable;
  }
  
  public static void setRemoteDebugURL(String paramString) {
    debugURL = paramString;
  }
  
  public static void start() {
    // Byte code:
    //   0: getstatic com/he/v8_inspect/Inspect.started : Z
    //   3: ifeq -> 7
    //   6: return
    //   7: ldc com/he/v8_inspect/Inspect
    //   9: monitorenter
    //   10: getstatic com/he/v8_inspect/Inspect.started : Z
    //   13: ifeq -> 20
    //   16: ldc com/he/v8_inspect/Inspect
    //   18: monitorexit
    //   19: return
    //   20: getstatic com/he/v8_inspect/Inspect.debugURL : Ljava/lang/String;
    //   23: ifnull -> 35
    //   26: getstatic com/he/v8_inspect/Inspect.debugURL : Ljava/lang/String;
    //   29: invokestatic nativeConnect : (Ljava/lang/String;)V
    //   32: goto -> 41
    //   35: sipush #9229
    //   38: invokestatic nativeListen : (I)V
    //   41: iconst_1
    //   42: putstatic com/he/v8_inspect/Inspect.started : Z
    //   45: ldc com/he/v8_inspect/Inspect
    //   47: monitorexit
    //   48: return
    //   49: astore_0
    //   50: ldc com/he/v8_inspect/Inspect
    //   52: monitorexit
    //   53: aload_0
    //   54: athrow
    // Exception table:
    //   from	to	target	type
    //   10	19	49	finally
    //   20	32	49	finally
    //   35	41	49	finally
    //   41	48	49	finally
    //   50	53	49	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\he\v8_inspect\Inspect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */