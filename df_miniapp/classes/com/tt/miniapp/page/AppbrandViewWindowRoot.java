package com.tt.miniapp.page;

import android.content.Context;
import android.text.TextUtils;
import android.view.animation.Animation;
import com.tt.frontendapiinterface.ApiCallResult;
import com.tt.miniapp.AppConfig;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandServiceManager;
import com.tt.miniapp.base.ui.viewwindow.ViewWindow;
import com.tt.miniapp.base.ui.viewwindow.ViewWindowRoot;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.util.PageUtil;
import com.tt.miniapp.util.RenderSnapShotManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.UIUtils;
import d.f.b.g;
import d.f.b.l;

public final class AppbrandViewWindowRoot extends ViewWindowRoot<AppbrandViewWindowBase> {
  public static final Companion Companion = new Companion(null);
  
  public final AppbrandApplicationImpl mApp;
  
  public final AppbrandHomePageViewWindow mHomePage;
  
  public AppbrandViewWindowRoot(Context paramContext, AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramContext);
    this.mApp = paramAppbrandApplicationImpl;
    this.mHomePage = new AppbrandHomePageViewWindow(getMContext(), this.mApp);
    showViewWindow(this.mHomePage, null);
  }
  
  public final AppbrandHomePageViewWindow getAppbrandHomePage() {
    return this.mHomePage;
  }
  
  public final ApiCallResult.a navigateBack(PageUtil.PageRouterParams paramPageRouterParams) {
    ApiCallResult.a a;
    l.b(paramPageRouterParams, "params");
    Integer integer = Integer.valueOf(1);
    AppBrandLogger.d("AppbrandViewWindowRoot", new Object[] { "navigateBack" });
    int i = getViewWindowCount();
    if (i <= 0) {
      a = ApiCallResult.a.b("navigateBack").d("top view is null").a("code", integer);
      l.a(a, "ApiCallResult.Builder.cr…ant.AppRouter.ROUTE_FAIL)");
      return a;
    } 
    if (i == 1) {
      a = ApiCallResult.a.b("navigateBack").d("cannot navigate back at first page").a("code", integer);
      l.a(a, "ApiCallResult.Builder.cr…ant.AppRouter.ROUTE_FAIL)");
      return a;
    } 
    int j = Math.min(Math.max(((PageUtil.PageRouterParams)a).delta, 1), i - 1);
    for (i = 0; i < j - 1; i++) {
      a = getMViewWindowList().get(getMViewWindowList().size() - 2);
      l.a(a, "mViewWindowList[mViewWindowList.size - 2]");
      closeViewWindow((ViewWindow)a);
    } 
    AppbrandViewWindowBase appbrandViewWindowBase = (AppbrandViewWindowBase)getTopView();
    if (appbrandViewWindowBase != null) {
      closeViewWindowWithAnim(appbrandViewWindowBase, UIUtils.getSlideOutAnimation(), null);
      appbrandViewWindowBase = (AppbrandViewWindowBase)getTopView();
      if (appbrandViewWindowBase != null) {
        appbrandViewWindowBase.setVisibility(0);
        appbrandViewWindowBase.sendOnAppRoute("navigateBack");
        ApiCallResult.a a1 = ApiCallResult.a.a("navigateBack").a("code", Integer.valueOf(0));
        l.a(a1, "ApiCallResult.Builder.cr…stant.AppRouter.ROUTE_OK)");
        return a1;
      } 
      throw (Throwable)new RuntimeException("TopView为空，不可能的情况_navigateBack2");
    } 
    Throwable throwable = new RuntimeException("TopView为空，不可能的情况_navigateBack");
    throw throwable;
  }
  
  public final ApiCallResult.a navigateTo(PageUtil.PageRouterParams paramPageRouterParams) {
    ApiCallResult.a a2;
    l.b(paramPageRouterParams, "params");
    Integer integer = Integer.valueOf(1);
    AppBrandLogger.d("AppbrandViewWindowRoot", new Object[] { "navigateTo" });
    if (getViewWindowCount() >= 10) {
      a2 = ApiCallResult.a.b("navigateTo").d("页面跳转超过10个").a("code", integer);
      l.a(a2, "ApiCallResult.Builder.cr…ant.AppRouter.ROUTE_FAIL)");
      return a2;
    } 
    AppConfig appConfig = this.mApp.getAppConfig();
    if (PageUtil.isTabPage(((PageUtil.PageRouterParams)a2).url, appConfig)) {
      a2 = ApiCallResult.a.b("navigateTo").d("只能跳到非Tab页面").a("code", integer);
      l.a(a2, "ApiCallResult.Builder.cr…ant.AppRouter.ROUTE_FAIL)");
      return a2;
    } 
    if (appConfig != null && TextUtils.equals(((PageUtil.PageRouterParams)a2).path, appConfig.mEntryPath))
      appConfig.isBackToHome = true; 
    AppbrandViewWindowBase appbrandViewWindowBase = (AppbrandViewWindowBase)getTopView();
    AppbrandSinglePageViewWindow appbrandSinglePageViewWindow = new AppbrandSinglePageViewWindow(getMContext(), this.mApp);
    String str = ((PageUtil.PageRouterParams)a2).url;
    l.a(str, "params.url");
    appbrandSinglePageViewWindow.setupRouterParams(str, "navigateTo");
    showViewWindowWithAnim(appbrandSinglePageViewWindow, null, UIUtils.getSlideInAnimation(), new AppbrandViewWindowRoot$navigateTo$1(appbrandViewWindowBase));
    ApiCallResult.a a1 = ApiCallResult.a.a("navigateTo").a("code", Integer.valueOf(0));
    l.a(a1, "ApiCallResult.Builder.cr…stant.AppRouter.ROUTE_OK)");
    return a1;
  }
  
  public final boolean onBackPressed() {
    AppBrandLogger.d("AppbrandViewWindowRoot", new Object[] { "onBackPressed" });
    AppbrandViewWindowBase appbrandViewWindowBase = (AppbrandViewWindowBase)getTopView();
    if (appbrandViewWindowBase != null) {
      boolean bool = appbrandViewWindowBase.onBackPressed();
      if (!bool && getViewWindowCount() > 1) {
        closeViewWindowWithAnim(appbrandViewWindowBase, UIUtils.getSlideOutAnimation(), null);
        appbrandViewWindowBase = (AppbrandViewWindowBase)getTopView();
        if (appbrandViewWindowBase != null) {
          appbrandViewWindowBase.setVisibility(0);
          appbrandViewWindowBase.sendOnAppRoute("navigateBack");
          return true;
        } 
        throw (Throwable)new RuntimeException("TopView为空，不可能的情况_onBackPressed");
      } 
      return bool;
    } 
    return false;
  }
  
  protected final void onChildViewSwipedBack(AppbrandViewWindowBase paramAppbrandViewWindowBase) {
    l.b(paramAppbrandViewWindowBase, "viewWindow");
    super.onChildViewSwipedBack(paramAppbrandViewWindowBase);
    paramAppbrandViewWindowBase = (AppbrandViewWindowBase)getTopView();
    if (paramAppbrandViewWindowBase != null)
      paramAppbrandViewWindowBase.sendOnAppRoute("navigateBack"); 
  }
  
  protected final void onChildViewSwipedCancel(AppbrandViewWindowBase paramAppbrandViewWindowBase) {
    l.b(paramAppbrandViewWindowBase, "viewWindow");
    super.onChildViewSwipedCancel(paramAppbrandViewWindowBase);
    int i = getViewWindowCount();
    if (i > 1) {
      paramAppbrandViewWindowBase = getMViewWindowList().get(i - 2);
      l.a(paramAppbrandViewWindowBase, "mViewWindowList[curPageCount - 2]");
      paramAppbrandViewWindowBase.setVisibility(4);
    } 
  }
  
  protected final void onChildViewSwipedStart(AppbrandViewWindowBase paramAppbrandViewWindowBase) {
    l.b(paramAppbrandViewWindowBase, "viewWindow");
    super.onChildViewSwipedStart(paramAppbrandViewWindowBase);
    int i = getViewWindowCount();
    if (i > 1) {
      paramAppbrandViewWindowBase = getMViewWindowList().get(i - 2);
      l.a(paramAppbrandViewWindowBase, "mViewWindowList[curPageCount - 2]");
      paramAppbrandViewWindowBase.setVisibility(0);
    } 
  }
  
  public final AppbrandSinglePage prepareSnapShotPage() {
    return this.mHomePage.prepareSnapShotPage();
  }
  
  public final ApiCallResult.a reLaunch(PageUtil.PageRouterParams paramPageRouterParams) {
    ApiCallResult.a a;
    l.b(paramPageRouterParams, "params");
    int j = getViewWindowCount();
    if (j <= 0) {
      a = ApiCallResult.a.b("reLaunch").d("top view is null").a("code", Integer.valueOf(1));
      l.a(a, "ApiCallResult.Builder.cr…ant.AppRouter.ROUTE_FAIL)");
      return a;
    } 
    for (int i = 0; i < j - 2; i++) {
      ViewWindow viewWindow = (ViewWindow)getMViewWindowList().get(getMViewWindowList().size() - 2);
      l.a(viewWindow, "mViewWindowList[mViewWindowList.size - 2]");
      closeViewWindow(viewWindow);
    } 
    AppConfig appConfig = this.mApp.getAppConfig();
    if (appConfig == null)
      l.a(); 
    l.a(appConfig, "mApp.appConfig!!");
    this.mHomePage.setVisibility(0);
    String str = ((PageUtil.PageRouterParams)a).url;
    l.a(str, "params.url");
    setupHomePage(appConfig, str, "reLaunch");
    AppbrandViewWindowBase appbrandViewWindowBase = (AppbrandViewWindowBase)getTopView();
    if (appbrandViewWindowBase != null) {
      if (appbrandViewWindowBase != this.mHomePage)
        closeViewWindow(appbrandViewWindowBase); 
      appConfig.isBackToHome = l.a(((PageUtil.PageRouterParams)a).path, appConfig.mEntryPath);
      a = ApiCallResult.a.a("reLaunch").a("code", Integer.valueOf(0));
      l.a(a, "ApiCallResult.Builder.cr…stant.AppRouter.ROUTE_OK)");
      return a;
    } 
    Throwable throwable = new RuntimeException("TopView为空，不可能的情况_reLaunch");
    throw throwable;
  }
  
  public final ApiCallResult.a redirectTo(PageUtil.PageRouterParams paramPageRouterParams) {
    ApiCallResult.a a;
    l.b(paramPageRouterParams, "params");
    Integer integer = Integer.valueOf(1);
    AppBrandLogger.d("AppbrandViewWindowRoot", new Object[] { "redirectTo" });
    if (getViewWindowCount() <= 0) {
      a = ApiCallResult.a.b("redirectTo").d("top view is null").a("code", integer);
      l.a(a, "ApiCallResult.Builder.cr…ant.AppRouter.ROUTE_FAIL)");
      return a;
    } 
    AppConfig appConfig = this.mApp.getAppConfig();
    if (PageUtil.isTabPage(((PageUtil.PageRouterParams)a).url, appConfig)) {
      a = ApiCallResult.a.b("redirectTo").d("只能跳到非Tab页面").a("code", integer);
      l.a(a, "ApiCallResult.Builder.cr…ant.AppRouter.ROUTE_FAIL)");
      return a;
    } 
    if (appConfig != null && TextUtils.equals(((PageUtil.PageRouterParams)a).path, appConfig.mEntryPath))
      appConfig.isBackToHome = true; 
    AppbrandViewWindowBase appbrandViewWindowBase = (AppbrandViewWindowBase)getTopView();
    if (appbrandViewWindowBase != null) {
      String str;
      AppbrandSinglePageViewWindow appbrandSinglePageViewWindow;
      AppbrandHomePageViewWindow appbrandHomePageViewWindow = this.mHomePage;
      if (appbrandViewWindowBase != appbrandHomePageViewWindow) {
        appbrandSinglePageViewWindow = new AppbrandSinglePageViewWindow(getMContext(), this.mApp);
        str = ((PageUtil.PageRouterParams)a).url;
        l.a(str, "params.url");
        appbrandSinglePageViewWindow.setupRouterParams(str, "redirectTo");
        showViewWindow(appbrandSinglePageViewWindow, null);
        closeViewWindow(appbrandViewWindowBase);
      } else {
        str = ((PageUtil.PageRouterParams)str).url;
        l.a(str, "params.url");
        appbrandSinglePageViewWindow.setupSingle(str, "redirectTo");
      } 
      ApiCallResult.a a1 = ApiCallResult.a.a("redirectTo").a("code", Integer.valueOf(0));
      l.a(a1, "ApiCallResult.Builder.cr…stant.AppRouter.ROUTE_OK)");
      return a1;
    } 
    throw (Throwable)new RuntimeException("TopView为空，不可能的情况_redirectTo");
  }
  
  public final void setupHomePage(AppConfig paramAppConfig, String paramString1, String paramString2) {
    l.b(paramAppConfig, "appConfig");
    l.b(paramString1, "entryPath");
    l.b(paramString2, "openType");
    AppConfig.TabBar tabBar = paramAppConfig.getTabBar();
    l.a(tabBar, "appConfig.tabBar");
    if (PageUtil.isTabPage(paramString1, paramAppConfig)) {
      this.mHomePage.setupTabHost(tabBar, paramString1, paramString2);
      return;
    } 
    this.mHomePage.setupSingle(paramString1, paramString2);
  }
  
  public final void setupLaunch(AppConfig paramAppConfig, String paramString) {
    l.b(paramAppConfig, "appConfig");
    l.b(paramString, "entryPath");
    this.mHomePage.prepareLaunch(paramString, "appLaunch");
    ThreadUtil.runOnUIThread(new AppbrandViewWindowRoot$setupLaunch$1(paramString, paramAppConfig));
  }
  
  public final ApiCallResult.a switchTab(PageUtil.PageRouterParams paramPageRouterParams) {
    ApiCallResult.a a;
    l.b(paramPageRouterParams, "params");
    int j = getViewWindowCount();
    boolean bool = true;
    Integer integer = Integer.valueOf(1);
    if (j <= 0) {
      a = ApiCallResult.a.b("switchTab").d("top view is null").a("code", integer);
      l.a(a, "ApiCallResult.Builder.cr…ant.AppRouter.ROUTE_FAIL)");
      return a;
    } 
    AppConfig appConfig = this.mApp.getAppConfig();
    if (appConfig == null)
      l.a(); 
    l.a(appConfig, "mApp.appConfig!!");
    if (!PageUtil.isTabPage(((PageUtil.PageRouterParams)a).url, this.mApp.getAppConfig())) {
      a = ApiCallResult.a.b("switchTab").d("目标页面不是tab").a("code", integer);
      l.a(a, "ApiCallResult.Builder.cr…ant.AppRouter.ROUTE_FAIL)");
      return a;
    } 
    int i;
    for (i = 0; i < j - 2; i++) {
      integer = getMViewWindowList().get(getMViewWindowList().size() - 2);
      l.a(integer, "mViewWindowList[mViewWindowList.size - 2]");
      closeViewWindow((ViewWindow)integer);
    } 
    AppbrandViewWindowBase appbrandViewWindowBase = (AppbrandViewWindowBase)getTopView();
    if (appbrandViewWindowBase != null) {
      String str;
      AppbrandHomePageViewWindow appbrandHomePageViewWindow;
      if (appbrandViewWindowBase != this.mHomePage) {
        i = bool;
      } else {
        i = 0;
      } 
      this.mHomePage.setVisibility(0);
      if (this.mHomePage.isTabMode()) {
        AppbrandSinglePage appbrandSinglePage = this.mHomePage.getCurrentPage();
        if (i != 0 && appbrandSinglePage != null && l.a(appbrandSinglePage.getPagePath(), ((PageUtil.PageRouterParams)a).path)) {
          appbrandSinglePage.sendOnAppRoute("switchTab");
        } else {
          appbrandHomePageViewWindow = this.mHomePage;
          str = ((PageUtil.PageRouterParams)a).path;
          l.a(str, "params.path");
          appbrandHomePageViewWindow.switchTab(str, "switchTab");
        } 
      } else {
        AppbrandHomePageViewWindow appbrandHomePageViewWindow1 = this.mHomePage;
        AppConfig.TabBar tabBar = appbrandHomePageViewWindow.getTabBar();
        l.a(tabBar, "appConfig.tabBar");
        str = ((PageUtil.PageRouterParams)str).path;
        l.a(str, "params.path");
        appbrandHomePageViewWindow1.setupTabHost(tabBar, str, "switchTab");
      } 
      if (i != 0)
        closeViewWindow(appbrandViewWindowBase); 
      ApiCallResult.a a1 = ApiCallResult.a.a("switchTab").a("code", Integer.valueOf(0));
      l.a(a1, "ApiCallResult.Builder.cr…stant.AppRouter.ROUTE_OK)");
      return a1;
    } 
    Throwable throwable = new RuntimeException("TopView为空，不可能的情况_switchTab");
    throw throwable;
  }
  
  public static final class Companion {
    private Companion() {}
  }
  
  public static final class AppbrandViewWindowRoot$navigateTo$1 implements Animation.AnimationListener {
    AppbrandViewWindowRoot$navigateTo$1(AppbrandViewWindowBase param1AppbrandViewWindowBase) {}
    
    public final void onAnimationEnd(Animation param1Animation) {
      AppbrandViewWindowBase appbrandViewWindowBase = this.$topPage;
      if (appbrandViewWindowBase != null)
        appbrandViewWindowBase.setVisibility(4); 
    }
    
    public final void onAnimationRepeat(Animation param1Animation) {}
    
    public final void onAnimationStart(Animation param1Animation) {}
  }
  
  static final class AppbrandViewWindowRoot$setupLaunch$1 implements Runnable {
    AppbrandViewWindowRoot$setupLaunch$1(String param1String, AppConfig param1AppConfig) {}
    
    public final void run() {
      AppbrandServiceManager.ServiceBase serviceBase = AppbrandViewWindowRoot.this.mApp.getService(RenderSnapShotManager.class);
      l.a(serviceBase, "mApp.getService(RenderSnapShotManager::class.java)");
      if (((RenderSnapShotManager)serviceBase).isSnapShotRender()) {
        AppbrandViewWindowRoot.this.mHomePage.setupSnapShotSingle(this.$entryPath, "appLaunch");
        return;
      } 
      AppbrandViewWindowRoot.this.setupHomePage(this.$appConfig, this.$entryPath, "appLaunch");
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\page\AppbrandViewWindowRoot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */