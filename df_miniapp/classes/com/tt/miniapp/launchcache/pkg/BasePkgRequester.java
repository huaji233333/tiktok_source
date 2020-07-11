package com.tt.miniapp.launchcache.pkg;

import android.content.Context;
import android.util.Log;
import com.storage.async.Action;
import com.storage.async.Scheduler;
import com.tt.miniapp.errorcode.ErrorCode;
import com.tt.miniapp.launchcache.LaunchCacheDAO;
import com.tt.miniapp.launchcache.RequestType;
import com.tt.miniapp.launchcache.StatusFlagType;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.util.TimeMeter;
import d.f.b.g;
import d.f.b.l;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public abstract class BasePkgRequester {
  public static final Companion Companion = new Companion(null);
  
  private TimeMeter mBeginRequestPkgTime;
  
  private final Context mContext;
  
  private final RequestType mRequestType;
  
  public BasePkgRequester(Context paramContext, RequestType paramRequestType) {
    this.mContext = paramContext;
    this.mRequestType = paramRequestType;
  }
  
  protected final TimeMeter getMBeginRequestPkgTime() {
    return this.mBeginRequestPkgTime;
  }
  
  protected final Context getMContext() {
    return this.mContext;
  }
  
  protected final RequestType getMRequestType() {
    return this.mRequestType;
  }
  
  protected final void onFileReady(PkgRequestContext paramPkgRequestContext) {
    String str1;
    l.b(paramPkgRequestContext, "requestContext");
    AppInfoEntity appInfoEntity = paramPkgRequestContext.getAppInfo();
    LaunchCacheDAO launchCacheDAO = LaunchCacheDAO.INSTANCE;
    Context context = this.mContext;
    String str2 = appInfoEntity.appId;
    l.a(str2, "appInfo.appId");
    LaunchCacheDAO.CacheAppIdDir cacheAppIdDir = launchCacheDAO.getCacheAppIdDir(context, str2);
    LaunchCacheDAO.LockObject lockObject = cacheAppIdDir.lock();
    if (lockObject == null) {
      str1 = ErrorCode.MAIN.GET_LAUNCHCACHE_FILE_LOCK_FAIL.getCode();
      l.a(str1, "ErrorCode.MAIN.GET_LAUNCHCACHE_FILE_LOCK_FAIL.code");
      paramPkgRequestContext.setErrCode(str1);
      paramPkgRequestContext.setErrMsg("requestPkgSuccess, get lock fail");
      paramPkgRequestContext.setMonitorStatus(6012);
      onRequestPkgFail(paramPkgRequestContext);
      return;
    } 
    try {
      HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
      LaunchCacheDAO.CacheVersionDir cacheVersionDir = cacheAppIdDir.getCacheVersionDir(((AppInfoEntity)str1).versionCode, this.mRequestType);
      PkgDownloadHelper pkgDownloadHelper = PkgDownloadHelper.INSTANCE;
      File file = paramPkgRequestContext.getPkgFile();
      if (file == null)
        l.a(); 
      if (pkgDownloadHelper.isPkgFileValid((AppInfoEntity)str1, file, (Map)hashMap)) {
        cacheVersionDir.setStatusFlagLocked(StatusFlagType.Verified);
        onRequestPkgSuccess(paramPkgRequestContext);
      } else {
        str1 = ErrorCode.DOWNLOAD.PKG_MD5_ERROR.getCode();
        l.a(str1, "ErrorCode.DOWNLOAD.PKG_MD5_ERROR.code");
        paramPkgRequestContext.setErrCode(str1);
        paramPkgRequestContext.setErrMsg("md5 verify failed");
        paramPkgRequestContext.setExtraInfo((Map)hashMap);
        paramPkgRequestContext.setMonitorStatus(1000);
        cacheVersionDir.clearLocked();
        onRequestPkgFail(paramPkgRequestContext);
      } 
      return;
    } finally {
      lockObject.unlock();
    } 
  }
  
  protected abstract boolean onLoadLocalPkg(PkgRequestContext paramPkgRequestContext);
  
  protected void onRequestPkgFail(PkgRequestContext paramPkgRequestContext) {
    l.b(paramPkgRequestContext, "requestContext");
    AppBrandLogger.e("BasePkgRequester", new Object[] { this.mRequestType, paramPkgRequestContext.getErrMsg() });
    if (paramPkgRequestContext.getExtraInfo() != null)
      AppBrandLogger.e("BasePkgRequester", new Object[] { this.mRequestType, paramPkgRequestContext.getExtraInfo() }); 
    if (paramPkgRequestContext.isNetDownload())
      PkgDownloadHelper.INSTANCE.uploadDownloadFailStat(paramPkgRequestContext.getAppInfo(), this.mRequestType, paramPkgRequestContext.getDownloadUrl(), paramPkgRequestContext.getUseTime(), paramPkgRequestContext.getErrMsg(), paramPkgRequestContext.getHttpStatusCode(), paramPkgRequestContext.getHttpContentLength()); 
    PkgDownloadHelper.INSTANCE.uploadDownloadInstallFailMpMonitor(paramPkgRequestContext.getAppInfo(), this.mRequestType, paramPkgRequestContext.getErrMsg(), paramPkgRequestContext.getExtraInfo(), paramPkgRequestContext.getMonitorStatus());
    paramPkgRequestContext.getListener().onFail(paramPkgRequestContext.getErrCode(), paramPkgRequestContext.getErrMsg());
  }
  
  protected void onRequestPkgSuccess(PkgRequestContext paramPkgRequestContext) {
    l.b(paramPkgRequestContext, "requestContext");
    if (paramPkgRequestContext.isNetDownload())
      PkgDownloadHelper.INSTANCE.uploadDownloadSuccessStat(paramPkgRequestContext.getAppInfo(), this.mRequestType, paramPkgRequestContext.getDownloadUrl(), paramPkgRequestContext.getUseTime(), paramPkgRequestContext.getHttpStatusCode(), paramPkgRequestContext.getHttpContentLength()); 
    PkgDownloadHelper.INSTANCE.uploadDownloadSuccessMpMonitor(paramPkgRequestContext.getAppInfo(), this.mRequestType, paramPkgRequestContext.getErrMsg());
    paramPkgRequestContext.getListener().onDownloadingProgress(100, -1L);
    StreamDownloadInstallListener streamDownloadInstallListener = paramPkgRequestContext.getListener();
    File file = paramPkgRequestContext.getPkgFile();
    if (file == null)
      l.a(); 
    streamDownloadInstallListener.onDownloadSuccess(file, paramPkgRequestContext.isNetDownload() ^ true);
  }
  
  protected abstract void onRequestSync(PkgRequestContext paramPkgRequestContext);
  
  public final void request(AppInfoEntity paramAppInfoEntity, Scheduler paramScheduler, StreamDownloadInstallListener paramStreamDownloadInstallListener) {
    l.b(paramAppInfoEntity, "appInfo");
    l.b(paramScheduler, "scheduler");
    l.b(paramStreamDownloadInstallListener, "streamDownloadInstallListener");
    this.mBeginRequestPkgTime = TimeMeter.newAndStart();
    ThreadUtil.runOnWorkThread(new BasePkgRequester$request$1(new PkgRequestContext(paramAppInfoEntity, paramStreamDownloadInstallListener)), paramScheduler);
  }
  
  protected final void setMBeginRequestPkgTime(TimeMeter paramTimeMeter) {
    this.mBeginRequestPkgTime = paramTimeMeter;
  }
  
  public static final class Companion {
    private Companion() {}
  }
  
  static final class BasePkgRequester$request$1 implements Action {
    BasePkgRequester$request$1(PkgRequestContext param1PkgRequestContext) {}
    
    public final void act() {
      try {
        if (!BasePkgRequester.this.onLoadLocalPkg(this.$requestContext)) {
          this.$requestContext.setNetDownload(true);
          BasePkgRequester.this.onRequestSync(this.$requestContext);
          return;
        } 
      } catch (Exception exception) {
        AppBrandLogger.e("BasePkgRequester", new Object[] { this.this$0.getMRequestType(), exception });
        this.$requestContext.setUseTime(TimeMeter.stop(BasePkgRequester.this.getMBeginRequestPkgTime()));
        PkgRequestContext pkgRequestContext = this.$requestContext;
        String str2 = ErrorCode.DOWNLOAD.UNKNOWN.getCode();
        l.a(str2, "ErrorCode.DOWNLOAD.UNKNOWN.code");
        pkgRequestContext.setErrCode(str2);
        pkgRequestContext = this.$requestContext;
        String str1 = Log.getStackTraceString(exception);
        l.a(str1, "Log.getStackTraceString(e)");
        pkgRequestContext.setErrMsg(str1);
        this.$requestContext.setMonitorStatus(1002);
        BasePkgRequester.this.onRequestPkgFail(this.$requestContext);
      } 
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\pkg\BasePkgRequester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */