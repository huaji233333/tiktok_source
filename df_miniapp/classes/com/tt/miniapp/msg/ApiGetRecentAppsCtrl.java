package com.tt.miniapp.msg;

import com.tt.frontendapiinterface.b;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.RecentAppsManager;
import com.tt.miniapphost.entity.AppLaunchInfo;
import com.tt.option.e.e;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiGetRecentAppsCtrl extends b {
  public ApiGetRecentAppsCtrl(String paramString, int paramInt, e parame) {
    super(paramString, paramInt, parame);
  }
  
  public void act() {
    RecentAppsManager.inst().getRecentAppList(new RecentAppsManager.OnDataGetListener() {
          public void onFail(boolean param1Boolean) {
            ApiGetRecentAppsCtrl apiGetRecentAppsCtrl = ApiGetRecentAppsCtrl.this;
            StringBuilder stringBuilder = new StringBuilder("get recent app fail, isFromLocalDb=");
            stringBuilder.append(param1Boolean);
            apiGetRecentAppsCtrl.callbackFail(stringBuilder.toString());
          }
          
          public void onSuccess(List<AppLaunchInfo> param1List, boolean param1Boolean) {
            ApiGetRecentAppsCtrl.this.dealDataList(param1List);
          }
        });
  }
  
  public void dealDataList(List<AppLaunchInfo> paramList) {
    JSONObject jSONObject1 = new JSONObject();
    JSONObject jSONObject2 = new JSONObject();
    if (paramList != null)
      try {
        if (paramList.size() > 0) {
          JSONArray jSONArray = new JSONArray();
          for (AppLaunchInfo appLaunchInfo : paramList) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("app_id", appLaunchInfo.appId);
            jSONObject.put("icon", appLaunchInfo.icon);
            jSONObject.put("min_jssdk", appLaunchInfo.minJssdk);
            jSONObject.put("name", appLaunchInfo.appName);
            jSONObject.put("orientation", appLaunchInfo.orientation);
            jSONObject.put("schema", appLaunchInfo.schema);
            jSONObject.put("state", appLaunchInfo.state);
            jSONObject.put("summary", appLaunchInfo.summary);
            jSONObject.put("ttid", appLaunchInfo.ttid);
            jSONObject.put("type", appLaunchInfo.type);
            jSONArray.put(jSONObject);
          } 
          jSONObject2.put("apps", jSONArray);
        } 
        jSONObject1.put("data", jSONObject2);
        callbackOk(jSONObject1);
        return;
      } catch (JSONException jSONException) {
        AppBrandLogger.e("ApiHandler", new Object[] { jSONException });
        callbackFail((Throwable)jSONException);
        return;
      }  
    jSONObject1.put("data", jSONObject2);
    callbackOk(jSONObject1);
  }
  
  public String getActionName() {
    return "getRecentAppList";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\msg\ApiGetRecentAppsCtrl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */