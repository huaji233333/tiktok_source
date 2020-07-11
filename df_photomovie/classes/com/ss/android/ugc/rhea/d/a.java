package com.ss.android.ugc.rhea.d;

import android.app.Notification;
import android.app.NotificationManager;
import android.widget.RemoteViews;
import com.ss.android.ugc.rhea.b.b;

public final class a {
  public static String a = "com.ss.android.ugc.conan.trace";
  
  public static NotificationManager b;
  
  private static int c = 1;
  
  private static int d = 2;
  
  private static RemoteViews e;
  
  private static Notification f;
  
  public static void a() {
    String str;
    if (e == null)
      return; 
    boolean bool = b.a();
    RemoteViews remoteViews = e;
    if (bool) {
      str = "Stop";
    } else {
      str = "Start";
    } 
    remoteViews.setTextViewText(1996685313, str);
    b.notify(a.hashCode(), f);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_photomovie\classes.jar!\com\ss\androi\\ugc\rhea\d\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */