package com.facebook.react.animated;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.JSApplicationCausedNativeException;
import com.facebook.react.bridge.ReadableMap;

abstract class AnimationDriver {
  ValueAnimatedNode mAnimatedValue;
  
  Callback mEndCallback;
  
  boolean mHasFinished;
  
  int mId;
  
  public void resetConfig(ReadableMap paramReadableMap) {
    StringBuilder stringBuilder = new StringBuilder("Animation config for ");
    stringBuilder.append(getClass().getSimpleName());
    stringBuilder.append(" cannot be reset");
    throw new JSApplicationCausedNativeException(stringBuilder.toString());
  }
  
  public abstract void runAnimationStep(long paramLong);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\animated\AnimationDriver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */