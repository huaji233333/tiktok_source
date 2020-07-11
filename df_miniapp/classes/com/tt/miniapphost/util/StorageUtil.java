package com.tt.miniapphost.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import com.tt.miniapp.mmkv.KVUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandConstants;
import java.io.File;

public class StorageUtil {
  public static File getExternalCacheDir(Context paramContext) {
    String str1;
    File file;
    try {
      str1 = Environment.getExternalStorageState();
    } catch (Exception exception) {
      str1 = "";
    } 
    if ("mounted".equals(str1)) {
      File file1 = paramContext.getExternalCacheDir();
    } else {
      str1 = null;
    } 
    String str2 = str1;
    if (str1 == null)
      file = new File(new File(new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data"), paramContext.getPackageName()), "cache"); 
    return file;
  }
  
  public static File getExternalFilesDir(Context paramContext) {
    File file1;
    try {
      str = Environment.getExternalStorageState();
    } catch (Exception exception) {
      str = "";
    } 
    boolean bool = "mounted".equals(str);
    String str = null;
    if (bool)
      file1 = paramContext.getExternalFilesDir(null); 
    File file2 = file1;
    if (file1 == null)
      file2 = new File(new File(new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data"), paramContext.getPackageName()), "files"); 
    if (file2 != null && !file2.exists())
      file2.mkdirs(); 
    return file2;
  }
  
  public static String getSessionByAppid(Context paramContext, String paramString) {
    AppBrandLogger.i("StorageUtil", new Object[] { "getSessionByAppid appId:", paramString });
    String str = KVUtil.getSharedPreferences(paramContext, "TmaSession").getString(paramString, "");
    if (!TextUtils.isEmpty(str)) {
      AppBrandLogger.i("StorageUtil", new Object[] { "getSessionByAppid finish session:", str });
      return str;
    } 
    AppBrandLogger.i("StorageUtil", new Object[] { "getSessionByAppid getDataFrom CookieSp" });
    str = KVUtil.getSharedPreferences(paramContext, AppbrandConstants.SharePreferences.getCookieSp()).getString(paramString, "");
    if (!TextUtils.isEmpty(str)) {
      AppBrandLogger.i("StorageUtil", new Object[] { "getSessionByAppid saveSession begin" });
      saveSession(paramContext, paramString, str);
      AppBrandLogger.i("StorageUtil", new Object[] { "getSessionByAppid saveSession end" });
    } 
    AppBrandLogger.i("StorageUtil", new Object[] { "getSessionByAppid finish session:", str });
    return str;
  }
  
  public static void saveSession(Context paramContext, String paramString1, String paramString2) {
    KVUtil.getSharedPreferences(paramContext, "TmaSession").edit().putString(paramString1, paramString2).commit();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphos\\util\StorageUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */