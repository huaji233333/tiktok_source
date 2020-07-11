package com.tt.miniapp.titlebar;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import com.tt.miniapp.AppConfig;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.event.Event;
import com.tt.miniapp.page.AppbrandSinglePage;
import com.tt.miniapp.route.PageRouter;
import com.tt.miniapp.util.PageUtil;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.util.UIUtils;

public class AppbrandTitleBar extends BaseTitleBar {
  private AppbrandSinglePage mSinglePageView;
  
  public AppbrandTitleBar(Context paramContext, AppbrandSinglePage paramAppbrandSinglePage) {
    super(paramContext, (ViewGroup)paramAppbrandSinglePage);
    this.mSinglePageView = paramAppbrandSinglePage;
  }
  
  public static boolean disableScroll(String paramString) {
    AppConfig appConfig = AppbrandApplicationImpl.getInst().getAppConfig();
    if (appConfig != null) {
      boolean bool;
      AppConfig.Page page = appConfig.page;
      if (page != null) {
        AppConfig.Window window = page.getWindow(paramString);
        if (window != null && window.hasDisableScroll)
          return window.disableScroll; 
      } 
      if (appConfig.global != null && appConfig.global.window != null) {
        bool = true;
      } else {
        bool = false;
      } 
      if (bool && appConfig.global.window.hasDisableScroll)
        return appConfig.global.window.disableScroll; 
    } 
    return false;
  }
  
  protected void exitInternal() {
    AppConfig appConfig = AppbrandApplicationImpl.getInst().getAppConfig();
    if (appConfig != null)
      appConfig.isBackToHome = false; 
    super.exitInternal();
  }
  
  public Activity getActivity() {
    return this.mSinglePageView.getActivity();
  }
  
  protected String getGlobalTitleText() {
    AppConfig appConfig = AppbrandApplicationImpl.getInst().getAppConfig();
    if (appConfig != null) {
      AppConfig.Global global = appConfig.global;
      if (global != null) {
        AppConfig.Window window = global.window;
        if (window != null && window.hasNavigationBarTitleText)
          return window.navigationBarTitleText; 
      } 
    } 
    return null;
  }
  
  protected String getNavigationBarBackgroundColor(String paramString) {
    if (!TextUtils.isEmpty(this.mBackgroundColor))
      return this.mBackgroundColor; 
    AppConfig appConfig = AppbrandApplicationImpl.getInst().getAppConfig();
    if (appConfig != null) {
      AppConfig.Page page = appConfig.page;
      if (page != null) {
        AppConfig.Window window = page.getWindow(paramString);
        if (window != null && window.hasNavigationBarBackgroundColor) {
          String str = window.navigationBarBackgroundColor;
          this.mBackgroundColor = str;
          return str;
        } 
      } 
      AppConfig.Global global = appConfig.global;
      if (global != null) {
        AppConfig.Window window = global.window;
        if (window != null && window.hasNavigationBarBackgroundColor) {
          String str = window.navigationBarBackgroundColor;
          this.mBackgroundColor = str;
          return str;
        } 
      } 
    } 
    this.mBackgroundColor = "#000000";
    return "#000000";
  }
  
  protected String getNavigationBarTextStyle(String paramString) {
    if (!TextUtils.isEmpty(this.mBarTextStyle))
      return this.mBarTextStyle; 
    AppConfig appConfig = AppbrandApplicationImpl.getInst().getAppConfig();
    if (appConfig != null) {
      AppConfig.Page page = appConfig.page;
      if (page != null) {
        AppConfig.Window window = page.getWindow(paramString);
        if (window != null && window.hasNavigationBarTextStyle) {
          String str = window.navigationBarTextStyle;
          this.mBarTextStyle = str;
          return str;
        } 
      } 
      AppConfig.Global global = appConfig.global;
      if (global != null) {
        AppConfig.Window window = global.window;
        if (window != null && !TextUtils.isEmpty(window.navigationBarTextStyle)) {
          String str = window.navigationBarTextStyle;
          this.mBarTextStyle = str;
          return str;
        } 
      } 
    } 
    this.mBarTextStyle = "white";
    return "white";
  }
  
  protected String getNavigationStyle(String paramString) {
    if (!TextUtils.isEmpty(this.mStyle))
      return this.mStyle; 
    AppConfig appConfig = AppbrandApplicationImpl.getInst().getAppConfig();
    if (appConfig != null) {
      if (appConfig.page != null) {
        AppConfig.Window window = appConfig.page.getWindow(paramString);
        if (window != null && window.hasNavigationStyle)
          return window.navigationStyle; 
      } 
      AppConfig.Global global = appConfig.global;
      if (global != null) {
        AppConfig.Window window = global.window;
        if (window != null && window.hasNavigationStyle)
          return window.navigationStyle; 
      } 
    } 
    return "default";
  }
  
  protected String getNavigationTransparentTitle(String paramString) {
    if (!TextUtils.isEmpty(this.mTransparentTitle))
      return this.mTransparentTitle; 
    AppConfig appConfig = AppbrandApplicationImpl.getInst().getAppConfig();
    if (appConfig != null) {
      if (appConfig.page != null) {
        AppConfig.Window window = appConfig.page.getWindow(paramString);
        if (window != null && window.hasTransparentTitle) {
          String str = getLegalTransParentTitle(window.transparentTitle);
          this.mTransparentTitle = str;
          return str;
        } 
      } 
      AppConfig.Global global = appConfig.global;
      if (global != null) {
        AppConfig.Window window = global.window;
        if (window != null && window.hasTransparentTitle) {
          String str = getLegalTransParentTitle(window.transparentTitle);
          this.mTransparentTitle = str;
          return str;
        } 
      } 
    } 
    this.mTransparentTitle = "none";
    return "none";
  }
  
  protected String getPageTitleText(String paramString) {
    if (!TextUtils.isEmpty(this.mPageText))
      return this.mPageText; 
    AppConfig appConfig = AppbrandApplicationImpl.getInst().getAppConfig();
    if (appConfig != null) {
      AppConfig.Page page = appConfig.page;
      if (page != null) {
        AppConfig.Window window = page.getWindow(paramString);
        if (window != null && window.hasNavigationBarTitleText) {
          String str = window.navigationBarTitleText;
          this.mPageText = str;
          return str;
        } 
      } 
    } 
    this.mPageText = null;
    return null;
  }
  
  protected boolean isBottomPage() {
    return this.mSinglePageView.getHost() instanceof com.tt.miniapp.page.AppbrandHomePageViewWindow;
  }
  
  protected boolean isShowBackHomeInMenuDialog() {
    AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
    AppConfig appConfig = AppbrandApplicationImpl.getInst().getAppConfig();
    if (appInfoEntity != null) {
      if (appConfig == null)
        return false; 
      if ((!appConfig.isBackToHome && !TextUtils.isEmpty(appInfoEntity.startPage) && !TextUtils.equals(AppConfig.getPagePath(this.mSinglePageView.getPage()), appConfig.mEntryPath) && !TextUtils.equals(AppConfig.getPagePath(appInfoEntity.startPage), appConfig.mEntryPath)) || (TextUtils.isEmpty(appInfoEntity.startPage) && this.mSinglePageView.isReLaunch() && !this.mSinglePageView.isRedirectTo() && !appConfig.isBackToHome && !this.mIsCustomNavigation && !UIUtils.isViewVisible((View)this.mPageCloseButton) && !TextUtils.equals(AppConfig.getPagePath(this.mSinglePageView.getPage()), appConfig.mEntryPath)))
        return true; 
    } 
    return false;
  }
  
  protected boolean isShowLeftBackHomeView() {
    AppConfig appConfig = AppbrandApplicationImpl.getInst().getAppConfig();
    return (appConfig == null) ? false : (this.mIsCustomNavigation ? false : (!isBottomPage() ? false : ((!PageUtil.isTabPage(this.mSinglePageView.getPagePath(), appConfig) && !TextUtils.equals(appConfig.mEntryPath, this.mSinglePageView.getPagePath())))));
  }
  
  protected void onClickBackHomeButton() {
    Event.builder("mp_home_btn_click").flush();
    AppConfig appConfig = AppbrandApplicationImpl.getInst().getAppConfig();
    if (appConfig != null) {
      if (TextUtils.isEmpty(appConfig.mEntryPath))
        return; 
      ((PageRouter)AppbrandApplicationImpl.getInst().getService(PageRouter.class)).reLaunchByUrl(appConfig.mEntryPath);
    } 
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\titlebar\AppbrandTitleBar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */