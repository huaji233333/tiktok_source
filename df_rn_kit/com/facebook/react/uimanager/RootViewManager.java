package com.facebook.react.uimanager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.react.uimanager.common.SizeMonitoringFrameLayout;

public class RootViewManager extends ViewGroupManager<ViewGroup> {
  protected ViewGroup createViewInstance(ThemedReactContext paramThemedReactContext) {
    return (ViewGroup)new SizeMonitoringFrameLayout((Context)paramThemedReactContext);
  }
  
  public String getName() {
    return "RootView";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\RootViewManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */