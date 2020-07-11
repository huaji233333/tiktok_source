package com.facebook.react;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import com.facebook.i.a.a;
import com.facebook.react.bridge.JSBundleLoader;
import com.facebook.react.bridge.JSCJavaScriptExecutorFactory;
import com.facebook.react.bridge.JSIModulesProvider;
import com.facebook.react.bridge.JavaScriptExecutorFactory;
import com.facebook.react.bridge.NativeModuleCallExceptionHandler;
import com.facebook.react.bridge.NotThreadSafeBridgeIdleDebugListener;
import com.facebook.react.bridge.RNDegradeExceptionHandler;
import com.facebook.react.bridge.RNJavaScriptRuntime;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.devsupport.RedBoxHandler;
import com.facebook.react.devsupport.interfaces.DevBundleDownloadListener;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.systeminfo.AndroidInfoHelpers;
import com.facebook.react.uimanager.UIImplementationProvider;
import java.util.ArrayList;
import java.util.List;

public class ReactInstanceManagerBuilder {
  private Application mApplication;
  
  private NotThreadSafeBridgeIdleDebugListener mBridgeIdleDebugListener;
  
  private Activity mCurrentActivity;
  
  private DefaultHardwareBackBtnHandler mDefaultHardwareBackBtnHandler;
  
  private RNDegradeExceptionHandler mDegradeExceptionHandler;
  
  private boolean mDelayViewManagerClassLoadsEnabled;
  
  private DevBundleDownloadListener mDevBundleDownloadListener;
  
  private LifecycleState mInitialLifecycleState;
  
  private String mJSBundleAssetUrl;
  
  private JSBundleLoader mJSBundleLoader;
  
  private JSIModulesProvider mJSIModulesProvider;
  
  private String mJSMainModulePath;
  
  private JavaScriptExecutorFactory mJavaScriptExecutorFactory;
  
  private boolean mLazyNativeModulesEnabled;
  
  private boolean mLazyViewManagersEnabled;
  
  private int mMinNumShakes = 1;
  
  private int mMinTimeLeftInFrameForNonBatchedOperationMs = -1;
  
  private NativeModuleCallExceptionHandler mNativeModuleCallExceptionHandler;
  
  private final List<ReactPackage> mPackages = new ArrayList<ReactPackage>();
  
  private RedBoxHandler mRedBoxHandler;
  
  private RNJavaScriptRuntime.SplitCommonType mSplitCommonType;
  
  private UIImplementationProvider mUIImplementationProvider;
  
  private boolean mUseDeveloperSupport;
  
  public ReactInstanceManagerBuilder addPackage(ReactPackage paramReactPackage) {
    this.mPackages.add(paramReactPackage);
    return this;
  }
  
  public ReactInstanceManagerBuilder addPackages(List<ReactPackage> paramList) {
    this.mPackages.addAll(paramList);
    return this;
  }
  
  public ReactInstanceManager build() {
    JSCJavaScriptExecutorFactory jSCJavaScriptExecutorFactory;
    a.a(this.mApplication, "Application property has not been set with this builder");
    boolean bool = this.mUseDeveloperSupport;
    boolean bool1 = true;
    if (bool || this.mJSBundleAssetUrl != null || this.mJSBundleLoader != null) {
      bool = true;
    } else {
      bool = false;
    } 
    a.a(bool, "JS Bundle File or Asset URL has to be provided when dev support is disabled");
    bool = bool1;
    if (this.mJSMainModulePath == null) {
      bool = bool1;
      if (this.mJSBundleAssetUrl == null)
        if (this.mJSBundleLoader != null) {
          bool = bool1;
        } else {
          bool = false;
        }  
    } 
    a.a(bool, "Either MainModulePath or JS Bundle File needs to be provided");
    if (this.mUIImplementationProvider == null)
      this.mUIImplementationProvider = new UIImplementationProvider(); 
    String str1 = this.mApplication.getPackageName();
    String str2 = AndroidInfoHelpers.getFriendlyDeviceName();
    Application application = this.mApplication;
    Activity activity = this.mCurrentActivity;
    DefaultHardwareBackBtnHandler defaultHardwareBackBtnHandler = this.mDefaultHardwareBackBtnHandler;
    JavaScriptExecutorFactory javaScriptExecutorFactory2 = this.mJavaScriptExecutorFactory;
    JavaScriptExecutorFactory javaScriptExecutorFactory1 = javaScriptExecutorFactory2;
    if (javaScriptExecutorFactory2 == null)
      jSCJavaScriptExecutorFactory = new JSCJavaScriptExecutorFactory(str1, str2, this.mSplitCommonType); 
    if (this.mJSBundleLoader == null) {
      String str = this.mJSBundleAssetUrl;
      if (str != null) {
        JSBundleLoader jSBundleLoader1 = JSBundleLoader.createAssetLoader((Context)this.mApplication, str, false, this.mSplitCommonType);
        return new ReactInstanceManager((Context)application, activity, defaultHardwareBackBtnHandler, (JavaScriptExecutorFactory)jSCJavaScriptExecutorFactory, jSBundleLoader1, this.mJSMainModulePath, this.mPackages, this.mUseDeveloperSupport, this.mBridgeIdleDebugListener, (LifecycleState)a.a(this.mInitialLifecycleState, "Initial lifecycle state was not set"), this.mUIImplementationProvider, this.mNativeModuleCallExceptionHandler, this.mRedBoxHandler, this.mLazyNativeModulesEnabled, this.mLazyViewManagersEnabled, this.mDelayViewManagerClassLoadsEnabled, this.mDevBundleDownloadListener, this.mMinNumShakes, this.mMinTimeLeftInFrameForNonBatchedOperationMs, this.mJSIModulesProvider, this.mDegradeExceptionHandler);
      } 
    } 
    JSBundleLoader jSBundleLoader = this.mJSBundleLoader;
    return new ReactInstanceManager((Context)application, activity, defaultHardwareBackBtnHandler, (JavaScriptExecutorFactory)jSCJavaScriptExecutorFactory, jSBundleLoader, this.mJSMainModulePath, this.mPackages, this.mUseDeveloperSupport, this.mBridgeIdleDebugListener, (LifecycleState)a.a(this.mInitialLifecycleState, "Initial lifecycle state was not set"), this.mUIImplementationProvider, this.mNativeModuleCallExceptionHandler, this.mRedBoxHandler, this.mLazyNativeModulesEnabled, this.mLazyViewManagersEnabled, this.mDelayViewManagerClassLoadsEnabled, this.mDevBundleDownloadListener, this.mMinNumShakes, this.mMinTimeLeftInFrameForNonBatchedOperationMs, this.mJSIModulesProvider, this.mDegradeExceptionHandler);
  }
  
  public ReactInstanceManager prebuild() {
    JSCJavaScriptExecutorFactory jSCJavaScriptExecutorFactory;
    a.a(this.mApplication, "Application property has not been set with this builder");
    if (this.mUIImplementationProvider == null)
      this.mUIImplementationProvider = new UIImplementationProvider(); 
    String str1 = this.mApplication.getPackageName();
    String str2 = AndroidInfoHelpers.getFriendlyDeviceName();
    Application application = this.mApplication;
    Activity activity = this.mCurrentActivity;
    DefaultHardwareBackBtnHandler defaultHardwareBackBtnHandler = this.mDefaultHardwareBackBtnHandler;
    JavaScriptExecutorFactory javaScriptExecutorFactory2 = this.mJavaScriptExecutorFactory;
    JavaScriptExecutorFactory javaScriptExecutorFactory1 = javaScriptExecutorFactory2;
    if (javaScriptExecutorFactory2 == null)
      jSCJavaScriptExecutorFactory = new JSCJavaScriptExecutorFactory(str1, str2, this.mSplitCommonType); 
    if (this.mJSBundleLoader == null) {
      String str = this.mJSBundleAssetUrl;
      if (str != null) {
        JSBundleLoader jSBundleLoader1 = JSBundleLoader.createAssetLoader((Context)this.mApplication, str, false, this.mSplitCommonType);
        return new ReactInstanceManager((Context)application, activity, defaultHardwareBackBtnHandler, (JavaScriptExecutorFactory)jSCJavaScriptExecutorFactory, jSBundleLoader1, this.mJSMainModulePath, this.mPackages, this.mUseDeveloperSupport, this.mBridgeIdleDebugListener, (LifecycleState)a.a(this.mInitialLifecycleState, "Initial lifecycle state was not set"), this.mUIImplementationProvider, this.mNativeModuleCallExceptionHandler, this.mRedBoxHandler, this.mLazyNativeModulesEnabled, this.mLazyViewManagersEnabled, this.mDelayViewManagerClassLoadsEnabled, this.mDevBundleDownloadListener, this.mMinNumShakes, this.mMinTimeLeftInFrameForNonBatchedOperationMs, this.mJSIModulesProvider, this.mDegradeExceptionHandler);
      } 
    } 
    JSBundleLoader jSBundleLoader = this.mJSBundleLoader;
    return new ReactInstanceManager((Context)application, activity, defaultHardwareBackBtnHandler, (JavaScriptExecutorFactory)jSCJavaScriptExecutorFactory, jSBundleLoader, this.mJSMainModulePath, this.mPackages, this.mUseDeveloperSupport, this.mBridgeIdleDebugListener, (LifecycleState)a.a(this.mInitialLifecycleState, "Initial lifecycle state was not set"), this.mUIImplementationProvider, this.mNativeModuleCallExceptionHandler, this.mRedBoxHandler, this.mLazyNativeModulesEnabled, this.mLazyViewManagersEnabled, this.mDelayViewManagerClassLoadsEnabled, this.mDevBundleDownloadListener, this.mMinNumShakes, this.mMinTimeLeftInFrameForNonBatchedOperationMs, this.mJSIModulesProvider, this.mDegradeExceptionHandler);
  }
  
  public ReactInstanceManagerBuilder setApplication(Application paramApplication) {
    this.mApplication = paramApplication;
    RNJavaScriptRuntime.setApplication(paramApplication);
    return this;
  }
  
  public ReactInstanceManagerBuilder setBridgeIdleDebugListener(NotThreadSafeBridgeIdleDebugListener paramNotThreadSafeBridgeIdleDebugListener) {
    this.mBridgeIdleDebugListener = paramNotThreadSafeBridgeIdleDebugListener;
    return this;
  }
  
  public ReactInstanceManagerBuilder setBundleAssetName(String paramString) {
    if (paramString == null) {
      paramString = null;
    } else {
      StringBuilder stringBuilder = new StringBuilder("assets://");
      stringBuilder.append(paramString);
      paramString = stringBuilder.toString();
    } 
    this.mJSBundleAssetUrl = paramString;
    this.mJSBundleLoader = null;
    return this;
  }
  
  public ReactInstanceManagerBuilder setBundleAssetName(String paramString, RNJavaScriptRuntime.SplitCommonType paramSplitCommonType) {
    if (paramString == null) {
      paramString = null;
    } else {
      StringBuilder stringBuilder = new StringBuilder("assets://");
      stringBuilder.append(paramString);
      paramString = stringBuilder.toString();
    } 
    this.mJSBundleAssetUrl = paramString;
    this.mJSBundleLoader = null;
    this.mSplitCommonType = paramSplitCommonType;
    return this;
  }
  
  public ReactInstanceManagerBuilder setCurrentActivity(Activity paramActivity) {
    this.mCurrentActivity = paramActivity;
    return this;
  }
  
  public ReactInstanceManagerBuilder setDefaultHardwareBackBtnHandler(DefaultHardwareBackBtnHandler paramDefaultHardwareBackBtnHandler) {
    this.mDefaultHardwareBackBtnHandler = paramDefaultHardwareBackBtnHandler;
    return this;
  }
  
  public ReactInstanceManagerBuilder setDegradeExceptionHandler(RNDegradeExceptionHandler paramRNDegradeExceptionHandler) {
    this.mDegradeExceptionHandler = paramRNDegradeExceptionHandler;
    return this;
  }
  
  public ReactInstanceManagerBuilder setDelayViewManagerClassLoadsEnabled(boolean paramBoolean) {
    this.mDelayViewManagerClassLoadsEnabled = paramBoolean;
    return this;
  }
  
  public ReactInstanceManagerBuilder setDevBundleDownloadListener(DevBundleDownloadListener paramDevBundleDownloadListener) {
    this.mDevBundleDownloadListener = paramDevBundleDownloadListener;
    return this;
  }
  
  public ReactInstanceManagerBuilder setInitialLifecycleState(LifecycleState paramLifecycleState) {
    this.mInitialLifecycleState = paramLifecycleState;
    return this;
  }
  
  public ReactInstanceManagerBuilder setJSBundleFile(String paramString) {
    if (paramString.startsWith("assets://")) {
      this.mJSBundleAssetUrl = paramString;
      this.mJSBundleLoader = null;
      return this;
    } 
    return setJSBundleLoader(JSBundleLoader.createFileLoader(paramString));
  }
  
  public ReactInstanceManagerBuilder setJSBundleFile(String paramString, RNJavaScriptRuntime.SplitCommonType paramSplitCommonType) {
    if (paramString.startsWith("assets://")) {
      this.mJSBundleAssetUrl = paramString;
      this.mJSBundleLoader = null;
      this.mSplitCommonType = paramSplitCommonType;
      return this;
    } 
    return setJSBundleLoader(JSBundleLoader.createFileLoader(paramString, paramSplitCommonType));
  }
  
  public ReactInstanceManagerBuilder setJSBundleLoader(JSBundleLoader paramJSBundleLoader) {
    this.mJSBundleLoader = paramJSBundleLoader;
    this.mJSBundleAssetUrl = null;
    return this;
  }
  
  public ReactInstanceManagerBuilder setJSIModulesProvider(JSIModulesProvider paramJSIModulesProvider) {
    this.mJSIModulesProvider = paramJSIModulesProvider;
    return this;
  }
  
  public ReactInstanceManagerBuilder setJSMainModulePath(String paramString) {
    this.mJSMainModulePath = paramString;
    return this;
  }
  
  public ReactInstanceManagerBuilder setJavaScriptExecutorFactory(JavaScriptExecutorFactory paramJavaScriptExecutorFactory) {
    this.mJavaScriptExecutorFactory = paramJavaScriptExecutorFactory;
    return this;
  }
  
  public ReactInstanceManagerBuilder setLazyNativeModulesEnabled(boolean paramBoolean) {
    this.mLazyNativeModulesEnabled = paramBoolean;
    return this;
  }
  
  public ReactInstanceManagerBuilder setLazyViewManagersEnabled(boolean paramBoolean) {
    this.mLazyViewManagersEnabled = paramBoolean;
    return this;
  }
  
  public ReactInstanceManagerBuilder setMinNumShakes(int paramInt) {
    this.mMinNumShakes = paramInt;
    return this;
  }
  
  public ReactInstanceManagerBuilder setMinTimeLeftInFrameForNonBatchedOperationMs(int paramInt) {
    this.mMinTimeLeftInFrameForNonBatchedOperationMs = paramInt;
    return this;
  }
  
  public ReactInstanceManagerBuilder setNativeModuleCallExceptionHandler(NativeModuleCallExceptionHandler paramNativeModuleCallExceptionHandler) {
    this.mNativeModuleCallExceptionHandler = paramNativeModuleCallExceptionHandler;
    return this;
  }
  
  public ReactInstanceManagerBuilder setRedBoxHandler(RedBoxHandler paramRedBoxHandler) {
    this.mRedBoxHandler = paramRedBoxHandler;
    return this;
  }
  
  public ReactInstanceManagerBuilder setSplitCommonBundleFile(String paramString, RNJavaScriptRuntime.SplitCommonType paramSplitCommonType) {
    if (paramSplitCommonType == RNJavaScriptRuntime.SplitCommonType.SPLIT_COMMONJS) {
      RNJavaScriptRuntime.setCommonJsBundle(paramString);
    } else if (paramSplitCommonType == RNJavaScriptRuntime.SplitCommonType.SPLIT_SNAPSHOT) {
      RNJavaScriptRuntime.setSnapSHotBundle(paramString);
    } 
    this.mSplitCommonType = paramSplitCommonType;
    return this;
  }
  
  public ReactInstanceManagerBuilder setSplitCommonType(RNJavaScriptRuntime.SplitCommonType paramSplitCommonType) {
    this.mSplitCommonType = paramSplitCommonType;
    return this;
  }
  
  public ReactInstanceManagerBuilder setUIImplementationProvider(UIImplementationProvider paramUIImplementationProvider) {
    this.mUIImplementationProvider = paramUIImplementationProvider;
    return this;
  }
  
  public ReactInstanceManagerBuilder setUseDeveloperSupport(boolean paramBoolean) {
    this.mUseDeveloperSupport = paramBoolean;
    return this;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\ReactInstanceManagerBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */