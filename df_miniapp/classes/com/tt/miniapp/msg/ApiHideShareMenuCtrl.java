package com.tt.miniapp.msg;

import android.support.v4.f.a;
import android.text.TextUtils;
import com.tt.frontendapiinterface.a;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.option.e.e;

public class ApiHideShareMenuCtrl extends b {
  public ApiHideShareMenuCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    AppbrandApplicationImpl appbrandApplicationImpl = AppbrandApplicationImpl.getInst();
    a a = appbrandApplicationImpl.getCurrentPageHideShareMenuArrayMap();
    AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
    boolean bool = appInfoEntity.isGame();
    Boolean bool1 = Boolean.valueOf(true);
    if (bool) {
      a.put(appInfoEntity.appId, bool1);
    } else {
      String str = appbrandApplicationImpl.getCurrentPageUrl();
      if (!TextUtils.isEmpty(str)) {
        AppBrandLogger.d("tma_ApiHideShareMenuCtrl", new Object[] { "currentPage:", str });
        a.put(str, bool1);
      } else {
        callbackFail(a.c("currentPage"));
        return;
      } 
    } 
    callbackOk();
  }
  
  public String getActionName() {
    return "hideShareMenu";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiHideShareMenuCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */