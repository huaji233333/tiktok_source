package com.facebook.react.uimanager;

import android.app.Activity;
import android.content.Context;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;

public class ThemedReactContext extends ReactContext {
  private final ReactApplicationContext mReactApplicationContext;
  
  public ThemedReactContext(ReactApplicationContext paramReactApplicationContext, Context paramContext) {
    super(paramContext);
    initializeWithInstance(paramReactApplicationContext.getCatalystInstance());
    this.mReactApplicationContext = paramReactApplicationContext;
    try {
      return;
    } finally {
      paramReactApplicationContext = null;
    } 
  }
  
  public void addLifecycleEventListener(LifecycleEventListener paramLifecycleEventListener) {
    this.mReactApplicationContext.addLifecycleEventListener(paramLifecycleEventListener);
  }
  
  public Activity getCurrentActivity() {
    return this.mReactApplicationContext.getCurrentActivity();
  }
  
  public boolean hasCurrentActivity() {
    return this.mReactApplicationContext.hasCurrentActivity();
  }
  
  public void removeLifecycleEventListener(LifecycleEventListener paramLifecycleEventListener) {
    this.mReactApplicationContext.removeLifecycleEventListener(paramLifecycleEventListener);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\ThemedReactContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */