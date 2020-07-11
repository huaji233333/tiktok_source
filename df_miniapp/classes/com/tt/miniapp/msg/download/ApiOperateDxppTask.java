package com.tt.miniapp.msg.download;

import android.text.TextUtils;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.e.e;
import org.json.JSONArray;
import org.json.JSONObject;

public class ApiOperateDxppTask extends b implements TmaDownloadParams {
  private DxppCallback mCallback = new DxppCallback() {
      public void onFail(Exception param1Exception) {
        ApiOperateDxppTask.this.callbackFail(param1Exception);
      }
      
      public void onFail(String param1String) {
        ApiOperateDxppTask.this.callbackFail(param1String);
      }
      
      public void onSuccess(JSONObject param1JSONObject) {
        ApiOperateDxppTask.this.callbackOk(param1JSONObject);
      }
    };
  
  public ApiOperateDxppTask(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    if (!HostDependManager.getInst().supportDxpp()) {
      callbackAppUnSupportFeature();
      return;
    } 
    try {
      JSONObject jSONObject = new JSONObject(this.mArgs);
      String str2 = jSONObject.optString("operation");
      String str3 = jSONObject.optString("guid");
      long l = jSONObject.optLong("id");
      String str4 = jSONObject.optString("app_name");
      String str5 = jSONObject.optString("pkg_name");
      String str6 = jSONObject.optString("download_url");
      JSONArray jSONArray = jSONObject.optJSONArray("backup_urls");
      String str7 = jSONObject.optString("icon");
      String str1 = jSONObject.optString("extra");
      boolean bool = TextUtils.isEmpty(str1);
      if (!bool)
        try {
          JSONObject jSONObject1 = new JSONObject(str1);
          if (TextUtils.isEmpty(str3)) {
            callbackFail(a.b("guid"));
            return;
          } 
          if (TextUtils.isEmpty(str4)) {
            callbackFail(a.b("app_name"));
            return;
          } 
          if (TextUtils.isEmpty(str5)) {
            callbackFail(a.b("pkg_name"));
            return;
          } 
          if (TextUtils.isEmpty(str6)) {
            callbackFail(a.b("download_url"));
            return;
          } 
          HostDependManager.getInst().operateDxppTask(str2, str3, l, str4, str5, str6, jSONArray, str7, jSONObject1, true, this.mCallback);
          return;
        } finally {} 
      str1 = null;
      if (TextUtils.isEmpty(str3)) {
        callbackFail(a.b("guid"));
        return;
      } 
      if (TextUtils.isEmpty(str4)) {
        callbackFail(a.b("app_name"));
        return;
      } 
      if (TextUtils.isEmpty(str5)) {
        callbackFail(a.b("pkg_name"));
        return;
      } 
      if (TextUtils.isEmpty(str6)) {
        callbackFail(a.b("download_url"));
        return;
      } 
      HostDependManager.getInst().operateDxppTask(str2, str3, l, str4, str5, str6, jSONArray, str7, (JSONObject)str1, true, this.mCallback);
      return;
    } catch (Exception exception) {
      callbackFail(exception);
      AppBrandLogger.e("tma_ApiOperateDxppTask", new Object[] { exception });
      return;
    } 
  }
  
  public String getActionName() {
    return "operateDxppTask";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\download\ApiOperateDxppTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */