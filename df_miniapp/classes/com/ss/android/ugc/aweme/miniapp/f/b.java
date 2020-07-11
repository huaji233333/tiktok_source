package com.ss.android.ugc.aweme.miniapp.f;

import android.app.Application;
import android.content.Context;
import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.handler.ISyncHostDataHandler;
import org.json.JSONObject;

public final class b implements ISyncHostDataHandler {
  public final CrossProcessDataEntity action(CrossProcessDataEntity paramCrossProcessDataEntity) {
    if (paramCrossProcessDataEntity == null)
      return null; 
    Application application2 = AppbrandContext.getInst().getApplicationContext();
    Application application1 = application2;
    if (application2 == null)
      application1 = MiniAppService.inst().getApplication(); 
    if (application1 == null) {
      AppBrandLogger.d("AppLogMiscHandler", new Object[] { "context null,abort" });
      return null;
    } 
    String str = paramCrossProcessDataEntity.getString("logEventName");
    JSONObject jSONObject = paramCrossProcessDataEntity.getJSONObject("logEventData");
    if (str != null && jSONObject != null) {
      MiniAppService.inst().getBaseLibDepend().a((Context)application1, str, jSONObject);
      StringBuilder stringBuilder = new StringBuilder("event name:");
      stringBuilder.append(str);
      stringBuilder.append(",event val:");
      stringBuilder.append(jSONObject.toString());
      AppBrandLogger.d("AppLogMiscHandler", new Object[] { stringBuilder.toString() });
    } 
    return null;
  }
  
  public final String getType() {
    return "actionMiscAppLog";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\f\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */