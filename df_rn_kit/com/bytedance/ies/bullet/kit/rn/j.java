package com.bytedance.ies.bullet.kit.rn;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import com.bytedance.ies.bullet.b.e.a.h;
import com.bytedance.ies.bullet.b.e.a.k;
import com.bytedance.ies.bullet.b.e.aa;
import com.bytedance.ies.bullet.b.e.i;
import com.bytedance.ies.bullet.b.e.m;
import com.bytedance.ies.bullet.b.e.p;
import com.bytedance.ies.bullet.b.h.i;
import com.bytedance.ies.bullet.b.h.l;
import com.bytedance.ies.bullet.b.i.t;
import com.bytedance.ies.bullet.kit.rn.b.a;
import com.bytedance.ies.bullet.kit.rn.c.c;
import com.bytedance.ies.bullet.kit.rn.d.b;
import com.bytedance.ies.bullet.kit.rn.internal.c;
import com.bytedance.ies.bullet.ui.common.c.c;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.TimingEventListener;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import d.f.b.g;
import d.f.b.l;
import d.f.b.m;
import d.m.p;
import d.u;
import d.x;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import org.json.JSONObject;

public final class j extends c<ReactRootView> implements f, DefaultHardwareBackBtnHandler {
  public static final a l = new a(null);
  
  public final List<k> e = new ArrayList<k>();
  
  public final List<c> f = new ArrayList<c>();
  
  public final List<Object> g = new ArrayList();
  
  public final List<e> h = new ArrayList<e>();
  
  public b i;
  
  public ReactInstanceManager j;
  
  public boolean k;
  
  private Uri m;
  
  private final com.bytedance.ies.bullet.b.b.a n;
  
  private final b o;
  
  public j(RnKitApi paramRnKitApi, aa paramaa, List<String> paramList, com.bytedance.ies.bullet.b.d paramd, com.bytedance.ies.bullet.b.g.a.b paramb) {
    super((com.bytedance.ies.bullet.b.e.d)paramRnKitApi, paramaa, paramList, paramd, paramb);
    com.bytedance.ies.bullet.b.b.a a2 = (com.bytedance.ies.bullet.b.b.a)paramb.c(com.bytedance.ies.bullet.b.b.a.class);
    com.bytedance.ies.bullet.b.b.a a1 = a2;
    if (a2 == null)
      a1 = new com.bytedance.ies.bullet.b.b.a(false, false, 3, null); 
    this.n = a1;
    this.o = new b(this);
  }
  
  private final c B() {
    t t = f();
    if (t == null)
      l.a(); 
    if (t != null)
      return (c)t; 
    throw new u("null cannot be cast to non-null type com.bytedance.ies.bullet.kit.rn.param.RnKitParamsBundle");
  }
  
  public final com.bytedance.ies.bullet.b.h.a a(Uri paramUri1, Uri paramUri2) {
    l.b(paramUri1, "uri");
    com.bytedance.ies.bullet.b.g.a.b b1 = new com.bytedance.ies.bullet.b.g.a.b();
    b1.a(com.bytedance.ies.bullet.b.h.d.class, q());
    b1.a(com.bytedance.ies.bullet.b.h.e.class, r());
    b1.a(f.class, this);
    b1.b(Uri.class, paramUri2);
    com.bytedance.ies.bullet.ui.common.c.d d = a();
    if (d != null) {
      ReactRootView reactRootView = (ReactRootView)d.a;
    } else {
      d = null;
    } 
    b1.b(View.class, d);
    String str3 = (String)((com.bytedance.ies.bullet.ui.common.d.a)B()).K.b();
    String str2 = "";
    String str1 = str3;
    if (str3 == null)
      str1 = ""; 
    str3 = (String)((com.bytedance.ies.bullet.ui.common.d.a)B()).L.b();
    if (str3 != null)
      str2 = str3; 
    return (com.bytedance.ies.bullet.b.h.a)new a(paramUri1, b1, str1, str2);
  }
  
  public final void a(Uri paramUri, boolean paramBoolean) {
    l.b(paramUri, "input");
    for (com.bytedance.ies.bullet.ui.common.c.d d : z()) {
      d.a(paramUri);
      ((ReactRootView)d.a).unmountReactApplication();
      ReactRootView reactRootView = (ReactRootView)d.a;
      ReactInstanceManager reactInstanceManager = this.j;
      String str = (String)(B()).e.b();
      Bundle bundle = new Bundle();
      Set set = paramUri.getQueryParameterNames();
      l.a(set, "input.queryParameterNames");
      for (String str1 : set)
        bundle.putString(str1, paramUri.getQueryParameter(str1)); 
      bundle.putString("reactId", (k_()).a);
      reactRootView.startReactApplication(reactInstanceManager, str, bundle);
      if (!this.k)
        d.b(paramUri); 
    } 
    this.m = paramUri;
  }
  
  public final void a(com.bytedance.ies.bullet.ui.common.c.d<ReactRootView> paramd) {
    l.b(paramd, "viewComponent");
  }
  
  public final void a(d.f.a.b<? super List<com.bytedance.ies.bullet.ui.common.c.d<ReactRootView>>, x> paramb) {
    // Byte code:
    //   0: aload_1
    //   1: ldc_w 'provider'
    //   4: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   7: aload_0
    //   8: invokevirtual d : ()Lcom/bytedance/ies/bullet/b/g/a/b;
    //   11: ldc_w android/content/Context
    //   14: invokevirtual c : (Ljava/lang/Class;)Ljava/lang/Object;
    //   17: checkcast android/content/Context
    //   20: astore #4
    //   22: iconst_1
    //   23: istore_3
    //   24: aload #4
    //   26: ifnull -> 87
    //   29: aload_0
    //   30: getfield j : Lcom/facebook/react/ReactInstanceManager;
    //   33: ifnull -> 87
    //   36: aload_0
    //   37: invokespecial B : ()Lcom/bytedance/ies/bullet/kit/rn/c/c;
    //   40: getfield e : Lcom/bytedance/ies/bullet/b/i/f;
    //   43: invokeinterface b : ()Ljava/lang/Object;
    //   48: checkcast java/lang/CharSequence
    //   51: astore #5
    //   53: aload #5
    //   55: ifnull -> 76
    //   58: aload #5
    //   60: invokeinterface length : ()I
    //   65: ifne -> 71
    //   68: goto -> 76
    //   71: iconst_0
    //   72: istore_2
    //   73: goto -> 78
    //   76: iconst_1
    //   77: istore_2
    //   78: iload_2
    //   79: ifne -> 87
    //   82: iload_3
    //   83: istore_2
    //   84: goto -> 89
    //   87: iconst_0
    //   88: istore_2
    //   89: iload_2
    //   90: ifeq -> 126
    //   93: aload_1
    //   94: new com/bytedance/ies/bullet/ui/common/c/d
    //   97: dup
    //   98: new com/facebook/react/ReactRootView
    //   101: dup
    //   102: aload #4
    //   104: invokespecial <init> : (Landroid/content/Context;)V
    //   107: checkcast android/view/View
    //   110: aconst_null
    //   111: iconst_2
    //   112: aconst_null
    //   113: invokespecial <init> : (Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;ILd/f/b/g;)V
    //   116: invokestatic a : (Ljava/lang/Object;)Ljava/util/List;
    //   119: invokeinterface invoke : (Ljava/lang/Object;)Ljava/lang/Object;
    //   124: pop
    //   125: return
    //   126: aload_1
    //   127: invokestatic a : ()Ljava/util/List;
    //   130: invokeinterface invoke : (Ljava/lang/Object;)Ljava/lang/Object;
    //   135: pop
    //   136: return
  }
  
  public final boolean a(Uri paramUri, d.f.a.b<? super Throwable, x> paramb) {
    l.b(paramUri, "uri");
    l.b(paramb, "reject");
    if (l.a((B()).f.b(), Boolean.valueOf(true))) {
      paramb.invoke(new p((i)this, paramUri, null, 4, null));
      return false;
    } 
    b(paramUri);
    com.bytedance.ies.bullet.b.h.d d = q();
    if (d != null) {
      JSONObject jSONObject = new JSONObject();
      jSONObject.put("type", 2);
      d.a("bullet_rn_aab_dynamic", jSONObject);
    } 
    return true;
  }
  
  public final void b(Uri paramUri, d.f.a.b<? super Uri, x> paramb, d.f.a.b<? super Throwable, x> paramb1) {
    l.b(paramUri, "input");
    l.b(paramb, "resolve");
    l.b(paramb1, "reject");
    String str = (String)(B()).h.b();
    if (str != null) {
      boolean bool = p.a(str);
      SharedPreferences.Editor editor = null;
      if ((bool ^ true) == 0)
        str = null; 
      if (str != null) {
        com.bytedance.ies.bullet.ui.common.d d = A();
        if (d != null) {
          Activity activity = d.a();
          if (activity != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context)activity);
            if (sharedPreferences != null)
              editor = sharedPreferences.edit(); 
            if (editor != null) {
              editor.putString("debug_http_host", str);
              if (editor != null)
                editor.apply(); 
            } 
          } 
        } 
      } 
    } 
    c c1 = new c(this, paramb, paramUri, paramb1);
    com.bytedance.ies.bullet.b.g.a.b b1 = d();
    c c2 = B();
    str = (k_()).a;
    com.bytedance.ies.bullet.b.c.a a1 = p();
    a a2 = y();
    h h = o();
    List<k> list = this.e;
    b b2 = this.i;
    (new c(this, b1, c2, str, a1, a2, h, list, this.g, this.f, this.h, b2, c1, w(), 0L)).a(new c.c());
  }
  
  public final void b(Throwable paramThrowable) {
    super.b(paramThrowable);
    com.bytedance.ies.bullet.ui.common.d d = A();
    if (d != null)
      d.b((com.bytedance.ies.bullet.ui.common.b.a)this.o); 
    ReactInstanceManager reactInstanceManager = this.j;
    if (reactInstanceManager != null) {
      d = A();
      if (d != null) {
        Activity activity = d.a();
      } else {
        d = null;
      } 
      reactInstanceManager.onHostDestroy((Activity)d);
      reactInstanceManager.deleteJSBundleFile();
      for (com.bytedance.ies.bullet.ui.common.c.d d1 : z()) {
        reactInstanceManager.detachRootView((ReactRootView)d1.a);
        ((ReactRootView)d1.a).unmountReactApplication();
      } 
    } 
  }
  
  public final void b(List<String> paramList, com.bytedance.ies.bullet.b.d paramd) {
    l.b(paramList, "packageNames");
    l.b(paramd, "kitPackageRegistryBundle");
    super.b(paramList, paramd);
    this.e.clear();
    f f1 = new f(this);
    for (m m1 : m()) {
      if (m1 != null) {
        f1.invoke(m1);
        continue;
      } 
      throw new u("null cannot be cast to non-null type com.bytedance.ies.bullet.kit.rn.IRnKitSettingsProvider");
    } 
    m m = l_();
    if (m != null)
      if (m != null) {
        i i = (i)m;
        if (i != null)
          f1.invoke(i); 
      } else {
        throw new u("null cannot be cast to non-null type com.bytedance.ies.bullet.kit.rn.IRnKitSettingsProvider");
      }  
    this.f.clear();
    this.g.clear();
    this.h.clear();
    e e = new e(this);
    for (com.bytedance.ies.bullet.b.e.e e2 : n()) {
      if (e2 != null) {
        e.invoke(e2);
        continue;
      } 
      throw new u("null cannot be cast to non-null type com.bytedance.ies.bullet.kit.rn.IRnKitDelegatesProvider");
    } 
    com.bytedance.ies.bullet.b.e.e e1 = l();
    if (e1 != null)
      if (e1 != null) {
        g g = (g)e1;
        if (g != null) {
          e.invoke(g);
          return;
        } 
      } else {
        throw new u("null cannot be cast to non-null type com.bytedance.ies.bullet.kit.rn.IRnKitDelegatesProvider");
      }  
  }
  
  public final void c(List<String> paramList, com.bytedance.ies.bullet.b.d paramd) {
    l.b(paramList, "packageNames");
    l.b(paramd, "newRegistryBundle");
  }
  
  public final Uri h() {
    return this.m;
  }
  
  public final void invokeDefaultOnBackPressed() {
    com.bytedance.ies.bullet.ui.common.d d = A();
    if (d != null) {
      Activity activity = d.a();
      if (activity != null)
        activity.finish(); 
    } 
  }
  
  public final void k() {
    if (!this.k)
      return; 
    Uri uri = this.m;
    if (uri != null) {
      Iterator<com.bytedance.ies.bullet.ui.common.c.d> iterator = z().iterator();
      while (iterator.hasNext())
        ((com.bytedance.ies.bullet.ui.common.c.d)iterator.next()).b(uri); 
    } 
  }
  
  public final void onEvent(k paramk) {
    l.b(paramk, "event");
    Object object = paramk.b();
    WritableMap writableMap2 = null;
    WritableMap writableMap1 = writableMap2;
    if (object != null)
      if (object instanceof WritableMap) {
        writableMap1 = (WritableMap)object;
      } else {
        writableMap1 = writableMap2;
        if (object instanceof JSONObject)
          writableMap1 = b.a((JSONObject)object); 
      }  
    ReactContext reactContext = v();
    String str = paramk.a();
    l.b(str, "eventName");
    if (reactContext != null) {
      DeviceEventManagerModule.RCTDeviceEventEmitter rCTDeviceEventEmitter = (DeviceEventManagerModule.RCTDeviceEventEmitter)reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);
      if (rCTDeviceEventEmitter != null)
        rCTDeviceEventEmitter.emit(str, writableMap1); 
    } 
  }
  
  public final void t() {
    com.bytedance.ies.bullet.ui.common.d d = A();
    if (d != null)
      d.a((com.bytedance.ies.bullet.ui.common.b.a)this.o); 
  }
  
  public final ReactContext v() {
    ReactInstanceManager reactInstanceManager = this.j;
    return (reactInstanceManager != null) ? reactInstanceManager.getCurrentReactContext() : null;
  }
  
  public final boolean w() {
    return (B().b() && this.n.a);
  }
  
  public final String x() {
    StringBuilder stringBuilder = new StringBuilder("React Native(");
    stringBuilder.append(b().getKitSDKVersion());
    stringBuilder.append(')');
    return stringBuilder.toString();
  }
  
  public final a y() {
    com.bytedance.ies.bullet.b.h.a a1 = g();
    if (a1 != null) {
      if (a1 != null)
        return (a)a1; 
      throw new u("null cannot be cast to non-null type com.bytedance.ies.bullet.kit.rn.monitor.RnKitMonitorSession");
    } 
    return null;
  }
  
  public static final class a {
    private a() {}
  }
  
  public static final class b extends com.bytedance.ies.bullet.ui.common.a {
    b(j param1j) {}
    
    public final void a(Activity param1Activity) {
      l.b(param1Activity, "activity");
      ReactInstanceManager reactInstanceManager = this.a.j;
      if (reactInstanceManager != null)
        reactInstanceManager.onHostResume(param1Activity, this.a); 
    }
    
    public final void b(Activity param1Activity) {
      l.b(param1Activity, "activity");
      ReactInstanceManager reactInstanceManager = this.a.j;
      if (reactInstanceManager != null)
        reactInstanceManager.onHostPause(param1Activity); 
    }
    
    public final boolean c(Activity param1Activity) {
      l.b(param1Activity, "activity");
      ReactContext reactContext = this.a.v();
      if (reactContext != null) {
        CatalystInstance catalystInstance = reactContext.getCatalystInstance();
        if (catalystInstance != null && !catalystInstance.isDestroyed()) {
          ReactInstanceManager reactInstanceManager = this.a.j;
          if (reactInstanceManager != null)
            reactInstanceManager.onBackPressed(); 
          return true;
        } 
      } 
      return false;
    }
  }
  
  public static final class c implements c.b {
    c(j param1j, d.f.a.b param1b1, Uri param1Uri, d.f.a.b param1b2) {}
    
    public final void a(c param1c, ReactInstanceManager param1ReactInstanceManager) {
      // Byte code:
      //   0: aload_1
      //   1: ldc 'builder'
      //   3: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
      //   6: aload_2
      //   7: ldc 'manager'
      //   9: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
      //   12: aload_0
      //   13: getfield a : Lcom/bytedance/ies/bullet/kit/rn/j;
      //   16: astore #5
      //   18: aload_2
      //   19: invokevirtual getCurrentReactContext : ()Lcom/facebook/react/bridge/ReactContext;
      //   22: astore #4
      //   24: aload #4
      //   26: ifnull -> 160
      //   29: aload #4
      //   31: invokevirtual getCatalystInstance : ()Lcom/facebook/react/bridge/CatalystInstance;
      //   34: astore #4
      //   36: aload #4
      //   38: ifnull -> 160
      //   41: aload #4
      //   43: instanceof com/facebook/react/bridge/CatalystInstanceImpl
      //   46: ifeq -> 96
      //   49: aload #5
      //   51: invokevirtual y : ()Lcom/bytedance/ies/bullet/kit/rn/b/a;
      //   54: ifnull -> 96
      //   57: aload #5
      //   59: invokevirtual y : ()Lcom/bytedance/ies/bullet/kit/rn/b/a;
      //   62: astore #6
      //   64: aload #6
      //   66: ifnonnull -> 72
      //   69: invokestatic a : ()V
      //   72: aload #6
      //   74: getfield v : Ld/f;
      //   77: invokeinterface getValue : ()Ljava/lang/Object;
      //   82: checkcast java/lang/Boolean
      //   85: invokevirtual booleanValue : ()Z
      //   88: ifeq -> 96
      //   91: iconst_1
      //   92: istore_3
      //   93: goto -> 98
      //   96: iconst_0
      //   97: istore_3
      //   98: iload_3
      //   99: ifeq -> 105
      //   102: goto -> 108
      //   105: aconst_null
      //   106: astore #4
      //   108: aload #4
      //   110: ifnull -> 160
      //   113: aload #4
      //   115: ifnull -> 150
      //   118: aload #4
      //   120: checkcast com/facebook/react/bridge/CatalystInstanceImpl
      //   123: astore #4
      //   125: aload #4
      //   127: ifnull -> 160
      //   130: aload #4
      //   132: new com/bytedance/ies/bullet/kit/rn/j$d
      //   135: dup
      //   136: aload #5
      //   138: invokespecial <init> : (Lcom/bytedance/ies/bullet/kit/rn/j;)V
      //   141: checkcast com/facebook/react/bridge/TimingEventListener
      //   144: invokevirtual setTimingEventsListener : (Lcom/facebook/react/bridge/TimingEventListener;)V
      //   147: goto -> 160
      //   150: new d/u
      //   153: dup
      //   154: ldc 'null cannot be cast to non-null type com.facebook.react.bridge.CatalystInstanceImpl'
      //   156: invokespecial <init> : (Ljava/lang/String;)V
      //   159: athrow
      //   160: aload_0
      //   161: getfield a : Lcom/bytedance/ies/bullet/kit/rn/j;
      //   164: astore #5
      //   166: aload #5
      //   168: invokevirtual A : ()Lcom/bytedance/ies/bullet/ui/common/d;
      //   171: astore #4
      //   173: aload #4
      //   175: ifnull -> 190
      //   178: aload #4
      //   180: invokeinterface a : ()Landroid/app/Activity;
      //   185: astore #4
      //   187: goto -> 193
      //   190: aconst_null
      //   191: astore #4
      //   193: aload_2
      //   194: aload #4
      //   196: aload_0
      //   197: getfield a : Lcom/bytedance/ies/bullet/kit/rn/j;
      //   200: checkcast com/facebook/react/modules/core/DefaultHardwareBackBtnHandler
      //   203: invokevirtual onHostResume : (Landroid/app/Activity;Lcom/facebook/react/modules/core/DefaultHardwareBackBtnHandler;)V
      //   206: aload #5
      //   208: aload_2
      //   209: putfield j : Lcom/facebook/react/ReactInstanceManager;
      //   212: aload_0
      //   213: getfield b : Ld/f/a/b;
      //   216: aload_0
      //   217: getfield c : Landroid/net/Uri;
      //   220: invokeinterface invoke : (Ljava/lang/Object;)Ljava/lang/Object;
      //   225: pop
      //   226: aload_0
      //   227: getfield a : Lcom/bytedance/ies/bullet/kit/rn/j;
      //   230: invokevirtual w : ()Z
      //   233: ifne -> 315
      //   236: aconst_null
      //   237: putstatic com/bytedance/ies/bullet/kit/rn/internal/c.q : Lcom/bytedance/ies/bullet/kit/rn/a;
      //   240: aload_1
      //   241: getfield h : Lcom/bytedance/ies/bullet/b/c/a;
      //   244: astore_2
      //   245: aload_2
      //   246: ifnull -> 315
      //   249: ldc 'rn_base_android'
      //   251: aconst_null
      //   252: iconst_2
      //   253: aconst_null
      //   254: invokestatic a : (Ljava/lang/String;Landroid/net/Uri;ILjava/lang/Object;)Landroid/net/Uri;
      //   257: astore #4
      //   259: ldc 'rn_snapshot'
      //   261: aconst_null
      //   262: iconst_2
      //   263: aconst_null
      //   264: invokestatic a : (Ljava/lang/String;Landroid/net/Uri;ILjava/lang/Object;)Landroid/net/Uri;
      //   267: astore #6
      //   269: new com/bytedance/ies/bullet/kit/rn/internal/c$u
      //   272: dup
      //   273: aload #6
      //   275: aload_2
      //   276: aload_1
      //   277: iconst_1
      //   278: aload_1
      //   279: invokespecial <init> : (Ljava/lang/Object;Lcom/bytedance/ies/bullet/b/c/a;Lcom/bytedance/ies/bullet/kit/rn/internal/c;ZLcom/bytedance/ies/bullet/kit/rn/internal/c;)V
      //   282: astore #5
      //   284: new com/bytedance/ies/bullet/kit/rn/internal/c$v
      //   287: dup
      //   288: aload #6
      //   290: aload_2
      //   291: aload_1
      //   292: iconst_1
      //   293: aload_1
      //   294: invokespecial <init> : (Ljava/lang/Object;Lcom/bytedance/ies/bullet/b/c/a;Lcom/bytedance/ies/bullet/kit/rn/internal/c;ZLcom/bytedance/ies/bullet/kit/rn/internal/c;)V
      //   297: checkcast d/f/a/b
      //   300: astore_1
      //   301: aload_2
      //   302: aload #4
      //   304: aload #5
      //   306: checkcast d/f/a/b
      //   309: aload_1
      //   310: invokeinterface b : (Landroid/net/Uri;Ld/f/a/b;Ld/f/a/b;)V
      //   315: return
    }
    
    public final void a(c param1c, Exception param1Exception) {
      l.b(param1c, "builder");
      l.b(param1Exception, "e");
      a a = this.a.y();
      if (a != null) {
        com.bytedance.ies.bullet.b.h.d d = a.b();
        if (d != null) {
          boolean bool;
          if (param1Exception != null) {
            bool = true;
          } else {
            bool = false;
          } 
          if (!bool)
            d = null; 
          if (d != null) {
            String str4 = ((com.bytedance.ies.bullet.b.h.a)a).e.getAuthority();
            String str2 = "none";
            String str1 = str4;
            if (str4 == null)
              str1 = "none"; 
            str4 = ((com.bytedance.ies.bullet.b.h.a)a).e.getLastPathSegment();
            if (str4 != null)
              str2 = str4; 
            StringBuilder stringBuilder = new StringBuilder("hybrid_rn_exception|");
            stringBuilder.append(str1);
            stringBuilder.append('|');
            stringBuilder.append(str2);
            stringBuilder.append("|bullet|");
            stringBuilder.append("native");
            String str3 = stringBuilder.toString();
            LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<Object, Object>();
            linkedHashMap.put("channel", str1);
            linkedHashMap.put("module_name", str2);
            linkedHashMap.put("exception_type", "native");
            if (param1Exception != null) {
              d.a(param1Exception, str3, linkedHashMap);
            } else {
              throw new u("null cannot be cast to non-null type kotlin.Exception /* = java.lang.Exception */");
            } 
          } 
        } 
        String str = param1Exception.getMessage();
        l.b("on_fail", "reason");
        ((com.bytedance.ies.bullet.b.h.a)a).b = "on_fail";
        ((com.bytedance.ies.bullet.b.h.a)a).c = str;
      } 
      this.d.invoke(param1Exception);
    }
  }
  
  static final class d implements TimingEventListener {
    d(j param1j) {}
    
    public final void onTimingEvent(String param1String, long param1Long) {
      a a = this.a.y();
      if (a != null) {
        l.a(param1String, "eventName");
        l.b(param1String, "event");
        a.n.put(param1String, Long.valueOf(param1Long));
        a.o = param1String;
        com.bytedance.ies.bullet.b.h.f f = (com.bytedance.ies.bullet.b.h.f)a.d();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("trigger", param1String);
        com.bytedance.ies.bullet.b.h.a.a((com.bytedance.ies.bullet.b.h.a)a, "hybrid_app_monitor_rn_timeline_event", f, jSONObject, i.a(param1String, new JSONObject(a.n), (l)a.m.getValue()), null, 16, null);
      } 
    }
  }
  
  static final class e extends m implements d.f.a.b<g, x> {
    e(j param1j) {
      super(1);
    }
  }
  
  static final class f extends m implements d.f.a.b<i, x> {
    f(j param1j) {
      super(1);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\bytedance\ies\bullet\kit\rn\j.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */