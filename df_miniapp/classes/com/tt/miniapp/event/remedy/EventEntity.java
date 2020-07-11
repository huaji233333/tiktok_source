package com.tt.miniapp.event.remedy;

import org.json.JSONObject;

public class EventEntity {
  public JSONObject eventData;
  
  public String eventName;
  
  public boolean innerHandled;
  
  public String uniqueKey;
  
  public EventEntity(String paramString, JSONObject paramJSONObject, boolean paramBoolean) {
    this(paramString, paramJSONObject, paramBoolean, EventEntityHelper.getUniqueKey(paramString, paramJSONObject));
  }
  
  private EventEntity(String paramString1, JSONObject paramJSONObject, boolean paramBoolean, String paramString2) {
    this.eventName = paramString1;
    this.eventData = paramJSONObject;
    this.innerHandled = paramBoolean;
    this.uniqueKey = paramString2;
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject != null) {
      if (getClass() != paramObject.getClass())
        return false; 
      paramObject = paramObject;
      return !this.eventName.equals(((EventEntity)paramObject).eventName) ? false : this.uniqueKey.equals(((EventEntity)paramObject).uniqueKey);
    } 
    return false;
  }
  
  public int hashCode() {
    return this.eventName.hashCode() * 31 + this.uniqueKey.hashCode();
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("{eventName='");
    stringBuilder.append(this.eventName);
    stringBuilder.append('\'');
    stringBuilder.append(", eventData=");
    stringBuilder.append(this.eventData);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\event\remedy\EventEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */