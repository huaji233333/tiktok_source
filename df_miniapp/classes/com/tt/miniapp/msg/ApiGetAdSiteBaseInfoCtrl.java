package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.b;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.jsbridge.ApiPermissionManager;
import com.tt.miniapp.manager.UserInfoManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.IActivityProxy;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.entity.InitParamsEntity;
import com.tt.option.e.e;
import com.tt.option.q.d;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiGetAdSiteBaseInfoCtrl extends b {
  public static int sPageType = 6;
  
  public ApiGetAdSiteBaseInfoCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    if (ApiPermissionManager.interceptAdApi(getActionName(), this.mCallBackId, this.mApiHandlerCallback))
      return; 
    long l2 = 0L;
    JSONObject jSONObject1 = new JSONObject();
    JSONObject jSONObject2 = new JSONObject();
    JSONObject jSONObject3 = new JSONObject();
    JSONObject jSONObject4 = AppbrandApplicationImpl.getInst().getAppInfo().getAdParams();
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    long l1 = l2;
    if (miniappHostBase != null) {
      IActivityProxy iActivityProxy = miniappHostBase.getActivityProxy();
      l1 = l2;
      if (iActivityProxy != null)
        l1 = iActivityProxy.getLaunchDuration(); 
    } 
    try {
      jSONObject2.put("page_type", sPageType);
      InitParamsEntity initParamsEntity = AppbrandContext.getInst().getInitParams();
      String str = "";
      if (initParamsEntity != null)
        str = initParamsEntity.getAppId(); 
      jSONObject2.put("app_id", str);
      jSONObject2.put("os", "android");
      jSONObject2.put("m_app_id", (AppbrandApplicationImpl.getInst().getAppInfo()).appId);
      jSONObject2.put("load_duration", l1);
      UserInfoManager.UserInfo userInfo = UserInfoManager.getHostClientUserInfo();
      jSONObject3.put("user_id", userInfo.userId);
      jSONObject3.put("device_id", d.a());
      jSONObject3.put("is_login", userInfo.isLogin);
      jSONObject1.put("appInfo", jSONObject2);
      jSONObject1.put("userInfo", jSONObject3);
      jSONObject1.put("adInfo", jSONObject4);
    } catch (JSONException jSONException) {
      AppBrandLogger.e("ApiGetAdSiteBaseInfoCtrl", new Object[] { jSONException });
    } 
    AppBrandLogger.d("ApiGetAdSiteBaseInfoCtrl", new Object[] { jSONObject1 });
    callbackOk(jSONObject1);
  }
  
  public String getActionName() {
    return "getAdSiteBaseInfo";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiGetAdSiteBaseInfoCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */