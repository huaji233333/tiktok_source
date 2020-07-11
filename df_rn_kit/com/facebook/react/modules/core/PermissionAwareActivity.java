package com.facebook.react.modules.core;

public interface PermissionAwareActivity {
  int checkPermission(String paramString, int paramInt1, int paramInt2);
  
  int checkSelfPermission(String paramString);
  
  void requestPermissions(String[] paramArrayOfString, int paramInt, PermissionListener paramPermissionListener);
  
  boolean shouldShowRequestPermissionRationale(String paramString);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\core\PermissionAwareActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */