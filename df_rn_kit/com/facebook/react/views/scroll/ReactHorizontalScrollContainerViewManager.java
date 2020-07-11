package com.facebook.react.views.scroll;

import android.content.Context;
import android.view.View;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;

@ReactModule(name = "AndroidHorizontalScrollContentView")
public class ReactHorizontalScrollContainerViewManager extends ViewGroupManager<ReactHorizontalScrollContainerView> {
  public ReactHorizontalScrollContainerView createViewInstance(ThemedReactContext paramThemedReactContext) {
    return new ReactHorizontalScrollContainerView((Context)paramThemedReactContext);
  }
  
  public String getName() {
    return "AndroidHorizontalScrollContentView";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\views\scroll\ReactHorizontalScrollContainerViewManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */