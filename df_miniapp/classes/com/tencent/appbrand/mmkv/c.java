package com.tencent.appbrand.mmkv;

public enum c {
  LevelDebug, LevelError, LevelInfo, LevelNone, LevelWarning;
  
  static {
    LevelError = new c("LevelError", 3);
    LevelNone = new c("LevelNone", 4);
    a = new c[] { LevelDebug, LevelInfo, LevelWarning, LevelError, LevelNone };
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tencent\appbrand\mmkv\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */