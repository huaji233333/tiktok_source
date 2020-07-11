package com.tt.miniapp.manager;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.storage.async.Action;
import com.storage.async.Scheduler;
import com.tt.miniapp.AppConfig;
import com.tt.miniapp.database.ProcessSpData;
import com.tt.miniapp.mmkv.KVUtil;
import com.tt.miniapp.process.AppProcessManager;
import com.tt.miniapp.process.bridge.InnerMiniAppProcessBridge;
import com.tt.miniapp.settings.data.SettingsDAO;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.ttapkgdecoder.reader.TTAPkgReader;
import com.tt.miniapp.ttapkgdecoder.source.ISource;
import com.tt.miniapp.ttapkgdecoder.source.MappedByteBufferDiskSource;
import com.tt.miniapp.util.JsCoreUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.LaunchThreadPool;
import com.tt.miniapphost.entity.MicroSchemaEntity;
import com.tt.miniapphost.util.ProcessUtil;
import com.tt.option.q.i;
import com.tt.option.q.j;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PreTTRequestManager {
  private static Boolean isEnable;
  
  public static ConcurrentHashMap<String, j> prefetchCacheMap = new ConcurrentHashMap<String, j>();
  
  private static volatile boolean sAlreadyPrefetched;
  
  private static HashMap<String, TTRequestPrefetchInfo> sCachePrefetchInfo;
  
  private static volatile ThreadPoolExecutor sPrefetchExecutor;
  
  private static PriorityBlockingQueue<Runnable> taskQueue = new PriorityBlockingQueue<Runnable>();
  
  static {
    sCachePrefetchInfo = new HashMap<String, TTRequestPrefetchInfo>();
    sAlreadyPrefetched = false;
  }
  
  public static void addPrefetchInfoToCache(TTRequestPrefetchInfo paramTTRequestPrefetchInfo, String paramString) {
    sCachePrefetchInfo.put(paramString, paramTTRequestPrefetchInfo);
  }
  
  public static void clearPrefetchInfo(Context paramContext, String paramString) {
    AppBrandLogger.d("PreTTRequestManager", new Object[] { "clearing", paramString });
    if (!TextUtils.isEmpty(paramString)) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramString);
      stringBuilder.append("prefetch_info");
      paramString = stringBuilder.toString();
      KVUtil.getSharedPreferences(paramContext, "config_prefetch").edit().remove(paramString).apply();
    } 
  }
  
  public static void console(Object paramObject) {
    JSONArray jSONArray = new JSONArray();
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("method", "log");
      jSONObject.put("msg", paramObject);
      jSONArray.put(jSONObject);
    } catch (Exception exception) {
      AppBrandLogger.i("PreTTRequestManager", new Object[] { exception });
    } 
    AppBrandLogger.d("PreTTRequestManager", new Object[] { jSONArray });
    JsCoreUtils.sendVConsole(jSONArray);
  }
  
  private static ThreadPoolExecutor createExecutor(int paramInt) {
    return new ThreadPoolExecutor(0, paramInt, 30L, TimeUnit.SECONDS, taskQueue, ThreadUtil.threadFactory("TmaTTPrefetch Dispatcher", false));
  }
  
  public static void doPrefetch(Context paramContext, TTRequestPrefetchInfo paramTTRequestPrefetchInfo, String paramString, Map<String, Object> paramMap) {
    // Byte code:
    //   0: ldc com/tt/miniapp/manager/PreTTRequestManager
    //   2: monitorenter
    //   3: getstatic com/tt/miniapp/manager/PreTTRequestManager.sAlreadyPrefetched : Z
    //   6: istore #4
    //   8: iload #4
    //   10: ifeq -> 17
    //   13: ldc com/tt/miniapp/manager/PreTTRequestManager
    //   15: monitorexit
    //   16: return
    //   17: aload_2
    //   18: ifnonnull -> 39
    //   21: ldc 'PreTTRequestManager'
    //   23: iconst_1
    //   24: anewarray java/lang/Object
    //   27: dup
    //   28: iconst_0
    //   29: ldc 'path is null ????'
    //   31: aastore
    //   32: invokestatic e : (Ljava/lang/String;[Ljava/lang/Object;)V
    //   35: ldc com/tt/miniapp/manager/PreTTRequestManager
    //   37: monitorexit
    //   38: return
    //   39: iconst_1
    //   40: putstatic com/tt/miniapp/manager/PreTTRequestManager.sAlreadyPrefetched : Z
    //   43: aload_1
    //   44: getfield prefetches : Ljava/util/LinkedHashMap;
    //   47: aload_2
    //   48: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   51: checkcast java/util/List
    //   54: astore_1
    //   55: aload_1
    //   56: ifnull -> 158
    //   59: aload_1
    //   60: invokeinterface size : ()I
    //   65: ifle -> 158
    //   68: getstatic com/tt/miniapp/manager/PreTTRequestManager.sPrefetchExecutor : Ljava/util/concurrent/ThreadPoolExecutor;
    //   71: ifnonnull -> 83
    //   74: invokestatic getConcurrentCount : ()I
    //   77: invokestatic createExecutor : (I)Ljava/util/concurrent/ThreadPoolExecutor;
    //   80: putstatic com/tt/miniapp/manager/PreTTRequestManager.sPrefetchExecutor : Ljava/util/concurrent/ThreadPoolExecutor;
    //   83: ldc '\$\{([^}]*)\}'
    //   85: invokestatic compile : (Ljava/lang/String;)Ljava/util/regex/Pattern;
    //   88: astore_0
    //   89: aload_1
    //   90: invokeinterface iterator : ()Ljava/util/Iterator;
    //   95: astore_1
    //   96: aload_1
    //   97: invokeinterface hasNext : ()Z
    //   102: ifeq -> 158
    //   105: aload_1
    //   106: invokeinterface next : ()Ljava/lang/Object;
    //   111: checkcast java/lang/String
    //   114: aload_0
    //   115: aload_3
    //   116: invokestatic getFixedUrl : (Ljava/lang/String;Ljava/util/regex/Pattern;Ljava/util/Map;)Ljava/lang/String;
    //   119: astore_2
    //   120: aload_2
    //   121: ifnull -> 96
    //   124: new com/tt/miniapp/manager/PreTTRequestManager$PrefetchTask
    //   127: dup
    //   128: new com/tt/option/q/i
    //   131: dup
    //   132: aload_2
    //   133: ldc 'GET'
    //   135: iconst_0
    //   136: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;Z)V
    //   139: invokespecial <init> : (Lcom/tt/option/q/i;)V
    //   142: astore_2
    //   143: aload_2
    //   144: iconst_1
    //   145: putfield priority : I
    //   148: getstatic com/tt/miniapp/manager/PreTTRequestManager.sPrefetchExecutor : Ljava/util/concurrent/ThreadPoolExecutor;
    //   151: aload_2
    //   152: invokevirtual execute : (Ljava/lang/Runnable;)V
    //   155: goto -> 96
    //   158: ldc com/tt/miniapp/manager/PreTTRequestManager
    //   160: monitorexit
    //   161: return
    //   162: astore_0
    //   163: ldc com/tt/miniapp/manager/PreTTRequestManager
    //   165: monitorexit
    //   166: goto -> 171
    //   169: aload_0
    //   170: athrow
    //   171: goto -> 169
    // Exception table:
    //   from	to	target	type
    //   3	8	162	finally
    //   21	35	162	finally
    //   39	55	162	finally
    //   59	83	162	finally
    //   83	96	162	finally
    //   96	120	162	finally
    //   124	155	162	finally
  }
  
  public static int getConcurrentCount() {
    return SettingsDAO.getInt((Context)AppbrandContext.getInst().getApplicationContext(), 2, new Enum[] { (Enum)Settings.BDP_STARTPAGE_PREFETCH, (Enum)Settings.BdpStartpagePrefetchConfig.MAX_CONCURRENT_COUNT });
  }
  
  private static String getFixedUrl(String paramString, Pattern paramPattern, Map<String, Object> paramMap) {
    Matcher matcher = paramPattern.matcher(paramString);
    String str = paramString;
    if (paramString.contains("${")) {
      if (paramMap == null)
        return null; 
      while (true) {
        str = paramString;
        if (matcher.find()) {
          str = matcher.group();
          String str1 = matcher.group(1);
          if (paramMap.containsKey(str1)) {
            str1 = (String)paramMap.get(str1);
            if (str1 != null) {
              paramString = paramString.replace(str, str1.toString());
              continue;
            } 
          } 
          return null;
        } 
        break;
      } 
    } 
    return str;
  }
  
  public static j getFromCacheIfMatched(i parami) {
    for (Map.Entry<String, j> entry : prefetchCacheMap.entrySet()) {
      String str = (String)entry.getKey();
      if (parami.c.equals("GET") && str.equals(parami.f()))
        return (j)entry.getValue(); 
    } 
    for (Runnable runnable : taskQueue) {
      if (runnable instanceof PrefetchTask) {
        runnable = runnable;
        if (((PrefetchTask)runnable).request.f().equals(parami.f())) {
          runnable.doWait();
          if (((PrefetchTask)runnable).response.f == null)
            return ((PrefetchTask)runnable).response; 
        } 
      } 
    } 
    return null;
  }
  
  private static String getFromToLocal(Context paramContext, String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append("prefetch_info");
    paramString = stringBuilder.toString();
    return KVUtil.getSharedPreferences(paramContext, "config_prefetch").getString(paramString, "");
  }
  
  public static TTRequestPrefetchInfo getPrefetch(Context paramContext, String paramString) {
    // Byte code:
    //   0: ldc com/tt/miniapp/manager/PreTTRequestManager
    //   2: monitorenter
    //   3: getstatic com/tt/miniapp/manager/PreTTRequestManager.sCachePrefetchInfo : Ljava/util/HashMap;
    //   6: aload_1
    //   7: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   10: checkcast com/tt/miniapp/manager/PreTTRequestManager$TTRequestPrefetchInfo
    //   13: astore_3
    //   14: aload_3
    //   15: astore_2
    //   16: aload_3
    //   17: ifnonnull -> 26
    //   20: aload_0
    //   21: aload_1
    //   22: invokestatic getPrefetchInfoFromLocal : (Landroid/content/Context;Ljava/lang/String;)Lcom/tt/miniapp/manager/PreTTRequestManager$TTRequestPrefetchInfo;
    //   25: astore_2
    //   26: ldc com/tt/miniapp/manager/PreTTRequestManager
    //   28: monitorexit
    //   29: aload_2
    //   30: areturn
    //   31: astore_0
    //   32: ldc com/tt/miniapp/manager/PreTTRequestManager
    //   34: monitorexit
    //   35: aload_0
    //   36: athrow
    // Exception table:
    //   from	to	target	type
    //   3	14	31	finally
    //   20	26	31	finally
  }
  
  private static TTRequestPrefetchInfo getPrefetchInfoFromLocal(Context paramContext, String paramString) {
    try {
      String str = getFromToLocal(paramContext, paramString);
      if (!TextUtils.isEmpty(str))
        return str.equals("no_prefetch") ? null : TTRequestPrefetchInfo.fromJSON(new JSONObject(str)); 
    } catch (Exception exception) {
      AppBrandLogger.d("PreTTRequestManager", new Object[] { "getPrefetchInfoFromLocal error", exception.toString() });
    } 
    return null;
  }
  
  public static boolean isEnabled() {
    boolean bool1;
    Boolean bool4 = isEnable;
    if (bool4 != null)
      return bool4.booleanValue(); 
    Application application = AppbrandContext.getInst().getApplicationContext();
    Settings settings = Settings.BDP_STARTPAGE_PREFETCH;
    boolean bool2 = false;
    if (SettingsDAO.getInt((Context)application, 0, new Enum[] { (Enum)settings, (Enum)Settings.BdpStartpagePrefetchConfig.ENABLE }) == 1) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    if (bool1 == true)
      bool2 = true; 
    Boolean bool3 = Boolean.valueOf(bool2);
    isEnable = bool3;
    return bool3.booleanValue();
  }
  
  public static void parseAndSavePrefetchList(final Context context, final String appId, final File pkgFile) {
    if (!isEnabled())
      return; 
    ThreadUtil.runOnWorkThread(new Action() {
          public final void act() {
            try {
              PreTTRequestManager.TTRequestPrefetchInfo tTRequestPrefetchInfo;
              JSONObject jSONObject1 = new JSONObject(PreTTRequestManager.readAppConfigDirectly(pkgFile));
              String str = jSONObject1.optString("entryPagePath");
              JSONObject jSONObject2 = jSONObject1.optJSONObject("prefetches");
              jSONObject1 = null;
              if (jSONObject2 != null && jSONObject2.length() > 0 && !TextUtils.isEmpty(str)) {
                tTRequestPrefetchInfo = PreTTRequestManager.TTRequestPrefetchInfo.create(jSONObject2, str);
                PreTTRequestManager.savePrefetchInfo(context, tTRequestPrefetchInfo, appId);
              } else {
                PreTTRequestManager.savePrefetchInfo(context, null, appId);
              } 
              if (ProcessUtil.isMiniappProcess())
                PreTTRequestManager.addPrefetchInfoToCache(tTRequestPrefetchInfo, appId); 
              return;
            } catch (Exception exception) {
              AppBrandLogger.w("PreTTRequestManager", new Object[] { exception });
              return;
            } 
          }
        }(Scheduler)LaunchThreadPool.getInst());
  }
  
  public static void preload(Context paramContext) {
    isEnabled();
    getConcurrentCount();
  }
  
  public static String readAppConfigDirectly(File paramFile) {
    MappedByteBufferDiskSource mappedByteBufferDiskSource = new MappedByteBufferDiskSource(paramFile);
    try {
      TTAPkgReader tTAPkgReader1 = new TTAPkgReader((ISource)mappedByteBufferDiskSource);
      TTAPkgReader tTAPkgReader2 = tTAPkgReader1;
    } finally {
      null = null;
    } 
    try {
      AppBrandLogger.e("PreTTRequestManager", new Object[] { null });
      return null;
    } finally {
      if (paramFile != null)
        paramFile.release(); 
    } 
  }
  
  public static void saveAndStartPrefetch(final Context context, final AppConfig config, final String appId, final String scheme) {
    if (sAlreadyPrefetched || !isEnabled()) {
      AppBrandLogger.d("AlreadyPrefetched in current process ", new Object[] { Boolean.valueOf(sAlreadyPrefetched), " / prefetch  enable ", Boolean.valueOf(isEnabled()) });
      return;
    } 
    ThreadUtil.runOnWorkThread(new Action() {
          public final void act() {
            AppBrandLogger.d("PreTTRequestManager", new Object[] { "saveAndStartPrefetch", this.val$appId, this.val$config.getPrefetches() });
            JSONObject jSONObject = config.getPrefetches();
            if (jSONObject != null && jSONObject.length() > 0 && !TextUtils.isEmpty(config.mEntryPath)) {
              PreTTRequestManager.TTRequestPrefetchInfo tTRequestPrefetchInfo = PreTTRequestManager.TTRequestPrefetchInfo.create(config.getPrefetches(), config.mEntryPath);
              PreTTRequestManager.addPrefetchInfoToCache(tTRequestPrefetchInfo, appId);
              PreTTRequestManager.startPrefetch(context, scheme);
              PreTTRequestManager.savePrefetchInfo(context, tTRequestPrefetchInfo, appId);
              return;
            } 
            PreTTRequestManager.savePrefetchInfo(context, null, appId);
          }
        }(Scheduler)LaunchThreadPool.getInst());
  }
  
  public static void savePrefetchInfo(Context paramContext, TTRequestPrefetchInfo paramTTRequestPrefetchInfo, String paramString) {
    if (paramTTRequestPrefetchInfo != null) {
      savePrefetchInfoToLocal(paramContext, paramString, paramTTRequestPrefetchInfo.toJSON().toString());
      return;
    } 
    savePrefetchInfoToLocal(paramContext, paramString, "no_prefetch");
  }
  
  public static void savePrefetchInfoByPreloadProcess(String paramString1, String paramString2) {
    if (paramString2.equals("no_prefetch")) {
      addPrefetchInfoToCache(null, paramString1);
      return;
    } 
    try {
      addPrefetchInfoToCache(TTRequestPrefetchInfo.fromJSON(new JSONObject(paramString2)), paramString1);
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("PreTTRequestManager", new Object[] { exception });
      return;
    } 
  }
  
  public static void savePrefetchInfoToLocal(Context paramContext, String paramString1, String paramString2) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString1);
    stringBuilder.append("prefetch_info");
    String str = stringBuilder.toString();
    if (ProcessUtil.isMainProcess(paramContext)) {
      AppProcessManager.ProcessInfo processInfo = AppProcessManager.getPreloadProcessInfo();
      if (processInfo != null)
        InnerMiniAppProcessBridge.syncTTRequestPrefetchInfo(processInfo.mProcessIdentity, paramString1, paramString2); 
      KVUtil.getSharedPreferences(paramContext, "config_prefetch").edit().putString(str, paramString2).apply();
      return;
    } 
    ProcessSpData.saveString("config_prefetch", str, paramString2);
  }
  
  public static void startPrefetch(final Context context, final String schema) {
    if (!isEnabled()) {
      AppBrandLogger.d("prefetch not enable...", new Object[0]);
      return;
    } 
    if (!sAlreadyPrefetched && schema != null)
      ThreadUtil.runOnWorkThread(new Action() {
            public final void act() {
              MicroSchemaEntity microSchemaEntity = SchemeEntityHelper.parseFromScheme(schema);
              if (microSchemaEntity != null) {
                String str1 = microSchemaEntity.getAppId();
                String str2 = microSchemaEntity.getPath();
                PreTTRequestManager.TTRequestPrefetchInfo tTRequestPrefetchInfo = PreTTRequestManager.getPrefetch(context, str1);
                if (tTRequestPrefetchInfo == null) {
                  AppBrandLogger.d("PreTTRequestManager", new Object[] { "prefetch info is null do not prefetch ..." });
                  return;
                } 
                str1 = str2;
                if (TextUtils.isEmpty(str2)) {
                  str1 = tTRequestPrefetchInfo.entryPath;
                  AppBrandLogger.d("PreTTRequestManager", new Object[] { "use entry path", str1 });
                } 
                Map<String, Object> map = microSchemaEntity.getQuery();
                PreTTRequestManager.doPrefetch(context, tTRequestPrefetchInfo, str1, map);
              } 
            }
          }(Scheduler)LaunchThreadPool.getInst()); 
  }
  
  static class PrefetchTask implements Comparable<PrefetchTask>, Runnable {
    volatile boolean needNotify;
    
    int priority = 1;
    
    final i request;
    
    volatile boolean requesting;
    
    j response;
    
    public PrefetchTask(i param1i) {
      this.request = param1i;
    }
    
    public int compareTo(PrefetchTask param1PrefetchTask) {
      int k = this.priority;
      int m = param1PrefetchTask.priority;
      return (k > m) ? -1 : ((k < m) ? 1 : 0);
    }
    
    void doWait() {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: iconst_1
      //   4: putfield needNotify : Z
      //   7: aload_0
      //   8: invokevirtual wait : ()V
      //   11: goto -> 28
      //   14: astore_1
      //   15: ldc 'PreTTRequestManager'
      //   17: iconst_1
      //   18: anewarray java/lang/Object
      //   21: dup
      //   22: iconst_0
      //   23: aload_1
      //   24: aastore
      //   25: invokestatic w : (Ljava/lang/String;[Ljava/lang/Object;)V
      //   28: aload_0
      //   29: monitorexit
      //   30: return
      //   31: astore_1
      //   32: aload_0
      //   33: monitorexit
      //   34: aload_1
      //   35: athrow
      // Exception table:
      //   from	to	target	type
      //   2	7	31	finally
      //   7	11	14	java/lang/InterruptedException
      //   7	11	31	finally
      //   15	28	31	finally
      //   28	30	31	finally
      //   32	34	31	finally
    }
    
    public void run() {
      // Byte code:
      //   0: aload_0
      //   1: iconst_1
      //   2: putfield requesting : Z
      //   5: aload_0
      //   6: getfield request : Lcom/tt/option/q/i;
      //   9: invokevirtual f : ()Ljava/lang/String;
      //   12: astore_1
      //   13: ldc 'PreTTRequestManager'
      //   15: iconst_2
      //   16: anewarray java/lang/Object
      //   19: dup
      //   20: iconst_0
      //   21: ldc '开始预请求'
      //   23: aastore
      //   24: dup
      //   25: iconst_1
      //   26: aload_0
      //   27: getfield request : Lcom/tt/option/q/i;
      //   30: invokevirtual f : ()Ljava/lang/String;
      //   33: aastore
      //   34: invokestatic d : (Ljava/lang/String;[Ljava/lang/Object;)V
      //   37: new java/lang/StringBuilder
      //   40: dup
      //   41: ldc 'start prefetch...'
      //   43: invokespecial <init> : (Ljava/lang/String;)V
      //   46: astore_2
      //   47: aload_2
      //   48: aload_1
      //   49: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   52: pop
      //   53: aload_2
      //   54: invokevirtual toString : ()Ljava/lang/String;
      //   57: invokestatic console : (Ljava/lang/Object;)V
      //   60: new org/json/JSONObject
      //   63: dup
      //   64: invokespecial <init> : ()V
      //   67: astore_2
      //   68: aload_2
      //   69: ldc 'timeStamp'
      //   71: invokestatic currentTimeMillis : ()J
      //   74: invokevirtual put : (Ljava/lang/String;J)Lorg/json/JSONObject;
      //   77: pop
      //   78: aload_2
      //   79: ldc 'url'
      //   81: aload_1
      //   82: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      //   85: pop
      //   86: aload_0
      //   87: invokestatic getInst : ()Lcom/tt/miniapp/base/netrequest/RequestManagerV2;
      //   90: aload_0
      //   91: getfield request : Lcom/tt/option/q/i;
      //   94: ldc 'httpdns'
      //   96: invokevirtual doRequest : (Lcom/tt/option/q/i;Ljava/lang/String;)Lcom/tt/option/q/j;
      //   99: putfield response : Lcom/tt/option/q/j;
      //   102: aload_0
      //   103: getfield response : Lcom/tt/option/q/j;
      //   106: getfield f : Ljava/lang/Throwable;
      //   109: ifnonnull -> 145
      //   112: getstatic com/tt/miniapp/manager/PreTTRequestManager.prefetchCacheMap : Ljava/util/concurrent/ConcurrentHashMap;
      //   115: aload_1
      //   116: aload_0
      //   117: getfield response : Lcom/tt/option/q/j;
      //   120: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      //   123: pop
      //   124: aload_2
      //   125: ldc 'data'
      //   127: aload_0
      //   128: getfield response : Lcom/tt/option/q/j;
      //   131: invokevirtual a : ()Ljava/lang/String;
      //   134: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      //   137: pop
      //   138: aload_2
      //   139: invokestatic console : (Ljava/lang/Object;)V
      //   142: goto -> 185
      //   145: new java/lang/StringBuilder
      //   148: dup
      //   149: ldc 'error:'
      //   151: invokespecial <init> : (Ljava/lang/String;)V
      //   154: astore_1
      //   155: aload_1
      //   156: aload_0
      //   157: getfield response : Lcom/tt/option/q/j;
      //   160: getfield f : Ljava/lang/Throwable;
      //   163: invokevirtual toString : ()Ljava/lang/String;
      //   166: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   169: pop
      //   170: aload_2
      //   171: ldc 'data'
      //   173: aload_1
      //   174: invokevirtual toString : ()Ljava/lang/String;
      //   177: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
      //   180: pop
      //   181: aload_2
      //   182: invokestatic console : (Ljava/lang/Object;)V
      //   185: aload_0
      //   186: iconst_0
      //   187: putfield requesting : Z
      //   190: aload_0
      //   191: monitorenter
      //   192: aload_0
      //   193: getfield needNotify : Z
      //   196: ifeq -> 208
      //   199: aload_0
      //   200: invokevirtual notifyAll : ()V
      //   203: aload_0
      //   204: iconst_0
      //   205: putfield needNotify : Z
      //   208: aload_0
      //   209: monitorexit
      //   210: return
      //   211: astore_1
      //   212: aload_0
      //   213: monitorexit
      //   214: aload_1
      //   215: athrow
      //   216: astore_1
      //   217: ldc 'PreTTRequestManager'
      //   219: iconst_1
      //   220: anewarray java/lang/Object
      //   223: dup
      //   224: iconst_0
      //   225: aload_1
      //   226: aastore
      //   227: invokestatic i : (Ljava/lang/String;[Ljava/lang/Object;)V
      //   230: aload_0
      //   231: iconst_0
      //   232: putfield requesting : Z
      //   235: return
      // Exception table:
      //   from	to	target	type
      //   37	142	216	java/lang/Exception
      //   145	185	216	java/lang/Exception
      //   185	192	216	java/lang/Exception
      //   192	208	211	finally
      //   208	210	211	finally
      //   212	214	211	finally
      //   214	216	216	java/lang/Exception
    }
  }
  
  public static class TTRequestPrefetchInfo {
    boolean available;
    
    String entryPath = "";
    
    LinkedHashMap<String, ArrayList<String>> prefetches = new LinkedHashMap<String, ArrayList<String>>();
    
    static TTRequestPrefetchInfo create(JSONObject param1JSONObject, String param1String) {
      TTRequestPrefetchInfo tTRequestPrefetchInfo = new TTRequestPrefetchInfo();
      if (param1JSONObject != null) {
        tTRequestPrefetchInfo.entryPath = param1String;
        Iterator<String> iterator = param1JSONObject.keys();
        while (iterator.hasNext()) {
          String str = iterator.next();
          JSONArray jSONArray = param1JSONObject.optJSONArray(str);
          if (jSONArray != null && jSONArray.length() > 0) {
            ArrayList<String> arrayList = new ArrayList();
            for (int i = 0; i < jSONArray.length(); i++) {
              String str1 = jSONArray.optString(i);
              if (!TextUtils.isEmpty(str1))
                arrayList.add(str1); 
            } 
            tTRequestPrefetchInfo.prefetches.put(str, arrayList);
          } 
        } 
        tTRequestPrefetchInfo.available = true;
      } 
      return tTRequestPrefetchInfo;
    }
    
    static TTRequestPrefetchInfo fromJSON(JSONObject param1JSONObject) {
      TTRequestPrefetchInfo tTRequestPrefetchInfo = new TTRequestPrefetchInfo();
      try {
        tTRequestPrefetchInfo.entryPath = param1JSONObject.getString("entryPath");
        param1JSONObject = param1JSONObject.getJSONObject("prefetches");
        Iterator<String> iterator = param1JSONObject.keys();
        while (iterator.hasNext()) {
          String str = iterator.next();
          JSONArray jSONArray = param1JSONObject.getJSONArray(str);
          ArrayList<String> arrayList = new ArrayList();
          int j = jSONArray.length();
          for (int i = 0; i < j; i++)
            arrayList.add(jSONArray.getString(i)); 
          tTRequestPrefetchInfo.prefetches.put(str, arrayList);
        } 
      } catch (JSONException jSONException) {
        AppBrandLogger.d("PreTTRequestManager", new Object[] { jSONException });
      } 
      return tTRequestPrefetchInfo;
    }
    
    public JSONObject toJSON() {
      JSONObject jSONObject = new JSONObject();
      try {
        jSONObject.put("entryPath", this.entryPath);
        JSONObject jSONObject1 = new JSONObject();
        for (Map.Entry<String, ArrayList<String>> entry : this.prefetches.entrySet()) {
          String str = (String)entry.getKey();
          ArrayList arrayList = (ArrayList)entry.getValue();
          JSONArray jSONArray = new JSONArray();
          Iterator<String> iterator = arrayList.iterator();
          while (iterator.hasNext())
            jSONArray.put(iterator.next()); 
          jSONObject1.put(str, jSONArray);
        } 
        jSONObject.put("prefetches", jSONObject1);
        return jSONObject;
      } catch (Exception exception) {
        AppBrandLogger.d("PreTTRequestManager", new Object[] { exception });
        return jSONObject;
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\PreTTRequestManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */