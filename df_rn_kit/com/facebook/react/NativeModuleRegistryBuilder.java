package com.facebook.react;

import com.facebook.common.e.a;
import com.facebook.react.bridge.BaseJavaModule;
import com.facebook.react.bridge.ModuleHolder;
import com.facebook.react.bridge.ModuleSpec;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.NativeModuleRegistry;
import com.facebook.react.bridge.OnBatchCompleteListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.ReactMarkerConstants;
import com.facebook.react.module.model.ReactModuleInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NativeModuleRegistryBuilder {
  private final List<Class<? extends NativeModule>> mCoreModules = new ArrayList<Class<? extends NativeModule>>();
  
  private final boolean mLazyNativeModulesEnabled;
  
  private final Map<Class<? extends NativeModule>, ModuleHolder> mModules = new HashMap<Class<? extends NativeModule>, ModuleHolder>();
  
  private final ReactApplicationContext mReactApplicationContext;
  
  private final ReactInstanceManager mReactInstanceManager;
  
  private final Map<String, Class<? extends NativeModule>> namesToType = new HashMap<String, Class<? extends NativeModule>>();
  
  public NativeModuleRegistryBuilder(ReactApplicationContext paramReactApplicationContext, ReactInstanceManager paramReactInstanceManager, boolean paramBoolean) {
    this.mReactApplicationContext = paramReactApplicationContext;
    this.mReactInstanceManager = paramReactInstanceManager;
    this.mLazyNativeModulesEnabled = paramBoolean;
  }
  
  public void addCorePackages(List<LazyReactPackage> paramList, ReactApplicationContext paramReactApplicationContext) {
    Iterator<LazyReactPackage> iterator = paramList.iterator();
    while (iterator.hasNext()) {
      for (ModuleSpec moduleSpec : ((LazyReactPackage)iterator.next()).getNativeModules(paramReactApplicationContext))
        this.mCoreModules.add(moduleSpec.getType()); 
    } 
  }
  
  public void addNativeModule(NativeModule paramNativeModule) {
    StringBuilder stringBuilder;
    String str = paramNativeModule.getName();
    Class<?> clazz = paramNativeModule.getClass();
    if (this.namesToType.containsKey(str)) {
      Class clazz1 = this.namesToType.get(str);
      if (paramNativeModule.canOverrideExistingModule()) {
        this.mModules.remove(clazz1);
      } else {
        stringBuilder = new StringBuilder("Native module ");
        stringBuilder.append(clazz.getSimpleName());
        stringBuilder.append(" tried to override ");
        stringBuilder.append(clazz1.getSimpleName());
        stringBuilder.append(" for module name ");
        stringBuilder.append(str);
        stringBuilder.append(". If this was your intention, set canOverrideExistingModule=true");
        throw new IllegalStateException(stringBuilder.toString());
      } 
    } 
    this.namesToType.put(str, clazz);
    ModuleHolder moduleHolder = new ModuleHolder((NativeModule)stringBuilder);
    this.mModules.put(clazz, moduleHolder);
  }
  
  public NativeModuleRegistry build() {
    ArrayList arrayList = new ArrayList();
    for (Map.Entry<Class<? extends NativeModule>, ModuleHolder> entry : this.mModules.entrySet()) {
      if (OnBatchCompleteListener.class.isAssignableFrom((Class)entry.getKey()))
        arrayList.add(entry.getValue()); 
    } 
    return new NativeModuleRegistry(this.mReactApplicationContext, this.mModules, this.mCoreModules, arrayList);
  }
  
  public void processPackage(ReactPackage paramReactPackage) {
    StringBuilder stringBuilder1;
    List<NativeModule> list;
    if (this.mLazyNativeModulesEnabled) {
      if (paramReactPackage instanceof LazyReactPackage) {
        LazyReactPackage lazyReactPackage = (LazyReactPackage)paramReactPackage;
        List<ModuleSpec> list1 = lazyReactPackage.getNativeModules(this.mReactApplicationContext);
        Map map = lazyReactPackage.getReactModuleInfoProvider().getReactModuleInfos();
        for (ModuleSpec moduleSpec : list1) {
          StringBuilder stringBuilder;
          ModuleHolder moduleHolder;
          Class<?> clazz = moduleSpec.getType();
          ReactModuleInfo reactModuleInfo = (ReactModuleInfo)map.get(clazz);
          if (reactModuleInfo == null) {
            if (!BaseJavaModule.class.isAssignableFrom(clazz)) {
              ReactMarker.logMarker(ReactMarkerConstants.CREATE_MODULE_START, moduleSpec.getType().getName());
              try {
                NativeModule nativeModule = (NativeModule)moduleSpec.getProvider().get();
                ReactMarker.logMarker(ReactMarkerConstants.CREATE_MODULE_END);
              } finally {
                ReactMarker.logMarker(ReactMarkerConstants.CREATE_MODULE_END);
              } 
            } else {
              stringBuilder = new StringBuilder("Native Java module ");
              stringBuilder.append(clazz.getSimpleName());
              stringBuilder.append(" should be annotated with @ReactModule and added to a @ReactModuleList.");
              throw new IllegalStateException(stringBuilder.toString());
            } 
          } else {
            moduleHolder = new ModuleHolder(reactModuleInfo, stringBuilder.getProvider());
          } 
          String str = moduleHolder.getName();
          if (this.namesToType.containsKey(str)) {
            Class clazz1 = this.namesToType.get(str);
            if (moduleHolder.getCanOverrideExistingModule()) {
              this.mModules.remove(clazz1);
            } else {
              stringBuilder1 = new StringBuilder("Native module ");
              stringBuilder1.append(clazz.getSimpleName());
              stringBuilder1.append(" tried to override ");
              stringBuilder1.append(clazz1.getSimpleName());
              stringBuilder1.append(" for module name ");
              stringBuilder1.append(str);
              stringBuilder1.append(". If this was your intention, set canOverrideExistingModule=true");
              throw new IllegalStateException(stringBuilder1.toString());
            } 
          } 
          this.namesToType.put(str, clazz);
          this.mModules.put(clazz, stringBuilder1);
        } 
        return;
      } 
      throw new IllegalStateException("Lazy native modules requires all ReactPackage to inherit from LazyReactPackage");
    } 
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(stringBuilder1.getClass().getSimpleName());
    stringBuilder2.append(" is not a LazyReactPackage, falling back to old version.");
    a.a("ReactNative", stringBuilder2.toString());
    if (stringBuilder1 instanceof ReactInstancePackage) {
      list = ((ReactInstancePackage)stringBuilder1).createNativeModules(this.mReactApplicationContext, this.mReactInstanceManager);
    } else {
      list = list.createNativeModules(this.mReactApplicationContext);
    } 
    Iterator<NativeModule> iterator = list.iterator();
    while (iterator.hasNext())
      addNativeModule(iterator.next()); 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\NativeModuleRegistryBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */