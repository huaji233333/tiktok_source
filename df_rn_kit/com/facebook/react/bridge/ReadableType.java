package com.facebook.react.bridge;

public enum ReadableType {
  Array, Boolean, Map, Null, Number, String;
  
  static {
    Boolean = new ReadableType("Boolean", 1);
    Number = new ReadableType("Number", 2);
    String = new ReadableType("String", 3);
    Map = new ReadableType("Map", 4);
    Array = new ReadableType("Array", 5);
    $VALUES = new ReadableType[] { Null, Boolean, Number, String, Map, Array };
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\ReadableType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */