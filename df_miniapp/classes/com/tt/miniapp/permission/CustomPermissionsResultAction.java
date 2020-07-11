package com.tt.miniapp.permission;

import android.os.Looper;

public abstract class CustomPermissionsResultAction extends PermissionsResultAction {
  public CustomPermissionsResultAction() {}
  
  public CustomPermissionsResultAction(Looper paramLooper) {
    super(paramLooper);
  }
  
  public abstract void onCustomAction(String[] paramArrayOfString);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\permission\CustomPermissionsResultAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */