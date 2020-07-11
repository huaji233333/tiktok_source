package com.bytedance.ies.bullet.kit.rn;

import android.app.Application;
import android.text.TextUtils;
import com.bytedance.ies.bullet.b.e.a.h;
import com.bytedance.ies.bullet.kit.rn.core.b;
import com.bytedance.ies.bullet.kit.rn.core.c;
import com.bytedance.ies.bullet.kit.rn.core.d;
import com.bytedance.ies.bullet.kit.rn.core.e;
import com.bytedance.ies.bullet.kit.rn.core.f;
import com.bytedance.ies.bullet.kit.rn.internal.a;
import com.bytedance.ies.bullet.kit.rn.internal.b;
import com.bytedance.ies.bullet.kit.rn.internal.wrapper.NativeModuleWrapper;
import com.bytedance.ies.bullet.kit.rn.internal.wrapper.SimpleViewManagerWrapper;
import com.bytedance.ies.bullet.kit.rn.internal.wrapper.ViewGroupManagerWrapper;
import com.bytedance.ies.bullet.kit.rn.internal.wrapper.a;
import com.bytedance.ies.bullet.kit.rn.pkg.animation.b;
import com.bytedance.ies.bullet.kit.rn.pkg.fastimage.b;
import com.bytedance.ies.bullet.kit.rn.pkg.iconfont.a;
import com.bytedance.ies.bullet.kit.rn.pkg.lineargradient.a;
import com.bytedance.ies.bullet.kit.rn.pkg.viewshot.b;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactInstanceManagerBuilder;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.NativeModuleCallExceptionHandler;
import com.facebook.react.bridge.RNDegradeExceptionHandler;
import com.facebook.react.bridge.RNJavaScriptRuntime;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.ReactMarkerConstants;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.shell.MainPackageConfig;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.react.uimanager.BaseViewManager;
import com.facebook.react.uimanager.ViewManager;
import com.graphic.RNCanvas.RNCanvasPackage;
import d.a.m;
import d.f.b.g;
import d.f.b.l;
import d.f.b.m;
import d.u;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class a extends ReactNativeHost {
  public static final a l = new a(null);
  
  public RNDegradeExceptionHandler a;
  
  public boolean b;
  
  public final j c;
  
  public final com.bytedance.ies.bullet.b.g.a.b d;
  
  public h e;
  
  public final List<Object> f;
  
  public final List<c> g;
  
  public final List<e> h;
  
  public b i;
  
  public final File j;
  
  public final File k;
  
  private Map<String, ? extends Object> m;
  
  private String n;
  
  public a(j paramj, com.bytedance.ies.bullet.b.g.a.b paramb, h paramh, List<Object> paramList, List<c> paramList1, List<e> paramList2, b paramb1, File paramFile1, File paramFile2) {
    super((Application)paramb.c(Application.class));
    this.c = paramj;
    this.d = paramb;
    this.e = paramh;
    this.f = paramList;
    this.g = paramList1;
    this.h = paramList2;
    this.i = paramb1;
    this.j = paramFile1;
    this.k = paramFile2;
    this.n = "";
  }
  
  public final void a(RNDegradeExceptionHandler paramRNDegradeExceptionHandler) {
    l.b(paramRNDegradeExceptionHandler, "handler");
    this.a = paramRNDegradeExceptionHandler;
  }
  
  public final void a(String paramString) {
    l.b(paramString, "sessionId");
    this.n = paramString;
  }
  
  public final void a(Map<String, ? extends Object> paramMap) {
    l.b(paramMap, "extraParams");
    this.m = paramMap;
  }
  
  public final ReactInstanceManager createReactInstanceManager() {
    ReactMarker.logMarker(ReactMarkerConstants.BUILD_REACT_INSTANCE_MANAGER_START);
    ReactInstanceManagerBuilder reactInstanceManagerBuilder = ReactInstanceManager.builder().setApplication(getApplication()).setJSMainModulePath(getJSMainModuleName()).setUseDeveloperSupport(this.b).setRedBoxHandler(getRedBoxHandler()).setJavaScriptExecutorFactory(getJavaScriptExecutorFactory()).setUIImplementationProvider(getUIImplementationProvider()).setJSIModulesProvider(getJSIModulesProvider()).setNativeModuleCallExceptionHandler(new b(this.c, this.f)).setInitialLifecycleState(LifecycleState.RESUMED).setDegradeExceptionHandler(getDegradeExceptionHandler());
    Iterator<ReactPackage> iterator = getPackages().iterator();
    while (iterator.hasNext())
      reactInstanceManagerBuilder.addPackage(iterator.next()); 
    File file = this.k;
    if (file != null) {
      String str1 = b.a.a(file, "blobdata");
    } else {
      file = null;
    } 
    String str = getJSBundleFile();
    if (!TextUtils.isEmpty((CharSequence)file)) {
      reactInstanceManagerBuilder.setSplitCommonBundleFile((String)file, RNJavaScriptRuntime.SplitCommonType.SPLIT_SNAPSHOT);
    } else if (!TextUtils.isEmpty(str)) {
      reactInstanceManagerBuilder.setSplitCommonBundleFile(str, RNJavaScriptRuntime.SplitCommonType.SPLIT_COMMONJS);
    } else {
      reactInstanceManagerBuilder.setSplitCommonType(RNJavaScriptRuntime.SplitCommonType.SPLIT_SNAPSHOT);
    } 
    ReactInstanceManager reactInstanceManager = reactInstanceManagerBuilder.prebuild();
    ReactMarker.logMarker(ReactMarkerConstants.BUILD_REACT_INSTANCE_MANAGER_END);
    l.a(reactInstanceManager, "reactInstanceManager");
    return reactInstanceManager;
  }
  
  public final String getBundleAssetName() {
    return "index.android.jsbundle";
  }
  
  public final RNDegradeExceptionHandler getDegradeExceptionHandler() {
    return new c(this);
  }
  
  public final String getJSBundleFile() {
    File file = this.j;
    if (file != null) {
      b.a a1 = b.d;
      return b.a.a(file, null);
    } 
    return null;
  }
  
  public final String getJSMainModuleName() {
    return "index";
  }
  
  public final List<ReactPackage> getPackages() {
    MainPackageConfig.Builder builder = new MainPackageConfig.Builder();
    b b1 = this.i;
    if (b1 != null)
      builder.setFrescoConfig(b1.a); 
    MainReactPackage mainReactPackage = new MainReactPackage(builder.build());
    ArrayList<a> arrayList = new ArrayList();
    List<e> list = this.h;
    ArrayList arrayList1 = new ArrayList();
    Iterator<e> iterator = list.iterator();
    while (iterator.hasNext()) {
      List list1 = ((e)iterator.next()).a(this.c, this.d);
      ArrayList<e> arrayList2 = new ArrayList(m.a(list1, 10));
      Iterator<d> iterator1 = list1.iterator();
      while (iterator1.hasNext())
        arrayList2.add(new e(iterator1.next())); 
      m.a(arrayList1, arrayList2);
    } 
    arrayList.addAll(arrayList1);
    arrayList.add(new a());
    arrayList.add(new a());
    arrayList.add(new b());
    arrayList.add(new com.airbnb.android.react.lottie.a());
    arrayList.add(new com.brentvatne.react.a());
    arrayList.add(new b());
    arrayList.add(new b());
    arrayList.add(new com.swmansion.gesturehandler.react.e());
    arrayList.add(new RNCanvasPackage());
    arrayList.add(mainReactPackage);
    arrayList.add(new a(this.c, new d(mainReactPackage, this), this.g, this.d));
    return (List)arrayList;
  }
  
  public final boolean getUseDeveloperSupport() {
    return this.b;
  }
  
  public static final class a {
    private a() {}
  }
  
  static final class b implements NativeModuleCallExceptionHandler {
    private final WeakReference<j> a;
    
    private final List<Object> b;
    
    public b(j param1j, List<Object> param1List) {
      this.b = param1List;
      this.a = new WeakReference<j>(param1j);
    }
    
    public final void handleException(Exception param1Exception) {
      j j = this.a.get();
      if (j != null) {
        Iterator iterator = m.f(this.b).iterator();
        while (true) {
          if (iterator.hasNext()) {
            iterator.next();
            try {
              l.a(j, "instance");
              if (param1Exception == null)
                new RuntimeException(); 
            } catch (com.bytedance.ies.bullet.b.a.d d) {}
            continue;
          } 
          return;
        } 
      } 
    }
  }
  
  static final class c implements RNDegradeExceptionHandler {
    c(a param1a) {}
    
    public final void onDegrade(Exception param1Exception) {
      RNDegradeExceptionHandler rNDegradeExceptionHandler = this.a.a;
      if (rNDegradeExceptionHandler != null)
        rNDegradeExceptionHandler.onDegrade(param1Exception); 
    }
  }
  
  static final class d extends m implements d.f.a.a<h> {
    d(MainReactPackage param1MainReactPackage, a param1a) {
      super(0);
    }
  }
  
  public static final class e implements ReactPackage {
    e(d param1d) {}
    
    public final List<NativeModule> createNativeModules(ReactApplicationContext param1ReactApplicationContext) {
      l.b(param1ReactApplicationContext, "reactContext");
      List list = this.a.a((c)new a((ReactContext)param1ReactApplicationContext));
      ArrayList<NativeModule> arrayList = new ArrayList(m.a(list, 10));
      Iterator<b> iterator = list.iterator();
      while (iterator.hasNext())
        arrayList.add(NativeModuleWrapper.a.a(iterator.next())); 
      return arrayList;
    }
    
    public final List<ViewManager<?, ?>> createViewManagers(ReactApplicationContext param1ReactApplicationContext) {
      l.b(param1ReactApplicationContext, "reactContext");
      List list = this.a.b((c)new a((ReactContext)param1ReactApplicationContext));
      ArrayList<BaseViewManager> arrayList = new ArrayList(m.a(list, 10));
      for (e e1 : list) {
        BaseViewManager baseViewManager;
        if (e1 instanceof f) {
          baseViewManager = (BaseViewManager)ViewGroupManagerWrapper.a.a((f)e1);
        } else if (baseViewManager != null) {
          baseViewManager = (BaseViewManager)SimpleViewManagerWrapper.a.a((e)baseViewManager);
        } else {
          throw new u("null cannot be cast to non-null type com.bytedance.ies.bullet.kit.rn.core.BulletSimpleViewManager<android.view.View>");
        } 
        arrayList.add(baseViewManager);
      } 
      return (List)arrayList;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\bytedance\ies\bullet\kit\rn\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */