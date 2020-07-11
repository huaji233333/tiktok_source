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

public class ApiGetLocalPhoneNumberCtrl extends b {
  public ApiGetLocalPhoneNumberCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    if (AppbrandContext.getInst().getCurrentActivity() == null) {
      callbackFail("activity is null");
      AdSiteEventHelper.reportGetLocalPhoneNumberEvent(false);
      return;
    } 
    HostProcessBridge.getLocalPhoneNumber(new IpcCallback() {
          public void onIpcCallback(CrossProcessDataEntity param1CrossProcessDataEntity) {
            try {
              JSONObject jSONObject1 = param1CrossProcessDataEntity.getJSONObject("jsonData");
              JSONObject jSONObject2 = new JSONObject();
              if (jSONObject1 == null) {
                ApiGetLocalPhoneNumberCtrl.this.callbackFail("callback is null");
                AdSiteEventHelper.reportGetLocalPhoneNumberEvent(false);
                return;
              } 
              jSONObject2.put("phoneMask", jSONObject1.optString("phoneMask"));
              int i = jSONObject1.optInt("code", -1);
              jSONObject2.put("code", i);
              if (i != 0) {
                ApiGetLocalPhoneNumberCtrl.this.callbackFail("obtain phone mask error", jSONObject2);
                AdSiteEventHelper.reportGetLocalPhoneNumberEvent(false);
                return;
              } 
              ApiGetLocalPhoneNumberCtrl.this.callbackOk(jSONObject2);
              AdSiteEventHelper.reportGetLocalPhoneNumberEvent(true);
              return;
            } catch (JSONException jSONException) {
              AppBrandLogger.e("tma_ApiGetLocalPhoneNumberCtrl", new Object[] { jSONException });
              ApiGetLocalPhoneNumberCtrl.this.callbackFail((Throwable)jSONException);
              AdSiteEventHelper.reportGetLocalPhoneNumberEvent(false);
              return;
            } 
          }
          
          public void onIpcConnectError() {
            ApiGetLocalPhoneNumberCtrl.this.callbackFail("ipc fail");
          }
        });
  }
  
  public String getActionName() {
    return "getLocalPhoneNumber";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiGetLocalPhoneNumberCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */