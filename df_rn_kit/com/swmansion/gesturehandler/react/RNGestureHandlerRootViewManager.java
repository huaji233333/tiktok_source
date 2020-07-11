package com.swmansion.gesturehandler.react;

import android.content.Context;
import android.view.View;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import java.util.Map;

@ReactModule(name = "GestureHandlerRootView")
public class RNGestureHandlerRootViewManager extends ViewGroupManager<h> {
  protected h createViewInstance(ThemedReactContext paramThemedReactContext) {
    return new h((Context)paramThemedReactContext);
  }
  
  public Map getExportedCustomDirectEventTypeConstants() {
    return MapBuilder.of("onGestureHandlerEvent", MapBuilder.of("registrationName", "onGestureHandlerEvent"), "onGestureHandlerStateChange", MapBuilder.of("registrationName", "onGestureHandlerStateChange"));
  }
  
  public String getName() {
    return "GestureHandlerRootView";
  }
  
  public void onDropViewInstance(h paramh) {
    if (paramh.a != null)
      paramh.a.a(); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\swmansion\gesturehandler\react\RNGestureHandlerRootViewManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */