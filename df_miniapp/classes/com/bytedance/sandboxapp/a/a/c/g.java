package com.bytedance.sandboxapp.a.a.c;

import com.bytedance.sandboxapp.c.a.a.d;
import com.bytedance.sandboxapp.protocol.service.api.entity.ApiCallbackData;
import org.json.JSONArray;

public abstract class g extends d {
  public g(com.bytedance.sandboxapp.c.a.b paramb, com.bytedance.sandboxapp.a.a.d.a parama) {
    super(paramb, parama);
  }
  
  public abstract ApiCallbackData a(b paramb, com.bytedance.sandboxapp.protocol.service.api.entity.a parama);
  
  public final ApiCallbackData a(com.bytedance.sandboxapp.protocol.service.api.entity.a parama) {
    b b = new b(this, parama);
    return (b.a != null) ? b.a : a(b, parama);
  }
  
  public static final class a {
    private String a;
    
    private String b;
    
    private String c;
    
    private JSONArray d;
    
    private JSONArray e;
    
    private Integer f;
    
    public static a a() {
      return new a();
    }
    
    public final a a(Integer param1Integer) {
      this.f = param1Integer;
      return this;
    }
    
    public final a a(String param1String) {
      this.a = param1String;
      return this;
    }
    
    public final a a(JSONArray param1JSONArray) {
      this.d = param1JSONArray;
      return this;
    }
    
    public final a b(String param1String) {
      this.b = param1String;
      return this;
    }
    
    public final a b(JSONArray param1JSONArray) {
      this.e = param1JSONArray;
      return this;
    }
    
    public final com.bytedance.sandboxapp.b.b.a b() {
      com.bytedance.sandboxapp.b.b.a a1 = new com.bytedance.sandboxapp.b.b.a();
      a1.a("schema", this.a);
      a1.a("appId", this.b);
      a1.a("session", this.c);
      a1.a("whiteList", this.d);
      a1.a("blackList", this.e);
      a1.a("pkgType", this.f);
      return a1;
    }
    
    public final a c(String param1String) {
      this.c = param1String;
      return this;
    }
  }
  
  public final class b {
    public ApiCallbackData a;
    
    public final Boolean b;
    
    public b(g this$0, com.bytedance.sandboxapp.protocol.service.api.entity.a param1a) {
      Object object = param1a.a("needSession");
      if (object instanceof Boolean) {
        this.b = (Boolean)object;
        return;
      } 
      this.b = Boolean.valueOf(false);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\a\a\c\g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */