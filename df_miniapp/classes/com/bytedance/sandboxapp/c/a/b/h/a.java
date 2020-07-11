package com.bytedance.sandboxapp.c.a.b.h;

import com.bytedance.sandboxapp.a.a.c.k;
import com.bytedance.sandboxapp.c.a.b;
import com.bytedance.sandboxapp.protocol.service.h.c;
import d.f.b.l;

public final class a extends k {
  public a(b paramb, com.bytedance.sandboxapp.a.a.d.a parama) {
    super(paramb, parama);
  }
  
  public final void handleApi(com.bytedance.sandboxapp.protocol.service.api.entity.a parama) {
    l.b(parama, "apiInvokeInfo");
    ((c)((com.bytedance.sandboxapp.c.a.a.a)this).context.getService(c.class)).loginHostApp(new a(this));
  }
  
  public static final class a implements c.a {
    a(a param1a) {}
    
    public final void a() {
      this.a.callbackOk();
    }
    
    public final void a(String param1String) {
      l.b(param1String, "failReason");
      this.a.a(param1String);
    }
    
    public final void b() {
      this.a.callbackAppInBackground();
    }
    
    public final void c() {
      this.a.callbackFeatureNotSupport();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\c\a\b\h\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */