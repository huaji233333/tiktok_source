package com.bytedance.sandboxapp.c.a.a;

import com.bytedance.sandboxapp.c.a.b;
import com.bytedance.sandboxapp.protocol.service.api.entity.ApiCallbackData;
import com.bytedance.sandboxapp.protocol.service.api.entity.b;
import d.f.b.g;
import d.f.b.l;

public abstract class d extends a {
  public static final a a = new a(null);
  
  public d(b paramb, com.bytedance.sandboxapp.a.a.d.a parama) {
    super(paramb, parama);
  }
  
  protected final ApiCallbackData a(com.bytedance.sandboxapp.b.b.a parama) {
    return ApiCallbackData.a.a.a(this.apiName, parama).a();
  }
  
  protected abstract ApiCallbackData a(com.bytedance.sandboxapp.protocol.service.api.entity.a parama);
  
  public b handleApiInvoke(com.bytedance.sandboxapp.protocol.service.api.entity.a parama) {
    ApiCallbackData apiCallbackData;
    l.b(parama, "apiInvokeInfo");
  }
  
  public static final class a {
    private a() {}
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\c\a\a\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */