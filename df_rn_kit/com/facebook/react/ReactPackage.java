package com.facebook.react;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import java.util.List;

public interface ReactPackage {
  List<NativeModule> createNativeModules(ReactApplicationContext paramReactApplicationContext);
  
  List<ViewManager> createViewManagers(ReactApplicationContext paramReactApplicationContext);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\ReactPackage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */