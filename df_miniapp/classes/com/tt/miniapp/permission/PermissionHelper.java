package com.tt.miniapp.permission;

import com.tt.miniapp.event.Event;

public class PermissionHelper {
  public static void reportAuthFailResult(String paramString1, String paramString2) {
    Event.builder("mp_auth_alert_result").kv("auth_type", paramString1).kv("fail_type", paramString2).kv("result", "fail").flush();
  }
  
  public static void reportAuthSuccessResult(String paramString) {
    Event.builder("mp_auth_alert_result").kv("auth_type", paramString).kv("result", "success").flush();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\permission\PermissionHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */