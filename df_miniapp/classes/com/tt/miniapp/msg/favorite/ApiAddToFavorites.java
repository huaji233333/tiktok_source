package com.tt.miniapp.msg.favorite;

import com.storage.async.Function;
import com.storage.async.Observable;
import com.storage.async.Subscriber;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.manager.NetManager;
import com.tt.miniapp.manager.UserInfoManager;
import com.tt.miniapp.process.bridge.InnerHostProcessBridge;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.option.e.e;
import com.tt.option.q.i;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiAddToFavorites extends b {
  String appId;
  
  public ApiAddToFavorites(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public static String addMiniappToCurrentUserFavoriteListOfNet(String paramString) {
    StringBuilder stringBuilder1 = new StringBuilder(AppbrandConstant.OpenApi.getInst().getADD_MINIAPP_TO_COLLECTION_LIST());
    StringBuilder stringBuilder2 = new StringBuilder("?aid=");
    stringBuilder2.append(AppbrandContext.getInst().getInitParams().getAppId());
    stringBuilder1.append(stringBuilder2.toString());
    stringBuilder2 = new StringBuilder("&appid=");
    stringBuilder2.append(paramString);
    stringBuilder1.append(stringBuilder2.toString());
    AppBrandLogger.d("ApiHandler", new Object[] { "addMiniappToCurrentUserFavoritesOfNet", "url == ", stringBuilder1.toString() });
    i i = new i(stringBuilder1.toString(), "GET");
    i.a("X-Tma-Host-Sessionid", (UserInfoManager.getHostClientUserInfo()).sessionId);
    String str = NetManager.getInst().request(i).a();
    AppBrandLogger.d("ApiHandler", new Object[] { "addMiniappToCurrentUserFavoritesOfNet", "respData == ", str });
    if (str != null)
      try {
        boolean bool;
        if ((new JSONObject(str)).optInt("error", 1) == 0) {
          bool = true;
        } else {
          bool = false;
        } 
        if (bool) {
          HostProcessBridge.addMiniAppToFavoriteMiniAppList(paramString);
          InnerHostProcessBridge.addToFavoriteSet(paramString);
          return str;
        } 
      } catch (JSONException jSONException) {
        AppBrandLogger.e("ApiHandler", new Object[] { jSONException });
      }  
    return str;
  }
  
  private void initArgs() throws JSONException {
    JSONObject jSONObject = new JSONObject(this.mArgs);
    AppBrandLogger.d("ApiHandler", new Object[] { "mArgs == ", this.mArgs });
    this.appId = jSONObject.optString("appId", null);
  }
  
  public void act() {
    try {
      initArgs();
      Observable.create(new Function<String>() {
            public String fun() {
              return ApiAddToFavorites.addMiniappToCurrentUserFavoriteListOfNet(ApiAddToFavorites.this.appId);
            }
          }).subscribe((Subscriber)new Subscriber.ResultableSubscriber<String>() {
            public void onError(Throwable param1Throwable) {
              AppBrandLogger.d("ApiHandler", new Object[] { param1Throwable });
              ApiAddToFavorites.this.callbackFail(param1Throwable);
            }
            
            public void onSuccess(String param1String) {
              JSONObject jSONObject = new JSONObject();
              try {
                jSONObject.put("response", new JSONObject(param1String));
                ApiAddToFavorites.this.callbackOk(jSONObject);
                return;
              } catch (JSONException jSONException) {
                ApiAddToFavorites.this.callbackFail("Server callback result not json!");
                return;
              } 
            }
          });
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.e("ApiHandler", new Object[] { jSONException });
      callbackFail((Throwable)jSONException);
      return;
    } 
  }
  
  public String getActionName() {
    return "addToFavorites";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\favorite\ApiAddToFavorites.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */