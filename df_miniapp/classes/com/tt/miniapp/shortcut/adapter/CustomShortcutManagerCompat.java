package com.tt.miniapp.shortcut.adapter;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v4.content.c;
import android.text.TextUtils;
import com.tt.miniapp.shortcut.ShortcutEntity;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class CustomShortcutManagerCompat {
  public static Intent createShortcutResultIntent(Context paramContext, CustomShortcutInfoCompat paramCustomShortcutInfoCompat) {
    Intent intent;
    if (Build.VERSION.SDK_INT >= 26) {
      Intent intent1 = ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).createShortcutResultIntent(paramCustomShortcutInfoCompat.toShortcutInfo());
    } else {
      paramContext = null;
    } 
    Context context = paramContext;
    if (paramContext == null)
      intent = new Intent(); 
    return paramCustomShortcutInfoCompat.addToIntent(intent);
  }
  
  private static void deleteShortcut(Context paramContext, String paramString1, String paramString2) {
    Intent intent2 = new Intent("android.intent.action.MAIN");
    intent2.setComponent(new ComponentName(paramContext.getPackageName(), paramString2));
    Intent intent1 = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
    intent1.putExtra("android.intent.extra.shortcut.NAME", paramString1);
    intent1.putExtra("android.intent.extra.shortcut.INTENT", (Parcelable)intent2);
    intent1.putExtra("duplicate", true);
    paramContext.sendBroadcast(intent1);
  }
  
  private static String getLauncherAuthority(Context paramContext) {
    Intent intent = new Intent("android.intent.action.MAIN");
    intent.addCategory("android.intent.category.HOME");
    PackageManager packageManager = paramContext.getPackageManager();
    ResolveInfo resolveInfo = packageManager.resolveActivity(intent, 0);
    if (resolveInfo == null) {
      AppBrandLogger.i("CustomShortcutManagerCompat", new Object[] { "resolveInfo not found" });
      return "";
    } 
    List list = packageManager.queryContentProviders(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.applicationInfo.uid, 8);
    if (list == null) {
      AppBrandLogger.i("CustomShortcutManagerCompat", new Object[] { "providerInfoList not found" });
      return "";
    } 
    for (ProviderInfo providerInfo : list) {
      if (!TextUtils.isEmpty(providerInfo.readPermission) && Pattern.matches(".*launcher.*READ_SETTINGS", providerInfo.readPermission))
        return providerInfo.authority; 
    } 
    return "";
  }
  
  public static ShortcutStatus getShortcutState(Context paramContext, ShortcutEntity paramShortcutEntity) {
    String str;
    ShortcutStatus shortcutStatus = new ShortcutStatus();
    if (paramContext == null || paramShortcutEntity == null) {
      AppBrandLogger.e("CustomShortcutManagerCompat", new Object[] { "query params error" });
      return shortcutStatus;
    } 
    if (Looper.myLooper() == Looper.getMainLooper())
      AppBrandLogger.e("CustomShortcutManagerCompat", new Object[] { "should not quey in main thread" }); 
    if (Build.VERSION.SDK_INT >= 25) {
      Iterator<ShortcutInfo> iterator = ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).getPinnedShortcuts().iterator();
      while (iterator.hasNext()) {
        ShortcutInfo shortcutInfo = iterator.next();
        if (TextUtils.equals(shortcutInfo.getId(), paramShortcutEntity.getAppId())) {
          shortcutStatus.exist = true;
          PersistableBundle persistableBundle = shortcutInfo.getExtras();
          if (persistableBundle != null) {
            str = persistableBundle.getString("key_shortcut_id");
            if (!TextUtils.equals(paramShortcutEntity.getShortcutMd5(), str))
              shortcutStatus.needUpdate = true; 
          } 
          return shortcutStatus;
        } 
      } 
    } else {
      queryLauncher((Context)str, paramShortcutEntity, shortcutStatus);
    } 
    return shortcutStatus;
  }
  
  public static boolean isRequestPinShortcutSupported(Context paramContext) {
    return (Build.VERSION.SDK_INT >= 26) ? ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).isRequestPinShortcutSupported() : (!(c.a(paramContext, "com.android.launcher.permission.INSTALL_SHORTCUT") != 0));
  }
  
  public static boolean isShortcutExist(Context paramContext, String paramString) {
    if (Build.VERSION.SDK_INT < 25)
      return false; 
    Iterator<ShortcutInfo> iterator = ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).getPinnedShortcuts().iterator();
    while (iterator.hasNext()) {
      if (TextUtils.equals(((ShortcutInfo)iterator.next()).getId(), paramString))
        return true; 
    } 
    return false;
  }
  
  private static void queryLauncher(Context paramContext, ShortcutEntity paramShortcutEntity, ShortcutStatus paramShortcutStatus) {
    Cursor cursor;
    String str2 = getLauncherAuthority(paramContext);
    String str1 = str2;
    if (TextUtils.isEmpty(str2)) {
      AppBrandLogger.e("CustomShortcutManagerCompat", new Object[] { "launcherAuthority not found" });
      int i = Build.VERSION.SDK_INT;
      if (i < 8) {
        str1 = "com.android.launcher.settings";
      } else if (i < 19) {
        str1 = "com.android.launcher2.settings";
      } else {
        str1 = "com.android.launcher3.settings";
      } 
    } 
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("content://");
    stringBuffer.append(str1);
    stringBuffer.append("/favorites?notify=true");
    String str4 = null;
    String str3 = null;
    str1 = str3;
    str2 = str4;
    try {
      Uri uri = Uri.parse(stringBuffer.toString());
      str1 = str3;
      str2 = str4;
      Cursor cursor1 = paramContext.getContentResolver().query(uri, null, "title=? ", new String[] { paramShortcutEntity.getLabel() }, null);
      if (cursor1 != null) {
        Cursor cursor2 = cursor1;
        cursor = cursor1;
        if (cursor1.moveToNext()) {
          cursor2 = cursor1;
          cursor = cursor1;
          paramShortcutStatus.exist = true;
          cursor2 = cursor1;
          cursor = cursor1;
          Intent intent = Intent.parseUri(cursor1.getString(cursor1.getColumnIndex("intent")), 0);
          cursor2 = cursor1;
          cursor = cursor1;
          if (!TextUtils.equals(intent.getStringExtra("key_shortcut_id"), paramShortcutEntity.getShortcutMd5())) {
            cursor2 = cursor1;
            cursor = cursor1;
            paramShortcutStatus.needUpdate = true;
          } 
          cursor2 = cursor1;
          cursor = cursor1;
          StringBuilder stringBuilder = new StringBuilder("get shortcut intent");
          cursor2 = cursor1;
          cursor = cursor1;
          stringBuilder.append(intent);
          cursor2 = cursor1;
          cursor = cursor1;
          AppBrandLogger.e("CustomShortcutManagerCompat", new Object[] { stringBuilder.toString() });
          cursor2 = cursor1;
          cursor = cursor1;
          if (!cursor1.isClosed()) {
            cursor2 = cursor1;
            cursor = cursor1;
            cursor1.close();
          } 
        } 
      } 
      if (cursor1 != null && !cursor1.isClosed()) {
        cursor1.close();
        return;
      } 
    } catch (Exception exception) {
      Cursor cursor1 = cursor;
      AppBrandLogger.e("CustomShortcutManagerCompat", new Object[] { exception });
      if (cursor != null && !cursor.isClosed())
        cursor.close(); 
    } finally {}
  }
  
  public static boolean requestPinShortcut(Context paramContext, CustomShortcutInfoCompat paramCustomShortcutInfoCompat, final IntentSender callback) {
    if (Build.VERSION.SDK_INT >= 26)
      return ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).requestPinShortcut(paramCustomShortcutInfoCompat.toShortcutInfo(), callback); 
    if (!isRequestPinShortcutSupported(paramContext))
      return false; 
    Intent intent = paramCustomShortcutInfoCompat.addToIntent(new Intent("com.android.launcher.action.INSTALL_SHORTCUT"));
    if (callback == null) {
      paramContext.sendBroadcast(intent);
      return true;
    } 
    paramContext.sendOrderedBroadcast(intent, null, new BroadcastReceiver() {
          public final void onReceive(Context param1Context, Intent param1Intent) {
            try {
              callback.sendIntent(param1Context, 0, null, null, null);
              return;
            } catch (android.content.IntentSender.SendIntentException sendIntentException) {
              return;
            } 
          }
        }null, -1, null, null);
    return true;
  }
  
  public static boolean updateShortcut(Context paramContext, final CustomShortcutInfoCompat shortcutInfoCompat, String paramString) {
    final ShortcutManager context;
    if (Build.VERSION.SDK_INT >= 25) {
      shortcutManager = (ShortcutManager)paramContext.getSystemService(ShortcutManager.class);
      return (shortcutManager == null) ? false : shortcutManager.updateShortcuts(Collections.singletonList(shortcutInfoCompat.toShortcutInfo()));
    } 
    deleteShortcut((Context)shortcutManager, shortcutInfoCompat.getShortLabel().toString(), paramString);
    AppbrandContext.mainHandler.postDelayed(new Runnable() {
          public final void run() {
            CustomShortcutManagerCompat.requestPinShortcut(context, shortcutInfoCompat, null);
          }
        }500L);
    return false;
  }
  
  public static class ShortcutStatus {
    public boolean exist;
    
    public boolean needUpdate;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\shortcut\adapter\CustomShortcutManagerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */