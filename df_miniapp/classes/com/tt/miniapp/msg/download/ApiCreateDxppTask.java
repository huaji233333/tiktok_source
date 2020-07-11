package com.tt.miniapp.msg.download;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.permission.PermissionsManager;
import com.tt.miniapp.permission.PermissionsResultAction;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.JsonBuilder;
import com.tt.option.e.e;
import java.util.HashSet;
import org.json.JSONArray;
import org.json.JSONObject;

public class ApiCreateDxppTask extends b implements TmaDownloadParams {
  public String mAppName;
  
  public DxppCallback mCallback = new DxppCallback() {
      public void onFail(Exception param1Exception) {
        ApiCreateDxppTask.this.callbackFail(param1Exception);
      }
      
      public void onFail(String param1String) {
        ApiCreateDxppTask.this.callbackFail(param1String);
      }
      
      public void onSuccess(JSONObject param1JSONObject) {
        ApiCreateDxppTask.this.callbackOk(param1JSONObject);
      }
    };
  
  public Context mContext;
  
  public String mDownloadUrl;
  
  public JSONObject mExtra;
  
  public String mGuid;
  
  public String mPackageName;
  
  public ApiCreateDxppTask(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    if (!HostDependManager.getInst().supportDxpp()) {
      callbackAppUnSupportFeature();
      return;
    } 
    this.mContext = (Context)AppbrandContext.getInst().getApplicationContext();
    try {
      JSONObject jSONObject = new JSONObject(this.mArgs);
      this.mGuid = jSONObject.optString("guid");
      final long id = jSONObject.optLong("id");
      this.mDownloadUrl = jSONObject.optString("download_url");
      this.mAppName = jSONObject.optString("app_name");
      this.mPackageName = jSONObject.optString("pkg_name");
      String str2 = jSONObject.optString("extra");
      final JSONArray backupUrls = jSONObject.optJSONArray("backup_urls");
      final String icon = jSONObject.optString("icon");
      boolean bool = TextUtils.isEmpty(str2);
      if (!bool)
        try {
          this.mExtra = new JSONObject(str2);
        } finally {} 
      if (TextUtils.isEmpty(this.mGuid)) {
        callbackFail(a.b("guid"));
        return;
      } 
      if (TextUtils.isEmpty(this.mAppName)) {
        callbackFail(a.b("app_name"));
        return;
      } 
      if (TextUtils.isEmpty(this.mPackageName)) {
        callbackFail(a.b("pkg_name"));
        return;
      } 
      if (TextUtils.isEmpty(this.mDownloadUrl)) {
        callbackFail(a.b("download_url"));
        return;
      } 
      if (PermissionsManager.getInstance().hasPermission(this.mContext, "android.permission.WRITE_EXTERNAL_STORAGE")) {
        HostDependManager.getInst().createDxppTask(this.mContext, this.mAppName, true, this.mPackageName, this.mGuid, this.mDownloadUrl, jSONArray, str1, this.mExtra, l, this.mCallback);
        return;
      } 
      HashSet<String> hashSet = new HashSet();
      hashSet.add("android.permission.WRITE_EXTERNAL_STORAGE");
      PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult((Activity)AppbrandContext.getInst().getCurrentActivity(), hashSet, new PermissionsResultAction() {
            public void onDenied(String param1String) {
              AppBrandLogger.d("tma_ApiCreateDxppTask", new Object[] { param1String });
              ApiCreateDxppTask.this.callbackFail("auth deny");
              JSONObject jSONObject = (new JsonBuilder()).put("guid", ApiCreateDxppTask.this.mGuid).put("state", "fail").put("data", (new JsonBuilder()).put("errCode", "0").put("errMsg", "auth deny").build()).build();
              AppbrandApplication.getInst().getJsBridge().sendMsgToJsCore("onDxppTaskStateChange", jSONObject.toString());
            }
            
            public void onGranted() {
              HostDependManager.getInst().createDxppTask(ApiCreateDxppTask.this.mContext, ApiCreateDxppTask.this.mAppName, true, ApiCreateDxppTask.this.mPackageName, ApiCreateDxppTask.this.mGuid, ApiCreateDxppTask.this.mDownloadUrl, backupUrls, icon, ApiCreateDxppTask.this.mExtra, id, ApiCreateDxppTask.this.mCallback);
            }
          });
      return;
    } catch (Exception exception) {
      callbackFail(exception);
      AppBrandLogger.e("tma_ApiCreateDxppTask", new Object[] { exception });
      JSONObject jSONObject = (new JsonBuilder()).put("guid", this.mGuid).put("state", "fail").put("data", (new JsonBuilder()).put("errCode", "0").put("errMsg", exception.getStackTrace()).build()).build();
      AppbrandApplication.getInst().getJsBridge().sendMsgToJsCore("onDxppTaskStateChange", jSONObject.toString());
      return;
    } 
  }
  
  public String getActionName() {
    return "createDxppTask";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\download\ApiCreateDxppTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */