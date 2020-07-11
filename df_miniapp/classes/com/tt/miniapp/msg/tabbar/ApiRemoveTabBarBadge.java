package com.tt.miniapp.msg.tabbar;

import android.text.TextUtils;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.page.AppbrandHomePageViewWindow;
import com.tt.miniapp.route.PageRouter;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.option.e.e;
import org.json.JSONException;

public class ApiRemoveTabBarBadge extends b {
  public ApiRemoveTabBarBadge(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      ThreadUtil.runOnUIThread(new Runnable() {
            public void run() {
              AppbrandHomePageViewWindow appbrandHomePageViewWindow = ((PageRouter)AppbrandApplicationImpl.getInst().getService(PageRouter.class)).getViewWindowRoot().getAppbrandHomePage();
              if (!appbrandHomePageViewWindow.isTabMode()) {
                ApiRemoveTabBarBadge.this.callbackFail("not TabBar page");
                return;
              } 
              String str = appbrandHomePageViewWindow.setTabBadge(false, pageIndex, "");
              if (TextUtils.isEmpty(str)) {
                ApiRemoveTabBarBadge.this.callbackOk();
                return;
              } 
              ApiRemoveTabBarBadge.this.callbackFail(str);
            }
          });
      return;
    } catch (JSONException jSONException) {
      callbackFail((Throwable)jSONException);
      AppBrandLogger.e("ApiRemoveTabbarBadge", new Object[] { jSONException });
      return;
    } 
  }
  
  public String getActionName() {
    return "removeTabBarBadge";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\tabbar\ApiRemoveTabBarBadge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */