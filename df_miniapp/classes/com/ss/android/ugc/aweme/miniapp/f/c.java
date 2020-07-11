package com.ss.android.ugc.aweme.miniapp.f;

import android.text.TextUtils;
import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.handler.ISyncHostDataHandler;
import org.json.JSONObject;

public final class c implements ISyncHostDataHandler {
  public final CrossProcessDataEntity action(CrossProcessDataEntity paramCrossProcessDataEntity) {
    if (paramCrossProcessDataEntity == null)
      return null; 
    String str = paramCrossProcessDataEntity.getString("mpMonitorServiceName");
    int i = paramCrossProcessDataEntity.getInt("mpMonitorStatusCode");
    JSONObject jSONObject = paramCrossProcessDataEntity.getJSONObject("mpMonitorData");
    if (!TextUtils.isEmpty(str))
      try {
        MiniAppService.inst().getMonitorDepend().a(str, i, jSONObject);
        return null;
      } catch (Exception exception) {
        AppBrandLogger.e("AppbrandMonitorHandler", new Object[] { exception });
      }  
    return null;
  }
  
  public final String getType() {
    return "appBrandMonitor";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\f\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */