package com.ss.android.ugc.rhea.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import com.ss.android.ugc.rhea.b;
import com.ss.android.ugc.rhea.b.b;
import com.ss.android.ugc.rhea.d.a;
import com.ss.android.ugc.rhea.e.a;
import d.e.h;
import d.f.b.l;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public final class ControllerReceiver extends BroadcastReceiver {
  public final void onReceive(Context paramContext, Intent paramIntent) {
    File file;
    String str;
    l.b(paramContext, "context");
    if (paramIntent != null) {
      str = paramIntent.getAction();
    } else {
      paramIntent = null;
    } 
    if (l.a(paramIntent, "rhea_action_trace")) {
      l.b(paramContext, "context");
      if (TextUtils.equals(b.a(), "manuallyMTrace")) {
        if (b.a()) {
          b.a(paramContext, false);
          return;
        } 
        l.b(paramContext, "context");
        b.a = true;
        a a = a.c;
        SimpleDateFormat simpleDateFormat = a.b;
        Calendar calendar = Calendar.getInstance();
        l.a(calendar, "Calendar.getInstance()");
        str = simpleDateFormat.format(calendar.getTime());
        l.a(str, "DATE_FORMATTER.format(Calendar.getInstance().time)");
        l.b(str, "fileName");
        if (paramContext != null) {
          String str1;
          l.b(paramContext, "context");
          if (b.a) {
            StringBuilder stringBuilder1 = new StringBuilder();
            File file1 = paramContext.getFilesDir();
            l.a(file1, "context.filesDir");
            stringBuilder1.append(file1.getAbsolutePath());
            stringBuilder1.append("/rhea");
            str1 = stringBuilder1.toString();
          } else if (Build.VERSION.SDK_INT >= 19) {
            StringBuilder stringBuilder1 = new StringBuilder();
            File file1 = str1.getExternalFilesDir("");
            l.a(file1, "context.getExternalFilesDir(\"\")");
            stringBuilder1.append(file1.getAbsolutePath());
            stringBuilder1.append("/rhea");
            String str2 = stringBuilder1.toString();
          } else {
            StringBuilder stringBuilder1 = new StringBuilder();
            File file1 = Environment.getExternalStorageDirectory();
            l.a(file1, "Environment.getExternalStorageDirectory()");
            stringBuilder1.append(file1.getPath());
            stringBuilder1.append("/rhea");
            str1 = stringBuilder1.toString();
          } 
          file = new File(str1);
          if (!file.exists())
            file.mkdirs(); 
          StringBuilder stringBuilder = new StringBuilder("rhea_");
          stringBuilder.append(str);
          stringBuilder.append(".fake");
          file = new File(file, stringBuilder.toString());
          a.a = file;
          h.a(file, "Version-1.0.0", null, 2, null);
        } 
        a.a();
      } 
      return;
    } 
    if (l.a(str, "rhea_action_stop_app")) {
      l.b(file, "context");
      if (TextUtils.equals(b.a(), "manuallyMTrace"))
        b.a((Context)file, true); 
      System.exit(0);
      throw (Throwable)new RuntimeException("System.exit returned normally, while it was supposed to halt JVM.");
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_photomovie\classes.jar!\com\ss\androi\\ugc\rhea\receiver\ControllerReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */