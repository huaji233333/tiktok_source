package com.tt.miniapp.util;

import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import org.json.JSONException;
import org.json.JSONObject;

public class JsRuntimeErrorReporter {
  private int mCount;
  
  public static JsRuntimeErrorReporter getInstance() {
    return Holder.mInstance;
  }
  
  public void report(String paramString1, String paramString2) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("message", paramString1);
      jSONObject.put("errorType", paramString2);
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable("JsRuntimeErrorReporter", "", (Throwable)jSONException);
    } 
    report(jSONObject);
  }
  
  public void report(JSONObject paramJSONObject) {
    if (paramJSONObject != null) {
      String str1 = paramJSONObject.optString("message");
      String str2 = paramJSONObject.optString("stack");
      String str3 = paramJSONObject.optString("errorType");
      String str4 = paramJSONObject.optString("extend");
      int i = -1;
      StringBuilder stringBuilder = new StringBuilder("js runtime error, \nerrorType:");
      stringBuilder.append(str3);
      stringBuilder.append("\nmessage:");
      stringBuilder.append(str1);
      stringBuilder.append("\nstack:");
      stringBuilder.append(str2);
      stringBuilder.append("\nextend:");
      stringBuilder.append(str4);
      AppBrandLogger.e("JsRuntimeErrorReporter", new Object[] { stringBuilder.toString() });
      AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
      if (appInfoEntity != null)
        i = appInfoEntity.type; 
      int j = this.mCount;
      this.mCount = j + 1;
      if (j <= 0) {
        AppBrandMonitor.statusRate("mp_js_runtime_error", i, paramJSONObject);
        AppBrandMonitor.flush();
      } 
    } 
  }
  
  static class Holder {
    public static JsRuntimeErrorReporter mInstance = new JsRuntimeErrorReporter();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\JsRuntimeErrorReporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */