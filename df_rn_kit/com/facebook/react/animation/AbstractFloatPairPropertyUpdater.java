package com.facebook.react.animation;

import android.view.View;

public abstract class AbstractFloatPairPropertyUpdater implements AnimationPropertyUpdater {
  private boolean mFromSource;
  
  private final float[] mFromValues = new float[2];
  
  private final float[] mToValues = new float[2];
  
  private final float[] mUpdateValues = new float[2];
  
  protected AbstractFloatPairPropertyUpdater(float paramFloat1, float paramFloat2) {
    float[] arrayOfFloat = this.mToValues;
    arrayOfFloat[0] = paramFloat1;
    arrayOfFloat[1] = paramFloat2;
    this.mFromSource = true;
  }
  
  protected AbstractFloatPairPropertyUpdater(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    this(paramFloat3, paramFloat4);
    float[] arrayOfFloat = this.mFromValues;
    arrayOfFloat[0] = paramFloat1;
    arrayOfFloat[1] = paramFloat2;
    this.mFromSource = false;
  }
  
  protected abstract void getProperty(View paramView, float[] paramArrayOffloat);
  
  public void onFinish(View paramView) {
    setProperty(paramView, this.mToValues);
  }
  
  public void onUpdate(View paramView, float paramFloat) {
    float[] arrayOfFloat1 = this.mUpdateValues;
    float[] arrayOfFloat2 = this.mFromValues;
    float f = arrayOfFloat2[0];
    float[] arrayOfFloat3 = this.mToValues;
    arrayOfFloat1[0] = f + (arrayOfFloat3[0] - arrayOfFloat2[0]) * paramFloat;
    arrayOfFloat1[1] = arrayOfFloat2[1] + (arrayOfFloat3[1] - arrayOfFloat2[1]) * paramFloat;
    setProperty(paramView, arrayOfFloat1);
  }
  
  public void prepare(View paramView) {
    if (this.mFromSource)
      getProperty(paramView, this.mFromValues); 
  }
  
  protected abstract void setProperty(View paramView, float[] paramArrayOffloat);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\animation\AbstractFloatPairPropertyUpdater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */