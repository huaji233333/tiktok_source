package com.bytedance.sandboxapp.c.a.a;

import d.f.b.g;
import d.f.b.l;

public abstract class b {
  public static final b Companion = new b(null);
  
  public final com.bytedance.sandboxapp.protocol.service.api.a.a apiRuntime;
  
  public final com.bytedance.sandboxapp.b.a context;
  
  private b mNextHandler;
  
  public b(com.bytedance.sandboxapp.protocol.service.api.a.a parama) {
    this.apiRuntime = parama;
    this.context = this.apiRuntime.getContext();
  }
  
  public final void addApiPreHandlerAtLast(b paramb) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mNextHandler : Lcom/bytedance/sandboxapp/c/a/a/b;
    //   6: ifnonnull -> 17
    //   9: aload_0
    //   10: aload_1
    //   11: putfield mNextHandler : Lcom/bytedance/sandboxapp/c/a/a/b;
    //   14: aload_0
    //   15: monitorexit
    //   16: return
    //   17: aload_0
    //   18: getfield mNextHandler : Lcom/bytedance/sandboxapp/c/a/a/b;
    //   21: astore_2
    //   22: aload_2
    //   23: ifnull -> 72
    //   26: aload_2
    //   27: getfield mNextHandler : Lcom/bytedance/sandboxapp/c/a/a/b;
    //   30: astore_3
    //   31: goto -> 34
    //   34: aload_3
    //   35: ifnull -> 46
    //   38: aload_2
    //   39: getfield mNextHandler : Lcom/bytedance/sandboxapp/c/a/a/b;
    //   42: astore_2
    //   43: goto -> 22
    //   46: aload_2
    //   47: ifnull -> 58
    //   50: aload_2
    //   51: aload_1
    //   52: putfield mNextHandler : Lcom/bytedance/sandboxapp/c/a/a/b;
    //   55: aload_0
    //   56: monitorexit
    //   57: return
    //   58: aload_0
    //   59: monitorexit
    //   60: return
    //   61: astore_1
    //   62: aload_0
    //   63: monitorexit
    //   64: goto -> 69
    //   67: aload_1
    //   68: athrow
    //   69: goto -> 67
    //   72: aconst_null
    //   73: astore_3
    //   74: goto -> 34
    // Exception table:
    //   from	to	target	type
    //   2	14	61	finally
    //   17	22	61	finally
    //   26	31	61	finally
    //   38	43	61	finally
    //   50	55	61	finally
  }
  
  public final com.bytedance.sandboxapp.protocol.service.api.entity.b continuePreHandleApi(a parama) {
    l.b(parama, "blockHandleApiInfo");
    b b1 = this.mNextHandler;
    if (b1 != null) {
      com.bytedance.sandboxapp.protocol.service.api.entity.b b2 = b1.triggerPreHandleApi(parama.a, parama.b);
    } else {
      b1 = null;
    } 
    return (com.bytedance.sandboxapp.protocol.service.api.entity.b)((b1 != null) ? b1 : parama.b.handleApiInvoke(parama.a));
  }
  
  protected abstract com.bytedance.sandboxapp.protocol.service.api.entity.b preHandleApi(com.bytedance.sandboxapp.protocol.service.api.entity.a parama, a parama1);
  
  public final com.bytedance.sandboxapp.protocol.service.api.entity.b triggerPreHandleApi(com.bytedance.sandboxapp.protocol.service.api.entity.a parama, a parama1) {
    l.b(parama, "apiInvokeInfo");
    l.b(parama1, "apiHandler");
    for (b b1 = this; b1 != null; b1 = b1.mNextHandler) {
      com.bytedance.sandboxapp.protocol.service.api.entity.b b2 = b1.preHandleApi(parama, parama1);
      if (b2 != null)
        return b2; 
    } 
    return null;
  }
  
  public final class a {
    public final com.bytedance.sandboxapp.protocol.service.api.entity.a a;
    
    public final a b;
    
    public a(b this$0, com.bytedance.sandboxapp.protocol.service.api.entity.a param1a, a param1a1) {
      this.a = param1a;
      this.b = param1a1;
      Boolean bool = this.b.apiInfoEntity.F;
      l.a(bool, "mApiHandler.apiInfoEntity.syncCall");
      if (bool.booleanValue())
        com.bytedance.sandboxapp.b.a.a.b.b.logOrThrow("AbsApiPreHandler", new Object[] { "只有异步 Api 才可以被 Block 执行" }); 
    }
  }
  
  public static final class b {
    private b() {}
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\c\a\a\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */