package com.tt.miniapphost.apm;

import android.content.Context;
import android.text.TextUtils;
import com.storage.async.Action;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.InitParamsEntity;
import com.tt.option.q.d;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONException;
import org.json.JSONObject;

public class AppbrandApmService {
  private static volatile IApmService apmService;
  
  public Runnable ApmStartTimeOutRunnable = new Runnable() {
      public void run() {
        if (AppbrandApmService.this.startStatus.get() == 1)
          ThreadUtil.runOnWorkThread(new Action() {
                public void act() {
                  AppbrandApmService.this.stop();
                  AppbrandApmService.this.start();
                  AppBrandLogger.d("AppbrandApmService", new Object[] { "retry start apm" });
                }
              }ThreadPools.defaults()); 
      }
    };
  
  public List<ALogUploadTask> pendingUploadTasks = new CopyOnWriteArrayList<ALogUploadTask>();
  
  public AtomicInteger startStatus = new AtomicInteger(0);
  
  private AppbrandApmService() {}
  
  private boolean addToPendList(long paramLong1, long paramLong2, String paramString) {
    for (ALogUploadTask aLogUploadTask : this.pendingUploadTasks) {
      if (aLogUploadTask.startTime <= paramLong1 && aLogUploadTask.endTime >= paramLong2 && TextUtils.equals(aLogUploadTask.scene, paramString))
        return false; 
    } 
    this.pendingUploadTasks.add(new ALogUploadTask(paramLong1, paramLong2, paramString));
    return true;
  }
  
  public static IApmService getApmService() {
    // Byte code:
    //   0: getstatic com/tt/miniapphost/apm/AppbrandApmService.apmService : Lcom/tt/miniapphost/apm/IApmService;
    //   3: ifnonnull -> 52
    //   6: ldc com/tt/miniapphost/apm/AppbrandApmService
    //   8: monitorenter
    //   9: getstatic com/tt/miniapphost/apm/AppbrandApmService.apmService : Lcom/tt/miniapphost/apm/IApmService;
    //   12: ifnonnull -> 40
    //   15: invokestatic getInst : ()Lcom/tt/miniapphost/host/HostDependManager;
    //   18: invokevirtual createApmService : ()Lcom/tt/miniapphost/apm/IApmService;
    //   21: astore_0
    //   22: aload_0
    //   23: putstatic com/tt/miniapphost/apm/AppbrandApmService.apmService : Lcom/tt/miniapphost/apm/IApmService;
    //   26: aload_0
    //   27: ifnonnull -> 40
    //   30: new com/tt/miniapphost/apm/AppbrandApmService$3
    //   33: dup
    //   34: invokespecial <init> : ()V
    //   37: putstatic com/tt/miniapphost/apm/AppbrandApmService.apmService : Lcom/tt/miniapphost/apm/IApmService;
    //   40: ldc com/tt/miniapphost/apm/AppbrandApmService
    //   42: monitorexit
    //   43: goto -> 52
    //   46: astore_0
    //   47: ldc com/tt/miniapphost/apm/AppbrandApmService
    //   49: monitorexit
    //   50: aload_0
    //   51: athrow
    //   52: getstatic com/tt/miniapphost/apm/AppbrandApmService.apmService : Lcom/tt/miniapphost/apm/IApmService;
    //   55: areturn
    // Exception table:
    //   from	to	target	type
    //   9	26	46	finally
    //   30	40	46	finally
    //   40	43	46	finally
    //   47	50	46	finally
  }
  
  public static AppbrandApmService getInstance() {
    return AppBrandApmServiceHolder.sInstance;
  }
  
  public void init() {
    getApmService().init((Context)AppbrandContext.getInst().getApplicationContext());
  }
  
  public void start() {
    InitParamsEntity initParamsEntity = AppbrandContext.getInst().getInitParams();
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("aid", initParamsEntity.getAppId());
      jSONObject.put("device_id", d.a());
      jSONObject.put("channel", initParamsEntity.getChannel());
      jSONObject.put("update_version_code", initParamsEntity.getUpdateVersionCode());
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable("AppbrandApmService", "", (Throwable)jSONException);
    } 
    AppbrandApmStartConfig.Builder builder = new AppbrandApmStartConfig.Builder();
    builder.aid(Integer.parseInt(initParamsEntity.getAppId())).deviceId(d.a()).channel(initParamsEntity.getChannel()).appVersion(initParamsEntity.getVersionCode()).updateVersionCode(initParamsEntity.getUpdateVersionCode()).extra(jSONObject).startListener(new ApmStartListener() {
          public void onReady() {}
          
          public void onStartComplete() {
            AppBrandLogger.d("AppbrandApmService", new Object[] { "Apm start complete" });
            AppbrandApmService.this.startStatus.set(2);
            AppbrandContext.mainHandler.removeCallbacks(AppbrandApmService.this.ApmStartTimeOutRunnable);
            for (AppbrandApmService.ALogUploadTask aLogUploadTask : AppbrandApmService.this.pendingUploadTasks)
              AppbrandApmService.this.uploadALog(aLogUploadTask.startTime, aLogUploadTask.endTime, aLogUploadTask.scene); 
            AppbrandApmService.this.pendingUploadTasks.clear();
          }
        });
    getApmService().start(builder.build());
    this.startStatus.set(1);
  }
  
  public void stop() {
    getApmService().stop();
  }
  
  public void tryUploadALog(long paramLong1, long paramLong2, String paramString) {
    int i = this.startStatus.get();
    if (i != 0) {
      if (i != 1) {
        if (i != 2)
          return; 
        AppBrandLogger.d("AppbrandApmService", new Object[] { "apm start complete, upload alog" });
        uploadALog(paramLong1, paramLong2, paramString);
        return;
      } 
      AppBrandLogger.d("AppbrandApmService", new Object[] { "apm starting,add to pending list" });
      addToPendList(paramLong1, paramLong2, paramString);
      return;
    } 
    AppBrandLogger.d("AppbrandApmService", new Object[] { "start apm,add to pending list" });
    init();
    start();
    addToPendList(paramLong1, paramLong2, paramString);
    AppbrandContext.mainHandler.postDelayed(this.ApmStartTimeOutRunnable, 1000L);
  }
  
  public void uploadALog(long paramLong1, long paramLong2, String paramString) {
    getApmService().uploadALog(paramLong1, paramLong2, paramString);
  }
  
  static class ALogUploadTask {
    long endTime;
    
    String scene;
    
    long startTime;
    
    ALogUploadTask(long param1Long1, long param1Long2, String param1String) {
      this.startTime = param1Long1;
      this.endTime = param1Long2;
      this.scene = param1String;
    }
  }
  
  static class AppBrandApmServiceHolder {
    static AppbrandApmService sInstance = new AppbrandApmService();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\apm\AppbrandApmService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */