package com.facebook.react;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import java.util.List;

public interface ViewManagerOnDemandReactPackage {
  ViewManager createViewManager(ReactApplicationContext paramReactApplicationContext, String paramString, boolean paramBoolean);
  
  List<String> getViewManagerNames(ReactApplicationContext paramReactApplicationContext, boolean paramBoolean);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\ViewManagerOnDemandReactPackage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */