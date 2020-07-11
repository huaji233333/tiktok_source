package com.tt.miniapp.launchcache.meta;

import android.content.Context;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.errorcode.ErrorCode;
import com.tt.miniapp.launchcache.LaunchCacheDAO;
import com.tt.miniapp.launchcache.LaunchCacheHelper;
import com.tt.miniapp.launchcache.RequestType;
import com.tt.miniapp.launchcache.StatusFlagType;
import com.tt.miniapp.util.TimeLogger;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.util.IOUtils;
import d.f.b.g;
import d.f.b.l;
import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public final class MetaHolder {
  public static final Companion Companion = new Companion(null);
  
  private RequestResultInfo cachedRequestResultInfo;
  
  private final AppbrandApplicationImpl mApp;
  
  private final LinkedBlockingQueue<AppInfoRequestResult> mNetMetaQueue;
  
  public MetaHolder(AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    this.mApp = paramAppbrandApplicationImpl;
    this.mNetMetaQueue = new LinkedBlockingQueue<AppInfoRequestResult>();
  }
  
  public final AppInfoRequestResult blockTakeNetMeta(long paramLong) {
    try {
      ((TimeLogger)this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "MetaHolder_blockGet" });
      return this.mNetMetaQueue.poll(paramLong, TimeUnit.MILLISECONDS);
    } catch (InterruptedException interruptedException) {
      return null;
    } 
  }
  
  public final void netMetaAvailable(AppInfoRequestResult paramAppInfoRequestResult) {
    l.b(paramAppInfoRequestResult, "result");
    TimeLogger timeLogger = (TimeLogger)this.mApp.getService(TimeLogger.class);
    StringBuilder stringBuilder = new StringBuilder("from: ");
    stringBuilder.append(paramAppInfoRequestResult.fromProcess);
    timeLogger.logTimeDuration(new String[] { "MetaHolder_appInfoAvailable", stringBuilder.toString() });
    this.mNetMetaQueue.offer(paramAppInfoRequestResult);
  }
  
  public final RequestResultInfo tryFetchLocalMeta(Context paramContext, String paramString, RequestType paramRequestType) {
    AppInfoEntity appInfoEntity;
    l.b(paramContext, "context");
    l.b(paramString, "appId");
    l.b(paramRequestType, "requestType");
    RequestResultInfo requestResultInfo = this.cachedRequestResultInfo;
    if (requestResultInfo != null)
      return requestResultInfo; 
    LaunchCacheDAO.CacheAppIdDir cacheAppIdDir = LaunchCacheDAO.INSTANCE.getCacheAppIdDir(paramContext, paramString);
    LaunchCacheDAO.LockObject lockObject = cacheAppIdDir.lock();
    if (lockObject == null)
      return null; 
    null = new RequestResultInfo();
    try {
      boolean bool;
      LaunchCacheDAO.CacheVersionDir cacheVersionDir1 = LaunchCacheHelper.INSTANCE.getBestAvailableCacheVersionDirLocked(cacheAppIdDir);
      if (cacheVersionDir1 == null)
        return null; 
      LaunchCacheDAO.CacheVersionDir cacheVersionDir2 = cacheAppIdDir.getCacheVersionDir(cacheVersionDir1.getVersionCode(), RequestType.normal);
      if (cacheVersionDir1.getRequestType() != RequestType.normal) {
        File file2 = cacheVersionDir2.getMetaFile();
        IOUtils.delete(file2);
        File file1 = cacheVersionDir1.getMetaFile();
        bool = file1.renameTo(new File(file2.getParentFile(), file1.getName()));
        if (!bool)
          return null; 
        if (cacheVersionDir2.getCurrentStatusFlag() == null)
          cacheVersionDir2.setStatusFlagLocked(StatusFlagType.Downloading); 
      } 
      if (LaunchCacheHelper.INSTANCE.isMetaExpired(cacheVersionDir2)) {
        bool = false;
        null.errorCode = ErrorCode.META.QRCODE_EXPIRED.getCode();
        null.errorMsg = "local meta is expired";
      } else {
        bool = LaunchCacheHelper.INSTANCE.getRawLocalMetaLocked(cacheVersionDir2, null);
      } 
      lockObject.unlock();
      return this.cachedRequestResultInfo;
    } finally {
      appInfoEntity.unlock();
    } 
  }
  
  public final AppInfoRequestResult tryTakeCachedNetMeta() {
    ((TimeLogger)this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "MetaHolder_tryGet" });
    return this.mNetMetaQueue.poll();
  }
  
  public static final class Companion {
    private Companion() {}
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\meta\MetaHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */