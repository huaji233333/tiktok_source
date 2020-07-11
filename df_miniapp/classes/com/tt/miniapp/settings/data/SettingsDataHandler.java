package com.tt.miniapp.settings.data;

import android.app.Application;
import android.content.Context;
import com.tt.miniapp.settings.keys.Settings;
import com.tt.miniapp.settings.net.SettingsData;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.AppbrandContext;
import org.json.JSONException;
import org.json.JSONObject;

public class SettingsDataHandler {
  private static JSONObject getSettingsJSONObject(SettingsData paramSettingsData) {
    if (paramSettingsData == null)
      return null; 
    JSONObject jSONObject = paramSettingsData.getAppSettings();
    if (jSONObject == null)
      return null; 
    jSONObject = jSONObject.optJSONObject("data");
    return (jSONObject == null) ? null : jSONObject.optJSONObject("settings");
  }
  
  private static JSONObject incrementalUpdate(JSONObject paramJSONObject1, JSONObject paramJSONObject2) {
    for (Settings settings : Settings.values()) {
      JSONObject jSONObject = paramJSONObject1.optJSONObject(settings.toString());
      if (jSONObject != null)
        try {
          paramJSONObject2.put(settings.toString(), jSONObject);
        } catch (JSONException jSONException) {
          AppBrandLogger.e("SettingsDataHandler", new Object[] { jSONException });
        }  
    } 
    return paramJSONObject2;
  }
  
  private static void setInfo(SettingsData paramSettingsData) {
    if (paramSettingsData == null)
      return; 
    Application application = AppbrandContext.getInst().getApplicationContext();
    if (application == null)
      return; 
    JSONObject jSONObject1 = paramSettingsData.getAppSettings();
    if (jSONObject1 == null)
      return; 
    JSONObject jSONObject2 = jSONObject1.optJSONObject("data");
    if (jSONObject2 == null)
      return; 
    String str = jSONObject2.optString("ctx_infos");
    jSONObject2 = jSONObject2.optJSONObject("vid_info");
    SettingsDAO.setCtxInfo((Context)application, str);
    if (jSONObject2 != null)
      ABTestDAO.getInstance().updateVidInfo((Context)application, jSONObject2); 
  }
  
  public static void update(SettingsData paramSettingsData) {
    if (paramSettingsData == null)
      return; 
    JSONObject jSONObject1 = getSettingsJSONObject(paramSettingsData);
    Application application = AppbrandContext.getInst().getApplicationContext();
    if (application == null)
      return; 
    JSONObject jSONObject2 = SettingsDAO.getSettingsJSONObject((Context)application);
    if (jSONObject2 == null) {
      SettingsDAO.setSettingsJSONObject((Context)application, jSONObject1);
    } else {
      SettingsDAO.setSettingsJSONObject((Context)application, incrementalUpdate(jSONObject1, jSONObject2));
    } 
    setInfo(paramSettingsData);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\settings\data\SettingsDataHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */