package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.b;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiGetUseDurationCtrl extends b {
  public ApiGetUseDurationCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    long l = HostDependManager.getInst().getUseDuration();
    if (l != -1L)
      try {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("duration", l);
        callbackOk(jSONObject);
        return;
      } catch (JSONException jSONException) {
        callbackFail((Throwable)jSONException);
        return;
      }  
    callbackAppUnSupportFeature();
  }
  
  public String getActionName() {
    return "getUseDuration";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiGetUseDurationCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */