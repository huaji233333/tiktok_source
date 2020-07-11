package com.facebook.react.bridge;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import com.facebook.i.a.a;
import com.facebook.react.bridge.queue.MessageQueueThread;
import com.facebook.react.bridge.queue.ReactQueueConfiguration;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.util.RNVersionUtils;
import java.lang.ref.WeakReference;
import java.util.concurrent.CopyOnWriteArraySet;
import org.json.JSONObject;

public class ReactContext extends ContextWrapper {
  private final CopyOnWriteArraySet<ActivityEventListener> mActivityEventListeners = new CopyOnWriteArraySet<ActivityEventListener>();
  
  private CatalystInstance mCatalystInstance;
  
  private WeakReference<Activity> mCurrentActivity;
  
  private RNDegradeExceptionHandler mDegradeExceptionhandle;
  
  private LayoutInflater mInflater;
  
  private MessageQueueThread mJSMessageQueueThread;
  
  private MessageQueueThread mLayoutThread;
  
  public final CopyOnWriteArraySet<LifecycleEventListener> mLifecycleEventListeners = new CopyOnWriteArraySet<LifecycleEventListener>();
  
  private LifecycleState mLifecycleState = LifecycleState.BEFORE_CREATE;
  
  private NativeModuleCallExceptionHandler mNativeModuleCallExceptionHandler;
  
  private MessageQueueThread mNativeModulesMessageQueueThread;
  
  private boolean mRemoteDebug = false;
  
  private MessageQueueThread mUiMessageQueueThread;
  
  public ReactContext(Context paramContext) {
    super(paramContext);
  }
  
  public void addActivityEventListener(ActivityEventListener paramActivityEventListener) {
    this.mActivityEventListeners.add(paramActivityEventListener);
  }
  
  public void addLifecycleEventListener(final LifecycleEventListener listener) {
    this.mLifecycleEventListeners.add(listener);
    if (hasActiveCatalystInstance()) {
      int i = null.$SwitchMap$com$facebook$react$common$LifecycleState[this.mLifecycleState.ordinal()];
      if (i != 1 && i != 2) {
        if (i == 3) {
          runOnUiQueueThread(new Runnable() {
                public void run() {
                  if (!ReactContext.this.mLifecycleEventListeners.contains(listener))
                    return; 
                  try {
                    listener.onHostResume();
                    return;
                  } catch (RuntimeException runtimeException) {
                    ReactContext.this.handleException(runtimeException);
                    return;
                  } 
                }
              });
          return;
        } 
        throw new RuntimeException("Unhandled lifecycle state.");
      } 
    } 
  }
  
  public void assertOnJSQueueThread() {
    ((MessageQueueThread)a.b(this.mJSMessageQueueThread)).assertIsOnThread();
  }
  
  public void assertOnLayoutThread() {
    if (this.mRemoteDebug) {
      ((MessageQueueThread)a.b(this.mNativeModulesMessageQueueThread)).assertIsOnThread();
      return;
    } 
    ((MessageQueueThread)a.b(this.mLayoutThread)).assertIsOnThread();
  }
  
  public void assertOnNativeModulesQueueThread() {
    ((MessageQueueThread)a.b(this.mNativeModulesMessageQueueThread)).assertIsOnThread();
  }
  
  public void assertOnNativeModulesQueueThread(String paramString) {
    ((MessageQueueThread)a.b(this.mNativeModulesMessageQueueThread)).assertIsOnThread(paramString);
  }
  
  public void assertOnUiQueueThread() {
    ((MessageQueueThread)a.b(this.mUiMessageQueueThread)).assertIsOnThread();
  }
  
  public void destroy() {
    UiThreadUtil.assertOnUiThread();
    CatalystInstance catalystInstance = this.mCatalystInstance;
    if (catalystInstance != null)
      catalystInstance.destroy(); 
  }
  
  public CatalystInstance getCatalystInstance() {
    return (CatalystInstance)a.b(this.mCatalystInstance);
  }
  
  public Activity getCurrentActivity() {
    WeakReference<Activity> weakReference = this.mCurrentActivity;
    return (weakReference == null) ? null : weakReference.get();
  }
  
  public <T extends JavaScriptModule> T getJSModule(Class<T> paramClass) {
    CatalystInstance catalystInstance = this.mCatalystInstance;
    if (catalystInstance != null)
      return catalystInstance.getJSModule(paramClass); 
    throw new RuntimeException("Tried to access a JS module before the React instance was fully set up. Calls to ReactContext#getJSModule should only happen once initialize() has been called on your native module.");
  }
  
  public JavaScriptContextHolder getJavaScriptContextHolder() {
    return this.mCatalystInstance.getJavaScriptContextHolder();
  }
  
  public LifecycleState getLifecycleState() {
    return this.mLifecycleState;
  }
  
  public <T extends NativeModule> T getNativeModule(Class<T> paramClass) {
    CatalystInstance catalystInstance = this.mCatalystInstance;
    if (catalystInstance != null)
      return catalystInstance.getNativeModule(paramClass); 
    throw new RuntimeException("Trying to call native module before CatalystInstance has been set!");
  }
  
  public Object getSystemService(String paramString) {
    if ("layout_inflater".equals(paramString)) {
      if (this.mInflater == null)
        this.mInflater = LayoutInflater.from(getBaseContext()).cloneInContext((Context)this); 
      return this.mInflater;
    } 
    return getBaseContext().getSystemService(paramString);
  }
  
  public void handleException(Exception paramException) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("Version", RNVersionUtils.getVersion());
      jSONObject.put("NativeExceptionTitle", "ReactContextHandleException");
      jSONObject.put("NativeExceptionStack", paramException.toString());
      ReactBridge.uploadJSException(jSONObject);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    CatalystInstance catalystInstance = this.mCatalystInstance;
    if (catalystInstance != null && !catalystInstance.isDestroyed()) {
      NativeModuleCallExceptionHandler nativeModuleCallExceptionHandler = this.mNativeModuleCallExceptionHandler;
      if (nativeModuleCallExceptionHandler != null)
        nativeModuleCallExceptionHandler.handleException(paramException); 
    } 
    RNDegradeExceptionHandler rNDegradeExceptionHandler = this.mDegradeExceptionhandle;
    if (rNDegradeExceptionHandler != null)
      rNDegradeExceptionHandler.onDegrade(paramException); 
  }
  
  public boolean hasActiveCatalystInstance() {
    CatalystInstance catalystInstance = this.mCatalystInstance;
    return (catalystInstance != null && !catalystInstance.isDestroyed());
  }
  
  public boolean hasCurrentActivity() {
    WeakReference<Activity> weakReference = this.mCurrentActivity;
    return (weakReference != null && weakReference.get() != null);
  }
  
  public <T extends NativeModule> boolean hasNativeModule(Class<T> paramClass) {
    CatalystInstance catalystInstance = this.mCatalystInstance;
    if (catalystInstance != null)
      return catalystInstance.hasNativeModule(paramClass); 
    throw new RuntimeException("Trying to call native module before CatalystInstance has been set!");
  }
  
  public void initializeWithInstance(CatalystInstance paramCatalystInstance) {
    if (paramCatalystInstance != null) {
      if (this.mCatalystInstance == null) {
        this.mCatalystInstance = paramCatalystInstance;
        ReactQueueConfiguration reactQueueConfiguration = paramCatalystInstance.getReactQueueConfiguration();
        this.mUiMessageQueueThread = reactQueueConfiguration.getUIQueueThread();
        this.mNativeModulesMessageQueueThread = reactQueueConfiguration.getNativeModulesQueueThread();
        this.mJSMessageQueueThread = reactQueueConfiguration.getJSQueueThread();
        this.mLayoutThread = reactQueueConfiguration.getLayoutThread();
        return;
      } 
      throw new IllegalStateException("ReactContext has been already initialized");
    } 
    throw new IllegalArgumentException("CatalystInstance cannot be null.");
  }
  
  public boolean isOnJSQueueThread() {
    return ((MessageQueueThread)a.b(this.mJSMessageQueueThread)).isOnThread();
  }
  
  public boolean isOnNativeModulesQueueThread() {
    return ((MessageQueueThread)a.b(this.mNativeModulesMessageQueueThread)).isOnThread();
  }
  
  public boolean isOnUiQueueThread() {
    return ((MessageQueueThread)a.b(this.mUiMessageQueueThread)).isOnThread();
  }
  
  public void onActivityResult(Activity paramActivity, int paramInt1, int paramInt2, Intent paramIntent) {
    for (ActivityEventListener activityEventListener : this.mActivityEventListeners) {
      try {
        activityEventListener.onActivityResult(paramActivity, paramInt1, paramInt2, paramIntent);
      } catch (RuntimeException runtimeException) {
        handleException(runtimeException);
      } 
    } 
  }
  
  public void onHostDestroy() {
    UiThreadUtil.assertOnUiThread();
    this.mLifecycleState = LifecycleState.BEFORE_CREATE;
    for (LifecycleEventListener lifecycleEventListener : this.mLifecycleEventListeners) {
      try {
        lifecycleEventListener.onHostDestroy();
      } catch (RuntimeException runtimeException) {
        handleException(runtimeException);
      } 
    } 
    this.mCurrentActivity = null;
  }
  
  public void onHostPause() {
    this.mLifecycleState = LifecycleState.BEFORE_RESUME;
    ReactMarker.logMarker(ReactMarkerConstants.ON_HOST_PAUSE_START);
    for (LifecycleEventListener lifecycleEventListener : this.mLifecycleEventListeners) {
      try {
        lifecycleEventListener.onHostPause();
      } catch (RuntimeException runtimeException) {
        handleException(runtimeException);
      } 
    } 
    ReactMarker.logMarker(ReactMarkerConstants.ON_HOST_PAUSE_END);
  }
  
  public void onHostResume(Activity paramActivity) {
    this.mLifecycleState = LifecycleState.RESUMED;
    this.mCurrentActivity = new WeakReference<Activity>(paramActivity);
    ReactMarker.logMarker(ReactMarkerConstants.ON_HOST_RESUME_START);
    for (LifecycleEventListener lifecycleEventListener : this.mLifecycleEventListeners) {
      try {
        lifecycleEventListener.onHostResume();
      } catch (RuntimeException runtimeException) {
        handleException(runtimeException);
      } 
    } 
    ReactMarker.logMarker(ReactMarkerConstants.ON_HOST_RESUME_END);
  }
  
  public void onNewIntent(Activity paramActivity, Intent paramIntent) {
    UiThreadUtil.assertOnUiThread();
    this.mCurrentActivity = new WeakReference<Activity>(paramActivity);
    for (ActivityEventListener activityEventListener : this.mActivityEventListeners) {
      try {
        activityEventListener.onNewIntent(paramIntent);
      } catch (RuntimeException runtimeException) {
        handleException(runtimeException);
      } 
    } 
  }
  
  public void removeActivityEventListener(ActivityEventListener paramActivityEventListener) {
    this.mActivityEventListeners.remove(paramActivityEventListener);
  }
  
  public void removeLifecycleEventListener(LifecycleEventListener paramLifecycleEventListener) {
    this.mLifecycleEventListeners.remove(paramLifecycleEventListener);
  }
  
  public void runOnJSQueueThread(Runnable paramRunnable) {
    ((MessageQueueThread)a.b(this.mJSMessageQueueThread)).runOnQueue(paramRunnable);
  }
  
  public void runOnLayoutThread(Runnable paramRunnable) {
    if (this.mRemoteDebug) {
      ((MessageQueueThread)a.b(this.mNativeModulesMessageQueueThread)).runOnQueue(paramRunnable);
      return;
    } 
    ((MessageQueueThread)a.b(this.mLayoutThread)).runOnQueue(paramRunnable);
  }
  
  public void runOnNativeModulesQueueThread(Runnable paramRunnable) {
    ((MessageQueueThread)a.b(this.mNativeModulesMessageQueueThread)).runOnQueue(paramRunnable);
  }
  
  public void runOnUiQueueThread(Runnable paramRunnable) {
    ((MessageQueueThread)a.b(this.mUiMessageQueueThread)).runOnQueue(paramRunnable);
  }
  
  public void setDegradeExceptionhandle(RNDegradeExceptionHandler paramRNDegradeExceptionHandler) {
    this.mDegradeExceptionhandle = paramRNDegradeExceptionHandler;
  }
  
  public void setNativeModuleCallExceptionHandler(NativeModuleCallExceptionHandler paramNativeModuleCallExceptionHandler) {
    this.mNativeModuleCallExceptionHandler = paramNativeModuleCallExceptionHandler;
  }
  
  public void setRemoteDebug(boolean paramBoolean) {
    this.mRemoteDebug = paramBoolean;
  }
  
  public boolean startActivityForResult(Intent paramIntent, int paramInt, Bundle paramBundle) {
    Activity activity = getCurrentActivity();
    a.b(activity);
    activity.startActivityForResult(paramIntent, paramInt, paramBundle);
    return true;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\ReactContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */