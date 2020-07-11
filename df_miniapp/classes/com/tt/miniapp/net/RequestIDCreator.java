package com.tt.miniapp.net;

import java.util.concurrent.atomic.AtomicInteger;

public class RequestIDCreator {
  private static final AtomicInteger ID = new AtomicInteger(0);
  
  public static int create() {
    return ID.incrementAndGet();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\net\RequestIDCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */