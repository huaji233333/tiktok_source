package com.facebook.react.bridge;

public interface Promise {
  @Deprecated
  void reject(String paramString);
  
  void reject(String paramString1, String paramString2);
  
  void reject(String paramString1, String paramString2, Throwable paramThrowable);
  
  void reject(String paramString, Throwable paramThrowable);
  
  void reject(Throwable paramThrowable);
  
  void resolve(Object paramObject);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\Promise.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */