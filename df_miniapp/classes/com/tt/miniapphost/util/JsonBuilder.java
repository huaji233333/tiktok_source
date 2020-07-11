package com.tt.miniapphost.util;

import android.support.v4.f.a;
import android.text.TextUtils;
import com.tt.miniapphost.AppBrandLogger;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonBuilder {
  private Map<String, Object> mKeyValues = (Map<String, Object>)new a();
  
  private JSONObject mObject;
  
  public JsonBuilder() {
    this.mObject = new JSONObject();
  }
  
  public JsonBuilder(String paramString) {
    try {
      if (TextUtils.isEmpty(paramString)) {
        this.mObject = new JSONObject();
        return;
      } 
      this.mObject = new JSONObject(paramString);
      return;
    } catch (JSONException jSONException) {
      AppBrandLogger.stacktrace(6, "JsonBuilder", jSONException.getStackTrace());
      this.mObject = new JSONObject();
      return;
    } 
  }
  
  public JsonBuilder(JSONObject paramJSONObject) {
    if (paramJSONObject == null) {
      this.mObject = new JSONObject();
      return;
    } 
    this.mObject = paramJSONObject;
  }
  
  public JSONObject build() {
    Set<Map.Entry<String, Object>> set = this.mKeyValues.entrySet();
    try {
      for (Map.Entry<String, Object> entry : set)
        this.mObject.put((String)entry.getKey(), entry.getValue()); 
    } catch (JSONException jSONException) {
      AppBrandLogger.stacktrace(6, "JsonBuilder", jSONException.getStackTrace());
    } 
    return this.mObject;
  }
  
  public JsonBuilder put(String paramString, Object paramObject) {
    this.mKeyValues.put(paramString, paramObject);
    return this;
  }
  
  public JsonBuilder putIfNotNull(String paramString, Object paramObject) {
    if (paramObject == null)
      return this; 
    if (paramObject instanceof String && ((String)paramObject).length() == 0)
      return this; 
    this.mKeyValues.put(paramString, paramObject);
    return this;
  }
  
  public String toString() {
    return build().toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapphos\\util\JsonBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */