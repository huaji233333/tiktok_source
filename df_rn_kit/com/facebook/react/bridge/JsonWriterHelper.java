package com.facebook.react.bridge;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class JsonWriterHelper {
  private static void listValue(JsonWriter paramJsonWriter, List<?> paramList) throws IOException {
    paramJsonWriter.beginArray();
    Iterator<?> iterator = paramList.iterator();
    while (iterator.hasNext())
      objectValue(paramJsonWriter, iterator.next()); 
    paramJsonWriter.endArray();
  }
  
  private static void mapValue(JsonWriter paramJsonWriter, Map<?, ?> paramMap) throws IOException {
    paramJsonWriter.beginObject();
    for (Map.Entry<?, ?> entry : paramMap.entrySet()) {
      paramJsonWriter.name(entry.getKey().toString());
      value(paramJsonWriter, entry.getValue());
    } 
    paramJsonWriter.endObject();
  }
  
  private static void objectValue(JsonWriter paramJsonWriter, Object paramObject) throws IOException {
    if (paramObject == null) {
      paramJsonWriter.nullValue();
      return;
    } 
    if (paramObject instanceof String) {
      paramJsonWriter.value((String)paramObject);
      return;
    } 
    if (paramObject instanceof Number) {
      paramJsonWriter.value((Number)paramObject);
      return;
    } 
    if (paramObject instanceof Boolean) {
      paramJsonWriter.value(((Boolean)paramObject).booleanValue());
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder("Unknown value: ");
    stringBuilder.append(paramObject);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public static void value(JsonWriter paramJsonWriter, Object paramObject) throws IOException {
    if (paramObject instanceof Map) {
      mapValue(paramJsonWriter, (Map<?, ?>)paramObject);
      return;
    } 
    if (paramObject instanceof List) {
      listValue(paramJsonWriter, (List)paramObject);
      return;
    } 
    objectValue(paramJsonWriter, paramObject);
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\bridge\JsonWriterHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */