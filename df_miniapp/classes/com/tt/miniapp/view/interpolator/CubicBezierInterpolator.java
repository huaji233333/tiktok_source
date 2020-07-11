package com.tt.miniapp.view.interpolator;

import android.view.animation.Interpolator;

public class CubicBezierInterpolator implements Interpolator {
  private final float mX1;
  
  private final float mX2;
  
  private final float mY1;
  
  private final float mY2;
  
  public CubicBezierInterpolator(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    this((float)paramDouble1, (float)paramDouble2, (float)paramDouble3, (float)paramDouble4);
  }
  
  public CubicBezierInterpolator(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    this.mX1 = paramFloat1;
    this.mY1 = paramFloat2;
    this.mX2 = paramFloat3;
    this.mY2 = paramFloat4;
  }
  
  private float getCoordinate(float paramFloat1, float paramFloat2, float paramFloat3) {
    float f = paramFloat1;
    if (paramFloat1 != 0.0F) {
      if (paramFloat1 == 1.0F)
        return paramFloat1; 
      f = linearInterpolate(0.0F, paramFloat2, paramFloat1);
      paramFloat2 = linearInterpolate(paramFloat2, paramFloat3, paramFloat1);
      paramFloat3 = linearInterpolate(paramFloat3, 1.0F, paramFloat1);
      f = linearInterpolate(linearInterpolate(f, paramFloat2, paramFloat1), linearInterpolate(paramFloat2, paramFloat3, paramFloat1), paramFloat1);
    } 
    return f;
  }
  
  private float getTForXValue(float paramFloat) {
    float f2;
    float f6;
    int j;
    if (paramFloat <= 0.0F)
      return 0.0F; 
    if (paramFloat >= 1.0F)
      return 1.0F; 
    byte b = 0;
    float f1 = paramFloat;
    int i = 0;
    float f4 = 0.0F;
    float f3 = 1.0F;
    float f5 = 0.0F;
    while (true) {
      f2 = f1;
      j = b;
      float f = f4;
      f6 = f3;
      if (i < 8) {
        float f7 = getX(f1);
        double d = ((getX(f1 + 1.0E-4F) - f7) / 1.0E-4F);
        float f8 = f7 - paramFloat;
        if (Math.abs(f8) < 1.0E-4F)
          return f1; 
        f2 = f1;
        j = b;
        f = f4;
        f6 = f3;
        f5 = f7;
        if (Math.abs(d) >= 9.999999747378752E-5D) {
          if (f7 < paramFloat) {
            f4 = f1;
          } else {
            f3 = f1;
          } 
          double d1 = f1;
          double d2 = f8;
          Double.isNaN(d2);
          Double.isNaN(d);
          d = d2 / d;
          Double.isNaN(d1);
          f1 = (float)(d1 - d);
          i++;
          f5 = f7;
          continue;
        } 
      } 
      break;
    } 
    while (Math.abs(f5 - paramFloat) > 1.0E-4F && j < 8) {
      float f;
      if (f5 < paramFloat) {
        f1 = (f2 + f6) / 2.0F;
        f = f2;
      } else {
        f1 = (f2 + f) / 2.0F;
        f6 = f2;
      } 
      f5 = getX(f1);
      j++;
      f2 = f1;
    } 
    return f2;
  }
  
  private float getX(float paramFloat) {
    return getCoordinate(paramFloat, this.mX1, this.mX2);
  }
  
  private float getY(float paramFloat) {
    return getCoordinate(paramFloat, this.mY1, this.mY2);
  }
  
  private float linearInterpolate(float paramFloat1, float paramFloat2, float paramFloat3) {
    return paramFloat1 + (paramFloat2 - paramFloat1) * paramFloat3;
  }
  
  public float getInterpolation(float paramFloat) {
    return getY(getTForXValue(paramFloat));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\view\interpolator\CubicBezierInterpolator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */