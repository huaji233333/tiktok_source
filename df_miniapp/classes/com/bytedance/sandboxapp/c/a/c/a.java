package com.bytedance.sandboxapp.c.a.c;

import com.bytedance.sandboxapp.c.a.a.b;
import d.f;
import d.f.b.g;
import d.f.b.m;
import d.f.b.u;
import d.f.b.v;
import d.f.b.x;
import d.g;
import d.k.d;
import d.k.h;
import java.util.ArrayList;
import java.util.List;

public final class a extends b {
  public static final a d = new a(null);
  
  public volatile boolean b;
  
  public final List<b.a> c = new ArrayList<b.a>();
  
  private final f e = g.a(new b(this));
  
  public a(com.bytedance.sandboxapp.c.a.b paramb) {
    super((com.bytedance.sandboxapp.protocol.service.api.a.a)paramb);
  }
  
  public final com.bytedance.sandboxapp.protocol.service.api.entity.b preHandleApi(com.bytedance.sandboxapp.protocol.service.api.entity.a parama, com.bytedance.sandboxapp.c.a.a.a parama1) {
    // Byte code:
    //   0: aload_1
    //   1: ldc 'apiInvokeInfo'
    //   3: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_2
    //   7: ldc 'apiHandler'
    //   9: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   12: aload_2
    //   13: getfield apiInfoEntity : Lcom/bytedance/sandboxapp/a/a/d/a;
    //   16: astore_3
    //   17: aload_3
    //   18: getfield F : Ljava/lang/Boolean;
    //   21: astore #4
    //   23: aload #4
    //   25: ldc 'apiInfoEntity.syncCall'
    //   27: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   30: aload #4
    //   32: invokevirtual booleanValue : ()Z
    //   35: ifeq -> 92
    //   38: aload_3
    //   39: getfield H : Lcom/bytedance/sandboxapp/a/a/d/b;
    //   42: getfield b : Ljava/lang/Boolean;
    //   45: astore_1
    //   46: aload_1
    //   47: ldc 'apiInfoEntity.foreBackSt…enBackgroundOverLimitTime'
    //   49: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   52: aload_1
    //   53: invokevirtual booleanValue : ()Z
    //   56: ifeq -> 159
    //   59: aload_0
    //   60: getfield e : Ld/f;
    //   63: invokeinterface getValue : ()Ljava/lang/Object;
    //   68: checkcast com/bytedance/sandboxapp/protocol/service/g/a
    //   71: invokeinterface isStayBackgroundOverLimitTime : ()Z
    //   76: ifeq -> 159
    //   79: new com/bytedance/sandboxapp/protocol/service/api/entity/b
    //   82: dup
    //   83: iconst_1
    //   84: aload_2
    //   85: invokevirtual buildAppInBackground : ()Lcom/bytedance/sandboxapp/protocol/service/api/entity/ApiCallbackData;
    //   88: invokespecial <init> : (ZLcom/bytedance/sandboxapp/protocol/service/api/entity/ApiCallbackData;)V
    //   91: areturn
    //   92: aload_0
    //   93: getfield b : Z
    //   96: ifeq -> 159
    //   99: aload_3
    //   100: getfield H : Lcom/bytedance/sandboxapp/a/a/d/b;
    //   103: getfield c : Ljava/lang/Boolean;
    //   106: invokevirtual booleanValue : ()Z
    //   109: ifne -> 159
    //   112: aload_0
    //   113: monitorenter
    //   114: aload_0
    //   115: getfield b : Z
    //   118: ifeq -> 149
    //   121: aload_0
    //   122: getfield c : Ljava/util/List;
    //   125: new com/bytedance/sandboxapp/c/a/a/b$a
    //   128: dup
    //   129: aload_0
    //   130: aload_1
    //   131: aload_2
    //   132: invokespecial <init> : (Lcom/bytedance/sandboxapp/c/a/a/b;Lcom/bytedance/sandboxapp/protocol/service/api/entity/a;Lcom/bytedance/sandboxapp/c/a/a/a;)V
    //   135: invokeinterface add : (Ljava/lang/Object;)Z
    //   140: pop
    //   141: getstatic com/bytedance/sandboxapp/protocol/service/api/entity/b.d : Lcom/bytedance/sandboxapp/protocol/service/api/entity/b;
    //   144: astore_1
    //   145: aload_0
    //   146: monitorexit
    //   147: aload_1
    //   148: areturn
    //   149: aload_0
    //   150: monitorexit
    //   151: goto -> 159
    //   154: astore_1
    //   155: aload_0
    //   156: monitorexit
    //   157: aload_1
    //   158: athrow
    //   159: aconst_null
    //   160: areturn
    // Exception table:
    //   from	to	target	type
    //   114	145	154	finally
  }
  
  public static final class a {
    private a() {}
  }
  
  static final class b extends m implements d.f.a.a<com.bytedance.sandboxapp.protocol.service.g.a> {
    b(a param1a) {
      super(0);
    }
    
    private com.bytedance.sandboxapp.protocol.service.g.a a() {
      com.bytedance.sandboxapp.b.a.b.b.b.d("ForeBackgroundPreHandler", new Object[] { "init mForeBackgroundService" });
      null = (com.bytedance.sandboxapp.protocol.service.g.a)this.a.context.getService(com.bytedance.sandboxapp.protocol.service.g.a.class);
      null.registerForeBackgroundListener((com.bytedance.sandboxapp.protocol.service.g.a.b)new com.bytedance.sandboxapp.protocol.service.g.a.a(this) {
            public final void a() {
              com.bytedance.sandboxapp.b.a.b.b.b.d("ForeBackgroundPreHandler", new Object[] { "onForeground" });
              if (this.a.a.b)
                synchronized (this.a.a) {
                  this.a.a.b = false;
                  for (b.a a1 : this.a.a.c)
                    this.a.a.continuePreHandleApi(a1); 
                  this.a.a.c.clear();
                }  
              com.bytedance.sandboxapp.b.a.b.b.b.d("ForeBackgroundPreHandler", new Object[] { "mIsInBackground", Boolean.valueOf(this.a.a.b) });
            }
            
            public final void b() {
              com.bytedance.sandboxapp.b.a.b.b.b.d("ForeBackgroundPreHandler", new Object[] { "onBackground" });
              if (!this.a.a.b)
                synchronized (this.a.a) {
                  this.a.a.b = true;
                }  
              com.bytedance.sandboxapp.b.a.b.b.b.d("ForeBackgroundPreHandler", new Object[] { "mIsInBackground", Boolean.valueOf(this.a.a.b) });
            }
          });
      synchronized (this.a) {
        this.a.b = null.isBackground();
        return null;
      } 
    }
  }
  
  public static final class null extends com.bytedance.sandboxapp.protocol.service.g.a.a {
    null(a.b param1b) {}
    
    public final void a() {
      com.bytedance.sandboxapp.b.a.b.b.b.d("ForeBackgroundPreHandler", new Object[] { "onForeground" });
      if (this.a.a.b)
        synchronized (this.a.a) {
          this.a.a.b = false;
          for (b.a a1 : this.a.a.c)
            this.a.a.continuePreHandleApi(a1); 
          this.a.a.c.clear();
        }  
      com.bytedance.sandboxapp.b.a.b.b.b.d("ForeBackgroundPreHandler", new Object[] { "mIsInBackground", Boolean.valueOf(this.a.a.b) });
    }
    
    public final void b() {
      com.bytedance.sandboxapp.b.a.b.b.b.d("ForeBackgroundPreHandler", new Object[] { "onBackground" });
      if (!this.a.a.b)
        synchronized (this.a.a) {
          this.a.a.b = true;
        }  
      com.bytedance.sandboxapp.b.a.b.b.b.d("ForeBackgroundPreHandler", new Object[] { "mIsInBackground", Boolean.valueOf(this.a.a.b) });
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\bytedance\sandboxapp\c\a\c\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */