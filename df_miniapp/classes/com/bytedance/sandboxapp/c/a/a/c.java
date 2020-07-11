package com.bytedance.sandboxapp.c.a.a;

import com.bytedance.sandboxapp.protocol.service.api.entity.ApiCallbackData;
import d.f.b.g;
import d.f.b.l;

public abstract class c extends a {
  public static final a Companion = new a(null);
  
  private com.bytedance.sandboxapp.protocol.service.api.entity.a mApiInvokeInfo;
  
  public c(com.bytedance.sandboxapp.c.a.b paramb, com.bytedance.sandboxapp.a.a.d.a parama) {
    super(paramb, parama);
  }
  
  public final void callbackAppInBackground() {
    callbackData(buildAppInBackground());
  }
  
  public final void callbackCancel() {
    String str = this.apiName;
    l.b(str, "apiName");
    callbackData((new ApiCallbackData.a(str, "cancel", null)).a());
  }
  
  protected final void callbackData(ApiCallbackData paramApiCallbackData) {
    l.b(paramApiCallbackData, "apiCallbackData");
    com.bytedance.sandboxapp.protocol.service.api.entity.a a1 = this.mApiInvokeInfo;
    if (a1 == null)
      l.a(); 
    if (!a1.a(paramApiCallbackData))
      com.bytedance.sandboxapp.b.a.a.b.b.logOrThrow("AbsAsyncApiHandler", new Object[] { "触发执行异步 Api 回调失败，apiInvokeInfo:", this.mApiInvokeInfo }); 
    onCallbackData();
  }
  
  public final void callbackFeatureNotSupport() {
    callbackData(buildFeatureNotSupport());
  }
  
  public final void callbackNativeException(Throwable paramThrowable) {
    callbackData(buildNativeException(paramThrowable));
  }
  
  public final void callbackOk() {
    callbackData(ApiCallbackData.a.a.a(this.apiName, null).a());
  }
  
  public final void callbackOk(com.bytedance.sandboxapp.b.b.a parama) {
    callbackData(ApiCallbackData.a.a.a(this.apiName, parama).a());
  }
  
  protected abstract void handleApi(com.bytedance.sandboxapp.protocol.service.api.entity.a parama);
  
  public com.bytedance.sandboxapp.protocol.service.api.entity.b handleApiInvoke(com.bytedance.sandboxapp.protocol.service.api.entity.a parama) {
    l.b(parama, "apiInvokeInfo");
    this.mApiInvokeInfo = parama;
    if (parama.a(new b(this, parama)))
      return com.bytedance.sandboxapp.protocol.service.api.entity.b.d; 
    com.bytedance.sandboxapp.b.a.a.b.b.logOrThrow("AbsAsyncApiHandler", new Object[] { "触发执行异步 Api 处理失败，apiInvokeInfo:", parama });
    return com.bytedance.sandboxapp.protocol.service.api.entity.b.c;
  }
  
  protected final void onCallbackData() {}
  
  public static final class a {
    private a() {}
  }
  
  static final class b implements Runnable {
    b(c param1c, com.bytedance.sandboxapp.protocol.service.api.entity.a param1a) {}
    
    public final void run() {
      try {
        return;
      } finally {
        Exception exception = null;
        this.a.callbackNativeException(exception);
        com.bytedance.sandboxapp.b.a.a.b.b.logOrThrow("AbsAsyncApiHandler", new Object[] { "handleApi 异常 api:", this.a.apiName, exception });
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\c\a\a\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */