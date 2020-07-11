package com.bytedance.ies.bullet.kit.rn.internal;

import android.net.Uri;
import android.text.TextUtils;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.CatalystInstanceImpl;
import com.facebook.react.bridge.PageFinishedListener;
import com.facebook.react.bridge.RNDegradeExceptionHandler;
import com.facebook.react.bridge.ReactContext;
import d.x;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

public final class c {
  public static com.bytedance.ies.bullet.kit.rn.a q;
  
  public static final a r = new a(null);
  
  public File a;
  
  public a.f b;
  
  public boolean c;
  
  public final com.bytedance.ies.bullet.kit.rn.j d;
  
  public final com.bytedance.ies.bullet.b.g.a.b e;
  
  public final com.bytedance.ies.bullet.kit.rn.c.c f;
  
  public final String g;
  
  public com.bytedance.ies.bullet.b.c.a h;
  
  public final com.bytedance.ies.bullet.kit.rn.b.a i;
  
  public final com.bytedance.ies.bullet.b.e.a.h j;
  
  final List<com.bytedance.ies.bullet.kit.rn.k> k;
  
  public final List<Object> l;
  
  public final List<com.bytedance.ies.bullet.kit.rn.c> m;
  
  public final List<com.bytedance.ies.bullet.kit.rn.e> n;
  
  public com.bytedance.ies.bullet.kit.rn.b o;
  
  public boolean p;
  
  private b s;
  
  private long t;
  
  public c(com.bytedance.ies.bullet.kit.rn.j paramj, com.bytedance.ies.bullet.b.g.a.b paramb, com.bytedance.ies.bullet.kit.rn.c.c paramc, String paramString, com.bytedance.ies.bullet.b.c.a parama, com.bytedance.ies.bullet.kit.rn.b.a parama1, com.bytedance.ies.bullet.b.e.a.h paramh, List<com.bytedance.ies.bullet.kit.rn.k> paramList, List<Object> paramList1, List<com.bytedance.ies.bullet.kit.rn.c> paramList2, List<com.bytedance.ies.bullet.kit.rn.e> paramList3, com.bytedance.ies.bullet.kit.rn.b paramb1, b paramb2, boolean paramBoolean, long paramLong) {
    this.d = paramj;
    this.e = paramb;
    this.f = paramc;
    this.g = paramString;
    this.h = parama;
    this.i = parama1;
    this.j = paramh;
    this.k = paramList;
    this.l = paramList1;
    this.m = paramList2;
    this.n = paramList3;
    this.o = paramb1;
    this.s = paramb2;
    this.p = paramBoolean;
    this.t = 0L;
    this.b = new a.f();
  }
  
  public static void a(ReactContext paramReactContext, String paramString) {
    CatalystInstance catalystInstance = paramReactContext.getCatalystInstance();
    if (catalystInstance != null)
      if (catalystInstance != null) {
        CatalystInstanceImpl catalystInstanceImpl = (CatalystInstanceImpl)catalystInstance;
        if (catalystInstanceImpl != null) {
          catalystInstanceImpl.loadScriptFromFile(paramString, paramString, false);
          return;
        } 
      } else {
        throw new d.u("null cannot be cast to non-null type com.facebook.react.bridge.CatalystInstanceImpl");
      }  
  }
  
  public final void a(c paramc) {
    // Byte code:
    //   0: aload_0
    //   1: getfield f : Lcom/bytedance/ies/bullet/kit/rn/c/c;
    //   4: getfield b : Lcom/bytedance/ies/bullet/b/i/f;
    //   7: invokeinterface b : ()Ljava/lang/Object;
    //   12: checkcast java/lang/CharSequence
    //   15: astore #4
    //   17: iconst_0
    //   18: istore_3
    //   19: aload #4
    //   21: ifnull -> 42
    //   24: aload #4
    //   26: invokeinterface length : ()I
    //   31: ifne -> 37
    //   34: goto -> 42
    //   37: iconst_0
    //   38: istore_2
    //   39: goto -> 44
    //   42: iconst_1
    //   43: istore_2
    //   44: iload_2
    //   45: ifne -> 240
    //   48: aload_0
    //   49: getfield f : Lcom/bytedance/ies/bullet/kit/rn/c/c;
    //   52: getfield e : Lcom/bytedance/ies/bullet/b/i/f;
    //   55: invokeinterface b : ()Ljava/lang/Object;
    //   60: checkcast java/lang/CharSequence
    //   63: astore #4
    //   65: aload #4
    //   67: ifnull -> 82
    //   70: iload_3
    //   71: istore_2
    //   72: aload #4
    //   74: invokeinterface length : ()I
    //   79: ifne -> 84
    //   82: iconst_1
    //   83: istore_2
    //   84: iload_2
    //   85: ifeq -> 91
    //   88: goto -> 240
    //   91: aload_0
    //   92: getfield p : Z
    //   95: ifne -> 155
    //   98: aload_1
    //   99: invokevirtual a : ()Z
    //   102: ifne -> 155
    //   105: aload_0
    //   106: getfield h : Lcom/bytedance/ies/bullet/b/c/a;
    //   109: astore #4
    //   111: aload #4
    //   113: ifnull -> 154
    //   116: aload #4
    //   118: ldc 'rn_base_android'
    //   120: aconst_null
    //   121: iconst_2
    //   122: aconst_null
    //   123: invokestatic a : (Ljava/lang/String;Landroid/net/Uri;ILjava/lang/Object;)Landroid/net/Uri;
    //   126: new com/bytedance/ies/bullet/kit/rn/internal/c$s
    //   129: dup
    //   130: aload_0
    //   131: aload_1
    //   132: invokespecial <init> : (Lcom/bytedance/ies/bullet/kit/rn/internal/c;Lcom/bytedance/ies/bullet/kit/rn/internal/c$c;)V
    //   135: checkcast d/f/a/b
    //   138: new com/bytedance/ies/bullet/kit/rn/internal/c$t
    //   141: dup
    //   142: aload_0
    //   143: invokespecial <init> : (Lcom/bytedance/ies/bullet/kit/rn/internal/c;)V
    //   146: checkcast d/f/a/b
    //   149: invokeinterface b : (Landroid/net/Uri;Ld/f/a/b;Ld/f/a/b;)V
    //   154: return
    //   155: aload_0
    //   156: getfield h : Lcom/bytedance/ies/bullet/b/c/a;
    //   159: astore #4
    //   161: aload #4
    //   163: ifnull -> 239
    //   166: ldc 'rn_base_android'
    //   168: aconst_null
    //   169: iconst_2
    //   170: aconst_null
    //   171: invokestatic a : (Ljava/lang/String;Landroid/net/Uri;ILjava/lang/Object;)Landroid/net/Uri;
    //   174: astore #5
    //   176: ldc 'rn_snapshot'
    //   178: aconst_null
    //   179: iconst_2
    //   180: aconst_null
    //   181: invokestatic a : (Ljava/lang/String;Landroid/net/Uri;ILjava/lang/Object;)Landroid/net/Uri;
    //   184: astore #7
    //   186: new com/bytedance/ies/bullet/kit/rn/internal/c$q
    //   189: dup
    //   190: aload #7
    //   192: aload #4
    //   194: aload_0
    //   195: iconst_0
    //   196: aload_0
    //   197: aload_0
    //   198: aload_1
    //   199: invokespecial <init> : (Ljava/lang/Object;Lcom/bytedance/ies/bullet/b/c/a;Lcom/bytedance/ies/bullet/kit/rn/internal/c;ZLcom/bytedance/ies/bullet/kit/rn/internal/c;Lcom/bytedance/ies/bullet/kit/rn/internal/c;Lcom/bytedance/ies/bullet/kit/rn/internal/c$c;)V
    //   202: astore #6
    //   204: new com/bytedance/ies/bullet/kit/rn/internal/c$r
    //   207: dup
    //   208: aload #7
    //   210: aload #4
    //   212: aload_0
    //   213: iconst_0
    //   214: aload_0
    //   215: aload_0
    //   216: aload_1
    //   217: invokespecial <init> : (Ljava/lang/Object;Lcom/bytedance/ies/bullet/b/c/a;Lcom/bytedance/ies/bullet/kit/rn/internal/c;ZLcom/bytedance/ies/bullet/kit/rn/internal/c;Lcom/bytedance/ies/bullet/kit/rn/internal/c;Lcom/bytedance/ies/bullet/kit/rn/internal/c$c;)V
    //   220: checkcast d/f/a/b
    //   223: astore_1
    //   224: aload #4
    //   226: aload #5
    //   228: aload #6
    //   230: checkcast d/f/a/b
    //   233: aload_1
    //   234: invokeinterface b : (Landroid/net/Uri;Ld/f/a/b;Ld/f/a/b;)V
    //   239: return
    //   240: aload_0
    //   241: new java/lang/Exception
    //   244: dup
    //   245: ldc_w 'channel name or module name is null'
    //   248: invokespecial <init> : (Ljava/lang/String;)V
    //   251: invokevirtual a : (Ljava/lang/Exception;)V
    //   254: return
  }
  
  public final void a(ReactInstanceManager paramReactInstanceManager) {
    this.s.a(this, paramReactInstanceManager);
  }
  
  public final void a(ReactInstanceManager paramReactInstanceManager, String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: getfield i : Lcom/bytedance/ies/bullet/kit/rn/b/a;
    //   4: astore #7
    //   6: iconst_0
    //   7: istore #4
    //   9: aload #7
    //   11: ifnull -> 172
    //   14: aload #7
    //   16: invokestatic currentTimeMillis : ()J
    //   19: putfield s : J
    //   22: aload #7
    //   24: invokevirtual d : ()Lcom/bytedance/ies/bullet/b/h/g;
    //   27: checkcast com/bytedance/ies/bullet/b/h/f
    //   30: astore #8
    //   32: new org/json/JSONObject
    //   35: dup
    //   36: invokespecial <init> : ()V
    //   39: astore #9
    //   41: aload #9
    //   43: ldc_w 'trigger'
    //   46: ldc_w 'prepare_rn_start'
    //   49: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   52: pop
    //   53: aload #9
    //   55: ldc_w 'is_first_screen'
    //   58: ldc_w 'first_screen'
    //   61: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   64: pop
    //   65: new org/json/JSONObject
    //   68: dup
    //   69: invokespecial <init> : ()V
    //   72: astore #6
    //   74: aload #7
    //   76: getfield r : J
    //   79: lconst_0
    //   80: lcmp
    //   81: ifle -> 99
    //   84: aload #7
    //   86: getfield q : J
    //   89: lconst_0
    //   90: lcmp
    //   91: ifle -> 99
    //   94: iconst_1
    //   95: istore_3
    //   96: goto -> 101
    //   99: iconst_0
    //   100: istore_3
    //   101: iload_3
    //   102: ifeq -> 112
    //   105: aload #6
    //   107: astore #5
    //   109: goto -> 115
    //   112: aconst_null
    //   113: astore #5
    //   115: aload #5
    //   117: ifnull -> 140
    //   120: aload #5
    //   122: ldc_w 'dynamic_update_interval'
    //   125: aload #7
    //   127: getfield r : J
    //   130: aload #7
    //   132: getfield q : J
    //   135: lsub
    //   136: invokevirtual put : (Ljava/lang/String;J)Lorg/json/JSONObject;
    //   139: pop
    //   140: aload #6
    //   142: ldc_w 'event_ts'
    //   145: aload #7
    //   147: getfield s : J
    //   150: invokevirtual put : (Ljava/lang/String;J)Lorg/json/JSONObject;
    //   153: pop
    //   154: aload #7
    //   156: ldc_w 'hybrid_app_monitor_load_url_event'
    //   159: aload #8
    //   161: aload #9
    //   163: aload #6
    //   165: aconst_null
    //   166: bipush #16
    //   168: aconst_null
    //   169: invokestatic a : (Lcom/bytedance/ies/bullet/b/h/a;Ljava/lang/String;Lcom/bytedance/ies/bullet/b/h/f;Lorg/json/JSONObject;Lorg/json/JSONObject;Lorg/json/JSONObject;ILjava/lang/Object;)V
    //   172: new com/bytedance/ies/bullet/kit/rn/internal/c$p
    //   175: dup
    //   176: aload_0
    //   177: invokespecial <init> : (Lcom/bytedance/ies/bullet/kit/rn/internal/c;)V
    //   180: checkcast d/f/a/b
    //   183: astore #6
    //   185: aload_1
    //   186: invokevirtual getCurrentReactContext : ()Lcom/facebook/react/bridge/ReactContext;
    //   189: astore #5
    //   191: aload #5
    //   193: ifnull -> 270
    //   196: aload #6
    //   198: aload #5
    //   200: invokeinterface invoke : (Ljava/lang/Object;)Ljava/lang/Object;
    //   205: pop
    //   206: aload_2
    //   207: checkcast java/lang/CharSequence
    //   210: astore #6
    //   212: aload #6
    //   214: ifnull -> 358
    //   217: iload #4
    //   219: istore_3
    //   220: aload #6
    //   222: invokeinterface length : ()I
    //   227: ifne -> 233
    //   230: goto -> 358
    //   233: iload_3
    //   234: ifne -> 243
    //   237: aload #5
    //   239: aload_2
    //   240: invokestatic a : (Lcom/facebook/react/bridge/ReactContext;Ljava/lang/String;)V
    //   243: aload_0
    //   244: getfield i : Lcom/bytedance/ies/bullet/kit/rn/b/a;
    //   247: astore_2
    //   248: aload_2
    //   249: ifnull -> 257
    //   252: aload_2
    //   253: iconst_1
    //   254: invokevirtual a : (Z)V
    //   257: aload_0
    //   258: aload_1
    //   259: invokevirtual a : (Lcom/facebook/react/ReactInstanceManager;)V
    //   262: return
    //   263: astore_1
    //   264: aload_0
    //   265: aload_1
    //   266: invokevirtual a : (Ljava/lang/Exception;)V
    //   269: return
    //   270: aload_1
    //   271: new com/bytedance/ies/bullet/kit/rn/internal/c$n
    //   274: dup
    //   275: aload_0
    //   276: aload #6
    //   278: aload_2
    //   279: aload_1
    //   280: invokespecial <init> : (Lcom/bytedance/ies/bullet/kit/rn/internal/c;Ld/f/a/b;Ljava/lang/String;Lcom/facebook/react/ReactInstanceManager;)V
    //   283: checkcast com/facebook/react/ReactInstanceManager$ReactInstanceEventListener
    //   286: invokevirtual addReactInstanceEventListener : (Lcom/facebook/react/ReactInstanceManager$ReactInstanceEventListener;)V
    //   289: aload_1
    //   290: invokevirtual hasStartedCreatingInitialContext : ()Z
    //   293: ifne -> 300
    //   296: aload_1
    //   297: invokevirtual createReactContextInBackground : ()V
    //   300: aload_0
    //   301: iconst_0
    //   302: putfield c : Z
    //   305: aload_0
    //   306: getfield t : J
    //   309: lconst_0
    //   310: lcmp
    //   311: ifle -> 357
    //   314: aload_0
    //   315: new a/f
    //   318: dup
    //   319: invokespecial <init> : ()V
    //   322: putfield b : La/f;
    //   325: aload_0
    //   326: getfield t : J
    //   329: invokestatic a : (J)La/i;
    //   332: new com/bytedance/ies/bullet/kit/rn/internal/c$o
    //   335: dup
    //   336: aload_0
    //   337: invokespecial <init> : (Lcom/bytedance/ies/bullet/kit/rn/internal/c;)V
    //   340: checkcast a/g
    //   343: getstatic a/i.b : Ljava/util/concurrent/Executor;
    //   346: aload_0
    //   347: getfield b : La/f;
    //   350: invokevirtual b : ()La/d;
    //   353: invokevirtual a : (La/g;Ljava/util/concurrent/Executor;La/d;)La/i;
    //   356: pop
    //   357: return
    //   358: iconst_1
    //   359: istore_3
    //   360: goto -> 233
    // Exception table:
    //   from	to	target	type
    //   196	212	263	java/lang/Exception
    //   220	230	263	java/lang/Exception
    //   237	243	263	java/lang/Exception
    //   243	248	263	java/lang/Exception
    //   252	257	263	java/lang/Exception
    //   257	262	263	java/lang/Exception
  }
  
  public final void a(Exception paramException) {
    this.s.a(this, paramException);
  }
  
  public final void b(c paramc) {
    com.bytedance.ies.bullet.b.c.a a1 = this.h;
    if (a1 != null) {
      String str2 = (String)this.f.b.b();
      String str1 = str2;
      if (str2 == null)
        str1 = ""; 
      a1.b(com.bytedance.ies.bullet.b.c.c.a(str1, null, 2, null), new j(this, paramc), new k(this));
    } 
  }
  
  public static final class a {
    private a() {}
  }
  
  public static interface b {
    void a(c param1c, ReactInstanceManager param1ReactInstanceManager);
    
    void a(c param1c, Exception param1Exception);
  }
  
  public static final class c {
    public String a;
    
    public final boolean a() {
      boolean bool;
      CharSequence charSequence = this.a;
      if (charSequence == null || charSequence.length() == 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return (!bool && (new File(this.a)).exists());
    }
  }
  
  static final class d extends d.f.b.m implements d.f.a.b<Uri, x> {
    d(c param1c, c.c param1c1) {
      super(1);
    }
  }
  
  static final class e extends d.f.b.m implements d.f.a.b<Throwable, x> {
    e(c param1c, c.c param1c1) {
      super(1);
    }
  }
  
  static final class f extends d.f.b.m implements d.f.a.b<Uri, x> {
    f(c param1c) {
      super(1);
    }
  }
  
  static final class g extends d.f.b.m implements d.f.a.b<Throwable, x> {
    g(c param1c) {
      super(1);
    }
  }
  
  static final class h extends d.f.b.m implements d.f.a.b<Uri, x> {
    h(c param1c, c.c param1c1) {
      super(1);
    }
  }
  
  static final class i extends d.f.b.m implements d.f.a.b<Throwable, x> {
    i(c param1c, c.c param1c1) {
      super(1);
    }
  }
  
  static final class j extends d.f.b.m implements d.f.a.b<File, x> {
    j(c param1c, c.c param1c1) {
      super(1);
    }
  }
  
  static final class k extends d.f.b.m implements d.f.a.b<Throwable, x> {
    k(c param1c) {
      super(1);
    }
  }
  
  static final class l extends d.f.b.m implements d.f.a.b<File, x> {
    l(c param1c, c.c param1c1) {
      super(1);
    }
  }
  
  static final class m extends d.f.b.m implements d.f.a.b<Throwable, x> {
    m(c param1c) {
      super(1);
    }
  }
  
  static final class n implements ReactInstanceManager.ReactInstanceEventListener {
    n(c param1c, d.f.a.b param1b, String param1String, ReactInstanceManager param1ReactInstanceManager) {}
    
    public final void onReactContextInitialized(ReactContext param1ReactContext) {
      try {
        d.f.a.b b1 = this.b;
        d.f.b.l.a(param1ReactContext, "context");
        b1.invoke(param1ReactContext);
        if (this.a.c)
          return; 
        this.a.b.c();
        if (!TextUtils.isEmpty(this.c))
          c.a(param1ReactContext, this.c); 
        com.bytedance.ies.bullet.kit.rn.b.a a = this.a.i;
        if (a != null)
          a.a(false); 
        this.a.a(this.d);
        return;
      } catch (Exception exception) {
        this.a.a(exception);
        return;
      } 
    }
  }
  
  static final class o<TTaskResult, TContinuationResult> implements a.g<Void, Void> {
    o(c param1c) {}
  }
  
  static final class p extends d.f.b.m implements d.f.a.b<ReactContext, x> {
    p(c param1c) {
      super(1);
    }
  }
  
  static final class null implements PageFinishedListener {
    null(c.p param1p) {}
    
    public final void upLoad(JSONObject param1JSONObject) {
      com.bytedance.ies.bullet.kit.rn.b.a a = this.a.a.i;
      if (a != null) {
        boolean bool1;
        a.u = System.currentTimeMillis();
        com.bytedance.ies.bullet.b.h.f f = (com.bytedance.ies.bullet.b.h.f)a.d();
        JSONObject jSONObject4 = new JSONObject();
        jSONObject4.put("trigger", "on_load");
        jSONObject4.put("is_first_screen", "first_screen");
        JSONObject jSONObject1 = new JSONObject();
        long l = a.r;
        boolean bool2 = true;
        if (l > 0L && a.q > 0L) {
          bool1 = true;
        } else {
          bool1 = false;
        } 
        JSONObject jSONObject3 = null;
        if (bool1) {
          jSONObject2 = jSONObject1;
        } else {
          jSONObject2 = null;
        } 
        if (jSONObject2 != null)
          jSONObject2.put("dynamic_update_interval", a.r - a.q); 
        if (a.t > 0L && a.s > 0L) {
          bool1 = true;
        } else {
          bool1 = false;
        } 
        if (bool1) {
          jSONObject2 = jSONObject1;
        } else {
          jSONObject2 = null;
        } 
        if (jSONObject2 != null)
          jSONObject2.put("prepare_rn_interval", a.t - a.s); 
        if (a.u > 0L && a.t > 0L) {
          bool1 = true;
        } else {
          bool1 = false;
        } 
        if (bool1) {
          jSONObject2 = jSONObject1;
        } else {
          jSONObject2 = null;
        } 
        if (jSONObject2 != null)
          jSONObject2.put("load_interval", a.u - a.t); 
        if (a.u > 0L && a.p > 0L) {
          bool1 = bool2;
        } else {
          bool1 = false;
        } 
        JSONObject jSONObject2 = jSONObject3;
        if (bool1)
          jSONObject2 = jSONObject1; 
        if (jSONObject2 != null)
          jSONObject2.put("page_load_interval", a.u - a.p); 
        jSONObject1.put("event_ts", a.u);
        com.bytedance.ies.bullet.b.h.a.a((com.bytedance.ies.bullet.b.h.a)a, "hybrid_app_monitor_load_url_event", f, jSONObject4, jSONObject1, null, 16, null);
      } 
      Iterator iterator = this.a.a.l.iterator();
      while (true) {
        if (iterator.hasNext()) {
          iterator.next();
          try {
            JSONObject jSONObject = new JSONObject();
            if (param1JSONObject != null)
              com.bytedance.ies.bullet.b.a.c.a(jSONObject, param1JSONObject); 
          } catch (com.bytedance.ies.bullet.b.a.d d) {}
          continue;
        } 
        return;
      } 
    }
  }
  
  public static final class q extends d.f.b.m implements d.f.a.b<File, x> {
    public q(Object param1Object, com.bytedance.ies.bullet.b.c.a param1a, c param1c1, boolean param1Boolean, c param1c2, c param1c3, c.c param1c) {
      super(1);
    }
    
    public final void invoke(File param1File) {
      Object object = this.b;
      d.f.a.b<File, x> b2 = new d.f.a.b<File, x>(this, param1File) {
          public final void invoke(File param2File) {
            // Byte code:
            //   0: aload_0
            //   1: getfield b : Ljava/lang/Object;
            //   4: astore_3
            //   5: aload_1
            //   6: checkcast java/io/File
            //   9: astore #6
            //   11: aload_3
            //   12: checkcast java/io/File
            //   15: astore #7
            //   17: aload #7
            //   19: ifnonnull -> 59
            //   22: aload #6
            //   24: ifnonnull -> 59
            //   27: new java/io/FileNotFoundException
            //   30: dup
            //   31: ldc 'rn_base_android & rn_snapshot not found'
            //   33: invokespecial <init> : (Ljava/lang/String;)V
            //   36: checkcast java/lang/Throwable
            //   39: astore_1
            //   40: aload_0
            //   41: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
            //   44: getfield e : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   47: new java/lang/Exception
            //   50: dup
            //   51: aload_1
            //   52: invokespecial <init> : (Ljava/lang/Throwable;)V
            //   55: invokevirtual a : (Ljava/lang/Exception;)V
            //   58: return
            //   59: aload_0
            //   60: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
            //   63: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   66: astore #4
            //   68: aload #4
            //   70: getfield f : Lcom/bytedance/ies/bullet/kit/rn/c/c;
            //   73: invokevirtual b : ()Z
            //   76: ifne -> 90
            //   79: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
            //   82: ifnull -> 90
            //   85: iconst_1
            //   86: istore_2
            //   87: goto -> 92
            //   90: iconst_0
            //   91: istore_2
            //   92: aconst_null
            //   93: astore #5
            //   95: iload_2
            //   96: ifeq -> 105
            //   99: aload #4
            //   101: astore_1
            //   102: goto -> 107
            //   105: aconst_null
            //   106: astore_1
            //   107: aload_1
            //   108: ifnull -> 144
            //   111: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
            //   114: astore_1
            //   115: aload_1
            //   116: ifnull -> 136
            //   119: aload_1
            //   120: aload_0
            //   121: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
            //   124: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   127: getfield j : Lcom/bytedance/ies/bullet/b/e/a/h;
            //   130: putfield e : Lcom/bytedance/ies/bullet/b/e/a/h;
            //   133: goto -> 138
            //   136: aconst_null
            //   137: astore_1
            //   138: aload_1
            //   139: astore_3
            //   140: aload_1
            //   141: ifnonnull -> 224
            //   144: aload #4
            //   146: getfield d : Lcom/bytedance/ies/bullet/kit/rn/j;
            //   149: astore_3
            //   150: aload #4
            //   152: getfield e : Lcom/bytedance/ies/bullet/b/g/a/b;
            //   155: astore #8
            //   157: aload_0
            //   158: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
            //   161: getfield d : Z
            //   164: ifeq -> 173
            //   167: aload #5
            //   169: astore_1
            //   170: goto -> 184
            //   173: aload_0
            //   174: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
            //   177: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   180: getfield j : Lcom/bytedance/ies/bullet/b/e/a/h;
            //   183: astore_1
            //   184: aload #4
            //   186: getfield o : Lcom/bytedance/ies/bullet/kit/rn/b;
            //   189: astore #5
            //   191: new com/bytedance/ies/bullet/kit/rn/a
            //   194: dup
            //   195: aload_3
            //   196: aload #8
            //   198: aload_1
            //   199: aload #4
            //   201: getfield l : Ljava/util/List;
            //   204: aload #4
            //   206: getfield m : Ljava/util/List;
            //   209: aload #4
            //   211: getfield n : Ljava/util/List;
            //   214: aload #5
            //   216: aload #7
            //   218: aload #6
            //   220: invokespecial <init> : (Lcom/bytedance/ies/bullet/kit/rn/j;Lcom/bytedance/ies/bullet/b/g/a/b;Lcom/bytedance/ies/bullet/b/e/a/h;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lcom/bytedance/ies/bullet/kit/rn/b;Ljava/io/File;Ljava/io/File;)V
            //   223: astore_3
            //   224: aload_3
            //   225: aload_0
            //   226: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
            //   229: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   232: getfield g : Ljava/lang/String;
            //   235: invokevirtual a : (Ljava/lang/String;)V
            //   238: aload_3
            //   239: aload_0
            //   240: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
            //   243: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   246: getfield f : Lcom/bytedance/ies/bullet/kit/rn/c/c;
            //   249: invokevirtual b : ()Z
            //   252: putfield b : Z
            //   255: aload_3
            //   256: new com/bytedance/ies/bullet/kit/rn/internal/c$q$1$a
            //   259: dup
            //   260: aload_0
            //   261: invokespecial <init> : (Lcom/bytedance/ies/bullet/kit/rn/internal/c$q$1;)V
            //   264: checkcast com/facebook/react/bridge/RNDegradeExceptionHandler
            //   267: invokevirtual a : (Lcom/facebook/react/bridge/RNDegradeExceptionHandler;)V
            //   270: aload_3
            //   271: new java/util/HashMap
            //   274: dup
            //   275: invokespecial <init> : ()V
            //   278: checkcast java/util/Map
            //   281: invokevirtual a : (Ljava/util/Map;)V
            //   284: aload_0
            //   285: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
            //   288: getfield e : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   291: astore_1
            //   292: aload_3
            //   293: invokevirtual getReactInstanceManager : ()Lcom/facebook/react/ReactInstanceManager;
            //   296: astore_3
            //   297: aload_3
            //   298: ldc 'it.reactInstanceManager'
            //   300: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
            //   303: aload_1
            //   304: aload_3
            //   305: aload_0
            //   306: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
            //   309: getfield f : Lcom/bytedance/ies/bullet/kit/rn/internal/c$c;
            //   312: getfield a : Ljava/lang/String;
            //   315: invokevirtual a : (Lcom/facebook/react/ReactInstanceManager;Ljava/lang/String;)V
            //   318: return
          }
          
          static final class a implements RNDegradeExceptionHandler {
            public final void onDegrade(Exception param3Exception) {
              c c = this.a.a.e;
              d.f.b.l.a(param3Exception, "it");
              c.a(param3Exception);
            }
          }
        };
      d.f.a.b<Throwable, x> b1 = new d.f.a.b<Throwable, x>(this, param1File) {
          public final void invoke(Throwable param2Throwable) {
            // Byte code:
            //   0: aload_1
            //   1: ldc 'it'
            //   3: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
            //   6: aload_0
            //   7: getfield b : Ljava/lang/Object;
            //   10: checkcast java/io/File
            //   13: astore #5
            //   15: aload #5
            //   17: ifnonnull -> 52
            //   20: new java/io/FileNotFoundException
            //   23: dup
            //   24: ldc 'rn_base_android & rn_snapshot not found'
            //   26: invokespecial <init> : (Ljava/lang/String;)V
            //   29: checkcast java/lang/Throwable
            //   32: astore_1
            //   33: aload_0
            //   34: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
            //   37: getfield e : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   40: new java/lang/Exception
            //   43: dup
            //   44: aload_1
            //   45: invokespecial <init> : (Ljava/lang/Throwable;)V
            //   48: invokevirtual a : (Ljava/lang/Exception;)V
            //   51: return
            //   52: aload_0
            //   53: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
            //   56: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   59: astore #4
            //   61: aload #4
            //   63: getfield f : Lcom/bytedance/ies/bullet/kit/rn/c/c;
            //   66: invokevirtual b : ()Z
            //   69: ifne -> 83
            //   72: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
            //   75: ifnull -> 83
            //   78: iconst_1
            //   79: istore_2
            //   80: goto -> 85
            //   83: iconst_0
            //   84: istore_2
            //   85: iload_2
            //   86: ifeq -> 95
            //   89: aload #4
            //   91: astore_1
            //   92: goto -> 97
            //   95: aconst_null
            //   96: astore_1
            //   97: aload_1
            //   98: ifnull -> 134
            //   101: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
            //   104: astore_1
            //   105: aload_1
            //   106: ifnull -> 126
            //   109: aload_1
            //   110: aload_0
            //   111: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
            //   114: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   117: getfield j : Lcom/bytedance/ies/bullet/b/e/a/h;
            //   120: putfield e : Lcom/bytedance/ies/bullet/b/e/a/h;
            //   123: goto -> 128
            //   126: aconst_null
            //   127: astore_1
            //   128: aload_1
            //   129: astore_3
            //   130: aload_1
            //   131: ifnonnull -> 212
            //   134: aload #4
            //   136: getfield d : Lcom/bytedance/ies/bullet/kit/rn/j;
            //   139: astore_3
            //   140: aload #4
            //   142: getfield e : Lcom/bytedance/ies/bullet/b/g/a/b;
            //   145: astore #6
            //   147: aload_0
            //   148: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
            //   151: getfield d : Z
            //   154: ifeq -> 162
            //   157: aconst_null
            //   158: astore_1
            //   159: goto -> 173
            //   162: aload_0
            //   163: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
            //   166: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   169: getfield j : Lcom/bytedance/ies/bullet/b/e/a/h;
            //   172: astore_1
            //   173: aload #4
            //   175: getfield o : Lcom/bytedance/ies/bullet/kit/rn/b;
            //   178: astore #7
            //   180: new com/bytedance/ies/bullet/kit/rn/a
            //   183: dup
            //   184: aload_3
            //   185: aload #6
            //   187: aload_1
            //   188: aload #4
            //   190: getfield l : Ljava/util/List;
            //   193: aload #4
            //   195: getfield m : Ljava/util/List;
            //   198: aload #4
            //   200: getfield n : Ljava/util/List;
            //   203: aload #7
            //   205: aload #5
            //   207: aconst_null
            //   208: invokespecial <init> : (Lcom/bytedance/ies/bullet/kit/rn/j;Lcom/bytedance/ies/bullet/b/g/a/b;Lcom/bytedance/ies/bullet/b/e/a/h;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lcom/bytedance/ies/bullet/kit/rn/b;Ljava/io/File;Ljava/io/File;)V
            //   211: astore_3
            //   212: aload_3
            //   213: aload_0
            //   214: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
            //   217: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   220: getfield g : Ljava/lang/String;
            //   223: invokevirtual a : (Ljava/lang/String;)V
            //   226: aload_3
            //   227: aload_0
            //   228: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
            //   231: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   234: getfield f : Lcom/bytedance/ies/bullet/kit/rn/c/c;
            //   237: invokevirtual b : ()Z
            //   240: putfield b : Z
            //   243: aload_3
            //   244: new com/bytedance/ies/bullet/kit/rn/internal/c$q$2$a
            //   247: dup
            //   248: aload_0
            //   249: invokespecial <init> : (Lcom/bytedance/ies/bullet/kit/rn/internal/c$q$2;)V
            //   252: checkcast com/facebook/react/bridge/RNDegradeExceptionHandler
            //   255: invokevirtual a : (Lcom/facebook/react/bridge/RNDegradeExceptionHandler;)V
            //   258: aload_3
            //   259: new java/util/HashMap
            //   262: dup
            //   263: invokespecial <init> : ()V
            //   266: checkcast java/util/Map
            //   269: invokevirtual a : (Ljava/util/Map;)V
            //   272: aload_0
            //   273: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
            //   276: getfield e : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   279: astore_1
            //   280: aload_3
            //   281: invokevirtual getReactInstanceManager : ()Lcom/facebook/react/ReactInstanceManager;
            //   284: astore_3
            //   285: aload_3
            //   286: ldc 'it.reactInstanceManager'
            //   288: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
            //   291: aload_1
            //   292: aload_3
            //   293: aload_0
            //   294: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
            //   297: getfield f : Lcom/bytedance/ies/bullet/kit/rn/internal/c$c;
            //   300: getfield a : Ljava/lang/String;
            //   303: invokevirtual a : (Lcom/facebook/react/ReactInstanceManager;Ljava/lang/String;)V
            //   306: return
          }
          
          static final class a implements RNDegradeExceptionHandler {
            public final void onDegrade(Exception param3Exception) {
              c c = this.a.a.e;
              d.f.b.l.a(param3Exception, "it");
              c.a(param3Exception);
            }
          }
        };
      b2 = b2;
      object = object;
      this.a.b((Uri)object, b2, b1);
    }
  }
  
  public static final class null extends d.f.b.m implements d.f.a.b<File, x> {
    public null(c.q param1q, Object param1Object) {
      super(1);
    }
    
    public final void invoke(File param1File) {
      // Byte code:
      //   0: aload_0
      //   1: getfield b : Ljava/lang/Object;
      //   4: astore_3
      //   5: aload_1
      //   6: checkcast java/io/File
      //   9: astore #6
      //   11: aload_3
      //   12: checkcast java/io/File
      //   15: astore #7
      //   17: aload #7
      //   19: ifnonnull -> 59
      //   22: aload #6
      //   24: ifnonnull -> 59
      //   27: new java/io/FileNotFoundException
      //   30: dup
      //   31: ldc 'rn_base_android & rn_snapshot not found'
      //   33: invokespecial <init> : (Ljava/lang/String;)V
      //   36: checkcast java/lang/Throwable
      //   39: astore_1
      //   40: aload_0
      //   41: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
      //   44: getfield e : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   47: new java/lang/Exception
      //   50: dup
      //   51: aload_1
      //   52: invokespecial <init> : (Ljava/lang/Throwable;)V
      //   55: invokevirtual a : (Ljava/lang/Exception;)V
      //   58: return
      //   59: aload_0
      //   60: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
      //   63: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   66: astore #4
      //   68: aload #4
      //   70: getfield f : Lcom/bytedance/ies/bullet/kit/rn/c/c;
      //   73: invokevirtual b : ()Z
      //   76: ifne -> 90
      //   79: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
      //   82: ifnull -> 90
      //   85: iconst_1
      //   86: istore_2
      //   87: goto -> 92
      //   90: iconst_0
      //   91: istore_2
      //   92: aconst_null
      //   93: astore #5
      //   95: iload_2
      //   96: ifeq -> 105
      //   99: aload #4
      //   101: astore_1
      //   102: goto -> 107
      //   105: aconst_null
      //   106: astore_1
      //   107: aload_1
      //   108: ifnull -> 144
      //   111: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
      //   114: astore_1
      //   115: aload_1
      //   116: ifnull -> 136
      //   119: aload_1
      //   120: aload_0
      //   121: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
      //   124: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   127: getfield j : Lcom/bytedance/ies/bullet/b/e/a/h;
      //   130: putfield e : Lcom/bytedance/ies/bullet/b/e/a/h;
      //   133: goto -> 138
      //   136: aconst_null
      //   137: astore_1
      //   138: aload_1
      //   139: astore_3
      //   140: aload_1
      //   141: ifnonnull -> 224
      //   144: aload #4
      //   146: getfield d : Lcom/bytedance/ies/bullet/kit/rn/j;
      //   149: astore_3
      //   150: aload #4
      //   152: getfield e : Lcom/bytedance/ies/bullet/b/g/a/b;
      //   155: astore #8
      //   157: aload_0
      //   158: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
      //   161: getfield d : Z
      //   164: ifeq -> 173
      //   167: aload #5
      //   169: astore_1
      //   170: goto -> 184
      //   173: aload_0
      //   174: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
      //   177: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   180: getfield j : Lcom/bytedance/ies/bullet/b/e/a/h;
      //   183: astore_1
      //   184: aload #4
      //   186: getfield o : Lcom/bytedance/ies/bullet/kit/rn/b;
      //   189: astore #5
      //   191: new com/bytedance/ies/bullet/kit/rn/a
      //   194: dup
      //   195: aload_3
      //   196: aload #8
      //   198: aload_1
      //   199: aload #4
      //   201: getfield l : Ljava/util/List;
      //   204: aload #4
      //   206: getfield m : Ljava/util/List;
      //   209: aload #4
      //   211: getfield n : Ljava/util/List;
      //   214: aload #5
      //   216: aload #7
      //   218: aload #6
      //   220: invokespecial <init> : (Lcom/bytedance/ies/bullet/kit/rn/j;Lcom/bytedance/ies/bullet/b/g/a/b;Lcom/bytedance/ies/bullet/b/e/a/h;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lcom/bytedance/ies/bullet/kit/rn/b;Ljava/io/File;Ljava/io/File;)V
      //   223: astore_3
      //   224: aload_3
      //   225: aload_0
      //   226: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
      //   229: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   232: getfield g : Ljava/lang/String;
      //   235: invokevirtual a : (Ljava/lang/String;)V
      //   238: aload_3
      //   239: aload_0
      //   240: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
      //   243: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   246: getfield f : Lcom/bytedance/ies/bullet/kit/rn/c/c;
      //   249: invokevirtual b : ()Z
      //   252: putfield b : Z
      //   255: aload_3
      //   256: new com/bytedance/ies/bullet/kit/rn/internal/c$q$1$a
      //   259: dup
      //   260: aload_0
      //   261: invokespecial <init> : (Lcom/bytedance/ies/bullet/kit/rn/internal/c$q$1;)V
      //   264: checkcast com/facebook/react/bridge/RNDegradeExceptionHandler
      //   267: invokevirtual a : (Lcom/facebook/react/bridge/RNDegradeExceptionHandler;)V
      //   270: aload_3
      //   271: new java/util/HashMap
      //   274: dup
      //   275: invokespecial <init> : ()V
      //   278: checkcast java/util/Map
      //   281: invokevirtual a : (Ljava/util/Map;)V
      //   284: aload_0
      //   285: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
      //   288: getfield e : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   291: astore_1
      //   292: aload_3
      //   293: invokevirtual getReactInstanceManager : ()Lcom/facebook/react/ReactInstanceManager;
      //   296: astore_3
      //   297: aload_3
      //   298: ldc 'it.reactInstanceManager'
      //   300: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
      //   303: aload_1
      //   304: aload_3
      //   305: aload_0
      //   306: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
      //   309: getfield f : Lcom/bytedance/ies/bullet/kit/rn/internal/c$c;
      //   312: getfield a : Ljava/lang/String;
      //   315: invokevirtual a : (Lcom/facebook/react/ReactInstanceManager;Ljava/lang/String;)V
      //   318: return
    }
    
    static final class a implements RNDegradeExceptionHandler {
      public final void onDegrade(Exception param3Exception) {
        c c = this.a.a.e;
        d.f.b.l.a(param3Exception, "it");
        c.a(param3Exception);
      }
    }
  }
  
  static final class a implements RNDegradeExceptionHandler {
    public final void onDegrade(Exception param1Exception) {
      c c = this.a.a.e;
      d.f.b.l.a(param1Exception, "it");
      c.a(param1Exception);
    }
  }
  
  public static final class null extends d.f.b.m implements d.f.a.b<Throwable, x> {
    public null(c.q param1q, Object param1Object) {
      super(1);
    }
    
    public final void invoke(Throwable param1Throwable) {
      // Byte code:
      //   0: aload_1
      //   1: ldc 'it'
      //   3: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
      //   6: aload_0
      //   7: getfield b : Ljava/lang/Object;
      //   10: checkcast java/io/File
      //   13: astore #5
      //   15: aload #5
      //   17: ifnonnull -> 52
      //   20: new java/io/FileNotFoundException
      //   23: dup
      //   24: ldc 'rn_base_android & rn_snapshot not found'
      //   26: invokespecial <init> : (Ljava/lang/String;)V
      //   29: checkcast java/lang/Throwable
      //   32: astore_1
      //   33: aload_0
      //   34: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
      //   37: getfield e : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   40: new java/lang/Exception
      //   43: dup
      //   44: aload_1
      //   45: invokespecial <init> : (Ljava/lang/Throwable;)V
      //   48: invokevirtual a : (Ljava/lang/Exception;)V
      //   51: return
      //   52: aload_0
      //   53: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
      //   56: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   59: astore #4
      //   61: aload #4
      //   63: getfield f : Lcom/bytedance/ies/bullet/kit/rn/c/c;
      //   66: invokevirtual b : ()Z
      //   69: ifne -> 83
      //   72: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
      //   75: ifnull -> 83
      //   78: iconst_1
      //   79: istore_2
      //   80: goto -> 85
      //   83: iconst_0
      //   84: istore_2
      //   85: iload_2
      //   86: ifeq -> 95
      //   89: aload #4
      //   91: astore_1
      //   92: goto -> 97
      //   95: aconst_null
      //   96: astore_1
      //   97: aload_1
      //   98: ifnull -> 134
      //   101: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
      //   104: astore_1
      //   105: aload_1
      //   106: ifnull -> 126
      //   109: aload_1
      //   110: aload_0
      //   111: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
      //   114: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   117: getfield j : Lcom/bytedance/ies/bullet/b/e/a/h;
      //   120: putfield e : Lcom/bytedance/ies/bullet/b/e/a/h;
      //   123: goto -> 128
      //   126: aconst_null
      //   127: astore_1
      //   128: aload_1
      //   129: astore_3
      //   130: aload_1
      //   131: ifnonnull -> 212
      //   134: aload #4
      //   136: getfield d : Lcom/bytedance/ies/bullet/kit/rn/j;
      //   139: astore_3
      //   140: aload #4
      //   142: getfield e : Lcom/bytedance/ies/bullet/b/g/a/b;
      //   145: astore #6
      //   147: aload_0
      //   148: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
      //   151: getfield d : Z
      //   154: ifeq -> 162
      //   157: aconst_null
      //   158: astore_1
      //   159: goto -> 173
      //   162: aload_0
      //   163: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
      //   166: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   169: getfield j : Lcom/bytedance/ies/bullet/b/e/a/h;
      //   172: astore_1
      //   173: aload #4
      //   175: getfield o : Lcom/bytedance/ies/bullet/kit/rn/b;
      //   178: astore #7
      //   180: new com/bytedance/ies/bullet/kit/rn/a
      //   183: dup
      //   184: aload_3
      //   185: aload #6
      //   187: aload_1
      //   188: aload #4
      //   190: getfield l : Ljava/util/List;
      //   193: aload #4
      //   195: getfield m : Ljava/util/List;
      //   198: aload #4
      //   200: getfield n : Ljava/util/List;
      //   203: aload #7
      //   205: aload #5
      //   207: aconst_null
      //   208: invokespecial <init> : (Lcom/bytedance/ies/bullet/kit/rn/j;Lcom/bytedance/ies/bullet/b/g/a/b;Lcom/bytedance/ies/bullet/b/e/a/h;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lcom/bytedance/ies/bullet/kit/rn/b;Ljava/io/File;Ljava/io/File;)V
      //   211: astore_3
      //   212: aload_3
      //   213: aload_0
      //   214: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
      //   217: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   220: getfield g : Ljava/lang/String;
      //   223: invokevirtual a : (Ljava/lang/String;)V
      //   226: aload_3
      //   227: aload_0
      //   228: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
      //   231: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   234: getfield f : Lcom/bytedance/ies/bullet/kit/rn/c/c;
      //   237: invokevirtual b : ()Z
      //   240: putfield b : Z
      //   243: aload_3
      //   244: new com/bytedance/ies/bullet/kit/rn/internal/c$q$2$a
      //   247: dup
      //   248: aload_0
      //   249: invokespecial <init> : (Lcom/bytedance/ies/bullet/kit/rn/internal/c$q$2;)V
      //   252: checkcast com/facebook/react/bridge/RNDegradeExceptionHandler
      //   255: invokevirtual a : (Lcom/facebook/react/bridge/RNDegradeExceptionHandler;)V
      //   258: aload_3
      //   259: new java/util/HashMap
      //   262: dup
      //   263: invokespecial <init> : ()V
      //   266: checkcast java/util/Map
      //   269: invokevirtual a : (Ljava/util/Map;)V
      //   272: aload_0
      //   273: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
      //   276: getfield e : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   279: astore_1
      //   280: aload_3
      //   281: invokevirtual getReactInstanceManager : ()Lcom/facebook/react/ReactInstanceManager;
      //   284: astore_3
      //   285: aload_3
      //   286: ldc 'it.reactInstanceManager'
      //   288: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
      //   291: aload_1
      //   292: aload_3
      //   293: aload_0
      //   294: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$q;
      //   297: getfield f : Lcom/bytedance/ies/bullet/kit/rn/internal/c$c;
      //   300: getfield a : Ljava/lang/String;
      //   303: invokevirtual a : (Lcom/facebook/react/ReactInstanceManager;Ljava/lang/String;)V
      //   306: return
    }
    
    static final class a implements RNDegradeExceptionHandler {
      public final void onDegrade(Exception param3Exception) {
        c c = this.a.a.e;
        d.f.b.l.a(param3Exception, "it");
        c.a(param3Exception);
      }
    }
  }
  
  static final class a implements RNDegradeExceptionHandler {
    public final void onDegrade(Exception param1Exception) {
      c c = this.a.a.e;
      d.f.b.l.a(param1Exception, "it");
      c.a(param1Exception);
    }
  }
  
  public static final class r extends d.f.b.m implements d.f.a.b<Throwable, x> {
    public r(Object param1Object, com.bytedance.ies.bullet.b.c.a param1a, c param1c1, boolean param1Boolean, c param1c2, c param1c3, c.c param1c) {
      super(1);
    }
    
    public final void invoke(Throwable param1Throwable) {
      d.f.b.l.b(param1Throwable, "it");
      Object object = this.b;
      d.f.a.b<File, x> b2 = new d.f.a.b<File, x>(this) {
          public final void invoke(File param2File) {
            // Byte code:
            //   0: aload_1
            //   1: checkcast java/io/File
            //   4: astore #5
            //   6: aload #5
            //   8: ifnonnull -> 43
            //   11: new java/io/FileNotFoundException
            //   14: dup
            //   15: ldc 'rn_base_android & rn_snapshot not found'
            //   17: invokespecial <init> : (Ljava/lang/String;)V
            //   20: checkcast java/lang/Throwable
            //   23: astore_1
            //   24: aload_0
            //   25: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$r;
            //   28: getfield e : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   31: new java/lang/Exception
            //   34: dup
            //   35: aload_1
            //   36: invokespecial <init> : (Ljava/lang/Throwable;)V
            //   39: invokevirtual a : (Ljava/lang/Exception;)V
            //   42: return
            //   43: aload_0
            //   44: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$r;
            //   47: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   50: astore #4
            //   52: aload #4
            //   54: getfield f : Lcom/bytedance/ies/bullet/kit/rn/c/c;
            //   57: invokevirtual b : ()Z
            //   60: ifne -> 74
            //   63: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
            //   66: ifnull -> 74
            //   69: iconst_1
            //   70: istore_2
            //   71: goto -> 76
            //   74: iconst_0
            //   75: istore_2
            //   76: iload_2
            //   77: ifeq -> 86
            //   80: aload #4
            //   82: astore_1
            //   83: goto -> 88
            //   86: aconst_null
            //   87: astore_1
            //   88: aload_1
            //   89: ifnull -> 125
            //   92: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
            //   95: astore_1
            //   96: aload_1
            //   97: ifnull -> 117
            //   100: aload_1
            //   101: aload_0
            //   102: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$r;
            //   105: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   108: getfield j : Lcom/bytedance/ies/bullet/b/e/a/h;
            //   111: putfield e : Lcom/bytedance/ies/bullet/b/e/a/h;
            //   114: goto -> 119
            //   117: aconst_null
            //   118: astore_1
            //   119: aload_1
            //   120: astore_3
            //   121: aload_1
            //   122: ifnonnull -> 203
            //   125: aload #4
            //   127: getfield d : Lcom/bytedance/ies/bullet/kit/rn/j;
            //   130: astore_3
            //   131: aload #4
            //   133: getfield e : Lcom/bytedance/ies/bullet/b/g/a/b;
            //   136: astore #6
            //   138: aload_0
            //   139: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$r;
            //   142: getfield d : Z
            //   145: ifeq -> 153
            //   148: aconst_null
            //   149: astore_1
            //   150: goto -> 164
            //   153: aload_0
            //   154: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$r;
            //   157: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   160: getfield j : Lcom/bytedance/ies/bullet/b/e/a/h;
            //   163: astore_1
            //   164: aload #4
            //   166: getfield o : Lcom/bytedance/ies/bullet/kit/rn/b;
            //   169: astore #7
            //   171: new com/bytedance/ies/bullet/kit/rn/a
            //   174: dup
            //   175: aload_3
            //   176: aload #6
            //   178: aload_1
            //   179: aload #4
            //   181: getfield l : Ljava/util/List;
            //   184: aload #4
            //   186: getfield m : Ljava/util/List;
            //   189: aload #4
            //   191: getfield n : Ljava/util/List;
            //   194: aload #7
            //   196: aconst_null
            //   197: aload #5
            //   199: invokespecial <init> : (Lcom/bytedance/ies/bullet/kit/rn/j;Lcom/bytedance/ies/bullet/b/g/a/b;Lcom/bytedance/ies/bullet/b/e/a/h;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lcom/bytedance/ies/bullet/kit/rn/b;Ljava/io/File;Ljava/io/File;)V
            //   202: astore_3
            //   203: aload_3
            //   204: aload_0
            //   205: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$r;
            //   208: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   211: getfield g : Ljava/lang/String;
            //   214: invokevirtual a : (Ljava/lang/String;)V
            //   217: aload_3
            //   218: aload_0
            //   219: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$r;
            //   222: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   225: getfield f : Lcom/bytedance/ies/bullet/kit/rn/c/c;
            //   228: invokevirtual b : ()Z
            //   231: putfield b : Z
            //   234: aload_3
            //   235: new com/bytedance/ies/bullet/kit/rn/internal/c$r$1$a
            //   238: dup
            //   239: aload_0
            //   240: invokespecial <init> : (Lcom/bytedance/ies/bullet/kit/rn/internal/c$r$1;)V
            //   243: checkcast com/facebook/react/bridge/RNDegradeExceptionHandler
            //   246: invokevirtual a : (Lcom/facebook/react/bridge/RNDegradeExceptionHandler;)V
            //   249: aload_3
            //   250: new java/util/HashMap
            //   253: dup
            //   254: invokespecial <init> : ()V
            //   257: checkcast java/util/Map
            //   260: invokevirtual a : (Ljava/util/Map;)V
            //   263: aload_0
            //   264: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$r;
            //   267: getfield e : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   270: astore_1
            //   271: aload_3
            //   272: invokevirtual getReactInstanceManager : ()Lcom/facebook/react/ReactInstanceManager;
            //   275: astore_3
            //   276: aload_3
            //   277: ldc 'it.reactInstanceManager'
            //   279: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
            //   282: aload_1
            //   283: aload_3
            //   284: aload_0
            //   285: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$r;
            //   288: getfield f : Lcom/bytedance/ies/bullet/kit/rn/internal/c$c;
            //   291: getfield a : Ljava/lang/String;
            //   294: invokevirtual a : (Lcom/facebook/react/ReactInstanceManager;Ljava/lang/String;)V
            //   297: return
          }
          
          static final class a implements RNDegradeExceptionHandler {
            public final void onDegrade(Exception param3Exception) {
              c c = this.a.a.e;
              d.f.b.l.a(param3Exception, "it");
              c.a(param3Exception);
            }
          }
        };
      d.f.a.b<Throwable, x> b1 = new d.f.a.b<Throwable, x>(this) {
          public final void invoke(Throwable param2Throwable) {
            d.f.b.l.b(param2Throwable, "it");
            param2Throwable = new FileNotFoundException("rn_base_android & rn_snapshot not found");
            this.a.e.a(new Exception(param2Throwable));
          }
        };
      b2 = b2;
      object = object;
      this.a.b((Uri)object, b2, b1);
    }
  }
  
  public static final class null extends d.f.b.m implements d.f.a.b<File, x> {
    public null(c.r param1r) {
      super(1);
    }
    
    public final void invoke(File param1File) {
      // Byte code:
      //   0: aload_1
      //   1: checkcast java/io/File
      //   4: astore #5
      //   6: aload #5
      //   8: ifnonnull -> 43
      //   11: new java/io/FileNotFoundException
      //   14: dup
      //   15: ldc 'rn_base_android & rn_snapshot not found'
      //   17: invokespecial <init> : (Ljava/lang/String;)V
      //   20: checkcast java/lang/Throwable
      //   23: astore_1
      //   24: aload_0
      //   25: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$r;
      //   28: getfield e : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   31: new java/lang/Exception
      //   34: dup
      //   35: aload_1
      //   36: invokespecial <init> : (Ljava/lang/Throwable;)V
      //   39: invokevirtual a : (Ljava/lang/Exception;)V
      //   42: return
      //   43: aload_0
      //   44: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$r;
      //   47: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   50: astore #4
      //   52: aload #4
      //   54: getfield f : Lcom/bytedance/ies/bullet/kit/rn/c/c;
      //   57: invokevirtual b : ()Z
      //   60: ifne -> 74
      //   63: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
      //   66: ifnull -> 74
      //   69: iconst_1
      //   70: istore_2
      //   71: goto -> 76
      //   74: iconst_0
      //   75: istore_2
      //   76: iload_2
      //   77: ifeq -> 86
      //   80: aload #4
      //   82: astore_1
      //   83: goto -> 88
      //   86: aconst_null
      //   87: astore_1
      //   88: aload_1
      //   89: ifnull -> 125
      //   92: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
      //   95: astore_1
      //   96: aload_1
      //   97: ifnull -> 117
      //   100: aload_1
      //   101: aload_0
      //   102: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$r;
      //   105: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   108: getfield j : Lcom/bytedance/ies/bullet/b/e/a/h;
      //   111: putfield e : Lcom/bytedance/ies/bullet/b/e/a/h;
      //   114: goto -> 119
      //   117: aconst_null
      //   118: astore_1
      //   119: aload_1
      //   120: astore_3
      //   121: aload_1
      //   122: ifnonnull -> 203
      //   125: aload #4
      //   127: getfield d : Lcom/bytedance/ies/bullet/kit/rn/j;
      //   130: astore_3
      //   131: aload #4
      //   133: getfield e : Lcom/bytedance/ies/bullet/b/g/a/b;
      //   136: astore #6
      //   138: aload_0
      //   139: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$r;
      //   142: getfield d : Z
      //   145: ifeq -> 153
      //   148: aconst_null
      //   149: astore_1
      //   150: goto -> 164
      //   153: aload_0
      //   154: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$r;
      //   157: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   160: getfield j : Lcom/bytedance/ies/bullet/b/e/a/h;
      //   163: astore_1
      //   164: aload #4
      //   166: getfield o : Lcom/bytedance/ies/bullet/kit/rn/b;
      //   169: astore #7
      //   171: new com/bytedance/ies/bullet/kit/rn/a
      //   174: dup
      //   175: aload_3
      //   176: aload #6
      //   178: aload_1
      //   179: aload #4
      //   181: getfield l : Ljava/util/List;
      //   184: aload #4
      //   186: getfield m : Ljava/util/List;
      //   189: aload #4
      //   191: getfield n : Ljava/util/List;
      //   194: aload #7
      //   196: aconst_null
      //   197: aload #5
      //   199: invokespecial <init> : (Lcom/bytedance/ies/bullet/kit/rn/j;Lcom/bytedance/ies/bullet/b/g/a/b;Lcom/bytedance/ies/bullet/b/e/a/h;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lcom/bytedance/ies/bullet/kit/rn/b;Ljava/io/File;Ljava/io/File;)V
      //   202: astore_3
      //   203: aload_3
      //   204: aload_0
      //   205: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$r;
      //   208: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   211: getfield g : Ljava/lang/String;
      //   214: invokevirtual a : (Ljava/lang/String;)V
      //   217: aload_3
      //   218: aload_0
      //   219: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$r;
      //   222: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   225: getfield f : Lcom/bytedance/ies/bullet/kit/rn/c/c;
      //   228: invokevirtual b : ()Z
      //   231: putfield b : Z
      //   234: aload_3
      //   235: new com/bytedance/ies/bullet/kit/rn/internal/c$r$1$a
      //   238: dup
      //   239: aload_0
      //   240: invokespecial <init> : (Lcom/bytedance/ies/bullet/kit/rn/internal/c$r$1;)V
      //   243: checkcast com/facebook/react/bridge/RNDegradeExceptionHandler
      //   246: invokevirtual a : (Lcom/facebook/react/bridge/RNDegradeExceptionHandler;)V
      //   249: aload_3
      //   250: new java/util/HashMap
      //   253: dup
      //   254: invokespecial <init> : ()V
      //   257: checkcast java/util/Map
      //   260: invokevirtual a : (Ljava/util/Map;)V
      //   263: aload_0
      //   264: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$r;
      //   267: getfield e : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   270: astore_1
      //   271: aload_3
      //   272: invokevirtual getReactInstanceManager : ()Lcom/facebook/react/ReactInstanceManager;
      //   275: astore_3
      //   276: aload_3
      //   277: ldc 'it.reactInstanceManager'
      //   279: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
      //   282: aload_1
      //   283: aload_3
      //   284: aload_0
      //   285: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$r;
      //   288: getfield f : Lcom/bytedance/ies/bullet/kit/rn/internal/c$c;
      //   291: getfield a : Ljava/lang/String;
      //   294: invokevirtual a : (Lcom/facebook/react/ReactInstanceManager;Ljava/lang/String;)V
      //   297: return
    }
    
    static final class a implements RNDegradeExceptionHandler {
      public final void onDegrade(Exception param3Exception) {
        c c = this.a.a.e;
        d.f.b.l.a(param3Exception, "it");
        c.a(param3Exception);
      }
    }
  }
  
  static final class a implements RNDegradeExceptionHandler {
    public final void onDegrade(Exception param1Exception) {
      c c = this.a.a.e;
      d.f.b.l.a(param1Exception, "it");
      c.a(param1Exception);
    }
  }
  
  public static final class null extends d.f.b.m implements d.f.a.b<Throwable, x> {
    public null(c.r param1r) {
      super(1);
    }
    
    public final void invoke(Throwable param1Throwable) {
      d.f.b.l.b(param1Throwable, "it");
      param1Throwable = new FileNotFoundException("rn_base_android & rn_snapshot not found");
      this.a.e.a(new Exception(param1Throwable));
    }
  }
  
  static final class s extends d.f.b.m implements d.f.a.b<File, x> {
    s(c param1c, c.c param1c1) {
      super(1);
    }
  }
  
  static final class t extends d.f.b.m implements d.f.a.b<Throwable, x> {
    t(c param1c) {
      super(1);
    }
  }
  
  public static final class u extends d.f.b.m implements d.f.a.b<File, x> {
    public u(Object param1Object, com.bytedance.ies.bullet.b.c.a param1a, c param1c1, boolean param1Boolean, c param1c2) {
      super(1);
    }
    
    public final void invoke(File param1File) {
      Object object = this.b;
      d.f.a.b<File, x> b2 = new d.f.a.b<File, x>(this, param1File) {
          public final void invoke(File param2File) {
            // Byte code:
            //   0: aload_0
            //   1: getfield b : Ljava/lang/Object;
            //   4: astore #5
            //   6: aload_1
            //   7: checkcast java/io/File
            //   10: astore #7
            //   12: aload #5
            //   14: checkcast java/io/File
            //   17: astore #8
            //   19: aload #8
            //   21: ifnonnull -> 40
            //   24: aload #7
            //   26: ifnonnull -> 40
            //   29: new java/io/FileNotFoundException
            //   32: dup
            //   33: ldc 'rn_base_android & rn_snapshot not found'
            //   35: invokespecial <init> : (Ljava/lang/String;)V
            //   38: pop
            //   39: return
            //   40: aload_0
            //   41: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$u;
            //   44: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   47: astore #6
            //   49: aload #6
            //   51: getfield f : Lcom/bytedance/ies/bullet/kit/rn/c/c;
            //   54: invokevirtual b : ()Z
            //   57: istore #4
            //   59: iconst_1
            //   60: istore_3
            //   61: iload #4
            //   63: ifne -> 77
            //   66: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
            //   69: ifnull -> 77
            //   72: iconst_1
            //   73: istore_2
            //   74: goto -> 79
            //   77: iconst_0
            //   78: istore_2
            //   79: iload_2
            //   80: ifeq -> 89
            //   83: aload #6
            //   85: astore_1
            //   86: goto -> 91
            //   89: aconst_null
            //   90: astore_1
            //   91: aload_1
            //   92: ifnull -> 129
            //   95: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
            //   98: astore_1
            //   99: aload_1
            //   100: ifnull -> 120
            //   103: aload_1
            //   104: aload_0
            //   105: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$u;
            //   108: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   111: getfield j : Lcom/bytedance/ies/bullet/b/e/a/h;
            //   114: putfield e : Lcom/bytedance/ies/bullet/b/e/a/h;
            //   117: goto -> 122
            //   120: aconst_null
            //   121: astore_1
            //   122: aload_1
            //   123: astore #5
            //   125: aload_1
            //   126: ifnonnull -> 211
            //   129: aload #6
            //   131: getfield d : Lcom/bytedance/ies/bullet/kit/rn/j;
            //   134: astore #5
            //   136: aload #6
            //   138: getfield e : Lcom/bytedance/ies/bullet/b/g/a/b;
            //   141: astore #9
            //   143: aload_0
            //   144: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$u;
            //   147: getfield d : Z
            //   150: ifeq -> 158
            //   153: aconst_null
            //   154: astore_1
            //   155: goto -> 169
            //   158: aload_0
            //   159: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$u;
            //   162: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   165: getfield j : Lcom/bytedance/ies/bullet/b/e/a/h;
            //   168: astore_1
            //   169: aload #6
            //   171: getfield o : Lcom/bytedance/ies/bullet/kit/rn/b;
            //   174: astore #10
            //   176: new com/bytedance/ies/bullet/kit/rn/a
            //   179: dup
            //   180: aload #5
            //   182: aload #9
            //   184: aload_1
            //   185: aload #6
            //   187: getfield l : Ljava/util/List;
            //   190: aload #6
            //   192: getfield m : Ljava/util/List;
            //   195: aload #6
            //   197: getfield n : Ljava/util/List;
            //   200: aload #10
            //   202: aload #8
            //   204: aload #7
            //   206: invokespecial <init> : (Lcom/bytedance/ies/bullet/kit/rn/j;Lcom/bytedance/ies/bullet/b/g/a/b;Lcom/bytedance/ies/bullet/b/e/a/h;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lcom/bytedance/ies/bullet/kit/rn/b;Ljava/io/File;Ljava/io/File;)V
            //   209: astore #5
            //   211: aload #5
            //   213: aload_0
            //   214: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$u;
            //   217: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   220: getfield g : Ljava/lang/String;
            //   223: invokevirtual a : (Ljava/lang/String;)V
            //   226: aload #5
            //   228: aload_0
            //   229: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$u;
            //   232: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   235: getfield f : Lcom/bytedance/ies/bullet/kit/rn/c/c;
            //   238: invokevirtual b : ()Z
            //   241: putfield b : Z
            //   244: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
            //   247: ifnonnull -> 316
            //   250: aload_0
            //   251: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$u;
            //   254: getfield e : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   257: getfield p : Z
            //   260: ifne -> 316
            //   263: aload #5
            //   265: putstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
            //   268: aload #5
            //   270: invokevirtual getReactInstanceManager : ()Lcom/facebook/react/ReactInstanceManager;
            //   273: astore_1
            //   274: aload_1
            //   275: ifnull -> 316
            //   278: aload_1
            //   279: invokevirtual getCurrentReactContext : ()Lcom/facebook/react/bridge/ReactContext;
            //   282: ifnonnull -> 297
            //   285: aload_1
            //   286: invokevirtual hasStartedCreatingInitialContext : ()Z
            //   289: ifne -> 297
            //   292: iload_3
            //   293: istore_2
            //   294: goto -> 299
            //   297: iconst_0
            //   298: istore_2
            //   299: iload_2
            //   300: ifeq -> 306
            //   303: goto -> 308
            //   306: aconst_null
            //   307: astore_1
            //   308: aload_1
            //   309: ifnull -> 316
            //   312: aload_1
            //   313: invokevirtual createReactContextInBackground : ()V
            //   316: return
          }
        };
      d.f.a.b<Throwable, x> b1 = new d.f.a.b<Throwable, x>(this, param1File) {
          public final void invoke(Throwable param2Throwable) {
            // Byte code:
            //   0: aload_1
            //   1: ldc 'it'
            //   3: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
            //   6: aload_0
            //   7: getfield b : Ljava/lang/Object;
            //   10: checkcast java/io/File
            //   13: astore #7
            //   15: aload #7
            //   17: ifnonnull -> 31
            //   20: new java/io/FileNotFoundException
            //   23: dup
            //   24: ldc 'rn_base_android & rn_snapshot not found'
            //   26: invokespecial <init> : (Ljava/lang/String;)V
            //   29: pop
            //   30: return
            //   31: aload_0
            //   32: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$u;
            //   35: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   38: astore #6
            //   40: aload #6
            //   42: getfield f : Lcom/bytedance/ies/bullet/kit/rn/c/c;
            //   45: invokevirtual b : ()Z
            //   48: istore #4
            //   50: iconst_1
            //   51: istore_3
            //   52: iload #4
            //   54: ifne -> 68
            //   57: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
            //   60: ifnull -> 68
            //   63: iconst_1
            //   64: istore_2
            //   65: goto -> 70
            //   68: iconst_0
            //   69: istore_2
            //   70: iload_2
            //   71: ifeq -> 80
            //   74: aload #6
            //   76: astore_1
            //   77: goto -> 82
            //   80: aconst_null
            //   81: astore_1
            //   82: aload_1
            //   83: ifnull -> 120
            //   86: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
            //   89: astore_1
            //   90: aload_1
            //   91: ifnull -> 111
            //   94: aload_1
            //   95: aload_0
            //   96: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$u;
            //   99: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   102: getfield j : Lcom/bytedance/ies/bullet/b/e/a/h;
            //   105: putfield e : Lcom/bytedance/ies/bullet/b/e/a/h;
            //   108: goto -> 113
            //   111: aconst_null
            //   112: astore_1
            //   113: aload_1
            //   114: astore #5
            //   116: aload_1
            //   117: ifnonnull -> 201
            //   120: aload #6
            //   122: getfield d : Lcom/bytedance/ies/bullet/kit/rn/j;
            //   125: astore #5
            //   127: aload #6
            //   129: getfield e : Lcom/bytedance/ies/bullet/b/g/a/b;
            //   132: astore #8
            //   134: aload_0
            //   135: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$u;
            //   138: getfield d : Z
            //   141: ifeq -> 149
            //   144: aconst_null
            //   145: astore_1
            //   146: goto -> 160
            //   149: aload_0
            //   150: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$u;
            //   153: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   156: getfield j : Lcom/bytedance/ies/bullet/b/e/a/h;
            //   159: astore_1
            //   160: aload #6
            //   162: getfield o : Lcom/bytedance/ies/bullet/kit/rn/b;
            //   165: astore #9
            //   167: new com/bytedance/ies/bullet/kit/rn/a
            //   170: dup
            //   171: aload #5
            //   173: aload #8
            //   175: aload_1
            //   176: aload #6
            //   178: getfield l : Ljava/util/List;
            //   181: aload #6
            //   183: getfield m : Ljava/util/List;
            //   186: aload #6
            //   188: getfield n : Ljava/util/List;
            //   191: aload #9
            //   193: aload #7
            //   195: aconst_null
            //   196: invokespecial <init> : (Lcom/bytedance/ies/bullet/kit/rn/j;Lcom/bytedance/ies/bullet/b/g/a/b;Lcom/bytedance/ies/bullet/b/e/a/h;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lcom/bytedance/ies/bullet/kit/rn/b;Ljava/io/File;Ljava/io/File;)V
            //   199: astore #5
            //   201: aload #5
            //   203: aload_0
            //   204: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$u;
            //   207: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   210: getfield g : Ljava/lang/String;
            //   213: invokevirtual a : (Ljava/lang/String;)V
            //   216: aload #5
            //   218: aload_0
            //   219: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$u;
            //   222: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   225: getfield f : Lcom/bytedance/ies/bullet/kit/rn/c/c;
            //   228: invokevirtual b : ()Z
            //   231: putfield b : Z
            //   234: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
            //   237: ifnonnull -> 306
            //   240: aload_0
            //   241: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$u;
            //   244: getfield e : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   247: getfield p : Z
            //   250: ifne -> 306
            //   253: aload #5
            //   255: putstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
            //   258: aload #5
            //   260: invokevirtual getReactInstanceManager : ()Lcom/facebook/react/ReactInstanceManager;
            //   263: astore_1
            //   264: aload_1
            //   265: ifnull -> 306
            //   268: aload_1
            //   269: invokevirtual getCurrentReactContext : ()Lcom/facebook/react/bridge/ReactContext;
            //   272: ifnonnull -> 287
            //   275: aload_1
            //   276: invokevirtual hasStartedCreatingInitialContext : ()Z
            //   279: ifne -> 287
            //   282: iload_3
            //   283: istore_2
            //   284: goto -> 289
            //   287: iconst_0
            //   288: istore_2
            //   289: iload_2
            //   290: ifeq -> 296
            //   293: goto -> 298
            //   296: aconst_null
            //   297: astore_1
            //   298: aload_1
            //   299: ifnull -> 306
            //   302: aload_1
            //   303: invokevirtual createReactContextInBackground : ()V
            //   306: return
          }
        };
      b2 = b2;
      object = object;
      this.a.b((Uri)object, b2, b1);
    }
  }
  
  public static final class null extends d.f.b.m implements d.f.a.b<File, x> {
    public null(c.u param1u, Object param1Object) {
      super(1);
    }
    
    public final void invoke(File param1File) {
      // Byte code:
      //   0: aload_0
      //   1: getfield b : Ljava/lang/Object;
      //   4: astore #5
      //   6: aload_1
      //   7: checkcast java/io/File
      //   10: astore #7
      //   12: aload #5
      //   14: checkcast java/io/File
      //   17: astore #8
      //   19: aload #8
      //   21: ifnonnull -> 40
      //   24: aload #7
      //   26: ifnonnull -> 40
      //   29: new java/io/FileNotFoundException
      //   32: dup
      //   33: ldc 'rn_base_android & rn_snapshot not found'
      //   35: invokespecial <init> : (Ljava/lang/String;)V
      //   38: pop
      //   39: return
      //   40: aload_0
      //   41: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$u;
      //   44: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   47: astore #6
      //   49: aload #6
      //   51: getfield f : Lcom/bytedance/ies/bullet/kit/rn/c/c;
      //   54: invokevirtual b : ()Z
      //   57: istore #4
      //   59: iconst_1
      //   60: istore_3
      //   61: iload #4
      //   63: ifne -> 77
      //   66: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
      //   69: ifnull -> 77
      //   72: iconst_1
      //   73: istore_2
      //   74: goto -> 79
      //   77: iconst_0
      //   78: istore_2
      //   79: iload_2
      //   80: ifeq -> 89
      //   83: aload #6
      //   85: astore_1
      //   86: goto -> 91
      //   89: aconst_null
      //   90: astore_1
      //   91: aload_1
      //   92: ifnull -> 129
      //   95: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
      //   98: astore_1
      //   99: aload_1
      //   100: ifnull -> 120
      //   103: aload_1
      //   104: aload_0
      //   105: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$u;
      //   108: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   111: getfield j : Lcom/bytedance/ies/bullet/b/e/a/h;
      //   114: putfield e : Lcom/bytedance/ies/bullet/b/e/a/h;
      //   117: goto -> 122
      //   120: aconst_null
      //   121: astore_1
      //   122: aload_1
      //   123: astore #5
      //   125: aload_1
      //   126: ifnonnull -> 211
      //   129: aload #6
      //   131: getfield d : Lcom/bytedance/ies/bullet/kit/rn/j;
      //   134: astore #5
      //   136: aload #6
      //   138: getfield e : Lcom/bytedance/ies/bullet/b/g/a/b;
      //   141: astore #9
      //   143: aload_0
      //   144: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$u;
      //   147: getfield d : Z
      //   150: ifeq -> 158
      //   153: aconst_null
      //   154: astore_1
      //   155: goto -> 169
      //   158: aload_0
      //   159: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$u;
      //   162: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   165: getfield j : Lcom/bytedance/ies/bullet/b/e/a/h;
      //   168: astore_1
      //   169: aload #6
      //   171: getfield o : Lcom/bytedance/ies/bullet/kit/rn/b;
      //   174: astore #10
      //   176: new com/bytedance/ies/bullet/kit/rn/a
      //   179: dup
      //   180: aload #5
      //   182: aload #9
      //   184: aload_1
      //   185: aload #6
      //   187: getfield l : Ljava/util/List;
      //   190: aload #6
      //   192: getfield m : Ljava/util/List;
      //   195: aload #6
      //   197: getfield n : Ljava/util/List;
      //   200: aload #10
      //   202: aload #8
      //   204: aload #7
      //   206: invokespecial <init> : (Lcom/bytedance/ies/bullet/kit/rn/j;Lcom/bytedance/ies/bullet/b/g/a/b;Lcom/bytedance/ies/bullet/b/e/a/h;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lcom/bytedance/ies/bullet/kit/rn/b;Ljava/io/File;Ljava/io/File;)V
      //   209: astore #5
      //   211: aload #5
      //   213: aload_0
      //   214: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$u;
      //   217: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   220: getfield g : Ljava/lang/String;
      //   223: invokevirtual a : (Ljava/lang/String;)V
      //   226: aload #5
      //   228: aload_0
      //   229: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$u;
      //   232: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   235: getfield f : Lcom/bytedance/ies/bullet/kit/rn/c/c;
      //   238: invokevirtual b : ()Z
      //   241: putfield b : Z
      //   244: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
      //   247: ifnonnull -> 316
      //   250: aload_0
      //   251: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$u;
      //   254: getfield e : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   257: getfield p : Z
      //   260: ifne -> 316
      //   263: aload #5
      //   265: putstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
      //   268: aload #5
      //   270: invokevirtual getReactInstanceManager : ()Lcom/facebook/react/ReactInstanceManager;
      //   273: astore_1
      //   274: aload_1
      //   275: ifnull -> 316
      //   278: aload_1
      //   279: invokevirtual getCurrentReactContext : ()Lcom/facebook/react/bridge/ReactContext;
      //   282: ifnonnull -> 297
      //   285: aload_1
      //   286: invokevirtual hasStartedCreatingInitialContext : ()Z
      //   289: ifne -> 297
      //   292: iload_3
      //   293: istore_2
      //   294: goto -> 299
      //   297: iconst_0
      //   298: istore_2
      //   299: iload_2
      //   300: ifeq -> 306
      //   303: goto -> 308
      //   306: aconst_null
      //   307: astore_1
      //   308: aload_1
      //   309: ifnull -> 316
      //   312: aload_1
      //   313: invokevirtual createReactContextInBackground : ()V
      //   316: return
    }
  }
  
  public static final class null extends d.f.b.m implements d.f.a.b<Throwable, x> {
    public null(c.u param1u, Object param1Object) {
      super(1);
    }
    
    public final void invoke(Throwable param1Throwable) {
      // Byte code:
      //   0: aload_1
      //   1: ldc 'it'
      //   3: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
      //   6: aload_0
      //   7: getfield b : Ljava/lang/Object;
      //   10: checkcast java/io/File
      //   13: astore #7
      //   15: aload #7
      //   17: ifnonnull -> 31
      //   20: new java/io/FileNotFoundException
      //   23: dup
      //   24: ldc 'rn_base_android & rn_snapshot not found'
      //   26: invokespecial <init> : (Ljava/lang/String;)V
      //   29: pop
      //   30: return
      //   31: aload_0
      //   32: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$u;
      //   35: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   38: astore #6
      //   40: aload #6
      //   42: getfield f : Lcom/bytedance/ies/bullet/kit/rn/c/c;
      //   45: invokevirtual b : ()Z
      //   48: istore #4
      //   50: iconst_1
      //   51: istore_3
      //   52: iload #4
      //   54: ifne -> 68
      //   57: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
      //   60: ifnull -> 68
      //   63: iconst_1
      //   64: istore_2
      //   65: goto -> 70
      //   68: iconst_0
      //   69: istore_2
      //   70: iload_2
      //   71: ifeq -> 80
      //   74: aload #6
      //   76: astore_1
      //   77: goto -> 82
      //   80: aconst_null
      //   81: astore_1
      //   82: aload_1
      //   83: ifnull -> 120
      //   86: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
      //   89: astore_1
      //   90: aload_1
      //   91: ifnull -> 111
      //   94: aload_1
      //   95: aload_0
      //   96: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$u;
      //   99: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   102: getfield j : Lcom/bytedance/ies/bullet/b/e/a/h;
      //   105: putfield e : Lcom/bytedance/ies/bullet/b/e/a/h;
      //   108: goto -> 113
      //   111: aconst_null
      //   112: astore_1
      //   113: aload_1
      //   114: astore #5
      //   116: aload_1
      //   117: ifnonnull -> 201
      //   120: aload #6
      //   122: getfield d : Lcom/bytedance/ies/bullet/kit/rn/j;
      //   125: astore #5
      //   127: aload #6
      //   129: getfield e : Lcom/bytedance/ies/bullet/b/g/a/b;
      //   132: astore #8
      //   134: aload_0
      //   135: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$u;
      //   138: getfield d : Z
      //   141: ifeq -> 149
      //   144: aconst_null
      //   145: astore_1
      //   146: goto -> 160
      //   149: aload_0
      //   150: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$u;
      //   153: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   156: getfield j : Lcom/bytedance/ies/bullet/b/e/a/h;
      //   159: astore_1
      //   160: aload #6
      //   162: getfield o : Lcom/bytedance/ies/bullet/kit/rn/b;
      //   165: astore #9
      //   167: new com/bytedance/ies/bullet/kit/rn/a
      //   170: dup
      //   171: aload #5
      //   173: aload #8
      //   175: aload_1
      //   176: aload #6
      //   178: getfield l : Ljava/util/List;
      //   181: aload #6
      //   183: getfield m : Ljava/util/List;
      //   186: aload #6
      //   188: getfield n : Ljava/util/List;
      //   191: aload #9
      //   193: aload #7
      //   195: aconst_null
      //   196: invokespecial <init> : (Lcom/bytedance/ies/bullet/kit/rn/j;Lcom/bytedance/ies/bullet/b/g/a/b;Lcom/bytedance/ies/bullet/b/e/a/h;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lcom/bytedance/ies/bullet/kit/rn/b;Ljava/io/File;Ljava/io/File;)V
      //   199: astore #5
      //   201: aload #5
      //   203: aload_0
      //   204: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$u;
      //   207: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   210: getfield g : Ljava/lang/String;
      //   213: invokevirtual a : (Ljava/lang/String;)V
      //   216: aload #5
      //   218: aload_0
      //   219: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$u;
      //   222: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   225: getfield f : Lcom/bytedance/ies/bullet/kit/rn/c/c;
      //   228: invokevirtual b : ()Z
      //   231: putfield b : Z
      //   234: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
      //   237: ifnonnull -> 306
      //   240: aload_0
      //   241: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$u;
      //   244: getfield e : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   247: getfield p : Z
      //   250: ifne -> 306
      //   253: aload #5
      //   255: putstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
      //   258: aload #5
      //   260: invokevirtual getReactInstanceManager : ()Lcom/facebook/react/ReactInstanceManager;
      //   263: astore_1
      //   264: aload_1
      //   265: ifnull -> 306
      //   268: aload_1
      //   269: invokevirtual getCurrentReactContext : ()Lcom/facebook/react/bridge/ReactContext;
      //   272: ifnonnull -> 287
      //   275: aload_1
      //   276: invokevirtual hasStartedCreatingInitialContext : ()Z
      //   279: ifne -> 287
      //   282: iload_3
      //   283: istore_2
      //   284: goto -> 289
      //   287: iconst_0
      //   288: istore_2
      //   289: iload_2
      //   290: ifeq -> 296
      //   293: goto -> 298
      //   296: aconst_null
      //   297: astore_1
      //   298: aload_1
      //   299: ifnull -> 306
      //   302: aload_1
      //   303: invokevirtual createReactContextInBackground : ()V
      //   306: return
    }
  }
  
  public static final class v extends d.f.b.m implements d.f.a.b<Throwable, x> {
    public v(Object param1Object, com.bytedance.ies.bullet.b.c.a param1a, c param1c1, boolean param1Boolean, c param1c2) {
      super(1);
    }
    
    public final void invoke(Throwable param1Throwable) {
      d.f.b.l.b(param1Throwable, "it");
      Object object = this.b;
      d.f.a.b<File, x> b2 = new d.f.a.b<File, x>(this) {
          public final void invoke(File param2File) {
            // Byte code:
            //   0: aload_1
            //   1: checkcast java/io/File
            //   4: astore #7
            //   6: aload #7
            //   8: ifnonnull -> 22
            //   11: new java/io/FileNotFoundException
            //   14: dup
            //   15: ldc 'rn_base_android & rn_snapshot not found'
            //   17: invokespecial <init> : (Ljava/lang/String;)V
            //   20: pop
            //   21: return
            //   22: aload_0
            //   23: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$v;
            //   26: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   29: astore #6
            //   31: aload #6
            //   33: getfield f : Lcom/bytedance/ies/bullet/kit/rn/c/c;
            //   36: invokevirtual b : ()Z
            //   39: istore #4
            //   41: iconst_1
            //   42: istore_3
            //   43: iload #4
            //   45: ifne -> 59
            //   48: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
            //   51: ifnull -> 59
            //   54: iconst_1
            //   55: istore_2
            //   56: goto -> 61
            //   59: iconst_0
            //   60: istore_2
            //   61: iload_2
            //   62: ifeq -> 71
            //   65: aload #6
            //   67: astore_1
            //   68: goto -> 73
            //   71: aconst_null
            //   72: astore_1
            //   73: aload_1
            //   74: ifnull -> 111
            //   77: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
            //   80: astore_1
            //   81: aload_1
            //   82: ifnull -> 102
            //   85: aload_1
            //   86: aload_0
            //   87: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$v;
            //   90: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   93: getfield j : Lcom/bytedance/ies/bullet/b/e/a/h;
            //   96: putfield e : Lcom/bytedance/ies/bullet/b/e/a/h;
            //   99: goto -> 104
            //   102: aconst_null
            //   103: astore_1
            //   104: aload_1
            //   105: astore #5
            //   107: aload_1
            //   108: ifnonnull -> 192
            //   111: aload #6
            //   113: getfield d : Lcom/bytedance/ies/bullet/kit/rn/j;
            //   116: astore #5
            //   118: aload #6
            //   120: getfield e : Lcom/bytedance/ies/bullet/b/g/a/b;
            //   123: astore #8
            //   125: aload_0
            //   126: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$v;
            //   129: getfield d : Z
            //   132: ifeq -> 140
            //   135: aconst_null
            //   136: astore_1
            //   137: goto -> 151
            //   140: aload_0
            //   141: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$v;
            //   144: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   147: getfield j : Lcom/bytedance/ies/bullet/b/e/a/h;
            //   150: astore_1
            //   151: aload #6
            //   153: getfield o : Lcom/bytedance/ies/bullet/kit/rn/b;
            //   156: astore #9
            //   158: new com/bytedance/ies/bullet/kit/rn/a
            //   161: dup
            //   162: aload #5
            //   164: aload #8
            //   166: aload_1
            //   167: aload #6
            //   169: getfield l : Ljava/util/List;
            //   172: aload #6
            //   174: getfield m : Ljava/util/List;
            //   177: aload #6
            //   179: getfield n : Ljava/util/List;
            //   182: aload #9
            //   184: aconst_null
            //   185: aload #7
            //   187: invokespecial <init> : (Lcom/bytedance/ies/bullet/kit/rn/j;Lcom/bytedance/ies/bullet/b/g/a/b;Lcom/bytedance/ies/bullet/b/e/a/h;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lcom/bytedance/ies/bullet/kit/rn/b;Ljava/io/File;Ljava/io/File;)V
            //   190: astore #5
            //   192: aload #5
            //   194: aload_0
            //   195: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$v;
            //   198: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   201: getfield g : Ljava/lang/String;
            //   204: invokevirtual a : (Ljava/lang/String;)V
            //   207: aload #5
            //   209: aload_0
            //   210: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$v;
            //   213: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   216: getfield f : Lcom/bytedance/ies/bullet/kit/rn/c/c;
            //   219: invokevirtual b : ()Z
            //   222: putfield b : Z
            //   225: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
            //   228: ifnonnull -> 297
            //   231: aload_0
            //   232: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$v;
            //   235: getfield e : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
            //   238: getfield p : Z
            //   241: ifne -> 297
            //   244: aload #5
            //   246: putstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
            //   249: aload #5
            //   251: invokevirtual getReactInstanceManager : ()Lcom/facebook/react/ReactInstanceManager;
            //   254: astore_1
            //   255: aload_1
            //   256: ifnull -> 297
            //   259: aload_1
            //   260: invokevirtual getCurrentReactContext : ()Lcom/facebook/react/bridge/ReactContext;
            //   263: ifnonnull -> 278
            //   266: aload_1
            //   267: invokevirtual hasStartedCreatingInitialContext : ()Z
            //   270: ifne -> 278
            //   273: iload_3
            //   274: istore_2
            //   275: goto -> 280
            //   278: iconst_0
            //   279: istore_2
            //   280: iload_2
            //   281: ifeq -> 287
            //   284: goto -> 289
            //   287: aconst_null
            //   288: astore_1
            //   289: aload_1
            //   290: ifnull -> 297
            //   293: aload_1
            //   294: invokevirtual createReactContextInBackground : ()V
            //   297: return
          }
        };
      d.f.a.b<Throwable, x> b1 = new d.f.a.b<Throwable, x>(this) {
          public final void invoke(Throwable param2Throwable) {
            d.f.b.l.b(param2Throwable, "it");
            new FileNotFoundException("rn_base_android & rn_snapshot not found");
          }
        };
      b2 = b2;
      object = object;
      this.a.b((Uri)object, b2, b1);
    }
  }
  
  public static final class null extends d.f.b.m implements d.f.a.b<File, x> {
    public null(c.v param1v) {
      super(1);
    }
    
    public final void invoke(File param1File) {
      // Byte code:
      //   0: aload_1
      //   1: checkcast java/io/File
      //   4: astore #7
      //   6: aload #7
      //   8: ifnonnull -> 22
      //   11: new java/io/FileNotFoundException
      //   14: dup
      //   15: ldc 'rn_base_android & rn_snapshot not found'
      //   17: invokespecial <init> : (Ljava/lang/String;)V
      //   20: pop
      //   21: return
      //   22: aload_0
      //   23: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$v;
      //   26: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   29: astore #6
      //   31: aload #6
      //   33: getfield f : Lcom/bytedance/ies/bullet/kit/rn/c/c;
      //   36: invokevirtual b : ()Z
      //   39: istore #4
      //   41: iconst_1
      //   42: istore_3
      //   43: iload #4
      //   45: ifne -> 59
      //   48: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
      //   51: ifnull -> 59
      //   54: iconst_1
      //   55: istore_2
      //   56: goto -> 61
      //   59: iconst_0
      //   60: istore_2
      //   61: iload_2
      //   62: ifeq -> 71
      //   65: aload #6
      //   67: astore_1
      //   68: goto -> 73
      //   71: aconst_null
      //   72: astore_1
      //   73: aload_1
      //   74: ifnull -> 111
      //   77: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
      //   80: astore_1
      //   81: aload_1
      //   82: ifnull -> 102
      //   85: aload_1
      //   86: aload_0
      //   87: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$v;
      //   90: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   93: getfield j : Lcom/bytedance/ies/bullet/b/e/a/h;
      //   96: putfield e : Lcom/bytedance/ies/bullet/b/e/a/h;
      //   99: goto -> 104
      //   102: aconst_null
      //   103: astore_1
      //   104: aload_1
      //   105: astore #5
      //   107: aload_1
      //   108: ifnonnull -> 192
      //   111: aload #6
      //   113: getfield d : Lcom/bytedance/ies/bullet/kit/rn/j;
      //   116: astore #5
      //   118: aload #6
      //   120: getfield e : Lcom/bytedance/ies/bullet/b/g/a/b;
      //   123: astore #8
      //   125: aload_0
      //   126: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$v;
      //   129: getfield d : Z
      //   132: ifeq -> 140
      //   135: aconst_null
      //   136: astore_1
      //   137: goto -> 151
      //   140: aload_0
      //   141: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$v;
      //   144: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   147: getfield j : Lcom/bytedance/ies/bullet/b/e/a/h;
      //   150: astore_1
      //   151: aload #6
      //   153: getfield o : Lcom/bytedance/ies/bullet/kit/rn/b;
      //   156: astore #9
      //   158: new com/bytedance/ies/bullet/kit/rn/a
      //   161: dup
      //   162: aload #5
      //   164: aload #8
      //   166: aload_1
      //   167: aload #6
      //   169: getfield l : Ljava/util/List;
      //   172: aload #6
      //   174: getfield m : Ljava/util/List;
      //   177: aload #6
      //   179: getfield n : Ljava/util/List;
      //   182: aload #9
      //   184: aconst_null
      //   185: aload #7
      //   187: invokespecial <init> : (Lcom/bytedance/ies/bullet/kit/rn/j;Lcom/bytedance/ies/bullet/b/g/a/b;Lcom/bytedance/ies/bullet/b/e/a/h;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lcom/bytedance/ies/bullet/kit/rn/b;Ljava/io/File;Ljava/io/File;)V
      //   190: astore #5
      //   192: aload #5
      //   194: aload_0
      //   195: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$v;
      //   198: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   201: getfield g : Ljava/lang/String;
      //   204: invokevirtual a : (Ljava/lang/String;)V
      //   207: aload #5
      //   209: aload_0
      //   210: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$v;
      //   213: getfield c : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   216: getfield f : Lcom/bytedance/ies/bullet/kit/rn/c/c;
      //   219: invokevirtual b : ()Z
      //   222: putfield b : Z
      //   225: getstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
      //   228: ifnonnull -> 297
      //   231: aload_0
      //   232: getfield a : Lcom/bytedance/ies/bullet/kit/rn/internal/c$v;
      //   235: getfield e : Lcom/bytedance/ies/bullet/kit/rn/internal/c;
      //   238: getfield p : Z
      //   241: ifne -> 297
      //   244: aload #5
      //   246: putstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
      //   249: aload #5
      //   251: invokevirtual getReactInstanceManager : ()Lcom/facebook/react/ReactInstanceManager;
      //   254: astore_1
      //   255: aload_1
      //   256: ifnull -> 297
      //   259: aload_1
      //   260: invokevirtual getCurrentReactContext : ()Lcom/facebook/react/bridge/ReactContext;
      //   263: ifnonnull -> 278
      //   266: aload_1
      //   267: invokevirtual hasStartedCreatingInitialContext : ()Z
      //   270: ifne -> 278
      //   273: iload_3
      //   274: istore_2
      //   275: goto -> 280
      //   278: iconst_0
      //   279: istore_2
      //   280: iload_2
      //   281: ifeq -> 287
      //   284: goto -> 289
      //   287: aconst_null
      //   288: astore_1
      //   289: aload_1
      //   290: ifnull -> 297
      //   293: aload_1
      //   294: invokevirtual createReactContextInBackground : ()V
      //   297: return
    }
  }
  
  public static final class null extends d.f.b.m implements d.f.a.b<Throwable, x> {
    public null(c.v param1v) {
      super(1);
    }
    
    public final void invoke(Throwable param1Throwable) {
      d.f.b.l.b(param1Throwable, "it");
      new FileNotFoundException("rn_base_android & rn_snapshot not found");
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\bytedance\ies\bullet\kit\rn\internal\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */