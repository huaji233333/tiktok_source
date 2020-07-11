package com.graphic.RNCanvas;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RNCanvasPackage implements ReactPackage {
  public List<NativeModule> createNativeModules(ReactApplicationContext paramReactApplicationContext) {
    ArrayList<CanvasAPI> arrayList = new ArrayList();
    arrayList.add(new CanvasAPI(paramReactApplicationContext));
    return (List)arrayList;
  }
  
  public List<ViewManager> createViewManagers(ReactApplicationContext paramReactApplicationContext) {
    return Arrays.asList(new ViewManager[] { (ViewManager)new CanvasViewManager() });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\graphic\RNCanvas\RNCanvasPackage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */