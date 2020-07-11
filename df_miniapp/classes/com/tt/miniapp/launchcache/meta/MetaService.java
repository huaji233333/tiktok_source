package com.tt.miniapp.launchcache.meta;

import android.content.Context;
import android.text.TextUtils;
import com.storage.async.Action;
import com.storage.async.Scheduler;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandServiceManager;
import com.tt.miniapp.launchcache.RequestType;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.util.TimeLogger;
import com.tt.miniapp.util.timeline.MpTimeLineReporter;
import com.tt.miniapphost.LaunchThreadPool;
import com.tt.miniapphost.entity.AppInfoEntity;
import d.f.b.g;
import d.f.b.l;
import org.json.JSONObject;

public final class MetaService extends AppbrandServiceManager.ServiceBase {
  public static final Companion Companion = new Companion(null);
  
  public final MetaHolder mAppInfoHolder;
  
  public MetaService(AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramAppbrandApplicationImpl);
    this.mAppInfoHolder = new MetaHolder(paramAppbrandApplicationImpl);
  }
  
  private final void mpRequestAppInfoTimeline(AppInfoRequestResult paramAppInfoRequestResult, int paramInt) {
    MpTimeLineReporter mpTimeLineReporter = (MpTimeLineReporter)this.mApp.getService(MpTimeLineReporter.class);
    JSONObject jSONObject = (new MpTimeLineReporter.ExtraBuilder()).kv("from_process", Integer.valueOf(paramAppInfoRequestResult.fromProcess)).build();
    mpTimeLineReporter.addPoint("generate_meta_params_begin", paramAppInfoRequestResult.generateMetaParamsBegin, paramAppInfoRequestResult.generateMetaParamsBeginCpuTime, jSONObject);
    mpTimeLineReporter.addPoint("generate_meta_params_end", paramAppInfoRequestResult.generateMetaParamsEnd, paramAppInfoRequestResult.generateMetaParamsEndCpuTime, jSONObject);
    for (AppInfoRequestResult.RequestMetaRecord requestMetaRecord : paramAppInfoRequestResult.requestRecordList) {
      mpTimeLineReporter.addPoint("request_meta_begin", requestMetaRecord.startTimeStamp, requestMetaRecord.startCpuTime, (new MpTimeLineReporter.ExtraBuilder()).kv("pre_generate_ttcode", Integer.valueOf(0)).kv("url", requestMetaRecord.url).kv("from_process", Integer.valueOf(paramAppInfoRequestResult.fromProcess)).kv("request_type", Integer.valueOf(paramInt)).build());
      mpTimeLineReporter.addPoint("request_meta_end", requestMetaRecord.stopTimeStamp, requestMetaRecord.stopCpuTime, jSONObject);
    } 
  }
  
  public final void appInfoRequestResultAvailable(AppInfoRequestResult paramAppInfoRequestResult) {
    l.b(paramAppInfoRequestResult, "result");
    if (TextUtils.isEmpty(paramAppInfoRequestResult.appId))
      return; 
    this.mAppInfoHolder.netMetaAvailable(paramAppInfoRequestResult);
  }
  
  public final AppInfoRequestResult competeRequest(Context paramContext, AppInfoEntity paramAppInfoEntity, RequestType paramRequestType, int paramInt) {
    l.b(paramContext, "context");
    l.b(paramAppInfoEntity, "appInfo");
    l.b(paramRequestType, "requestType");
    AppInfoRequestResult appInfoRequestResult2 = this.mAppInfoHolder.tryTakeCachedNetMeta();
    AppInfoRequestResult appInfoRequestResult1 = appInfoRequestResult2;
    if (appInfoRequestResult2 == null) {
      ThreadUtil.runOnWorkThread(new MetaService$competeRequest$1(paramContext, paramAppInfoEntity, paramRequestType), ThreadPools.longIO());
      int i = 0;
      appInfoRequestResult1 = appInfoRequestResult2;
      while (i < 5) {
        AppInfoRequestResult appInfoRequestResult = this.mAppInfoHolder.blockTakeNetMeta(6000L);
        appInfoRequestResult1 = appInfoRequestResult;
        if (appInfoRequestResult == null) {
          ((TimeLogger)this.mApp.getService(TimeLogger.class)).logError(new String[] { "MetaService_competeRequestTimeOut", paramRequestType.name() });
          i++;
          appInfoRequestResult1 = appInfoRequestResult;
        } 
      } 
    } 
    if (appInfoRequestResult1 != null)
      mpRequestAppInfoTimeline(appInfoRequestResult1, paramInt); 
    return appInfoRequestResult1;
  }
  
  public final void requestAsyncMeta(Context paramContext, AppInfoRequestListener paramAppInfoRequestListener) {
    l.b(paramContext, "context");
    l.b(paramAppInfoRequestListener, "appInfoRequestListener");
    AppbrandApplicationImpl appbrandApplicationImpl = this.mApp;
    l.a(appbrandApplicationImpl, "mApp");
    AsyncMetaRequester asyncMetaRequester = new AsyncMetaRequester(appbrandApplicationImpl, paramContext);
    appbrandApplicationImpl = this.mApp;
    l.a(appbrandApplicationImpl, "mApp");
    AppInfoEntity appInfoEntity = appbrandApplicationImpl.getAppInfo();
    LaunchThreadPool launchThreadPool = LaunchThreadPool.getInst();
    l.a(launchThreadPool, "LaunchThreadPool.getInst()");
    asyncMetaRequester.request(appInfoEntity, (Scheduler)launchThreadPool, paramAppInfoRequestListener);
  }
  
  public final void requestNormalMeta(Context paramContext, AppInfoRequestListener paramAppInfoRequestListener) {
    l.b(paramContext, "context");
    l.b(paramAppInfoRequestListener, "appInfoRequestListener");
    AppbrandApplicationImpl appbrandApplicationImpl = this.mApp;
    l.a(appbrandApplicationImpl, "mApp");
    NormalMetaRequester normalMetaRequester = new NormalMetaRequester(appbrandApplicationImpl, paramContext);
    appbrandApplicationImpl = this.mApp;
    l.a(appbrandApplicationImpl, "mApp");
    AppInfoEntity appInfoEntity = appbrandApplicationImpl.getAppInfo();
    LaunchThreadPool launchThreadPool = LaunchThreadPool.getInst();
    l.a(launchThreadPool, "LaunchThreadPool.getInst()");
    normalMetaRequester.request(appInfoEntity, (Scheduler)launchThreadPool, paramAppInfoRequestListener);
  }
  
  public final RequestResultInfo tryFetchLocalMeta(Context paramContext, String paramString, RequestType paramRequestType) {
    l.b(paramContext, "context");
    l.b(paramString, "appId");
    l.b(paramRequestType, "requestType");
    return this.mAppInfoHolder.tryFetchLocalMeta(paramContext, paramString, paramRequestType);
  }
  
  public final void updateAppInfoAfterRequest(AppInfoEntity paramAppInfoEntity) {
    // Byte code:
    //   0: aload_1
    //   1: ldc_w 'newAppInfo'
    //   4: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   7: aload_0
    //   8: getfield mApp : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   11: astore #4
    //   13: aload #4
    //   15: ldc 'mApp'
    //   17: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   20: aload #4
    //   22: invokevirtual getAppInfo : ()Lcom/tt/miniapphost/entity/AppInfoEntity;
    //   25: astore #6
    //   27: aload #6
    //   29: getfield adlist : Ljava/util/ArrayList;
    //   32: astore #5
    //   34: aload #5
    //   36: ifnull -> 51
    //   39: aload #5
    //   41: astore #4
    //   43: aload #5
    //   45: invokevirtual size : ()I
    //   48: ifne -> 57
    //   51: aload_1
    //   52: getfield adlist : Ljava/util/ArrayList;
    //   55: astore #4
    //   57: ldc_w 'local_dev'
    //   60: aload #6
    //   62: getfield versionType : Ljava/lang/String;
    //   65: invokestatic a : (Ljava/lang/Object;Ljava/lang/Object;)Z
    //   68: ifeq -> 177
    //   71: aload #6
    //   73: ldc_w 'oldAppInfo'
    //   76: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   79: aload #6
    //   81: invokevirtual getDefaultUrl : ()Ljava/lang/String;
    //   84: astore #5
    //   86: aload #5
    //   88: checkcast java/lang/CharSequence
    //   91: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   94: ifne -> 177
    //   97: new java/net/URL
    //   100: dup
    //   101: aload #5
    //   103: invokespecial <init> : (Ljava/lang/String;)V
    //   106: invokevirtual getPath : ()Ljava/lang/String;
    //   109: astore #5
    //   111: aload #5
    //   113: ldc_w 'url.path'
    //   116: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   119: goto -> 127
    //   122: ldc_w ''
    //   125: astore #5
    //   127: aload #5
    //   129: ldc_w '.zip'
    //   132: iconst_0
    //   133: iconst_2
    //   134: aconst_null
    //   135: invokestatic c : (Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Object;)Z
    //   138: ifne -> 177
    //   141: aload #6
    //   143: invokevirtual getDefaultUrl : ()Ljava/lang/String;
    //   146: checkcast java/lang/CharSequence
    //   149: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   152: ifeq -> 164
    //   155: aload_1
    //   156: getfield appUrls : Ljava/util/List;
    //   159: astore #5
    //   161: goto -> 171
    //   164: aload #6
    //   166: getfield appUrls : Ljava/util/List;
    //   169: astore #5
    //   171: aload_1
    //   172: aload #5
    //   174: putfield appUrls : Ljava/util/List;
    //   177: aload_1
    //   178: aload #6
    //   180: getfield isNotRecordRecentUseApps : Z
    //   183: putfield isNotRecordRecentUseApps : Z
    //   186: aload #6
    //   188: getfield version : Ljava/lang/String;
    //   191: checkcast java/lang/CharSequence
    //   194: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   197: ifeq -> 209
    //   200: aload_1
    //   201: getfield version : Ljava/lang/String;
    //   204: astore #5
    //   206: goto -> 216
    //   209: aload #6
    //   211: getfield version : Ljava/lang/String;
    //   214: astore #5
    //   216: aload_1
    //   217: aload #5
    //   219: putfield version : Ljava/lang/String;
    //   222: aload #6
    //   224: getfield appId : Ljava/lang/String;
    //   227: checkcast java/lang/CharSequence
    //   230: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   233: ifeq -> 245
    //   236: aload_1
    //   237: getfield appId : Ljava/lang/String;
    //   240: astore #5
    //   242: goto -> 252
    //   245: aload #6
    //   247: getfield appId : Ljava/lang/String;
    //   250: astore #5
    //   252: aload_1
    //   253: aload #5
    //   255: putfield appId : Ljava/lang/String;
    //   258: aload #6
    //   260: getfield versionCode : J
    //   263: lconst_0
    //   264: lcmp
    //   265: ifne -> 276
    //   268: aload_1
    //   269: getfield versionCode : J
    //   272: lstore_2
    //   273: goto -> 282
    //   276: aload #6
    //   278: getfield versionCode : J
    //   281: lstore_2
    //   282: aload_1
    //   283: lload_2
    //   284: putfield versionCode : J
    //   287: aload_1
    //   288: aload #6
    //   290: getfield versionType : Ljava/lang/String;
    //   293: putfield versionType : Ljava/lang/String;
    //   296: aload_1
    //   297: aload #6
    //   299: getfield refererInfo : Ljava/lang/String;
    //   302: putfield refererInfo : Ljava/lang/String;
    //   305: aload_1
    //   306: aload #6
    //   308: getfield launchFrom : Ljava/lang/String;
    //   311: putfield launchFrom : Ljava/lang/String;
    //   314: aload_1
    //   315: aload #6
    //   317: getfield scene : Ljava/lang/String;
    //   320: putfield scene : Ljava/lang/String;
    //   323: aload_1
    //   324: aload #6
    //   326: getfield subScene : Ljava/lang/String;
    //   329: putfield subScene : Ljava/lang/String;
    //   332: aload_1
    //   333: aload #6
    //   335: getfield shareTicket : Ljava/lang/String;
    //   338: putfield shareTicket : Ljava/lang/String;
    //   341: aload_1
    //   342: aload #6
    //   344: getfield startPage : Ljava/lang/String;
    //   347: putfield startPage : Ljava/lang/String;
    //   350: aload_1
    //   351: aload #6
    //   353: getfield oriStartPage : Ljava/lang/String;
    //   356: putfield oriStartPage : Ljava/lang/String;
    //   359: aload_1
    //   360: aload #6
    //   362: getfield timelineServerUrl : Ljava/lang/String;
    //   365: putfield timelineServerUrl : Ljava/lang/String;
    //   368: aload_1
    //   369: aload #6
    //   371: getfield session : Ljava/lang/String;
    //   374: putfield session : Ljava/lang/String;
    //   377: aload_1
    //   378: aload #6
    //   380: getfield gtoken : Ljava/lang/String;
    //   383: putfield gtoken : Ljava/lang/String;
    //   386: aload_1
    //   387: aload #6
    //   389: getfield roomid : Ljava/lang/String;
    //   392: putfield roomid : Ljava/lang/String;
    //   395: aload_1
    //   396: aload #4
    //   398: putfield adlist : Ljava/util/ArrayList;
    //   401: aload_1
    //   402: aload #6
    //   404: getfield extra : Ljava/lang/String;
    //   407: putfield extra : Ljava/lang/String;
    //   410: aload_1
    //   411: aload #6
    //   413: getfield bdpLaunchQuery : Ljava/lang/String;
    //   416: putfield bdpLaunchQuery : Ljava/lang/String;
    //   419: aload_1
    //   420: aload #6
    //   422: getfield query : Ljava/lang/String;
    //   425: putfield query : Ljava/lang/String;
    //   428: aload_1
    //   429: aload #6
    //   431: getfield bdpLog : Ljava/lang/String;
    //   434: putfield bdpLog : Ljava/lang/String;
    //   437: aload_1
    //   438: aload #6
    //   440: getfield location : Ljava/lang/String;
    //   443: putfield location : Ljava/lang/String;
    //   446: aload_1
    //   447: aload #6
    //   449: getfield bizLocation : Ljava/lang/String;
    //   452: putfield bizLocation : Ljava/lang/String;
    //   455: aload_1
    //   456: aload #6
    //   458: getfield launchType : Ljava/lang/String;
    //   461: putfield launchType : Ljava/lang/String;
    //   464: aload_1
    //   465: aload #6
    //   467: getfield token : Ljava/lang/String;
    //   470: putfield token : Ljava/lang/String;
    //   473: aload_1
    //   474: aload #6
    //   476: getfield toolbarStyle : I
    //   479: putfield toolbarStyle : I
    //   482: aload_1
    //   483: aload #6
    //   485: getfield adSiteVersionFromSchema : Ljava/lang/String;
    //   488: putfield adSiteVersionFromSchema : Ljava/lang/String;
    //   491: aload_1
    //   492: aload #6
    //   494: getfield isAutoTest : Z
    //   497: putfield isAutoTest : Z
    //   500: aload_0
    //   501: getfield mApp : Lcom/tt/miniapp/AppbrandApplicationImpl;
    //   504: astore #4
    //   506: aload #4
    //   508: ldc 'mApp'
    //   510: invokestatic a : (Ljava/lang/Object;Ljava/lang/String;)V
    //   513: aload #4
    //   515: aload_1
    //   516: invokevirtual setAppInfo : (Lcom/tt/miniapphost/entity/AppInfoEntity;)V
    //   519: return
    //   520: astore #5
    //   522: goto -> 122
    // Exception table:
    //   from	to	target	type
    //   97	119	520	java/net/MalformedURLException
  }
  
  public static final class Companion {
    private Companion() {}
  }
  
  static final class MetaService$competeRequest$1 implements Action {
    MetaService$competeRequest$1(Context param1Context, AppInfoEntity param1AppInfoEntity, RequestType param1RequestType) {}
    
    public final void act() {
      MetaHolder metaHolder = MetaService.this.mAppInfoHolder;
      AppInfoRequestResult appInfoRequestResult = AppInfoHelper.request(this.$context, this.$appInfo, this.$requestType);
      l.a(appInfoRequestResult, "AppInfoHelper.request(co…xt, appInfo, requestType)");
      metaHolder.netMetaAvailable(appInfoRequestResult);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\meta\MetaService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */