package com.facebook.react.bridge;

import android.app.Activity;

public abstract class ReactContextBaseJavaModule extends BaseJavaModule {
  private final ReactApplicationContext mReactApplicationContext;
  
  public ReactContextBaseJavaModule(ReactApplicationContext paramReactApplicationContext) {
    this.mReactApplicationContext = paramReactApplicationContext;
  }
  
  protected final Activity getCurrentActivity() {
    return this.mReactApplicationContext.getCurrentActivity();
  }
  
  public final ReactApplicationContext getReactApplicationContext() {
    return this.mReactApplicationContext;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\ReactContextBaseJavaModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */