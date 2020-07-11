package com.tt.miniapp.event;

import com.tt.miniapp.time.CustomizeTimer;
import com.tt.miniapphost.util.TimeMeter;

public class LoadStateManager {
  private volatile TimeMeter mBeginRenderTime;
  
  private volatile TimeMeter mEntranceClickMeterTime;
  
  private volatile CustomizeTimer mLaunchProfileTime;
  
  private volatile TimeMeter mLoadStartTime;
  
  private volatile String mLoadState = "mini_process_unknown";
  
  private volatile String mLoadingBgState = "no_image";
  
  private LoadStateManager() {}
  
  public static LoadStateManager getIns() {
    return Holder.sInstance;
  }
  
  private void updateLoadResult() {
    InnerEventHelper.mpLoadResultInner(getDuration(), "cancel", "process killed", getOpenDuration(), getTotalDuration(), this.mLoadState);
  }
  
  public long getDuration() {
    return TimeMeter.nowAfterStart(this.mLoadStartTime);
  }
  
  public String getLoadState() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mLoadState : Ljava/lang/String;
    //   6: astore_1
    //   7: aload_0
    //   8: monitorexit
    //   9: aload_1
    //   10: areturn
    //   11: astore_1
    //   12: aload_0
    //   13: monitorexit
    //   14: aload_1
    //   15: athrow
    // Exception table:
    //   from	to	target	type
    //   2	9	11	finally
    //   12	14	11	finally
  }
  
  String getLoadingBgState() {
    return this.mLoadingBgState;
  }
  
  public long getOpenDuration() {
    return (this.mLaunchProfileTime != null) ? this.mLaunchProfileTime.getTime() : 0L;
  }
  
  public long getTotalDuration() {
    return TimeMeter.nowAfterStart(this.mEntranceClickMeterTime);
  }
  
  public void reportPreloadResult(String paramString) {
    InnerEventHelper.mpPreloadResult(paramString, TimeMeter.stop(this.mBeginRenderTime));
  }
  
  public void reportRenderTime(String paramString1, String paramString2) {
    InnerEventHelper.mpRenderResult(paramString1, TimeMeter.stop(this.mBeginRenderTime), paramString2);
  }
  
  public void setEntranceClickMeterTime(TimeMeter paramTimeMeter) {
    this.mEntranceClickMeterTime = paramTimeMeter;
  }
  
  public void setLaunchProfileTime(CustomizeTimer paramCustomizeTimer) {
    this.mLaunchProfileTime = paramCustomizeTimer;
  }
  
  public void setLoadStartTime(TimeMeter paramTimeMeter) {
    this.mLoadStartTime = paramTimeMeter;
  }
  
  public void setLoadState(String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: putfield mLoadState : Ljava/lang/String;
    //   7: aload_0
    //   8: monitorexit
    //   9: aload_0
    //   10: getfield mLoadStartTime : Lcom/tt/miniapphost/util/TimeMeter;
    //   13: ifnonnull -> 17
    //   16: return
    //   17: aload_0
    //   18: invokespecial updateLoadResult : ()V
    //   21: new java/lang/StringBuilder
    //   24: dup
    //   25: ldc 'r60508: updateLoadState: '
    //   27: invokespecial <init> : (Ljava/lang/String;)V
    //   30: astore_2
    //   31: aload_2
    //   32: aload_1
    //   33: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   36: pop
    //   37: ldc 'tma_LoadStateManager'
    //   39: iconst_1
    //   40: anewarray java/lang/Object
    //   43: dup
    //   44: iconst_0
    //   45: aload_2
    //   46: invokevirtual toString : ()Ljava/lang/String;
    //   49: aastore
    //   50: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   53: return
    //   54: astore_1
    //   55: aload_0
    //   56: monitorexit
    //   57: aload_1
    //   58: athrow
    // Exception table:
    //   from	to	target	type
    //   2	9	54	finally
    //   55	57	54	finally
  }
  
  public void setLoadingBgState(String paramString) {
    this.mLoadingBgState = paramString;
    updateLoadResult();
  }
  
  public void startRenderTime() {
    this.mBeginRenderTime = TimeMeter.newAndStart();
  }
  
  public void stopRenderTime() {
    TimeMeter.stop(this.mBeginRenderTime);
  }
  
  static class Holder {
    public static final LoadStateManager sInstance = new LoadStateManager();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\event\LoadStateManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */