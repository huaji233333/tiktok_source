package com.swmansion.gesturehandler.react;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import java.util.Arrays;
import java.util.List;

public final class e implements ReactPackage {
  public final List<NativeModule> createNativeModules(ReactApplicationContext paramReactApplicationContext) {
    return Arrays.asList(new NativeModule[] { (NativeModule)new RNGestureHandlerModule(paramReactApplicationContext) });
  }
  
  public final List<ViewManager> createViewManagers(ReactApplicationContext paramReactApplicationContext) {
    return Arrays.asList(new ViewManager[] { (ViewManager)new RNGestureHandlerRootViewManager(), (ViewManager)new RNGestureHandlerButtonViewManager() });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\swmansion\gesturehandler\react\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */