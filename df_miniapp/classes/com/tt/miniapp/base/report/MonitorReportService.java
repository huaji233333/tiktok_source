package com.tt.miniapp.base.report;

import com.bytedance.sandboxapp.b.a;
import com.bytedance.sandboxapp.protocol.service.n.b;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import d.f.b.l;
import org.json.JSONObject;

public final class MonitorReportService implements b {
  private final a context;
  
  public MonitorReportService(a parama) {
    this.context = parama;
  }
  
  public final a getContext() {
    return this.context;
  }
  
  public final void onDestroy() {}
  
  public final void reportInvokeApiFail(int paramInt, JSONObject paramJSONObject) {
    l.b(paramJSONObject, "param");
    AppBrandMonitor.statusRate("mp_invoke_api_failed", 7000, paramJSONObject);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\base\report\MonitorReportService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */