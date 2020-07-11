package com.facebook.react;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CompositeReactPackage extends ReactInstancePackage implements ViewManagerOnDemandReactPackage {
  private final List<ReactPackage> mChildReactPackages = new ArrayList<ReactPackage>();
  
  public CompositeReactPackage(ReactPackage paramReactPackage1, ReactPackage paramReactPackage2, ReactPackage... paramVarArgs) {
    this.mChildReactPackages.add(paramReactPackage1);
    this.mChildReactPackages.add(paramReactPackage2);
    Collections.addAll(this.mChildReactPackages, paramVarArgs);
  }
  
  public List<NativeModule> createNativeModules(ReactApplicationContext paramReactApplicationContext) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    Iterator<ReactPackage> iterator = this.mChildReactPackages.iterator();
    while (iterator.hasNext()) {
      for (NativeModule nativeModule : ((ReactPackage)iterator.next()).createNativeModules(paramReactApplicationContext))
        hashMap.put(nativeModule.getName(), nativeModule); 
    } 
    return new ArrayList<NativeModule>(hashMap.values());
  }
  
  public List<NativeModule> createNativeModules(ReactApplicationContext paramReactApplicationContext, ReactInstanceManager paramReactInstanceManager) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    for (ReactPackage reactPackage : this.mChildReactPackages) {
      List<NativeModule> list;
      if (reactPackage instanceof ReactInstancePackage) {
        list = ((ReactInstancePackage)reactPackage).createNativeModules(paramReactApplicationContext, paramReactInstanceManager);
      } else {
        list = list.createNativeModules(paramReactApplicationContext);
      } 
      for (NativeModule nativeModule : list)
        hashMap.put(nativeModule.getName(), nativeModule); 
    } 
    return new ArrayList<NativeModule>(hashMap.values());
  }
  
  public ViewManager createViewManager(ReactApplicationContext paramReactApplicationContext, String paramString, boolean paramBoolean) {
    List<ReactPackage> list = this.mChildReactPackages;
    ListIterator<ReactPackage> listIterator = list.listIterator(list.size());
    while (listIterator.hasPrevious()) {
      ReactPackage reactPackage = listIterator.previous();
      if (reactPackage instanceof ViewManagerOnDemandReactPackage) {
        ViewManager viewManager = ((ViewManagerOnDemandReactPackage)reactPackage).createViewManager(paramReactApplicationContext, paramString, paramBoolean);
        if (viewManager != null)
          return viewManager; 
      } 
    } 
    return null;
  }
  
  public List<ViewManager> createViewManagers(ReactApplicationContext paramReactApplicationContext) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    Iterator<ReactPackage> iterator = this.mChildReactPackages.iterator();
    while (iterator.hasNext()) {
      for (ViewManager viewManager : ((ReactPackage)iterator.next()).createViewManagers(paramReactApplicationContext))
        hashMap.put(viewManager.getName(), viewManager); 
    } 
    return new ArrayList<ViewManager>(hashMap.values());
  }
  
  public List<String> getViewManagerNames(ReactApplicationContext paramReactApplicationContext, boolean paramBoolean) {
    HashSet<String> hashSet = new HashSet();
    for (ReactPackage reactPackage : this.mChildReactPackages) {
      if (reactPackage instanceof ViewManagerOnDemandReactPackage) {
        List<String> list = ((ViewManagerOnDemandReactPackage)reactPackage).getViewManagerNames(paramReactApplicationContext, paramBoolean);
        if (list != null)
          hashSet.addAll(list); 
      } 
    } 
    return new ArrayList<String>(hashSet);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\CompositeReactPackage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */