package com.facebook.react.bridge;

import android.support.v4.f.l;

public class DynamicFromMap implements Dynamic {
  private static final l.b<DynamicFromMap> sPool = new l.b(10);
  
  private ReadableMap mMap;
  
  private String mName;
  
  public static DynamicFromMap create(ReadableMap paramReadableMap, String paramString) {
    DynamicFromMap dynamicFromMap2 = (DynamicFromMap)sPool.acquire();
    DynamicFromMap dynamicFromMap1 = dynamicFromMap2;
    if (dynamicFromMap2 == null)
      dynamicFromMap1 = new DynamicFromMap(); 
    dynamicFromMap1.mMap = paramReadableMap;
    dynamicFromMap1.mName = paramString;
    return dynamicFromMap1;
  }
  
  public ReadableArray asArray() {
    ReadableMap readableMap = this.mMap;
    if (readableMap != null) {
      String str = this.mName;
      if (str != null)
        return readableMap.getArray(str); 
    } 
    throw new IllegalStateException("This dynamic value has been recycled");
  }
  
  public boolean asBoolean() {
    ReadableMap readableMap = this.mMap;
    if (readableMap != null) {
      String str = this.mName;
      if (str != null)
        return readableMap.getBoolean(str); 
    } 
    throw new IllegalStateException("This dynamic value has been recycled");
  }
  
  public double asDouble() {
    ReadableMap readableMap = this.mMap;
    if (readableMap != null) {
      String str = this.mName;
      if (str != null)
        return readableMap.getDouble(str); 
    } 
    throw new IllegalStateException("This dynamic value has been recycled");
  }
  
  public int asInt() {
    ReadableMap readableMap = this.mMap;
    if (readableMap != null) {
      String str = this.mName;
      if (str != null)
        return readableMap.getInt(str); 
    } 
    throw new IllegalStateException("This dynamic value has been recycled");
  }
  
  public ReadableMap asMap() {
    ReadableMap readableMap = this.mMap;
    if (readableMap != null) {
      String str = this.mName;
      if (str != null)
        return readableMap.getMap(str); 
    } 
    throw new IllegalStateException("This dynamic value has been recycled");
  }
  
  public String asString() {
    ReadableMap readableMap = this.mMap;
    if (readableMap != null) {
      String str = this.mName;
      if (str != null)
        return readableMap.getString(str); 
    } 
    throw new IllegalStateException("This dynamic value has been recycled");
  }
  
  public ReadableType getType() {
    ReadableMap readableMap = this.mMap;
    if (readableMap != null) {
      String str = this.mName;
      if (str != null)
        return readableMap.getType(str); 
    } 
    throw new IllegalStateException("This dynamic value has been recycled");
  }
  
  public boolean isNull() {
    ReadableMap readableMap = this.mMap;
    if (readableMap != null) {
      String str = this.mName;
      if (str != null)
        return readableMap.isNull(str); 
    } 
    throw new IllegalStateException("This dynamic value has been recycled");
  }
  
  public void recycle() {
    this.mMap = null;
    this.mName = null;
    sPool.release(this);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\DynamicFromMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */