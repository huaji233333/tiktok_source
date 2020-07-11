package com.facebook.react.bridge;

import android.os.AsyncTask;

public abstract class GuardedResultAsyncTask<Result> extends AsyncTask<Void, Void, Result> {
  private final ReactContext mReactContext;
  
  public GuardedResultAsyncTask(ReactContext paramReactContext) {
    this.mReactContext = paramReactContext;
  }
  
  protected final Result doInBackground(Void... paramVarArgs) {
    try {
      return doInBackgroundGuarded();
    } catch (RuntimeException runtimeException) {
      this.mReactContext.handleException(runtimeException);
      throw runtimeException;
    } 
  }
  
  protected abstract Result doInBackgroundGuarded();
  
  protected final void onPostExecute(Result paramResult) {
    try {
      onPostExecuteGuarded(paramResult);
      return;
    } catch (RuntimeException runtimeException) {
      this.mReactContext.handleException(runtimeException);
      return;
    } 
  }
  
  protected abstract void onPostExecuteGuarded(Result paramResult);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\GuardedResultAsyncTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */