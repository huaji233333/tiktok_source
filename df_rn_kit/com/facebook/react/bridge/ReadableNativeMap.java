package com.facebook.react.bridge;

import com.facebook.i.a.a;
import com.facebook.jni.HybridData;
import java.util.HashMap;

public class ReadableNativeMap extends NativeMap implements ReadableMap {
  private static int mJniCallCounter;
  
  private static boolean mUseNativeAccessor;
  
  private String[] mKeys;
  
  private HashMap<String, Object> mLocalMap;
  
  private HashMap<String, ReadableType> mLocalTypeMap;
  
  protected ReadableNativeMap(HybridData paramHybridData) {
    super(paramHybridData);
  }
  
  private native ReadableNativeArray getArrayNative(String paramString);
  
  private native boolean getBooleanNative(String paramString);
  
  private native double getDoubleNative(String paramString);
  
  private native int getIntNative(String paramString);
  
  public static int getJNIPassCounter() {
    return mJniCallCounter;
  }
  
  private HashMap<String, Object> getLocalMap() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mLocalMap : Ljava/util/HashMap;
    //   4: astore_2
    //   5: aload_2
    //   6: ifnull -> 11
    //   9: aload_2
    //   10: areturn
    //   11: aload_0
    //   12: monitorenter
    //   13: aload_0
    //   14: getfield mKeys : [Ljava/lang/String;
    //   17: ifnonnull -> 42
    //   20: aload_0
    //   21: aload_0
    //   22: invokespecial importKeys : ()[Ljava/lang/String;
    //   25: invokestatic b : (Ljava/lang/Object;)Ljava/lang/Object;
    //   28: checkcast [Ljava/lang/String;
    //   31: putfield mKeys : [Ljava/lang/String;
    //   34: getstatic com/facebook/react/bridge/ReadableNativeMap.mJniCallCounter : I
    //   37: iconst_1
    //   38: iadd
    //   39: putstatic com/facebook/react/bridge/ReadableNativeMap.mJniCallCounter : I
    //   42: aload_0
    //   43: getfield mLocalMap : Ljava/util/HashMap;
    //   46: ifnonnull -> 114
    //   49: aload_0
    //   50: invokespecial importValues : ()[Ljava/lang/Object;
    //   53: invokestatic b : (Ljava/lang/Object;)Ljava/lang/Object;
    //   56: checkcast [Ljava/lang/Object;
    //   59: astore_2
    //   60: getstatic com/facebook/react/bridge/ReadableNativeMap.mJniCallCounter : I
    //   63: iconst_1
    //   64: iadd
    //   65: putstatic com/facebook/react/bridge/ReadableNativeMap.mJniCallCounter : I
    //   68: aload_0
    //   69: new java/util/HashMap
    //   72: dup
    //   73: invokespecial <init> : ()V
    //   76: putfield mLocalMap : Ljava/util/HashMap;
    //   79: iconst_0
    //   80: istore_1
    //   81: iload_1
    //   82: aload_0
    //   83: getfield mKeys : [Ljava/lang/String;
    //   86: arraylength
    //   87: if_icmpge -> 114
    //   90: aload_0
    //   91: getfield mLocalMap : Ljava/util/HashMap;
    //   94: aload_0
    //   95: getfield mKeys : [Ljava/lang/String;
    //   98: iload_1
    //   99: aaload
    //   100: aload_2
    //   101: iload_1
    //   102: aaload
    //   103: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   106: pop
    //   107: iload_1
    //   108: iconst_1
    //   109: iadd
    //   110: istore_1
    //   111: goto -> 81
    //   114: aload_0
    //   115: monitorexit
    //   116: aload_0
    //   117: getfield mLocalMap : Ljava/util/HashMap;
    //   120: areturn
    //   121: astore_2
    //   122: aload_0
    //   123: monitorexit
    //   124: goto -> 129
    //   127: aload_2
    //   128: athrow
    //   129: goto -> 127
    // Exception table:
    //   from	to	target	type
    //   13	42	121	finally
    //   42	79	121	finally
    //   81	107	121	finally
    //   114	116	121	finally
    //   122	124	121	finally
  }
  
  private HashMap<String, ReadableType> getLocalTypeMap() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mLocalTypeMap : Ljava/util/HashMap;
    //   4: astore_2
    //   5: aload_2
    //   6: ifnull -> 11
    //   9: aload_2
    //   10: areturn
    //   11: aload_0
    //   12: monitorenter
    //   13: aload_0
    //   14: getfield mKeys : [Ljava/lang/String;
    //   17: ifnonnull -> 42
    //   20: aload_0
    //   21: aload_0
    //   22: invokespecial importKeys : ()[Ljava/lang/String;
    //   25: invokestatic b : (Ljava/lang/Object;)Ljava/lang/Object;
    //   28: checkcast [Ljava/lang/String;
    //   31: putfield mKeys : [Ljava/lang/String;
    //   34: getstatic com/facebook/react/bridge/ReadableNativeMap.mJniCallCounter : I
    //   37: iconst_1
    //   38: iadd
    //   39: putstatic com/facebook/react/bridge/ReadableNativeMap.mJniCallCounter : I
    //   42: aload_0
    //   43: getfield mLocalTypeMap : Ljava/util/HashMap;
    //   46: ifnonnull -> 117
    //   49: aload_0
    //   50: invokespecial importTypes : ()[Ljava/lang/Object;
    //   53: invokestatic b : (Ljava/lang/Object;)Ljava/lang/Object;
    //   56: checkcast [Ljava/lang/Object;
    //   59: astore_2
    //   60: getstatic com/facebook/react/bridge/ReadableNativeMap.mJniCallCounter : I
    //   63: iconst_1
    //   64: iadd
    //   65: putstatic com/facebook/react/bridge/ReadableNativeMap.mJniCallCounter : I
    //   68: aload_0
    //   69: new java/util/HashMap
    //   72: dup
    //   73: invokespecial <init> : ()V
    //   76: putfield mLocalTypeMap : Ljava/util/HashMap;
    //   79: iconst_0
    //   80: istore_1
    //   81: iload_1
    //   82: aload_0
    //   83: getfield mKeys : [Ljava/lang/String;
    //   86: arraylength
    //   87: if_icmpge -> 117
    //   90: aload_0
    //   91: getfield mLocalTypeMap : Ljava/util/HashMap;
    //   94: aload_0
    //   95: getfield mKeys : [Ljava/lang/String;
    //   98: iload_1
    //   99: aaload
    //   100: aload_2
    //   101: iload_1
    //   102: aaload
    //   103: checkcast com/facebook/react/bridge/ReadableType
    //   106: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   109: pop
    //   110: iload_1
    //   111: iconst_1
    //   112: iadd
    //   113: istore_1
    //   114: goto -> 81
    //   117: aload_0
    //   118: monitorexit
    //   119: aload_0
    //   120: getfield mLocalTypeMap : Ljava/util/HashMap;
    //   123: areturn
    //   124: astore_2
    //   125: aload_0
    //   126: monitorexit
    //   127: goto -> 132
    //   130: aload_2
    //   131: athrow
    //   132: goto -> 130
    // Exception table:
    //   from	to	target	type
    //   13	42	124	finally
    //   42	79	124	finally
    //   81	110	124	finally
    //   117	119	124	finally
    //   125	127	124	finally
  }
  
  private native ReadableNativeMap getMapNative(String paramString);
  
  private Object getNullableValue(String paramString) {
    if (hasKey(paramString))
      return getLocalMap().get(paramString); 
    throw new NoSuchKeyException(paramString);
  }
  
  private native String getStringNative(String paramString);
  
  private native ReadableType getTypeNative(String paramString);
  
  private Object getValue(String paramString) {
    if (hasKey(paramString) && !isNull(paramString))
      return a.b(getLocalMap().get(paramString)); 
    throw new NoSuchKeyException(paramString);
  }
  
  private native boolean hasKeyNative(String paramString);
  
  private native String[] importKeys();
  
  private native Object[] importTypes();
  
  private native Object[] importValues();
  
  private native boolean isNullNative(String paramString);
  
  public static void setUseNativeAccessor(boolean paramBoolean) {
    mUseNativeAccessor = paramBoolean;
  }
  
  private double transformDataType(String paramString) {
    Object object = getValue(paramString);
    return (object != null && object.toString().endsWith("deg")) ? (Double.valueOf(object.toString().replace("deg", "")).doubleValue() * Math.PI / 180.0D) : ((Double)object).doubleValue();
  }
  
  public ReadableArray getArray(String paramString) {
    if (mUseNativeAccessor) {
      mJniCallCounter++;
      return getArrayNative(paramString);
    } 
    return (ReadableArray)getNullableValue(paramString);
  }
  
  public boolean getBoolean(String paramString) {
    if (mUseNativeAccessor) {
      mJniCallCounter++;
      return getBooleanNative(paramString);
    } 
    return ((Boolean)getValue(paramString)).booleanValue();
  }
  
  public double getDouble(String paramString) {
    if (mUseNativeAccessor) {
      mJniCallCounter++;
      return getDoubleNative(paramString);
    } 
    return ((Double)getValue(paramString)).doubleValue();
  }
  
  public double getDoubleFromDeg(String paramString) {
    if (mUseNativeAccessor) {
      mJniCallCounter++;
      return getDoubleNative(paramString);
    } 
    return transformDataType(paramString);
  }
  
  public Dynamic getDynamic(String paramString) {
    return DynamicFromMap.create(this, paramString);
  }
  
  public int getInt(String paramString) {
    if (mUseNativeAccessor) {
      mJniCallCounter++;
      return getIntNative(paramString);
    } 
    return ((Double)getValue(paramString)).intValue();
  }
  
  public ReadableNativeMap getMap(String paramString) {
    if (mUseNativeAccessor) {
      mJniCallCounter++;
      return getMapNative(paramString);
    } 
    return (ReadableNativeMap)getNullableValue(paramString);
  }
  
  public String getString(String paramString) {
    if (mUseNativeAccessor) {
      mJniCallCounter++;
      return getStringNative(paramString);
    } 
    return (String)getNullableValue(paramString);
  }
  
  public ReadableType getType(String paramString) {
    if (mUseNativeAccessor) {
      mJniCallCounter++;
      return getTypeNative(paramString);
    } 
    if (getLocalTypeMap().containsKey(paramString))
      return (ReadableType)a.b(getLocalTypeMap().get(paramString)); 
    throw new NoSuchKeyException(paramString);
  }
  
  public boolean hasKey(String paramString) {
    if (mUseNativeAccessor) {
      mJniCallCounter++;
      return hasKeyNative(paramString);
    } 
    return getLocalMap().containsKey(paramString);
  }
  
  public boolean isNull(String paramString) {
    if (mUseNativeAccessor) {
      mJniCallCounter++;
      return isNullNative(paramString);
    } 
    if (getLocalMap().containsKey(paramString))
      return (getLocalMap().get(paramString) == null); 
    throw new NoSuchKeyException(paramString);
  }
  
  public ReadableMapKeySetIterator keySetIterator() {
    return new ReadableNativeMapKeySetIterator(this);
  }
  
  public HashMap<String, Object> toHashMap() {
    StringBuilder stringBuilder;
    if (mUseNativeAccessor) {
      ReadableMapKeySetIterator readableMapKeySetIterator = keySetIterator();
      HashMap<Object, Object> hashMap1 = new HashMap<Object, Object>();
      while (readableMapKeySetIterator.hasNextKey()) {
        mJniCallCounter++;
        String str = readableMapKeySetIterator.nextKey();
        mJniCallCounter++;
        switch (getType(str)) {
          default:
            stringBuilder = new StringBuilder("Could not convert object with key: ");
            stringBuilder.append(str);
            stringBuilder.append(".");
            throw new IllegalArgumentException(stringBuilder.toString());
          case null:
            hashMap1.put(str, ((ReadableArray)a.b(getArray(str))).toArrayList());
            continue;
          case Map:
            hashMap1.put(str, ((ReadableNativeMap)a.b(getMap(str))).toHashMap());
            continue;
          case String:
            hashMap1.put(str, getString(str));
            continue;
          case Number:
            hashMap1.put(str, Double.valueOf(getDouble(str)));
            continue;
          case Boolean:
            hashMap1.put(str, Boolean.valueOf(getBoolean(str)));
            continue;
          case Null:
            break;
        } 
        hashMap1.put(str, null);
      } 
      return (HashMap)hashMap1;
    } 
    HashMap<String, Object> hashMap = new HashMap<String, Object>(getLocalMap());
    for (String str : hashMap.keySet()) {
      switch (getType(str)) {
        case Null:
        case Boolean:
        case Number:
        case String:
          continue;
        default:
          stringBuilder = new StringBuilder("Could not convert object with key: ");
          stringBuilder.append(str);
          stringBuilder.append(".");
          throw new IllegalArgumentException(stringBuilder.toString());
        case null:
          stringBuilder.put(str, ((ReadableArray)a.b(getArray(str))).toArrayList());
          continue;
        case Map:
          break;
      } 
      stringBuilder.put(str, ((ReadableNativeMap)a.b(getMap(str))).toHashMap());
    } 
    return (HashMap<String, Object>)stringBuilder;
  }
  
  static class ReadableNativeMapKeySetIterator implements ReadableMapKeySetIterator {
    private final HybridData mHybridData;
    
    private final ReadableNativeMap mMap;
    
    public ReadableNativeMapKeySetIterator(ReadableNativeMap param1ReadableNativeMap) {
      this.mMap = param1ReadableNativeMap;
      this.mHybridData = initHybrid(param1ReadableNativeMap);
    }
    
    private static native HybridData initHybrid(ReadableNativeMap param1ReadableNativeMap);
    
    public native boolean hasNextKey();
    
    public native String nextKey();
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\ReadableNativeMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */