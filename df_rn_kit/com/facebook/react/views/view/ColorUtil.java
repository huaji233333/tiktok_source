package com.facebook.react.views.view;

public class ColorUtil {
  public static int getOpacityFromColor(int paramInt) {
    paramInt >>>= 24;
    return (paramInt == 255) ? -1 : ((paramInt == 0) ? -2 : -3);
  }
  
  public static int multiplyColorAlpha(int paramInt1, int paramInt2) {
    return (paramInt2 == 255) ? paramInt1 : ((paramInt2 == 0) ? (paramInt1 & 0xFFFFFF) : (paramInt1 & 0xFFFFFF | (paramInt1 >>> 24) * (paramInt2 + (paramInt2 >> 7)) >> 8 << 24));
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\view\ColorUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */