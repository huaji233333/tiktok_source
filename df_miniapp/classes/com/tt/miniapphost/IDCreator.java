package com.tt.miniapphost;

import java.util.concurrent.atomic.AtomicInteger;

public class IDCreator {
  private static AtomicInteger sId = new AtomicInteger(0);
  
  public static int create() {
    return sId.incrementAndGet();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\IDCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */