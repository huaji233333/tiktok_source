package com.facebook.react.devsupport;

import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import java.io.File;

@ReactModule(name = "JSCHeapCapture", needsEagerInit = true)
public class JSCHeapCapture extends ReactContextBaseJavaModule {
  private CaptureCallback mCaptureInProgress;
  
  public JSCHeapCapture(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
  }
  
  @ReactMethod
  public void captureComplete(String paramString1, String paramString2) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mCaptureInProgress : Lcom/facebook/react/devsupport/JSCHeapCapture$CaptureCallback;
    //   6: ifnull -> 55
    //   9: aload_2
    //   10: ifnonnull -> 33
    //   13: aload_0
    //   14: getfield mCaptureInProgress : Lcom/facebook/react/devsupport/JSCHeapCapture$CaptureCallback;
    //   17: new java/io/File
    //   20: dup
    //   21: aload_1
    //   22: invokespecial <init> : (Ljava/lang/String;)V
    //   25: invokeinterface onSuccess : (Ljava/io/File;)V
    //   30: goto -> 50
    //   33: aload_0
    //   34: getfield mCaptureInProgress : Lcom/facebook/react/devsupport/JSCHeapCapture$CaptureCallback;
    //   37: new com/facebook/react/devsupport/JSCHeapCapture$CaptureException
    //   40: dup
    //   41: aload_2
    //   42: invokespecial <init> : (Ljava/lang/String;)V
    //   45: invokeinterface onFailure : (Lcom/facebook/react/devsupport/JSCHeapCapture$CaptureException;)V
    //   50: aload_0
    //   51: aconst_null
    //   52: putfield mCaptureInProgress : Lcom/facebook/react/devsupport/JSCHeapCapture$CaptureCallback;
    //   55: aload_0
    //   56: monitorexit
    //   57: return
    //   58: astore_1
    //   59: aload_0
    //   60: monitorexit
    //   61: aload_1
    //   62: athrow
    // Exception table:
    //   from	to	target	type
    //   2	9	58	finally
    //   13	30	58	finally
    //   33	50	58	finally
    //   50	55	58	finally
  }
  
  public void captureHeap(String paramString, CaptureCallback paramCaptureCallback) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mCaptureInProgress : Lcom/facebook/react/devsupport/JSCHeapCapture$CaptureCallback;
    //   6: ifnull -> 27
    //   9: aload_2
    //   10: new com/facebook/react/devsupport/JSCHeapCapture$CaptureException
    //   13: dup
    //   14: ldc 'Heap capture already in progress.'
    //   16: invokespecial <init> : (Ljava/lang/String;)V
    //   19: invokeinterface onFailure : (Lcom/facebook/react/devsupport/JSCHeapCapture$CaptureException;)V
    //   24: aload_0
    //   25: monitorexit
    //   26: return
    //   27: new java/lang/StringBuilder
    //   30: dup
    //   31: invokespecial <init> : ()V
    //   34: astore_3
    //   35: aload_3
    //   36: aload_1
    //   37: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   40: pop
    //   41: aload_3
    //   42: ldc '/capture.json'
    //   44: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   47: pop
    //   48: new java/io/File
    //   51: dup
    //   52: aload_3
    //   53: invokevirtual toString : ()Ljava/lang/String;
    //   56: invokespecial <init> : (Ljava/lang/String;)V
    //   59: astore_1
    //   60: aload_1
    //   61: invokevirtual delete : ()Z
    //   64: pop
    //   65: aload_0
    //   66: invokevirtual getReactApplicationContext : ()Lcom/facebook/react/bridge/ReactApplicationContext;
    //   69: ldc com/facebook/react/devsupport/JSCHeapCapture$HeapCapture
    //   71: invokevirtual getJSModule : (Ljava/lang/Class;)Lcom/facebook/react/bridge/JavaScriptModule;
    //   74: checkcast com/facebook/react/devsupport/JSCHeapCapture$HeapCapture
    //   77: astore_3
    //   78: aload_3
    //   79: ifnonnull -> 100
    //   82: aload_2
    //   83: new com/facebook/react/devsupport/JSCHeapCapture$CaptureException
    //   86: dup
    //   87: ldc 'Heap capture js module not registered.'
    //   89: invokespecial <init> : (Ljava/lang/String;)V
    //   92: invokeinterface onFailure : (Lcom/facebook/react/devsupport/JSCHeapCapture$CaptureException;)V
    //   97: aload_0
    //   98: monitorexit
    //   99: return
    //   100: aload_0
    //   101: aload_2
    //   102: putfield mCaptureInProgress : Lcom/facebook/react/devsupport/JSCHeapCapture$CaptureCallback;
    //   105: aload_3
    //   106: aload_1
    //   107: invokevirtual getPath : ()Ljava/lang/String;
    //   110: invokeinterface captureHeap : (Ljava/lang/String;)V
    //   115: aload_0
    //   116: monitorexit
    //   117: return
    //   118: astore_1
    //   119: aload_0
    //   120: monitorexit
    //   121: aload_1
    //   122: athrow
    // Exception table:
    //   from	to	target	type
    //   2	24	118	finally
    //   27	78	118	finally
    //   82	97	118	finally
    //   100	115	118	finally
  }
  
  public String getName() {
    return "JSCHeapCapture";
  }
  
  public static interface CaptureCallback {
    void onFailure(JSCHeapCapture.CaptureException param1CaptureException);
    
    void onSuccess(File param1File);
  }
  
  public static class CaptureException extends Exception {
    CaptureException(String param1String) {
      super(param1String);
    }
    
    CaptureException(String param1String, Throwable param1Throwable) {
      super(param1String, param1Throwable);
    }
  }
  
  public static interface HeapCapture extends JavaScriptModule {
    void captureHeap(String param1String);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\devsupport\JSCHeapCapture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */