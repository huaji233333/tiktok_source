package com.bytedance.sandboxapp.a.a.c;

import com.bytedance.sandboxapp.c.a.a.d;
import com.bytedance.sandboxapp.protocol.service.api.entity.ApiCallbackData;
import org.json.JSONObject;

public abstract class e extends d {
  public e(com.bytedance.sandboxapp.c.a.b paramb, com.bytedance.sandboxapp.a.a.d.a parama) {
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
      a1.a("uploadTaskId", this.a);
      return a1;
    }
  }
  
  public final class b {
    public ApiCallbackData a;
    
    public final String b;
    
    public final String c;
    
    public final String d;
    
    public final JSONObject e;
    
    public final JSONObject f;
    
    public final Boolean g;
    
    public b(e this$0, com.bytedance.sandboxapp.protocol.service.api.entity.a param1a) {
      boolean bool;
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
      object2 = param1a.a("filePath");
      if (object2 instanceof String) {
        this.c = (String)object2;
      } else {
        if (object2 == null) {
          this.a = com.bytedance.sandboxapp.c.a.a.a.a.a(str, "filePath");
        } else {
          this.a = com.bytedance.sandboxapp.c.a.a.a.a.a(str, "filePath", "String");
        } 
        this.c = null;
      } 
      object2 = param1a.a("name");
      if (object2 instanceof String) {
        this.d = (String)object2;
      } else {
        if (object2 == null) {
          this.a = com.bytedance.sandboxapp.c.a.a.a.a.a(str, "name");
        } else {
          this.a = com.bytedance.sandboxapp.c.a.a.a.a.a(str, "name", "String");
        } 
        this.d = null;
      } 
      object2 = this.d;
      if (object2 != null && object2.equals("")) {
        bool = true;
      } else {
        bool = false;
      } 
      if (bool)
        this.a = com.bytedance.sandboxapp.c.a.a.a.a.b(str, "name"); 
      Object object1 = param1a.a("header");
      if (object1 instanceof JSONObject) {
        this.e = (JSONObject)object1;
      } else {
        this.e = null;
      } 
      object1 = param1a.a("formData");
      if (object1 instanceof JSONObject) {
        this.f = (JSONObject)object1;
      } else {
        this.f = null;
      } 
      object1 = param1a.a("useCloud");
      if (object1 instanceof Boolean) {
        this.g = (Boolean)object1;
        return;
      } 
      this.g = Boolean.valueOf(false);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\a\a\c\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */