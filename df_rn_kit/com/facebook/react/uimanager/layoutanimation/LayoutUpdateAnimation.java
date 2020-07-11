package com.facebook.react.uimanager.layoutanimation;

import android.view.View;
import android.view.animation.Animation;

class LayoutUpdateAnimation extends AbstractLayoutAnimation {
  Animation createAnimationImpl(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    boolean bool1;
    float f1 = paramView.getX();
    float f2 = paramInt1;
    boolean bool2 = false;
    if (f1 != f2 || paramView.getY() != paramInt2) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    if (paramView.getWidth() != paramInt3 || paramView.getHeight() != paramInt4)
      bool2 = true; 
    return (!bool1 && !bool2) ? null : new PositionAndSizeAnimation(paramView, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  boolean isValid() {
    return (this.mDurationMs > 0);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\layoutanimation\LayoutUpdateAnimation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */