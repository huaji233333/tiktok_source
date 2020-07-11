package com.tt.miniapp.autotest;

import d.f.b.l;
import org.json.JSONObject;

public final class AutoTestLoopEvent {
  private final String dispatch;
  
  private final long endTime;
  
  private String stackTrace;
  
  private final long startTime;
  
  public AutoTestLoopEvent(String paramString1, long paramLong1, long paramLong2, String paramString2) {
    this.dispatch = paramString1;
    this.startTime = paramLong1;
    this.endTime = paramLong2;
    this.stackTrace = paramString2;
  }
  
  public final String component1() {
    return this.dispatch;
  }
  
  public final long component2() {
    return this.startTime;
  }
  
  public final long component3() {
    return this.endTime;
  }
  
  public final String component4() {
    return this.stackTrace;
  }
  
  public final AutoTestLoopEvent copy(String paramString1, long paramLong1, long paramLong2, String paramString2) {
    return new AutoTestLoopEvent(paramString1, paramLong1, paramLong2, paramString2);
  }
  
  public final boolean equals(Object paramObject) {
    if (this != paramObject) {
      if (paramObject instanceof AutoTestLoopEvent) {
        paramObject = paramObject;
        if (l.a(this.dispatch, ((AutoTestLoopEvent)paramObject).dispatch)) {
          boolean bool;
          if (this.startTime == ((AutoTestLoopEvent)paramObject).startTime) {
            bool = true;
          } else {
            bool = false;
          } 
          if (bool) {
            if (this.endTime == ((AutoTestLoopEvent)paramObject).endTime) {
              bool = true;
            } else {
              bool = false;
            } 
            if (bool && l.a(this.stackTrace, ((AutoTestLoopEvent)paramObject).stackTrace))
              return true; 
          } 
        } 
      } 
      return false;
    } 
    return true;
  }
  
  public final String getDispatch() {
    return this.dispatch;
  }
  
  public final long getEndTime() {
    return this.endTime;
  }
  
  public final String getStackTrace() {
    return this.stackTrace;
  }
  
  public final long getStartTime() {
    return this.startTime;
  }
  
  public final int hashCode() {
    byte b;
    String str = this.dispatch;
    int i = 0;
    if (str != null) {
      b = str.hashCode();
    } else {
      b = 0;
    } 
    long l = this.startTime;
    int j = (int)(l ^ l >>> 32L);
    l = this.endTime;
    int k = (int)(l ^ l >>> 32L);
    str = this.stackTrace;
    if (str != null)
      i = str.hashCode(); 
    return ((b * 31 + j) * 31 + k) * 31 + i;
  }
  
  public final void setStackTrace(String paramString) {
    this.stackTrace = paramString;
  }
  
  public final JSONObject toJson() {
    JSONObject jSONObject = new JSONObject();
    jSONObject.put("dispatch", this.dispatch);
    jSONObject.put("startTime", this.startTime);
    jSONObject.put("endTime", this.endTime);
    jSONObject.put("stackTrace", this.stackTrace);
    return jSONObject;
  }
  
  public final String toString() {
    StringBuilder stringBuilder = new StringBuilder("AutoTestLoopEvent(dispatch=");
    stringBuilder.append(this.dispatch);
    stringBuilder.append(", startTime=");
    stringBuilder.append(this.startTime);
    stringBuilder.append(", endTime=");
    stringBuilder.append(this.endTime);
    stringBuilder.append(", stackTrace=");
    stringBuilder.append(this.stackTrace);
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\autotest\AutoTestLoopEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */