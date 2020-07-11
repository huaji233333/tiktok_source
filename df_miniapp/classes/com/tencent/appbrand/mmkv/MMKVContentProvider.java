package com.tencent.appbrand.mmkv;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

public class MMKVContentProvider extends ContentProvider {
  static Uri a;
  
  static String a(Context paramContext) {
    try {
      ComponentName componentName = new ComponentName(paramContext, MMKVContentProvider.class.getName());
      PackageManager packageManager = paramContext.getPackageManager();
      if (packageManager != null) {
        ProviderInfo providerInfo = packageManager.getProviderInfo(componentName, 0);
        if (providerInfo != null)
          return providerInfo.authority; 
      } 
    } catch (Exception exception) {}
    return null;
  }
  
  protected static String a(Context paramContext, int paramInt) {
    ActivityManager activityManager = (ActivityManager)paramContext.getSystemService("activity");
    if (activityManager != null)
      for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : activityManager.getRunningAppProcesses()) {
        if (runningAppProcessInfo.pid == paramInt)
          return runningAppProcessInfo.processName; 
      }  
    return "";
  }
  
  public Bundle call(String paramString1, String paramString2, Bundle paramBundle) {
    if (paramString1.equals("mmkvFromAshmemID") && paramBundle != null) {
      int i = paramBundle.getInt("KEY_SIZE");
      int j = paramBundle.getInt("KEY_MODE");
      paramString1 = paramBundle.getString("KEY_CRYPT");
      MMKV mMKV = MMKV.mmkvWithAshmemID(getContext(), paramString2, i, j, paramString1);
      if (mMKV != null) {
        ParcelableMMKV parcelableMMKV = new ParcelableMMKV(mMKV);
        Bundle bundle = new Bundle();
        bundle.putParcelable("KEY", parcelableMMKV);
        return bundle;
      } 
    } 
    return null;
  }
  
  public int delete(Uri paramUri, String paramString, String[] paramArrayOfString) {
    throw new UnsupportedOperationException("Not implement in MMKV");
  }
  
  public String getType(Uri paramUri) {
    return null;
  }
  
  public Uri insert(Uri paramUri, ContentValues paramContentValues) {
    throw new UnsupportedOperationException("Not implement in MMKV");
  }
  
  public boolean onCreate() {
    Context context = getContext();
    if (context == null)
      return false; 
    String str = a(context);
    if (str == null)
      return false; 
    if (a == null) {
      StringBuilder stringBuilder = new StringBuilder("content://");
      stringBuilder.append(str);
      a = Uri.parse(stringBuilder.toString());
    } 
    return true;
  }
  
  public Cursor query(Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2) {
    throw new UnsupportedOperationException("Not implement in MMKV");
  }
  
  public int update(Uri paramUri, ContentValues paramContentValues, String paramString, String[] paramArrayOfString) {
    throw new UnsupportedOperationException("Not implement in MMKV");
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tencent\appbrand\mmkv\MMKVContentProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */