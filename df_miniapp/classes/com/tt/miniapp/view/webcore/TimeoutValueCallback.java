package com.tt.miniapp.view.webcore;

import android.webkit.ValueCallback;
import com.tt.miniapphost.AppbrandContext;

public abstract class TimeoutValueCallback<T> implements ValueCallback<T> {
  private Runnable mTimeoutRunnable;
  
  public TimeoutValueCallback(long paramLong) {
    if (paramLong > 0L) {
      this.mTimeoutRunnable = new Runnable() {
          public void run() {
            TimeoutValueCallback.this.onTimeout();
          }
        };
      AppbrandContext.mainHandler.postDelayed(this.mTimeoutRunnable, paramLong);
    } 
  }
  
  public final void onReceiveValue(T paramT) {
    if (this.mTimeoutRunnable != null) {
      AppbrandContext.mainHandler.removeCallbacks(this.mTimeoutRunnable);
      this.mTimeoutRunnable = null;
    } 
    onReceiveValue2(paramT);
  }
  
  abstract void onReceiveValue2(T paramT);
  
  abstract void onTimeout();
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\webcore\TimeoutValueCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */