package com.facebook.react.devsupport;

import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = "JSDevSupport", needsEagerInit = true)
public class JSDevSupport extends ReactContextBaseJavaModule {
  private volatile DevSupportCallback mCurrentCallback;
  
  public JSDevSupport(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
  }
  
  public void getJSHierarchy(String paramString, DevSupportCallback paramDevSupportCallback) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mCurrentCallback : Lcom/facebook/react/devsupport/JSDevSupport$DevSupportCallback;
    //   6: ifnull -> 27
    //   9: aload_2
    //   10: new java/lang/RuntimeException
    //   13: dup
    //   14: ldc 'JS Hierarchy download already in progress.'
    //   16: invokespecial <init> : (Ljava/lang/String;)V
    //   19: invokeinterface onFailure : (Ljava/lang/Exception;)V
    //   24: aload_0
    //   25: monitorexit
    //   26: return
    //   27: aload_0
    //   28: invokevirtual getReactApplicationContext : ()Lcom/facebook/react/bridge/ReactApplicationContext;
    //   31: ldc com/facebook/react/devsupport/JSDevSupport$JSDevSupportModule
    //   33: invokevirtual getJSModule : (Ljava/lang/Class;)Lcom/facebook/react/bridge/JavaScriptModule;
    //   36: checkcast com/facebook/react/devsupport/JSDevSupport$JSDevSupportModule
    //   39: astore_3
    //   40: aload_3
    //   41: ifnonnull -> 62
    //   44: aload_2
    //   45: new com/facebook/react/devsupport/JSCHeapCapture$CaptureException
    //   48: dup
    //   49: ldc 'JSDevSupport module not registered.'
    //   51: invokespecial <init> : (Ljava/lang/String;)V
    //   54: invokeinterface onFailure : (Ljava/lang/Exception;)V
    //   59: aload_0
    //   60: monitorexit
    //   61: return
    //   62: aload_0
    //   63: aload_2
    //   64: putfield mCurrentCallback : Lcom/facebook/react/devsupport/JSDevSupport$DevSupportCallback;
    //   67: aload_3
    //   68: aload_1
    //   69: invokeinterface getJSHierarchy : (Ljava/lang/String;)V
    //   74: aload_0
    //   75: monitorexit
    //   76: return
    //   77: astore_1
    //   78: aload_0
    //   79: monitorexit
    //   80: aload_1
    //   81: athrow
    // Exception table:
    //   from	to	target	type
    //   2	24	77	finally
    //   27	40	77	finally
    //   44	59	77	finally
    //   62	74	77	finally
  }
  
  public String getName() {
    return "JSDevSupport";
  }
  
  @ReactMethod
  public void setResult(String paramString1, String paramString2) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mCurrentCallback : Lcom/facebook/react/devsupport/JSDevSupport$DevSupportCallback;
    //   6: ifnull -> 43
    //   9: aload_2
    //   10: ifnonnull -> 26
    //   13: aload_0
    //   14: getfield mCurrentCallback : Lcom/facebook/react/devsupport/JSDevSupport$DevSupportCallback;
    //   17: aload_1
    //   18: invokeinterface onSuccess : (Ljava/lang/String;)V
    //   23: goto -> 43
    //   26: aload_0
    //   27: getfield mCurrentCallback : Lcom/facebook/react/devsupport/JSDevSupport$DevSupportCallback;
    //   30: new java/lang/RuntimeException
    //   33: dup
    //   34: aload_2
    //   35: invokespecial <init> : (Ljava/lang/String;)V
    //   38: invokeinterface onFailure : (Ljava/lang/Exception;)V
    //   43: aload_0
    //   44: aconst_null
    //   45: putfield mCurrentCallback : Lcom/facebook/react/devsupport/JSDevSupport$DevSupportCallback;
    //   48: aload_0
    //   49: monitorexit
    //   50: return
    //   51: astore_1
    //   52: aload_0
    //   53: monitorexit
    //   54: aload_1
    //   55: athrow
    // Exception table:
    //   from	to	target	type
    //   2	9	51	finally
    //   13	23	51	finally
    //   26	43	51	finally
    //   43	48	51	finally
  }
  
  public static interface DevSupportCallback {
    void onFailure(Exception param1Exception);
    
    void onSuccess(String param1String);
  }
  
  public static interface JSDevSupportModule extends JavaScriptModule {
    void getJSHierarchy(String param1String);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\devsupport\JSDevSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */