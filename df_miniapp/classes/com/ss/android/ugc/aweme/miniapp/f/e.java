package com.ss.android.ugc.aweme.miniapp.f;

import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;
import com.tt.miniapphost.process.handler.ISyncHostDataHandler;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public final class e implements ISyncHostDataHandler {
  public final CrossProcessDataEntity action(CrossProcessDataEntity paramCrossProcessDataEntity) {
    JSONObject jSONObject = new JSONObject();
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    MiniAppService.inst().getNetWorkDepend().a(hashMap, true);
    for (Map.Entry<Object, Object> entry : hashMap.entrySet()) {
      if (entry != null)
        try {
          if (entry.getKey() != null && entry.getValue() != null)
            jSONObject.put((String)entry.getKey(), entry.getValue()); 
        } catch (Exception exception) {
          AppBrandLogger.stacktrace(5, "GetNetCommonParamsHandler", exception.getStackTrace());
        }  
    } 
    return CrossProcessDataEntity.Builder.create().put("netCommonParams", jSONObject).build();
  }
  
  public final String getType() {
    return "getNetCommonParams";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\f\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */