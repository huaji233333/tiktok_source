package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.b;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.e.e;
import com.tt.option.z.b;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiCheckSessionCtrl extends b {
  public ApiCheckSessionCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    HostDependManager.getInst().checkSession((AppbrandApplication.getInst().getAppInfo()).appId, new b.a() {
          public void onSessionChecked(boolean param1Boolean, String param1String) {
            if (param1Boolean) {
              JSONObject jSONObject = new JSONObject();
              try {
                jSONObject.put("session", param1String);
                ApiCheckSessionCtrl.this.callbackOk(jSONObject);
                return;
              } catch (JSONException jSONException) {
                ApiCheckSessionCtrl.this.callbackFail((Throwable)jSONException);
                return;
              } 
            } 
            ApiCheckSessionCtrl.this.callbackFail("session invalid");
          }
        });
  }
  
  public String getActionName() {
    return "checkSession";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiCheckSessionCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */