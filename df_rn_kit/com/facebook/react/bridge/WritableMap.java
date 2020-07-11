package com.facebook.react.bridge;

public interface WritableMap extends ReadableMap {
  void merge(ReadableMap paramReadableMap);
  
  void putArray(String paramString, WritableArray paramWritableArray);
  
  void putBoolean(String paramString, boolean paramBoolean);
  
  void putDouble(String paramString, double paramDouble);
  
  void putInt(String paramString, int paramInt);
  
  void putMap(String paramString, WritableMap paramWritableMap);
  
  void putNull(String paramString);
  
  void putString(String paramString1, String paramString2);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\WritableMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */