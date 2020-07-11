package com.bytedance.sandboxapp.c.a.b.e;

import com.bytedance.sandboxapp.a.a.c.i;
import com.bytedance.sandboxapp.a.a.d.a;
import com.bytedance.sandboxapp.c.a.a.a;
import com.bytedance.sandboxapp.protocol.service.api.entity.ApiCallbackData;
import com.bytedance.sandboxapp.protocol.service.api.entity.a;
import com.bytedance.sandboxapp.protocol.service.e.a;
import com.bytedance.sandboxapp.protocol.service.h.a;
import com.bytedance.sandboxapp.protocol.service.h.c;
import d.f.b.l;

public final class b extends i {
  public b(com.bytedance.sandboxapp.c.a.b paramb, a parama) {
    super(paramb, parama);
  }
  
  public final ApiCallbackData a(a parama) {
    l.b(parama, "apiInvokeInfo");
    c c = (c)((a)this).context.getService(c.class);
    com.bytedance.sandboxapp.protocol.service.e.b b1 = (com.bytedance.sandboxapp.protocol.service.e.b)((a)this).context.getService(com.bytedance.sandboxapp.protocol.service.e.b.class);
    a a1 = c.getHostAppInfo();
    com.bytedance.sandboxapp.protocol.service.h.b b2 = c.getHostAppUserInfo();
    a a2 = b1.getDeviceInfo();
    return a(i.a.a().a(a1.a).c(a1.b).e(a1.c).f(a1.d).h(b2.a).a(Boolean.valueOf(b2.c)).b(a2.a).d(a2.b).g(a2.c).b());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\c\a\b\e\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */