package com.airbnb.android.react.lottie;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import java.util.Collections;
import java.util.List;

public final class a implements ReactPackage {
  public final List<NativeModule> createNativeModules(ReactApplicationContext paramReactApplicationContext) {
    return Collections.emptyList();
  }
  
  public final List<ViewManager> createViewManagers(ReactApplicationContext paramReactApplicationContext) {
    return (List)Collections.singletonList(new LottieAnimationViewManager());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\airbnb\android\react\lottie\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */