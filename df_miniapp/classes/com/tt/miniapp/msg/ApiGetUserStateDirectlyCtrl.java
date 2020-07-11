package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.b;
import com.tt.miniapp.manager.UserInfoManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiGetUserStateDirectlyCtrl extends b {
  public ApiGetUserStateDirectlyCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    if (!HostProcessBridge.isDataHandlerExist("getUserInfo")) {
      callbackAppUnSupportFeature();
      return;
    } 
    try {
      CrossProcessDataEntity crossProcessDataEntity = HostProcessBridge.getUserInfo();
      if (crossProcessDataEntity != null) {
        UserInfoManager.UserInfo userInfo = new UserInfoManager.UserInfo(crossProcessDataEntity);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("uid", userInfo.userId);
        jSONObject.put("isLogin", userInfo.isLogin);
        callbackOk(jSONObject);
        return;
      } 
      callbackFail("get user info fail");
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.e("ApiGetUserStateDirectlyCtrl", new Object[] { jSONException });
      callbackFail((Throwable)jSONException);
      return;
    } 
  }
  
  public String getActionName() {
    return "getUserStateDirectly";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiGetUserStateDirectlyCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */