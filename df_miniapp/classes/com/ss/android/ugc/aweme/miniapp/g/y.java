package com.ss.android.ugc.aweme.miniapp.g;

import android.content.Context;
import com.ss.android.ugc.aweme.miniapp.MiniAppService;
import com.ss.android.ugc.aweme.miniapp_api.a.m;
import com.tt.option.p.b;
import com.tt.option.p.e;
import java.util.Arrays;
import org.json.JSONException;
import org.json.JSONObject;

public class y extends b {
  public m a = MiniAppService.inst().getSDKMonitorDepend();
  
  public e createSDKMonitorInstance(Context paramContext, String paramString, JSONObject paramJSONObject) {
    this.a.a(paramString, Arrays.asList(new String[] { "https://mon.isnssdk.com/monitor/appmonitor/v2/settings", "https://i.isnssdk.com/monitor/appmonitor/v2/settings" }));
    if (paramJSONObject != null)
      try {
        paramJSONObject.put("oversea", "1");
      } catch (JSONException jSONException) {} 
    this.a.a(paramContext, paramString, paramJSONObject);
    this.a.a(paramString);
    return new e(this) {
        public final void flushBuffer() {}
        
        public final void monitorCommonLog(String param1String, JSONObject param1JSONObject) {
          this.a.a.a(param1String, param1JSONObject);
        }
        
        public final void monitorDuration(String param1String, JSONObject param1JSONObject1, JSONObject param1JSONObject2) {
          this.a.a.a(param1String, param1JSONObject1, param1JSONObject2);
        }
        
        public final void monitorEvent(String param1String, JSONObject param1JSONObject1, JSONObject param1JSONObject2, JSONObject param1JSONObject3) {
          this.a.a.a(param1String, param1JSONObject1, param1JSONObject2, param1JSONObject3);
        }
        
        public final void monitorStatusAndDuration(String param1String, int param1Int, JSONObject param1JSONObject1, JSONObject param1JSONObject2) {
          this.a.a.a(param1String, param1Int, param1JSONObject1, param1JSONObject2);
        }
        
        public final void monitorStatusRate(String param1String, int param1Int, JSONObject param1JSONObject) {
          this.a.a.a(param1String, param1Int, param1JSONObject);
        }
      };
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\ss\androi\\ugc\aweme\miniapp\g\y.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */