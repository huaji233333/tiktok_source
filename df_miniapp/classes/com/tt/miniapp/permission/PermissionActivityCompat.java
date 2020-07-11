package com.tt.miniapp.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;

public class PermissionActivityCompat {
  public static int checkSelfPermission(Context paramContext, String paramString) {
    if (paramString != null)
      try {
        return paramContext.checkPermission(paramString, Process.myPid(), Process.myUid());
      } finally {
        paramContext = null;
      }  
    throw new IllegalArgumentException("permission is null");
  }
  
  public static void requestPermissions(final Activity activity, final String[] permissions, final int requestCode) {
    if (Build.VERSION.SDK_INT >= 23) {
      ActivityCompatApi23.requestPermissions(activity, permissions, requestCode);
      return;
    } 
    if (activity instanceof OnRequestPermissionsResultCallback)
      (new Handler(Looper.getMainLooper())).post(new Runnable() {
            public final void run() {
              int[] arrayOfInt = new int[permissions.length];
              PackageManager packageManager = activity.getPackageManager();
              String str = activity.getPackageName();
              int j = permissions.length;
              for (int i = 0; i < j; i++)
                arrayOfInt[i] = packageManager.checkPermission(permissions[i], str); 
              ((PermissionActivityCompat.OnRequestPermissionsResultCallback)activity).onRequestPermissionsResult(requestCode, permissions, arrayOfInt);
            }
          }); 
  }
  
  public static boolean shouldShowRequestPermissionRationale(Activity paramActivity, String paramString) {
    return (Build.VERSION.SDK_INT >= 23) ? ActivityCompatApi23.shouldShowRequestPermissionRationale(paramActivity, paramString) : false;
  }
  
  public static interface OnRequestPermissionsResultCallback {
    void onRequestPermissionsResult(int param1Int, String[] param1ArrayOfString, int[] param1ArrayOfint);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\permission\PermissionActivityCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */