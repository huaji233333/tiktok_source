package com.tt.miniapp.msg.sync;

import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.debug.PerformanceService;
import com.tt.miniapphost.AppBrandLogger;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiGetPerformanceTimingSyncCtrl extends SyncMsgCtrl {
  public ApiGetPerformanceTimingSyncCtrl(String paramString) {
    super(paramString);
  }
  
  public String act() {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("timings", ((PerformanceService)AppbrandApplicationImpl.getInst().getService(PerformanceService.class)).getPerformanceTimingArray());
      return makeOkMsg(jSONObject);
    } catch (JSONException jSONException) {
      AppBrandLogger.e("ApiGetPerformanceTimingSyncCtrl", new Object[] { jSONException });
      return makeFailMsg((Throwable)jSONException);
    } 
  }
  
  public String getName() {
    return "getPerformanceTimingSync";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\sync\ApiGetPerformanceTimingSyncCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */