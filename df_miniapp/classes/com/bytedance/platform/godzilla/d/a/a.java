package com.bytedance.platform.godzilla.d.a;

import com.bytedance.platform.godzilla.d.c;
import java.util.concurrent.ThreadPoolExecutor;
import org.json.JSONException;
import org.json.JSONObject;

public final class a implements Runnable {
  private Thread a;
  
  private Runnable b;
  
  private ThreadPoolExecutor c;
  
  public a(ThreadPoolExecutor paramThreadPoolExecutor, Thread paramThread, Runnable paramRunnable) {
    this.c = paramThreadPoolExecutor;
    this.a = paramThread;
    this.b = paramRunnable;
  }
  
  public final void run() {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("poolName", ((c)this.c).a());
      jSONObject.put("poolInfo", this.c.toString());
      jSONObject.put("threadName", this.a.getName());
      jSONObject.put("stack", b.a(this.a.getStackTrace()));
      return;
    } catch (JSONException jSONException) {
      return;
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\platform\godzilla\d\a\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */