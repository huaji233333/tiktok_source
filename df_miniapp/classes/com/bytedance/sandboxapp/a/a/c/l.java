package com.bytedance.sandboxapp.a.a.c;

import com.bytedance.sandboxapp.c.a.a.c;
import com.bytedance.sandboxapp.c.a.b;
import com.bytedance.sandboxapp.protocol.service.api.entity.ApiCallbackData;
import org.json.JSONObject;

public abstract class l extends c {
  public l(b paramb, com.bytedance.sandboxapp.a.a.d.a parama) {
    super(paramb, parama);
  }
  
  public final void a() {
    callbackData(ApiCallbackData.a.a(((com.bytedance.sandboxapp.c.a.a.a)this).apiName, com.a.a("feature is not supported in app", new Object[0]), 0).a());
  }
  
  public abstract void a(a parama, com.bytedance.sandboxapp.protocol.service.api.entity.a parama1);
  
  public final void a(String paramString) {
    callbackData(ApiCallbackData.a.a(((com.bytedance.sandboxapp.c.a.a.a)this).apiName, com.a.a("open schema fail, schema is %s", new Object[] { paramString }), 0).a());
  }
  
  public final void b() {
    callbackData(ApiCallbackData.a.a(((com.bytedance.sandboxapp.c.a.a.a)this).apiName, com.a.a("schema is invalid", new Object[0]), 0).a());
  }
  
  public final void handleApi(com.bytedance.sandboxapp.protocol.service.api.entity.a parama) {
    a a1 = new a(this, parama);
    if (a1.a != null) {
      callbackData(a1.a);
      return;
    } 
    a(a1, parama);
  }
  
  public final class a {
    public ApiCallbackData a;
    
    public final String b;
    
    public final String c;
    
    public final JSONObject d;
    
    public a(l this$0, com.bytedance.sandboxapp.protocol.service.api.entity.a param1a) {
      boolean bool;
      String str = param1a.b;
      Object object2 = param1a.a("type");
      if (object2 instanceof String) {
        this.b = (String)object2;
      } else {
        if (object2 == null) {
          this.a = com.bytedance.sandboxapp.c.a.a.a.a.a(str, "type");
        } else {
          this.a = com.bytedance.sandboxapp.c.a.a.a.a.a(str, "type", "String");
        } 
        this.b = null;
      } 
      object2 = this.b;
      if (object2 != null && (object2.equals("quickapp") || this.b.equals("schema") || this.b.equals("microapp") || this.b.equals("market"))) {
        bool = true;
      } else {
        bool = false;
      } 
      if (!bool)
        this.a = com.bytedance.sandboxapp.c.a.a.a.a.b(str, "type"); 
      object2 = param1a.a("schema");
      if (object2 instanceof String) {
        this.c = (String)object2;
      } else {
        if (object2 == null) {
          this.a = com.bytedance.sandboxapp.c.a.a.a.a.a(str, "schema");
        } else {
          this.a = com.bytedance.sandboxapp.c.a.a.a.a.a(str, "schema", "String");
        } 
        this.c = null;
      } 
      Object object1 = param1a.a("data");
      if (object1 instanceof JSONObject) {
        this.d = (JSONObject)object1;
        return;
      } 
      this.d = null;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\a\a\c\l.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */