package com.tt.miniapp.launchschedule;

import com.tt.miniapp.AppbrandApplicationImpl;

public abstract class AbsSubLaunchScheduler {
  protected AppbrandApplicationImpl mApp;
  
  protected LaunchScheduler mParentScheduler;
  
  AbsSubLaunchScheduler(LaunchScheduler paramLaunchScheduler, AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    this.mParentScheduler = paramLaunchScheduler;
    this.mApp = paramAppbrandApplicationImpl;
  }
  
  public abstract void onMiniAppInstallSuccess();
  
  public abstract void onStartLaunch();
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\launchschedule\AbsSubLaunchScheduler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */