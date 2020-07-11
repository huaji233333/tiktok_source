package com.bytedance.sandboxapp.c.a.a;

import com.bytedance.sandboxapp.protocol.service.api.entity.ApiCallbackData;
import d.f.b.g;
import d.f.b.l;

public abstract class e extends d {
  public static final a c = new a(null);
  
  com.bytedance.sandboxapp.protocol.service.api.entity.a b;
  
  public e(com.bytedance.sandboxapp.c.a.b paramb, com.bytedance.sandboxapp.a.a.d.a parama) {
    super(paramb, parama);
  }
  
  public final com.bytedance.sandboxapp.protocol.service.api.entity.b b(com.bytedance.sandboxapp.protocol.service.api.entity.a parama) {
    return super.handleApiInvoke(parama);
  }
  
  public com.bytedance.sandboxapp.protocol.service.api.entity.b handleApiInvoke(com.bytedance.sandboxapp.protocol.service.api.entity.a parama) {
    l.b(parama, "apiInvokeInfo");
    Boolean bool = this.apiInfoEntity.F;
    l.a(bool, "apiInfoEntity.syncCall");
    if (bool.booleanValue())
      return b(parama); 
    this.b = parama;
    if (parama.a(new b(this, parama)))
      return com.bytedance.sandboxapp.protocol.service.api.entity.b.d; 
    com.bytedance.sandboxapp.b.a.a.b.b.logOrThrow("AbsTwinApiHandler", new Object[] { "触发执行异步 Api 处理失败，apiInvokeInfo:", parama });
    return com.bytedance.sandboxapp.protocol.service.api.entity.b.c;
  }
  
  public static final class a {
    private a() {}
  }
  
  static final class b implements Runnable {
    b(e param1e, com.bytedance.sandboxapp.protocol.service.api.entity.a param1a) {}
    
    public final void run() {
      e e1 = this.a;
      ApiCallbackData apiCallbackData = (e1.b(this.b)).b;
      if (apiCallbackData == null)
        l.a(); 
      l.b(apiCallbackData, "apiCallbackData");
      com.bytedance.sandboxapp.protocol.service.api.entity.a a1 = e1.b;
      if (a1 == null)
        l.a(); 
      if (!a1.a(apiCallbackData))
        com.bytedance.sandboxapp.b.a.a.b.b.logOrThrow("AbsTwinApiHandler", new Object[] { "触发执行异步 Api 回调失败，apiInvokeInfo:", e1.b }); 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\c\a\a\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */