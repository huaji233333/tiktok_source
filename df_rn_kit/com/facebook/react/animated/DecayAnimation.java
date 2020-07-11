package com.facebook.react.animated;

import com.facebook.react.bridge.ReadableMap;

public class DecayAnimation extends AnimationDriver {
  private int mCurrentLoop;
  
  private double mDeceleration;
  
  private double mFromValue;
  
  private int mIterations;
  
  private double mLastValue;
  
  private long mStartFrameTimeMillis;
  
  private final double mVelocity;
  
  public DecayAnimation(ReadableMap paramReadableMap) {
    this.mVelocity = paramReadableMap.getDouble("velocity");
    resetConfig(paramReadableMap);
  }
  
  public void resetConfig(ReadableMap paramReadableMap) {
    boolean bool1;
    this.mDeceleration = paramReadableMap.getDouble("deceleration");
    boolean bool = paramReadableMap.hasKey("iterations");
    boolean bool2 = true;
    if (bool) {
      bool1 = paramReadableMap.getInt("iterations");
    } else {
      bool1 = true;
    } 
    this.mIterations = bool1;
    this.mCurrentLoop = 1;
    if (this.mIterations != 0)
      bool2 = false; 
    this.mHasFinished = bool2;
    this.mStartFrameTimeMillis = -1L;
    this.mFromValue = 0.0D;
    this.mLastValue = 0.0D;
  }
  
  public void runAnimationStep(long paramLong) {
    paramLong /= 1000000L;
    if (this.mStartFrameTimeMillis == -1L) {
      this.mStartFrameTimeMillis = paramLong - 16L;
      if (this.mFromValue == this.mLastValue) {
        this.mFromValue = this.mAnimatedValue.mValue;
      } else {
        this.mAnimatedValue.mValue = this.mFromValue;
      } 
      this.mLastValue = this.mAnimatedValue.mValue;
    } 
    double d1 = this.mFromValue;
    double d2 = this.mVelocity;
    double d3 = this.mDeceleration;
    d2 /= 1.0D - d3;
    d3 = -(1.0D - d3);
    double d4 = (paramLong - this.mStartFrameTimeMillis);
    Double.isNaN(d4);
    d1 += d2 * (1.0D - Math.exp(d3 * d4));
    if (Math.abs(this.mLastValue - d1) < 0.1D) {
      int i = this.mIterations;
      if (i == -1 || this.mCurrentLoop < i) {
        this.mStartFrameTimeMillis = -1L;
        this.mCurrentLoop++;
      } else {
        this.mHasFinished = true;
        return;
      } 
    } 
    this.mLastValue = d1;
    this.mAnimatedValue.mValue = d1;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\animated\DecayAnimation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */