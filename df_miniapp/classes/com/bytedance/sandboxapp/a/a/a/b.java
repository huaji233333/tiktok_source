package com.bytedance.sandboxapp.a.a.a;

import com.bytedance.sandboxapp.b.b.a;
import com.bytedance.sandboxapp.c.a.a.f;
import org.json.JSONObject;

public final class b {
  private JSONObject a;
  
  private String b;
  
  private Long c;
  
  private Long d;
  
  private String e;
  
  public static b a() {
    return new b();
  }
  
  public final b a(String paramString) {
    this.b = paramString;
    return this;
  }
  
  public final b a(JSONObject paramJSONObject) {
    this.a = paramJSONObject;
    return this;
  }
  
  public final b b(String paramString) {
    this.e = paramString;
    return this;
  }
  
  public final f b() {
    a a = new a();
    a.a("appad", this.a);
    a.a("status", this.b);
    a.a("total_bytes", this.c);
    a.a("current_bytes", this.d);
    a.a("message", this.e);
    return new f(a);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\a\a\a\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */