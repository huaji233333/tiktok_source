package com.facebook.react.devsupport;

import android.os.Build;

class WindowOverlayCompat {
  static final int TYPE_SYSTEM_ALERT;
  
  static final int TYPE_SYSTEM_OVERLAY;
  
  static {
    int i = Build.VERSION.SDK_INT;
    char c = '߶';
    if (i < 26) {
      i = 2003;
    } else {
      i = 2038;
    } 
    TYPE_SYSTEM_ALERT = i;
    i = c;
    if (Build.VERSION.SDK_INT < 26)
      i = 2006; 
    TYPE_SYSTEM_OVERLAY = i;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\devsupport\WindowOverlayCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */