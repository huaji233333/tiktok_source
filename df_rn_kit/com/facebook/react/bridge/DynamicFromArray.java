package com.facebook.react.bridge;

import android.support.v4.f.l;

public class DynamicFromArray implements Dynamic {
  private static final l.b<DynamicFromArray> sPool = new l.b(10);
  
  private ReadableArray mArray;
  
  private int mIndex = -1;
  
  public static DynamicFromArray create(ReadableArray paramReadableArray, int paramInt) {
    DynamicFromArray dynamicFromArray2 = (DynamicFromArray)sPool.acquire();
    DynamicFromArray dynamicFromArray1 = dynamicFromArray2;
    if (dynamicFromArray2 == null)
      dynamicFromArray1 = new DynamicFromArray(); 
    dynamicFromArray1.mArray = paramReadableArray;
    dynamicFromArray1.mIndex = paramInt;
    return dynamicFromArray1;
  }
  
  public ReadableArray asArray() {
    ReadableArray readableArray = this.mArray;
    if (readableArray != null)
      return readableArray.getArray(this.mIndex); 
    throw new IllegalStateException("This dynamic value has been recycled");
  }
  
  public boolean asBoolean() {
    ReadableArray readableArray = this.mArray;
    if (readableArray != null)
      return readableArray.getBoolean(this.mIndex); 
    throw new IllegalStateException("This dynamic value has been recycled");
  }
  
  public double asDouble() {
    ReadableArray readableArray = this.mArray;
    if (readableArray != null)
      return readableArray.getDouble(this.mIndex); 
    throw new IllegalStateException("This dynamic value has been recycled");
  }
  
  public int asInt() {
    ReadableArray readableArray = this.mArray;
    if (readableArray != null)
      return readableArray.getInt(this.mIndex); 
    throw new IllegalStateException("This dynamic value has been recycled");
  }
  
  public ReadableMap asMap() {
    ReadableArray readableArray = this.mArray;
    if (readableArray != null)
      return readableArray.getMap(this.mIndex); 
    throw new IllegalStateException("This dynamic value has been recycled");
  }
  
  public String asString() {
    ReadableArray readableArray = this.mArray;
    if (readableArray != null)
      return readableArray.getString(this.mIndex); 
    throw new IllegalStateException("This dynamic value has been recycled");
  }
  
  public ReadableType getType() {
    ReadableArray readableArray = this.mArray;
    if (readableArray != null)
      return readableArray.getType(this.mIndex); 
    throw new IllegalStateException("This dynamic value has been recycled");
  }
  
  public boolean isNull() {
    ReadableArray readableArray = this.mArray;
    if (readableArray != null)
      return readableArray.isNull(this.mIndex); 
    throw new IllegalStateException("This dynamic value has been recycled");
  }
  
  public void recycle() {
    this.mArray = null;
    this.mIndex = -1;
    sPool.release(this);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\DynamicFromArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */