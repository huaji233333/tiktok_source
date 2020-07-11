package com.facebook.react.bridge;

import com.facebook.i.a.a;
import com.facebook.m.a;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NativeModuleRegistry {
  private final ArrayList<ModuleHolder> mBatchCompleteListenerModules;
  
  private final List<Class<? extends NativeModule>> mCoreModules;
  
  private final Map<Class<? extends NativeModule>, ModuleHolder> mModules;
  
  private final ReactApplicationContext mReactApplicationContext;
  
  public NativeModuleRegistry(ReactApplicationContext paramReactApplicationContext, Map<Class<? extends NativeModule>, ModuleHolder> paramMap, List<Class<? extends NativeModule>> paramList, ArrayList<ModuleHolder> paramArrayList) {
    this.mReactApplicationContext = paramReactApplicationContext;
    this.mModules = paramMap;
    this.mBatchCompleteListenerModules = paramArrayList;
    this.mCoreModules = paramList;
  }
  
  private ArrayList<ModuleHolder> getBatchCompleteListenerModules() {
    return this.mBatchCompleteListenerModules;
  }
  
  private Map<Class<? extends NativeModule>, ModuleHolder> getModuleMap() {
    return this.mModules;
  }
  
  private ReactApplicationContext getReactApplicationContext() {
    return this.mReactApplicationContext;
  }
  
  public List<NativeModule> getAllModules() {
    ArrayList<NativeModule> arrayList = new ArrayList();
    Iterator<ModuleHolder> iterator = this.mModules.values().iterator();
    while (iterator.hasNext())
      arrayList.add(((ModuleHolder)iterator.next()).getModule()); 
    return arrayList;
  }
  
  Collection<String> getCoreModules() {
    ArrayList<String> arrayList = new ArrayList();
    for (Class<? extends NativeModule> clazz : this.mCoreModules) {
      if (this.mModules.get(clazz) != null)
        arrayList.add(((ModuleHolder)this.mModules.get(clazz)).getName()); 
    } 
    return arrayList;
  }
  
  Collection<ModuleHolder> getCxxModules() {
    ArrayList<ModuleHolder> arrayList = new ArrayList();
    for (Map.Entry<Class<? extends NativeModule>, ModuleHolder> entry : this.mModules.entrySet()) {
      if (CxxModuleWrapperBase.class.isAssignableFrom((Class)entry.getKey()))
        arrayList.add(entry.getValue()); 
    } 
    return arrayList;
  }
  
  Collection<JavaModuleWrapper> getJavaModules(JSInstance paramJSInstance) {
    ArrayList<JavaModuleWrapper> arrayList = new ArrayList();
    for (Map.Entry<Class<? extends NativeModule>, ModuleHolder> entry : this.mModules.entrySet()) {
      Class<?> clazz = (Class)entry.getKey();
      if (!CxxModuleWrapperBase.class.isAssignableFrom(clazz))
        arrayList.add(new JavaModuleWrapper(paramJSInstance, (Class)clazz, (ModuleHolder)entry.getValue())); 
    } 
    return arrayList;
  }
  
  public <T extends NativeModule> T getModule(Class<T> paramClass) {
    return (T)((ModuleHolder)a.b(this.mModules.get(paramClass))).getModule();
  }
  
  public <T extends NativeModule> boolean hasModule(Class<T> paramClass) {
    return this.mModules.containsKey(paramClass);
  }
  
  void notifyJSInstanceDestroy() {
    this.mReactApplicationContext.assertOnNativeModulesQueueThread();
    a.a(0L, "NativeModuleRegistry_notifyJSInstanceDestroy");
    try {
      Iterator<ModuleHolder> iterator = this.mModules.values().iterator();
      while (iterator.hasNext())
        ((ModuleHolder)iterator.next()).destroy(); 
      return;
    } finally {
      a.a(0L);
    } 
  }
  
  void notifyJSInstanceInitialized() {
    this.mReactApplicationContext.assertOnNativeModulesQueueThread("From version React Native v0.44, native modules are explicitly not initialized on the UI thread. See https://github.com/facebook/react-native/wiki/Breaking-Changes#d4611211-reactnativeandroidbreaking-move-nativemodule-initialization-off-ui-thread---aaachiuuu  for more details.");
    ReactMarker.logMarker(ReactMarkerConstants.NATIVE_MODULE_INITIALIZE_START);
    a.a(0L, "NativeModuleRegistry_notifyJSInstanceInitialized");
    try {
      Iterator<ModuleHolder> iterator = this.mModules.values().iterator();
      while (iterator.hasNext())
        ((ModuleHolder)iterator.next()).markInitializable(); 
      return;
    } finally {
      a.a(0L);
      ReactMarker.logMarker(ReactMarkerConstants.NATIVE_MODULE_INITIALIZE_END);
    } 
  }
  
  public void onBatchComplete() {
    for (ModuleHolder moduleHolder : this.mBatchCompleteListenerModules) {
      if (moduleHolder.hasInstance())
        ((OnBatchCompleteListener)moduleHolder.getModule()).onBatchComplete(); 
    } 
  }
  
  void registerModules(NativeModuleRegistry paramNativeModuleRegistry) {
    a.a(this.mReactApplicationContext.equals(paramNativeModuleRegistry.getReactApplicationContext()), "Extending native modules with non-matching application contexts.");
    Map<Class<? extends NativeModule>, ModuleHolder> map = paramNativeModuleRegistry.getModuleMap();
    ArrayList<ModuleHolder> arrayList = paramNativeModuleRegistry.getBatchCompleteListenerModules();
    for (Map.Entry<Class<? extends NativeModule>, ModuleHolder> entry : map.entrySet()) {
      Class<? extends NativeModule> clazz = (Class)entry.getKey();
      if (!this.mModules.containsKey(clazz)) {
        ModuleHolder moduleHolder = (ModuleHolder)entry.getValue();
        if (arrayList.contains(moduleHolder))
          this.mBatchCompleteListenerModules.add(moduleHolder); 
        this.mModules.put(clazz, moduleHolder);
      } 
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\NativeModuleRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */