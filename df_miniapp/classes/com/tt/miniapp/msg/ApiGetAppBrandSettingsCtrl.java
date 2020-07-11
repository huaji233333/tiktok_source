package com.tt.miniapp.msg;

import android.content.Context;
import com.tt.frontendapiinterface.a;
import com.tt.miniapp.msg.sync.SyncMsgCtrl;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapphost.AppbrandContext;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiGetAppBrandSettingsCtrl extends SyncMsgCtrl {
  public ApiGetAppBrandSettingsCtrl(String paramString) {
    super(paramString);
  }
  
  public String act() {
    try {
      String str = (new JSONObject(this.mParams)).optJSONArray("fields").optString(0);
      JSONObject jSONObject2 = SettingsDAO.getSettingsJSONObject((Context)AppbrandContext.getInst().getApplicationContext());
      if (jSONObject2 == null)
        return makeFailMsg(a.c("settings")); 
      JSONObject jSONObject1 = jSONObject2.optJSONObject(str);
      if (jSONObject1 == null)
        return makeFailMsg(a.c("targetModule")); 
      jSONObject2 = new JSONObject();
      jSONObject2.put("data", jSONObject1.toString());
      return makeOkMsg(jSONObject2);
    } catch (JSONException jSONException) {
      return makeFailMsg((Throwable)jSONException);
    } 
  }
  
  public String getName() {
    return "getAppbrandSettingsSync";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiGetAppBrandSettingsCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */