package com.bytedance.ies.bullet.kit.rn.d;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class b {
  private static WritableArray a(JSONArray paramJSONArray) throws JSONException {
    WritableArray writableArray = Arguments.createArray();
    for (int i = 0; i < paramJSONArray.length(); i++) {
      Object object = paramJSONArray.get(i);
      if (object instanceof Float || object instanceof Double) {
        writableArray.pushDouble(paramJSONArray.getDouble(i));
      } else if (object instanceof Long) {
        object = object;
        int j = (int)object.longValue();
        double d = object.doubleValue();
        if (j == d) {
          writableArray.pushInt(paramJSONArray.getInt(i));
        } else {
          writableArray.pushDouble(paramJSONArray.getDouble(i));
        } 
      } else if (object instanceof Number) {
        writableArray.pushInt(paramJSONArray.getInt(i));
      } else if (object instanceof String) {
        writableArray.pushString(paramJSONArray.getString(i));
      } else if (object instanceof JSONObject) {
        writableArray.pushMap(a(paramJSONArray.getJSONObject(i)));
      } else if (object instanceof Boolean) {
        writableArray.pushBoolean(paramJSONArray.getBoolean(i));
      } else if (object instanceof JSONArray) {
        writableArray.pushArray(a(paramJSONArray.getJSONArray(i)));
      } else if (object == JSONObject.NULL) {
        writableArray.pushNull();
      } 
    } 
    return writableArray;
  }
  
  public static WritableMap a(JSONObject paramJSONObject) throws JSONException {
    WritableMap writableMap = Arguments.createMap();
    Iterator<String> iterator = paramJSONObject.keys();
    while (iterator.hasNext()) {
      String str = iterator.next();
      Object object = paramJSONObject.get(str);
      if (object instanceof Float || object instanceof Double) {
        writableMap.putDouble(str, paramJSONObject.getDouble(str));
        continue;
      } 
      if (object instanceof Long) {
        object = object;
        int i = (int)object.longValue();
        double d = object.doubleValue();
        if (i == d) {
          writableMap.putInt(str, paramJSONObject.getInt(str));
          continue;
        } 
        writableMap.putDouble(str, paramJSONObject.getDouble(str));
        continue;
      } 
      if (object instanceof Number) {
        writableMap.putInt(str, paramJSONObject.getInt(str));
        continue;
      } 
      if (object instanceof String) {
        writableMap.putString(str, paramJSONObject.getString(str));
        continue;
      } 
      if (object instanceof JSONObject) {
        writableMap.putMap(str, a(paramJSONObject.getJSONObject(str)));
        continue;
      } 
      if (object instanceof JSONArray) {
        writableMap.putArray(str, a(paramJSONObject.getJSONArray(str)));
        continue;
      } 
      if (object instanceof Boolean) {
        writableMap.putBoolean(str, paramJSONObject.getBoolean(str));
        continue;
      } 
      if (object == JSONObject.NULL)
        writableMap.putNull(str); 
    } 
    return writableMap;
  }
  
  private static JSONArray a(ReadableArray paramReadableArray) throws JSONException {
    // Byte code:
    //   0: new org/json/JSONArray
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore_2
    //   8: iconst_0
    //   9: istore_1
    //   10: iload_1
    //   11: aload_0
    //   12: invokeinterface size : ()I
    //   17: if_icmpge -> 190
    //   20: aload_0
    //   21: iload_1
    //   22: invokeinterface getType : (I)Lcom/facebook/react/bridge/ReadableType;
    //   27: astore_3
    //   28: getstatic com/bytedance/ies/bullet/kit/rn/d/b$1.a : [I
    //   31: aload_3
    //   32: invokevirtual ordinal : ()I
    //   35: iaload
    //   36: tableswitch default -> 76, 1 -> 175, 2 -> 160, 3 -> 130, 4 -> 115, 5 -> 97, 6 -> 79
    //   76: goto -> 183
    //   79: aload_2
    //   80: aload_0
    //   81: iload_1
    //   82: invokeinterface getArray : (I)Lcom/facebook/react/bridge/ReadableArray;
    //   87: invokestatic a : (Lcom/facebook/react/bridge/ReadableArray;)Lorg/json/JSONArray;
    //   90: invokevirtual put : (Ljava/lang/Object;)Lorg/json/JSONArray;
    //   93: pop
    //   94: goto -> 183
    //   97: aload_2
    //   98: aload_0
    //   99: iload_1
    //   100: invokeinterface getMap : (I)Lcom/facebook/react/bridge/ReadableMap;
    //   105: invokestatic a : (Lcom/facebook/react/bridge/ReadableMap;)Lorg/json/JSONObject;
    //   108: invokevirtual put : (Ljava/lang/Object;)Lorg/json/JSONArray;
    //   111: pop
    //   112: goto -> 183
    //   115: aload_2
    //   116: aload_0
    //   117: iload_1
    //   118: invokeinterface getString : (I)Ljava/lang/String;
    //   123: invokevirtual put : (Ljava/lang/Object;)Lorg/json/JSONArray;
    //   126: pop
    //   127: goto -> 183
    //   130: aload_2
    //   131: aload_0
    //   132: iload_1
    //   133: invokeinterface getInt : (I)I
    //   138: invokevirtual put : (I)Lorg/json/JSONArray;
    //   141: pop
    //   142: goto -> 183
    //   145: aload_2
    //   146: aload_0
    //   147: iload_1
    //   148: invokeinterface getDouble : (I)D
    //   153: invokevirtual put : (D)Lorg/json/JSONArray;
    //   156: pop
    //   157: goto -> 183
    //   160: aload_2
    //   161: aload_0
    //   162: iload_1
    //   163: invokeinterface getBoolean : (I)Z
    //   168: invokevirtual put : (Z)Lorg/json/JSONArray;
    //   171: pop
    //   172: goto -> 183
    //   175: aload_2
    //   176: getstatic org/json/JSONObject.NULL : Ljava/lang/Object;
    //   179: invokevirtual put : (Ljava/lang/Object;)Lorg/json/JSONArray;
    //   182: pop
    //   183: iload_1
    //   184: iconst_1
    //   185: iadd
    //   186: istore_1
    //   187: goto -> 10
    //   190: aload_2
    //   191: areturn
    //   192: astore_3
    //   193: goto -> 145
    // Exception table:
    //   from	to	target	type
    //   130	142	192	java/lang/Exception
  }
  
  public static JSONObject a(ReadableMap paramReadableMap) throws JSONException {
    // Byte code:
    //   0: new org/json/JSONObject
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore_1
    //   8: aload_0
    //   9: invokeinterface keySetIterator : ()Lcom/facebook/react/bridge/ReadableMapKeySetIterator;
    //   14: astore_2
    //   15: aload_2
    //   16: invokeinterface hasNextKey : ()Z
    //   21: ifeq -> 205
    //   24: aload_2
    //   25: invokeinterface nextKey : ()Ljava/lang/String;
    //   30: astore_3
    //   31: aload_0
    //   32: aload_3
    //   33: invokeinterface getType : (Ljava/lang/String;)Lcom/facebook/react/bridge/ReadableType;
    //   38: astore #4
    //   40: getstatic com/bytedance/ies/bullet/kit/rn/d/b$1.a : [I
    //   43: aload #4
    //   45: invokevirtual ordinal : ()I
    //   48: iaload
    //   49: tableswitch default -> 88, 1 -> 193, 2 -> 177, 3 -> 145, 4 -> 129, 5 -> 110, 6 -> 91
    //   88: goto -> 15
    //   91: aload_1
    //   92: aload_3
    //   93: aload_0
    //   94: aload_3
    //   95: invokeinterface getArray : (Ljava/lang/String;)Lcom/facebook/react/bridge/ReadableArray;
    //   100: invokestatic a : (Lcom/facebook/react/bridge/ReadableArray;)Lorg/json/JSONArray;
    //   103: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   106: pop
    //   107: goto -> 15
    //   110: aload_1
    //   111: aload_3
    //   112: aload_0
    //   113: aload_3
    //   114: invokeinterface getMap : (Ljava/lang/String;)Lcom/facebook/react/bridge/ReadableMap;
    //   119: invokestatic a : (Lcom/facebook/react/bridge/ReadableMap;)Lorg/json/JSONObject;
    //   122: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   125: pop
    //   126: goto -> 15
    //   129: aload_1
    //   130: aload_3
    //   131: aload_0
    //   132: aload_3
    //   133: invokeinterface getString : (Ljava/lang/String;)Ljava/lang/String;
    //   138: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   141: pop
    //   142: goto -> 15
    //   145: aload_1
    //   146: aload_3
    //   147: aload_0
    //   148: aload_3
    //   149: invokeinterface getInt : (Ljava/lang/String;)I
    //   154: invokevirtual put : (Ljava/lang/String;I)Lorg/json/JSONObject;
    //   157: pop
    //   158: goto -> 15
    //   161: aload_1
    //   162: aload_3
    //   163: aload_0
    //   164: aload_3
    //   165: invokeinterface getDouble : (Ljava/lang/String;)D
    //   170: invokevirtual put : (Ljava/lang/String;D)Lorg/json/JSONObject;
    //   173: pop
    //   174: goto -> 15
    //   177: aload_1
    //   178: aload_3
    //   179: aload_0
    //   180: aload_3
    //   181: invokeinterface getBoolean : (Ljava/lang/String;)Z
    //   186: invokevirtual put : (Ljava/lang/String;Z)Lorg/json/JSONObject;
    //   189: pop
    //   190: goto -> 15
    //   193: aload_1
    //   194: aload_3
    //   195: getstatic org/json/JSONObject.NULL : Ljava/lang/Object;
    //   198: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   201: pop
    //   202: goto -> 15
    //   205: aload_1
    //   206: areturn
    //   207: astore #4
    //   209: goto -> 161
    // Exception table:
    //   from	to	target	type
    //   145	158	207	java/lang/Exception
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\bytedance\ies\bullet\kit\rn\d\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */