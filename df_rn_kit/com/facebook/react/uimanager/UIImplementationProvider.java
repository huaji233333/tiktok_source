package com.facebook.react.uimanager;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.events.EventDispatcher;
import java.util.List;

public class UIImplementationProvider {
  public UIImplementation createUIImplementation(ReactApplicationContext paramReactApplicationContext, UIManagerModule.ViewManagerResolver paramViewManagerResolver, EventDispatcher paramEventDispatcher, int paramInt) {
    return new UIImplementation(paramReactApplicationContext, paramViewManagerResolver, paramEventDispatcher, paramInt);
  }
  
  public UIImplementation createUIImplementation(ReactApplicationContext paramReactApplicationContext, List<ViewManager> paramList, EventDispatcher paramEventDispatcher, int paramInt) {
    return new UIImplementation(paramReactApplicationContext, paramList, paramEventDispatcher, paramInt);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\UIImplementationProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */