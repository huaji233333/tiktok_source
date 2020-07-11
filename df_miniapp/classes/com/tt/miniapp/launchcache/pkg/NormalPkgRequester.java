package com.tt.miniapp.launchcache.pkg;

import android.content.Context;
import android.os.SystemClock;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.debug.PerformanceService;
import com.tt.miniapp.errorcode.ErrorCodeUtil;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.launchcache.LaunchCacheCleanDataManager;
import com.tt.miniapp.launchcache.LaunchCacheDAO;
import com.tt.miniapp.launchcache.RequestType;
import com.tt.miniapp.manager.PreConnectCDNManager;
import com.tt.miniapp.streamloader.StreamLoadListener;
import com.tt.miniapp.streamloader.StreamLoader;
import com.tt.miniapp.ttapkgdecoder.TTAPkgInfo;
import com.tt.miniapp.util.TimeLogger;
import com.tt.miniapp.util.timeline.MpTimeLineReporter;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.util.TimeMeter;
import d.f.b.g;
import d.f.b.l;
import d.f.b.w;
import d.m.p;
import java.io.File;

public final class NormalPkgRequester extends BasePkgRequester {
  public static final Companion Companion = new Companion(null);
  
  public final AppbrandApplicationImpl mApp;
  
  public NormalPkgRequester(AppbrandApplicationImpl paramAppbrandApplicationImpl, Context paramContext) {
    super(paramContext, RequestType.normal);
    this.mApp = paramAppbrandApplicationImpl;
  }
  
  private final void loadLocalPkgNormal(AppInfoEntity paramAppInfoEntity, boolean paramBoolean, LaunchCacheDAO.CacheVersionDir paramCacheVersionDir, PkgRequestContext paramPkgRequestContext) {
    String str;
    ((TimeLogger)this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "NormalPkgRequester", "loadLocalPkgNormal", "isFirstLaunch:", String.valueOf(paramBoolean) });
    File file = paramCacheVersionDir.getInstallDir();
    if (paramBoolean) {
      str = "firstLaunchPreloadPkg";
    } else {
      str = "launchedLocalPkg";
    } 
    paramPkgRequestContext.setPkgFile(paramCacheVersionDir.getPkgFile());
    paramPkgRequestContext.setErrMsg("useLocalInstallApp");
    StreamLoader.streamLoadApp(paramAppInfoEntity, paramPkgRequestContext.getPkgFile(), file.getCanonicalPath(), str, paramCacheVersionDir.getCurrentSourceFlag(), paramBoolean, new StreamLoaderListenerAdapter(paramPkgRequestContext));
  }
  
  protected final boolean onLoadLocalPkg(PkgRequestContext paramPkgRequestContext) {
    // Byte code:
    //   0: aload_1
    //   1: ldc 'requestContext'
    //   3: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_1
    //   7: invokevirtual getAppInfo : ()Lcom/tt/miniapphost/entity/AppInfoEntity;
    //   10: astore #9
    //   12: getstatic com/tt/miniapp/launchcache/LaunchCacheDAO.INSTANCE : Lcom/tt/miniapp/launchcache/LaunchCacheDAO;
    //   15: astore #6
    //   17: aload_0
    //   18: invokevirtual getMContext : ()Landroid/content/Context;
    //   21: astore #7
    //   23: aload #9
    //   25: getfield appId : Ljava/lang/String;
    //   28: astore #8
    //   30: aload #8
    //   32: ldc 'appInfo.appId'
    //   34: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   37: aload #6
    //   39: aload #7
    //   41: aload #8
    //   43: invokevirtual getCacheAppIdDir : (Landroid/content/Context;Ljava/lang/String;)Lcom/tt/miniapp/launchcache/LaunchCacheDAO$CacheAppIdDir;
    //   46: astore #6
    //   48: aload #6
    //   50: invokevirtual lock : ()Lcom/tt/miniapp/launchcache/LaunchCacheDAO$LockObject;
    //   53: astore #10
    //   55: aload #10
    //   57: ifnonnull -> 112
    //   60: aload_1
    //   61: aload_0
    //   62: invokevirtual getMBeginRequestPkgTime : ()Lcom/tt/miniapphost/util/TimeMeter;
    //   65: invokestatic stop : (Lcom/tt/miniapphost/util/TimeMeter;)J
    //   68: invokevirtual setUseTime : (J)V
    //   71: getstatic com/tt/miniapp/errorcode/ErrorCode$MAIN.GET_LAUNCHCACHE_FILE_LOCK_FAIL : Lcom/tt/miniapp/errorcode/ErrorCode$MAIN;
    //   74: invokevirtual getCode : ()Ljava/lang/String;
    //   77: astore #6
    //   79: aload #6
    //   81: ldc 'ErrorCode.MAIN.GET_LAUNCHCACHE_FILE_LOCK_FAIL.code'
    //   83: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   86: aload_1
    //   87: aload #6
    //   89: invokevirtual setErrCode : (Ljava/lang/String;)V
    //   92: aload_1
    //   93: ldc 'requestNormal, get lock fail'
    //   95: invokevirtual setErrMsg : (Ljava/lang/String;)V
    //   98: aload_1
    //   99: sipush #6012
    //   102: invokevirtual setMonitorStatus : (I)V
    //   105: aload_0
    //   106: aload_1
    //   107: invokevirtual onRequestPkgFail : (Lcom/tt/miniapp/launchcache/pkg/PkgRequestContext;)V
    //   110: iconst_1
    //   111: ireturn
    //   112: aload #6
    //   114: aload #9
    //   116: getfield versionCode : J
    //   119: getstatic com/tt/miniapp/launchcache/RequestType.normal : Lcom/tt/miniapp/launchcache/RequestType;
    //   122: invokevirtual getCacheVersionDir : (JLcom/tt/miniapp/launchcache/RequestType;)Lcom/tt/miniapp/launchcache/LaunchCacheDAO$CacheVersionDir;
    //   125: astore #11
    //   127: aload #9
    //   129: getfield versionCode : J
    //   132: lstore #4
    //   134: aload #6
    //   136: invokevirtual listCacheVersionDirs : ()Ljava/util/List;
    //   139: invokeinterface iterator : ()Ljava/util/Iterator;
    //   144: astore #12
    //   146: aconst_null
    //   147: astore #7
    //   149: aconst_null
    //   150: astore #6
    //   152: iconst_0
    //   153: istore_2
    //   154: aload #12
    //   156: invokeinterface hasNext : ()Z
    //   161: ifeq -> 264
    //   164: aload #12
    //   166: invokeinterface next : ()Ljava/lang/Object;
    //   171: checkcast com/tt/miniapp/launchcache/LaunchCacheDAO$CacheVersionDir
    //   174: astore #8
    //   176: iload_2
    //   177: istore_3
    //   178: aload #8
    //   180: invokevirtual getRequestType : ()Lcom/tt/miniapp/launchcache/RequestType;
    //   183: getstatic com/tt/miniapp/launchcache/RequestType.normal : Lcom/tt/miniapp/launchcache/RequestType;
    //   186: if_acmpne -> 204
    //   189: iload_2
    //   190: istore_3
    //   191: aload #8
    //   193: invokevirtual getPkgFile : ()Ljava/io/File;
    //   196: invokevirtual exists : ()Z
    //   199: ifeq -> 204
    //   202: iconst_1
    //   203: istore_3
    //   204: iload_3
    //   205: istore_2
    //   206: aload #8
    //   208: invokevirtual getVersionCode : ()J
    //   211: lload #4
    //   213: lcmp
    //   214: ifne -> 154
    //   217: iload_3
    //   218: istore_2
    //   219: aload #8
    //   221: getstatic com/tt/miniapp/launchcache/StatusFlagType.Verified : Lcom/tt/miniapp/launchcache/StatusFlagType;
    //   224: invokevirtual checkStatusFlag : (Lcom/tt/miniapp/launchcache/StatusFlagType;)Z
    //   227: ifeq -> 154
    //   230: iload_3
    //   231: istore_2
    //   232: aload #8
    //   234: invokevirtual getPkgFile : ()Ljava/io/File;
    //   237: invokevirtual exists : ()Z
    //   240: ifeq -> 154
    //   243: aload #6
    //   245: ifnull -> 370
    //   248: iload_3
    //   249: istore_2
    //   250: aload #8
    //   252: invokevirtual getRequestType : ()Lcom/tt/miniapp/launchcache/RequestType;
    //   255: getstatic com/tt/miniapp/launchcache/RequestType.normal : Lcom/tt/miniapp/launchcache/RequestType;
    //   258: if_acmpne -> 154
    //   261: goto -> 370
    //   264: aload #6
    //   266: ifnull -> 379
    //   269: aload #6
    //   271: invokevirtual getRequestType : ()Lcom/tt/miniapp/launchcache/RequestType;
    //   274: getstatic com/tt/miniapp/launchcache/RequestType.normal : Lcom/tt/miniapp/launchcache/RequestType;
    //   277: if_acmpeq -> 379
    //   280: aload #6
    //   282: invokevirtual getPkgFile : ()Ljava/io/File;
    //   285: aload #11
    //   287: invokevirtual getPkgFile : ()Ljava/io/File;
    //   290: invokevirtual renameTo : (Ljava/io/File;)Z
    //   293: ifeq -> 317
    //   296: aload #11
    //   298: getstatic com/tt/miniapp/launchcache/StatusFlagType.Verified : Lcom/tt/miniapp/launchcache/StatusFlagType;
    //   301: invokevirtual setStatusFlagLocked : (Lcom/tt/miniapp/launchcache/StatusFlagType;)V
    //   304: aload #11
    //   306: aload #6
    //   308: invokevirtual getRequestType : ()Lcom/tt/miniapp/launchcache/RequestType;
    //   311: invokevirtual setSourceFlagLocked : (Lcom/tt/miniapp/launchcache/RequestType;)V
    //   314: goto -> 379
    //   317: aload #7
    //   319: ifnonnull -> 330
    //   322: aload #11
    //   324: getstatic com/tt/miniapp/launchcache/RequestType.normal : Lcom/tt/miniapp/launchcache/RequestType;
    //   327: invokevirtual setSourceFlagLocked : (Lcom/tt/miniapp/launchcache/RequestType;)V
    //   330: aload #10
    //   332: invokevirtual unlock : ()V
    //   335: aload #7
    //   337: ifnull -> 354
    //   340: aload_0
    //   341: aload #9
    //   343: iload_2
    //   344: iconst_1
    //   345: ixor
    //   346: aload #11
    //   348: aload_1
    //   349: invokespecial loadLocalPkgNormal : (Lcom/tt/miniapphost/entity/AppInfoEntity;ZLcom/tt/miniapp/launchcache/LaunchCacheDAO$CacheVersionDir;Lcom/tt/miniapp/launchcache/pkg/PkgRequestContext;)V
    //   352: iconst_1
    //   353: ireturn
    //   354: iconst_0
    //   355: ireturn
    //   356: astore_1
    //   357: aload #10
    //   359: invokevirtual unlock : ()V
    //   362: goto -> 367
    //   365: aload_1
    //   366: athrow
    //   367: goto -> 365
    //   370: aload #8
    //   372: astore #6
    //   374: iload_3
    //   375: istore_2
    //   376: goto -> 154
    //   379: aload #6
    //   381: astore #7
    //   383: goto -> 317
    // Exception table:
    //   from	to	target	type
    //   127	146	356	finally
    //   154	176	356	finally
    //   178	189	356	finally
    //   191	202	356	finally
    //   206	217	356	finally
    //   219	230	356	finally
    //   232	243	356	finally
    //   250	261	356	finally
    //   269	280	356	finally
    //   280	314	356	finally
    //   322	330	356	finally
  }
  
  protected final void onRequestPkgFail(PkgRequestContext paramPkgRequestContext) {
    l.b(paramPkgRequestContext, "requestContext");
    paramPkgRequestContext.setHttpStatusCode(-2);
    paramPkgRequestContext.setHttpContentLength(-2L);
    super.onRequestPkgFail(paramPkgRequestContext);
  }
  
  protected final void onRequestPkgSuccess(PkgRequestContext paramPkgRequestContext) {
    l.b(paramPkgRequestContext, "requestContext");
    if (!paramPkgRequestContext.isNetDownload()) {
      PkgService pkgService = (PkgService)this.mApp.getService(PkgService.class);
      File file = paramPkgRequestContext.getPkgFile();
      if (file == null)
        l.a(); 
      String str1 = file.getAbsolutePath();
      l.a(str1, "requestContext.pkgFile!!.absolutePath");
      pkgService.notifyLocalPkgReady(str1);
    } 
    AppInfoEntity appInfoEntity = paramPkgRequestContext.getAppInfo();
    LaunchCacheCleanDataManager launchCacheCleanDataManager = LaunchCacheCleanDataManager.INSTANCE;
    Context context = getMContext();
    String str = appInfoEntity.appId;
    l.a(str, "appInfo.appId");
    launchCacheCleanDataManager.manageCacheForLaunchSuccess(context, str, appInfoEntity.versionCode);
    super.onRequestPkgSuccess(paramPkgRequestContext);
  }
  
  protected final void onRequestSync(PkgRequestContext paramPkgRequestContext) {
    l.b(paramPkgRequestContext, "requestContext");
    ((TimeLogger)this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "NormalPkgRequester", "onRequestSync" });
    AppInfoEntity appInfoEntity = paramPkgRequestContext.getAppInfo();
    PreConnectCDNManager.inst().sortUrls(appInfoEntity.appUrls);
    LaunchCacheDAO launchCacheDAO = LaunchCacheDAO.INSTANCE;
    Context context = getMContext();
    String str = appInfoEntity.appId;
    l.a(str, "appInfo.appId");
    LaunchCacheDAO.CacheVersionDir cacheVersionDir = launchCacheDAO.getCacheAppIdDir(context, str).getCacheVersionDir(appInfoEntity.versionCode, getMRequestType());
    File file = cacheVersionDir.getInstallDir();
    paramPkgRequestContext.setDownloadUrl(appInfoEntity.getDefaultUrl());
    paramPkgRequestContext.setPkgFile(cacheVersionDir.getPkgFile());
    Event.builder("mp_download_start", appInfoEntity).kv("request_type", getMRequestType()).flush();
    PkgDownloadHelper.INSTANCE.updatePkgCompressType(appInfoEntity, getMRequestType());
    MpTimeLineReporter mpTimeLineReporter = (MpTimeLineReporter)this.mApp.getService(MpTimeLineReporter.class);
    mpTimeLineReporter.addPoint("request_ttpkg_begin", System.currentTimeMillis(), SystemClock.elapsedRealtime(), (new MpTimeLineReporter.ExtraBuilder()).kv("request_type", Integer.valueOf(0)).kv("url", paramPkgRequestContext.getDownloadUrl()).kv("pkg_compress_type", Integer.valueOf(PkgDownloadHelper.INSTANCE.getPkgCompressType(appInfoEntity.pkgCompressType))).build());
    TimeMeter timeMeter = TimeMeter.newAndStart();
    ((PkgService)this.mApp.getService(PkgService.class)).setDownloadTime(timeMeter);
    w.e e = new w.e();
    e.element = ((PerformanceService)this.mApp.getService(PerformanceService.class)).createPerformanceTimingObj("downloadPackage", System.currentTimeMillis());
    paramPkgRequestContext.setErrMsg("download & check success");
    StreamLoader.streamLoadApp(appInfoEntity, paramPkgRequestContext.getPkgFile(), file.getCanonicalPath(), "firstLaunchStreamPkg", getMRequestType(), true, new NormalPkgRequester$onRequestSync$1(mpTimeLineReporter, timeMeter, e, paramPkgRequestContext, paramPkgRequestContext));
  }
  
  public static final class Companion {
    private Companion() {}
  }
  
  public class StreamLoaderListenerAdapter implements StreamLoadListener {
    private TimeMeter mStartTime;
    
    private final PkgRequestContext requestContext;
    
    public StreamLoaderListenerAdapter(PkgRequestContext param1PkgRequestContext) {
      this.requestContext = param1PkgRequestContext;
      this.mStartTime = TimeMeter.newAndStart();
    }
    
    public void onDownloadProgress(int param1Int) {
      this.requestContext.getListener().onDownloadingProgress(param1Int, -1L);
    }
    
    public void onHeadInfoLoadSuccess() {
      this.requestContext.getListener().onInstallSuccess();
    }
    
    public void onRetry(String param1String1, String param1String2, String param1String3) {
      l.b(param1String1, "errorStr");
      l.b(param1String2, "failedUrl");
      l.b(param1String3, "nextUrl");
      PkgDownloadHelper.INSTANCE.uploadDownloadFailStat(this.requestContext.getAppInfo(), NormalPkgRequester.this.getMRequestType(), param1String2, TimeMeter.stop(this.mStartTime), param1String1, -2, -2L);
      this.mStartTime = TimeMeter.newAndStart();
      this.requestContext.setDownloadUrl(param1String3);
    }
    
    public void onStreamLoadError(int param1Int, String param1String) {
      l.b(param1String, "errMsg");
      this.requestContext.setUseTime(TimeMeter.stop(this.mStartTime));
      PkgRequestContext pkgRequestContext = this.requestContext;
      String str = ErrorCodeUtil.mappingStreamDownloadCode(param1Int);
      l.a(str, "ErrorCodeUtil.mappingStreamDownloadCode(errorCode)");
      pkgRequestContext.setErrCode(str);
      this.requestContext.setErrMsg(param1String);
      this.requestContext.setMonitorStatus(param1Int);
      NormalPkgRequester.this.onRequestPkgFail(this.requestContext);
    }
    
    public void onStreamLoadFinish(TTAPkgInfo param1TTAPkgInfo) {
      l.b(param1TTAPkgInfo, "info");
      this.requestContext.setUseTime(TimeMeter.stop(this.mStartTime));
      NormalPkgRequester.this.onFileReady(this.requestContext);
    }
  }
  
  public static final class NormalPkgRequester$onRequestSync$1 extends StreamLoaderListenerAdapter {
    NormalPkgRequester$onRequestSync$1(MpTimeLineReporter param1MpTimeLineReporter, TimeMeter param1TimeMeter, w.e param1e, PkgRequestContext param1PkgRequestContext1, PkgRequestContext param1PkgRequestContext2) {
      super(param1NormalPkgRequester, param1PkgRequestContext2);
    }
    
    public final void onRetry(String param1String1, String param1String2, String param1String3) {
      l.b(param1String1, "errorStr");
      l.b(param1String2, "failedUrl");
      l.b(param1String3, "nextUrl");
      this.$mpTimeLineReporter.addPoint("request_ttpkg_end");
      this.$downloadProfileTime.stop();
      ((PerformanceService.PerformanceTimingObj)this.$performanceTimingObj.element).setEndTime(System.currentTimeMillis());
      byte b = 2;
      if (!p.c(param1String3, "br", false, 2, null))
        b = 1; 
      this.$mpTimeLineReporter.addPoint("request_ttpkg_begin", System.currentTimeMillis(), SystemClock.elapsedRealtime(), (new MpTimeLineReporter.ExtraBuilder()).kv("request_type", Integer.valueOf(0)).kv("url", param1String3).kv("pkg_compress_type", Integer.valueOf(b)).build());
      this.$downloadProfileTime.start();
      this.$performanceTimingObj.element = ((PerformanceService)NormalPkgRequester.this.mApp.getService(PerformanceService.class)).createPerformanceTimingObj("downloadPackage", System.currentTimeMillis());
      super.onRetry(param1String1, param1String2, param1String3);
    }
    
    public final void onStreamLoadFinish(TTAPkgInfo param1TTAPkgInfo) {
      l.b(param1TTAPkgInfo, "info");
      this.$mpTimeLineReporter.addPoint("request_ttpkg_end");
      this.$downloadProfileTime.stop();
      ((PerformanceService.PerformanceTimingObj)this.$performanceTimingObj.element).setEndTime(System.currentTimeMillis());
      super.onStreamLoadFinish(param1TTAPkgInfo);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\pkg\NormalPkgRequester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */