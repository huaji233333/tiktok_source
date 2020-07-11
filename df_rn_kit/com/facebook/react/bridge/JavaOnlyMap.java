package com.facebook.react.bridge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JavaOnlyMap implements ReadableMap, WritableMap {
  public final Map mBackingMap;
  
  public JavaOnlyMap() {
    this.mBackingMap = new HashMap<Object, Object>();
  }
  
  private JavaOnlyMap(Object... paramVarArgs) {
    if (paramVarArgs.length % 2 == 0) {
      this.mBackingMap = new HashMap<Object, Object>();
      for (int i = 0; i < paramVarArgs.length; i += 2)
        this.mBackingMap.put(paramVarArgs[i], paramVarArgs[i + 1]); 
      return;
    } 
    IllegalArgumentException illegalArgumentException = new IllegalArgumentException("You must provide the same number of keys and values");
    throw illegalArgumentException;
  }
  
  public static JavaOnlyMap deepClone(ReadableMap paramReadableMap) {
    JavaOnlyMap javaOnlyMap = new JavaOnlyMap();
    ReadableMapKeySetIterator readableMapKeySetIterator = paramReadableMap.keySetIterator();
    while (readableMapKeySetIterator.hasNextKey()) {
      String str = readableMapKeySetIterator.nextKey();
      ReadableType readableType = paramReadableMap.getType(str);
      switch (readableType) {
        default:
          continue;
        case null:
          javaOnlyMap.putArray(str, JavaOnlyArray.deepClone(paramReadableMap.getArray(str)));
          continue;
        case Map:
          javaOnlyMap.putMap(str, deepClone(paramReadableMap.getMap(str)));
          continue;
        case String:
          javaOnlyMap.putString(str, paramReadableMap.getString(str));
          continue;
        case Number:
          javaOnlyMap.putDouble(str, paramReadableMap.getDouble(str));
          continue;
        case Boolean:
          javaOnlyMap.putBoolean(str, paramReadableMap.getBoolean(str));
          continue;
        case Null:
          break;
      } 
      javaOnlyMap.putNull(str);
    } 
    return javaOnlyMap;
  }
  
  public static JavaOnlyMap of(Object... paramVarArgs) {
    return new JavaOnlyMap(paramVarArgs);
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject != null) {
      if (getClass() != paramObject.getClass())
        return false; 
      JavaOnlyMap javaOnlyMap = (JavaOnlyMap)paramObject;
      paramObject = this.mBackingMap;
      Map map = javaOnlyMap.mBackingMap;
      if (paramObject != null) {
        if (!paramObject.equals(map))
          return false; 
      } else if (map != null) {
        return false;
      } 
      return true;
    } 
    return false;
  }
  
  public JavaOnlyArray getArray(String paramString) {
    return (JavaOnlyArray)this.mBackingMap.get(paramString);
  }
  
  public boolean getBoolean(String paramString) {
    return ((Boolean)this.mBackingMap.get(paramString)).booleanValue();
  }
  
  public double getDouble(String paramString) {
    return ((Number)this.mBackingMap.get(paramString)).doubleValue();
  }
  
  public double getDoubleFromDeg(String paramString) {
    return ((Number)this.mBackingMap.get(paramString)).doubleValue();
  }
  
  public Dynamic getDynamic(String paramString) {
    return DynamicFromMap.create(this, paramString);
  }
  
  public int getInt(String paramString) {
    return ((Number)this.mBackingMap.get(paramString)).intValue();
  }
  
  public ReadableMap getMap(String paramString) {
    return (ReadableMap)this.mBackingMap.get(paramString);
  }
  
  public String getString(String paramString) {
    return (String)this.mBackingMap.get(paramString);
  }
  
  public ReadableType getType(String paramString) {
    Object object = this.mBackingMap.get(paramString);
    if (object == null)
      return ReadableType.Null; 
    if (object instanceof Number)
      return ReadableType.Number; 
    if (object instanceof String)
      return ReadableType.String; 
    if (object instanceof Boolean)
      return ReadableType.Boolean; 
    if (object instanceof ReadableMap)
      return ReadableType.Map; 
    if (object instanceof ReadableArray)
      return ReadableType.Array; 
    if (object instanceof Dynamic)
      return ((Dynamic)object).getType(); 
    StringBuilder stringBuilder = new StringBuilder("Invalid value ");
    stringBuilder.append(object.toString());
    stringBuilder.append(" for key ");
    stringBuilder.append(paramString);
    stringBuilder.append("contained in JavaOnlyMap");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public boolean hasKey(String paramString) {
    return this.mBackingMap.containsKey(paramString);
  }
  
  public int hashCode() {
    Map map = this.mBackingMap;
    return (map != null) ? map.hashCode() : 0;
  }
  
  public boolean isNull(String paramString) {
    return (this.mBackingMap.get(paramString) == null);
  }
  
  public ReadableMapKeySetIterator keySetIterator() {
    return new ReadableMapKeySetIterator() {
        Iterator<String> mIterator = JavaOnlyMap.this.mBackingMap.keySet().iterator();
        
        public boolean hasNextKey() {
          return this.mIterator.hasNext();
        }
        
        public String nextKey() {
          return this.mIterator.next();
        }
      };
  }
  
  public void merge(ReadableMap paramReadableMap) {
    this.mBackingMap.putAll(((JavaOnlyMap)paramReadableMap).mBackingMap);
  }
  
  public void putArray(String paramString, WritableArray paramWritableArray) {
    this.mBackingMap.put(paramString, paramWritableArray);
  }
  
  public void putBoolean(String paramString, boolean paramBoolean) {
    this.mBackingMap.put(paramString, Boolean.valueOf(paramBoolean));
  }
  
  public void putDouble(String paramString, double paramDouble) {
    this.mBackingMap.put(paramString, Double.valueOf(paramDouble));
  }
  
  public void putInt(String paramString, int paramInt) {
    this.mBackingMap.put(paramString, Integer.valueOf(paramInt));
  }
  
  public void putMap(String paramString, WritableMap paramWritableMap) {
    this.mBackingMap.put(paramString, paramWritableMap);
  }
  
  public void putNull(String paramString) {
    this.mBackingMap.put(paramString, null);
  }
  
  public void putString(String paramString1, String paramString2) {
    this.mBackingMap.put(paramString1, paramString2);
  }
  
  public HashMap<String, Object> toHashMap() {
    return new HashMap<String, Object>(this.mBackingMap);
  }
  
  public String toString() {
    return this.mBackingMap.toString();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\JavaOnlyMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */