package com.tt.miniapp.msg.favorite;

import android.text.TextUtils;
import com.tt.miniapp.manager.UserInfoManager;
import com.tt.miniapp.msg.sync.SyncMsgCtrl;
import com.tt.miniapp.titlemenu.item.FavoriteMiniAppMenuItem;
import com.tt.miniapphost.AppbrandApplication;
import com.tt.miniapphost.entity.AppInfoEntity;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiIsInUserFavoritesSync extends SyncMsgCtrl {
  public ApiIsInUserFavoritesSync(String paramString) {
    super(paramString);
  }
  
  private String check() {
    String str;
    if (!(UserInfoManager.getHostClientUserInfo()).isLogin) {
      str = "Client NOT login";
    } else {
      str = "";
    } 
    AppInfoEntity appInfoEntity = AppbrandApplication.getInst().getAppInfo();
    if (appInfoEntity == null)
      str = "common env error"; 
    if (appInfoEntity.isBox())
      str = "box app not support"; 
    if (!FavoriteMiniAppMenuItem.isDisplayFavoriteEnterHostLevel())
      str = "favorites function offline"; 
    if (!FavoriteMiniAppMenuItem.isDisplayFavoriteEnterPlatformLevel())
      str = "favorites function offline"; 
    if (TextUtils.isEmpty(appInfoEntity.appId))
      str = "get appId error"; 
    if (!FavoriteMiniAppMenuItem.isCollected())
      str = "not added to favorites list"; 
    return str;
  }
  
  public String act() {
    JSONObject jSONObject1 = new JSONObject();
    JSONObject jSONObject2 = new JSONObject();
    String str = check();
    try {
      jSONObject2.put("isIn", str.contentEquals(""));
      jSONObject2.put("msg", str);
      jSONObject1.put("data", jSONObject2);
      return makeOkMsg(jSONObject1);
    } catch (JSONException jSONException) {
      return makeFailMsg((Throwable)jSONException);
    } 
  }
  
  public String getName() {
    return "isInUserFavoritesSync";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\favorite\ApiIsInUserFavoritesSync.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */