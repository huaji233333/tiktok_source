package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.b;
import com.tt.miniapp.adsite.AdSiteEventHelper;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.process.callback.IpcCallback;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiGetLocalPhoneNumberTokenCtrl extends b {
  public ApiGetLocalPhoneNumberTokenCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    if (AppbrandContext.getInst().getCurrentActivity() == null) {
      callbackFail("activity is null");
      AdSiteEventHelper.reportGetLocalPhoneNumberTokenEvent(false);
      return;
    } 
    HostProcessBridge.getLocalPhoneNumberToken(new IpcCallback() {
          public void onIpcCallback(CrossProcessDataEntity param1CrossProcessDataEntity) {
            try {
              JSONObject jSONObject1 = param1CrossProcessDataEntity.getJSONObject("jsonData");
              JSONObject jSONObject2 = new JSONObject();
              if (jSONObject1 == null) {
                ApiGetLocalPhoneNumberTokenCtrl.this.callbackFail("callback is null");
                AdSiteEventHelper.reportGetLocalPhoneNumberTokenEvent(false);
                return;
              } 
              jSONObject2.put("verifyToken", jSONObject1.optString("verifyToken"));
              jSONObject2.put("from", jSONObject1.optString("from"));
              int i = jSONObject1.optInt("code", -1);
              jSONObject2.put("code", i);
              if (i != 0) {
                ApiGetLocalPhoneNumberTokenCtrl.this.callbackFail("obtain phone token error", jSONObject2);
                AdSiteEventHelper.reportGetLocalPhoneNumberTokenEvent(false);
                return;
              } 
              ApiGetLocalPhoneNumberTokenCtrl.this.callbackOk(jSONObject2);
              AdSiteEventHelper.reportGetLocalPhoneNumberTokenEvent(true);
              return;
            } catch (JSONException jSONException) {
              AppBrandLogger.e("tma_ApiGetLocalPhoneNumberTokenCtrl", new Object[] { jSONException });
              ApiGetLocalPhoneNumberTokenCtrl.this.callbackFail((Throwable)jSONException);
              AdSiteEventHelper.reportGetLocalPhoneNumberTokenEvent(false);
              return;
            } 
          }
          
          public void onIpcConnectError() {
            ApiGetLocalPhoneNumberTokenCtrl.this.callbackFail("ipc fail");
          }
        });
  }
  
  public String getActionName() {
    return "getLocalPhoneNumberToken";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiGetLocalPhoneNumberTokenCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */