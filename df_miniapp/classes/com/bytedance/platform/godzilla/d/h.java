package com.bytedance.platform.godzilla.d;

public interface h {
  public static final h a = new h() {
      public final void a(Throwable param1Throwable) {}
    };
  
  public static final h b = new h() {
      public final void a(Throwable param1Throwable) {}
    };
  
  public static final h c = new h() {
      public final void a(Throwable param1Throwable) {
        if (param1Throwable == null)
          return; 
        throw new RuntimeException("Request threw uncaught throwable", param1Throwable);
      }
    };
  
  public static final h d = b;
  
  void a(Throwable paramThrowable);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\platform\godzilla\d\h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */