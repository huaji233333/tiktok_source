package com.tt.miniapp.monitor;

import android.os.Handler;
import android.os.Looper;
import com.tt.miniapphost.AppBrandLogger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MonitorHandler extends Handler {
  public static long TASK_LIMIT = 5000L;
  
  public List<BaseMonitorTask> mTasks = new CopyOnWriteArrayList<BaseMonitorTask>();
  
  private Runnable mTimerTask = new Runnable() {
      public void run() {
        ArrayList<BaseMonitorTask> arrayList = new ArrayList<BaseMonitorTask>(MonitorHandler.this.mTasks);
        for (int i = 0; i < arrayList.size(); i++)
          ((BaseMonitorTask)arrayList.get(i)).execute(); 
        MonitorHandler.this.postDelayed(this, MonitorHandler.TASK_LIMIT);
      }
    };
  
  public MonitorHandler(Looper paramLooper) {
    super(paramLooper);
    this.mTasks.add(new CpuMonitorTask());
    this.mTasks.add(new MemoryMonitorTask());
    this.mTasks.add(new MonitorInfoPackTask());
  }
  
  public MonitorHandler(Looper paramLooper, List<BaseMonitorTask> paramList) {
    super(paramLooper);
    this.mTasks.addAll(paramList);
  }
  
  public static long getMinimumInterval() {
    return TASK_LIMIT;
  }
  
  public static void setMinimunInterval(long paramLong) {
    TASK_LIMIT = paramLong;
  }
  
  public void addTask(BaseMonitorTask paramBaseMonitorTask) {
    this.mTasks.add(paramBaseMonitorTask);
  }
  
  public void cancel() {
    try {
      AppBrandLogger.d("tma_MonitorHandler", new Object[] { "cancelDownload ", toString() });
      removeCallbacks(this.mTimerTask);
      return;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "tma_MonitorHandler", exception.getStackTrace());
      return;
    } 
  }
  
  public void start() {
    post(this.mTimerTask);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\monitor\MonitorHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */