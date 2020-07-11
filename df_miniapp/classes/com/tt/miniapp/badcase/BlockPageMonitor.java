package com.tt.miniapp.badcase;

import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import org.json.JSONException;
import org.json.JSONObject;

class BlockPageMonitor {
  private static void monitor(int paramInt, String paramString) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("errorMsg", paramString);
      AppBrandMonitor.statusRate("mp_block_page", paramInt, jSONObject);
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.e("BlockPageMonitor", new Object[] { jSONException });
      return;
    } 
  }
  
  static void pushGeneralDataFail(String paramString) {
    monitor(1003, paramString);
  }
  
  static void requestFail(String paramString) {
    monitor(1000, paramString);
  }
  
  static void showErrorNotFirstPage(String paramString) {
    monitor(1004, paramString);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\badcase\BlockPageMonitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */