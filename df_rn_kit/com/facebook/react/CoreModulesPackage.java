package com.facebook.react;

import com.facebook.m.a;
import com.facebook.react.bridge.ModuleSpec;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.ReactMarkerConstants;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.modules.core.ExceptionsManagerModule;
import com.facebook.react.modules.core.HeadlessJsTaskSupportModule;
import com.facebook.react.modules.core.Timing;
import com.facebook.react.modules.debug.SourceCodeModule;
import com.facebook.react.modules.deviceinfo.DeviceInfoModule;
import com.facebook.react.modules.systeminfo.AndroidInfoModule;
import com.facebook.react.uimanager.UIImplementationProvider;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewManager;
import java.util.Arrays;
import java.util.List;
import javax.a.a;

class CoreModulesPackage extends LazyReactPackage implements ReactPackageLogger {
  public final DefaultHardwareBackBtnHandler mHardwareBackBtnHandler;
  
  private final boolean mLazyViewManagersEnabled;
  
  private final int mMinTimeLeftInFrameForNonBatchedOperationMs;
  
  public final ReactInstanceManager mReactInstanceManager;
  
  private final UIImplementationProvider mUIImplementationProvider;
  
  CoreModulesPackage(ReactInstanceManager paramReactInstanceManager, DefaultHardwareBackBtnHandler paramDefaultHardwareBackBtnHandler, UIImplementationProvider paramUIImplementationProvider, boolean paramBoolean, int paramInt) {
    this.mReactInstanceManager = paramReactInstanceManager;
    this.mHardwareBackBtnHandler = paramDefaultHardwareBackBtnHandler;
    this.mUIImplementationProvider = paramUIImplementationProvider;
    this.mLazyViewManagersEnabled = paramBoolean;
    this.mMinTimeLeftInFrameForNonBatchedOperationMs = paramInt;
  }
  
  public UIManagerModule createUIManager(ReactApplicationContext paramReactApplicationContext) {
    ReactMarker.logMarker(ReactMarkerConstants.CREATE_UI_MANAGER_MODULE_START);
    a.a(0L, "createUIManagerModule");
    try {
      if (this.mLazyViewManagersEnabled) {
        uIManagerModule = new UIManagerModule(paramReactApplicationContext, new UIManagerModule.ViewManagerResolver() {
              public ViewManager getViewManager(String param1String) {
                return CoreModulesPackage.this.mReactInstanceManager.createViewManager(param1String);
              }
              
              public List<String> getViewManagerNames() {
                return CoreModulesPackage.this.mReactInstanceManager.getViewManagerNames();
              }
            },  this.mUIImplementationProvider, this.mMinTimeLeftInFrameForNonBatchedOperationMs);
        return uIManagerModule;
      } 
      UIManagerModule uIManagerModule = new UIManagerModule((ReactApplicationContext)uIManagerModule, this.mReactInstanceManager.getOrCreateViewManagers((ReactApplicationContext)uIManagerModule), this.mUIImplementationProvider, this.mMinTimeLeftInFrameForNonBatchedOperationMs);
      return uIManagerModule;
    } finally {
      a.a(0L);
      ReactMarker.logMarker(ReactMarkerConstants.CREATE_UI_MANAGER_MODULE_END);
    } 
  }
  
  public void endProcessPackage() {
    ReactMarker.logMarker(ReactMarkerConstants.PROCESS_CORE_REACT_PACKAGE_END);
  }
  
  public List<ModuleSpec> getNativeModules(final ReactApplicationContext reactContext) {
    return Arrays.asList(new ModuleSpec[] { ModuleSpec.nativeModuleSpec(AndroidInfoModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new AndroidInfoModule(reactContext);
              }
            }), ModuleSpec.nativeModuleSpec(DeviceEventManagerModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new DeviceEventManagerModule(reactContext, CoreModulesPackage.this.mHardwareBackBtnHandler);
              }
            }), ModuleSpec.nativeModuleSpec(ExceptionsManagerModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new ExceptionsManagerModule(CoreModulesPackage.this.mReactInstanceManager.getDevSupportManager());
              }
            }), ModuleSpec.nativeModuleSpec(HeadlessJsTaskSupportModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new HeadlessJsTaskSupportModule(reactContext);
              }
            }), ModuleSpec.nativeModuleSpec(SourceCodeModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new SourceCodeModule((ReactContext)reactContext);
              }
            }), ModuleSpec.nativeModuleSpec(Timing.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new Timing(reactContext, CoreModulesPackage.this.mReactInstanceManager.getDevSupportManager());
              }
            }), ModuleSpec.nativeModuleSpec(UIManagerModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)CoreModulesPackage.this.createUIManager(reactContext);
              }
            }), ModuleSpec.nativeModuleSpec(DeviceInfoModule.class, new a<NativeModule>() {
              public NativeModule get() {
                return (NativeModule)new DeviceInfoModule(reactContext);
              }
            }) });
  }
  
  public ReactModuleInfoProvider getReactModuleInfoProvider() {
    return LazyReactPackage.getReactModuleInfoProviderViaReflection(this);
  }
  
  public void startProcessPackage() {
    ReactMarker.logMarker(ReactMarkerConstants.PROCESS_CORE_REACT_PACKAGE_START);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\CoreModulesPackage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */