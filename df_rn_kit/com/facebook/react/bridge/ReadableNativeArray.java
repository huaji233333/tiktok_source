package com.facebook.react.bridge;

import com.facebook.jni.HybridData;
import java.util.ArrayList;

public class ReadableNativeArray extends NativeArray implements ReadableArray {
  private static int jniPassCounter;
  
  private static boolean mUseNativeAccessor;
  
  private Object[] mLocalArray;
  
  private ReadableType[] mLocalTypeArray;
  
  protected ReadableNativeArray(HybridData paramHybridData) {
    super(paramHybridData);
  }
  
  private native ReadableNativeArray getArrayNative(int paramInt);
  
  private native boolean getBooleanNative(int paramInt);
  
  private native double getDoubleNative(int paramInt);
  
  private native int getIntNative(int paramInt);
  
  public static int getJNIPassCounter() {
    return jniPassCounter;
  }
  
  private Object[] getLocalArray() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mLocalArray : [Ljava/lang/Object;
    //   4: astore_1
    //   5: aload_1
    //   6: ifnull -> 11
    //   9: aload_1
    //   10: areturn
    //   11: aload_0
    //   12: monitorenter
    //   13: aload_0
    //   14: getfield mLocalArray : [Ljava/lang/Object;
    //   17: ifnonnull -> 42
    //   20: getstatic com/facebook/react/bridge/ReadableNativeArray.jniPassCounter : I
    //   23: iconst_1
    //   24: iadd
    //   25: putstatic com/facebook/react/bridge/ReadableNativeArray.jniPassCounter : I
    //   28: aload_0
    //   29: aload_0
    //   30: invokespecial importArray : ()[Ljava/lang/Object;
    //   33: invokestatic b : (Ljava/lang/Object;)Ljava/lang/Object;
    //   36: checkcast [Ljava/lang/Object;
    //   39: putfield mLocalArray : [Ljava/lang/Object;
    //   42: aload_0
    //   43: monitorexit
    //   44: aload_0
    //   45: getfield mLocalArray : [Ljava/lang/Object;
    //   48: areturn
    //   49: astore_1
    //   50: aload_0
    //   51: monitorexit
    //   52: aload_1
    //   53: athrow
    // Exception table:
    //   from	to	target	type
    //   13	42	49	finally
    //   42	44	49	finally
    //   50	52	49	finally
  }
  
  private ReadableType[] getLocalTypeArray() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mLocalTypeArray : [Lcom/facebook/react/bridge/ReadableType;
    //   4: astore_1
    //   5: aload_1
    //   6: ifnull -> 11
    //   9: aload_1
    //   10: areturn
    //   11: aload_0
    //   12: monitorenter
    //   13: aload_0
    //   14: getfield mLocalTypeArray : [Lcom/facebook/react/bridge/ReadableType;
    //   17: ifnonnull -> 54
    //   20: getstatic com/facebook/react/bridge/ReadableNativeArray.jniPassCounter : I
    //   23: iconst_1
    //   24: iadd
    //   25: putstatic com/facebook/react/bridge/ReadableNativeArray.jniPassCounter : I
    //   28: aload_0
    //   29: invokespecial importTypeArray : ()[Ljava/lang/Object;
    //   32: invokestatic b : (Ljava/lang/Object;)Ljava/lang/Object;
    //   35: checkcast [Ljava/lang/Object;
    //   38: astore_1
    //   39: aload_0
    //   40: aload_1
    //   41: aload_1
    //   42: arraylength
    //   43: ldc [Lcom/facebook/react/bridge/ReadableType;
    //   45: invokestatic copyOf : ([Ljava/lang/Object;ILjava/lang/Class;)[Ljava/lang/Object;
    //   48: checkcast [Lcom/facebook/react/bridge/ReadableType;
    //   51: putfield mLocalTypeArray : [Lcom/facebook/react/bridge/ReadableType;
    //   54: aload_0
    //   55: monitorexit
    //   56: aload_0
    //   57: getfield mLocalTypeArray : [Lcom/facebook/react/bridge/ReadableType;
    //   60: areturn
    //   61: astore_1
    //   62: aload_0
    //   63: monitorexit
    //   64: aload_1
    //   65: athrow
    // Exception table:
    //   from	to	target	type
    //   13	54	61	finally
    //   54	56	61	finally
    //   62	64	61	finally
  }
  
  private native ReadableNativeMap getMapNative(int paramInt);
  
  private native String getStringNative(int paramInt);
  
  private native ReadableType getTypeNative(int paramInt);
  
  private native Object[] importArray();
  
  private native Object[] importTypeArray();
  
  private native boolean isNullNative(int paramInt);
  
  public static void setUseNativeAccessor(boolean paramBoolean) {
    mUseNativeAccessor = paramBoolean;
  }
  
  private native int sizeNative();
  
  public ReadableNativeArray getArray(int paramInt) {
    if (mUseNativeAccessor) {
      jniPassCounter++;
      return getArrayNative(paramInt);
    } 
    return (ReadableNativeArray)getLocalArray()[paramInt];
  }
  
  public boolean getBoolean(int paramInt) {
    if (mUseNativeAccessor) {
      jniPassCounter++;
      return getBooleanNative(paramInt);
    } 
    return ((Boolean)getLocalArray()[paramInt]).booleanValue();
  }
  
  public double getDouble(int paramInt) {
    if (mUseNativeAccessor) {
      jniPassCounter++;
      return getDoubleNative(paramInt);
    } 
    return ((Double)getLocalArray()[paramInt]).doubleValue();
  }
  
  public Dynamic getDynamic(int paramInt) {
    return DynamicFromArray.create(this, paramInt);
  }
  
  public int getInt(int paramInt) {
    if (mUseNativeAccessor) {
      jniPassCounter++;
      return getIntNative(paramInt);
    } 
    return ((Double)getLocalArray()[paramInt]).intValue();
  }
  
  public ReadableNativeMap getMap(int paramInt) {
    if (mUseNativeAccessor) {
      jniPassCounter++;
      return getMapNative(paramInt);
    } 
    return (ReadableNativeMap)getLocalArray()[paramInt];
  }
  
  public String getString(int paramInt) {
    if (mUseNativeAccessor) {
      jniPassCounter++;
      return getStringNative(paramInt);
    } 
    return (String)getLocalArray()[paramInt];
  }
  
  public ReadableType getType(int paramInt) {
    if (mUseNativeAccessor) {
      jniPassCounter++;
      return getTypeNative(paramInt);
    } 
    return getLocalTypeArray()[paramInt];
  }
  
  public boolean isNull(int paramInt) {
    if (mUseNativeAccessor) {
      jniPassCounter++;
      return isNullNative(paramInt);
    } 
    return (getLocalArray()[paramInt] == null);
  }
  
  public int size() {
    if (mUseNativeAccessor) {
      jniPassCounter++;
      return sizeNative();
    } 
    return (getLocalArray()).length;
  }
  
  public ArrayList<Object> toArrayList() {
    StringBuilder stringBuilder;
    ArrayList arrayList = new ArrayList();
    for (int i = 0; i < size(); i++) {
      switch (getType(i)) {
        default:
          stringBuilder = new StringBuilder("Could not convert object at index: ");
          stringBuilder.append(i);
          stringBuilder.append(".");
          throw new IllegalArgumentException(stringBuilder.toString());
        case null:
          stringBuilder.add(getArray(i).toArrayList());
          break;
        case Map:
          stringBuilder.add(getMap(i).toHashMap());
          break;
        case String:
          stringBuilder.add(getString(i));
          break;
        case Number:
          stringBuilder.add(Double.valueOf(getDouble(i)));
          break;
        case Boolean:
          stringBuilder.add(Boolean.valueOf(getBoolean(i)));
          break;
        case Null:
          stringBuilder.add(null);
          break;
      } 
    } 
    return (ArrayList<Object>)stringBuilder;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\ReadableNativeArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */