package com.facebook.react.bridge;

import com.facebook.jni.HybridData;

public class CxxCallbackImpl implements Callback {
  private final HybridData mHybridData;
  
  private CxxCallbackImpl(HybridData paramHybridData) {
    this.mHybridData = paramHybridData;
  }
  
  private native void nativeInvoke(NativeArray paramNativeArray);
  
  public void invoke(Object... paramVarArgs) {
    nativeInvoke(Arguments.fromJavaArgs(paramVarArgs));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\CxxCallbackImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */