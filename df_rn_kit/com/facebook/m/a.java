package com.facebook.m;

import android.os.Build;
import android.os.Trace;

public final class a {
  public static void a(long paramLong) {
    if (Build.VERSION.SDK_INT >= 18)
      Trace.endSection(); 
  }
  
  public static void a(long paramLong, String paramString) {
    if (Build.VERSION.SDK_INT >= 18)
      Trace.beginSection(paramString); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\m\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */