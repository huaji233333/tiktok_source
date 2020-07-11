package com.tt.miniapp.business.frontendapihandle.handler.subscribe.data;

import android.text.TextUtils;
import com.tt.miniapphost.AppBrandLogger;
import com.tt.miniapphost.util.JsonBuilder;
import org.json.JSONException;
import org.json.JSONObject;

public class TemplateMsgLimitInfo {
  private int mMaxCount;
  
  private int mTimeUnit;
  
  public TemplateMsgLimitInfo(String paramString) {
    if (!TextUtils.isEmpty(paramString)) {
      JSONObject jSONObject = (new JsonBuilder(paramString)).build();
      if (jSONObject != null) {
        this.mMaxCount = jSONObject.optInt("max_count");
        this.mTimeUnit = jSONObject.optInt("time_unit");
      } 
    } 
  }
  
  public TemplateMsgLimitInfo(JSONObject paramJSONObject) {
    if (paramJSONObject != null) {
      this.mMaxCount = paramJSONObject.optInt("max_count");
      this.mTimeUnit = paramJSONObject.optInt("time_unit");
    } 
  }
  
  public int getMaxCount() {
    return this.mMaxCount;
  }
  
  public int getTimeUnit() {
    return this.mTimeUnit;
  }
  
  public void setMaxCount(int paramInt) {
    this.mMaxCount = paramInt;
  }
  
  public void setTimeUnit(int paramInt) {
    this.mTimeUnit = paramInt;
  }
  
  public String toString() {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("max_count", this.mMaxCount);
      jSONObject.put("time_unit", this.mTimeUnit);
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable("TemplateMsgAuthShowLimitInfo", "", (Throwable)jSONException);
    } 
    return jSONObject.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\frontendapihandle\handler\subscribe\data\TemplateMsgLimitInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */