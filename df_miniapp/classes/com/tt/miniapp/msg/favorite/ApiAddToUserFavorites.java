package com.tt.miniapp.msg.favorite;

import android.content.Context;
import com.storage.async.Action;
import com.storage.async.Schedulers;
import com.tt.frontendapiinterface.b;
import com.tt.frontendapiinterface.d;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapp.favorite.FavoriteGuideWidget;
import com.tt.miniapp.favorite.FavoriteUtils;
import com.tt.miniapp.thread.ThreadUtil;
import com.tt.miniapp.user.TmaUserManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.MiniappHostBase;
import com.tt.miniapphost.host.HostDependManager;
import com.tt.option.e.e;
import org.json.JSONObject;

public class ApiAddToUserFavorites extends b {
  public ApiAddToUserFavorites(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  private void addMiniAppToFavoriteListFail(String paramString) {
    StringBuilder stringBuilder = new StringBuilder("add to user favorites failed: ");
    stringBuilder.append(paramString);
    callbackExtraInfoMsg(false, stringBuilder.toString());
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            String str = ApiAddToUserFavorites.this.getContext().getString(2097741891);
            HostDependManager.getInst().showToast(ApiAddToUserFavorites.this.getContext(), null, str, 0L, null);
          }
        });
  }
  
  private void addMiniAppToFavoriteListSuccess(final boolean isFirst) {
    callbackOk();
    ThreadUtil.runOnUIThread(new Runnable() {
          public void run() {
            if (isFirst) {
              AppBrandLogger.d("ApiHandler", new Object[] { "addMiniAppToFavoriteList", "firstFavorite" });
              HostDependManager.getInst().firstFavoriteAction();
              return;
            } 
            String str = ApiAddToUserFavorites.this.getContext().getString(2097741895);
            HostDependManager.getInst().showToast(ApiAddToUserFavorites.this.getContext(), null, str, 0L, "success");
          }
        });
  }
  
  private void doAddMiniAppToFavoriteList() {
    ThreadUtil.runOnWorkThread(new Action() {
          public void act() {
            if (ApiAddToUserFavorites.this.addMiniAppToFavoriteListAction())
              ThreadUtil.runOnUIThread(new Runnable() {
                    public void run() {
                      FavoriteGuideWidget.dismissAllFavoriteGuide();
                    }
                  },  ); 
          }
        },  Schedulers.longIO());
  }
  
  public void act() {
    d d = FavoriteUtils.checkCommonLimit();
    if (!d.a) {
      callbackFail(d.b);
      return;
    } 
    if (!TmaUserManager.getInstance().isLogin()) {
      callbackFail("Client NOT login");
      return;
    } 
    doAddMiniAppToFavoriteList();
  }
  
  public boolean addMiniAppToFavoriteListAction() {
    String str = ApiAddToFavorites.addMiniappToCurrentUserFavoriteListOfNet((AppbrandApplicationImpl.getInst().getAppInfo()).appId);
    try {
      JSONObject jSONObject = new JSONObject(str);
      int i = jSONObject.optInt("error", 1);
      AppBrandLogger.d("ApiHandler", new Object[] { "addMiniAppToFavoriteList", "error == ", Integer.valueOf(i) });
      if (i == 0) {
        i = 1;
      } else {
        i = 0;
      } 
      if (i != 0) {
        jSONObject = jSONObject.optJSONObject("data");
        if (jSONObject != null) {
          boolean bool1 = jSONObject.optBoolean("isFirst", false);
          addMiniAppToFavoriteListSuccess(bool1);
          return true;
        } 
      } else {
        AppBrandLogger.d("ApiHandler", new Object[] { "addMiniAppToFavoriteList", jSONObject.optString("data", "not errMsg") });
        addMiniAppToFavoriteListFail(str);
        return false;
      } 
    } catch (Exception exception) {
      AppBrandLogger.e("ApiHandler", new Object[] { exception });
      addMiniAppToFavoriteListFail(str);
      return false;
    } 
    boolean bool = false;
    addMiniAppToFavoriteListSuccess(bool);
    return true;
  }
  
  public String getActionName() {
    return "addToUserFavorites";
  }
  
  public Context getContext() {
    MiniappHostBase miniappHostBase = AppbrandContext.getInst().getCurrentActivity();
    return (Context)((miniappHostBase != null) ? miniappHostBase : AppbrandContext.getInst().getApplicationContext());
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\favorite\ApiAddToUserFavorites.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */