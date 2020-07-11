package com.tt.miniapp.debug.appData;

import org.json.JSONException;
import org.json.JSONObject;

public class AppData {
  public String data;
  
  public String route;
  
  public int webviewId;
  
  public static AppData parseJson(JSONObject paramJSONObject) {
    AppData appData = new AppData();
    appData.webviewId = paramJSONObject.optInt("__webviewId__");
    appData.data = paramJSONObject.optString("data");
    appData.route = paramJSONObject.optString("__route__");
    return appData;
  }
  
  public static JSONObject toJson(AppData paramAppData) {
    JSONObject jSONObject = new JSONObject();
    if (paramAppData != null)
      try {
        jSONObject.put("__route__", paramAppData.route);
        jSONObject.put("data", paramAppData.data);
        jSONObject.put("__webviewId__", paramAppData.webviewId);
        return jSONObject;
      } catch (JSONException jSONException) {
        return jSONObject;
      }  
    return jSONObject;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\debug\appData\AppData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */