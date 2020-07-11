package com.tt.miniapp.feedback;

import android.content.Context;
import com.tt.miniapp.monitor.MonitorHandler;
import com.tt.miniapp.monitor.MonitorInfoPackTask;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.StorageUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PerformanceLogger implements IFeedbackLogger {
  public static final String PATH;
  
  private long mMinimumInterval;
  
  public BufferedWriter performanceLogBW;
  
  static {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(StorageUtil.getExternalCacheDir((Context)AppbrandContext.getInst().getApplicationContext()));
    stringBuilder.append("/TT/feedback/performance.txt");
    PATH = stringBuilder.toString();
  }
  
  public void init() {
    this.mMinimumInterval = MonitorHandler.getMinimumInterval();
    try {
      File file = new File(PATH);
      if (file.exists()) {
        this.performanceLogBW = new BufferedWriter(new FileWriter(file));
        return;
      } 
      if (file.createNewFile())
        this.performanceLogBW = new BufferedWriter(new FileWriter(file)); 
      return;
    } catch (IOException iOException) {
      AppBrandLogger.stacktrace(6, "tma_PerformanceLogger", iOException.getStackTrace());
      return;
    } 
  }
  
  public void log() {
    boolean bool;
    if (FeedbackLogHandler.getInstance() != null && FeedbackLogHandler.getInstance().getSwitch()) {
      bool = true;
    } else {
      bool = false;
    } 
    if (bool) {
      if (this.performanceLogBW == null)
        return; 
      MonitorHandler.setMinimunInterval(1000L);
      MonitorInfoPackTask.registerFeedback(new MonitorInfoPackTask.IFeedback() {
            public void onPerformanceReport(String param1String) {
              try {
                PerformanceLogger.this.performanceLogBW.write(FeedbackUtil.createLog(new Object[] { param1String }));
                return;
              } catch (IOException iOException) {
                AppBrandLogger.stacktrace(6, "tma_PerformanceLogger", iOException.getStackTrace());
                return;
              } 
            }
          });
    } 
  }
  
  public void stop() {
    try {
      MonitorHandler.setMinimunInterval(this.mMinimumInterval);
      MonitorInfoPackTask.unregisterFeedback();
      if (this.performanceLogBW != null) {
        this.performanceLogBW.flush();
        this.performanceLogBW.close();
      } 
      return;
    } catch (IOException iOException) {
      AppBrandLogger.stacktrace(6, "tma_PerformanceLogger", iOException.getStackTrace());
      return;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\feedback\PerformanceLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */