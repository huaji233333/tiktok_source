package com.facebook.react.modules.debug;

import com.facebook.i.a.a;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.ChoreographerCompat;
import com.facebook.react.uimanager.UIManagerModule;
import java.util.Map;
import java.util.TreeMap;

public class FpsDebugFrameCallback extends ChoreographerCompat.FrameCallback {
  private int m4PlusFrameStutters;
  
  private final ChoreographerCompat mChoreographer;
  
  private final DidJSUpdateUiDuringFrameDetector mDidJSUpdateUiDuringFrameDetector;
  
  private int mExpectedNumFramesPrev;
  
  private long mFirstFrameTime = -1L;
  
  private boolean mIsRecordingFpsInfoAtEachFrame;
  
  private long mLastFrameTime = -1L;
  
  private int mNumFrameCallbacks;
  
  private int mNumFrameCallbacksWithBatchDispatches;
  
  private final ReactContext mReactContext;
  
  private boolean mShouldStop;
  
  private TreeMap<Long, FpsInfo> mTimeToFps;
  
  private final UIManagerModule mUIManagerModule;
  
  public FpsDebugFrameCallback(ChoreographerCompat paramChoreographerCompat, ReactContext paramReactContext) {
    this.mChoreographer = paramChoreographerCompat;
    this.mReactContext = paramReactContext;
    this.mUIManagerModule = (UIManagerModule)paramReactContext.getNativeModule(UIManagerModule.class);
    this.mDidJSUpdateUiDuringFrameDetector = new DidJSUpdateUiDuringFrameDetector();
  }
  
  public void doFrame(long paramLong) {
    if (this.mShouldStop)
      return; 
    if (this.mFirstFrameTime == -1L)
      this.mFirstFrameTime = paramLong; 
    long l = this.mLastFrameTime;
    this.mLastFrameTime = paramLong;
    if (this.mDidJSUpdateUiDuringFrameDetector.getDidJSHitFrameAndCleanup(l, paramLong))
      this.mNumFrameCallbacksWithBatchDispatches++; 
    this.mNumFrameCallbacks++;
    int i = getExpectedNumFrames();
    if (i - this.mExpectedNumFramesPrev - 1 >= 4)
      this.m4PlusFrameStutters++; 
    if (this.mIsRecordingFpsInfoAtEachFrame) {
      a.b(this.mTimeToFps);
      FpsInfo fpsInfo = new FpsInfo(getNumFrames(), getNumJSFrames(), i, this.m4PlusFrameStutters, getFPS(), getJSFPS(), getTotalTimeMS());
      this.mTimeToFps.put(Long.valueOf(System.currentTimeMillis()), fpsInfo);
    } 
    this.mExpectedNumFramesPrev = i;
    this.mChoreographer.postFrameCallback(this);
  }
  
  public int get4PlusFrameStutters() {
    return this.m4PlusFrameStutters;
  }
  
  public int getExpectedNumFrames() {
    double d = getTotalTimeMS();
    Double.isNaN(d);
    return (int)(d / 16.9D + 1.0D);
  }
  
  public double getFPS() {
    if (this.mLastFrameTime == this.mFirstFrameTime)
      return 0.0D; 
    double d1 = getNumFrames();
    Double.isNaN(d1);
    double d2 = (this.mLastFrameTime - this.mFirstFrameTime);
    Double.isNaN(d2);
    return d1 * 1.0E9D / d2;
  }
  
  public FpsInfo getFpsInfo(long paramLong) {
    a.a(this.mTimeToFps, "FPS was not recorded at each frame!");
    Map.Entry<Long, FpsInfo> entry = this.mTimeToFps.floorEntry(Long.valueOf(paramLong));
    return (entry == null) ? null : entry.getValue();
  }
  
  public double getJSFPS() {
    if (this.mLastFrameTime == this.mFirstFrameTime)
      return 0.0D; 
    double d1 = getNumJSFrames();
    Double.isNaN(d1);
    double d2 = (this.mLastFrameTime - this.mFirstFrameTime);
    Double.isNaN(d2);
    return d1 * 1.0E9D / d2;
  }
  
  public int getNumFrames() {
    return this.mNumFrameCallbacks - 1;
  }
  
  public int getNumJSFrames() {
    return this.mNumFrameCallbacksWithBatchDispatches - 1;
  }
  
  public int getTotalTimeMS() {
    double d1 = this.mLastFrameTime;
    double d2 = this.mFirstFrameTime;
    Double.isNaN(d1);
    Double.isNaN(d2);
    return (int)(d1 - d2) / 1000000;
  }
  
  public void reset() {
    this.mFirstFrameTime = -1L;
    this.mLastFrameTime = -1L;
    this.mNumFrameCallbacks = 0;
    this.m4PlusFrameStutters = 0;
    this.mNumFrameCallbacksWithBatchDispatches = 0;
    this.mIsRecordingFpsInfoAtEachFrame = false;
    this.mTimeToFps = null;
  }
  
  public void start() {
    this.mShouldStop = false;
    this.mReactContext.getCatalystInstance().addBridgeIdleDebugListener(this.mDidJSUpdateUiDuringFrameDetector);
    this.mUIManagerModule.setViewHierarchyUpdateDebugListener(this.mDidJSUpdateUiDuringFrameDetector);
    this.mChoreographer.postFrameCallback(this);
  }
  
  public void startAndRecordFpsAtEachFrame() {
    this.mTimeToFps = new TreeMap<Long, FpsInfo>();
    this.mIsRecordingFpsInfoAtEachFrame = true;
    start();
  }
  
  public void stop() {
    this.mShouldStop = true;
    this.mReactContext.getCatalystInstance().removeBridgeIdleDebugListener(this.mDidJSUpdateUiDuringFrameDetector);
    this.mUIManagerModule.setViewHierarchyUpdateDebugListener(null);
  }
  
  public static class FpsInfo {
    public final double fps;
    
    public final double jsFps;
    
    public final int total4PlusFrameStutters;
    
    public final int totalExpectedFrames;
    
    public final int totalFrames;
    
    public final int totalJsFrames;
    
    public final int totalTimeMs;
    
    public FpsInfo(int param1Int1, int param1Int2, int param1Int3, int param1Int4, double param1Double1, double param1Double2, int param1Int5) {
      this.totalFrames = param1Int1;
      this.totalJsFrames = param1Int2;
      this.totalExpectedFrames = param1Int3;
      this.total4PlusFrameStutters = param1Int4;
      this.fps = param1Double1;
      this.jsFps = param1Double2;
      this.totalTimeMs = param1Int5;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\debug\FpsDebugFrameCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */