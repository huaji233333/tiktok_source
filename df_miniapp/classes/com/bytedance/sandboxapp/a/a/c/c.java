package com.bytedance.sandboxapp.a.a.c;

import com.bytedance.sandboxapp.c.a.a.d;
import com.bytedance.sandboxapp.protocol.service.api.entity.ApiCallbackData;
import org.json.JSONObject;

public abstract class c extends d {
  public c(com.bytedance.sandboxapp.c.a.b paramb, com.bytedance.sandboxapp.a.a.d.a parama) {
    super(paramb, parama);
  }
  
  public abstract ApiCallbackData a(b paramb, com.bytedance.sandboxapp.protocol.service.api.entity.a parama);
  
  public final ApiCallbackData a(com.bytedance.sandboxapp.protocol.service.api.entity.a parama) {
    b b = new b(this, parama);
    return (b.a != null) ? b.a : a(b, parama);
  }
  
  public final ApiCallbackData a(String paramString) {
    return ApiCallbackData.a.a(((com.bytedance.sandboxapp.c.a.a.a)this).apiName, com.a.a("url is not valid domain, url == %s", new Object[] { paramString }), 0).a();
  }
  
  public final ApiCallbackData b(String paramString) {
    return ApiCallbackData.a.a(((com.bytedance.sandboxapp.c.a.a.a)this).apiName, com.a.a("permission denied, open \"%s\"", new Object[] { paramString }), 0).a();
  }
  
  public final ApiCallbackData c(String paramString) {
    return ApiCallbackData.a.a(((com.bytedance.sandboxapp.c.a.a.a)this).apiName, com.a.a("no such file or directory \"%s\"", new Object[] { paramString }), 0).a();
  }
  
  public static final class a {
    private Integer a;
    
    public static a a() {
      return new a();
    }
    
    public final a a(Integer param1Integer) {
      this.a = param1Integer;
      return this;
    }
    
    public final com.bytedance.sandboxapp.b.b.a b() {
      com.bytedance.sandboxapp.b.b.a a1 = new com.bytedance.sandboxapp.b.b.a();
      a1.a("downloadTaskId", this.a);
      return a1;
    }
  }
  
  public final class b {
    public ApiCallbackData a;
    
    public final String b;
    
    public final JSONObject c;
    
    public final String d;
    
    public final Boolean e;
    
    public b(c this$0, com.bytedance.sandboxapp.protocol.service.api.entity.a param1a) {
      String str = param1a.b;
      Object object2 = param1a.a("url");
      if (object2 instanceof String) {
        this.b = (String)object2;
      } else {
        if (object2 == null) {
          this.a = com.bytedance.sandboxapp.c.a.a.a.a.a(str, "url");
        } else {
          this.a = com.bytedance.sandboxapp.c.a.a.a.a.a(str, "url", "String");
        } 
        this.b = null;
      } 
      Object object1 = param1a.a("header");
      if (object1 instanceof JSONObject) {
        this.c = (JSONObject)object1;
      } else {
        this.c = null;
      } 
      object1 = param1a.a("filePath");
      if (object1 instanceof String) {
        this.d = (String)object1;
      } else {
        this.d = null;
      } 
      object1 = param1a.a("useCloud");
      if (object1 instanceof Boolean) {
        this.e = (Boolean)object1;
        return;
      } 
      this.e = Boolean.valueOf(false);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\a\a\c\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */