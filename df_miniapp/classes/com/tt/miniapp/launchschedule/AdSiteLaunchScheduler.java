package com.tt.miniapp.launchschedule;

import com.tt.miniapp.AppbrandApplicationImpl;

public class AdSiteLaunchScheduler extends AbsSubLaunchScheduler {
  AdSiteLaunchScheduler(LaunchScheduler paramLaunchScheduler, AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramLaunchScheduler, paramAppbrandApplicationImpl);
  }
  
  public void onMiniAppInstallSuccess() {
    this.mParentScheduler.onEnvironmentReady();
  }
  
  public void onStartLaunch() {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchschedule\AdSiteLaunchScheduler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */