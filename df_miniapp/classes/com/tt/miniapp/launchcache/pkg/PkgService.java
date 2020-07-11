package com.tt.miniapp.launchcache.pkg;

import android.content.Context;
import com.storage.async.Scheduler;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandServiceManager;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapphost.LaunchThreadPool;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.util.TimeMeter;
import d.f.b.l;

public final class PkgService extends AppbrandServiceManager.ServiceBase {
  private TimeMeter downloadTime;
  
  private String mLocalPkgFile;
  
  private LocalPackageFileReadyCallback mLocalPkgFileReadyCallback;
  
  public PkgService(AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramAppbrandApplicationImpl);
  }
  
  public final void downloadAsync(Context paramContext, AppInfoEntity paramAppInfoEntity, StreamDownloadInstallListener paramStreamDownloadInstallListener) {
    l.b(paramContext, "context");
    l.b(paramAppInfoEntity, "appInfo");
    l.b(paramStreamDownloadInstallListener, "streamDownloadInstallListener");
    AppbrandApplicationImpl appbrandApplicationImpl = this.mApp;
    l.a(appbrandApplicationImpl, "mApp");
    AsyncPkgRequester asyncPkgRequester = new AsyncPkgRequester(appbrandApplicationImpl, paramContext);
    Scheduler scheduler = ThreadPools.longIO();
    l.a(scheduler, "ThreadPools.longIO()");
    asyncPkgRequester.request(paramAppInfoEntity, scheduler, paramStreamDownloadInstallListener);
  }
  
  public final void downloadNormal(Context paramContext, AppInfoEntity paramAppInfoEntity, StreamDownloadInstallListener paramStreamDownloadInstallListener) {
    l.b(paramContext, "context");
    l.b(paramAppInfoEntity, "appInfo");
    l.b(paramStreamDownloadInstallListener, "streamDownloadInstallListener");
    AppbrandApplicationImpl appbrandApplicationImpl = this.mApp;
    l.a(appbrandApplicationImpl, "mApp");
    NormalPkgRequester normalPkgRequester = new NormalPkgRequester(appbrandApplicationImpl, paramContext);
    LaunchThreadPool launchThreadPool = LaunchThreadPool.getInst();
    l.a(launchThreadPool, "LaunchThreadPool.getInst()");
    normalPkgRequester.request(paramAppInfoEntity, (Scheduler)launchThreadPool, paramStreamDownloadInstallListener);
  }
  
  public final TimeMeter getDownloadTime() {
    return this.downloadTime;
  }
  
  public final void notifyLocalPkgReady(String paramString) {
    // Byte code:
    //   0: aload_1
    //   1: ldc 'localPkgPath'
    //   3: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   6: aload_0
    //   7: aload_1
    //   8: putfield mLocalPkgFile : Ljava/lang/String;
    //   11: ldc com/tt/miniapp/launchcache/pkg/PkgService$LocalPackageFileReadyCallback
    //   13: monitorenter
    //   14: aload_0
    //   15: getfield mLocalPkgFileReadyCallback : Lcom/tt/miniapp/launchcache/pkg/PkgService$LocalPackageFileReadyCallback;
    //   18: astore_2
    //   19: aload_2
    //   20: ifnull -> 35
    //   23: aload_2
    //   24: aload_1
    //   25: invokeinterface localPackageReady : (Ljava/lang/String;)V
    //   30: aload_0
    //   31: aconst_null
    //   32: putfield mLocalPkgFileReadyCallback : Lcom/tt/miniapp/launchcache/pkg/PkgService$LocalPackageFileReadyCallback;
    //   35: ldc com/tt/miniapp/launchcache/pkg/PkgService$LocalPackageFileReadyCallback
    //   37: monitorexit
    //   38: return
    //   39: astore_1
    //   40: ldc com/tt/miniapp/launchcache/pkg/PkgService$LocalPackageFileReadyCallback
    //   42: monitorexit
    //   43: aload_1
    //   44: athrow
    // Exception table:
    //   from	to	target	type
    //   14	19	39	finally
    //   23	35	39	finally
  }
  
  public final void onLocalPackageFileReady(LocalPackageFileReadyCallback paramLocalPackageFileReadyCallback) {
    // Byte code:
    //   0: aload_1
    //   1: ldc 'callback'
    //   3: invokestatic b : (Ljava/lang/Object;Ljava/lang/String;)V
    //   6: ldc com/tt/miniapp/launchcache/pkg/PkgService$LocalPackageFileReadyCallback
    //   8: monitorenter
    //   9: aload_0
    //   10: getfield mLocalPkgFile : Ljava/lang/String;
    //   13: astore_2
    //   14: aload_2
    //   15: ifnull -> 28
    //   18: aload_1
    //   19: aload_2
    //   20: invokeinterface localPackageReady : (Ljava/lang/String;)V
    //   25: goto -> 33
    //   28: aload_0
    //   29: aload_1
    //   30: putfield mLocalPkgFileReadyCallback : Lcom/tt/miniapp/launchcache/pkg/PkgService$LocalPackageFileReadyCallback;
    //   33: ldc com/tt/miniapp/launchcache/pkg/PkgService$LocalPackageFileReadyCallback
    //   35: monitorexit
    //   36: return
    //   37: astore_1
    //   38: ldc com/tt/miniapp/launchcache/pkg/PkgService$LocalPackageFileReadyCallback
    //   40: monitorexit
    //   41: aload_1
    //   42: athrow
    // Exception table:
    //   from	to	target	type
    //   9	14	37	finally
    //   18	25	37	finally
    //   28	33	37	finally
  }
  
  public final void setDownloadTime(TimeMeter paramTimeMeter) {
    this.downloadTime = paramTimeMeter;
  }
  
  public static interface LocalPackageFileReadyCallback {
    void localPackageReady(String param1String);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchcache\pkg\PkgService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */