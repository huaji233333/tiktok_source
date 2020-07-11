package com.tt.miniapp.monitor;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.os.Process;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;

public class MemoryMonitorTask extends BaseMonitorTask {
  private static long sMaxMemory;
  
  public MemoryMonitorTask() {
    super(10000L);
  }
  
  public MemoryMonitorTask(long paramLong) {
    super(paramLong);
  }
  
  public static long getAvailMemory(Context paramContext) {
    ActivityManager activityManager = (ActivityManager)paramContext.getSystemService("activity");
    ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
    activityManager.getMemoryInfo(memoryInfo);
    double d = memoryInfo.totalMem;
    Double.isNaN(d);
    return (long)(d * 0.12D);
  }
  
  public static long getMaxMemory() {
    return sMaxMemory;
  }
  
  private void monitorMemoryInfo() {
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase != null) {
      ActivityManager activityManager = (ActivityManager)miniappHostBase.getSystemService("activity");
      if (activityManager == null)
        return; 
      Debug.MemoryInfo[] arrayOfMemoryInfo = activityManager.getProcessMemoryInfo(new int[] { Process.myPid() });
      if (arrayOfMemoryInfo != null) {
        if (arrayOfMemoryInfo.length <= 0)
          return; 
        Debug.MemoryInfo memoryInfo = arrayOfMemoryInfo[0];
        if (sMaxMemory == 0L)
          sMaxMemory = activityManager.getMemoryClass() * 1024L; 
        MonitorInfoPackTask.addMemoryInfo(AppbrandApplicationImpl.getInst().getForeBackgroundManager().isBackground(), memoryInfo);
      } 
    } 
  }
  
  protected void executeActual() {
    monitorMemoryInfo();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\monitor\MemoryMonitorTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */