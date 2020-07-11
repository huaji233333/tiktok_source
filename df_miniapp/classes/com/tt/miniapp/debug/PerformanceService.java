package com.tt.miniapp.debug;

import android.os.HandlerThread;
import com.storage.async.Action;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandServiceManager;
import com.tt.miniapp.LifeCycleManager;
import com.tt.miniapp.LifeCycleManager.LifecycleInterest;
import com.tt.miniapp.monitor.BaseMonitorTask;
import com.tt.miniapp.monitor.MonitorHandler;
import com.tt.miniapp.thread.HandlerThreadUtil;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.util.DebugUtil;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PerformanceService extends AppbrandServiceManager.ServiceBase {
  private MonitorHandler mMonitorHandler;
  
  private HandlerThread mMonitorThread;
  
  private List<PerformanceTimingObj> timingArray = new ArrayList<PerformanceTimingObj>();
  
  private PerformanceService(AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramAppbrandApplicationImpl);
  }
  
  public void cancelReportPerformance() {
    MonitorHandler monitorHandler = this.mMonitorHandler;
    if (monitorHandler != null) {
      AppBrandLogger.d("PerformanceService", new Object[] { "cancelReportPerformance ", monitorHandler.toString() });
      this.mMonitorHandler.cancel();
    } 
  }
  
  public PerformanceTimingObj createPerformanceTimingObj(String paramString, long paramLong) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: new com/tt/miniapp/debug/PerformanceService$PerformanceTimingObj
    //   5: dup
    //   6: aload_1
    //   7: lload_2
    //   8: invokespecial <init> : (Ljava/lang/String;J)V
    //   11: astore_1
    //   12: aload_0
    //   13: getfield timingArray : Ljava/util/List;
    //   16: aload_1
    //   17: invokeinterface add : (Ljava/lang/Object;)Z
    //   22: pop
    //   23: aload_0
    //   24: monitorexit
    //   25: aload_1
    //   26: areturn
    //   27: astore_1
    //   28: aload_0
    //   29: monitorexit
    //   30: aload_1
    //   31: athrow
    // Exception table:
    //   from	to	target	type
    //   2	23	27	finally
  }
  
  public MonitorHandler getMonitorHandler() {
    return this.mMonitorHandler;
  }
  
  public JSONArray getPerformanceTimingArray() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: new org/json/JSONArray
    //   5: dup
    //   6: invokespecial <init> : ()V
    //   9: astore_1
    //   10: aload_0
    //   11: getfield timingArray : Ljava/util/List;
    //   14: invokeinterface iterator : ()Ljava/util/Iterator;
    //   19: astore_2
    //   20: aload_2
    //   21: invokeinterface hasNext : ()Z
    //   26: ifeq -> 49
    //   29: aload_1
    //   30: aload_2
    //   31: invokeinterface next : ()Ljava/lang/Object;
    //   36: checkcast com/tt/miniapp/debug/PerformanceService$PerformanceTimingObj
    //   39: invokevirtual toJSON : ()Lorg/json/JSONObject;
    //   42: invokevirtual put : (Ljava/lang/Object;)Lorg/json/JSONArray;
    //   45: pop
    //   46: goto -> 20
    //   49: aload_0
    //   50: monitorexit
    //   51: aload_1
    //   52: areturn
    //   53: astore_1
    //   54: aload_0
    //   55: monitorexit
    //   56: goto -> 61
    //   59: aload_1
    //   60: athrow
    //   61: goto -> 59
    // Exception table:
    //   from	to	target	type
    //   2	20	53	finally
    //   20	46	53	finally
  }
  
  @LifecycleInterest({LifeCycleManager.LifeCycleEvent.ON_APP_INFO_INITED})
  public void onAppInfoInited(LifeCycleManager.LifeCycleEvent paramLifeCycleEvent, final AppInfoEntity appInfo) {
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            boolean bool;
            if (HostProcessBridge.isReportPerformance() || DebugUtil.debug()) {
              bool = true;
            } else {
              bool = false;
            } 
            BaseMonitorTask.isReportPerformance = bool;
            if (!bool && !appInfo.isLocalTest()) {
              PerformanceService.this.cancelReportPerformance();
              return;
            } 
            MonitorHandler.setMinimunInterval(5000L);
            PerformanceService.this.reportPerformance();
          }
        },  ThreadPools.defaults());
  }
  
  public void reportPerformance() {
    if (this.mMonitorHandler == null) {
      this.mMonitorThread = HandlerThreadUtil.getBackgroundHandlerThread();
      this.mMonitorHandler = new MonitorHandler(this.mMonitorThread.getLooper());
    } 
    this.mMonitorHandler.start();
  }
  
  public static class PerformanceTimingObj {
    Long mEndTime;
    
    String mName;
    
    long mStartTime;
    
    PerformanceTimingObj(String param1String, long param1Long) {
      this.mName = param1String;
      this.mStartTime = param1Long;
    }
    
    public void setEndTime(long param1Long) {
      this.mEndTime = Long.valueOf(param1Long);
    }
    
    public JSONObject toJSON() {
      JSONObject jSONObject = new JSONObject();
      try {
        jSONObject.put("name", this.mName);
        jSONObject.put("startTime", this.mStartTime);
        if (this.mEndTime != null)
          jSONObject.put("endTime", this.mEndTime); 
        return jSONObject;
      } catch (JSONException jSONException) {
        AppBrandLogger.e("PerformanceService", new Object[] { jSONException });
        return null;
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\debug\PerformanceService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */