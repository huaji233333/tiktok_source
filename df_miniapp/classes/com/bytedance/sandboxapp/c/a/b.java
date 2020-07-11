package com.bytedance.sandboxapp.c.a;

import com.bytedance.sandboxapp.c.a.a.a;
import com.bytedance.sandboxapp.c.a.a.b;
import com.bytedance.sandboxapp.c.a.c.a;
import com.bytedance.sandboxapp.protocol.service.api.a.a;
import com.bytedance.sandboxapp.protocol.service.api.entity.ApiCallbackData;
import d.f;
import d.f.b.l;
import d.f.b.m;
import d.f.b.u;
import d.f.b.v;
import d.f.b.x;
import d.g;
import d.k.d;
import d.k.h;

public final class b implements a {
  final String b;
  
  b c;
  
  final com.bytedance.sandboxapp.b.a d;
  
  private final f e;
  
  public b(com.bytedance.sandboxapp.b.a parama) {
    this.d = parama;
    this.b = "SandboxAppApiRuntime";
    this.e = g.a(new b(this));
    this.c = a();
  }
  
  final b a() {
    return (b)this.e.getValue();
  }
  
  public final com.bytedance.sandboxapp.b.a getContext() {
    return this.d;
  }
  
  public final com.bytedance.sandboxapp.protocol.service.api.entity.b handleApiInvoke(com.bytedance.sandboxapp.protocol.service.api.entity.a parama) {
    l.b(parama, "apiInvokeInfo");
    a a1 = com.bytedance.sandboxapp.a.a.b.a.a(this, parama);
    if (a1 == null)
      return com.bytedance.sandboxapp.protocol.service.api.entity.b.c; 
    if (!a1.apiInfoEntity.F.booleanValue())
      parama.a = new a(this, parama); 
    com.bytedance.sandboxapp.b.a.b.b.b.d(this.b, new Object[] { "handleApiInvoke apiName:", parama.b });
    b b1 = this.c;
    if (b1 != null) {
      com.bytedance.sandboxapp.protocol.service.api.entity.b b2 = b1.triggerPreHandleApi(parama, a1);
    } else {
      b1 = null;
    } 
    if (b1 != null) {
      com.bytedance.sandboxapp.b.a.b.b.b.d(this.b, new Object[] { "被预处理的 apiName:", parama.b });
      return (com.bytedance.sandboxapp.protocol.service.api.entity.b)b1;
    } 
    return a1.handleApiInvoke(parama);
  }
  
  public static final class a implements com.bytedance.sandboxapp.protocol.service.api.entity.a.a {
    a(b param1b, com.bytedance.sandboxapp.protocol.service.api.entity.a param1a) {}
    
    public final void a(ApiCallbackData param1ApiCallbackData) {
      l.b(param1ApiCallbackData, "apiCallbackData");
      if (param1ApiCallbackData.b) {
        b b1 = this.a;
        String str2 = this.b.b;
        String str1 = param1ApiCallbackData.toString();
        com.bytedance.sandboxapp.b.a.b.b.b.e(b1.b, new Object[] { "monitorInvokeApiFailed eventName:", str2, "errorMsg:", str1 });
        ((com.bytedance.sandboxapp.protocol.service.n.b)b1.d.getService(com.bytedance.sandboxapp.protocol.service.n.b.class)).reportInvokeApiFail(7000, ((new com.bytedance.sandboxapp.b.b.a()).a("eventName", str2).a("errorMessage", str1)).a);
      } 
    }
  }
  
  static final class b extends m implements d.f.a.a<b> {
    b(b param1b) {
      super(0);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\c\a\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */