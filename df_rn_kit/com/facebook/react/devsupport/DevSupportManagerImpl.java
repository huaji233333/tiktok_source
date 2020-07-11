package com.facebook.react.devsupport;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.a;
import com.facebook.common.e.a;
import com.facebook.i.a.a;
import com.facebook.react.bridge.DefaultNativeModuleCallExceptionHandler;
import com.facebook.react.bridge.JavaJSExecutor;
import com.facebook.react.bridge.JavaScriptContextHolder;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.ReactMarkerConstants;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.common.DebugServerException;
import com.facebook.react.common.ShakeDetector;
import com.facebook.react.common.futures.SimpleSettableFuture;
import com.facebook.react.devsupport.interfaces.DevBundleDownloadListener;
import com.facebook.react.devsupport.interfaces.DevOptionHandler;
import com.facebook.react.devsupport.interfaces.DevSupportManager;
import com.facebook.react.devsupport.interfaces.ErrorCustomizer;
import com.facebook.react.devsupport.interfaces.PackagerStatusCallback;
import com.facebook.react.devsupport.interfaces.StackFrame;
import com.facebook.react.modules.debug.interfaces.DeveloperSettings;
import com.facebook.react.packagerconnection.RequestHandler;
import com.facebook.react.packagerconnection.Responder;
import com.facebook.react.uimanager.IllegalViewOperationException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import okhttp3.ac;
import okhttp3.ad;
import okhttp3.w;
import okhttp3.y;

public class DevSupportManagerImpl implements DevInternalSettings.Listener, DevServerHelper.PackagerCommandListener, DevSupportManager {
  public final Context mApplicationContext;
  
  public DevBundleDownloadListener mBundleDownloadListener;
  
  public InspectorPackagerConnection.BundleStatus mBundleStatus;
  
  public ReactContext mCurrentContext;
  
  private final LinkedHashMap<String, DevOptionHandler> mCustomDevOptions = new LinkedHashMap<String, DevOptionHandler>();
  
  private DebugOverlayController mDebugOverlayController;
  
  private final DefaultNativeModuleCallExceptionHandler mDefaultNativeModuleCallExceptionHandler;
  
  public final DevLoadingViewController mDevLoadingViewController;
  
  public boolean mDevLoadingViewVisible = false;
  
  public AlertDialog mDevOptionsDialog;
  
  public final DevServerHelper mDevServerHelper;
  
  public DevInternalSettings mDevSettings;
  
  private List<ErrorCustomizer> mErrorCustomizers;
  
  private final List<ExceptionLogger> mExceptionLoggers = new ArrayList<ExceptionLogger>();
  
  private boolean mIsDevSupportEnabled = false;
  
  private boolean mIsReceiverRegistered = false;
  
  private boolean mIsShakeDetectorStarted = false;
  
  private final String mJSAppBundleName;
  
  private final File mJSBundleTempFile;
  
  public int mLastErrorCookie = 0;
  
  private StackFrame[] mLastErrorStack;
  
  private String mLastErrorTitle;
  
  private ErrorType mLastErrorType;
  
  public final ReactInstanceManagerDevHelper mReactInstanceManagerHelper;
  
  public RedBoxDialog mRedBoxDialog;
  
  public RedBoxHandler mRedBoxHandler;
  
  private final BroadcastReceiver mReloadAppBroadcastReceiver;
  
  private final ShakeDetector mShakeDetector;
  
  public DevSupportManagerImpl(Context paramContext, ReactInstanceManagerDevHelper paramReactInstanceManagerDevHelper, String paramString, boolean paramBoolean, int paramInt) {
    this(paramContext, paramReactInstanceManagerDevHelper, paramString, paramBoolean, null, null, paramInt);
  }
  
  public DevSupportManagerImpl(Context paramContext, ReactInstanceManagerDevHelper paramReactInstanceManagerDevHelper, String paramString, boolean paramBoolean, RedBoxHandler paramRedBoxHandler, DevBundleDownloadListener paramDevBundleDownloadListener, int paramInt) {
    this.mReactInstanceManagerHelper = paramReactInstanceManagerDevHelper;
    this.mApplicationContext = paramContext;
    this.mJSAppBundleName = paramString;
    this.mDevSettings = new DevInternalSettings(paramContext, (DevInternalSettings.Listener)this);
    this.mBundleStatus = new InspectorPackagerConnection.BundleStatus();
    this.mDevServerHelper = new DevServerHelper(this.mDevSettings, this.mApplicationContext.getPackageName(), new InspectorPackagerConnection.BundleStatusProvider() {
          public InspectorPackagerConnection.BundleStatus getBundleStatus() {
            return DevSupportManagerImpl.this.mBundleStatus;
          }
        });
    this.mBundleDownloadListener = paramDevBundleDownloadListener;
    this.mShakeDetector = new ShakeDetector(new ShakeDetector.ShakeListener() {
          public void onShake() {
            DevSupportManagerImpl.this.showDevOptionsDialog();
          }
        },  paramInt);
    this.mReloadAppBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context param1Context, Intent param1Intent) {
          String str = param1Intent.getAction();
          if (DevSupportManagerImpl.getReloadAppAction(param1Context).equals(str)) {
            if (param1Intent.getBooleanExtra("jsproxy", false)) {
              DevSupportManagerImpl.this.mDevSettings.setRemoteJSDebugEnabled(true);
              DevSupportManagerImpl.this.mDevServerHelper.launchJSDevtools();
            } else {
              DevSupportManagerImpl.this.mDevSettings.setRemoteJSDebugEnabled(false);
            } 
            DevSupportManagerImpl.this.handleReloadJS();
          } 
        }
      };
    this.mJSBundleTempFile = new File(paramContext.getFilesDir(), "ReactNativeDevBundle.js");
    this.mDefaultNativeModuleCallExceptionHandler = new DefaultNativeModuleCallExceptionHandler();
    setDevSupportEnabled(paramBoolean);
    this.mRedBoxHandler = paramRedBoxHandler;
    this.mDevLoadingViewController = new DevLoadingViewController(paramContext, paramReactInstanceManagerDevHelper);
    this.mExceptionLoggers.add(new JSExceptionLogger());
    this.mExceptionLoggers.add(new StackOverflowExceptionLogger());
  }
  
  public static String getReloadAppAction(Context paramContext) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramContext.getPackageName());
    stringBuilder.append(".RELOAD_APP_ACTION");
    return stringBuilder.toString();
  }
  
  private void hideDevOptionsDialog() {
    AlertDialog alertDialog = this.mDevOptionsDialog;
    if (alertDialog != null) {
      alertDialog.dismiss();
      this.mDevOptionsDialog = null;
    } 
  }
  
  private void reloadJSInProxyMode() {
    this.mDevServerHelper.launchJSDevtools();
    JavaJSExecutor.Factory factory = new JavaJSExecutor.Factory() {
        public JavaJSExecutor create() throws Exception {
          WebsocketJavaScriptExecutor websocketJavaScriptExecutor = new WebsocketJavaScriptExecutor();
          SimpleSettableFuture<Boolean> simpleSettableFuture = new SimpleSettableFuture();
          websocketJavaScriptExecutor.connect(DevSupportManagerImpl.this.mDevServerHelper.getWebsocketProxyURL(), DevSupportManagerImpl.this.getExecutorConnectCallback(simpleSettableFuture));
          try {
            simpleSettableFuture.get(90L, TimeUnit.SECONDS);
            return websocketJavaScriptExecutor;
          } catch (ExecutionException executionException) {
            throw (Exception)executionException.getCause();
          } catch (InterruptedException interruptedException) {
            throw new RuntimeException(interruptedException);
          } catch (TimeoutException timeoutException) {
            throw new RuntimeException(timeoutException);
          } 
        }
      };
    this.mReactInstanceManagerHelper.onReloadWithJSDebugger(factory);
  }
  
  private void resetCurrentContext(ReactContext paramReactContext) {
    if (this.mCurrentContext == paramReactContext)
      return; 
    this.mCurrentContext = paramReactContext;
    DebugOverlayController debugOverlayController = this.mDebugOverlayController;
    if (debugOverlayController != null)
      debugOverlayController.setFpsDebugViewVisible(false); 
    if (paramReactContext != null)
      this.mDebugOverlayController = new DebugOverlayController(paramReactContext); 
    if (this.mDevSettings.isHotModuleReplacementEnabled() && this.mCurrentContext != null)
      try {
        URL uRL = new URL(getSourceUrl());
        String str1 = uRL.getPath().substring(1);
        String str2 = uRL.getHost();
        int i = uRL.getPort();
        ((HMRClient)this.mCurrentContext.getJSModule(HMRClient.class)).enable("android", str1, str2, i);
      } catch (MalformedURLException malformedURLException) {
        showNewJavaError(malformedURLException.getMessage(), malformedURLException);
      }  
    reloadSettings();
  }
  
  public void addCustomDevOption(String paramString, DevOptionHandler paramDevOptionHandler) {
    this.mCustomDevOptions.put(paramString, paramDevOptionHandler);
  }
  
  public void deleteJSBundleFile() {
    if (this.mJSBundleTempFile.exists())
      this.mJSBundleTempFile.delete(); 
  }
  
  public File downloadBundleResourceFromUrlSync(String paramString, File paramFile) {
    return this.mDevServerHelper.downloadBundleResourceFromUrlSync(paramString, paramFile);
  }
  
  public DeveloperSettings getDevSettings() {
    return this.mDevSettings;
  }
  
  public boolean getDevSupportEnabled() {
    return this.mIsDevSupportEnabled;
  }
  
  public String getDownloadedJSBundleFile() {
    return this.mJSBundleTempFile.getAbsolutePath();
  }
  
  public WebsocketJavaScriptExecutor.JSExecutorConnectCallback getExecutorConnectCallback(final SimpleSettableFuture<Boolean> future) {
    return new WebsocketJavaScriptExecutor.JSExecutorConnectCallback() {
        public void onFailure(Throwable param1Throwable) {
          DevSupportManagerImpl.this.mDevLoadingViewController.hide();
          DevSupportManagerImpl.this.mDevLoadingViewVisible = false;
          a.c("ReactNative", "Unable to connect to remote debugger", param1Throwable);
          future.setException(new IOException(DevSupportManagerImpl.this.mApplicationContext.getString(1980104724), param1Throwable));
        }
        
        public void onSuccess() {
          future.set(Boolean.valueOf(true));
          DevSupportManagerImpl.this.mDevLoadingViewController.hide();
          DevSupportManagerImpl.this.mDevLoadingViewVisible = false;
        }
      };
  }
  
  public String getJSBundleURLForRemoteDebugging() {
    return this.mDevServerHelper.getJSBundleURLForRemoteDebugging((String)a.b(this.mJSAppBundleName));
  }
  
  public StackFrame[] getLastErrorStack() {
    return this.mLastErrorStack;
  }
  
  public String getLastErrorTitle() {
    return this.mLastErrorTitle;
  }
  
  public String getSourceMapUrl() {
    String str = this.mJSAppBundleName;
    return (str == null) ? "" : this.mDevServerHelper.getSourceMapUrl((String)a.b(str));
  }
  
  public String getSourceUrl() {
    String str = this.mJSAppBundleName;
    return (str == null) ? "" : this.mDevServerHelper.getSourceUrl((String)a.b(str));
  }
  
  public void handleCaptureHeap(final Responder responder) {
    ReactContext reactContext = this.mCurrentContext;
    if (reactContext == null)
      return; 
    ((JSCHeapCapture)reactContext.getNativeModule(JSCHeapCapture.class)).captureHeap(this.mApplicationContext.getCacheDir().getPath(), new JSCHeapCapture.CaptureCallback() {
          public void onFailure(JSCHeapCapture.CaptureException param1CaptureException) {
            responder.error(param1CaptureException.toString());
          }
          
          public void onSuccess(File param1File) {
            responder.respond(param1File.toString());
          }
        });
  }
  
  public void handleException(Exception paramException) {
    if (this.mIsDevSupportEnabled) {
      Iterator<ExceptionLogger> iterator = this.mExceptionLoggers.iterator();
      while (iterator.hasNext())
        ((ExceptionLogger)iterator.next()).log(paramException); 
      return;
    } 
    this.mDefaultNativeModuleCallExceptionHandler.handleException(paramException);
  }
  
  public void handlePokeSamplingProfiler() {
    try {
      Iterator<String> iterator = JSCSamplingProfiler.poke(60000L).iterator();
      while (true) {
        if (iterator.hasNext()) {
          String str1;
          String str2 = iterator.next();
          ReactContext reactContext = this.mCurrentContext;
          if (str2 == null) {
            str1 = "Started JSC Sampling Profiler";
          } else {
            str1 = "Stopped JSC Sampling Profiler";
          } 
          _lancet.com_ss_android_ugc_aweme_lancet_DesignBugFixLancet_show(Toast.makeText((Context)reactContext, str1, 1));
          (new JscProfileTask(getSourceUrl())).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new String[] { str2 });
          continue;
        } 
        return;
      } 
    } catch (ProfilerException profilerException) {
      showNewJavaError(profilerException.getMessage(), profilerException);
      return;
    } 
  }
  
  public void handleReloadJS() {
    UiThreadUtil.assertOnUiThread();
    ReactMarker.logMarker(ReactMarkerConstants.RELOAD, this.mDevSettings.getPackagerConnectionSettings().getDebugServerHost());
    hideRedboxDialog();
    if (this.mDevSettings.isRemoteJSDebugEnabled()) {
      this.mDevLoadingViewController.showForRemoteJSEnabled();
      this.mDevLoadingViewVisible = true;
      reloadJSInProxyMode();
      return;
    } 
    reloadJSFromServer(this.mDevServerHelper.getDevServerBundleURL((String)a.b(this.mJSAppBundleName)));
  }
  
  public boolean hasBundleInAssets(String paramString) {
    try {
      String[] arrayOfString = this.mApplicationContext.getAssets().list("");
      for (int i = 0; i < arrayOfString.length; i++) {
        boolean bool = arrayOfString[i].equals(paramString);
        if (bool)
          return true; 
      } 
    } catch (IOException iOException) {
      a.c("ReactNative", "Error while loading assets list");
    } 
    return false;
  }
  
  public boolean hasUpToDateJSBundleInCache() {
    if (this.mIsDevSupportEnabled && this.mJSBundleTempFile.exists())
      try {
        String str = this.mApplicationContext.getPackageName();
        PackageInfo packageInfo = this.mApplicationContext.getPackageManager().getPackageInfo(str, 0);
        if (this.mJSBundleTempFile.lastModified() > packageInfo.lastUpdateTime) {
          File file = new File(a.a(Locale.US, "/data/local/tmp/exopackage/%s//secondary-dex", new Object[] { str }));
          if (file.exists()) {
            long l1 = this.mJSBundleTempFile.lastModified();
            long l2 = file.lastModified();
            return (l1 > l2);
          } 
          return true;
        } 
      } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
        a.c("ReactNative", "DevSupport is unable to get current app info");
      }  
    return false;
  }
  
  public void hideRedboxDialog() {
    RedBoxDialog redBoxDialog = this.mRedBoxDialog;
    if (redBoxDialog != null) {
      redBoxDialog.dismiss();
      this.mRedBoxDialog = null;
    } 
  }
  
  public void isPackagerRunning(PackagerStatusCallback paramPackagerStatusCallback) {
    this.mDevServerHelper.isPackagerRunning(paramPackagerStatusCallback);
  }
  
  public void onCaptureHeapCommand(final Responder responder) {
    UiThreadUtil.runOnUiThread(new Runnable() {
          public void run() {
            DevSupportManagerImpl.this.handleCaptureHeap(responder);
          }
        });
  }
  
  public void onInternalSettingsChanged() {
    reloadSettings();
  }
  
  public void onNewReactContextCreated(ReactContext paramReactContext) {
    resetCurrentContext(paramReactContext);
  }
  
  public void onPackagerConnected() {}
  
  public void onPackagerDevMenuCommand() {
    UiThreadUtil.runOnUiThread(new Runnable() {
          public void run() {
            DevSupportManagerImpl.this.showDevOptionsDialog();
          }
        });
  }
  
  public void onPackagerDisconnected() {}
  
  public void onPackagerReloadCommand() {
    this.mDevServerHelper.disableDebugger();
    UiThreadUtil.runOnUiThread(new Runnable() {
          public void run() {
            DevSupportManagerImpl.this.handleReloadJS();
          }
        });
  }
  
  public void onPokeSamplingProfilerCommand(final Responder responder) {
    UiThreadUtil.runOnUiThread(new Runnable() {
          public void run() {
            if (DevSupportManagerImpl.this.mCurrentContext == null) {
              responder.error("JSCContext is missing, unable to profile");
              return;
            } 
            try {
              synchronized (DevSupportManagerImpl.this.mCurrentContext.getJavaScriptContextHolder()) {
                ((RequestHandler)Class.forName("com.facebook.react.packagerconnection.SamplingProfilerPackagerMethod").getConstructor(new Class[] { long.class }).newInstance(new Object[] { Long.valueOf(null.get()) })).onRequest(null, responder);
                return;
              } 
            } catch (Exception exception) {
              return;
            } 
          }
        });
  }
  
  public void onReactInstanceDestroyed(ReactContext paramReactContext) {
    if (paramReactContext == this.mCurrentContext)
      resetCurrentContext(null); 
  }
  
  public Pair<String, StackFrame[]> processErrorCustomizers(Pair<String, StackFrame[]> paramPair) {
    List<ErrorCustomizer> list = this.mErrorCustomizers;
    if (list == null)
      return paramPair; 
    Iterator<ErrorCustomizer> iterator = list.iterator();
    while (iterator.hasNext()) {
      Pair<String, StackFrame[]> pair = ((ErrorCustomizer)iterator.next()).customizeErrorInfo(paramPair);
      if (pair != null)
        paramPair = pair; 
    } 
    return paramPair;
  }
  
  public void registerErrorCustomizer(ErrorCustomizer paramErrorCustomizer) {
    if (this.mErrorCustomizers == null)
      this.mErrorCustomizers = new ArrayList<ErrorCustomizer>(); 
    this.mErrorCustomizers.add(paramErrorCustomizer);
  }
  
  public void reload() {
    UiThreadUtil.assertOnUiThread();
    if (this.mIsDevSupportEnabled) {
      DebugOverlayController debugOverlayController1 = this.mDebugOverlayController;
      if (debugOverlayController1 != null)
        debugOverlayController1.setFpsDebugViewVisible(this.mDevSettings.isFpsDebugEnabled()); 
      if (!this.mIsShakeDetectorStarted) {
        this.mShakeDetector.start((SensorManager)this.mApplicationContext.getSystemService("sensor"));
        this.mIsShakeDetectorStarted = true;
      } 
      if (!this.mIsReceiverRegistered) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(getReloadAppAction(this.mApplicationContext));
        this.mApplicationContext.registerReceiver(this.mReloadAppBroadcastReceiver, intentFilter);
        this.mIsReceiverRegistered = true;
      } 
      if (this.mDevLoadingViewVisible)
        this.mDevLoadingViewController.show(); 
      this.mDevServerHelper.openPackagerConnection(getClass().getSimpleName(), (DevServerHelper.PackagerCommandListener)this);
      if (this.mDevSettings.isReloadOnJSChangeEnabled()) {
        this.mDevServerHelper.startPollingOnChangeEndpoint(new DevServerHelper.OnServerContentChangeListener() {
              public void onServerContentChanged() {
                DevSupportManagerImpl.this.handleReloadJS();
              }
            });
        return;
      } 
      this.mDevServerHelper.stopPollingOnChangeEndpoint();
      return;
    } 
    DebugOverlayController debugOverlayController = this.mDebugOverlayController;
    if (debugOverlayController != null)
      debugOverlayController.setFpsDebugViewVisible(false); 
    if (this.mIsShakeDetectorStarted) {
      this.mShakeDetector.stop();
      this.mIsShakeDetectorStarted = false;
    } 
    if (this.mIsReceiverRegistered) {
      this.mApplicationContext.unregisterReceiver(this.mReloadAppBroadcastReceiver);
      this.mIsReceiverRegistered = false;
    } 
    hideRedboxDialog();
    hideDevOptionsDialog();
    this.mDevLoadingViewController.hide();
    this.mDevServerHelper.closePackagerConnection();
    this.mDevServerHelper.stopPollingOnChangeEndpoint();
  }
  
  public void reloadJSFromServer(String paramString) {
    ReactMarker.logMarker(ReactMarkerConstants.DOWNLOAD_START);
    this.mDevLoadingViewController.showForUrl(paramString);
    this.mDevLoadingViewVisible = true;
    final BundleDownloader.BundleInfo bundleInfo = new BundleDownloader.BundleInfo();
    this.mDevServerHelper.downloadBundleFromURL(new DevBundleDownloadListener() {
          public void onFailure(Exception param1Exception) {
            // Byte code:
            //   0: aload_0
            //   1: getfield this$0 : Lcom/facebook/react/devsupport/DevSupportManagerImpl;
            //   4: getfield mDevLoadingViewController : Lcom/facebook/react/devsupport/DevLoadingViewController;
            //   7: invokevirtual hide : ()V
            //   10: aload_0
            //   11: getfield this$0 : Lcom/facebook/react/devsupport/DevSupportManagerImpl;
            //   14: astore_2
            //   15: aload_2
            //   16: iconst_0
            //   17: putfield mDevLoadingViewVisible : Z
            //   20: aload_2
            //   21: monitorenter
            //   22: aload_0
            //   23: getfield this$0 : Lcom/facebook/react/devsupport/DevSupportManagerImpl;
            //   26: getfield mBundleStatus : Lcom/facebook/react/devsupport/InspectorPackagerConnection$BundleStatus;
            //   29: iconst_0
            //   30: invokestatic valueOf : (Z)Ljava/lang/Boolean;
            //   33: putfield isLastDownloadSucess : Ljava/lang/Boolean;
            //   36: aload_2
            //   37: monitorexit
            //   38: aload_0
            //   39: getfield this$0 : Lcom/facebook/react/devsupport/DevSupportManagerImpl;
            //   42: getfield mBundleDownloadListener : Lcom/facebook/react/devsupport/interfaces/DevBundleDownloadListener;
            //   45: ifnull -> 61
            //   48: aload_0
            //   49: getfield this$0 : Lcom/facebook/react/devsupport/DevSupportManagerImpl;
            //   52: getfield mBundleDownloadListener : Lcom/facebook/react/devsupport/interfaces/DevBundleDownloadListener;
            //   55: aload_1
            //   56: invokeinterface onFailure : (Ljava/lang/Exception;)V
            //   61: ldc 'ReactNative'
            //   63: ldc 'Unable to download JS bundle'
            //   65: aload_1
            //   66: invokestatic c : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
            //   69: new com/facebook/react/devsupport/DevSupportManagerImpl$25$2
            //   72: dup
            //   73: aload_0
            //   74: aload_1
            //   75: invokespecial <init> : (Lcom/facebook/react/devsupport/DevSupportManagerImpl$25;Ljava/lang/Exception;)V
            //   78: invokestatic runOnUiThread : (Ljava/lang/Runnable;)V
            //   81: return
            //   82: astore_1
            //   83: aload_2
            //   84: monitorexit
            //   85: aload_1
            //   86: athrow
            // Exception table:
            //   from	to	target	type
            //   22	38	82	finally
            //   83	85	82	finally
          }
          
          public void onProgress(String param1String, Integer param1Integer1, Integer param1Integer2) {
            DevSupportManagerImpl.this.mDevLoadingViewController.updateProgress(param1String, param1Integer1, param1Integer2);
            if (DevSupportManagerImpl.this.mBundleDownloadListener != null)
              DevSupportManagerImpl.this.mBundleDownloadListener.onProgress(param1String, param1Integer1, param1Integer2); 
          }
          
          public void onSuccess() {
            // Byte code:
            //   0: aload_0
            //   1: getfield this$0 : Lcom/facebook/react/devsupport/DevSupportManagerImpl;
            //   4: getfield mDevLoadingViewController : Lcom/facebook/react/devsupport/DevLoadingViewController;
            //   7: invokevirtual hide : ()V
            //   10: aload_0
            //   11: getfield this$0 : Lcom/facebook/react/devsupport/DevSupportManagerImpl;
            //   14: astore_1
            //   15: aload_1
            //   16: iconst_0
            //   17: putfield mDevLoadingViewVisible : Z
            //   20: aload_1
            //   21: monitorenter
            //   22: aload_0
            //   23: getfield this$0 : Lcom/facebook/react/devsupport/DevSupportManagerImpl;
            //   26: getfield mBundleStatus : Lcom/facebook/react/devsupport/InspectorPackagerConnection$BundleStatus;
            //   29: iconst_1
            //   30: invokestatic valueOf : (Z)Ljava/lang/Boolean;
            //   33: putfield isLastDownloadSucess : Ljava/lang/Boolean;
            //   36: aload_0
            //   37: getfield this$0 : Lcom/facebook/react/devsupport/DevSupportManagerImpl;
            //   40: getfield mBundleStatus : Lcom/facebook/react/devsupport/InspectorPackagerConnection$BundleStatus;
            //   43: invokestatic currentTimeMillis : ()J
            //   46: putfield updateTimestamp : J
            //   49: aload_1
            //   50: monitorexit
            //   51: aload_0
            //   52: getfield this$0 : Lcom/facebook/react/devsupport/DevSupportManagerImpl;
            //   55: getfield mBundleDownloadListener : Lcom/facebook/react/devsupport/interfaces/DevBundleDownloadListener;
            //   58: ifnull -> 73
            //   61: aload_0
            //   62: getfield this$0 : Lcom/facebook/react/devsupport/DevSupportManagerImpl;
            //   65: getfield mBundleDownloadListener : Lcom/facebook/react/devsupport/interfaces/DevBundleDownloadListener;
            //   68: invokeinterface onSuccess : ()V
            //   73: new com/facebook/react/devsupport/DevSupportManagerImpl$25$1
            //   76: dup
            //   77: aload_0
            //   78: invokespecial <init> : (Lcom/facebook/react/devsupport/DevSupportManagerImpl$25;)V
            //   81: invokestatic runOnUiThread : (Ljava/lang/Runnable;)V
            //   84: return
            //   85: astore_2
            //   86: aload_1
            //   87: monitorexit
            //   88: aload_2
            //   89: athrow
            // Exception table:
            //   from	to	target	type
            //   22	51	85	finally
            //   86	88	85	finally
          }
        }this.mJSBundleTempFile, paramString, bundleInfo);
  }
  
  public void reloadSettings() {
    if (UiThreadUtil.isOnUiThread()) {
      reload();
      return;
    } 
    UiThreadUtil.runOnUiThread(new Runnable() {
          public void run() {
            DevSupportManagerImpl.this.reload();
          }
        });
  }
  
  public void setDevSupportEnabled(boolean paramBoolean) {
    this.mIsDevSupportEnabled = paramBoolean;
    reloadSettings();
  }
  
  public void showDevOptionsDialog() {
    if (this.mDevOptionsDialog == null && this.mIsDevSupportEnabled) {
      String str1;
      if (ActivityManager.isUserAMonkey())
        return; 
      LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<Object, Object>();
      linkedHashMap.put(this.mApplicationContext.getString(1980104723), new DevOptionHandler() {
            public void onOptionSelected() {
              DevSupportManagerImpl.this.handleReloadJS();
            }
          });
      if (this.mDevSettings.isNuclideJSDebugEnabled()) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mApplicationContext.getString(1980104707));
        stringBuilder.append(" 💯");
        linkedHashMap.put(stringBuilder.toString(), new DevOptionHandler() {
              public void onOptionSelected() {
                DevSupportManagerImpl.this.mDevServerHelper.attachDebugger(DevSupportManagerImpl.this.mApplicationContext, "ReactNative");
              }
            });
      } 
      if (this.mDevSettings.isRemoteJSDebugEnabled()) {
        str1 = this.mApplicationContext.getString(1980104709);
      } else {
        str1 = this.mApplicationContext.getString(1980104706);
      } 
      String str2 = str1;
      if (this.mDevSettings.isNuclideJSDebugEnabled()) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str1);
        stringBuilder.append(" 🙅");
        str2 = stringBuilder.toString();
      } 
      linkedHashMap.put(str2, new DevOptionHandler() {
            public void onOptionSelected() {
              DevSupportManagerImpl.this.mDevSettings.setRemoteJSDebugEnabled(DevSupportManagerImpl.this.mDevSettings.isRemoteJSDebugEnabled() ^ true);
              DevSupportManagerImpl.this.handleReloadJS();
            }
          });
      if (this.mDevSettings.isReloadOnJSChangeEnabled()) {
        str1 = this.mApplicationContext.getString(1980104717);
      } else {
        str1 = this.mApplicationContext.getString(1980104716);
      } 
      linkedHashMap.put(str1, new DevOptionHandler() {
            public void onOptionSelected() {
              DevSupportManagerImpl.this.mDevSettings.setReloadOnJSChangeEnabled(DevSupportManagerImpl.this.mDevSettings.isReloadOnJSChangeEnabled() ^ true);
            }
          });
      if (this.mDevSettings.isHotModuleReplacementEnabled()) {
        str1 = this.mApplicationContext.getString(1980104714);
      } else {
        str1 = this.mApplicationContext.getString(1980104713);
      } 
      linkedHashMap.put(str1, new DevOptionHandler() {
            public void onOptionSelected() {
              DevSupportManagerImpl.this.mDevSettings.setHotModuleReplacementEnabled(DevSupportManagerImpl.this.mDevSettings.isHotModuleReplacementEnabled() ^ true);
              DevSupportManagerImpl.this.handleReloadJS();
            }
          });
      linkedHashMap.put(this.mApplicationContext.getString(1980104711), new DevOptionHandler() {
            public void onOptionSelected() {
              DevSupportManagerImpl.this.mDevSettings.setElementInspectorEnabled(DevSupportManagerImpl.this.mDevSettings.isElementInspectorEnabled() ^ true);
              DevSupportManagerImpl.this.mReactInstanceManagerHelper.toggleElementInspector();
            }
          });
      if (this.mDevSettings.isFpsDebugEnabled()) {
        str1 = this.mApplicationContext.getString(1980104720);
      } else {
        str1 = this.mApplicationContext.getString(1980104719);
      } 
      linkedHashMap.put(str1, new DevOptionHandler() {
            public void onOptionSelected() {
              if (!DevSupportManagerImpl.this.mDevSettings.isFpsDebugEnabled()) {
                Activity activity = DevSupportManagerImpl.this.mReactInstanceManagerHelper.getCurrentActivity();
                if (activity == null) {
                  a.c("ReactNative", "Unable to get reference to react activity");
                } else {
                  DebugOverlayController.requestPermission((Context)activity);
                } 
              } 
              DevSupportManagerImpl.this.mDevSettings.setFpsDebugEnabled(DevSupportManagerImpl.this.mDevSettings.isFpsDebugEnabled() ^ true);
            }
          });
      linkedHashMap.put(this.mApplicationContext.getString(1980104721), new DevOptionHandler() {
            public void onOptionSelected() {
              DevSupportManagerImpl.this.handlePokeSamplingProfiler();
            }
          });
      linkedHashMap.put(this.mApplicationContext.getString(1980104727), new DevOptionHandler() {
            public void onOptionSelected() {
              Intent intent = new Intent(DevSupportManagerImpl.this.mApplicationContext, DevSettingsActivity.class);
              intent.setFlags(268435456);
              DevSupportManagerImpl.this.mApplicationContext.startActivity(intent);
            }
          });
      if (this.mCustomDevOptions.size() > 0)
        linkedHashMap.putAll(this.mCustomDevOptions); 
      final DevOptionHandler[] optionHandlers = (DevOptionHandler[])linkedHashMap.values().toArray((Object[])new DevOptionHandler[0]);
      Activity activity = this.mReactInstanceManagerHelper.getCurrentActivity();
      if (activity == null || activity.isFinishing()) {
        a.c("ReactNative", "Unable to launch dev options menu because react activity isn't available");
        return;
      } 
      this.mDevOptionsDialog = (new AlertDialog.Builder((Context)activity)).setItems((CharSequence[])linkedHashMap.keySet().toArray((Object[])new String[0]), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface param1DialogInterface, int param1Int) {
              optionHandlers[param1Int].onOptionSelected();
              DevSupportManagerImpl.this.mDevOptionsDialog = null;
            }
          }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface param1DialogInterface) {
              DevSupportManagerImpl.this.mDevOptionsDialog = null;
            }
          }).create();
      this.mDevOptionsDialog.show();
      return;
    } 
  }
  
  public void showNewError(final String message, final StackFrame[] stack, final int errorCookie, final ErrorType errorType) {
    UiThreadUtil.runOnUiThread(new Runnable() {
          public void run() {
            if (DevSupportManagerImpl.this.mRedBoxDialog == null) {
              StringBuilder stringBuilder;
              Activity activity = DevSupportManagerImpl.this.mReactInstanceManagerHelper.getCurrentActivity();
              if (activity == null || activity.isFinishing()) {
                stringBuilder = new StringBuilder("Unable to launch redbox because react activity is not available, here is the error that redbox would've displayed: ");
                stringBuilder.append(message);
                a.c("ReactNative", stringBuilder.toString());
                return;
              } 
              DevSupportManagerImpl devSupportManagerImpl = DevSupportManagerImpl.this;
              devSupportManagerImpl.mRedBoxDialog = new RedBoxDialog((Context)stringBuilder, (DevSupportManager)devSupportManagerImpl, devSupportManagerImpl.mRedBoxHandler);
            } 
            if (DevSupportManagerImpl.this.mRedBoxDialog.isShowing())
              return; 
            Pair<String, StackFrame[]> pair = DevSupportManagerImpl.this.processErrorCustomizers(Pair.create(message, stack));
            DevSupportManagerImpl.this.mRedBoxDialog.setExceptionDetails((String)pair.first, (StackFrame[])pair.second);
            DevSupportManagerImpl.this.updateLastErrorInfo(message, stack, errorCookie, errorType);
            if (DevSupportManagerImpl.this.mRedBoxHandler != null && errorType == DevSupportManagerImpl.ErrorType.NATIVE)
              DevSupportManagerImpl.this.mRedBoxHandler.handleRedbox(message, stack, RedBoxHandler.ErrorType.NATIVE); 
            DevSupportManagerImpl.this.mRedBoxDialog.resetReporting();
            DevSupportManagerImpl.this.mRedBoxDialog.show();
          }
        });
  }
  
  public void showNewJSError(String paramString, ReadableArray paramReadableArray, int paramInt) {
    showNewError(paramString, StackTraceHelper.convertJsStackTrace(paramReadableArray), paramInt, ErrorType.JS);
  }
  
  public void showNewJavaError(String paramString, Throwable paramThrowable) {
    a.c("ReactNative", "Exception in native call", paramThrowable);
    showNewError(paramString, StackTraceHelper.convertJavaStackTrace(paramThrowable), -1, ErrorType.NATIVE);
  }
  
  public void startInspector() {
    if (this.mIsDevSupportEnabled)
      this.mDevServerHelper.openInspectorConnection(); 
  }
  
  public void stopInspector() {
    this.mDevServerHelper.closeInspectorConnection();
  }
  
  public void updateJSError(final String message, final ReadableArray details, final int errorCookie) {
    UiThreadUtil.runOnUiThread(new Runnable() {
          public void run() {
            if (DevSupportManagerImpl.this.mRedBoxDialog != null && DevSupportManagerImpl.this.mRedBoxDialog.isShowing()) {
              if (errorCookie != DevSupportManagerImpl.this.mLastErrorCookie)
                return; 
              StackFrame[] arrayOfStackFrame = StackTraceHelper.convertJsStackTrace(details);
              Pair<String, StackFrame[]> pair = DevSupportManagerImpl.this.processErrorCustomizers(Pair.create(message, arrayOfStackFrame));
              DevSupportManagerImpl.this.mRedBoxDialog.setExceptionDetails((String)pair.first, (StackFrame[])pair.second);
              DevSupportManagerImpl.this.updateLastErrorInfo(message, arrayOfStackFrame, errorCookie, DevSupportManagerImpl.ErrorType.JS);
              if (DevSupportManagerImpl.this.mRedBoxHandler != null) {
                DevSupportManagerImpl.this.mRedBoxHandler.handleRedbox(message, arrayOfStackFrame, RedBoxHandler.ErrorType.JS);
                DevSupportManagerImpl.this.mRedBoxDialog.resetReporting();
              } 
              DevSupportManagerImpl.this.mRedBoxDialog.show();
            } 
          }
        });
  }
  
  public void updateLastErrorInfo(String paramString, StackFrame[] paramArrayOfStackFrame, int paramInt, ErrorType paramErrorType) {
    this.mLastErrorTitle = paramString;
    this.mLastErrorStack = paramArrayOfStackFrame;
    this.mLastErrorCookie = paramInt;
    this.mLastErrorType = paramErrorType;
  }
  
  enum ErrorType {
    JS, NATIVE;
    
    static {
    
    }
  }
  
  static interface ExceptionLogger {
    void log(Exception param1Exception);
  }
  
  class JSExceptionLogger implements ExceptionLogger {
    private JSExceptionLogger() {}
    
    public void log(Exception param1Exception) {
      DevSupportManagerImpl devSupportManagerImpl;
      DevSupportManagerImpl.ErrorType errorType;
      StringBuilder stringBuilder = new StringBuilder(param1Exception.getMessage());
      for (Throwable throwable = param1Exception.getCause(); throwable != null; throwable = throwable.getCause()) {
        stringBuilder.append("\n\n");
        stringBuilder.append(throwable.getMessage());
      } 
      if (param1Exception instanceof JSException) {
        a.c("ReactNative", "Exception in native call from JS", param1Exception);
        String str1 = ((JSException)param1Exception).getStack();
        stringBuilder.append("\n\n");
        stringBuilder.append(str1);
        devSupportManagerImpl = DevSupportManagerImpl.this;
        String str2 = stringBuilder.toString();
        errorType = DevSupportManagerImpl.ErrorType.JS;
        devSupportManagerImpl.showNewError(str2, new StackFrame[0], -1, errorType);
        return;
      } 
      DevSupportManagerImpl.this.showNewJavaError(errorType.toString(), (Throwable)devSupportManagerImpl);
    }
  }
  
  static class JscProfileTask extends AsyncTask<String, Void, Void> {
    private static final w JSON = w.a("application/json; charset=utf-8");
    
    private final String mSourceUrl;
    
    private JscProfileTask(String param1String) {
      this.mSourceUrl = param1String;
    }
    
    protected Void doInBackground(String... param1VarArgs) {
      try {
        String str = Uri.parse(this.mSourceUrl).buildUpon().path("/jsc-profile").query(null).build().toString();
        y y = new y();
        int j = param1VarArgs.length;
        for (int i = 0; i < j; i++) {
          String str1 = param1VarArgs[i];
          ad ad = ad.create(JSON, str1);
          y.a((new ac.a()).a(str).a(ad).c()).b();
        } 
      } catch (IOException iOException) {
        a.c("ReactNative", "Failed not talk to server", iOException);
      } 
      return null;
    }
  }
  
  class StackOverflowExceptionLogger implements ExceptionLogger {
    private StackOverflowExceptionLogger() {}
    
    private Pair<View, Integer> getDeepestNativeView(View param1View) {
      LinkedList<Pair> linkedList = new LinkedList();
      Pair<View, Integer> pair = new Pair(param1View, Integer.valueOf(1));
      linkedList.add(pair);
      label16: while (!linkedList.isEmpty()) {
        Pair<View, Integer> pair2 = linkedList.poll();
        Pair<View, Integer> pair1 = pair;
        if (((Integer)pair2.second).intValue() > ((Integer)pair.second).intValue())
          pair1 = pair2; 
        pair = pair1;
        if (pair2.first instanceof ViewGroup) {
          ViewGroup viewGroup = (ViewGroup)pair2.first;
          int j = ((Integer)pair2.second).intValue();
          int i = 0;
          while (true) {
            pair = pair1;
            if (i < viewGroup.getChildCount()) {
              linkedList.add(new Pair(viewGroup.getChildAt(i), Integer.valueOf(j + 1)));
              i++;
              continue;
            } 
            continue label16;
          } 
        } 
      } 
      return pair;
    }
    
    private void logDeepestJSHierarchy(View param1View) {
      if (DevSupportManagerImpl.this.mCurrentContext != null) {
        if (param1View == null)
          return; 
        Pair<View, Integer> pair = getDeepestNativeView(param1View);
        int i = ((View)pair.first).getId();
        final int depth = ((Integer)pair.second).intValue();
        ((JSDevSupport)DevSupportManagerImpl.this.mCurrentContext.getNativeModule(JSDevSupport.class)).getJSHierarchy(Integer.valueOf(i).toString(), new JSDevSupport.DevSupportCallback() {
              public void onFailure(Exception param2Exception) {
                StringBuilder stringBuilder = new StringBuilder("Error retrieving JS Hierarchy (depth of native hierarchy = ");
                stringBuilder.append(depth);
                stringBuilder.append(").");
                String str = stringBuilder.toString();
                if (a.a.b(6))
                  a.a.c("ReactNative", a.a(str, new Object[0]), param2Exception); 
              }
              
              public void onSuccess(String param2String) {
                StringBuilder stringBuilder = new StringBuilder("StackOverflowError when rendering JS Hierarchy (depth of native hierarchy = ");
                stringBuilder.append(depth);
                stringBuilder.append("): \n");
                stringBuilder.append(param2String);
                a.c("ReactNative", stringBuilder.toString());
              }
            });
      } 
    }
    
    public void log(Exception param1Exception) {
      if (param1Exception instanceof IllegalViewOperationException && param1Exception.getCause() instanceof StackOverflowError) {
        View view = ((IllegalViewOperationException)param1Exception).getView();
        if (view != null)
          logDeepestJSHierarchy(view); 
      } 
    }
  }
  
  class null implements JSDevSupport.DevSupportCallback {
    public void onFailure(Exception param1Exception) {
      StringBuilder stringBuilder = new StringBuilder("Error retrieving JS Hierarchy (depth of native hierarchy = ");
      stringBuilder.append(depth);
      stringBuilder.append(").");
      String str = stringBuilder.toString();
      if (a.a.b(6))
        a.a.c("ReactNative", a.a(str, new Object[0]), param1Exception); 
    }
    
    public void onSuccess(String param1String) {
      StringBuilder stringBuilder = new StringBuilder("StackOverflowError when rendering JS Hierarchy (depth of native hierarchy = ");
      stringBuilder.append(depth);
      stringBuilder.append("): \n");
      stringBuilder.append(param1String);
      a.c("ReactNative", stringBuilder.toString());
    }
  }
  
  class DevSupportManagerImpl {}
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\devsupport\DevSupportManagerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */