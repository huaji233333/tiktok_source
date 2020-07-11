package com.tt.option.p;

import org.json.JSONObject;

public interface e {
  void flushBuffer();
  
  void monitorCommonLog(String paramString, JSONObject paramJSONObject);
  
  void monitorDuration(String paramString, JSONObject paramJSONObject1, JSONObject paramJSONObject2);
  
  void monitorEvent(String paramString, JSONObject paramJSONObject1, JSONObject paramJSONObject2, JSONObject paramJSONObject3);
  
  void monitorStatusAndDuration(String paramString, int paramInt, JSONObject paramJSONObject1, JSONObject paramJSONObject2);
  
  void monitorStatusRate(String paramString, int paramInt, JSONObject paramJSONObject);
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\option\p\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */