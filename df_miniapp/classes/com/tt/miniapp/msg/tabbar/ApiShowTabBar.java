package com.tt.miniapp.msg.tabbar;

import android.text.TextUtils;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.page.AppbrandHomePageViewWindow;
import com.tt.miniapp.route.PageRouter;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.option.e.e;
import org.json.JSONException;

public class ApiShowTabBar extends b {
  public ApiShowTabBar(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      ThreadUtil.runOnUIThread(new Runnable() {
            public void run() {
              AppbrandHomePageViewWindow appbrandHomePageViewWindow = ((PageRouter)AppbrandApplicationImpl.getInst().getService(PageRouter.class)).getViewWindowRoot().getAppbrandHomePage();
              if (!appbrandHomePageViewWindow.isTabMode()) {
                ApiShowTabBar.this.callbackFail("not TabBar page");
                return;
              } 
              String str = appbrandHomePageViewWindow.setTabBarVisibility(true, needAnimation);
              if (TextUtils.isEmpty(str)) {
                ApiShowTabBar.this.callbackOk();
                return;
              } 
              ApiShowTabBar.this.callbackFail(str);
            }
          });
      return;
    } catch (JSONException jSONException) {
      callbackFail((Throwable)jSONException);
      return;
    } 
  }
  
  public String getActionName() {
    return "showTabBar";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\tabbar\ApiShowTabBar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */