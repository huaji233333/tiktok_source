package com.bytedance.sandboxapp.a.a.c;

import com.bytedance.sandboxapp.c.a.a.e;
import com.bytedance.sandboxapp.c.a.b;
import org.json.JSONObject;

public abstract class h extends e {
  public h(b paramb, com.bytedance.sandboxapp.a.a.d.a parama) {
    super(paramb, parama);
  }
  
  public static final class a {
    private JSONObject a;
    
    public static a a() {
      return new a();
    }
    
    public final a a(JSONObject param1JSONObject) {
      this.a = param1JSONObject;
      return this;
    }
    
    public final com.bytedance.sandboxapp.b.b.a b() {
      com.bytedance.sandboxapp.b.b.a a1 = new com.bytedance.sandboxapp.b.b.a();
      a1.a("extConfig", this.a);
      return a1;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\a\a\c\h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */