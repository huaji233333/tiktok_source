package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.b;
import com.tt.miniapp.process.extra.CrossProcessOperatorScheduler;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.ModeManager;
import com.tt.miniapphost.NativeModule;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiMakePhoneCallCtrl extends b {
  public ApiMakePhoneCallCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    NativeModule nativeModule = ModeManager.getInst().get("makePhoneCall");
    if (nativeModule == null) {
      callbackAppUnSupportFeature();
      return;
    } 
    try {
      CrossProcessOperatorScheduler.nativeModuleInvoke(nativeModule, this.mArgs, new NativeModule.NativeModuleCallback<String>() {
            public void onNativeModuleCall(String param1String) {
              AppBrandLogger.d("tma_ApiMakePhoneCallCtrl", new Object[] { "ApiMakePhoneCallCtrl invoke ", param1String });
              JSONObject jSONObject = new JSONObject();
              try {
                jSONObject.put("errMsg", b.buildErrorMsg("makePhoneCall", param1String));
              } catch (JSONException jSONException) {
                AppBrandLogger.e("tma_ApiMakePhoneCallCtrl", new Object[] { "act", jSONException });
              } 
              ApiMakePhoneCallCtrl.this.doCallbackByApiHandler(jSONObject.toString());
            }
          });
      return;
    } catch (Exception exception) {
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "makePhoneCall";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiMakePhoneCallCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */