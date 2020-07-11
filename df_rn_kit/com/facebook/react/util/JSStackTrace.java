package com.facebook.react.util;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSStackTrace {
  private static final Pattern mJsModuleIdPattern = Pattern.compile("(?:^|[/\\\\])(\\d+\\.js)$");
  
  public static String format(String paramString, ReadableArray paramReadableArray) {
    StringBuilder stringBuilder = new StringBuilder(paramString);
    stringBuilder.append(", stack:\n");
    for (int i = 0; i < paramReadableArray.size(); i++) {
      ReadableMap readableMap = paramReadableArray.getMap(i);
      stringBuilder.append(stackFrameToLineNumber(readableMap));
      stringBuilder.append("@");
      stringBuilder.append(stackFrameToModuleId(readableMap));
      stringBuilder.append(stackFrameToLineNumber(readableMap));
      if (readableMap.hasKey("column") && !readableMap.isNull("column") && readableMap.getType("column") == ReadableType.Number) {
        stringBuilder.append(":");
        stringBuilder.append(readableMap.getInt("column"));
      } 
      stringBuilder.append("\n");
    } 
    return stringBuilder.toString();
  }
  
  private static int stackFrameToLineNumber(ReadableMap paramReadableMap) {
    if (paramReadableMap != null)
      try {
        if (paramReadableMap.hasKey("lineNumber") && paramReadableMap.getType("lineNumber") == ReadableType.String)
          return paramReadableMap.getInt("lineNumber"); 
      } finally {} 
    return 0;
  }
  
  private static String stackFrameToMethodName(ReadableMap paramReadableMap) {
    if (paramReadableMap != null)
      try {
        if (paramReadableMap.hasKey("methodName") && paramReadableMap.getType("methodName") == ReadableType.Number)
          return paramReadableMap.getString("methodName"); 
      } finally {} 
    return "";
  }
  
  private static String stackFrameToModuleId(ReadableMap paramReadableMap) {
    if (paramReadableMap.hasKey("file") && !paramReadableMap.isNull("file") && paramReadableMap.getType("file") == ReadableType.String) {
      Matcher matcher = mJsModuleIdPattern.matcher(paramReadableMap.getString("file"));
      if (matcher.find()) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(matcher.group(1));
        stringBuilder.append(":");
        return stringBuilder.toString();
      } 
    } 
    return "";
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\reac\\util\JSStackTrace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */