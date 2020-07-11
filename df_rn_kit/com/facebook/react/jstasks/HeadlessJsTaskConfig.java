package com.facebook.react.jstasks;

import com.facebook.react.bridge.WritableMap;

public class HeadlessJsTaskConfig {
  private final boolean mAllowedInForeground;
  
  private final WritableMap mData;
  
  private final String mTaskKey;
  
  private final long mTimeout;
  
  public HeadlessJsTaskConfig(String paramString, WritableMap paramWritableMap) {
    this(paramString, paramWritableMap, 0L, false);
  }
  
  public HeadlessJsTaskConfig(String paramString, WritableMap paramWritableMap, long paramLong) {
    this(paramString, paramWritableMap, paramLong, false);
  }
  
  public HeadlessJsTaskConfig(String paramString, WritableMap paramWritableMap, long paramLong, boolean paramBoolean) {
    this.mTaskKey = paramString;
    this.mData = paramWritableMap;
    this.mTimeout = paramLong;
    this.mAllowedInForeground = paramBoolean;
  }
  
  WritableMap getData() {
    return this.mData;
  }
  
  String getTaskKey() {
    return this.mTaskKey;
  }
  
  long getTimeout() {
    return this.mTimeout;
  }
  
  boolean isAllowedInForeground() {
    return this.mAllowedInForeground;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\jstasks\HeadlessJsTaskConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */