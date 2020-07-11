package com.facebook.react.animated;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

class FrameBasedAnimationDriver extends AnimationDriver {
  private int mCurrentLoop;
  
  private double[] mFrames;
  
  private double mFromValue;
  
  private int mIterations;
  
  private long mStartFrameTimeNanos;
  
  private double mToValue;
  
  FrameBasedAnimationDriver(ReadableMap paramReadableMap) {
    resetConfig(paramReadableMap);
  }
  
  public void resetConfig(ReadableMap paramReadableMap) {
    ReadableArray readableArray = paramReadableMap.getArray("frames");
    int j = readableArray.size();
    double[] arrayOfDouble = this.mFrames;
    if (arrayOfDouble == null || arrayOfDouble.length != j)
      this.mFrames = new double[j]; 
    boolean bool = false;
    int i;
    for (i = 0; i < j; i++)
      this.mFrames[i] = readableArray.getDouble(i); 
    this.mToValue = paramReadableMap.getDouble("toValue");
    if (paramReadableMap.hasKey("iterations")) {
      i = paramReadableMap.getInt("iterations");
    } else {
      i = 1;
    } 
    this.mIterations = i;
    this.mCurrentLoop = 1;
    if (this.mIterations == 0)
      bool = true; 
    this.mHasFinished = bool;
    this.mStartFrameTimeNanos = -1L;
  }
  
  public void runAnimationStep(long paramLong) {
    if (this.mStartFrameTimeNanos < 0L) {
      this.mStartFrameTimeNanos = paramLong;
      if (this.mCurrentLoop == 1)
        this.mFromValue = this.mAnimatedValue.mValue; 
    } 
    double d = ((paramLong - this.mStartFrameTimeNanos) / 1000000L);
    Double.isNaN(d);
    int i = (int)Math.round(d / 16.666666666666668D);
    if (i >= 0) {
      if (this.mHasFinished)
        return; 
      double[] arrayOfDouble = this.mFrames;
      if (i >= arrayOfDouble.length - 1) {
        d = this.mToValue;
        i = this.mIterations;
        if (i == -1 || this.mCurrentLoop < i) {
          this.mStartFrameTimeNanos = -1L;
          this.mCurrentLoop++;
        } else {
          this.mHasFinished = true;
        } 
      } else {
        d = this.mFromValue;
        d += arrayOfDouble[i] * (this.mToValue - d);
      } 
      this.mAnimatedValue.mValue = d;
      return;
    } 
    throw new IllegalStateException("Calculated frame index should never be lower than 0");
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\animated\FrameBasedAnimationDriver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */