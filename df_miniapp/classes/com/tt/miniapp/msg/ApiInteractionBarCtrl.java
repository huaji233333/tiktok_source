package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.b;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.page.AppbrandSinglePage;
import com.tt.miniapp.page.AppbrandViewWindowBase;
import com.tt.miniapp.route.PageRouter;
import com.tt.option.e.e;

abstract class ApiInteractionBarCtrl extends b {
  public ApiInteractionBarCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  protected AppbrandSinglePage getAppbrandSinglePage() {
    AppbrandViewWindowBase appbrandViewWindowBase = (AppbrandViewWindowBase)((PageRouter)AppbrandApplicationImpl.getInst().getService(PageRouter.class)).getViewWindowRoot().getTopView();
    return (appbrandViewWindowBase != null) ? appbrandViewWindowBase.getCurrentPage() : null;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiInteractionBarCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */