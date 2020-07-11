package com.facebook.react.bridge;

public abstract class GuardedRunnable implements Runnable {
  private final ReactContext mReactContext;
  
  public GuardedRunnable(ReactContext paramReactContext) {
    this.mReactContext = paramReactContext;
  }
  
  public final void run() {
    try {
      runGuarded();
      return;
    } catch (RuntimeException runtimeException) {
      this.mReactContext.handleException(runtimeException);
      return;
    } 
  }
  
  public abstract void runGuarded();
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\GuardedRunnable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */