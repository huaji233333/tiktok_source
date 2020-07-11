package com.facebook.react.bridge;

import java.util.Map;

public abstract class BaseJavaModule implements NativeModule {
  public boolean canOverrideExistingModule() {
    return false;
  }
  
  public Map<String, Object> getConstants() {
    return null;
  }
  
  public boolean hasConstants() {
    return false;
  }
  
  public void initialize() {}
  
  public void onCatalystInstanceDestroy() {}
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\BaseJavaModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */