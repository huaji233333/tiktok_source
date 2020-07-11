package com.tt.miniapp.util;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;

public class CountDownHelper {
  private Handler Handler = new Handler(Looper.getMainLooper());
  
  public ICountDownListener mCountDownListener;
  
  public Object mCountDownTimer;
  
  public long mLastRunningMS;
  
  public void pause() {
    this.Handler.post(new Runnable() {
          public void run() {
            if (CountDownHelper.this.mCountDownTimer != null)
              ((CountDownTimer)CountDownHelper.this.mCountDownTimer).cancel(); 
          }
        });
  }
  
  public void resume() {
    this.Handler.post(new Runnable() {
          public void run() {
            CountDownHelper countDownHelper = CountDownHelper.this;
            countDownHelper.updateTimer(countDownHelper.mLastRunningMS);
            if (CountDownHelper.this.mCountDownTimer != null)
              ((CountDownTimer)CountDownHelper.this.mCountDownTimer).start(); 
          }
        });
  }
  
  public void setListener(ICountDownListener paramICountDownListener) {
    this.mCountDownListener = paramICountDownListener;
  }
  
  public void start(final long ms) {
    this.Handler.post(new Runnable() {
          public void run() {
            CountDownHelper.this.updateTimer(ms);
            if (CountDownHelper.this.mCountDownTimer != null)
              ((CountDownTimer)CountDownHelper.this.mCountDownTimer).start(); 
          }
        });
  }
  
  public void stop() {
    this.Handler.post(new Runnable() {
          public void run() {
            if (CountDownHelper.this.mCountDownTimer != null)
              ((CountDownTimer)CountDownHelper.this.mCountDownTimer).cancel(); 
          }
        });
  }
  
  public void updateTimer(long paramLong) {
    this.mCountDownTimer = new CountDownTimer(paramLong, 100L) {
        public void onFinish() {
          if (CountDownHelper.this.mCountDownListener != null)
            CountDownHelper.this.mCountDownListener.onFinish(); 
        }
        
        public void onTick(long param1Long) {
          CountDownHelper.this.mLastRunningMS = param1Long;
        }
      };
  }
  
  public static interface ICountDownListener {
    void onFinish();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\CountDownHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */