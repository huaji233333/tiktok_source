package com.tt.miniapp.settings.net;

import java.util.UUID;
import org.json.JSONObject;

public class SettingsData {
  private JSONObject appSettings;
  
  private boolean fromServer = true;
  
  private String token;
  
  private JSONObject userSettings;
  
  public SettingsData(JSONObject paramJSONObject1, JSONObject paramJSONObject2) {
    this(paramJSONObject1, paramJSONObject2, UUID.randomUUID().toString(), true);
  }
  
  public SettingsData(JSONObject paramJSONObject1, JSONObject paramJSONObject2, String paramString, boolean paramBoolean) {
    this.appSettings = paramJSONObject1;
    this.userSettings = paramJSONObject2;
    this.token = paramString;
    this.fromServer = paramBoolean;
  }
  
  public JSONObject getAppSettings() {
    return this.appSettings;
  }
  
  public String getToken() {
    return this.token;
  }
  
  public JSONObject getUserSettings() {
    return this.userSettings;
  }
  
  public boolean isFromServer() {
    return this.fromServer;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\settings\net\SettingsData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */