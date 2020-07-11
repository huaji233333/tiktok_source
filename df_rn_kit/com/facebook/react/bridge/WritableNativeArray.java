package com.facebook.react.bridge;

import com.facebook.i.a.a;
import com.facebook.jni.HybridData;

public class WritableNativeArray extends ReadableNativeArray implements WritableArray {
  public WritableNativeArray() {
    super(initHybrid());
  }
  
  private static native HybridData initHybrid();
  
  private native void pushNativeArray(WritableNativeArray paramWritableNativeArray);
  
  private native void pushNativeMap(WritableNativeMap paramWritableNativeMap);
  
  public void pushArray(WritableArray paramWritableArray) {
    boolean bool;
    if (paramWritableArray == null || paramWritableArray instanceof WritableNativeArray) {
      bool = true;
    } else {
      bool = false;
    } 
    a.a(bool, "Illegal type provided");
    pushNativeArray((WritableNativeArray)paramWritableArray);
  }
  
  public native void pushBoolean(boolean paramBoolean);
  
  public native void pushDouble(double paramDouble);
  
  public native void pushInt(int paramInt);
  
  public void pushMap(WritableMap paramWritableMap) {
    boolean bool;
    if (paramWritableMap == null || paramWritableMap instanceof WritableNativeMap) {
      bool = true;
    } else {
      bool = false;
    } 
    a.a(bool, "Illegal type provided");
    pushNativeMap((WritableNativeMap)paramWritableMap);
  }
  
  public native void pushNull();
  
  public native void pushString(String paramString);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\WritableNativeArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */