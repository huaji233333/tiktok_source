package com.tt.miniapp;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.he.JsRunLoop;
import com.he.Monitor;
import com.he.SettingsProvider;
import com.he.jsbinding.JsContext;
import com.he.jsbinding.JsObject;
import com.he.jsbinding.JsScopedContext;
import com.he.loader.LoadScriptSample;
import com.he.loader.TTAppLoader;
import com.tt.frontendapiinterface.j;
import com.tt.miniapp.autotest.AutoTestManager;
import com.tt.miniapp.dialog.LoadHelper;
import com.tt.miniapp.errorcode.ErrorCode;
import com.tt.miniapp.event.InnerEventHelper;
import com.tt.miniapp.event.LoadStateManager;
import com.tt.miniapp.jsbridge.JsBridge;
import com.tt.miniapp.jsbridge.Jscore;
import com.tt.miniapp.jsbridge.PreloadedJsContext;
import com.tt.miniapp.launchcache.pkg.PkgService;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapp.thread.HandlerThreadUtil;
import com.tt.miniapp.util.JsRuntimeErrorReporter;
import com.tt.miniapp.util.TimeLogger;
import com.tt.miniapp.util.timeline.MpTimeLineReporter;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.util.DebugUtil;
import com.tt.miniapphost.util.IOUtils;
import com.tt.miniapphost.util.TimeMeter;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

public abstract class JsRuntime implements JsRunLoop.SetupCallback, SettingsProvider {
  private static final long epoch_cpu_time_start;
  
  private static final long epoch_start = System.currentTimeMillis() - System.nanoTime() / 1000000L;
  
  public volatile boolean localPkgReady = false;
  
  private final JsBridge mJsBridge = new JsBridge(this);
  
  public int mJsSdkLoadState = 2;
  
  protected JsRunLoop mJsThread;
  
  protected Handler mJsThreadHandler;
  
  protected final TTAppLoader mLoader;
  
  public List<JsContext.ScopeCallback> mPendingScopeCallbackList = new ArrayList<JsContext.ScopeCallback>();
  
  protected Handler mWorkHandler;
  
  private HandlerThread mWorkThread;
  
  private PreloadedJsContext preloaded;
  
  static {
    epoch_cpu_time_start = SystemClock.elapsedRealtime() - System.nanoTime() / 1000000L;
  }
  
  public JsRuntime(PreloadedJsContext paramPreloadedJsContext) {
    this.preloaded = paramPreloadedJsContext;
    TTAppLoaderImpl tTAppLoaderImpl = new TTAppLoaderImpl();
    Monitor.impl = tTAppLoaderImpl;
    this.mLoader = new TTAppLoader(tTAppLoaderImpl);
  }
  
  private boolean isTMARuntime() {
    return this instanceof com.tt.miniapp.jsbridge.JsTMARuntime;
  }
  
  private void loadJsCoreFile(JsScopedContext paramJsScopedContext, File paramFile, String paramString, TimeMeter paramTimeMeter) throws Exception {
    CodeNullException codeNullException;
    StringBuilder stringBuilder1;
    int i;
    MpTimeLineReporter mpTimeLineReporter = AppbrandApplicationImpl.getInst().<MpTimeLineReporter>getService(MpTimeLineReporter.class);
    JSONObject jSONObject = (new MpTimeLineReporter.ExtraBuilder()).kv("file_path", paramString).build();
    PreloadedJsContext preloadedJsContext = this.preloaded;
    if (preloadedJsContext == null) {
      i = -1;
    } else {
      i = preloadedJsContext.get(paramString);
    } 
    if (i != -1) {
      this.preloaded = null;
      stringBuilder1 = new StringBuilder("use precompiled script ");
      stringBuilder1.append(i);
      AppBrandLogger.d("loadJsSdk", new Object[] { stringBuilder1.toString() });
      mpTimeLineReporter.addPoint("load_coreJs_begin", jSONObject);
      paramJsScopedContext.execute(i, true);
      paramJsScopedContext.pop();
      mpTimeLineReporter.addPoint("load_coreJs_end", jSONObject);
    } else {
      byte[] arrayOfByte = IOUtils.readBytes(stringBuilder1.getAbsolutePath());
      mpTimeLineReporter.addPoint("load_coreJs_begin", jSONObject);
      if (arrayOfByte != null) {
        paramJsScopedContext.eval(arrayOfByte, stringBuilder1.getName());
        paramJsScopedContext.pop();
        mpTimeLineReporter.addPoint("load_coreJs_end", jSONObject);
      } else {
        codeNullException = new CodeNullException();
        throw codeNullException;
      } 
    } 
    this.mJsSdkLoadState = 0;
    InnerEventHelper.mpJsLoadResult("success", TimeMeter.stop(paramTimeMeter), "");
    TimeLogger timeLogger = TimeLogger.getInstance();
    StringBuilder stringBuilder2 = new StringBuilder("JsRuntime_load_");
    stringBuilder2.append(paramString);
    stringBuilder2.append("_success");
    timeLogger.logTimeDuration(new String[] { stringBuilder2.toString() });
    Iterator<JsContext.ScopeCallback> iterator = this.mPendingScopeCallbackList.iterator();
    while (iterator.hasNext())
      codeNullException.run(iterator.next()); 
    this.mPendingScopeCallbackList.clear();
  }
  
  private boolean retryLoadJsCoreFile(JsScopedContext paramJsScopedContext, File paramFile, String paramString, TimeMeter paramTimeMeter) {
    Object object = null;
    int i = 0;
    boolean bool2 = false;
    boolean bool1 = false;
    while (true) {
      ErrorCode.JSCORE jSCORE;
      if (i < 20 && !bool2) {
        i++;
        try {
          Thread.sleep(500L);
          loadJsCoreFile(paramJsScopedContext, paramFile, paramString, paramTimeMeter);
          bool2 = true;
        } catch (InterruptedException interruptedException) {
          AppBrandLogger.e("JsRuntime", new Object[] { interruptedException });
        } catch (CodeNullException codeNullException) {
          bool1 = true;
        } catch (Exception exception) {}
        continue;
      } 
      if (bool2)
        return true; 
      if (paramFile != null) {
        TimeLogger timeLogger = TimeLogger.getInstance();
        StringBuilder stringBuilder1 = new StringBuilder("jscore file lenght:");
        stringBuilder1.append(paramFile.length());
        timeLogger.logTimeDuration(new String[] { stringBuilder1.toString() });
      } 
      if (bool1) {
        if (isTMARuntime()) {
          jSCORE = ErrorCode.JSCORE.TMA_CORE_NOT_FOUND;
        } else {
          jSCORE = ErrorCode.JSCORE.TMG_CORE_NOT_FOUND;
        } 
        showLoadError(jSCORE.getCode());
        return false;
      } 
      StringBuilder stringBuilder = new StringBuilder("js core load ");
      stringBuilder.append(paramString);
      stringBuilder.append(" fail ");
      AppBrandLogger.eWithThrowable("JsRuntime", stringBuilder.toString(), exception);
      this.mJsSdkLoadState = 1;
      InnerEventHelper.mpJsLoadResult("fail", TimeMeter.stop(paramTimeMeter), Log.getStackTraceString(exception));
      TimeLogger.getInstance().logError(new String[] { "JSRUNTIME_LOAD_JSCORE_ERROR", paramString, Log.getStackTraceString(exception) });
      if (isTMARuntime()) {
        jSCORE = ErrorCode.JSCORE.TMA_CORE_EXECUTE_ERROR;
      } else {
        jSCORE = ErrorCode.JSCORE.TMG_CORE_EXECUTE_ERROR;
      } 
      showLoadError(jSCORE.getCode());
      return false;
    } 
  }
  
  public static long toEpochCpuTime(long paramLong) {
    return epoch_cpu_time_start + paramLong / 1000L;
  }
  
  public static long toEpochTime(long paramLong) {
    return epoch_start + paramLong / 1000L;
  }
  
  public void executeInJsThread(JsContext.ScopeCallback paramScopeCallback) {
    executeInJsThread(paramScopeCallback, false, false);
  }
  
  public void executeInJsThread(final JsContext.ScopeCallback callback, final boolean forceNotPending, boolean paramBoolean2) {
    if (this.mJsThreadHandler == null)
      return; 
    Runnable runnable = new Runnable() {
        public void run() {
          if (forceNotPending || JsRuntime.this.mJsSdkLoadState == 0)
            try {
              JsRuntime.this.mJsThread.getJsContext().run(callback);
              return;
            } catch (Exception exception) {
              JsRuntimeErrorReporter jsRuntimeErrorReporter = JsRuntimeErrorReporter.getInstance();
              StringBuilder stringBuilder = new StringBuilder("js context run fail: ");
              stringBuilder.append(exception);
              jsRuntimeErrorReporter.report(stringBuilder.toString(), "unCaughtScriptError");
              DebugUtil.outputError("JsRuntime", new Object[] { "js context run fail ", exception });
              return;
            }  
          TimeLogger.getInstance().logTimeDuration(new String[] { "JsRuntime_addPendingCallback", Log.getStackTraceString(new Throwable()) });
          JsRuntime.this.mPendingScopeCallbackList.add(callback);
        }
      };
    if (paramBoolean2) {
      this.mJsThreadHandler.postAtFrontOfQueue(runnable);
      return;
    } 
    this.mJsThreadHandler.post(runnable);
  }
  
  public j getJsBridge() {
    return (j)this.mJsBridge;
  }
  
  protected abstract String getJsCoreFileName();
  
  public int getJsSdkLoadState() {
    return this.mJsSdkLoadState;
  }
  
  public int getSetting(Context paramContext, Enum<?> paramEnum, int paramInt) {
    return SettingsDAO.getInt(paramContext, paramInt, new Enum[] { (Enum)Settings.BDP_HELIUM_CONFIG, paramEnum });
  }
  
  public String getSetting(Context paramContext, Enum<?> paramEnum, String paramString) {
    return SettingsDAO.getString(paramContext, paramString, new Enum[] { (Enum)Settings.BDP_HELIUM_CONFIG, paramEnum });
  }
  
  public boolean getSetting(Context paramContext, Enum<?> paramEnum, boolean paramBoolean) {
    return SettingsDAO.getBoolean(paramContext, paramBoolean, new Enum[] { (Enum)Settings.BDP_HELIUM_CONFIG, paramEnum });
  }
  
  protected final boolean loadJsSdk(JsScopedContext paramJsScopedContext) {
    LoadStateManager.getIns().setLoadState("lib_js_loading");
    String str = getJsCoreFileName();
    TimeLogger timeLogger = TimeLogger.getInstance();
    StringBuilder stringBuilder = new StringBuilder("JsRuntime_load_");
    stringBuilder.append(str);
    timeLogger.logTimeDuration(new String[] { stringBuilder.toString() });
    JsObject jsObject1 = paramJsScopedContext.global();
    JsObject jsObject2 = paramJsScopedContext.createObject();
    registFuntions2Js(paramJsScopedContext, jsObject2, this.mJsBridge);
    jsObject1.set("ttJSCore", jsObject2);
    TimeMeter timeMeter = TimeMeter.newAndStart();
    File file = new File(AppbrandConstant.getJsBundleDir((Context)AppbrandContext.getInst().getApplicationContext()), str);
    try {
      loadJsCoreFile(paramJsScopedContext, file, str, timeMeter);
      return true;
    } catch (Exception exception) {
      return retryLoadJsCoreFile(paramJsScopedContext, file, str, timeMeter);
    } 
  }
  
  public abstract void loadMainJs(MainJsLoadCallback paramMainJsLoadCallback);
  
  protected void registFuntions2Js(JsScopedContext paramJsScopedContext, JsObject paramJsObject, JsBridge paramJsBridge) {
    AppBrandLogger.d("JsRuntime", new Object[] { "registFuntions2Js--------prepare---- " });
    for (Method method : JsBridge.class.getMethods()) {
      Jscore jscore = method.<Jscore>getAnnotation(Jscore.class);
      if (jscore != null && !TextUtils.isEmpty(jscore.jsfunctionname())) {
        paramJsObject.set(method.getName(), paramJsScopedContext.createFunction(paramJsBridge, method));
        AppBrandLogger.d("JsRuntime", new Object[] { "registFuntions2Js", "registFuntions2Js finish : method name is:", method.getName(), "&jsfunctionname = ", jscore.jsfunctionname() });
      } else {
        AppBrandLogger.d("JsRuntime", new Object[] { "registFuntions2Js method :", method.getName(), " ignored " });
      } 
    } 
  }
  
  public void release() {
    TimeLogger.getInstance().logTimeDuration(new String[] { "JsRuntime_release", toString() });
    if (this.mJsThread != null) {
      this.mJsBridge.release();
      this.mJsThreadHandler.post(new Runnable() {
            public void run() {
              JsRuntime.this.mJsThread.quit();
              JsRuntime jsRuntime = JsRuntime.this;
              jsRuntime.mJsThread = null;
              jsRuntime.mJsThreadHandler = null;
            }
          });
    } 
    HandlerThread handlerThread = this.mWorkThread;
    if (handlerThread != null) {
      handlerThread.quit();
      this.mWorkThread = null;
      this.mWorkHandler = null;
    } 
  }
  
  protected final void setupLoader() {
    ((PkgService)AppbrandApplicationImpl.getInst().<PkgService>getService(PkgService.class)).onLocalPackageFileReady(new PkgService.LocalPackageFileReadyCallback() {
          public void localPackageReady(String param1String) {
            if (JsRuntime.this.mJsThread == null)
              return; 
            StringBuilder stringBuilder = new StringBuilder("TTAppLoader.loadPackage: ");
            stringBuilder.append(param1String);
            AppBrandLogger.d("JsRuntime", new Object[] { stringBuilder.toString() });
            JsRuntime.this.mLoader.loadPackage(param1String);
            JsRuntime.this.localPkgReady = true;
          }
        });
    this.mLoader.setLoadScriptSampleCallback(new LoadScriptSample.Callback() {
          public void onSample(final LoadScriptSample sample) {
            AutoTestManager autoTestManager = AppbrandApplicationImpl.getInst().<AutoTestManager>getService(AutoTestManager.class);
            autoTestManager.addEventWithValue("loadScriptBegin", sample.path, JsRuntime.toEpochTime(sample.start));
            autoTestManager.addEventWithValue("loadScriptEnd", sample.path, JsRuntime.toEpochTime(sample.end));
            JsRuntime.this.mWorkHandler.post(new Runnable() {
                  public void run() {
                    throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s2stmt(TypeTransformer.java:820)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:843)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
                  }
                });
          }
        });
  }
  
  public void showLoadError(String paramString) {
    LoadHelper.handleMiniProcessFail(paramString);
  }
  
  protected final void start() {
    PreloadedJsContext preloadedJsContext = this.preloaded;
    if (preloadedJsContext == null) {
      this.mJsThread = new JsRunLoop();
      this.mJsThread.setup(this);
      this.mJsThread.start();
    } else {
      this.mJsThread = preloadedJsContext.getLoop();
      this.mJsThread.setup(this);
    } 
    this.mWorkThread = HandlerThreadUtil.getBackgroundHandlerThread();
    this.mWorkThread.start();
    this.mJsThreadHandler = this.mJsThread.getHandler();
    this.mWorkHandler = new Handler(this.mWorkThread.getLooper());
  }
  
  static class CodeNullException extends Exception {
    private CodeNullException() {}
  }
  
  public static interface MainJsLoadCallback {
    void afterEval();
    
    void beforeEval();
    
    void evalException(Exception param1Exception);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\JsRuntime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */