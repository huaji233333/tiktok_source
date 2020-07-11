package com.facebook.jni;

public class NativeRunnable implements Runnable {
  private final HybridData mHybridData;
  
  private NativeRunnable(HybridData paramHybridData) {
    this.mHybridData = paramHybridData;
  }
  
  public native void run();
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\jni\NativeRunnable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */