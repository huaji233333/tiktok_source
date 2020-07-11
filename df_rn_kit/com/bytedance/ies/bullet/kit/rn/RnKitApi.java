package com.bytedance.ies.bullet.kit.rn;

import com.bytedance.ies.bullet.b.e.aa;
import com.bytedance.ies.bullet.b.e.h;
import com.bytedance.ies.bullet.b.e.i;
import com.bytedance.ies.bullet.b.e.l;
import com.bytedance.ies.bullet.b.e.m;
import com.bytedance.ies.bullet.b.e.s;
import com.bytedance.ies.bullet.b.e.w;
import com.bytedance.ies.bullet.kit.rn.a.b;
import com.bytedance.ies.bullet.kit.rn.a.c;
import com.facebook.react.bridge.INativeLibraryLoader;
import com.facebook.react.bridge.OnRNLoadExceptionListener;
import com.facebook.react.bridge.ReactBridge;
import d.f.b.l;
import java.util.List;
import org.json.JSONObject;

public final class RnKitApi implements IRnKitApi<j> {
  public static final a Companion = new a(null);
  
  public static final String SCHEME_RN = "react-native";
  
  private com.bytedance.ies.bullet.b.g.a.b contextProviderFactory;
  
  private h globalSettingsProvider;
  
  private boolean hasInitialized;
  
  private final Class<j> instanceType = j.class;
  
  public static IRnKitApi createIRnKitApibyMonsterPlugin() {
    Object object = com.ss.android.ugc.b.a(IRnKitApi.class);
    return (object != null) ? (IRnKitApi)object : new RnKitApi();
  }
  
  public final h<h> convertToGlobalSettingsProvider(Object paramObject) {
    l.b(paramObject, "delegate");
    return (paramObject instanceof b) ? new b(paramObject) : null;
  }
  
  public final l<i, g> convertToPackageProviderFactory(Object paramObject) {
    l.b(paramObject, "delegate");
    return (paramObject instanceof c) ? new c(paramObject) : null;
  }
  
  public final void ensureKitInitialized() {
    if (!this.hasInitialized) {
      e e;
      h h1 = this.globalSettingsProvider;
      h h2 = null;
      if (h1 != null) {
        d d = h1.a();
      } else {
        h1 = null;
      } 
      f f = new f((d)h1);
      com.bytedance.ies.bullet.b.g.a.b b1 = this.contextProviderFactory;
      h1 = h2;
      if (b1 != null) {
        com.bytedance.ies.bullet.b.f.d d = (com.bytedance.ies.bullet.b.f.d)b1.c(com.bytedance.ies.bullet.b.f.d.class);
        h1 = h2;
        if (d != null)
          e = new e(d); 
      } 
      if (e == null) {
        ReactBridge.staticInit(f);
      } else {
        ReactBridge.staticInit(f, e);
      } 
      ReactBridge.setJSExceptionListener(d.a);
      this.hasInitialized = true;
    } 
  }
  
  public final Class<j> getInstanceType() {
    return this.instanceType;
  }
  
  public final String getKitSDKVersion() {
    return "0.55.4-LYNX-1.3.0.39";
  }
  
  public final com.bytedance.ies.bullet.b.e.a getKitType() {
    return com.bytedance.ies.bullet.b.e.a.RN;
  }
  
  public final void onApiMounted(j paramj) {
    l.b(paramj, "kitInstanceApi");
  }
  
  public final void onInitialized(h paramh, com.bytedance.ies.bullet.b.g.a.b paramb) {
    l.b(paramb, "contextProviderFactory");
    this.globalSettingsProvider = paramh;
    this.contextProviderFactory = paramb;
  }
  
  public final j provideInstanceApi(aa paramaa, List<String> paramList, com.bytedance.ies.bullet.b.d paramd, com.bytedance.ies.bullet.b.g.a.b paramb) {
    l.b(paramaa, "sessionInfo");
    l.b(paramList, "packageNames");
    l.b(paramd, "kitPackageRegistryBundle");
    l.b(paramb, "providerFactory");
    ensureKitInitialized();
    return new j(this, paramaa, paramList, paramd, paramb);
  }
  
  public final com.bytedance.ies.bullet.b.g.c.d<w> provideProcessor() {
    return new g(this);
  }
  
  public final boolean useNewInstance() {
    return true;
  }
  
  public static final class a {
    private a() {}
  }
  
  public static final class b implements h<h> {
    b(Object param1Object) {}
  }
  
  public static final class c implements l<i, g> {
    c(Object param1Object) {}
  }
  
  static final class d implements ReactBridge.JSExceptionListener {
    public static final d a = new d();
    
    public final void upLoad(JSONObject param1JSONObject) {}
  }
  
  public static final class e implements INativeLibraryLoader {
    e(com.bytedance.ies.bullet.b.f.d param1d) {}
    
    public final void loadLibrary(String param1String) {}
  }
  
  public static final class f implements OnRNLoadExceptionListener {
    f(d param1d) {}
    
    public final void onLoadError(String param1String) {}
  }
  
  public static final class g implements com.bytedance.ies.bullet.b.g.c.d<w> {
    g(RnKitApi param1RnKitApi) {}
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\bytedance\ies\bullet\kit\rn\RnKitApi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */