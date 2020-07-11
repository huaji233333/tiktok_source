package com.tt.miniapp.msg;

import android.text.TextUtils;
import com.tt.frontendapiinterface.b;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.process.callback.IpcCallback;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiDealUserRelationCtrl extends b {
  public ApiDealUserRelationCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    if (!HostProcessBridge.isDataHandlerExist("handleUserRelation")) {
      callbackAppUnSupportFeature();
      return;
    } 
    try {
      JSONObject jSONObject = new JSONObject(this.mArgs);
      String str1 = jSONObject.optString("action");
      String str2 = jSONObject.optString("userId");
      if (TextUtils.isEmpty(str2) || TextUtils.equals(str2, "null")) {
        callbackFail("userId is invalid");
        return;
      } 
      if (!TextUtils.equals(str1, "follow") && !TextUtils.equals(str1, "unfollow")) {
        callbackFail("action is invalid");
        return;
      } 
      HostProcessBridge.handleUserRelation(this.mArgs, (AppbrandApplication.getInst().getAppInfo()).ttId, new IpcCallback() {
            public void onIpcCallback(CrossProcessDataEntity param1CrossProcessDataEntity) {
              JSONObject jSONObject = new JSONObject();
              String str = "fail";
              if (param1CrossProcessDataEntity != null) {
                try {
                  str = param1CrossProcessDataEntity.getString("userRelationHandleResult", "fail");
                  jSONObject.put("errMsg", b.buildErrorMsg("dealUserRelation", str));
                } catch (JSONException jSONException) {
                  AppBrandLogger.e("tma_DealUserRelationCtrl", new Object[] { jSONException });
                } 
                ApiDealUserRelationCtrl.this.doCallbackByApiHandler(jSONObject.toString());
                finishListenIpcCallback();
                return;
              } 
              jSONObject.put("errMsg", b.buildErrorMsg("dealUserRelation", str));
            }
            
            public void onIpcConnectError() {
              ApiDealUserRelationCtrl.this.callbackFail("ipc fail");
            }
          });
      return;
    } catch (JSONException jSONException) {
      callbackFail((Throwable)jSONException);
      AppBrandLogger.e("tma_DealUserRelationCtrl", new Object[] { jSONException });
      return;
    } 
  }
  
  public String getActionName() {
    return "dealUserRelation";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiDealUserRelationCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */