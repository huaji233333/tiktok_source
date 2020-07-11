package com.tt.miniapp.mvp;

import android.content.Context;
import android.text.TextUtils;
import com.storage.async.Action;
import com.storage.async.Scheduler;
import com.storage.async.Schedulers;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.launchcache.LaunchCacheCleanDataManager;
import com.tt.miniapp.launchcache.meta.AppInfoRequestListener;
import com.tt.miniapp.launchcache.meta.MetaService;
import com.tt.miniapp.launchcache.pkg.PkgService;
import com.tt.miniapp.launchcache.pkg.StreamDownloadInstallListener;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapp.settings.data.SettingsManager;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.util.TimeLogger;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.LaunchThreadPool;
import com.tt.miniapphost.entity.AppInfoEntity;
import java.io.File;

public class TTAppbrandPresenter {
  private AppbrandApplicationImpl mApp;
  
  public TTAppbrandView mView;
  
  public TTAppbrandPresenter(AppbrandApplicationImpl paramAppbrandApplicationImpl, TTAppbrandView paramTTAppbrandView) {
    this.mApp = paramAppbrandApplicationImpl;
    this.mView = paramTTAppbrandView;
  }
  
  public void downloadInstallMiniApp(Context paramContext, AppInfoEntity paramAppInfoEntity) {
    AppBrandLogger.d("tma_TTAppbrandPresenter", new Object[] { "downloadInstallMiniApp" });
    ((TimeLogger)this.mApp.getService(TimeLogger.class)).logTimeDuration(new String[] { "TTAppbrandPresenter_downloadInstallMiniApp" });
    ((PkgService)this.mApp.getService(PkgService.class)).downloadNormal(paramContext, paramAppInfoEntity, new StreamDownloadInstallListener() {
          public void onDownloadSuccess(File param1File, boolean param1Boolean) {
            AppBrandLogger.d("tma_TTAppbrandPresenter", new Object[] { "miniAppDownloadSuccess", Boolean.valueOf(param1Boolean) });
          }
          
          public void onDownloadingProgress(int param1Int, long param1Long) {
            if (TTAppbrandPresenter.this.mView != null)
              TTAppbrandPresenter.this.mView.miniAppDownloadInstallProgress(param1Int); 
          }
          
          public void onFail(String param1String1, String param1String2) {
            AppBrandLogger.e("tma_TTAppbrandPresenter", new Object[] { "miniAppDownloadInstallFail errMsg == ", param1String2 });
            if (TTAppbrandPresenter.this.mView != null)
              TTAppbrandPresenter.this.mView.miniAppDownloadInstallFail(param1String1, param1String2); 
          }
          
          public void onInstallSuccess() {
            AppBrandLogger.d("tma_TTAppbrandPresenter", new Object[] { "miniAppInstallSuccess" });
            ThreadUtil.runOnWorkThread(new Action() {
                  public void act() {
                    if (TTAppbrandPresenter.this.mView != null)
                      TTAppbrandPresenter.this.mView.miniAppInstallSuccess(); 
                  }
                },  (Scheduler)LaunchThreadPool.getInst());
          }
          
          public void onStop() {}
        });
  }
  
  public void requestAppInfo(final AppInfoEntity info) {
    ((MetaService)this.mApp.getService(MetaService.class)).requestNormalMeta((Context)AppbrandContext.getInst().getApplicationContext(), new AppInfoRequestListener() {
          public void onAppInfoInvalid(AppInfoEntity param1AppInfoEntity, int param1Int) {
            SettingsManager.getInstance().updateSettings();
            LaunchCacheCleanDataManager.INSTANCE.cleanMiniAppCache((Context)AppbrandContext.getInst().getApplicationContext(), info.appId);
            if (TTAppbrandPresenter.this.mView != null) {
              if (param1Int != 1) {
                if (param1Int != 2) {
                  if (param1Int != 3) {
                    if (param1Int != 4) {
                      if (param1Int != 5)
                        return; 
                      TTAppbrandPresenter.this.mView.metaExpired();
                      return;
                    } 
                    TTAppbrandPresenter.this.mView.mismatchHost();
                    return;
                  } 
                  TTAppbrandPresenter.this.mView.noPermission();
                  return;
                } 
                TTAppbrandPresenter.this.mView.showNotSupportView();
                return;
              } 
              TTAppbrandPresenter.this.mView.offline();
            } 
          }
          
          public void requestAppInfoFail(String param1String1, String param1String2) {
            if (TTAppbrandPresenter.this.mView != null) {
              if (info.isLocalTest() && !TextUtils.isEmpty(info.getDefaultUrl())) {
                TTAppbrandPresenter.this.mView.requestAppInfoSuccess(info);
                return;
              } 
              TTAppbrandPresenter.this.mView.requestAppInfoFail(param1String1, param1String2);
            } 
          }
          
          public void requestAppInfoSuccess(final AppInfoEntity appInfo) {
            if (TTAppbrandPresenter.this.mView != null) {
              TTAppbrandPresenter.this.mView.requestAppInfoSuccess(appInfo);
              ThreadUtil.runOnWorkThread(new Action() {
                    public void act() {
                      InnerHostProcessBridge.updateJumpList(appInfo.appId, appInfo.isGame(), appInfo.isSpecial());
                    }
                  }Schedulers.shortIO());
            } 
          }
        });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\mvp\TTAppbrandPresenter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */