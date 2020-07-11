package com.bytedance.platform.godzilla.d.a;

import java.util.concurrent.ThreadPoolExecutor;
import org.json.JSONException;
import org.json.JSONObject;

public final class c implements Runnable {
  private Runnable a;
  
  private ThreadPoolExecutor b;
  
  public c(ThreadPoolExecutor paramThreadPoolExecutor, Runnable paramRunnable) {
    this.a = paramRunnable;
    this.b = paramThreadPoolExecutor;
  }
  
  public final void run() {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("poolName", ((com.bytedance.platform.godzilla.d.c)this.b).a());
      jSONObject.put("poolInfo", this.b.toString());
      jSONObject.put("task", this.a.toString());
      return;
    } catch (JSONException jSONException) {
      return;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\platform\godzilla\d\a\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */