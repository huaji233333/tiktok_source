package com.bytedance.sandboxapp.a.a.c;

import com.bytedance.sandboxapp.c.a.a.c;
import com.bytedance.sandboxapp.protocol.service.api.entity.ApiCallbackData;

public abstract class b extends c {
  public b(com.bytedance.sandboxapp.c.a.b paramb, com.bytedance.sandboxapp.a.a.d.a parama) {
    super(paramb, parama);
  }
  
  public final void a() {
    callbackData(ApiCallbackData.a.a(((com.bytedance.sandboxapp.c.a.a.a)this).apiName, com.a.a("feature is not supported in app", new Object[0]), 0).a());
  }
  
  public final void a(String paramString) {
    callbackData(ApiCallbackData.a.a(((com.bytedance.sandboxapp.c.a.a.a)this).apiName, com.a.a("checkFollowAwemeState:%s", new Object[] { paramString }), 0).a());
  }
  
  public final void b() {
    callbackData(ApiCallbackData.a.a(((com.bytedance.sandboxapp.c.a.a.a)this).apiName, com.a.a("not login", new Object[0]), 0).a());
  }
  
  public final void c() {
    callbackData(ApiCallbackData.a.a(((com.bytedance.sandboxapp.c.a.a.a)this).apiName, com.a.a("aweme uid is null", new Object[0]), 0).a());
  }
  
  public static final class a {
    private Boolean a;
    
    public static a a() {
      return new a();
    }
    
    public final a a(Boolean param1Boolean) {
      this.a = param1Boolean;
      return this;
    }
    
    public final com.bytedance.sandboxapp.b.b.a b() {
      com.bytedance.sandboxapp.b.b.a a1 = new com.bytedance.sandboxapp.b.b.a();
      a1.a("hasFollowed", this.a);
      return a1;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\a\a\c\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */