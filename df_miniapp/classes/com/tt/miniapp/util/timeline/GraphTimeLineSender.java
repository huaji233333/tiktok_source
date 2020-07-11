package com.tt.miniapp.util.timeline;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import com.bytedance.sandboxapp.protocol.service.h.c;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.errorcode.NetErrorUtil;
import com.tt.miniapp.manager.NetManager;
import com.tt.miniapp.permission.PermissionsManager;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapp.util.DevicesUtil;
import com.tt.miniapp.util.NetUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.ParamManager;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.entity.InitParamsEntity;
import com.tt.miniapphost.util.DebugUtil;
import com.tt.miniapphost.util.IOUtils;
import com.tt.miniapphost.util.ProcessUtil;
import com.tt.option.q.i;
import com.tt.option.q.j;
import d.f.b.g;
import d.f.b.l;
import d.u;
import java.io.File;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class GraphTimeLineSender extends AbsTimeLineSender {
  public static final Companion Companion = new Companion(null);
  
  public static final int MSG_REPORT_GRAPH;
  
  private boolean isSwitchEnable;
  
  private String mReportUrl = "";
  
  private File mTmpCacheFile;
  
  static {
    AbsTimeLineSender.Companion companion = AbsTimeLineSender.Companion;
    int i = companion.getMSG_SEED$miniapp_release();
    companion.setMSG_SEED$miniapp_release(i + 1);
    MSG_REPORT_GRAPH = i;
  }
  
  public GraphTimeLineSender(Looper paramLooper) {
    super(paramLooper);
  }
  
  private final void clearGraphLog() {
    AppbrandContext appbrandContext = AppbrandContext.getInst();
    l.a(appbrandContext, "AppbrandContext.getInst()");
    Context context = (Context)appbrandContext.getApplicationContext();
    File file = new File(AppbrandConstant.getMiniAppRootDir(context), "tl_cache");
    if (!file.exists())
      file.mkdirs(); 
    StringBuilder stringBuilder = new StringBuilder("timeline_");
    stringBuilder.append(ProcessUtil.getCurProcessName(context));
    stringBuilder.append(".log");
    this.mTmpCacheFile = new File(file, stringBuilder.toString());
    file = this.mTmpCacheFile;
    if (file == null)
      l.a(); 
    if (file.exists())
      IOUtils.delete(this.mTmpCacheFile); 
  }
  
  private final void copyToAutoTestLog() {
    try {
      PermissionsManager permissionsManager = PermissionsManager.getInstance();
      AppbrandContext appbrandContext = AppbrandContext.getInst();
      l.a(appbrandContext, "AppbrandContext.getInst()");
      if (!permissionsManager.hasPermission((Context)appbrandContext.getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE")) {
        AppBrandLogger.e("GraphTimeLineSender", new Object[] { "Debug模式写启动日志失败，请检查文件写权限" });
        return;
      } 
      File file = new File(Environment.getExternalStorageDirectory(), "miniapp_launchlog");
      if (!file.exists() && !file.mkdirs()) {
        AppBrandLogger.e("GraphTimeLineSender", new Object[] { "Debug模式写启动日志失败，日志目录创建失败" });
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append((AppbrandApplicationImpl.getInst().getAppInfo()).appId);
      stringBuilder.append("_");
      stringBuilder.append(getMUniqueId());
      stringBuilder.append(".log");
      file = new File(file, stringBuilder.toString());
      IOUtils.delete(file);
      IOUtils.copyFile(this.mTmpCacheFile, file, false);
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("GraphTimeLineSender", new Object[] { "Debug模式拷贝启动日志失败", exception });
      return;
    } 
  }
  
  private final void doReportTimelineGraph(MpTimeLineReporter.Callback<String> paramCallback) {
    StringBuilder stringBuilder;
    String str;
    AppBrandLogger.d("GraphTimeLineSender", new Object[] { "doReportTimelineGraph" });
    File file = this.mTmpCacheFile;
    if (file == null || !file.exists()) {
      if (paramCallback != null)
        paramCallback.onError("file not exist"); 
      return;
    } 
    i i = generateRequest(readJsonArrayFile(file));
    j j = NetManager.getInst().request(i);
    l.a(j, "NetManager.getInst().request(tmaRequest)");
    if (NetErrorUtil.isSuccessful(j.b)) {
      String str1 = j.a();
      l.a(str1, "response.data");
      JSONObject jSONObject = new JSONObject(str1);
      if (jSONObject.optBoolean("result")) {
        str = jSONObject.optString("url");
        if (paramCallback != null) {
          l.a(str, "url");
          paramCallback.onSuccess(str);
        } 
        stringBuilder = new StringBuilder("url: ");
        stringBuilder.append(str);
        AppBrandLogger.d("GraphTimeLineSender", new Object[] { stringBuilder.toString() });
        return;
      } 
      if (stringBuilder != null) {
        stringBuilder.onError("server result is false");
        return;
      } 
      return;
    } 
    if (stringBuilder != null) {
      StringBuilder stringBuilder1 = new StringBuilder("response code: ");
      stringBuilder1.append(((j)str).b);
      stringBuilder.onError(stringBuilder1.toString());
    } 
  }
  
  private final i generateRequest(JSONArray paramJSONArray) {
    String str;
    AppbrandContext appbrandContext1 = AppbrandContext.getInst();
    l.a(appbrandContext1, "AppbrandContext.getInst()");
    Application application = appbrandContext1.getApplicationContext();
    AppbrandApplicationImpl appbrandApplicationImpl2 = AppbrandApplicationImpl.getInst();
    l.a(appbrandApplicationImpl2, "AppbrandApplicationImpl.getInst()");
    AppInfoEntity appInfoEntity = appbrandApplicationImpl2.getAppInfo();
    AppbrandContext appbrandContext2 = AppbrandContext.getInst();
    l.a(appbrandContext2, "AppbrandContext.getInst()");
    InitParamsEntity initParamsEntity = appbrandContext2.getInitParams();
    i i = new i(this.mReportUrl, "POST");
    i.a("Content-Type", "application/json");
    AppbrandContext appbrandContext3 = AppbrandContext.getInst();
    l.a(appbrandContext3, "AppbrandContext.getInst()");
    i.a("unique_id", appbrandContext3.getUniqueId());
    i.a("points", paramJSONArray);
    i.a("mp_id", appInfoEntity.appId);
    i.a("mp_name", appInfoEntity.appName);
    l.a(appInfoEntity, "appInfo");
    if (appInfoEntity.isGame()) {
      str = "micro_game";
    } else {
      str = "micro_app";
    } 
    i.a("param_for_special", str);
    Context context = (Context)application;
    i.a("lib_version", ParamManager.getJsSdkVersion(context));
    i.a("miniapp_sdk_version", ParamManager.getFullAppSdkVersion());
    i.a("dora_version", ParamManager.getDoraVersion());
    i.a("launch_from", appInfoEntity.launchFrom);
    l.a(initParamsEntity, "initParamsEntity");
    i.a("app_id", initParamsEntity.getAppId());
    i.a("app_version", initParamsEntity.getVersionCode());
    i.a("version_code", initParamsEntity.getUpdateVersionCode());
    i.a("channel", initParamsEntity.getChannel());
    AppbrandApplicationImpl appbrandApplicationImpl1 = AppbrandApplicationImpl.getInst();
    l.a(appbrandApplicationImpl1, "AppbrandApplicationImpl.getInst()");
    i.a("user_id", (((c)appbrandApplicationImpl1.getMiniAppContext().getService(c.class)).getHostAppUserInfo()).b);
    i.a("device_id", initParamsEntity.getDeviceId());
    i.a("device_model", Build.MODEL);
    i.a("os_type", "Android");
    i.a("os_version", DevicesUtil.getSystem());
    i.a("access", NetUtil.getNewNetType(context));
    return i;
  }
  
  private final JSONObject packData(JSONArray paramJSONArray) {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("points", paramJSONArray);
      jSONObject.put("unique_id", getMUniqueId());
      jSONObject.put("index", getMGroupIndex().getAndIncrement());
      jSONObject.put("cpu_time", SystemClock.elapsedRealtime());
      return jSONObject;
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable(getClass().getName(), "", (Throwable)jSONException);
      return jSONObject;
    } 
  }
  
  private final JSONArray readJsonArrayFile(File paramFile) {
    // Byte code:
    //   0: new org/json/JSONArray
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore #8
    //   9: new java/io/FileReader
    //   12: dup
    //   13: aload_1
    //   14: invokespecial <init> : (Ljava/io/File;)V
    //   17: astore_1
    //   18: new java/io/BufferedReader
    //   21: dup
    //   22: aload_1
    //   23: checkcast java/io/Reader
    //   26: invokespecial <init> : (Ljava/io/Reader;)V
    //   29: astore #5
    //   31: aload_1
    //   32: astore #4
    //   34: aload #5
    //   36: astore #6
    //   38: aload #5
    //   40: invokevirtual readLine : ()Ljava/lang/String;
    //   43: astore_3
    //   44: aload_1
    //   45: astore #4
    //   47: aload #5
    //   49: astore #6
    //   51: aload_3
    //   52: checkcast java/lang/CharSequence
    //   55: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   58: ifne -> 148
    //   61: aload_1
    //   62: astore #4
    //   64: aload #5
    //   66: astore #6
    //   68: new org/json/JSONArray
    //   71: dup
    //   72: new org/json/JSONObject
    //   75: dup
    //   76: aload_3
    //   77: invokespecial <init> : (Ljava/lang/String;)V
    //   80: ldc_w 'points'
    //   83: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
    //   86: invokespecial <init> : (Ljava/lang/String;)V
    //   89: astore_3
    //   90: iconst_0
    //   91: istore_2
    //   92: aload_1
    //   93: astore #4
    //   95: aload #5
    //   97: astore #6
    //   99: iload_2
    //   100: aload_3
    //   101: invokevirtual length : ()I
    //   104: if_icmpge -> 132
    //   107: aload_1
    //   108: astore #4
    //   110: aload #5
    //   112: astore #6
    //   114: aload #8
    //   116: aload_3
    //   117: iload_2
    //   118: invokevirtual get : (I)Ljava/lang/Object;
    //   121: invokevirtual put : (Ljava/lang/Object;)Lorg/json/JSONArray;
    //   124: pop
    //   125: iload_2
    //   126: iconst_1
    //   127: iadd
    //   128: istore_2
    //   129: goto -> 92
    //   132: aload_1
    //   133: astore #4
    //   135: aload #5
    //   137: astore #6
    //   139: aload #5
    //   141: invokevirtual readLine : ()Ljava/lang/String;
    //   144: astore_3
    //   145: goto -> 44
    //   148: aload_1
    //   149: astore #4
    //   151: aload #5
    //   153: astore #6
    //   155: aload #5
    //   157: invokevirtual close : ()V
    //   160: aload_1
    //   161: astore #4
    //   163: aload #5
    //   165: astore #6
    //   167: aload_1
    //   168: invokevirtual close : ()V
    //   171: goto -> 245
    //   174: astore #7
    //   176: aload_1
    //   177: astore_3
    //   178: aload #5
    //   180: astore_1
    //   181: goto -> 220
    //   184: astore_3
    //   185: aconst_null
    //   186: astore #6
    //   188: goto -> 267
    //   191: astore #7
    //   193: aconst_null
    //   194: astore #4
    //   196: aload_1
    //   197: astore_3
    //   198: aload #4
    //   200: astore_1
    //   201: goto -> 220
    //   204: astore_3
    //   205: aconst_null
    //   206: astore #6
    //   208: aload #6
    //   210: astore_1
    //   211: goto -> 267
    //   214: astore #7
    //   216: aconst_null
    //   217: astore_3
    //   218: aload_3
    //   219: astore_1
    //   220: aload_3
    //   221: astore #4
    //   223: aload_1
    //   224: astore #6
    //   226: ldc 'GraphTimeLineSender'
    //   228: iconst_1
    //   229: anewarray java/lang/Object
    //   232: dup
    //   233: iconst_0
    //   234: aload #7
    //   236: aastore
    //   237: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   240: aload_1
    //   241: astore #5
    //   243: aload_3
    //   244: astore_1
    //   245: aload #5
    //   247: checkcast java/io/Closeable
    //   250: invokestatic close : (Ljava/io/Closeable;)V
    //   253: aload_1
    //   254: checkcast java/io/Closeable
    //   257: invokestatic close : (Ljava/io/Closeable;)V
    //   260: aload #8
    //   262: areturn
    //   263: astore_3
    //   264: aload #4
    //   266: astore_1
    //   267: aload #6
    //   269: checkcast java/io/Closeable
    //   272: invokestatic close : (Ljava/io/Closeable;)V
    //   275: aload_1
    //   276: checkcast java/io/Closeable
    //   279: invokestatic close : (Ljava/io/Closeable;)V
    //   282: goto -> 287
    //   285: aload_3
    //   286: athrow
    //   287: goto -> 285
    // Exception table:
    //   from	to	target	type
    //   9	18	214	java/io/IOException
    //   9	18	204	finally
    //   18	31	191	java/io/IOException
    //   18	31	184	finally
    //   38	44	174	java/io/IOException
    //   38	44	263	finally
    //   51	61	174	java/io/IOException
    //   51	61	263	finally
    //   68	90	174	java/io/IOException
    //   68	90	263	finally
    //   99	107	174	java/io/IOException
    //   99	107	263	finally
    //   114	125	174	java/io/IOException
    //   114	125	263	finally
    //   139	145	174	java/io/IOException
    //   139	145	263	finally
    //   155	160	174	java/io/IOException
    //   155	160	263	finally
    //   167	171	174	java/io/IOException
    //   167	171	263	finally
    //   226	240	263	finally
  }
  
  public final boolean handleMessage(Message paramMessage) {
    l.b(paramMessage, "msg");
    if (paramMessage.what == MSG_REPORT_GRAPH) {
      Object object = paramMessage.obj;
      if (object != null) {
        doReportTimelineGraph((MpTimeLineReporter.Callback<String>)object);
      } else {
        throw new u("null cannot be cast to non-null type com.tt.miniapp.util.timeline.MpTimeLineReporter.Callback<kotlin.String>");
      } 
    } 
    return super.handleMessage(paramMessage);
  }
  
  public final boolean isEnableTrace() {
    return (this.isSwitchEnable || DebugUtil.debug());
  }
  
  public final boolean isReadySend() {
    return true;
  }
  
  public final boolean isThreshold() {
    return (getMStashPointList().size() >= 50);
  }
  
  public final void onAppInfoInited(AppInfoEntity paramAppInfoEntity) {
    l.b(paramAppInfoEntity, "appInfo");
    AppbrandContext appbrandContext = AppbrandContext.getInst();
    l.a(appbrandContext, "AppbrandContext.getInst()");
    this.isSwitchEnable = SettingsDAO.getBoolean((Context)appbrandContext.getApplicationContext(), false, new Enum[] { (Enum)Settings.TT_TIMELINE_SWITCH, (Enum)Settings.TimeLineSwitch.SWITCH });
    if (this.isSwitchEnable) {
      appbrandContext = AppbrandContext.getInst();
      l.a(appbrandContext, "AppbrandContext.getInst()");
      String str = SettingsDAO.getString((Context)appbrandContext.getApplicationContext(), "", new Enum[] { (Enum)Settings.TT_TIMELINE_SWITCH, (Enum)Settings.TimeLineSwitch.URL });
      l.a(str, "SettingsDAO.getString(Ap…tings.TimeLineSwitch.URL)");
      this.mReportUrl = str;
    } 
    if (isEnableTrace()) {
      clearGraphLog();
      return;
    } 
    release();
  }
  
  public final void realSendData(String paramString) {
    l.b(paramString, "content");
    try {
      realSendData(new JSONArray(paramString));
      return;
    } catch (JSONException jSONException) {
      return;
    } 
  }
  
  public final void realSendData(JSONArray paramJSONArray) {
    // Byte code:
    //   0: aload_1
    //   1: ldc_w 'ja'
    //   4: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   7: aload_0
    //   8: invokevirtual isEnableTrace : ()Z
    //   11: ifeq -> 127
    //   14: aconst_null
    //   15: astore #4
    //   17: aconst_null
    //   18: astore_2
    //   19: new java/io/FileWriter
    //   22: dup
    //   23: aload_0
    //   24: getfield mTmpCacheFile : Ljava/io/File;
    //   27: iconst_1
    //   28: invokespecial <init> : (Ljava/io/File;Z)V
    //   31: astore_3
    //   32: aload_3
    //   33: aload_0
    //   34: aload_1
    //   35: invokespecial packData : (Lorg/json/JSONArray;)Lorg/json/JSONObject;
    //   38: invokevirtual toString : ()Ljava/lang/String;
    //   41: checkcast java/lang/CharSequence
    //   44: invokevirtual append : (Ljava/lang/CharSequence;)Ljava/io/Writer;
    //   47: bipush #10
    //   49: invokevirtual append : (C)Ljava/io/Writer;
    //   52: pop
    //   53: aload_3
    //   54: checkcast java/io/Closeable
    //   57: invokestatic close : (Ljava/io/Closeable;)V
    //   60: goto -> 107
    //   63: astore_1
    //   64: aload_3
    //   65: astore_2
    //   66: goto -> 118
    //   69: astore_2
    //   70: aload_3
    //   71: astore_1
    //   72: aload_2
    //   73: astore_3
    //   74: goto -> 85
    //   77: astore_1
    //   78: goto -> 118
    //   81: astore_3
    //   82: aload #4
    //   84: astore_1
    //   85: aload_1
    //   86: astore_2
    //   87: ldc 'GraphTimeLineSender'
    //   89: iconst_1
    //   90: anewarray java/lang/Object
    //   93: dup
    //   94: iconst_0
    //   95: aload_3
    //   96: aastore
    //   97: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   100: aload_1
    //   101: checkcast java/io/Closeable
    //   104: invokestatic close : (Ljava/io/Closeable;)V
    //   107: invokestatic debug : ()Z
    //   110: ifeq -> 127
    //   113: aload_0
    //   114: invokespecial copyToAutoTestLog : ()V
    //   117: return
    //   118: aload_2
    //   119: checkcast java/io/Closeable
    //   122: invokestatic close : (Ljava/io/Closeable;)V
    //   125: aload_1
    //   126: athrow
    //   127: return
    // Exception table:
    //   from	to	target	type
    //   19	32	81	java/io/IOException
    //   19	32	77	finally
    //   32	53	69	java/io/IOException
    //   32	53	63	finally
    //   87	100	77	finally
  }
  
  public static final class Companion {
    private Companion() {}
    
    public final int getMSG_REPORT_GRAPH$miniapp_release() {
      return GraphTimeLineSender.MSG_REPORT_GRAPH;
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniap\\util\timeline\GraphTimeLineSender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */