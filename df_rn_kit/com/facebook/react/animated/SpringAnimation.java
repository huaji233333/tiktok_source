package com.facebook.react.animated;

import com.facebook.react.bridge.ReadableMap;

class SpringAnimation extends AnimationDriver {
  private int mCurrentLoop;
  
  private final PhysicsState mCurrentState = new PhysicsState();
  
  private double mDisplacementFromRestThreshold;
  
  private double mEndValue;
  
  private double mInitialVelocity;
  
  private int mIterations;
  
  private long mLastTime;
  
  private double mOriginalValue;
  
  private boolean mOvershootClampingEnabled;
  
  private double mRestSpeedThreshold;
  
  private double mSpringDamping;
  
  private double mSpringMass;
  
  private boolean mSpringStarted;
  
  private double mSpringStiffness;
  
  private double mStartValue;
  
  private double mTimeAccumulator;
  
  SpringAnimation(ReadableMap paramReadableMap) {
    this.mCurrentState.velocity = paramReadableMap.getDouble("initialVelocity");
    resetConfig(paramReadableMap);
  }
  
  private void advance(double paramDouble) {
    if (isAtRest())
      return; 
    double d1 = 0.064D;
    if (paramDouble > 0.064D)
      paramDouble = d1; 
    this.mTimeAccumulator += paramDouble;
    double d3 = this.mSpringDamping;
    d1 = this.mSpringMass;
    double d2 = this.mSpringStiffness;
    paramDouble = -this.mInitialVelocity;
    double d6 = d3 / Math.sqrt(d2 * d1) * 2.0D;
    double d7 = Math.sqrt(d2 / d1);
    double d4 = Math.sqrt(1.0D - d6 * d6) * d7;
    d2 = this.mEndValue - this.mStartValue;
    double d5 = this.mTimeAccumulator;
    if (d6 < 1.0D) {
      d1 = Math.exp(-d6 * d7 * d5);
      d3 = this.mEndValue;
      d6 *= d7;
      paramDouble += d6 * d2;
      d7 = paramDouble / d4;
      double d9 = d5 * d4;
      d5 = Math.sin(d9);
      double d8 = Math.cos(d9);
      paramDouble = d6 * d1 * (Math.sin(d9) * paramDouble / d4 + Math.cos(d9) * d2) - (Math.cos(d9) * paramDouble - d4 * d2 * Math.sin(d9)) * d1;
      d1 = d3 - (d7 * d5 + d8 * d2) * d1;
    } else {
      d3 = Math.exp(-d7 * d5);
      d1 = this.mEndValue - ((d7 * d2 + paramDouble) * d5 + d2) * d3;
      paramDouble = d3 * (paramDouble * (d5 * d7 - 1.0D) + d5 * d2 * d7 * d7);
    } 
    PhysicsState physicsState = this.mCurrentState;
    physicsState.position = d1;
    physicsState.velocity = paramDouble;
    if (isAtRest() || (this.mOvershootClampingEnabled && isOvershooting())) {
      if (this.mSpringStiffness > 0.0D) {
        paramDouble = this.mEndValue;
        this.mStartValue = paramDouble;
        this.mCurrentState.position = paramDouble;
      } else {
        this.mEndValue = this.mCurrentState.position;
        this.mStartValue = this.mEndValue;
      } 
      this.mCurrentState.velocity = 0.0D;
    } 
  }
  
  private double getDisplacementDistanceForState(PhysicsState paramPhysicsState) {
    return Math.abs(this.mEndValue - paramPhysicsState.position);
  }
  
  private boolean isAtRest() {
    return (Math.abs(this.mCurrentState.velocity) <= this.mRestSpeedThreshold && (getDisplacementDistanceForState(this.mCurrentState) <= this.mDisplacementFromRestThreshold || this.mSpringStiffness == 0.0D));
  }
  
  private boolean isOvershooting() {
    return (this.mSpringStiffness > 0.0D && ((this.mStartValue < this.mEndValue && this.mCurrentState.position > this.mEndValue) || (this.mStartValue > this.mEndValue && this.mCurrentState.position < this.mEndValue)));
  }
  
  public void resetConfig(ReadableMap paramReadableMap) {
    boolean bool1;
    this.mSpringStiffness = paramReadableMap.getDouble("stiffness");
    this.mSpringDamping = paramReadableMap.getDouble("damping");
    this.mSpringMass = paramReadableMap.getDouble("mass");
    this.mInitialVelocity = this.mCurrentState.velocity;
    this.mEndValue = paramReadableMap.getDouble("toValue");
    this.mRestSpeedThreshold = paramReadableMap.getDouble("restSpeedThreshold");
    this.mDisplacementFromRestThreshold = paramReadableMap.getDouble("restDisplacementThreshold");
    this.mOvershootClampingEnabled = paramReadableMap.getBoolean("overshootClamping");
    boolean bool = paramReadableMap.hasKey("iterations");
    boolean bool2 = true;
    if (bool) {
      bool1 = paramReadableMap.getInt("iterations");
    } else {
      bool1 = true;
    } 
    this.mIterations = bool1;
    if (this.mIterations != 0)
      bool2 = false; 
    this.mHasFinished = bool2;
    this.mCurrentLoop = 0;
    this.mTimeAccumulator = 0.0D;
    this.mSpringStarted = false;
  }
  
  public void runAnimationStep(long paramLong) {
    paramLong /= 1000000L;
    if (!this.mSpringStarted) {
      if (this.mCurrentLoop == 0) {
        this.mOriginalValue = this.mAnimatedValue.mValue;
        this.mCurrentLoop = 1;
      } 
      PhysicsState physicsState = this.mCurrentState;
      double d1 = this.mAnimatedValue.mValue;
      physicsState.position = d1;
      this.mStartValue = d1;
      this.mLastTime = paramLong;
      this.mTimeAccumulator = 0.0D;
      this.mSpringStarted = true;
    } 
    double d = (paramLong - this.mLastTime);
    Double.isNaN(d);
    advance(d / 1000.0D);
    this.mLastTime = paramLong;
    this.mAnimatedValue.mValue = this.mCurrentState.position;
    if (isAtRest()) {
      int i = this.mIterations;
      if (i == -1 || this.mCurrentLoop < i) {
        this.mSpringStarted = false;
        this.mAnimatedValue.mValue = this.mOriginalValue;
        this.mCurrentLoop++;
        return;
      } 
      this.mHasFinished = true;
      return;
    } 
  }
  
  static class PhysicsState {
    double position;
    
    double velocity;
    
    private PhysicsState() {}
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\animated\SpringAnimation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */