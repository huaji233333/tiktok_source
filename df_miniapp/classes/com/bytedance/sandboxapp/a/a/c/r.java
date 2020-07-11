package com.bytedance.sandboxapp.a.a.c;

import com.bytedance.sandboxapp.c.a.a.c;
import com.bytedance.sandboxapp.c.a.b;
import com.bytedance.sandboxapp.protocol.service.api.entity.ApiCallbackData;

public abstract class r extends c {
  public r(b paramb, com.bytedance.sandboxapp.a.a.d.a parama) {
    super(paramb, parama);
  }
  
  public final void a() {
    callbackData(ApiCallbackData.a.a(((com.bytedance.sandboxapp.c.a.a.a)this).apiName, com.a.a("wechat is not installed", new Object[0]), 0).a());
  }
  
  public abstract void a(a parama, com.bytedance.sandboxapp.protocol.service.api.entity.a parama1);
  
  public final void a(String paramString) {
    callbackData(ApiCallbackData.a.a(((com.bytedance.sandboxapp.c.a.a.a)this).apiName, com.a.a("pay fail:%s", new Object[] { paramString }), 0).a());
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
    
    public final Integer d;
    
    public final Integer e;
    
    public final Integer f;
    
    public final Integer g;
    
    public a(r this$0, com.bytedance.sandboxapp.protocol.service.api.entity.a param1a) {
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
      object2 = param1a.a("referer");
      if (object2 instanceof String) {
        this.c = (String)object2;
      } else {
        if (object2 == null) {
          this.a = com.bytedance.sandboxapp.c.a.a.a.a.a(str, "referer");
        } else {
          this.a = com.bytedance.sandboxapp.c.a.a.a.a.a(str, "referer", "String");
        } 
        this.c = null;
      } 
      Object object1 = param1a.a("x");
      if (object1 instanceof Integer) {
        this.d = (Integer)object1;
      } else {
        this.d = Integer.valueOf(0);
      } 
      object1 = param1a.a("y");
      if (object1 instanceof Integer) {
        this.e = (Integer)object1;
      } else {
        this.e = Integer.valueOf(0);
      } 
      object1 = param1a.a("width");
      if (object1 instanceof Integer) {
        this.f = (Integer)object1;
      } else {
        this.f = Integer.valueOf(0);
      } 
      object1 = param1a.a("height");
      if (object1 instanceof Integer) {
        this.g = (Integer)object1;
        return;
      } 
      this.g = Integer.valueOf(0);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\a\a\c\r.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */