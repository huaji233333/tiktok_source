package com.facebook.react.bridge;

import com.facebook.jni.HybridData;

public abstract class JavaScriptExecutor {
  private RNJavaScriptRuntime.SplitCommonType mCommmonSplit = RNJavaScriptRuntime.SplitCommonType.NONE;
  
  private final HybridData mHybridData;
  
  protected JavaScriptExecutor(HybridData paramHybridData) {
    this.mHybridData = paramHybridData;
  }
  
  protected JavaScriptExecutor(HybridData paramHybridData, RNJavaScriptRuntime.SplitCommonType paramSplitCommonType) {
    this.mHybridData = paramHybridData;
    this.mCommmonSplit = paramSplitCommonType;
  }
  
  public void close() {
    this.mHybridData.resetNative();
  }
  
  public RNJavaScriptRuntime.SplitCommonType getCommonSplit() {
    return this.mCommmonSplit;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\JavaScriptExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */