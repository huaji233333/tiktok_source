package com.tt.miniapphost.permission;

import java.util.LinkedHashMap;

public interface IPermissionsRequestCallback {
  void onDenied(LinkedHashMap<Integer, String> paramLinkedHashMap);
  
  void onGranted(LinkedHashMap<Integer, String> paramLinkedHashMap);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\permission\IPermissionsRequestCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */