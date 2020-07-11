package com.tt.miniapp.manager;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import com.storage.async.Action;
import com.storage.async.Scheduler;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandServiceManager;
import com.tt.miniapp.base.ui.viewwindow.ViewWindow;
import com.tt.miniapp.route.PageRouter;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.LaunchThreadPool;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.process.callback.IpcCallback;
import com.tt.miniapphost.process.data.CrossProcessDataEntity;

public class HostSnapShotManager extends AppbrandServiceManager.ServiceBase {
  private volatile boolean mFirstUpdateSnapshot = true;
  
  private boolean mNeedUpdateSnapshotWhenOnStart;
  
  private volatile boolean mTriggeredHomeOrRecentApp;
  
  public Runnable mUpdateSnapshotRunnable;
  
  public HostSnapShotManager(AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramAppbrandApplicationImpl);
  }
  
  public void clearSwipeBackground() {
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            HostSnapShotManager.this.getHomeViewWindow().getRoot().getContainer().setBackground(null);
          }
        });
  }
  
  public ViewWindow getHomeViewWindow() {
    return (ViewWindow)((PageRouter)AppbrandApplicationImpl.getInst().getService(PageRouter.class)).getViewWindowRoot().getAppbrandHomePage();
  }
  
  public boolean isNeedUpdateSnapshotWhenOnStart() {
    return this.mNeedUpdateSnapshotWhenOnStart;
  }
  
  public boolean isTriggeredHomeOrRecentApp() {
    return this.mTriggeredHomeOrRecentApp;
  }
  
  public void notifyUpdateSnapShot() {
    if (AppbrandApplicationImpl.getInst().getMiniAppLaunchConfig().isLaunchWithFloatStyle())
      return; 
    boolean bool = AppbrandApplicationImpl.getInst().getForeBackgroundManager().isBackground();
    AppBrandLogger.i("HostSnapShotManager", new Object[] { "notifyUpdateSnapShot isBackground:", Boolean.valueOf(bool) });
    if (bool) {
      clearSwipeBackground();
      this.mNeedUpdateSnapshotWhenOnStart = true;
      return;
    } 
    updateSnapShotView();
  }
  
  public void setNeedUpdateSnapshotWhenOnStart(boolean paramBoolean) {
    this.mNeedUpdateSnapshotWhenOnStart = paramBoolean;
  }
  
  public void setTriggeredHomeOrRecentApp(boolean paramBoolean) {
    this.mTriggeredHomeOrRecentApp = paramBoolean;
  }
  
  public void updateSnapShotView() {
    updateSnapShotView(this.mApp.getMiniAppContext().getApplicationContext(), false);
  }
  
  public void updateSnapShotView(final Context context, boolean paramBoolean) {
    final long delayUpdateTime;
    if (this.mTriggeredHomeOrRecentApp) {
      AppBrandLogger.i("HostSnapShotManager", new Object[] { "updateSnapShotView mTriggeredHomeOrRecentApp" });
      return;
    } 
    final ViewWindow homeViewWindow = getHomeViewWindow();
    if (this.mFirstUpdateSnapshot) {
      l = 100L;
    } else {
      l = 0L;
    } 
    this.mFirstUpdateSnapshot = false;
    AppBrandLogger.i("HostSnapShotManager", new Object[] { "updateSnapShotView getSnapshot" });
    HostProcessBridge.getSnapshot(paramBoolean, new IpcCallback() {
          public void onIpcCallback(final CrossProcessDataEntity callbackData) {
            AppBrandLogger.d("HostSnapShotManager", new Object[] { "getSnapshot callback callbackData:", callbackData });
            finishListenIpcCallback();
            ThreadUtil.runOnWorkThread(new Action() {
                  public void act() {
                    CrossProcessDataEntity crossProcessDataEntity = callbackData;
                    if (crossProcessDataEntity != null) {
                      String str = crossProcessDataEntity.getString("snapshot");
                    } else {
                      crossProcessDataEntity = null;
                    } 
                    if (TextUtils.isEmpty((CharSequence)crossProcessDataEntity)) {
                      AppBrandLogger.e("HostSnapShotManager", new Object[] { "getSnapshot callback null snapshotFilePath" });
                      return;
                    } 
                    try {
                      ThreadUtil.cancelUIRunnable(HostSnapShotManager.this.mUpdateSnapshotRunnable);
                      if (!homeViewWindow.isDragEnabled()) {
                        AppBrandLogger.i("HostSnapShotManager", new Object[] { "!swipeBackLayout.isEnableGesture() onIpcCallback" });
                        return;
                      } 
                      final BitmapDrawable snapshotDrawable = SnapshotManager.getSnapshotDrawableFromFile(context.getResources(), (String)crossProcessDataEntity);
                      if (bitmapDrawable == null) {
                        AppBrandLogger.e("HostSnapShotManager", new Object[] { "getSnapshot snapshotDrawable error" });
                        return;
                      } 
                      HostSnapShotManager.this.mUpdateSnapshotRunnable = new Runnable() {
                          public void run() {
                            homeViewWindow.getRoot().getContainer().setBackground((Drawable)snapshotDrawable);
                          }
                        };
                      ThreadUtil.runOnUIThread(HostSnapShotManager.this.mUpdateSnapshotRunnable, delayUpdateTime);
                      return;
                    } catch (Exception exception) {
                      AppBrandLogger.eWithThrowable("HostSnapShotManager", "setSnapshotAsBackground", exception);
                      return;
                    } 
                  }
                }(Scheduler)LaunchThreadPool.getInst(), false);
          }
          
          public void onIpcConnectError() {
            AppBrandLogger.i("HostSnapShotManager", new Object[] { "updateSnapShotView HostProcessNotExist clearSwipeBackground" });
            HostSnapShotManager.this.clearSwipeBackground();
          }
        });
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\manager\HostSnapShotManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */