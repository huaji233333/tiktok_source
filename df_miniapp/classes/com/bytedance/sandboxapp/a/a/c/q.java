package com.bytedance.sandboxapp.a.a.c;

import com.bytedance.sandboxapp.c.a.a.c;
import com.bytedance.sandboxapp.c.a.b;
import com.bytedance.sandboxapp.protocol.service.api.entity.ApiCallbackData;

public abstract class q extends c {
  public q(b paramb, com.bytedance.sandboxapp.a.a.d.a parama) {
    super(paramb, parama);
  }
  
  public abstract void a(a parama, com.bytedance.sandboxapp.protocol.service.api.entity.a parama1);
  
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
    
    public final Integer b;
    
    public final String c;
    
    public a(q this$0, com.bytedance.sandboxapp.protocol.service.api.entity.a param1a) {
      String str = param1a.b;
      Object object2 = param1a.a("uploadTaskId");
      if (object2 instanceof Integer) {
        this.b = (Integer)object2;
      } else {
        if (object2 == null) {
          this.a = com.bytedance.sandboxapp.c.a.a.a.a.a(str, "uploadTaskId");
        } else {
          this.a = com.bytedance.sandboxapp.c.a.a.a.a.a(str, "uploadTaskId", "Integer");
        } 
        this.b = null;
      } 
      Object object1 = param1a.a("operationType");
      if (object1 instanceof String) {
        this.c = (String)object1;
        return;
      } 
      if (object1 == null) {
        this.a = com.bytedance.sandboxapp.c.a.a.a.a.a(str, "operationType");
      } else {
        this.a = com.bytedance.sandboxapp.c.a.a.a.a.a(str, "operationType", "String");
      } 
      this.c = null;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\a\a\c\q.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */