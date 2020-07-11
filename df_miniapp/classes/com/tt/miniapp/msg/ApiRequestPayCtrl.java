package com.tt.miniapp.msg;

import android.app.Activity;
import com.tt.frontendapiinterface.ApiCallResult;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapp.notification.MiniAppNotificationManager;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.miniapphost.util.DebugUtil;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiRequestPayCtrl extends b {
  private MiniAppNotificationManager.NotificationEntity mNotificationEntity;
  
  public ApiRequestPayCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    JSONException jSONException1;
    IPayExecutor iPayExecutor = HostDependManager.getInst().getPayExecutor((Activity)AppbrandContext.getInst().getCurrentActivity());
    if (iPayExecutor == null) {
      callbackAppUnSupportFeature();
      return;
    } 
    try {
      jSONException1 = (JSONException)new JSONObject(this.mArgs);
      try {
        JSONObject jSONObject = jSONException1.optJSONObject("data");
        if (jSONObject != null) {
          JSONObject jSONObject1 = (JSONObject)jSONException1;
          if (!jSONObject.has("tt_pay_business")) {
            jSONObject.put("tt_pay_business", "littleapp");
            jSONObject1 = (JSONObject)jSONException1;
          } 
        } else {
          DebugUtil.outputError("ApiRequestPayCtrl", new Object[] { "data is null" });
          JSONObject jSONObject1 = (JSONObject)jSONException1;
        } 
      } catch (JSONException null) {}
    } catch (JSONException jSONException2) {
      jSONException1 = null;
    } 
    DebugUtil.outputError("ApiRequestPayCtrl", new Object[] { "act", jSONException2 });
    jSONException2 = jSONException1;
  }
  
  public String getActionName() {
    return "requestPayment";
  }
  
  public void onApiHandlerCallback() {
    MiniAppNotificationManager.cancelPayNotification(this.mNotificationEntity);
  }
  
  public static interface IPayExecutor {
    void doPay(String param1String, ApiRequestPayCtrl.PayCallback param1PayCallback);
  }
  
  public static interface PayCallback {
    void onResult(ApiCallResult param1ApiCallResult);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiRequestPayCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */