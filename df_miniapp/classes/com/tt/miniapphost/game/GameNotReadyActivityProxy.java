package com.tt.miniapphost.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.BaseActivityProxy;
import com.tt.miniapp.base.activity.IActivityResultHandler;
import com.tt.miniapp.preload.PreloadManager;
import com.tt.miniapp.titlebar.ITitleBar;
import com.tt.miniapp.util.ToolUtils;
import com.tt.miniapp.view.LaunchLoadingView;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.language.LocaleManager;
import com.tt.miniapphost.util.UIUtils;

public class GameNotReadyActivityProxy extends BaseActivityProxy {
  protected AppbrandApplicationImpl mApp;
  
  private View mCloseBtn;
  
  public GameNotReadyActivityProxy(FragmentActivity paramFragmentActivity) {
    super(paramFragmentActivity);
  }
  
  private void initLoadingLayout() {
    this.mLaunchLoadingView = (LaunchLoadingView)((PreloadManager)this.mApp.getService(PreloadManager.class)).getPreloadedView(1);
    this.mLaunchLoadingView.initWithActivity((Activity)this.mActivity);
    if (!LocaleManager.getInst().isInCNLang())
      this.mLaunchLoadingView.hideBottomTip(); 
    this.mActivity.setContentView((View)this.mLaunchLoadingView);
    this.mCloseBtn = this.mLaunchLoadingView.findViewById(2097545405);
    AppInfoEntity appInfoEntity = this.mApp.getAppInfo();
    if (appInfoEntity != null)
      this.mLaunchLoadingView.updateViews(appInfoEntity); 
    this.mCloseBtn.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            ToolUtils.onActivityExit((Activity)GameNotReadyActivityProxy.this.mActivity, 2);
          }
        });
  }
  
  public View findViewById(int paramInt) {
    return null;
  }
  
  public FrameLayout getRootView() {
    return null;
  }
  
  public ITitleBar getTitleBar() {
    return null;
  }
  
  public void miniAppDownloadInstallProgress(int paramInt) {}
  
  public void miniAppInstallSuccess() {}
  
  public boolean onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    return false;
  }
  
  public void onAddVideoFragment() {}
  
  public void onBackPressed() {
    ToolUtils.onActivityExit((Activity)this.mActivity, 9);
  }
  
  public void onCreate(Bundle paramBundle) {
    this.mApp = AppbrandApplicationImpl.getInst();
    View view = ((PreloadManager)this.mApp.getService(PreloadManager.class)).getPreloadedLoadingView((Activity)this.mActivity, 2);
    if (view == null) {
      try {
        view = LayoutInflater.from((Context)AppbrandContext.getInst().getApplicationContext()).inflate(2097676316, null);
        this.mActivity.setContentView(view);
      } finally {}
    } else {
      UIUtils.removeParentView(view);
      this.mActivity.setContentView(view);
    } 
    initLoadingLayout();
  }
  
  public void onDOMReady() {}
  
  public void onEnvironmentReady() {}
  
  public void onFirstContentfulPaint(long paramLong) {}
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
    return false;
  }
  
  public void onRemoteDebugOpen() {}
  
  public void onRemoveVideoFragment() {}
  
  public void onSnapShotDOMReady() {}
  
  public void onUserInteraction() {}
  
  public void setActivityResultHandler(IActivityResultHandler paramIActivityResultHandler) {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphost\game\GameNotReadyActivityProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */