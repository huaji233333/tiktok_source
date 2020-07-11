package com.facebook.react.bridge;

import android.os.AsyncTask;

public abstract class GuardedAsyncTask<Params, Progress> extends AsyncTask<Params, Progress, Void> {
  private final ReactContext mReactContext;
  
  public GuardedAsyncTask(ReactContext paramReactContext) {
    this.mReactContext = paramReactContext;
  }
  
  protected final Void doInBackground(Params... paramVarArgs) {
    try {
      doInBackgroundGuarded(paramVarArgs);
    } catch (RuntimeException runtimeException) {
      this.mReactContext.handleException(runtimeException);
    } 
    return null;
  }
  
  protected abstract void doInBackgroundGuarded(Params... paramVarArgs);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\GuardedAsyncTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */