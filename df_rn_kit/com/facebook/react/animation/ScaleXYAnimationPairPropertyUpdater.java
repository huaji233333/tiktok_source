package com.facebook.react.animation;

import android.view.View;

public class ScaleXYAnimationPairPropertyUpdater extends AbstractFloatPairPropertyUpdater {
  public ScaleXYAnimationPairPropertyUpdater(float paramFloat1, float paramFloat2) {
    super(paramFloat1, paramFloat2);
  }
  
  public ScaleXYAnimationPairPropertyUpdater(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    super(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  protected void getProperty(View paramView, float[] paramArrayOffloat) {
    paramArrayOffloat[0] = paramView.getScaleX();
    paramArrayOffloat[1] = paramView.getScaleY();
  }
  
  protected void setProperty(View paramView, float[] paramArrayOffloat) {
    paramView.setScaleX(paramArrayOffloat[0]);
    paramView.setScaleY(paramArrayOffloat[1]);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\animation\ScaleXYAnimationPairPropertyUpdater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */