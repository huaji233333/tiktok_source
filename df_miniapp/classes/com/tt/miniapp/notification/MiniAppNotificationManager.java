package com.tt.miniapp.notification;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import com.tt.miniapp.process.AppProcessManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.host.HostDependManager;

public class MiniAppNotificationManager {
  public static void cancelPayNotification(NotificationEntity paramNotificationEntity) {}
  
  public static NotificationEntity createPayNotification() {
    return null;
  }
  
  private static boolean enable() {
    return HostDependManager.getInst().getPayEnable();
  }
  
  public static void init(Context paramContext) {
    if (Build.VERSION.SDK_INT >= 26) {
      NotificationManager notificationManager = (NotificationManager)paramContext.getSystemService("notification");
      if (notificationManager == null)
        return; 
      String str2 = paramContext.getString(2097741963);
      String str1 = paramContext.getString(2097742049);
      NotificationChannel notificationChannel = new NotificationChannel("miniAppPay", str2, 4);
      notificationChannel.setDescription(str1);
      notificationChannel.enableLights(false);
      notificationChannel.enableVibration(false);
      if (enable())
        notificationManager.createNotificationChannel(notificationChannel); 
    } 
  }
  
  private static void startMiniAppServiceForeground(int paramInt, Notification paramNotification) {
    Class clazz = AppProcessManager.getCurrentMiniAppProcessServiceClass();
    if (clazz == null)
      return; 
    Application application = AppbrandContext.getInst().getApplicationContext();
    Intent intent = new Intent((Context)application, clazz);
    intent.putExtra("command", "startForeground");
    intent.putExtra("foregroundNotificationId", paramInt);
    intent.putExtra("foregroundNotification", (Parcelable)paramNotification);
    try {
      _lancet.com_ss_android_ugc_aweme_push_downgrade_StartServiceLancet_startService((Context)application, intent);
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("MiniAppNotificationManager", new Object[] { "startMiniAppServiceForeground", exception });
      return;
    } 
  }
  
  private static void stopMiniAppServiceForeground() {
    Class clazz = AppProcessManager.getCurrentMiniAppProcessServiceClass();
    if (clazz == null)
      return; 
    Application application = AppbrandContext.getInst().getApplicationContext();
    Intent intent = new Intent((Context)application, clazz);
    intent.putExtra("command", "stopForeground");
    try {
      _lancet.com_ss_android_ugc_aweme_push_downgrade_StartServiceLancet_startService((Context)application, intent);
      return;
    } catch (Exception exception) {
      AppBrandLogger.e("MiniAppNotificationManager", new Object[] { "startMiniAppServiceForeground", exception });
      return;
    } 
  }
  
  public static class NotificationEntity {
    private final Notification mNotification;
    
    private final int mNotificationId;
    
    public NotificationEntity(int param1Int, Notification param1Notification) {
      this.mNotificationId = param1Int;
      this.mNotification = param1Notification;
    }
    
    public Notification getNotification() {
      return this.mNotification;
    }
    
    public int getNotificationId() {
      return this.mNotificationId;
    }
  }
  
  class MiniAppNotificationManager {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\notification\MiniAppNotificationManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */