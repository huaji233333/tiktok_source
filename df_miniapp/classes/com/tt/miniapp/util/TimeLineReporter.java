package com.tt.miniapp.util;

import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandServiceManager;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.streamloader.LoadTask;
import com.tt.miniapp.streamloader.StreamLoader;
import java.util.concurrent.atomic.AtomicBoolean;

public class TimeLineReporter extends AppbrandServiceManager.ServiceBase {
  private long appLaunchStartTime;
  
  private AtomicBoolean appStartTimeRecorded = new AtomicBoolean(false);
  
  private AtomicBoolean appStopTimeRecorded = new AtomicBoolean(false);
  
  private TimeLineReporter(AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramAppbrandApplicationImpl);
  }
  
  private int isLocalPkg() {
    LoadTask loadTask = StreamLoader.getLoadTask();
    if (loadTask != null) {
      Boolean bool = loadTask.isUseLocalPkg();
    } else {
      loadTask = null;
    } 
    return (loadTask != null) ? (loadTask.booleanValue() ? 1 : 0) : -1;
  }
  
  public void recordLaunchStartTime() {
    if (!this.appStartTimeRecorded.getAndSet(true))
      this.appLaunchStartTime = System.currentTimeMillis(); 
  }
  
  public void recordLaunchStopTime() {
    if (!this.appStartTimeRecorded.get())
      return; 
    if (StreamLoader.getLoadTask() != null && StreamLoader.getLoadTask().getTTApkgVersion() == 2 && !this.appStopTimeRecorded.getAndSet(true)) {
      long l = System.currentTimeMillis();
      Event.builder("mp_load_time_ttpkg2").kv("load_flag", Integer.valueOf(1)).kv("duration", Long.valueOf(l - this.appLaunchStartTime)).kv("local_pkg", Integer.valueOf(isLocalPkg())).flush();
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\TimeLineReporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */