package com.tt.miniapphost;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.IBinder;
import android.text.TextUtils;
import com.tt.miniapp.process.ServiceBindManager;
import com.tt.miniapp.thread.ThreadPools;
import com.tt.miniapphost.process.base.Host2MiniAppBinderStub;
import com.tt.miniapphost.util.DynamicAppAssetsCompat;

public class MiniappHostService extends Service {
  protected void attachBaseContext(Context paramContext) {
    super.attachBaseContext(paramContext);
    AppbrandContext.tryKillIfNotInit(paramContext);
    DynamicAppAssetsCompat.ensureDynamicFeatureAssets((Context)this, super.getAssets());
  }
  
  public Context getApplicationContext() {
    DynamicAppAssetsCompat.ensureDynamicFeatureAssets((Context)this, super.getAssets());
    return super.getApplicationContext();
  }
  
  public AssetManager getAssets() {
    DynamicAppAssetsCompat.ensureDynamicFeatureAssets((Context)this, super.getAssets());
    return super.getAssets();
  }
  
  public Resources getResources() {
    DynamicAppAssetsCompat.ensureDynamicFeatureAssets((Context)this, super.getAssets());
    return super.getResources();
  }
  
  public IBinder onBind(Intent paramIntent) {
    String str = AppbrandContext.getInst().getProcessCommunicationPermission();
    if (!TextUtils.isEmpty(str) && checkCallingOrSelfPermission(str) == -1) {
      AppBrandLogger.e("MiniappHostService", new Object[] { "do not have processCommunicationPermission" });
      return null;
    } 
    ServiceBindManager.getInstance().bindHostService();
    return (new Host2MiniAppBinderStub()).asBinder();
  }
  
  public void onCreate() {
    super.onCreate();
    AppBrandLogger.d("MiniappHostService", new Object[] { "localemanager" });
    if (AppbrandApplication.getInst().getPreloadManager() != null)
      ThreadPools.defaults().execute(new Runnable() {
            public void run() {
              AppbrandApplication.getInst().getPreloadManager().preloadOnProcessInit();
            }
          }); 
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2) {
    AppBrandLogger.d("MiniappHostService", new Object[] { "onStartCommand: ", paramIntent, " flags: ", Integer.valueOf(paramInt1), " startId: ", Integer.valueOf(paramInt2) });
    if (paramIntent != null) {
      String str = paramIntent.getStringExtra("command");
      AppBrandLogger.d("MiniappHostService", new Object[] { "onStartCommand command ", str });
      if (!TextUtils.isEmpty(str)) {
        paramInt1 = -1;
        paramInt2 = str.hashCode();
        if (paramInt2 != -1508200356) {
          if (paramInt2 != -410224827) {
            if (paramInt2 == -248532251 && str.equals("stopForeground"))
              paramInt1 = 2; 
          } else if (str.equals("startForeground")) {
            paramInt1 = 1;
          } 
        } else if (str.equals("finishSticky")) {
          paramInt1 = 0;
        } 
        if (paramInt1 != 0) {
          if (paramInt1 != 1) {
            if (paramInt1 != 2)
              return 2; 
            stopForeground(true);
            return 2;
          } 
          paramInt1 = paramIntent.getIntExtra("foregroundNotificationId", 0);
          if (paramInt1 != 0)
            startForeground(paramInt1, (Notification)paramIntent.getParcelableExtra("foregroundNotification")); 
        } 
        return 2;
      } 
    } 
    return super.onStartCommand(paramIntent, paramInt1, paramInt2);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\MiniappHostService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */