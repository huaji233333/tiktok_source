package com.facebook.react.modules.location;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

public class PositionError {
  public static int PERMISSION_DENIED = 1;
  
  public static int POSITION_UNAVAILABLE = 2;
  
  public static int TIMEOUT = 3;
  
  public static WritableMap buildError(int paramInt, String paramString) {
    WritableMap writableMap = Arguments.createMap();
    writableMap.putInt("code", paramInt);
    if (paramString != null)
      writableMap.putString("message", paramString); 
    return writableMap;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\modules\location\PositionError.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */