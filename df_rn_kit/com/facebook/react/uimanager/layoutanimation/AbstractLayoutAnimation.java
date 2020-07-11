package com.facebook.react.uimanager.layoutanimation;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.IllegalViewOperationException;
import java.util.Map;

abstract class AbstractLayoutAnimation {
  private static final Map<InterpolatorType, Interpolator> INTERPOLATOR = MapBuilder.of(InterpolatorType.LINEAR, new LinearInterpolator(), InterpolatorType.EASE_IN, new AccelerateInterpolator(), InterpolatorType.EASE_OUT, new DecelerateInterpolator(), InterpolatorType.EASE_IN_EASE_OUT, new AccelerateDecelerateInterpolator(), InterpolatorType.SPRING, new SimpleSpringInterpolator());
  
  protected AnimatedPropertyType mAnimatedProperty;
  
  private int mDelayMs;
  
  protected int mDurationMs;
  
  private Interpolator mInterpolator;
  
  private static Interpolator getInterpolator(InterpolatorType paramInterpolatorType) {
    Interpolator interpolator = INTERPOLATOR.get(paramInterpolatorType);
    if (interpolator != null)
      return interpolator; 
    StringBuilder stringBuilder = new StringBuilder("Missing interpolator for type : ");
    stringBuilder.append(paramInterpolatorType);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public final Animation createAnimation(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (!isValid())
      return null; 
    Animation animation = createAnimationImpl(paramView, paramInt1, paramInt2, paramInt3, paramInt4);
    if (animation != null) {
      animation.setDuration((this.mDurationMs * 1));
      animation.setStartOffset((this.mDelayMs * 1));
      animation.setInterpolator(this.mInterpolator);
    } 
    return animation;
  }
  
  abstract Animation createAnimationImpl(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public void initializeFromConfig(ReadableMap paramReadableMap, int paramInt) {
    AnimatedPropertyType animatedPropertyType;
    if (paramReadableMap.hasKey("property")) {
      animatedPropertyType = AnimatedPropertyType.fromString(paramReadableMap.getString("property"));
    } else {
      animatedPropertyType = null;
    } 
    this.mAnimatedProperty = animatedPropertyType;
    if (paramReadableMap.hasKey("duration"))
      paramInt = paramReadableMap.getInt("duration"); 
    this.mDurationMs = paramInt;
    if (paramReadableMap.hasKey("delay")) {
      paramInt = paramReadableMap.getInt("delay");
    } else {
      paramInt = 0;
    } 
    this.mDelayMs = paramInt;
    if (paramReadableMap.hasKey("type")) {
      this.mInterpolator = getInterpolator(InterpolatorType.fromString(paramReadableMap.getString("type")));
      if (isValid())
        return; 
      StringBuilder stringBuilder = new StringBuilder("Invalid layout animation : ");
      stringBuilder.append(paramReadableMap);
      throw new IllegalViewOperationException(stringBuilder.toString());
    } 
    throw new IllegalArgumentException("Missing interpolation type.");
  }
  
  abstract boolean isValid();
  
  public void reset() {
    this.mAnimatedProperty = null;
    this.mDurationMs = 0;
    this.mDelayMs = 0;
    this.mInterpolator = null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\layoutanimation\AbstractLayoutAnimation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */