package com.facebook.react;

import com.facebook.react.bridge.ModuleSpec;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.devsupport.JSCHeapCapture;
import com.facebook.react.devsupport.JSCSamplingProfiler;
import com.facebook.react.devsupport.JSDevSupport;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import java.util.ArrayList;
import java.util.List;
import javax.a.a;

class DebugCorePackage extends LazyReactPackage {
  public List<ModuleSpec> getNativeModules(final ReactApplicationContext reactContext) {
    ArrayList<ModuleSpec> arrayList = new ArrayList();
    arrayList.add(ModuleSpec.nativeModuleSpec(JSCHeapCapture.class, new a<NativeModule>() {
            public NativeModule get() {
              return (NativeModule)new JSCHeapCapture(reactContext);
            }
          }));
    arrayList.add(ModuleSpec.nativeModuleSpec(JSDevSupport.class, new a<NativeModule>() {
            public NativeModule get() {
              return (NativeModule)new JSDevSupport(reactContext);
            }
          }));
    arrayList.add(ModuleSpec.nativeModuleSpec(JSCSamplingProfiler.class, new a<NativeModule>() {
            public NativeModule get() {
              return (NativeModule)new JSCSamplingProfiler(reactContext);
            }
          }));
    return arrayList;
  }
  
  public ReactModuleInfoProvider getReactModuleInfoProvider() {
    return LazyReactPackage.getReactModuleInfoProviderViaReflection(this);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\DebugCorePackage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */