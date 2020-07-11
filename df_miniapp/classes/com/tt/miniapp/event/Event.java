package com.tt.miniapp.event;

import android.text.TextUtils;
import com.tt.miniapp.AppbrandApplicationImpl;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.EventHelper;
import com.tt.miniapphost.entity.AppInfoEntity;
import com.tt.miniapphost.process.HostProcessBridge;
import com.tt.miniapphost.util.ProcessUtil;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class Event {
  public static IFeedback sIFeedback;
  
  public static Builder builder(String paramString) {
    return new Builder(paramString);
  }
  
  public static Builder builder(String paramString, AppInfoEntity paramAppInfoEntity) {
    return new Builder(paramString, paramAppInfoEntity);
  }
  
  public static void registerIFeedback(IFeedback paramIFeedback) {
    sIFeedback = paramIFeedback;
  }
  
  public static void unregisterIFeedback() {
    sIFeedback = null;
  }
  
  public static class Builder {
    private String eventName;
    
    private JSONObject paramsJsonObject;
    
    public Builder(String param1String) {
      this.eventName = param1String;
      if (ProcessUtil.isMiniappProcess()) {
        AppInfoEntity appInfoEntity = AppbrandApplicationImpl.getInst().getAppInfo();
      } else {
        param1String = null;
      } 
      this.paramsJsonObject = EventHelper.getCommonParams(new JSONObject(), (AppInfoEntity)param1String);
    }
    
    public Builder(String param1String, AppInfoEntity param1AppInfoEntity) {
      this.eventName = param1String;
      this.paramsJsonObject = EventHelper.getCommonParams(new JSONObject(), param1AppInfoEntity);
    }
    
    public Builder addKVJsonObject(JSONObject param1JSONObject) {
      if (param1JSONObject == null)
        return this; 
      try {
        Iterator<String> iterator = param1JSONObject.keys();
        while (iterator.hasNext()) {
          String str = iterator.next();
          this.paramsJsonObject.put(str, param1JSONObject.get(str));
        } 
      } catch (JSONException jSONException) {
        AppBrandLogger.stacktrace(5, "tma_Event", jSONException.getStackTrace());
      } 
      return this;
    }
    
    public void flush() {
      if (!TextUtils.isEmpty(this.eventName))
        HostProcessBridge.logEvent(this.eventName, this.paramsJsonObject); 
      if (Event.sIFeedback != null)
        Event.sIFeedback.onLogEvent(this.eventName, this.paramsJsonObject); 
    }
    
    public Builder kv(String param1String, Object param1Object) {
      if (param1String != null && param1Object != null)
        try {
          this.paramsJsonObject.put(param1String, param1Object);
          return this;
        } catch (JSONException jSONException) {
          AppBrandLogger.stacktrace(5, "tma_Event", jSONException.getStackTrace());
        }  
      return this;
    }
  }
  
  public static interface IFeedback {
    void onLogEvent(String param1String, JSONObject param1JSONObject);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\event\Event.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */