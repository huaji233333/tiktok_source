package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.b;
import com.tt.miniapp.event.Event;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;
import org.json.JSONObject;

public class ApiSystemLogCtrl extends b {
  public ApiSystemLogCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      JSONObject jSONObject = new JSONObject(this.mArgs);
      String str = jSONObject.optString("tag");
      jSONObject = jSONObject.optJSONObject("data");
      StringBuilder stringBuilder = new StringBuilder("event");
      stringBuilder.append(str);
      stringBuilder.append("data");
      stringBuilder.append(jSONObject);
      AppBrandLogger.d("tma_SystemLogCtrl", new Object[] { stringBuilder.toString() });
      Event.builder(str).addKVJsonObject(jSONObject).flush();
      callbackOk();
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("tma_SystemLogCtrl", new Object[] { exception });
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "systemLog";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiSystemLogCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */