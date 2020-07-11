package com.facebook.react.uimanager;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.ChoreographerCompat;

public abstract class GuardedFrameCallback extends ChoreographerCompat.FrameCallback {
  private final ReactContext mReactContext;
  
  public GuardedFrameCallback(ReactContext paramReactContext) {
    this.mReactContext = paramReactContext;
  }
  
  public final void doFrame(long paramLong) {
    try {
      doFrameGuarded(paramLong);
      return;
    } catch (RuntimeException runtimeException) {
      this.mReactContext.handleException(runtimeException);
      return;
    } 
  }
  
  protected abstract void doFrameGuarded(long paramLong);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\GuardedFrameCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */