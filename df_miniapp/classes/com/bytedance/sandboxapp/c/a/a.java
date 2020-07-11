package com.bytedance.sandboxapp.c.a;

import com.bytedance.sandboxapp.c.a.a.b;
import com.bytedance.sandboxapp.protocol.service.api.a;
import d.f;
import d.f.b.l;
import d.f.b.m;
import d.f.b.u;
import d.f.b.v;
import d.f.b.x;
import d.g;
import d.k.d;
import d.k.h;

public final class a implements a {
  final com.bytedance.sandboxapp.b.a b;
  
  private final f c;
  
  public a(com.bytedance.sandboxapp.b.a parama) {
    this.b = parama;
    this.c = g.a(new a(this));
  }
  
  private final b b() {
    return (b)this.c.getValue();
  }
  
  public final void a(b[] paramArrayOfb) {
    l.b(paramArrayOfb, "customizeApiPreHandlers");
    b b2 = b();
    l.b(paramArrayOfb, "customizeApiPreHandlers");
    int j = paramArrayOfb.length;
    b b1 = null;
    for (int i = 0; i < j; i++) {
      b b3 = paramArrayOfb[i];
      if (b1 == null) {
        b1 = b3;
      } else {
        b1.addApiPreHandlerAtLast(b3);
      } 
    } 
    if (b1 != null)
      b1.addApiPreHandlerAtLast(b2.a()); 
    if (b1 != null)
      b2.c = b1; 
  }
  
  static final class a extends m implements d.f.a.a<b> {
    a(a param1a) {
      super(0);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\c\a\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */