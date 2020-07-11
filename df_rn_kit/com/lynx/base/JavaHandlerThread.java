package com.lynx.base;

import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;

public class JavaHandlerThread extends HandlerThread {
  public JavaHandlerThread(String paramString) {
    super(paramString);
  }
  
  public static JavaHandlerThread create(String paramString) {
    return new JavaHandlerThread(paramString);
  }
  
  private boolean hasStarted() {
    return (getState() != Thread.State.NEW);
  }
  
  private void start(final long nativeThread, final long nativeEvent) {
    maybeStart();
    (new Handler(getLooper())).post(new Runnable() {
          public void run() {
            JavaHandlerThread.this.nativeInitializeThread(nativeThread, nativeEvent);
          }
        });
  }
  
  private void stop(final long nativeThread, final long nativeEvent) {
    final boolean quitSafely;
    if (Build.VERSION.SDK_INT >= 18) {
      bool = true;
    } else {
      bool = false;
    } 
    (new Handler(getLooper())).post(new Runnable() {
          public void run() {
            JavaHandlerThread.this.nativeStopThread(nativeThread, nativeEvent);
            if (!quitSafely)
              JavaHandlerThread.this.quit(); 
          }
        });
    if (bool)
      quitSafely(); 
  }
  
  public void maybeStart() {
    if (hasStarted())
      return; 
    start();
  }
  
  public native void nativeInitializeThread(long paramLong1, long paramLong2);
  
  public native void nativeStopThread(long paramLong1, long paramLong2);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\lynx\base\JavaHandlerThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */