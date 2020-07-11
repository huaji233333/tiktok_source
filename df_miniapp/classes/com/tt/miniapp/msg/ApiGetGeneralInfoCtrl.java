package com.tt.miniapp.msg;

import android.content.Context;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.manager.basebundle.BaseBundleManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.option.e.e;
import org.json.JSONObject;

public class ApiGetGeneralInfoCtrl extends b {
  public ApiGetGeneralInfoCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      JSONObject jSONObject = HostProcessBridge.getNetCommonParams();
      if (jSONObject != null) {
        jSONObject.put("tma_jssdk_version", BaseBundleManager.getInst().getSdkCurrentVersionStr((Context)AppbrandContext.getInst().getApplicationContext()));
        JSONObject jSONObject1 = new JSONObject();
        jSONObject1.put("info", jSONObject);
        callbackOk(jSONObject1);
        return;
      } 
      callbackFail("get net common params fail");
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("tma_ApiGetGeneralInfoCtrl", new Object[] { exception });
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "getGeneralInfo";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiGetGeneralInfoCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */