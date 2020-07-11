package com.bytedance.sandboxapp.c.a.b.a.a;

import com.bytedance.sandboxapp.a.a.a.b;
import com.bytedance.sandboxapp.a.a.c.t;
import com.bytedance.sandboxapp.a.a.d.a;
import com.bytedance.sandboxapp.c.a.a.a;
import com.bytedance.sandboxapp.c.a.b;
import com.bytedance.sandboxapp.protocol.service.a.a.a;
import com.bytedance.sandboxapp.protocol.service.a.a.a.b;
import com.bytedance.sandboxapp.protocol.service.api.a.a;
import com.bytedance.sandboxapp.protocol.service.api.b.a;
import com.bytedance.sandboxapp.protocol.service.api.entity.a;

public final class e extends t {
  public e(b paramb, a parama) {
    super(paramb, parama);
  }
  
  public final void a(t.a parama, a parama1) {
    a a1 = (a)((a)this).context.getService(a.class);
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
    a1.unsubscribeAppAd(b1);
    callbackOk(null);
    parama1.c.handleApiInvoke(a.b.a((a)((a)this).sandboxAppApiRuntime, "onDxppAdStatusChange", (a)b.a().a((parama1.a()).a).a("unsubscribed").b("success").b()).a());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\c\a\b\a\a\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */