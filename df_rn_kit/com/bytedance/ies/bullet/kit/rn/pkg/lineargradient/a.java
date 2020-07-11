package com.bytedance.ies.bullet.kit.rn.pkg.lineargradient;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import d.a.m;
import java.util.ArrayList;
import java.util.List;

public final class a implements ReactPackage {
  public final List<NativeModule> createNativeModules(ReactApplicationContext paramReactApplicationContext) {
    return new ArrayList<NativeModule>();
  }
  
  public final List<ViewManager<?, ?>> createViewManagers(ReactApplicationContext paramReactApplicationContext) {
    return m.c((Object[])new ViewManager[] { (ViewManager)new LinearGradientManager() });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\bytedance\ies\bullet\kit\rn\pkg\lineargradient\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */