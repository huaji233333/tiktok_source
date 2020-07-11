package com.tt.miniapphost.util;

import android.app.Application;
import android.content.ContextWrapper;
import android.content.pm.PackageInfo;
import android.webkit.WebView;
import com.storage.async.Action;
import com.storage.async.Schedulers;
import com.tt.miniapp.thread.ThreadUtil;
import java.io.File;

public class WebViewDataUtil {
  private static String DIR_NAME_SUFFIX = "";
  
  public static String getSuffix() {
    return DIR_NAME_SUFFIX;
  }
  
  public static void init(ContextWrapper paramContextWrapper, String paramString) {
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(Application.getProcessName());
    stringBuilder1.append("_");
    stringBuilder1.append(paramString);
    DIR_NAME_SUFFIX = stringBuilder1.toString();
    PackageInfo packageInfo = WebView.getCurrentWebViewPackage();
    if (packageInfo == null)
      return; 
    String str2 = packageInfo.versionName;
    int i = str2.indexOf('.');
    String str1 = str2;
    if (i != -1)
      str1 = str2.substring(0, i); 
    File file2 = paramContextWrapper.getDataDir();
    StringBuilder stringBuilder2 = new StringBuilder("app_webview_");
    stringBuilder2.append(DIR_NAME_SUFFIX);
    file2 = new File(file2, stringBuilder2.toString());
    stringBuilder2 = new StringBuilder("ver_");
    stringBuilder2.append(str1);
    File file1 = new File(file2, stringBuilder2.toString());
    if (file1.exists())
      return; 
    if (file2.isDirectory()) {
      final File TO_DELETE = paramContextWrapper.getDataDir();
      stringBuilder2 = new StringBuilder("app_webview_");
      stringBuilder2.append(DIR_NAME_SUFFIX);
      stringBuilder2.append("-obsolete-");
      stringBuilder2.append(System.currentTimeMillis());
      file = new File(file, stringBuilder2.toString());
      file2.renameTo(file);
      ThreadUtil.runOnWorkThread(new Action() {
            public final void act() {
              FileUtil.delete(TO_DELETE);
            }
          },  Schedulers.shortIO(), true);
    } 
    try {
      file2.mkdir();
      return;
    } finally {
      paramContextWrapper = null;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphos\\util\WebViewDataUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */