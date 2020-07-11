package com.bytedance.sandboxapp.c.a.b.a.a;

import com.bytedance.sandboxapp.a.a.a.b;
import com.bytedance.sandboxapp.a.a.c.a;
import com.bytedance.sandboxapp.c.a.b;
import com.bytedance.sandboxapp.protocol.service.a.a.a.b;

public final class a extends a {
  public a(b paramb, com.bytedance.sandboxapp.a.a.d.a parama) {
    super(paramb, parama);
  }
  
  public final void a(a.a parama, com.bytedance.sandboxapp.protocol.service.api.entity.a parama1) {
    com.bytedance.sandboxapp.protocol.service.a.a.a a1 = (com.bytedance.sandboxapp.protocol.service.a.a.a)((com.bytedance.sandboxapp.c.a.a.a)this).context.getService(com.bytedance.sandboxapp.protocol.service.a.a.a.class);
    if (a1 == null || !a1.isSupportDxppManager()) {
      a();
      return;
    } 
    b b1 = new b();
    b1.a = parama.b.longValue();
    b1.b = parama.c;
    b1.c = parama.d;
    b1.d = parama.e;
    b1.e = parama.f;
    b1.f = parama.g;
    b1.g = parama.h;
    b1.h = parama.i;
    b1.i = parama.j;
    Integer integer = parama.k;
    byte b = 0;
    if (integer != null) {
      i = parama.k.intValue();
    } else {
      i = 0;
    } 
    b1.j = i;
    int i = b;
    if (parama.l != null)
      i = parama.l.intValue(); 
    b1.k = i;
    if (parama.m != null) {
      String str = parama.m.toString();
    } else {
      parama = null;
    } 
    b1.l = (String)parama;
    a1.cancelDxppAd(b1);
    callbackOk(null);
    parama1.c.handleApiInvoke(com.bytedance.sandboxapp.protocol.service.api.entity.a.b.a((com.bytedance.sandboxapp.protocol.service.api.a.a)((com.bytedance.sandboxapp.c.a.a.a)this).sandboxAppApiRuntime, "onDxppAdStatusChange", (com.bytedance.sandboxapp.protocol.service.api.b.a)b.a().a((parama1.a()).a).a("cancel_download").b("success").b()).a());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\c\a\b\a\a\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */