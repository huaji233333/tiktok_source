package com.bytedance.sandboxapp.c.a.a;

import com.bytedance.sandboxapp.b.b.a;
import com.bytedance.sandboxapp.protocol.service.api.b.a;
import d.f.b.l;

public final class f implements a {
  public final a a;
  
  public f(a parama) {
    this.a = parama;
  }
  
  public final <T> T getParam(String paramString) {
    l.b(paramString, "key");
    a a1 = this.a;
    l.b(paramString, "key");
    return (T)a1.a.get(paramString);
  }
  
  public final a toJson() {
    return this.a;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\c\a\a\f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */