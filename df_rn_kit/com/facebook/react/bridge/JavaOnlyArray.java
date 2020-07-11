package com.facebook.react.bridge;

import java.util.ArrayList;
import java.util.List;

public class JavaOnlyArray implements ReadableArray, WritableArray {
  private final List mBackingList = new ArrayList();
  
  public JavaOnlyArray() {}
  
  private JavaOnlyArray(List<?> paramList) {}
  
  private JavaOnlyArray(Object... paramVarArgs) {}
  
  public static JavaOnlyArray deepClone(ReadableArray paramReadableArray) {
    JavaOnlyArray javaOnlyArray = new JavaOnlyArray();
    int j = paramReadableArray.size();
    for (int i = 0; i < j; i++) {
      ReadableType readableType = paramReadableArray.getType(i);
      switch (readableType) {
        case null:
          javaOnlyArray.pushArray(deepClone(paramReadableArray.getArray(i)));
          break;
        case Map:
          javaOnlyArray.pushMap(JavaOnlyMap.deepClone(paramReadableArray.getMap(i)));
          break;
        case String:
          javaOnlyArray.pushString(paramReadableArray.getString(i));
          break;
        case Number:
          javaOnlyArray.pushDouble(paramReadableArray.getDouble(i));
          break;
        case Boolean:
          javaOnlyArray.pushBoolean(paramReadableArray.getBoolean(i));
          break;
        case Null:
          javaOnlyArray.pushNull();
          break;
      } 
    } 
    return javaOnlyArray;
  }
  
  public static JavaOnlyArray from(List paramList) {
    return new JavaOnlyArray(paramList);
  }
  
  public static JavaOnlyArray of(Object... paramVarArgs) {
    return new JavaOnlyArray(paramVarArgs);
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject != null) {
      if (getClass() != paramObject.getClass())
        return false; 
      JavaOnlyArray javaOnlyArray = (JavaOnlyArray)paramObject;
      paramObject = this.mBackingList;
      List list = javaOnlyArray.mBackingList;
      if (paramObject != null) {
        if (!paramObject.equals(list))
          return false; 
      } else if (list != null) {
        return false;
      } 
      return true;
    } 
    return false;
  }
  
  public JavaOnlyArray getArray(int paramInt) {
    return this.mBackingList.get(paramInt);
  }
  
  public boolean getBoolean(int paramInt) {
    return ((Boolean)this.mBackingList.get(paramInt)).booleanValue();
  }
  
  public double getDouble(int paramInt) {
    return ((Number)this.mBackingList.get(paramInt)).doubleValue();
  }
  
  public Dynamic getDynamic(int paramInt) {
    return DynamicFromArray.create(this, paramInt);
  }
  
  public int getInt(int paramInt) {
    return ((Number)this.mBackingList.get(paramInt)).intValue();
  }
  
  public JavaOnlyMap getMap(int paramInt) {
    return this.mBackingList.get(paramInt);
  }
  
  public String getString(int paramInt) {
    return this.mBackingList.get(paramInt);
  }
  
  public ReadableType getType(int paramInt) {
    Object object = this.mBackingList.get(paramInt);
    return (object == null) ? ReadableType.Null : ((object instanceof Boolean) ? ReadableType.Boolean : ((object instanceof Double || object instanceof Float || object instanceof Integer) ? ReadableType.Number : ((object instanceof String) ? ReadableType.String : ((object instanceof ReadableArray) ? ReadableType.Array : ((object instanceof ReadableMap) ? ReadableType.Map : null)))));
  }
  
  public int hashCode() {
    List list = this.mBackingList;
    return (list != null) ? list.hashCode() : 0;
  }
  
  public boolean isNull(int paramInt) {
    return (this.mBackingList.get(paramInt) == null);
  }
  
  public void pushArray(WritableArray paramWritableArray) {
    this.mBackingList.add(paramWritableArray);
  }
  
  public void pushBoolean(boolean paramBoolean) {
    this.mBackingList.add(Boolean.valueOf(paramBoolean));
  }
  
  public void pushDouble(double paramDouble) {
    this.mBackingList.add(Double.valueOf(paramDouble));
  }
  
  public void pushInt(int paramInt) {
    this.mBackingList.add(Integer.valueOf(paramInt));
  }
  
  public void pushMap(WritableMap paramWritableMap) {
    this.mBackingList.add(paramWritableMap);
  }
  
  public void pushNull() {
    this.mBackingList.add(null);
  }
  
  public void pushString(String paramString) {
    this.mBackingList.add(paramString);
  }
  
  public int size() {
    return this.mBackingList.size();
  }
  
  public ArrayList<Object> toArrayList() {
    return new ArrayList(this.mBackingList);
  }
  
  public String toString() {
    return this.mBackingList.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\JavaOnlyArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */