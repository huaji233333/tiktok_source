package com.facebook.react.uimanager.layoutanimation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import com.facebook.react.uimanager.IllegalViewOperationException;

abstract class BaseLayoutAnimation extends AbstractLayoutAnimation {
  Animation createAnimationImpl(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (this.mAnimatedProperty != null) {
      StringBuilder stringBuilder;
      float f1;
      paramInt1 = null.$SwitchMap$com$facebook$react$uimanager$layoutanimation$AnimatedPropertyType[this.mAnimatedProperty.ordinal()];
      float f2 = 0.0F;
      if (paramInt1 != 1) {
        if (paramInt1 == 2) {
          if (isReverse()) {
            f1 = 1.0F;
          } else {
            f1 = 0.0F;
          } 
          if (isReverse()) {
            f2 = 0.0F;
          } else {
            f2 = 1.0F;
          } 
          return (Animation)new ScaleAnimation(f1, f2, f1, f2, 1, 0.5F, 1, 0.5F);
        } 
        stringBuilder = new StringBuilder("Missing animation for property : ");
        stringBuilder.append(this.mAnimatedProperty);
        throw new IllegalViewOperationException(stringBuilder.toString());
      } 
      if (isReverse()) {
        f1 = stringBuilder.getAlpha();
      } else {
        f1 = 0.0F;
      } 
      if (!isReverse())
        f2 = stringBuilder.getAlpha(); 
      return new OpacityAnimation((View)stringBuilder, f1, f2);
    } 
    throw new IllegalViewOperationException("Missing animated property from animation config");
  }
  
  abstract boolean isReverse();
  
  boolean isValid() {
    return (this.mDurationMs > 0 && this.mAnimatedProperty != null);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\layoutanimation\BaseLayoutAnimation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */