package com.facebook.react.bridge;

public interface NativeModule {
  boolean canOverrideExistingModule();
  
  String getName();
  
  void initialize();
  
  void onCatalystInstanceDestroy();
  
  public static interface NativeMethod {
    String getType();
    
    void invoke(JSInstance param1JSInstance, ReadableNativeArray param1ReadableNativeArray);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\NativeModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */