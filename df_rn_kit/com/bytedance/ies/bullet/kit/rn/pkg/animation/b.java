package com.bytedance.ies.bullet.kit.rn.pkg.animation;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import d.a.m;
import d.f.b.l;
import java.util.List;

public final class b implements ReactPackage {
  public final List<NativeModule> createNativeModules(ReactApplicationContext paramReactApplicationContext) {
    l.b(paramReactApplicationContext, "reactContext");
    return m.a();
  }
  
  public final List<ViewManager<?, ?>> createViewManagers(ReactApplicationContext paramReactApplicationContext) {
    l.b(paramReactApplicationContext, "reactContext");
    return m.a(new AnimatedReactImageManager());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\bytedance\ies\bullet\kit\rn\pkg\animation\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */