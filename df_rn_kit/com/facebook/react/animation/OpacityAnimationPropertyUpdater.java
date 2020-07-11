package com.facebook.react.animation;

import android.view.View;

public class OpacityAnimationPropertyUpdater extends AbstractSingleFloatProperyUpdater {
  public OpacityAnimationPropertyUpdater(float paramFloat) {
    super(paramFloat);
  }
  
  public OpacityAnimationPropertyUpdater(float paramFloat1, float paramFloat2) {
    super(paramFloat1, paramFloat2);
  }
  
  protected float getProperty(View paramView) {
    return paramView.getAlpha();
  }
  
  protected void setProperty(View paramView, float paramFloat) {
    paramView.setAlpha(paramFloat);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\animation\OpacityAnimationPropertyUpdater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */