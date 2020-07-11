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

public class ApiRemoveFromFavorites extends b {
  String appId;
  
  public ApiRemoveFromFavorites(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  private void initArgs() throws JSONException {
    JSONObject jSONObject = new JSONObject(this.mArgs);
    AppBrandLogger.d("ApiHandler", new Object[] { "mArgs == ", this.mArgs });
    this.appId = jSONObject.optString("appId", null);
  }
  
  public static String removeMiniappFromCurrentUserFavoriteListOfNet(String paramString) {
    StringBuilder stringBuilder1 = new StringBuilder(AppbrandConstant.OpenApi.getInst().getREMOVE_MINIAPP_FROM_COLLECTION_LIST());
    StringBuilder stringBuilder2 = new StringBuilder("?aid=");
    stringBuilder2.append(AppbrandContext.getInst().getInitParams().getAppId());
    stringBuilder1.append(stringBuilder2.toString());
    stringBuilder2 = new StringBuilder("&appid=");
    stringBuilder2.append(paramString);
    stringBuilder1.append(stringBuilder2.toString());
    AppBrandLogger.d("ApiHandler", new Object[] { "removeMiniappFromCurrentUserFavoriteSetOfNet", "url == ", stringBuilder1.toString() });
    i i = new i(stringBuilder1.toString(), "GET");
    i.a("X-Tma-Host-Sessionid", (UserInfoManager.getHostClientUserInfo()).sessionId);
    String str = NetManager.getInst().request(i).a();
    AppBrandLogger.d("ApiHandler", new Object[] { "removeMiniappFromCurrentUserFavoriteSetOfNet", "respData == ", str });
    if (str != null)
      try {
        boolean bool;
        if ((new JSONObject(str)).optInt("error", 1) == 0) {
          bool = true;
        } else {
          bool = false;
        } 
        if (bool) {
          HostProcessBridge.removeMiniAppFromFavoriteMiniAppList(paramString);
          InnerHostProcessBridge.removeFromFavoriteSet(paramString);
          return str;
        } 
      } catch (JSONException jSONException) {
        AppBrandLogger.e("ApiHandler", new Object[] { jSONException });
      }  
    return str;
  }
  
  public void act() {
    try {
      initArgs();
      Observable.create(new Function<String>() {
            public String fun() {
              return ApiRemoveFromFavorites.removeMiniappFromCurrentUserFavoriteListOfNet(ApiRemoveFromFavorites.this.appId);
            }
          }).subscribe((Subscriber)new Subscriber.ResultableSubscriber<String>() {
            public void onError(Throwable param1Throwable) {
              AppBrandLogger.d("ApiHandler", new Object[] { param1Throwable });
              ApiRemoveFromFavorites.this.callbackFail(param1Throwable);
            }
            
            public void onSuccess(String param1String) {
              JSONObject jSONObject = new JSONObject();
              try {
                jSONObject.put("response", new JSONObject(param1String));
                ApiRemoveFromFavorites.this.callbackOk(jSONObject);
                return;
              } catch (JSONException jSONException) {
                ApiRemoveFromFavorites.this.callbackFail("Server callback result not json!");
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
    return "removeFromFavorites";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\favorite\ApiRemoveFromFavorites.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */