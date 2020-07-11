package com.facebook.react.bridge;

import org.json.JSONObject;

public class ReactBridge {
  private static JSExceptionListener mJSExceptionListener;
  
  private static PageFinishedListener mPageFinishListener;
  
  public static volatile boolean sDidInit;
  
  public static volatile INativeLibraryLoader soLoader;
  
  public static boolean isSupportRN(OnRNLoadExceptionListener paramOnRNLoadExceptionListener) {
    staticInit(paramOnRNLoadExceptionListener);
    return sDidInit;
  }
  
  public static void setJSExceptionListener(JSExceptionListener paramJSExceptionListener) {
    mJSExceptionListener = paramJSExceptionListener;
  }
  
  public static void setPageFinishListener(PageFinishedListener paramPageFinishedListener) {
    mPageFinishListener = paramPageFinishedListener;
  }
  
  private static void staticInit() {
    // Byte code:
    //   0: ldc com/facebook/react/bridge/ReactBridge
    //   2: monitorenter
    //   3: getstatic com/facebook/react/bridge/ReactBridge.sDidInit : Z
    //   6: ifne -> 37
    //   9: getstatic com/facebook/react/bridge/ReactBridge.soLoader : Lcom/facebook/react/bridge/INativeLibraryLoader;
    //   12: ifnull -> 28
    //   15: getstatic com/facebook/react/bridge/ReactBridge.soLoader : Lcom/facebook/react/bridge/INativeLibraryLoader;
    //   18: ldc 'reactnativejni'
    //   20: invokeinterface loadLibrary : (Ljava/lang/String;)V
    //   25: goto -> 33
    //   28: ldc 'reactnativejni'
    //   30: invokestatic com_ss_android_ugc_aweme_lancet_launch_LoadSoLancet_loadLibrary : (Ljava/lang/String;)V
    //   33: iconst_1
    //   34: putstatic com/facebook/react/bridge/ReactBridge.sDidInit : Z
    //   37: ldc com/facebook/react/bridge/ReactBridge
    //   39: monitorexit
    //   40: return
    //   41: astore_0
    //   42: ldc com/facebook/react/bridge/ReactBridge
    //   44: monitorexit
    //   45: aload_0
    //   46: athrow
    // Exception table:
    //   from	to	target	type
    //   3	25	41	finally
    //   28	33	41	finally
    //   33	37	41	finally
  }
  
  public static void staticInit(OnRNLoadExceptionListener paramOnRNLoadExceptionListener) {
    // Byte code:
    //   0: ldc com/facebook/react/bridge/ReactBridge
    //   2: monitorenter
    //   3: invokestatic staticInit : ()V
    //   6: ldc com/facebook/react/bridge/ReactBridge
    //   8: monitorexit
    //   9: return
    //   10: astore_1
    //   11: aload_0
    //   12: ifnull -> 34
    //   15: aload_0
    //   16: aload_1
    //   17: invokevirtual toString : ()Ljava/lang/String;
    //   20: invokeinterface onLoadError : (Ljava/lang/String;)V
    //   25: goto -> 34
    //   28: astore_0
    //   29: ldc com/facebook/react/bridge/ReactBridge
    //   31: monitorexit
    //   32: aload_0
    //   33: athrow
    //   34: ldc com/facebook/react/bridge/ReactBridge
    //   36: monitorexit
    //   37: return
    // Exception table:
    //   from	to	target	type
    //   3	6	10	finally
    //   15	25	28	finally
  }
  
  public static void staticInit(OnRNLoadExceptionListener paramOnRNLoadExceptionListener, INativeLibraryLoader paramINativeLibraryLoader) {
    // Byte code:
    //   0: ldc com/facebook/react/bridge/ReactBridge
    //   2: monitorenter
    //   3: getstatic com/facebook/react/bridge/ReactBridge.soLoader : Lcom/facebook/react/bridge/INativeLibraryLoader;
    //   6: aload_1
    //   7: if_acmpeq -> 14
    //   10: aload_1
    //   11: putstatic com/facebook/react/bridge/ReactBridge.soLoader : Lcom/facebook/react/bridge/INativeLibraryLoader;
    //   14: invokestatic staticInit : ()V
    //   17: ldc com/facebook/react/bridge/ReactBridge
    //   19: monitorexit
    //   20: return
    //   21: astore_1
    //   22: aload_0
    //   23: ifnull -> 45
    //   26: aload_0
    //   27: aload_1
    //   28: invokevirtual toString : ()Ljava/lang/String;
    //   31: invokeinterface onLoadError : (Ljava/lang/String;)V
    //   36: goto -> 45
    //   39: astore_0
    //   40: ldc com/facebook/react/bridge/ReactBridge
    //   42: monitorexit
    //   43: aload_0
    //   44: athrow
    //   45: ldc com/facebook/react/bridge/ReactBridge
    //   47: monitorexit
    //   48: return
    // Exception table:
    //   from	to	target	type
    //   3	14	21	finally
    //   14	17	21	finally
    //   26	36	39	finally
  }
  
  public static void uploadJSException(JSONObject paramJSONObject) {
    JSExceptionListener jSExceptionListener = mJSExceptionListener;
    if (jSExceptionListener != null)
      jSExceptionListener.upLoad(paramJSONObject); 
  }
  
  public static void uploadPageFinishPerf(JSONObject paramJSONObject) {
    PageFinishedListener pageFinishedListener = mPageFinishListener;
    if (pageFinishedListener != null)
      pageFinishedListener.upLoad(paramJSONObject); 
  }
  
  public static interface JSExceptionListener {
    void upLoad(JSONObject param1JSONObject);
  }
  
  class ReactBridge {}
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\ReactBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */