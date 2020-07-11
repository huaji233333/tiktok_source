package com.facebook.g.a.b;

import android.os.Build;
import android.text.Layout;

public final class a {
  public static int a(Layout paramLayout) {
    byte b = 0;
    if (paramLayout == null)
      return 0; 
    int i = b;
    if (Build.VERSION.SDK_INT < 20) {
      i = b;
      if (paramLayout instanceof android.text.StaticLayout) {
        i = paramLayout.getLineAscent(paramLayout.getLineCount() - 1);
        float f = (paramLayout.getLineDescent(paramLayout.getLineCount() - 1) - i);
        f -= (f - paramLayout.getSpacingAdd()) / paramLayout.getSpacingMultiplier();
        if (f >= 0.0F) {
          double d = f;
          Double.isNaN(d);
          i = (int)(d + 0.5D);
        } else {
          double d = -f;
          Double.isNaN(d);
          i = -((int)(d + 0.5D));
        } 
      } 
    } 
    return paramLayout.getHeight() - i;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\g\a\b\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */