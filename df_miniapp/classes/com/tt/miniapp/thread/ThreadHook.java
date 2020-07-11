package com.tt.miniapp.thread;

import com.bytedance.knot.base.annotation.MatchScope;
import com.bytedance.knot.base.annotation.Proxy;
import com.bytedance.knot.base.annotation.Scope;
import com.bytedance.platform.godzilla.d.g;
import com.storage.async.Scheduler;
import java.util.concurrent.ExecutorService;

public class ThreadHook {
  public static final Scheduler LongIo;
  
  public static final Scheduler ShortIo = new ShortIOSchedulerCreator();
  
  static {
    LongIo = new LongIOSchedulerCreator();
  }
  
  @MatchScope(type = Scope.ALL)
  @Proxy("com.storage.async.Schedulers.longIO()")
  public static Scheduler longIO() {
    return LongIo;
  }
  
  @MatchScope(type = Scope.ALL)
  @Proxy("com.storage.async.Schedulers.shortIO()")
  public static Scheduler shortIO() {
    return ShortIo;
  }
  
  static class LongIOSchedulerCreator implements Scheduler {
    ExecutorService service = g.a();
    
    public void execute(Runnable param1Runnable) {
      this.service.execute(param1Runnable);
    }
  }
  
  static class ShortIOSchedulerCreator implements Scheduler {
    ExecutorService service = g.b();
    
    public void execute(Runnable param1Runnable) {
      this.service.execute(param1Runnable);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\thread\ThreadHook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */