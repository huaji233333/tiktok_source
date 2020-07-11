package com.facebook.react;

import com.facebook.react.bridge.ModuleSpec;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.ReactMarkerConstants;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.uimanager.ViewManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class LazyReactPackage implements ReactPackage {
  public static ReactModuleInfoProvider getReactModuleInfoProviderViaReflection(LazyReactPackage paramLazyReactPackage) {
    try {
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append(paramLazyReactPackage.getClass().getCanonicalName());
      stringBuilder2.append("$$ReactModuleInfoProvider");
      Class<?> clazz = Class.forName(stringBuilder2.toString());
      if (clazz != null)
        try {
          return (ReactModuleInfoProvider)clazz.newInstance();
        } catch (InstantiationException instantiationException) {
          StringBuilder stringBuilder = new StringBuilder("Unable to instantiate ReactModuleInfoProvider for ");
          stringBuilder.append(paramLazyReactPackage.getClass());
          throw new RuntimeException(stringBuilder.toString(), instantiationException);
        } catch (IllegalAccessException illegalAccessException) {
          StringBuilder stringBuilder = new StringBuilder("Unable to instantiate ReactModuleInfoProvider for ");
          stringBuilder.append(paramLazyReactPackage.getClass());
          throw new RuntimeException(stringBuilder.toString(), illegalAccessException);
        }  
      StringBuilder stringBuilder1 = new StringBuilder("ReactModuleInfoProvider class for ");
      stringBuilder1.append(paramLazyReactPackage.getClass().getCanonicalName());
      stringBuilder1.append(" not found.");
      throw new RuntimeException(stringBuilder1.toString());
    } catch (ClassNotFoundException classNotFoundException) {
      throw new RuntimeException(classNotFoundException);
    } 
  }
  
  public final List<NativeModule> createNativeModules(ReactApplicationContext paramReactApplicationContext) {
    ArrayList<NativeModule> arrayList = new ArrayList();
    for (ModuleSpec moduleSpec : getNativeModules(paramReactApplicationContext)) {
      moduleSpec.getType();
      ReactMarker.logMarker(ReactMarkerConstants.CREATE_MODULE_START, moduleSpec.getType().getSimpleName());
      try {
        NativeModule nativeModule = (NativeModule)moduleSpec.getProvider().get();
        ReactMarker.logMarker(ReactMarkerConstants.CREATE_MODULE_END);
      } finally {
        ReactMarker.logMarker(ReactMarkerConstants.CREATE_MODULE_END);
      } 
    } 
    return arrayList;
  }
  
  public List<ViewManager> createViewManagers(ReactApplicationContext paramReactApplicationContext) {
    List<ModuleSpec> list = getViewManagers(paramReactApplicationContext);
    if (list == null || list.isEmpty())
      return Collections.emptyList(); 
    ArrayList<ViewManager> arrayList = new ArrayList();
    Iterator<ModuleSpec> iterator = list.iterator();
    while (iterator.hasNext())
      arrayList.add((ViewManager)((ModuleSpec)iterator.next()).getProvider().get()); 
    return arrayList;
  }
  
  public abstract List<ModuleSpec> getNativeModules(ReactApplicationContext paramReactApplicationContext);
  
  public abstract ReactModuleInfoProvider getReactModuleInfoProvider();
  
  public List<ModuleSpec> getViewManagers(ReactApplicationContext paramReactApplicationContext) {
    return Collections.emptyList();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\LazyReactPackage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */