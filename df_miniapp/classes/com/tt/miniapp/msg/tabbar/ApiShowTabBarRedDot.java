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

public class ApiShowTabBarRedDot extends b {
  public ApiShowTabBarRedDot(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      ThreadUtil.runOnUIThread(new Runnable() {
            public void run() {
              AppbrandHomePageViewWindow appbrandHomePageViewWindow = ((PageRouter)AppbrandApplicationImpl.getInst().getService(PageRouter.class)).getViewWindowRoot().getAppbrandHomePage();
              if (!appbrandHomePageViewWindow.isTabMode()) {
                ApiShowTabBarRedDot.this.callbackFail("not TabBar page");
                return;
              } 
              String str = appbrandHomePageViewWindow.setTabBarRedDotVisibility(pageIndex, true);
              if (TextUtils.isEmpty(str)) {
                ApiShowTabBarRedDot.this.callbackOk();
                return;
              } 
              ApiShowTabBarRedDot.this.callbackFail(str);
            }
          });
      return;
    } catch (JSONException jSONException) {
      callbackFail((Throwable)jSONException);
      AppBrandLogger.e("tma_ApiShowTabbarRedDot", new Object[] { jSONException });
      return;
    } 
  }
  
  public String getActionName() {
    return "showTabBarRedDot";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\tabbar\ApiShowTabBarRedDot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */