package com.tt.miniapp.launchcache.pkg;

import android.content.Context;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.launchcache.LaunchCacheDAO;
import com.tt.miniapp.launchcache.RequestType;
import com.tt.miniapp.launchcache.StatusFlagType;
import com.tt.miniapp.manager.PreTTRequestManager;
import com.tt.miniapp.streamloader.StreamDownloadListener;
import com.tt.miniapp.streamloader.StreamDownloader;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.util.TimeMeter;
import d.f.b.g;
import d.f.b.l;
import java.io.File;

public abstract class DownloadOnlyBasePkgRequester extends BasePkgRequester {
  public static final Companion Companion = new Companion(null);
  
  public DownloadOnlyBasePkgRequester(Context paramContext, RequestType paramRequestType) {
    super(paramContext, paramRequestType);
  }
  
  public StreamDownloadListenerAdapter createStreamDownloadListenerAdapter(PkgRequestContext paramPkgRequestContext) {
    l.b(paramPkgRequestContext, "requestContext");
    return new StreamDownloadListenerAdapter(paramPkgRequestContext);
  }
  
  protected boolean onLoadLocalPkg(PkgRequestContext paramPkgRequestContext) {
    l.b(paramPkgRequestContext, "requestContext");
    AppBrandLogger.i("DownloadOnlyBasePkgRequester", new Object[] { getMRequestType(), "onLoadLocalPkg" });
    AppInfoEntity appInfoEntity = paramPkgRequestContext.getAppInfo();
    LaunchCacheDAO launchCacheDAO = LaunchCacheDAO.INSTANCE;
    Context context = getMContext();
    String str = appInfoEntity.appId;
    l.a(str, "appInfo.appId");
    LaunchCacheDAO.CacheAppIdDir cacheAppIdDir = launchCacheDAO.getCacheAppIdDir(context, str);
    LaunchCacheDAO.LockObject lockObject = cacheAppIdDir.lock();
    if (lockObject == null) {
      paramPkgRequestContext.setUseTime(TimeMeter.stop(getMBeginRequestPkgTime()));
      paramPkgRequestContext.setErrMsg("onLoadLocalPkg, get lock fail");
      paramPkgRequestContext.setMonitorStatus(6012);
      paramPkgRequestContext.setHttpStatusCode(0);
      paramPkgRequestContext.setHttpContentLength(0L);
      onRequestPkgFail(paramPkgRequestContext);
      return true;
    } 
    launchCacheDAO = null;
    try {
      AppInfoEntity appInfoEntity1;
      long l = appInfoEntity.versionCode;
      for (LaunchCacheDAO.CacheVersionDir cacheVersionDir : cacheAppIdDir.listCacheVersionDirs()) {
        if (cacheVersionDir.getVersionCode() == l && cacheVersionDir.checkStatusFlag(StatusFlagType.Verified) && cacheVersionDir.getPkgFile().exists()) {
          if (appInfoEntity1 != null) {
            RequestType requestType1 = cacheVersionDir.getRequestType();
            RequestType requestType2 = RequestType.normal;
            if (requestType1 == requestType2)
              continue; 
            continue;
          } 
          continue;
        } 
        continue;
        appInfoEntity1 = appInfoEntity;
      } 
      lockObject.unlock();
      return false;
    } finally {
      lockObject.unlock();
    } 
  }
  
  protected void onRequestPkgSuccess(PkgRequestContext paramPkgRequestContext) {
    l.b(paramPkgRequestContext, "requestContext");
    super.onRequestPkgSuccess(paramPkgRequestContext);
    Context context = getMContext();
    String str = (paramPkgRequestContext.getAppInfo()).appId;
    File file = paramPkgRequestContext.getPkgFile();
    if (file == null)
      l.a(); 
    PreTTRequestManager.parseAndSavePrefetchList(context, str, file);
  }
  
  protected void onRequestSync(PkgRequestContext paramPkgRequestContext) {
    l.b(paramPkgRequestContext, "requestContext");
    AppBrandLogger.i("DownloadOnlyBasePkgRequester", new Object[] { getMRequestType(), "onRequestSync" });
    AppInfoEntity appInfoEntity = paramPkgRequestContext.getAppInfo();
    LaunchCacheDAO launchCacheDAO = LaunchCacheDAO.INSTANCE;
    Context context = getMContext();
    String str = appInfoEntity.appId;
    l.a(str, "appInfo.appId");
    LaunchCacheDAO.CacheVersionDir cacheVersionDir = launchCacheDAO.getCacheAppIdDir(context, str).getCacheVersionDir(appInfoEntity.versionCode, getMRequestType());
    paramPkgRequestContext.setDownloadUrl(appInfoEntity.getDefaultUrl());
    paramPkgRequestContext.setPkgFile(cacheVersionDir.getPkgFile());
    PkgDownloadHelper.INSTANCE.updatePkgCompressType(appInfoEntity, getMRequestType());
    reportStartDownload(paramPkgRequestContext);
    paramPkgRequestContext.setErrMsg("download & check success");
    StreamDownloader.startStreamDownloadPkg(appInfoEntity, paramPkgRequestContext.getPkgFile(), createStreamDownloadListenerAdapter(paramPkgRequestContext));
  }
  
  public void reportStartDownload(PkgRequestContext paramPkgRequestContext) {
    l.b(paramPkgRequestContext, "requestContext");
    Event.builder("mp_download_start", paramPkgRequestContext.getAppInfo()).kv("request_type", getMRequestType()).flush();
  }
  
  public static final class Companion {
    private Companion() {}
  }
  
  public class StreamDownloadListenerAdapter implements StreamDownloadListener {
    private TimeMeter mStartTime;
    
    private final PkgRequestContext requestContext;
    
    public StreamDownloadListenerAdapter(PkgRequestContext param1PkgRequestContext) {
      this.requestContext = param1PkgRequestContext;
      this.mStartTime = TimeMeter.newAndStart();
    }
    
    public void onDownloadProgress(int param1Int) {
      this.requestContext.getListener().onDownloadingProgress(param1Int, -1L);
    }
    
    public void onRetry(String param1String1, String param1String2, String param1String3, int param1Int, long param1Long) {
      l.b(param1String1, "errorStr");
      l.b(param1String2, "failedUrl");
      l.b(param1String3, "nextUrl");
      PkgDownloadHelper.INSTANCE.uploadDownloadFailStat(this.requestContext.getAppInfo(), DownloadOnlyBasePkgRequester.this.getMRequestType(), param1String2, TimeMeter.stop(this.mStartTime), param1String1, param1Int, param1Long);
      this.mStartTime = TimeMeter.newAndStart();
      this.requestContext.setDownloadUrl(param1String3);
      this.requestContext.setHttpStatusCode(param1Int);
      this.requestContext.setHttpContentLength(param1Long);
    }
    
    public void onStreamDownloadError(String param1String, int param1Int, long param1Long) {
      l.b(param1String, "errMsg");
      this.requestContext.setUseTime(TimeMeter.stop(this.mStartTime));
      this.requestContext.setErrMsg(param1String);
      this.requestContext.setMonitorStatus(1017);
      this.requestContext.setHttpStatusCode(param1Int);
      this.requestContext.setHttpContentLength(param1Long);
      DownloadOnlyBasePkgRequester.this.onRequestPkgFail(this.requestContext);
    }
    
    public void onStreamDownloadFinish(int param1Int, long param1Long) {
      this.requestContext.setUseTime(TimeMeter.stop(this.mStartTime));
      this.requestContext.setHttpStatusCode(param1Int);
      this.requestContext.setHttpContentLength(param1Long);
      DownloadOnlyBasePkgRequester.this.onFileReady(this.requestContext);
    }
    
    public void onStreamDownloadStop() {
      this.requestContext.getListener().onStop();
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\pkg\DownloadOnlyBasePkgRequester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */