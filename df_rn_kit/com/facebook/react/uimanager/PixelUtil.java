package com.facebook.react.uimanager;

import android.util.TypedValue;

public class PixelUtil {
  public static float toDIPFromPixel(float paramFloat) {
    return paramFloat / (DisplayMetricsHolder.getWindowDisplayMetrics()).density;
  }
  
  public static float toPixelFromDIP(double paramDouble) {
    return toPixelFromDIP((float)paramDouble);
  }
  
  public static float toPixelFromDIP(float paramFloat) {
    return TypedValue.applyDimension(1, paramFloat, DisplayMetricsHolder.getWindowDisplayMetrics());
  }
  
  public static float toPixelFromSP(double paramDouble) {
    return toPixelFromSP((float)paramDouble);
  }
  
  public static float toPixelFromSP(float paramFloat) {
    return TypedValue.applyDimension(2, paramFloat, DisplayMetricsHolder.getWindowDisplayMetrics());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\PixelUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */