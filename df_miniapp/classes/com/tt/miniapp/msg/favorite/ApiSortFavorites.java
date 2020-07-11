package com.tt.miniapp.msg.favorite;

import com.storage.async.Function;
import com.storage.async.Observable;
import com.storage.async.Subscriber;
import com.tt.frontendapiinterface.b;
import com.tt.miniapp.AppbrandConstant;
import com.tt.miniapp.manager.NetManager;
import com.tt.miniapp.manager.UserInfoManager;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import com.tt.option.e.e;
import com.tt.option.q.i;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiSortFavorites extends b {
  String appId;
  
  Boolean isBefore;
  
  String pivotAppId;
  
  public ApiSortFavorites(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  private void initArgs() throws JSONException {
    JSONObject jSONObject = new JSONObject(this.mArgs);
    AppBrandLogger.d("ApiHandler", new Object[] { "mArgs == ", this.mArgs });
    this.appId = jSONObject.optString("appId", null);
    this.pivotAppId = jSONObject.optString("pivotAppId", null);
    if (jSONObject.has("isBefore"))
      this.isBefore = Boolean.valueOf(jSONObject.optBoolean("isBefore")); 
  }
  
  public static String sortCurrentUserFavoriteListOfNet(String paramString1, String paramString2, boolean paramBoolean) {
    StringBuilder stringBuilder2 = new StringBuilder(AppbrandConstant.OpenApi.getInst().getSORT_COLLECTION_LIST());
    StringBuilder stringBuilder3 = new StringBuilder("?aid=");
    stringBuilder3.append(AppbrandContext.getInst().getInitParams().getAppId());
    stringBuilder2.append(stringBuilder3.toString());
    stringBuilder3 = new StringBuilder("&appid=");
    stringBuilder3.append(paramString1);
    stringBuilder2.append(stringBuilder3.toString());
    StringBuilder stringBuilder1 = new StringBuilder("&pivot_appid=");
    stringBuilder1.append(paramString2);
    stringBuilder2.append(stringBuilder1.toString());
    stringBuilder1 = new StringBuilder("&is_before=");
    stringBuilder1.append(paramBoolean);
    stringBuilder2.append(stringBuilder1.toString());
    AppBrandLogger.d("ApiHandler", new Object[] { "sortCurrentUserFavoriteSetOfNet", "url == ", stringBuilder2.toString() });
    i i = new i(stringBuilder2.toString(), "GET");
    i.a("X-Tma-Host-Sessionid", (UserInfoManager.getHostClientUserInfo()).sessionId);
    String str = NetManager.getInst().request(i).a();
    AppBrandLogger.d("ApiHandler", new Object[] { "sortCurrentUserFavoriteSetOfNet", "respData == ", str });
    return str;
  }
  
  public void act() {
    try {
      initArgs();
      Observable.create(new Function<String>() {
            public String fun() {
              return ApiSortFavorites.sortCurrentUserFavoriteListOfNet(ApiSortFavorites.this.appId, ApiSortFavorites.this.pivotAppId, ApiSortFavorites.this.isBefore.booleanValue());
            }
          }).subscribe((Subscriber)new Subscriber.ResultableSubscriber<String>() {
            public void onError(Throwable param1Throwable) {
              AppBrandLogger.d("ApiHandler", new Object[] { param1Throwable });
              ApiSortFavorites.this.callbackFail(param1Throwable);
            }
            
            public void onSuccess(String param1String) {
              JSONObject jSONObject = new JSONObject();
              try {
                jSONObject.put("response", new JSONObject(param1String));
                ApiSortFavorites.this.callbackOk(jSONObject);
                return;
              } catch (JSONException jSONException) {
                ApiSortFavorites.this.callbackFail("Server callback result not json!");
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
    return "sortFavorites";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\favorite\ApiSortFavorites.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */