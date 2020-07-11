package com.tt.miniapp.jsbridge;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.bytedance.sandboxapp.b.a;
import com.bytedance.sandboxapp.c.a.a.b;
import com.bytedance.sandboxapp.protocol.service.api.a;
import com.bytedance.sandboxapp.protocol.service.api.a.a;
import com.bytedance.sandboxapp.protocol.service.api.b.a;
import com.bytedance.sandboxapp.protocol.service.api.b.b;
import com.bytedance.sandboxapp.protocol.service.api.b.c;
import com.bytedance.sandboxapp.protocol.service.api.entity.ApiCallbackData;
import com.bytedance.sandboxapp.protocol.service.api.entity.a;
import com.bytedance.sandboxapp.protocol.service.api.entity.b;
import com.bytedance.sandboxapp.protocol.service.k.a;
import com.he.jsbinding.JsContext;
import com.he.jsbinding.JsObject;
import com.he.jsbinding.JsScopedContext;
import com.storage.async.Action;
import com.tt.frontendapiinterface.f;
import com.tt.frontendapiinterface.g;
import com.tt.frontendapiinterface.j;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.JsRuntime;
import com.tt.miniapp.WebViewManager;
import com.tt.miniapp.audio.AudioManager;
import com.tt.miniapp.business.frontendapihandle.entity.CommonApiOutputParam;
import com.tt.miniapp.business.frontendapihandle.handler.OverridePreHandler;
import com.tt.miniapp.business.frontendapihandle.handler.net.CreateSocketTaskSync;
import com.tt.miniapp.business.frontendapihandle.handler.net.CreateSocketTaskSyncV2;
import com.tt.miniapp.manager.ForeBackgroundManager;
import com.tt.miniapp.msg.ApiCloseCallbackReturnCtrl;
import com.tt.miniapp.msg.ApiCreateFollowButtonSync;
import com.tt.miniapp.msg.ApiGetAppBrandSettingsCtrl;
import com.tt.miniapp.msg.ApiInvokeCtrl;
import com.tt.miniapp.msg.ApiJsExecuteContext;
import com.tt.miniapp.msg.ApiParamParser;
import com.tt.miniapp.msg.MiniAppApiInvokeParam;
import com.tt.miniapp.msg.ad.ApiOperateBannerAdSyncCtrl;
import com.tt.miniapp.msg.ad.ApiOperateInterstitialAdCtrl;
import com.tt.miniapp.msg.audio.sync.ApiAddAudioTrackCtrl;
import com.tt.miniapp.msg.audio.sync.GetAudioStateSyncCtrl;
import com.tt.miniapp.msg.audio.sync.OperateAudioRecorderSyncCtrl;
import com.tt.miniapp.msg.download.ApiGetDxppTaskStatus;
import com.tt.miniapp.msg.favorite.ApiIsInUserFavoritesSync;
import com.tt.miniapp.msg.file.FileSystemManagerSync;
import com.tt.miniapp.msg.file.FileSystemManagerSyncV2;
import com.tt.miniapp.msg.game.ApiMenuButtonBoundingCtrl;
import com.tt.miniapp.msg.storage.internal.ApiOperateInternalStorageSyncCtrl;
import com.tt.miniapp.msg.sync.ApiBase64ToTempFilePathSyncCtrl;
import com.tt.miniapp.msg.sync.ApiCanLaunchAppSyncCtrl;
import com.tt.miniapp.msg.sync.ApiCanvasSync;
import com.tt.miniapp.msg.sync.ApiGetPerformanceTimingSyncCtrl;
import com.tt.miniapp.msg.sync.ApiGetUsageRecordCtrl;
import com.tt.miniapp.msg.sync.ApiReportAnalyticsCtrl;
import com.tt.miniapp.msg.sync.ApiReportAppLogSyncCtrl;
import com.tt.miniapp.msg.sync.ApiSaveLogEventCtrl;
import com.tt.miniapp.msg.sync.ClearStorageSync;
import com.tt.miniapp.msg.sync.GetBatteryInfoSync;
import com.tt.miniapp.msg.sync.GetHostLaunchQuerySync;
import com.tt.miniapp.msg.sync.GetLaunchOptionsSync;
import com.tt.miniapp.msg.sync.GetStorageInfoSyncCtrl;
import com.tt.miniapp.msg.sync.GetStorageSyncCtrl;
import com.tt.miniapp.msg.sync.GetSystemInfoSyncCtrl;
import com.tt.miniapp.msg.sync.RemoveStorageSync;
import com.tt.miniapp.msg.sync.SetKeyboardValueSync;
import com.tt.miniapp.msg.sync.SetStorageSyncCtrl;
import com.tt.miniapp.msg.sync.SyncCallHostMethodCtrl;
import com.tt.miniapp.msg.sync.SyncMsgCtrl;
import com.tt.miniapp.route.RouteEventCtrl;
import com.tt.miniapp.util.NetUtil;
import com.tt.miniapp.util.TimeLogger;
import com.tt.miniapp.util.WebviewSchemaUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.game.GameModuleController;
import com.tt.miniapphost.monitor.AppBrandMonitor;
import com.tt.miniapphost.util.CharacterUtils;
import com.tt.miniapphost.util.DebugUtil;
import com.tt.option.e.e;
import com.tt.option.e.f;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsBridge implements j {
  public e mApiHandlerCallBack = new e() {
      public void callback(int param1Int, String param1String) {
        JsBridge.this.returnAsyncResult(param1Int, param1String);
      }
    };
  
  private a mApiRuntime;
  
  private c mAsyncApiHandleExecutor = new c() {
      public void scheduleHandle(Runnable param1Runnable) {
        JsBridge.this.asyncJsInvoke(new JsBridge.NativeApiEvent(param1Runnable));
      }
    };
  
  public List<NativeApiEvent> mBlockNativeApiEventList = new ArrayList<NativeApiEvent>();
  
  public final List<String> mInterceptEventListWhenBackgroundOverLimitTime = Arrays.asList(new String[] { "createInnerRequestTask", "createRequestTask", "createDownloadTask", "createSocketTask", "createUploadTask" });
  
  public volatile boolean mIsBlockingJsInvokeNativeApi;
  
  private a mJSCoreApiRuntime = new a() {
      public a getContext() {
        return (a)AppbrandApplicationImpl.getInst().getMiniAppContext();
      }
      
      public b handleApiInvoke(a param1a) {
        AppbrandApplication.getInst().getJsBridge().sendMsgToJsCore(param1a.b, param1a.a().toString());
        return b.d;
      }
      
      public boolean isDestroyed() {
        return false;
      }
    };
  
  private a mJSCoreApiRuntimeOnArrayBuffer = new a() {
      public a getContext() {
        return (a)AppbrandApplicationImpl.getInst().getMiniAppContext();
      }
      
      public b handleApiInvoke(a param1a) {
        AppbrandApplication.getInst().getJsBridge().sendMsgToJsCore2(param1a.b, (g)new CommonApiOutputParam((param1a.a()).a));
        return b.d;
      }
      
      public boolean isDestroyed() {
        return false;
      }
    };
  
  private JSMsgHandler mJsMsgHandler;
  
  private final JsRuntime mJsRuntime;
  
  private volatile JsTimerHandler mJsTimerHanlder;
  
  public final List<String> mNotBlockAsyncApiListWhenBackground = Arrays.asList(new String[] { "setBgAudioState", "getBgAudioState", "operateBgAudio", "reportTimelinePoints", "systemLog", "operateSocketTask" });
  
  public JsBridge(JsRuntime paramJsRuntime) {
    this.mJsRuntime = paramJsRuntime;
    initBlockJsInvokeNativeApiFeature();
    a a1 = (a)AppbrandApplicationImpl.getInst().getMiniAppContext().getService(a.class);
    this.mApiRuntime = a1.a();
    a1.a(new b[] { (b)new OverridePreHandler(this.mApiRuntime) });
  }
  
  private void asyncJsInvoke(String paramString1, String paramString2, int paramInt) {
    preHandleJscAsyncPayApi(paramString1);
    NativeApiEvent nativeApiEvent = new NativeApiEvent(paramString1, paramString2, paramInt, this.mApiHandlerCallBack);
    if (this.mIsBlockingJsInvokeNativeApi && !this.mNotBlockAsyncApiListWhenBackground.contains(paramString1)) {
      this.mBlockNativeApiEventList.add(nativeApiEvent);
      return;
    } 
    asyncJsInvoke(nativeApiEvent);
  }
  
  private void asyncJsInvokeV2(String paramString, f paramf, int paramInt) {
    NativeApiEvent nativeApiEvent = new NativeApiEvent(paramString, paramf, paramInt);
    if (this.mIsBlockingJsInvokeNativeApi && !this.mNotBlockAsyncApiListWhenBackground.contains(paramString)) {
      this.mBlockNativeApiEventList.add(nativeApiEvent);
      return;
    } 
    asyncJsInvoke(nativeApiEvent);
  }
  
  private JSMsgHandler getJsMsgHandler() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mJsMsgHandler : Lcom/tt/miniapp/jsbridge/JsBridge$JSMsgHandler;
    //   4: ifnonnull -> 47
    //   7: ldc com/tt/miniapp/jsbridge/JsBridge$JSMsgHandler
    //   9: monitorenter
    //   10: aload_0
    //   11: getfield mJsMsgHandler : Lcom/tt/miniapp/jsbridge/JsBridge$JSMsgHandler;
    //   14: ifnonnull -> 35
    //   17: aload_0
    //   18: new com/tt/miniapp/jsbridge/JsBridge$JSMsgHandler
    //   21: dup
    //   22: invokestatic getDefaultHandlerThread : ()Landroid/os/HandlerThread;
    //   25: invokevirtual getLooper : ()Landroid/os/Looper;
    //   28: aconst_null
    //   29: invokespecial <init> : (Landroid/os/Looper;Lcom/tt/miniapp/jsbridge/JsBridge$1;)V
    //   32: putfield mJsMsgHandler : Lcom/tt/miniapp/jsbridge/JsBridge$JSMsgHandler;
    //   35: ldc com/tt/miniapp/jsbridge/JsBridge$JSMsgHandler
    //   37: monitorexit
    //   38: goto -> 47
    //   41: astore_1
    //   42: ldc com/tt/miniapp/jsbridge/JsBridge$JSMsgHandler
    //   44: monitorexit
    //   45: aload_1
    //   46: athrow
    //   47: aload_0
    //   48: getfield mJsMsgHandler : Lcom/tt/miniapp/jsbridge/JsBridge$JSMsgHandler;
    //   51: areturn
    // Exception table:
    //   from	to	target	type
    //   10	35	41	finally
    //   35	38	41	finally
    //   42	45	41	finally
  }
  
  private JsTimerHandler getJsTimerHandler() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mJsTimerHanlder : Lcom/tt/miniapp/jsbridge/JsTimerHandler;
    //   4: ifnonnull -> 50
    //   7: ldc com/tt/miniapp/jsbridge/JsTimerHandler
    //   9: monitorenter
    //   10: aload_0
    //   11: getfield mJsTimerHanlder : Lcom/tt/miniapp/jsbridge/JsTimerHandler;
    //   14: ifnonnull -> 38
    //   17: aload_0
    //   18: new com/tt/miniapp/jsbridge/JsTimerHandler
    //   21: dup
    //   22: invokestatic getDefaultHandlerThread : ()Landroid/os/HandlerThread;
    //   25: invokevirtual getLooper : ()Landroid/os/Looper;
    //   28: aload_0
    //   29: getfield mJsRuntime : Lcom/tt/miniapp/JsRuntime;
    //   32: invokespecial <init> : (Landroid/os/Looper;Lcom/tt/miniapp/JsRuntime;)V
    //   35: putfield mJsTimerHanlder : Lcom/tt/miniapp/jsbridge/JsTimerHandler;
    //   38: ldc com/tt/miniapp/jsbridge/JsTimerHandler
    //   40: monitorexit
    //   41: goto -> 50
    //   44: astore_1
    //   45: ldc com/tt/miniapp/jsbridge/JsTimerHandler
    //   47: monitorexit
    //   48: aload_1
    //   49: athrow
    //   50: aload_0
    //   51: getfield mJsTimerHanlder : Lcom/tt/miniapp/jsbridge/JsTimerHandler;
    //   54: areturn
    // Exception table:
    //   from	to	target	type
    //   10	38	44	finally
    //   38	41	44	finally
    //   45	48	44	finally
  }
  
  private void initBlockJsInvokeNativeApiFeature() {
    AppbrandApplicationImpl.getInst().getForeBackgroundManager().registerForeBackgroundListener((ForeBackgroundManager.ForeBackgroundListener)new ForeBackgroundManager.DefaultForeBackgroundListener() {
          public void onBackground() {
            AppBrandLogger.d("JsBridge", new Object[] { "onBackground" });
            if (!JsBridge.this.mIsBlockingJsInvokeNativeApi)
              synchronized (JsBridge.this) {
                JsBridge.this.mIsBlockingJsInvokeNativeApi = true;
              }  
            AppBrandLogger.d("JsBridge", new Object[] { "mIsBlockingJsInvokeNativeApi", Boolean.valueOf(this.this$0.mIsBlockingJsInvokeNativeApi) });
          }
          
          public void onForeground() {
            AppBrandLogger.d("JsBridge", new Object[] { "onForeground" });
            if (JsBridge.this.mIsBlockingJsInvokeNativeApi)
              synchronized (JsBridge.this) {
                JsBridge.this.mIsBlockingJsInvokeNativeApi = false;
                for (JsBridge.NativeApiEvent nativeApiEvent : JsBridge.this.mBlockNativeApiEventList)
                  JsBridge.this.asyncJsInvoke(nativeApiEvent); 
                JsBridge.this.mBlockNativeApiEventList.clear();
              }  
            AppBrandLogger.d("JsBridge", new Object[] { "mIsBlockingJsInvokeNativeApi", Boolean.valueOf(this.this$0.mIsBlockingJsInvokeNativeApi) });
          }
        });
  }
  
  private boolean interceptByBackground(String paramString) {
    return (this.mInterceptEventListWhenBackgroundOverLimitTime.contains(paramString) && AppbrandApplicationImpl.getInst().getForeBackgroundManager().isStayBackgroundOverLimitTime());
  }
  
  private void preHandleJscAsyncPayApi(String paramString) {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual hashCode : ()I
    //   4: istore_2
    //   5: iload_2
    //   6: ldc -2056950218
    //   8: if_icmpeq -> 34
    //   11: iload_2
    //   12: ldc 1652140151
    //   14: if_icmpeq -> 20
    //   17: goto -> 48
    //   20: aload_1
    //   21: ldc 'requestPayment'
    //   23: invokevirtual equals : (Ljava/lang/Object;)Z
    //   26: ifeq -> 48
    //   29: iconst_0
    //   30: istore_2
    //   31: goto -> 50
    //   34: aload_1
    //   35: ldc 'requestWXPayment'
    //   37: invokevirtual equals : (Ljava/lang/Object;)Z
    //   40: ifeq -> 48
    //   43: iconst_1
    //   44: istore_2
    //   45: goto -> 50
    //   48: iconst_m1
    //   49: istore_2
    //   50: iload_2
    //   51: ifeq -> 60
    //   54: iload_2
    //   55: iconst_1
    //   56: if_icmpeq -> 60
    //   59: return
    //   60: new com/tt/miniapp/jsbridge/JsBridge$11
    //   63: dup
    //   64: aload_0
    //   65: invokespecial <init> : (Lcom/tt/miniapp/jsbridge/JsBridge;)V
    //   68: invokestatic shortIO : ()Lcom/storage/async/Scheduler;
    //   71: invokestatic runOnWorkThread : (Lcom/storage/async/Action;Lcom/storage/async/Scheduler;)V
    //   74: return
  }
  
  public void asyncJsInvoke(NativeApiEvent paramNativeApiEvent) {
    getJsMsgHandler().obtainMessage(1, paramNativeApiEvent).sendToTarget();
  }
  
  @Jscore(jsfunctionname = "call")
  public JsObject call(String paramString, JsObject paramJsObject, int paramInt) {
    ApiCallbackData apiCallbackData;
    FileSystemManagerSyncV2 fileSystemManagerSyncV2;
    AppBrandLogger.d("JsBridge", new Object[] { "call event ", paramString, " params ", paramJsObject, " callbackId ", Integer.valueOf(paramInt) });
    if (ApiPermissionManager.intercept(paramString, paramInt))
      return null; 
    ApiJsExecuteContext apiJsExecuteContext = new ApiJsExecuteContext(paramJsObject);
    a a1 = this.mApiRuntime;
    a.b b2 = a.b.a(this.mJSCoreApiRuntimeOnArrayBuffer, paramString, (a)new MiniAppApiInvokeParam(ApiParamParser.convertArrayBufferToJSONObject(paramString, apiJsExecuteContext))).a(this.mAsyncApiHandleExecutor, new JSCoreAsyncApiOnArrayCallbackExecutor(paramInt));
    b2.a = true;
    b b1 = a1.handleApiInvoke(b2.a());
    if (b1.a) {
      apiCallbackData = b1.b;
      if (apiCallbackData == null) {
        AppBrandLogger.d("JsBridge", new Object[] { "ApiService handle asyncEvent:", paramString });
        return null;
      } 
      JsObject jsObject = ApiParamParser.createResponseParamFromCommon(paramString, new CommonApiOutputParam(apiCallbackData.a), apiJsExecuteContext);
      apiJsExecuteContext.release();
      if (DebugUtil.debug())
        AppBrandLogger.d("JsBridge", new Object[] { "ApiService handle syncEvent:", paramString, "result:", apiCallbackData.a.toString() }); 
      return jsObject;
    } 
    f f = ApiParamParser.parse(paramString, apiJsExecuteContext);
    byte b = -1;
    int i = paramString.hashCode();
    if (i != 1713034038) {
      if (i != 1856784820) {
        if (i == 2112368109 && paramString.equals("readFileSync"))
          b = 1; 
      } else if (paramString.equals("createSocketTask")) {
        b = 2;
      } 
    } else if (paramString.equals("writeFileSync")) {
      b = 0;
    } 
    if (b != 0 && b != 1) {
      if (b != 2) {
        b1 = null;
      } else {
        CreateSocketTaskSyncV2 createSocketTaskSyncV2 = new CreateSocketTaskSyncV2(f);
      } 
    } else {
      fileSystemManagerSyncV2 = new FileSystemManagerSyncV2(f, paramString);
    } 
    if (fileSystemManagerSyncV2 != null) {
      String str;
      if (interceptByBackground(paramString)) {
        AppBrandLogger.d("JsBridge", new Object[] { "interceptByBackground ", paramString });
        str = fileSystemManagerSyncV2.makeMsgWithExtraInfo(false, "app in background");
        JsObject jsObject1 = apiCallbackData.getScopedContext().createObject();
        jsObject1.set("errMsg", str);
        monitorInvokeApiFailed(paramString, "call", str, 2);
        apiJsExecuteContext.release();
        return jsObject1;
      } 
      JsObject jsObject = ApiParamParser.createResponseParam(paramString, str.act(), apiJsExecuteContext);
      if (jsObject != null) {
        str = jsObject.getString("errMsg");
        if (!TextUtils.isEmpty(str) && str.contains("fail"))
          monitorInvokeApiFailed(paramString, "call", str, 2); 
      } 
      apiJsExecuteContext.release();
      return jsObject;
    } 
    asyncJsInvokeV2(paramString, f, paramInt);
    return null;
  }
  
  @Jscore(jsfunctionname = "clearTimer")
  public void clearTimer(String paramString, int paramInt) {
    AppBrandLogger.d("JsBridge", new Object[] { "clearTimer timerType ", paramString, " timerId ", Integer.valueOf(paramInt) });
    getJsTimerHandler().clearTimer(paramString, paramInt);
  }
  
  public a getJSCoreApiRuntime() {
    return this.mJSCoreApiRuntime;
  }
  
  @Jscore(jsfunctionname = "invoke")
  public String invoke(String paramString1, String paramString2, int paramInt) {
    String str;
    SyncMsgCtrl syncMsgCtrl1;
    AppBrandLogger.d("JsBridge", new Object[] { "invoke event ", paramString1, " param ", paramString2, " callbackId ", Integer.valueOf(paramInt) });
    if (ApiPermissionManager.intercept(paramString1, paramInt))
      return CharacterUtils.empty(); 
    b b1 = this.mApiRuntime.handleApiInvoke(a.b.a(this.mJSCoreApiRuntime, paramString1, (a)new MiniAppApiInvokeParam(paramString2)).a(this.mAsyncApiHandleExecutor, new JSCoreAsyncApiCallbackExecutor(paramInt)).a());
    if (b1.a) {
      ApiCallbackData apiCallbackData = b1.b;
      if (apiCallbackData == null) {
        AppBrandLogger.d("JsBridge", new Object[] { "ApiService handle asyncEvent:", paramString1 });
        return CharacterUtils.empty();
      } 
      str = apiCallbackData.toString();
      AppBrandLogger.d("JsBridge", new Object[] { "ApiService handle syncEvent:", paramString1, "result:", str });
      return str;
    } 
    b b2 = null;
    if (TextUtils.equals(paramString1, "getStorageSync")) {
      GetStorageSyncCtrl getStorageSyncCtrl = new GetStorageSyncCtrl(str);
    } else if (TextUtils.equals(paramString1, "setStorageSync")) {
      SetStorageSyncCtrl setStorageSyncCtrl = new SetStorageSyncCtrl(str);
    } else if (TextUtils.equals(paramString1, "clearStorageSync")) {
      ClearStorageSync clearStorageSync = new ClearStorageSync(str);
    } else if (TextUtils.equals(paramString1, "removeStorageSync")) {
      RemoveStorageSync removeStorageSync = new RemoveStorageSync(str);
    } else if (TextUtils.equals(paramString1, "getStorageInfoSync")) {
      GetStorageInfoSyncCtrl getStorageInfoSyncCtrl = new GetStorageInfoSyncCtrl(str);
    } else if (TextUtils.equals(paramString1, "operateInternalStorageSync")) {
      ApiOperateInternalStorageSyncCtrl apiOperateInternalStorageSyncCtrl = new ApiOperateInternalStorageSyncCtrl(str);
    } else if (TextUtils.equals(paramString1, "getSystemInfoSync")) {
      GetSystemInfoSyncCtrl getSystemInfoSyncCtrl = new GetSystemInfoSyncCtrl(str);
    } else if (TextUtils.equals(paramString1, "createSocketTask")) {
      CreateSocketTaskSync createSocketTaskSync = new CreateSocketTaskSync(str);
    } else if (TextUtils.equals(paramString1, "getLaunchOptionsSync")) {
      GetLaunchOptionsSync getLaunchOptionsSync = new GetLaunchOptionsSync(str);
    } else if (TextUtils.equals(paramString1, "getBatteryInfoSync")) {
      GetBatteryInfoSync getBatteryInfoSync = new GetBatteryInfoSync(str);
    } else if (TextUtils.equals(paramString1, "readFileSync")) {
      FileSystemManagerSync fileSystemManagerSync = new FileSystemManagerSync(paramString1, str);
    } else if (TextUtils.equals(paramString1, "accessSync")) {
      FileSystemManagerSync fileSystemManagerSync = new FileSystemManagerSync(paramString1, str);
    } else if (TextUtils.equals(paramString1, "copyFileSync")) {
      FileSystemManagerSync fileSystemManagerSync = new FileSystemManagerSync(paramString1, str);
    } else if (TextUtils.equals(paramString1, "mkdirSync")) {
      FileSystemManagerSync fileSystemManagerSync = new FileSystemManagerSync(paramString1, str);
    } else if (TextUtils.equals(paramString1, "readdirSync")) {
      FileSystemManagerSync fileSystemManagerSync = new FileSystemManagerSync(paramString1, str);
    } else if (TextUtils.equals(paramString1, "renameSync")) {
      FileSystemManagerSync fileSystemManagerSync = new FileSystemManagerSync(paramString1, str);
    } else if (TextUtils.equals(paramString1, "rmdirSync")) {
      FileSystemManagerSync fileSystemManagerSync = new FileSystemManagerSync(paramString1, str);
    } else if (TextUtils.equals(paramString1, "statSync")) {
      FileSystemManagerSync fileSystemManagerSync = new FileSystemManagerSync(paramString1, str);
    } else if (TextUtils.equals(paramString1, "saveFileSync")) {
      FileSystemManagerSync fileSystemManagerSync = new FileSystemManagerSync(paramString1, str);
    } else if (TextUtils.equals(paramString1, "unlinkSync")) {
      FileSystemManagerSync fileSystemManagerSync = new FileSystemManagerSync(paramString1, str);
    } else if (TextUtils.equals(paramString1, "writeFileSync")) {
      FileSystemManagerSync fileSystemManagerSync = new FileSystemManagerSync(paramString1, str);
    } else if (TextUtils.equals(paramString1, "measureText")) {
      ApiCanvasSync apiCanvasSync = new ApiCanvasSync(paramString1, str);
    } else if (TextUtils.equals(paramString1, "operateRecorder")) {
      OperateAudioRecorderSyncCtrl operateAudioRecorderSyncCtrl = new OperateAudioRecorderSyncCtrl(str);
    } else if (TextUtils.equals(paramString1, "setKeyboardValue")) {
      SetKeyboardValueSync setKeyboardValueSync = new SetKeyboardValueSync(str);
    } else if (TextUtils.equals(paramString1, "protocolPathToAbsPath")) {
      FileSystemManagerSync fileSystemManagerSync = new FileSystemManagerSync(paramString1, str);
    } else if (TextUtils.equals(paramString1, "getAudioStateSync")) {
      GetAudioStateSyncCtrl getAudioStateSyncCtrl = new GetAudioStateSyncCtrl(str);
    } else if (TextUtils.equals(paramString1, "callHostMethodSync")) {
      SyncCallHostMethodCtrl syncCallHostMethodCtrl = new SyncCallHostMethodCtrl(str);
    } else if (TextUtils.equals(paramString1, "reportAnalytics")) {
      ApiReportAnalyticsCtrl apiReportAnalyticsCtrl = new ApiReportAnalyticsCtrl(paramString1, str);
    } else if (TextUtils.equals(paramString1, "getUsageRecord")) {
      ApiGetUsageRecordCtrl apiGetUsageRecordCtrl = new ApiGetUsageRecordCtrl(paramString1, str);
    } else if (TextUtils.equals(paramString1, "base64ToTempFilePathSync")) {
      ApiBase64ToTempFilePathSyncCtrl apiBase64ToTempFilePathSyncCtrl = new ApiBase64ToTempFilePathSyncCtrl(str);
    } else if (TextUtils.equals(paramString1, "getDxppTaskStatusSync")) {
      ApiGetDxppTaskStatus apiGetDxppTaskStatus = new ApiGetDxppTaskStatus(str);
    } else if (TextUtils.equals(paramString1, "createFollowButton")) {
      ApiCreateFollowButtonSync apiCreateFollowButtonSync = new ApiCreateFollowButtonSync(str);
    } else if (TextUtils.equals(paramString1, "getHostLaunchQuerySync")) {
      GetHostLaunchQuerySync getHostLaunchQuerySync = new GetHostLaunchQuerySync(str);
    } else if (TextUtils.equals(paramString1, "canLaunchAppSync")) {
      ApiCanLaunchAppSyncCtrl apiCanLaunchAppSyncCtrl = new ApiCanLaunchAppSyncCtrl(str);
    } else if (TextUtils.equals(paramString1, "operateInterstitialAd")) {
      ApiOperateInterstitialAdCtrl apiOperateInterstitialAdCtrl = new ApiOperateInterstitialAdCtrl(str);
    } else if (TextUtils.equals(paramString1, "operateBannerAd")) {
      b1 = b2;
      try {
        if (TextUtils.equals((new JSONObject(str)).getString("type"), "destroy"))
          ApiOperateBannerAdSyncCtrl apiOperateBannerAdSyncCtrl = new ApiOperateBannerAdSyncCtrl(str); 
      } catch (Exception exception) {
        b b = b2;
      } 
    } else if (TextUtils.equals(paramString1, "addAudioTrack")) {
      ApiAddAudioTrackCtrl apiAddAudioTrackCtrl = new ApiAddAudioTrackCtrl(str);
    } else if (TextUtils.equals(paramString1, "getAppbrandSettingsSync")) {
      ApiGetAppBrandSettingsCtrl apiGetAppBrandSettingsCtrl = new ApiGetAppBrandSettingsCtrl(str);
    } else if (TextUtils.equals(paramString1, "onBeforeCloseReturnSync")) {
      ApiCloseCallbackReturnCtrl apiCloseCallbackReturnCtrl = new ApiCloseCallbackReturnCtrl(str);
    } else if (TextUtils.equals(paramString1, "isInUserFavoritesSync")) {
      ApiIsInUserFavoritesSync apiIsInUserFavoritesSync = new ApiIsInUserFavoritesSync(str);
    } else if (TextUtils.equals(paramString1, "reportAppLog")) {
      ApiReportAppLogSyncCtrl apiReportAppLogSyncCtrl = new ApiReportAppLogSyncCtrl(str);
    } else if (TextUtils.equals(paramString1, "getPerformanceTimingSync")) {
      ApiGetPerformanceTimingSyncCtrl apiGetPerformanceTimingSyncCtrl = new ApiGetPerformanceTimingSyncCtrl(str);
    } else if (TextUtils.equals(paramString1, "getMenuButtonBoundingClientRect")) {
      ApiMenuButtonBoundingCtrl apiMenuButtonBoundingCtrl = new ApiMenuButtonBoundingCtrl(str);
    } else {
      b1 = b2;
      if (TextUtils.equals(paramString1, "saveLog"))
        ApiSaveLogEventCtrl apiSaveLogEventCtrl = new ApiSaveLogEventCtrl(str); 
    } 
    SyncMsgCtrl syncMsgCtrl2 = GameModuleController.inst().invokeSyncApi(paramString1, str, paramInt);
    if (syncMsgCtrl2 != null)
      syncMsgCtrl1 = syncMsgCtrl2; 
    syncMsgCtrl2 = syncMsgCtrl1;
    if (WebviewSchemaUtil.isLark()) {
      f.a a1 = AppbrandContext.getInst().getExtensionApiCreator();
      syncMsgCtrl2 = syncMsgCtrl1;
      if (a1 != null) {
        SyncMsgCtrl syncMsgCtrl = a1.a(paramString1, str);
        syncMsgCtrl2 = syncMsgCtrl1;
        if (syncMsgCtrl != null)
          syncMsgCtrl2 = syncMsgCtrl; 
      } 
    } 
    if (syncMsgCtrl2 != null) {
      if (interceptByBackground(paramString1)) {
        AppBrandLogger.d("JsBridge", new Object[] { "interceptByBackground ", paramString1 });
        return syncMsgCtrl2.makeMsgByExtraInfo(false, "app in background");
      } 
      TimeLogger.getInstance().logTimeDuration(new String[] { "JsBridge_beforeCallSyncAPI", paramString1 });
      String str1 = syncMsgCtrl2.act();
      TimeLogger.getInstance().logTimeDuration(new String[] { "JsBridge_afterCallSyncAPI", paramString1 });
      if (!TextUtils.isEmpty(str1) && str1.contains("fail")) {
        AppBrandLogger.e("JsBridge", new Object[] { "event == ", paramString1, ", params == ", str, "\n******************invoke sync return ", str1 });
        monitorInvokeApiFailed(paramString1, "invoke", str1, 1);
        return str1;
      } 
      AppBrandLogger.d("JsBridge", new Object[] { "invoke sync return ", str1 });
      return str1;
    } 
    asyncJsInvoke(paramString1, str, paramInt);
    return CharacterUtils.empty();
  }
  
  public void invokeApi(final String eventName, final g apiParam, final int callBackId) {
    this.mJsRuntime.executeInJsThread(new JsContext.ScopeCallback() {
          public void run(JsScopedContext param1JsScopedContext) {
            try {
              JsObject jsObject1 = ApiParamParser.createResponseParam(eventName, apiParam, new ApiJsExecuteContext((JsContext)param1JsScopedContext));
              if (jsObject1 != null) {
                String str = jsObject1.getString("errMsg");
                if (!TextUtils.isEmpty(str) && str.contains("fail"))
                  JsBridge.this.monitorInvokeApiFailed(eventName, "callHandler", str, 2); 
              } 
              JsObject jsObject2 = param1JsScopedContext.global().getObject("ttJSBridge");
              param1JsScopedContext.push(callBackId);
              param1JsScopedContext.push(jsObject1);
              jsObject2.callMethod("callHandler", 2);
              return;
            } catch (Exception exception) {
              DebugUtil.outputError("JsBridge", new Object[] { "returnAsyncResult fail", exception });
              JsBridge.this.monitorInvokeApiFailed(eventName, "callHandler", Log.getStackTraceString(exception), 2);
              return;
            } 
          }
        });
  }
  
  public void monitorInvokeApiFailed(String paramString1, String paramString2, String paramString3, int paramInt) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("eventName", paramString1);
      jSONObject.put("invokeMethodName", paramString2);
      jSONObject.put("errorMessage", paramString3);
      jSONObject.put("apiVersion", paramInt);
      AppBrandMonitor.statusRate("mp_invoke_api_failed", 7000, jSONObject);
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("JsBridge", new Object[] { exception });
      return;
    } 
  }
  
  @Jscore(jsfunctionname = "onDocumentReady")
  public void onDocumentReady() {}
  
  public void onHide() {
    if (this.mJsTimerHanlder != null)
      this.mJsTimerHanlder.onEnterBackground(); 
    RouteEventCtrl routeEventCtrl = AppbrandApplicationImpl.getInst().getRouteEventCtrl();
    if (routeEventCtrl != null)
      routeEventCtrl.onAppHide(); 
  }
  
  @Jscore(jsfunctionname = "onNetworkStatusChange")
  public void onNetworkStatusChange() {
    NetUtil.registerListener();
  }
  
  public void onShow() {
    if (this.mJsTimerHanlder != null)
      this.mJsTimerHanlder.onEnterForeground(); 
    RouteEventCtrl routeEventCtrl = AppbrandApplicationImpl.getInst().getRouteEventCtrl();
    if (routeEventCtrl != null)
      routeEventCtrl.onAppShow(); 
  }
  
  @Jscore(jsfunctionname = "publish")
  public String publish(String paramString1, String paramString2, String paramString3) {
    if (paramString2 != null && paramString2.contains("fail")) {
      AppBrandLogger.e("JsBridge", new Object[] { "event ", paramString1, " param ", paramString2, " webviewIds ", paramString3, new Throwable() });
    } else {
      AppBrandLogger.d("JsBridge", new Object[] { "event ", paramString1, " param ", paramString2, " webviewIds ", paramString3 });
    } 
    try {
      JSONArray jSONArray = new JSONArray(paramString3);
      int k = jSONArray.length();
      int i;
      for (i = 0; i < k; i++) {
        WebViewManager webViewManager = AppbrandApplicationImpl.getInst().getWebViewManager();
        if (webViewManager != null) {
          webViewManager.publish(jSONArray.getInt(i), paramString1, paramString2);
        } else {
          AppBrandLogger.d("JsBridge", new Object[] { "publish webViewManager == null " });
        } 
      } 
    } catch (Exception exception) {
      AppBrandLogger.stacktrace(6, "JsBridge", exception.getStackTrace());
    } 
    return null;
  }
  
  public void release() {
    getJsMsgHandler().sendEmptyMessage(2);
  }
  
  public void returnAsyncResult(final int callbackID, final String data) {
    if (!TextUtils.isEmpty(data) && data.contains(":fail")) {
      AppBrandLogger.e("JsBridge", new Object[] { "******************returnAsyncResult callbackID ", Integer.valueOf(callbackID), " data ", data, new Throwable() });
      monitorInvokeApiFailed("returnAsyncResult", "invokeHandler2", data, 1);
    } else {
      AppBrandLogger.d("JsBridge", new Object[] { "returnAsyncResult callbackID ", Integer.valueOf(callbackID), " data ", data });
    } 
    this.mJsRuntime.executeInJsThread(new JsContext.ScopeCallback() {
          public void run(JsScopedContext param1JsScopedContext) {
            try {
              JsObject jsObject = param1JsScopedContext.global().getObject("ttJSBridge");
              param1JsScopedContext.push(callbackID);
              param1JsScopedContext.push(data);
              jsObject.callMethod("invokeHandler", 2);
              return;
            } catch (Exception exception) {
              DebugUtil.outputError("JsBridge", new Object[] { "returnAsyncResult fail", exception });
              JsBridge.this.monitorInvokeApiFailed("returnAsyncResult", "invokeHandler2", Log.getStackTraceString(exception), 1);
              return;
            } 
          }
        });
  }
  
  public void sendMsgToJsCore(final String event, final String data) {
    if (data != null && data.contains("fail")) {
      AppBrandLogger.e("JsBridge", new Object[] { "publishToServer event ", event, " data ", data, new Throwable() });
      monitorInvokeApiFailed(event, "subscribeHandler2", data, 1);
    } else {
      AppBrandLogger.d("JsBridge", new Object[] { "publishToServer event ", event, " data ", data });
    } 
    this.mJsRuntime.executeInJsThread(new JsContext.ScopeCallback() {
          public void run(JsScopedContext param1JsScopedContext) {
            try {
              JsObject jsObject = param1JsScopedContext.global().getObject("ttJSBridge");
              param1JsScopedContext.push(event);
              param1JsScopedContext.push(data);
              jsObject.callMethod("subscribeHandler", 2);
              return;
            } catch (Exception exception) {
              DebugUtil.outputError("JsBridge", new Object[] { "sendMsgToJsCoreCall2 fail", exception });
              JsBridge.this.monitorInvokeApiFailed(event, "subscribeHandler", Log.getStackTraceString(exception), 1);
              return;
            } 
          }
        });
  }
  
  public void sendMsgToJsCore(String paramString1, String paramString2, int paramInt) {
    sendMsgToJsCore(paramString1, paramString2, paramInt, false);
  }
  
  public void sendMsgToJsCore(final String event, final String data, final int weviewId, boolean paramBoolean) {
    if (data != null && data.contains("fail")) {
      AppBrandLogger.e("JsBridge", new Object[] { "publishToServer event ", event, " data ", data, new Throwable() });
      monitorInvokeApiFailed(event, "subscribeHandler3", data, 1);
    } else {
      AppBrandLogger.d("JsBridge", new Object[] { "publishToServer event ", event, " data ", data });
    } 
    this.mJsRuntime.executeInJsThread(new JsContext.ScopeCallback() {
          public void run(JsScopedContext param1JsScopedContext) {
            try {
              JsObject jsObject = param1JsScopedContext.global().getObject("ttJSBridge");
              param1JsScopedContext.push(event);
              param1JsScopedContext.push(data);
              param1JsScopedContext.push(weviewId);
              jsObject.callMethod("subscribeHandler", 3);
              return;
            } catch (Exception exception) {
              DebugUtil.outputError("JsBridge", new Object[] { "sendMsgToJsCoreCall3 fail", exception });
              JsBridge.this.monitorInvokeApiFailed(event, "subscribeHandler3", Log.getStackTraceString(exception), 1);
              return;
            } 
          }
        }false, paramBoolean);
  }
  
  public void sendMsgToJsCore2(final String event, final g param) {
    this.mJsRuntime.executeInJsThread(new JsContext.ScopeCallback() {
          public void run(JsScopedContext param1JsScopedContext) {
            try {
              JsObject jsObject1 = ApiParamParser.createResponseParam(event, param, new ApiJsExecuteContext((JsContext)param1JsScopedContext));
              if (jsObject1 != null) {
                String str = jsObject1.getString("errMsg");
                if (!TextUtils.isEmpty(str) && str.contains("fail"))
                  JsBridge.this.monitorInvokeApiFailed(event, "subscribeHandler2", str, 2); 
              } 
              JsObject jsObject2 = param1JsScopedContext.global().getObject("ttJSBridge");
              param1JsScopedContext.push(event);
              param1JsScopedContext.push(jsObject1);
              jsObject2.callMethod("subscribeHandler", 2);
              return;
            } catch (Exception exception) {
              AppBrandLogger.e("JsBridge", new Object[] { exception });
              JsBridge.this.monitorInvokeApiFailed(event, "subscribeHandler2", Log.getStackTraceString(exception), 2);
              return;
            } 
          }
        });
  }
  
  @Jscore(jsfunctionname = "setTimer")
  public void setTimer(String paramString, int paramInt, long paramLong) {
    AppBrandLogger.d("JsBridge", new Object[] { "setTimer timerType ", paramString, " timerId ", Integer.valueOf(paramInt), "time ", Long.valueOf(paramLong) });
    getJsTimerHandler().setTimer(paramString, paramInt, paramLong);
  }
  
  class JSCoreAsyncApiCallbackExecutor implements b {
    private int mCallbackId;
    
    public JSCoreAsyncApiCallbackExecutor(int param1Int) {
      this.mCallbackId = param1Int;
    }
    
    public void executeCallback(ApiCallbackData param1ApiCallbackData) {
      if (DebugUtil.debug())
        AppBrandLogger.d("JsBridge", new Object[] { "ApiService async callback:", param1ApiCallbackData.toString() }); 
      JsBridge.this.mApiHandlerCallBack.callback(this.mCallbackId, param1ApiCallbackData.toString());
    }
  }
  
  class JSCoreAsyncApiOnArrayCallbackExecutor implements b {
    private int mCallbackId;
    
    public JSCoreAsyncApiOnArrayCallbackExecutor(int param1Int) {
      this.mCallbackId = param1Int;
    }
    
    public void executeCallback(ApiCallbackData param1ApiCallbackData) {
      if (DebugUtil.debug())
        AppBrandLogger.d("JsBridge", new Object[] { "ApiService async callback:", param1ApiCallbackData.toString() }); 
    }
  }
  
  static class JSMsgHandler extends Handler {
    private JSMsgHandler(Looper param1Looper) {
      super(param1Looper);
    }
    
    public void handleMessage(Message param1Message) {
      super.handleMessage(param1Message);
      int i = param1Message.what;
      if (i != 1) {
        if (i != 2)
          return; 
        AudioManager.getInst().releaseAllPlayers();
        return;
      } 
      JsBridge.NativeApiEvent nativeApiEvent = (JsBridge.NativeApiEvent)param1Message.obj;
      if (nativeApiEvent == null) {
        AppBrandMonitor.reportError("mp_special_error", "nativeApiEvent is null", param1Message.toString());
        return;
      } 
      if (nativeApiEvent.mRunnable != null) {
        nativeApiEvent.mRunnable.run();
        return;
      } 
      (new ApiInvokeCtrl(nativeApiEvent)).doAct();
    }
  }
  
  public static class NativeApiEvent {
    public String mApi;
    
    public e mApiHandlerCallback;
    
    public int mCallbackId;
    
    public f mInputParam;
    
    public String mParams;
    
    public Runnable mRunnable;
    
    public NativeApiEvent(Runnable param1Runnable) {
      this.mRunnable = param1Runnable;
    }
    
    public NativeApiEvent(String param1String, f param1f, int param1Int) {
      this.mApi = param1String;
      this.mInputParam = param1f;
      this.mCallbackId = param1Int;
    }
    
    public NativeApiEvent(String param1String1, String param1String2, int param1Int, e param1e) {
      this.mApi = param1String1;
      this.mParams = param1String2;
      this.mCallbackId = param1Int;
      this.mApiHandlerCallback = param1e;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\jsbridge\JsBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */