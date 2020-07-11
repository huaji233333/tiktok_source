package com.facebook.react.bridge;

import java.util.ArrayList;

public interface ReadableArray {
  ReadableArray getArray(int paramInt);
  
  boolean getBoolean(int paramInt);
  
  double getDouble(int paramInt);
  
  Dynamic getDynamic(int paramInt);
  
  int getInt(int paramInt);
  
  ReadableMap getMap(int paramInt);
  
  String getString(int paramInt);
  
  ReadableType getType(int paramInt);
  
  boolean isNull(int paramInt);
  
  int size();
  
  ArrayList<Object> toArrayList();
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\ReadableArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */