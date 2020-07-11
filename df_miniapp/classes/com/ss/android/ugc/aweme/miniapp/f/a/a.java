package com.ss.android.ugc.aweme.miniapp.f.a;

import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;

public final class a {
  public static CrossProcessDataEntity a(CrossProcessDataEntity paramCrossProcessDataEntity) {
    if (paramCrossProcessDataEntity == null)
      return null; 
    try {
      MiniAppService.inst().getMonitorDepend().a(null, paramCrossProcessDataEntity.getString("category"), paramCrossProcessDataEntity.getString("logEventName"), paramCrossProcessDataEntity.getString("labelName"), paramCrossProcessDataEntity.getLong("logVIValue"), paramCrossProcessDataEntity.getLong("logVIExtValue"), paramCrossProcessDataEntity.getJSONObject("logEventData"));
      return null;
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(5, "AppV1LogHandler", exception.getStackTrace());
      return null;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\f\a\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */