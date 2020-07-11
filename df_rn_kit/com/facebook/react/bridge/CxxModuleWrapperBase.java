package com.facebook.react.bridge;

import com.facebook.jni.HybridData;

public class CxxModuleWrapperBase implements NativeModule {
  private HybridData mHybridData;
  
  protected CxxModuleWrapperBase(HybridData paramHybridData) {
    this.mHybridData = paramHybridData;
  }
  
  public boolean canOverrideExistingModule() {
    return false;
  }
  
  public native String getName();
  
  public void initialize() {}
  
  public void onCatalystInstanceDestroy() {
    this.mHybridData.resetNative();
  }
  
  protected void resetModule(HybridData paramHybridData) {
    HybridData hybridData = this.mHybridData;
    if (paramHybridData != hybridData) {
      hybridData.resetNative();
      this.mHybridData = paramHybridData;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\CxxModuleWrapperBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */