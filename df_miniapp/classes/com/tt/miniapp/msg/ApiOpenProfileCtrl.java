package com.tt.miniapp.msg;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.util.UserInfoUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.entity.InitParamsEntity;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiOpenProfileCtrl extends b {
  public ApiOpenProfileCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    final MiniappHostBase activity = AppbrandContext.getInst().getCurrentActivity();
    if (miniappHostBase == null) {
      AppBrandLogger.e("tma_ApiOpenProfileCtrl", new Object[] { "activity == nul" });
      callbackFail("activity is null");
      return;
    } 
    try {
      String str1;
      String str3 = (new JSONObject(this.mArgs)).optString("openid");
      AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
      String str2 = "";
      if (appInfoEntity != null) {
        str1 = appInfoEntity.appId;
      } else {
        str1 = "";
      } 
      InitParamsEntity initParamsEntity = AppbrandContext.getInst().getInitParams();
      if (initParamsEntity != null)
        str2 = initParamsEntity.getAppId(); 
      if (TextUtils.isEmpty(str3) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str1)) {
        str1 = a.a("session = %s aId = %s appId = %s", new Object[] { str3, str2, str1 });
        AppBrandLogger.e("tma_ApiOpenProfileCtrl", new Object[] { str1 });
        JSONObject jSONObject = new JSONObject();
        try {
          jSONObject.put("errMsg", str1);
          AppBrandMonitor.statusRate("mp_start_error", 2004, jSONObject);
        } catch (JSONException jSONException) {
          AppBrandLogger.e("tma_ApiOpenProfileCtrl", new Object[] { jSONException });
        } 
        callbackFail(str1);
        return;
      } 
      UserInfoUtil.getUid((Context)AppbrandContext.getInst().getCurrentActivity(), (String)jSONException, str1, str3, new UserInfoUtil.GetUidListener() {
            public void onFail(int param1Int, String param1String) {
              ApiOpenProfileCtrl.this.callbackFail(a.a("%s errorCode = %s", new Object[] { param1String, Integer.valueOf(param1Int) }));
            }
            
            public void onSuccess(String param1String) {
              if (!TextUtils.isEmpty(param1String)) {
                AppbrandApplicationImpl.getInst().setOpenedSchema(true);
                if (HostDependManager.getInst().openProfile(activity, param1String)) {
                  ApiOpenProfileCtrl.this.callbackOk();
                  return;
                } 
                ApiOpenProfileCtrl.this.callbackAppUnSupportFeature();
                return;
              } 
              AppBrandLogger.e("tma_ApiOpenProfileCtrl", new Object[] { "uid is ", param1String });
              ApiOpenProfileCtrl.this.callbackFail("requestResult is null");
            }
          });
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("tma_ApiOpenProfileCtrl", new Object[] { exception });
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "openUserProfile";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiOpenProfileCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */