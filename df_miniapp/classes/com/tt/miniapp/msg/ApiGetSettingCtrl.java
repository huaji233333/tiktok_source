package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.b;
import com.tt.miniapp.permission.BrandPermissionUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiGetSettingCtrl extends b {
  public ApiGetSettingCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public static void getLocalScope(JSONObject paramJSONObject) throws JSONException {
    for (BrandPermissionUtils.BrandPermission brandPermission : BrandPermissionUtils.BrandPermission.getUserDefinablePermissionList()) {
      if (BrandPermissionUtils.hasRequestPermission(brandPermission.getPermissionType())) {
        switch (brandPermission.getPermissionType()) {
          default:
            continue;
          case 17:
            paramJSONObject.put("scope.album", BrandPermissionUtils.isGranted(brandPermission.getPermissionType()));
            continue;
          case 15:
            paramJSONObject.put("scope.address", BrandPermissionUtils.isGranted(brandPermission.getPermissionType()));
            continue;
          case 14:
            paramJSONObject.put("scope.camera", BrandPermissionUtils.isGranted(brandPermission.getPermissionType()));
            continue;
          case 13:
            paramJSONObject.put("scope.record", BrandPermissionUtils.isGranted(brandPermission.getPermissionType()));
            continue;
          case 12:
            paramJSONObject.put("scope.userLocation", BrandPermissionUtils.isGranted(brandPermission.getPermissionType()));
            continue;
          case 11:
            break;
        } 
        paramJSONObject.put("scope.userInfo", BrandPermissionUtils.isGranted(brandPermission.getPermissionType()));
      } 
    } 
    if (AppbrandApplication.getInst().getAppInfo().isGame())
      paramJSONObject.put("scope.screenRecord", BrandPermissionUtils.isGranted(18, true)); 
  }
  
  public void act() {
    JSONObject jSONObject = new JSONObject();
    try {
      HostDependManager.getInst().getLocalScope(jSONObject);
      JSONObject jSONObject1 = new JSONObject();
      jSONObject1.put("authSetting", jSONObject);
      callbackOk(jSONObject1);
      return;
    } catch (JSONException jSONException) {
      callbackFail((Throwable)jSONException);
      AppBrandLogger.e("tma_ApiGetSettingCtrl", new Object[] { jSONException });
      return;
    } 
  }
  
  public String getActionName() {
    return "getSetting";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiGetSettingCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */