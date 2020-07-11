package com.facebook.react.bridge;

public final class CallbackImpl implements Callback {
  private final int mCallbackId;
  
  private boolean mInvoked;
  
  private final JSInstance mJSInstance;
  
  public CallbackImpl(JSInstance paramJSInstance, int paramInt) {
    this.mJSInstance = paramJSInstance;
    this.mCallbackId = paramInt;
  }
  
  public final void invoke(Object... paramVarArgs) {
    if (!this.mInvoked) {
      this.mJSInstance.invokeCallback(this.mCallbackId, Arguments.fromJavaArgs(paramVarArgs));
      this.mInvoked = true;
      return;
    } 
    throw new RuntimeException("Illegal callback invocation from native module. This callback type only permits a single invocation from native code.");
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\CallbackImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */