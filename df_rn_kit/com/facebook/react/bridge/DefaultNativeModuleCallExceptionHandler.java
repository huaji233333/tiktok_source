package com.facebook.react.bridge;

public class DefaultNativeModuleCallExceptionHandler implements NativeModuleCallExceptionHandler {
  public void handleException(Exception paramException) {
    if (paramException instanceof RuntimeException)
      throw (RuntimeException)paramException; 
    throw new RuntimeException(paramException);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\DefaultNativeModuleCallExceptionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */