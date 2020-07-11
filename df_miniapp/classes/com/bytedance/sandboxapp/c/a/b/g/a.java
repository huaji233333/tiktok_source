package com.bytedance.sandboxapp.c.a.b.g;

import com.bytedance.sandboxapp.a.a.c.r;
import com.bytedance.sandboxapp.c.a.b;
import d.f.b.l;

public final class a extends r {
  public com.bytedance.sandboxapp.protocol.service.k.a a = (com.bytedance.sandboxapp.protocol.service.k.a)((com.bytedance.sandboxapp.c.a.a.a)this).context.getService(com.bytedance.sandboxapp.protocol.service.k.a.class);
  
  public com.bytedance.sandboxapp.protocol.service.k.a.c b;
  
  public a(b paramb, com.bytedance.sandboxapp.a.a.d.a parama) {
    super(paramb, parama);
  }
  
  public final void a(r.a parama, com.bytedance.sandboxapp.protocol.service.api.entity.a parama1) {
    l.b(parama, "paramParser");
    l.b(parama1, "apiInvokeInfo");
    Integer integer = parama.d;
    l.a(integer, "paramParser.x");
    int i = integer.intValue();
    integer = parama.e;
    l.a(integer, "paramParser.y");
    int j = integer.intValue();
    integer = parama.f;
    l.a(integer, "paramParser.width");
    int k = integer.intValue();
    integer = parama.g;
    l.a(integer, "paramParser.height");
    com.bytedance.sandboxapp.protocol.service.k.a.b b = new com.bytedance.sandboxapp.protocol.service.k.a.b(i, j, k, integer.intValue());
    com.bytedance.sandboxapp.protocol.service.k.a a1 = this.a;
    String str2 = parama.b;
    l.a(str2, "paramParser.url");
    String str1 = parama.c;
    l.a(str1, "paramParser.referer");
    a1.payOnH5(str2, str1, b, new a(this));
  }
  
  public static final class a implements com.bytedance.sandboxapp.protocol.service.k.a.a {
    a(a param1a) {}
    
    public final void a() {
      this.a.a();
    }
    
    public final void a(String param1String) {
      l.b(param1String, "failMessage");
      com.bytedance.sandboxapp.protocol.service.k.a.c c = this.a.b;
      if (c != null)
        c.a(); 
      this.a.a(param1String);
    }
    
    public final void b() {
      a a1 = this.a;
      a1.b = a1.a.createPayNotification();
    }
    
    public final void c() {
      com.bytedance.sandboxapp.protocol.service.k.a.c c = this.a.b;
      if (c != null)
        c.a(); 
      this.a.callbackOk();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\c\a\b\g\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */