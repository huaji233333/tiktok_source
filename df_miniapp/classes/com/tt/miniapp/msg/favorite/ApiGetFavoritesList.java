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
import com.tt.option.e.e;
import com.tt.option.q.i;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiGetFavoritesList extends b {
  public ApiGetFavoritesList(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public static List<String> favoriteJsonToFavoriteList(String paramString) {
    if (paramString == null)
      return null; 
    ArrayList<String> arrayList = new ArrayList();
    try {
      JSONObject jSONObject = new JSONObject(paramString);
      int i = jSONObject.optInt("error", 1);
      AppBrandLogger.d("ApiHandler", new Object[] { "favoriteJsonToFavoriteSet", "error == ", Integer.valueOf(i) });
      if (i == 1) {
        i = 1;
      } else {
        i = 0;
      } 
      if (i != 0) {
        AppBrandLogger.e("ApiHandler", new Object[] { "favoriteJsonToFavoriteSet", jSONObject.optString("data", "not errMsg") });
        return null;
      } 
      jSONObject = jSONObject.optJSONObject("data");
      if (jSONObject == null)
        return null; 
      AppBrandLogger.d("ApiHandler", new Object[] { "favoriteJsonToFavoriteSet", "data == ", jSONObject.toString() });
      JSONArray jSONArray = jSONObject.optJSONArray("list");
      if (jSONArray == null)
        return null; 
      AppBrandLogger.d("ApiHandler", new Object[] { "favoriteJsonToFavoriteSet", "list == ", jSONArray.toString() });
      for (i = 0; i < jSONArray.length(); i++)
        arrayList.add(jSONArray.optString(i)); 
    } catch (JSONException jSONException) {
      AppBrandLogger.e("ApiHandler", new Object[] { "favoriteJsonToFavoriteSet", jSONException });
    } 
    return arrayList;
  }
  
  public static String getCurrentUserMiniAppFavoriteListFromNet() {
    StringBuilder stringBuilder1 = new StringBuilder(AppbrandConstant.OpenApi.getInst().getGET_MINIAPP_COLLECTION_LIST());
    StringBuilder stringBuilder2 = new StringBuilder("?aid=");
    stringBuilder2.append(AppbrandContext.getInst().getInitParams().getAppId());
    stringBuilder1.append(stringBuilder2.toString());
    AppBrandLogger.e("ApiHandler", new Object[] { "getCurrentUserMiniAppFavoritesFromNet", "url == ", stringBuilder1.toString() });
    i i = new i(stringBuilder1.toString(), "GET");
    i.a("X-Tma-Host-Sessionid", (UserInfoManager.getHostClientUserInfo()).sessionId);
    String str = NetManager.getInst().request(i).a();
    AppBrandLogger.e("ApiHandler", new Object[] { "getCurrentUserMiniAppFavoritesFromNet", "respData == ", str });
    List<String> list = favoriteJsonToFavoriteList(str);
    if (list != null)
      InnerHostProcessBridge.updateFavoriteSet(list); 
    return str;
  }
  
  public void act() {
    Observable.create(new Function<String>() {
          public String fun() {
            return ApiGetFavoritesList.getCurrentUserMiniAppFavoriteListFromNet();
          }
        }).subscribe((Subscriber)new Subscriber.ResultableSubscriber<String>() {
          public void onError(Throwable param1Throwable) {
            AppBrandLogger.e("ApiHandler", new Object[] { param1Throwable });
            ApiGetFavoritesList.this.callbackFail(param1Throwable);
          }
          
          public void onSuccess(String param1String) {
            JSONObject jSONObject = new JSONObject();
            try {
              jSONObject.put("response", new JSONObject(param1String));
              ApiGetFavoritesList.this.callbackOk(jSONObject);
              return;
            } catch (JSONException jSONException) {
              ApiGetFavoritesList.this.callbackFail("Server callback result not json!");
              return;
            } 
          }
        });
  }
  
  public String getActionName() {
    return "getFavoritesList";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\favorite\ApiGetFavoritesList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */