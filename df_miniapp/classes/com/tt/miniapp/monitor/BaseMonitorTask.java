package com.tt.miniapp.monitor;

public abstract class BaseMonitorTask {
  public static boolean isReportPerformance;
  
  protected long mLastExecuteTime;
  
  protected long mTaskInterval = 30000L;
  
  public BaseMonitorTask() {}
  
  public BaseMonitorTask(long paramLong) {}
  
  public void execute() {
    long l = System.currentTimeMillis();
    if (l - this.mLastExecuteTime < this.mTaskInterval)
      return; 
    this.mLastExecuteTime = l;
    executeActual();
  }
  
  protected abstract void executeActual();
  
  protected long getTaskInterval() {
    return this.mTaskInterval;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\monitor\BaseMonitorTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */