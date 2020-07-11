package com.facebook.react.devsupport;

import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import java.util.HashSet;
import java.util.List;

@ReactModule(name = "JSCSamplingProfiler", needsEagerInit = true)
public class JSCSamplingProfiler extends ReactContextBaseJavaModule {
  private static final HashSet<JSCSamplingProfiler> sRegisteredDumpers = new HashSet<JSCSamplingProfiler>();
  
  private String mOperationError;
  
  private boolean mOperationInProgress;
  
  private int mOperationToken;
  
  private SamplingProfiler mSamplingProfiler;
  
  private String mSamplingProfilerResult;
  
  public JSCSamplingProfiler(ReactApplicationContext paramReactApplicationContext) {
    super(paramReactApplicationContext);
  }
  
  private int getOperationToken() throws ProfilerException {
    if (!this.mOperationInProgress) {
      this.mOperationInProgress = true;
      int i = this.mOperationToken + 1;
      this.mOperationToken = i;
      return i;
    } 
    throw new ProfilerException("Another operation already in progress.");
  }
  
  public static List<String> poke(long paramLong) throws ProfilerException {
    // Byte code:
    //   0: ldc com/facebook/react/devsupport/JSCSamplingProfiler
    //   2: monitorenter
    //   3: new java/util/LinkedList
    //   6: dup
    //   7: invokespecial <init> : ()V
    //   10: astore_2
    //   11: getstatic com/facebook/react/devsupport/JSCSamplingProfiler.sRegisteredDumpers : Ljava/util/HashSet;
    //   14: invokevirtual isEmpty : ()Z
    //   17: ifne -> 71
    //   20: getstatic com/facebook/react/devsupport/JSCSamplingProfiler.sRegisteredDumpers : Ljava/util/HashSet;
    //   23: invokevirtual iterator : ()Ljava/util/Iterator;
    //   26: astore_3
    //   27: aload_3
    //   28: invokeinterface hasNext : ()Z
    //   33: ifeq -> 66
    //   36: aload_3
    //   37: invokeinterface next : ()Ljava/lang/Object;
    //   42: checkcast com/facebook/react/devsupport/JSCSamplingProfiler
    //   45: astore #4
    //   47: aload #4
    //   49: lload_0
    //   50: invokespecial pokeHelper : (J)V
    //   53: aload_2
    //   54: aload #4
    //   56: getfield mSamplingProfilerResult : Ljava/lang/String;
    //   59: invokevirtual add : (Ljava/lang/Object;)Z
    //   62: pop
    //   63: goto -> 27
    //   66: ldc com/facebook/react/devsupport/JSCSamplingProfiler
    //   68: monitorexit
    //   69: aload_2
    //   70: areturn
    //   71: new com/facebook/react/devsupport/JSCSamplingProfiler$ProfilerException
    //   74: dup
    //   75: ldc 'No JSC registered'
    //   77: invokespecial <init> : (Ljava/lang/String;)V
    //   80: athrow
    //   81: astore_2
    //   82: ldc com/facebook/react/devsupport/JSCSamplingProfiler
    //   84: monitorexit
    //   85: goto -> 90
    //   88: aload_2
    //   89: athrow
    //   90: goto -> 88
    // Exception table:
    //   from	to	target	type
    //   3	27	81	finally
    //   27	63	81	finally
    //   71	81	81	finally
  }
  
  private void pokeHelper(long paramLong) throws ProfilerException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mSamplingProfiler : Lcom/facebook/react/devsupport/JSCSamplingProfiler$SamplingProfiler;
    //   6: ifnull -> 30
    //   9: aload_0
    //   10: getfield mSamplingProfiler : Lcom/facebook/react/devsupport/JSCSamplingProfiler$SamplingProfiler;
    //   13: aload_0
    //   14: invokespecial getOperationToken : ()I
    //   17: invokeinterface poke : (I)V
    //   22: aload_0
    //   23: lload_1
    //   24: invokespecial waitForOperation : (J)V
    //   27: aload_0
    //   28: monitorexit
    //   29: return
    //   30: new com/facebook/react/devsupport/JSCSamplingProfiler$ProfilerException
    //   33: dup
    //   34: ldc 'SamplingProfiler.js module not connected'
    //   36: invokespecial <init> : (Ljava/lang/String;)V
    //   39: athrow
    //   40: astore_3
    //   41: aload_0
    //   42: monitorexit
    //   43: aload_3
    //   44: athrow
    // Exception table:
    //   from	to	target	type
    //   2	27	40	finally
    //   30	40	40	finally
  }
  
  private static void registerSamplingProfiler(JSCSamplingProfiler paramJSCSamplingProfiler) {
    // Byte code:
    //   0: ldc com/facebook/react/devsupport/JSCSamplingProfiler
    //   2: monitorenter
    //   3: getstatic com/facebook/react/devsupport/JSCSamplingProfiler.sRegisteredDumpers : Ljava/util/HashSet;
    //   6: aload_0
    //   7: invokevirtual contains : (Ljava/lang/Object;)Z
    //   10: ifne -> 25
    //   13: getstatic com/facebook/react/devsupport/JSCSamplingProfiler.sRegisteredDumpers : Ljava/util/HashSet;
    //   16: aload_0
    //   17: invokevirtual add : (Ljava/lang/Object;)Z
    //   20: pop
    //   21: ldc com/facebook/react/devsupport/JSCSamplingProfiler
    //   23: monitorexit
    //   24: return
    //   25: new java/lang/RuntimeException
    //   28: dup
    //   29: ldc 'a JSCSamplingProfiler registered more than once'
    //   31: invokespecial <init> : (Ljava/lang/String;)V
    //   34: athrow
    //   35: astore_0
    //   36: ldc com/facebook/react/devsupport/JSCSamplingProfiler
    //   38: monitorexit
    //   39: aload_0
    //   40: athrow
    // Exception table:
    //   from	to	target	type
    //   3	21	35	finally
    //   25	35	35	finally
  }
  
  private static void unregisterSamplingProfiler(JSCSamplingProfiler paramJSCSamplingProfiler) {
    // Byte code:
    //   0: ldc com/facebook/react/devsupport/JSCSamplingProfiler
    //   2: monitorenter
    //   3: getstatic com/facebook/react/devsupport/JSCSamplingProfiler.sRegisteredDumpers : Ljava/util/HashSet;
    //   6: aload_0
    //   7: invokevirtual remove : (Ljava/lang/Object;)Z
    //   10: pop
    //   11: ldc com/facebook/react/devsupport/JSCSamplingProfiler
    //   13: monitorexit
    //   14: return
    //   15: astore_0
    //   16: ldc com/facebook/react/devsupport/JSCSamplingProfiler
    //   18: monitorexit
    //   19: aload_0
    //   20: athrow
    // Exception table:
    //   from	to	target	type
    //   3	11	15	finally
  }
  
  private void waitForOperation(long paramLong) throws ProfilerException {
    try {
      wait(paramLong);
      if (!this.mOperationInProgress) {
        String str = this.mOperationError;
        if (str == null)
          return; 
        throw new ProfilerException(str);
      } 
      this.mOperationInProgress = false;
      throw new ProfilerException("heap capture timed out.");
    } catch (InterruptedException interruptedException) {
      StringBuilder stringBuilder = new StringBuilder("Waiting for heap capture failed: ");
      stringBuilder.append(interruptedException.getMessage());
      throw new ProfilerException(stringBuilder.toString());
    } 
  }
  
  public String getName() {
    return "JSCSamplingProfiler";
  }
  
  public void initialize() {
    super.initialize();
    this.mSamplingProfiler = (SamplingProfiler)getReactApplicationContext().getJSModule(SamplingProfiler.class);
    registerSamplingProfiler(this);
  }
  
  public void onCatalystInstanceDestroy() {
    super.onCatalystInstanceDestroy();
    unregisterSamplingProfiler(this);
    this.mSamplingProfiler = null;
  }
  
  @ReactMethod
  public void operationComplete(int paramInt, String paramString1, String paramString2) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: iload_1
    //   3: aload_0
    //   4: getfield mOperationToken : I
    //   7: if_icmpne -> 32
    //   10: aload_0
    //   11: iconst_0
    //   12: putfield mOperationInProgress : Z
    //   15: aload_0
    //   16: aload_2
    //   17: putfield mSamplingProfilerResult : Ljava/lang/String;
    //   20: aload_0
    //   21: aload_3
    //   22: putfield mOperationError : Ljava/lang/String;
    //   25: aload_0
    //   26: invokevirtual notify : ()V
    //   29: aload_0
    //   30: monitorexit
    //   31: return
    //   32: new java/lang/RuntimeException
    //   35: dup
    //   36: ldc 'Completed operation is not in progress.'
    //   38: invokespecial <init> : (Ljava/lang/String;)V
    //   41: athrow
    //   42: astore_2
    //   43: aload_0
    //   44: monitorexit
    //   45: aload_2
    //   46: athrow
    // Exception table:
    //   from	to	target	type
    //   2	29	42	finally
    //   32	42	42	finally
  }
  
  public static class ProfilerException extends Exception {
    ProfilerException(String param1String) {
      super(param1String);
    }
  }
  
  public static interface SamplingProfiler extends JavaScriptModule {
    void poke(int param1Int);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\devsupport\JSCSamplingProfiler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */