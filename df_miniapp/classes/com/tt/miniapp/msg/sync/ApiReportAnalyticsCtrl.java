package com.tt.miniapp.msg.sync;

import android.text.TextUtils;
import com.tt.frontendapiinterface.a;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.process.HostProcessBridge;
import org.json.JSONObject;

public class ApiReportAnalyticsCtrl extends SyncMsgCtrl {
  private String functionName;
  
  public ApiReportAnalyticsCtrl(String paramString1, String paramString2) {
    super(paramString2);
    this.functionName = paramString1;
  }
  
  public String act() {
    try {
      JSONObject jSONObject1 = new JSONObject(this.mParams);
      String str1 = jSONObject1.optString("event");
      if (TextUtils.isEmpty(str1))
        return makeFailMsg(a.b("event")); 
      if (str1.length() >= 85)
        return makeFailMsg("event.length must be less than 85"); 
      JSONObject jSONObject2 = jSONObject1.optJSONObject("value");
      String str2 = jSONObject2.toString();
      if (TextUtils.isEmpty(str2))
        return makeFailMsg(a.b("value")); 
      if (str2.length() >= 294912)
        return makeFailMsg("data.length must be less than 294912"); 
      int i = jSONObject1.optInt("type", 0);
      AppBrandLogger.d("tma_reportAnalytics", new Object[] { "event=", str1, "&value=", str2 });
      if (i == 1 && (AppbrandApplication.getInst().getAppInfo()).innertype == 1) {
        HostProcessBridge.logEvent(str1, jSONObject2);
      } else {
        String str;
        jSONObject2.put("mp_id", (AppbrandApplication.getInst().getAppInfo()).appId);
        jSONObject2.put("mp_name", (AppbrandApplication.getInst().getAppInfo()).appName);
        if (AppbrandApplication.getInst().getAppInfo().isGame()) {
          str = "micro_game";
        } else {
          str = "micro_app";
        } 
        jSONObject2.put("_param_for_special", str);
        jSONObject2.put("cp_event_key_name", str1);
        HostProcessBridge.logEvent("mp_cp_event_log", jSONObject2);
      } 
      AppBrandLogger.d("tma_reportAnalytics", new Object[] { "event", str1, "params", jSONObject2.toString() });
      return makeOkMsg();
    } catch (Exception exception) {
      AppBrandLogger.e("tma_reportAnalytics", new Object[] { exception });
      return makeFailMsg(exception);
    } 
  }
  
  public String getName() {
    return this.functionName;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\sync\ApiReportAnalyticsCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */