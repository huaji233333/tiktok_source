package com.tt.miniapp.business.frontendapihandle.handler.subscribe.data;

import com.tt.miniapphost.AppBrandLogger;
import org.json.JSONException;
import org.json.JSONObject;

public class TemplateMsgInfo {
  private String mContent;
  
  private String mId;
  
  private long mLastUpdateTime;
  
  private TemplateMsgLimitInfo mLimitInfo;
  
  private int mStatus;
  
  private String mTitle;
  
  public TemplateMsgInfo(String paramString, JSONObject paramJSONObject) {
    if (paramJSONObject != null) {
      this.mId = paramString;
      this.mTitle = paramJSONObject.optString("title");
      this.mContent = paramJSONObject.optString("content");
      this.mStatus = paramJSONObject.optInt("status");
      this.mLastUpdateTime = paramJSONObject.optLong("lastUpdateTime", System.currentTimeMillis());
      this.mLimitInfo = new TemplateMsgLimitInfo(paramJSONObject.optString("limit"));
    } 
  }
  
  public String getContent() {
    return this.mContent;
  }
  
  public String getId() {
    return this.mId;
  }
  
  public long getLastUpdateTime() {
    return this.mLastUpdateTime;
  }
  
  public TemplateMsgLimitInfo getLimitInfo() {
    return this.mLimitInfo;
  }
  
  public int getStatus() {
    return this.mStatus;
  }
  
  public String getTitle() {
    return this.mTitle;
  }
  
  public boolean isValid() {
    return (this.mStatus == 1);
  }
  
  public boolean needUpdateOnline() {
    return (Math.abs(System.currentTimeMillis() - this.mLastUpdateTime) > 7200000L);
  }
  
  public void setContent(String paramString) {
    this.mContent = paramString;
  }
  
  public void setId(String paramString) {
    this.mId = paramString;
  }
  
  public void setLastUpdateTime(long paramLong) {
    this.mLastUpdateTime = paramLong;
  }
  
  public void setLimitInfo(TemplateMsgLimitInfo paramTemplateMsgLimitInfo) {
    this.mLimitInfo = paramTemplateMsgLimitInfo;
  }
  
  public void setStatus(int paramInt) {
    this.mStatus = paramInt;
  }
  
  public void setTitle(String paramString) {
    this.mTitle = paramString;
  }
  
  public String toString() {
    JSONObject jSONObject = new JSONObject();
    try {
      jSONObject.put("title", this.mTitle);
      jSONObject.put("content", this.mContent);
      jSONObject.put("status", this.mStatus);
      jSONObject.put("lastUpdateTime", this.mLastUpdateTime);
      if (this.mLimitInfo != null)
        jSONObject.put("limit", this.mLimitInfo.toString()); 
    } catch (JSONException jSONException) {
      AppBrandLogger.eWithThrowable("TemplateMsgInfo", "", (Throwable)jSONException);
    } 
    return jSONObject.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\business\frontendapihandle\handler\subscribe\data\TemplateMsgInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */