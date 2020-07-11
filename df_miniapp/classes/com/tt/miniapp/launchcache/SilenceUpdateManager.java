package com.tt.miniapp.launchcache;

import android.content.Context;
import com.storage.async.Action;
import com.storage.async.Scheduler;
import com.tt.miniapp.launchcache.meta.AppInfoBatchRequestListener;
import com.tt.miniapp.launchcache.meta.RequestResultInfo;
import com.tt.miniapp.launchcache.meta.SilenceBatchMetaRequester;
import com.tt.miniapp.manager.MiniAppPreloadManager;
import com.tt.miniapp.process.AppProcessManager;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.entity.PreLoadAppEntity;
import com.tt.miniapphost.host.HostDependManager;
import d.a.ac;
import d.a.m;
import d.f.a.b;
import d.f.b.l;
import d.f.b.m;
import d.l.h;
import d.n;
import d.t;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

public final class SilenceUpdateManager {
  public static final SilenceUpdateManager INSTANCE = new SilenceUpdateManager();
  
  private static Integer sUpdateInterval;
  
  public final int getSilenceUpdateInterval(Context paramContext) {
    l.b(paramContext, "context");
    if (sUpdateInterval == null) {
      HostDependManager hostDependManager = HostDependManager.getInst();
      l.a(hostDependManager, "HostDependManager.getInst()");
      JSONObject jSONObject = hostDependManager.getTmaFeatureConfig();
      byte b = -1;
      int i = b;
      if (jSONObject != null) {
        jSONObject = jSONObject.optJSONObject("bdp_silence_update_strategy");
        i = b;
        if (jSONObject != null)
          i = jSONObject.optInt("silence_update_interval", -1); 
      } 
      sUpdateInterval = Integer.valueOf(i * 60 * 60 * 1000);
    } 
    Integer integer = sUpdateInterval;
    if (integer == null)
      l.a(); 
    return integer.intValue();
  }
  
  public final void handleRequestResult(Context paramContext, RequestResultInfo paramRequestResultInfo) {
    AppInfoEntity appInfoEntity = paramRequestResultInfo.appInfo;
    if (appInfoEntity != null) {
      if (!appInfoEntity.isAppValid())
        return; 
      long l = appInfoEntity.leastVersionCode;
      if (l > 0L && l <= appInfoEntity.versionCode) {
        LaunchCacheDAO launchCacheDAO = LaunchCacheDAO.INSTANCE;
        String str = appInfoEntity.appId;
        l.a(str, "appInfo.appId");
        LaunchCacheDAO.CacheAppIdDir cacheAppIdDir = launchCacheDAO.getCacheAppIdDir(paramContext, str);
        LaunchCacheDAO.LockObject lockObject = cacheAppIdDir.lock();
        if (lockObject == null)
          return; 
        try {
          boolean bool = AppProcessManager.isAppProcessExist(paramContext, appInfoEntity.appId);
          if (bool)
            return; 
          Iterator<LaunchCacheDAO.CacheVersionDir> iterator = h.a(m.l(cacheAppIdDir.listCacheVersionDirs()), new SilenceUpdateManager$handleRequestResult$1(l)).a();
          while (iterator.hasNext())
            ((LaunchCacheDAO.CacheVersionDir)iterator.next()).clearLocked(); 
          return;
        } finally {
          lockObject.unlock();
        } 
      } 
    } 
  }
  
  public final void startSilenceDownload(Context paramContext) {
    AppBrandLogger.i("SilenceUpdateManager", new Object[] { "startSilenceDownload" });
    Iterator<LaunchCacheDAO.CacheAppIdDir> iterator = LaunchCacheDAO.INSTANCE.listCacheAppIdDirs(paramContext).iterator();
    while (iterator.hasNext()) {
      Iterator<LaunchCacheDAO.CacheVersionDir> iterator1 = ((LaunchCacheDAO.CacheAppIdDir)iterator.next()).listCacheVersionDirs().iterator();
      label24: while (true) {
        LaunchCacheDAO.CacheVersionDir cacheVersionDir;
        paramContext = null;
        while (iterator1.hasNext()) {
          LaunchCacheDAO.CacheVersionDir cacheVersionDir1 = iterator1.next();
          if (cacheVersionDir1.getRequestType() == RequestType.silence && cacheVersionDir1.checkStatusFlag(StatusFlagType.Downloading) && cacheVersionDir1.getMetaFile().exists()) {
            if (paramContext == null || paramContext.getVersionCode() < cacheVersionDir1.getVersionCode())
              cacheVersionDir = cacheVersionDir1; 
            continue;
          } 
          if (cacheVersionDir != null && cacheVersionDir1.getVersionCode() > cacheVersionDir.getVersionCode())
            continue label24; 
        } 
        if (cacheVersionDir != null) {
          PreLoadAppEntity preLoadAppEntity = new PreLoadAppEntity();
          preLoadAppEntity.setAppid(cacheVersionDir.getAppId());
          preLoadAppEntity.setPreloadMode(2);
          preLoadAppEntity.setDownloadPriority(0);
          MiniAppPreloadManager.startPreloadMiniApp(m.a(preLoadAppEntity), ac.a(new n[] { t.a("__inner_preload_type", RequestType.silence.toString()), t.a("__inner_version_code", String.valueOf(cacheVersionDir.getVersionCode())) }), null);
        } 
      } 
    } 
  }
  
  public final void updateForSdkLaunch(Context paramContext) {
    l.b(paramContext, "context");
    if (AppProcessManager.isMiniAppProcessExist(paramContext)) {
      AppBrandLogger.e("SilenceUpdateManager", new Object[] { "silenceUpdateForSdkLaunch fail: miniAppProcessExist" });
      return;
    } 
    AppBrandLogger.i("SilenceUpdateManager", new Object[] { "updateForSdkLaunch" });
    ThreadUtil.runOnWorkThread(new SilenceUpdateManager$updateForSdkLaunch$1(paramContext), ThreadPools.longIO());
  }
  
  static final class SilenceUpdateManager$handleRequestResult$1 extends m implements b<LaunchCacheDAO.CacheVersionDir, Boolean> {
    SilenceUpdateManager$handleRequestResult$1(long param1Long) {
      super(1);
    }
    
    public final boolean invoke(LaunchCacheDAO.CacheVersionDir param1CacheVersionDir) {
      l.b(param1CacheVersionDir, "it");
      return (param1CacheVersionDir.getVersionCode() < this.$leastVersionCode);
    }
  }
  
  static final class SilenceUpdateManager$updateForSdkLaunch$1 implements Action {
    SilenceUpdateManager$updateForSdkLaunch$1(Context param1Context) {}
    
    public final void act() {
      long l1 = LaunchCacheDAO.INSTANCE.getLastSilenceUpdateTime(this.$context);
      long l2 = System.currentTimeMillis();
      int i = SilenceUpdateManager.INSTANCE.getSilenceUpdateInterval(this.$context);
      if (i < 0)
        return; 
      if (l2 > l1 && l2 - l1 < i) {
        SilenceUpdateManager.INSTANCE.startSilenceDownload(this.$context);
        return;
      } 
      List<LaunchCacheDAO.CacheAppIdDir> list = LaunchCacheDAO.INSTANCE.listCacheAppIdDirs(this.$context);
      ArrayList<LaunchCacheDAO.CacheAppIdDir> arrayList1 = new ArrayList();
      label35: for (LaunchCacheDAO.CacheAppIdDir cacheAppIdDir : list) {
        List<LaunchCacheDAO.CacheVersionDir> list1 = ((LaunchCacheDAO.CacheAppIdDir)cacheAppIdDir).listCacheVersionDirs();
        boolean bool1 = list1 instanceof java.util.Collection;
        boolean bool = true;
        if (!bool1 || !list1.isEmpty()) {
          Iterator<LaunchCacheDAO.CacheVersionDir> iterator1 = list1.iterator();
          while (iterator1.hasNext()) {
            if (((LaunchCacheDAO.CacheVersionDir)iterator1.next()).getRequestType() == RequestType.normal) {
              i = 1;
            } else {
              i = 0;
            } 
            if (i != 0) {
              i = bool;
              continue label35;
            } 
          } 
        } 
        i = 0;
        if (i != 0)
          arrayList1.add(cacheAppIdDir); 
      } 
      list = arrayList1;
      arrayList1 = new ArrayList<LaunchCacheDAO.CacheAppIdDir>(m.a(list, 10));
      Iterator<LaunchCacheDAO.CacheAppIdDir> iterator = list.iterator();
      while (iterator.hasNext())
        arrayList1.add(((LaunchCacheDAO.CacheAppIdDir)iterator.next()).getAppId()); 
      ArrayList<LaunchCacheDAO.CacheAppIdDir> arrayList2 = arrayList1;
      SilenceBatchMetaRequester silenceBatchMetaRequester = new SilenceBatchMetaRequester(this.$context);
      arrayList2 = arrayList2;
      Scheduler scheduler = ThreadPools.just();
      l.a(scheduler, "ThreadPools.just()");
      silenceBatchMetaRequester.request(arrayList2, scheduler, new AppInfoBatchRequestListener() {
            public final void requestBatchAppInfoFail(String param1String) {
              l.b(param1String, "errMsg");
              SilenceUpdateManager.INSTANCE.startSilenceDownload(SilenceUpdateManager$updateForSdkLaunch$1.this.$context);
            }
            
            public final void requestBatchAppInfoSuccess(List<? extends RequestResultInfo> param1List) {
              l.b(param1List, "requestResultList");
              LaunchCacheDAO.INSTANCE.setSilenceUpdateTime(SilenceUpdateManager$updateForSdkLaunch$1.this.$context, System.currentTimeMillis());
              for (RequestResultInfo requestResultInfo : param1List)
                SilenceUpdateManager.INSTANCE.handleRequestResult(SilenceUpdateManager$updateForSdkLaunch$1.this.$context, requestResultInfo); 
              SilenceUpdateManager.INSTANCE.startSilenceDownload(SilenceUpdateManager$updateForSdkLaunch$1.this.$context);
            }
          });
    }
  }
  
  public static final class null implements AppInfoBatchRequestListener {
    public final void requestBatchAppInfoFail(String param1String) {
      l.b(param1String, "errMsg");
      SilenceUpdateManager.INSTANCE.startSilenceDownload(SilenceUpdateManager$updateForSdkLaunch$1.this.$context);
    }
    
    public final void requestBatchAppInfoSuccess(List<? extends RequestResultInfo> param1List) {
      l.b(param1List, "requestResultList");
      LaunchCacheDAO.INSTANCE.setSilenceUpdateTime(SilenceUpdateManager$updateForSdkLaunch$1.this.$context, System.currentTimeMillis());
      for (RequestResultInfo requestResultInfo : param1List)
        SilenceUpdateManager.INSTANCE.handleRequestResult(SilenceUpdateManager$updateForSdkLaunch$1.this.$context, requestResultInfo); 
      SilenceUpdateManager.INSTANCE.startSilenceDownload(SilenceUpdateManager$updateForSdkLaunch$1.this.$context);
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\SilenceUpdateManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */