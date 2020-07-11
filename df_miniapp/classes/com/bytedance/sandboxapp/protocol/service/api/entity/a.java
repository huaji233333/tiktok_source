package com.bytedance.sandboxapp.protocol.service.api.entity;

import com.bytedance.sandboxapp.protocol.service.api.b.c;
import d.f.b.g;
import d.f.b.l;

public final class a {
  public a a;
  
  public final String b;
  
  public final com.bytedance.sandboxapp.protocol.service.api.a.a c;
  
  public final boolean d;
  
  private final com.bytedance.sandboxapp.protocol.service.api.b.a e;
  
  private final c f;
  
  private final com.bytedance.sandboxapp.protocol.service.api.b.b g;
  
  private a(String paramString, com.bytedance.sandboxapp.protocol.service.api.a.a parama, com.bytedance.sandboxapp.protocol.service.api.b.a parama1, boolean paramBoolean, c paramc, com.bytedance.sandboxapp.protocol.service.api.b.b paramb) {
    this.b = paramString;
    this.c = parama;
    this.e = parama1;
    this.d = paramBoolean;
    this.f = paramc;
    this.g = paramb;
  }
  
  public final com.bytedance.sandboxapp.b.b.a a() {
    return this.e.toJson();
  }
  
  public final <T> T a(String paramString) {
    l.b(paramString, "key");
    return (T)this.e.getParam(paramString);
  }
  
  public final boolean a(ApiCallbackData paramApiCallbackData) {
    l.b(paramApiCallbackData, "apiCallbackData");
    com.bytedance.sandboxapp.protocol.service.api.b.b b1 = this.g;
    if (b1 == null)
      return false; 
    b1.executeCallback(paramApiCallbackData);
    a a1 = this.a;
    if (a1 != null)
      a1.a(paramApiCallbackData); 
    return true;
  }
  
  public final boolean a(Runnable paramRunnable) {
    c c1 = this.f;
    if (c1 == null)
      return false; 
    c1.scheduleHandle(paramRunnable);
    return true;
  }
  
  public static interface a {
    void a(ApiCallbackData param1ApiCallbackData);
  }
  
  public static final class b {
    public static final a b = new a(null);
    
    public boolean a;
    
    private c c;
    
    private com.bytedance.sandboxapp.protocol.service.api.b.b d;
    
    private final com.bytedance.sandboxapp.protocol.service.api.a.a e;
    
    private final String f;
    
    private final com.bytedance.sandboxapp.protocol.service.api.b.a g;
    
    private b(com.bytedance.sandboxapp.protocol.service.api.a.a param1a, String param1String, com.bytedance.sandboxapp.protocol.service.api.b.a param1a1) {
      this.e = param1a;
      this.f = param1String;
      this.g = param1a1;
    }
    
    public static final b a(com.bytedance.sandboxapp.protocol.service.api.a.a param1a, String param1String, com.bytedance.sandboxapp.protocol.service.api.b.a param1a1) {
      return a.a(param1a, param1String, param1a1);
    }
    
    public final b a(c param1c, com.bytedance.sandboxapp.protocol.service.api.b.b param1b) {
      l.b(param1c, "asyncApiHandleScheduler");
      l.b(param1b, "asyncApiCallbackExecutor");
      this.c = param1c;
      this.d = param1b;
      return this;
    }
    
    public final a a() {
      return new a(this.f, this.e, this.g, this.a, this.c, this.d, null);
    }
    
    public static final class a {
      private a() {}
      
      public static a.b a(com.bytedance.sandboxapp.protocol.service.api.a.a param2a, String param2String, com.bytedance.sandboxapp.protocol.service.api.b.a param2a1) {
        l.b(param2a, "apiRuntime");
        l.b(param2String, "apiName");
        l.b(param2a1, "param");
        return new a.b(param2a, param2String, param2a1, null);
      }
    }
  }
  
  public static final class a {
    private a() {}
    
    public static a.b a(com.bytedance.sandboxapp.protocol.service.api.a.a param1a, String param1String, com.bytedance.sandboxapp.protocol.service.api.b.a param1a1) {
      l.b(param1a, "apiRuntime");
      l.b(param1String, "apiName");
      l.b(param1a1, "param");
      return new a.b(param1a, param1String, param1a1, null);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\protocol\service\api\entity\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */