package com.tt.miniapp.time;

import android.os.SystemClock;

public class CustomizeTimer {
  private boolean isStarted;
  
  private long mDurationTime;
  
  private long startTime;
  
  public void begin() {
    if (!this.isStarted) {
      this.startTime = SystemClock.elapsedRealtime();
      this.isStarted = true;
    } 
  }
  
  public void begin(long paramLong) {
    if (!this.isStarted) {
      this.startTime = paramLong;
      this.isStarted = true;
    } 
  }
  
  public long getTime() {
    return (this.startTime == 0L) ? 0L : (this.isStarted ? (this.mDurationTime + SystemClock.elapsedRealtime() - this.startTime) : this.mDurationTime);
  }
  
  public void pause() {
    if (this.isStarted) {
      this.mDurationTime += SystemClock.elapsedRealtime() - this.startTime;
      this.isStarted = false;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\time\CustomizeTimer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */