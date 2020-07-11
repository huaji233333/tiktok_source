package com.bytedance.sandboxapp.a.a.c;

import com.bytedance.sandboxapp.c.a.a.c;
import com.bytedance.sandboxapp.c.a.b;
import com.bytedance.sandboxapp.protocol.service.api.entity.ApiCallbackData;

public abstract class n extends c {
  public n(b paramb, com.bytedance.sandboxapp.a.a.d.a parama) {
    super(paramb, parama);
  }
  
  public abstract void a(a parama, com.bytedance.sandboxapp.protocol.service.api.entity.a parama1);
  
  public final void a(String paramString) {
    callbackData(ApiCallbackData.a.a(((com.bytedance.sandboxapp.c.a.a.a)this).apiName, com.a.a("not in valid domains, schema == %s", new Object[] { paramString }), 0).a());
  }
  
  public final void a(String paramString1, String paramString2) {
    callbackData(ApiCallbackData.a.a(((com.bytedance.sandboxapp.c.a.a.a)this).apiName, com.a.a("host open schema fail: %s, schema == %s", new Object[] { paramString1, paramString2 }), 0).a());
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
    
    public final Boolean c;
    
    public final Boolean d;
    
    public final Integer e;
    
    public a(n this$0, com.bytedance.sandboxapp.protocol.service.api.entity.a param1a) {
      String str = param1a.b;
      Object object2 = param1a.a("schema");
      if (object2 instanceof String) {
        this.b = (String)object2;
      } else {
        if (object2 == null) {
          this.a = com.bytedance.sandboxapp.c.a.a.a.a.a(str, "schema");
        } else {
          this.a = com.bytedance.sandboxapp.c.a.a.a.a.a(str, "schema", "String");
        } 
        this.b = null;
      } 
      Object object1 = param1a.a("killCurrentProcess");
      if (object1 instanceof Boolean) {
        this.c = (Boolean)object1;
      } else {
        this.c = Boolean.valueOf(false);
      } 
      object1 = param1a.a("forceColdBoot");
      if (object1 instanceof Boolean) {
        this.d = (Boolean)object1;
      } else {
        this.d = Boolean.valueOf(false);
      } 
      object1 = param1a.a("toolbarStyle");
      if (object1 instanceof Integer) {
        this.e = (Integer)object1;
        return;
      } 
      this.e = Integer.valueOf(0);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\a\a\c\n.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */