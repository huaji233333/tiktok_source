package com.facebook.react.animation;

import android.view.View;

public class ScaleXAnimationPropertyUpdater extends AbstractSingleFloatProperyUpdater {
  public ScaleXAnimationPropertyUpdater(float paramFloat) {
    super(paramFloat);
  }
  
  public ScaleXAnimationPropertyUpdater(float paramFloat1, float paramFloat2) {
    super(paramFloat1, paramFloat2);
  }
  
  protected float getProperty(View paramView) {
    return paramView.getScaleX();
  }
  
  protected void setProperty(View paramView, float paramFloat) {
    paramView.setScaleX(paramFloat);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\animation\ScaleXAnimationPropertyUpdater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */