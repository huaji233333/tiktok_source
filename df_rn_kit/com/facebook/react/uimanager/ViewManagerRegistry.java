package com.facebook.react.uimanager;

import com.facebook.react.common.MapBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ViewManagerRegistry {
  private final UIManagerModule.ViewManagerResolver mViewManagerResolver;
  
  private final Map<String, ViewManager> mViewManagers;
  
  public ViewManagerRegistry(UIManagerModule.ViewManagerResolver paramViewManagerResolver) {
    this.mViewManagers = MapBuilder.newHashMap();
    this.mViewManagerResolver = paramViewManagerResolver;
  }
  
  public ViewManagerRegistry(List<ViewManager> paramList) {
    HashMap<String, ViewManager> hashMap = MapBuilder.newHashMap();
    for (ViewManager viewManager : paramList)
      hashMap.put(viewManager.getName(), viewManager); 
    this.mViewManagers = hashMap;
    this.mViewManagerResolver = null;
  }
  
  public ViewManagerRegistry(Map<String, ViewManager> paramMap) {
    if (paramMap == null)
      paramMap = MapBuilder.newHashMap(); 
    this.mViewManagers = paramMap;
    this.mViewManagerResolver = null;
  }
  
  public final ViewManager get(String paramString) {
    ViewManager viewManager = this.mViewManagers.get(paramString);
    if (viewManager != null)
      return viewManager; 
    UIManagerModule.ViewManagerResolver viewManagerResolver = this.mViewManagerResolver;
    if (viewManagerResolver != null) {
      ViewManager viewManager1 = viewManagerResolver.getViewManager(paramString);
      if (viewManager1 != null) {
        this.mViewManagers.put(paramString, viewManager1);
        return viewManager1;
      } 
    } 
    StringBuilder stringBuilder = new StringBuilder("No ViewManager defined for class ");
    stringBuilder.append(paramString);
    throw new IllegalViewOperationException(stringBuilder.toString());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\ViewManagerRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */