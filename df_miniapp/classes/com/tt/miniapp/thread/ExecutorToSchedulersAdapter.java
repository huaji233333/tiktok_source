package com.tt.miniapp.thread;

import com.storage.async.Scheduler;
import java.util.concurrent.Executor;

public class ExecutorToSchedulersAdapter implements Scheduler {
  private Executor mExecutor;
  
  public ExecutorToSchedulersAdapter(Executor paramExecutor) {
    this.mExecutor = paramExecutor;
  }
  
  public void execute(Runnable paramRunnable) {
    this.mExecutor.execute(paramRunnable);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\thread\ExecutorToSchedulersAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */