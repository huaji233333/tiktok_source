package com.facebook.react.devsupport;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.devsupport.interfaces.StackFrame;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StackTraceHelper {
  private static final Pattern STACK_FRAME_PATTERN = Pattern.compile("^(?:(.*?)@)?(.*?)\\:([0-9]+)\\:([0-9]+)$");
  
  public static StackFrame[] convertJavaStackTrace(Throwable paramThrowable) {
    StackTraceElement[] arrayOfStackTraceElement = paramThrowable.getStackTrace();
    StackFrame[] arrayOfStackFrame = new StackFrame[arrayOfStackTraceElement.length];
    for (int i = 0; i < arrayOfStackTraceElement.length; i++)
      arrayOfStackFrame[i] = new StackFrameImpl(arrayOfStackTraceElement[i].getClassName(), arrayOfStackTraceElement[i].getFileName(), arrayOfStackTraceElement[i].getMethodName(), arrayOfStackTraceElement[i].getLineNumber(), -1); 
    return arrayOfStackFrame;
  }
  
  public static StackFrame[] convertJsStackTrace(ReadableArray paramReadableArray) {
    byte b;
    int i = 0;
    if (paramReadableArray != null) {
      b = paramReadableArray.size();
    } else {
      b = 0;
    } 
    StackFrame[] arrayOfStackFrame = new StackFrame[b];
    while (i < b) {
      ReadableMap readableMap;
      ReadableType readableType = paramReadableArray.getType(i);
      if (readableType == ReadableType.Map) {
        byte b1;
        byte b2;
        readableMap = paramReadableArray.getMap(i);
        String str1 = readableMap.getString("methodName");
        String str2 = readableMap.getString("file");
        if (readableMap.hasKey("lineNumber") && !readableMap.isNull("lineNumber")) {
          b1 = readableMap.getInt("lineNumber");
        } else {
          b1 = -1;
        } 
        if (readableMap.hasKey("column") && !readableMap.isNull("column")) {
          b2 = readableMap.getInt("column");
        } else {
          b2 = -1;
        } 
        arrayOfStackFrame[i] = new StackFrameImpl(str2, str1, b1, b2);
      } else if (readableMap == ReadableType.String) {
        arrayOfStackFrame[i] = new StackFrameImpl(null, paramReadableArray.getString(i), -1, -1);
      } 
      i++;
    } 
    return arrayOfStackFrame;
  }
  
  public static StackFrame[] convertJsStackTrace(String paramString) {
    String[] arrayOfString = paramString.split("\n");
    StackFrame[] arrayOfStackFrame = new StackFrame[arrayOfString.length];
    for (int i = 0; i < arrayOfString.length; i++) {
      Matcher matcher = STACK_FRAME_PATTERN.matcher(arrayOfString[i]);
      if (matcher.find()) {
        String str = matcher.group(2);
        if (matcher.group(1) == null) {
          paramString = "(unknown)";
        } else {
          paramString = matcher.group(1);
        } 
        arrayOfStackFrame[i] = new StackFrameImpl(str, paramString, Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
      } else {
        arrayOfStackFrame[i] = new StackFrameImpl(null, arrayOfString[i], -1, -1);
      } 
    } 
    return arrayOfStackFrame;
  }
  
  public static StackFrame[] convertJsStackTrace(JSONArray paramJSONArray) {
    byte b;
    int i = 0;
    if (paramJSONArray != null) {
      b = paramJSONArray.length();
    } else {
      b = 0;
    } 
    StackFrame[] arrayOfStackFrame = new StackFrame[b];
    while (true) {
      if (i < b) {
        try {
          byte b1;
          byte b2;
          JSONObject jSONObject = paramJSONArray.getJSONObject(i);
          String str1 = jSONObject.getString("methodName");
          String str2 = jSONObject.getString("file");
          if (jSONObject.has("lineNumber") && !jSONObject.isNull("lineNumber")) {
            b1 = jSONObject.getInt("lineNumber");
          } else {
            b1 = -1;
          } 
          if (jSONObject.has("column") && !jSONObject.isNull("column")) {
            b2 = jSONObject.getInt("column");
          } else {
            b2 = -1;
          } 
          arrayOfStackFrame[i] = new StackFrameImpl(str2, str1, b1, b2);
          i++;
        } catch (JSONException jSONException) {
          throw new RuntimeException(jSONException);
        } 
        continue;
      } 
      return arrayOfStackFrame;
    } 
  }
  
  public static String formatFrameSource(StackFrame paramStackFrame) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramStackFrame.getFileName());
    int i = paramStackFrame.getLine();
    if (i > 0) {
      stringBuilder.append(":");
      stringBuilder.append(i);
      i = paramStackFrame.getColumn();
      if (i > 0) {
        stringBuilder.append(":");
        stringBuilder.append(i);
      } 
    } 
    return stringBuilder.toString();
  }
  
  public static String formatStackTrace(String paramString, StackFrame[] paramArrayOfStackFrame) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append("\n");
    int j = paramArrayOfStackFrame.length;
    for (int i = 0; i < j; i++) {
      StackFrame stackFrame = paramArrayOfStackFrame[i];
      stringBuilder.append(stackFrame.getMethod());
      stringBuilder.append("\n    ");
      stringBuilder.append(formatFrameSource(stackFrame));
      stringBuilder.append("\n");
    } 
    return stringBuilder.toString();
  }
  
  public static String formatStackTrace(StackFrame[] paramArrayOfStackFrame, boolean paramBoolean) {
    StringBuilder stringBuilder = new StringBuilder();
    int j = paramArrayOfStackFrame.length;
    for (int i = 0; i < j; i++) {
      String str1;
      StackFrame stackFrame = paramArrayOfStackFrame[i];
      stringBuilder.append(stackFrame.getMethod());
      String str2 = "\n";
      if (paramBoolean) {
        str1 = "\n";
      } else {
        str1 = ":";
      } 
      stringBuilder.append(str1);
      if (paramBoolean) {
        str1 = "    ";
      } else {
        str1 = "";
      } 
      stringBuilder.append(str1);
      stringBuilder.append(formatFrameSource(stackFrame));
      if (paramBoolean) {
        str1 = str2;
      } else {
        str1 = "    ";
      } 
      stringBuilder.append(str1);
    } 
    return stringBuilder.toString();
  }
  
  public static class StackFrameImpl implements StackFrame {
    private final int mColumn;
    
    private final String mFile;
    
    private final String mFileName;
    
    private final int mLine;
    
    private final String mMethod;
    
    private StackFrameImpl(String param1String1, String param1String2, int param1Int1, int param1Int2) {
      this.mFile = param1String1;
      this.mMethod = param1String2;
      this.mLine = param1Int1;
      this.mColumn = param1Int2;
      if (param1String1 != null) {
        param1String1 = (new File(param1String1)).getName();
      } else {
        param1String1 = "";
      } 
      this.mFileName = param1String1;
    }
    
    private StackFrameImpl(String param1String1, String param1String2, String param1String3, int param1Int1, int param1Int2) {
      this.mFile = param1String1;
      this.mFileName = param1String2;
      this.mMethod = param1String3;
      this.mLine = param1Int1;
      this.mColumn = param1Int2;
    }
    
    public int getColumn() {
      return this.mColumn;
    }
    
    public String getFile() {
      return this.mFile;
    }
    
    public String getFileName() {
      return this.mFileName;
    }
    
    public int getLine() {
      return this.mLine;
    }
    
    public String getMethod() {
      return this.mMethod;
    }
    
    public JSONObject toJSON() {
      return new JSONObject(MapBuilder.of("file", getFile(), "methodName", getMethod(), "lineNumber", Integer.valueOf(getLine()), "column", Integer.valueOf(getColumn())));
    }
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_rn_kit\classes.jar.jar!\com\facebook\react\devsupport\StackTraceHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */