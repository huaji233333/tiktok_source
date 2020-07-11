package com.facebook.react.bridge;

import android.os.Bundle;
import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Arguments {
  private static void addEntry(WritableNativeMap paramWritableNativeMap, String paramString, Object paramObject) {
    paramObject = makeNativeObject(paramObject);
    if (paramObject == null) {
      paramWritableNativeMap.putNull(paramString);
      return;
    } 
    if (paramObject instanceof Boolean) {
      paramWritableNativeMap.putBoolean(paramString, ((Boolean)paramObject).booleanValue());
      return;
    } 
    if (paramObject instanceof Integer) {
      paramWritableNativeMap.putInt(paramString, ((Integer)paramObject).intValue());
      return;
    } 
    if (paramObject instanceof Number) {
      paramWritableNativeMap.putDouble(paramString, ((Number)paramObject).doubleValue());
      return;
    } 
    if (paramObject instanceof String) {
      paramWritableNativeMap.putString(paramString, (String)paramObject);
      return;
    } 
    if (paramObject instanceof WritableNativeArray) {
      paramWritableNativeMap.putArray(paramString, (WritableNativeArray)paramObject);
      return;
    } 
    if (paramObject instanceof WritableNativeMap) {
      paramWritableNativeMap.putMap(paramString, (WritableNativeMap)paramObject);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Could not convert ");
    stringBuilder.append(paramObject.getClass());
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public static WritableArray createArray() {
    return new WritableNativeArray();
  }
  
  public static WritableMap createMap() {
    return new WritableNativeMap();
  }
  
  public static WritableArray fromArray(Object paramObject) {
    StringBuilder stringBuilder;
    WritableArray writableArray = createArray();
    boolean bool = paramObject instanceof String[];
    int j = 0;
    int k = 0;
    boolean bool1 = false;
    boolean bool2 = false;
    boolean bool3 = false;
    int i = 0;
    if (bool) {
      paramObject = paramObject;
      j = paramObject.length;
      while (i < j) {
        writableArray.pushString((String)paramObject[i]);
        i++;
      } 
    } else if (paramObject instanceof Bundle[]) {
      paramObject = paramObject;
      k = paramObject.length;
      for (i = j; i < k; i++)
        writableArray.pushMap(fromBundle((Bundle)paramObject[i])); 
    } else if (paramObject instanceof int[]) {
      paramObject = paramObject;
      j = paramObject.length;
      for (i = k; i < j; i++)
        writableArray.pushInt(paramObject[i]); 
    } else if (paramObject instanceof float[]) {
      paramObject = paramObject;
      j = paramObject.length;
      for (i = bool1; i < j; i++)
        writableArray.pushDouble(paramObject[i]); 
    } else if (paramObject instanceof double[]) {
      paramObject = paramObject;
      j = paramObject.length;
      for (i = bool2; i < j; i++)
        writableArray.pushDouble(paramObject[i]); 
    } else {
      if (paramObject instanceof boolean[]) {
        paramObject = paramObject;
        j = paramObject.length;
        for (i = bool3; i < j; i++)
          writableArray.pushBoolean(paramObject[i]); 
        return writableArray;
      } 
      stringBuilder = new StringBuilder("Unknown array type ");
      stringBuilder.append(paramObject.getClass());
      paramObject = new IllegalArgumentException(stringBuilder.toString());
      throw paramObject;
    } 
    return (WritableArray)stringBuilder;
  }
  
  public static WritableMap fromBundle(Bundle paramBundle) {
    WritableMap writableMap = createMap();
    for (String str : paramBundle.keySet()) {
      Object object = paramBundle.get(str);
      if (object == null) {
        writableMap.putNull(str);
        continue;
      } 
      if (object.getClass().isArray()) {
        writableMap.putArray(str, fromArray(object));
        continue;
      } 
      if (object instanceof String) {
        writableMap.putString(str, (String)object);
        continue;
      } 
      if (object instanceof Number) {
        if (object instanceof Integer) {
          writableMap.putInt(str, ((Integer)object).intValue());
          continue;
        } 
        writableMap.putDouble(str, ((Number)object).doubleValue());
        continue;
      } 
      if (object instanceof Boolean) {
        writableMap.putBoolean(str, ((Boolean)object).booleanValue());
        continue;
      } 
      if (object instanceof Bundle) {
        writableMap.putMap(str, fromBundle((Bundle)object));
        continue;
      } 
      if (object instanceof List) {
        writableMap.putArray(str, fromList((List)object));
        continue;
      } 
      StringBuilder stringBuilder = new StringBuilder("Could not convert ");
      stringBuilder.append(object.getClass());
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    return writableMap;
  }
  
  public static WritableNativeArray fromJavaArgs(Object[] paramArrayOfObject) {
    WritableNativeArray writableNativeArray = new WritableNativeArray();
    for (int i = 0; i < paramArrayOfObject.length; i++) {
      Object object = paramArrayOfObject[i];
      if (object == null) {
        writableNativeArray.pushNull();
      } else {
        Class<?> clazz = object.getClass();
        if (clazz == Boolean.class) {
          writableNativeArray.pushBoolean(((Boolean)object).booleanValue());
        } else if (clazz == Integer.class) {
          writableNativeArray.pushDouble(((Integer)object).doubleValue());
        } else if (clazz == Double.class) {
          writableNativeArray.pushDouble(((Double)object).doubleValue());
        } else if (clazz == Float.class) {
          writableNativeArray.pushDouble(((Float)object).doubleValue());
        } else if (clazz == String.class) {
          writableNativeArray.pushString(object.toString());
        } else if (clazz == WritableNativeMap.class) {
          writableNativeArray.pushMap((WritableNativeMap)object);
        } else if (clazz == WritableNativeArray.class) {
          writableNativeArray.pushArray((WritableNativeArray)object);
        } else {
          StringBuilder stringBuilder = new StringBuilder("Cannot convert argument of type ");
          stringBuilder.append(clazz);
          throw new RuntimeException(stringBuilder.toString());
        } 
      } 
    } 
    return writableNativeArray;
  }
  
  public static WritableArray fromList(List paramList) {
    StringBuilder stringBuilder;
    WritableArray writableArray = createArray();
    for (List paramList : paramList) {
      if (paramList == null) {
        writableArray.pushNull();
        continue;
      } 
      if (paramList.getClass().isArray()) {
        writableArray.pushArray(fromArray(paramList));
        continue;
      } 
      if (paramList instanceof Bundle) {
        writableArray.pushMap(fromBundle((Bundle)paramList));
        continue;
      } 
      if (paramList instanceof List) {
        writableArray.pushArray(fromList(paramList));
        continue;
      } 
      if (paramList instanceof String) {
        writableArray.pushString((String)paramList);
        continue;
      } 
      if (paramList instanceof Integer) {
        writableArray.pushInt(((Integer)paramList).intValue());
        continue;
      } 
      if (paramList instanceof Number) {
        writableArray.pushDouble(((Number)paramList).doubleValue());
        continue;
      } 
      if (paramList instanceof Boolean) {
        writableArray.pushBoolean(((Boolean)paramList).booleanValue());
        continue;
      } 
      stringBuilder = new StringBuilder("Unknown value type ");
      stringBuilder.append(paramList.getClass());
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    return (WritableArray)stringBuilder;
  }
  
  public static <T> WritableNativeArray makeNativeArray(final Object objects) {
    return (objects == null) ? new WritableNativeArray() : makeNativeArray(new AbstractList() {
          public final Object get(int param1Int) {
            return Array.get(objects, param1Int);
          }
          
          public final int size() {
            return Array.getLength(objects);
          }
        });
  }
  
  public static WritableNativeArray makeNativeArray(List paramList) {
    StringBuilder stringBuilder;
    WritableNativeArray writableNativeArray = new WritableNativeArray();
    if (paramList == null)
      return writableNativeArray; 
    Iterator iterator = paramList.iterator();
    while (iterator.hasNext()) {
      Object object = makeNativeObject(iterator.next());
      if (object == null) {
        writableNativeArray.pushNull();
        continue;
      } 
      if (object instanceof Boolean) {
        writableNativeArray.pushBoolean(((Boolean)object).booleanValue());
        continue;
      } 
      if (object instanceof Integer) {
        writableNativeArray.pushInt(((Integer)object).intValue());
        continue;
      } 
      if (object instanceof Double) {
        writableNativeArray.pushDouble(((Double)object).doubleValue());
        continue;
      } 
      if (object instanceof String) {
        writableNativeArray.pushString((String)object);
        continue;
      } 
      if (object instanceof WritableNativeArray) {
        writableNativeArray.pushArray((WritableNativeArray)object);
        continue;
      } 
      if (object instanceof WritableNativeMap) {
        writableNativeArray.pushMap((WritableNativeMap)object);
        continue;
      } 
      stringBuilder = new StringBuilder("Could not convert ");
      stringBuilder.append(object.getClass());
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    return (WritableNativeArray)stringBuilder;
  }
  
  public static WritableNativeMap makeNativeMap(Bundle paramBundle) {
    WritableNativeMap writableNativeMap = new WritableNativeMap();
    if (paramBundle == null)
      return writableNativeMap; 
    for (String str : paramBundle.keySet())
      addEntry(writableNativeMap, str, paramBundle.get(str)); 
    return writableNativeMap;
  }
  
  public static WritableNativeMap makeNativeMap(Map<String, Object> paramMap) {
    WritableNativeMap writableNativeMap = new WritableNativeMap();
    if (paramMap == null)
      return writableNativeMap; 
    for (Map.Entry<String, Object> entry : paramMap.entrySet())
      addEntry(writableNativeMap, (String)entry.getKey(), entry.getValue()); 
    return writableNativeMap;
  }
  
  private static Object makeNativeObject(Object paramObject) {
    if (paramObject == null)
      return null; 
    if (paramObject instanceof Float || paramObject instanceof Long || paramObject instanceof Byte || paramObject instanceof Short)
      return new Double(((Number)paramObject).doubleValue()); 
    if (paramObject.getClass().isArray())
      return makeNativeArray(paramObject); 
    if (paramObject instanceof List)
      return makeNativeArray((List)paramObject); 
    if (paramObject instanceof Map)
      return makeNativeMap((Map<String, Object>)paramObject); 
    Object object = paramObject;
    if (paramObject instanceof Bundle)
      object = makeNativeMap((Bundle)paramObject); 
    return object;
  }
  
  public static Bundle toBundle(ReadableMap paramReadableMap) {
    if (paramReadableMap == null)
      return null; 
    ReadableMapKeySetIterator readableMapKeySetIterator = paramReadableMap.keySetIterator();
    Bundle bundle = new Bundle();
    while (readableMapKeySetIterator.hasNextKey()) {
      StringBuilder stringBuilder;
      String str = readableMapKeySetIterator.nextKey();
      ReadableType readableType = paramReadableMap.getType(str);
      switch (readableType) {
        default:
          stringBuilder = new StringBuilder("Could not convert object with key: ");
          stringBuilder.append(str);
          stringBuilder.append(".");
          throw new IllegalArgumentException(stringBuilder.toString());
        case null:
          bundle.putSerializable(str, toList(stringBuilder.getArray(str)));
          continue;
        case Map:
          bundle.putBundle(str, toBundle(stringBuilder.getMap(str)));
          continue;
        case String:
          bundle.putString(str, stringBuilder.getString(str));
          continue;
        case Number:
          bundle.putDouble(str, stringBuilder.getDouble(str));
          continue;
        case Boolean:
          bundle.putBoolean(str, stringBuilder.getBoolean(str));
          continue;
        case Null:
          break;
      } 
      bundle.putString(str, null);
    } 
    return bundle;
  }
  
  public static ArrayList toList(ReadableArray paramReadableArray) {
    if (paramReadableArray == null)
      return null; 
    ArrayList<ArrayList> arrayList = new ArrayList();
    for (int i = 0; i < paramReadableArray.size(); i++) {
      double d;
      switch (paramReadableArray.getType(i)) {
        default:
          throw new IllegalArgumentException("Could not convert object in array.");
        case null:
          arrayList.add(toList(paramReadableArray.getArray(i)));
          break;
        case Map:
          arrayList.add(toBundle(paramReadableArray.getMap(i)));
          break;
        case String:
          arrayList.add(paramReadableArray.getString(i));
          break;
        case Number:
          d = paramReadableArray.getDouble(i);
          if (d == Math.rint(d)) {
            arrayList.add(Integer.valueOf((int)d));
            break;
          } 
          arrayList.add(Double.valueOf(d));
          break;
        case Boolean:
          arrayList.add(Boolean.valueOf(paramReadableArray.getBoolean(i)));
          break;
        case Null:
          arrayList.add(null);
          break;
      } 
    } 
    return arrayList;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\Arguments.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */