package com.tt.miniapp.msg.tabbar;

import android.text.TextUtils;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.page.AppbrandHomePageViewWindow;
import com.tt.miniapp.route.PageRouter;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.option.e.e;
import org.json.JSONObject;

public class ApiSetTabBarItem extends b {
  public ApiSetTabBarItem(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    try {
      JSONObject jSONObject = new JSONObject(this.mArgs);
      ThreadUtil.runOnUIThread(new Runnable() {
            public void run() {
              AppbrandHomePageViewWindow appbrandHomePageViewWindow = ((PageRouter)AppbrandApplicationImpl.getInst().getService(PageRouter.class)).getViewWindowRoot().getAppbrandHomePage();
              if (!appbrandHomePageViewWindow.isTabMode()) {
                ApiSetTabBarItem.this.callbackFail("not TabBar page");
                return;
              } 
              String str = appbrandHomePageViewWindow.setTabItem(index, text, iconPath, selectedIconPath);
              if (TextUtils.isEmpty(str)) {
                ApiSetTabBarItem.this.callbackOk();
                return;
              } 
              ApiSetTabBarItem.this.callbackFail(str);
            }
          });
      return;
    } catch (Exception exception) {
      callbackFail(exception);
      return;
    } 
  }
  
  public String getActionName() {
    return "setTabBarItem";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\tabbar\ApiSetTabBarItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */