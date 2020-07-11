package com.tt.miniapp.adsite;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.titlebar.BaseTitleBar;
import com.tt.miniapphost.entity.AppInfoEntity;

public class AdSiteTitleBar extends BaseTitleBar {
  public AdSiteTitleBar(Context paramContext, ViewGroup paramViewGroup) {
    super(paramContext, paramViewGroup);
  }
  
  public Activity getActivity() {
    return AppbrandApplicationImpl.getInst().getMiniAppContext().getCurrentActivity();
  }
  
  public String getGlobalTitleText() {
    AppInfoEntity appInfoEntity = AppbrandApplicationImpl.getInst().getAppInfo();
    return (appInfoEntity != null) ? appInfoEntity.appName : null;
  }
  
  public String getNavigationBarBackgroundColor(String paramString) {
    this.mBackgroundColor = "#ffffff";
    return "#ffffff";
  }
  
  public String getNavigationBarTextStyle(String paramString) {
    this.mBarTextStyle = "black";
    return "black";
  }
  
  public String getNavigationStyle(String paramString) {
    return "default";
  }
  
  public String getNavigationTransparentTitle(String paramString) {
    this.mTransparentTitle = "none";
    return "none";
  }
  
  public String getPageTitleText(String paramString) {
    this.mPageText = null;
    return null;
  }
  
  public boolean isBottomPage() {
    return true;
  }
  
  public boolean isShowBackHomeInMenuDialog() {
    return false;
  }
  
  public boolean isShowLeftBackHomeView() {
    return false;
  }
  
  public void onClickBackHomeButton() {}
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\adsite\AdSiteTitleBar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */