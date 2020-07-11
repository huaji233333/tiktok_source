package com.facebook.react.uimanager;

public class FloatUtil {
  public static boolean floatsEqual(float paramFloat1, float paramFloat2) {
    return (Float.isNaN(paramFloat1) || Float.isNaN(paramFloat2)) ? ((Float.isNaN(paramFloat1) && Float.isNaN(paramFloat2))) : ((Math.abs(paramFloat2 - paramFloat1) < 1.0E-5F));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\FloatUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */