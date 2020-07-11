package com.bytedance.ies.bullet.kit.rn.b;

import android.net.Uri;
import android.view.View;
import com.bytedance.ies.bullet.b.a.c;
import com.bytedance.ies.bullet.b.e.d;
import com.bytedance.ies.bullet.b.h.a;
import com.bytedance.ies.bullet.b.h.e;
import com.bytedance.ies.bullet.b.h.f;
import com.bytedance.ies.bullet.b.h.k;
import com.bytedance.ies.bullet.b.h.l;
import com.bytedance.ies.bullet.kit.rn.f;
import d.a.m;
import d.f;
import d.f.b.l;
import d.f.b.m;
import d.f.b.u;
import d.f.b.v;
import d.f.b.x;
import d.g;
import d.k;
import d.k.d;
import d.k.h;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

public final class a extends a {
  public final f m = g.a(k.NONE, b.a);
  
  public final ConcurrentHashMap<String, Long> n;
  
  public String o;
  
  public long p;
  
  public long q;
  
  public long r;
  
  public long s;
  
  public long t;
  
  public long u;
  
  public final f v;
  
  private long w;
  
  private long x;
  
  private final int y;
  
  public a(Uri paramUri, com.bytedance.ies.bullet.b.g.a.b paramb, String paramString1, String paramString2) {
    super(paramUri, paramb, paramString1, paramString2);
    ConcurrentHashMap<Object, Object> concurrentHashMap = new ConcurrentHashMap<Object, Object>();
    Long long_ = Long.valueOf(0L);
    concurrentHashMap.put("PageStartTime", long_);
    concurrentHashMap.put("StartLoadTime", long_);
    concurrentHashMap.put("FirstDrawTime", long_);
    concurrentHashMap.put("FirstScreenTime", long_);
    concurrentHashMap.put("PageFinishTime", long_);
    this.n = (ConcurrentHashMap)concurrentHashMap;
    this.y = 2;
    this.v = g.a(k.NONE, new a(this, paramUri));
  }
  
  public final int a() {
    return this.y;
  }
  
  public final void a(View paramView) {
    this.w = System.currentTimeMillis();
  }
  
  public final void a(String paramString) {
    l.b(paramString, "id");
    super.a(paramString);
    this.p = System.currentTimeMillis();
    f f1 = (f)d();
    JSONObject jSONObject = new JSONObject();
    jSONObject.put("trigger", "load_url");
    a.a(this, "hybrid_app_monitor_load_url_event", f1, jSONObject, null, null, 24, null);
  }
  
  public final void a(String paramString, f paramf, JSONObject paramJSONObject1, JSONObject paramJSONObject2, JSONObject paramJSONObject3) {
    l.b(paramString, "eventName");
    l.b(paramf, "identifier");
    JSONObject jSONObject = new JSONObject();
    if (paramJSONObject1 != null)
      c.a(jSONObject, paramJSONObject1); 
    f f1 = (f)this.f.c(f.class);
    if (f1 != null) {
      d d = f1.b();
      if (d != null) {
        String str = d.getKitSDKVersion();
        if (str != null)
          jSONObject.put("engin_sdk_version", str); 
      } 
    } 
    super.a(paramString, paramf, jSONObject, paramJSONObject2, paramJSONObject3);
  }
  
  public final void a(boolean paramBoolean) {
    boolean bool;
    JSONObject jSONObject1;
    this.t = System.currentTimeMillis();
    f f1 = (f)d();
    JSONObject jSONObject3 = new JSONObject();
    jSONObject3.put("trigger", "prepare_rn_end");
    jSONObject3.put("is_first_screen", "first_screen");
    jSONObject3.put("is_reuse", String.valueOf(paramBoolean));
    JSONObject jSONObject2 = new JSONObject();
    if (this.r > 0L && this.q > 0L) {
      bool = true;
    } else {
      bool = false;
    } 
    if (bool) {
      jSONObject1 = jSONObject2;
    } else {
      jSONObject1 = null;
    } 
    if (jSONObject1 != null)
      jSONObject1.put("dynamic_update_interval", this.r - this.q); 
    jSONObject2.put("prepare_rn_interval", this.t - this.s);
    jSONObject2.put("event_ts", this.t);
    a.a(this, "hybrid_app_monitor_load_url_event", f1, jSONObject3, jSONObject2, null, 16, null);
  }
  
  public final void b(View paramView) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual c : ()Lcom/bytedance/ies/bullet/b/h/e;
    //   4: astore #4
    //   6: aconst_null
    //   7: astore #5
    //   9: aload #4
    //   11: ifnull -> 76
    //   14: aload #4
    //   16: invokeinterface c : ()Z
    //   21: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   24: astore #4
    //   26: aload #4
    //   28: invokevirtual booleanValue : ()Z
    //   31: ifeq -> 37
    //   34: goto -> 40
    //   37: aconst_null
    //   38: astore #4
    //   40: aload #4
    //   42: ifnull -> 76
    //   45: aload #4
    //   47: invokevirtual booleanValue : ()Z
    //   50: pop
    //   51: invokestatic currentTimeMillis : ()J
    //   54: lstore_2
    //   55: aload_1
    //   56: invokestatic a : (Landroid/view/View;)Z
    //   59: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   62: astore_1
    //   63: invokestatic currentTimeMillis : ()J
    //   66: lload_2
    //   67: lsub
    //   68: invokestatic valueOf : (J)Ljava/lang/Long;
    //   71: astore #4
    //   73: goto -> 82
    //   76: aconst_null
    //   77: astore_1
    //   78: aload #5
    //   80: astore #4
    //   82: aload_0
    //   83: invokevirtual e : ()Lcom/bytedance/ies/bullet/b/h/j;
    //   86: checkcast com/bytedance/ies/bullet/b/h/f
    //   89: astore #6
    //   91: new org/json/JSONObject
    //   94: dup
    //   95: invokespecial <init> : ()V
    //   98: astore #7
    //   100: aload_1
    //   101: ifnull -> 120
    //   104: aload #7
    //   106: ldc_w 'isBlank'
    //   109: aload_1
    //   110: invokevirtual booleanValue : ()Z
    //   113: invokestatic valueOf : (Z)Ljava/lang/String;
    //   116: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   119: pop
    //   120: aload_0
    //   121: getfield u : J
    //   124: lconst_0
    //   125: lcmp
    //   126: ifle -> 136
    //   129: ldc_w 'true'
    //   132: astore_1
    //   133: goto -> 140
    //   136: ldc_w 'false'
    //   139: astore_1
    //   140: aload #7
    //   142: ldc_w 'pageFinish'
    //   145: aload_1
    //   146: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   149: pop
    //   150: aload_0
    //   151: getfield u : J
    //   154: lconst_0
    //   155: lcmp
    //   156: ifle -> 166
    //   159: ldc_w 'page_finish'
    //   162: astore_1
    //   163: goto -> 292
    //   166: aload_0
    //   167: getfield o : Ljava/lang/String;
    //   170: astore #5
    //   172: aload #5
    //   174: ifnull -> 194
    //   177: aload #5
    //   179: astore_1
    //   180: aload #5
    //   182: ifnonnull -> 292
    //   185: invokestatic a : ()V
    //   188: aload #5
    //   190: astore_1
    //   191: goto -> 292
    //   194: aload_0
    //   195: getfield t : J
    //   198: lconst_0
    //   199: lcmp
    //   200: ifle -> 209
    //   203: ldc 'prepare_rn_end'
    //   205: astore_1
    //   206: goto -> 292
    //   209: aload_0
    //   210: getfield s : J
    //   213: lconst_0
    //   214: lcmp
    //   215: ifle -> 225
    //   218: ldc_w 'prepare_rn_start'
    //   221: astore_1
    //   222: goto -> 292
    //   225: aload_0
    //   226: getfield r : J
    //   229: lconst_0
    //   230: lcmp
    //   231: ifle -> 241
    //   234: ldc_w 'dynamic_update_end'
    //   237: astore_1
    //   238: goto -> 292
    //   241: aload_0
    //   242: getfield q : J
    //   245: lconst_0
    //   246: lcmp
    //   247: ifle -> 257
    //   250: ldc_w 'dynamic_update_start'
    //   253: astore_1
    //   254: goto -> 292
    //   257: aload_0
    //   258: getfield p : J
    //   261: lconst_0
    //   262: lcmp
    //   263: ifle -> 272
    //   266: ldc 'load_url'
    //   268: astore_1
    //   269: goto -> 292
    //   272: aload_0
    //   273: getfield w : J
    //   276: lconst_0
    //   277: lcmp
    //   278: ifle -> 288
    //   281: ldc_w 'attach_window'
    //   284: astore_1
    //   285: goto -> 292
    //   288: ldc_w 'none'
    //   291: astore_1
    //   292: aload #7
    //   294: ldc 'trigger'
    //   296: aload_1
    //   297: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   300: pop
    //   301: new org/json/JSONObject
    //   304: dup
    //   305: invokespecial <init> : ()V
    //   308: astore_1
    //   309: aload #4
    //   311: ifnull -> 330
    //   314: aload_1
    //   315: ldc_w 'detectDuration'
    //   318: aload #4
    //   320: checkcast java/lang/Number
    //   323: invokevirtual longValue : ()J
    //   326: invokevirtual put : (Ljava/lang/String;J)Lorg/json/JSONObject;
    //   329: pop
    //   330: aload_1
    //   331: ldc_w 'loadUrlToAttachedWindow'
    //   334: aload_0
    //   335: getfield w : J
    //   338: aload_0
    //   339: getfield p : J
    //   342: lsub
    //   343: invokevirtual put : (Ljava/lang/String;J)Lorg/json/JSONObject;
    //   346: pop
    //   347: aload_0
    //   348: getfield u : J
    //   351: lstore_2
    //   352: lload_2
    //   353: lconst_0
    //   354: lcmp
    //   355: ifle -> 372
    //   358: aload_1
    //   359: ldc_w 'loadUrlToPageFinish'
    //   362: lload_2
    //   363: aload_0
    //   364: getfield p : J
    //   367: lsub
    //   368: invokevirtual put : (Ljava/lang/String;J)Lorg/json/JSONObject;
    //   371: pop
    //   372: aload_1
    //   373: ldc_w 'attachedWindowToDetachWindow'
    //   376: aload_0
    //   377: getfield x : J
    //   380: aload_0
    //   381: getfield w : J
    //   384: lsub
    //   385: invokevirtual put : (Ljava/lang/String;J)Lorg/json/JSONObject;
    //   388: pop
    //   389: aload_1
    //   390: ldc_w 'loadUrlToDetachWindow'
    //   393: aload_0
    //   394: getfield x : J
    //   397: aload_0
    //   398: getfield p : J
    //   401: lsub
    //   402: invokevirtual put : (Ljava/lang/String;J)Lorg/json/JSONObject;
    //   405: pop
    //   406: aload_0
    //   407: ldc_w 'hybrid_app_monitor_rn_blank_screen'
    //   410: aload #6
    //   412: aload #7
    //   414: aload_1
    //   415: aconst_null
    //   416: bipush #16
    //   418: aconst_null
    //   419: invokestatic a : (Lcom/bytedance/ies/bullet/b/h/a;Ljava/lang/String;Lcom/bytedance/ies/bullet/b/h/f;Lorg/json/JSONObject;Lorg/json/JSONObject;Lorg/json/JSONObject;ILjava/lang/Object;)V
    //   422: return
  }
  
  public final void g() {
    this.q = System.currentTimeMillis();
    f f1 = (f)d();
    JSONObject jSONObject1 = new JSONObject();
    jSONObject1.put("trigger", "dynamic_update_start");
    jSONObject1.put("is_first_screen", "first_screen");
    JSONObject jSONObject2 = new JSONObject();
    jSONObject2.put("event_ts", this.q);
    a.a(this, "hybrid_app_monitor_load_url_event", f1, jSONObject1, jSONObject2, null, 16, null);
  }
  
  public final void h() {
    this.r = System.currentTimeMillis();
    f f1 = (f)d();
    JSONObject jSONObject1 = new JSONObject();
    jSONObject1.put("trigger", "dynamic_update_end");
    jSONObject1.put("is_first_screen", "first_screen");
    JSONObject jSONObject2 = new JSONObject();
    jSONObject2.put("dynamic_update_interval", this.r - this.q);
    jSONObject2.put("event_ts", this.r);
    a.a(this, "hybrid_app_monitor_load_url_event", f1, jSONObject1, jSONObject2, null, 16, null);
  }
  
  static final class a extends m implements d.f.a.a<Boolean> {
    a(a param1a, Uri param1Uri) {
      super(0);
    }
  }
  
  static final class b extends m implements d.f.a.a<l> {
    public static final b a = new b();
    
    b() {
      super(0);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\bytedance\ies\bullet\kit\rn\b\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */