package com.facebook.react.bridge;

import com.facebook.jni.HybridData;

public abstract class NativeArray {
  private HybridData mHybridData;
  
  protected NativeArray(HybridData paramHybridData) {
    this.mHybridData = paramHybridData;
  }
  
  public native String toString();
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\NativeArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */