package com.tt.miniapp.monitor;

import android.view.Choreographer;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapphost.AppbrandContext;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

public class FpsMonitorTask extends BaseMonitorTask implements Choreographer.FrameCallback {
  private Choreographer mChoreographer;
  
  private int mFrameCounter;
  
  private long mStartTimeNano = -1L;
  
  public WeakReference<DebugMonitorTextView> mUpadteTextView;
  
  public FpsMonitorTask(Choreographer paramChoreographer) {
    super(9999L);
    this.mChoreographer = paramChoreographer;
  }
  
  public FpsMonitorTask(Choreographer paramChoreographer, int paramInt) {
    super((paramInt - 1));
    this.mChoreographer = paramChoreographer;
  }
  
  private void updatePerformanceTextView() {
    AppbrandContext.mainHandler.post(new Runnable() {
          public void run() {
            if (FpsMonitorTask.this.mUpadteTextView != null && FpsMonitorTask.this.mUpadteTextView.get() != null)
              ((DebugMonitorTextView)FpsMonitorTask.this.mUpadteTextView.get()).update(); 
          }
        });
  }
  
  public void doFrame(long paramLong) {
    if (this.mStartTimeNano == -1L) {
      this.mStartTimeNano = paramLong;
      this.mFrameCounter = 0;
    } 
    paramLong -= this.mStartTimeNano;
    if (paramLong > TimeUnit.MILLISECONDS.toNanos(200L)) {
      float f = this.mFrameCounter * 1.0F * (float)TimeUnit.SECONDS.toNanos(1L) / (float)paramLong;
      MonitorInfoPackTask.addFps(AppbrandApplicationImpl.getInst().getForeBackgroundManager().isBackground(), Math.round(f));
      this.mStartTimeNano = -1L;
      if (this.mUpadteTextView != null)
        updatePerformanceTextView(); 
      return;
    } 
    this.mFrameCounter++;
    this.mChoreographer.postFrameCallback(this);
  }
  
  protected void executeActual() {
    this.mChoreographer.postFrameCallback(this);
  }
  
  public void setUpadteDebugTextView(DebugMonitorTextView paramDebugMonitorTextView) {
    this.mUpadteTextView = new WeakReference<DebugMonitorTextView>(paramDebugMonitorTextView);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\monitor\FpsMonitorTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */