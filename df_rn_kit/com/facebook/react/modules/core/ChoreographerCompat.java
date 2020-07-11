package com.facebook.react.modules.core;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.Choreographer;

public class ChoreographerCompat {
  private static final ChoreographerCompat INSTANCE;
  
  private static final boolean IS_JELLYBEAN_OR_HIGHER;
  
  private Choreographer mChoreographer;
  
  private Handler mHandler;
  
  static {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 16) {
      bool = true;
    } else {
      bool = false;
    } 
    IS_JELLYBEAN_OR_HIGHER = bool;
    INSTANCE = new ChoreographerCompat();
  }
  
  private ChoreographerCompat() {
    if (IS_JELLYBEAN_OR_HIGHER) {
      this.mChoreographer = getChoreographer();
      return;
    } 
    this.mHandler = new Handler(Looper.getMainLooper());
  }
  
  private void choreographerPostFrameCallback(Choreographer.FrameCallback paramFrameCallback) {
    this.mChoreographer.postFrameCallback(paramFrameCallback);
  }
  
  private void choreographerPostFrameCallbackDelayed(Choreographer.FrameCallback paramFrameCallback, long paramLong) {
    this.mChoreographer.postFrameCallbackDelayed(paramFrameCallback, paramLong);
  }
  
  private void choreographerRemoveFrameCallback(Choreographer.FrameCallback paramFrameCallback) {
    this.mChoreographer.removeFrameCallback(paramFrameCallback);
  }
  
  private Choreographer getChoreographer() {
    return Choreographer.getInstance();
  }
  
  public static ChoreographerCompat getInstance() {
    return INSTANCE;
  }
  
  public void postFrameCallback(FrameCallback paramFrameCallback) {
    if (IS_JELLYBEAN_OR_HIGHER) {
      choreographerPostFrameCallback(paramFrameCallback.getFrameCallback());
      return;
    } 
    this.mHandler.postDelayed(paramFrameCallback.getRunnable(), 0L);
  }
  
  public void postFrameCallbackDelayed(FrameCallback paramFrameCallback, long paramLong) {
    if (IS_JELLYBEAN_OR_HIGHER) {
      choreographerPostFrameCallbackDelayed(paramFrameCallback.getFrameCallback(), paramLong);
      return;
    } 
    this.mHandler.postDelayed(paramFrameCallback.getRunnable(), paramLong + 17L);
  }
  
  public void removeFrameCallback(FrameCallback paramFrameCallback) {
    if (IS_JELLYBEAN_OR_HIGHER) {
      choreographerRemoveFrameCallback(paramFrameCallback.getFrameCallback());
      return;
    } 
    this.mHandler.removeCallbacks(paramFrameCallback.getRunnable());
  }
  
  public static abstract class FrameCallback {
    private Choreographer.FrameCallback mFrameCallback;
    
    private Runnable mRunnable;
    
    public abstract void doFrame(long param1Long);
    
    Choreographer.FrameCallback getFrameCallback() {
      if (this.mFrameCallback == null)
        this.mFrameCallback = new Choreographer.FrameCallback() {
            public void doFrame(long param2Long) {
              ChoreographerCompat.FrameCallback.this.doFrame(param2Long);
            }
          }; 
      return this.mFrameCallback;
    }
    
    Runnable getRunnable() {
      if (this.mRunnable == null)
        this.mRunnable = new Runnable() {
            public void run() {
              ChoreographerCompat.FrameCallback.this.doFrame(System.nanoTime());
            }
          }; 
      return this.mRunnable;
    }
  }
  
  class null implements Choreographer.FrameCallback {
    public void doFrame(long param1Long) {
      this.this$0.doFrame(param1Long);
    }
  }
  
  class null implements Runnable {
    public void run() {
      this.this$0.doFrame(System.nanoTime());
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\core\ChoreographerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */