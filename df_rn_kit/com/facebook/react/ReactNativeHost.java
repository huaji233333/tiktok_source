package com.facebook.react;

import android.app.Application;
import com.facebook.i.a.a;
import com.facebook.react.bridge.JSIModulesProvider;
import com.facebook.react.bridge.JavaScriptExecutorFactory;
import com.facebook.react.bridge.RNDegradeExceptionHandler;
import com.facebook.react.bridge.RNJavaScriptRuntime;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.ReactMarkerConstants;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.devsupport.RedBoxHandler;
import com.facebook.react.uimanager.UIImplementationProvider;
import java.util.Iterator;
import java.util.List;

public abstract class ReactNativeHost {
  private final Application mApplication;
  
  private ReactInstanceManager mReactInstanceManager;
  
  public ReactNativeHost(Application paramApplication) {
    this.mApplication = paramApplication;
  }
  
  public void clear() {
    ReactInstanceManager reactInstanceManager = this.mReactInstanceManager;
    if (reactInstanceManager != null) {
      reactInstanceManager.destroy();
      this.mReactInstanceManager = null;
    } 
  }
  
  protected ReactInstanceManager createReactInstanceManager() {
    ReactMarker.logMarker(ReactMarkerConstants.BUILD_REACT_INSTANCE_MANAGER_START);
    ReactInstanceManagerBuilder reactInstanceManagerBuilder = ReactInstanceManager.builder().setApplication(this.mApplication).setJSMainModulePath(getJSMainModuleName()).setUseDeveloperSupport(getUseDeveloperSupport()).setRedBoxHandler(getRedBoxHandler()).setJavaScriptExecutorFactory(getJavaScriptExecutorFactory()).setUIImplementationProvider(getUIImplementationProvider()).setJSIModulesProvider(getJSIModulesProvider()).setInitialLifecycleState(LifecycleState.BEFORE_CREATE).setDegradeExceptionHandler(getDegradeExceptionHandler());
    Iterator<ReactPackage> iterator = getPackages().iterator();
    while (iterator.hasNext())
      reactInstanceManagerBuilder.addPackage(iterator.next()); 
    String str = getJSBundleFile();
    if (str != null) {
      reactInstanceManagerBuilder.setJSBundleFile(str, getSplitCommonPakageType());
    } else {
      reactInstanceManagerBuilder.setBundleAssetName((String)a.b(getBundleAssetName()), getSplitCommonPakageType());
    } 
    ReactInstanceManager reactInstanceManager = reactInstanceManagerBuilder.build();
    ReactMarker.logMarker(ReactMarkerConstants.BUILD_REACT_INSTANCE_MANAGER_END);
    return reactInstanceManager;
  }
  
  protected final Application getApplication() {
    return this.mApplication;
  }
  
  protected String getBundleAssetName() {
    return "index.android.bundle";
  }
  
  protected RNDegradeExceptionHandler getDegradeExceptionHandler() {
    return null;
  }
  
  protected String getJSBundleFile() {
    return null;
  }
  
  protected JSIModulesProvider getJSIModulesProvider() {
    return null;
  }
  
  protected String getJSMainModuleName() {
    return "index.android";
  }
  
  protected JavaScriptExecutorFactory getJavaScriptExecutorFactory() {
    return null;
  }
  
  protected abstract List<ReactPackage> getPackages();
  
  public ReactInstanceManager getReactInstanceManager() {
    if (this.mReactInstanceManager == null) {
      ReactMarker.logMarker(ReactMarkerConstants.GET_REACT_INSTANCE_MANAGER_START);
      this.mReactInstanceManager = createReactInstanceManager();
      ReactMarker.logMarker(ReactMarkerConstants.GET_REACT_INSTANCE_MANAGER_END);
    } 
    return this.mReactInstanceManager;
  }
  
  protected RedBoxHandler getRedBoxHandler() {
    return null;
  }
  
  protected RNJavaScriptRuntime.SplitCommonType getSplitCommonPakageType() {
    return RNJavaScriptRuntime.SplitCommonType.NONE;
  }
  
  protected UIImplementationProvider getUIImplementationProvider() {
    return new UIImplementationProvider();
  }
  
  public abstract boolean getUseDeveloperSupport();
  
  public boolean hasInstance() {
    return (this.mReactInstanceManager != null);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\ReactNativeHost.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */