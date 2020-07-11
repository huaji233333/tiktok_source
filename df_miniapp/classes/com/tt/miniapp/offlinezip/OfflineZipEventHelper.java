package com.tt.miniapp.offlinezip;

import android.text.TextUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import org.json.JSONObject;

public class OfflineZipEventHelper {
  public static void monitor(String paramString1, int paramInt1, String paramString2, int paramInt2) {
    try {
      JSONObject jSONObject1 = new JSONObject();
      if (paramInt1 >= 0)
        jSONObject1.put("errCode", paramInt1); 
      JSONObject jSONObject2 = new JSONObject();
      if (paramInt2 >= 0)
        jSONObject2.put("duration", paramInt2); 
      JSONObject jSONObject3 = new JSONObject();
      if (!TextUtils.isEmpty(paramString1))
        jSONObject3.put("moduleName", paramString1); 
      if (!TextUtils.isEmpty(paramString2))
        jSONObject3.put("errMsg", paramString2); 
      AppBrandMonitor.event("mp_offline_zip_update", jSONObject1, jSONObject2, jSONObject3);
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("tma_OfflineZipEventHelper", new Object[] { "monitor: ", exception });
      return;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\offlinezip\OfflineZipEventHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */