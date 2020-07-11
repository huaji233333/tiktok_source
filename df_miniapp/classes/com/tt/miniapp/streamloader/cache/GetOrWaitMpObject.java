package com.tt.miniapp.streamloader.cache;

import com.tt.miniapphost.monitor.AppBrandMonitor;
import org.json.JSONException;
import org.json.JSONObject;

public class GetOrWaitMpObject {
  public String fileName;
  
  public boolean isReleased;
  
  public String loadFrom;
  
  public String status;
  
  public String useTime;
  
  public void report() {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("tma_event", "GetOrWait");
      jSONObject.put("tma_status", String.valueOf(this.status));
      jSONObject.put("tma_fileName", String.valueOf(this.fileName));
      jSONObject.put("tma_loadFrom", String.valueOf(this.loadFrom));
      jSONObject.put("tma_useTime", String.valueOf(this.useTime));
      jSONObject.put("tma_isReleased", this.isReleased);
      AppBrandMonitor.statusRate("mp_streamload_monitor", 0, jSONObject);
      return;
    } catch (JSONException jSONException) {
      return;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\streamloader\cache\GetOrWaitMpObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */