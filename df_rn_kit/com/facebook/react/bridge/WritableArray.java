package com.facebook.react.bridge;

public interface WritableArray extends ReadableArray {
  void pushArray(WritableArray paramWritableArray);
  
  void pushBoolean(boolean paramBoolean);
  
  void pushDouble(double paramDouble);
  
  void pushInt(int paramInt);
  
  void pushMap(WritableMap paramWritableMap);
  
  void pushNull();
  
  void pushString(String paramString);
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\WritableArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */