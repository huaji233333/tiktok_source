package com.tt.miniapp.route;

import android.content.Context;
import android.text.TextUtils;
import com.tt.frontendapiinterface.ApiCallResult;
import com.tt.miniapp.AppConfig;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.AppbrandServiceManager;
import com.tt.miniapp.LifeCycleManager;
import com.tt.miniapp.LifeCycleManager.LifecycleInterest;
import com.tt.miniapp.page.AppbrandViewWindowRoot;
import com.tt.miniapp.util.PageUtil;
import com.tt.miniapphost.AppbrandContext;

public class PageRouter extends AppbrandServiceManager.ServiceBase {
  private AppbrandViewWindowRoot mAppbrandPageRoot;
  
  private final Context mContext = (Context)AppbrandContext.getInst().getApplicationContext();
  
  public PageRouter(AppbrandApplicationImpl paramAppbrandApplicationImpl) {
    super(paramAppbrandApplicationImpl);
  }
  
  public AppbrandViewWindowRoot getViewWindowRoot() {
    return this.mAppbrandPageRoot;
  }
  
  @LifecycleInterest({LifeCycleManager.LifeCycleEvent.ON_APP_CREATE})
  public void onAppCreate() {
    this.mAppbrandPageRoot = new AppbrandViewWindowRoot(this.mContext, this.mApp);
  }
  
  public boolean onBackPressed() {
    this.mApp.getLifeCycleManager().notifyAppRoute();
    return this.mAppbrandPageRoot.onBackPressed();
  }
  
  public void reLaunchByUrl(String paramString) {
    this.mApp.getLifeCycleManager().notifyAppRoute();
    if (TextUtils.isEmpty(paramString))
      return; 
    PageUtil.PageRouterParams pageRouterParams = new PageUtil.PageRouterParams();
    pageRouterParams.url = paramString;
    int i = paramString.indexOf("?");
    String str = paramString;
    if (i > 0)
      str = paramString.substring(0, i); 
    pageRouterParams.path = str;
    route("reLaunch", pageRouterParams);
  }
  
  public ApiCallResult.a route(String paramString, PageUtil.PageRouterParams paramPageRouterParams) {
    this.mApp.getLifeCycleManager().notifyAppRoute();
    return "navigateTo".equals(paramString) ? this.mAppbrandPageRoot.navigateTo(paramPageRouterParams) : ("navigateBack".equals(paramString) ? this.mAppbrandPageRoot.navigateBack(paramPageRouterParams) : ("redirectTo".equals(paramString) ? this.mAppbrandPageRoot.redirectTo(paramPageRouterParams) : ("reLaunch".equals(paramString) ? this.mAppbrandPageRoot.reLaunch(paramPageRouterParams) : ("switchTab".equals(paramString) ? this.mAppbrandPageRoot.switchTab(paramPageRouterParams) : null))));
  }
  
  public void setup(AppConfig paramAppConfig, String paramString) {
    this.mAppbrandPageRoot.setupLaunch(paramAppConfig, paramString);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\route\PageRouter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */