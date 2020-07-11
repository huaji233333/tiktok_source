package com.tt.miniapp.permission;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Process;
import com.tt.miniapp.util.DevicesUtil;
import com.tt.miniapphost.AppBrandLogger;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MIUIPermissionUtils {
  public static boolean checkPermission(Context paramContext, String paramString) {
    return (Build.VERSION.SDK_INT >= 19) ? checkPermissionKITKAT(paramContext, paramString) : checkPermissionDefault(paramContext, paramString);
  }
  
  private static boolean checkPermissionDefault(Context paramContext, String paramString) {
    return (paramContext.getPackageManager().checkPermission(paramString, paramContext.getPackageName()) == 0);
  }
  
  private static boolean checkPermissionKITKAT(Context paramContext, String paramString) {
    try {
      if ("android.permission.ACCESS_COARSE_LOCATION".equals(paramString)) {
        paramString = "COARSE_LOCATION";
      } else if ("android.permission.ACCESS_FINE_LOCATION".equals(paramString)) {
        paramString = "FINE_LOCATION";
      } else {
        paramString = paramString.replaceFirst("android.permission.", "");
      } 
      AppOpsManager appOpsManager = (AppOpsManager)paramContext.getSystemService("appops");
      PackageInfo packageInfo = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 1);
      StringBuilder stringBuilder = new StringBuilder("OP_");
      stringBuilder.append(paramString);
      Field field = AppOpsManager.class.getField(stringBuilder.toString());
      Method method = AppOpsManager.class.getDeclaredMethod("checkOp", new Class[] { int.class, int.class, String.class });
      method.setAccessible(true);
      int i = ((Integer)method.invoke(appOpsManager, new Object[] { Integer.valueOf(field.getInt(appOpsManager)), Integer.valueOf(packageInfo.applicationInfo.uid), packageInfo.packageName })).intValue();
      return (i != 2 && i != 1 && i != 4);
    } catch (Exception exception) {
      AppBrandLogger.e("MIUIPermissionUtils", new Object[] { "权限检查出错时默认返回有权限，异常代码：", exception });
      return true;
    } 
  }
  
  private static void resolveDefaultAll(Intent paramIntent) {
    paramIntent.setAction("android.intent.action.MAIN");
    paramIntent.setClassName("com.android.settings", "com.android.settings.ManageApplications");
  }
  
  private static void resolveDefaultDetail(Intent paramIntent, String paramString) {
    paramIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
    StringBuilder stringBuilder = new StringBuilder("package://");
    stringBuilder.append(paramString);
    paramIntent.setData(Uri.parse(stringBuilder.toString()));
    paramIntent.putExtra("package", paramString);
    paramIntent.setClassName("com.android.settings", "com.android.settings.applications.InstalledAppDetails");
  }
  
  private static boolean safeStartActivity(Intent paramIntent, Activity paramActivity) {
    try {
      paramActivity.startActivity(paramIntent);
      return true;
    } catch (ActivityNotFoundException activityNotFoundException) {
      return false;
    } 
  }
  
  public static void startPermissionManager(Activity paramActivity) {
    if (paramActivity == null)
      return; 
    Intent intent = new Intent();
    String str = paramActivity.getPackageName();
    boolean bool = false;
    if (DevicesUtil.isMiui()) {
      intent.setAction("miui.intent.action.APP_PERM_EDITOR");
      intent.putExtra("extra_pkgname", str);
      intent.putExtra("extra_package_uid", Process.myUid());
      bool = true;
    } else {
      resolveDefaultDetail(intent, str);
    } 
    if (!safeStartActivity(intent, paramActivity))
      if (bool) {
        resolveDefaultDetail(intent, str);
        if (!safeStartActivity(intent, paramActivity)) {
          resolveDefaultAll(intent);
          safeStartActivity(intent, paramActivity);
          return;
        } 
      } else {
        resolveDefaultAll(intent);
        safeStartActivity(intent, paramActivity);
      }  
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\permission\MIUIPermissionUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */