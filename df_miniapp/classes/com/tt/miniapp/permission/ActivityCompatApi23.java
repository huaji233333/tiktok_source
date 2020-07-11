package com.tt.miniapp.permission;

import android.app.Activity;

public class ActivityCompatApi23 {
  public static void requestPermissions(Activity paramActivity, String[] paramArrayOfString, int paramInt) {
    if (paramActivity instanceof RequestPermissionsRequestCodeValidator)
      ((RequestPermissionsRequestCodeValidator)paramActivity).validateRequestPermissionsRequestCode(paramInt); 
    paramActivity.requestPermissions(paramArrayOfString, paramInt);
  }
  
  public static boolean shouldShowRequestPermissionRationale(Activity paramActivity, String paramString) {
    return paramActivity.shouldShowRequestPermissionRationale(paramString);
  }
  
  public static interface RequestPermissionsRequestCodeValidator {
    void validateRequestPermissionsRequestCode(int param1Int);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\permission\ActivityCompatApi23.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */