package com.ss.android.ugc.aweme.miniapp.f;

import com.google.gson.g;
import com.google.gson.l;
import com.google.gson.o;
import com.google.gson.q;
import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.handler.ISyncHostDataHandler;
import org.json.JSONObject;

public final class a implements ISyncHostDataHandler {
  public final CrossProcessDataEntity action(CrossProcessDataEntity paramCrossProcessDataEntity) {
    JSONObject jSONObject;
    if (paramCrossProcessDataEntity == null)
      return null; 
    String str = paramCrossProcessDataEntity.getString("logEventVersion");
    if ("V3".equals(str)) {
      str = paramCrossProcessDataEntity.getString("logEventName");
      jSONObject = paramCrossProcessDataEntity.getJSONObject("logEventData");
      if (jSONObject != null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append("=============================\n");
        String str1 = stringBuilder.toString();
        String str2 = jSONObject.toString();
        o o = (new q()).a(str2).m();
        AppBrandLogger.i("AppLogHandler", new Object[] { str1, (new g()).b().d().a((l)o) });
      } 
      MiniAppService.inst().getMonitorDepend().b(str, jSONObject);
      return null;
    } 
    if ("V1".equals(str)) {
      str = jSONObject.getString("category");
      String str1 = jSONObject.getString("tag");
      String str2 = jSONObject.getString("label");
      long l1 = jSONObject.getLong("value");
      long l2 = jSONObject.getLong("ext_value");
      jSONObject = jSONObject.getJSONObject("ext_json");
      MiniAppService.inst().getMonitorDepend().b(null, str, str1, str2, l1, l2, jSONObject);
      AppBrandLogger.i("AppLogHandler", new Object[] { 
            "category", str, "tag", str1, "label", str2, "value", Long.valueOf(l1), "ext_value", Long.valueOf(l2), 
            "ext_json", jSONObject });
    } 
    return null;
  }
  
  public final String getType() {
    return "actionLog";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\f\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */