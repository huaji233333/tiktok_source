package com.tt.miniapp.jsbridge;

import java.util.concurrent.LinkedBlockingQueue;

public class TimerCacheManager {
  private LinkedBlockingQueue<TimerData> mTimerCacheQueue = new LinkedBlockingQueue<TimerData>();
  
  public void addData(TimerData paramTimerData) {
    this.mTimerCacheQueue.add(paramTimerData);
  }
  
  public TimerData getTimerData() {
    return this.mTimerCacheQueue.poll();
  }
  
  public boolean hasData() {
    return (this.mTimerCacheQueue.size() > 0);
  }
  
  public static class TimerData {
    int id;
    
    String type;
    
    public TimerData(String param1String, int param1Int) {
      this.type = param1String;
      this.id = param1Int;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\jsbridge\TimerCacheManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */