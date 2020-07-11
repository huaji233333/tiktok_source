package com.facebook.react.uimanager;

import android.content.ComponentCallbacks;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import com.facebook.common.e.a;
import com.facebook.m.a;
import com.facebook.react.animation.Animation;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.GuardedRunnable;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.OnBatchCompleteListener;
import com.facebook.react.bridge.PerformanceCounter;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.ReactMarkerConstants;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.UIManager;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.common.SizeMonitoringFrameLayout;
import com.facebook.react.uimanager.debug.NotThreadSafeViewHierarchyUpdateDebugListener;
import com.facebook.react.uimanager.events.EventDispatcher;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@ReactModule(name = "UIManager")
public class UIManagerModule extends ReactContextBaseJavaModule implements LifecycleEventListener, OnBatchCompleteListener, PerformanceCounter, UIManager {
  private static final boolean DEBUG = false;
  
  private static Map<String, Object> mModuleConstants;
  
  private int mBatchId = 0;
  
  public final Map<String, Object> mCustomDirectEvents;
  
  private final EventDispatcher mEventDispatcher;
  
  private Handler mLayoutTriggerHandler = null;
  
  private final List<UIManagerModuleListener> mListeners = new ArrayList<UIManagerModuleListener>();
  
  private final MemoryTrimCallback mMemoryTrimCallback = new MemoryTrimCallback();
  
  public final UIImplementation mUIImplementation;
  
  public boolean mWaitForLayout = false;
  
  public UIManagerModule(ReactApplicationContext paramReactApplicationContext, ViewManagerResolver paramViewManagerResolver, UIImplementationProvider paramUIImplementationProvider, int paramInt) {
    super(paramReactApplicationContext);
    DisplayMetricsHolder.initDisplayMetricsIfNotInitialized((Context)paramReactApplicationContext);
    this.mEventDispatcher = new EventDispatcher(paramReactApplicationContext);
    mModuleConstants = createConstants(paramViewManagerResolver);
    this.mCustomDirectEvents = UIManagerModuleConstants.getDirectEventTypeConstants();
    this.mUIImplementation = paramUIImplementationProvider.createUIImplementation(paramReactApplicationContext, paramViewManagerResolver, this.mEventDispatcher, paramInt);
    paramReactApplicationContext.addLifecycleEventListener(this);
  }
  
  public UIManagerModule(ReactApplicationContext paramReactApplicationContext, List<ViewManager> paramList, UIImplementationProvider paramUIImplementationProvider, int paramInt) {
    super(paramReactApplicationContext);
    DisplayMetricsHolder.initDisplayMetricsIfNotInitialized((Context)paramReactApplicationContext);
    this.mEventDispatcher = new EventDispatcher(paramReactApplicationContext);
    this.mCustomDirectEvents = MapBuilder.newHashMap();
    if (mModuleConstants == null)
      mModuleConstants = createConstants(paramList, null, this.mCustomDirectEvents); 
    this.mUIImplementation = paramUIImplementationProvider.createUIImplementation(paramReactApplicationContext, paramList, this.mEventDispatcher, paramInt);
    paramReactApplicationContext.addLifecycleEventListener(this);
  }
  
  private void ReLayoutNextTick() {
    if (this.mLayoutTriggerHandler == null)
      this.mLayoutTriggerHandler = new Handler(); 
    if (this.mWaitForLayout)
      return; 
    this.mLayoutTriggerHandler.postDelayed(new Runnable() {
          public void run() {
            UIManagerModule.this.onBatchComplete();
            UIManagerModule.this.mWaitForLayout = false;
          }
        },  16L);
    this.mWaitForLayout = true;
  }
  
  private static Map<String, Object> createConstants(ViewManagerResolver paramViewManagerResolver) {
    ReactMarker.logMarker(ReactMarkerConstants.CREATE_UI_MANAGER_MODULE_CONSTANTS_START);
    a.a(0L, "CreateUIManagerConstants");
    try {
      return UIManagerModuleConstantsHelper.createConstants(paramViewManagerResolver);
    } finally {
      a.a(0L);
      ReactMarker.logMarker(ReactMarkerConstants.CREATE_UI_MANAGER_MODULE_CONSTANTS_END);
    } 
  }
  
  private static Map<String, Object> createConstants(List<ViewManager> paramList, Map<String, Object> paramMap1, Map<String, Object> paramMap2) {
    ReactMarker.logMarker(ReactMarkerConstants.CREATE_UI_MANAGER_MODULE_CONSTANTS_START);
    a.a(0L, "CreateUIManagerConstants");
    try {
      return UIManagerModuleConstantsHelper.createConstants(paramList, paramMap1, paramMap2);
    } finally {
      a.a(0L);
      ReactMarker.logMarker(ReactMarkerConstants.CREATE_UI_MANAGER_MODULE_CONSTANTS_END);
    } 
  }
  
  public void addAnimation(int paramInt1, int paramInt2, Callback paramCallback) {
    this.mUIImplementation.addAnimation(paramInt1, paramInt2, paramCallback);
  }
  
  public <T extends SizeMonitoringFrameLayout & com.facebook.react.uimanager.common.MeasureSpecProvider> int addRootView(T paramT) {
    // Byte code:
    //   0: lconst_0
    //   1: ldc 'UIManagerModule.addRootView'
    //   3: invokestatic a : (JLjava/lang/String;)V
    //   6: invokestatic getNextRootViewTag : ()I
    //   9: istore_2
    //   10: aload_0
    //   11: invokevirtual getReactApplicationContext : ()Lcom/facebook/react/bridge/ReactApplicationContext;
    //   14: astore_3
    //   15: new com/facebook/react/uimanager/ThemedReactContext
    //   18: dup
    //   19: aload_3
    //   20: aload_1
    //   21: invokevirtual getContext : ()Landroid/content/Context;
    //   24: invokespecial <init> : (Lcom/facebook/react/bridge/ReactApplicationContext;Landroid/content/Context;)V
    //   27: astore #4
    //   29: aload_0
    //   30: getfield mUIImplementation : Lcom/facebook/react/uimanager/UIImplementation;
    //   33: aload_1
    //   34: iload_2
    //   35: aload #4
    //   37: invokevirtual registerRootView : (Lcom/facebook/react/uimanager/common/SizeMonitoringFrameLayout;ILcom/facebook/react/uimanager/ThemedReactContext;)V
    //   40: aload_1
    //   41: new com/facebook/react/uimanager/UIManagerModule$3
    //   44: dup
    //   45: aload_0
    //   46: aload_3
    //   47: iload_2
    //   48: invokespecial <init> : (Lcom/facebook/react/uimanager/UIManagerModule;Lcom/facebook/react/bridge/ReactApplicationContext;I)V
    //   51: invokevirtual setOnSizeChangedListener : (Lcom/facebook/react/uimanager/common/SizeMonitoringFrameLayout$OnSizeChangedListener;)V
    //   54: lconst_0
    //   55: invokestatic a : (J)V
    //   58: iload_2
    //   59: ireturn
  }
  
  public void addUIBlock(UIBlock paramUIBlock) {
    this.mUIImplementation.addUIBlock(paramUIBlock);
  }
  
  public void addUIManagerListener(UIManagerModuleListener paramUIManagerModuleListener) {
    this.mListeners.add(paramUIManagerModuleListener);
  }
  
  @ReactMethod
  public void clearJSResponder() {
    this.mUIImplementation.clearJSResponder();
  }
  
  @ReactMethod
  public void configureNextLayoutAnimation(ReadableMap paramReadableMap, Callback paramCallback1, Callback paramCallback2) {
    this.mUIImplementation.configureNextLayoutAnimation(paramReadableMap, paramCallback1, paramCallback2);
  }
  
  @ReactMethod
  public void createView(int paramInt1, String paramString, int paramInt2, ReadableMap paramReadableMap) {
    if (DEBUG) {
      StringBuilder stringBuilder = new StringBuilder("(UIManager.createView) tag: ");
      stringBuilder.append(paramInt1);
      stringBuilder.append(", class: ");
      stringBuilder.append(paramString);
      stringBuilder.append(", props: ");
      stringBuilder.append(paramReadableMap);
      a.a("ReactNative", stringBuilder.toString());
    } 
    this.mUIImplementation.createView(paramInt1, paramString, paramInt2, paramReadableMap);
  }
  
  @ReactMethod
  public void dispatchViewManagerCommand(int paramInt1, int paramInt2, ReadableArray paramReadableArray) {
    this.mUIImplementation.dispatchViewManagerCommand(paramInt1, paramInt2, paramReadableArray);
  }
  
  @ReactMethod
  public void findSubviewIn(int paramInt, ReadableArray paramReadableArray, Callback paramCallback) {
    this.mUIImplementation.findSubviewIn(paramInt, Math.round(PixelUtil.toPixelFromDIP(paramReadableArray.getDouble(0))), Math.round(PixelUtil.toPixelFromDIP(paramReadableArray.getDouble(1))), paramCallback);
  }
  
  public Map<String, Object> getConstants() {
    return mModuleConstants;
  }
  
  @ReactMethod(isBlockingSynchronousMethod = true)
  public WritableMap getConstantsForViewManager(String paramString) {
    if (paramString != null) {
      ViewManager viewManager = this.mUIImplementation.resolveViewManager(paramString);
    } else {
      paramString = null;
    } 
    if (paramString == null)
      return null; 
    paramString.getName();
    try {
      Map<String, Object> map = UIManagerModuleConstantsHelper.createConstantsForViewManager((ViewManager)paramString, null, null, null, this.mCustomDirectEvents);
      return (WritableMap)((map != null) ? Arguments.makeNativeMap(map) : null);
    } finally {}
  }
  
  @ReactMethod(isBlockingSynchronousMethod = true)
  public WritableMap getDefaultEventTypes() {
    return (WritableMap)Arguments.makeNativeMap(UIManagerModuleConstantsHelper.getDefaultExportableEventTypes());
  }
  
  public CustomEventNamesResolver getDirectEventNamesResolver() {
    return new CustomEventNamesResolver() {
        public String resolveCustomEventName(String param1String) {
          Map map = (Map)UIManagerModule.this.mCustomDirectEvents.get(param1String);
          if (map != null)
            param1String = (String)map.get("registrationName"); 
          return param1String;
        }
      };
  }
  
  public EventDispatcher getEventDispatcher() {
    return this.mEventDispatcher;
  }
  
  public String getName() {
    return "UIManager";
  }
  
  public Map<String, Long> getPerformanceCounters() {
    return this.mUIImplementation.getProfiledBatchPerfCounters();
  }
  
  public UIImplementation getUIImplementation() {
    return this.mUIImplementation;
  }
  
  public void initialize() {
    getReactApplicationContext().registerComponentCallbacks((ComponentCallbacks)this.mMemoryTrimCallback);
  }
  
  public void invalidateNodeLayout(int paramInt) {
    StringBuilder stringBuilder;
    ReactShadowNode reactShadowNode = this.mUIImplementation.resolveShadowNode(paramInt);
    if (reactShadowNode == null) {
      stringBuilder = new StringBuilder("Warning : attempted to dirty a non-existent react shadow node. reactTag=");
      stringBuilder.append(paramInt);
      a.b("ReactNative", stringBuilder.toString());
      return;
    } 
    stringBuilder.dirty();
  }
  
  @ReactMethod
  public void manageChildren(int paramInt, ReadableArray paramReadableArray1, ReadableArray paramReadableArray2, ReadableArray paramReadableArray3, ReadableArray paramReadableArray4, ReadableArray paramReadableArray5) {
    if (DEBUG) {
      StringBuilder stringBuilder = new StringBuilder("(UIManager.manageChildren) tag: ");
      stringBuilder.append(paramInt);
      stringBuilder.append(", moveFrom: ");
      stringBuilder.append(paramReadableArray1);
      stringBuilder.append(", moveTo: ");
      stringBuilder.append(paramReadableArray2);
      stringBuilder.append(", addTags: ");
      stringBuilder.append(paramReadableArray3);
      stringBuilder.append(", atIndices: ");
      stringBuilder.append(paramReadableArray4);
      stringBuilder.append(", removeFrom: ");
      stringBuilder.append(paramReadableArray5);
      a.a("ReactNative", stringBuilder.toString());
    } 
    this.mUIImplementation.manageChildren(paramInt, paramReadableArray1, paramReadableArray2, paramReadableArray3, paramReadableArray4, paramReadableArray5);
    ReLayoutNextTick();
  }
  
  @ReactMethod
  public void measure(int paramInt, Callback paramCallback) {
    this.mUIImplementation.measure(paramInt, paramCallback);
  }
  
  @ReactMethod
  public void measureInWindow(int paramInt, Callback paramCallback) {
    this.mUIImplementation.measureInWindow(paramInt, paramCallback);
  }
  
  @ReactMethod
  public void measureLayout(int paramInt1, int paramInt2, Callback paramCallback1, Callback paramCallback2) {
    this.mUIImplementation.measureLayout(paramInt1, paramInt2, paramCallback1, paramCallback2);
  }
  
  @ReactMethod
  public void measureLayoutRelativeToParent(int paramInt, Callback paramCallback1, Callback paramCallback2) {
    this.mUIImplementation.measureLayoutRelativeToParent(paramInt, paramCallback1, paramCallback2);
  }
  
  public void onBatchComplete() {
    ReactApplicationContext reactApplicationContext = getReactApplicationContext();
    reactApplicationContext.runOnLayoutThread((Runnable)new GuardedRunnable((ReactContext)reactApplicationContext) {
          public void runGuarded() {
            UIManagerModule.this.onBatchCompleteOnLayoutThread();
          }
        });
  }
  
  public void onBatchCompleteOnLayoutThread() {
    int i = this.mBatchId;
    this.mBatchId = i + 1;
    null = this.mListeners.iterator();
    while (null.hasNext())
      ((UIManagerModuleListener)null.next()).willDispatchViewUpdates(this); 
    try {
      this.mUIImplementation.dispatchViewUpdates(i);
      return;
    } finally {
      a.a(0L);
    } 
  }
  
  public void onCatalystInstanceDestroy() {
    super.onCatalystInstanceDestroy();
    this.mEventDispatcher.onCatalystInstanceDestroyed();
    getReactApplicationContext().unregisterComponentCallbacks((ComponentCallbacks)this.mMemoryTrimCallback);
    YogaNodePool.get().clear();
    ViewManagerPropertyUpdater.clear();
  }
  
  public void onHostDestroy() {
    this.mUIImplementation.onHostDestroy();
  }
  
  public void onHostPause() {
    this.mUIImplementation.onHostPause();
  }
  
  public void onHostResume() {
    this.mUIImplementation.onHostResume();
  }
  
  public void prependUIBlock(UIBlock paramUIBlock) {
    this.mUIImplementation.prependUIBlock(paramUIBlock);
  }
  
  public void registerAnimation(Animation paramAnimation) {
    this.mUIImplementation.registerAnimation(paramAnimation);
  }
  
  public void removeAnimation(int paramInt1, int paramInt2) {
    this.mUIImplementation.removeAnimation(paramInt1, paramInt2);
  }
  
  @ReactMethod
  public void removeRootView(int paramInt) {
    this.mUIImplementation.removeRootView(paramInt);
    ReLayoutNextTick();
  }
  
  @ReactMethod
  public void removeSubviewsFromContainerWithID(int paramInt) {
    this.mUIImplementation.removeSubviewsFromContainerWithID(paramInt);
    ReLayoutNextTick();
  }
  
  public void removeUIManagerListener(UIManagerModuleListener paramUIManagerModuleListener) {
    this.mListeners.remove(paramUIManagerModuleListener);
  }
  
  @ReactMethod
  public void replaceExistingNonRootView(int paramInt1, int paramInt2) {
    this.mUIImplementation.replaceExistingNonRootView(paramInt1, paramInt2);
    ReLayoutNextTick();
  }
  
  public int resolveRootTagFromReactTag(int paramInt) {
    return this.mUIImplementation.resolveRootTagFromReactTag(paramInt);
  }
  
  @ReactMethod
  public void sendAccessibilityEvent(int paramInt1, int paramInt2) {
    this.mUIImplementation.sendAccessibilityEvent(paramInt1, paramInt2);
  }
  
  @ReactMethod
  public void setChildren(int paramInt, ReadableArray paramReadableArray) {
    if (DEBUG) {
      StringBuilder stringBuilder = new StringBuilder("(UIManager.setChildren) tag: ");
      stringBuilder.append(paramInt);
      stringBuilder.append(", children: ");
      stringBuilder.append(paramReadableArray);
      a.a("ReactNative", stringBuilder.toString());
    } 
    this.mUIImplementation.setChildren(paramInt, paramReadableArray);
    ReLayoutNextTick();
  }
  
  @ReactMethod
  public void setJSResponder(int paramInt, boolean paramBoolean) {
    this.mUIImplementation.setJSResponder(paramInt, paramBoolean);
  }
  
  @ReactMethod
  public void setLayoutAnimationEnabledExperimental(boolean paramBoolean) {
    this.mUIImplementation.setLayoutAnimationEnabledExperimental(paramBoolean);
  }
  
  public void setViewHierarchyUpdateDebugListener(NotThreadSafeViewHierarchyUpdateDebugListener paramNotThreadSafeViewHierarchyUpdateDebugListener) {
    this.mUIImplementation.setViewHierarchyUpdateDebugListener(paramNotThreadSafeViewHierarchyUpdateDebugListener);
  }
  
  public void setViewLocalData(final int tag, final Object data) {
    ReactApplicationContext reactApplicationContext = getReactApplicationContext();
    reactApplicationContext.assertOnUiQueueThread();
    reactApplicationContext.runOnLayoutThread((Runnable)new GuardedRunnable((ReactContext)reactApplicationContext) {
          public void runGuarded() {
            UIManagerModule.this.mUIImplementation.setViewLocalData(tag, data);
          }
        });
  }
  
  @ReactMethod
  public void showPopupMenu(int paramInt, ReadableArray paramReadableArray, Callback paramCallback1, Callback paramCallback2) {
    this.mUIImplementation.showPopupMenu(paramInt, paramReadableArray, paramCallback1, paramCallback2);
  }
  
  public void updateNodeSize(int paramInt1, int paramInt2, int paramInt3) {
    getReactApplicationContext().assertOnLayoutThread();
    this.mUIImplementation.updateNodeSize(paramInt1, paramInt2, paramInt3);
  }
  
  public void updateRootLayoutSpecs(int paramInt1, int paramInt2, int paramInt3) {
    this.mUIImplementation.updateRootView(paramInt1, paramInt2, paramInt3);
    this.mUIImplementation.dispatchViewUpdates(-1);
  }
  
  @ReactMethod
  public void updateView(int paramInt, String paramString, ReadableMap paramReadableMap) {
    if (DEBUG) {
      StringBuilder stringBuilder = new StringBuilder("(UIManager.updateView) tag: ");
      stringBuilder.append(paramInt);
      stringBuilder.append(", class: ");
      stringBuilder.append(paramString);
      stringBuilder.append(", props: ");
      stringBuilder.append(paramReadableMap);
      a.a("ReactNative", stringBuilder.toString());
    } 
    this.mUIImplementation.updateView(paramInt, paramString, paramReadableMap);
    ReLayoutNextTick();
  }
  
  @ReactMethod
  public void viewIsDescendantOf(int paramInt1, int paramInt2, Callback paramCallback) {
    this.mUIImplementation.viewIsDescendantOf(paramInt1, paramInt2, paramCallback);
  }
  
  public static interface CustomEventNamesResolver {
    String resolveCustomEventName(String param1String);
  }
  
  class MemoryTrimCallback implements ComponentCallbacks2 {
    private MemoryTrimCallback() {}
    
    public void onConfigurationChanged(Configuration param1Configuration) {}
    
    public void onLowMemory() {}
    
    public void onTrimMemory(int param1Int) {
      if (param1Int >= 60)
        YogaNodePool.get().clear(); 
    }
  }
  
  public static interface ViewManagerResolver {
    ViewManager getViewManager(String param1String);
    
    List<String> getViewManagerNames();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\uimanager\UIManagerModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */