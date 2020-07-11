package com.facebook.react.bridge;

import com.facebook.jni.HybridData;

class JSCJavaScriptExecutor extends JavaScriptExecutor {
  JSCJavaScriptExecutor(ReadableNativeMap paramReadableNativeMap) {
    super(initHybrid(paramReadableNativeMap));
  }
  
  JSCJavaScriptExecutor(ReadableNativeMap paramReadableNativeMap, RNJavaScriptRuntime.SplitCommonType paramSplitCommonType) {
    super(initHybrid(paramReadableNativeMap), paramSplitCommonType);
  }
  
  private static native HybridData initHybrid(ReadableNativeMap paramReadableNativeMap);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\JSCJavaScriptExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */