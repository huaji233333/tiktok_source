package com.tt.miniapphost;

import com.bytedance.platform.godzilla.d.g;
import com.storage.async.Scheduler;
import java.util.concurrent.ThreadPoolExecutor;

public class TmaScheduler implements Scheduler {
  private ThreadPoolExecutor mExecutor = g.a();
  
  private TmaScheduler() {}
  
  public static TmaScheduler getInst() {
    return Holder.sInstance;
  }
  
  public void execute(Runnable paramRunnable) {
    this.mExecutor.execute(paramRunnable);
  }
  
  static class Holder {
    public static TmaScheduler sInstance = new TmaScheduler();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\TmaScheduler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */