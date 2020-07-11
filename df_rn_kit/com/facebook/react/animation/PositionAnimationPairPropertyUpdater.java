package com.facebook.react.animation;

import android.view.View;

public class PositionAnimationPairPropertyUpdater extends AbstractFloatPairPropertyUpdater {
  public PositionAnimationPairPropertyUpdater(float paramFloat1, float paramFloat2) {
    super(paramFloat1, paramFloat2);
  }
  
  public PositionAnimationPairPropertyUpdater(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    super(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  protected void getProperty(View paramView, float[] paramArrayOffloat) {
    paramArrayOffloat[0] = paramView.getX() + paramView.getWidth() * 0.5F;
    paramArrayOffloat[1] = paramView.getY() + paramView.getHeight() * 0.5F;
  }
  
  protected void setProperty(View paramView, float[] paramArrayOffloat) {
    paramView.setX(paramArrayOffloat[0] - paramView.getWidth() * 0.5F);
    paramView.setY(paramArrayOffloat[1] - paramView.getHeight() * 0.5F);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\animation\PositionAnimationPairPropertyUpdater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */