package com.facebook.react.bridge;

public class PromiseImpl implements Promise {
  private Callback mReject;
  
  private Callback mResolve;
  
  public PromiseImpl(Callback paramCallback1, Callback paramCallback2) {
    this.mResolve = paramCallback1;
    this.mReject = paramCallback2;
  }
  
  @Deprecated
  public void reject(String paramString) {
    reject("EUNSPECIFIED", paramString, null);
  }
  
  public void reject(String paramString1, String paramString2) {
    reject(paramString1, paramString2, null);
  }
  
  public void reject(String paramString1, String paramString2, Throwable paramThrowable) {
    if (this.mReject != null) {
      String str = paramString1;
      if (paramString1 == null)
        str = "EUNSPECIFIED"; 
      WritableNativeMap writableNativeMap = new WritableNativeMap();
      writableNativeMap.putString("code", str);
      writableNativeMap.putString("message", paramString2);
      this.mReject.invoke(new Object[] { writableNativeMap });
    } 
  }
  
  public void reject(String paramString, Throwable paramThrowable) {
    reject(paramString, paramThrowable.getMessage(), paramThrowable);
  }
  
  public void reject(Throwable paramThrowable) {
    reject("EUNSPECIFIED", paramThrowable.getMessage(), paramThrowable);
  }
  
  public void resolve(Object paramObject) {
    Callback callback = this.mResolve;
    if (callback != null)
      callback.invoke(new Object[] { paramObject }); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\PromiseImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */