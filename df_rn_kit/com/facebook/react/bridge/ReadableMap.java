package com.facebook.react.bridge;

import java.util.HashMap;

public interface ReadableMap {
  ReadableArray getArray(String paramString);
  
  boolean getBoolean(String paramString);
  
  double getDouble(String paramString);
  
  double getDoubleFromDeg(String paramString);
  
  Dynamic getDynamic(String paramString);
  
  int getInt(String paramString);
  
  ReadableMap getMap(String paramString);
  
  String getString(String paramString);
  
  ReadableType getType(String paramString);
  
  boolean hasKey(String paramString);
  
  boolean isNull(String paramString);
  
  ReadableMapKeySetIterator keySetIterator();
  
  HashMap<String, Object> toHashMap();
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\ReadableMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */