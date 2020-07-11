package com.facebook.react.bridge;

import android.content.res.AssetManager;
import android.os.Handler;
import com.facebook.common.e.a;
import com.facebook.i.a.a;
import com.facebook.jni.HybridData;
import com.facebook.m.b;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.queue.MessageQueueThread;
import com.facebook.react.bridge.queue.MessageQueueThreadImpl;
import com.facebook.react.bridge.queue.QueueThreadExceptionHandler;
import com.facebook.react.bridge.queue.ReactQueueConfiguration;
import com.facebook.react.bridge.queue.ReactQueueConfigurationImpl;
import com.facebook.react.bridge.queue.ReactQueueConfigurationSpec;
import com.facebook.react.util.RNVersionUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONObject;

public class CatalystInstanceImpl implements CatalystInstance {
  private static final AtomicInteger sNextInstanceIdForTrace = new AtomicInteger(1);
  
  private volatile boolean mAcceptCalls;
  
  public final CopyOnWriteArrayList<NotThreadSafeBridgeIdleDebugListener> mBridgeIdleListeners;
  
  public volatile long mCurPageFinishedTime;
  
  private volatile boolean mDestroyed;
  
  private long mFirstDrawTime;
  
  private boolean mFirstDrawn;
  
  private long mFirstScreenTime;
  
  public final HybridData mHybridData;
  
  private boolean mInitialized;
  
  private boolean mJSBundleHasLoaded;
  
  private final JSBundleLoader mJSBundleLoader;
  
  private final ArrayList<PendingJSCall> mJSCallsPendingInit;
  
  private final Object mJSCallsPendingInitLock;
  
  private final JSIModuleRegistry mJSIModuleRegistry;
  
  private final JavaScriptModuleRegistry mJSModuleRegistry;
  
  public JavaScriptContextHolder mJavaScriptContextHolder;
  
  private final String mJsPendingCallsTitleForTrace;
  
  private String mMainJsBudlePath;
  
  private final NativeModuleCallExceptionHandler mNativeModuleCallExceptionHandler;
  
  public final NativeModuleRegistry mNativeModuleRegistry;
  
  private final MessageQueueThread mNativeModulesQueueThread;
  
  private volatile long mPageFinishedTime;
  
  private long mPageStartTime;
  
  public final AtomicInteger mPendingJSCalls;
  
  public int mPerfDelayCout;
  
  private PageFinishedListener mPerfListener;
  
  public long mPreviousPageTime;
  
  private final ReactQueueConfigurationImpl mReactQueueConfiguration;
  
  private WeakReference<ReactRootView> mRootViewWeakReference;
  
  private String mSourceURL;
  
  private long mStartLoadTime;
  
  private TimingEventListener mTimingEventListener;
  
  private final b mTraceListener;
  
  public boolean mUpdateLayoutStarted;
  
  private CatalystInstanceImpl(ReactQueueConfigurationSpec paramReactQueueConfigurationSpec, JavaScriptExecutor paramJavaScriptExecutor, NativeModuleRegistry paramNativeModuleRegistry, JSBundleLoader paramJSBundleLoader, NativeModuleCallExceptionHandler paramNativeModuleCallExceptionHandler) {
    int i;
    this.mPendingJSCalls = new AtomicInteger(0);
    StringBuilder stringBuilder = new StringBuilder("pending_js_calls_instance");
    stringBuilder.append(sNextInstanceIdForTrace.getAndIncrement());
    this.mJsPendingCallsTitleForTrace = stringBuilder.toString();
    this.mDestroyed = false;
    this.mJSCallsPendingInit = new ArrayList<PendingJSCall>();
    this.mJSCallsPendingInitLock = new Object();
    this.mJSIModuleRegistry = new JSIModuleRegistry();
    this.mInitialized = false;
    this.mAcceptCalls = false;
    this.mFirstDrawn = true;
    this.mUpdateLayoutStarted = false;
    this.mPerfDelayCout = 1;
    this.mPageStartTime = 0L;
    this.mPageFinishedTime = 0L;
    this.mCurPageFinishedTime = 0L;
    this.mPreviousPageTime = 0L;
    this.mFirstScreenTime = 0L;
    this.mFirstDrawTime = 0L;
    this.mStartLoadTime = 0L;
    setPageStartTime(System.currentTimeMillis());
    this.mPerfDelayCout = 1;
    this.mHybridData = initHybrid();
    this.mReactQueueConfiguration = ReactQueueConfigurationImpl.create(paramReactQueueConfigurationSpec, new NativeExceptionHandler());
    this.mBridgeIdleListeners = new CopyOnWriteArrayList<NotThreadSafeBridgeIdleDebugListener>();
    this.mNativeModuleRegistry = paramNativeModuleRegistry;
    this.mJSModuleRegistry = new JavaScriptModuleRegistry();
    this.mJSBundleLoader = paramJSBundleLoader;
    this.mNativeModuleCallExceptionHandler = paramNativeModuleCallExceptionHandler;
    this.mNativeModulesQueueThread = this.mReactQueueConfiguration.getNativeModulesQueueThread();
    this.mTraceListener = new JSProfilerTraceListener(this);
    RNJavaScriptRuntime.SplitCommonType splitCommonType = useCommonSplit(paramJavaScriptExecutor, paramJSBundleLoader);
    if (splitCommonType == null) {
      i = 0;
    } else {
      i = splitCommonType.ordinal();
    } 
    initializeBridgeCommonJsf(new BridgeCallback(this), paramJavaScriptExecutor, this.mReactQueueConfiguration.getJSQueueThread(), this.mNativeModulesQueueThread, ((MessageQueueThreadImpl)this.mReactQueueConfiguration.getLayoutThread()).getLooper().getThread(), this.mNativeModuleRegistry.getJavaModules(this), this.mNativeModuleRegistry.getCxxModules(), this.mNativeModuleRegistry.getCoreModules(), i, RNJavaScriptRuntime.getAssetManager(), RNJavaScriptRuntime.getCommonURL(), RNJavaScriptRuntime.getBaseJsURL(), RNJavaScriptRuntime.getSnapshotURL());
    this.mJavaScriptContextHolder = new JavaScriptContextHolder(getJavaScriptContext());
  }
  
  private native long getJavaScriptContext();
  
  private static native HybridData initHybrid();
  
  private native void initializeBridgeCommonJsf(ReactCallback paramReactCallback, JavaScriptExecutor paramJavaScriptExecutor, MessageQueueThread paramMessageQueueThread1, MessageQueueThread paramMessageQueueThread2, Thread paramThread, Collection<JavaModuleWrapper> paramCollection, Collection<ModuleHolder> paramCollection1, Collection<String> paramCollection2, int paramInt, AssetManager paramAssetManager, String paramString1, String paramString2, String paramString3);
  
  private native void jniCallJSCallback(int paramInt, NativeArray paramNativeArray);
  
  private native void jniCallJSCallbackDirect(long paramLong, NativeArray paramNativeArray);
  
  private native void jniExtendNativeModules(Collection<JavaModuleWrapper> paramCollection, Collection<ModuleHolder> paramCollection1);
  
  private native void jniHandleMemoryPressure(int paramInt);
  
  private native void jniLoadScript(String paramString1, String paramString2, boolean paramBoolean);
  
  private native void jniLoadScriptFromAssets(AssetManager paramAssetManager, String paramString, boolean paramBoolean);
  
  private native void jniLoadScriptFromFile(String paramString1, String paramString2, boolean paramBoolean);
  
  private native void jniRegisterSegment(int paramInt, String paramString);
  
  private native void jniSetSourceURL(String paramString);
  
  private void onTimingEventIfExists(String paramString, long paramLong) {
    TimingEventListener timingEventListener = this.mTimingEventListener;
    if (timingEventListener != null)
      timingEventListener.onTimingEvent(paramString, paramLong); 
  }
  
  private void setFirstScreenTime(long paramLong) {
    if (this.mFirstScreenTime == 0L) {
      this.mFirstScreenTime = paramLong;
      onTimingEventIfExists("FirstScreenTime", this.mFirstScreenTime);
    } 
  }
  
  private void setPageStartTime(long paramLong) {
    if (this.mPageStartTime == 0L) {
      this.mPageStartTime = paramLong;
      onTimingEventIfExists("PageStartTime", this.mPageStartTime);
    } 
  }
  
  private RNJavaScriptRuntime.SplitCommonType useCommonSplit(JavaScriptExecutor paramJavaScriptExecutor, JSBundleLoader paramJSBundleLoader) {
    return (paramJSBundleLoader != null) ? paramJSBundleLoader.mUseCommonSplit : ((paramJavaScriptExecutor != null) ? paramJavaScriptExecutor.getCommonSplit() : RNJavaScriptRuntime.SplitCommonType.NONE);
  }
  
  public void addBridgeIdleDebugListener(NotThreadSafeBridgeIdleDebugListener paramNotThreadSafeBridgeIdleDebugListener) {
    this.mBridgeIdleListeners.add(paramNotThreadSafeBridgeIdleDebugListener);
  }
  
  public void addJSIModules(List<JSIModuleHolder> paramList) {
    this.mJSIModuleRegistry.registerModules(paramList);
  }
  
  public void callFunction(PendingJSCall paramPendingJSCall) {
    String str;
    if (this.mDestroyed) {
      str = paramPendingJSCall.toString();
      StringBuilder stringBuilder = new StringBuilder("Calling JS function after bridge has been destroyed: ");
      stringBuilder.append(str);
      a.b("ReactNative", stringBuilder.toString());
      return;
    } 
    if (!this.mAcceptCalls)
      synchronized (this.mJSCallsPendingInitLock) {
        if (!this.mAcceptCalls) {
          this.mJSCallsPendingInit.add(str);
          return;
        } 
      }  
    str.call(this);
  }
  
  public void callFunction(String paramString1, String paramString2, NativeArray paramNativeArray) {
    callFunction(new PendingJSCall(paramString1, paramString2, paramNativeArray));
  }
  
  public void decrementPendingJSCalls() {
    boolean bool;
    if (this.mPendingJSCalls.decrementAndGet() == 0) {
      bool = true;
    } else {
      bool = false;
    } 
    if (bool && !this.mBridgeIdleListeners.isEmpty())
      this.mNativeModulesQueueThread.runOnQueue(new Runnable() {
            public void run() {
              Iterator<NotThreadSafeBridgeIdleDebugListener> iterator = CatalystInstanceImpl.this.mBridgeIdleListeners.iterator();
              while (iterator.hasNext())
                ((NotThreadSafeBridgeIdleDebugListener)iterator.next()).onTransitionToBridgeIdle(); 
            }
          }); 
  }
  
  public void destroy() {
    UiThreadUtil.assertOnUiThread();
    if (this.mDestroyed)
      return; 
    ReactMarker.logMarker(ReactMarkerConstants.DESTROY_CATALYST_INSTANCE_START);
    this.mDestroyed = true;
    this.mNativeModulesQueueThread.runOnQueue(new Runnable() {
          public void run() {
            CatalystInstanceImpl.this.mNativeModuleRegistry.notifyJSInstanceDestroy();
            AtomicInteger atomicInteger = CatalystInstanceImpl.this.mPendingJSCalls;
            boolean bool = false;
            if (atomicInteger.getAndSet(0) == 0)
              bool = true; 
            if (!bool && !CatalystInstanceImpl.this.mBridgeIdleListeners.isEmpty()) {
              Iterator<NotThreadSafeBridgeIdleDebugListener> iterator = CatalystInstanceImpl.this.mBridgeIdleListeners.iterator();
              while (iterator.hasNext())
                ((NotThreadSafeBridgeIdleDebugListener)iterator.next()).onTransitionToBridgeIdle(); 
            } 
            (new Thread() {
                public void run() {
                  CatalystInstanceImpl.this.mJavaScriptContextHolder.clear();
                  CatalystInstanceImpl.this.mHybridData.resetNative();
                  CatalystInstanceImpl.this.getReactQueueConfiguration().destroy();
                  ReactMarker.logMarker(ReactMarkerConstants.DESTROY_CATALYST_INSTANCE_END);
                }
              }).start();
          }
        });
  }
  
  public void extendNativeModules(NativeModuleRegistry paramNativeModuleRegistry) {
    this.mNativeModuleRegistry.registerModules(paramNativeModuleRegistry);
    jniExtendNativeModules(paramNativeModuleRegistry.getJavaModules(this), paramNativeModuleRegistry.getCxxModules());
  }
  
  public long getFirstDrawTime() {
    return this.mFirstDrawTime;
  }
  
  public long getFirstScreenTime() {
    return this.mFirstScreenTime;
  }
  
  public JSBundleLoader getJSBundleLoader() {
    return this.mJSBundleLoader;
  }
  
  public <T extends JSIModule> T getJSIModule(Class<T> paramClass) {
    return this.mJSIModuleRegistry.getModule(paramClass);
  }
  
  public <T extends JavaScriptModule> T getJSModule(Class<T> paramClass) {
    return this.mJSModuleRegistry.getJavaScriptModule(this, paramClass);
  }
  
  public JavaScriptContextHolder getJavaScriptContextHolder() {
    return this.mJavaScriptContextHolder;
  }
  
  public String getMainJsBundlePath() {
    return this.mMainJsBudlePath;
  }
  
  public <T extends NativeModule> T getNativeModule(Class<T> paramClass) {
    return this.mNativeModuleRegistry.getModule(paramClass);
  }
  
  public Collection<NativeModule> getNativeModules() {
    return this.mNativeModuleRegistry.getAllModules();
  }
  
  public long getPageFinishedTime() {
    return this.mPageFinishedTime;
  }
  
  public long getPageStartTime() {
    return this.mPageStartTime;
  }
  
  public ReactQueueConfiguration getReactQueueConfiguration() {
    return (ReactQueueConfiguration)this.mReactQueueConfiguration;
  }
  
  public String getSourceURL() {
    return this.mSourceURL;
  }
  
  public long getStartLoadTime() {
    return this.mStartLoadTime;
  }
  
  public void handleMemoryPressure(int paramInt) {
    if (this.mDestroyed)
      return; 
    jniHandleMemoryPressure(paramInt);
  }
  
  public <T extends NativeModule> boolean hasNativeModule(Class<T> paramClass) {
    return this.mNativeModuleRegistry.hasModule(paramClass);
  }
  
  public boolean hasRunJSBundle() {
    synchronized (this.mJSCallsPendingInitLock) {
      if (this.mJSBundleHasLoaded && this.mAcceptCalls)
        return true; 
    } 
    boolean bool = false;
    /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/lang/Object}, name=SYNTHETIC_LOCAL_VARIABLE_2} */
    return bool;
  }
  
  public void incrementPendingJSCalls() {
    boolean bool;
    if (this.mPendingJSCalls.getAndIncrement() == 0) {
      bool = true;
    } else {
      bool = false;
    } 
    if (bool && !this.mBridgeIdleListeners.isEmpty())
      this.mNativeModulesQueueThread.runOnQueue(new Runnable() {
            public void run() {
              Iterator<NotThreadSafeBridgeIdleDebugListener> iterator = CatalystInstanceImpl.this.mBridgeIdleListeners.iterator();
              while (iterator.hasNext())
                ((NotThreadSafeBridgeIdleDebugListener)iterator.next()).onTransitionToBridgeBusy(); 
            }
          }); 
  }
  
  public void initialize() {
    a.a(this.mInitialized ^ true, "This catalyst instance has already been initialized");
    a.a(this.mAcceptCalls, "RunJSBundle hasn't completed.");
    this.mInitialized = true;
    this.mNativeModulesQueueThread.runOnQueue(new Runnable() {
          public void run() {
            CatalystInstanceImpl.this.mNativeModuleRegistry.notifyJSInstanceInitialized();
          }
        });
  }
  
  public void invokeCallback(int paramInt, NativeArray paramNativeArray) {
    if (this.mDestroyed) {
      a.b("ReactNative", "Invoking JS callback after bridge has been destroyed.");
      return;
    } 
    try {
      jniCallJSCallback(paramInt, paramNativeArray);
      return;
    } catch (Exception exception) {
      return;
    } 
  }
  
  public void invokeCallbackDirect(long paramLong, NativeArray paramNativeArray) {
    try {
      jniCallJSCallbackDirect(paramLong, paramNativeArray);
      return;
    } catch (Exception exception) {
      return;
    } 
  }
  
  public boolean isDestroyed() {
    return this.mDestroyed;
  }
  
  public boolean isFirstDrawn() {
    return this.mFirstDrawn;
  }
  
  public native void jniCallJSFunction(String paramString1, String paramString2, NativeArray paramNativeArray);
  
  public void loadJavaScript(String paramString1, String paramString2, boolean paramBoolean) {
    jniLoadScript(paramString1, paramString2, paramBoolean);
  }
  
  public void loadScriptFromAssets(AssetManager paramAssetManager, String paramString, boolean paramBoolean) {
    this.mSourceURL = paramString;
    jniLoadScriptFromAssets(paramAssetManager, paramString, paramBoolean);
  }
  
  public void loadScriptFromFile(String paramString1, String paramString2, boolean paramBoolean) {
    this.mSourceURL = paramString2;
    jniLoadScriptFromFile(paramString1, paramString2, paramBoolean);
  }
  
  public void loadScriptFromFile(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2) {
    if (paramBoolean2)
      setMainJsBundlePathFromFile(paramString1); 
    this.mSourceURL = paramString2;
    jniLoadScriptFromFile(paramString1, paramString2, paramBoolean1);
  }
  
  public void onNativeException(Exception paramException) {
    this.mNativeModuleCallExceptionHandler.handleException(paramException);
    this.mReactQueueConfiguration.getUIQueueThread().runOnQueue(new Runnable() {
          public void run() {
            CatalystInstanceImpl.this.destroy();
          }
        });
  }
  
  public void onPageFinished() {
    if (this.mFirstScreenTime == 0L)
      setFirstScreenTime(this.mPageFinishedTime); 
    uploadPageFinishPerf();
  }
  
  public void registerSegment(int paramInt, String paramString) {
    jniRegisterSegment(paramInt, paramString);
  }
  
  public void removeBridgeIdleDebugListener(NotThreadSafeBridgeIdleDebugListener paramNotThreadSafeBridgeIdleDebugListener) {
    this.mBridgeIdleListeners.remove(paramNotThreadSafeBridgeIdleDebugListener);
  }
  
  public void runJSBundle() {
    a.a(this.mJSBundleHasLoaded ^ true, "JS bundle was already loaded!");
    JSBundleLoader jSBundleLoader = this.mJSBundleLoader;
    if (jSBundleLoader != null)
      jSBundleLoader.loadScript(this); 
    synchronized (this.mJSCallsPendingInitLock) {
      this.mAcceptCalls = true;
      Iterator<PendingJSCall> iterator = this.mJSCallsPendingInit.iterator();
      while (iterator.hasNext())
        ((PendingJSCall)iterator.next()).call(this); 
      this.mJSCallsPendingInit.clear();
      this.mJSBundleHasLoaded = true;
      return;
    } 
  }
  
  public void setFirstDraw(long paramLong) {
    if (this.mFirstDrawTime == 0L) {
      this.mFirstDrawTime = paramLong;
      onTimingEventIfExists("FirstDrawTime", this.mFirstDrawTime);
    } 
  }
  
  public void setFirstScreenPaintFinished(long paramLong) {
    setFirstScreenTime(paramLong);
  }
  
  public native void setGlobalVariable(String paramString1, String paramString2);
  
  public void setMainJsBundlePath(String paramString) {
    this.mMainJsBudlePath = paramString;
  }
  
  public void setMainJsBundlePathFromFile(String paramString) {
    if (paramString != null) {
      int i = paramString.lastIndexOf("/");
      if (i > 0)
        setMainJsBundlePath(paramString.substring(0, i + 1)); 
    } 
  }
  
  public void setPageFinishListener(PageFinishedListener paramPageFinishedListener) {
    if (paramPageFinishedListener != null)
      this.mPerfListener = paramPageFinishedListener; 
  }
  
  public void setPageFinishedTime(long paramLong) {
    if (this.mPageFinishedTime == 0L) {
      this.mPageFinishedTime = paramLong;
      onTimingEventIfExists("PageFinishTime", this.mPageFinishedTime);
    } 
  }
  
  public void setRootView(WeakReference<ReactRootView> paramWeakReference) {
    this.mRootViewWeakReference = paramWeakReference;
  }
  
  void setSourceURLs(String paramString1, String paramString2) {
    this.mSourceURL = paramString1;
    jniSetSourceURL(paramString2);
  }
  
  public void setStartLoad(long paramLong) {
    if (this.mStartLoadTime == 0L) {
      this.mStartLoadTime = paramLong;
      onTimingEventIfExists("StartLoadTime", this.mStartLoadTime);
    } 
  }
  
  public void setTimingEventsListener(TimingEventListener paramTimingEventListener) {
    if (paramTimingEventListener != null) {
      this.mTimingEventListener = paramTimingEventListener;
      long l = this.mPageStartTime;
      if (l > 0L)
        this.mTimingEventListener.onTimingEvent("PageStartTime", l); 
      l = this.mStartLoadTime;
      if (l > 0L)
        this.mTimingEventListener.onTimingEvent("StartLoadTime", l); 
      l = this.mFirstDrawTime;
      if (l > 0L)
        this.mTimingEventListener.onTimingEvent("FirstDrawTime", l); 
      l = this.mFirstScreenTime;
      if (l > 0L)
        this.mTimingEventListener.onTimingEvent("FirstScreenTime", l); 
      if (this.mPageFinishedTime > 0L)
        this.mTimingEventListener.onTimingEvent("PageFinishTime", this.mPageFinishedTime); 
    } 
  }
  
  public void startFirstDraw() {
    this.mFirstDrawn = false;
  }
  
  public void updateLayout() {
    this.mNativeModulesQueueThread.runOnQueue(new Runnable() {
          public void run() {
            CatalystInstanceImpl.this.mCurPageFinishedTime = System.currentTimeMillis();
            CatalystInstanceImpl catalystInstanceImpl = CatalystInstanceImpl.this;
            catalystInstanceImpl.setPageFinishedTime(catalystInstanceImpl.mCurPageFinishedTime);
            if (!CatalystInstanceImpl.this.mUpdateLayoutStarted) {
              final Handler mPageFinishedHandler = new Handler();
              CatalystInstanceImpl.this.mUpdateLayoutStarted = true;
              handler.postDelayed(new Runnable() {
                    public void run() {
                      if (CatalystInstanceImpl.this.mCurPageFinishedTime == CatalystInstanceImpl.this.mPreviousPageTime) {
                        CatalystInstanceImpl.this.onPageFinished();
                        mPageFinishedHandler.removeCallbacks(this);
                        return;
                      } 
                      CatalystInstanceImpl.this.mPreviousPageTime = CatalystInstanceImpl.this.mCurPageFinishedTime;
                      mPageFinishedHandler.postDelayed(this, (CatalystInstanceImpl.this.mPerfDelayCout * 50));
                      CatalystInstanceImpl catalystInstanceImpl = CatalystInstanceImpl.this;
                      catalystInstanceImpl.mPerfDelayCout++;
                    }
                  }50L);
            } 
          }
        });
  }
  
  public void uploadPageFinishPerf() {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("Version", RNVersionUtils.getVersion());
      jSONObject.put("url", this.mSourceURL);
      jSONObject.put("StartLoadTime", this.mStartLoadTime);
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mPageStartTime);
      jSONObject.put("PageStartTime", stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append(this.mFirstDrawTime);
      jSONObject.put("FirstDrawTime", stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append(this.mFirstScreenTime);
      jSONObject.put("FirstScreenTime", stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append(this.mPageFinishedTime);
      jSONObject.put("PageFinishedTime", stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append(this.mFirstDrawTime - this.mPageStartTime);
      jSONObject.put("PageFirstDraw", stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append(this.mFirstScreenTime - this.mPageStartTime);
      jSONObject.put("PageFirstScreen", stringBuilder.toString());
      stringBuilder = new StringBuilder();
      stringBuilder.append(this.mPageFinishedTime - this.mPageStartTime);
      jSONObject.put("PageFinish", stringBuilder.toString());
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    ReactBridge.uploadPageFinishPerf(jSONObject);
    PageFinishedListener pageFinishedListener = this.mPerfListener;
    if (pageFinishedListener != null)
      pageFinishedListener.upLoad(jSONObject); 
  }
  
  static {
    try {
      ReactBridge.staticInit(null);
    } finally {
      Exception exception;
    } 
  }
  
  static class BridgeCallback implements ReactCallback {
    private final WeakReference<CatalystInstanceImpl> mOuter;
    
    BridgeCallback(CatalystInstanceImpl param1CatalystInstanceImpl) {
      this.mOuter = new WeakReference<CatalystInstanceImpl>(param1CatalystInstanceImpl);
    }
    
    public void decrementPendingJSCalls() {
      CatalystInstanceImpl catalystInstanceImpl = this.mOuter.get();
      if (catalystInstanceImpl != null)
        catalystInstanceImpl.decrementPendingJSCalls(); 
    }
    
    public void incrementPendingJSCalls() {
      CatalystInstanceImpl catalystInstanceImpl = this.mOuter.get();
      if (catalystInstanceImpl != null)
        catalystInstanceImpl.incrementPendingJSCalls(); 
    }
    
    public void onBatchComplete() {
      CatalystInstanceImpl catalystInstanceImpl = this.mOuter.get();
      if (catalystInstanceImpl != null)
        catalystInstanceImpl.mNativeModuleRegistry.onBatchComplete(); 
    }
  }
  
  public static class Builder {
    private JSBundleLoader mJSBundleLoader;
    
    private JavaScriptExecutor mJSExecutor;
    
    private NativeModuleCallExceptionHandler mNativeModuleCallExceptionHandler;
    
    private ReactQueueConfigurationSpec mReactQueueConfigurationSpec;
    
    private NativeModuleRegistry mRegistry;
    
    public CatalystInstanceImpl build() {
      return new CatalystInstanceImpl((ReactQueueConfigurationSpec)a.b(this.mReactQueueConfigurationSpec), (JavaScriptExecutor)a.b(this.mJSExecutor), (NativeModuleRegistry)a.b(this.mRegistry), this.mJSBundleLoader, (NativeModuleCallExceptionHandler)a.b(this.mNativeModuleCallExceptionHandler));
    }
    
    public Builder setJSBundleLoader(JSBundleLoader param1JSBundleLoader) {
      this.mJSBundleLoader = param1JSBundleLoader;
      return this;
    }
    
    public Builder setJSExecutor(JavaScriptExecutor param1JavaScriptExecutor) {
      this.mJSExecutor = param1JavaScriptExecutor;
      return this;
    }
    
    public Builder setNativeModuleCallExceptionHandler(NativeModuleCallExceptionHandler param1NativeModuleCallExceptionHandler) {
      this.mNativeModuleCallExceptionHandler = param1NativeModuleCallExceptionHandler;
      return this;
    }
    
    public Builder setReactQueueConfigurationSpec(ReactQueueConfigurationSpec param1ReactQueueConfigurationSpec) {
      this.mReactQueueConfigurationSpec = param1ReactQueueConfigurationSpec;
      return this;
    }
    
    public Builder setRegistry(NativeModuleRegistry param1NativeModuleRegistry) {
      this.mRegistry = param1NativeModuleRegistry;
      return this;
    }
  }
  
  static class JSProfilerTraceListener implements b {
    private final WeakReference<CatalystInstanceImpl> mOuter;
    
    public JSProfilerTraceListener(CatalystInstanceImpl param1CatalystInstanceImpl) {
      this.mOuter = new WeakReference<CatalystInstanceImpl>(param1CatalystInstanceImpl);
    }
    
    public void onTraceStarted() {
      CatalystInstanceImpl catalystInstanceImpl = this.mOuter.get();
      if (catalystInstanceImpl != null)
        ((Systrace)catalystInstanceImpl.<Systrace>getJSModule(Systrace.class)).setEnabled(true); 
    }
    
    public void onTraceStopped() {
      CatalystInstanceImpl catalystInstanceImpl = this.mOuter.get();
      if (catalystInstanceImpl != null)
        ((Systrace)catalystInstanceImpl.<Systrace>getJSModule(Systrace.class)).setEnabled(false); 
    }
  }
  
  class NativeExceptionHandler implements QueueThreadExceptionHandler {
    private NativeExceptionHandler() {}
    
    public void handleException(Exception param1Exception) {
      CatalystInstanceImpl.this.onNativeException(param1Exception);
    }
  }
  
  public static class PendingJSCall {
    public NativeArray mArguments;
    
    public String mMethod;
    
    public String mModule;
    
    public PendingJSCall(String param1String1, String param1String2, NativeArray param1NativeArray) {
      this.mModule = param1String1;
      this.mMethod = param1String2;
      this.mArguments = param1NativeArray;
    }
    
    void call(CatalystInstanceImpl param1CatalystInstanceImpl) {
      NativeArray nativeArray = this.mArguments;
      if (nativeArray == null)
        nativeArray = new WritableNativeArray(); 
      param1CatalystInstanceImpl.jniCallJSFunction(this.mModule, this.mMethod, nativeArray);
    }
    
    public String toString() {
      String str;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.mModule);
      stringBuilder.append(".");
      stringBuilder.append(this.mMethod);
      stringBuilder.append("(");
      NativeArray nativeArray = this.mArguments;
      if (nativeArray == null) {
        str = "";
      } else {
        str = str.toString();
      } 
      stringBuilder.append(str);
      stringBuilder.append(")");
      return stringBuilder.toString();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\CatalystInstanceImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */