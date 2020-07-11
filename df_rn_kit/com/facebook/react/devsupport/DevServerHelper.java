package com.facebook.react.devsupport;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;
import com.a;
import com.facebook.common.e.a;
import com.facebook.i.a.a;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.common.network.OkHttpCallUtil;
import com.facebook.react.devsupport.interfaces.DevBundleDownloadListener;
import com.facebook.react.devsupport.interfaces.PackagerStatusCallback;
import com.facebook.react.devsupport.interfaces.StackFrame;
import com.facebook.react.modules.systeminfo.AndroidInfoHelpers;
import com.facebook.react.packagerconnection.FileIoHandler;
import com.facebook.react.packagerconnection.JSPackagerClient;
import com.facebook.react.packagerconnection.NotificationOnlyHandler;
import com.facebook.react.packagerconnection.ReconnectingWebSocket;
import com.facebook.react.packagerconnection.RequestOnlyHandler;
import com.facebook.react.packagerconnection.Responder;
import g.q;
import g.x;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import okhttp3.ac;
import okhttp3.ad;
import okhttp3.ae;
import okhttp3.af;
import okhttp3.e;
import okhttp3.f;
import okhttp3.j;
import okhttp3.w;
import okhttp3.y;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DevServerHelper {
  private final BundleDownloader mBundleDownloader;
  
  public InspectorPackagerConnection.BundleStatusProvider mBundlerStatusProvider;
  
  private final y mClient;
  
  public InspectorPackagerConnection mInspectorPackagerConnection;
  
  private y mOnChangePollingClient;
  
  public boolean mOnChangePollingEnabled;
  
  public OnServerContentChangeListener mOnServerContentChangeListener;
  
  public final String mPackageName;
  
  public JSPackagerClient mPackagerClient;
  
  public final Handler mRestartOnChangePollingHandler;
  
  public final DevInternalSettings mSettings;
  
  public DevServerHelper(DevInternalSettings paramDevInternalSettings, String paramString, InspectorPackagerConnection.BundleStatusProvider paramBundleStatusProvider) {
    this.mSettings = paramDevInternalSettings;
    this.mBundlerStatusProvider = paramBundleStatusProvider;
    this.mClient = (new y.a()).a(5000L, TimeUnit.MILLISECONDS).b(0L, TimeUnit.MILLISECONDS).c(0L, TimeUnit.MILLISECONDS).a();
    this.mBundleDownloader = new BundleDownloader(this.mClient);
    this.mRestartOnChangePollingHandler = new Handler();
    this.mPackageName = paramString;
  }
  
  private String createBundleURL(String paramString, BundleType paramBundleType) {
    return createBundleURL(paramString, paramBundleType, this.mSettings.getPackagerConnectionSettings().getDebugServerHost());
  }
  
  private String createBundleURL(String paramString1, BundleType paramBundleType, String paramString2) {
    return a.a(Locale.US, "http://%s/%s.%s?platform=android&dev=%s&minify=%s", new Object[] { paramString2, paramString1, paramBundleType.typeID(), Boolean.valueOf(getDevMode()), Boolean.valueOf(getJSMinifyMode()) });
  }
  
  private String createLaunchJSDevtoolsCommandUrl() {
    return a.a(Locale.US, "http://%s/launch-js-devtools", new Object[] { this.mSettings.getPackagerConnectionSettings().getDebugServerHost() });
  }
  
  private String createOnChangeEndpointUrl() {
    return a.a(Locale.US, "http://%s/onchange", new Object[] { this.mSettings.getPackagerConnectionSettings().getDebugServerHost() });
  }
  
  private static String createOpenStackFrameURL(String paramString) {
    return a.a(Locale.US, "http://%s/open-stack-frame", new Object[] { paramString });
  }
  
  private static String createPackagerStatusURL(String paramString) {
    return a.a(Locale.US, "http://%s/status", new Object[] { paramString });
  }
  
  private static String createResourceURL(String paramString1, String paramString2) {
    return a.a(Locale.US, "http://%s/%s", new Object[] { paramString1, paramString2 });
  }
  
  private static String createSymbolicateURL(String paramString) {
    return a.a(Locale.US, "http://%s/symbolicate", new Object[] { paramString });
  }
  
  private void enqueueOnChangeEndpointLongPolling() {
    ac ac = (new ac.a()).a(createOnChangeEndpointUrl()).a(this).c();
    ((y)a.b(this.mOnChangePollingClient)).a(ac).a(new f() {
          public void onFailure(e param1e, IOException param1IOException) {
            if (DevServerHelper.this.mOnChangePollingEnabled) {
              a.a("ReactNative", "Error while requesting /onchange endpoint", param1IOException);
              DevServerHelper.this.mRestartOnChangePollingHandler.postDelayed(new Runnable() {
                    public void run() {
                      DevServerHelper.this.handleOnChangePollingResponse(false);
                    }
                  },  5000L);
            } 
          }
          
          public void onResponse(e param1e, ae param1ae) throws IOException {
            boolean bool;
            DevServerHelper devServerHelper = DevServerHelper.this;
            if (param1ae.c == 205) {
              bool = true;
            } else {
              bool = false;
            } 
            devServerHelper.handleOnChangePollingResponse(bool);
          }
        });
  }
  
  private boolean getDevMode() {
    return this.mSettings.isJSDevModeEnabled();
  }
  
  private String getHostForJSProxy() {
    String str = (String)a.b(this.mSettings.getPackagerConnectionSettings().getDebugServerHost());
    return (str.lastIndexOf(':') >= 0) ? str : "localhost";
  }
  
  private boolean getJSMinifyMode() {
    return this.mSettings.isJSMinifyEnabled();
  }
  
  public void attachDebugger(final Context context, final String title) {
    (new AsyncTask<Void, String, Boolean>() {
        protected Boolean doInBackground(Void... param1VarArgs) {
          return Boolean.valueOf(doSync());
        }
        
        public boolean doSync() {
          try {
            String str = DevServerHelper.this.getInspectorAttachUrl(title);
            (new y()).a((new ac.a()).a(str).c()).b();
            return true;
          } catch (IOException iOException) {
            a.c("ReactNative", "Failed to send attach request to Inspector", iOException);
            return false;
          } 
        }
        
        protected void onPostExecute(Boolean param1Boolean) {
          if (!param1Boolean.booleanValue()) {
            String str = context.getString(1980104708);
            _lancet.com_ss_android_ugc_aweme_lancet_DesignBugFixLancet_show(Toast.makeText(context, str, 1));
          } 
        }
        
        class null {}
      }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
  }
  
  public void closeInspectorConnection() {
    (new AsyncTask<Void, Void, Void>() {
        protected Void doInBackground(Void... param1VarArgs) {
          if (DevServerHelper.this.mInspectorPackagerConnection != null) {
            DevServerHelper.this.mInspectorPackagerConnection.closeQuietly();
            DevServerHelper.this.mInspectorPackagerConnection = null;
          } 
          return null;
        }
      }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
  }
  
  public void closePackagerConnection() {
    (new AsyncTask<Void, Void, Void>() {
        protected Void doInBackground(Void... param1VarArgs) {
          if (DevServerHelper.this.mPackagerClient != null) {
            DevServerHelper.this.mPackagerClient.close();
            DevServerHelper.this.mPackagerClient = null;
          } 
          return null;
        }
      }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
  }
  
  public void disableDebugger() {
    InspectorPackagerConnection inspectorPackagerConnection = this.mInspectorPackagerConnection;
    if (inspectorPackagerConnection != null)
      inspectorPackagerConnection.sendEventToAllConnections("{ \"id\":1,\"method\":\"Debugger.disable\" }"); 
  }
  
  public void downloadBundleFromURL(DevBundleDownloadListener paramDevBundleDownloadListener, File paramFile, String paramString, BundleDownloader.BundleInfo paramBundleInfo) {
    this.mBundleDownloader.downloadBundleFromURL(paramDevBundleDownloadListener, paramFile, paramString, paramBundleInfo);
  }
  
  public File downloadBundleResourceFromUrlSync(String paramString, File paramFile) {
    String str = createResourceURL(this.mSettings.getPackagerConnectionSettings().getDebugServerHost(), paramString);
    ac ac = (new ac.a()).a(str).c();
    try {
      ae ae = this.mClient.a(ac).b();
      try {
        Exception exception;
        boolean bool = ae.a();
        if (!bool)
          return null; 
        try {
          x x = q.a(paramFile);
        } finally {
          exception = null;
        } 
        if (ac != null)
          ac.close(); 
        throw exception;
      } finally {
        ac = null;
      } 
    } catch (Exception exception) {
      a.c("ReactNative", "Failed to fetch resource synchronously - resourcePath: \"%s\", outputFile: \"%s\"", new Object[] { paramString, paramFile.getAbsolutePath(), exception });
      return null;
    } 
  }
  
  public String getDevServerBundleURL(String paramString) {
    BundleType bundleType;
    if (this.mSettings.isBundleDeltasEnabled()) {
      bundleType = BundleType.DELTA;
    } else {
      bundleType = BundleType.BUNDLE;
    } 
    return createBundleURL(paramString, bundleType, this.mSettings.getPackagerConnectionSettings().getDebugServerHost());
  }
  
  public String getInspectorAttachUrl(String paramString) {
    return a.a(Locale.US, "http://%s/nuclide/attach-debugger-nuclide?title=%s&app=%s&device=%s", new Object[] { AndroidInfoHelpers.getServerHost(), paramString, this.mPackageName, AndroidInfoHelpers.getFriendlyDeviceName() });
  }
  
  public String getInspectorDeviceUrl() {
    return a.a(Locale.US, "http://%s/inspector/device?name=%s&app=%s", new Object[] { this.mSettings.getPackagerConnectionSettings().getInspectorServerHost(), AndroidInfoHelpers.getFriendlyDeviceName(), this.mPackageName });
  }
  
  public String getJSBundleURLForRemoteDebugging(String paramString) {
    return createBundleURL(paramString, BundleType.BUNDLE, getHostForJSProxy());
  }
  
  public String getSourceMapUrl(String paramString) {
    return createBundleURL(paramString, BundleType.MAP);
  }
  
  public String getSourceUrl(String paramString) {
    BundleType bundleType;
    if (this.mSettings.isBundleDeltasEnabled()) {
      bundleType = BundleType.DELTA;
    } else {
      bundleType = BundleType.BUNDLE;
    } 
    return createBundleURL(paramString, bundleType);
  }
  
  public String getWebsocketProxyURL() {
    return a.a(Locale.US, "ws://%s/debugger-proxy?role=client", new Object[] { this.mSettings.getPackagerConnectionSettings().getDebugServerHost() });
  }
  
  public void handleOnChangePollingResponse(boolean paramBoolean) {
    if (this.mOnChangePollingEnabled) {
      if (paramBoolean)
        UiThreadUtil.runOnUiThread(new Runnable() {
              public void run() {
                if (DevServerHelper.this.mOnServerContentChangeListener != null)
                  DevServerHelper.this.mOnServerContentChangeListener.onServerContentChanged(); 
              }
            }); 
      enqueueOnChangeEndpointLongPolling();
    } 
  }
  
  public void isPackagerRunning(final PackagerStatusCallback callback) {
    String str = createPackagerStatusURL(this.mSettings.getPackagerConnectionSettings().getDebugServerHost());
    ac ac = (new ac.a()).a(str).c();
    this.mClient.a(ac).a(new f() {
          public void onFailure(e param1e, IOException param1IOException) {
            StringBuilder stringBuilder = new StringBuilder("The packager does not seem to be running as we got an IOException requesting its status: ");
            stringBuilder.append(param1IOException.getMessage());
            a.b("ReactNative", stringBuilder.toString());
            callback.onPackagerStatusFetched(false);
          }
          
          public void onResponse(e param1e, ae param1ae) throws IOException {
            if (!param1ae.a()) {
              StringBuilder stringBuilder = new StringBuilder("Got non-success http code from packager when requesting status: ");
              stringBuilder.append(param1ae.c);
              a.c("ReactNative", stringBuilder.toString());
              callback.onPackagerStatusFetched(false);
              return;
            } 
            af af = param1ae.g;
            if (af == null) {
              a.c("ReactNative", "Got null body response from packager when requesting status");
              callback.onPackagerStatusFetched(false);
              return;
            } 
            if (!"packager-status:running".equals(af.string())) {
              StringBuilder stringBuilder = new StringBuilder("Got unexpected response from packager when requesting status: ");
              stringBuilder.append(af.string());
              a.c("ReactNative", stringBuilder.toString());
              callback.onPackagerStatusFetched(false);
              return;
            } 
            callback.onPackagerStatusFetched(true);
          }
        });
  }
  
  public void launchJSDevtools() {
    ac ac = (new ac.a()).a(createLaunchJSDevtoolsCommandUrl()).c();
    this.mClient.a(ac).a(new f() {
          public void onFailure(e param1e, IOException param1IOException) {}
          
          public void onResponse(e param1e, ae param1ae) throws IOException {}
        });
  }
  
  public void openInspectorConnection() {
    if (this.mInspectorPackagerConnection != null) {
      a.b("ReactNative", "Inspector connection already open, nooping.");
      return;
    } 
    (new AsyncTask<Void, Void, Void>() {
        protected Void doInBackground(Void... param1VarArgs) {
          DevServerHelper devServerHelper = DevServerHelper.this;
          devServerHelper.mInspectorPackagerConnection = new InspectorPackagerConnection(devServerHelper.getInspectorDeviceUrl(), DevServerHelper.this.mPackageName, DevServerHelper.this.mBundlerStatusProvider);
          DevServerHelper.this.mInspectorPackagerConnection.connect();
          return null;
        }
      }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
  }
  
  public void openPackagerConnection(final String clientId, final PackagerCommandListener commandListener) {
    if (this.mPackagerClient != null) {
      a.b("ReactNative", "Packager connection already open, nooping.");
      return;
    } 
    (new AsyncTask<Void, Void, Void>() {
        protected Void doInBackground(Void... param1VarArgs) {
          HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
          hashMap.put("reload", new NotificationOnlyHandler() {
                public void onNotification(Object param2Object) {
                  commandListener.onPackagerReloadCommand();
                }
              });
          hashMap.put("devMenu", new NotificationOnlyHandler() {
                public void onNotification(Object param2Object) {
                  commandListener.onPackagerDevMenuCommand();
                }
              });
          hashMap.put("captureHeap", new RequestOnlyHandler() {
                public void onRequest(Object param2Object, Responder param2Responder) {
                  commandListener.onCaptureHeapCommand(param2Responder);
                }
              });
          hashMap.put("pokeSamplingProfiler", new RequestOnlyHandler() {
                public void onRequest(Object param2Object, Responder param2Responder) {
                  commandListener.onPokeSamplingProfilerCommand(param2Responder);
                }
              });
          hashMap.putAll((new FileIoHandler()).handlers());
          ReconnectingWebSocket.ConnectionCallback connectionCallback = new ReconnectingWebSocket.ConnectionCallback() {
              public void onConnected() {
                commandListener.onPackagerConnected();
              }
              
              public void onDisconnected() {
                commandListener.onPackagerDisconnected();
              }
            };
          DevServerHelper devServerHelper = DevServerHelper.this;
          devServerHelper.mPackagerClient = new JSPackagerClient(clientId, devServerHelper.mSettings.getPackagerConnectionSettings(), hashMap, connectionCallback);
          DevServerHelper.this.mPackagerClient.init();
          return null;
        }
      }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
  }
  
  public void openStackFrameCall(StackFrame paramStackFrame) {
    String str = createOpenStackFrameURL(this.mSettings.getPackagerConnectionSettings().getDebugServerHost());
    ac ac = (new ac.a()).a(str).a(ad.create(w.a("application/json"), paramStackFrame.toJSON().toString())).c();
    ((e)a.b(this.mClient.a(ac))).a(new f() {
          public void onFailure(e param1e, IOException param1IOException) {
            StringBuilder stringBuilder = new StringBuilder("Got IOException when attempting to open stack frame: ");
            stringBuilder.append(param1IOException.getMessage());
            a.b("ReactNative", stringBuilder.toString());
          }
          
          public void onResponse(e param1e, ae param1ae) throws IOException {}
        });
  }
  
  public void startPollingOnChangeEndpoint(OnServerContentChangeListener paramOnServerContentChangeListener) {
    if (this.mOnChangePollingEnabled)
      return; 
    this.mOnChangePollingEnabled = true;
    this.mOnServerContentChangeListener = paramOnServerContentChangeListener;
    this.mOnChangePollingClient = (new y.a()).a(new j(1, 120000L, TimeUnit.MINUTES)).a(5000L, TimeUnit.MILLISECONDS).a();
    enqueueOnChangeEndpointLongPolling();
  }
  
  public void stopPollingOnChangeEndpoint() {
    this.mOnChangePollingEnabled = false;
    this.mRestartOnChangePollingHandler.removeCallbacksAndMessages(null);
    y y1 = this.mOnChangePollingClient;
    if (y1 != null) {
      OkHttpCallUtil.cancelTag(y1, this);
      this.mOnChangePollingClient = null;
    } 
    this.mOnServerContentChangeListener = null;
  }
  
  public void symbolicateStackTrace(Iterable<StackFrame> paramIterable, final SymbolicationListener listener) {
    try {
      String str = createSymbolicateURL(this.mSettings.getPackagerConnectionSettings().getDebugServerHost());
      JSONArray jSONArray = new JSONArray();
      Iterator<StackFrame> iterator = paramIterable.iterator();
      while (iterator.hasNext())
        jSONArray.put(((StackFrame)iterator.next()).toJSON()); 
      ac ac = (new ac.a()).a(str).a(ad.create(w.a("application/json"), (new JSONObject()).put("stack", jSONArray).toString())).c();
      ((e)a.b(this.mClient.a(ac))).a(new f() {
            public void onFailure(e param1e, IOException param1IOException) {
              StringBuilder stringBuilder = new StringBuilder("Got IOException when attempting symbolicate stack trace: ");
              stringBuilder.append(param1IOException.getMessage());
              a.b("ReactNative", stringBuilder.toString());
              listener.onSymbolicationComplete(null);
            }
            
            public void onResponse(e param1e, ae param1ae) throws IOException {
              try {
                listener.onSymbolicationComplete(Arrays.asList(StackTraceHelper.convertJsStackTrace((new JSONObject(param1ae.g.string())).getJSONArray("stack"))));
                return;
              } catch (JSONException jSONException) {
                listener.onSymbolicationComplete(null);
                return;
              } 
            }
          });
      return;
    } catch (JSONException jSONException) {
      StringBuilder stringBuilder = new StringBuilder("Got JSONException when attempting symbolicate stack trace: ");
      stringBuilder.append(jSONException.getMessage());
      a.b("ReactNative", stringBuilder.toString());
      return;
    } 
  }
  
  enum BundleType {
    BUNDLE("bundle"),
    DELTA("delta"),
    MAP("map");
    
    private final String mTypeID;
    
    static {
    
    }
    
    BundleType(String param1String1) {
      this.mTypeID = param1String1;
    }
    
    public final String typeID() {
      return this.mTypeID;
    }
  }
  
  public static interface OnServerContentChangeListener {
    void onServerContentChanged();
  }
  
  public static interface PackagerCommandListener {
    void onCaptureHeapCommand(Responder param1Responder);
    
    void onPackagerConnected();
    
    void onPackagerDevMenuCommand();
    
    void onPackagerDisconnected();
    
    void onPackagerReloadCommand();
    
    void onPokeSamplingProfilerCommand(Responder param1Responder);
  }
  
  public static interface SymbolicationListener {
    void onSymbolicationComplete(Iterable<StackFrame> param1Iterable);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\devsupport\DevServerHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */