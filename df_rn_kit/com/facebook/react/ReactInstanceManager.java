package com.facebook.react;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.support.v4.view.u;
import android.view.View;
import com.facebook.common.e.a;
import com.facebook.i.a.a;
import com.facebook.m.a;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.CatalystInstanceImpl;
import com.facebook.react.bridge.JSBundleLoader;
import com.facebook.react.bridge.JSIModulesProvider;
import com.facebook.react.bridge.JavaJSExecutor;
import com.facebook.react.bridge.JavaScriptExecutor;
import com.facebook.react.bridge.JavaScriptExecutorFactory;
import com.facebook.react.bridge.MemoryPressureListener;
import com.facebook.react.bridge.NativeModuleCallExceptionHandler;
import com.facebook.react.bridge.NativeModuleRegistry;
import com.facebook.react.bridge.NotThreadSafeBridgeIdleDebugListener;
import com.facebook.react.bridge.ProxyJavaScriptExecutor;
import com.facebook.react.bridge.RNDegradeExceptionHandler;
import com.facebook.react.bridge.RNJavaScriptRuntime;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactBridge;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.ReactMarkerConstants;
import com.facebook.react.bridge.UIManager;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.queue.ReactQueueConfigurationSpec;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.devsupport.DevSupportManagerFactory;
import com.facebook.react.devsupport.ReactInstanceManagerDevHelper;
import com.facebook.react.devsupport.RedBoxHandler;
import com.facebook.react.devsupport.interfaces.DevBundleDownloadListener;
import com.facebook.react.devsupport.interfaces.DevSupportManager;
import com.facebook.react.devsupport.interfaces.PackagerStatusCallback;
import com.facebook.react.modules.appregistry.AppRegistry;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.modules.core.ReactChoreographer;
import com.facebook.react.modules.debug.interfaces.DeveloperSettings;
import com.facebook.react.modules.fabric.ReactFabric;
import com.facebook.react.uimanager.DisplayMetricsHolder;
import com.facebook.react.uimanager.UIImplementationProvider;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.util.RNThread;
import com.facebook.react.views.imagehelper.ResourceDrawableIdHelper;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class ReactInstanceManager implements Handler.Callback {
  private static final String TAG = ReactInstanceManager.class.getSimpleName();
  
  private final Context mApplicationContext;
  
  private final List<ReactRootView> mAttachedRootViews = Collections.synchronizedList(new ArrayList<ReactRootView>());
  
  private final NotThreadSafeBridgeIdleDebugListener mBridgeIdleDebugListener;
  
  private final JSBundleLoader mBundleLoader;
  
  public volatile boolean mCreateContextReady = true;
  
  private volatile RNThread mCreateReactContextThread;
  
  public Activity mCurrentActivity;
  
  private volatile ReactContext mCurrentReactContext;
  
  private DefaultHardwareBackBtnHandler mDefaultBackButtonImpl;
  
  public RNDegradeExceptionHandler mDegradeExceptionHandler;
  
  private final boolean mDelayViewManagerClassLoadsEnabled;
  
  public final DevSupportManager mDevSupportManager;
  
  public volatile boolean mHasStartedCreatingInitialContext = false;
  
  public volatile Boolean mHasStartedDestroying = Boolean.valueOf(false);
  
  private final JSIModulesProvider mJSIModulesProvider;
  
  private final String mJSMainModulePath;
  
  private final JavaScriptExecutorFactory mJavaScriptExecutorFactory;
  
  private final boolean mLazyNativeModulesEnabled;
  
  private volatile LifecycleState mLifecycleState;
  
  private final MemoryPressureRouter mMemoryPressureRouter;
  
  private final NativeModuleCallExceptionHandler mNativeModuleCallExceptionHandler;
  
  private final List<ReactPackage> mPackages;
  
  public ReactContextInitParams mPendingReactContextInitParams;
  
  private final Object mReactContextLock = new Object();
  
  private final Collection<ReactInstanceEventListener> mReactInstanceEventListeners = Collections.synchronizedSet(new HashSet<ReactInstanceEventListener>());
  
  public final boolean mUseDeveloperSupport;
  
  private List<ViewManager> mViewManagers;
  
  ReactInstanceManager(Context paramContext, Activity paramActivity, DefaultHardwareBackBtnHandler paramDefaultHardwareBackBtnHandler, JavaScriptExecutorFactory paramJavaScriptExecutorFactory, JSBundleLoader paramJSBundleLoader, String paramString, List<ReactPackage> paramList, boolean paramBoolean1, NotThreadSafeBridgeIdleDebugListener paramNotThreadSafeBridgeIdleDebugListener, LifecycleState paramLifecycleState, UIImplementationProvider paramUIImplementationProvider, NativeModuleCallExceptionHandler paramNativeModuleCallExceptionHandler, RedBoxHandler paramRedBoxHandler, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, DevBundleDownloadListener paramDevBundleDownloadListener, int paramInt1, int paramInt2, JSIModulesProvider paramJSIModulesProvider, RNDegradeExceptionHandler paramRNDegradeExceptionHandler) {
    initializeSoLoaderIfNecessary(paramContext);
    DisplayMetricsHolder.initDisplayMetricsIfNotInitialized(paramContext);
    this.mDegradeExceptionHandler = paramRNDegradeExceptionHandler;
    this.mApplicationContext = paramContext;
    this.mCurrentActivity = paramActivity;
    this.mDefaultBackButtonImpl = paramDefaultHardwareBackBtnHandler;
    this.mJavaScriptExecutorFactory = paramJavaScriptExecutorFactory;
    this.mBundleLoader = paramJSBundleLoader;
    this.mJSMainModulePath = paramString;
    this.mPackages = new ArrayList<ReactPackage>();
    this.mUseDeveloperSupport = paramBoolean1;
    this.mDevSupportManager = DevSupportManagerFactory.create(paramContext, createDevHelperInterface(), this.mJSMainModulePath, paramBoolean1, paramRedBoxHandler, paramDevBundleDownloadListener, paramInt1);
    this.mBridgeIdleDebugListener = paramNotThreadSafeBridgeIdleDebugListener;
    this.mLifecycleState = paramLifecycleState;
    this.mMemoryPressureRouter = new MemoryPressureRouter(paramContext);
    this.mNativeModuleCallExceptionHandler = paramNativeModuleCallExceptionHandler;
    this.mLazyNativeModulesEnabled = paramBoolean2;
    this.mDelayViewManagerClassLoadsEnabled = paramBoolean4;
    synchronized (this.mPackages) {
      this.mPackages.add(new CoreModulesPackage(this, new DefaultHardwareBackBtnHandler() {
              public void invokeDefaultOnBackPressed() {
                ReactInstanceManager.this.invokeDefaultOnBackPressed();
              }
            },  paramUIImplementationProvider, paramBoolean3, paramInt2));
      if (this.mUseDeveloperSupport)
        this.mPackages.add(new DebugCorePackage()); 
      this.mPackages.addAll(paramList);
      this.mJSIModulesProvider = paramJSIModulesProvider;
      ReactChoreographer.initialize();
      if (this.mUseDeveloperSupport)
        this.mDevSupportManager.startInspector(); 
      this.mCreateReactContextThread = new RNThread("ReactNativeContextThread", this);
      return;
    } 
  }
  
  private void attachRootViewToInstance(final ReactRootView rootView, CatalystInstance paramCatalystInstance) {
    UIManager uIManager;
    a.a(0L, "attachRootViewToInstance");
    if (rootView.isFabric()) {
      uIManager = (UIManager)paramCatalystInstance.getJSIModule(UIManager.class);
    } else {
      uIManager = (UIManager)paramCatalystInstance.getNativeModule(UIManagerModule.class);
    } 
    final int rootTag = uIManager.addRootView(rootView);
    rootView.setRootViewTag(i);
    rootView.invokeJSEntryPoint();
    paramCatalystInstance.setRootView(new WeakReference<ReactRootView>(rootView));
    UiThreadUtil.runOnUiThread(new Runnable() {
          public void run() {
            rootView.onAttachedToReactInstance();
          }
        });
    a.a(0L);
  }
  
  public static ReactInstanceManagerBuilder builder() {
    return new ReactInstanceManagerBuilder();
  }
  
  private ReactInstanceManagerDevHelper createDevHelperInterface() {
    return new ReactInstanceManagerDevHelper() {
        public Activity getCurrentActivity() {
          return ReactInstanceManager.this.mCurrentActivity;
        }
        
        public void onJSBundleLoadedFromServer() {
          ReactInstanceManager.this.onJSBundleLoadedFromServer();
        }
        
        public void onReloadWithJSDebugger(JavaJSExecutor.Factory param1Factory) {
          ReactInstanceManager.this.onReloadWithJSDebugger(param1Factory);
        }
        
        public void toggleElementInspector() {
          ReactInstanceManager.this.toggleElementInspector();
        }
      };
  }
  
  private void detachViewFromInstance(ReactRootView paramReactRootView, CatalystInstance paramCatalystInstance) {
    UiThreadUtil.assertOnUiThread();
    if (paramReactRootView.isFabric()) {
      ((ReactFabric)paramCatalystInstance.getJSModule(ReactFabric.class)).unmountComponentAtNodeAndRemoveContainer(paramReactRootView.getId());
      return;
    } 
    ((AppRegistry)paramCatalystInstance.getJSModule(AppRegistry.class)).unmountApplicationComponentAtRootTag(paramReactRootView.getId());
  }
  
  private Handler getReactContextThreadHandler() {
    RNThread rNThread = this.mCreateReactContextThread;
    return (rNThread == null) ? null : rNThread.getHandler();
  }
  
  private static void initializeSoLoaderIfNecessary(Context paramContext) {
    try {
      return;
    } finally {
      paramContext = null;
    } 
  }
  
  private void moveReactContextToCurrentLifecycleState() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mLifecycleState : Lcom/facebook/react/common/LifecycleState;
    //   6: getstatic com/facebook/react/common/LifecycleState.RESUMED : Lcom/facebook/react/common/LifecycleState;
    //   9: if_acmpne -> 17
    //   12: aload_0
    //   13: iconst_1
    //   14: invokespecial moveToResumedLifecycleState : (Z)V
    //   17: aload_0
    //   18: monitorexit
    //   19: return
    //   20: astore_1
    //   21: aload_0
    //   22: monitorexit
    //   23: aload_1
    //   24: athrow
    // Exception table:
    //   from	to	target	type
    //   2	17	20	finally
  }
  
  private void moveToBeforeCreateLifecycleState() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual getCurrentReactContext : ()Lcom/facebook/react/bridge/ReactContext;
    //   6: astore_1
    //   7: aload_1
    //   8: ifnull -> 46
    //   11: aload_0
    //   12: getfield mLifecycleState : Lcom/facebook/react/common/LifecycleState;
    //   15: getstatic com/facebook/react/common/LifecycleState.RESUMED : Lcom/facebook/react/common/LifecycleState;
    //   18: if_acmpne -> 32
    //   21: aload_1
    //   22: invokevirtual onHostPause : ()V
    //   25: aload_0
    //   26: getstatic com/facebook/react/common/LifecycleState.BEFORE_RESUME : Lcom/facebook/react/common/LifecycleState;
    //   29: putfield mLifecycleState : Lcom/facebook/react/common/LifecycleState;
    //   32: aload_0
    //   33: getfield mLifecycleState : Lcom/facebook/react/common/LifecycleState;
    //   36: getstatic com/facebook/react/common/LifecycleState.BEFORE_RESUME : Lcom/facebook/react/common/LifecycleState;
    //   39: if_acmpne -> 46
    //   42: aload_1
    //   43: invokevirtual onHostDestroy : ()V
    //   46: aload_0
    //   47: getstatic com/facebook/react/common/LifecycleState.BEFORE_CREATE : Lcom/facebook/react/common/LifecycleState;
    //   50: putfield mLifecycleState : Lcom/facebook/react/common/LifecycleState;
    //   53: aload_0
    //   54: monitorexit
    //   55: return
    //   56: astore_1
    //   57: aload_0
    //   58: monitorexit
    //   59: aload_1
    //   60: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	56	finally
    //   11	32	56	finally
    //   32	46	56	finally
    //   46	53	56	finally
  }
  
  private void moveToBeforeResumeLifecycleState() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual getCurrentReactContext : ()Lcom/facebook/react/bridge/ReactContext;
    //   6: astore_1
    //   7: aload_1
    //   8: ifnull -> 50
    //   11: aload_0
    //   12: getfield mLifecycleState : Lcom/facebook/react/common/LifecycleState;
    //   15: getstatic com/facebook/react/common/LifecycleState.BEFORE_CREATE : Lcom/facebook/react/common/LifecycleState;
    //   18: if_acmpne -> 36
    //   21: aload_1
    //   22: aload_0
    //   23: getfield mCurrentActivity : Landroid/app/Activity;
    //   26: invokevirtual onHostResume : (Landroid/app/Activity;)V
    //   29: aload_1
    //   30: invokevirtual onHostPause : ()V
    //   33: goto -> 50
    //   36: aload_0
    //   37: getfield mLifecycleState : Lcom/facebook/react/common/LifecycleState;
    //   40: getstatic com/facebook/react/common/LifecycleState.RESUMED : Lcom/facebook/react/common/LifecycleState;
    //   43: if_acmpne -> 50
    //   46: aload_1
    //   47: invokevirtual onHostPause : ()V
    //   50: aload_0
    //   51: getstatic com/facebook/react/common/LifecycleState.BEFORE_RESUME : Lcom/facebook/react/common/LifecycleState;
    //   54: putfield mLifecycleState : Lcom/facebook/react/common/LifecycleState;
    //   57: aload_0
    //   58: monitorexit
    //   59: return
    //   60: astore_1
    //   61: aload_0
    //   62: monitorexit
    //   63: aload_1
    //   64: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	60	finally
    //   11	33	60	finally
    //   36	50	60	finally
    //   50	57	60	finally
  }
  
  private void moveToResumedLifecycleState(boolean paramBoolean) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual getCurrentReactContext : ()Lcom/facebook/react/bridge/ReactContext;
    //   6: astore_2
    //   7: aload_2
    //   8: ifnull -> 43
    //   11: iload_1
    //   12: ifne -> 35
    //   15: aload_0
    //   16: getfield mLifecycleState : Lcom/facebook/react/common/LifecycleState;
    //   19: getstatic com/facebook/react/common/LifecycleState.BEFORE_RESUME : Lcom/facebook/react/common/LifecycleState;
    //   22: if_acmpeq -> 35
    //   25: aload_0
    //   26: getfield mLifecycleState : Lcom/facebook/react/common/LifecycleState;
    //   29: getstatic com/facebook/react/common/LifecycleState.BEFORE_CREATE : Lcom/facebook/react/common/LifecycleState;
    //   32: if_acmpne -> 43
    //   35: aload_2
    //   36: aload_0
    //   37: getfield mCurrentActivity : Landroid/app/Activity;
    //   40: invokevirtual onHostResume : (Landroid/app/Activity;)V
    //   43: aload_0
    //   44: getstatic com/facebook/react/common/LifecycleState.RESUMED : Lcom/facebook/react/common/LifecycleState;
    //   47: putfield mLifecycleState : Lcom/facebook/react/common/LifecycleState;
    //   50: aload_0
    //   51: monitorexit
    //   52: return
    //   53: astore_2
    //   54: aload_0
    //   55: monitorexit
    //   56: aload_2
    //   57: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	53	finally
    //   15	35	53	finally
    //   35	43	53	finally
    //   43	50	53	finally
  }
  
  private void processPackage(ReactPackage paramReactPackage, NativeModuleRegistryBuilder paramNativeModuleRegistryBuilder) {
    paramReactPackage.getClass().getSimpleName();
    boolean bool = paramReactPackage instanceof ReactPackageLogger;
    if (bool)
      ((ReactPackageLogger)paramReactPackage).startProcessPackage(); 
    paramNativeModuleRegistryBuilder.processPackage(paramReactPackage);
    if (bool)
      ((ReactPackageLogger)paramReactPackage).endProcessPackage(); 
  }
  
  private NativeModuleRegistry processPackages(ReactApplicationContext paramReactApplicationContext, List<ReactPackage> paramList, boolean paramBoolean) {
    // Byte code:
    //   0: new com/facebook/react/NativeModuleRegistryBuilder
    //   3: dup
    //   4: aload_1
    //   5: aload_0
    //   6: aload_0
    //   7: getfield mLazyNativeModulesEnabled : Z
    //   10: invokespecial <init> : (Lcom/facebook/react/bridge/ReactApplicationContext;Lcom/facebook/react/ReactInstanceManager;Z)V
    //   13: astore #5
    //   15: getstatic com/facebook/react/bridge/ReactMarkerConstants.PROCESS_PACKAGES_START : Lcom/facebook/react/bridge/ReactMarkerConstants;
    //   18: invokestatic logMarker : (Lcom/facebook/react/bridge/ReactMarkerConstants;)V
    //   21: aload_0
    //   22: getfield mPackages : Ljava/util/List;
    //   25: astore #4
    //   27: aload #4
    //   29: monitorenter
    //   30: iload_3
    //   31: ifne -> 118
    //   34: iconst_2
    //   35: anewarray com/facebook/react/LazyReactPackage
    //   38: astore #6
    //   40: aload_2
    //   41: invokeinterface iterator : ()Ljava/util/Iterator;
    //   46: astore #7
    //   48: aload #7
    //   50: invokeinterface hasNext : ()Z
    //   55: ifeq -> 107
    //   58: aload #7
    //   60: invokeinterface next : ()Ljava/lang/Object;
    //   65: checkcast com/facebook/react/ReactPackage
    //   68: astore #8
    //   70: aload #8
    //   72: instanceof com/facebook/react/CoreModulesPackage
    //   75: ifeq -> 87
    //   78: aload #6
    //   80: iconst_0
    //   81: aload #8
    //   83: checkcast com/facebook/react/LazyReactPackage
    //   86: aastore
    //   87: aload #8
    //   89: instanceof com/facebook/react/shell/MainReactPackage
    //   92: ifeq -> 48
    //   95: aload #6
    //   97: iconst_1
    //   98: aload #8
    //   100: checkcast com/facebook/react/LazyReactPackage
    //   103: aastore
    //   104: goto -> 48
    //   107: aload #5
    //   109: aload #6
    //   111: invokestatic asList : ([Ljava/lang/Object;)Ljava/util/List;
    //   114: aload_1
    //   115: invokevirtual addCorePackages : (Ljava/util/List;Lcom/facebook/react/bridge/ReactApplicationContext;)V
    //   118: aload_2
    //   119: invokeinterface iterator : ()Ljava/util/Iterator;
    //   124: astore_1
    //   125: aload_1
    //   126: invokeinterface hasNext : ()Z
    //   131: ifeq -> 204
    //   134: aload_1
    //   135: invokeinterface next : ()Ljava/lang/Object;
    //   140: checkcast com/facebook/react/ReactPackage
    //   143: astore_2
    //   144: iload_3
    //   145: ifeq -> 161
    //   148: aload_0
    //   149: getfield mPackages : Ljava/util/List;
    //   152: aload_2
    //   153: invokeinterface contains : (Ljava/lang/Object;)Z
    //   158: ifne -> 125
    //   161: lconst_0
    //   162: ldc_w 'createAndProcessCustomReactPackage'
    //   165: invokestatic a : (JLjava/lang/String;)V
    //   168: iload_3
    //   169: ifeq -> 183
    //   172: aload_0
    //   173: getfield mPackages : Ljava/util/List;
    //   176: aload_2
    //   177: invokeinterface add : (Ljava/lang/Object;)Z
    //   182: pop
    //   183: aload_0
    //   184: aload_2
    //   185: aload #5
    //   187: invokespecial processPackage : (Lcom/facebook/react/ReactPackage;Lcom/facebook/react/NativeModuleRegistryBuilder;)V
    //   190: lconst_0
    //   191: invokestatic a : (J)V
    //   194: goto -> 125
    //   197: astore_1
    //   198: lconst_0
    //   199: invokestatic a : (J)V
    //   202: aload_1
    //   203: athrow
    //   204: aload #4
    //   206: monitorexit
    //   207: getstatic com/facebook/react/bridge/ReactMarkerConstants.PROCESS_PACKAGES_END : Lcom/facebook/react/bridge/ReactMarkerConstants;
    //   210: invokestatic logMarker : (Lcom/facebook/react/bridge/ReactMarkerConstants;)V
    //   213: getstatic com/facebook/react/bridge/ReactMarkerConstants.BUILD_NATIVE_MODULE_REGISTRY_START : Lcom/facebook/react/bridge/ReactMarkerConstants;
    //   216: invokestatic logMarker : (Lcom/facebook/react/bridge/ReactMarkerConstants;)V
    //   219: lconst_0
    //   220: ldc_w 'buildNativeModuleRegistry'
    //   223: invokestatic a : (JLjava/lang/String;)V
    //   226: aload #5
    //   228: invokevirtual build : ()Lcom/facebook/react/bridge/NativeModuleRegistry;
    //   231: astore_1
    //   232: lconst_0
    //   233: invokestatic a : (J)V
    //   236: getstatic com/facebook/react/bridge/ReactMarkerConstants.BUILD_NATIVE_MODULE_REGISTRY_END : Lcom/facebook/react/bridge/ReactMarkerConstants;
    //   239: invokestatic logMarker : (Lcom/facebook/react/bridge/ReactMarkerConstants;)V
    //   242: aload_1
    //   243: areturn
    //   244: astore_1
    //   245: lconst_0
    //   246: invokestatic a : (J)V
    //   249: getstatic com/facebook/react/bridge/ReactMarkerConstants.BUILD_NATIVE_MODULE_REGISTRY_END : Lcom/facebook/react/bridge/ReactMarkerConstants;
    //   252: invokestatic logMarker : (Lcom/facebook/react/bridge/ReactMarkerConstants;)V
    //   255: aload_1
    //   256: athrow
    //   257: astore_1
    //   258: aload #4
    //   260: monitorexit
    //   261: goto -> 266
    //   264: aload_1
    //   265: athrow
    //   266: goto -> 264
    // Exception table:
    //   from	to	target	type
    //   34	48	257	finally
    //   48	87	257	finally
    //   87	104	257	finally
    //   107	118	257	finally
    //   118	125	257	finally
    //   125	144	257	finally
    //   148	161	257	finally
    //   161	168	257	finally
    //   172	183	197	finally
    //   183	190	197	finally
    //   190	194	257	finally
    //   198	204	257	finally
    //   204	207	257	finally
    //   226	232	244	finally
    //   258	261	257	finally
  }
  
  private void recreateReactContextInBackground(JavaScriptExecutorFactory paramJavaScriptExecutorFactory, JSBundleLoader paramJSBundleLoader) {
    UiThreadUtil.assertOnUiThread();
    ReactContextInitParams reactContextInitParams = new ReactContextInitParams(paramJavaScriptExecutorFactory, paramJSBundleLoader);
    if (this.mCreateContextReady) {
      runCreateReactContextOnNewThread(reactContextInitParams);
      return;
    } 
    this.mPendingReactContextInitParams = reactContextInitParams;
  }
  
  private void recreateReactContextInBackgroundInner() {
    UiThreadUtil.assertOnUiThread();
    if (this.mUseDeveloperSupport && this.mJSMainModulePath != null) {
      final DeveloperSettings devSettings = this.mDevSupportManager.getDevSettings();
      if (this.mDevSupportManager.hasUpToDateJSBundleInCache() && !developerSettings.isRemoteJSDebugEnabled()) {
        onJSBundleLoadedFromServer();
        return;
      } 
      if (this.mBundleLoader == null) {
        this.mDevSupportManager.handleReloadJS();
        return;
      } 
      this.mDevSupportManager.isPackagerRunning(new PackagerStatusCallback() {
            public void onPackagerStatusFetched(final boolean packagerIsRunning) {
              UiThreadUtil.runOnUiThread(new Runnable() {
                    public void run() {
                      if (packagerIsRunning) {
                        ReactInstanceManager.this.mDevSupportManager.handleReloadJS();
                        return;
                      } 
                      devSettings.setRemoteJSDebugEnabled(false);
                      ReactInstanceManager.this.recreateReactContextInBackgroundFromBundleLoader();
                    }
                  });
            }
          });
      return;
    } 
    recreateReactContextInBackgroundFromBundleLoader();
  }
  
  private void tearDownReactContext(ReactContext paramReactContext) {
    UiThreadUtil.assertOnUiThread();
    if (this.mLifecycleState == LifecycleState.RESUMED)
      paramReactContext.onHostPause(); 
    synchronized (this.mAttachedRootViews) {
      for (ReactRootView reactRootView : this.mAttachedRootViews) {
        reactRootView.removeAllViews();
        reactRootView.setId(-1);
      } 
      paramReactContext.destroy();
      this.mDevSupportManager.onReactInstanceDestroyed(paramReactContext);
      this.mMemoryPressureRouter.removeMemoryPressureListener((MemoryPressureListener)paramReactContext.getCatalystInstance());
      return;
    } 
  }
  
  public void addReactInstanceEventListener(ReactInstanceEventListener paramReactInstanceEventListener) {
    this.mReactInstanceEventListeners.add(paramReactInstanceEventListener);
  }
  
  public void attachRootView(ReactRootView paramReactRootView) {
    UiThreadUtil.assertOnUiThread();
    this.mAttachedRootViews.add(paramReactRootView);
    paramReactRootView.removeAllViews();
    paramReactRootView.setId(-1);
    ReactContext reactContext = getCurrentReactContext();
    if (this.mCreateContextReady && reactContext != null)
      attachRootViewToInstance(paramReactRootView, reactContext.getCatalystInstance()); 
  }
  
  public ReactApplicationContext createReactContext(JavaScriptExecutor paramJavaScriptExecutor, JSBundleLoader paramJSBundleLoader) {
    DevSupportManager devSupportManager;
    ReactMarker.logMarker(ReactMarkerConstants.CREATE_REACT_CONTEXT_START);
    ReactApplicationContext reactApplicationContext = new ReactApplicationContext(this.mApplicationContext);
    if (paramJSBundleLoader != null)
      reactApplicationContext.setRemoteDebug(paramJSBundleLoader.mDebugRemote); 
    if (this.mUseDeveloperSupport)
      reactApplicationContext.setNativeModuleCallExceptionHandler((NativeModuleCallExceptionHandler)this.mDevSupportManager); 
    NativeModuleRegistry nativeModuleRegistry = processPackages(reactApplicationContext, this.mPackages, false);
    NativeModuleCallExceptionHandler nativeModuleCallExceptionHandler = this.mNativeModuleCallExceptionHandler;
    if (nativeModuleCallExceptionHandler == null)
      devSupportManager = this.mDevSupportManager; 
    null = (new CatalystInstanceImpl.Builder()).setReactQueueConfigurationSpec(ReactQueueConfigurationSpec.createDefault()).setJSExecutor(paramJavaScriptExecutor).setRegistry(nativeModuleRegistry).setJSBundleLoader(paramJSBundleLoader).setNativeModuleCallExceptionHandler((NativeModuleCallExceptionHandler)devSupportManager);
    ReactMarker.logMarker(ReactMarkerConstants.CREATE_CATALYST_INSTANCE_START);
    a.a(0L, "createCatalystInstance");
    try {
      CatalystInstanceImpl catalystInstanceImpl = null.build();
      a.a(0L);
      ReactMarker.logMarker(ReactMarkerConstants.CREATE_CATALYST_INSTANCE_END);
      JSIModulesProvider jSIModulesProvider = this.mJSIModulesProvider;
      if (jSIModulesProvider != null)
        catalystInstanceImpl.addJSIModules(jSIModulesProvider.getJSIModules(reactApplicationContext, catalystInstanceImpl.getJavaScriptContextHolder())); 
      NotThreadSafeBridgeIdleDebugListener notThreadSafeBridgeIdleDebugListener = this.mBridgeIdleDebugListener;
      if (notThreadSafeBridgeIdleDebugListener != null)
        catalystInstanceImpl.addBridgeIdleDebugListener(notThreadSafeBridgeIdleDebugListener); 
      ReactMarker.logMarker(ReactMarkerConstants.PRE_RUN_JS_BUNDLE_START);
      return reactApplicationContext;
    } finally {
      a.a(0L);
      ReactMarker.logMarker(ReactMarkerConstants.CREATE_CATALYST_INSTANCE_END);
    } 
  }
  
  public void createReactContextInBackground() {
    a.a(this.mHasStartedCreatingInitialContext ^ true, "createReactContextInBackground should only be called when creating the react application for the first time. When reloading JS, e.g. from a new file, explicitlyuse recreateReactContextInBackground");
    this.mHasStartedCreatingInitialContext = true;
    recreateReactContextInBackgroundInner();
  }
  
  public ViewManager createViewManager(String paramString) {
    synchronized (this.mReactContextLock) {
      ReactApplicationContext reactApplicationContext = (ReactApplicationContext)getCurrentReactContext();
      if (reactApplicationContext == null || !reactApplicationContext.hasActiveCatalystInstance())
        return null; 
      synchronized (this.mPackages) {
        Iterator<ReactPackage> iterator = this.mPackages.iterator();
        while (true) {
          if (iterator.hasNext()) {
            ReactPackage reactPackage = iterator.next();
            if (reactPackage instanceof ViewManagerOnDemandReactPackage) {
              boolean bool;
              ViewManagerOnDemandReactPackage viewManagerOnDemandReactPackage = (ViewManagerOnDemandReactPackage)reactPackage;
              if (!this.mDelayViewManagerClassLoadsEnabled) {
                bool = true;
              } else {
                bool = false;
              } 
              ViewManager viewManager = viewManagerOnDemandReactPackage.createViewManager(reactApplicationContext, paramString, bool);
              if (viewManager != null)
                return viewManager; 
            } 
            continue;
          } 
          return null;
        } 
      } 
    } 
  }
  
  public void delayLoadJavaScriptOnCureentContext(final String sourceUrl, final String script, final boolean loadSynchronously, long paramLong) {
    try {
      postDelayed(new Runnable() {
            public void run() {
              if (ReactInstanceManager.this.getCurrentReactContext() != null)
                ReactInstanceManager.this.getCurrentReactContext().getCatalystInstance().loadJavaScript(sourceUrl, script, loadSynchronously); 
            }
          }paramLong);
      return;
    } catch (Exception exception) {
      exception.printStackTrace();
      return;
    } 
  }
  
  public void deleteJSBundleFile() {
    if (this.mUseDeveloperSupport)
      this.mDevSupportManager.deleteJSBundleFile(); 
  }
  
  public void destroy() {
    UiThreadUtil.assertOnUiThread();
    this.mHasStartedDestroying = Boolean.valueOf(true);
    if (this.mUseDeveloperSupport) {
      this.mDevSupportManager.setDevSupportEnabled(false);
      this.mDevSupportManager.stopInspector();
    } 
    moveToBeforeCreateLifecycleState();
    if (this.mCreateReactContextThread != null) {
      this.mCreateReactContextThread.quit();
      this.mCreateReactContextThread = null;
    } 
    this.mMemoryPressureRouter.destroy(this.mApplicationContext);
    synchronized (this.mReactContextLock) {
      if (this.mCurrentReactContext != null) {
        this.mCurrentReactContext.destroy();
        this.mCurrentReactContext = null;
      } 
      this.mHasStartedCreatingInitialContext = false;
      this.mCurrentActivity = null;
      ResourceDrawableIdHelper.getInstance().clear();
      this.mHasStartedDestroying = Boolean.valueOf(false);
      synchronized (this.mHasStartedDestroying) {
        this.mHasStartedDestroying.notifyAll();
        return;
      } 
    } 
  }
  
  public void detachRootView(ReactRootView paramReactRootView) {
    UiThreadUtil.assertOnUiThread();
    synchronized (this.mAttachedRootViews) {
      if (this.mAttachedRootViews.remove(paramReactRootView)) {
        ReactContext reactContext = getCurrentReactContext();
        this.mAttachedRootViews.remove(paramReactRootView);
        if (reactContext != null && reactContext.hasActiveCatalystInstance())
          detachViewFromInstance(paramReactRootView, reactContext.getCatalystInstance()); 
      } 
      return;
    } 
  }
  
  public ReactContext getCurrentReactContext() {
    synchronized (this.mReactContextLock) {
      return this.mCurrentReactContext;
    } 
  }
  
  public DevSupportManager getDevSupportManager() {
    return this.mDevSupportManager;
  }
  
  public LifecycleState getLifecycleState() {
    return this.mLifecycleState;
  }
  
  public MemoryPressureRouter getMemoryPressureRouter() {
    return this.mMemoryPressureRouter;
  }
  
  public List<ViewManager> getOrCreateViewManagers(ReactApplicationContext paramReactApplicationContext) {
    ReactMarker.logMarker(ReactMarkerConstants.CREATE_VIEW_MANAGERS_START);
    a.a(0L, "createAllViewManagers");
    try {
      if (this.mViewManagers == null)
        synchronized (this.mPackages) {
          if (this.mViewManagers == null) {
            this.mViewManagers = new ArrayList<ViewManager>();
            for (ReactPackage reactPackage : this.mPackages)
              this.mViewManagers.addAll(reactPackage.createViewManagers(paramReactApplicationContext)); 
            return this.mViewManagers;
          } 
        }  
      return this.mViewManagers;
    } finally {
      a.a(0L);
      ReactMarker.logMarker(ReactMarkerConstants.CREATE_VIEW_MANAGERS_END);
    } 
  }
  
  public List<String> getViewManagerNames() {
    synchronized (this.mReactContextLock) {
      null = (ReactApplicationContext)getCurrentReactContext();
      if (null == null || !null.hasActiveCatalystInstance())
        return null; 
      synchronized (this.mPackages) {
        HashSet<String> hashSet = new HashSet();
        Iterator<ReactPackage> iterator = this.mPackages.iterator();
        while (true) {
          if (iterator.hasNext()) {
            ReactPackage reactPackage = iterator.next();
            if (reactPackage instanceof ViewManagerOnDemandReactPackage) {
              boolean bool;
              ViewManagerOnDemandReactPackage viewManagerOnDemandReactPackage = (ViewManagerOnDemandReactPackage)reactPackage;
              if (!this.mDelayViewManagerClassLoadsEnabled) {
                bool = true;
              } else {
                bool = false;
              } 
              List<String> list = viewManagerOnDemandReactPackage.getViewManagerNames(null, bool);
              if (list != null)
                hashSet.addAll(list); 
            } 
            continue;
          } 
          return new ArrayList<String>(hashSet);
        } 
      } 
    } 
  }
  
  public boolean handleMessage(Message paramMessage) {
    if (paramMessage == null)
      return false; 
    int i = paramMessage.what;
    if (i != 10001) {
      if (i != 10002)
        return true; 
      RNDegradeExceptionHandler rNDegradeExceptionHandler = this.mDegradeExceptionHandler;
      if (rNDegradeExceptionHandler != null && !this.mUseDeveloperSupport)
        rNDegradeExceptionHandler.onDegrade(new Exception("NATIVE_ERROR_JSBUNDLE_LOST")); 
      this.mHasStartedCreatingInitialContext = false;
      NativeModuleCallExceptionHandler nativeModuleCallExceptionHandler = this.mNativeModuleCallExceptionHandler;
      if (nativeModuleCallExceptionHandler == null)
        return true; 
      if (paramMessage.obj instanceof Exception) {
        nativeModuleCallExceptionHandler.handleException((Exception)paramMessage.obj);
        return true;
      } 
      if (paramMessage.obj instanceof Throwable) {
        nativeModuleCallExceptionHandler.handleException(new Exception((Throwable)paramMessage.obj));
        return true;
      } 
    } else {
      NativeModuleCallExceptionHandler nativeModuleCallExceptionHandler = this.mNativeModuleCallExceptionHandler;
      if (nativeModuleCallExceptionHandler == null)
        return true; 
      if (paramMessage.obj instanceof Exception) {
        nativeModuleCallExceptionHandler.handleException((Exception)paramMessage.obj);
        return true;
      } 
      if (paramMessage.obj instanceof Throwable)
        nativeModuleCallExceptionHandler.handleException(new Exception((Throwable)paramMessage.obj)); 
    } 
    return true;
  }
  
  public boolean hasStartedCreatingInitialContext() {
    return this.mHasStartedCreatingInitialContext;
  }
  
  public void invokeDefaultOnBackPressed() {
    UiThreadUtil.assertOnUiThread();
    DefaultHardwareBackBtnHandler defaultHardwareBackBtnHandler = this.mDefaultBackButtonImpl;
    if (defaultHardwareBackBtnHandler != null)
      defaultHardwareBackBtnHandler.invokeDefaultOnBackPressed(); 
  }
  
  public boolean isReactContextThread() {
    return (this.mCreateReactContextThread != null && this.mCreateReactContextThread.getId() == Thread.currentThread().getId());
  }
  
  public void loadJavaScriptOnCureentContext(final String sourceUrl, final String script, final boolean loadSynchronously) {
    try {
      post(new Runnable() {
            public void run() {
              if (ReactInstanceManager.this.getCurrentReactContext() != null)
                ReactInstanceManager.this.getCurrentReactContext().getCatalystInstance().loadJavaScript(sourceUrl, script, loadSynchronously); 
            }
          });
      return;
    } catch (Exception exception) {
      exception.printStackTrace();
      return;
    } 
  }
  
  public void onActivityResult(Activity paramActivity, int paramInt1, int paramInt2, Intent paramIntent) {
    ReactContext reactContext = getCurrentReactContext();
    if (reactContext != null)
      reactContext.onActivityResult(paramActivity, paramInt1, paramInt2, paramIntent); 
  }
  
  public void onBackPressed() {
    UiThreadUtil.assertOnUiThread();
    ReactContext reactContext = this.mCurrentReactContext;
    if (reactContext == null) {
      a.b("ReactNative", "Instance detached from instance manager");
      invokeDefaultOnBackPressed();
      return;
    } 
    ((DeviceEventManagerModule)reactContext.getNativeModule(DeviceEventManagerModule.class)).emitHardwareBackPressed();
  }
  
  public void onHostDestroy() {
    UiThreadUtil.assertOnUiThread();
    if (this.mUseDeveloperSupport)
      this.mDevSupportManager.setDevSupportEnabled(false); 
    moveToBeforeCreateLifecycleState();
    this.mCurrentActivity = null;
  }
  
  public void onHostDestroy(Activity paramActivity) {
    if (paramActivity == this.mCurrentActivity)
      onHostDestroy(); 
  }
  
  public void onHostPause() {
    UiThreadUtil.assertOnUiThread();
    this.mDefaultBackButtonImpl = null;
    if (this.mUseDeveloperSupport)
      this.mDevSupportManager.setDevSupportEnabled(false); 
    moveToBeforeResumeLifecycleState();
  }
  
  public void onHostPause(Activity paramActivity) {
    boolean bool;
    a.b(this.mCurrentActivity);
    if (paramActivity == this.mCurrentActivity) {
      bool = true;
    } else {
      bool = false;
    } 
    StringBuilder stringBuilder = new StringBuilder("Pausing an activity that is not the current activity, this is incorrect! Current activity: ");
    stringBuilder.append(this.mCurrentActivity.getClass().getSimpleName());
    stringBuilder.append(" Paused activity: ");
    stringBuilder.append(paramActivity.getClass().getSimpleName());
    a.a(bool, stringBuilder.toString());
    onHostPause();
  }
  
  public void onHostResume(Activity paramActivity) {
    UiThreadUtil.assertOnUiThread();
    this.mCurrentActivity = paramActivity;
    if (this.mUseDeveloperSupport) {
      final View decorView = this.mCurrentActivity.getWindow().getDecorView();
      if (!u.B(view)) {
        view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
              public void onViewAttachedToWindow(View param1View) {
                decorView.removeOnAttachStateChangeListener(this);
                ReactInstanceManager.this.mDevSupportManager.setDevSupportEnabled(true);
              }
              
              public void onViewDetachedFromWindow(View param1View) {}
            });
      } else {
        this.mDevSupportManager.setDevSupportEnabled(true);
      } 
    } 
    moveToResumedLifecycleState(false);
  }
  
  public void onHostResume(Activity paramActivity, DefaultHardwareBackBtnHandler paramDefaultHardwareBackBtnHandler) {
    UiThreadUtil.assertOnUiThread();
    this.mDefaultBackButtonImpl = paramDefaultHardwareBackBtnHandler;
    onHostResume(paramActivity);
  }
  
  public void onJSBundleLoadedFromServer() {
    recreateReactContextInBackground(this.mJavaScriptExecutorFactory, JSBundleLoader.createCachedBundleFromNetworkLoader(this.mDevSupportManager.getSourceUrl(), this.mDevSupportManager.getDownloadedJSBundleFile(), RNJavaScriptRuntime.SplitCommonType.NONE));
  }
  
  public void onNewIntent(Intent paramIntent) {
    UiThreadUtil.assertOnUiThread();
    ReactContext reactContext = getCurrentReactContext();
    if (reactContext == null) {
      a.b("ReactNative", "Instance detached from instance manager");
      return;
    } 
    String str = paramIntent.getAction();
    Uri uri = paramIntent.getData();
    if ("android.intent.action.VIEW".equals(str) && uri != null)
      ((DeviceEventManagerModule)reactContext.getNativeModule(DeviceEventManagerModule.class)).emitNewIntentReceived(uri); 
    reactContext.onNewIntent(this.mCurrentActivity, paramIntent);
  }
  
  public void onReloadWithJSDebugger(JavaJSExecutor.Factory paramFactory) {
    recreateReactContextInBackground((JavaScriptExecutorFactory)new ProxyJavaScriptExecutor.Factory(paramFactory), JSBundleLoader.createRemoteDebuggerBundleLoader(this.mDevSupportManager.getJSBundleURLForRemoteDebugging(), this.mDevSupportManager.getSourceUrl(), RNJavaScriptRuntime.SplitCommonType.NONE));
  }
  
  public void post(Runnable paramRunnable) {
    RNThread rNThread = this.mCreateReactContextThread;
    if (rNThread == null)
      return; 
    rNThread.post(paramRunnable);
  }
  
  public void post(Runnable paramRunnable, Object paramObject) {
    RNThread rNThread = this.mCreateReactContextThread;
    if (rNThread == null)
      return; 
    rNThread.post(paramRunnable, paramObject);
  }
  
  public void postDelayed(Runnable paramRunnable, long paramLong) {
    RNThread rNThread = this.mCreateReactContextThread;
    if (rNThread == null)
      return; 
    rNThread.post(paramRunnable, Long.valueOf(paramLong));
  }
  
  public void recreateReactContextInBackground() {
    a.a(this.mHasStartedCreatingInitialContext, "recreateReactContextInBackground should only be called after the initial createReactContextInBackground call.");
    recreateReactContextInBackgroundInner();
  }
  
  public void recreateReactContextInBackgroundFromBundleLoader() {
    recreateReactContextInBackground(this.mJavaScriptExecutorFactory, this.mBundleLoader);
  }
  
  public void removeMessage(int paramInt, Object paramObject) {
    RNThread rNThread = this.mCreateReactContextThread;
    if (rNThread != null) {
      if (rNThread.getLooper() == null)
        return; 
      rNThread.removeMessage(paramInt, paramObject);
    } 
  }
  
  public void removeReactInstanceEventListener(ReactInstanceEventListener paramReactInstanceEventListener) {
    this.mReactInstanceEventListeners.remove(paramReactInstanceEventListener);
  }
  
  public void runCreateReactContextOnNewThread(final ReactContextInitParams initParams) {
    UiThreadUtil.assertOnUiThread();
    synchronized (this.mAttachedRootViews) {
      synchronized (this.mReactContextLock) {
        if (this.mCurrentReactContext != null) {
          tearDownReactContext(this.mCurrentReactContext);
          this.mCurrentReactContext = null;
        } 
        this.mCreateContextReady = false;
        post(new Runnable() {
              public void run() {
                // Byte code:
                //   0: getstatic com/facebook/react/bridge/ReactMarkerConstants.REACT_CONTEXT_THREAD_END : Lcom/facebook/react/bridge/ReactMarkerConstants;
                //   3: invokestatic logMarker : (Lcom/facebook/react/bridge/ReactMarkerConstants;)V
                //   6: aload_0
                //   7: getfield this$0 : Lcom/facebook/react/ReactInstanceManager;
                //   10: getfield mHasStartedDestroying : Ljava/lang/Boolean;
                //   13: astore_2
                //   14: aload_2
                //   15: monitorenter
                //   16: aload_0
                //   17: getfield this$0 : Lcom/facebook/react/ReactInstanceManager;
                //   20: getfield mHasStartedDestroying : Ljava/lang/Boolean;
                //   23: invokevirtual booleanValue : ()Z
                //   26: istore_1
                //   27: iload_1
                //   28: ifeq -> 44
                //   31: aload_0
                //   32: getfield this$0 : Lcom/facebook/react/ReactInstanceManager;
                //   35: getfield mHasStartedDestroying : Ljava/lang/Boolean;
                //   38: invokevirtual wait : ()V
                //   41: goto -> 16
                //   44: aload_2
                //   45: monitorexit
                //   46: aload_0
                //   47: getfield this$0 : Lcom/facebook/react/ReactInstanceManager;
                //   50: iconst_1
                //   51: putfield mHasStartedCreatingInitialContext : Z
                //   54: bipush #-4
                //   56: invokestatic setThreadPriority : (I)V
                //   59: aload_0
                //   60: getfield this$0 : Lcom/facebook/react/ReactInstanceManager;
                //   63: aload_0
                //   64: getfield val$initParams : Lcom/facebook/react/ReactInstanceManager$ReactContextInitParams;
                //   67: invokevirtual getJsExecutorFactory : ()Lcom/facebook/react/bridge/JavaScriptExecutorFactory;
                //   70: invokeinterface create : ()Lcom/facebook/react/bridge/JavaScriptExecutor;
                //   75: aload_0
                //   76: getfield val$initParams : Lcom/facebook/react/ReactInstanceManager$ReactContextInitParams;
                //   79: invokevirtual getJsBundleLoader : ()Lcom/facebook/react/bridge/JSBundleLoader;
                //   82: invokevirtual createReactContext : (Lcom/facebook/react/bridge/JavaScriptExecutor;Lcom/facebook/react/bridge/JSBundleLoader;)Lcom/facebook/react/bridge/ReactApplicationContext;
                //   85: astore_2
                //   86: aload_0
                //   87: getfield this$0 : Lcom/facebook/react/ReactInstanceManager;
                //   90: iconst_1
                //   91: putfield mCreateContextReady : Z
                //   94: getstatic com/facebook/react/bridge/ReactMarkerConstants.PRE_SETUP_REACT_CONTEXT_START : Lcom/facebook/react/bridge/ReactMarkerConstants;
                //   97: invokestatic logMarker : (Lcom/facebook/react/bridge/ReactMarkerConstants;)V
                //   100: new com/facebook/react/ReactInstanceManager$7$1
                //   103: dup
                //   104: aload_0
                //   105: invokespecial <init> : (Lcom/facebook/react/ReactInstanceManager$7;)V
                //   108: astore_3
                //   109: aload_2
                //   110: new com/facebook/react/ReactInstanceManager$7$2
                //   113: dup
                //   114: aload_0
                //   115: aload_2
                //   116: invokespecial <init> : (Lcom/facebook/react/ReactInstanceManager$7;Lcom/facebook/react/bridge/ReactApplicationContext;)V
                //   119: invokevirtual runOnNativeModulesQueueThread : (Ljava/lang/Runnable;)V
                //   122: aload_3
                //   123: invokestatic runOnUiThread : (Ljava/lang/Runnable;)V
                //   126: return
                //   127: astore_2
                //   128: aload_0
                //   129: getfield this$0 : Lcom/facebook/react/ReactInstanceManager;
                //   132: getfield mDevSupportManager : Lcom/facebook/react/devsupport/interfaces/DevSupportManager;
                //   135: aload_2
                //   136: invokeinterface handleException : (Ljava/lang/Exception;)V
                //   141: aload_2
                //   142: instanceof java/lang/RuntimeException
                //   145: ifeq -> 205
                //   148: aload_2
                //   149: invokevirtual toString : ()Ljava/lang/String;
                //   152: ldc 'java.lang.RuntimeException: Unable to load script'
                //   154: invokevirtual contains : (Ljava/lang/CharSequence;)Z
                //   157: ifne -> 172
                //   160: aload_2
                //   161: invokevirtual toString : ()Ljava/lang/String;
                //   164: ldc 'java.lang.RuntimeException: Could not open file'
                //   166: invokevirtual contains : (Ljava/lang/CharSequence;)Z
                //   169: ifeq -> 205
                //   172: aload_0
                //   173: getfield this$0 : Lcom/facebook/react/ReactInstanceManager;
                //   176: getfield mDegradeExceptionHandler : Lcom/facebook/react/bridge/RNDegradeExceptionHandler;
                //   179: ifnull -> 205
                //   182: aload_0
                //   183: getfield this$0 : Lcom/facebook/react/ReactInstanceManager;
                //   186: getfield mUseDeveloperSupport : Z
                //   189: ifne -> 205
                //   192: aload_0
                //   193: getfield this$0 : Lcom/facebook/react/ReactInstanceManager;
                //   196: getfield mDegradeExceptionHandler : Lcom/facebook/react/bridge/RNDegradeExceptionHandler;
                //   199: aload_2
                //   200: invokeinterface onDegrade : (Ljava/lang/Exception;)V
                //   205: return
                //   206: astore_3
                //   207: aload_2
                //   208: monitorexit
                //   209: goto -> 214
                //   212: aload_3
                //   213: athrow
                //   214: goto -> 212
                //   217: astore_3
                //   218: goto -> 16
                // Exception table:
                //   from	to	target	type
                //   16	27	206	finally
                //   31	41	217	java/lang/InterruptedException
                //   31	41	206	finally
                //   44	46	206	finally
                //   54	126	127	java/lang/Exception
                //   207	209	206	finally
              }
            });
        ReactMarker.logMarker(ReactMarkerConstants.REACT_CONTEXT_THREAD_START);
        return;
      } 
    } 
  }
  
  public void sendMessageDelayed(Message paramMessage, long paramLong) {
    RNThread rNThread = this.mCreateReactContextThread;
    if (rNThread != null) {
      if (rNThread.getLooper() == null)
        return; 
      rNThread.sendMessageDelayed(paramMessage, paramLong);
    } 
  }
  
  public void setupReactContext(final ReactApplicationContext reactContext) {
    List<ReactRootView> list;
    ReactInstanceEventListener[] arrayOfReactInstanceEventListener;
    ReactMarker.logMarker(ReactMarkerConstants.PRE_SETUP_REACT_CONTEXT_END);
    ReactMarker.logMarker(ReactMarkerConstants.SETUP_REACT_CONTEXT_START);
    a.a(0L, "setupReactContext");
    synchronized (this.mAttachedRootViews) {
      synchronized (this.mReactContextLock) {
        this.mCurrentReactContext = (ReactContext)a.b(reactContext);
        this.mCurrentReactContext.setDegradeExceptionhandle(this.mDegradeExceptionHandler);
        null = a.b(reactContext.getCatalystInstance());
        null.initialize();
        this.mDevSupportManager.onNewReactContextCreated((ReactContext)reactContext);
        this.mMemoryPressureRouter.addMemoryPressureListener((MemoryPressureListener)null);
        moveReactContextToCurrentLifecycleState();
        ReactMarker.logMarker(ReactMarkerConstants.ATTACH_MEASURED_ROOT_VIEWS_START);
        Iterator<ReactRootView> iterator = this.mAttachedRootViews.iterator();
        while (iterator.hasNext())
          attachRootViewToInstance(iterator.next(), (CatalystInstance)null); 
        ReactMarker.logMarker(ReactMarkerConstants.ATTACH_MEASURED_ROOT_VIEWS_END);
        arrayOfReactInstanceEventListener = new ReactInstanceEventListener[this.mReactInstanceEventListeners.size()];
        UiThreadUtil.runOnUiThread(new Runnable() {
              public void run() {
                ReactInstanceManager.ReactInstanceEventListener[] arrayOfReactInstanceEventListener = finalListeners;
                int j = arrayOfReactInstanceEventListener.length;
                for (int i = 0; i < j; i++)
                  arrayOfReactInstanceEventListener[i].onReactContextInitialized((ReactContext)reactContext); 
              }
            });
        a.a(0L);
        ReactMarker.logMarker(ReactMarkerConstants.SETUP_REACT_CONTEXT_END);
        reactContext.runOnJSQueueThread(new Runnable() {
              public void run() {
                Process.setThreadPriority(0);
              }
            });
        reactContext.runOnNativeModulesQueueThread(new Runnable() {
              public void run() {
                Process.setThreadPriority(0);
              }
            });
        return;
      } 
    } 
  }
  
  public void showDevOptionsDialog() {
    UiThreadUtil.assertOnUiThread();
    this.mDevSupportManager.showDevOptionsDialog();
  }
  
  public void toggleElementInspector() {
    ReactContext reactContext = getCurrentReactContext();
    if (reactContext != null)
      ((DeviceEventManagerModule.RCTDeviceEventEmitter)reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("toggleElementInspector", null); 
  }
  
  static {
    try {
      ReactBridge.staticInit(null);
    } finally {
      Exception exception;
    } 
  }
  
  class ReactContextInitParams {
    private final JSBundleLoader mJsBundleLoader;
    
    private final JavaScriptExecutorFactory mJsExecutorFactory;
    
    public ReactContextInitParams(JavaScriptExecutorFactory param1JavaScriptExecutorFactory, JSBundleLoader param1JSBundleLoader) {
      this.mJsExecutorFactory = (JavaScriptExecutorFactory)a.b(param1JavaScriptExecutorFactory);
      this.mJsBundleLoader = param1JSBundleLoader;
    }
    
    public JSBundleLoader getJsBundleLoader() {
      return this.mJsBundleLoader;
    }
    
    public JavaScriptExecutorFactory getJsExecutorFactory() {
      return this.mJsExecutorFactory;
    }
  }
  
  public static interface ReactInstanceEventListener {
    void onReactContextInitialized(ReactContext param1ReactContext);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\ReactInstanceManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */