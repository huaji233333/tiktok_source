package com.facebook.react.bridge;

import com.facebook.i.a.a;
import com.facebook.jni.HybridData;

public class WritableNativeMap extends ReadableNativeMap implements WritableMap {
  public WritableNativeMap() {
    super(initHybrid());
  }
  
  private static native HybridData initHybrid();
  
  private native void mergeNativeMap(ReadableNativeMap paramReadableNativeMap);
  
  private native void putNativeArray(String paramString, WritableNativeArray paramWritableNativeArray);
  
  private native void putNativeMap(String paramString, WritableNativeMap paramWritableNativeMap);
  
  public void merge(ReadableMap paramReadableMap) {
    a.a(paramReadableMap instanceof ReadableNativeMap, "Illegal type provided");
    mergeNativeMap((ReadableNativeMap)paramReadableMap);
  }
  
  public void putArray(String paramString, WritableArray paramWritableArray) {
    boolean bool;
    if (paramWritableArray == null || paramWritableArray instanceof WritableNativeArray) {
      bool = true;
    } else {
      bool = false;
    } 
    a.a(bool, "Illegal type provided");
    putNativeArray(paramString, (WritableNativeArray)paramWritableArray);
  }
  
  public native void putBoolean(String paramString, boolean paramBoolean);
  
  public native void putDouble(String paramString, double paramDouble);
  
  public native void putInt(String paramString, int paramInt);
  
  public void putMap(String paramString, WritableMap paramWritableMap) {
    boolean bool;
    if (paramWritableMap == null || paramWritableMap instanceof WritableNativeMap) {
      bool = true;
    } else {
      bool = false;
    } 
    a.a(bool, "Illegal type provided");
    putNativeMap(paramString, (WritableNativeMap)paramWritableMap);
  }
  
  public native void putNull(String paramString);
  
  public native void putString(String paramString1, String paramString2);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\WritableNativeMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */