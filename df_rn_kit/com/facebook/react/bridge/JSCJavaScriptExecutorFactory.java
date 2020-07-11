package com.facebook.react.bridge;

public class JSCJavaScriptExecutorFactory implements JavaScriptExecutorFactory {
  private final String mAppName;
  
  private final String mDeviceName;
  
  private final RNJavaScriptRuntime.SplitCommonType mSplitCommon;
  
  public JSCJavaScriptExecutorFactory(String paramString1, String paramString2, RNJavaScriptRuntime.SplitCommonType paramSplitCommonType) {
    this.mAppName = paramString1;
    this.mDeviceName = paramString2;
    this.mSplitCommon = paramSplitCommonType;
  }
  
  public JavaScriptExecutor create() throws Exception {
    WritableNativeMap writableNativeMap = new WritableNativeMap();
    writableNativeMap.putString("OwnerIdentity", "ReactNative");
    writableNativeMap.putString("AppIdentity", this.mAppName);
    writableNativeMap.putString("DeviceIdentity", this.mDeviceName);
    return new JSCJavaScriptExecutor(writableNativeMap, this.mSplitCommon);
  }
  
  public JavaScriptExecutor create(RNJavaScriptRuntime.SplitCommonType paramSplitCommonType) throws Exception {
    WritableNativeMap writableNativeMap = new WritableNativeMap();
    writableNativeMap.putString("OwnerIdentity", "ReactNative");
    writableNativeMap.putString("AppIdentity", this.mAppName);
    writableNativeMap.putString("DeviceIdentity", this.mDeviceName);
    return new JSCJavaScriptExecutor(writableNativeMap, paramSplitCommonType);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\JSCJavaScriptExecutorFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */