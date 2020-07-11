package com.tt.miniapp.autotest;

import d.f.b.g;
import d.f.b.l;
import org.json.JSONObject;

public final class AutoTestEvent {
  private final String id;
  
  private final long timestamp;
  
  private final Object value;
  
  public AutoTestEvent(String paramString, long paramLong, Object paramObject) {
    this.id = paramString;
    this.timestamp = paramLong;
    this.value = paramObject;
  }
  
  public final String component1() {
    return this.id;
  }
  
  public final long component2() {
    return this.timestamp;
  }
  
  public final Object component3() {
    return this.value;
  }
  
  public final AutoTestEvent copy(String paramString, long paramLong, Object paramObject) {
    l.b(paramString, "id");
    return new AutoTestEvent(paramString, paramLong, paramObject);
  }
  
  public final boolean equals(Object paramObject) {
    if (this != paramObject) {
      if (paramObject instanceof AutoTestEvent) {
        paramObject = paramObject;
        if (l.a(this.id, ((AutoTestEvent)paramObject).id)) {
          boolean bool;
          if (this.timestamp == ((AutoTestEvent)paramObject).timestamp) {
            bool = true;
          } else {
            bool = false;
          } 
          if (bool && l.a(this.value, ((AutoTestEvent)paramObject).value))
            return true; 
        } 
      } 
      return false;
    } 
    return true;
  }
  
  public final String getId() {
    return this.id;
  }
  
  public final long getTimestamp() {
    return this.timestamp;
  }
  
  public final Object getValue() {
    return this.value;
  }
  
  public final int hashCode() {
    byte b;
    String str = this.id;
    int i = 0;
    if (str != null) {
      b = str.hashCode();
    } else {
      b = 0;
    } 
    long l = this.timestamp;
    int j = (int)(l ^ l >>> 32L);
    Object object = this.value;
    if (object != null)
      i = object.hashCode(); 
    return (b * 31 + j) * 31 + i;
  }
  
  public final JSONObject toJson() {
    JSONObject jSONObject = new JSONObject();
    jSONObject.put("id", this.id);
    jSONObject.put("timestamp", this.timestamp);
    jSONObject.put("value", this.value);
    return jSONObject;
  }
  
  public final String toString() {
    StringBuilder stringBuilder = new StringBuilder("AutoTestEvent(id=");
    stringBuilder.append(this.id);
    stringBuilder.append(", timestamp=");
    stringBuilder.append(this.timestamp);
    stringBuilder.append(", value=");
    stringBuilder.append(this.value);
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\autotest\AutoTestEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */