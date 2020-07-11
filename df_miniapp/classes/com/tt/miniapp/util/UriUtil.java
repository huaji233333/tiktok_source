package com.tt.miniapp.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import com.tt.miniapphost.AppbrandContext;
import java.io.File;
import java.util.Set;

public class UriUtil {
  public static Intent generateCommonIntent(Context paramContext, String paramString1, String paramString2) {
    Intent intent = new Intent();
    intent.addFlags(268435456);
    intent.setAction("android.intent.action.VIEW");
    intent.setDataAndType(getUri(paramContext, intent, new File(paramString1)), paramString2);
    return intent;
  }
  
  private static Uri getUri(Context paramContext, Intent paramIntent, File paramFile) {
    if (Build.VERSION.SDK_INT >= 24) {
      Uri uri = FileProvider.getUriForFile(paramContext, AppbrandContext.getInst().getInitParams().getHostStr(1007, "com.ss.android.uri.fileprovider"), paramFile);
      paramIntent.addFlags(1);
      return uri;
    } 
    return Uri.fromFile(paramFile);
  }
  
  public static Uri replaceUriParameter(Uri paramUri, String paramString1, String paramString2) {
    Set set = paramUri.getQueryParameterNames();
    Uri.Builder builder = paramUri.buildUpon().clearQuery();
    for (String str2 : set) {
      String str1;
      if (str2.equals(paramString1)) {
        str1 = paramString2;
      } else {
        str1 = paramUri.getQueryParameter(str2);
      } 
      builder.appendQueryParameter(str2, str1);
    } 
    return builder.build();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\UriUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */