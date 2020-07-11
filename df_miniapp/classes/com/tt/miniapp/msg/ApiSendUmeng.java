package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.b;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.EventHelper;
import com.tt.option.e.e;
import org.json.JSONObject;

public class ApiSendUmeng extends b {
  public ApiSendUmeng(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      JSONObject jSONObject = new JSONObject(this.mArgs);
      String str1 = jSONObject.optString("tag");
      String str2 = jSONObject.optString("label");
      long l1 = jSONObject.optLong("value");
      long l2 = jSONObject.optLong("ext_value");
      jSONObject = jSONObject.optJSONObject("ext_json");
      AppBrandLogger.d("tma_SendUmengEventV1", new Object[] { 
            "category", "umeng", "tag", str1, "label", str2, "value", Long.valueOf(l1), "ext_value", Long.valueOf(l2), 
            "ext_json", jSONObject });
      EventHelper.sendLogV1("umeng", str1, str2, l1, l2, jSONObject);
      callbackOk();
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("tma_SendUmengEventV1", new Object[] { exception });
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "sendUmengEventV1";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiSendUmeng.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */