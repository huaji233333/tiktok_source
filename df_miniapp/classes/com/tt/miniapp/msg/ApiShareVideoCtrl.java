package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiShareVideoCtrl extends b {
  public ApiShareVideoCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      JSONObject jSONObject3 = new JSONObject(this.mArgs);
      String str = jSONObject3.optString("videoPath");
      JSONObject jSONObject2 = jSONObject3.optJSONObject("extra");
      JSONObject jSONObject1 = jSONObject2;
      if (jSONObject2 == null)
        jSONObject1 = new JSONObject(); 
      try {
        jSONObject1.put("videoPath", str);
        jSONObject3.put("extra", jSONObject1);
        jSONObject3.put("channel", "video");
      } catch (JSONException jSONException) {
        AppBrandLogger.e("ApiShareVideoCtrl", new Object[] { "act", jSONException });
      } 
      (new ApiShareMessageDirectlyNewCtrl(jSONObject3.toString(), this.mCallBackId, this.mApiHandlerCallback, getActionName())).doAct();
      return;
    } catch (JSONException jSONException) {
      callbackFail(a.a(this.mArgs));
      return;
    } 
  }
  
  public String getActionName() {
    return "shareVideo";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiShareVideoCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */