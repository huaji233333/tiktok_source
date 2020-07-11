package com.facebook.react.modules.storage;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

public class AsyncStorageErrorUtil {
  static WritableMap getDBError(String paramString) {
    return getError(paramString, "Database Error");
  }
  
  static WritableMap getError(String paramString1, String paramString2) {
    WritableMap writableMap = Arguments.createMap();
    writableMap.putString("message", paramString2);
    if (paramString1 != null)
      writableMap.putString("key", paramString1); 
    return writableMap;
  }
  
  static WritableMap getInvalidKeyError(String paramString) {
    return getError(paramString, "Invalid key");
  }
  
  static WritableMap getInvalidValueError(String paramString) {
    return getError(paramString, "Invalid Value");
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\storage\AsyncStorageErrorUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */