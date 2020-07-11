package com.bytedance.d.a.a;

import android.app.Application;
import com.bytedance.d.a.a;
import com.bytedance.d.a.b.a;
import d.f.b.l;
import java.util.concurrent.ConcurrentHashMap;

public final class b extends a {
  private final ConcurrentHashMap<com.bytedance.d.a.b.b, a> a = new ConcurrentHashMap<com.bytedance.d.a.b.b, a>();
  
  public final a a(a parama) {
    l.b(parama, "plugin");
    this.a.put(parama.a(), parama);
    Application application = a.b;
    if (application == null)
      l.a("application"); 
    parama.a(application);
    return this;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\d\a\a\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */