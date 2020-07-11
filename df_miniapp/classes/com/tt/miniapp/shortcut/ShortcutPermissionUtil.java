package com.tt.miniapp.shortcut;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import com.tt.miniapphost.AppBrandLogger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShortcutPermissionUtil {
  private static final String MARK = Build.MANUFACTURER.toLowerCase();
  
  public static String getPhoneBrand() {
    return MARK.contains("huawei") ? "huawei" : (MARK.contains("xiaomi") ? "xiaomi" : (MARK.contains("oppo") ? "oppo" : (MARK.contains("vivo") ? "vivo" : "other")));
  }
  
  private static String getSystemProperty(String paramString) {
    Exception exception = null;
    String str = null;
    try {
      Runtime runtime = Runtime.getRuntime();
      StringBuilder stringBuilder = new StringBuilder("getprop ");
      stringBuilder.append(paramString);
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runtime.exec(stringBuilder.toString()).getInputStream()), 1024);
    } catch (IOException iOException1) {
    
    } finally {
      if (iOException != null)
        try {
          iOException.close();
        } catch (IOException iOException1) {} 
    } 
    if (paramString != null)
      try {
        paramString.close();
      } catch (IOException iOException1) {} 
    return "";
  }
  
  public static void goSettingPage(Context paramContext) {
    Intent intent;
    if (MARK.contains("huawei")) {
      intent = openHWSetting(paramContext);
    } else if (MARK.contains("xiaomi")) {
      intent = openMISetting(paramContext);
    } else if (MARK.contains("oppo")) {
      intent = openOppoSetting(paramContext);
    } else if (MARK.contains("vivo")) {
      intent = openVivoSetting(paramContext);
    } else if (MARK.contains("meizu")) {
      intent = openMZSetting(paramContext);
    } else {
      intent = openDefaultSetting(paramContext);
    } 
    if (intent != null)
      try {
        intent.putExtra("start_only_for_android", true);
        paramContext.startActivity(intent);
        return;
      } catch (Exception exception) {
        AppBrandLogger.e("ShortcutPermissionUtil", new Object[] { exception });
        paramContext.startActivity(openDefaultSetting(paramContext));
        return;
      }  
    paramContext.startActivity((Intent)exception);
  }
  
  private static boolean hasActivity(Context paramContext, Intent paramIntent) {
    return (paramContext.getPackageManager().queryIntentActivities(paramIntent, 65536).size() > 0);
  }
  
  private static Intent openDefaultSetting(Context paramContext) {
    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
    intent.setData(Uri.fromParts("package", paramContext.getPackageName(), null));
    return intent;
  }
  
  private static Intent openHWSetting(Context paramContext) {
    Intent intent = new Intent();
    intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity"));
    return intent;
  }
  
  private static Intent openMISetting(Context paramContext) {
    Intent intent;
    String str = getSystemProperty("ro.miui.ui.version.name");
    if (TextUtils.isEmpty(str) || str.contains("V7") || str.contains("V8")) {
      intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
      intent.putExtra("extra_pkgname", paramContext.getPackageName());
      return intent;
    } 
    if (TextUtils.isEmpty((CharSequence)intent) || intent.contains("V9") || intent.contains("V10") || intent.contains("V11")) {
      intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
      intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity"));
      intent.putExtra("extra_pkgname", paramContext.getPackageName());
      return intent;
    } 
    return openDefaultSetting(paramContext);
  }
  
  private static Intent openMZSetting(Context paramContext) {
    if (Build.VERSION.SDK_INT >= 24)
      return openDefaultSetting(paramContext); 
    Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
    intent.putExtra("packageName", paramContext.getPackageName());
    intent.setComponent(new ComponentName("com.meizu.safe", "com.meizu.safe.security.AppSecActivity"));
    return intent;
  }
  
  private static Intent openOppoSetting(Context paramContext) {
    Intent intent = new Intent();
    intent.putExtra("packageName", paramContext.getPackageName());
    intent.setClassName("com.oppo.launcher", "com.oppo.launcher.shortcut.ShortcutSettingsActivity");
    if (hasActivity(paramContext, intent))
      return intent; 
    intent.setComponent(new ComponentName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity"));
    return intent;
  }
  
  private static Intent openVivoSetting(Context paramContext) {
    Intent intent = new Intent();
    intent.putExtra("packagename", paramContext.getPackageName());
    intent.setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity"));
    if (hasActivity(paramContext, intent))
      return intent; 
    intent.setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.safeguard.SoftPermissionDetailActivity"));
    return intent;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\shortcut\ShortcutPermissionUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */