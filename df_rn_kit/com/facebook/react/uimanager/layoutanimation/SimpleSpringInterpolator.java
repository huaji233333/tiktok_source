package com.facebook.react.uimanager.layoutanimation;

import android.view.animation.Interpolator;

class SimpleSpringInterpolator implements Interpolator {
  public float getInterpolation(float paramFloat) {
    double d1 = Math.pow(2.0D, (-10.0F * paramFloat));
    double d2 = (paramFloat - 0.125F);
    Double.isNaN(d2);
    return (float)(d1 * Math.sin(d2 * Math.PI * 2.0D / 0.5D) + 1.0D);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\layoutanimation\SimpleSpringInterpolator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */