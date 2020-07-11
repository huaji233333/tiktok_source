package com.facebook.react.bridge;

public interface JSIModuleHolder {
  <T extends JSIModule> T getJSIModule();
  
  Class<? extends JSIModule> getJSIModuleClass();
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\JSIModuleHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */