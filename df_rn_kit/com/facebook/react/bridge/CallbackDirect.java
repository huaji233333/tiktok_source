package com.facebook.react.bridge;

public class CallbackDirect implements Callback {
  private final long mCallbackPoint;
  
  private boolean mHasCalled;
  
  private final JSInstance mJSInstance;
  
  public CallbackDirect(JSInstance paramJSInstance, long paramLong) {
    this.mJSInstance = paramJSInstance;
    this.mCallbackPoint = paramLong;
  }
  
  public static CallbackDirect createCallbackDirect(JSInstance paramJSInstance, long paramLong) {
    return new CallbackDirect(paramJSInstance, paramLong);
  }
  
  public void invoke(Object... paramVarArgs) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mHasCalled : Z
    //   6: ifne -> 31
    //   9: aload_0
    //   10: getfield mJSInstance : Lcom/facebook/react/bridge/JSInstance;
    //   13: aload_0
    //   14: getfield mCallbackPoint : J
    //   17: aload_1
    //   18: invokestatic fromJavaArgs : ([Ljava/lang/Object;)Lcom/facebook/react/bridge/WritableNativeArray;
    //   21: invokeinterface invokeCallbackDirect : (JLcom/facebook/react/bridge/NativeArray;)V
    //   26: aload_0
    //   27: iconst_1
    //   28: putfield mHasCalled : Z
    //   31: aload_0
    //   32: monitorexit
    //   33: return
    //   34: astore_1
    //   35: aload_0
    //   36: monitorexit
    //   37: aload_1
    //   38: athrow
    // Exception table:
    //   from	to	target	type
    //   2	31	34	finally
    //   31	33	34	finally
    //   35	37	34	finally
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\CallbackDirect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */