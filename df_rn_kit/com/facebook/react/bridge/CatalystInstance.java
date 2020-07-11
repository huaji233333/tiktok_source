package com.facebook.react.bridge;

import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.queue.ReactQueueConfiguration;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.List;

public interface CatalystInstance extends JSInstance, MemoryPressureListener {
  void addBridgeIdleDebugListener(NotThreadSafeBridgeIdleDebugListener paramNotThreadSafeBridgeIdleDebugListener);
  
  void addJSIModules(List<JSIModuleHolder> paramList);
  
  void callFunction(String paramString1, String paramString2, NativeArray paramNativeArray);
  
  void destroy();
  
  void extendNativeModules(NativeModuleRegistry paramNativeModuleRegistry);
  
  JSBundleLoader getJSBundleLoader();
  
  <T extends JSIModule> T getJSIModule(Class<T> paramClass);
  
  <T extends JavaScriptModule> T getJSModule(Class<T> paramClass);
  
  JavaScriptContextHolder getJavaScriptContextHolder();
  
  String getMainJsBundlePath();
  
  <T extends NativeModule> T getNativeModule(Class<T> paramClass);
  
  Collection<NativeModule> getNativeModules();
  
  ReactQueueConfiguration getReactQueueConfiguration();
  
  String getSourceURL();
  
  <T extends NativeModule> boolean hasNativeModule(Class<T> paramClass);
  
  boolean hasRunJSBundle();
  
  void initialize();
  
  void invokeCallback(int paramInt, NativeArray paramNativeArray);
  
  void invokeCallbackDirect(long paramLong, NativeArray paramNativeArray);
  
  boolean isDestroyed();
  
  boolean isFirstDrawn();
  
  void loadJavaScript(String paramString1, String paramString2, boolean paramBoolean);
  
  void onPageFinished();
  
  void registerSegment(int paramInt, String paramString);
  
  void removeBridgeIdleDebugListener(NotThreadSafeBridgeIdleDebugListener paramNotThreadSafeBridgeIdleDebugListener);
  
  void runJSBundle();
  
  void setFirstDraw(long paramLong);
  
  void setFirstScreenPaintFinished(long paramLong);
  
  void setGlobalVariable(String paramString1, String paramString2);
  
  void setPageFinishListener(PageFinishedListener paramPageFinishedListener);
  
  void setRootView(WeakReference<ReactRootView> paramWeakReference);
  
  void setStartLoad(long paramLong);
  
  void startFirstDraw();
  
  void updateLayout();
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\CatalystInstance.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */